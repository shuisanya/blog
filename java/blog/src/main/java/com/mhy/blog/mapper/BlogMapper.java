package com.mhy.blog.mapper;

import com.mhy.blog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
@Mapper
public interface BlogMapper {
    /**
     * 根据相关信息查询博客信息 主要是classify和label在使用
     * @param map 相关信息集合
     * @return 博客对象集合
     */
    List<Blog> queryBlogsByInfo(Map<String,Object> map);

    /**
     * 增加一篇博客
     * @param blog 博客对象
     */
    void addBlog(Blog blog);

    /**
     * 增加博客和label标签的关系，多对多
     * @param blogCode 博客的id
     * @param labelCode 标签的id
     */
    void addBlogWithLabel(@Param("blogCode") String blogCode,@Param("labelCode") String labelCode);

    /**
     * 根据相关信息条件查询博客的数量
     * @param map 相关信息集合
     * @return 博客的数量
     */
    Integer queryBlogTotalCount(Map<String, Object> map);

    /**
     * 根据相关信息条件查询博客
     * @param map 相关信息集合
     * @return 博客对象的集合
     */
    List<Blog> queryBlogs(Map<String, Object> map);

    /**
     * 根据博客id查询一篇博客
     * @param code 博客id
     * @return 一个博客对象
     */
    Blog queryBlogByCode(@Param("code") String code);

    /**
     * 更新一个博客
     * @param blog 博客对象
     */
    void updateBlog(Blog blog);

    /**
     * 删除一个博客跟标签的关系
     * @param code 博客id
     */
    void deleteBlogWithLabelByCode(@Param("code") String code);

    /**
     * 删除一篇博客
     * @param code 博客id
     */
    void deleteBlog(@Param("code") String code);

    /**
     * 更新一个博客和类别的关系
     * @param classifyCode 类别编号
     */
    void updateBlogClassifyByClassifyCode(@Param("classifyCode") String classifyCode);

    /**
     * 查询博客的最早更新时间
     * @return Date时间
     */
    Date queryMinData();

    /**
     * 根据时间查询博客信息
     * @param map 时间的map
     * @return 博客对象的集合
     */
    List<Blog> queryDateBlogs(Map<String,Object> map);

    /**
     * 更新一个博客的阅读数
     * @param code 博客id
     */
    void updateBlogReadNums(@Param("code") String code);

    /**
     * 查询一个一个时间的上一篇博客
     * @param date 时间
     * @return 一篇博客
     */
    Blog queryBlogPre(@Param("date") String date);

    /**
     * 查询一个一个时间的下一篇博客
     * @param date 时间
     * @return 一篇博客
     */
    Blog queryBlogNext(@Param("date") String date);

    /**
     * 增加一篇博客的推荐数
     * @param code 博客id
     */
    void recommendBlog(@Param("code") String code);

    /**
     * 根据阅读数排名查询博客数
     * @param map 要求的集合
     * @return 博客集合对象
     */
    List<Blog> queryReadBlogRank(Map<String,Object> map);
}
