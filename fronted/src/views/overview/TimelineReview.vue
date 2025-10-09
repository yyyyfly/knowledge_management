<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50 p-6">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-3xl font-bold text-gray-900 flex items-center space-x-3">
            <div class="w-12 h-12 bg-gradient-to-br from-blue-500 to-purple-600 rounded-xl flex items-center justify-center">
              <i class="fas fa-history text-white text-xl"></i>
            </div>
            <span>时光轨迹</span>
          </h1>
          <p class="mt-2 text-gray-600">时光轴记录，回顾学习与成长的每一步足迹</p>
        </div>
        
        <!-- 视图模式切换 -->
        <div class="flex items-center space-x-4 relative z-20">
          <div class="bg-white rounded-lg p-1 shadow-md flex space-x-1">
            <button 
              @click="viewMode = 'global'"
              :class="[
                'px-4 py-2 rounded-md transition-all duration-200',
                viewMode === 'global' 
                  ? 'bg-gradient-to-r from-blue-500 to-purple-600 text-white shadow-md' 
                  : 'text-gray-600 hover:bg-gray-100'
              ]"
            >
              <i class="fas fa-globe mr-2"></i>全局历史
            </button>
            <button 
              @click="viewMode = 'topic'"
              :class="[
                'px-4 py-2 rounded-md transition-all duration-200',
                viewMode === 'topic' 
                  ? 'bg-gradient-to-r from-blue-500 to-purple-600 text-white shadow-md' 
                  : 'text-gray-600 hover:bg-gray-100'
              ]"
            >
              <i class="fas fa-project-diagram mr-2"></i>专项时间轴
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 寄语卡片 -->
    <div class="mb-8">
      <div class="bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50 rounded-2xl shadow-lg p-8 border border-blue-100">
        <div class="flex items-start space-x-6">
          <div class="flex-shrink-0">
            <div class="w-16 h-16 bg-gradient-to-br from-blue-500 to-purple-600 rounded-2xl shadow-lg flex items-center justify-center">
              <i class="fas fa-quote-left text-white text-2xl"></i>
            </div>
          </div>
            <div class="flex-1">
              <h3 class="text-3xl font-extrabold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent mb-4">
                时光如梭，记录永恒
              </h3>
              <p class="text-gray-700 leading-relaxed text-lg mb-3 font-semibold">
                我们走过的每一步，都会在时间里留下光亮。那些写下的字句、完成的项目、短暂的顿悟，
                都是不被风吹散的证据，提醒我们：今天的努力，正在成为明天的答案。
              </p>
              <p class="text-gray-700 leading-relaxed text-lg font-semibold mb-3">
                愿你在回望时光轨迹的每一刻，都能与更好的自己相遇；
                愿每一个里程碑，不止是抵达，更是再次出发的勇气。
              </p>
              <p class="text-gray-700 leading-relaxed text-lg font-semibold mb-3">
                时间不会辜负每一次认真，知识也会在反复书写中沉淀为能力。
                把日常的点滴连接起来，便是你独一无二的成长路径。
              </p>
              <p class="text-gray-700 leading-relaxed text-lg font-semibold">
                愿我们把平凡的日子串成一条路，把微小的进步积累成海；
                当下的记录，都会成为将来某一刻笃定与自信的来源。
              </p>
            </div>
          <div class="flex-shrink-0">
            <div class="w-16 h-16 bg-gradient-to-br from-purple-500 to-pink-600 rounded-2xl shadow-lg flex items-center justify-center">
              <i class="fas fa-heart text-white text-2xl"></i>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 全局历史视图 -->
    <div v-if="viewMode === 'global'" class="bg-white rounded-xl shadow-lg p-8">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-xl font-bold text-gray-900 flex items-center">
          <i class="fas fa-globe text-blue-600 mr-2"></i>
          全局历史事件
        </h2>
        <!-- 时间粒度选择 -->
        <div class="flex items-center space-x-2 relative z-10">
          <button 
            v-for="level in timeLevels" 
            :key="level.value"
            @click="selectedTimeLevel = level.value"
            :class="[
              'px-4 py-2 rounded-lg transition-all duration-200 text-sm',
              selectedTimeLevel === level.value
                ? 'bg-blue-500 text-white'
                : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
            ]"
          >
            {{ level.label }}
          </button>
        </div>
      </div>

      <!-- 按时间分级展示 -->
      <div class="space-y-12">
        <div v-for="period in groupedEvents" :key="period.period" class="relative">
          <!-- 时间段标题 -->
          <div class="sticky top-0 z-[5] bg-gradient-to-r from-blue-50 to-purple-50 rounded-2xl p-4 mb-6 backdrop-blur-sm bg-opacity-90 shadow-sm">
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <div class="w-10 h-10 bg-gradient-to-br from-blue-500 to-purple-600 rounded-xl flex items-center justify-center shadow-md">
                  <i class="fas fa-calendar-alt text-white text-sm"></i>
                </div>
              </div>
              <div class="ml-4 flex-1">
                <h3 class="text-xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">{{ period.period }}</h3>
                <p class="text-xs text-gray-600 mt-0.5">{{ period.events.length }} 个事件</p>
              </div>
              <button 
                @click="togglePeriod(period.period)"
                class="w-8 h-8 rounded-lg bg-white shadow-sm flex items-center justify-center text-gray-400 hover:text-blue-600 hover:bg-blue-50 transition-all duration-200"
              >
                <i :class="expandedPeriods.includes(period.period) ? 'fas fa-chevron-up' : 'fas fa-chevron-down'" class="text-sm"></i>
              </button>
            </div>
          </div>

          <!-- 事件列表（可折叠） -->
          <Transition name="slide-fade">
            <div v-show="expandedPeriods.includes(period.period)" class="relative">
              <!-- 时间轴线 -->
              <div class="absolute left-6 top-0 bottom-0 w-0.5 bg-gradient-to-b from-blue-200 via-purple-200 to-transparent"></div>
              
              <div class="space-y-4">
                <div 
                  v-for="(event, index) in period.events" 
                  :key="index"
                  class="relative pl-16 group"
                >
                  <!-- 时间轴节点 -->
                  <div class="absolute left-3 top-6 w-6 h-6 rounded-full border-4 border-white shadow-md transition-transform duration-200 group-hover:scale-110"
                    :class="getEventNodeColor(event.type)"
                  >
                  </div>
                  
                  <!-- 事件卡片 -->
                  <div class="bg-white rounded-2xl shadow-sm hover:shadow-xl transition-all duration-300 overflow-hidden border border-gray-100 group-hover:border-blue-200 relative">
                    <!-- 删除按钮 -->
                    <button 
                      @click="deleteGlobalEvent(period.period, index)"
                      class="absolute top-3 right-3 w-8 h-8 rounded-lg bg-red-50 text-red-500 hover:bg-red-500 hover:text-white transition-all duration-200 flex items-center justify-center opacity-0 group-hover:opacity-100 z-10"
                      title="删除此事件"
                    >
                      <i class="fas fa-trash-alt text-sm"></i>
                    </button>
                    
                    <!-- 日期标签 -->
                    <div class="bg-gradient-to-r from-gray-50 to-transparent px-6 py-3 border-b border-gray-100">
                      <div class="flex items-center space-x-2 text-xs font-medium text-gray-600">
                        <i class="far fa-calendar text-blue-500"></i>
                        <span>{{ event.date }}</span>
                      </div>
                    </div>
                    
                    <!-- 内容区域 -->
                    <div class="px-6 py-4">
                      <h4 class="text-lg font-semibold text-gray-900 mb-2 group-hover:text-blue-600 transition-colors">{{ event.title }}</h4>
                      <p v-if="event.description" class="text-sm text-gray-600 leading-relaxed">{{ event.description }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Transition>
        </div>
      </div>
    </div>

    <!-- 专项时间轴视图 -->
    <div v-else class="bg-white rounded-xl shadow-lg p-8">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-xl font-bold text-gray-900 flex items-center">
          <i class="fas fa-project-diagram text-blue-600 mr-2"></i>
          专项时间轴
        </h2>
        <!-- 主题选择 -->
        <select 
          v-model="selectedTopic" 
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 relative z-10"
        >
          <option value="">选择主题</option>
          <option v-for="topic in topicList" :key="topic.id" :value="topic.id">
            {{ topic.name }}
          </option>
        </select>
      </div>

      <!-- 主题详情 -->
      <div v-if="selectedTopicData" class="mb-8 p-6 bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50 rounded-2xl border border-blue-100 shadow-sm">
        <div class="flex items-center space-x-4">
          <div class="w-16 h-16 bg-gradient-to-br from-blue-500 to-purple-600 rounded-2xl shadow-lg flex items-center justify-center">
            <i class="fas fa-project-diagram text-3xl text-white"></i>
          </div>
          <div class="flex-1">
            <h3 class="text-xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">{{ selectedTopicData.name }}</h3>
            <div class="flex items-center space-x-2 mt-1">
              <i class="far fa-calendar text-blue-500 text-xs"></i>
              <span class="text-sm text-gray-600">起始：{{ selectedTopicData.startDate }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 演进时间轴 -->
      <div v-if="selectedTopicData" class="relative">
        <!-- 时间轴主线 -->
        <div class="absolute left-8 top-0 bottom-0 w-0.5 bg-gradient-to-b from-blue-200 via-purple-200 to-transparent"></div>

        <!-- 演进节点 -->
        <div class="space-y-6 relative">
          <div 
            v-for="(milestone, index) in selectedTopicData.timeline" 
            :key="index"
            class="relative pl-16 group"
          >
            <!-- 节点图标 -->
            <div class="absolute left-2 top-5 flex items-center">
              <div class="w-10 h-10 rounded-full bg-gradient-to-br from-blue-500 to-purple-600 border-4 border-white shadow-xl flex items-center justify-center transition-all duration-200 group-hover:scale-110">
                <i class="fas fa-flag text-white text-sm"></i>
              </div>
            </div>

            <!-- 里程碑卡片 -->
            <div class="bg-white rounded-2xl shadow-sm hover:shadow-xl transition-all duration-300 overflow-hidden border border-gray-100 group-hover:border-blue-200 relative">
              <!-- 删除按钮 -->
              <button 
                @click="deleteMilestone(selectedTopic, index)"
                class="absolute top-3 right-3 w-8 h-8 rounded-lg bg-red-50 text-red-500 hover:bg-red-500 hover:text-white transition-all duration-200 flex items-center justify-center opacity-0 group-hover:opacity-100 z-10"
                title="删除此里程碑"
              >
                <i class="fas fa-trash-alt text-sm"></i>
              </button>
              
              <!-- 头部标签 -->
              <div class="bg-gradient-to-r from-gray-50 to-transparent px-6 py-3 border-b border-gray-100">
                <div class="flex items-center space-x-2">
                  <i class="far fa-calendar text-blue-500 text-xs"></i>
                  <span class="text-xs font-medium text-gray-600">{{ milestone.date }}</span>
                </div>
              </div>
              
              <!-- 内容区域 -->
              <div class="px-6 py-4">
                <h4 class="text-lg font-semibold text-gray-900 mb-2 group-hover:text-blue-600 transition-colors">{{ milestone.title }}</h4>
                <p v-if="milestone.description" class="text-sm text-gray-600 leading-relaxed">{{ milestone.description }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 未选择主题提示 -->
      <div v-else class="text-center py-16">
        <div class="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
          <i class="fas fa-folder-open text-gray-400 text-4xl"></i>
        </div>
        <p class="text-gray-500">请选择一个主题查看演进历史</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated, watch } from 'vue'

// 视图模式
const viewMode = ref<'global' | 'topic'>('global')

// ===== 全局历史视图 =====
// 时间粒度
const timeLevels = [
  { label: '按年', value: 'year' },
  { label: '按月', value: 'month' }
]
const selectedTimeLevel = ref('month')

// 展开的时间段
const expandedPeriods = ref<string[]>([])

// 全局事件数据
const globalEvents = ref<any[]>([])

// 按时间分组的事件
const groupedEvents = computed(() => {
  const groups: Record<string, any[]> = {}
  
  globalEvents.value.forEach(event => {
    let period = ''
    const date = new Date(event.date)
    
    if (selectedTimeLevel.value === 'year') {
      period = `${date.getFullYear()}年`
    } else {
      period = `${date.getFullYear()}年${date.getMonth() + 1}月`
    }
    
    if (!groups[period]) {
      groups[period] = []
    }
    groups[period].push(event)
  })
  
  return Object.entries(groups)
    .map(([period, events]) => ({ period, events }))
    .sort((a, b) => b.period.localeCompare(a.period))
})

// 切换时间段展开/折叠
const togglePeriod = (period: string) => {
  const index = expandedPeriods.value.indexOf(period)
  if (index > -1) {
    expandedPeriods.value.splice(index, 1)
  } else {
    expandedPeriods.value.push(period)
  }
}

// ===== 专项时间轴视图 =====
const selectedTopic = ref('')
const topicList = ref<any[]>([])

const selectedTopicData = computed(() => {
  return topicList.value.find(t => t.id === selectedTopic.value)
})

// ===== 样式辅助函数 =====
const getEventBorderColor = (type: string) => {
  const colors: Record<string, string> = {
    note: 'border-blue-500',
    project: 'border-green-500',
    summary: 'border-purple-500',
    milestone: 'border-orange-500',
    task: 'border-red-500'
  }
  return colors[type] || 'border-gray-500'
}

const getEventNodeColor = (type: string) => {
  const colors: Record<string, string> = {
    note: 'bg-blue-500',
    project: 'bg-green-500',
    summary: 'bg-purple-500',
    milestone: 'bg-orange-500',
    task: 'bg-red-500'
  }
  return colors[type] || 'bg-gray-500'
}

const getEventBadgeColor = (type: string) => {
  const colors: Record<string, string> = {
    note: 'bg-blue-100 text-blue-700',
    project: 'bg-green-100 text-green-700',
    summary: 'bg-purple-100 text-purple-700',
    milestone: 'bg-orange-100 text-orange-700',
    task: 'bg-red-100 text-red-700'
  }
  return colors[type] || 'bg-gray-100 text-gray-700'
}

const getEventTypeName = (type: string) => {
  const names: Record<string, string> = {
    note: '笔记',
    project: '项目',
    summary: '总结',
    milestone: '里程碑',
    task: '任务'
  }
  return names[type] || '其他'
}

const getMilestoneColor = (status: string) => {
  const colors: Record<string, string> = {
    completed: 'bg-green-500',
    ongoing: 'bg-blue-500',
    planned: 'bg-gray-400'
  }
  return colors[status] || 'bg-gray-500'
}

const getMilestoneIcon = (status: string) => {
  const icons: Record<string, string> = {
    completed: 'fas fa-check',
    ongoing: 'fas fa-circle-notch fa-spin',
    planned: 'fas fa-clock'
  }
  return icons[status] || 'fas fa-circle'
}

const getMilestoneBadge = (status: string) => {
  const badges: Record<string, string> = {
    completed: 'bg-green-100 text-green-700',
    ongoing: 'bg-blue-100 text-blue-700',
    planned: 'bg-gray-100 text-gray-700'
  }
  return badges[status] || 'bg-gray-100 text-gray-700'
}

// ===== 日期格式化辅助函数 =====
const formatDate = (dateInput: any): string => {
  if (!dateInput) return new Date().toISOString().split('T')[0]
  
  // 如果是字符串，尝试解析
  if (typeof dateInput === 'string') {
    // ISO 8601 格式: 2025-10-08T16:00:00.000+00:00
    if (dateInput.includes('T')) {
      return dateInput.split('T')[0]
    }
    // 数据库格式: 2025-10-08 16:00:00
    if (dateInput.includes(' ')) {
      return dateInput.split(' ')[0]
    }
    // 已经是纯日期格式
    return dateInput
  }
  
  // 如果是Date对象
  if (dateInput instanceof Date) {
    return dateInput.toISOString().split('T')[0]
  }
  
  // 兜底：尝试转换为Date对象
  try {
    const date = new Date(dateInput)
    return date.toISOString().split('T')[0]
  } catch {
    return new Date().toISOString().split('T')[0]
  }
}

// ===== 数据加载 =====
const loadGlobalEvents = async () => {
  try {
    const request = (await import('@/api/request')).default
    const response = await request.get('/timeline/global')
    if (response.code === 200) {
      // 转换后端数据格式
      globalEvents.value = response.data.map((event: any) => ({
        id: event.id,
        type: 'milestone',
        date: formatDate(event.eventDate),
        title: event.title,
        description: event.description
      }))
      
      // 默认展开最近的时间段
      if (groupedEvents.value.length > 0) {
        expandedPeriods.value = [groupedEvents.value[0].period]
      }
    }
  } catch (error) {
    console.error('加载全局历史事件失败:', error)
  }
}

const loadTopicList = async () => {
  try {
    const request = (await import('@/api/request')).default
    const response = await request.get('/timeline/milestones')
    if (response.code === 200) {
      topicList.value = response.data.topicList || []
    }
  } catch (error) {
    console.error('加载专项时间轴失败:', error)
  }
}

// ===== 删除功能 =====
const deleteGlobalEvent = async (period: string, eventIndex: number) => {
  if (!confirm('确定要删除这个历史事件吗？')) {
    return
  }
  
  // 从globalEvents中找到对应的事件
  const allEvents = globalEvents.value
  let currentIndex = 0
  let targetEvent: any = null
  let targetArrayIndex = -1
  
  for (let i = 0; i < allEvents.length; i++) {
    const event = allEvents[i]
    const date = new Date(event.date)
    let eventPeriod = ''
    
    if (selectedTimeLevel.value === 'year') {
      eventPeriod = `${date.getFullYear()}年`
    } else {
      eventPeriod = `${date.getFullYear()}年${date.getMonth() + 1}月`
    }
    
    if (eventPeriod === period) {
      if (currentIndex === eventIndex) {
        targetEvent = event
        targetArrayIndex = i
        break
      }
      currentIndex++
    }
  }
  
  if (!targetEvent || !targetEvent.id) {
    alert('未找到要删除的事件')
    return
  }
  
  try {
    const request = (await import('@/api/request')).default
    const response = await request.delete(`/timeline/${targetEvent.id}`)
    
    if (response.code === 200) {
      // 从本地数组删除
      globalEvents.value.splice(targetArrayIndex, 1)
      alert('✅ 删除成功')
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除全局事件失败:', error)
    alert('删除失败，请稍后重试')
  }
}

const deleteMilestone = async (topicId: string, milestoneIndex: number) => {
  if (!confirm('确定要删除这个里程碑吗？')) {
    return
  }
  
  const topic = topicList.value.find(t => t.id === topicId)
  if (!topic || !topic.timeline || !topic.timeline[milestoneIndex]) {
    alert('未找到要删除的里程碑')
    return
  }
  
  const milestone = topic.timeline[milestoneIndex]
  
  if (!milestone.id) {
    alert('里程碑数据异常：缺少ID')
    return
  }
  
  try {
    const request = (await import('@/api/request')).default
    const response = await request.delete(`/timeline/${milestone.id}`)
    
    if (response.code === 200) {
      // 从本地数组删除
      topic.timeline.splice(milestoneIndex, 1)
      alert('✅ 删除成功')
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除里程碑失败:', error)
    alert('删除失败，请稍后重试')
  }
}

// 监听时间粒度变化
watch(selectedTimeLevel, () => {
  expandedPeriods.value = []
  if (groupedEvents.value.length > 0) {
    expandedPeriods.value = [groupedEvents.value[0].period]
  }
})

onMounted(() => {
  loadGlobalEvents()
  loadTopicList()
})

onActivated(() => {
  loadGlobalEvents()
  loadTopicList()
})
</script>

<style scoped>
/* 折叠展开过渡动画 */
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.2s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}

/* 时间轴动画 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.relative > div {
  animation: fadeInUp 0.6s ease-out;
}
</style>

