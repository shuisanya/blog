package com.mhy.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Page<T> {
    public final Integer PAGE_SIZE = 10;
    //当前页码
    private Integer currentPage;

    //总页码
    private Integer pageTotal;

    //当前页面显示的数据的量
    private Integer pageSize = PAGE_SIZE;

    //总记录数
    private  Integer pageTotalCount;

    //当前页面数据
    private List<T> items;


    public void setPageTotal(Integer pageTotal) {
        //判断边界条件
        if(getPageTotalCount() % getPageSize() > 0){
            pageTotal += 1;
        }

        this.pageTotal = pageTotal;
    }
}
