package com.tym.Tmall.member.dao;

import com.tym.Tmall.member.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:14:15
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
