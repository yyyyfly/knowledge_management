// 项目类型定义
export interface Project {
  id: number
  name: string
  description: string
  status: string
  category: string
  createTime: string
  taskCount: number
}

export const projects: Project[] = [
  {
    id: 1,
    name: '个人博客系统',
    description: '开发一个现代化的个人博客系统，支持Markdown编辑和响应式设计',
    status: 'active',
    category: 'construction',
    createTime: '2025-06-25 09:00',
    taskCount: 5
  },
  {
    id: 2,
    name: '学习管理系统',
    description: '构建一个在线学习平台，包含课程管理、进度跟踪等功能',
    status: 'planning',
    category: 'construction',
    createTime: '2025-06-27 14:30',
    taskCount: 3
  },
  {
    id: 3,
    name: '任务管理工具',
    description: '开发一个简洁高效的任务管理应用，提升个人工作效率',
    status: 'completed',
    category: 'defense',
    createTime: '2025-06-20 10:15',
    taskCount: 8
  },
  {
    id: 4,
    name: '企业级Vue 3应用开发',
    description: '基于Vue 3 + TypeScript的企业级前端应用开发项目',
    status: 'active',
    category: 'defense',
    createTime: '2025-06-25 08:30',
    taskCount: 6
  },
  {
    id: 5,
    name: '微前端架构实践',
    description: '基于qiankun的微前端架构设计和实现',
    status: 'completed',
    category: 'construction',
    createTime: '2025-06-23 16:45',
    taskCount: 4
  },
  {
    id: 6,
    name: '技术分享系列',
    description: '团队内部技术分享和知识传播项目',
    status: 'active',
    category: 'diplomatic',
    createTime: '2025-06-25 11:20',
    taskCount: 3
  },
  {
    id: 7,
    name: '开源项目贡献',
    description: '参与开源社区，贡献代码和文档',
    status: 'planning',
    category: 'diplomatic',
    createTime: '2025-06-27 14:15',
    taskCount: 2
  },
  {
    id: 8,
    name: '性能优化专项',
    description: '前端性能优化和用户体验提升专项',
    status: 'active',
    category: 'defense',
    createTime: '2025-06-28 09:45',
    taskCount: 5
  }
];

// 获取已完成的项目
export const getCompletedProjects = () => {
  return projects.filter(project => project.status === 'completed');
};

// 更新项目状态
export const updateProjectStatus = (projectId: number, newStatus: string) => {
  const project = projects.find(p => p.id === projectId);
  if (project) {
    project.status = newStatus;
  }
}; 