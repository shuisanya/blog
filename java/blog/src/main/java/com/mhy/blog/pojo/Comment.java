package com.mhy.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
//    评论编号
    private String commentCode;
//    评论名称
    private String commentName;
//    评论内容
    private String commentContent;
//    评论时间
    private Date commentTime;

//    评论的文章
    private Blog blog;
}
