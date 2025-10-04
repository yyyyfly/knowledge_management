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
export const register = (username: string, password: string, nickname: string) => {
  return request({
    url: '/auth/register',
    method: 'post',
    data: {
      username,
      password,
      nickname
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

