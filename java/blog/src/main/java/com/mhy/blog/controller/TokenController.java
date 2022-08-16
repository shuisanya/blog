package com.mhy.blog.controller;


import com.mhy.blog.utils.TokenUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    /**
     * 验证token存在 是否合规
     * @param token 前端来的token
     * @return 返回给前端是否合规
     */
    @GetMapping("/token/{token}")
    public boolean token(@PathVariable String token){
        return TokenUtils.verify(token);
    }
}
