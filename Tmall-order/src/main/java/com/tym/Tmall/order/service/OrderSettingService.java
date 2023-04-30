package com.tym.Tmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:09:39
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

