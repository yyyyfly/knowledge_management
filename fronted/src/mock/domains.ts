// 项目领域管理
export interface Domain {
  id: number
  name: string
  description?: string
  createdAt: string
  updatedAt: string
}

// 初始领域数据
export const domains: Domain[] = [
  {
    id: 1,
    name: '网页开发',
    description: 'Web应用程序开发',
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 2,
    name: '移动应用',
    description: '移动端应用程序开发',
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 3,
    name: '桌面软件',
    description: '桌面应用程序开发',
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 4,
    name: '视觉设计',
    description: 'UI/UX设计和视觉创意',
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  },
  {
    id: 5,
    name: '数据分析',
    description: '数据分析和可视化',
    createdAt: '2024-01-01T00:00:00Z',
    updatedAt: '2024-01-01T00:00:00Z'
  }
]

// 领域管理API模拟
export const domainAPI = {
  // 获取所有领域
  getAll: (): Domain[] => {
    return domains
  },

  // 添加新领域
  add: (name: string, description?: string): Domain => {
    const newDomain: Domain = {
      id: Math.max(...domains.map(d => d.id)) + 1,
      name: name.trim(),
      description: description?.trim(),
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString()
    }
    domains.push(newDomain)
    return newDomain
  },

  // 更新领域
  update: (id: number, updates: Partial<Domain>): Domain | null => {
    const index = domains.findIndex(d => d.id === id)
    if (index === -1) return null
    
    domains[index] = {
      ...domains[index],
      ...updates,
      updatedAt: new Date().toISOString()
    }
    return domains[index]
  },

  // 删除领域
  delete: (id: number): boolean => {
    const index = domains.findIndex(d => d.id === id)
    if (index === -1) return false
    
    domains.splice(index, 1)
    return true
  },

  // 根据名称查找领域
  findByName: (name: string): Domain | undefined => {
    return domains.find(d => d.name.toLowerCase() === name.toLowerCase())
  },

  // 检查领域是否存在
  exists: (name: string): boolean => {
    return domains.some(d => d.name.toLowerCase() === name.toLowerCase())
  }
} 