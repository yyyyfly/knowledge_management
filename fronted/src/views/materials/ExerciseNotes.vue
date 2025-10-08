<template>
  <div class="p-6 bg-gray-50 min-h-screen">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-2">练习笔记</h1>
      <p class="text-gray-600">刷题练习和技能提升的统计概览</p>
    </div>

    <!-- 统计卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-5 gap-6 mb-8">
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">总笔记数</p>
            <p class="text-3xl font-bold text-gray-900">{{ exerciseNotes.length }}</p>
            <p class="text-xs text-green-600 mt-1">+{{ thisDayNotes.length }} 本日新增</p>
          </div>
          <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">题目数量</p>
            <p class="text-3xl font-bold text-gray-900">{{ uniqueProblems.length }}</p>
            <p class="text-xs text-blue-600 mt-1">已刷题目</p>
          </div>
          <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">学科类型</p>
            <p class="text-3xl font-bold text-gray-900">{{ uniqueSubjectTypes.length }}</p>
            <p class="text-xs text-purple-600 mt-1">练习领域</p>
          </div>
          <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">知识点</p>
            <p class="text-3xl font-bold text-gray-900">{{ uniqueKnowledgePoints.length }}</p>
            <p class="text-xs text-indigo-600 mt-1">掌握要点</p>
          </div>
          <div class="w-12 h-12 bg-indigo-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"></path>
            </svg>
          </div>
        </div>
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
              <p class="text-sm text-gray-600">浏览所有刷题笔记</p>
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

    <!-- 刷题概览 -->
    <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 mb-8">
      <h2 class="text-xl font-semibold text-gray-900 mb-6">刷题概览</h2>
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div class="text-center p-4 bg-blue-50 rounded-xl">
          <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-blue-600">{{ exerciseNotes.length }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">总笔记数</p>
          <p class="text-xs text-gray-600">刷题笔记总数</p>
          <div class="mt-2 w-full bg-blue-200 rounded-full h-2">
            <div class="bg-blue-600 h-2 rounded-full" :style="{ width: Math.min((exerciseNotes.length / 100) * 100, 100) + '%' }"></div>
          </div>
        </div>
        <div class="text-center p-4 bg-green-50 rounded-xl">
          <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-green-600">{{ averageAccuracy }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">平均正确率</p>
          <p class="text-xs text-gray-600">解题正确率</p>
          <div class="mt-2 w-full bg-green-200 rounded-full h-2">
            <div class="bg-green-600 h-2 rounded-full" :style="{ width: averageAccuracy + '%' }"></div>
          </div>
        </div>
        <div class="text-center p-4 bg-purple-50 rounded-xl">
          <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-purple-600">{{ uniqueSources.length }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">题目来源</p>
          <p class="text-xs text-gray-600">来源平台数量</p>
          <div class="mt-2 w-full bg-purple-200 rounded-full h-2">
            <div class="bg-purple-600 h-2 rounded-full" :style="{ width: Math.min((uniqueSources.length / 20) * 100, 100) + '%' }"></div>
          </div>
        </div>
        <div class="text-center p-4 bg-orange-50 rounded-xl">
          <div class="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center mx-auto mb-3">
            <span class="text-2xl font-bold text-orange-600">{{ thisMonthNotes.length }}</span>
          </div>
          <p class="text-sm font-medium text-gray-900">本月新增</p>
          <p class="text-xs text-gray-600">新增笔记数</p>
          <div class="mt-2 w-full bg-orange-200 rounded-full h-2">
            <div class="bg-orange-600 h-2 rounded-full" :style="{ width: Math.min((thisMonthNotes.length / 20) * 100, 100) + '%' }"></div>
          </div>
        </div>
        </div>
      </div>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-8">
      <!-- 统计图表区域 -->
      <div class="lg:col-span-2">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
          <!-- 月度新增趋势 -->
          <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
            <h2 class="text-xl font-semibold text-gray-900 mb-6">月度新增趋势</h2>
            <div class="h-80">
              <Line :data="lineChartData" :options="lineChartOptions" />
            </div>
          </div>
          
          <!-- 题目来源分布 -->
          <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
            <h2 class="text-xl font-semibold text-gray-900 mb-6">题目来源分布</h2>
            <div class="h-80">
              <Bar :data="sourceChartData" :options="sourceChartOptions" />
            </div>
          </div>
        </div>

        <!-- 知识点分布图表 -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <h2 class="text-xl font-semibold text-gray-900 mb-6">知识点分布统计</h2>
          <div class="h-80 overflow-y-auto">
            <div class="space-y-4">
              <div v-for="point in paginatedKnowledgePointStats" :key="point.name" class="flex items-center">
                <div class="w-40 text-sm text-gray-600 truncate">{{ point.name }}</div>
                <div class="flex-1 mx-4">
                  <div class="w-full bg-gray-200 rounded-full h-3">
                    <div class="bg-gradient-to-r from-green-500 to-green-600 h-3 rounded-full transition-all duration-300" 
                         :style="{ width: point.percentage + '%' }"></div>
                  </div>
                </div>
                <div class="w-20 text-right">
                  <span class="text-sm font-semibold text-gray-900">{{ point.count }}</span>
                  <span class="text-xs text-gray-500 ml-1">({{ point.percentage }}%)</span>
                </div>
              </div>
            </div>
            <!-- 分页控件 -->
            <div class="flex items-center justify-between mt-6 pt-4 border-t border-gray-200">
              <div class="text-sm text-gray-600">
                显示 {{ (currentKnowledgePointPage - 1) * knowledgePointsPerPage + 1 }} - {{ Math.min(currentKnowledgePointPage * knowledgePointsPerPage, knowledgePointStats.length) }} 共 {{ knowledgePointStats.length }} 项
              </div>
            <div class="flex items-center space-x-2">
                <button 
                  @click="currentKnowledgePointPage = Math.max(1, currentKnowledgePointPage - 1)"
                  :disabled="currentKnowledgePointPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">
                  上一页
                </button>
                <span class="text-sm text-gray-600">
                  {{ currentKnowledgePointPage }} / {{ totalKnowledgePointPages }}
                </span>
                <button 
                  @click="currentKnowledgePointPage = Math.min(totalKnowledgePointPages, currentKnowledgePointPage + 1)"
                  :disabled="currentKnowledgePointPage === totalKnowledgePointPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">
                  下一页
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 最近笔记 -->
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
        <h2 class="text-xl font-semibold text-gray-900 mb-6">最近笔记</h2>
        <div class="space-y-4">
          <div 
            v-for="note in recentNotes" 
            :key="note.id" 
            class="p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors cursor-pointer"
            @click="viewNoteDetail(note)"
          >
            <h3 class="text-sm font-semibold text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h3>
            <p class="text-xs text-gray-600 mb-2 line-clamp-2">{{ note.summary }}</p>
                          <div class="flex items-center mb-3 whitespace-nowrap gap-1">
                <!-- 题目来源标签 -->
                <span v-if="note.exerciseSource" class="bg-red-100 text-red-700 px-2 py-1 rounded-md text-xs font-medium">
                  {{ note.exerciseSource }}
                </span>
                <!-- 学科类型标签 -->
                <span v-if="note.exerciseSubject" class="bg-blue-100 text-blue-700 px-2 py-1 rounded-md text-xs font-medium">
                  {{ note.exerciseSubject }}
                </span>
                <!-- 知识点标签 -->
                <span v-for="knowledge in note.exerciseKnowledge?.slice(0, 2)" :key="knowledge" class="bg-green-100 text-green-700 px-2 py-1 rounded-md text-xs font-medium">
                  {{ knowledge }}
                </span>
                <!-- 更多知识点提示 -->
                <span v-if="note.exerciseKnowledge && note.exerciseKnowledge.length > 2" class="text-xs text-gray-500 px-2 py-1">
                  +{{ note.exerciseKnowledge.length - 2 }}
                </span>
              </div>
            <div class="flex items-center justify-between">
              <span class="text-xs text-gray-500">{{ formatDate(note.createTime) }}</span>
              <div class="w-2 h-2 bg-green-500 rounded-full"></div>
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
                <div class="w-12 h-12 bg-orange-100 rounded-full flex items-center justify-center">
                  <i class="fa-regular fa-pen-to-square text-2xl text-orange-600"></i>
                </div>
                <div>
                  <h3 class="text-2xl font-semibold text-gray-900">刷题笔记列表</h3>
                  <p class="text-sm text-gray-500">{{ exerciseNotes.length }} 条笔记</p>
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
                      class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
                    >
                  </div>
                </div>
                                <div>
                  <select v-model="selectedSource" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500">
                    <option value="">全部来源</option>
                    <option v-for="source in uniqueSources" :key="source" :value="source">
                      {{ source }}
                    </option>
                  </select>
                </div>
                <div>
                  <select v-model="selectedSubject" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500">
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
                    <h5 class="text-sm font-medium text-gray-900 line-clamp-2 group-hover:text-orange-600 transition-colors">{{ note.title }}</h5>
                  </div>
                  <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
                                      <div class="flex items-center justify-between text-xs text-gray-500 mb-2">
                      <span>{{ formatDate(note.createTime) }}</span>
                      <span v-if="note.exerciseSource" class="bg-red-100 text-red-700 px-2 py-1 rounded text-xs font-medium">
                        {{ note.exerciseSource }}
                      </span>
                    </div>
                    <div class="flex flex-wrap gap-1">
                      <!-- 学科类型标签 -->
                      <span v-if="note.exerciseSubject" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                        {{ note.exerciseSubject }}
                      </span>
                      <!-- 知识点标签 -->
                      <span v-for="knowledge in note.exerciseKnowledge?.slice(0, 3)" :key="knowledge" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                        {{ knowledge }}
                      </span>
                      <!-- 更多知识点提示 -->
                      <span v-if="note.exerciseKnowledge && note.exerciseKnowledge.length > 3" class="text-xs text-gray-500 px-2 py-1">
                        +{{ note.exerciseKnowledge.length - 3 }}
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
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <div v-if="selectedNote" class="space-y-4">
                              <div class="flex items-center space-x-3 mb-4">
                  <h4 class="text-lg font-medium text-gray-900">{{ selectedNote.title }}</h4>
                  <span v-if="selectedNote.exerciseSource" class="bg-red-100 text-red-700 px-2 py-1 rounded-full text-xs font-medium">
                    {{ selectedNote.exerciseSource }}
                  </span>
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
                  <div class="space-y-2">
                    <!-- 题目来源 -->
                    <div v-if="selectedNote.exerciseSource" class="flex flex-wrap gap-1">
                      <span class="text-xs font-medium text-gray-600">题目来源：</span>
                      <span class="bg-red-100 text-red-700 px-2 py-1 rounded text-xs">
                        {{ selectedNote.exerciseSource }}
                      </span>
                    </div>
                    <!-- 学科类型 -->
                    <div v-if="selectedNote.exerciseSubject" class="flex flex-wrap gap-1">
                      <span class="text-xs font-medium text-gray-600">学科类型：</span>
                      <span class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                        {{ selectedNote.exerciseSubject }}
                      </span>
                    </div>
                    <!-- 知识点类型 -->
                    <div v-if="selectedNote.exerciseKnowledge && selectedNote.exerciseKnowledge.length > 0" class="flex flex-wrap gap-1">
                      <span class="text-xs font-medium text-gray-600">知识点：</span>
                      <span v-for="knowledge in selectedNote.exerciseKnowledge" :key="knowledge" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
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

  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted, onActivated } from 'vue'
import { getNotesByType } from '@/services/noteService'

// 响应式笔记数据
const notesData = ref<any[]>([])

// 加载数据
const loadNotes = async () => {
  try {
    notesData.value = await getNotesByType('exercise')
  } catch (error) {
    console.error('加载刷题笔记失败:', error)
  }
}

onMounted(() => {
  loadNotes()
})

onActivated(() => {
  loadNotes()
})
import { Bar, Line } from 'vue-chartjs'
import {
  Chart,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement
} from 'chart.js'

Chart.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement
)

// 获取练习笔记（使用异步加载的数据）
const exerciseNotes = computed(() => {
  return notesData.value
})

// 获取唯一题目
const uniqueProblems = computed(() => {
  const problems = new Set<string>()
  exerciseNotes.value.forEach(note => {
    if (note.problemName) {
      problems.add(note.problemName)
    }
  })
  return Array.from(problems)
})

// 计算属性
const uniqueSubjectTypes = computed(() => {
  const subjects = new Set<string>()
  exerciseNotes.value.forEach(note => {
    if (note.exerciseSubject && Array.isArray(note.exerciseSubject)) {
      note.exerciseSubject.forEach(subject => subjects.add(subject))
    }
  })
  return Array.from(subjects)
})

const uniqueKnowledgePoints = computed(() => {
  const points = new Set<string>()
  exerciseNotes.value.forEach(note => {
    if (note.exerciseKnowledge && Array.isArray(note.exerciseKnowledge)) {
      note.exerciseKnowledge.forEach(point => points.add(point))
    }
  })
  return Array.from(points)
})

// 本日新增笔记
const thisDayNotes = computed(() => {
  const now = new Date()
  const today = now.toDateString()
  
  return exerciseNotes.value.filter(note => {
    const noteDate = new Date(note.createTime).toDateString()
    return noteDate === today
  })
})

// 本日新增题目
const thisDayProblems = computed(() => {
  const now = new Date()
  const today = now.toDateString()
  
  const problems = new Set<string>()
  exerciseNotes.value.forEach(note => {
    if (note.problemName) {
      if (new Date(note.createTime).toDateString() === today) {
        problems.add(note.problemName)
      }
    }
  })
  return Array.from(problems)
})

// 知识点统计
const knowledgePointStats = computed(() => {
  const stats: Record<string, number> = {}
  let totalOccurrences = 0
  exerciseNotes.value.forEach(note => {
    if (note.exerciseKnowledge && Array.isArray(note.exerciseKnowledge)) {
      note.exerciseKnowledge.forEach(point => {
        stats[point] = (stats[point] || 0) + 1
        totalOccurrences++
      })
    }
  })
  return Object.entries(stats)
    .map(([name, count]) => ({
      name,
      count,
      percentage: totalOccurrences > 0 ? Math.round((count / totalOccurrences) * 100) : 0
    }))
    .sort((a, b) => b.count - a.count)
})

// 最近笔记
const recentNotes = computed(() => {
  return exerciseNotes.value
    .sort((a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime())
    .slice(0, 5)
})

// 练习概览统计
const practiceDays = computed(() => {
  const uniqueDays = new Set<string>()
  exerciseNotes.value.forEach(note => {
    const date = new Date(note.createTime).toDateString()
    uniqueDays.add(date)
  })
  return uniqueDays.size
})

const maxConsecutiveDays = computed(() => {
  if (exerciseNotes.value.length === 0) return 0
  
  const dates = exerciseNotes.value.map(note => new Date(note.createTime).toDateString())
  const uniqueDates = [...new Set(dates)].sort()
  
  let maxConsecutive = 1
  let currentConsecutive = 1
  
  for (let i = 1; i < uniqueDates.length; i++) {
    const prevDate = new Date(uniqueDates[i - 1])
    const currDate = new Date(uniqueDates[i])
    const diffDays = (currDate.getTime() - prevDate.getTime()) / (1000 * 60 * 60 * 24)
    
    if (diffDays === 1) {
      currentConsecutive++
      maxConsecutive = Math.max(maxConsecutive, currentConsecutive)
    } else {
      currentConsecutive = 1
    }
  }
  
  return maxConsecutive
})

const averageProblemsPerDay = computed(() => {
  if (practiceDays.value === 0) return 0
  return Math.round((uniqueProblems.value.length / practiceDays.value) * 10) / 10
})

const avgNotesPerDay = computed(() => {
  if (practiceDays.value === 0) return 0
  return Math.round((exerciseNotes.value.length / practiceDays.value) * 10) / 10
})

// 统计图表数据
const months = computed(() => {
  // 取近6个月
  const arr: string[] = []
  const now = new Date()
  for (let i = 5; i >= 0; i--) {
    const d = new Date(now.getFullYear(), now.getMonth() - i, 1)
    arr.push(`${d.getFullYear()}-${d.getMonth() + 1}`)
  }
  return arr
})

const notesByMonth = computed(() => {
  const map: Record<string, number> = {}
  months.value.forEach(m => (map[m] = 0))
  exerciseNotes.value.forEach(note => {
    const d = new Date(note.createTime)
    const key = `${d.getFullYear()}-${d.getMonth() + 1}`
    if (key in map) map[key]++
  })
  return months.value.map(m => map[m])
})

// 月度新增趋势
const lineChartData = computed(() => ({
  labels: months.value,
  datasets: [
    {
      label: '新增笔记数',
      data: notesByMonth.value,
      borderColor: 'rgb(34,197,94)',
      backgroundColor: 'rgba(34,197,94,0.1)',
            tension: 0.4,
            fill: true
    }
  ]
}))

const lineChartOptions = {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
    legend: { display: false }
          },
          scales: {
            y: {
              beginAtZero: true,
      min: 0,
      grid: { color: 'rgba(0,0,0,0.1)' }
            },
            x: {
      grid: { display: false }
            }
    }
  }

// 题目来源分布
const sourceDistribution = computed(() => {
  const sourceCount = new Map<string, number>()
  exerciseNotes.value.forEach(note => {
    if (note.exerciseSource && typeof note.exerciseSource === 'string') {
      sourceCount.set(note.exerciseSource, (sourceCount.get(note.exerciseSource) || 0) + 1)
    }
  })
  
  const sortedSources = Array.from(sourceCount.entries())
    .sort((a, b) => b[1] - a[1])
    .slice(0, 10) // 只显示前10个来源
  
  return {
    labels: sortedSources.map(([source]) => source),
    data: sortedSources.map(([, count]) => count)
  }
})

const sourceChartData = computed(() => ({
  labels: sourceDistribution.value.labels,
  datasets: [
    {
      label: '题目数量',
      data: sourceDistribution.value.data,
      backgroundColor: [
        '#3B82F6', '#10B981', '#F59E0B', '#EF4444', '#8B5CF6',
        '#06B6D4', '#84CC16', '#F97316', '#EC4899', '#6B7280'
      ]
    }
  ]
}))

const sourceChartOptions = {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
    legend: { display: false }
          },
          scales: {
            y: {
              beginAtZero: true,
      grid: { color: 'rgba(0,0,0,0.1)' }
            },
            x: {
      grid: { display: false }
            }
          }
        }

// 格式化日期
const formatDate = (dateString: string) => {
  const d = new Date(dateString)
  const pad = (n: number) => String(n).padStart(2, '0')
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}

// 分页逻辑
const knowledgePointsPerPage = 5
const currentKnowledgePointPage = ref(1)
const totalKnowledgePointPages = computed(() => Math.ceil(knowledgePointStats.value.length / knowledgePointsPerPage))
const paginatedKnowledgePointStats = computed(() => {
  const start = (currentKnowledgePointPage.value - 1) * knowledgePointsPerPage
  const end = start + knowledgePointsPerPage
  return knowledgePointStats.value.slice(start, end)
})

// 新增变量
const showNotesList = ref(false)
const showNoteDetail = ref(false)
const selectedNote = ref<any>(null)
const searchQuery = ref('')
const selectedSource = ref('')
const selectedSubject = ref('')

// 新增方法
const viewNoteDetail = (note: any) => {
  selectedNote.value = note
  showNoteDetail.value = true
}

// 新增计算属性
// 获取唯一题目来源
const uniqueSources = computed(() => {
  const sources = new Set<string>()
  exerciseNotes.value.forEach(note => {
    if (note.exerciseSource && typeof note.exerciseSource === 'string') {
      sources.add(note.exerciseSource)
    }
  })
  return Array.from(sources)
})

// 获取唯一学科
const uniqueSubjects = computed(() => {
  const subjects = new Set<string>()
  exerciseNotes.value.forEach(note => {
    if (note.exerciseSubject && typeof note.exerciseSubject === 'string') {
      subjects.add(note.exerciseSubject)
    }
  })
  return Array.from(subjects)
})

const thisMonthNotes = computed(() => {
  const now = new Date()
  const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1)
  const endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0)
  
  return exerciseNotes.value.filter(note => {
    const noteDate = new Date(note.createTime)
    return noteDate >= startOfMonth && noteDate <= endOfMonth
  })
})

const averageAccuracy = computed(() => {
  if (exerciseNotes.value.length === 0) return 0
  const totalAccuracy = exerciseNotes.value.reduce((total, note) => {
    return total + (note.accuracy || 0)
  }, 0)
  return Math.round(totalAccuracy / exerciseNotes.value.length)
})

const filteredNotes = computed(() => {
  return exerciseNotes.value.filter(note => {
    const matchesSearch = !searchQuery.value || note.title.toLowerCase().includes(searchQuery.value.toLowerCase()) || note.summary.toLowerCase().includes(searchQuery.value.toLowerCase())
    const matchesSource = !selectedSource.value || selectedSource.value === '' || note.exerciseSource === selectedSource.value
    const matchesSubject = !selectedSubject.value || selectedSubject.value === '' || note.exerciseSubject === selectedSubject.value
    return matchesSearch && matchesSource && matchesSubject
  })
})
</script> 

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

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