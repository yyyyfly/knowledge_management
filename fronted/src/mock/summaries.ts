// 系统总结类型定义
export interface SystemSummary {
  id: number
  title: string
  type: 'daily' | 'monthly' | 'quarterly' | 'yearly'
  period: string // 总结周期
  description: string // 长段描述
  content: {
    // 日报字段
    dailyTasks?: { content: string, duration?: string }[] // 今日任务完成情况
    dailyProblems?: { problem: string, solution?: string }[] // 过程中的小问题与即时应对
    dailyHighlights?: string[] // 当日亮点或有效行为
    dailySummary?: string
    
    // 月报字段
    monthlyTaskCategories?: string[] // 本月主要任务分类及比重
    monthlyTrends?: string[] // 行动趋势与效率变化
    monthlyExperiences?: string[] // 初步经验与方法雏形
    monthlySummary?: string
    
    // 季报字段
    quarterlyTaskOverview?: string[] // 季度任务结构与节奏概览
    quarterlyEfficientPaths?: string[] // 月报中反复出现的高效路径汇总
    quarterlyProblemSolutions?: string[] // 问题类型与对应解决模式归纳
    quarterlyUpgrades?: string[] // 技能或思维方式的升级点
    quarterlySummary?: string
    
    // 年报字段
    yearlyMap?: string[] // 全年能力/任务地图
    yearlyMethodology?: string[] // 形成的个人方法论体系
    yearlyCriticalReviews?: string[] // 最关键的偏差/误判复盘
    yearlyCognitiveLeaps?: string[] // 认知跃迁节点标记
    yearlyAssets?: string[] // 资产沉淀与系统复用
    yearlySummary?: string
  }
  satisfaction: number // 1-10分
  tags: string[]
  createTime: string
  updateTime: string
}

// 总结模板 - 递进式分层结构
export const summaryTemplates = {
  daily: {
    title: '日报模板（原始记录 + 微反思）',
    description: '为月报提供具体素材，是事实库',
    structure: {
      description: '长段描述',
      dailyTasks: '今日任务完成情况（内容 + 用时）',
      dailyProblems: '过程中的小问题与即时应对',
      dailyHighlights: '当日亮点或有效行为'
    },
    defaultContent: {
      dailyTasks: [{ content: '', duration: '' }],
      dailyProblems: [{ problem: '', solution: '' }],
      dailyHighlights: ['']
    }
  },
  monthly: {
    title: '月报模板（提炼日报 + 构建初步经验）',
    description: '从日报中提炼出趋势、规律、局部方法雏形',
    structure: {
      description: '长段描述',
      monthlyTaskCategories: '本月主要任务分类及比重',
      monthlyTrends: '行动趋势与效率变化',
      monthlyExperiences: '初步经验与方法雏形'
    },
    defaultContent: {
      monthlyTaskCategories: [''],
      monthlyTrends: [''],
      monthlyExperiences: ['']
    }
  },
  quarterly: {
    title: '季报模板（提炼月报 + 抽象结构化方法）',
    description: '将月度经验结构化沉淀为流程、模型或稳定方法',
    structure: {
      description: '长段描述',
      quarterlyTaskOverview: '季度任务结构与节奏概览',
      quarterlyEfficientPaths: '月报中反复出现的高效路径汇总',
      quarterlyProblemSolutions: '问题类型与对应解决模式归纳',
      quarterlyUpgrades: '技能或思维方式的升级点'
    },
    defaultContent: {
      quarterlyTaskOverview: [''],
      quarterlyEfficientPaths: [''],
      quarterlyProblemSolutions: [''],
      quarterlyUpgrades: ['']
    }
  },
  yearly: {
    title: '年报模板（提炼季报 + 认知跃迁与方法论沉淀）',
    description: '基于季度回顾构建方法论/能力地图/战略级认知转折',
    structure: {
      description: '长段描述',
      yearlyMap: '全年能力/任务地图',
      yearlyMethodology: '形成的个人方法论体系',
      yearlyCriticalReviews: '最关键的偏差/误判复盘',
      yearlyCognitiveLeaps: '认知跃迁节点标记',
      yearlyAssets: '资产沉淀与系统复用'
    },
    defaultContent: {
      yearlyMap: [''],
      yearlyMethodology: [''],
      yearlyCriticalReviews: [''],
      yearlyCognitiveLeaps: [''],
      yearlyAssets: ['']
    }
  }
}

// 分类选项
export const summaryCategories = [
  { value: 'work-learning', label: '工作学习', description: '工作相关的学习和技能提升' },
  { value: 'learning', label: '学习', description: '纯粹的学习和知识获取' },
  { value: 'work', label: '工作', description: '工作成果和项目进展' },
  { value: 'project', label: '项目', description: '具体项目的总结和回顾' },
  { value: 'personal', label: '个人', description: '个人成长和发展' },
  { value: 'technical', label: '技术', description: '技术相关的总结' },
  { value: 'business', label: '业务', description: '业务相关的总结' }
]

// Mock数据
export const systemSummaries: SystemSummary[] = [
  {
    id: 1,
    title: '2024年1月15日工作学习总结',
    type: 'daily',
    period: '2024-01-15',
    description: '今天在Vue 3项目开发中学习了新的性能优化技术，同时完成了几个关键功能的开发。整体来说是非常充实且有收获的一天。',
    content: {
      dailyTasks: [
        { content: '完成用户管理模块的前端开发', duration: '4小时' },
        { content: '学习Vue 3的Composition API最佳实践', duration: '2小时' },
        { content: '参加团队技术分享会', duration: '1小时' },
        { content: '修复3个前端bug', duration: '1.5小时' }
      ],
      dailyProblems: [
        { problem: '虚拟滚动实现复杂度较高', solution: '查阅文档并参考开源实现' },
        { problem: '性能优化与开发效率的平衡', solution: '制定渐进式优化策略' }
      ],
      dailyHighlights: [
        '成功实现了虚拟滚动组件',
        '页面加载速度提升30%',
        '掌握了Vue 3的响应式原理'
      ],
      dailySummary: '今天在Vue 3项目开发中学习了新的性能优化技术，同时完成了几个关键功能的开发。整体来说是非常充实且有收获的一天。'
    },
    satisfaction: 8,
    tags: ['Vue3', '性能优化', '前端开发', '工作学习'],
    createTime: '2024-01-15 20:00:00',
    updateTime: '2024-01-15 20:00:00'
  },
  {
    id: 2,
    title: '2024年1月工作学习总结',
    type: 'monthly',
    period: '2024年1月',
    description: '本月在Vue 3项目开发中取得了重要进展，完成了多个核心模块，同时学习了新的技术栈和最佳实践。从日报中提炼出了明显的技术成长趋势和工作效率提升模式。',
    content: {
      monthlyTaskCategories: [
        '前端开发占60%（主要是用户管理模块）',
        '学习新技术占25%（Vue3、性能优化）',
        '团队协作占10%（技术分享、代码审查）',
        '问题修复占5%（bug修复、优化）'
      ],
      monthlyTrends: [
        '开发效率在月中明显提升，熟悉Vue3后速度增加40%',
        '问题解决能力增强，复杂问题处理时间从2天缩短到半天',
        '技术分享频率增加，从被动学习转为主动输出'
      ],
      monthlyExperiences: [
        '虚拟滚动 + 懒加载的组合能有效处理大数据量展示',
        'Composition API的组合式写法提升了代码复用性',
        '性能优化需要从多个维度考虑：加载、渲染、交互'
      ],
      monthlySummary: '本月在Vue 3项目开发中取得了重要进展，完成了多个核心模块，同时学习了新的技术栈和最佳实践。从日报中提炼出了明显的技术成长趋势和工作效率提升模式。'
    },
    satisfaction: 9,
    tags: ['Vue3', '性能优化', '前端开发', '工作学习', '团队协作', '技术分享'],
    createTime: '2024-01-31 18:00:00',
    updateTime: '2024-01-31 18:00:00'
  },
  {
    id: 3,
    title: '2024年第1季度工作学习总结',
    type: 'quarterly',
    period: '2024年第1季度',
    description: '本季度在技术能力和项目管理方面都有了显著提升，成功交付了多个重要项目。从3个月的月报中提炼出了稳定的工作模式和问题解决流程，形成了可复用的方法体系。',
    content: {
      quarterlyTaskOverview: [
        '核心项目交付：用户管理系统（1月）、性能优化项目（2月）、技术规范建设（3月）',
        '技术学习节奏：每月一个主技术栈深入学习',
        '团队协作模式：周技术分享 + 代码审查 + 新人指导'
      ],
      quarterlyEfficientPaths: [
        '项目开发流程：需求分析 → 技术选型 → 原型开发 → 迭代优化',
        '学习新技术方法：官方文档 → 实践项目 → 总结分享',
        '问题解决模式：现象分析 → 根因定位 → 方案设计 → 验证优化'
      ],
      quarterlyProblemSolutions: [
        '性能问题 → 虚拟滚动 + 懒加载 + 缓存策略',
        '代码质量问题 → ESLint规范 + 代码审查 + 重构',
        '学习效率问题 → 项目驱动学习 + 输出倒逼输入'
      ],
      quarterlyUpgrades: [
        '从被动执行任务转向主动规划和优化',
        '建立了系统性的技术学习和实践方法',
        '形成了"学习-实践-分享-迭代"的成长闭环'
      ],
      quarterlySummary: '本季度在技术能力和项目管理方面都有了显著提升，成功交付了多个重要项目。从3个月的月报中提炼出了稳定的工作模式和问题解决流程，形成了可复用的方法体系。'
    },
    satisfaction: 9,
    tags: ['项目管理', '前端开发', '团队协作', '技术分享', '工作学习'],
    createTime: '2024-03-31 18:00:00',
    updateTime: '2024-03-31 18:00:00'
  },
  {
    id: 4,
    title: '2024年度工作学习总结',
    type: 'yearly',
    period: '2024年',
    description: '2024年是技术能力快速提升的一年，在多个重要项目中担任核心角色，同时建立了完善的技术分享和培训体系。基于四个季度的总结，构建了完整的个人技术发展方法论和能力体系。',
    content: {
      yearlyMap: [
        '技术能力：前端架构设计、性能优化、工程化建设',
        '项目管理：需求分析、技术选型、团队协作、质量控制',
        '团队影响：技术分享、新人指导、规范建设、最佳实践推广'
      ],
      yearlyMethodology: [
        '技术学习方法论：项目驱动 + 深度实践 + 输出分享 + 迭代优化',
        '问题解决方法论：现象→本质→方案→验证的四步法',
        '团队协作方法论：透明沟通 + 知识共享 + 持续改进'
      ],
      yearlyCriticalReviews: [
        'Q2技术选型过于激进导致项目延期，学会了平衡创新与稳定',
        'Q3团队扩张期沟通成本激增，建立了更好的协作流程',
        'Q4过度关注技术细节忽略业务价值，调整了技术与业务的平衡'
      ],
      yearlyCognitiveLeaps: [
        'Q1：从"完成任务"到"主动优化"的思维转变',
        'Q2：从"个人技术"到"团队效能"的视角扩展',
        'Q3：从"技术实现"到"业务价值"的认知升级',
        'Q4：从"解决问题"到"预防问题"的思维进化'
      ],
      yearlyAssets: [
        '技术资产：前端性能优化工具库、代码规范模板、最佳实践文档',
        '流程资产：项目开发流程、代码审查checklist、技术分享模板',
        '知识资产：技术学习笔记体系、问题解决案例库、团队知识库'
      ],
      yearlySummary: '2024年是技术能力快速提升的一年，在多个重要项目中担任核心角色，同时建立了完善的技术分享和培训体系。基于四个季度的总结，构建了完整的个人技术发展方法论和能力体系。'
    },
    satisfaction: 9,
    tags: ['技术领导力', '项目管理', '团队协作', '技术分享', '工作学习', '年度总结'],
    createTime: '2024-12-31 18:00:00',
    updateTime: '2024-12-31 18:00:00'
  }
]

// 获取所有总结
export const getAllSummaries = (): SystemSummary[] => {
  return systemSummaries
}

// 根据类型获取总结
export const getSummariesByType = (type: SystemSummary['type']): SystemSummary[] => {
  return systemSummaries.filter(summary => summary.type === type)
}



// 根据标签搜索总结
export const searchSummariesByTags = (tags: string[]): SystemSummary[] => {
  return systemSummaries.filter(summary => 
    tags.some(tag => summary.tags.includes(tag))
  )
}

// 添加新总结
export const addSummary = (summary: Omit<SystemSummary, 'id' | 'createTime' | 'updateTime'>): SystemSummary => {
  const newSummary: SystemSummary = {
    ...summary,
    id: Date.now(),
    createTime: new Date().toISOString(),
    updateTime: new Date().toISOString()
  }
  systemSummaries.unshift(newSummary)
  return newSummary
}

// 更新总结
export const updateSummary = (id: number, updates: Partial<SystemSummary>): SystemSummary | null => {
  const index = systemSummaries.findIndex(summary => summary.id === id)
  if (index === -1) return null
  
  systemSummaries[index] = {
    ...systemSummaries[index],
    ...updates,
    updateTime: new Date().toISOString()
  }
  return systemSummaries[index]
}

// 删除总结
export const deleteSummary = (id: number): boolean => {
  const index = systemSummaries.findIndex(summary => summary.id === id)
  if (index === -1) return false
  
  systemSummaries.splice(index, 1)
  return true
} 