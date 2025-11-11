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
  summary?: string
  tags?: string[]
  materialIds?: string
  recCreator?: string
  recCreateTime?: string
  createTime?: string
  // 背诵笔记字段
  project?: string
  knowledgePoint?: string[]
  reviewCount?: number
  originalText?: string
  explanation?: string
  cue?: string
  // 技能笔记字段
  skillType?: string
  skillPoint?: string
  expectedDescription?: string
  thinkingSummary?: string
  finalEffect?: string
  // 求学笔记字段
  course?: string[]
  studySubject?: string[]
  bookName?: string
  bookSubject?: string[]
  // 刷题笔记字段
  exerciseSource?: string
  exerciseSubject?: string[]
  exerciseKnowledge?: string[]
  exerciseDifficulty?: number
  // 实战笔记字段
  techTags?: string[]
  techStack?: string[]
  projectType?: string[]
  projectName?: string
  // 碎片笔记字段
  fragmentCategory?: string[]
  fragmentTheme?: string[]
  importance?: string
}

/**
 * 将后端返回的逗号分隔字符串字段转换为数组
 */
const normalizeNoteArrayFields = (notes: any[]): any[] => {
  return notes.map(note => {
    const normalized = { ...note }
    
    // 将逗号分隔的字符串、JSON字符串或单个值转换为数组
    const toArray = (value: any) => {
      if (!value) return []
      if (Array.isArray(value)) return value
      if (typeof value === 'string') {
        // 尝试解析 JSON 字符串
        if (value.trim().startsWith('[') && value.trim().endsWith(']')) {
          try {
            const parsed = JSON.parse(value)
            if (Array.isArray(parsed)) return parsed
          } catch (e) {
            // JSON 解析失败，继续使用逗号分割
          }
        }
        // 如果是逗号分隔的字符串，拆分成数组
        return value.split(',').map((item: string) => item.trim()).filter((item: string) => item !== '')
      }
      return [value]
    }
    
    // 处理所有可能的数组字段
    if (normalized.course) normalized.course = toArray(normalized.course)
    if (normalized.studySubject) normalized.studySubject = toArray(normalized.studySubject)
    if (normalized.bookSubject) normalized.bookSubject = toArray(normalized.bookSubject)
    if (normalized.knowledgePoint) normalized.knowledgePoint = toArray(normalized.knowledgePoint)
    if (normalized.techTags) normalized.techTags = toArray(normalized.techTags)
    if (normalized.techStack) normalized.techStack = toArray(normalized.techStack)
    if (normalized.fragmentCategory) normalized.fragmentCategory = toArray(normalized.fragmentCategory)
    if (normalized.fragmentTheme) normalized.fragmentTheme = toArray(normalized.fragmentTheme)
    if (normalized.projectType) normalized.projectType = toArray(normalized.projectType)
    if (normalized.exerciseKnowledge) normalized.exerciseKnowledge = toArray(normalized.exerciseKnowledge)
    if (normalized.exerciseSubject) normalized.exerciseSubject = toArray(normalized.exerciseSubject)
    if (normalized.tags) normalized.tags = toArray(normalized.tags)
    
    return normalized
  })
}

/**
 * 获取所有笔记
 */
export const getAllNotes = async (): Promise<Note[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockNotes.getAllNotes())
  } else {
    const response = await apiNote.getAllNotes()
    const notes = response.data || []
    return normalizeNoteArrayFields(notes)
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
    const notes = response.data || []
    return normalizeNoteArrayFields(notes)
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





