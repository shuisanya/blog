package com.mhy.blog.service.impl;

import com.mhy.blog.mapper.UserMapper;
import com.mhy.blog.pojo.User;
import com.mhy.blog.service.api.LoginService;
import com.mhy.blog.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(Map<String,Object> loginUser) {
//        根据用户账号查询一个用户
        User user = userMapper.queryUserByCode((String) loginUser.get("userCode"));
//        md加密比对
        String userPassword = Md5Utils.md5((String) loginUser.get("userPassword"));
//        验证密码是否正确
        return user.getUserPassword().equals(userPassword);
    }


}
