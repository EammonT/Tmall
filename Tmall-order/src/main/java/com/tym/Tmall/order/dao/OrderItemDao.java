package com.tym.Tmall.order.dao;

import com.tym.Tmall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:09:39
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
