package com.knowledge.util;

/**
 * 用户上下文工具类
 * 用于在请求线程中保存和获取当前登录用户信息
 */
public class UserContext {
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    /**
     * 设置当前用户
     */
    public static void setCurrentUser(String username) {
        currentUser.set(username);
    }

    /**
     * 获取当前用户
     */
    public static String getCurrentUser() {
        return currentUser.get();
    }

    /**
     * 清除当前用户
     */
    public static void clear() {
        currentUser.remove();
    }
}

