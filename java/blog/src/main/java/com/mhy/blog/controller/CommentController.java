package com.mhy.blog.controller;

import com.mhy.blog.pojo.Comment;
import com.mhy.blog.service.api.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    private CommentService commentService;
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/index/comment/addComment")
    public String addComment(@RequestBody Map<String,Object> map){
        return commentService.addComment(map);
    }

    @GetMapping("/index/comment/queryCommentsByBlogCode/{blogCode}")
    public List<Comment> queryCommentsByBlogCode(@PathVariable String blogCode){
        return commentService.queryCommentsByBlogCode(blogCode);
    }
}
