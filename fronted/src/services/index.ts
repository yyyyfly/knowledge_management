/**
 * 统一服务层 - 自动切换Mock数据和真实API
 */
import { USE_MOCK_DATA } from '@/config/dataSource'

// Mock数据导入
import * as mockProjects from '@/mock/projects'
import * as mockTasks from '@/mock/tasks'
import * as mockNotes from '@/mock/notes'
import * as mockHonors from '@/mock/honors'
import * as mockData from '@/mock/data'
import * as mockSummaries from '@/mock/summaries'

// 真实API导入
import * as apiProject from '@/api/project'
import * as apiTask from '@/api/task'
import * as apiNote from '@/api/note'
import * as apiHonor from '@/api/honor'
import * as apiTimeNode from '@/api/timenode'
import * as apiSummary from '@/api/summary'

/**
 * 项目服务
 */
export const projectService = USE_MOCK_DATA ? {
  getAllProjects: () => Promise.resolve({ code: 200, data: mockProjects.projects }),
  getProjectsByCategory: (category: string) => Promise.resolve({ 
    code: 200, 
    data: mockProjects.projects.filter(p => p.category === category) 
  }),
  // ... 其他方法可以继续添加
} : apiProject

/**
 * 任务服务
 */
export const taskService = USE_MOCK_DATA ? {
  getAllTasks: () => Promise.resolve({ code: 200, data: mockTasks.tasks }),
  getTasksByStatus: (status: string) => Promise.resolve({ 
    code: 200, 
    data: mockTasks.tasks.filter(t => t.status === status) 
  }),
} : apiTask

/**
 * 笔记服务
 */
export const noteService = USE_MOCK_DATA ? mockNotes : apiNote

/**
 * 荣誉服务
 */
export const honorService = USE_MOCK_DATA ? mockHonors : apiHonor

/**
 * 时间节点服务
 */
export const timeNodeService = USE_MOCK_DATA ? mockData : {
  getAllTimeNodes: apiTimeNode.getAllTimeNodes,
  getTimeNodesByType: apiTimeNode.getTimeNodesByType,
  getTimeNodesByStatus: apiTimeNode.getTimeNodesByStatus,
}

/**
 * 总结服务
 */
export const summaryService = USE_MOCK_DATA ? mockSummaries : apiSummary

// 导出所有服务
export default {
  project: projectService,
  task: taskService,
  note: noteService,
  honor: honorService,
  timeNode: timeNodeService,
  summary: summaryService
}





