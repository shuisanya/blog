package com.mhy.blog.mapper;

import com.mhy.blog.pojo.Classify;
import com.mhy.blog.pojo.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface LabelMapper {
    /**
     * 增加一个标签
     * @param label 标签对象
     */
    void addLabel(Label label);

    /**
     * 查询标签的所有总数
     * @param map 要求集合
     * @return  返回查询数量
     */
    int queryLabelTotalCount(Map<String,Object> map);

    /**
     * 根据页码查询标签
     * @param map 相关信息
     * @return 返回标签集合
     */
    List<Label> queryLabels(Map<String,Object> map);

    /**
     * 更新一个标签
     * @param label 更新信息对象
     */
    void updateLabel(Label label);

    /**
     * 删除一个标签
     * @param labelCode 表编号
     */
    void deleteLabel(@Param("labelCode") String labelCode);

    /**
     * 查询全部标签的编号和名称
     * @return 标签对象
     */
    List<Label> queryAllLabels();

    /**
     * 根据博客编号查询标签
     * @param code 博客编号
     * @return 标签集合
     */
    List<Label> queryLabelsByCode(String code);

    /**
     * 删除一个标签和博客的关系
     * @param labelCode 标签编号
     */
    void deleteLabelWithBlog(String labelCode);
}
