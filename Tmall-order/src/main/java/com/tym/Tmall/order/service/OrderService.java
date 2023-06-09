package com.tym.Tmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.order.entity.OrderEntity;
import com.tym.Tmall.order.vo.OrderConfirmVO;
import com.tym.Tmall.order.vo.OrderSubmitVO;
import com.tym.Tmall.order.vo.SubmitOrderResponseVO;

import java.util.Map;

/**
 * 订单
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:09:39
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVO confirmOrder();

    SubmitOrderResponseVO submitOrder(OrderSubmitVO submitVO);

    PageUtils queryPageWithItem(Map<String, Object> params);
}

