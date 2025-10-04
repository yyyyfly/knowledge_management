export * from './projects'
export * from './tasks'
export * from './notes'
export * from './honors'

// 获取当前北京时间
const getBeijingTime = () => {
  const now = new Date()
  // 北京时间是UTC+8
  const beijingTime = new Date(now.getTime() + 8 * 60 * 60 * 1000)
  return beijingTime
}



// 生成基于当前时间的时间节点数据
const generateTimeNodes = () => {
  const beijingTime = getBeijingTime()
  const formatTime = (date: Date) => date.toISOString().slice(0, 19).replace('T', ' ')
  
  // 计算不同时间点
  const fiveDaysAgo = new Date(beijingTime.getTime() - 5 * 24 * 60 * 60 * 1000)
  const threeDaysAgo = new Date(beijingTime.getTime() - 3 * 24 * 60 * 60 * 1000)
  const twoDaysAgo = new Date(beijingTime.getTime() - 2 * 24 * 60 * 60 * 1000)
  const oneDayAgo = new Date(beijingTime.getTime() - 1 * 24 * 60 * 60 * 1000)
  const oneDayLater = new Date(beijingTime.getTime() + 1 * 24 * 60 * 60 * 1000)
  const twoDaysLater = new Date(beijingTime.getTime() + 2 * 24 * 60 * 60 * 1000)
  const threeDaysLater = new Date(beijingTime.getTime() + 3 * 24 * 60 * 60 * 1000)
  const fiveDaysLater = new Date(beijingTime.getTime() + 5 * 24 * 60 * 60 * 1000)
  const sevenDaysLater = new Date(beijingTime.getTime() + 7 * 24 * 60 * 60 * 1000)
  const tenDaysLater = new Date(beijingTime.getTime() + 10 * 24 * 60 * 60 * 1000)
  const twelveDaysLater = new Date(beijingTime.getTime() + 12 * 24 * 60 * 60 * 1000)
  const fifteenDaysLater = new Date(beijingTime.getTime() + 15 * 24 * 60 * 60 * 1000)
  const twentyDaysLater = new Date(beijingTime.getTime() + 20 * 24 * 60 * 60 * 1000)
  const thirtyDaysLater = new Date(beijingTime.getTime() + 30 * 24 * 60 * 60 * 1000)
  
  return [
    // 战争行动时间节点
    {
      id: 1,
      title: 'Vue 3 项目上线',
      description: '企业级知识管理系统正式上线部署',
      time: formatTime(fiveDaysAgo), // 已逾期
      type: 'war' as const,
      status: 'overdue' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 10 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 2,
      title: '客户需求评审会议',
      description: '与客户进行新功能需求的详细评审',
      time: formatTime(threeDaysAgo), // 已逾期
      type: 'war' as const,
      status: 'overdue' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 8 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 3,
      title: '技术文档提交',
      description: '提交项目技术文档给客户审核',
      time: formatTime(oneDayLater), // 即将开始（一周内）
      type: 'war' as const,
      status: 'upcoming' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 6 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 4,
      title: '系统性能测试',
      description: '进行系统性能压力测试和优化',
      time: formatTime(twoDaysLater), // 即将开始（一周内）
      type: 'war' as const,
      status: 'upcoming' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 5 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 5,
      title: '项目里程碑验收',
      description: '第一个开发阶段的功能验收',
      time: formatTime(fiveDaysLater), // 即将开始（一周内）
      type: 'war' as const,
      status: 'upcoming' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 4 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 6,
      title: '代码审查会议',
      description: '团队代码审查和最佳实践讨论',
      time: formatTime(sevenDaysLater), // 未开始（超过一周）
      type: 'war' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 3 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 7,
      title: '安全测试评估',
      description: '进行系统安全漏洞扫描和修复',
      time: formatTime(tenDaysLater), // 未开始（超过一周）
      type: 'war' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 2 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 8,
      title: '用户培训准备',
      description: '准备用户操作培训和文档',
      time: formatTime(fifteenDaysLater), // 未开始（超过一周）
      type: 'war' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 1 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 9,
      title: '生产环境部署',
      description: '正式环境部署和监控配置',
      time: formatTime(twentyDaysLater), // 未开始（超过一周）
      type: 'war' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 1 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },

    // 外交活动时间节点
    {
      id: 10,
      title: 'Vue.js 技术分享会',
      description: '分享Vue 3新特性和企业级应用开发经验',
      time: formatTime(twoDaysAgo), // 已逾期
      type: 'diplomatic' as const,
      status: 'overdue' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 9 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 11,
      title: '前端技术社区聚会',
      description: '与本地前端开发者交流技术趋势和项目经验',
      time: formatTime(oneDayAgo), // 已逾期
      type: 'diplomatic' as const,
      status: 'overdue' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 7 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 12,
      title: '开源项目贡献活动',
      description: '参与Vue生态系统开源项目，提交代码和文档贡献',
      time: formatTime(threeDaysLater), // 即将开始（一周内）
      type: 'diplomatic' as const,
      status: 'upcoming' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 6 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 13,
      title: '技术博客写作挑战',
      description: '参与技术写作挑战，分享前端开发经验和最佳实践',
      time: formatTime(fiveDaysLater), // 即将开始（一周内）
      type: 'diplomatic' as const,
      status: 'upcoming' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 5 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 14,
      title: '线上技术讲座',
      description: '举办线上技术讲座，分享最新前端技术趋势',
      time: formatTime(sevenDaysLater), // 即将开始（一周内）
      type: 'diplomatic' as const,
      status: 'upcoming' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 4 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 15,
      title: '开发者大会参与',
      description: '参加年度开发者大会，展示项目成果',
      time: formatTime(twelveDaysLater), // 未开始（超过一周）
      type: 'diplomatic' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 3 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 16,
      title: '技术书籍编写',
      description: '开始编写前端技术相关书籍',
      time: formatTime(fifteenDaysLater), // 未开始（超过一周）
      type: 'diplomatic' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 2 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 17,
      title: '国际技术会议',
      description: '参加国际前端技术会议，学习最新技术',
      time: formatTime(twentyDaysLater), // 未开始（超过一周）
      type: 'diplomatic' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 1 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    },
    {
      id: 18,
      title: '技术导师计划',
      description: '启动技术导师计划，指导新人开发者',
      time: formatTime(thirtyDaysLater), // 未开始（超过一周）
      type: 'diplomatic' as const,
      status: 'not-started' as const,
      createTime: formatTime(new Date(beijingTime.getTime() - 1 * 24 * 60 * 60 * 1000)),
      updateTime: formatTime(beijingTime)
    }
  ]
}

// 时间节点类型定义
export interface TimeNode {
  id: number
  title: string
  description: string
  time: string
  type: 'war' | 'diplomatic' // 战争行动或外交活动
  status: 'not-started' | 'upcoming' | 'ongoing' | 'completed' | 'overdue' | 'closed' // 未开始、即将开始、进行中、已完成、已逾期、已关闭
  createTime: string
  updateTime: string
}

// 时间节点数据
export const timeNodes: TimeNode[] = generateTimeNodes()

// 获取所有时间节点
export const getAllTimeNodes = (): TimeNode[] => {
  return timeNodes
}

// 根据类型获取时间节点
export const getTimeNodesByType = (type: 'war' | 'diplomatic'): TimeNode[] => {
  return timeNodes.filter(node => node.type === type)
}

// 根据状态获取时间节点
export const getTimeNodesByStatus = (status: 'not-started' | 'upcoming' | 'ongoing' | 'completed' | 'overdue' | 'closed'): TimeNode[] => {
  return timeNodes.filter(node => node.status === status)
}

// 检查并更新逾期状态
export const checkAndUpdateOverdueStatus = () => {
  const beijingTime = getBeijingTime()
  timeNodes.forEach(node => {
    const nodeTime = new Date(node.time.replace(' ', 'T'))
    const oneWeekLater = new Date(beijingTime.getTime() + 7 * 24 * 60 * 60 * 1000)
    
    // 如果时间已过且状态不是进行中、已完成或已关闭，则标记为逾期
    if (nodeTime < beijingTime && !['ongoing', 'completed', 'closed'].includes(node.status)) {
      node.status = 'overdue'
      node.updateTime = beijingTime.toISOString().slice(0, 19).replace('T', ' ')
    } else if (nodeTime > beijingTime) {
      // 如果时间未到
      if (node.status === 'overdue') {
        // 如果状态是逾期，重新检查是否应该设为即将开始
        if (nodeTime <= oneWeekLater) {
          node.status = 'upcoming'
          node.updateTime = beijingTime.toISOString().slice(0, 19).replace('T', ' ')
        }
      } else if (node.status === 'not-started' && nodeTime <= oneWeekLater) {
        // 如果状态是未开始且时间在一周内，设为即将开始
        node.status = 'upcoming'
        node.updateTime = beijingTime.toISOString().slice(0, 19).replace('T', ' ')
      }
    }
  })
}

// 添加时间节点
export const addTimeNode = (timeNode: Omit<TimeNode, 'id' | 'createTime' | 'updateTime' | 'status'>): TimeNode => {
  const now = new Date().toISOString().slice(0, 19).replace('T', ' ')
  const beijingTime = getBeijingTime()
  const nodeTime = new Date(timeNode.time.replace(' ', 'T'))
  const oneWeekLater = new Date(beijingTime.getTime() + 7 * 24 * 60 * 60 * 1000)
  
  // 自动状态设置逻辑
  let status: 'not-started' | 'upcoming' | 'ongoing' | 'completed' | 'overdue' | 'closed'
  
  // 如果时间已过，设为逾期
  if (nodeTime < beijingTime) {
    status = 'overdue'
  } else if (nodeTime <= oneWeekLater) {
    // 如果时间在一周内，设为即将开始
    status = 'upcoming'
  } else {
    // 如果时间超过一周，设为未开始
    status = 'not-started'
  }
  
  const newTimeNode: TimeNode = {
    ...timeNode,
    status,
    id: Math.max(...timeNodes.map(n => n.id)) + 1,
    createTime: now,
    updateTime: now
  }
  timeNodes.push(newTimeNode)
  return newTimeNode
}

// 更新时间节点
export const updateTimeNode = (id: number, updates: Partial<Omit<TimeNode, 'id' | 'createTime' | 'status'>>): TimeNode | null => {
  const index = timeNodes.findIndex(node => node.id === id)
  if (index === -1) return null
  
  const beijingTime = getBeijingTime()
  const now = beijingTime.toISOString().slice(0, 19).replace('T', ' ')
  
  // 如果更新时间，需要重新检查状态
  let newStatus = timeNodes[index].status
  if (updates.time) {
    const nodeTime = new Date(updates.time.replace(' ', 'T'))
    const oneWeekLater = new Date(beijingTime.getTime() + 7 * 24 * 60 * 60 * 1000)
    
    if (nodeTime < beijingTime && timeNodes[index].status === 'upcoming') {
      newStatus = 'overdue'
    } else if (nodeTime > beijingTime) {
      if (timeNodes[index].status === 'overdue' && nodeTime <= oneWeekLater) {
        newStatus = 'upcoming'
      } else if (timeNodes[index].status === 'not-started' && nodeTime <= oneWeekLater) {
        newStatus = 'upcoming'
      }
    }
  }
  
  timeNodes[index] = {
    ...timeNodes[index],
    ...updates,
    status: newStatus,
    updateTime: now
  }
  return timeNodes[index]
}

// 更新时间节点状态
export const updateTimeNodeStatus = (id: number, status: 'not-started' | 'upcoming' | 'ongoing' | 'completed' | 'overdue' | 'closed'): TimeNode | null => {
  const index = timeNodes.findIndex(node => node.id === id)
  if (index === -1) return null
  
  const beijingTime = getBeijingTime()
  const now = beijingTime.toISOString().slice(0, 19).replace('T', ' ')
  timeNodes[index] = {
    ...timeNodes[index],
    status,
    updateTime: now
  }
  return timeNodes[index]
}

// 删除时间节点
export const deleteTimeNode = (id: number): boolean => {
  const index = timeNodes.findIndex(node => node.id === id)
  if (index === -1) return false
  
  timeNodes.splice(index, 1)
  return true
}

// 获取时间节点统计
export const getTimeNodeStats = () => {
  const allNodes = getAllTimeNodes()
  const warNodes = getTimeNodesByType('war')
  const diplomaticNodes = getTimeNodesByType('diplomatic')
  
  return {
    total: allNodes.length,
    war: warNodes.length,
    diplomatic: diplomaticNodes.length,
    'not-started': getTimeNodesByStatus('not-started').length,
    upcoming: getTimeNodesByStatus('upcoming').length,
    ongoing: getTimeNodesByStatus('ongoing').length,
    completed: getTimeNodesByStatus('completed').length,
    overdue: getTimeNodesByStatus('overdue').length,
    closed: getTimeNodesByStatus('closed').length
  }
} 