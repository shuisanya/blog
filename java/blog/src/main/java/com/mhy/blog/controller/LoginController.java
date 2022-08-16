package com.mhy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.mhy.blog.pojo.User;
import com.mhy.blog.service.api.LoginService;
import com.mhy.blog.utils.ConstUtils;
import com.mhy.blog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 登入功能
     * @param user 前端的用户登入信息
     * @return 返回登录情况
     */
    @PostMapping("/login/{user}")
    public Map<String,Object> login(@PathVariable String user){
        Map<String,Object> userMap = JSONObject.parseObject(user, (Type)Map.class);
        Map<String,Object> result = new HashMap<>();

//        登入验证
        if(loginService.login(userMap)){
//            登入成功
//            设置token
            User userToken = new User();
            userToken.setUserCode((String) userMap.get("userCode"));
            userToken.setUserCode((String) userMap.get("userPassword"));
            String token = TokenUtils.sign(userToken);
            result.put("token",token);
            result.put("userCode",userMap.get("userCode"));
//            设置返回信息
            result.put("msg",ConstUtils.LOGIN_SUCCESS);
            result.put("is_login",true);
        }else {
//            登入失败
            result.put("msg",ConstUtils.LOGIN_FAILURE);
            result.put("is_login",false);
        }

        return result;

    }


}
