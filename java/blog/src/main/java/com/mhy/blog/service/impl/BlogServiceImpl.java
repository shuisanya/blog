package com.mhy.blog.service.impl;

import com.mhy.blog.mapper.BlogMapper;
import com.mhy.blog.mapper.ClassifyMapper;
import com.mhy.blog.mapper.LabelMapper;
import com.mhy.blog.pojo.*;
import com.mhy.blog.service.api.BlogService;
import com.mhy.blog.utils.ConstUtils;
import com.mhy.blog.utils.ParseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    private BlogMapper blogMapper;
    private LabelMapper labelMapper;
    private ClassifyMapper classifyMapper;
    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }
    @Autowired
    public void setLabelMapper(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }
    @Autowired
    public void setClassifyMapper(ClassifyMapper classifyMapper) {
        this.classifyMapper = classifyMapper;
    }

    @Override
    @Transactional
    public String addBlog(Map<String, Object> map) {
        Blog blog = new Blog();
        blog.setCode(Long.toString(System.currentTimeMillis()));
        blog.setContent((String) map.get("content"));
        blog.setTitle((String) map.get("title"));
        blog.setImg((String) map.get("img"));
        blog.setCommentIs(ParseUtils.parseInt(map.get("commentIs")));
        blog.setRelease(ParseUtils.parseInt(map.get("release")));
        blog.setReadNum(1);
        blog.setRecommendNum(1);

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());

        Classify classify = new Classify();
        if(map.get("classify") != null && map.get("classify") != ""){
            classify.setClassifyCode((String) map.get("classify"));
        }else{
            classify.setClassifyCode(null);
        }
        blog.setClassify(classify);

        User user = new User();
        user.setUserCode((String) map.get("userCode"));
        blog.setUser(user);

        blogMapper.addBlog(blog);
        if(map.get("labels") != null){
            List<String> labelCodes = (List<String>) map.get("labels");
            for (String labelCode : labelCodes) {
                blogMapper.addBlogWithLabel(blog.getCode(),labelCode);
            }
        }

        return blog.getRelease() == 1 ? ConstUtils.ADD_BLOG_SUCCESS : ConstUtils.SAVE_BLOG_SUCCESS;
    }

    @Override
    public Page<Blog> queryBlogs(Map<String, Object> map) {
        //创建一分页对象
        Page<Blog> blogPage = new Page<>();
        //获取当前页数和当前页
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //设置当前页数
        blogPage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //设置每页数量
            blogPage.setPageSize(pageSize);
        }

        //查询的数量
        Integer pageTotalCount = blogMapper.queryBlogTotalCount(map);
        //设置总数
        blogPage.setPageTotalCount(pageTotalCount);

        //求出总的页数
        int pageTotal = blogPage.getPageTotalCount() / blogPage.getPageSize();
        //设置总的页数
        blogPage.setPageTotal(pageTotal);

        //获取开始位置
        int begin = (blogPage.getCurrentPage() - 1) * blogPage.getPageSize();
        map.put("begin",begin);
        map.put("pageSize",blogPage.getPageSize());

        //查询
        List<Blog> blogs = blogMapper.queryBlogs(map);

        for (Blog blog : blogs) {
            blog.setContent(blog.getContent().trim().
                    replaceAll("#","")
                    .replaceAll("\\n","")
                    .replaceAll("`","")
            );
        }


        //设置items
        blogPage.setItems(blogs);

        return blogPage;
    }

    @Override
    public Blog queryBlogByCode(String code) {
        Blog blog = blogMapper.queryBlogByCode(code);
        blog.setLabels(labelMapper.queryLabelsByCode(code));
        return blog;
    }

    @Override
    @Transactional
    public String updateBlog(Map<String, Object> map) {
        System.out.println(map);
        Blog blog = new Blog();
        blog.setCode((String) map.get("code"));
        blog.setContent((String) map.get("content"));
        blog.setTitle((String) map.get("title"));
        blog.setImg((String) map.get("img"));
        blog.setRelease(ParseUtils.parseInt(map.get("release")));

        blog.setUpdateTime(new Date());

        Classify classify = new Classify();
        if(map.get("classify") != null && map.get("classify") != ""){
            classify.setClassifyCode((String) map.get("classify"));
        }else{
            classify.setClassifyCode(null);
        }
        blog.setClassify(classify);

        blogMapper.updateBlog(blog);

        if(map.get("labels") != null){
            List<String> labelCodes = (List<String>) map.get("labels");
            blogMapper.deleteBlogWithLabelByCode(blog.getCode());
            for (String labelCode : labelCodes) {
                System.out.println(labelCode);
                System.out.println(blog.getCode());
                blogMapper.addBlogWithLabel(blog.getCode(),labelCode);
            }
        }

        return ConstUtils.UPLOAD_BLOG_SUCCESS;
    }

    @Override
    @Transactional
    public String deleteBlog(String code) {
        blogMapper.deleteBlogWithLabelByCode(code);
        blogMapper.deleteBlog(code);
        return ConstUtils.DELETE_BLOG_SUCCESS;
    }

    @Override
    public Page<Classify> queryClassifyBlogs(Map<String, Object> map) {
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

        //查询
        List<Classify> classifies = classifyMapper.queryClassifies(map);

        for (Classify classify : classifies) {
            map.put("classifyCode",classify.getClassifyCode());
            List<Blog> blogs = blogMapper.queryBlogsByInfo(map);
            classify.setBlogs(blogs);
        }

        //设置items
        classifyPage.setItems(classifies);

        return classifyPage;
    }

    @Override
    public Page<Label> queryLabelBlogs(Map<String, Object> map) {
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

        for (Label label : labels) {
            map.put("labelCode",label.getLabelCode());
            List<Blog> blogs = blogMapper.queryBlogsByInfo(map);
            label.setBlogs(blogs);
        }

        //设置items
        labelPage.setItems(labels);
        return labelPage;
    }

    @Override
    public Map<String, Object> queryDateBlogs(Map<String, Object> map) {
        //创建一分页对象
        Page<Blog> datePage = new Page<>();
        //获取当前页数和当前页
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //设置当前页数
        datePage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //设置每页数量
            datePage.setPageSize(pageSize);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int nowYear = ParseUtils.parseInt(simpleDateFormat.format(new Date()));
//        获取最低年份
        int minTear = ParseUtils.parseInt(simpleDateFormat.format(blogMapper.queryMinData()));

        //查询的数量年份差
        Integer pageTotalCount = nowYear - minTear + 1;
        //设置总数
        datePage.setPageTotalCount(pageTotalCount);

        //求出总的页数
        int pageTotal = datePage.getPageTotalCount() / datePage.getPageSize();
        //设置总的页数
        datePage.setPageTotal(pageTotal);

        Map<String, Object> dateMap = new HashMap<>();
        dateMap.put("page",datePage);
        int year = nowYear - (datePage.getCurrentPage() - 1)*datePage.getPageSize();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (int i = 0; i < datePage.getPageSize(); i++) {
            Map<String, Object> objectMap = new HashMap<>();
            Map<String, Object> objectMap1 = new HashMap<>();
            if(map.get("release") != null && map.get("release") != ""){
                objectMap1.put("release",map.get("release"));
            }
            objectMap1.put("year",year);
            List<Blog> blogs = blogMapper.queryDateBlogs(objectMap1);
            objectMap.put("date",year--);
            objectMap.put("blogs",blogs);
            mapList.add(objectMap);
        }
        dateMap.put("dateBlogs",mapList);

        return dateMap;
    }

    @Override
    @Transactional
    public void updateBlogReadNums(String code) {
        blogMapper.updateBlogReadNums(code);
    }

    @Override
    public Map<String, Object> queryPreAndNextBlog(String date) {
        Map<String,Object> map = new HashMap<>();
        Blog preBlog = blogMapper.queryBlogPre(date);

        Blog nextBlog = blogMapper.queryBlogNext(date);
        map.put("preBlog",preBlog);
        map.put("nextBlog",nextBlog);

        return map;
    }

    @Override
    @Transactional
    public String recommendBlog(String code) {
        blogMapper.recommendBlog(code);
        return ConstUtils.RECOMMEND_BLOG_SUCCESS;
    }

    @Override
    public int queryAllLBlogsCount() {
        Map<String,Object> map = new HashMap<>();
        map.put("release",1);
        return blogMapper.queryBlogTotalCount(map);
    }

    @Override
    public List<Blog> queryReadBlogRank(Map<String, Object> map) {
        return blogMapper.queryReadBlogRank(map);
    }
}
