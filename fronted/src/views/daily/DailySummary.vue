<template>
      <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">日常总结</h1>
        <span class="bg-red-100 text-red-600 px-3 py-1 rounded-full text-sm font-medium">每日必填</span>
      </div>
      <p class="text-gray-600">记录每日工作与成长，为系统总结积累素材</p>
    </div>
  <section id="daily-summary" class="p-6 animate-fade-in bg-gray-50 rounded-2xl">
    <!-- 页面头部 -->

    <!-- 上下布局 -->
    <div class="space-y-8">
      <!-- 第一部分：日报表单 -->
      <div class="space-y-6">
        <div class="flex items-center space-x-3 mb-8">
          <div class="flex items-center space-x-3">
            <div class="w-2 h-12 bg-blue-500 rounded-full"></div>
            <h1 class="text-3xl font-bold text-gray-900">日报表单</h1>
          </div>
        </div>

        <!-- 日报模板外层容器 -->
        <div class="bg-white shadow-lg rounded-2xl p-6 border-l-4 border-blue-500">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-bold text-gray-900">选择日报模板</h3>
          </div>

          <div class="bg-blue-50 rounded-xl p-3 mb-4 border-l-4 border-blue-400">
            <p class="text-sm text-gray-700 flex items-start">
              <i class="fas fa-info-circle text-blue-600 mr-2 mt-0.5"></i>
              <span>选择合适的日报模板，填写今日工作与成长内容。</span>
            </p>
      </div>

          <!-- 日报模板选择 -->
          <div class="grid grid-cols-2 gap-3">
            <button 
              type="button"
              @click="toggleDailyTemplate('general')"
              class="p-4 rounded-xl border-2 transition-all text-left"
              :class="selectedDailyTemplate === 'general' ? 'border-blue-500 bg-blue-50' : 'border-gray-200 hover:border-blue-300'"
            >
              <div class="flex items-center space-x-3">
                <div class="w-10 h-10 rounded-lg flex items-center justify-center" :class="selectedDailyTemplate === 'general' ? 'bg-blue-500' : 'bg-gray-200'">
                  <i class="fas fa-file-alt text-lg" :class="selectedDailyTemplate === 'general' ? 'text-white' : 'text-gray-500'"></i>
                </div>
                <div class="flex-1">
                  <div class="font-semibold text-base text-gray-900">通用日报</div>
                  <div class="text-xs text-gray-500 mt-0.5">记录每日工作与成长</div>
                </div>
                <div class="flex items-center text-blue-600">
                  <i class="fas text-lg" :class="selectedDailyTemplate === 'general' ? 'fa-chevron-up' : 'fa-chevron-down'"></i>
                </div>
              </div>
          </button>
            
            <!-- 预留：未来可添加更多模板 -->
            <div class="p-4 rounded-xl border-2 border-dashed border-gray-300 flex items-center justify-center text-gray-400">
              <i class="fas fa-plus-circle mr-2"></i>
              <span class="text-sm">预留更多模板...</span>
            </div>
        </div>

          <!-- 通用日报表单内容 -->
          <transition name="slide-fade">
          <div v-show="selectedDailyTemplate === 'general'" class="mt-4">
        <form @submit.prevent="submitDaily" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">
                <i class="fas fa-heading text-blue-500 mr-2"></i>日报标题
              </label>
              <input 
                v-model="dailyForm.title" 
                type="text" 
                class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-200" 
                placeholder="输入日报标题"
              >
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">
                <i class="fas fa-calendar-alt text-blue-500 mr-2"></i>日期
              </label>
              <div class="relative">
                <input 
                  v-model="dailyForm.period" 
                  type="date" 
                  class="date-input w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-200 cursor-pointer hover:border-blue-400" 
                  :max="today"
                >
                <div class="absolute right-3 top-1/2 -translate-y-1/2 pointer-events-none text-gray-400">
                  <i class="fas fa-calendar text-sm"></i>
                </div>
              </div>
            </div>
          </div>
          <h4 class="text-lg mt-4 mb-2 tracking-wide text-shadow font-normal"><i class="fas fa-tasks text-blue-500 mr-2"></i>今日任务完成情况（含耗时）</h4>
          <div class="mb-6">
            <div v-for="(task, idx) in dailyForm.content.dailyTasks" :key="idx" class="flex items-center space-x-2 mb-2">
              <input v-model="task.content" class="flex-1 px-3 py-2 border rounded" placeholder="任务内容">
              <input v-model="task.duration" class="w-24 px-3 py-2 border rounded" placeholder="耗时">
              <button type="button" @click="dailyForm.content.dailyTasks.splice(idx,1)" v-if="dailyForm.content.dailyTasks.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
            </div>
          </div>
          <button type="button" @click="dailyForm.content.dailyTasks.push({content:'',duration:''})" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加任务</button>
          <h4 class="text-lg mt-4 mb-2 tracking-wide text-shadow font-normal"><i class="fas fa-bolt text-orange-500 mr-2"></i>过程中的问题与应对方式</h4>
          <div class="mb-6">
            <div v-for="(prob, idx) in dailyForm.content.dailyProblems" :key="idx" class="flex items-center space-x-2 mb-2">
              <input v-model="prob.problem" class="flex-1 px-3 py-2 border rounded" placeholder="问题描述">
              <input v-model="prob.solution" class="flex-1 px-3 py-2 border rounded" placeholder="应对方法">
              <button type="button" @click="dailyForm.content.dailyProblems.splice(idx,1)" v-if="dailyForm.content.dailyProblems.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
            </div>
          </div>
          <button type="button" @click="dailyForm.content.dailyProblems.push({problem:'',solution:''})" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加问题</button>
          <h4 class="text-lg mt-4 mb-2 tracking-wide text-shadow font-normal"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>今日收获</h4>
          <div class="mb-6">
            <div v-for="(item, idx) in dailyForm.content.dailyHighlights" :key="idx" class="flex items-center space-x-2 mb-2">
              <input v-model="dailyForm.content.dailyHighlights[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="收获/亮点">
              <button type="button" @click="dailyForm.content.dailyHighlights.splice(idx,1)" v-if="dailyForm.content.dailyHighlights.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
            </div>
          </div>
          <button type="button" @click="dailyForm.content.dailyHighlights.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加收获</button>
          <h4 class="text-lg mt-4 mb-2 tracking-wide text-shadow font-normal"><i class="fas fa-quote-right text-purple-500 mr-2"></i>简要总结段</h4>
          <div class="mb-6">
            <textarea v-model="dailyForm.content.dailySummary" rows="4" class="w-full border rounded px-3 py-2" placeholder="今天整体状态如何？是否有感受、意外、反思、思考？"></textarea>
          </div>
          <div class="flex justify-end space-x-3 mt-6 pt-4 border-t border-gray-200">
            <button type="button" @click="resetDailyForm" class="px-5 py-2.5 border-2 border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 hover:border-gray-400 transition-all text-sm font-medium">
              <i class="fas fa-redo mr-2"></i>清空重置
            </button>
            <button type="submit" class="px-6 py-2.5 bg-gradient-to-r from-blue-600 to-blue-700 text-white rounded-lg hover:from-blue-700 hover:to-blue-800 shadow-lg hover:shadow-xl transition-all text-sm font-medium">
              <i class="fas fa-save mr-2"></i>保存并记录历史
            </button>
          </div>
        </form>
      </div>
    </transition>
        </div>
      </div>

      <!-- 第二部分：历史事件记录 -->
      <div class="space-y-6">
        <div class="flex items-center space-x-3 mb-8">
          <div class="flex items-center space-x-3">
            <div class="w-2 h-12 bg-purple-500 rounded-full"></div>
            <h1 class="text-3xl font-bold text-gray-900">历史事件记录</h1>
          </div>
        </div>
        
          <!-- 历史事件外层容器 -->
        <div class="bg-white shadow-lg rounded-2xl p-6 border-l-4 border-purple-500">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-bold text-gray-900">记录历史事件</h3>
          </div>

          <div class="bg-blue-50 rounded-xl p-3 mb-4 border-l-4 border-blue-400">
            <p class="text-sm text-gray-700 flex items-start">
              <i class="fas fa-info-circle text-blue-600 mr-2 mt-0.5"></i>
              <span>记录今日值得载入史册的重要事件，这些事件将同步显示在「时光轨迹」页面中。</span>
            </p>
          </div>

          <!-- 选择事件类型 -->
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-3">
                <i class="fas fa-layer-group text-purple-500 mr-2"></i>事件类型
              </label>
              <div class="grid grid-cols-2 gap-3">
                <button 
                  type="button"
                  @click="toggleHistoryEventType('global')"
                  class="p-4 rounded-xl border-2 transition-all text-left"
                  :class="historyEventType === 'global' ? 'border-purple-500 bg-purple-50' : 'border-gray-200 hover:border-purple-300'"
                >
                  <div class="flex items-center space-x-3">
                    <div class="w-10 h-10 rounded-lg flex items-center justify-center" :class="historyEventType === 'global' ? 'bg-purple-500' : 'bg-gray-200'">
                      <i class="fas fa-globe text-lg" :class="historyEventType === 'global' ? 'text-white' : 'text-gray-500'"></i>
                    </div>
                    <div class="flex-1">
                      <div class="font-semibold text-base text-gray-900">全局历史事件</div>
                      <div class="text-xs text-gray-500 mt-0.5">记录日常重要事件</div>
                    </div>
                    <div class="flex items-center text-purple-600">
                      <i class="fas text-lg transition-transform duration-200" :class="historyEventType === 'global' ? 'fa-chevron-up' : 'fa-chevron-down'"></i>
                    </div>
                  </div>
                </button>

                <button 
                  type="button"
                  @click="toggleHistoryEventType('milestone')"
                  class="p-4 rounded-xl border-2 transition-all text-left"
                  :class="historyEventType === 'milestone' ? 'border-purple-500 bg-purple-50' : 'border-gray-200 hover:border-purple-300'"
                >
                  <div class="flex items-center space-x-3">
                    <div class="w-10 h-10 rounded-lg flex items-center justify-center" :class="historyEventType === 'milestone' ? 'bg-purple-500' : 'bg-gray-200'">
                      <i class="fas fa-flag text-lg" :class="historyEventType === 'milestone' ? 'text-white' : 'text-gray-500'"></i>
                    </div>
                    <div class="flex-1">
                      <div class="font-semibold text-base text-gray-900">专项时间轴</div>
                      <div class="text-xs text-gray-500 mt-0.5">记录主题/项目里程碑</div>
                    </div>
                    <div class="flex items-center text-purple-600">
                      <i class="fas text-lg transition-transform duration-200" :class="historyEventType === 'milestone' ? 'fa-chevron-up' : 'fa-chevron-down'"></i>
                    </div>
                  </div>
                </button>
              </div>
            </div>

            <!-- 全局历史事件表单 -->
            <transition name="slide-fade">
            <div v-show="historyEventType === 'global'" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-flag text-purple-500 mr-2"></i>事件标题
                </label>
                <input 
                  v-model="historyForm.title" 
                  type="text" 
                  class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200" 
                  placeholder="例如：完成第一个Vue项目、通过CET-6考试"
                >
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-align-left text-purple-500 mr-2"></i>事件描述
                </label>
                <textarea 
                  v-model="historyForm.description" 
                  rows="3" 
                  class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200" 
                  placeholder="描述这个事件的详细内容和意义..."
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-calendar text-purple-500 mr-2"></i>事件日期
                </label>
                <input 
                  v-model="historyForm.date" 
                  type="date" 
                  :max="today"
                  class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200 date-input"
                >
              </div>

              <div class="flex justify-end space-x-3 mt-6 pt-4 border-t border-gray-200">
                <button 
                  type="button" 
                  @click.prevent.stop="resetHistoryForm" 
                  class="px-5 py-2.5 border-2 border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 hover:border-gray-400 transition-all text-sm font-medium"
                >
                  <i class="fas fa-redo mr-2"></i>清空
                </button>
                <button 
                  type="button" 
                  @click.prevent.stop="addHistoryEvent" 
                  :disabled="!historyForm.title"
                  class="px-6 py-2.5 bg-gradient-to-r from-purple-600 to-purple-700 text-white rounded-lg hover:from-purple-700 hover:to-purple-800 disabled:opacity-50 disabled:cursor-not-allowed shadow-lg hover:shadow-xl transition-all text-sm font-medium"
                >
                  <i class="fas fa-chart-line mr-2"></i>添加到时间轴
                </button>
              </div>
            </div>
            </transition>

            <!-- 专项时间轴里程碑表单 -->
            <transition name="slide-fade">
            <div v-show="historyEventType === 'milestone'" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-project-diagram text-purple-500 mr-2"></i>选择主题/项目
                </label>
                <div class="flex space-x-3">
                  <select 
                    v-model="milestoneForm.topicId" 
                    class="flex-1 px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200"
                  >
                    <option value="">选择已有主题</option>
                    <option v-for="topic in availableTopics" :key="topic.id" :value="topic.id">
                      {{ topic.name }}
                    </option>
                  </select>
                  <button 
                    type="button"
                    @click="showEditTopicInput"
                    :disabled="!milestoneForm.topicId"
                    class="px-4 py-2.5 bg-blue-100 text-blue-700 rounded-lg hover:bg-blue-200 disabled:opacity-50 disabled:cursor-not-allowed transition-colors font-medium"
                    title="编辑选中的主题名称"
                  >
                    <i class="fas fa-edit"></i>
                  </button>
                  <button 
                    type="button"
                    @click="deleteTopic"
                    :disabled="!milestoneForm.topicId"
                    class="px-4 py-2.5 bg-red-100 text-red-700 rounded-lg hover:bg-red-200 disabled:opacity-50 disabled:cursor-not-allowed transition-colors font-medium"
                    title="删除选中的主题及其所有里程碑"
                  >
                    <i class="fas fa-trash-alt"></i>
                  </button>
                  <button 
                    type="button"
                    @click="showNewTopicInput = !showNewTopicInput"
                    class="px-4 py-2.5 bg-purple-100 text-purple-700 rounded-lg hover:bg-purple-200 transition-colors font-medium"
                  >
                    <i class="fas fa-plus mr-1"></i>新建主题
                  </button>
                </div>
              </div>

              <!-- 编辑主题输入框 -->
              <div v-if="isEditingTopic" class="bg-blue-50 rounded-xl p-4 border-l-4 border-blue-400">
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-edit text-blue-500 mr-2"></i>编辑主题名称
                </label>
                <div class="flex space-x-3">
                  <input 
                    v-model="editTopicName" 
                    type="text" 
                    class="flex-1 px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-200" 
                    placeholder="请输入新的主题名称"
                    @keyup.enter="confirmEditTopic"
                  >
                  <button 
                    type="button"
                    @click="confirmEditTopic"
                    :disabled="!editTopicName.trim()"
                    class="px-4 py-2.5 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed transition-colors font-medium"
                  >
                    <i class="fas fa-check mr-1"></i>保存
                  </button>
                  <button 
                    type="button"
                    @click="cancelEditTopic"
                    class="px-4 py-2.5 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition-colors font-medium"
                  >
                    <i class="fas fa-times mr-1"></i>取消
                  </button>
                </div>
              </div>

              <!-- 新建主题输入框 -->
              <div v-if="showNewTopicInput" class="bg-purple-50 rounded-xl p-4 border-l-4 border-purple-400">
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-plus-circle text-purple-500 mr-2"></i>新主题名称
                </label>
                <div class="flex space-x-3">
                  <input 
                    v-model="milestoneForm.newTopicName" 
                    type="text" 
                    class="flex-1 px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200" 
                    placeholder="例如：Spring Boot学习历程、毕业设计开发记录"
                    @keyup.enter="confirmNewTopic"
                  >
                  <button 
                    type="button"
                    @click="confirmNewTopic"
                    :disabled="!milestoneForm.newTopicName.trim()"
                    class="px-4 py-2.5 bg-purple-600 text-white rounded-lg hover:bg-purple-700 disabled:opacity-50 disabled:cursor-not-allowed transition-colors font-medium"
                  >
                    <i class="fas fa-check mr-1"></i>确认
                  </button>
                  <button 
                    type="button"
                    @click="cancelNewTopic"
                    class="px-4 py-2.5 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition-colors font-medium"
                  >
                    <i class="fas fa-times mr-1"></i>取消
                  </button>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-flag-checkered text-purple-500 mr-2"></i>里程碑标题
                </label>
                <input 
                  v-model="milestoneForm.title" 
                  type="text" 
                  class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200" 
                  placeholder="例如：完成用户认证模块、掌握Vue3 Composition API"
                >
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-align-left text-purple-500 mr-2"></i>里程碑描述
                </label>
                <textarea 
                  v-model="milestoneForm.description" 
                  rows="3" 
                  class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200" 
                  placeholder="描述这个里程碑的详细内容、技术要点或重要意义..."
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-calendar text-purple-500 mr-2"></i>里程碑日期
                </label>
                <input 
                  v-model="milestoneForm.date" 
                  type="date" 
                  :max="today"
                  class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-200 date-input"
                >
              </div>

              <div class="flex justify-end space-x-3 mt-6 pt-4 border-t border-gray-200">
                <button 
                  type="button" 
                  @click.prevent.stop="resetHistoryForm" 
                  class="px-5 py-2.5 border-2 border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 hover:border-gray-400 transition-all text-sm font-medium"
                >
                  <i class="fas fa-redo mr-2"></i>清空
                </button>
                <button 
                  type="button" 
                  @click.prevent.stop="addMilestone" 
                  :disabled="!milestoneForm.title || (!milestoneForm.topicId && !milestoneForm.newTopicName)"
                  class="px-6 py-2.5 bg-gradient-to-r from-purple-600 to-purple-700 text-white rounded-lg hover:from-purple-700 hover:to-purple-800 disabled:opacity-50 disabled:cursor-not-allowed shadow-lg hover:shadow-xl transition-all text-sm font-medium"
                >
                  <i class="fas fa-chart-line mr-2"></i>添加到时间轴
                </button>
              </div>
            </div>
            </transition>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated } from 'vue'
import request from '@/api/request'

const selectedDailyTemplate = ref('general')

// 可用主题列表
const availableTopics = ref<Array<{ id: string, name: string }>>([])

// 加载可用主题列表
const loadAvailableTopics = async () => {
  try {
    const response = await request.get('/timeline/topics')
    
    if (response.code === 200) {
      availableTopics.value = (response.data || []).map((topic: any) => ({
        id: topic.id,
        name: topic.name
      }))
    } else {
      availableTopics.value = []
    }
  } catch (error) {
    console.error('加载主题列表失败:', error)
    availableTopics.value = []
  }
}

// 切换日报模板
const toggleDailyTemplate = (template: string) => {
  if (selectedDailyTemplate.value === template) {
    selectedDailyTemplate.value = ''
  } else {
    selectedDailyTemplate.value = template
  }
}

// 获取今天的日期（格式：YYYY-MM-DD）
const today = computed(() => {
  const date = new Date()
  return date.toISOString().split('T')[0]
})

// 初始化表单，默认日期为今天
const dailyForm = ref({
  title: '',
  period: new Date().toISOString().split('T')[0], // 默认为今天
  content: {
    dailyTasks: [{ content: '', duration: '' }],
    dailyProblems: [{ problem: '', solution: '' }],
    dailyHighlights: [''],
    dailySummary: ''
  }
})

const resetDailyForm = () => {
  dailyForm.value = {
    title: '',
    period: new Date().toISOString().split('T')[0], // 重置为今天
    content: {
      dailyTasks: [{ content: '', duration: '' }],
      dailyProblems: [{ problem: '', solution: '' }],
      dailyHighlights: [''],
      dailySummary: ''
    }
  }
}

// ===== 历史事件相关 =====
const historyEventType = ref<'global' | 'milestone' | ''>('')

// 切换历史事件类型
const toggleHistoryEventType = (type: 'global' | 'milestone') => {
  if (historyEventType.value === type) {
    historyEventType.value = ''
  } else {
    historyEventType.value = type
  }
}

// 全局历史事件表单
const historyForm = ref({
  title: '',
  description: '',
  date: new Date().toISOString().split('T')[0] // 默认为今天
})

// 专项时间轴里程碑表单
const milestoneForm = ref({
  topicId: '',
  newTopicName: '',
  title: '',
  description: '',
  date: new Date().toISOString().split('T')[0] // 默认为今天
})

const showNewTopicInput = ref(false)
const isEditingTopic = ref(false)
const editTopicName = ref('')
const editingTopicId = ref('')

const resetHistoryForm = () => {
  if (historyEventType.value === 'global') {
    historyForm.value = {
      title: '',
      description: '',
      date: new Date().toISOString().split('T')[0]
    }
  } else {
    milestoneForm.value = {
      topicId: '',
      newTopicName: '',
      title: '',
      description: '',
      date: new Date().toISOString().split('T')[0]
    }
    showNewTopicInput.value = false
  }
}

const addHistoryEvent = async () => {
  if (!historyForm.value.title) {
    alert('请填写事件标题')
    return
  }

  try {
    const eventData = {
      title: historyForm.value.title,
      description: historyForm.value.description,
      date: historyForm.value.date
    }
    
    const response = await request.post('/timeline/global', eventData)
    
    if (response.code === 200) {
      alert('✅ 历史事件已添加到时间轴！\n\n您可以前往「驾驶舱 > 时光轨迹」页面查看。')
      resetHistoryForm()
    } else {
      alert('保存失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('保存历史事件失败:', error)
    alert('保存失败，请稍后重试')
  }
}

// 确认新建主题
const confirmNewTopic = async () => {
  if (!milestoneForm.value.newTopicName.trim()) {
    alert('请输入主题名称')
    return
  }
  
  // 生成唯一的主题ID
  const newTopicId = `user-topic-${Date.now()}`
  const newTopicName = milestoneForm.value.newTopicName.trim()
  
  try {
    // 调用API保存主题到数据库
    const response = await request.post('/timeline/topic', {
      id: newTopicId,
      name: newTopicName
    })
    
    if (response.code === 200) {
      // 添加到可用主题列表
      availableTopics.value.push({
        id: newTopicId,
        name: newTopicName
      })
      
      // 自动选中新建的主题
      milestoneForm.value.topicId = newTopicId
      
      // 清空输入框并关闭
      milestoneForm.value.newTopicName = ''
      showNewTopicInput.value = false
      
      alert('✅ 新主题已创建并保存到数据库')
    } else {
      alert('创建失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('创建主题失败:', error)
    alert('创建失败，请稍后重试')
  }
}

// 取消新建主题
const cancelNewTopic = () => {
  milestoneForm.value.newTopicName = ''
  showNewTopicInput.value = false
}

// 显示编辑主题输入框
const showEditTopicInput = () => {
  if (!milestoneForm.value.topicId) {
    return
  }
  
  // 关闭新建主题输入框
  showNewTopicInput.value = false
  
  // 找到当前选中的主题
  const selectedTopic = availableTopics.value.find(t => t.id === milestoneForm.value.topicId)
  if (selectedTopic) {
    editingTopicId.value = selectedTopic.id
    editTopicName.value = selectedTopic.name
    isEditingTopic.value = true
  }
}

// 确认编辑主题
const confirmEditTopic = async () => {
  if (!editTopicName.value.trim()) {
    alert('请输入主题名称')
    return
  }
  
  const newName = editTopicName.value.trim()
  const oldTopic = availableTopics.value.find(t => t.id === editingTopicId.value)
  
  if (oldTopic && oldTopic.name === newName) {
    alert('主题名称未修改')
    cancelEditTopic()
    return
  }
  
  try {
    // 调用API更新主题名称
    const response = await request.put(`/timeline/topic/${editingTopicId.value}`, {
      name: newName
    })
    
    if (response.code === 200) {
      // 更新本地列表
      const topicIndex = availableTopics.value.findIndex(t => t.id === editingTopicId.value)
      if (topicIndex !== -1) {
        availableTopics.value[topicIndex].name = newName
      }
      
      alert('✅ 主题名称已更新')
      cancelEditTopic()
      
      // 重新加载主题列表
      await loadAvailableTopics()
    } else {
      alert('更新失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('更新主题失败:', error)
    alert('更新失败，请稍后重试')
  }
}

// 取消编辑主题
const cancelEditTopic = () => {
  isEditingTopic.value = false
  editTopicName.value = ''
  editingTopicId.value = ''
}

// 删除主题
const deleteTopic = async () => {
  if (!milestoneForm.value.topicId) {
    return
  }
  
  const selectedTopic = availableTopics.value.find(t => t.id === milestoneForm.value.topicId)
  if (!selectedTopic) {
    return
  }
  
  const confirmed = confirm(
    `⚠️ 确认删除主题「${selectedTopic.name}」吗？\n\n` +
    `注意：删除主题将同时删除该主题下的所有里程碑事件！\n` +
    `此操作无法撤销。`
  )
  
  if (!confirmed) {
    return
  }
  
  try {
    const response = await request.delete(`/timeline/topic/${milestoneForm.value.topicId}`)
    
    if (response.code === 200) {
      alert('✅ 主题及其所有里程碑已删除')
      
      // 从列表中移除
      availableTopics.value = availableTopics.value.filter(t => t.id !== milestoneForm.value.topicId)
      
      // 清空选中
      milestoneForm.value.topicId = ''
      
      // 刷新主题列表
      await loadAvailableTopics()
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除主题失败:', error)
    alert('删除失败，请稍后重试')
  }
}

const addMilestone = async () => {
  if (!milestoneForm.value.title) {
    alert('请填写里程碑标题')
    return
  }

  if (!milestoneForm.value.topicId && !milestoneForm.value.newTopicName) {
    alert('请选择已有主题或新建主题')
    return
  }

  try {
    const milestoneData = {
      topicId: milestoneForm.value.topicId || `user-topic-${Date.now()}`,
      title: milestoneForm.value.title,
      description: milestoneForm.value.description,
      date: milestoneForm.value.date
    }
    
    const response = await request.post('/timeline/milestone', milestoneData)
    
    if (response.code === 200) {
      alert('✅ 里程碑已添加到时间轴！\n\n您可以前往「驾驶舱 > 时光轨迹」页面查看。')
      resetHistoryForm()
      // 刷新主题列表
      await loadAvailableTopics()
    } else {
      alert('保存失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('保存里程碑失败:', error)
    alert('保存失败，请稍后重试')
  }
}

const submitDaily = async () => {
  try {
    if (!dailyForm.value.title || !dailyForm.value.period) {
      alert('请填写标题和日期')
      return
    }

    // 检查是否存在相同日期的总结
    try {
      const existingRes = await request.get('/summary/list')
      if (existingRes.code === 200) {
        const existingSummaries = existingRes.data || []
        const duplicate = existingSummaries.find((s: any) => 
          s.type === 'daily' && s.period === dailyForm.value.period
        )
        
        if (duplicate) {
          const confirmOverwrite = confirm(
            `已有日期「${dailyForm.value.period}」的日报笔记！\n\n` +
            `现有笔记：${duplicate.title}\n\n` +
            `是否覆盖原有笔记？`
          )
          
          if (!confirmOverwrite) {
            return
          }
          
          // 用户确认覆盖，删除旧的记录
          await request.delete(`/summary/${duplicate.id}`)
        }
      }
    } catch (error) {
      console.error('检查重复日报失败:', error)
      // 继续执行保存
    }

    // 保存到后端（使用系统总结接口）
    const summaryData = {
      title: dailyForm.value.title,
      period: dailyForm.value.period,
      description: dailyForm.value.content.dailySummary || '每日工作总结',
      content: JSON.stringify(dailyForm.value.content),
      type: 'daily',
      tags: '日报'
    }

    const response = await request.post('/summary', summaryData)
    
    if (response.code === 200) {
      alert('日报已保存')
  resetDailyForm()
    } else {
      alert('保存失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('保存日报失败:', error)
    alert('保存失败，请稍后重试')
  }
}

// 页面加载时获取主题列表
onMounted(() => {
  loadAvailableTopics()
})

// 页面激活时刷新主题列表
onActivated(() => {
  loadAvailableTopics()
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
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

/* 滑动淡入淡出动画 */
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.2s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from {
  transform: translateY(-10px);
  opacity: 0;
}

.slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}

/* 日期输入框样式优化 */
.date-input {
  color-scheme: light;
  font-size: 0.875rem;
  font-weight: 500;
}

/* 隐藏默认的日历图标，使用我们自定义的 */
.date-input::-webkit-calendar-picker-indicator {
  opacity: 0;
  cursor: pointer;
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
}

/* Firefox 兼容 */
.date-input::-moz-calendar-picker-indicator {
  opacity: 0;
}

/* 改善日期显示的字体 */
.date-input::-webkit-datetime-edit {
  padding: 0;
}

.date-input::-webkit-datetime-edit-fields-wrapper {
  padding: 0;
}

.date-input::-webkit-datetime-edit-text {
  color: #6b7280;
  padding: 0 2px;
}

.date-input::-webkit-datetime-edit-month-field,
.date-input::-webkit-datetime-edit-day-field,
.date-input::-webkit-datetime-edit-year-field {
  color: #1f2937;
}
</style> 