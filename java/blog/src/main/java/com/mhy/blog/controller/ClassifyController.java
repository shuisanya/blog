package com.mhy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.mhy.blog.pojo.Classify;
import com.mhy.blog.pojo.Page;
import com.mhy.blog.service.api.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
public class ClassifyController {
    private ClassifyService classifyService;
    @Autowired
    public void setClassifyService(ClassifyService classifyService) {
        this.classifyService = classifyService;
    }

    @GetMapping("/manage/classify/addClassify/{classifyName}")
    public String addClassify(@PathVariable String classifyName){
        Classify classify = new Classify();
        classify.setClassifyName(classifyName);
        return classifyService.addClassify(classify);
    }

    @GetMapping("/manage/classify/queryClassifies/{strPage}")
    public Page<Classify> queryClassifies(@PathVariable String strPage){
        Map<String,Object> map = JSONObject.parseObject(strPage, (Type)Map.class);

        return classifyService.queryClassifies(map);
    }

    @GetMapping("/manage/classify/updateClassify/{strClassify}")
    public String updateClassify(@PathVariable String strClassify){
        Map<String,Object> map = JSONObject.parseObject(strClassify, (Type) Map.class);

        return classifyService.updateClassifyByClassifyCode(map);
    }

    @GetMapping("/manage/classify/deleteClassify/{classifyCode}")
    public String deleteClassify(@PathVariable String classifyCode){
        return classifyService.deleteClassifyByClassifyCode(classifyCode);
    }

    @GetMapping("/manage/classify/queryAllClassifies")
    public List<Classify> queryAllClassifies(){

        return classifyService.queryAllClassifies();
    }

//    前台查询
    @GetMapping("/index/classify/queryAllClassifies")
    public List<Classify> queryIndexAllClassifies(){
        return classifyService.queryAllClassifies();
    }


}
