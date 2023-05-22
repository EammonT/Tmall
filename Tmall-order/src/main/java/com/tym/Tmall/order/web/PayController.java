package com.tym.Tmall.order.web;

import com.alibaba.fastjson.JSON;
import com.tym.Tmall.common.constant.AuthServerConstant;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.R;
import com.tym.Tmall.common.vo.MemberResponseVo;
import com.tym.Tmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Controller
public class PayController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    OrderService orderService;

    @PostMapping("/pay")
    public String pay(String password, Model model){
        String s = redisTemplate.opsForValue().get(AuthServerConstant.LOGIN_USER);
        MemberResponseVo memberResponseVo = JSON.parseObject(s, MemberResponseVo.class);
        System.out.println(memberResponseVo.getPassword());
        System.out.println("password========"+password);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (password!=null&&passwordEncoder.matches(password, memberResponseVo.getPassword())){
            HashMap<String, Object> page = new HashMap<>();
            page.put("page","1");
            PageUtils pageUtils = orderService.queryPageWithItem(page);
            String orderString = JSON.toJSONString(pageUtils);
            R r = new R();
            r.put("page",pageUtils);
            model.addAttribute("orders",r);
            System.out.println(JSON.toJSONString(r));
            return "orderList";
        }
        return "redirect:http://order.tmall.com/toTrade";
    }
}
