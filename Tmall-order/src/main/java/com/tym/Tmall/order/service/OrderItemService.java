package com.tym.Tmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 订单项信息
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:09:39
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

