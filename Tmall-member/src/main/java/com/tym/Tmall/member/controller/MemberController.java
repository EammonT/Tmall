package com.tym.Tmall.member.controller;

import com.alibaba.fastjson.JSON;
import com.tym.Tmall.common.constant.AuthServerConstant;
import com.tym.Tmall.common.exception.BizCodeEnume;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.R;
import com.tym.Tmall.member.entity.MemberEntity;
import com.tym.Tmall.member.exception.EmailException;
import com.tym.Tmall.member.exception.PhoneException;
import com.tym.Tmall.member.exception.UsernameException;
import com.tym.Tmall.member.feign.CouponFeignService;
import com.tym.Tmall.member.service.MemberService;
import com.tym.Tmall.member.vo.UserLoginVO;
import com.tym.Tmall.member.vo.UserRegVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 会员
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:14:15
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;
    @RequestMapping("/coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R membercoupons = couponFeignService.membercoupons();
        return R.ok().put("member",memberEntity).put("coupons",membercoupons.get("coupons"));
    }

    @PostMapping("/login")
    public R login(@RequestBody UserLoginVO userLoginVO){
        MemberEntity memberEntity = memberService.login(userLoginVO);
        if (memberEntity!=null){
            String s = JSON.toJSONString(memberEntity);
            redisTemplate.opsForValue().set(AuthServerConstant.LOGIN_USER,s);
            return R.ok();
        }
        return R.error(BizCodeEnume.LOGINACCT_PASS_INVALID_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASS_INVALID_EXCEPTION.getMsg());
    }

    /**
     * 注册
     * @param userRegVO
     * @return
     */
    @PostMapping("/regist")
    public R regist(@RequestBody UserRegVO userRegVO){
        try {
            memberService.regist(userRegVO);
        } catch (PhoneException e) {
            return R.error(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
        }catch (UsernameException e){
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(), BizCodeEnume.USER_EXIST_EXCEPTION.getMsg());
        }catch (EmailException e){
            return R.error(BizCodeEnume.EMAIL_EXIST_EXCEPTION.getCode(), BizCodeEnume.EMAIL_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
