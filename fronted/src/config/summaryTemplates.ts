/**
 * 总结模板配置
 * 用于定义不同类型总结的字段结构和显示方式
 */

export interface TemplateField {
  key: string // 字段键名
  label: string // 显示标题
  icon: string // 图标
  iconColor: string // 图标颜色
  type: 'list' | 'objectList' | 'text' | 'textarea' // 字段类型
  placeholder?: string // 占位符
  borderColor?: string // 边框颜色
  bgGradient?: string // 背景渐变
  subFields?: { // 对象列表的子字段
    key: string
    placeholder: string
  }[]
}

export interface SummaryTemplate {
  type: string
  label: string
  fields: TemplateField[]
}

// 每日总结模板
export const dailyTemplate: SummaryTemplate = {
  type: 'daily',
  label: '每日总结',
  fields: [
    {
      key: 'dailyTasks',
      label: '今日任务完成情况（含耗时）',
      icon: 'fas fa-tasks',
      iconColor: 'text-blue-500',
      type: 'objectList',
      borderColor: 'border-blue-400',
      bgGradient: 'from-blue-50 via-blue-100 to-blue-200',
      subFields: [
        { key: 'content', placeholder: '任务内容' },
        { key: 'duration', placeholder: '耗时' }
      ]
    },
    {
      key: 'dailyProblems',
      label: '过程中的问题与应对方式',
      icon: 'fas fa-bolt',
      iconColor: 'text-orange-500',
      type: 'objectList',
      borderColor: 'border-orange-400',
      bgGradient: 'from-orange-50 via-orange-100 to-orange-200',
      subFields: [
        { key: 'problem', placeholder: '问题描述' },
        { key: 'solution', placeholder: '应对方法' }
      ]
    },
    {
      key: 'dailyHighlights',
      label: '今日收获',
      icon: 'fas fa-lightbulb',
      iconColor: 'text-yellow-500',
      type: 'list',
      placeholder: '收获/亮点',
      borderColor: 'border-yellow-400',
      bgGradient: 'from-yellow-50 via-yellow-100 to-yellow-200'
    },
    {
      key: 'dailySummary',
      label: '简要总结段',
      icon: 'fas fa-quote-right',
      iconColor: 'text-purple-500',
      type: 'textarea',
      placeholder: '今天整体状态如何？是否有感受、意外、反思、思考？',
      borderColor: 'border-purple-400',
      bgGradient: 'from-purple-50 via-purple-100 to-purple-200'
    }
  ]
}

// 所有模板配置
export const summaryTemplates: Record<string, SummaryTemplate> = {
  daily: dailyTemplate
  // 未来可以添加其他模板，如：
  // weekly: weeklyTemplate,
  // custom: customTemplate
}

// 获取指定类型的模板
export function getTemplate(type: string): SummaryTemplate | null {
  return summaryTemplates[type] || null
}


