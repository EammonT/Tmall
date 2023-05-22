package com.tym.Tmall.auth.feign;

import com.tym.Tmall.auth.vo.UserLoginVO;
import com.tym.Tmall.auth.vo.UserRegVO;
import com.tym.Tmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("tmall-member")
public interface MemberFeignService {
    @PostMapping("/member/member/regist")
    public R regist(@RequestBody UserRegVO userRegVO);

    @PostMapping("/member/member/login")
    public R login(@RequestBody UserLoginVO userRegVO);
}
