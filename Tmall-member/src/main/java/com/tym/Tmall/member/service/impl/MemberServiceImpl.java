package com.tym.Tmall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.Query;
import com.tym.Tmall.member.dao.MemberDao;
import com.tym.Tmall.member.dao.MemberLevelDao;
import com.tym.Tmall.member.entity.MemberEntity;
import com.tym.Tmall.member.entity.MemberLevelEntity;
import com.tym.Tmall.member.exception.EmailException;
import com.tym.Tmall.member.exception.PhoneException;
import com.tym.Tmall.member.exception.UsernameException;
import com.tym.Tmall.member.service.MemberService;
import com.tym.Tmall.member.vo.UserLoginVO;
import com.tym.Tmall.member.vo.UserRegVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(UserRegVO userRegVO) {
        MemberEntity memberEntity = new MemberEntity();
        MemberLevelEntity memberLevelEntity = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(memberLevelEntity.getId());
        checkPhoneUnique(userRegVO.getPhone());
        checkUsernameUnique(userRegVO.getUserName());
        memberEntity.setMobile(userRegVO.getPhone());
        memberEntity.setUsername(userRegVO.getUserName());
        memberEntity.setNickname(userRegVO.getUserName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(userRegVO.getPassword());
        memberEntity.setPassword(encode);
        memberEntity.setCreateTime(new Date());

        baseMapper.insert(memberEntity);
    }

    @Override
    public void checkEmailUnique(String email) throws EmailException {
        Integer count = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("email", email));
        if (count>0){
            throw new EmailException();
        }
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneException {
        Integer count = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (count>0){
            throw new PhoneException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameException {
        Integer count = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (count>0){
            throw new UsernameException();
        }
    }

    @Override
    public MemberEntity login(UserLoginVO userLoginVO) {

        String loginAcct = userLoginVO.getLoginAcct();
        String password = userLoginVO.getPassword();
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginAcct).or().eq("mobile", loginAcct).or().eq("email", loginAcct));
        if (memberEntity==null){
            //登录失败
            return null;
        }else {
            String dbPass = memberEntity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(password, dbPass);
            if (matches){
                return memberEntity;
            }else {
                return null;
            }
        }

    }

}