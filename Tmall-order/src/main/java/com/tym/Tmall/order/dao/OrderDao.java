package com.tym.Tmall.order.dao;

import com.tym.Tmall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:09:39
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
