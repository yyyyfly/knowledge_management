/**
 * 日期格式化工具函数
 */

/**
 * 格式化日期为本地化字符串
 * @param date - 可以是 Date 对象、时间戳、或日期字符串
 * @returns 格式化后的日期字符串，格式：YYYY/MM/DD
 */
export function formatDate(date: Date | number | string | null | undefined): string {
  if (!date) return '-'
  
  try {
    let dateObj: Date
    
    if (date instanceof Date) {
      dateObj = date
    } else if (typeof date === 'number') {
      dateObj = new Date(date)
    } else if (typeof date === 'string') {
      // 如果是空字符串，返回 '-'
      if (date.trim() === '') return '-'
      dateObj = new Date(date)
    } else {
      return '-'
    }
    
    // 检查日期是否有效
    if (isNaN(dateObj.getTime())) {
      return '-'
    }
    
    return dateObj.toLocaleDateString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    })
  } catch (error) {
    console.error('日期格式化错误:', error)
    return '-'
  }
}

/**
 * 格式化日期时间为本地化字符串
 * @param date - 可以是 Date 对象、时间戳、或日期字符串
 * @returns 格式化后的日期时间字符串，格式：YYYY/MM/DD HH:mm:ss
 */
export function formatDateTime(date: Date | number | string | null | undefined): string {
  if (!date) return '-'
  
  try {
    let dateObj: Date
    
    if (date instanceof Date) {
      dateObj = date
    } else if (typeof date === 'number') {
      dateObj = new Date(date)
    } else if (typeof date === 'string') {
      if (date.trim() === '') return '-'
      dateObj = new Date(date)
    } else {
      return '-'
    }
    
    // 检查日期是否有效
    if (isNaN(dateObj.getTime())) {
      return '-'
    }
    
    return dateObj.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    })
  } catch (error) {
    console.error('日期时间格式化错误:', error)
    return '-'
  }
}

/**
 * 格式化相对时间（如：刚刚、5分钟前、1小时前）
 * @param date - 可以是 Date 对象、时间戳、或日期字符串
 * @returns 格式化后的相对时间字符串
 */
export function formatRelativeTime(date: Date | number | string | null | undefined): string {
  if (!date) return '-'
  
  try {
    let dateObj: Date
    
    if (date instanceof Date) {
      dateObj = date
    } else if (typeof date === 'number') {
      dateObj = new Date(date)
    } else if (typeof date === 'string') {
      if (date.trim() === '') return '-'
      dateObj = new Date(date)
    } else {
      return '-'
    }
    
    // 检查日期是否有效
    if (isNaN(dateObj.getTime())) {
      return '-'
    }
    
    const now = new Date().getTime()
    const targetTime = dateObj.getTime()
    const diff = now - targetTime
    
    // 未来时间
    if (diff < 0) {
      return formatDate(dateObj)
    }
    
    const seconds = Math.floor(diff / 1000)
    const minutes = Math.floor(seconds / 60)
    const hours = Math.floor(minutes / 60)
    const days = Math.floor(hours / 24)
    
    if (seconds < 60) {
      return '刚刚'
    } else if (minutes < 60) {
      return `${minutes}分钟前`
    } else if (hours < 24) {
      return `${hours}小时前`
    } else if (days < 7) {
      return `${days}天前`
    } else {
      return formatDate(dateObj)
    }
  } catch (error) {
    console.error('相对时间格式化错误:', error)
    return '-'
  }
}



















