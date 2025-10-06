/**
 * 笔记服务 - 统一Mock和API的接口
 */
import { USE_MOCK_DATA } from '@/config/dataSource'

// Mock导入
import * as mockNotes from '@/mock/notes'

// API导入
import * as apiNote from '@/api/note'

// 类型定义
export interface Note {
  id?: number
  title: string
  content?: string
  type: string
  tags?: string[]
  materialIds?: string
  recCreator?: string
  recCreateTime?: string
  createTime?: string
}

/**
 * 获取所有笔记
 */
export const getAllNotes = async (): Promise<Note[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockNotes.getAllNotes())
  } else {
    const response = await apiNote.getAllNotes()
    return response.data || []
  }
}

/**
 * 根据类型获取笔记
 */
export const getNotesByType = async (type: string): Promise<Note[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockNotes.getNotesByType(type as any))
  } else {
    const response = await apiNote.getNotesByType(type)
    return response.data || []
  }
}

/**
 * 获取笔记统计
 */
export const getNotesStats = async () => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockNotes.getNotesStats())
  } else {
    const notes = await getAllNotes()
    const byType: Record<string, number> = {}
    notes.forEach(note => {
      byType[note.type] = (byType[note.type] || 0) + 1
    })
    return {
      total: notes.length,
      byType
    }
  }
}

/**
 * 创建笔记
 */
export const createNote = async (note: Note): Promise<Note | null> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持创建笔记')
    return Promise.resolve(null)
  } else {
    const response = await apiNote.createNote(note)
    return response.data
  }
}

/**
 * 更新笔记
 */
export const updateNote = async (note: Note): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持更新笔记')
    return Promise.resolve(false)
  } else {
    const response = await apiNote.updateNote(note)
    return response.code === 200
  }
}

/**
 * 删除笔记
 */
export const deleteNote = async (id: number): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持删除笔记')
    return Promise.resolve(false)
  } else {
    const response = await apiNote.deleteNote(id)
    return response.code === 200
  }
}





