package com.mhy.blog.pojo;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
//    用户编号(账号)
    private String userCode;
//    用户密码
    private String userPassword;
//    用户姓名
    private String userName;
//    用户邮箱
    private String userEmail;
//    用户头像地址
    private String img;
//    创建时间
    private Date createTime;
//    更新时间
    private Date updateTime;

//    一个用户也有多篇博客
    private List<Blog> blogs;

}
