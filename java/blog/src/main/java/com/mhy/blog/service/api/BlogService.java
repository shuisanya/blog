package com.mhy.blog.service.api;

import com.mhy.blog.pojo.Blog;
import com.mhy.blog.pojo.Classify;
import com.mhy.blog.pojo.Label;
import com.mhy.blog.pojo.Page;

import java.util.List;
import java.util.Map;

public interface BlogService {

    String addBlog(Map<String,Object> map);

    Page<Blog> queryBlogs(Map<String, Object> page);

    Blog queryBlogByCode(String code);

    String updateBlog(Map<String, Object> map);

    String deleteBlog(String code);

    Page<Classify> queryClassifyBlogs(Map<String, Object> map);

    Page<Label> queryLabelBlogs(Map<String, Object> map);

    Map<String, Object> queryDateBlogs(Map<String, Object> map);

    void updateBlogReadNums(String code);

    Map<String, Object> queryPreAndNextBlog(String date);

    String recommendBlog(String code);

    int queryAllLBlogsCount();

    List<Blog> queryReadBlogRank(Map<String,Object> map);
}
