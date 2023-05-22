package com.tym.Tmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.member.entity.MemberEntity;
import com.tym.Tmall.member.exception.EmailException;
import com.tym.Tmall.member.exception.PhoneException;
import com.tym.Tmall.member.exception.UsernameException;
import com.tym.Tmall.member.vo.UserLoginVO;
import com.tym.Tmall.member.vo.UserRegVO;

import java.util.Map;

/**
 * 会员
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:14:15
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(UserRegVO userRegVO);

    void checkEmailUnique(String email) throws EmailException;

    void checkPhoneUnique(String phone) throws PhoneException;

    void checkUsernameUnique(String username) throws UsernameException;

    MemberEntity login(UserLoginVO userLoginVO);
}

