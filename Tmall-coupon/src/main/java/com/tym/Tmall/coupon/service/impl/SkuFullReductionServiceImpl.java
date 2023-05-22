package com.tym.Tmall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tym.Tmall.common.to.MemberPrice;
import com.tym.Tmall.common.to.SkuReductionTO;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.Query;
import com.tym.Tmall.coupon.dao.SkuFullReductionDao;
import com.tym.Tmall.coupon.entity.MemberPriceEntity;
import com.tym.Tmall.coupon.entity.SkuFullReductionEntity;
import com.tym.Tmall.coupon.entity.SkuLadderEntity;
import com.tym.Tmall.coupon.service.MemberPriceService;
import com.tym.Tmall.coupon.service.SkuFullReductionService;
import com.tym.Tmall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {


    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTO skuReductionTO) {
        //保存满减
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionTO.getSkuId());
        skuLadderEntity.setFullCount(skuReductionTO.getFullCount());
        skuLadderEntity.setDiscount(skuReductionTO.getDiscount());
        skuLadderEntity.setAddOther(skuReductionTO.getCountStatus());
        if (skuReductionTO.getFullCount() > 0){
            skuLadderService.save(skuLadderEntity);
        }
        //保存打折
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTO,skuFullReductionEntity);
        if (skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal(0)) == 1){
            this.save(skuFullReductionEntity);
        }
        //保存会员价
        List<MemberPrice> memberPrices = skuReductionTO.getMemberPrices();
        if (memberPrices!=null){
            List<MemberPriceEntity> memberPriceEntities = memberPrices.stream().map(item -> {
                MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                memberPriceEntity.setSkuId(skuReductionTO.getSkuId());
                memberPriceEntity.setMemberLevelId(item.getId());
                memberPriceEntity.setMemberLevelName(item.getName());
                memberPriceEntity.setMemberPrice(item.getPrice());
                memberPriceEntity.setAddOther(1);
                return memberPriceEntity;
            }).filter(item->{
                return item.getMemberPrice().compareTo(new BigDecimal(0))== 1;
            }).collect(Collectors.toList());

            memberPriceService.saveBatch(memberPriceEntities);
        }
    }

}