<template>
  <section id="system-summary" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">系统总结</h1>
        <span class="bg-blue-100 text-blue-600 px-3 py-1 rounded-full text-sm font-medium">阶段性总结</span>
      </div>
      <p class="text-gray-600">阶段性参考小总结，定期阶段性大总结，按类型分类管理</p>
    </div>

    <!-- 类型选择卡片 -->
    <div v-if="!currentType" class="mb-8">
      <h3 class="text-xl font-semibold text-gray-900 mb-6">选择总结类型</h3>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 xl:grid-cols-4 gap-6">
        <div 
          v-for="type in summaryTypes" 
          :key="type.value"
          @click="selectType(type.value)"
          class="bg-white rounded-xl shadow-soft p-6 cursor-pointer hover:shadow-lg transition-all duration-300 hover:scale-105 group border-2 border-transparent hover:border-blue-200"
        >
          <div class="text-center">
            <div :class="getTypeIconClass(type.value)" class="w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <i :class="getTypeIcon(type.value)" class="text-2xl text-white"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2 group-hover:text-blue-600 transition-colors">
              {{ type.label }}
            </h4>
            <p class="text-sm text-gray-600 mb-3">{{ type.description }}</p>
            <div class="flex items-center justify-center space-x-2 text-xs text-gray-500">
              <i class="fas fa-file-alt"></i>
              <span>{{ getTypeCount(type.value) }} 篇总结</span>
            </div>
          </div>
        </div>
      </div>
      <div class="bg-white shadow-lg rounded-xl p-6 mt-4">
        <h3 class="text-xl font-semibold text-gray-900 mb-6">新建总结</h3>
        <form @submit.prevent="submitSummary" class="space-y-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">总结标题</label>
              <input v-model="summaryForm.title" type="text" class="w-full px-4 py-2 border border-gray-300 rounded-lg" placeholder="输入总结标题">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">总结类型</label>
              <select v-model="summaryForm.type" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
                <option value="">请选择类型</option>
                <option value="monthly">月报</option>
                <option value="quarterly">季报</option>
                <option value="yearly">年报</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">周期</label>
              <input v-model="summaryForm.period" type="text" class="w-full px-4 py-2 border border-gray-300 rounded-lg" placeholder="如 2024-01-15 或 2024年1季度">
            </div>
          </div>
          <!-- 动态字段区 -->
          <div v-if="summaryForm.type === 'daily'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>今日任务完成情况（含耗时）</h4>
            <div class="mb-6">
              <div v-for="(task, idx) in summaryForm.content.dailyTasks" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="task.content" class="flex-1 px-3 py-2 border rounded" placeholder="任务内容">
                <input v-model="task.duration" class="w-24 px-3 py-2 border rounded" placeholder="耗时">
                <button type="button" @click="summaryForm.content.dailyTasks.splice(idx,1)" v-if="summaryForm.content.dailyTasks.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.dailyTasks.push({content:'',duration:''})" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加任务</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-bolt text-orange-500 mr-2"></i>过程中的问题与应对方式</h4>
            <div class="mb-6">
              <div v-for="(prob, idx) in summaryForm.content.dailyProblems" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="prob.problem" class="flex-1 px-3 py-2 border rounded" placeholder="问题描述">
                <input v-model="prob.solution" class="flex-1 px-3 py-2 border rounded" placeholder="应对方法">
                <button type="button" @click="summaryForm.content.dailyProblems.splice(idx,1)" v-if="summaryForm.content.dailyProblems.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.dailyProblems.push({problem:'',solution:''})" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加问题</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>有效动作 / 有感收获</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.dailyHighlights" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.dailyHighlights[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="收获/亮点">
                <button type="button" @click="summaryForm.content.dailyHighlights.splice(idx,1)" v-if="summaryForm.content.dailyHighlights.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.dailyHighlights.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加收获</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>简要总结段</h4>
            <div class="mb-6">
              <textarea v-model="summaryForm.content.dailySummary" rows="4" class="w-full border rounded px-3 py-2" placeholder="今天整体状态如何？是否有感受、意外、反思、思考？"></textarea>
            </div>
          </div>
          <div v-else-if="summaryForm.type === 'monthly'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>本月主要任务分类及比重</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.monthlyTaskCategories" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.monthlyTaskCategories[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="任务分类/比重/代表事件">
                <button type="button" @click="summaryForm.content.monthlyTaskCategories.splice(idx,1)" v-if="summaryForm.content.monthlyTaskCategories.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.monthlyTaskCategories.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加分类</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-chart-line text-green-500 mr-2"></i>节奏与效率变化趋势</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.monthlyTrends" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.monthlyTrends[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="趋势描述">
                <button type="button" @click="summaryForm.content.monthlyTrends.splice(idx,1)" v-if="summaryForm.content.monthlyTrends.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.monthlyTrends.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加趋势</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>初步形成的个人方法/技巧</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.monthlyExperiences" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.monthlyExperiences[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="方法/技巧">
                <button type="button" @click="summaryForm.content.monthlyExperiences.splice(idx,1)" v-if="summaryForm.content.monthlyExperiences.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.monthlyExperiences.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加方法</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>月度总结段</h4>
            <div class="mb-6">
              <textarea v-model="summaryForm.content.monthlySummary" rows="4" class="w-full border rounded px-3 py-2" placeholder="这个月让你记住的不是工作量，而是感受、判断、尝试的过程……"></textarea>
            </div>
          </div>
          <div v-else-if="summaryForm.type === 'quarterly'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>任务结构与节奏概览 + 阶段成果总结</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.quarterlyTaskOverview" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.quarterlyTaskOverview[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="任务结构/成果">
                <button type="button" @click="summaryForm.content.quarterlyTaskOverview.splice(idx,1)" v-if="summaryForm.content.quarterlyTaskOverview.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.quarterlyTaskOverview.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加结构/成果</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-chart-line text-green-500 mr-2"></i>高频高效路径整合</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.quarterlyEfficientPaths" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.quarterlyEfficientPaths[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="高效路径">
                <button type="button" @click="summaryForm.content.quarterlyEfficientPaths.splice(idx,1)" v-if="summaryForm.content.quarterlyEfficientPaths.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.quarterlyEfficientPaths.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加路径</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-bolt text-orange-500 mr-2"></i>问题类型与应对模式归纳</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.quarterlyProblemSolutions" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.quarterlyProblemSolutions[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="问题类型/应对模式">
                <button type="button" @click="summaryForm.content.quarterlyProblemSolutions.splice(idx,1)" v-if="summaryForm.content.quarterlyProblemSolutions.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.quarterlyProblemSolutions.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加问题类型</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>技能或思维方式进化点</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.quarterlyUpgrades" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.quarterlyUpgrades[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="进化点">
                <button type="button" @click="summaryForm.content.quarterlyUpgrades.splice(idx,1)" v-if="summaryForm.content.quarterlyUpgrades.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.quarterlyUpgrades.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加进化点</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>季度总结段</h4>
            <div class="mb-6">
              <textarea v-model="summaryForm.content.quarterlySummary" rows="4" class="w-full border rounded px-3 py-2" placeholder="从一个季度的视角看，是否有能力模块的增长迹象……"></textarea>
            </div>
          </div>
          <div v-else-if="summaryForm.type === 'yearly'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>年度任务结构与节奏演变 + 关键成果地图</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.yearlyMap" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.yearlyMap[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="任务主线/里程碑/产出内容">
                <button type="button" @click="summaryForm.content.yearlyMap.splice(idx,1)" v-if="summaryForm.content.yearlyMap.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.yearlyMap.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加主线/成果</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-chart-line text-green-500 mr-2"></i>稳定方法论沉淀</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.yearlyMethodology" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.yearlyMethodology[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="方法论/通用做法/工具">
                <button type="button" @click="summaryForm.content.yearlyMethodology.splice(idx,1)" v-if="summaryForm.content.yearlyMethodology.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.yearlyMethodology.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加方法论</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-bolt text-orange-500 mr-2"></i>最大偏差或误判复盘</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.yearlyCriticalReviews" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.yearlyCriticalReviews[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="偏差/误判/复盘">
                <button type="button" @click="summaryForm.content.yearlyCriticalReviews.splice(idx,1)" v-if="summaryForm.content.yearlyCriticalReviews.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.yearlyCriticalReviews.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加复盘</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>认知跃迁与思维升级</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.yearlyCognitiveLeaps" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.yearlyCognitiveLeaps[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="跃迁/升级点">
                <button type="button" @click="summaryForm.content.yearlyCognitiveLeaps.splice(idx,1)" v-if="summaryForm.content.yearlyCognitiveLeaps.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.yearlyCognitiveLeaps.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加跃迁</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>资产沉淀 & 个人系统搭建</h4>
            <div class="mb-6">
              <div v-for="(item, idx) in summaryForm.content.yearlyAssets" :key="idx" class="flex items-center space-x-2 mb-2">
                <input v-model="summaryForm.content.yearlyAssets[idx]" class="flex-1 px-3 py-2 border rounded" placeholder="资产/系统/工具">
                <button type="button" @click="summaryForm.content.yearlyAssets.splice(idx,1)" v-if="summaryForm.content.yearlyAssets.length>1" class="text-red-500"><i class="fas fa-times"></i></button>
              </div>
            </div>
            <button type="button" @click="summaryForm.content.yearlyAssets.push('')" class="text-blue-600 text-sm mb-2"><i class="fas fa-plus mr-1"></i>添加资产</button>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>年度总结段</h4>
            <div class="mb-6">
              <textarea v-model="summaryForm.content.yearlySummary" rows="4" class="w-full border rounded px-3 py-2" placeholder="这一年，你真正拥有的成长不在于做了什么，而在于成为了怎样的人……"></textarea>
            </div>
          </div>
          <div class="flex justify-end space-x-4 mt-6">
            <button type="button" @click="resetForm" class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">清空重置</button>
            <button type="submit" class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">{{ isEditing ? '更新总结' : '保存总结' }}</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 查看总结详情弹窗 -->
    <div v-if="showDetailModal" class="fixed inset-0 z-50 flex items-center justify-center">
      <!-- 背景遮罩 -->
      <div 
        class="absolute inset-0 bg-black bg-opacity-50 transition-opacity duration-300 ease-in-out"
        @click="closeDetailModal"
      ></div>
      
      <!-- 弹窗内容 -->
      <div class="relative bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[95vh] overflow-y-auto p-8">
        <div class="mb-4">
          <h2 class="text-2xl font-bold mb-2">{{ selectedSummary?.title }}</h2>
          <div class="flex flex-wrap items-center gap-2 mb-2">
            <span :class="getTypeClass(selectedSummary?.type || '')" class="px-2 py-1 rounded-full text-xs font-medium">{{ getTypeText(selectedSummary?.type || '') }}</span>
            <span class="text-xs text-gray-500">{{ selectedSummary?.period }}</span>
          </div>
          <div class="flex items-center gap-4 text-sm text-gray-500">
            <span>满意度：{{ selectedSummary?.satisfaction }}</span>
            <span>标签：<span v-for="tag in selectedSummary?.tags" :key="tag" class="inline-block bg-gray-100 text-gray-600 px-2 py-1 rounded text-xs mr-1">{{ tag }}</span></span>
          </div>
        </div>
        <div v-if="selectedSummary">
          <template v-if="selectedSummary.type === 'daily'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>今日任务完成情况（含耗时）</h4>
            <div class="mb-6">
              <div class="border-l-4 border-blue-400 bg-gradient-to-r from-blue-50 via-blue-100 to-blue-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(task, idx) in selectedSummary.content.dailyTasks" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-blue-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ task.content }} <span class="text-xs text-gray-400">({{ task.duration }})</span>
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-bolt text-orange-500 mr-2"></i>过程中的问题与应对方式</h4>
            <div class="mb-6">
              <div class="border-l-4 border-orange-400 bg-gradient-to-r from-orange-50 via-orange-100 to-orange-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(prob, idx) in selectedSummary.content.dailyProblems" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-orange-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ prob.problem }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>有效动作 / 有感收获</h4>
            <div class="mb-6">
              <div class="border-l-4 border-yellow-400 bg-gradient-to-r from-yellow-50 via-yellow-100 to-yellow-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.dailyHighlights" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-yellow-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>简要总结段</h4>
            <div class="mb-6">
              <div class="border-l-4 border-purple-400 bg-gradient-to-r from-purple-50 via-purple-100 to-purple-200 shadow-lg rounded-xl p-4 mb-8">
                {{ selectedSummary.content.dailySummary }}
              </div>
            </div>
          </template>
          <template v-else-if="selectedSummary.type === 'monthly'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>本月主要任务分类及比重</h4>
            <div class="mb-6">
              <div class="border-l-4 border-blue-400 bg-gradient-to-r from-blue-50 via-blue-100 to-blue-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.monthlyTaskCategories" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-blue-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-chart-line text-green-500 mr-2"></i>节奏与效率变化趋势</h4>
            <div class="mb-6">
              <div class="border-l-4 border-green-400 bg-gradient-to-r from-green-50 via-green-100 to-green-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.monthlyTrends" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-green-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>初步形成的个人方法/技巧</h4>
            <div class="mb-6">
              <div class="border-l-4 border-yellow-400 bg-gradient-to-r from-yellow-50 via-yellow-100 to-yellow-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.monthlyExperiences" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-yellow-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>月度总结段</h4>
            <div class="mb-6">
              <div class="border-l-4 border-purple-400 bg-gradient-to-r from-purple-50 via-purple-100 to-purple-200 shadow-lg rounded-xl p-4 mb-8">
                {{ selectedSummary.content.monthlySummary }}
              </div>
            </div>
          </template>
          <template v-else-if="selectedSummary.type === 'quarterly'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>任务结构与节奏概览 + 阶段成果总结</h4>
            <div class="mb-6">
              <div class="border-l-4 border-blue-400 bg-gradient-to-r from-blue-50 via-blue-100 to-blue-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.quarterlyTaskOverview" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-blue-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-chart-line text-green-500 mr-2"></i>高频高效路径整合</h4>
            <div class="mb-6">
              <div class="border-l-4 border-green-400 bg-gradient-to-r from-green-50 via-green-100 to-green-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.quarterlyEfficientPaths" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-green-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-bolt text-orange-500 mr-2"></i>问题类型与应对模式归纳</h4>
            <div class="mb-6">
              <div class="border-l-4 border-orange-400 bg-gradient-to-r from-orange-50 via-orange-100 to-orange-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.quarterlyProblemSolutions" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-orange-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>技能或思维方式进化点</h4>
            <div class="mb-6">
              <div class="border-l-4 border-yellow-400 bg-gradient-to-r from-yellow-50 via-yellow-100 to-yellow-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.quarterlyUpgrades" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-yellow-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>季度总结段</h4>
            <div class="mb-6">
              <div class="border-l-4 border-purple-400 bg-gradient-to-r from-purple-50 via-purple-100 to-purple-200 shadow-lg rounded-xl p-4 mb-8">
                {{ selectedSummary.content.quarterlySummary }}
              </div>
            </div>
          </template>
          <template v-else-if="selectedSummary.type === 'yearly'">
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-tasks text-blue-500 mr-2"></i>年度任务结构与节奏演变 + 关键成果地图</h4>
            <div class="mb-6">
              <div class="border-l-4 border-blue-400 bg-gradient-to-r from-blue-50 via-blue-100 to-blue-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.yearlyMap" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-blue-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-chart-line text-green-500 mr-2"></i>稳定方法论沉淀</h4>
            <div class="mb-6">
              <div class="border-l-4 border-green-400 bg-gradient-to-r from-green-50 via-green-100 to-green-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.yearlyMethodology" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-green-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-bolt text-orange-500 mr-2"></i>最大偏差或误判复盘</h4>
            <div class="mb-6">
              <div class="border-l-4 border-orange-400 bg-gradient-to-r from-orange-50 via-orange-100 to-orange-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.yearlyCriticalReviews" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-orange-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-lightbulb text-yellow-500 mr-2"></i>认知跃迁与思维升级</h4>
            <div class="mb-6">
              <div class="border-l-4 border-yellow-400 bg-gradient-to-r from-yellow-50 via-yellow-100 to-yellow-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.yearlyCognitiveLeaps" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-yellow-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>资产沉淀 & 个人系统搭建</h4>
            <div class="mb-6">
              <div class="border-l-4 border-purple-400 bg-gradient-to-r from-purple-50 via-purple-100 to-purple-200 shadow-lg rounded-xl p-4 mb-8">
                <ul class="mb-2">
                  <li v-for="(item, idx) in selectedSummary.content.yearlyAssets" :key="idx" class="py-2 font-medium text-gray-800 leading-relaxed">
                    <div class='relative inline-block w-7 h-7 mr-3 align-middle'>
                      <div class="w-6 h-6 rounded-full bg-purple-400 text-white text-center">
                        {{ idx+1 }}
                      </div>
                    </div>
                    {{ item }}
                  </li>
                </ul>
              </div>
            </div>
            <h4 class="font-bold text-lg mt-4 mb-2 tracking-wide font-extrabold text-xl text-shadow"><i class="fas fa-quote-right text-purple-500 mr-2"></i>年度总结段</h4>
            <div class="mb-6">
              <div class="border-l-4 border-purple-400 bg-gradient-to-r from-purple-50 via-purple-100 to-purple-200 shadow-lg rounded-xl p-4 mb-8">
                {{ selectedSummary.content.yearlySummary }}
              </div>
            </div>
          </template>
        </div>
        <div class="flex justify-end mt-6">
          <button @click="closeDetailModal" class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">关闭</button>
        </div>
      </div>
    </div>

    <!-- 新增类型卡片弹窗 -->
    <Transition>
      <div v-if="showTypeModal" class="fixed inset-0 z-50 flex items-center justify-center">
        <!-- 背景遮罩 -->
        <div 
          class="absolute inset-0 bg-black bg-opacity-50 transition-opacity duration-300 ease-in-out"
          @click="closeTypeModal"
        ></div>
        
        <!-- 弹窗内容 -->
        <div class="relative bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[95vh] overflow-y-auto p-8">
          <div class="mb-4">
            <h2 class="text-2xl font-bold mb-2">{{ getCurrentTypeLabel() }}总结</h2>
            <div class="flex flex-wrap items-center gap-2 mb-2">
              <span :class="getTypeClass(selectedType)" class="px-2 py-1 rounded-full text-xs font-medium">{{ getTypeText(selectedType) }}</span>
            </div>
          </div>
          <div v-if="selectedType">
            <!-- 总结卡片网格 -->
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
              <div 
                v-for="summary in summaries.filter((summary: SystemSummary) => summary.type === selectedType)" 
                :key="summary.id" 
                @click="viewSummary(summary)"
                class="bg-white rounded-xl shadow-soft p-6 cursor-pointer hover:shadow-lg transition-all duration-300 hover:scale-105 group"
              >
                <!-- 卡片头部 -->
                <div class="flex items-start justify-between mb-4">
                  <div class="flex-1">
                    <h3 class="text-lg font-semibold text-gray-900 mb-2 group-hover:text-blue-600 transition-colors">
                      {{ summary.title }}
                    </h3>
                    <p class="text-sm text-gray-500">{{ summary.period }}</p>
                  </div>
                  <div class="opacity-0 group-hover:opacity-100 transition-opacity">
                    <i class="fas fa-arrow-right text-blue-500"></i>
                  </div>
                </div>

                <!-- 卡片内容预览 -->
                <div class="mb-4">
                  <p class="text-sm text-gray-600 line-clamp-3">{{ summary.description }}</p>
                </div>

                <!-- 卡片底部 -->
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-4 text-xs text-gray-500">
                    <div class="flex items-center space-x-1">
                      <i class="fas fa-tasks"></i>
                      <span>{{ summary.satisfaction }}</span>
                    </div>
                  </div>
                  <div class="flex space-x-1">
                    <span v-for="tag in summary.tags.slice(0, 2)" :key="tag" class="bg-gray-100 text-gray-600 px-2 py-1 rounded text-xs">
                      {{ tag }}
                    </span>
                    <span v-if="summary.tags.length > 2" class="bg-gray-100 text-gray-600 px-2 py-1 rounded text-xs">
                      +{{ summary.tags.length - 2 }}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <!-- 空状态 -->
            <div v-if="summaries.filter((summary: SystemSummary) => summary.type === selectedType).length === 0" class="text-center py-12">
              <div class="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <i class="fas fa-file-alt text-3xl text-gray-400"></i>
              </div>
              <h3 class="text-lg font-medium text-gray-900 mb-2">暂无{{ getTypeText(selectedType) }}总结</h3>
              <p class="text-gray-600 mb-6">开始创建您的第一个{{ getTypeText(selectedType) }}总结吧</p>
              <button 
                @click="showCreateForm = true"
                class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
              >
                创建{{ getTypeText(selectedType) }}总结
              </button>
            </div>
          </div>
          <div class="flex justify-end mt-6">
            <button @click="closeTypeModal" class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">关闭</button>
      </div>
    </div>
  </div>
    </Transition>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import request from '@/api/request'

// 定义 SystemSummary 类型
type SystemSummary = {
  id: number
  title: string
  type: 'daily' | 'monthly' | 'quarterly' | 'yearly'
  period: string
  description: string
  content: any
  satisfaction: number
  tags: string[]
  recCreateTime?: string
  createTime?: string
}

// 总结类型选项
const summaryTypes = [
  { 
    value: 'daily', 
    label: '每日总结', 
    description: '记录每日的学习成果和工作进展',
    icon: 'fas fa-calendar-day'
  },
  { 
    value: 'monthly', 
    label: '月度总结', 
    description: '总结月度目标和项目进展情况',
    icon: 'fas fa-calendar-alt'
  },
  { 
    value: 'quarterly', 
    label: '季度总结', 
    description: '分析季度成果和战略规划执行',
    icon: 'fas fa-chart-line'
  },
  { 
    value: 'yearly', 
    label: '年度总结', 
    description: '回顾年度成就和制定来年计划',
    icon: 'fas fa-calendar-check'
  }
]

// 状态管理
const summaries = ref<SystemSummary[]>([])
const currentType = ref('')
const showCreateForm = ref(false)
const showDetailModal = ref(false)
const selectedSummary = ref<SystemSummary | null>(null)
const isEditing = ref(false)
const editingSummaryId = ref<number | null>(null)
const selectedType = ref('')
const showTypeModal = ref(false)

// 表单数据
const summaryForm = reactive({
  title: '',
  type: '' as SystemSummary['type'],
  period: '',
  description: '',
  content: {
    // 日报字段
    dailyTasks: [{ content: '', duration: '' }],
    dailyProblems: [{ problem: '', solution: '' }],
    dailyHighlights: [''],
    dailySummary: '',
    // 月报字段
    monthlyTaskCategories: [''],
    monthlyTrends: [''],
    monthlyExperiences: [''],
    monthlySummary: '',
    // 季报字段
    quarterlyTaskOverview: [''],
    quarterlyEfficientPaths: [''],
    quarterlyProblemSolutions: [''],
    quarterlyUpgrades: [''],
    quarterlySummary: '',
    // 年报字段
    yearlyMap: [''],
    yearlyMethodology: [''],
    yearlyCriticalReviews: [''],
    yearlyCognitiveLeaps: [''],
    yearlyAssets: [''],
    yearlySummary: ''
  },
  satisfaction: 5,
  tags: ''
})

// 计算属性
const filteredSummaries = computed(() => {
  if (!selectedType.value) return summaries.value
  return summaries.value.filter(summary => summary.type === selectedType.value)
})

// 方法
const loadSummaries = async () => {
  try {
    const res = await request.get('/summary/list')
    if (res.code === 200) {
      summaries.value = (res.data || []).map((s: any) => ({
        ...s,
        tags: Array.isArray(s.tags) ? s.tags : (s.tags ? s.tags.split(',') : []),
        content: typeof s.content === 'string' ? JSON.parse(s.content) : s.content
      }))
    }
  } catch (error) {
    console.error('加载总结失败:', error)
  }
}

const selectType = (type: string) => {
  selectedType.value = type
  showTypeModal.value = true
}

const backToTypeSelection = () => {
  selectedType.value = ''
  resetForm()
}

const getTypeCount = (type: string) => {
  return summaries.value.filter(summary => summary.type === type).length
}

const getCurrentTypeLabel = () => {
  const type = summaryTypes.find(t => t.value === selectedType.value)
  return type ? type.label : ''
}

const resetForm = () => {
  summaryForm.title = ''
  summaryForm.type = '' as SystemSummary['type']
  summaryForm.period = ''
  summaryForm.description = ''
  summaryForm.content.dailyTasks = [{ content: '', duration: '' }]
  summaryForm.content.dailyProblems = [{ problem: '', solution: '' }]
  summaryForm.content.dailyHighlights = ['']
  summaryForm.content.monthlyTaskCategories = ['']
  summaryForm.content.monthlyTrends = ['']
  summaryForm.content.monthlyExperiences = ['']
  summaryForm.content.quarterlyTaskOverview = ['']
  summaryForm.content.quarterlyEfficientPaths = ['']
  summaryForm.content.quarterlyProblemSolutions = ['']
  summaryForm.content.quarterlyUpgrades = ['']
  summaryForm.content.yearlyMap = ['']
  summaryForm.content.yearlyMethodology = ['']
  summaryForm.content.yearlyCriticalReviews = ['']
  summaryForm.content.yearlyCognitiveLeaps = ['']
  summaryForm.content.yearlyAssets = ['']
  summaryForm.satisfaction = 5
  summaryForm.tags = ''
  summaryForm.content.dailySummary = ''
  summaryForm.content.monthlySummary = ''
  summaryForm.content.quarterlySummary = ''
  summaryForm.content.yearlySummary = ''
}

const closeModal = () => {
  showCreateForm.value = false
  isEditing.value = false
  editingSummaryId.value = null
  resetForm()
}

const closeDetailModal = () => {
  showDetailModal.value = false
  selectedSummary.value = null
  if (selectedType.value) showTypeModal.value = true
}

const viewSummary = (summary: SystemSummary) => {
  selectedSummary.value = summary
  showDetailModal.value = true
  showTypeModal.value = false
}

const submitSummary = async () => {
  if (!summaryForm.title || !summaryForm.type || !summaryForm.period) {
    alert('请填写必要信息')
    return
  }

  const contentData = {
    // 根据类型添加对应字段
    ...(summaryForm.type === 'daily' && {
      dailyTasks: summaryForm.content.dailyTasks.filter(item => item.content.trim()),
      dailyProblems: summaryForm.content.dailyProblems.filter(item => item.problem.trim()),
      dailyHighlights: summaryForm.content.dailyHighlights.filter(item => item.trim())
    }),
    ...(summaryForm.type === 'monthly' && {
      monthlyTaskCategories: summaryForm.content.monthlyTaskCategories.filter(item => item.trim()),
      monthlyTrends: summaryForm.content.monthlyTrends.filter(item => item.trim()),
      monthlyExperiences: summaryForm.content.monthlyExperiences.filter(item => item.trim())
    }),
    ...(summaryForm.type === 'quarterly' && {
      quarterlyTaskOverview: summaryForm.content.quarterlyTaskOverview.filter(item => item.trim()),
      quarterlyEfficientPaths: summaryForm.content.quarterlyEfficientPaths.filter(item => item.trim()),
      quarterlyProblemSolutions: summaryForm.content.quarterlyProblemSolutions.filter(item => item.trim()),
      quarterlyUpgrades: summaryForm.content.quarterlyUpgrades.filter(item => item.trim())
    }),
    ...(summaryForm.type === 'yearly' && {
      yearlyMap: summaryForm.content.yearlyMap.filter(item => item.trim()),
      yearlyMethodology: summaryForm.content.yearlyMethodology.filter(item => item.trim()),
      yearlyCriticalReviews: summaryForm.content.yearlyCriticalReviews.filter(item => item.trim()),
      yearlyCognitiveLeaps: summaryForm.content.yearlyCognitiveLeaps.filter(item => item.trim()),
      yearlyAssets: summaryForm.content.yearlyAssets.filter(item => item.trim())
    })
  }

  const summaryData = {
    title: summaryForm.title,
    type: summaryForm.type,
    period: summaryForm.period,
    description: summaryForm.description,
    content: JSON.stringify(contentData),
    satisfaction: summaryForm.satisfaction,
    tags: summaryForm.tags.split(',').map(tag => tag.trim()).filter(tag => tag).join(',')
  }

  try {
    if (isEditing.value && editingSummaryId.value) {
      const res = await request.put('/summary', { ...summaryData, id: editingSummaryId.value })
      if (res.code === 200) {
        alert('总结更新成功！')
        loadSummaries()
      }
    } else {
      const res = await request.post('/summary', summaryData)
      if (res.code === 200) {
        alert('总结创建成功！')
        loadSummaries()
      }
    }
    closeModal()
  } catch (error) {
    console.error('保存总结失败:', error)
    alert('操作失败，请重试')
  }
}

const editSummary = (summary: SystemSummary) => {
  isEditing.value = true
  editingSummaryId.value = summary.id
  summaryForm.title = summary.title
  summaryForm.type = summary.type
  summaryForm.period = summary.period
  summaryForm.description = summary.description
  // 日报
  summaryForm.content.dailyTasks = summary.content.dailyTasks ? [...summary.content.dailyTasks.map(item => ({ content: item.content, duration: item.duration || '' })), { content: '', duration: '' }] : [{ content: '', duration: '' }]
  summaryForm.content.dailyProblems = summary.content.dailyProblems ? [...summary.content.dailyProblems.map(item => ({ problem: item.problem, solution: item.solution || '' })), { problem: '', solution: '' }] : [{ problem: '', solution: '' }]
  summaryForm.content.dailyHighlights = summary.content.dailyHighlights ? [...summary.content.dailyHighlights, ''] : ['']
  // 月报
  summaryForm.content.monthlyTaskCategories = summary.content.monthlyTaskCategories ? [...summary.content.monthlyTaskCategories, ''] : ['']
  summaryForm.content.monthlyTrends = summary.content.monthlyTrends ? [...summary.content.monthlyTrends, ''] : ['']
  summaryForm.content.monthlyExperiences = summary.content.monthlyExperiences ? [...summary.content.monthlyExperiences, ''] : ['']
  // 季报
  summaryForm.content.quarterlyTaskOverview = summary.content.quarterlyTaskOverview ? [...summary.content.quarterlyTaskOverview, ''] : ['']
  summaryForm.content.quarterlyEfficientPaths = summary.content.quarterlyEfficientPaths ? [...summary.content.quarterlyEfficientPaths, ''] : ['']
  summaryForm.content.quarterlyProblemSolutions = summary.content.quarterlyProblemSolutions ? [...summary.content.quarterlyProblemSolutions, ''] : ['']
  summaryForm.content.quarterlyUpgrades = summary.content.quarterlyUpgrades ? [...summary.content.quarterlyUpgrades, ''] : ['']
  // 年报
  summaryForm.content.yearlyMap = summary.content.yearlyMap ? [...summary.content.yearlyMap, ''] : ['']
  summaryForm.content.yearlyMethodology = summary.content.yearlyMethodology ? [...summary.content.yearlyMethodology, ''] : ['']
  summaryForm.content.yearlyCriticalReviews = summary.content.yearlyCriticalReviews ? [...summary.content.yearlyCriticalReviews, ''] : ['']
  summaryForm.content.yearlyCognitiveLeaps = summary.content.yearlyCognitiveLeaps ? [...summary.content.yearlyCognitiveLeaps, ''] : ['']
  summaryForm.content.yearlyAssets = summary.content.yearlyAssets ? [...summary.content.yearlyAssets, ''] : ['']
  summaryForm.satisfaction = summary.satisfaction
  summaryForm.tags = summary.tags.join(', ')
  showCreateForm.value = true
  closeDetailModal()
}

const deleteSummary = async (id: number) => {
  if (confirm('确定要删除这个总结吗？')) {
    try {
      const res = await request.delete(`/summary/${id}`)
      if (res.code === 200) {
        alert('总结删除成功！')
        loadSummaries()
        closeDetailModal()
      }
    } catch (error) {
      console.error('删除总结失败:', error)
      alert('删除失败，请重试')
    }
  }
}

// 工具方法
const getTypeClass = (type: string) => {
  const classes = {
    daily: 'bg-green-100 text-green-600',
    monthly: 'bg-purple-100 text-purple-600',
    quarterly: 'bg-orange-100 text-orange-600',
    yearly: 'bg-red-100 text-red-600'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

const getTypeText = (type: string) => {
  const texts = {
    daily: '每日',
    monthly: '月度总结',
    quarterly: '季度',
    yearly: '年度'
  }
  return texts[type as keyof typeof texts] || '其他'
}

const getTypeIconClass = (type: string) => {
  const classes = {
    daily: 'bg-green-500',
    monthly: 'bg-purple-500',
    quarterly: 'bg-orange-500',
    yearly: 'bg-red-500'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-500'
}

const getTypeIcon = (type: string) => {
  const typeInfo = summaryTypes.find(t => t.value === type)
  return typeInfo ? typeInfo.icon : 'fas fa-file-alt'
}

const closeTypeModal = () => {
  showTypeModal.value = false
}

onMounted(() => {
  loadSummaries()
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

/* 弹窗动画 */
.modal-enter-active,
.modal-leave-active {
  transition: all 0.3s ease-in-out;
}

.modal-enter-from {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

.modal-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(20px);
}

/* 文本截断 */
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style> 