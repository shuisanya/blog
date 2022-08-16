package com.mhy.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Classify {
//    类别编号
    private String classifyCode;
//    类别名称
    private String classifyName;
//    创建时间
    private Date classifyCreateTime;
//    更新时间
    private Date classifyUpdateTime;

//    一个类别有多片博客
    List<Blog> blogs;
}
