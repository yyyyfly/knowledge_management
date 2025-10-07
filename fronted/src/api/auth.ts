/**
 * 认证相关API
 */
import request from './request'

/**
 * 用户登录
 */
export const login = (username: string, password: string) => {
  return request({
    url: '/auth/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

/**
 * 用户注册
 */
export const register = (username: string, password: string, nickname: string, email: string, phone?: string) => {
  return request({
    url: '/auth/register',
    method: 'post',
    data: {
      username,
      password,
      nickname,
      email,
      phone
    }
  })
}

/**
 * 获取当前用户信息
 */
export const getCurrentUser = () => {
  return request({
    url: '/auth/current',
    method: 'get'
  })
}

/**
 * 退出登录
 */
export const logout = () => {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

/**
 * 重置密码
 */
export const resetPassword = (username: string, email: string, newPassword: string) => {
  return request({
    url: '/auth/reset-password',
    method: 'post',
    data: {
      username,
      email,
      newPassword
    }
  })
}

/**
 * 更新用户信息
 */
export const updateUserInfo = (userInfo: { nickname: string, email: string, phone?: string }) => {
  return request({
    url: '/auth/update-info',
    method: 'post',
    data: userInfo
  })
}

/**
 * 修改密码（已登录用户）
 */
export const changePassword = (oldPassword: string, newPassword: string) => {
  return request({
    url: '/auth/change-password',
    method: 'post',
    data: {
      oldPassword,
      newPassword
    }
  })
}

