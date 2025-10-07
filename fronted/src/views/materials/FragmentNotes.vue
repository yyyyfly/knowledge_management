<template>
  <div class="p-6 bg-gray-50 min-h-screen">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-2">碎片笔记</h1>
      <p class="text-gray-600">零散思考和知识碎片的统计概览</p>
    </div>

    <!-- 统计卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-5 gap-6 mb-8">
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">总笔记数</p>
            <p class="text-3xl font-bold text-gray-900">{{ fragmentNotes.length }}</p>
            <p class="text-xs text-green-600 mt-1">+{{ thisMonthNotes.length }} 本月新增</p>
          </div>
          <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">分类数量</p>
            <p class="text-3xl font-bold text-gray-900">{{ uniqueCategories.length }}</p>
            <p class="text-xs text-blue-600 mt-1">思考分类</p>
          </div>
          <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">主题数量</p>
            <p class="text-3xl font-bold text-gray-900">{{ uniqueThemes.length }}</p>
            <p class="text-xs text-purple-600 mt-1">思考主题</p>
          </div>
          <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">平均字数</p>
            <p class="text-3xl font-bold text-gray-900">{{ averageWordCount }}</p>
            <p class="text-xs text-orange-600 mt-1">字符/笔记</p>
          </div>
          <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
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
              <p class="text-sm text-gray-600">浏览所有碎片笔记</p>
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
          
          <!-- 重要性分布 -->
          <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
            <h2 class="text-xl font-semibold text-gray-900 mb-6">重要性分布</h2>
            <div class="h-80">
              <Bar :data="importanceChartData" :options="importanceChartOptions" />
            </div>
          </div>
        </div>

        <!-- 分类分布图表 -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <h2 class="text-xl font-semibold text-gray-900 mb-6">分类分布统计</h2>
          <div class="h-80 overflow-y-auto">
            <div class="space-y-4">
              <div v-for="category in paginatedCategoryStats" :key="category.name" class="flex items-center">
                <div class="w-40 text-sm text-gray-600 truncate">{{ category.name }}</div>
                <div class="flex-1 mx-4">
                  <div class="w-full bg-gray-200 rounded-full h-3">
                    <div class="bg-gradient-to-r from-purple-500 to-purple-600 h-3 rounded-full transition-all duration-300" 
                         :style="{ width: category.percentage + '%' }"></div>
                  </div>
                </div>
                <div class="w-20 text-right">
                  <span class="text-sm font-semibold text-gray-900">{{ category.count }}</span>
                  <span class="text-xs text-gray-500 ml-1">({{ category.percentage }}%)</span>
                </div>
              </div>
            </div>
            <!-- 分页控件 -->
            <div class="flex items-center justify-between mt-6 pt-4 border-t border-gray-200">
              <div class="text-sm text-gray-600">
                显示 {{ (currentCategoryPage - 1) * categoriesPerPage + 1 }} - {{ Math.min(currentCategoryPage * categoriesPerPage, categoryStats.length) }} 共 {{ categoryStats.length }} 项
              </div>
            <div class="flex items-center space-x-2">
                <button 
                  @click="currentCategoryPage = Math.max(1, currentCategoryPage - 1)"
                  :disabled="currentCategoryPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">
                  上一页
                </button>
                <span class="text-sm text-gray-600">
                  {{ currentCategoryPage }} / {{ totalCategoryPages }}
                </span>
                <button 
                  @click="currentCategoryPage = Math.min(totalCategoryPages, currentCategoryPage + 1)"
                  :disabled="currentCategoryPage === totalCategoryPages"
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
            <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
            <div class="flex items-center mb-3 overflow-x-auto whitespace-nowrap gap-1">
              <span v-for="cat in note.fragmentCategory?.slice(0, 2)" :key="cat" class="bg-blue-100 text-blue-700 px-2 py-1 rounded-md text-xs font-medium">
                {{ cat }}
              </span>
              <span v-for="theme in note.fragmentTheme?.slice(0, 2)" :key="theme" class="bg-green-100 text-green-700 px-2 py-1 rounded-md text-xs font-medium">
                {{ theme }}
              </span>
              <span v-if="(note.fragmentCategory?.length || 0) + (note.fragmentTheme?.length || 0) > 4" class="text-xs text-gray-500 px-2 py-1">
                +{{ (note.fragmentCategory?.length || 0) + (note.fragmentTheme?.length || 0) - 4 }}
              </span>
            </div>
            <div class="flex items-center justify-between">
              <span class="text-xs text-gray-500">{{ formatDate(note.createTime) }}</span>
              <div class="w-2 h-2 bg-purple-500 rounded-full"></div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 思考概览 -->
    <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-xl font-semibold text-gray-900">思考概览</h2>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <!-- 思考天数 -->
        <div class="relative p-6 bg-gradient-to-br from-blue-50 to-blue-100 rounded-xl border border-blue-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-blue-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-calendar-day text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-blue-600">{{ thinkingDays }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-blue-900">思考天数</p>
            <p class="text-xs text-blue-700">有记录的天数</p>
          </div>
          <!-- 最近活动情况 -->
          <div class="text-xs text-blue-600 mb-2">
            <span>最近7天：{{ recentWeekDays }}天有记录</span>
          </div>
          <div class="flex space-x-1">
            <div v-for="(hasNote, index) in recentWeekActivity" :key="index" 
                 :class="hasNote ? 'bg-blue-500' : 'bg-blue-200'" 
                 class="flex-1 h-2 rounded-sm transition-colors duration-300">
            </div>
          </div>
        </div>

        <!-- 主题数量 -->
        <div class="relative p-6 bg-gradient-to-br from-green-50 to-green-100 rounded-xl border border-green-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-green-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-lightbulb text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-green-600">{{ uniqueThemes.length }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-green-900">主题数量</p>
            <p class="text-xs text-green-700">涉及的思考主题</p>
          </div>
          <!-- 最活跃主题 -->
          <div class="text-xs text-green-600 mb-2">
            <span>最活跃：{{ mostActiveTheme.name }}({{ mostActiveTheme.count }}篇)</span>
          </div>
          <div class="space-y-1">
            <div v-for="theme in topThemes" :key="theme.name" class="flex items-center text-xs">
              <div class="w-2 h-2 bg-green-500 rounded-full mr-2"></div>
              <span class="flex-1 truncate">{{ theme.name }}</span>
              <span class="text-green-600">{{ theme.count }}</span>
            </div>
          </div>
        </div>

        <!-- 月度增长 -->
        <div class="relative p-6 bg-gradient-to-br from-purple-50 to-purple-100 rounded-xl border border-purple-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-purple-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-chart-line text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-purple-600">{{ thisMonthNotes.length }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-purple-900">本月新增</p>
            <p class="text-xs text-purple-700">当前月份新增笔记</p>
          </div>
          <!-- 月度对比 -->
          <div class="text-xs text-purple-600 mb-2">
            <span>较上月：{{ monthlyGrowth >= 0 ? '+' : '' }}{{ monthlyGrowth }}篇</span>
          </div>
          <div class="space-y-1">
            <div class="flex items-center justify-between text-xs">
              <span>本月</span>
              <div class="flex-1 mx-2 bg-purple-200 rounded-full h-1">
                <div class="bg-purple-500 h-1 rounded-full" :style="{ width: Math.min((thisMonthNotes.length / Math.max(thisMonthNotes.length, lastMonthNotes.length, 1)) * 100, 100) + '%' }"></div>
              </div>
              <span class="text-purple-600">{{ thisMonthNotes.length }}</span>
            </div>
            <div class="flex items-center justify-between text-xs">
              <span>上月</span>
              <div class="flex-1 mx-2 bg-purple-200 rounded-full h-1">
                <div class="bg-purple-400 h-1 rounded-full" :style="{ width: Math.min((lastMonthNotes.length / Math.max(thisMonthNotes.length, lastMonthNotes.length, 1)) * 100, 100) + '%' }"></div>
              </div>
              <span class="text-purple-500">{{ lastMonthNotes.length }}</span>
            </div>
          </div>
        </div>

        <!-- 重要性分布 -->
        <div class="relative p-6 bg-gradient-to-br from-orange-50 to-orange-100 rounded-xl border border-orange-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-orange-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-exclamation-triangle text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-orange-600">{{ highImportanceCount }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-orange-900">高重要性</p>
            <p class="text-xs text-orange-700">标记为高重要的笔记</p>
          </div>
          <!-- 重要性分布 -->
          <div class="text-xs text-orange-600 mb-2">
            <span>高/中/低：{{ highImportanceCount }}/{{ mediumImportanceCount }}/{{ lowImportanceCount }}</span>
          </div>
          <div class="flex space-x-1 h-3">
            <div class="bg-red-500 rounded-sm" :style="{ width: importanceDistribution.high + '%' }"></div>
            <div class="bg-yellow-500 rounded-sm" :style="{ width: importanceDistribution.medium + '%' }"></div>
            <div class="bg-green-500 rounded-sm" :style="{ width: importanceDistribution.low + '%' }"></div>
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
                <div class="w-12 h-12 bg-purple-100 rounded-full flex items-center justify-center">
                  <i class="fas fa-lightbulb text-2xl text-purple-600"></i>
                </div>
                <div>
                  <h3 class="text-2xl font-semibold text-gray-900">碎片笔记列表</h3>
                  <p class="text-sm text-gray-500">{{ fragmentNotes.length }} 条笔记</p>
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
                      class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all"
                    >
                  </div>
                </div>
                <div>
                  <select v-model="selectedCategory" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
                    <option value="">全部分类</option>
                    <option v-for="category in uniqueCategories" :key="category" :value="category">
                      {{ category }}
                    </option>
                  </select>
                </div>
                <div>
                  <select v-model="selectedTheme" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
                    <option value="">全部主题</option>
                    <option v-for="theme in uniqueThemes" :key="theme" :value="theme">
                      {{ theme }}
                    </option>
                  </select>
                </div>
                <div>
                  <select v-model="selectedImportance" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
                    <option value="">全部重要性</option>
                    <option value="high">高</option>
                    <option value="medium">中</option>
                    <option value="low">低</option>
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
                    <span :class="getImportanceClass(note.importance || 'medium')" class="px-2 py-1 rounded text-xs font-medium">
                      {{ getImportanceText(note.importance || 'medium') }}
                    </span>
                  </div>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="cat in note.fragmentCategory?.slice(0, 2)" :key="cat" class="bg-purple-100 text-purple-700 px-2 py-1 rounded text-xs">
                      {{ cat }}
                    </span>
                    <span v-for="theme in note.fragmentTheme?.slice(0, 2)" :key="theme" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                      {{ theme }}
                    </span>
                    <span v-if="(note.fragmentCategory?.length || 0) + (note.fragmentTheme?.length || 0) > 4" class="text-xs text-gray-500 px-2 py-1">
                      +{{ (note.fragmentCategory?.length || 0) + (note.fragmentTheme?.length || 0) - 4 }}
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
                <span :class="getImportanceClass(selectedNote.importance || 'medium')" class="px-2 py-1 rounded-full text-xs font-medium">
                  {{ getImportanceText(selectedNote.importance || 'medium') }}
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
                <div class="flex flex-wrap gap-1">
                  <span v-for="cat in selectedNote.fragmentCategory" :key="cat" class="bg-purple-100 text-purple-700 px-2 py-1 rounded text-xs">
                    {{ cat }}
                  </span>
                  <span v-for="theme in selectedNote.fragmentTheme" :key="theme" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                    {{ theme }}
                  </span>
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
    notesData.value = await getNotesByType('fragment')
  } catch (error) {
    console.error('加载碎片笔记失败:', error)
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

// 获取碎片笔记（使用异步加载的数据）
const fragmentNotes = computed(() => {
  return notesData.value
})

// 获取唯一分类
const uniqueCategories = computed(() => {
  const categories = new Set<string>()
  fragmentNotes.value.forEach(note => {
    if (note.fragmentCategory && Array.isArray(note.fragmentCategory)) {
      note.fragmentCategory.forEach(cat => categories.add(cat))
    }
  })
  return Array.from(categories)
})

// 获取唯一主题
const uniqueThemes = computed(() => {
  const themes = new Set<string>()
  fragmentNotes.value.forEach(note => {
    if (note.fragmentTheme && Array.isArray(note.fragmentTheme)) {
      note.fragmentTheme.forEach(theme => themes.add(theme))
    }
  })
  return Array.from(themes)
})

// 本月新增笔记
const thisMonthNotes = computed(() => {
  const now = new Date()
  const thisMonth = now.getMonth()
  const thisYear = now.getFullYear()
  
  return fragmentNotes.value.filter(note => {
    const noteDate = new Date(note.createTime)
    return noteDate.getMonth() === thisMonth && noteDate.getFullYear() === thisYear
  })
})

// 上月笔记
const lastMonthNotes = computed(() => {
  const now = new Date()
  const lastMonth = now.getMonth() - 1
  const lastMonthYear = lastMonth < 0 ? now.getFullYear() - 1 : now.getFullYear()
  const adjustedLastMonth = lastMonth < 0 ? 11 : lastMonth
  
  return fragmentNotes.value.filter(note => {
    const noteDate = new Date(note.createTime)
    return noteDate.getMonth() === adjustedLastMonth && noteDate.getFullYear() === lastMonthYear
  })
})

// 月度增长
const monthlyGrowth = computed(() => {
  return thisMonthNotes.value.length - lastMonthNotes.value.length
})

// 本月新增分类
const thisMonthCategories = computed(() => {
  const now = new Date()
  const thisMonth = now.getMonth()
  const thisYear = now.getFullYear()
  
  const categories = new Set<string>()
  fragmentNotes.value.forEach(note => {
    if (note.fragmentCategory && Array.isArray(note.fragmentCategory)) {
      note.fragmentCategory.forEach(cat => {
        if (new Date(note.createTime).getMonth() === thisMonth && new Date(note.createTime).getFullYear() === thisYear) {
          categories.add(cat)
        }
      })
    }
  })
  return Array.from(categories)
})

// 平均字数
const averageWordCount = computed(() => {
  if (fragmentNotes.value.length === 0) return 0
  
  const totalWords = fragmentNotes.value.reduce((sum, note) => {
    const textContent = note.content.replace(/<[^>]*>/g, '')
    return sum + textContent.length
  }, 0)
  
  return Math.round(totalWords / fragmentNotes.value.length)
})

// 分类统计
const categoryStats = computed(() => {
  const stats: Record<string, number> = {}
  let totalOccurrences = 0
  fragmentNotes.value.forEach(note => {
    if (note.fragmentCategory && Array.isArray(note.fragmentCategory)) {
      note.fragmentCategory.forEach(cat => {
        stats[cat] = (stats[cat] || 0) + 1
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
  return fragmentNotes.value
    .sort((a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime())
    .slice(0, 5)
})

// 思考概览统计
const thinkingDays = computed(() => {
  const uniqueDays = new Set<string>()
  fragmentNotes.value.forEach(note => {
    const date = new Date(note.createTime).toDateString()
    uniqueDays.add(date)
  })
  return uniqueDays.size
})

const maxConsecutiveDays = computed(() => {
  if (fragmentNotes.value.length === 0) return 0
  
  const dates = fragmentNotes.value.map(note => new Date(note.createTime).toDateString())
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

const averageNotesPerCategory = computed(() => {
  if (uniqueCategories.value.length === 0) return 0
  return Math.round(fragmentNotes.value.length / uniqueCategories.value.length)
})

const avgNotesPerDay = computed(() => {
  if (thinkingDays.value === 0) return 0
  return Math.round((fragmentNotes.value.length / thinkingDays.value) * 10) / 10
})

// 最近7天活动统计
const recentWeekActivity = computed(() => {
  const today = new Date()
  const activity = []
  for (let i = 6; i >= 0; i--) {
    const date = new Date(today)
    date.setDate(date.getDate() - i)
    const dateStr = date.toDateString()
    
    const hasNote = fragmentNotes.value.some(note => {
      const noteDate = new Date(note.createTime)
      return noteDate.toDateString() === dateStr
    })
    activity.push(hasNote)
  }
  return activity
})

const recentWeekDays = computed(() => {
  return recentWeekActivity.value.filter(hasNote => hasNote).length
})

// 主题统计
const themeStats = computed(() => {
  const stats: Record<string, number> = {}
  let totalOccurrences = 0
  fragmentNotes.value.forEach(note => {
    if (note.fragmentTheme && Array.isArray(note.fragmentTheme)) {
      note.fragmentTheme.forEach(theme => {
        stats[theme] = (stats[theme] || 0) + 1
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

const mostActiveTheme = computed(() => {
  if (themeStats.value.length === 0) return { name: '暂无', count: 0 }
  return themeStats.value[0]
})

const topThemes = computed(() => {
  return themeStats.value.slice(0, 3)
})

// 分类统计
const mostActiveCategory = computed(() => {
  if (categoryStats.value.length === 0) return { name: '暂无', count: 0 }
  return categoryStats.value[0]
})

const topCategories = computed(() => {
  return categoryStats.value.slice(0, 3)
})

// 重要性统计
const highImportanceCount = computed(() => {
  return fragmentNotes.value.filter(note => note.importance === 'high').length
})

const mediumImportanceCount = computed(() => {
  return fragmentNotes.value.filter(note => note.importance === 'medium').length
})

const lowImportanceCount = computed(() => {
  return fragmentNotes.value.filter(note => note.importance === 'low').length
})

const importanceDistribution = computed(() => {
  const total = fragmentNotes.value.length
  if (total === 0) return { high: 0, medium: 0, low: 0 }
  
  return {
    high: Math.round((highImportanceCount.value / total) * 100),
    medium: Math.round((mediumImportanceCount.value / total) * 100),
    low: Math.round((lowImportanceCount.value / total) * 100)
  }
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
  fragmentNotes.value.forEach(note => {
    const d = new Date(note.createTime)
    const key = `${d.getFullYear()}-${d.getMonth() + 1}`
    if (map[key] !== undefined) map[key]++
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
      borderColor: 'rgb(147,51,234)',
      backgroundColor: 'rgba(147,51,234,0.1)',
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

// 重要性分布
const importanceRanges = computed(() => {
  const levels = ['high', 'medium', 'low']
  
  const counts = levels.map(level => {
    return fragmentNotes.value.filter(note => note.importance === level).length
  })
  
  return { labels: ['高', '中', '低'], data: counts }
})

const importanceChartData = computed(() => ({
  labels: importanceRanges.value.labels,
  datasets: [
    {
      label: '笔记数量',
      data: importanceRanges.value.data,
      backgroundColor: 'rgba(147,51,234,0.7)'
    }
  ]
}))

const importanceChartOptions = {
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
const categoriesPerPage = 5
const currentCategoryPage = ref(1)
const totalCategoryPages = computed(() => Math.ceil(categoryStats.value.length / categoriesPerPage))
const paginatedCategoryStats = computed(() => {
  const start = (currentCategoryPage.value - 1) * categoriesPerPage
  const end = start + categoriesPerPage
  return categoryStats.value.slice(start, end)
})

// 笔记列表过滤逻辑
const searchQuery = ref('')
const selectedCategory = ref('')
const selectedTheme = ref('')
const selectedImportance = ref('')
const filteredNotes = computed(() => {
  return fragmentNotes.value.filter(note => {
    const matchesSearch = note.title.toLowerCase().includes(searchQuery.value.toLowerCase()) || 
                         (note.summary && note.summary.toLowerCase().includes(searchQuery.value.toLowerCase()))
    const matchesCategory = selectedCategory.value === '' || 
                           (note.fragmentCategory && note.fragmentCategory.includes(selectedCategory.value))
    const matchesTheme = selectedTheme.value === '' || 
                        (note.fragmentTheme && note.fragmentTheme.includes(selectedTheme.value))
    const matchesImportance = selectedImportance.value === '' || note.importance === selectedImportance.value
    return matchesSearch && matchesCategory && matchesTheme && matchesImportance
  })
})

// 笔记详情逻辑
const showNoteDetail = ref(false)
const selectedNote = ref<any>(null)
const viewNoteDetail = (note: any) => {
  selectedNote.value = note
  showNoteDetail.value = true
}

// 重要性类和文本
const getImportanceClass = (importance: string) => {
  switch (importance) {
    case 'high':
      return 'bg-red-100 text-red-700'
    case 'medium':
      return 'bg-yellow-100 text-yellow-700'
    case 'low':
      return 'bg-green-100 text-green-700'
    default:
      return ''
  }
}

const getImportanceText = (importance: string) => {
  switch (importance) {
    case 'high':
      return '高'
    case 'medium':
      return '中'
    case 'low':
      return '低'
    default:
      return ''
  }
}

// 笔记列表弹窗逻辑
const showNotesList = ref(false)


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