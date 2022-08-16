package com.mhy.blog.service.impl;

import com.mhy.blog.mapper.BlogMapper;
import com.mhy.blog.mapper.LabelMapper;
import com.mhy.blog.pojo.Blog;
import com.mhy.blog.pojo.Label;
import com.mhy.blog.pojo.Page;
import com.mhy.blog.service.api.LabelService;
import com.mhy.blog.utils.ConstUtils;
import com.mhy.blog.utils.ParseUtils;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LabelServiceImpl implements LabelService {

    private LabelMapper labelMapper;
    private BlogMapper blogMapper;

    @Autowired
    public void setLabelMapper(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }
    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    @Transactional
    public String addLabel(Label label) {
        label.setLabelCode(UUID.randomUUID().toString());
        label.setLabelCreateTime(new Date());
        label.setLabelUpdateTime(new Date());
        System.out.println(label);
        labelMapper.addLabel(label);
        return ConstUtils.ADD_LABEL_SUCCESS;
    }

    @Override
    public Page<Label> queryLabels(Map<String, Object> map) {
        //创建一分页对象
        Page<Label> labelPage = new Page<>();
        //获取当前页数和当前页
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //设置当前页数
        labelPage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //设置每页数量
            labelPage.setPageSize(pageSize);
        }

        //查询的数量
        Integer pageTotalCount = labelMapper.queryLabelTotalCount(map);
        //设置总数
        labelPage.setPageTotalCount(pageTotalCount);

        //求出总的页数
        int pageTotal = labelPage.getPageTotalCount() / labelPage.getPageSize();
        //设置总的页数
        labelPage.setPageTotal(pageTotal);

        //获取开始位置
        int begin = (labelPage.getCurrentPage() - 1) * labelPage.getPageSize();
        map.put("begin",begin);
        map.put("pageSize",labelPage.getPageSize());

        //查询
        List<Label> labels = labelMapper.queryLabels(map);

        for(Label label : labels){
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("labelCode",label.getLabelCode());
            List<Blog> blogs = blogMapper.queryBlogsByInfo(objectMap);
            label.setBlogs(blogs);
        }

        //设置items
        labelPage.setItems(labels);

        return labelPage;
    }

    @Override
    @Transactional
    public String updateLabel(Map<String, Object> map) {
        Label label = new Label();
        label.setLabelName((String) map.get("labelName"));
        label.setLabelCode((String) map.get("labelCode"));
        label.setLabelUpdateTime(new Date());
        labelMapper.updateLabel(label);
        return ConstUtils.UPDATE_LABEL_SUCCESS;
    }

    @Override
    @Transactional
    public String deleteLabel(String labelCode) {
        labelMapper.deleteLabelWithBlog(labelCode);
        labelMapper.deleteLabel(labelCode);
        return ConstUtils.DELETE_LABEL_SUCCESS;
    }

    @Override
    public List<Label> queryAllLabels() {
        List<Label> labels = labelMapper.queryAllLabels();
        for (Label label : labels) {
            Map<String,Object> map = new HashMap<>();
            map.put("labelCode",label.getLabelCode());
            map.put("release",1);
            List<Blog> blogs = blogMapper.queryBlogsByInfo(map);
            label.setBlogs(blogs);
        }
        return labels;
    }

}
