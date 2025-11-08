<template>
  <div class="p-6 bg-gray-50 min-h-screen">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4">
        <div class="w-12 h-12 bg-gradient-to-br from-blue-500 to-indigo-600 rounded-xl flex items-center justify-center shadow-lg">
          <i class="fas fa-book-open text-white text-2xl"></i>
        </div>
          <div>
          <h1 class="text-3xl font-bold text-gray-900">拓展笔记</h1>
          <p class="text-gray-600">原文记录、深度理解、知识拓展</p>
        </div>
      </div>
    </div>

    <!-- 推荐巩固区 -->
    <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-6 mb-8">
      <h2 class="text-2xl font-bold text-gray-900 mb-6">推荐巩固（{{ recommendedNotes.length }}/5）</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6">
        <div v-for="note in recommendedNotes" :key="note.id"
             @click="viewNoteDetail(note)"
             class="bg-white rounded-xl p-6 border-2 border-gray-200 hover:border-blue-400 hover:shadow-lg cursor-pointer transition-all">
          <h3 class="font-bold text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h3>
          <p class="text-sm text-gray-600 mb-4 line-clamp-2">{{ note.summary || '暂无摘要' }}</p>
          <div class="flex items-center justify-between text-xs">
            <span class="text-gray-500">巩固 {{ note.reviewCount || 0 }} 次</span>
            <span class="text-gray-500">{{ formatLastReviewTime(note.lastReviewTime) }}</span>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-if="recommendedNotes.length === 0" class="text-center py-12">
        <i class="fas fa-check-circle text-5xl text-blue-500 mb-4"></i>
        <p class="text-gray-600">太棒了！暂时没有需要巩固的笔记</p>
      </div>
    </div>

    <!-- 手动复习区 -->
    <div @click="showNotesList = true"
         class="bg-gradient-to-br from-blue-50 to-indigo-50 border-2 border-blue-200 rounded-2xl p-8 hover:shadow-lg cursor-pointer transition-all text-center">
      <i class="fas fa-search text-4xl text-blue-600 mb-4"></i>
      <h3 class="text-xl font-bold text-gray-900 mb-2">搜索所有笔记</h3>
      <p class="text-gray-600">手动选择想要复习的笔记内容</p>
        </div>

    <!-- 笔记列表弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showNotesList" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[90vh] flex flex-col">
            <!-- 弹窗头部 -->
            <div class="flex items-center justify-between p-6 border-b border-gray-200">
              <div class="flex items-center space-x-3">
                <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
                  <i class="fas fa-book-open text-2xl text-blue-600"></i>
                </div>
                <div>
                  <h3 class="text-2xl font-semibold text-gray-900">拓展笔记列表</h3>
                  <p class="text-sm text-gray-500">{{ expansionNotes.length }} 条笔记</p>
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
                  <div class="relative">
                    <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                    <input 
                      v-model="searchQuery"
                      type="text" 
                      placeholder="搜索笔记标题或内容..."
                      class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
                    >
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
                    <h5 class="text-sm font-medium text-gray-900 line-clamp-2 group-hover:text-blue-600 transition-colors">{{ note.title }}</h5>
                  </div>
                  <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
                  
                  <!-- 巩固信息 -->
                  <div class="flex items-center justify-between text-xs mb-2 p-2 bg-gray-50 rounded">
                    <span class="text-gray-500">巩固 {{ note.reviewCount || 0 }} 次</span>
                    <span class="text-gray-500">{{ formatLastReviewTime(note.lastReviewTime) }}</span>
                  </div>
                  
                  <div class="flex flex-wrap gap-1">
                    <!-- 项目标签 -->
                    <span v-if="note.project" class="bg-indigo-100 text-indigo-700 px-2 py-1 rounded text-xs font-medium">
                      {{ note.project }}
                    </span>
                    <!-- 知识点标签 -->
                    <span v-for="knowledgePoint in (note.knowledgePoint || []).slice(0, 2)" :key="knowledgePoint" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                      {{ knowledgePoint }}
                    </span>
                    <span v-if="note.knowledgePoint && note.knowledgePoint.length > 2" class="text-xs text-gray-500 px-2 py-1">
                      +{{ note.knowledgePoint.length - 2 }}
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
              <!-- 巩固历史 -->
              <div class="bg-blue-50 rounded-xl p-4 mb-4">
                <div class="flex items-center justify-between">
                  <div>
                    <span class="text-sm text-gray-600">巩固历史：</span>
                    <span class="text-lg font-bold text-blue-600 ml-2">已巩固 {{ selectedNote.reviewCount || 0 }} 次</span>
                  </div>
                  <span v-if="selectedNote.lastReviewTime" class="text-sm text-gray-600">
                    上次巩固：{{ formatLastReviewTime(selectedNote.lastReviewTime) }}
                  </span>
                  <span v-else class="text-sm text-gray-600">尚未巩固</span>
                </div>
              </div>

              <div class="flex items-center justify-between mb-4">
                <h4 class="text-lg font-medium text-gray-900">{{ selectedNote?.title }}</h4>
              </div>
              
              <!-- Tab切换 -->
              <div class="flex space-x-2 mb-2">
                <button v-for="tab in tabs" :key="tab.value" @click="activeTab = tab.value"
                  :class="[ 'px-4 py-2 rounded-t-lg font-medium transition', activeTab === tab.value ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200' ]">
                  {{ tab.label }}
                </button>
              </div>
              
              <div class="bg-gray-50 p-4 rounded-lg">
                <p class="text-sm font-medium text-gray-700 mb-2">摘要：</p>
                <p class="text-sm text-gray-600">{{ selectedNote?.summary }}</p>
              </div>
              
              <div>
                <p class="text-sm font-medium text-gray-700 mb-2">详细内容：</p>
                <div class="note-content bg-gray-50 p-4 rounded-lg min-h-[80px]">
                  <div v-if="activeTab === 'original'" v-html="selectedNote?.originalText || '暂无内容'"></div>
                  <div v-else-if="activeTab === 'understanding'" v-html="selectedNote?.understanding || '暂无内容'"></div>
                </div>
              </div>
              
              <div class="space-y-3">
                <div class="flex items-center space-x-4 text-sm text-gray-500">
                  <span>创建时间：{{ formatDate(selectedNote?.recCreateTime) }}</span>
                </div>
                
                <!-- 项目标签 -->
                <div v-if="selectedNote?.project" class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">学科项目：</span>
                  <span class="bg-indigo-100 text-indigo-700 px-2 py-1 rounded text-xs">
                    {{ selectedNote.project }}
                  </span>
                </div>
                
                <!-- 知识点标签 -->
                <div v-if="selectedNote?.knowledgePoint && selectedNote.knowledgePoint.length > 0" class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">知识点：</span>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="knowledgePoint in selectedNote.knowledgePoint" :key="knowledgePoint" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                      {{ knowledgePoint }}
                    </span>
                  </div>
                </div>
              </div>

              <!-- 完成巩固按钮 -->
              <div class="flex gap-4 mt-6">
                <button @click="handleCompleteReview" 
                        class="flex-1 bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white py-4 rounded-xl font-medium transition-all duration-300 hover:shadow-lg">
                  <i class="fas fa-check-circle mr-2"></i>
                  完成巩固
                </button>
                <button @click="showNoteDetail = false" 
                        class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 py-4 rounded-xl font-medium transition-colors">
                  <i class="fas fa-times mr-2"></i>
                  关闭
                </button>
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
import { getNotesByType } from '@/services/noteService'
import { getRecommendedNotes, completeReview } from '@/api/note'
import { formatDate } from '@/utils/dateUtils'

// 定义拓展笔记类型
type ExpansionNote = {
  id: number
  title: string
  content: string
  type: string
  summary?: string
  project: string
  knowledgePoint: string[]
  reviewCount?: number
  createTime?: string
  recCreateTime?: string
  lastReviewTime?: string
  originalText?: string
  understanding?: string
}

// 响应式数据
const expansionNotesData = ref<ExpansionNote[]>([])
const recommendedNotes = ref<ExpansionNote[]>([])

// 加载拓展笔记
const loadNotes = async () => {
  try {
    const notes = await getNotesByType('expansion')
    expansionNotesData.value = notes.map((note: any) => ({
      ...note,
      knowledgePoint: Array.isArray(note.knowledgePoint) ? note.knowledgePoint : [],
      reviewCount: note.reviewCount || 0,
      project: note.project || '未分类'
    })) as ExpansionNote[]
  } catch (error) {
    console.error('加载拓展笔记失败:', error)
  }
}

// 加载推荐巩固笔记
const loadRecommendedNotes = async () => {
  try {
    const response = await getRecommendedNotes('expansion')
    if (response.data && Array.isArray(response.data)) {
      recommendedNotes.value = response.data.map((note: any) => ({
        ...note,
        knowledgePoint: Array.isArray(note.knowledgePoint) ? note.knowledgePoint : [],
        reviewCount: note.reviewCount || 0,
        project: note.project || '未分类'
      }))
    }
  } catch (error) {
    console.error('加载推荐笔记失败:', error)
  }
}

// 组件挂载时加载
onMounted(() => {
  loadNotes()
  loadRecommendedNotes()
})

// 组件激活时刷新数据
onActivated(() => {
  loadNotes()
  loadRecommendedNotes()
})

const expansionNotes = computed(() => expansionNotesData.value)

const showNotesList = ref(false)
const showNoteDetail = ref(false)
const selectedNote = ref<ExpansionNote | null>(null)
const searchQuery = ref('')

const filteredNotes = computed(() => {
  let notes = expansionNotes.value
  
  if (searchQuery.value) {
    notes = notes.filter(note => {
      return note.title.includes(searchQuery.value) || 
             (note.summary && note.summary.includes(searchQuery.value))
    })
  }
  
  return notes
})

// Tab切换 - 只有两个Tab：原文记录和理解记录
const tabs = [
  { label: '原文记录', value: 'original' },
  { label: '理解记录', value: 'understanding' }
]
const activeTab = ref('original')

const viewNoteDetail = (note: ExpansionNote) => {
  selectedNote.value = note
  showNoteDetail.value = true
  activeTab.value = 'original'
}

// 完成巩固
const handleCompleteReview = async () => {
  if (!selectedNote.value) return
  
  try {
    await completeReview(selectedNote.value.id)
    // 重新加载推荐列表和所有笔记
    await loadRecommendedNotes()
    await loadNotes()
    showNoteDetail.value = false
    selectedNote.value = null
  } catch (error) {
    console.error('完成巩固失败:', error)
    alert('操作失败，请重试')
  }
}

// 格式化最后巩固时间
const formatLastReviewTime = (lastReviewTime: string | undefined) => {
  if (!lastReviewTime) return '未巩固'
  
  const now = new Date()
  const reviewTime = new Date(lastReviewTime)
  const diffDays = Math.floor((now.getTime() - reviewTime.getTime()) / (1000 * 60 * 60 * 24))
  
  if (diffDays === 0) return '今天'
  if (diffDays === 1) return '昨天'
  if (diffDays < 7) return `${diffDays}天前`
  if (diffDays < 30) return `${Math.floor(diffDays / 7)}周前`
  return `${Math.floor(diffDays / 30)}个月前`
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

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>

