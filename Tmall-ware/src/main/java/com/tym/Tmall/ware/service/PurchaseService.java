package com.tym.Tmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.ware.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:33:37
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

