package com.tym.Tmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.order.entity.PaymentInfoEntity;

import java.util.Map;

/**
 * 支付信息表
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:09:39
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

