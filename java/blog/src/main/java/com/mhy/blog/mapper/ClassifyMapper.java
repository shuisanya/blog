package com.mhy.blog.mapper;

import com.mhy.blog.pojo.Classify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ClassifyMapper {
    /**
     * 增加一个分类
     * @param classify 分类对象
     */
    void addClassify(Classify classify);

    /**
     * 查询类别的所有信息
     * @param map 要求集合
     * @return  返回查询数量
     */
    int queryClassifyTotalCount(Map<String,Object> map);

    /**
     * 根据相关信息查询类别信息
     * @param map 相关信息集合
     * @return 返回类别集合
     */
    List<Classify> queryClassifies(Map<String,Object> map);

    /**
     * 更新一个类别
     * @param classify 类别对象
     */
    void updateClassify(Classify classify);

    /**
     * 删除一个类别
     * @param classifyCode 类别编号
     */
    void deleteClassify(@Param("classifyCode") String classifyCode);

    /**
     * 查询全部类别的编号和名称
     * @return 类别对象的集合
     */
    List<Classify> queryAllClassifies();
}
