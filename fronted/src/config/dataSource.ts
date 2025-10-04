/**
 * 数据源配置
 * 用于切换Mock数据和真实API
 */

// 是否使用Mock数据（改为false使用真实API）
export const USE_MOCK_DATA = false

// API基础URL
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

/**
 * 获取数据源配置
 */
export const getDataSourceConfig = () => {
  return {
    useMock: USE_MOCK_DATA,
    apiBaseUrl: API_BASE_URL
  }
}

