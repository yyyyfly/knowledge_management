<template>
  <div class="p-6 bg-gray-50 min-h-screen">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-2">背诵笔记</h1>
      <p class="text-gray-600">重点内容、公式记忆、知识点背诵的统计概览</p>
    </div>

    <!-- 统计卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6 mb-8">
      <!-- 总数 -->
      <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">背诵笔记总数</p>
            <p class="text-2xl font-bold text-gray-900">{{ memorizationNotes.length }}</p>
          </div>
          <div class="p-3 bg-green-50 rounded-full">
            <i class="fas fa-lightbulb text-green-600 text-xl"></i>
          </div>
        </div>
        <div class="mt-4 text-xs text-gray-500">所有背诵内容记录</div>
      </div>
      <!-- 项目数量 -->
      <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">项目数量</p>
            <p class="text-2xl font-bold text-gray-900">{{ uniqueProjects.length }}</p>
          </div>
          <div class="p-3 bg-blue-50 rounded-full">
            <i class="fas fa-project-diagram text-blue-600 text-xl"></i>
          </div>
        </div>
        <div class="mt-4 text-xs text-gray-500">覆盖的学习项目</div>
      </div>
      <!-- 知识点数量 -->
      <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">知识点数量</p>
            <p class="text-2xl font-bold text-gray-900">{{ uniqueKnowledgePoints.length }}</p>
          </div>
          <div class="p-3 bg-yellow-50 rounded-full">
            <i class="fas fa-brain text-yellow-600 text-xl"></i>
          </div>
        </div>
        <div class="mt-4 text-xs text-gray-500">覆盖的知识点</div>
      </div>
      <!-- 最近更新 -->
      <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">最近更新</p>
            <p class="text-2xl font-bold text-gray-900">{{ latestUpdate }}</p>
          </div>
          <div class="p-3 bg-purple-50 rounded-full">
            <i class="fas fa-clock text-purple-600 text-xl"></i>
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
              <p class="text-sm text-gray-600">浏览所有背诵笔记</p>
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

    <!-- 最近笔记和项目分类概览 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
      <!-- 项目分类概览 -->
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">项目分类概览</h2>
        <div class="space-y-4">
          <div 
            v-for="project in paginatedProjectStats" 
            :key="project.project"
            class="group relative flex flex-col min-h-[120px] p-6 border border-gray-100 rounded-xl bg-gradient-to-br from-blue-50/80 to-white hover:from-blue-100 hover:to-blue-50 shadow-sm hover:shadow-md transition-all duration-300 cursor-pointer overflow-hidden"
          >
            <!-- 装饰圆形 -->
            <div class="absolute right-0 bottom-0 w-24 h-24 bg-gradient-to-br from-blue-200/30 to-blue-400/10 rounded-full translate-x-1/3 translate-y-1/3 pointer-events-none"></div>
            
            <!-- 项目名称和笔记数量 -->
            <div class="flex items-center justify-between mb-3 z-10">
              <h3 class="font-semibold text-lg text-gray-900 group-hover:text-blue-700 transition-colors">{{ project.project }}</h3>
              <span class="bg-blue-500/10 text-blue-700 px-4 py-1.5 rounded-full text-lg font-bold tracking-wide shadow-sm border border-blue-100">
                {{ project.count }} 条
              </span>
            </div>
            
            <!-- 项目描述（如果有） -->
            <div v-if="getProjectDescription(project.project)" class="mb-3 z-10">
              <p class="text-sm text-gray-600 line-clamp-2">
                {{ getProjectDescription(project.project) }}
              </p>
            </div>
            
            <!-- 统计信息 -->
            <div class="flex flex-col space-y-2 text-xs text-gray-500 z-10">
              <span class="flex items-center">
                <i class="fas fa-clock text-blue-500 mr-1"></i>
                最近更新: {{ getProjectLastUpdate(project.project) }}
              </span>
              <span class="flex items-center">
                <i class="fas fa-star text-yellow-500 mr-1"></i>
                平均背诵: {{ getProjectAvgReview(project.project) }}次
              </span>
            </div>
          </div>
        </div>
        <!-- 分页控件 -->
        <div v-if="projectStats.length > 5" class="mt-6 flex items-center justify-center space-x-2">
          <button 
            @click="currentProjectPage = Math.max(1, currentProjectPage - 1)"
            :disabled="currentProjectPage === 1"
            class="px-3 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            <i class="fas fa-chevron-left"></i>
          </button>
          <div class="flex items-center space-x-1">
            <button 
              v-for="page in visibleProjectPages" 
              :key="page"
              @click="currentProjectPage = page"
              :class="[
                'px-3 py-2 text-sm border rounded-lg transition-colors',
                currentProjectPage === page 
                  ? 'bg-blue-600 text-white border-blue-600' 
                  : 'border-gray-300 hover:bg-gray-50'
              ]"
            >
              {{ page }}
            </button>
          </div>
          <button 
            @click="currentProjectPage = Math.min(totalProjectPages, currentProjectPage + 1)"
            :disabled="currentProjectPage === totalProjectPages"
            class="px-3 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            <i class="fas fa-chevron-right"></i>
          </button>
        </div>
      </div>

      <!-- 最近笔记 -->
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">最近笔记</h2>
        <div class="space-y-4">
          <div 
            v-for="note in recentNotes" 
            :key="note.id"
            class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 hover:border-gray-300 cursor-pointer transition-all duration-200 group"
            @click="viewNoteDetail(note)"
          >
            <div class="mb-3">
              <h5 class="text-sm font-medium text-gray-900 line-clamp-2 group-hover:text-green-600 transition-colors">{{ note.title }}</h5>
            </div>
            <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
            <div class="flex items-center justify-between text-xs text-gray-500 mb-2">
              <span>{{ formatDate(note.createTime) }}</span>
              <div class="flex items-center space-x-2">
                <span class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-medium">
                  背诵: {{ note.reviewCount }}次
                </span>
                <span v-if="note.lastReviewTime" class="bg-purple-100 text-purple-700 px-2 py-1 rounded text-xs font-medium">
                  上次背诵: {{ formatRelativeTime(note.lastReviewTime) }}
                </span>
              </div>
            </div>
            <div class="flex flex-wrap gap-1">
              <!-- 项目标签 -->
              <span v-if="note.project" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-medium">
                {{ note.project }}
              </span>
              <!-- 知识点标签 -->
              <span v-for="knowledgePoint in note.knowledgePoint?.slice(0, 2)" :key="knowledgePoint" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                {{ knowledgePoint }}
              </span>
              <span v-if="note.knowledgePoint && note.knowledgePoint.length > 2" class="text-xs text-gray-500 px-2 py-1">
                +{{ note.knowledgePoint.length - 2 }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 实用统计信息 -->
    <!-- <div class="bg-white rounded-xl shadow-card p-6 border border-gray-100">
      <h2 class="text-xl font-semibold text-gray-900 mb-6">背诵进度统计</h2>
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <!-- 折线图：今日背诵次数 -->
        <!-- <div class="bg-gradient-to-br from-green-50 to-emerald-50 rounded-xl p-6 border border-green-100">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-lg font-semibold text-gray-900">今日背诵次数</h3>
            <div class="w-12 h-12 bg-green-500 rounded-full flex items-center justify-center">
              <i class="fas fa-chart-line text-white text-xl"></i>
            </div>
          </div>
          <div class="mb-4">
            <div class="text-3xl font-bold text-green-600 mb-2">{{ getTodayReviewCount() }}</div>
            <div class="text-sm text-gray-600">今日已完成背诵</div>
          </div>
          <!-- 简单的折线图 -->
          <!-- <div class="h-32 flex items-end justify-between space-x-2">
            <div v-for="(count, index) in getWeeklyReviewData()" :key="index" class="flex-1 flex flex-col items-center">
              <div class="w-full bg-gray-200 rounded-t-sm mb-2" style="height: 1px;"></div>
              <div 
                class="w-full bg-green-500 rounded-t-sm transition-all duration-500"
                :style="{ height: (count / Math.max(...getWeeklyReviewData())) * 100 + '%' }"
              ></div>
              <div class="text-xs text-gray-500 mt-2">{{ ['一', '二', '三', '四', '五', '六', '日'][index] }}</div>
            </div>
          </div>
        </div>
        
        <!-- 柱状图：积累笔记数量 -->
        <!-- <div class="bg-gradient-to-br from-blue-50 to-indigo-50 rounded-xl p-6 border border-blue-100">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-lg font-semibold text-gray-900">积累笔记数量</h3>
            <div class="w-12 h-12 bg-blue-500 rounded-full flex items-center justify-center">
              <i class="fas fa-chart-bar text-white text-xl"></i>
            </div>
          </div>
          <div class="mb-4">
            <div class="text-3xl font-bold text-blue-600 mb-2">{{ memorizationNotes.length }}</div>
            <div class="text-sm text-gray-600">总笔记数量</div>
          </div>
          <!-- 简单的柱状图 -->
          <!-- <div class="h-32 flex items-end justify-between space-x-2">
            <div v-for="project in projectStats.slice(0, 5)" :key="project.project" class="flex-1 flex flex-col items-center">
              <div 
                class="w-full bg-blue-500 rounded-t-sm transition-all duration-500"
                :style="{ height: (project.count / Math.max(...projectStats.map(p => p.count))) * 100 + '%' }"
              ></div>
              <div class="text-xs text-gray-500 mt-2 text-center leading-tight">{{ project.project.split(' ')[0] }}</div>
            </div>
          </div>
        </div>
      </div>
    </div> -->

    <!-- 笔记列表弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showNotesList" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[90vh] flex flex-col">
            <!-- 弹窗头部 -->
            <div class="flex items-center justify-between p-6 border-b border-gray-200">
              <div class="flex items-center space-x-3">
                <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center">
                  <i class="fas fa-lightbulb text-2xl text-green-600"></i>
                </div>
                <div>
                  <h3 class="text-2xl font-semibold text-gray-900">背诵笔记列表</h3>
                  <p class="text-sm text-gray-500">{{ filteredNotes.length }} 条笔记</p>
                </div>
              </div>
              <button 
                @click="showNotesList = false"
                class="text-gray-500 hover:text-gray-700 transition-colors"
              >
                <i class="fas fa-times text-2xl"></i>
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
                      class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500 transition-all"
                    >
                  </div>
                </div>
                <div>
                  <select v-model="selectedProject" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500">
                    <option value="">全部项目</option>
                    <option v-for="project in uniqueProjects" :key="project" :value="project">
                      {{ project }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
            
            <!-- 笔记列表 -->
            <div class="flex-1 overflow-y-auto p-6">
              <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div 
                  v-for="note in paginatedNotes" 
                  :key="note.id"
                  class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 hover:border-gray-300 cursor-pointer transition-all duration-200 group"
                  @click="viewNoteDetail(note)"
                >
                  <div class="mb-3">
                    <h5 class="text-sm font-medium text-gray-900 line-clamp-2 group-hover:text-green-600 transition-colors">{{ note.title }}</h5>
                  </div>
                  <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
                  <div class="flex items-center justify-between text-xs text-gray-500 mb-2">
                    <span>{{ formatDate(note.createTime) }}</span>
                    <div class="flex items-center space-x-2">
                      <span class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-medium">
                        背诵: {{ note.reviewCount }}次
                      </span>
                      <span v-if="note.lastReviewTime" class="bg-purple-100 text-purple-700 px-2 py-1 rounded text-xs font-medium">
                        上次背诵: {{ formatRelativeTime(note.lastReviewTime) }}
                      </span>
                    </div>
                  </div>
                  <div class="flex flex-wrap gap-1">
                    <!-- 项目标签 -->
                    <span v-if="note.project" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-medium">
                      {{ note.project }}
                    </span>
                    <!-- 知识点标签 -->
                    <span v-for="knowledgePoint in note.knowledgePoint?.slice(0, 2)" :key="knowledgePoint" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                      {{ knowledgePoint }}
                    </span>
                    <span v-if="note.knowledgePoint && note.knowledgePoint.length > 2" class="text-xs text-gray-500 px-2 py-1">
                      +{{ note.knowledgePoint.length - 2 }}
                    </span>
                  </div>
                </div>
                
                <!-- 空状态 -->
                <div v-if="paginatedNotes.length === 0" class="col-span-full text-center py-16 text-gray-500">
                  <i class="fas fa-search text-5xl mb-4 text-gray-300"></i>
                  <p class="text-lg font-medium mb-2">未找到匹配的笔记</p>
                  <p class="text-sm">请尝试调整搜索条件</p>
                </div>
              </div>
              
              <!-- 分页控件 -->
              <div v-if="totalPages > 1" class="mt-8 flex items-center justify-center space-x-2">
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
                        ? 'bg-green-600 text-white border-green-600' 
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
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            <div v-if="selectedNote" class="space-y-4">
              <div class="flex items-center justify-between mb-4">
                <div class="flex items-center space-x-3">
                  <h4 class="text-lg font-medium text-gray-900">{{ selectedNote?.title }}</h4>
                  <div class="flex items-center space-x-2">
                    <span class="bg-blue-100 text-blue-700 px-2 py-1 rounded-full text-xs font-medium">
                      背诵次数: {{ selectedNote?.reviewCount }}次
                    </span>
                  </div>
                </div>
                <button 
                  @click="handleCompleteMemorization"
                  class="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                >
                  完成背诵
                </button>
              </div>
              <!-- 新增tab切换，仅影响详细内容 -->
              <div class="flex space-x-2 mb-2">
                <button v-for="tab in tabs" :key="tab.value" @click="activeTab = tab.value"
                  :class="[ 'px-4 py-2 rounded-t-lg font-medium transition', activeTab === tab.value ? 'bg-green-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200' ]">
                  {{ tab.label }}
                </button>
              </div>
              <div class="bg-gray-50 p-4 rounded-lg">
                <p class="text-sm font-medium text-gray-700 mb-2">摘要：</p>
                <p class="text-sm text-gray-600">{{ selectedNote?.summary }}</p>
              </div>
              <div>
                <p class="text-sm font-medium text-gray-700 mb-2">详细内容：</p>
                <div class="text-sm text-gray-600 prose prose-sm max-w-none min-h-[80px]">
                  <template v-if="activeTab === 'original'">{{ selectedNote?.originalText }}</template>
                  <template v-else-if="activeTab === 'explanation'">{{ selectedNote?.explanation }}</template>
                  <template v-else-if="activeTab === 'cue'">{{ selectedNote?.cue }}</template>
                </div>
              </div>
              <div class="space-y-3">
                <div class="flex items-center space-x-4 text-sm text-gray-500">
                  <span>创建时间：{{ formatDate(selectedNote?.createTime || '') }}</span>
                  <span v-if="selectedNote?.lastReviewTime">上次背诵：{{ formatRelativeTime(selectedNote?.lastReviewTime) }}</span>
                </div>
                
                <!-- 项目标签 -->
                <div v-if="selectedNote?.project" class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">项目：</span>
                  <span class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                    {{ selectedNote.project }}
                  </span>
                </div>
                
                <!-- 知识点标签 -->
                <div v-if="selectedNote?.knowledgePoint && selectedNote.knowledgePoint.length > 0" class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">知识点：</span>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="knowledgePoint in selectedNote.knowledgePoint" :key="knowledgePoint" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                      {{ knowledgePoint }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated } from 'vue'
import dayjs from 'dayjs'
import { getNotesByType } from '@/services/noteService'

// 定义背诵笔记类型
type MemorizationNote = {
  id: number
  title: string
  content: string
  type: string
  summary?: string
  project: string
  knowledgePoint: string[]
  reviewCount: number
  createTime?: string
  recCreateTime?: string
  completedCount?: number
  lastReviewTime?: string
  nextReviewTime?: string
  originalText?: string
  explanation?: string
  cue?: string
}

// 响应式数据
const memorizationNotesData = ref<MemorizationNote[]>([])

// 加载背诵笔记
const loadNotes = async () => {
  try {
    const notes = await getNotesByType('memorization')
    // noteService 已经将所有字符串字段转换为数组，但需要确保字段完整性
    memorizationNotesData.value = notes.map((note: any) => ({
      ...note,
      knowledgePoint: Array.isArray(note.knowledgePoint) ? note.knowledgePoint : [],
      reviewCount: note.reviewCount || 0,
      project: note.project || '未分类'
    })) as MemorizationNote[]
  } catch (error) {
    console.error('加载背诵笔记失败:', error)
  }
}

// 组件挂载时加载
onMounted(() => {
  loadNotes()
})

// 组件激活时刷新数据（从其他页面返回时）
onActivated(() => {
  loadNotes()
})

const memorizationNotes = computed(() => memorizationNotesData.value)

const recentNotes = computed(() => {
  return memorizationNotesData.value
    .sort((a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime())
    .slice(0, 5)
})

const topKnowledgePoints = computed(() => {
  const countMap: Record<string, number> = {}
  memorizationNotes.value.forEach(note => {
    // 确保 knowledgePoint 是数组
    if (Array.isArray(note.knowledgePoint)) {
      note.knowledgePoint.forEach(kp => {
        countMap[kp] = (countMap[kp] || 0) + 1
      })
    }
  })
  return Object.entries(countMap)
    .map(([knowledgePoint, count]) => ({ knowledgePoint, count }))
    .sort((a, b) => b.count - a.count)
    .slice(0, 5)
})

const latestUpdate = computed(() => {
  if (!memorizationNotes.value.length) return '暂无数据'
  const latest = memorizationNotes.value.reduce((max, n) => n.createTime > max ? n.createTime : max, memorizationNotes.value[0].createTime)
  return dayjs(latest).format('YYYY-MM-DD')
})

const uniqueProjects = computed(() => {
  const projects = new Set<string>()
  memorizationNotesData.value.forEach(note => {
    if (note.title) projects.add(note.title.split('-')[0])
  })
  return Array.from(projects)
})

const uniqueKnowledgePoints = computed(() => {
  const knowledgePoints = new Set<string>()
  memorizationNotes.value.forEach(note => {
    // 确保 knowledgePoint 是数组
    if (Array.isArray(note.knowledgePoint)) {
      note.knowledgePoint.forEach(kp => knowledgePoints.add(kp))
    }
  })
  return Array.from(knowledgePoints)
})

const projectStats = computed(() => {
  const stats: Record<string, { count: number; knowledgePoints: string[]; totalReviewCount: number }> = {}
  
  memorizationNotes.value.forEach(note => {
    if (!stats[note.project]) {
      stats[note.project] = { count: 0, knowledgePoints: [], totalReviewCount: 0 }
    }
    stats[note.project].count++
    // 确保 knowledgePoint 是数组
    if (Array.isArray(note.knowledgePoint)) {
      stats[note.project].knowledgePoints.push(...note.knowledgePoint)
    }
    // 确保 reviewCount 不为空
    stats[note.project].totalReviewCount += (note.reviewCount || 0)
  })
  
  return Object.entries(stats).map(([project, data]) => ({
    project,
    count: data.count,
    knowledgePointCount: new Set(data.knowledgePoints).size,
    knowledgePoints: [...new Set(data.knowledgePoints)],
    totalReviewCount: data.totalReviewCount
  })).sort((a, b) => b.count - a.count)
})

const showNotesList = ref(false)
const showNoteDetail = ref(false)
const selectedNote = ref<MemorizationNote | null>(null)
const searchQuery = ref('')
const selectedProject = ref('')

const filteredNotes = computed(() => {
  let notes = memorizationNotes.value
  
  if (searchQuery.value) {
    notes = notes.filter(note => {
      return note.title.includes(searchQuery.value) || note.summary.includes(searchQuery.value)
    })
  }
  
  if (selectedProject.value) {
    notes = notes.filter(note => note.project === selectedProject.value)
  }
  
  return notes
})

const handleCompleteMemorization = () => {
  if (selectedNote.value) {
    // 更新背诵次数（本地更新）
    const noteIndex = memorizationNotesData.value.findIndex(note => note.id === selectedNote.value!.id)
    if (noteIndex !== -1) {
      memorizationNotesData.value[noteIndex].reviewCount = (memorizationNotesData.value[noteIndex].reviewCount || 0) + 1
      memorizationNotesData.value[noteIndex].lastReviewTime = new Date().toISOString()
      
      // 更新选中的笔记
      selectedNote.value = { ...memorizationNotesData.value[noteIndex] }
    }
    
    // 延迟关闭弹窗，让用户看到更新效果
    setTimeout(() => {
      showNoteDetail.value = false
      selectedNote.value = null
    }, 500)
  }
}

const formatDate = (timestamp: string) => {
  return dayjs(timestamp).format('YYYY-MM-DD')
}

const formatRelativeTime = (timestamp: string) => {
  const now = dayjs()
  const time = dayjs(timestamp)
  const diffMinutes = now.diff(time, 'minute')
  const diffHours = now.diff(time, 'hour')
  const diffDays = now.diff(time, 'day')
  const diffWeeks = now.diff(time, 'week')
  const diffMonths = now.diff(time, 'month')
  
  if (diffMinutes < 60) {
    return `${diffMinutes}分钟前`
  } else if (diffHours < 24) {
    return `${diffHours}小时前`
  } else if (diffDays < 7) {
    return `${diffDays}天前`
  } else if (diffWeeks < 4) {
    return `${diffWeeks}周前`
  } else if (diffMonths < 12) {
    return `${diffMonths}个月前`
  } else {
    return `${now.diff(time, 'year')}年前`
  }
}

const totalPages = computed(() => Math.ceil(filteredNotes.value.length / 9))
const currentPage = ref(1)
const visiblePages = computed(() => {
  const pages = []
  for (let i = Math.max(1, currentPage.value - 2); i <= Math.min(totalPages.value, currentPage.value + 2); i++) {
    pages.push(i)
  }
  return pages
})

const paginatedNotes = computed(() => {
  const start = (currentPage.value - 1) * 9
  const end = start + 9
  return filteredNotes.value.slice(start, end)
})

// 项目分页相关
const currentProjectPage = ref(1)
const projectPageSize = ref(5)
const totalProjectPages = computed(() => Math.ceil(projectStats.value.length / projectPageSize.value))
const visibleProjectPages = computed(() => {
  const pages = []
  for (let i = Math.max(1, currentProjectPage.value - 2); i <= Math.min(totalProjectPages.value, currentProjectPage.value + 2); i++) {
    pages.push(i)
  }
  return pages
})

const paginatedProjectStats = computed(() => {
  const start = (currentProjectPage.value - 1) * projectPageSize.value
  const end = start + projectPageSize.value
  return projectStats.value.slice(start, end)
})

const getProjectDescription = (project: string) => {
  const descriptions: Record<string, string> = {
    'Vue.js 前端开发': 'Vue.js框架学习笔记，包含组件开发、状态管理、路由配置等核心知识点',
    'React 技术栈': 'React生态系统学习，涵盖Hooks、Context、Redux等现代开发技术',
    'Node.js 后端开发': 'Node.js服务器端开发，包括Express框架、数据库操作、API设计',
    'Python 数据分析': 'Python数据分析与机器学习，pandas、numpy、matplotlib等库的使用',
    'Java 企业级开发': 'Java企业级应用开发，Spring框架、微服务架构、设计模式',
    '数据库设计与优化': 'SQL数据库设计、性能优化、索引策略、事务管理',
    '算法与数据结构': '经典算法实现、数据结构设计、时间复杂度分析',
    '系统架构设计': '分布式系统设计、微服务架构、高可用性设计模式'
  }
  return descriptions[project] || ''
}

const getProjectLastUpdate = (project: string) => {
  const projectNotes = memorizationNotes.value.filter(note => note.project === project)
  if (projectNotes.length === 0) return '暂无更新'
  
  const latestNote = projectNotes.reduce((latest, note) => 
    new Date(note.createTime) > new Date(latest.createTime) ? note : latest
  )
  
  const daysAgo = dayjs().diff(dayjs(latestNote.createTime), 'day')
  if (daysAgo === 0) return '今天'
  if (daysAgo === 1) return '昨天'
  if (daysAgo < 7) return `${daysAgo}天前`
  if (daysAgo < 30) return `${Math.floor(daysAgo / 7)}周前`
  return `${Math.floor(daysAgo / 30)}个月前`
}

const getProjectAvgReview = (project: string) => {
  const projectNotes = memorizationNotes.value.filter(note => note.project === project)
  if (projectNotes.length === 0) return 0
  
  const totalReviews = projectNotes.reduce((sum, note) => sum + note.reviewCount, 0)
  return Math.round(totalReviews / projectNotes.length)
}

// 模拟获取今日背诵次数
const getTodayReviewCount = () => {
  const today = dayjs().startOf('day')
  const notesToday = memorizationNotes.value.filter(note => 
    note.lastReviewTime && dayjs(note.lastReviewTime).isSame(today, 'day')
  )
  return notesToday.length
}

// 模拟获取本周每日背诵次数数据
const getWeeklyReviewData = () => {
  const weekData: number[] = []
  for (let i = 6; i >= 0; i--) {
    const date = dayjs().subtract(i, 'day').startOf('day')
    const notesOnDate = memorizationNotes.value.filter(note => 
      note.lastReviewTime && dayjs(note.lastReviewTime).isSame(date, 'day')
    )
    weekData.push(notesOnDate.length)
  }
  return weekData
}

const tabs = [
  { label: '原文', value: 'original' },
  { label: '解释', value: 'explanation' },
  { label: '提示词', value: 'cue' }
]
const activeTab = ref('original')

// 获取当前tab对应的内容
const getNoteContent = (note: MemorizationNote | null) => {
  if (!note) return ''
  if (activeTab.value === 'original') return note.originalText
  if (activeTab.value === 'explanation') return note.explanation
  if (activeTab.value === 'cue') return note.cue
  return note.originalText
}

const viewNoteDetail = (note: MemorizationNote) => {
  selectedNote.value = note
  showNoteDetail.value = true
  activeTab.value = 'original'
}
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

/* 文本截断 */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style> 