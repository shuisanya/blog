package com.mhy.blog;

import com.mhy.blog.mapper.BlogMapper;
import com.mhy.blog.mapper.UserMapper;
import com.mhy.blog.pojo.Classify;
import com.mhy.blog.pojo.Page;
import com.mhy.blog.pojo.User;
import com.mhy.blog.service.api.ClassifyService;
import com.mhy.blog.service.api.LoginService;
import com.mhy.blog.utils.Md5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    LoginService loginService;
    @Autowired
    ClassifyService classifyService;
    @Autowired
    BlogMapper blogMapper;

    @Test
    void contextLoads() {
//        System.out.println(blogMapper.queryMinData());

//        System.out.println(blogMapper.queryDateBlogs(2021));
        System.out.println(blogMapper.queryBlogPre("2022-08-02 13:06:19"));
        System.out.println(blogMapper.queryBlogNext("2022-08-02 13:06:19"));
    }

    @Test
    void mds(){
        System.out.println(Md5Utils.md5(Md5Utils.md5("123456")));
        System.out.println(Md5Utils.md5(Md5Utils.md5("123456")));
        System.out.println(Md5Utils.md5("123456"));

        Map user = new HashMap<String,String>();

        user.put("userPassword","e10adc3949ba59abbe56e057f20f883e");
        user.put("userCode","shuisanya");

        System.out.println(loginService.login(user));
    }

    @Test
    void addClassify(){
        Classify classify = new Classify();
        classify.setClassifyName("数学");
        classify.setClassifyCreateTime(new Date());
        classify.setClassifyUpdateTime(new Date());
        String s = classifyService.addClassify(classify);

        System.out.println(s);
    }

    @Test
    void page(){
        Map<String,Object> map = new HashMap<>();
        map.put("currentPage",1);
        map.put("pageSize",5);
        Page<Classify> classifyPage = classifyService.queryClassifies(map);
        System.out.println(classifyPage);
    }

}
