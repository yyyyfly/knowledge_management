/**
 * 笔记相关API
 */
import request from './request'

// 笔记接口类型定义
export interface Note {
  id?: number
  type: string
  title: string
  summary?: string
  content?: string
  tags?: string
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有笔记
 */
export const getAllNotes = () => {
  return request({
    url: '/note/list',
    method: 'get'
  })
}

/**
 * 根据ID获取笔记
 */
export const getNoteById = (id: number) => {
  return request({
    url: `/note/${id}`,
    method: 'get'
  })
}

/**
 * 根据类型获取笔记
 */
export const getNotesByType = (type: string) => {
  return request({
    url: `/note/type/${type}`,
    method: 'get'
  })
}

/**
 * 搜索笔记
 */
export const searchNotes = (keyword: string) => {
  return request({
    url: '/note/search',
    method: 'get',
    params: { keyword }
  })
}

/**
 * 创建笔记
 */
export const createNote = (note: Note) => {
  return request({
    url: '/note',
    method: 'post',
    data: note
  })
}

/**
 * 更新笔记
 */
export const updateNote = (note: Note) => {
  return request({
    url: '/note',
    method: 'put',
    data: note
  })
}

/**
 * 删除笔记
 */
export const deleteNote = (id: number) => {
  return request({
    url: `/note/${id}`,
    method: 'delete'
  })
}





