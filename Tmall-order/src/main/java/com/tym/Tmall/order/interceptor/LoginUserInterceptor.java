package com.tym.Tmall.order.interceptor;

import com.tym.Tmall.common.constant.AuthServerConstant;
import com.tym.Tmall.common.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MemberResponseVo attribute = (MemberResponseVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute!=null){

            return true;
        }
        request.getSession().setAttribute("msg","请登录！");
        response.sendRedirect("http://auth.tmall.com/login.html");
        return false;
    }
}
