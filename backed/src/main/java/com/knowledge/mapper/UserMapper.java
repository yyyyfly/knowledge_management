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

    /**
     * 根据用户名和邮箱查询用户（用于密码重置验证）
     */
    User findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    /**
     * 更新用户密码
     */
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    /**
     * 更新用户信息
     */
    int updateUserInfo(User user);

    /**
     * 查询所有用户（仅管理员可用）
     */
    java.util.List<User> findAllUsers();

    /**
     * 检查用户名是否已存在
     */
    int countByUsername(@Param("username") String username);
}

