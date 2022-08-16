package com.mhy.blog.service.api;



import com.mhy.blog.pojo.Label;
import com.mhy.blog.pojo.Page;

import java.util.List;
import java.util.Map;


public interface LabelService {
    String addLabel(Label label);

    Page<Label> queryLabels(Map<String,Object> map);

    String updateLabel(Map<String,Object> map);

    String deleteLabel(String labelCode);

    List<Label> queryAllLabels();
}
