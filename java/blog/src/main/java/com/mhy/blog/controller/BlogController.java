package com.mhy.blog.controller;

import com.mhy.blog.pojo.Blog;
import com.mhy.blog.pojo.Classify;
import com.mhy.blog.pojo.Label;
import com.mhy.blog.pojo.Page;
import com.mhy.blog.service.api.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {
    private BlogService blogService;
    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/manage/blog/addBlog")
    public String addBlog(@RequestBody Map<String,Object> blog){

        return blogService.addBlog(blog);
    }

    @PostMapping("/manage/blog/queryBlogs")//只有post请求次啊可以接受为map
    public Page<Blog> queryBlogs(@RequestBody Map<String,Object> page){

        return blogService.queryBlogs(page);
    }

    @GetMapping("/manage/blog/queryBlogByCode/{code}")
    public Blog queryBlogByCode(@PathVariable String code){

        return blogService.queryBlogByCode(code);
    }

    @PostMapping("/manage/blog/updateBlog")
    public String updateBlog(@RequestBody Map<String,Object> map){

        return blogService.updateBlog(map);
    }

    @GetMapping("/manage/blog/deleteBlog/{code}")
    public String deleteBlog(@PathVariable String code){

        return blogService.deleteBlog(code);
    }

    @PostMapping("/manage/blog/queryClassifyBlogs")
    public Page<Classify> queryClassifyBlogs(@RequestBody Map<String,Object> map){

        return blogService.queryClassifyBlogs(map);
    }

    @PostMapping("/manage/blog/queryLabelBlogs")
    public Page<Label> queryLabelBlogs(@RequestBody Map<String,Object> map){

        return blogService.queryLabelBlogs(map);
    }

    @PostMapping("/manage/blog/queryDateBlogs")
    public Map<String ,Object> queryDateBlogs(@RequestBody Map<String,Object> map){

        return blogService.queryDateBlogs(map);
    }

//    前台功能

    @PostMapping("/index/blog/queryBlogs")
    public Page<Blog> queryIndexBlogs(@RequestBody Map<String,Object> map){

        return blogService.queryBlogs(map);
    }

    @GetMapping("/index/blog/queryBlogByCode/{code}")
    public Blog queryIndexBlogByCode(@PathVariable String code){
        blogService.updateBlogReadNums(code);
        return blogService.queryBlogByCode(code);
    }

    @GetMapping("/index/blog/queryPreAndNextBlog/{date}")
    public Map<String,Object> queryPreAndNextBlog(@PathVariable String date){

        return blogService.queryPreAndNextBlog(date);
    }

    @GetMapping("/index/blog/recommendBlog/{code}")
    public String recommendBlog(@PathVariable String code){
        return blogService.recommendBlog(code);
    }

    @PostMapping("/index/blog/queryDateBlogs")
    public Map<String ,Object> queryIndexDateBlogs(@RequestBody Map<String,Object> map){
        map.put("release",1);
        return blogService.queryDateBlogs(map);
    }

    @GetMapping("/index/blog/queryAllLBlogsCount")
    public int queryAllLBlogsCount(){
        return blogService.queryAllLBlogsCount();
    }

    @PostMapping("/index/blog/queryReadBlogRank")
    public List<Blog> queryReadBlogRank(@RequestBody Map<String,Object> map){
        System.out.println(map);
        return blogService.queryReadBlogRank(map);
    }

}
