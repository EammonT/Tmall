package com.tym.Tmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:14:15
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

