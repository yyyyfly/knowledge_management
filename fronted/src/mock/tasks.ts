// 生成当前北京时间往前推6个月的时间范围
const generateTimeRange = () => {
  const now = new Date();
  // 获取北京时间（UTC+8）
  const beijingTime = new Date(now.getTime() + 8 * 60 * 60 * 1000);
  
  // 往前推6个月
  const sixMonthsAgo = new Date(beijingTime);
  sixMonthsAgo.setMonth(sixMonthsAgo.getMonth() - 6);
  
  return {
    start: sixMonthsAgo,
    end: beijingTime
  };
};

// 生成指定时间范围内的随机时间
const generateRandomTime = (start: Date, end: Date): string => {
  const randomTime = new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
  const year = randomTime.getFullYear();
  const month = String(randomTime.getMonth() + 1).padStart(2, '0');
  const day = String(randomTime.getDate()).padStart(2, '0');
  const hours = String(randomTime.getHours()).padStart(2, '0');
  const minutes = String(randomTime.getMinutes()).padStart(2, '0');
  
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

// 获取时间范围
const timeRange = generateTimeRange();

// 任务类型定义
export interface ExecutionRecord {
  id: number
  date: string
  progress: string
}

export interface Task {
  id: number
  projectId: number
  name: string
  description: string
  status: string
  createTime: string
  deadline: string
  executionRecords: ExecutionRecord[]
  newProgress: string
  completedTime?: string | null
}

export const tasks: Task[] = [
  // 个人博客系统任务
  {
    id: 1,
    projectId: 1,
    name: '设计数据库结构',
    description: '设计博客系统的数据库表结构，包括用户、文章、分类等表',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-28 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 2,
    projectId: 1,
    name: '实现用户认证',
    description: '实现用户注册、登录、密码重置等认证功能',
    status: 'in-progress',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-02 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 3,
    projectId: 1,
    name: '文章管理功能',
    description: '实现文章的创建、编辑、删除、发布等功能',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-05 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 4,
    projectId: 1,
    name: '评论系统开发',
    description: '实现文章评论功能，包括评论的发布、回复、审核等',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-08 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 5,
    projectId: 1,
    name: '响应式UI设计',
    description: '设计并实现响应式用户界面，适配各种设备',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-12 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  
  // 学习管理系统任务
  {
    id: 6,
    projectId: 2,
    name: '需求分析',
    description: '详细分析学习管理系统的功能需求和用户需求',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-03 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 7,
    projectId: 2,
    name: '系统架构设计',
    description: '设计系统整体架构，包括前端、后端、数据库等',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-05 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 8,
    projectId: 2,
    name: '用户界面设计',
    description: '设计用户界面原型和交互流程',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-08 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  
  // 任务管理工具任务（已完成项目）
  {
    id: 9,
    projectId: 3,
    name: '需求调研',
    description: '调研用户需求，分析竞品功能',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-25 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 10,
    projectId: 3,
    name: '原型设计',
    description: '设计应用原型和用户流程',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-27 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 11,
    projectId: 3,
    name: '前端开发',
    description: '使用Vue 3开发前端界面和交互功能',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-01 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 12,
    projectId: 3,
    name: '后端API开发',
    description: '开发后端API接口和数据库操作',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-03 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 13,
    projectId: 3,
    name: '功能测试',
    description: '进行功能测试和bug修复',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-05 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 14,
    projectId: 3,
    name: '性能优化',
    description: '优化应用性能和用户体验',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-07 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 15,
    projectId: 3,
    name: '部署上线',
    description: '部署应用到生产环境',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-10 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 16,
    projectId: 3,
    name: '用户培训',
    description: '编写用户手册和进行用户培训',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-10 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  
  // 企业级Vue 3应用开发任务
  {
    id: 17,
    projectId: 4,
    name: '项目初始化',
    description: '搭建Vue 3 + TypeScript项目基础架构',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-27 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 18,
    projectId: 4,
    name: '组件库开发',
    description: '开发可复用的UI组件库',
    status: 'in-progress',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-03 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 19,
    projectId: 4,
    name: '状态管理设计',
    description: '设计并实现Pinia状态管理方案',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-08 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 20,
    projectId: 4,
    name: '路由权限系统',
    description: '实现基于角色的路由权限控制',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-10 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 21,
    projectId: 4,
    name: 'API接口集成',
    description: '集成后端API接口，实现数据交互',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-12 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 22,
    projectId: 4,
    name: '单元测试',
    description: '编写单元测试，确保代码质量',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-15 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  
  // 微前端架构实践任务
  {
    id: 23,
    projectId: 5,
    name: '架构调研',
    description: '调研微前端架构方案和最佳实践',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-27 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 24,
    projectId: 5,
    name: '主应用开发',
    description: '开发微前端主应用框架',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-30 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 25,
    projectId: 5,
    name: '子应用开发',
    description: '开发微前端子应用示例',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-02 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 26,
    projectId: 5,
    name: '通信机制实现',
    description: '实现主应用和子应用间的通信机制',
    status: 'completed',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-04 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  
  // 技术分享系列任务
  {
    id: 27,
    projectId: 6,
    name: 'Vue 3新特性分享',
    description: '准备Vue 3 Composition API的技术分享内容',
    status: 'in-progress',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-30 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 28,
    projectId: 6,
    name: 'TypeScript最佳实践',
    description: '整理TypeScript在企业项目中的最佳实践',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-05 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 29,
    projectId: 6,
    name: '前端性能优化',
    description: '准备前端性能优化的技术分享',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-10 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  
  // 开源项目贡献任务
  {
    id: 30,
    projectId: 7,
    name: '项目调研',
    description: '调研适合贡献的开源项目',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-03 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 31,
    projectId: 7,
    name: '代码贡献',
    description: '为选定的开源项目贡献代码',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-15 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  
  // 性能优化专项任务
  {
    id: 32,
    projectId: 8,
    name: '性能分析',
    description: '分析当前应用的性能瓶颈',
    status: 'in-progress',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-01 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 33,
    projectId: 8,
    name: '代码分割优化',
    description: '实现代码分割和懒加载优化',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-04 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 34,
    projectId: 8,
    name: '缓存策略优化',
    description: '优化前端缓存策略，提升加载速度',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-06 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 35,
    projectId: 8,
    name: '图片资源优化',
    description: '优化图片资源，实现压缩和懒加载',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-08 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 36,
    projectId: 8,
    name: '性能监控',
    description: '建立性能监控体系，持续跟踪优化效果',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-12 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  
  // 已停止的任务
  {
    id: 37,
    projectId: 8,
    name: '文档编写',
    description: '编写性能优化相关的技术文档',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-25 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 38,
    projectId: 9,
    name: '技术选型',
    description: '选择移动端开发技术栈和框架',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-06-30 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  },
  {
    id: 39,
    projectId: 10,
    name: '需求调研',
    description: '调研数据可视化项目的具体需求',
    status: 'pending',
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    deadline: '2025-07-05 18:00',
    executionRecords: [],
    newProgress: '',
    completedTime: null
  }
]; 