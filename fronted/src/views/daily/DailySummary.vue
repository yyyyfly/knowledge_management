<template>
      <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">日常总结</h1>
        <span class="bg-red-100 text-red-600 px-3 py-1 rounded-full text-sm font-medium">每日必填</span>
      </div>
      <p class="text-gray-600">记录每日工作与成长，为系统总结积累素材</p>
    </div>
  <section id="daily-summary" class="p-6 animate-fade-in bg-gray-50 rounded-2xl shadow-inner">
    <!-- 页面头部 -->


    <transition name="fade">
      <div v-if="isCollapsed" class="bg-white shadow-lg rounded-xl p-6 flex items-center justify-between mt-2 min-h-[80px]">
        <div>
          <h3 class="text-xl font-semibold text-gray-900">通用日报</h3>
          <p class="text-gray-400 text-sm mt-1">点击展开填写今日总结</p>
        </div>
        <button @click="isCollapsed = false" class="flex items-center text-sm text-blue-600 hover:bg-blue-50 px-3 py-1 rounded-lg transition">
          <i class="fas fa-chevron-down mr-1"></i> 展开表单
        </button>
      </div>
    </transition>

    <transition name="fade">
      <div v-show="!isCollapsed" class="bg-white shadow-lg rounded-xl p-6 mt-2">
        <div class="flex items-center justify-between mb-2">
          <h3 class="text-xl font-semibold text-gray-900">通用日报</h3>
          <button @click="isCollapsed = true" class="flex items-center text-sm text-blue-600 hover:bg-blue-50 px-3 py-1 rounded-lg transition">
            <i class="fas fa-chevron-up mr-1"></i> 折叠表单
          </button>
        </div>
        <form @submit.prevent="submitDaily" class="space-y-6">
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
          <div class="flex justify-end space-x-4 mt-6">
            <button type="button" @click="resetDailyForm" class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">清空重置</button>
            <button type="submit" class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">保存日报</button>
          </div>
        </form>
      </div>
    </transition>
  </section>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import request from '@/api/request'

const isCollapsed = ref(false)

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