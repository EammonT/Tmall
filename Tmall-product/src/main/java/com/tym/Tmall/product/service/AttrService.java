package com.tym.Tmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 16:14:29
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

