package com.mhy.blog.mapper;

import com.mhy.blog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    /**
     * 根据用户账号查询一个 用户
     * @param userCode 用户账号
     * @return 用户对象
     */
    User queryUserByCode(@Param("userCode") String userCode);
}
