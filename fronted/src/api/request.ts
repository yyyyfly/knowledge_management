/**
 * 统一请求封装
 */
import axios, { AxiosInstance, AxiosResponse } from 'axios'
import type { ApiResponse } from '@/types/api'

// API基础路径
const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

// 创建axios实例
const request: AxiosInstance = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

// 请求拦截器
request.interceptors.request.use(
  (config: any) => {
    // 添加Token到请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    return config
  },
  (error: any) => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    
    // 根据后端返回的code判断
    if (res.code === 200) {
      return res
    } else {
      console.error('请求失败:', res.message)
      return Promise.reject(new Error(res.message || '请求失败'))
    }
  },
  (error: any) => {
    // 处理401未授权错误
    if (error.response?.status === 401) {
      // Token过期或未登录，清除本地存储
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      // 跳转到登录页
      window.location.href = '/login'
    }
    console.error('响应错误:', error)
    return Promise.reject(error)
  }
)

export default request

