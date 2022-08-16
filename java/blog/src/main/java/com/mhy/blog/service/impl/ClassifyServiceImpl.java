package com.mhy.blog.service.impl;

import com.mhy.blog.mapper.BlogMapper;
import com.mhy.blog.mapper.ClassifyMapper;
import com.mhy.blog.pojo.Blog;
import com.mhy.blog.pojo.Classify;
import com.mhy.blog.pojo.Page;
import com.mhy.blog.service.api.ClassifyService;
import com.mhy.blog.utils.ConstUtils;
import com.mhy.blog.utils.ParseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClassifyServiceImpl implements ClassifyService {
    private ClassifyMapper classifyMapper;
    private BlogMapper blogMapper;

    @Autowired
    public void setClassifyMapper(ClassifyMapper classifyMapper) {
        this.classifyMapper = classifyMapper;
    }
    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    @Transactional
    public String addClassify(Classify classify) {
        classify.setClassifyCode(UUID.randomUUID().toString());
        classify.setClassifyCreateTime(new Date());
        classify.setClassifyUpdateTime(new Date());
        classifyMapper.addClassify(classify);
        return ConstUtils.ADD_CLASSIFY_SUCCESS;
    }

    @Override
    public Page<Classify> queryClassifies(Map<String, Object> map) {
        //创建一分页对象
        Page<Classify> classifyPage = new Page<>();
        //获取当前页数和当前页
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //设置当前页数
        classifyPage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //设置每页数量
            classifyPage.setPageSize(pageSize);
        }

        //查询的数量
        Integer pageTotalCount = classifyMapper.queryClassifyTotalCount(map);
        //设置总数
        classifyPage.setPageTotalCount(pageTotalCount);

        //求出总的页数
        int pageTotal = classifyPage.getPageTotalCount() / classifyPage.getPageSize();
        //设置总的页数
        classifyPage.setPageTotal(pageTotal);

        //获取开始位置
        int begin = (classifyPage.getCurrentPage() - 1) * classifyPage.getPageSize();
        map.put("begin",begin);
        map.put("pageSize",classifyPage.getPageSize());

        //查询order
        List<Classify> classifies = classifyMapper.queryClassifies(map);

        for(Classify classify : classifies){
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("classifyCode",classify.getClassifyCode());
            List<Blog> blogs = blogMapper.queryBlogsByInfo(objectMap);
            classify.setBlogs(blogs);
        }

        //设置items
        classifyPage.setItems(classifies);

        return classifyPage;
    }

    @Override
    @Transactional
    public String updateClassifyByClassifyCode(Map<String, Object> map) {
        Classify classify = new Classify();
        classify.setClassifyCode((String) map.get("classifyCode"));
        classify.setClassifyName((String) map.get("classifyName"));
        classify.setClassifyUpdateTime(new Date());
        classifyMapper.updateClassify(classify);
        return ConstUtils.UPDATE_CLASSIFY_SUCCESS;
    }

    @Override
    @Transactional
    public String deleteClassifyByClassifyCode(String classifyCode) {
        blogMapper.updateBlogClassifyByClassifyCode(classifyCode);
        classifyMapper.deleteClassify(classifyCode);
        return ConstUtils.DELETE_CLASSIFY_SUCCESS;
    }

    @Override
    public List<Classify> queryAllClassifies() {
        List<Classify> classifies = classifyMapper.queryAllClassifies();
        for (Classify classify : classifies) {
            Map<String,Object> map = new HashMap<>();
            map.put("classifyCode",classify.getClassifyCode());
            map.put("release",1);
            List<Blog> blogs = blogMapper.queryBlogsByInfo(map);
            classify.setBlogs(blogs);
        }
        return classifies;
    }


}
