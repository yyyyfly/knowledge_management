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

    <!-- 项目复盘区域 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <div>
          <h3 class="text-xl font-semibold text-gray-900">项目复盘</h3>
          <p class="text-gray-600 mt-1 text-sm">查看项目的任务执行、心得总结和问题记录</p>
        </div>
        
        <!-- 状态筛选器 -->
        <div class="flex items-center space-x-2">
          <span class="text-sm text-gray-600">筛选：</span>
          <select v-model="statusFilter" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-red-500 focus:border-red-500">
            <option value="">全部状态</option>
            <option value="active">进行中</option>
            <option value="completed">已完成</option>
            <option value="planning">计划中</option>
          </select>
        </div>
      </div>

      <!-- 选中的项目展开区域 -->
      <transition name="project-lift" mode="out-in">
        <div v-if="selectedProject" :key="selectedProject.id" class="mb-8">
          <div class="rounded-2xl border-2 shadow-xl" :class="getProjectColorClasses(selectedProject.id)">
            <div class="flex items-center justify-between px-6 py-5">
              <div class="flex items-center gap-4">
                <div class="w-12 h-12 rounded-xl flex items-center justify-center flex-shrink-0" :class="getProjectIconClasses(selectedProject.id)">
                  <i class="fa-solid fa-box-archive text-white text-xl"></i>
                </div>
                <div class="min-w-0 flex-1">
                  <h3 class="text-xl font-bold text-gray-900 truncate">{{ selectedProject.name }}</h3>
                  <p class="text-sm text-gray-500 mt-1 line-clamp-3" :title="selectedProject.description">{{ selectedProject.description }}</p>
                </div>
              </div>
              <div class="flex items-center gap-3 flex-shrink-0">
                <span class="px-3 py-1 rounded-full text-sm font-medium" :class="getProjectStatusClasses(selectedProject.id)">
                  {{ selectedProject.status === 'active' ? '进行中' : selectedProject.status === 'completed' ? '已完成' : '计划中' }}
                </span>
                <button @click="deselectProject" class="text-gray-400 hover:text-gray-600 transition-colors duration-200">
                  <i class="fa-solid fa-times text-xl"></i>
                </button>
              </div>
            </div>

            <!-- 统计数据 -->
            <div class="px-6 pb-4">
              <div class="grid grid-cols-2 gap-4">
                <div class="bg-white/90 rounded-lg p-4">
                  <div class="flex items-center justify-between mb-2">
                    <span class="text-sm font-medium text-gray-700">任务完成度</span>
                    <span class="text-2xl font-bold text-blue-600">{{ getTaskStats(selectedProject.id).rate }}%</span>
                  </div>
                  <div class="text-xs text-gray-500">
                    已完成 {{ getTaskStats(selectedProject.id).completed }} / 总计 {{ getTaskStats(selectedProject.id).total }}
                  </div>
                </div>
                <div class="bg-white/90 rounded-lg p-4">
                  <div class="flex items-center justify-between mb-2">
                    <span class="text-sm font-medium text-gray-700">问题解决率</span>
                    <span class="text-2xl font-bold text-green-600">{{ getIssueStats(selectedProject.id).rate }}%</span>
                  </div>
                  <div class="text-xs text-gray-500">
                    已解决 {{ getIssueStats(selectedProject.id).resolved }} / 总计 {{ getIssueStats(selectedProject.id).total }}
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 任务列表 -->
            <div class="px-6 pb-6">
              <h4 class="text-md font-semibold text-gray-800 mb-3">项目任务</h4>
              <div v-if="getProjectTasks(selectedProject.id).length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3">
                <div v-for="task in getProjectTasks(selectedProject.id)" :key="task.id" class="bg-white/90 border rounded-lg p-3 shadow-sm hover:shadow-md transition-all duration-200" :class="getProjectBorderClasses(selectedProject.id)">
                  <div class="flex items-start justify-between mb-2">
                    <div class="font-medium text-gray-900 text-sm flex-1 min-w-0 mr-2">{{ task.name }}</div>
                    <span class="px-2 py-1 rounded-full text-xs font-medium flex-shrink-0" :class="{
                      'bg-emerald-100 text-emerald-700': task.status === 'completed',
                      'bg-blue-100 text-blue-700': task.status === 'in-progress',
                      'bg-gray-100 text-gray-700': task.status === 'pending',
                      'bg-red-100 text-red-700': task.status === 'stopped'
                    }">
                      {{ task.status === 'completed' ? '已完成' : task.status === 'in-progress' ? '进行中' : task.status === 'stopped' ? '已停止' : '未开始' }}
                    </span>
                  </div>
                  <div class="text-xs text-gray-500 mb-2 line-clamp-2">{{ task.description }}</div>
                  <div class="text-xs text-gray-400">创建：{{ task.createTime }}</div>
                  <div class="text-xs text-gray-400">截止：{{ task.deadline }}</div>
                </div>
              </div>
              <div v-else class="text-gray-400 text-sm">暂无任务</div>
            </div>

            <!-- 项目心得 -->
            <div class="px-6 pb-6">
              <h4 class="text-md font-semibold text-gray-800 mb-3">项目心得</h4>
              <div v-if="getProjectInsights(selectedProject.id).length > 0" class="space-y-3">
                <div 
                  v-for="record in getProjectInsights(selectedProject.id)" 
                  :key="record.id" 
                  class="bg-white/90 border rounded-lg p-3 shadow-sm hover:shadow-md transition-all duration-200 cursor-pointer group" 
                  :class="getProjectRecordBorderClasses(selectedProject.id)"
                  @click="showRecordDetail(record)"
                >
                  <div class="flex items-start justify-between mb-2">
                    <div class="font-medium text-gray-900 flex-1">{{ record.title }}</div>
                    <i class="fa-solid fa-chevron-right text-gray-400 group-hover:text-gray-600 transition-colors duration-200 text-sm"></i>
                  </div>
                  <div class="text-xs text-gray-500 mb-2 line-clamp-2">{{ record.summary }}</div>
                  <div class="text-xs text-gray-400 mb-2">更新时间：{{ record.updateTime }}</div>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="tag in record.tags" :key="tag" class="px-2 py-0.5 rounded-full text-xs" :class="getProjectTagClasses(selectedProject.id)">{{ tag }}</span>
                  </div>
                </div>
              </div>
              <div v-else class="text-gray-400 text-sm">暂无心得</div>
            </div>

            <!-- 问题记录 -->
            <div class="px-6 pb-6">
              <h4 class="text-md font-semibold text-gray-800 mb-3">问题记录</h4>
              <div v-if="getProjectIssues(selectedProject.id).length > 0" class="space-y-3">
                <div v-for="issue in getProjectIssues(selectedProject.id)" :key="issue.id" class="bg-white/90 border rounded-lg p-3 shadow-sm">
                  <div class="flex items-center justify-between mb-2">
                    <div class="font-medium text-gray-900 text-sm">{{ issue.issueTitle }}</div>
                    <div class="flex items-center space-x-2">
                      <span class="px-2 py-1 rounded text-xs font-medium" :class="{
                        'bg-red-100 text-red-700': issue.priority === 'urgent',
                        'bg-orange-100 text-orange-700': issue.priority === 'high',
                        'bg-yellow-100 text-yellow-700': issue.priority === 'medium',
                        'bg-gray-100 text-gray-700': issue.priority === 'low'
                      }">
                        {{ getPriorityText(issue.priority) }}
                      </span>
                      <span class="px-2 py-1 rounded text-xs font-medium" :class="{
                        'bg-gray-100 text-gray-700': issue.status === 'open',
                        'bg-blue-100 text-blue-700': issue.status === 'in_progress',
                        'bg-green-100 text-green-700': issue.status === 'resolved'
                      }">
                        {{ getIssueStatusText(issue.status) }}
                      </span>
                    </div>
                  </div>
                  <div class="text-xs text-gray-600 mb-2">{{ issue.issueDescription }}</div>
                  <div v-if="issue.solution" class="text-xs text-green-700 bg-green-50 rounded p-2 mt-2">
                    <span class="font-medium">解决方案：</span>{{ issue.solution }}
                  </div>
                  <div class="text-xs text-gray-400 mt-2">
                    {{ getIssueTypeText(issue.issueType) }} · {{ formatDate(issue.recCreateTime) }}
                  </div>
                </div>
              </div>
              <div v-else class="text-gray-400 text-sm">暂无问题记录</div>
            </div>
          </div>
        </div>
      </transition>

      <!-- 项目网格 -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <transition-group name="grid-item" tag="div" class="contents">
          <div 
            v-for="project in visibleProjects" 
            :key="project.id" 
            class="rounded-xl border shadow-lg hover:shadow-xl transition-all duration-500 cursor-pointer group transform hover:scale-105"
            :class="getProjectColorClasses(project.id)"
            @click="selectProject(project)"
          >
            <div class="p-4 h-full flex flex-col">
              <div class="flex items-start gap-3 mb-3">
                <div class="w-10 h-10 rounded-lg flex items-center justify-center flex-shrink-0" :class="getProjectIconClasses(project.id)">
                  <i class="fa-solid fa-box-archive text-white text-sm"></i>
                </div>
                <div class="min-w-0 flex-1">
                  <h3 class="text-sm font-bold text-gray-900 group-hover:text-gray-800 transition-colors duration-200 truncate">{{ project.name }}</h3>
                  <p class="text-xs text-gray-500 mt-1 line-clamp-2">{{ project.description }}</p>
                </div>
              </div>
              
              <div class="flex items-center justify-between mt-auto">
                <span class="px-2 py-1 rounded-full text-xs font-medium" :class="getProjectStatusClasses(project.id)">
                  {{ project.status === 'active' ? '进行中' : project.status === 'completed' ? '已完成' : '计划中' }}
                </span>
                <div class="text-xs text-gray-400">
                  {{ getProjectTasks(project.id).length }} 任务 · {{ getProjectInsights(project.id).length }} 心得
                </div>
              </div>
            </div>
          </div>
        </transition-group>
      </div>

      <!-- 空状态 -->
      <div v-if="filteredProjects.length === 0" class="text-center py-12 text-gray-500">
        <i class="fas fa-folder-open text-6xl mb-4 text-gray-300"></i>
        <p class="text-lg font-medium mb-2">暂无匹配的项目</p>
        <p class="text-sm">尝试调整筛选条件或创建新项目</p>
      </div>
    </div>

    <!-- 心得详情模态框 -->
    <transition name="modal-fade">
      <div v-if="selectedRecord" class="fixed inset-0 z-50 overflow-y-auto">
        <div class="flex items-center justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0">
          <!-- 背景遮罩 -->
          <div class="fixed inset-0 transition-opacity bg-gray-500 bg-opacity-75" @click="closeRecordDetail"></div>

          <!-- 模态框内容 -->
          <div class="inline-block w-full max-w-4xl px-4 pt-5 pb-4 overflow-hidden text-left align-bottom transition-all transform bg-white rounded-lg shadow-xl sm:my-8 sm:align-middle sm:p-6">
            <div class="absolute top-0 right-0 pt-4 pr-4">
              <button @click="closeRecordDetail" class="text-gray-400 hover:text-gray-600 transition-colors duration-200">
                <i class="fa-solid fa-times text-xl"></i>
              </button>
            </div>
            
            <div class="sm:flex sm:items-start">
              <div class="w-full">
                <!-- 标题和基本信息 -->
                <div class="mb-6">
                  <h3 class="text-2xl font-bold text-gray-900 mb-2">{{ selectedRecord.title }}</h3>
                  <div class="flex items-center gap-4 text-sm text-gray-500 mb-4">
                    <span>更新时间：{{ selectedRecord.updateTime }}</span>
                    <span>项目：{{ selectedRecord.projectName || '未知项目' }}</span>
                  </div>
                  <div class="flex flex-wrap gap-2">
                    <span v-for="tag in selectedRecord.tags" :key="tag" class="px-3 py-1 rounded-full text-sm font-medium" :class="getProjectTagClasses(selectedRecord.projectId || 0)">{{ tag }}</span>
                  </div>
                </div>

                <!-- 摘要 -->
                <div class="mb-6">
                  <h4 class="text-lg font-semibold text-gray-800 mb-2">摘要</h4>
                  <p class="text-gray-600 leading-relaxed">{{ selectedRecord.summary }}</p>
                </div>

                <!-- 详细内容 -->
                <div class="mb-6">
                  <h4 class="text-lg font-semibold text-gray-800 mb-2">详细内容</h4>
                  <div class="bg-gray-50 rounded-lg p-4">
                    <div class="prose max-w-none">
                      <div v-html="selectedRecord.content" class="text-gray-700 leading-relaxed"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </section>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated } from 'vue'
import request from '@/api/request'

// 战争行动仪表板 - 专注于实战任务和核心工作

// 响应式数据
const projects = ref<any[]>([])
const tasks = ref<any[]>([])
const projectRecords = ref<any[]>([])
const issues = ref<any[]>([])
const statusFilter = ref<string>('')
const selectedProject = ref<any>(null)
const selectedRecord = ref<any>(null)

// 加载项目心得数据
const loadProjectInsights = async () => {
  try {
    const response = await request.get('/project/insight/list')
    if (response.code === 200) {
      const allRecords = response.data || []
      const projectIds = projects.value.map(p => p.id)
      projectRecords.value = allRecords
        .filter((r: any) => projectIds.includes(r.projectId))
        .map((item: any) => ({
          ...item,
          tags: item.tags ? item.tags.split(',').filter((t: string) => t.trim()) : []
        }))
      console.log('【战争行动】加载项目心得数据:', projectRecords.value.length, '个')
    }
  } catch (error) {
    console.error('【战争行动】加载项目心得失败:', error)
  }
}

// 加载问题记录数据
const loadIssues = async () => {
  try {
    const response = await request.get('/project/issue/list')
    if (response.code === 200) {
      const allIssues = response.data || []
      const projectIds = projects.value.map(p => p.id)
      issues.value = allIssues.filter((i: any) => projectIds.includes(i.projectId))
      console.log('【战争行动】加载问题记录数据:', issues.value.length, '个')
    }
  } catch (error) {
    console.error('【战争行动】加载问题记录失败:', error)
  }
}

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
    
    // 加载项目心得和问题记录
    await loadProjectInsights()
    await loadIssues()
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

// 根据状态筛选项目
const filteredProjects = computed(() => {
  if (!statusFilter.value) return defenseProjects.value
  return defenseProjects.value.filter(p => p.status === statusFilter.value)
})

// 展示的项目列表（排除已选中的）
const visibleProjects = computed(() => {
  if (selectedProject.value) {
    return filteredProjects.value.filter(p => p.id !== selectedProject.value.id)
  }
  return filteredProjects.value
})

// 获取项目任务
const getProjectTasks = (projectId: number) => {
  return tasks.value.filter(task => task.projectId === projectId)
}

// 获取项目心得
const getProjectInsights = (projectId: number) => {
  return projectRecords.value.filter(record => record.projectId === projectId)
}

// 获取项目问题
const getProjectIssues = (projectId: number) => {
  return issues.value.filter(issue => issue.projectId === projectId)
}

// 计算任务统计
const getTaskStats = (projectId: number) => {
  const projectTasks = getProjectTasks(projectId)
  const completedTasks = projectTasks.filter(t => t.status === 'completed')
  return {
    total: projectTasks.length,
    completed: completedTasks.length,
    rate: projectTasks.length > 0 ? Math.round((completedTasks.length / projectTasks.length) * 100) : 0
  }
}

// 计算问题统计
const getIssueStats = (projectId: number) => {
  const projectIssues = getProjectIssues(projectId)
  const resolvedIssues = projectIssues.filter(i => i.status === 'resolved')
  return {
    total: projectIssues.length,
    resolved: resolvedIssues.length,
    rate: projectIssues.length > 0 ? Math.round((resolvedIssues.length / projectIssues.length) * 100) : 0
  }
}

// 获取项目名称
const getProjectName = (projectId: number) => {
  const project = projects.value.find(p => p.id === projectId)
  return project ? project.name : '未知项目'
}

// 颜色主题配置
const colorThemes = [
  {
    bg: 'bg-gradient-to-br from-blue-50 to-indigo-50',
    border: 'border-blue-300',
    icon: 'bg-gradient-to-br from-blue-500 to-indigo-600',
    status: 'bg-blue-100 text-blue-700',
    taskBorder: 'border-blue-100',
    recordBorder: 'border-purple-100',
    tag: 'bg-purple-100 text-purple-700'
  },
  {
    bg: 'bg-gradient-to-br from-emerald-50 to-teal-50',
    border: 'border-emerald-300',
    icon: 'bg-gradient-to-br from-emerald-500 to-teal-600',
    status: 'bg-emerald-100 text-emerald-700',
    taskBorder: 'border-emerald-100',
    recordBorder: 'border-teal-100',
    tag: 'bg-teal-100 text-teal-700'
  },
  {
    bg: 'bg-gradient-to-br from-purple-50 to-violet-50',
    border: 'border-purple-300',
    icon: 'bg-gradient-to-br from-purple-500 to-violet-600',
    status: 'bg-purple-100 text-purple-700',
    taskBorder: 'border-purple-100',
    recordBorder: 'border-violet-100',
    tag: 'bg-violet-100 text-violet-700'
  },
  {
    bg: 'bg-gradient-to-br from-amber-50 to-orange-50',
    border: 'border-amber-300',
    icon: 'bg-gradient-to-br from-amber-500 to-orange-600',
    status: 'bg-amber-100 text-amber-700',
    taskBorder: 'border-amber-100',
    recordBorder: 'border-orange-100',
    tag: 'bg-orange-100 text-orange-700'
  },
  {
    bg: 'bg-gradient-to-br from-rose-50 to-pink-50',
    border: 'border-rose-300',
    icon: 'bg-gradient-to-br from-rose-500 to-pink-600',
    status: 'bg-rose-100 text-rose-700',
    taskBorder: 'border-rose-100',
    recordBorder: 'border-pink-100',
    tag: 'bg-pink-100 text-pink-700'
  },
  {
    bg: 'bg-gradient-to-br from-cyan-50 to-sky-50',
    border: 'border-cyan-300',
    icon: 'bg-gradient-to-br from-cyan-500 to-sky-600',
    status: 'bg-cyan-100 text-cyan-700',
    taskBorder: 'border-cyan-100',
    recordBorder: 'border-sky-100',
    tag: 'bg-sky-100 text-sky-700'
  }
]

// 根据项目ID获取颜色主题
const getProjectTheme = (projectId: number) => {
  const index = projectId % colorThemes.length
  return colorThemes[index]
}

// 获取项目颜色类
const getProjectColorClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return `${theme.bg} ${theme.border}`
}

// 获取项目图标类
const getProjectIconClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.icon
}

// 获取项目状态类
const getProjectStatusClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.status
}

// 获取项目边框类
const getProjectBorderClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.taskBorder
}

// 获取项目记录边框类
const getProjectRecordBorderClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.recordBorder
}

// 获取项目标签类
const getProjectTagClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.tag
}

// 选择项目
const selectProject = (project: any) => {
  selectedProject.value = project
}

// 取消选择项目
const deselectProject = () => {
  selectedProject.value = null
}

// 显示心得详情
const showRecordDetail = (record: any) => {
  selectedRecord.value = {
    ...record,
    projectName: getProjectName(record.projectId)
  }
}

// 关闭心得详情
const closeRecordDetail = () => {
  selectedRecord.value = null
}

// 获取问题优先级文本
const getPriorityText = (priority: string) => {
  const texts: Record<string, string> = {
    urgent: '紧急',
    high: '高',
    medium: '中',
    low: '低'
  }
  return texts[priority] || priority
}

// 获取问题类型文本
const getIssueTypeText = (type: string) => {
  const texts: Record<string, string> = {
    technical: '技术问题',
    requirement: '需求问题',
    design: '设计问题',
    other: '其他问题'
  }
  return texts[type] || type
}

// 获取问题状态文本
const getIssueStatusText = (status: string) => {
  const texts: Record<string, string> = {
    open: '待处理',
    in_progress: '处理中',
    resolved: '已解决',
    closed: '已关闭'
  }
  return texts[status] || status
}

// 格式化日期
const formatDate = (date: any) => {
  if (!date) return ''
  const d = new Date(date)
  return d.toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
}

// 组件挂载时加载数据
onMounted(() => {
  loadData()
})

// 组件激活时重新加载数据（当从其他页面切换回来时）
onActivated(() => {
  console.log('【战争行动】页面被激活，刷新数据...')
  loadData()
})
</script>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.project-lift-enter-active,
.project-lift-leave-active {
  transition: all 0.3s ease;
}

.project-lift-enter-from,
.project-lift-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

.grid-item-enter-active,
.grid-item-leave-active {
  transition: all 0.3s ease;
}

.grid-item-enter-from,
.grid-item-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-slide-enter-active,
.modal-slide-leave-active {
  transition: all 0.3s ease;
}

.modal-slide-enter-from {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

.modal-slide-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}
</style> 
