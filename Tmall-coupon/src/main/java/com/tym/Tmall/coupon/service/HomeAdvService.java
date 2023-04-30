package com.tym.Tmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.coupon.entity.HomeAdvEntity;

import java.util.Map;

/**
 * 首页轮播广告
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 17:11:05
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

