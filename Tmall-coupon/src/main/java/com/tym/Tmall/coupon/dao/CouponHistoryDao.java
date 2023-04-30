package com.tym.Tmall.coupon.dao;

import com.tym.Tmall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 17:11:05
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
