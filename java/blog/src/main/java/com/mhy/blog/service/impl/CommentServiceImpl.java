package com.mhy.blog.service.impl;

import com.mhy.blog.mapper.CommentMapper;
import com.mhy.blog.pojo.Blog;
import com.mhy.blog.pojo.Comment;
import com.mhy.blog.service.api.CommentService;
import com.mhy.blog.utils.ConstUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentMapper commentMapper;
    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public String addComment(Map<String, Object> map) {
        Comment comment = new Comment();
        comment.setCommentCode(UUID.randomUUID().toString());
        Blog blog = new Blog();
        blog.setCode((String) map.get("blogCode"));
        comment.setBlog(blog);
        comment.setCommentContent((String) map.get("commentContent"));
        comment.setCommentTime(new Date());
        comment.setCommentName((String) map.get("commentName"));
        commentMapper.addComment(comment);
        return ConstUtils.ADD_COMMENT_SUCCESS;
    }

    @Override
    public List<Comment> queryCommentsByBlogCode(String blogCode) {
        return commentMapper.queryCommentsByBlogCode(blogCode);
    }
}
