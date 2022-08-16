package com.mhy.blog.mapper;

import com.mhy.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {

    /**
     * 增加一个评论
     * @param comment 评论的对象
     */
    void addComment(Comment comment);

    /**
     * 根据博客id查询评论
     * @param blogCode 博客的id
     * @return  评论的集合
     */
    List<Comment> queryCommentsByBlogCode(@Param("blogCode") String blogCode);
}
