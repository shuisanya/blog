package com.mhy.blog.service.api;

import com.mhy.blog.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    String addComment(Map<String,Object> map);

    List<Comment> queryCommentsByBlogCode(String blogCode);
}
