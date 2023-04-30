package com.tym.Tmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:14:15
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

