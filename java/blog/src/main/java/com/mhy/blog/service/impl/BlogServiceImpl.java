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
        //?????????????????????
        Page<Blog> blogPage = new Page<>();
        //??????????????????????????????
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //??????????????????
        blogPage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //??????????????????
            blogPage.setPageSize(pageSize);
        }

        //???????????????
        Integer pageTotalCount = blogMapper.queryBlogTotalCount(map);
        //????????????
        blogPage.setPageTotalCount(pageTotalCount);

        //??????????????????
        int pageTotal = blogPage.getPageTotalCount() / blogPage.getPageSize();
        //??????????????????
        blogPage.setPageTotal(pageTotal);

        //??????????????????
        int begin = (blogPage.getCurrentPage() - 1) * blogPage.getPageSize();
        map.put("begin",begin);
        map.put("pageSize",blogPage.getPageSize());

        //??????
        List<Blog> blogs = blogMapper.queryBlogs(map);

        for (Blog blog : blogs) {
            blog.setContent(blog.getContent().trim().
                    replaceAll("#","")
                    .replaceAll("\\n","")
                    .replaceAll("`","")
            );
        }


        //??????items
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
        //?????????????????????
        Page<Classify> classifyPage = new Page<>();
        //??????????????????????????????
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //??????????????????
        classifyPage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //??????????????????
            classifyPage.setPageSize(pageSize);
        }

        //???????????????
        Integer pageTotalCount = classifyMapper.queryClassifyTotalCount(map);
        //????????????
        classifyPage.setPageTotalCount(pageTotalCount);

        //??????????????????
        int pageTotal = classifyPage.getPageTotalCount() / classifyPage.getPageSize();
        //??????????????????
        classifyPage.setPageTotal(pageTotal);

        //??????????????????
        int begin = (classifyPage.getCurrentPage() - 1) * classifyPage.getPageSize();
        map.put("begin",begin);
        map.put("pageSize",classifyPage.getPageSize());

        //??????
        List<Classify> classifies = classifyMapper.queryClassifies(map);

        for (Classify classify : classifies) {
            map.put("classifyCode",classify.getClassifyCode());
            List<Blog> blogs = blogMapper.queryBlogsByInfo(map);
            classify.setBlogs(blogs);
        }

        //??????items
        classifyPage.setItems(classifies);

        return classifyPage;
    }

    @Override
    public Page<Label> queryLabelBlogs(Map<String, Object> map) {
        //?????????????????????
        Page<Label> labelPage = new Page<>();
        //??????????????????????????????
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //??????????????????
        labelPage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //??????????????????
            labelPage.setPageSize(pageSize);
        }

        //???????????????
        Integer pageTotalCount = labelMapper.queryLabelTotalCount(map);
        //????????????
        labelPage.setPageTotalCount(pageTotalCount);

        //??????????????????
        int pageTotal = labelPage.getPageTotalCount() / labelPage.getPageSize();
        //??????????????????
        labelPage.setPageTotal(pageTotal);

        //??????????????????
        int begin = (labelPage.getCurrentPage() - 1) * labelPage.getPageSize();
        map.put("begin",begin);
        map.put("pageSize",labelPage.getPageSize());

        //??????
        List<Label> labels = labelMapper.queryLabels(map);

        for (Label label : labels) {
            map.put("labelCode",label.getLabelCode());
            List<Blog> blogs = blogMapper.queryBlogsByInfo(map);
            label.setBlogs(blogs);
        }

        //??????items
        labelPage.setItems(labels);
        return labelPage;
    }

    @Override
    public Map<String, Object> queryDateBlogs(Map<String, Object> map) {
        //?????????????????????
        Page<Blog> datePage = new Page<>();
        //??????????????????????????????
        int currentPage = ParseUtils.parseInt(map.get("currentPage"));
        //??????????????????
        datePage.setCurrentPage(currentPage);
        if(map.get("pageSize") != null){
            int pageSize = ParseUtils.parseInt(map.get("pageSize"));
            //??????????????????
            datePage.setPageSize(pageSize);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int nowYear = ParseUtils.parseInt(simpleDateFormat.format(new Date()));
//        ??????????????????
        int minTear = ParseUtils.parseInt(simpleDateFormat.format(blogMapper.queryMinData()));

        //????????????????????????
        Integer pageTotalCount = nowYear - minTear + 1;
        //????????????
        datePage.setPageTotalCount(pageTotalCount);

        //??????????????????
        int pageTotal = datePage.getPageTotalCount() / datePage.getPageSize();
        //??????????????????
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
