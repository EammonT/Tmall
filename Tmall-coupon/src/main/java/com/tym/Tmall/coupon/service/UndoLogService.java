package com.tym.Tmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.coupon.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 17:11:05
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

