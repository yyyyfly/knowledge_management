<template>
  <section id="system-decisions" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">系统决策</h1>
        <span class="bg-purple-100 text-purple-600 px-3 py-1 rounded-full text-sm font-medium">项目管理</span>
      </div>
      <p class="text-gray-600">管理项目结构和任务分配，为项目执行提供决策支持</p>
    </div>

    <!-- 项目管理界面 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8 max-h-[80vh] flex flex-col">
      <div class="flex items-center justify-between mb-6 flex-shrink-0">
        <h3 class="text-xl font-semibold text-gray-900">项目管理</h3>
        <div class="flex space-x-2">
          <button 
            @click="showCreateProject = true"
            class="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 flex items-center space-x-2"
          >
            <i class="fas fa-plus"></i>
            <span>新建项目</span>
          </button>
        </div>
      </div>
      
      <!-- 项目搜索和分类筛选 -->
      <div class="mb-6 flex-shrink-0">
        <div class="flex flex-col md:flex-row gap-4">
          <div class="flex-1">
            <input 
              v-model="projectSearchQuery"
              type="text" 
              placeholder="搜索项目名称或描述..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
            >
          </div>
          <div>
            <select v-model="projectFilterCategory" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
              <option value="">全部分类</option>
              <option value="defense">战争行动</option>
              <option value="construction">工程建设</option>
              <option value="diplomatic">外交行动</option>
            </select>
          </div>
          <div>
            <select v-model="projectFilterStatus" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
              <option value="">全部状态</option>
              <option value="planning">规划中</option>
              <option value="active">进行中</option>
              <option value="completed">已完成</option>
              <option value="paused">暂停</option>
            </select>
          </div>
        </div>
      </div>
      
      <!-- 项目列表 -->
      <div class="space-y-6 flex-1 overflow-y-auto">
        <div v-for="project in paginatedProjects" :key="project.id" class="border border-gray-200 rounded-lg overflow-hidden">
          <!-- 项目头部 -->
          <div class="bg-gray-50 px-6 py-4 border-b border-gray-200">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-3">
                <div class="w-8 h-8 rounded-lg flex items-center justify-center" :class="getCategoryClass(project.category)">
                  <i :class="getCategoryIcon(project.category)" class="text-white text-sm"></i>
                </div>
                <div>
                  <h4 class="text-lg font-semibold text-gray-900">{{ project.name }}</h4>
                  <div class="flex items-center space-x-2 mt-1">
                    <span :class="getProjectStatusClass(project.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                      {{ getProjectStatusText(project.status) }}
                    </span>
                    <span class="text-xs text-gray-500 bg-gray-200 px-2 py-1 rounded-full">
                      {{ getCategoryText(project.category) }}
                    </span>
                    <span class="text-sm text-gray-500">({{ getProjectTasks(project.id).length }} 个任务)</span>
                  </div>
                </div>
              </div>
              <div class="flex items-center space-x-2">
                <!-- 加入荣誉战绩按钮 - 只对已完成项目显示 -->
                <button 
                  v-if="project.status === 'completed' && !isProjectInHonors(project.id)"
                  @click="addProjectToHonors(project)" 
                  class="text-yellow-600 hover:text-yellow-800"
                  :title="'将项目加入荣誉战绩'"
                >
                  <i class="fas fa-trophy"></i>
                </button>
                <button @click="editProject(project)" class="text-blue-600 hover:text-blue-800">
                  <i class="fas fa-edit"></i>
                </button>
                <button @click="deleteProject(project.id)" class="text-red-600 hover:text-red-800">
                  <i class="fas fa-trash"></i>
                </button>
                <button 
                  @click="toggleProjectExpanded(project.id)"
                  class="text-gray-500 hover:text-gray-700"
                >
                  <i :class="projectExpanded.includes(project.id) ? 'fas fa-chevron-up' : 'fas fa-chevron-down'"></i>
                </button>
              </div>
            </div>
            <p class="text-sm text-gray-600 mt-2">{{ project.description }}</p>
            <div class="flex items-center space-x-4 text-sm text-gray-500 mt-2">
              <span>创建时间：{{ project.createTime }}</span>
            </div>
          </div>
          
          <!-- 项目任务列表 -->
          <Transition name="expand">
            <div v-if="projectExpanded.includes(project.id)" class="p-6">
              <!-- 任务管理头部 -->
              <div class="flex items-center justify-between mb-4">
                <h5 class="text-lg font-medium text-gray-900">任务管理</h5>
                <div class="flex space-x-2">
                  <button 
                    @click="addTaskToProject(project.id)"
                    class="px-3 py-1 bg-blue-600 text-white text-sm rounded hover:bg-blue-700 flex items-center space-x-1"
                  >
                    <i class="fas fa-plus"></i>
                    <span>添加任务</span>
                  </button>
                </div>
              </div>
              
              <!-- 任务筛选 -->
              <div class="mb-4">
                <div class="flex flex-col md:flex-row gap-4">
                  <div class="flex-1">
                    <input 
                      v-model="taskSearchQuery"
                      type="text" 
                      placeholder="搜索任务名称或描述..."
                      class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-sm"
                    >
                  </div>
                  <div>
                    <select v-model="taskFilterStatus" class="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-sm">
                      <option value="">全部状态</option>
                      <option value="pending">待开始</option>
                      <option value="in-progress">进行中</option>
                      <option value="completed">已完成</option>
                    </select>
                  </div>
                </div>
              </div>
              
              <!-- 任务列表 -->
              <div class="space-y-3">
                <div v-for="task in getFilteredProjectTasks(project.id)" :key="task.id" class="p-3 border border-gray-200 rounded-lg hover:bg-gray-50">
                  <div class="flex items-start justify-between">
                    <div class="flex-1">
                      <div class="flex items-center space-x-3 mb-2">
                        <h6 class="text-md font-medium text-gray-900">{{ task.name }}</h6>
                        <span :class="getTaskStatusClass(task.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                          {{ getTaskStatusText(task.status) }}
                        </span>
                      </div>
                      <p class="text-sm text-gray-600 mb-2">{{ task.description }}</p>
                      <div class="flex items-center space-x-4 text-xs text-gray-500">
                        <span>创建时间：{{ task.createTime }}</span>
                        <span>截止时间：{{ task.deadline }}</span>
                      </div>
                    </div>
                    <div class="flex space-x-2">
                      <button @click="editTask(task)" class="text-blue-600 hover:text-blue-800">
                        <i class="fas fa-edit"></i>
                      </button>
                      <button @click="deleteTask(task.id)" class="text-red-600 hover:text-red-800">
                        <i class="fas fa-trash"></i>
                      </button>
                    </div>
                  </div>
                </div>
                
                <!-- 空状态 -->
                <div v-if="getFilteredProjectTasks(project.id).length === 0" class="text-center py-6 text-gray-500">
                  <i class="fas fa-tasks text-2xl mb-2"></i>
                  <p class="text-sm">该项目暂无任务</p>
                  <button 
                    @click="addTaskToProject(project.id)"
                    class="mt-2 px-3 py-1 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
                  >
                    添加第一个任务
                  </button>
                </div>
              </div>
            </div>
          </Transition>
        </div>

        <!-- 项目搜索空状态 -->
        <div v-if="paginatedProjects.length === 0" class="text-center py-12 text-gray-500">
          <i class="fas fa-search text-4xl mb-4"></i>
          <p class="text-lg font-medium mb-2">未找到匹配的项目</p>
          <p class="text-sm">请尝试调整搜索条件或创建新项目</p>
          <button 
            @click="showCreateProject = true"
            class="mt-4 px-6 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700"
          >
            创建新项目
          </button>
        </div>
      </div>
      
      <!-- 项目分页控制 -->
      <div v-if="projectTotalPages > 1" class="flex items-center justify-center space-x-4 mt-6 flex-shrink-0">
        <button 
          @click="prevProjectPage" 
          :disabled="projectCurrentPage === 1"
          class="px-4 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <i class="fas fa-chevron-left mr-1"></i>上一页
        </button>
        <span class="text-sm text-gray-600">{{ projectCurrentPage }} / {{ projectTotalPages }}</span>
        <button 
          @click="nextProjectPage" 
          :disabled="projectCurrentPage === projectTotalPages"
          class="px-4 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          下一页<i class="fas fa-chevron-right ml-1"></i>
        </button>
      </div>
    </div>

    <!-- 项目弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCreateProject" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-md mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">{{ editingProject ? '编辑项目' : '新建项目' }}</h3>
              <button 
                @click="closeProjectForm"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitProject" class="space-y-6">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目名称</label>
                <input 
                  v-model="projectForm.name" 
                  type="text" 
                  placeholder="请输入项目名称"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  required
                >
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目描述</label>
                <textarea 
                  v-model="projectForm.description" 
                  rows="4" 
                  placeholder="请输入项目描述..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  required
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目分类</label>
                <select v-model="projectForm.category" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500" required>
                  <option value="">请选择项目分类</option>
                  <option value="defense">战争行动</option>
                  <option value="construction">工程建设</option>
                  <option value="diplomatic">外交行动</option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目状态</label>
                <select v-model="projectForm.status" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
                  <option value="planning">规划中</option>
                  <option value="active">进行中</option>
                  <option value="completed">已完成</option>
                  <option value="paused">暂停</option>
                </select>
              </div>

              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeProjectForm"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700"
                >
                  {{ editingProject ? '更新项目' : '创建项目' }}
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 任务弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCreateTask" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-md mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">{{ editingTask ? '编辑任务' : '新建任务' }}</h3>
              <button 
                @click="closeTaskForm"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitTask" class="space-y-6">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">所属项目</label>
                <select v-model="taskForm.projectId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" required>
                  <option value="">请选择项目</option>
                  <option v-for="project in projects" :key="project.id" :value="project.id">
                    {{ project.name }}
                  </option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">任务名称</label>
                <input 
                  v-model="taskForm.name" 
                  type="text" 
                  placeholder="请输入任务名称"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  required
                >
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">任务描述</label>
                <textarea 
                  v-model="taskForm.description" 
                  rows="4" 
                  placeholder="请输入任务描述..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  required
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">截止时间</label>
                <input 
                  v-model="taskForm.deadline" 
                  type="datetime-local"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  required
                >
              </div>

              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeTaskForm"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                  {{ editingTask ? '更新任务' : '创建任务' }}
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch, onMounted, onUnmounted } from 'vue'
import request from '@/api/request'

// 系统决策仪表板 - 专注于项目管理和决策制定

// 响应式数据
const projects = ref<any[]>([])
const tasks = ref<any[]>([])

// 加载数据
const loadData = async () => {
  try {
    // 加载项目
    const projectRes = await request.get('/project/list')
    if (projectRes.code === 200) {
      projects.value = projectRes.data || []
    }
    
    // 加载任务
    const taskRes = await request.get('/task/list')
    if (taskRes.code === 200) {
      tasks.value = taskRes.data || []
    }
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

// 定时器引用
let statusUpdateTimer: number | null = null

// 翻页和排序相关
const currentPage = ref(1)
const pageSize = 4

// 响应式数据
const showCreateProject = ref(false)
const showCreateTask = ref(false)

// 编辑状态
const editingProject = ref<any>(null)
const editingTask = ref<any>(null)

// 搜索和筛选
const taskSearchQuery = ref('')
const taskFilterStatus = ref('')
const projectSearchQuery = ref('')
const projectFilterStatus = ref('')
const projectFilterCategory = ref('')

// 项目展开状态
const projectExpanded = ref<number[]>([])

// 初始化项目展开状态
onMounted(async () => {
  await loadData()
  projectExpanded.value = projects.value.map(project => project.id)
})

// 项目表单
const projectForm = reactive({
  name: '',
  description: '',
  status: 'planning',
  category: ''
})

// 任务表单
const taskForm = reactive({
  projectId: '',
  name: '',
  description: '',
  deadline: '',
  status: 'pending'
})

// 项目分页相关
const projectPageSize = 5
const projectCurrentPage = ref(1)

// 项目分页计算属性
const projectTotalPages = computed(() => Math.ceil(filteredProjects.value.length / projectPageSize))

const paginatedProjects = computed(() => {
  const start = (projectCurrentPage.value - 1) * projectPageSize
  const end = start + projectPageSize
  return filteredProjects.value.slice(start, end)
})

// 项目翻页方法
const prevProjectPage = () => {
  if (projectCurrentPage.value > 1) {
    projectCurrentPage.value--
  }
}

const nextProjectPage = () => {
  if (projectCurrentPage.value < projectTotalPages.value) {
    projectCurrentPage.value++
  }
}

// 监听项目筛选变化，重置到第一页
watch([projectSearchQuery, projectFilterCategory, projectFilterStatus], () => {
  projectCurrentPage.value = 1
})

// 获取项目任务
const getProjectTasks = (projectId: number) => {
  return tasks.value.filter(task => task.projectId === projectId)
}

// 获取过滤后的项目任务
const getFilteredProjectTasks = (projectId: number) => {
  let projectTasks = getProjectTasks(projectId)
  
  // 按状态筛选
  if (taskFilterStatus.value) {
    projectTasks = projectTasks.filter(task => task.status === taskFilterStatus.value)
  }
  
  // 按搜索关键词筛选
  if (taskSearchQuery.value) {
    const query = taskSearchQuery.value.toLowerCase()
    projectTasks = projectTasks.filter(task => 
      task.name.toLowerCase().includes(query) ||
      task.description.toLowerCase().includes(query)
    )
  }
  
  return projectTasks
}

// 获取项目名称
const getProjectName = (projectId: number) => {
  const project = projects.value.find(p => p.id === projectId)
  return project ? project.name : '未知项目'
}

// 获取分类样式
const getCategoryClass = (category: string) => {
  const classes = {
    defense: 'bg-red-500',
    construction: 'bg-blue-500',
    diplomatic: 'bg-green-500'
  }
  return classes[category as keyof typeof classes] || 'bg-gray-500'
}

// 获取分类图标
const getCategoryIcon = (category: string) => {
  const icons = {
    defense: 'fa-solid fa-crosshairs',
    construction: 'fa-solid fa-building',
    diplomatic: 'fa-solid fa-handshake'
  }
  return icons[category as keyof typeof icons] || 'fa-solid fa-folder'
}

// 获取分类文本
const getCategoryText = (category: string) => {
  const texts = {
    defense: '战争行动',
    construction: '工程建设',
    diplomatic: '外交行动'
  }
  return texts[category as keyof typeof texts] || '未分类'
}

// 获取项目状态样式
const getProjectStatusClass = (status: string) => {
  const classes = {
    planning: 'bg-yellow-100 text-yellow-600',
    active: 'bg-green-100 text-green-600',
    completed: 'bg-blue-100 text-blue-600',
    paused: 'bg-gray-100 text-gray-600'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

// 获取项目状态文本
const getProjectStatusText = (status: string) => {
  const texts = {
    planning: '规划中',
    active: '进行中',
    completed: '已完成',
    paused: '暂停'
  }
  return texts[status as keyof typeof texts] || '未知'
}

// 获取任务状态样式
const getTaskStatusClass = (status: string) => {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-600',
    'in-progress': 'bg-blue-100 text-blue-600',
    completed: 'bg-green-100 text-green-600'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

// 获取任务状态文本
const getTaskStatusText = (status: string) => {
  const texts = {
    pending: '待开始',
    'in-progress': '进行中',
    completed: '已完成'
  }
  return texts[status as keyof typeof texts] || '未知'
}

// 编辑项目
const editProject = (project: any) => {
  editingProject.value = project
  projectForm.name = project.name
  projectForm.description = project.description
  projectForm.status = project.status
  projectForm.category = project.category
  showCreateProject.value = true
}

// 删除项目
const deleteProject = async (id: number) => {
  if (confirm('确定要删除这个项目吗？相关的任务也会被删除。')) {
    try {
      const res = await request.delete(`/project/${id}`)
      if (res.code === 200) {
        alert('项目已删除！')
        loadData()
      }
    } catch (error) {
      console.error('删除项目失败:', error)
      alert('删除失败，请重试')
    }
  }
}

// 提交项目
const submitProject = async () => {
  try {
    const projectData = {
      name: projectForm.name,
      description: projectForm.description,
      status: projectForm.status,
      category: projectForm.category
    }
    
    if (editingProject.value) {
      // 更新项目
      const res = await request.put('/project', { ...projectData, id: editingProject.value.id })
      if (res.code === 200) {
        alert('项目已更新！')
        loadData()
      }
    } else {
      // 创建新项目
      const res = await request.post('/project', projectData)
      if (res.code === 200) {
        alert('项目已创建！')
        loadData()
      }
    }
    closeProjectForm()
  } catch (error) {
    console.error('保存项目失败:', error)
    alert('操作失败，请重试')
  }
}

// 关闭项目表单
const closeProjectForm = () => {
  showCreateProject.value = false
  editingProject.value = null
  projectForm.name = ''
  projectForm.description = ''
  projectForm.status = 'planning'
  projectForm.category = ''
}

// 编辑任务
const editTask = (task: any) => {
  editingTask.value = task
  taskForm.projectId = task.projectId.toString()
  taskForm.name = task.name
  taskForm.description = task.description
  // 转换日期格式：从后端格式 (yyyy-MM-dd HH:mm:ss) 到 datetime-local (yyyy-MM-ddTHH:mm)
  if (task.deadline) {
    // 将 "2025-10-07 14:30:00" 转换为 "2025-10-07T14:30"
    taskForm.deadline = task.deadline.substring(0, 16).replace(' ', 'T')
  } else {
    taskForm.deadline = ''
  }
  // 不设置状态，状态由执行页面管理
  showCreateTask.value = true
}

// 删除任务
const deleteTask = async (id: number) => {
  if (confirm('确定要删除这个任务吗？')) {
    try {
      const res = await request.delete(`/task/${id}`)
      if (res.code === 200) {
        alert('任务已删除！')
        loadData()
      }
    } catch (error) {
      console.error('删除任务失败:', error)
      alert('删除失败，请重试')
    }
  }
}

// 提交任务
const submitTask = async () => {
  try {
    // 转换日期格式：从 datetime-local (yyyy-MM-ddTHH:mm) 到后端格式 (yyyy-MM-dd HH:mm:ss)
    let formattedDeadline = taskForm.deadline
    if (formattedDeadline) {
      // 将 "2025-10-07T14:30" 转换为 "2025-10-07 14:30:00"
      formattedDeadline = formattedDeadline.replace('T', ' ') + ':00'
    }
    
    const taskData = {
      projectId: parseInt(taskForm.projectId),
      name: taskForm.name,
      description: taskForm.description,
      deadline: formattedDeadline,
      status: taskForm.status || 'pending'
    }
    
    if (editingTask.value) {
      // 更新任务
      const res = await request.put('/task', { ...taskData, id: editingTask.value.id })
      if (res.code === 200) {
        alert('任务已更新！')
        loadData()
      }
    } else {
      // 创建新任务
      const res = await request.post('/task', taskData)
      if (res.code === 200) {
        alert('任务已创建！')
        loadData()
      }
    }
    closeTaskForm()
  } catch (error) {
    console.error('保存任务失败:', error)
    alert('操作失败，请重试')
  }
}

// 关闭任务表单
const closeTaskForm = () => {
  showCreateTask.value = false
  editingTask.value = null
  taskForm.projectId = ''
  taskForm.name = ''
  taskForm.description = ''
  taskForm.deadline = ''
  taskForm.status = 'pending'
}

// 添加任务到项目
const addTaskToProject = (projectId: number) => {
  taskForm.projectId = projectId.toString()
  showCreateTask.value = true
}

// 切换项目展开状态
const toggleProjectExpanded = (projectId: number) => {
  if (projectExpanded.value.includes(projectId)) {
    projectExpanded.value = projectExpanded.value.filter(id => id !== projectId)
  } else {
    projectExpanded.value.push(projectId)
  }
}

// 计算过滤后的项目列表
const filteredProjects = computed(() => {
  let filtered = projects.value
  
  // 按名称搜索
  if (projectSearchQuery.value) {
    const query = projectSearchQuery.value.toLowerCase()
    filtered = filtered.filter(project => 
      project.name.toLowerCase().includes(query) ||
      project.description.toLowerCase().includes(query)
    )
  }
  
  // 按状态筛选
  if (projectFilterStatus.value) {
    filtered = filtered.filter(project => project.status === projectFilterStatus.value)
  }
  
  // 按分类筛选
  if (projectFilterCategory.value) {
    filtered = filtered.filter(project => project.category === projectFilterCategory.value)
  }
  
  return filtered
})

// 将项目加入荣誉战绩
const addProjectToHonors = (project: any) => {
  if (confirm(`确定要将项目「${project.name}」加入荣誉战绩吗？`)) {
    try {
      const newHonor = addHonorFromProject(project.id, project.name, project.description, project.category)
      alert('项目已成功加入荣誉战绩！')
      console.log('新增荣誉战绩：', newHonor)
    } catch (error) {
      alert('加入荣誉战绩失败，请重试')
      console.error('加入荣誉战绩失败：', error)
    }
  }
}

// 组件卸载时清理定时器
onUnmounted(() => {
  if (statusUpdateTimer) {
    clearInterval(statusUpdateTimer)
  }
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

/* 弹窗渐入渐出效果 */
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

/* 弹窗内容滑入滑出效果 */
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

/* 项目展开收起动画 */
.expand-enter-active,
.expand-leave-active {
  transition: all 0.3s ease;
  overflow: hidden;
}

.expand-enter-from,
.expand-leave-to {
  opacity: 0;
  max-height: 0;
  padding-top: 0;
  padding-bottom: 0;
}

.expand-enter-to,
.expand-leave-from {
  opacity: 1;
  max-height: 1000px;
}

/* 自定义滚动条样式 */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f5f9;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}
</style> 