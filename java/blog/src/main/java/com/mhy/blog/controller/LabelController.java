package com.mhy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.mhy.blog.pojo.Label;
import com.mhy.blog.pojo.Page;
import com.mhy.blog.service.api.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
public class LabelController {

    private LabelService labelService;
    @Autowired
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/manage/label/addLabel/{labelName}")
    public String addLabel(@PathVariable String labelName){
        Label label = new Label();
        label.setLabelName(labelName);
        return labelService.addLabel(label);
    }

    @GetMapping("/manage/label/queryLabels/{strPage}")
    public Page<Label> queryLabels(@PathVariable String strPage){
        Map<String,Object> map = JSONObject.parseObject(strPage, (Type) Map.class);

        return labelService.queryLabels(map);
    }

    @GetMapping("/manage/label/updateLabel/{strLabel}")
    public String updateLabel(@PathVariable String strLabel){
        Map<String,Object> map = JSONObject.parseObject(strLabel, (Type) Map.class);
        return labelService.updateLabel(map);
    }

    @GetMapping("/manage/label/deleteLabel/{labelCode}")
    public String deleteLabel(@PathVariable String labelCode){

        return labelService.deleteLabel(labelCode);
    }

    @GetMapping("/manage/label/queryAllLabels")
    public List<Label> queryAllLabels(){

        return labelService.queryAllLabels();
    }

//    前台
    @GetMapping("/index/label/queryAllLabels")
    public List<Label> queryIndexAllLabels(){
        return labelService.queryAllLabels();
    }
}
