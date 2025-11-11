/**
 * 统一请求封装
 */
import axios, { AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios'
import type { ApiResponse } from '@/types/api'

// API基础路径
const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

// 创建自定义请求实例类型
interface CustomAxiosInstance extends AxiosInstance {
  <T = any>(config: any): Promise<ApiResponse<T>>
  <T = any>(url: string, config?: any): Promise<ApiResponse<T>>
  get<T = any>(url: string, config?: any): Promise<ApiResponse<T>>
  delete<T = any>(url: string, config?: any): Promise<ApiResponse<T>>
  head<T = any>(url: string, config?: any): Promise<ApiResponse<T>>
  options<T = any>(url: string, config?: any): Promise<ApiResponse<T>>
  post<T = any>(url: string, data?: any, config?: any): Promise<ApiResponse<T>>
  put<T = any>(url: string, data?: any, config?: any): Promise<ApiResponse<T>>
  patch<T = any>(url: string, data?: any, config?: any): Promise<ApiResponse<T>>
}

// 创建axios实例
const service = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

// 请求拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 添加Token到请求头
    const token = localStorage.getItem('token')
    if (token && config.headers) {
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
service.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    const res = response.data
    
    // 根据后端返回的code判断
    if (res.code === 200) {
      return res as any
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

const request = service as CustomAxiosInstance

export default request

