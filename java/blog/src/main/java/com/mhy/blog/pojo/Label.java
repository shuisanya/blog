package com.mhy.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Label {
//    标签编号
    private String labelCode;
//    标签名称
    private String labelName;
//    创建时间
    private Date labelCreateTime;
//    更新时间
    private Date labelUpdateTime;

//    一个标签有多个博客
    private List<Blog> blogs;
}
