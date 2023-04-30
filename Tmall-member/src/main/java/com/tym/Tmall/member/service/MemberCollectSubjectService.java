package com.tym.Tmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.member.entity.MemberCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:14:15
 */
public interface MemberCollectSubjectService extends IService<MemberCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

