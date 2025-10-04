<template>
  <div class="p-6 bg-gray-50 min-h-screen">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-2">学习笔记</h1>
      <p class="text-gray-600">课程学习和知识掌握的统计概览</p>
    </div>

    <!-- 统计卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-5 gap-6 mb-8">
      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">总笔记数</p>
            <p class="text-3xl font-bold text-gray-900">{{ studyNotes.length }}</p>
            <p class="text-xs text-green-600 mt-1">+{{ thisMonthNotes.length }} 本月新增</p>
          </div>
          <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">课程数量</p>
            <p class="text-3xl font-bold text-gray-900">{{ uniqueCourses.length }}</p>
            <p class="text-xs text-blue-600 mt-1">学习课程</p>
          </div>
          <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"></path>
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

      <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">本月新增课程</p>
            <p class="text-3xl font-bold text-gray-900">{{ thisMonthCourses.length }}</p>
            <p class="text-xs text-purple-600 mt-1">新增学习课程</p>
          </div>
          <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
            <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
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
              <p class="text-sm text-gray-600">浏览所有求学笔记</p>
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

    <!-- 学习概览 -->
    <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-xl font-semibold text-gray-900">学习概览</h2>
        <div class="text-sm text-gray-500">学习统计数据概览</div>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
        <!-- 总笔记数 -->
        <div class="relative p-6 bg-gradient-to-br from-blue-50 to-blue-100 rounded-xl border border-blue-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-blue-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-book text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-blue-600">{{ studyNotes.length }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-blue-900">总笔记数</p>
            <p class="text-xs text-blue-700">累计学习笔记</p>
          </div>
          <!-- 本月新增 -->
          <div class="text-xs text-blue-600 mb-2">
            <span>本月新增：{{ thisMonthNotes.length }}篇</span>
          </div>
          <div class="space-y-1">
            <div class="flex items-center justify-between text-xs">
              <span>当前总数</span>
              <div class="flex-1 mx-2 bg-blue-200 rounded-full h-1">
                <div class="bg-blue-500 h-1 rounded-full" :style="{ width: Math.min((studyNotes.length / Math.max(studyNotes.length, 50)) * 100, 100) + '%' }"></div>
              </div>
              <span class="text-blue-600">{{ studyNotes.length }}</span>
            </div>
          </div>
        </div>

        <!-- 学科数量 -->
        <div class="relative p-6 bg-gradient-to-br from-green-50 to-green-100 rounded-xl border border-green-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-green-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-graduation-cap text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-green-600">{{ uniqueSubjects.length }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-green-900">学科数量</p>
            <p class="text-xs text-green-700">涉及学习领域</p>
          </div>
          <!-- 最活跃学科 -->
          <div class="text-xs text-green-600 mb-2">
            <span>最活跃：{{ mostActiveSubject.name }}({{ mostActiveSubject.count }}篇)</span>
          </div>
          <div class="space-y-1">
            <div v-for="subject in topSubjects" :key="subject.name" class="flex items-center text-xs">
              <div class="w-2 h-2 bg-green-500 rounded-full mr-2"></div>
              <span class="flex-1 truncate">{{ subject.name }}</span>
              <span class="text-green-600">{{ subject.count }}</span>
            </div>
          </div>
        </div>

        <!-- 本月新增 -->
        <div class="relative p-6 bg-gradient-to-br from-orange-50 to-orange-100 rounded-xl border border-orange-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-orange-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-calendar-plus text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-orange-600">{{ thisMonthNotes.length }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-orange-900">本月新增</p>
            <p class="text-xs text-orange-700">当前月份新增</p>
          </div>
          <!-- 月度对比 -->
          <div class="text-xs text-orange-600 mb-2">
            <span>较上月：{{ monthlyGrowth >= 0 ? '+' : '' }}{{ monthlyGrowth }}篇</span>
          </div>
          <div class="space-y-1">
            <div class="flex items-center justify-between text-xs">
              <span>本月</span>
              <div class="flex-1 mx-2 bg-orange-200 rounded-full h-1">
                <div class="bg-orange-500 h-1 rounded-full" :style="{ width: Math.min((thisMonthNotes.length / Math.max(thisMonthNotes.length, lastMonthNotes.length, 1)) * 100, 100) + '%' }"></div>
              </div>
              <span class="text-orange-600">{{ thisMonthNotes.length }}</span>
            </div>
            <div class="flex items-center justify-between text-xs">
              <span>上月</span>
              <div class="flex-1 mx-2 bg-orange-200 rounded-full h-1">
                <div class="bg-orange-400 h-1 rounded-full" :style="{ width: Math.min((lastMonthNotes.length / Math.max(thisMonthNotes.length, lastMonthNotes.length, 1)) * 100, 100) + '%' }"></div>
              </div>
              <span class="text-orange-500">{{ lastMonthNotes.length }}</span>
            </div>
          </div>
        </div>

        <!-- 学习效率 -->
        <div class="relative p-6 bg-gradient-to-br from-purple-50 to-purple-100 rounded-xl border border-purple-200 hover:shadow-md transition-all duration-200">
          <div class="flex items-center justify-between mb-4">
            <div class="w-12 h-12 bg-purple-500 rounded-lg flex items-center justify-center">
              <i class="fas fa-chart-line text-white text-lg"></i>
            </div>
            <span class="text-3xl font-bold text-purple-600">{{ dailyAverage }}</span>
          </div>
          <div class="mb-3">
            <p class="text-sm font-semibold text-purple-900">学习效率</p>
            <p class="text-xs text-purple-700">日均笔记数量</p>
          </div>
                     <!-- 活跃度指标 -->
           <div class="text-xs text-purple-600 mb-2">
             <span>学习天数：{{ learningDays }}天</span>
           </div>
          <div class="space-y-1">
            <div class="flex items-center justify-between text-xs">
              <span>效率指数</span>
              <div class="flex-1 mx-2 bg-purple-200 rounded-full h-1">
                <div class="bg-purple-500 h-1 rounded-full" :style="{ width: Math.min((dailyAverage / 5) * 100, 100) + '%' }"></div>
              </div>
              <span class="text-purple-600">{{ efficiencyLevel }}</span>
            </div>
            <div class="flex items-center text-xs space-x-1">
              <span v-for="n in 5" :key="n" class="text-lg">
                {{ n <= Math.ceil(dailyAverage) ? '⭐' : '☆' }}
              </span>
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
                <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
                  <i class="fas fa-graduation-cap text-2xl text-blue-600"></i>
                </div>
                <div>
                  <h3 class="text-2xl font-semibold text-gray-900">求学笔记列表</h3>
                  <p class="text-sm text-gray-500">{{ studyNotes.length }} 条笔记</p>
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
                      class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
                    >
                  </div>
                </div>
                <div>
                  <select v-model="selectedSubject" class="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
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
                    <h5 class="text-sm font-medium text-gray-900 line-clamp-2 group-hover:text-blue-600 transition-colors">{{ note.title }}</h5>
                  </div>
                  <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
                  <div class="flex items-center justify-between text-xs text-gray-500 mb-2">
                    <span>{{ formatDate(note.createTime) }}</span>
                  </div>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="subject in note.studySubject?.slice(0, 2)" :key="subject" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                      {{ subject }}
                    </span>
                    <span v-for="point in note.knowledgePoint?.slice(0, 2)" :key="point" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                      {{ point }}
                    </span>
                    <span v-if="(note.studySubject?.length || 0) + (note.knowledgePoint?.length || 0) > 4" class="text-xs text-gray-500 px-2 py-1">
                      +{{ (note.studySubject?.length || 0) + (note.knowledgePoint?.length || 0) - 4 }}
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
              </div>
              
              <div class="bg-gray-50 p-4 rounded-lg">
                <p class="text-sm font-medium text-gray-700 mb-2">摘要：</p>
                <p class="text-sm text-gray-600">{{ selectedNote.summary }}</p>
              </div>
              
              <div>
                <p class="text-sm font-medium text-gray-700 mb-2">详细内容：</p>
                <div class="text-sm text-gray-600 prose prose-sm max-w-none" v-html="selectedNote.content"></div>
              </div>
              
              <div class="space-y-3">
                <div class="flex items-center space-x-4 text-sm text-gray-500">
                  <span>创建时间：{{ formatDate(selectedNote.createTime) }}</span>
                </div>
                
                <!-- 学科标签 -->
                <div v-if="selectedNote.studySubject && selectedNote.studySubject.length > 0" class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">学科：</span>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="subject in selectedNote.studySubject" :key="subject" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs">
                      {{ subject }}
                    </span>
                  </div>
                </div>
                
                <!-- 知识点标签 -->
                <div v-if="selectedNote.knowledgePoint && selectedNote.knowledgePoint.length > 0" class="flex items-center space-x-2">
                  <span class="text-sm font-medium text-gray-700">知识点：</span>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="point in selectedNote.knowledgePoint" :key="point" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs">
                      {{ point }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>

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

          <!-- 笔记字数分布 -->
          <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
            <h2 class="text-xl font-semibold text-gray-900 mb-6">笔记字数分布</h2>
            <div class="h-80">
              <Bar :data="wordCountChartData" :options="wordCountChartOptions" />
        </div>
      </div>
    </div>

        <!-- 课程分布图表 -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <h2 class="text-xl font-semibold text-gray-900 mb-6">课程分布统计</h2>
          <div class="h-80 overflow-y-auto">
            <div class="space-y-4">
              <div v-for="course in paginatedCourseStats" :key="course.name" class="flex items-center">
                <div class="w-40 text-sm text-gray-600 truncate">{{ course.name }}</div>
                <div class="flex-1 mx-4">
                  <div class="w-full bg-gray-200 rounded-full h-3">
                    <div class="bg-gradient-to-r from-green-500 to-green-600 h-3 rounded-full transition-all duration-300" 
                         :style="{ width: course.percentage + '%' }"></div>
                  </div>
                </div>
                <div class="w-20 text-right">
                  <span class="text-sm font-semibold text-gray-900">{{ course.count }}</span>
                  <span class="text-xs text-gray-500 ml-1">({{ course.percentage }}%)</span>
                </div>
              </div>
                  </div>
            <!-- 分页控件 -->
            <div class="flex items-center justify-between mt-6 pt-4 border-t border-gray-200">
              <div class="text-sm text-gray-600">
                显示 {{ (currentCoursePage - 1) * coursesPerPage + 1 }} - {{ Math.min(currentCoursePage * coursesPerPage, courseStats.length) }} 共 {{ courseStats.length }} 项
                </div>
              <div class="flex items-center space-x-2">
                <button 
                  @click="currentCoursePage = Math.max(1, currentCoursePage - 1)"
                  :disabled="currentCoursePage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">
                  上一页
                </button>
                <span class="text-sm text-gray-600">
                  {{ currentCoursePage }} / {{ totalCoursePages }}
                </span>
                <button 
                  @click="currentCoursePage = Math.min(totalCoursePages, currentCoursePage + 1)"
                  :disabled="currentCoursePage === totalCoursePages"
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
              <span v-for="subject in note.studySubject?.slice(0, 2)" :key="subject" class="bg-blue-100 text-blue-700 px-2 py-1 rounded-md text-xs font-medium">
                {{ subject }}
              </span>
              <span v-for="point in note.knowledgePoint?.slice(0, 2)" :key="point" class="bg-green-100 text-green-700 px-2 py-1 rounded-md text-xs font-medium">
                {{ point }}
              </span>
              <span v-if="(note.studySubject?.length || 0) + (note.knowledgePoint?.length || 0) > 4" class="text-xs text-gray-500 px-2 py-1">
                +{{ (note.studySubject?.length || 0) + (note.knowledgePoint?.length || 0) - 4 }}
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
  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue'
import { getNotesByType } from '@/services/noteService'

// 响应式笔记数据
const notesData = ref<any[]>([])

// 加载数据
onMounted(async () => {
  try {
    notesData.value = await getNotesByType('study')
  } catch (error) {
    console.error('加载求学笔记失败:', error)
  }
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

// 获取学习笔记（使用异步加载的数据）
const studyNotes = computed(() => {
  return notesData.value
})

// 获取唯一课程
const uniqueCourses = computed(() => {
  const courses = new Set<string>()
  studyNotes.value.forEach(note => {
    if (note.course && Array.isArray(note.course)) {
      note.course.forEach(course => courses.add(course))
    }
  })
  return Array.from(courses)
})

// 获取唯一学科
const uniqueSubjects = computed(() => {
  const subjects = new Set<string>()
  studyNotes.value.forEach(note => {
    if (note.studySubject && Array.isArray(note.studySubject)) {
      note.studySubject.forEach(subject => subjects.add(subject))
    }
  })
  return Array.from(subjects)
})

// 获取唯一知识点
const uniqueKnowledgePoints = computed(() => {
  const points = new Set<string>()
  studyNotes.value.forEach(note => {
    if (note.knowledgePoint && Array.isArray(note.knowledgePoint)) {
      note.knowledgePoint.forEach(point => points.add(point))
    }
  })
  return Array.from(points)
})

// 本月新增笔记
const thisMonthNotes = computed(() => {
  const now = new Date()
  const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1)
  const endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0)
  
  return studyNotes.value.filter(note => {
    const noteDate = new Date(note.createTime)
    return noteDate >= startOfMonth && noteDate <= endOfMonth
  })
})

// 本月新增课程
const thisMonthCourses = computed(() => {
  const now = new Date()
  const thisMonth = now.getMonth()
  const thisYear = now.getFullYear()
  
  const courses = new Set<string>()
  studyNotes.value.forEach(note => {
    if (note.course && Array.isArray(note.course)) {
      note.course.forEach(course => {
        if (new Date(note.createTime).getMonth() === thisMonth && new Date(note.createTime).getFullYear() === thisYear) {
          courses.add(course)
        }
      })
    }
  })
  return Array.from(courses)
})

// 平均字数
const averageWordCount = computed(() => {
  if (studyNotes.value.length === 0) return 0
  
  const totalWords = studyNotes.value.reduce((sum, note) => {
    const textContent = note.content.replace(/<[^>]*>/g, '')
    return sum + textContent.length
  }, 0)
  
  return Math.round(totalWords / studyNotes.value.length)
})

// 课程统计
const courseStats = computed(() => {
  const stats: Record<string, number> = {}
  let totalOccurrences = 0
  studyNotes.value.forEach(note => {
    if (note.course && Array.isArray(note.course)) {
      note.course.forEach(course => {
        stats[course] = (stats[course] || 0) + 1
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
  return studyNotes.value
    .sort((a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime())
    .slice(0, 5)
})

// 学习概览统计
const learningDays = computed(() => {
  const uniqueDays = new Set<string>()
  studyNotes.value.forEach(note => {
    const date = new Date(note.createTime).toDateString()
    uniqueDays.add(date)
  })
  return uniqueDays.size
})

// 上月笔记
const lastMonthNotes = computed(() => {
  const now = new Date()
  const lastMonth = now.getMonth() - 1
  const lastMonthYear = lastMonth < 0 ? now.getFullYear() - 1 : now.getFullYear()
  const adjustedLastMonth = lastMonth < 0 ? 11 : lastMonth
  
  return studyNotes.value.filter(note => {
    const noteDate = new Date(note.createTime)
    return noteDate.getMonth() === adjustedLastMonth && noteDate.getFullYear() === lastMonthYear
  })
})

// 月度增长
const monthlyGrowth = computed(() => {
  return thisMonthNotes.value.length - lastMonthNotes.value.length
})

// 学科统计
const subjectStats = computed(() => {
  const stats: Record<string, number> = {}
  studyNotes.value.forEach(note => {
    if (note.studySubject && Array.isArray(note.studySubject)) {
      note.studySubject.forEach(subject => {
        stats[subject] = (stats[subject] || 0) + 1
      })
    }
  })
  return Object.entries(stats)
    .map(([name, count]) => ({ name, count }))
    .sort((a, b) => b.count - a.count)
})

// 最活跃学科
const mostActiveSubject = computed(() => {
  const stats = subjectStats.value
  return stats.length > 0 ? stats[0] : { name: '暂无', count: 0 }
})

// 前3个最活跃学科
const topSubjects = computed(() => {
  return subjectStats.value.slice(0, 3)
})

// 日均笔记数
const dailyAverage = computed(() => {
  if (learningDays.value === 0) return 0
  return Math.round((studyNotes.value.length / learningDays.value) * 10) / 10
})

// 效率等级
const efficiencyLevel = computed(() => {
  const avg = dailyAverage.value
  if (avg >= 4) return '极高'
  if (avg >= 3) return '较高'
  if (avg >= 2) return '中等'
  if (avg >= 1) return '较低'
  return '偏低'
})

const maxConsecutiveDays = computed(() => {
  if (studyNotes.value.length === 0) return 0
  
  const dates = studyNotes.value.map(note => new Date(note.createTime).toDateString())
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

const averageNotesPerCourse = computed(() => {
  if (uniqueCourses.value.length === 0) return 0
  return Math.round(studyNotes.value.length / uniqueCourses.value.length)
})

const avgNotesPerDay = computed(() => {
  if (learningDays.value === 0) return 0
  return Math.round((studyNotes.value.length / learningDays.value) * 10) / 10
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
  studyNotes.value.forEach(note => {
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

// 笔记字数分布
const wordCountRanges = computed(() => {
  const ranges = [
    { label: '0-500字', min: 0, max: 500 },
    { label: '500-1000字', min: 500, max: 1000 },
    { label: '1000-2000字', min: 1000, max: 2000 },
    { label: '2000字以上', min: 2000, max: Infinity }
  ]
  
  const counts = ranges.map(range => {
    return studyNotes.value.filter(note => {
      const wordCount = getWordCount(note.content)
      return wordCount >= range.min && wordCount < range.max
    }).length
  })
  
  return { labels: ranges.map(r => r.label), data: counts }
})

const wordCountChartData = computed(() => ({
  labels: wordCountRanges.value.labels,
  datasets: [
    {
      label: '笔记数量',
      data: wordCountRanges.value.data,
      backgroundColor: 'rgba(34,197,94,0.7)'
    }
  ]
}))

const wordCountChartOptions = {
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

// 获取字数
const getWordCount = (content: string) => {
  const textContent = content.replace(/<[^>]*>/g, '')
  return textContent.length
}

// 格式化日期
const formatDate = (dateString: string) => {
  const d = new Date(dateString)
  const pad = (n: number) => String(n).padStart(2, '0')
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}

// 分页逻辑
const coursesPerPage = 5
const currentCoursePage = ref(1)
const totalCoursePages = computed(() => Math.ceil(courseStats.value.length / coursesPerPage))
const paginatedCourseStats = computed(() => {
  const start = (currentCoursePage.value - 1) * coursesPerPage
  const end = start + coursesPerPage
  return courseStats.value.slice(start, end)
})

// 笔记列表弹窗
const showNotesList = ref(false)
const searchQuery = ref('')
const selectedSubject = ref('')


const filteredNotes = computed(() => {
  return studyNotes.value.filter(note => {
    const matchesSearch = note.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
                         note.summary.toLowerCase().includes(searchQuery.value.toLowerCase())
    const matchesSubject = selectedSubject.value === '' || note.studySubject?.includes(selectedSubject.value)
    return matchesSearch && matchesSubject
  })
})

// 笔记详情弹窗
const showNoteDetail = ref(false)
const selectedNote = ref(null)

const viewNoteDetail = (note) => {
  selectedNote.value = note
  showNoteDetail.value = true
}


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