package com.mhy.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Blog {
//    博客id
    private String code;
//    博客标题
    private String title;
//    博客内容
    private String content;
//    博客首图地址
    private String img;
//    阅读量
    private int readNum;
//    推荐量
    private int recommendNum;
//    创建时间
    private Date createTime;
//    最新更新时间
    private Date updateTime;
//    是否开启评论 1表示开启 0表示关闭
    private int commentIs;
//    是否发布 1发布 0表示未发布（也就是草稿)
    private int release;

//    文章的类别  一博客只有一个类别
    private Classify classify;
//    文章的标签  一个博客有多个标签
    private List<Label> labels;
//    用户信息  一博客只有一个作者
    private User user;

}
