package com.mhy.blog.service.api;

import com.mhy.blog.pojo.Classify;
import com.mhy.blog.pojo.Page;

import java.util.List;
import java.util.Map;

public interface ClassifyService {
    String addClassify(Classify classify);

    Page<Classify> queryClassifies(Map<String,Object> map);

    String updateClassifyByClassifyCode(Map<String,Object> map);

    String deleteClassifyByClassifyCode(String classifyCode);

    List<Classify> queryAllClassifies();
}
