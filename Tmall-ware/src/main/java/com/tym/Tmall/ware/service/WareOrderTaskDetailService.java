package com.tym.Tmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:33:37
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

