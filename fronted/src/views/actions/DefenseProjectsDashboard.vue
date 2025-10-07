<template>
  <section id="defense-projects-dashboard" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">战争行动</h1>
        <span class="bg-red-100 text-red-600 px-3 py-1 rounded-full text-sm font-medium">实战任务</span>
      </div>
      <p class="text-gray-600">日常工作执行、考试备战、副业发展的综合管理中心，专注于高效完成任务、提升个人价值、实现目标突破</p>
    </div>

    <!-- 任务提醒 -->
    <div v-if="urgentTasks.length > 0" class="bg-gradient-to-r from-orange-50 to-red-50 border-l-4 border-orange-500 rounded-xl shadow-soft p-6 mb-8">
      <div class="flex items-center mb-4">
        <i class="fas fa-exclamation-triangle text-orange-600 text-2xl mr-3"></i>
        <div>
          <h3 class="text-xl font-semibold text-gray-900">紧急任务提醒</h3>
          <p class="text-sm text-gray-600">以下任务需要及时处理（包括已过期和即将到期的任务）</p>
        </div>
      </div>
      <div class="space-y-3">
        <div 
          v-for="task in urgentTasks" 
          :key="task.id"
          class="bg-white rounded-lg p-4 border-l-4 hover:shadow-md transition-shadow"
          :class="{
            'border-red-600 bg-red-50': task.isOverdue,
            'border-red-500': !task.isOverdue && task.daysLeft <= 2,
            'border-orange-500': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
          }"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center space-x-3 mb-2">
                <h4 class="text-md font-semibold text-gray-900">{{ task.name }}</h4>
                <span 
                  class="px-2 py-1 rounded-full text-xs font-medium flex items-center space-x-1"
                  :class="{
                    'bg-red-600 text-white': task.isOverdue,
                    'bg-red-100 text-red-700': !task.isOverdue && task.daysLeft <= 2,
                    'bg-orange-100 text-orange-700': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
                  }"
                >
                  <i v-if="task.isOverdue" class="fas fa-fire"></i>
                  <span>{{ task.isOverdue ? `已逾期${Math.abs(task.daysLeft)}天` : task.daysLeft === 0 ? '今天到期' : task.daysLeft === 1 ? '明天到期' : `还剩${task.daysLeft}天` }}</span>
                </span>
              </div>
              <p class="text-sm text-gray-600 mb-2">{{ task.description }}</p>
              <div class="text-xs text-gray-500">
                <i class="far fa-calendar mr-1"></i>截止时间：{{ task.deadline }}
              </div>
            </div>
          </div>
        </div>
      </div>
      </div>

    <!-- 项目概览 -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
      <!-- 进行中项目 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-900">进行中项目</h3>
          <span class="bg-blue-100 text-blue-600 px-2 py-1 rounded-full text-xs">活跃</span>
        </div>
        <p class="text-2xl font-bold text-blue-600">{{ defenseProjects.filter(p => p.status === 'active').length }} 个</p>
        <p class="text-sm text-gray-500 mt-2">正在执行的项目</p>
      </div>

      <!-- 已完成项目 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-900">已完成项目</h3>
          <span class="bg-green-100 text-green-600 px-2 py-1 rounded-full text-xs">完成</span>
        </div>
        <p class="text-2xl font-bold text-green-600">{{ defenseProjects.filter(p => p.status === 'completed').length }} 个</p>
        <p class="text-sm text-gray-500 mt-2">成功完成的项目</p>
      </div>

      <!-- 计划中项目 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-900">计划中项目</h3>
          <span class="bg-purple-100 text-purple-600 px-2 py-1 rounded-full text-xs">规划</span>
        </div>
        <p class="text-2xl font-bold text-purple-600">{{ defenseProjects.filter(p => p.status === 'planning').length }} 个</p>
        <p class="text-sm text-gray-500 mt-2">准备启动的项目</p>
      </div>
      
      <!-- 总子任务 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-900">总子任务</h3>
          <span class="bg-orange-100 text-orange-600 px-2 py-1 rounded-full text-xs">总计</span>
        </div>
        <p class="text-2xl font-bold text-orange-600">{{ defenseTasks.length }} 个</p>
        <p class="text-sm text-gray-500 mt-2">所有项目子任务</p>
      </div>
    </div>

    <!-- 战争行动项目区域 -->
    <div class="space-y-6">
      <h3 class="text-xl font-semibold text-gray-900">战争行动项目</h3>
      
      <!-- 空状态 -->
      <div v-if="defenseProjects.length === 0" class="bg-white rounded-xl shadow-soft p-12 text-center">
        <i class="fas fa-folder-open text-6xl text-gray-300 mb-4"></i>
        <p class="text-gray-500 text-lg">暂无战争行动项目</p>
        <p class="text-gray-400 text-sm mt-2">开始创建你的第一个实战项目吧！</p>
      </div>

      <!-- 项目列表 -->
      <div v-for="project in defenseProjects" :key="project.id" class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h4 class="text-lg font-semibold text-gray-900">{{ project.name }}</h4>
          <span :class="{
            'bg-blue-100 text-blue-600': project.status === 'active',
            'bg-green-100 text-green-600': project.status === 'completed',
            'bg-purple-100 text-purple-600': project.status === 'planning'
          }" class="px-3 py-1 rounded-full text-sm font-medium">
            {{ project.status === 'active' ? '进行中' : project.status === 'completed' ? '已完成' : '计划中' }}
          </span>
        </div>
        <p class="text-gray-600 mb-4">{{ project.description || '暂无描述' }}</p>
        
        <!-- 项目任务 -->
        <div v-if="defenseTasks.filter(t => t.projectId === project.id).length > 0" class="space-y-3 mt-4">
          <h5 class="text-sm font-medium text-gray-700 mb-2">任务列表：</h5>
          <div v-for="task in defenseTasks.filter(t => t.projectId === project.id)" :key="task.id" 
               :class="{
                 'bg-green-50': task.status === 'completed',
                 'bg-blue-50': task.status === 'in-progress',
                 'bg-gray-50': task.status === 'pending',
                 'bg-red-50': task.status === 'stopped'
               }" 
               class="rounded-lg p-4">
              <div class="flex items-center justify-between mb-2">
                <div class="flex items-center space-x-3">
                <span class="text-sm font-medium text-gray-900">{{ task.name }}</span>
              </div>
              <span :class="{
                'bg-green-100 text-green-600': task.status === 'completed',
                'bg-blue-100 text-blue-600': task.status === 'in-progress',
                'bg-gray-100 text-gray-600': task.status === 'pending',
                'bg-red-100 text-red-600': task.status === 'stopped'
              }" class="px-2 py-1 rounded-full text-xs">
                {{ task.status === 'completed' ? '已完成' : task.status === 'in-progress' ? '进行中' : task.status === 'stopped' ? '已停止' : '未开始' }}
              </span>
            </div>
            <p class="text-sm text-gray-600">{{ task.description || '暂无描述' }}</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated } from 'vue'
import request from '@/api/request'

// 战争行动仪表板 - 专注于实战任务和核心工作

// 响应式数据
const projects = ref<any[]>([])
const tasks = ref<any[]>([])

// 加载数据
const loadData = async () => {
  try {
    console.log('【战争行动】开始加载数据...')
    // 加载战争行动类别的项目
    const projectRes = await request.get('/project/category/defense')
    if (projectRes.code === 200) {
      projects.value = projectRes.data || []
      console.log('【战争行动】加载项目数据:', projects.value.length, '个')
    }
    
    // 加载所有任务（后续根据项目ID过滤）
    const taskRes = await request.get('/task/list')
    if (taskRes.code === 200) {
      const allTasks = taskRes.data || []
      const projectIds = projects.value.map(p => p.id)
      tasks.value = allTasks.filter((t: any) => projectIds.includes(t.projectId))
      console.log('【战争行动】加载任务数据:', tasks.value.length, '个')
    }
  } catch (error) {
    console.error('【战争行动】加载数据失败:', error)
  }
}

// 计算属性
const defenseProjects = computed(() => {
  return projects.value
})

const defenseTasks = computed(() => {
  return tasks.value
})

// 获取项目名称
const getProjectName = (projectId: number) => {
  const project = projects.value.find(p => p.id === projectId)
  return project ? project.name : '未知项目'
}

// 计算紧急任务（已过期或5天内到期且未完成）
const urgentTasks = computed(() => {
  const now = new Date()
  const fiveDaysLater = new Date(now.getTime() + 5 * 24 * 60 * 60 * 1000)
  
  return tasks.value
    .filter(task => {
      // 排除已完成和已停止的任务
      if (task.status === 'completed' || task.status === 'stopped') return false
      
      // 检查截止时间
      if (!task.deadline) return false
      
      const deadline = new Date(task.deadline)
      // 已过期或在5天内到期
      return deadline <= fiveDaysLater
    })
    .map(task => {
      const deadline = new Date(task.deadline)
      const timeDiff = deadline.getTime() - now.getTime()
      const daysLeft = Math.ceil(timeDiff / (1000 * 60 * 60 * 24))
      
      return {
        ...task,
        daysLeft,
        isOverdue: deadline < now // 标记是否已过期
      }
    })
    .sort((a, b) => a.daysLeft - b.daysLeft) // 按剩余天数排序（过期的会排在最前面，因为是负数）
})

// 组件挂载时加载数据
onMounted(() => {
  loadData()
})

// 组件激活时重新加载数据（当从其他页面切换回来时）
onActivated(() => {
  console.log('【战争行动】页面被激活，刷新数据...')
  loadData()
})

// 获取任务状态样式
const getTaskStatusClass = (status: string) => {
  const classes = {
    completed: 'bg-green-50 border-green-200',
    inProgress: 'bg-blue-50 border-blue-200',
    pending: 'bg-gray-50 border-gray-200',
    cancelled: 'bg-red-50 border-red-200'
  }
  return classes[status as keyof typeof classes] || classes.pending
}

const getStatusBadgeClass = (status: string) => {
  const classes = {
    completed: 'bg-green-100 text-green-600',
    inProgress: 'bg-blue-100 text-blue-600',
    pending: 'bg-gray-100 text-gray-600',
    cancelled: 'bg-red-100 text-red-600'
  }
  return classes[status as keyof typeof classes] || classes.pending
}

const getStatusText = (status: string) => {
  const texts = {
    completed: '已完成',
    inProgress: '进行中',
    pending: '未开始',
    cancelled: '已取消'
  }
  return texts[status as keyof typeof texts] || '未知'
}
</script> 
