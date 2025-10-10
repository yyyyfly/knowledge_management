<template>
  <section id="actions-dashboard" class="p-6 animate-fade-in">
    <!-- 行动组概览 -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-2">行动组仪表盘</h1>
      <p class="text-gray-600">工程任务管理、进度跟踪、资源调配的指挥中心</p>
    </div>

    <!-- 核心指标卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
      <!-- 总任务数 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-xl font-semibold text-gray-900">总任务数</h3>
          <span class="bg-secondary/20 text-secondary px-3 py-1 rounded-full text-sm font-medium">任务管理</span>
        </div>
        <p class="text-gray-600 mb-4">所有工程任务的总数量</p>
        <div class="flex justify-between items-center">
          <div>
            <p class="text-gray-500 text-sm">进行中</p>
            <p class="text-2xl font-bold text-gray-900">{{ totalInProgressTasks }} 个任务</p>
          </div>
          <div class="text-secondary">
            <i class="fas fa-tasks text-2xl"></i>
          </div>
        </div>
      </div>

      <!-- 待办任务 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-xl font-semibold text-gray-900">待办任务</h3>
          <span class="bg-orange-100 text-orange-600 px-3 py-1 rounded-full text-sm font-medium">紧急</span>
        </div>
        <p class="text-gray-600 mb-4">需要立即处理的任务</p>
        <div class="flex justify-between items-center">
          <div>
            <p class="text-gray-500 text-sm">待处理</p>
            <p class="text-2xl font-bold text-gray-900">{{ totalPendingTasks }} 个任务</p>
          </div>
          <div class="text-orange-500">
            <i class="fas fa-exclamation-triangle text-2xl"></i>
          </div>
        </div>
      </div>

      <!-- 完成率 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-xl font-semibold text-gray-900">完成率</h3>
          <span class="bg-green-100 text-green-600 px-3 py-1 rounded-full text-sm font-medium">高效</span>
        </div>
        <p class="text-gray-600 mb-4">整体任务完成情况</p>
        <div class="flex justify-between items-center">
          <div>
            <p class="text-gray-500 text-sm">完成率</p>
            <p class="text-2xl font-bold text-gray-900">{{ completionRate }}%</p>
          </div>
          <div class="text-green-500">
            <i class="fas fa-check-circle text-2xl"></i>
          </div>
        </div>
      </div>

      <!-- 资源利用率 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-xl font-semibold text-gray-900">资源利用率</h3>
          <span class="bg-blue-100 text-blue-600 px-3 py-1 rounded-full text-sm font-medium">优化</span>
        </div>
        <p class="text-gray-600 mb-4">人力物力资源使用效率</p>
        <div class="flex justify-between items-center">
          <div>
            <p class="text-gray-500 text-sm">项目数</p>
            <p class="text-2xl font-bold text-gray-900">{{ allProjects.length }} 个</p>
          </div>
          <div class="text-blue-500">
            <i class="fas fa-chart-line text-2xl"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- 工程分类管理 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-6 mb-8">
      <!-- 战争行动工程 -->
      <div class="bg-white rounded-xl shadow-soft p-6 hover:shadow-lg transition-shadow cursor-pointer" @click="$router.push('/actions/defense-projects')">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-900">战争行动工程</h3>
          <span class="bg-red-100 text-red-600 px-2 py-1 rounded-full text-xs font-medium">技术攻关</span>
        </div>
        <p class="text-gray-600 mb-4 text-sm">技术突破、技能提升、能力建设</p>
        <div class="space-y-3 mb-4">
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">总任务数</span>
            <span class="text-sm font-semibold text-gray-900">{{ defenseTasks.length }} 个</span>
          </div>
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">待办任务</span>
            <span class="text-sm font-semibold text-orange-600">{{ defenseTasks.filter(t => t.status === 'pending').length }} 个</span>
          </div>
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">完成率</span>
            <span class="text-sm font-semibold text-green-600">{{ getCompletionRate(defenseTasks) }}%</span>
          </div>
        </div>
        <div class="flex items-center text-sm text-gray-500">
          <i class="fas fa-clock mr-1"></i>
          <span>最近更新: 2小时前</span>
        </div>
      </div>

      <!-- 工程建设管理 -->
      <div class="bg-white rounded-xl shadow-soft p-6 hover:shadow-lg transition-shadow cursor-pointer" @click="$router.push('/actions/construction-projects')">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-900">工程建设管理</h3>
          <span class="bg-blue-100 text-blue-600 px-2 py-1 rounded-full text-xs font-medium">系统构建</span>
        </div>
        <p class="text-gray-600 mb-4 text-sm">平台搭建、架构设计、基础设施</p>
        <div class="space-y-3 mb-4">
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">总任务数</span>
            <span class="text-sm font-semibold text-gray-900">{{ constructionTasks.length }} 个</span>
          </div>
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">待办任务</span>
            <span class="text-sm font-semibold text-orange-600">{{ constructionTasks.filter(t => t.status === 'pending').length }} 个</span>
          </div>
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">完成率</span>
            <span class="text-sm font-semibold text-green-600">{{ getCompletionRate(constructionTasks) }}%</span>
          </div>
        </div>
        <div class="flex items-center text-sm text-gray-500">
          <i class="fas fa-clock mr-1"></i>
          <span>最近更新: 1小时前</span>
        </div>
      </div>

      <!-- 外交行动协作 -->
      <div class="bg-white rounded-xl shadow-soft p-6 hover:shadow-lg transition-shadow cursor-pointer" @click="$router.push('/actions/diplomatic-projects')">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-gray-900">外交行动协作</h3>
          <span class="bg-green-100 text-green-600 px-2 py-1 rounded-full text-xs font-medium">团队协作</span>
        </div>
        <p class="text-gray-600 mb-4 text-sm">知识分享、资源整合、团队合作</p>
        <div class="space-y-3 mb-4">
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">总任务数</span>
            <span class="text-sm font-semibold text-gray-900">{{ diplomaticTasks.length }} 个</span>
          </div>
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">待办任务</span>
            <span class="text-sm font-semibold text-orange-600">{{ diplomaticTasks.filter(t => t.status === 'pending').length }} 个</span>
          </div>
          <div class="flex justify-between items-center">
            <span class="text-sm text-gray-600">完成率</span>
            <span class="text-sm font-semibold text-green-600">{{ getCompletionRate(diplomaticTasks) }}%</span>
          </div>
        </div>
        <div class="flex items-center text-sm text-gray-500">
          <i class="fas fa-clock mr-1"></i>
          <span>最近更新: 4小时前</span>
        </div>
      </div>
    </div>

    <!-- 任务进度概览 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-xl font-semibold text-gray-900">任务进度概览</h3>
        <div class="flex space-x-2">
          <button class="px-3 py-1 text-sm bg-secondary/20 text-secondary rounded-md">本周</button>
          <button class="px-3 py-1 text-sm bg-gray-100 hover:bg-gray-200 rounded-md">本月</button>
        </div>
      </div>
      <div class="h-64">
        <canvas ref="progressChartRef"></canvas>
      </div>
    </div>

    <!-- 紧急待办任务 -->
    <div class="bg-white rounded-xl shadow-soft p-6">
      <h3 class="text-xl font-semibold text-gray-900 mb-6">紧急待办任务</h3>
      <div v-if="urgentTasks.length > 0" class="space-y-4">
        <div v-for="task in urgentTasks" :key="task.id" class="flex items-center space-x-4 p-4 bg-red-50 rounded-lg border-l-4 border-red-500">
          <div class="w-10 h-10 bg-red-100 rounded-full flex items-center justify-center">
            <i class="fas fa-exclamation text-red-600"></i>
          </div>
          <div class="flex-1">
            <p class="text-sm font-medium text-gray-900">{{ task.name }}</p>
            <p class="text-xs text-gray-500">{{ task.description || '暂无描述' }}</p>
            <div class="flex items-center space-x-2 mt-1">
              <span class="bg-red-100 text-red-600 px-2 py-1 rounded-full text-xs">高优先级</span>
              <span v-if="task.tags" class="bg-blue-100 text-blue-600 px-2 py-1 rounded-full text-xs">{{ task.tags }}</span>
            </div>
          </div>
          <span class="text-xs text-gray-400">{{ formatDueDate(task.dueDate) }}</span>
            </div>
          </div>
      <div v-else class="text-center py-8 text-gray-500">
        <i class="fas fa-check-circle text-4xl mb-2"></i>
        <p>暂无紧急待办任务</p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import Chart from 'chart.js/auto'
import request from '@/api/request'

const progressChartRef = ref<HTMLCanvasElement>()

// 响应式数据
const allProjects = ref<any[]>([])
const allTasks = ref<any[]>([])
const loading = ref(true)

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    // 加载项目
    const projectRes = await request.get('/project/list')
    if (projectRes.code === 200) {
      allProjects.value = projectRes.data || []
    }

    // 加载任务
    const taskRes = await request.get('/task/list')
    if (taskRes.code === 200) {
      allTasks.value = taskRes.data || []
    }
  } catch (error) {
    console.error('加载数据失败:', error)
  } finally {
    loading.value = false
  }
}

// 计算各类项目
const defenseProjects = computed(() => allProjects.value.filter(p => p.category === 'defense'))
const constructionProjects = computed(() => allProjects.value.filter(p => p.category === 'construction'))
const diplomaticProjects = computed(() => allProjects.value.filter(p => p.category === 'diplomatic'))

// 计算各类任务
const defenseTasks = computed(() => allTasks.value.filter(t => defenseProjects.value.some(p => p.id === t.projectId)))
const constructionTasks = computed(() => allTasks.value.filter(t => constructionProjects.value.some(p => p.id === t.projectId)))
const diplomaticTasks = computed(() => allTasks.value.filter(t => diplomaticProjects.value.some(p => p.id === t.projectId)))

// 计算总数
const totalInProgressTasks = computed(() => allTasks.value.filter(t => t.status === 'inProgress').length)
const totalPendingTasks = computed(() => allTasks.value.filter(t => t.status === 'pending').length)
const totalCompletedTasks = computed(() => allTasks.value.filter(t => t.status === 'completed').length)
const completionRate = computed(() => {
  const total = allTasks.value.length
  return total > 0 ? Math.round((totalCompletedTasks.value / total) * 100) : 0
})

// 计算各类别完成率
const getCompletionRate = (tasks: any[]) => {
  const total = tasks.length
  const completed = tasks.filter(t => t.status === 'completed').length
  return total > 0 ? Math.round((completed / total) * 100) : 0
}

// 获取紧急待办任务（优先级高且未完成的）
const urgentTasks = computed(() => {
  return allTasks.value
    .filter(t => t.status !== 'completed' && t.priority === 'high')
    .sort((a, b) => new Date(a.dueDate || '').getTime() - new Date(b.dueDate || '').getTime())
    .slice(0, 3)
})

// 格式化日期
const formatDueDate = (dueDate: string) => {
  if (!dueDate) return '未设置截止日期'
  const date = new Date(dueDate)
  const now = new Date()
  const diff = Math.ceil((date.getTime() - now.getTime()) / (1000 * 60 * 60 * 24))
  if (diff < 0) return '已逾期'
  if (diff === 0) return '今天截止'
  if (diff === 1) return '明天截止'
  return `${diff}天后截止`
}

onMounted(async () => {
  // 先加载数据
  await loadData()

  // 然后初始化图表
  if (progressChartRef.value) {
    const ctx = progressChartRef.value.getContext('2d')
    if (ctx) {
      new Chart(ctx, {
        type: 'line',
        data: {
          labels: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
          datasets: [{
            label: '任务完成数',
            data: [8, 12, 15, 18, 22, 25, 28],
            borderColor: 'rgb(16, 185, 129)',
            backgroundColor: 'rgba(16, 185, 129, 0.1)',
            tension: 0.4,
            fill: true
          }, {
            label: '新增任务数',
            data: [5, 8, 6, 10, 7, 4, 3],
            borderColor: 'rgb(59, 130, 246)',
            backgroundColor: 'rgba(59, 130, 246, 0.1)',
            tension: 0.4,
            fill: true
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'top'
            }
          },
          scales: {
            y: {
              beginAtZero: true,
              grid: {
                color: 'rgba(0, 0, 0, 0.1)'
              }
            },
            x: {
              grid: {
                display: false
              }
            }
          }
        }
      })
    }
  }
})
</script> 