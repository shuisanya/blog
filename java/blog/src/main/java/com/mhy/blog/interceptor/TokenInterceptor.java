package com.mhy.blog.interceptor;


import com.mhy.blog.utils.TokenUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){

//            System.out.println("跨域的第一次 OPTIONS 放行");
            return true;
        }

//        System.out.println("拦截了 manage");
//        从请求头中获取token
        String token = request.getHeader("token");
//        System.out.println(token);

//        判断token是否正确
        if(token == null || token.equals("token")){
//            System.out.println("未登入");
            return false;
        }
//        解析token
        if(!TokenUtils.verify(token)){
//            System.out.println("解析失败");
            return false;
        }

        return true;
    }


}
