package com.tym.Tmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.coupon.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 17:11:05
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

