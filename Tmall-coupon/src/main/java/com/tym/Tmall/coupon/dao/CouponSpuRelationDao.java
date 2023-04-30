package com.tym.Tmall.coupon.dao;

import com.tym.Tmall.coupon.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 17:11:05
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
