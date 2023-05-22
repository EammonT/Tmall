package com.tym.Tmall.auth.controller;

import com.tym.Tmall.auth.feign.MemberFeignService;
import com.tym.Tmall.auth.vo.UserLoginVO;
import com.tym.Tmall.auth.vo.UserRegVO;
import com.tym.Tmall.common.constant.AuthServerConstant;
import com.tym.Tmall.common.utils.R;
import com.tym.Tmall.common.vo.MemberResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MemberFeignService memberFeignService;
    /**
     * 模拟发送手机验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone")String phone){
        String code = UUID.randomUUID().toString().substring(0, 5);
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        if (stringStringValueOperations.get(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone)!=null){
            System.out.println("验证码已发送，请稍后重试！");
            return R.error("验证码已发送，请稍后重试！");
        }
        System.out.println(code);
        stringStringValueOperations.set(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone,code,60, TimeUnit.SECONDS);
        return R.ok();
    }

    @PostMapping("/regist")
    public String regist(UserRegVO userRegVO, RedirectAttributes attributes){
        String code = userRegVO.getCode();
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + userRegVO.getPhone());
        if (StringUtils.hasLength(redisCode)){
            if (redisCode.equals(code)){
                //删除验证码
                stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX+userRegVO);
                //验证码通过，进行注册
                R r = memberFeignService.regist(userRegVO);
                if (r.getCode()==0){
                    return "redirect:http://auth.tmall.com/login.html";
                }else {
                    HashMap<String, String> errors = new HashMap<>();
                    errors.put("msg",r.getMsg());
                    attributes.addFlashAttribute("errors",errors);
                    return "redirect:http://auth.tmall.com/reg.html";
                }
            }else {
                //验证码错误
                HashMap<String, String> errors = new HashMap<>();
                errors.put("code","验证码错误");
                attributes.addFlashAttribute("errors",errors);
                return "redirect:http://auth.tmall.com/reg.html";
            }
        }else {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("code","验证码超时");
            attributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.tmall.com/reg.html";
        }
    }

    @GetMapping("/login.html")
    public String loginPage(HttpSession session){
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute == null){
            return "login";
        }else {
            return "redirect:http://tmall.com";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVO userLoginVO, RedirectAttributes attributes, HttpSession session){
        R login = memberFeignService.login(userLoginVO);
        if (login.getCode()==0){
            MemberResponseVo memberResponseVo = new MemberResponseVo();
            memberResponseVo.setNickname(userLoginVO.getLoginAcct());
            session.setAttribute(AuthServerConstant.LOGIN_USER,memberResponseVo);
            return "redirect:http://tmall.com";
        }else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg",login.getMsg());
            attributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.tmall.com/login.html";
        }

    }
}
