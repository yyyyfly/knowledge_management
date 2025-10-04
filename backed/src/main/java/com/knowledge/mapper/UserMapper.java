package com.knowledge.mapper;

import com.knowledge.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     */
    User findByUsername(@Param("username") String username);

    /**
     * 插入新用户
     */
    int insert(User user);

    /**
     * 更新最后登录时间
     */
    int updateLastLoginTime(@Param("id") Long id);
}

