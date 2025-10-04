// 荣誉战绩类型定义
export interface Honor {
  id: number
  title: string
  description: string
  category: 'defense' | 'construction' | 'diplomatic' // 对应项目的分类
  achievedTime: string
  projectId?: number // 关联的项目ID（如果是从项目加入的）
  icon: string // 图标类型
  createTime: string
}

// 荣誉战绩数据
export const honors: Honor[] = [
  // 战争行动荣誉
  {
    id: 1,
    title: 'PMP认证',
    description: '项目管理专业认证',
    category: 'defense',
    achievedTime: '2024-03-15 10:00',
    icon: 'fa-medal',
    createTime: '2024-03-15 10:00'
  },
  {
    id: 2,
    title: '高级工程师',
    description: '技术职称认证',
    category: 'defense',
    achievedTime: '2023-12-20 14:30',
    icon: 'fa-medal',
    createTime: '2023-12-20 14:30'
  },
  // 工程建设荣誉
  {
    id: 3,
    title: '个人知识管理系统',
    description: 'Vue3 + TypeScript 开发',
    category: 'construction',
    achievedTime: '2024-06-15 16:00',
    icon: 'fa-star',
    createTime: '2024-06-15 16:00'
  },
  {
    id: 4,
    title: '自动化测试平台',
    description: 'CI/CD 集成测试',
    category: 'construction',
    achievedTime: '2024-05-20 11:30',
    icon: 'fa-star',
    createTime: '2024-05-20 11:30'
  },
  // 外交行动荣誉
  {
    id: 5,
    title: '技术分享会',
    description: '团队技术交流活动',
    category: 'diplomatic',
    achievedTime: '2024-04-10 09:00',
    icon: 'fa-handshake',
    createTime: '2024-04-10 09:00'
  },
  {
    id: 6,
    title: '开源项目贡献',
    description: '社区代码贡献',
    category: 'diplomatic',
    achievedTime: '2024-02-25 15:45',
    icon: 'fa-handshake',
    createTime: '2024-02-25 15:45'
  }
]

// 获取所有荣誉战绩
export const getAllHonors = (): Honor[] => {
  return honors
}

// 根据分类获取荣誉战绩
export const getHonorsByCategory = (category: 'defense' | 'construction' | 'diplomatic'): Honor[] => {
  return honors.filter(honor => honor.category === category)
}

// 从项目添加荣誉战绩
export const addHonorFromProject = (projectId: number, projectName: string, projectDescription: string, category: 'defense' | 'construction' | 'diplomatic'): Honor => {
  const now = new Date()
  const currentTime = now.toISOString().slice(0, 19).replace('T', ' ')
  
  // 根据分类选择图标
  const iconMap = {
    defense: 'fa-medal',
    construction: 'fa-star',
    diplomatic: 'fa-handshake'
  }
  
  const newHonor: Honor = {
    id: Math.max(...honors.map(h => h.id)) + 1,
    title: projectName,
    description: projectDescription,
    category,
    achievedTime: currentTime, // 使用完整的日期时间格式
    projectId,
    icon: iconMap[category],
    createTime: currentTime
  }
  
  honors.push(newHonor)
  return newHonor
}

// 删除荣誉战绩
export const deleteHonor = (id: number): boolean => {
  const index = honors.findIndex(honor => honor.id === id)
  if (index === -1) return false
  
  honors.splice(index, 1)
  return true
}

// 检查项目是否已经在荣誉战绩中
export const isProjectInHonors = (projectId: number): boolean => {
  return honors.some(honor => honor.projectId === projectId)
}

// 获取荣誉战绩统计
export const getHonorsStats = () => {
  const allHonors = getAllHonors()
  const defenseHonors = getHonorsByCategory('defense')
  const constructionHonors = getHonorsByCategory('construction')
  const diplomaticHonors = getHonorsByCategory('diplomatic')
  
  return {
    total: allHonors.length,
    defense: defenseHonors.length,
    construction: constructionHonors.length,
    diplomatic: diplomaticHonors.length
  }
} 