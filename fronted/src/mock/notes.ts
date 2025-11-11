

// 统一笔记类型定义
export interface Note {
  id: number
  type: 'skill' | 'study' | 'memorization' | 'exercise' | 'practical' | 'fragment'
  title: string
  summary: string
  content: string
  tags: string[]
  createTime: string
  // 框架笔记字段
  subject?: string[]
  complexity?: number
  // 求学笔记字段
  course?: string[]
  studySubject?: string[]
  bookName?: string
  bookSubject?: string[]
  difficulty?: number
  // 背诵笔记字段
  project?: string
  knowledgePoint?: string[]
  reviewCount?: number
  // 刷题笔记字段
  problemName?: string
  subjectType?: string[]
  exerciseKnowledgePoint?: string[]
  accuracy?: number
  // 实战笔记字段
  techStack?: string[]
  projectType?: string[]
  projectName?: string
  // 碎片笔记字段
  category?: string[]
  fragmentCategory?: string[]
  fragmentTheme?: string[]
  importance?: 'low' | 'medium' | 'high'
}

// 导入各类型笔记数据
import { getAllSkillNotes } from './frameworkNotes'
import { getAllStudyNotes } from './studyNotes'
import { getAllExerciseNotes } from './exerciseNotes'
import { getAllPracticalNotes } from './practicalNotes'
import { getAllFragmentNotes } from './fragmentNotes'
import { getAllMemorizationNotes } from './memorizationNotes'

// 获取所有笔记
export const getAllNotes = (): Note[] => {
  const skillNotes = getAllSkillNotes()
  const studyNotes = getAllStudyNotes()
  const exerciseNotes = getAllExerciseNotes()
  const practicalNotes = getAllPracticalNotes()
  const fragmentNotes = getAllFragmentNotes()
  const memorizationNotes = getAllMemorizationNotes()
  
  return [
    ...skillNotes,
    ...studyNotes,
    ...exerciseNotes,
    ...practicalNotes,
    ...fragmentNotes,
    ...memorizationNotes
  ]
}

// 根据类型获取笔记
export const getNotesByType = (type: Note['type']): Note[] => {
  switch (type) {
    case 'skill':
      return getAllSkillNotes()
    case 'study':
      return getAllStudyNotes()
    case 'exercise':
      return getAllExerciseNotes()
    case 'practical':
      return getAllPracticalNotes()
    case 'fragment':
      return getAllFragmentNotes()
    case 'memorization':
      return getAllMemorizationNotes()
    default:
      return []
  }
}

// 根据ID获取笔记
export const getNoteById = (id: number): Note | undefined => {
  const allNotes = getAllNotes()
  return allNotes.find(note => note.id === id)
}

// 搜索笔记
export const searchNotes = (query: string): Note[] => {
  const lowerQuery = query.toLowerCase()
  const allNotes = getAllNotes()
  return allNotes.filter(note => 
    note.title.toLowerCase().includes(lowerQuery) ||
    note.summary.toLowerCase().includes(lowerQuery) ||
    note.tags.some(tag => tag.toLowerCase().includes(lowerQuery))
  )
}

// 根据标签获取笔记
export const getNotesByTag = (tag: string): Note[] => {
  const allNotes = getAllNotes()
  return allNotes.filter(note => note.tags.includes(tag))
}

// 获取笔记统计信息
export const getNotesStats = () => {
  const allNotes = getAllNotes()
  const stats = {
    total: allNotes.length,
    byType: {
      skill: allNotes.filter(n => n.type === 'skill').length,
      study: allNotes.filter(n => n.type === 'study').length,
      memorization: allNotes.filter(n => n.type === 'memorization').length,
      exercise: allNotes.filter(n => n.type === 'exercise').length,
      practical: allNotes.filter(n => n.type === 'practical').length,
      fragment: allNotes.filter(n => n.type === 'fragment').length
    }
  }
  return stats
} 
 