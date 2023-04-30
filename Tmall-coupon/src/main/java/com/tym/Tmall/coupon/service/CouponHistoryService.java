package com.tym.Tmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.coupon.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券领取历史记录
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 17:11:05
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

