package com.tym.Tmall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tym.Tmall.common.constant.AuthServerConstant;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.Query;
import com.tym.Tmall.common.utils.R;
import com.tym.Tmall.common.vo.MemberResponseVo;
import com.tym.Tmall.order.dao.OrderDao;
import com.tym.Tmall.order.entity.OrderEntity;
import com.tym.Tmall.order.entity.OrderItemEntity;
import com.tym.Tmall.order.enume.OrderStatusEnum;
import com.tym.Tmall.order.feign.CartFeignService;
import com.tym.Tmall.order.feign.MemberFeignService;
import com.tym.Tmall.order.feign.ProductFeignService;
import com.tym.Tmall.order.service.OrderItemService;
import com.tym.Tmall.order.service.OrderService;
import com.tym.Tmall.order.to.OrderCreateTo;
import com.tym.Tmall.order.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    OrderItemService orderItemService;
    @Resource
    MemberFeignService memberFeignService;

    @Resource
    CartFeignService cartFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Resource
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVO confirmOrder() {

        String s = redisTemplate.opsForValue().get(AuthServerConstant.LOGIN_USER);
        MemberResponseVo memberResponseVo = JSON.parseObject(s, MemberResponseVo.class);
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        List<MemberAddressVO> address = memberFeignService.getAddress(memberResponseVo.getId());
        List<OrderItemVO> orderItemVOS = cartFeignService.getcurrentUserCartItems();
        orderConfirmVO.setMemberAddressVos(address);
        orderConfirmVO.setItems(orderItemVOS);
        Integer integration = memberResponseVo.getIntegration();
        orderConfirmVO.setIntegration(integration);

        return orderConfirmVO;
    }

    @Override
    @Transactional
    public SubmitOrderResponseVO submitOrder(OrderSubmitVO submitVO) {
        SubmitOrderResponseVO responseVO = new SubmitOrderResponseVO();
        responseVO.setCode(0);
        if (submitVO==null){
            responseVO.setCode(1);
            return responseVO;
        }else {
            OrderCreateTo order = orderCreateTo();
            saveOrder(order);
            responseVO.setOrder(order.getOrder());
            return responseVO;
        }


    }

    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {
        String s = redisTemplate.opsForValue().get(AuthServerConstant.LOGIN_USER);
        MemberResponseVo memberResponseVo = JSON.parseObject(s, MemberResponseVo.class);
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().eq("member_id",memberResponseVo.getId())
        );

        List<OrderEntity> collect = page.getRecords().stream().map(order -> {
            List<OrderItemEntity> itemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", order.getOrderSn()));

            order.setItemEntities(itemEntities);
            return order;
        }).collect(Collectors.toList());
        page.setRecords(collect);
        return new PageUtils(page);
    }

    private void saveOrder(OrderCreateTo order) {
        OrderEntity orderEntity = order.getOrder();
        orderEntity.setModifyTime(new Date());
        this.save(orderEntity);
        List<OrderItemEntity> orderItems = order.getOrderItems();
        orderItemService.saveBatch(orderItems);
    }

    private OrderCreateTo orderCreateTo(){
        String orderSn = IdWorker.getTimeId();
        OrderCreateTo orderCreateTo = new OrderCreateTo();

        OrderEntity orderEntity = buildOrder(orderSn);

        List<OrderItemEntity> itemEntities = buildOrderItems(orderSn);

        computePrice(orderEntity,itemEntities);
        orderCreateTo.setOrder(orderEntity);
        orderCreateTo.setOrderItems(itemEntities);
        return orderCreateTo;
    }

    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> itemEntities) {
        BigDecimal total = new BigDecimal("0.0");
        for (OrderItemEntity itemEntity : itemEntities) {
            BigDecimal realAmount = itemEntity.getRealAmount();
            total = total.add(realAmount);
        }
        orderEntity.setTotalAmount(total);
        orderEntity.setPayAmount(total);

        orderEntity.setDeleteStatus(0);

    }

    private OrderEntity buildOrder(String orderSn) {

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderSn(orderSn);
        String s = redisTemplate.opsForValue().get(AuthServerConstant.LOGIN_USER);
        MemberResponseVo memberResponseVo = JSON.parseObject(s, MemberResponseVo.class);
        R info = memberFeignService.info(memberResponseVo.getId());
        Object memberReceiveAddress = info.get("memberReceiveAddress");
        MemberAddressVO member = JSON.parseObject(JSON.toJSONString(memberReceiveAddress),MemberAddressVO.class);
        orderEntity.setMemberId(member.getMemberId());
        orderEntity.setReceiverCity(member.getCity());
        orderEntity.setIntegration(memberResponseVo.getIntegration());
        orderEntity.setReceiverPhone(member.getPhone());
        orderEntity.setReceiverProvince(member.getProvince());
        orderEntity.setReceiverName(member.getName());
        orderEntity.setReceiverRegion(member.getRegion());

        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setAutoConfirmDay(7);

        return orderEntity;
    }

    private List<OrderItemEntity> buildOrderItems(String orderSn) {
        List<OrderItemVO> items = cartFeignService.getcurrentUserCartItems();
        if (items!=null&&items.size()>0){
            List<OrderItemEntity> collect = items.stream().map(item -> {
                OrderItemEntity orderItemEntity = buildOrderItem(item);
                orderItemEntity.setOrderSn(orderSn);

                return orderItemEntity;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;

    }

    private OrderItemEntity buildOrderItem(OrderItemVO item) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        Long skuId = item.getSkuId();
        R r = productFeignService.getSpuInfoBySkuId(skuId);
        SpuInfoVO data = r.getData(new TypeReference<SpuInfoVO>() {
        });
        orderItemEntity.setSpuId(data.getId());
        orderItemEntity.setSpuBrand(data.getBrandId().toString());
        orderItemEntity.setSpuName(data.getSpuName());
        orderItemEntity.setCategoryId(data.getCatalogId());

        orderItemEntity.setSkuId(item.getSkuId());
        orderItemEntity.setSkuName(item.getTitle());
        orderItemEntity.setSkuPrice(item.getPrice());
        String skuAttr = StringUtils.collectionToDelimitedString(item.getSkuAttrValues(), ";");
        orderItemEntity.setSkuAttrsVals(skuAttr);
        orderItemEntity.setSkuQuantity(item.getCount());
        orderItemEntity.setPromotionAmount(new BigDecimal("0.0"));
        orderItemEntity.setCouponAmount(new BigDecimal("0"));
        orderItemEntity.setIntegrationAmount(new BigDecimal("0"));
        //当前订单项的实际金额
        orderItemEntity.setRealAmount(orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity().toString())));
        return orderItemEntity;
    }
}