<template>
  <div class="p-6 bg-gray-50 min-h-screen">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-2">框架笔记</h1>
      <p class="text-gray-600">知识体系、结构梳理的统计概览</p>
    </div>

    <!-- 统计卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <!-- 总字数 -->
      <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">总字数</p>
            <p class="text-2xl font-bold text-gray-900">{{ totalWordCount }}</p>
          </div>
          <div class="p-3 bg-indigo-50 rounded-full">
            <i class="fas fa-font text-indigo-600 text-xl"></i>
          </div>
        </div>
        <div class="mt-4 text-xs text-gray-500">所有笔记内容总字数</div>
      </div>
      <!-- 平均字数 -->
      <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">平均字数</p>
            <p class="text-2xl font-bold text-gray-900">{{ averageWordCount }}</p>
          </div>
          <div class="p-3 bg-blue-50 rounded-full">
            <i class="fas fa-chart-line text-blue-600 text-xl"></i>
          </div>
        </div>
        <div class="mt-4 text-xs text-gray-500">单篇笔记平均字数</div>
      </div>
      <!-- 最近更新 -->
      <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">最近更新</p>
            <p class="text-2xl font-bold text-gray-900">{{ latestUpdate }}</p>
          </div>
          <div class="p-3 bg-green-50 rounded-full">
            <i class="fas fa-clock text-green-600 text-xl"></i>
          </div>
        </div>
        <div class="mt-4 text-xs text-gray-500">最新创建时间</div>
      </div>
      <!-- 阅读笔记 -->
      <div 
        @click="showNotesList = true"
        class="group relative cursor-pointer bg-gradient-to-br from-purple-50 to-blue-50 border border-purple-200 rounded-xl p-6 hover:shadow-lg hover:scale-[1.02] transition-all duration-300"
      >
        <!-- 背景装饰 -->
        <div class="absolute top-0 right-0 w-20 h-20 bg-gradient-to-br from-purple-100 to-transparent rounded-full -translate-y-10 translate-x-10 opacity-60"></div>
        
        <div class="relative z-10">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-lg font-semibold text-gray-900">阅读笔记</p>
              <p class="text-sm text-gray-600">浏览所有框架笔记</p>
            </div>
            <div class="p-3 bg-gradient-to-br from-purple-500 to-blue-600 rounded-full shadow-md group-hover:shadow-lg transition-shadow">
              <i class="fas fa-book-open text-white text-xl"></i>
            </div>
          </div>
          
          <!-- 点击提示 -->
          <div class="mt-3 text-center">
            <span class="inline-block bg-gradient-to-r from-purple-500 to-blue-600 text-white px-3 py-1.5 rounded-full text-sm font-medium shadow-sm">
              开始阅读
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- 框架概览 -->
    <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-xl font-semibold text-gray-900">框架概览</h2>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div class="text-center p-4 bg-blue-50 rounded-xl">
          <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-blue-600">{{ frameworkNotes.length }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">总框架数</p>
          <p class="text-xs text-gray-600">知识框架总数</p>
          <div class="mt-2 w-full bg-blue-200 rounded-full h-2">
            <div class="bg-blue-600 h-2 rounded-full" :style="{ width: Math.min((frameworkNotes.length / 20) * 100, 100) + '%' }"></div>
          </div>
        </div>
        <div class="text-center p-4 bg-green-50 rounded-xl">
          <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-green-600">{{ uniqueSubjects.length }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">学科数量</p>
          <p class="text-xs text-gray-600">覆盖学科数</p>
          <div class="mt-2 w-full bg-green-200 rounded-full h-2">
            <div class="bg-green-600 h-2 rounded-full" :style="{ width: Math.min((uniqueSubjects.length / 10) * 100, 100) + '%' }"></div>
          </div>
        </div>
        <div class="text-center p-4 bg-purple-50 rounded-xl">
          <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-purple-600">{{ uniqueKnowledgePoints.length }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">知识点数量</p>
          <p class="text-xs text-gray-600">覆盖知识点数</p>
          <div class="mt-2 w-full bg-purple-200 rounded-full h-2">
            <div class="bg-purple-600 h-2 rounded-full" :style="{ width: Math.min((uniqueKnowledgePoints.length / 30) * 100, 100) + '%' }"></div>
          </div>
        </div>
        <div class="text-center p-4 bg-orange-50 rounded-xl">
          <div class="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-orange-600">{{ thisMonthFrameworks.length }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">本月新增</p>
          <p class="text-xs text-gray-600">新增框架数</p>
          <div class="mt-2 w-full bg-orange-200 rounded-full h-2">
            <div class="bg-orange-600 h-2 rounded-full" :style="{ width: Math.min((thisMonthFrameworks.length / 5) * 100, 100) + '%' }"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
      <!-- 左侧：课程分类概览 -->
      <div class="lg:col-span-2">
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 h-full">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-xl font-semibold text-gray-900">学科分类概览</h2>
            <div class="flex items-center space-x-2 text-sm text-gray-500">
              <i class="fas fa-graduation-cap"></i>
              <span>{{ uniqueSubjects.length }} 个学科</span>
            </div>
          </div>
          
          <!-- 课程列表 -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 flex-1">
            <div 
              v-for="subject in paginatedSubjects" 
              :key="subject.subject"
              class="group relative overflow-hidden bg-gradient-to-br from-purple-50 to-blue-50 border border-purple-200 rounded-xl p-5 hover:shadow-lg hover:scale-[1.02] transition-all duration-300"
            >
              <!-- 背景装饰 -->
              <div class="absolute top-0 right-0 w-20 h-20 bg-gradient-to-br from-purple-100 to-transparent rounded-full -translate-y-10 translate-x-10 opacity-60"></div>
              
              <div class="relative z-10">
                <div class="flex items-center justify-between mb-3">
                  <div class="flex items-center space-x-3">
                    <div class="w-10 h-10 bg-gradient-to-br from-purple-500 to-blue-600 rounded-lg flex items-center justify-center shadow-md">
                      <i class="fas fa-graduation-cap text-white text-sm"></i>
                    </div>
                    <div>
                      <h3 class="font-semibold text-gray-900 text-lg group-hover:text-purple-700 transition-colors">{{ subject.subject }}</h3>
                      <p class="text-sm text-gray-600">{{ subject.count }} 个框架</p>
                    </div>
                  </div>
                </div>
                
                <!-- 进度条 -->
                <div class="mt-4">
                  <div class="flex items-center justify-between text-xs text-gray-600 mb-2">
                    <span>框架数量</span>
                    <span class="font-medium">{{ subject.count }}</span>
                  </div>
                  <div class="w-full bg-white/60 rounded-full h-2 overflow-hidden">
                    <div 
                      class="bg-gradient-to-r from-purple-500 to-blue-600 h-2 rounded-full transition-all duration-500 ease-out" 
                      :style="{ width: Math.min((subject.count / Math.max(...subjectStats.map(s => s.count))) * 100, 100) + '%' }"
                    ></div>
                  </div>
                </div>
                
                <!-- 占比显示 -->
                <div class="mt-3 text-center">
                  <span class="inline-block bg-white/80 text-purple-700 px-3 py-1 rounded-full text-xs font-medium shadow-sm">
                    占比 {{ Math.round((subject.count / frameworkNotes.length) * 100) }}%
                  </span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 分页控件 -->
          <div v-if="totalPages > 1" class="mt-6 flex items-center justify-center space-x-2">
            <button 
              @click="currentPage = Math.max(1, currentPage - 1)"
              :disabled="currentPage === 1"
              class="px-3 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <i class="fas fa-chevron-left"></i>
            </button>
            
            <div class="flex items-center space-x-1">
              <button 
                v-for="page in visiblePages" 
                :key="page"
                @click="currentPage = page"
                :class="[
                  'px-3 py-2 text-sm border rounded-lg',
                  currentPage === page 
                    ? 'bg-purple-600 text-white border-purple-600' 
                    : 'border-gray-300 hover:bg-gray-50'
                ]"
              >
                {{ page }}
              </button>
            </div>
            
            <button 
              @click="currentPage = Math.min(totalPages, currentPage + 1)"
              :disabled="currentPage === totalPages"
              class="px-3 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <i class="fas fa-chevron-right"></i>
            </button>
          </div>
        </div>
      </div>
      
      <!-- 右侧：最新笔记 -->
      <div class="lg:col-span-1">
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 h-full">
          <h2 class="text-xl font-semibold text-gray-900 mb-6">最新笔记</h2>
          <div class="space-y-4 flex-1">
            <div 
              v-for="note in latestNotes" 
              :key="note.id"
              class="p-3 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
            >
              <h4 class="font-medium text-gray-900 text-sm line-clamp-2 mb-2">{{ note.title }}</h4>
              <p class="text-xs text-gray-600 line-clamp-2 mb-2">{{ note.summary }}</p>
              <div class="flex items-center justify-between text-xs text-gray-500">
                <span>{{ formatDate(note.createTime) }}</span>
                <span class="bg-purple-100 text-purple-700 px-2 py-1 rounded">
                  {{ note.subjectType?.[0] || '未分类' }}
                </span>
              </div>
            </div>
            
            <div v-if="latestNotes.length === 0" class="text-center py-8 text-gray-500">
              <i class="fas fa-file-alt text-3xl mb-2 text-gray-300"></i>
              <p class="text-sm">暂无最新笔记</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 统计图表 -->
    <div class="mt-8 grid grid-cols-1 md:grid-cols-2 gap-6">
      <!-- 课程分布饼图卡片 -->
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
                  <h2 class="text-xl font-semibold text-gray-900 mb-6">学科分布</h2>
        <div class="relative h-64">
                      <canvas v-if="subjectStats.length > 0" ref="pieChartRef" class="w-full h-full"></canvas>
          <div v-else class="w-full h-full bg-gray-50 rounded-lg flex items-center justify-center">
            <div class="text-center text-gray-500">
              <i class="fas fa-chart-pie text-4xl mb-2"></i>
              <p class="text-sm">暂无课程数据</p>
              <p class="text-xs mt-1">添加框架笔记后显示分布</p>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 创建时间分布柱状图卡片 -->
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">创建时间分布</h2>
        <div class="relative h-64">
          <canvas v-if="frameworkNotes.length > 0" ref="barChartRef" class="w-full h-full"></canvas>
          <div v-else class="w-full h-full bg-gray-50 rounded-lg flex items-center justify-center">
            <div class="text-center text-gray-500">
              <i class="fas fa-chart-bar text-4xl mb-2"></i>
              <p class="text-sm">暂无框架数据</p>
              <p class="text-xs mt-1">添加框架笔记后显示分布</p>
            </div>
          </div>
        </div>
      </div>
    </div>


  </div>

  <!-- 笔记列表弹窗 -->
  <Transition name="modal-fade">
    <div v-if="showNotesList" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <Transition name="modal-slide">
        <div class="bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[90vh] flex flex-col">
          <!-- 弹窗头部 -->
          <div class="flex items-center justify-between p-6 border-b border-gray-200">
            <div class="flex items-center space-x-3">
              <div class="w-12 h-12 bg-gradient-to-br from-purple-500 to-blue-600 rounded-full flex items-center justify-center shadow-lg">
                <i class="fas fa-sitemap text-2xl text-white"></i>
              </div>
              <div>
                <h3 class="text-2xl font-semibold text-gray-900">框架笔记列表</h3>
                <p class="text-sm text-gray-500">{{ frameworkNotes.length }} 条笔记</p>
              </div>
            </div>
            <button 
              @click="showNotesList = false"
              class="text-gray-500 hover:text-gray-700 transition-colors p-2 hover:bg-gray-100 rounded-lg"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          
          <!-- 搜索和筛选 -->
          <div class="p-6 border-b border-gray-200">
            <div class="flex flex-col md:flex-row gap-4">
              <div class="flex-1">
                <div class="relative">
                  <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="搜索笔记标题或内容..."
                    class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all"
                  >
                </div>
              </div>
              <div>
                <select v-model="selectedSubject" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
                  <option value="">全部学科</option>
                  <option v-for="subject in uniqueSubjects" :key="subject" :value="subject">
                    {{ subject }}
                  </option>
                </select>
              </div>
            </div>
          </div>
          
          <!-- 笔记列表 -->
          <div class="flex-1 overflow-y-auto p-6">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              <div 
                v-for="note in filteredNotes" 
                :key="note.id"
                class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 hover:border-gray-300 cursor-pointer transition-all duration-200 group"
                @click="viewNoteDetail(note)"
              >
                <div class="mb-3">
                  <h5 class="text-sm font-medium text-gray-900 line-clamp-2 group-hover:text-purple-600 transition-colors">{{ note.title }}</h5>
                </div>
                <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
                <div class="flex items-center justify-between text-xs text-gray-500 mb-2">
                  <span>{{ formatDate(note.createTime) }}</span>
                </div>
                <div class="flex flex-wrap gap-1">
                  <span v-for="subject in note.subjectType?.slice(0, 1)" :key="subject" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                    {{ subject }}
                  </span>
                  <span v-for="knowledge in note.knowledgePoint?.slice(0, 2)" :key="knowledge" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                    {{ knowledge }}
                  </span>
                  <span v-if="(note.subjectType?.length || 0) + (note.knowledgePoint?.length || 0) > 3" class="text-xs text-gray-500 px-2 py-1">
                    +{{ ((note.subjectType?.length || 0) + (note.knowledgePoint?.length || 0)) - 3 }}
                  </span>
                </div>
              </div>
              
              <!-- 空状态 -->
              <div v-if="filteredNotes.length === 0" class="col-span-full text-center py-16 text-gray-500">
                <i class="fas fa-search text-5xl mb-4 text-gray-300"></i>
                <p class="text-lg font-medium mb-2">未找到匹配的笔记</p>
                <p class="text-sm">请尝试调整搜索条件</p>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </div>
  </Transition>

  <!-- 笔记详情弹窗 -->
  <Transition name="modal-fade">
    <div v-if="showNoteDetail" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <Transition name="modal-slide">
        <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-xl font-semibold text-gray-900">笔记详情</h3>
            <button 
              @click="showNoteDetail = false"
              class="text-gray-500 hover:text-gray-700 p-2 hover:bg-gray-100 rounded-lg transition-colors"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          
          <div v-if="selectedNote" class="space-y-4">
            <div class="flex items-center space-x-3 mb-4">
              <h4 class="text-lg font-medium text-gray-900">{{ selectedNote.title }}</h4>
            </div>
            
            <div class="bg-gray-50 p-4 rounded-lg">
              <p class="text-sm font-medium text-gray-700 mb-2">摘要：</p>
              <p class="text-sm text-gray-600">{{ selectedNote.summary }}</p>
            </div>
            
            <div>
              <p class="text-sm font-medium text-gray-700 mb-2">详细内容：</p>
              <div class="text-sm text-gray-600 prose prose-sm max-w-none" v-html="selectedNote.content"></div>
            </div>
            
            <div class="flex items-center space-x-4 text-sm text-gray-500">
              <span>创建时间：{{ formatDate(selectedNote.createTime) }}</span>
            </div>
            
            <!-- 标签区域 -->
            <div class="space-y-2">
              <div v-if="selectedNote.subjectType && selectedNote.subjectType.length > 0">
                <span class="text-sm font-medium text-gray-700">学科类型：</span>
                <div class="flex flex-wrap gap-1 mt-1">
                  <span v-for="subject in selectedNote.subjectType" :key="subject" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                    {{ subject }}
                  </span>
                </div>
              </div>
              <div v-if="selectedNote.knowledgePoint && selectedNote.knowledgePoint.length > 0">
                <span class="text-sm font-medium text-gray-700">知识点类型：</span>
                <div class="flex flex-wrap gap-1 mt-1">
                  <span v-for="knowledge in selectedNote.knowledgePoint" :key="knowledge" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                    {{ knowledge }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, nextTick, watch, onUnmounted } from 'vue'
import dayjs from 'dayjs'
import { getAllNotes } from '@/services/noteService'

// 响应式笔记数据
const allNotesData = ref<any[]>([])

// 加载所有笔记数据
const loadNotes = async () => {
  try {
    allNotesData.value = await getAllNotes()
  } catch (error) {
    console.error('加载框架笔记失败:', error)
  }
}
import {
  Chart,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  DoughnutController
} from 'chart.js'

Chart.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  DoughnutController
)

const frameworkNotes = computed(() => allNotesData.value.filter(n => n.type === 'framework'))

const topSubjects = computed(() => {
  const countMap: Record<string, number> = {}
  frameworkNotes.value.forEach(note => {
    (note.subjectType || []).forEach(sub => {
      countMap[sub] = (countMap[sub] || 0) + 1
    })
  })
  return Object.entries(countMap)
    .map(([subject, count]) => ({ subject, count }))
    .sort((a, b) => b.count - a.count)
    .slice(0, 3)
})

const latestUpdate = computed(() => {
  if (!frameworkNotes.value.length) return '暂无数据'
  const latest = frameworkNotes.value.reduce((max, n) => n.createTime > max ? n.createTime : max, frameworkNotes.value[0].createTime)
  return dayjs(latest).format('YYYY-MM-DD')
})

const tagCount = computed(() => {
  const tags = new Set<string>()
  frameworkNotes.value.forEach(note => (note.tags || []).forEach(tag => tags.add(tag)))
  return tags.size
})

const topTags = computed(() => {
  const countMap: Record<string, number> = {}
  frameworkNotes.value.forEach(note => {
    (note.tags || []).forEach(tag => {
      countMap[tag] = (countMap[tag] || 0) + 1
    })
  })
  return Object.entries(countMap)
    .map(([name, count]) => ({ name, count }))
    .sort((a, b) => b.count - a.count)
    .slice(0, 8)
})

const getSubjectCount = (subject: string) => {
  return frameworkNotes.value.filter(note => note.subjectType?.includes(subject)).length
}

const uniqueSubjects = computed(() => {
  const subjects = new Set<string>()
  frameworkNotes.value.forEach(note => {
    if (note.subjectType && Array.isArray(note.subjectType)) {
      note.subjectType.forEach(subject => subjects.add(subject))
    }
  })
  return Array.from(subjects)
})

const uniqueKnowledgePoints = computed(() => {
  const knowledgePoints = new Set<string>()
  frameworkNotes.value.forEach(note => {
    if (note.knowledgePoint && Array.isArray(note.knowledgePoint)) {
      note.knowledgePoint.forEach(knowledge => knowledgePoints.add(knowledge))
    }
  })
  return Array.from(knowledgePoints)
})

const uniqueCourses = computed(() => {
  const courses = new Set<string>()
  frameworkNotes.value.forEach(note => {
    if (note.subjectType && Array.isArray(note.subjectType)) {
      note.subjectType.forEach(subject => {
        courses.add(subject)
      })
    }
  })
  return Array.from(courses)
})

const subjectStats = computed(() => {
  const stats: Record<string, number> = {}
  
  frameworkNotes.value.forEach(note => {
    if (note.subjectType && Array.isArray(note.subjectType)) {
      note.subjectType.forEach(subject => {
        stats[subject] = (stats[subject] || 0) + 1
      })
    }
  })
  
  return Object.entries(stats).map(([subject, count]) => ({
    subject,
    count
  })).sort((a, b) => b.count - a.count)
})

const thisMonthFrameworks = computed(() => {
  const now = new Date()
  const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1)
  const endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0)
  
  return frameworkNotes.value.filter(note => {
    const noteDate = new Date(note.createTime)
    return noteDate >= startOfMonth && noteDate <= endOfMonth
  })
})

// 总字数
const totalWordCount = computed(() => {
  return frameworkNotes.value.reduce((sum, note) => {
    const textContent = note.content.replace(/<[^>]*>/g, '')
    return sum + textContent.length
  }, 0)
})

// 平均字数
const averageWordCount = computed(() => {
  if (frameworkNotes.value.length === 0) return 0
  return Math.round(totalWordCount.value / frameworkNotes.value.length)
})

// 获取学科字数统计
const getSubjectWordCount = (subject: string) => {
  const subjectNotes = frameworkNotes.value.filter(note => 
    note.subjectType && note.subjectType.includes(subject)
  )
  return subjectNotes.reduce((sum, note) => {
    const textContent = note.content.replace(/<[^>]*>/g, '')
    return sum + textContent.length
  }, 0)
}





// 分页相关
const currentPage = ref(1)
const itemsPerPage = 6

const totalPages = computed(() => Math.ceil(subjectStats.value.length / itemsPerPage))

const visiblePages = computed(() => {
  const pages = []
  for (let i = Math.max(1, currentPage.value - 2); i <= Math.min(totalPages.value, currentPage.value + 2); i++) {
    pages.push(i)
  }
  return pages
})

const paginatedSubjects = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return subjectStats.value.slice(start, end)
})

// 阅读笔记相关
const showNotesList = ref(false)
const showNoteDetail = ref(false)
const selectedNote = ref<any>(null)
const searchQuery = ref('')
const selectedSubject = ref('')

const filteredNotes = computed(() => {
  return frameworkNotes.value.filter(note => {
    const matchesQuery = searchQuery.value === '' || note.title.includes(searchQuery.value) || note.summary.includes(searchQuery.value)
    const matchesSubject = selectedSubject.value === '' || note.subjectType?.includes(selectedSubject.value)
    return matchesQuery && matchesSubject
  })
})

const viewNoteDetail = (note: any) => {
  selectedNote.value = note
  showNoteDetail.value = true
}

// 最新笔记
const latestNotes = computed(() => {
  return frameworkNotes.value
    .sort((a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime())
    .slice(0, 5)
})



const formatDate = (timestamp: string) => {
  return dayjs(timestamp).format('YYYY-MM-DD')
}

// 图表引用
const pieChartRef = ref<HTMLCanvasElement>()
const barChartRef = ref<HTMLCanvasElement>()

// 图表实例
let pieChart: Chart | null = null
let barChart: Chart | null = null

// 初始化图表
onMounted(async () => {
  await loadNotes()
  nextTick(() => {
    initCharts()
  })
})

// 监听数据变化，更新图表
watch([subjectStats, frameworkNotes], () => {
  nextTick(() => {
    if (pieChart && barChart) {
      updateCharts()
    } else {
      initCharts()
    }
  })
}, { deep: true })

// 监听canvas元素变化
watch([pieChartRef, barChartRef], () => {
  nextTick(() => {
    if (pieChartRef.value && barChartRef.value) {
      initCharts()
    }
  })
})

const initCharts = () => {
  // 初始化饼图
  if (pieChartRef.value) {
    const ctx = pieChartRef.value.getContext('2d')
    if (ctx) {
      pieChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: subjectStats.value.map(s => s.subject),
          datasets: [{
            data: subjectStats.value.map(s => s.count),
            backgroundColor: [
              '#3B82F6', '#10B981', '#8B5CF6', '#F59E0B', '#EF4444',
              '#06B6D4', '#84CC16', '#F97316', '#EC4899', '#6366F1'
            ],
            borderWidth: 2,
            borderColor: '#ffffff'
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'bottom',
              labels: {
                padding: 20,
                usePointStyle: true,
                font: {
                  size: 12
                }
              }
            },
            tooltip: {
              callbacks: {
                label: function(context) {
                  const total = context.dataset.data.reduce((a: number, b: number) => a + b, 0)
                  const percentage = ((context.parsed / total) * 100).toFixed(1)
                  return `${context.label}: ${context.parsed} 个 (${percentage}%)`
                }
              }
            }
          }
        }
      })
    }
  }

  // 初始化柱状图
  if (barChartRef.value) {
    const ctx = barChartRef.value.getContext('2d')
    if (ctx) {
      // 计算创建时间分布（按月份）
      const timeStats: Record<string, number> = {
        '1月': 0,
        '2月': 0,
        '3月': 0,
        '4月': 0,
        '5月': 0,
        '6月': 0,
        '7月': 0,
        '8月': 0,
        '9月': 0,
        '10月': 0,
        '11月': 0,
        '12月': 0
      }
      
      const currentYear = new Date().getFullYear()
      frameworkNotes.value.forEach(note => {
        const noteDate = new Date(note.createTime)
        if (noteDate.getFullYear() === currentYear) {
          const month = noteDate.getMonth() + 1
          const monthKey = `${month}月`
          timeStats[monthKey]++
        }
      })

      barChart = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: Object.keys(timeStats),
          datasets: [{
            label: '框架数量',
            data: Object.values(timeStats),
            backgroundColor: [
              '#3B82F6', '#10B981', '#8B5CF6', '#F59E0B', '#EF4444',
              '#06B6D4', '#84CC16', '#F97316', '#EC4899', '#6366F1',
              '#8B5A2B', '#FF6B6B'
            ],
            borderColor: [
              '#2563EB', '#059669', '#7C3AED', '#D97706', '#DC2626',
              '#0891B2', '#65A30D', '#EA580C', '#DB2777', '#4F46E5',
              '#713F12', '#DC2626'
            ],
            borderWidth: 1,
            borderRadius: 4
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              display: false
            },
            tooltip: {
              callbacks: {
                label: function(context) {
                  return `框架数量: ${context.parsed.y} 个`
                }
              }
            }
          },
          scales: {
            y: {
              beginAtZero: true,
              ticks: {
                stepSize: 1
              }
            }
          }
        }
      })
    }
  }
}

const updateCharts = () => {
  // 更新饼图
  if (pieChart) {
            pieChart.data.labels = subjectStats.value.map(s => s.subject)
        pieChart.data.datasets[0].data = subjectStats.value.map(s => s.count)
    pieChart.update()
  }

  // 更新柱状图
  if (barChart) {
    const timeStats: Record<string, number> = {
      '1月': 0,
      '2月': 0,
      '3月': 0,
      '4月': 0,
      '5月': 0,
      '6月': 0,
      '7月': 0,
      '8月': 0,
      '9月': 0,
      '10月': 0,
      '11月': 0,
      '12月': 0
    }
    
    frameworkNotes.value.forEach(note => {
      const noteDate = new Date(note.createTime)
      const month = noteDate.getMonth() + 1
      const monthKey = `${month}月`
      timeStats[monthKey]++
    })

    barChart.data.labels = Object.keys(timeStats)
    barChart.data.datasets[0].data = Object.values(timeStats)
    barChart.update()
  }
}

// 组件卸载时的图表清理逻辑
onUnmounted(() => {
  pieChart = null
  barChart = null
})
  </script>

<style scoped>
/* 弹窗动画 */
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