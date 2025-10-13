<template>
  <section id="dashboard" class="p-6 animate-fade-in">
    <!-- 倒计时提醒 -->
    <div v-if="countdownEvents.length > 0" class="bg-gradient-to-r from-purple-50 to-indigo-50 rounded-xl shadow-soft p-6 mb-6 border border-purple-200">
      <div class="flex items-center mb-4">
        <i class="fas fa-hourglass-half text-purple-600 text-xl mr-3"></i>
        <h3 class="text-xl font-semibold text-gray-900">重要日期倒计时</h3>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div 
          v-for="event in countdownEvents" 
          :key="event.id"
          class="bg-white rounded-lg p-4 border-l-4 hover:shadow-md transition-shadow"
          :class="getDaysUntilClass(event.daysUntil)"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <h4 class="font-semibold text-gray-900 mb-1">{{ event.eventTitle }}</h4>
              <p class="text-sm text-gray-600 mb-2">{{ formatEventDate(event.eventDate) }}</p>
              <div class="flex items-center">
                <span class="text-2xl font-bold" :class="getDaysTextColor(event.daysUntil)">
                  {{ Math.abs(event.daysUntil) }}
                </span>
                <span class="ml-2 text-sm text-gray-600">
                  {{ event.daysUntil > 0 ? '天后' : event.daysUntil === 0 ? '今天' : '天前' }}
                </span>
              </div>
            </div>
            <div class="ml-4">
              <span class="text-3xl">{{ getEventEmoji(event.daysUntil) }}</span>
            </div>
          </div>
          <p v-if="event.description" class="text-xs text-gray-500 mt-2 line-clamp-2">{{ event.description }}</p>
        </div>
      </div>
    </div>

    <!-- 空状态（无倒计时） -->
    <div v-else class="bg-gradient-to-r from-gray-50 to-gray-100 rounded-xl shadow-soft p-6 mb-6 border border-gray-200">
      <div class="text-center py-4">
        <i class="fas fa-calendar-check text-gray-400 text-4xl mb-3"></i>
        <p class="text-gray-500 text-sm">暂无重要日期倒计时</p>
        <p class="text-gray-400 text-xs mt-1">在"系统决策"页面的日历中添加日期并启用倒计时功能</p>
      </div>
    </div>

    <!-- 荣誉战绩 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="mb-6">
        <h3 class="text-xl font-semibold text-gray-900 flex items-center">
          <i class="fa-solid fa-trophy text-warning mr-3"></i>
          荣誉战绩
        </h3>
        <p class="text-gray-600 text-sm mt-1">重要成就与里程碑</p>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- 战争行动成就 -->
        <div class="bg-gradient-to-br from-red-50 to-red-100 rounded-lg p-5 border border-red-200 hover:shadow-lg transition-shadow">
          <div class="flex items-center justify-between mb-4">
            <h4 class="text-lg font-semibold text-gray-900 flex items-center">
              <i class="fa-solid fa-crosshairs text-red-600 mr-2"></i>
              战争行动
            </h4>
            <span class="bg-red-200 text-red-700 px-3 py-1 rounded-full text-sm font-medium">已攻克</span>
          </div>
          <div class="space-y-4">
            <div v-for="honor in displayedDefenseHonors" :key="honor.id" class="flex items-center justify-between p-3 bg-white/50 rounded-lg border border-red-100 group">
              <div class="flex items-center">
                <i class="fa-solid fa-trophy text-gray-600 mr-3 text-lg"></i>
                <div>
                  <span class="text-gray-900 font-medium">{{ honor.title }}</span>
                  <p class="text-xs text-gray-500">{{ honor.description }}</p>
                </div>
              </div>
              <div class="flex items-center space-x-2">
                <span class="text-gray-500 text-sm font-medium">{{ formatAchievedTime(honor.achievedTime) }}</span>
                <button 
                  @click="removeHonor(honor.id)"
                  class="opacity-0 group-hover:opacity-100 text-red-500 hover:text-red-700 transition-opacity"
                  title="删除荣誉战绩"
                >
                  <i class="fas fa-times text-xs"></i>
                </button>
            </div>
                </div>
            <div v-if="defenseHonors.length === 0" class="text-center py-4 text-gray-500">
              <p class="text-sm">暂无战争行动荣誉战绩</p>
            </div>
            <!-- 展开/折叠按钮 -->
            <button 
              v-if="defenseHonors.length > 3"
              @click="toggleDefenseHonors"
              class="w-full py-2 text-sm text-red-600 hover:text-red-700 hover:bg-red-50 rounded-lg transition-colors flex items-center justify-center space-x-2"
            >
              <span>{{ isDefenseHonorsCollapsed ? '展开全部' : '收起' }}</span>
              <i :class="`fas fa-chevron-${isDefenseHonorsCollapsed ? 'down' : 'up'} text-xs`"></i>
            </button>
          </div>
        </div>

        <!-- 工程建设成就 -->
        <div class="bg-gradient-to-br from-blue-50 to-blue-100 rounded-lg p-5 border border-blue-200 hover:shadow-lg transition-shadow">
          <div class="flex items-center justify-between mb-4">
            <h4 class="text-lg font-semibold text-gray-900 flex items-center">
              <i class="fa-solid fa-building text-blue-600 mr-2"></i>
              工程建设
            </h4>
            <span class="bg-blue-200 text-blue-700 px-3 py-1 rounded-full text-sm font-medium">已完成</span>
          </div>
          <div class="space-y-4">
            <div v-for="honor in displayedConstructionHonors" :key="honor.id" class="flex items-center justify-between p-3 bg-white/50 rounded-lg border border-blue-100 group">
              <div class="flex items-center">
                <i class="fa-solid fa-trophy text-gray-600 mr-3 text-lg"></i>
                <div>
                  <span class="text-gray-900 font-medium">{{ honor.title }}</span>
                  <p class="text-xs text-gray-500">{{ honor.description }}</p>
                </div>
              </div>
              <div class="flex items-center space-x-2">
                <span class="text-gray-500 text-sm font-medium">{{ formatAchievedTime(honor.achievedTime) }}</span>
                <button 
                  @click="removeHonor(honor.id)"
                  class="opacity-0 group-hover:opacity-100 text-red-500 hover:text-red-700 transition-opacity"
                  title="删除荣誉战绩"
                >
                  <i class="fas fa-times text-xs"></i>
                </button>
            </div>
                </div>
            <div v-if="constructionHonors.length === 0" class="text-center py-4 text-gray-500">
              <p class="text-sm">暂无工程建设荣誉战绩</p>
            </div>
            <!-- 展开/折叠按钮 -->
            <button 
              v-if="constructionHonors.length > 3"
              @click="toggleConstructionHonors"
              class="w-full py-2 text-sm text-blue-600 hover:text-blue-700 hover:bg-blue-50 rounded-lg transition-colors flex items-center justify-center space-x-2"
            >
              <span>{{ isConstructionHonorsCollapsed ? '展开全部' : '收起' }}</span>
              <i :class="`fas fa-chevron-${isConstructionHonorsCollapsed ? 'down' : 'up'} text-xs`"></i>
            </button>
          </div>
        </div>

        <!-- 外交行动成就 -->
        <div class="bg-gradient-to-br from-green-50 to-green-100 rounded-lg p-5 border border-green-200 hover:shadow-lg transition-shadow">
          <div class="flex items-center justify-between mb-4">
            <h4 class="text-lg font-semibold text-gray-900 flex items-center">
              <i class="fa-solid fa-handshake text-green-600 mr-2"></i>
              外交行动
            </h4>
            <span class="bg-green-200 text-green-700 px-3 py-1 rounded-full text-sm font-medium">已达成</span>
          </div>
          <div class="space-y-4">
            <div v-for="honor in displayedDiplomaticHonors" :key="honor.id" class="flex items-center justify-between p-3 bg-white/50 rounded-lg border border-green-100 group">
              <div class="flex items-center">
                <i class="fa-solid fa-trophy text-gray-600 mr-3 text-lg"></i>
                <div>
                  <span class="text-gray-900 font-medium">{{ honor.title }}</span>
                  <p class="text-xs text-gray-500">{{ honor.description }}</p>
                </div>
              </div>
              <div class="flex items-center space-x-2">
                <span class="text-gray-500 text-sm font-medium">{{ formatAchievedTime(honor.achievedTime) }}</span>
                <button 
                  @click="removeHonor(honor.id)"
                  class="opacity-0 group-hover:opacity-100 text-red-500 hover:text-red-700 transition-opacity"
                  title="删除荣誉战绩"
                >
                  <i class="fas fa-times text-xs"></i>
                </button>
            </div>
                </div>
            <div v-if="diplomaticHonors.length === 0" class="text-center py-4 text-gray-500">
              <p class="text-sm">暂无外交行动荣誉战绩</p>
            </div>
            <!-- 展开/折叠按钮 -->
            <button 
              v-if="diplomaticHonors.length > 3"
              @click="toggleDiplomaticHonors"
              class="w-full py-2 text-sm text-green-600 hover:text-green-700 hover:bg-green-50 rounded-lg transition-colors flex items-center justify-center space-x-2"
            >
              <span>{{ isDiplomaticHonorsCollapsed ? '展开全部' : '收起' }}</span>
              <i :class="`fas fa-chevron-${isDiplomaticHonorsCollapsed ? 'down' : 'up'} text-xs`"></i>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 打卡记录 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="mb-6">
        <h3 class="text-xl font-semibold text-gray-900 flex items-center">
          <i class="fa-solid fa-calendar-days text-gray-900 mr-3"></i>
          打卡记录
        </h3>
        <p class="text-gray-600 text-sm mt-1">习惯养成与目标追踪</p>
      </div>

      <!-- 空状态 -->
      <div v-if="activeCheckinItems.length === 0" class="text-center py-12 text-gray-500">
        <i class="fas fa-check-circle text-6xl mb-4 text-gray-300"></i>
        <p class="text-lg font-medium mb-2">暂无打卡项目</p>
        <p class="text-sm">前往"系统决策"创建打卡项目开始你的习惯养成之旅</p>
        <button 
          @click="$router.push('/system-decisions')"
          class="mt-4 px-6 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors"
        >
          去创建
        </button>
      </div>

      <!-- 打卡项目网格 -->
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div v-for="item in activeCheckinItems" :key="item.id" 
          class="bg-gradient-to-br from-purple-50 to-purple-100 rounded-lg p-5 border border-purple-200 hover:shadow-lg transition-shadow">
          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center space-x-3">
              <div class="w-10 h-10 bg-purple-600 rounded-lg flex items-center justify-center">
                <i class="fas fa-check text-white text-lg"></i>
              </div>
              <div>
                <h4 class="text-lg font-semibold text-gray-900">{{ item.title }}</h4>
                <span :class="getFrequencyBadgeClass(item.frequency)" class="text-xs px-2 py-0.5 rounded-full">
                  {{ getFrequencyLabel(item.frequency) }}
                </span>
              </div>
            </div>
          </div>

          <!-- 打卡状态提醒 -->
          <div v-if="!checkinStatusMap[item.id!]" class="mb-3 p-3 bg-yellow-50 border border-yellow-200 rounded-lg">
            <div class="flex items-center space-x-2">
              <i class="fas fa-exclamation-circle text-yellow-600"></i>
              <span class="text-sm text-yellow-700 font-medium">本周期还未打卡</span>
            </div>
          </div>
          <div v-else class="mb-3 p-3 bg-green-50 border border-green-200 rounded-lg">
            <div class="flex items-center space-x-2">
              <i class="fas fa-check-circle text-green-600"></i>
              <span class="text-sm text-green-700 font-medium">本周期已打卡</span>
            </div>
          </div>
          
          <div class="space-y-3">
            <!-- 最长连续打卡 -->
            <div class="flex items-center justify-between bg-white/70 rounded-lg p-3">
              <div class="flex items-center space-x-2">
                <i class="fas fa-fire text-orange-500"></i>
                <span class="text-sm text-gray-600">最长连续</span>
              </div>
              <span class="text-lg font-bold text-orange-600">{{ getMaxStreak(item.id!) }} 次</span>
            </div>
            
            <!-- 累计打卡 -->
            <div class="flex items-center justify-between bg-white/70 rounded-lg p-3">
              <div class="flex items-center space-x-2">
                <i class="fas fa-chart-line text-green-500"></i>
                <span class="text-sm text-gray-600">累计打卡</span>
              </div>
              <span class="text-lg font-bold text-green-600">{{ getTotalCheckins(item.id!) }} 次</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 素材分类仪表盘 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="mb-6">
        <h3 class="text-xl font-semibold text-gray-900 flex items-center">
          <i class="fa-solid fa-chart-pie text-primary mr-3"></i>
          素材组
        </h3>
        <p class="text-gray-600 text-sm mt-1">知识积累与整理</p>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- 碎片笔记仪表盘 -->
        <div class="bg-gradient-to-br from-indigo-50 to-indigo-100 rounded-lg p-5 hover:shadow-lg transition-shadow cursor-pointer border border-indigo-200" @click="$router.push('/fragment-notes')">
          <div class="flex items-center justify-between mb-3">
            <h4 class="text-lg font-semibold text-gray-900">碎片笔记</h4>
            <span class="bg-indigo-200 text-indigo-700 px-2 py-1 rounded-full text-xs font-medium">灵感</span>
          </div>
          <p class="text-gray-600 mb-3 text-sm">灵感碎片、想法记录、临时笔记</p>
          <div class="flex justify-between items-center mb-3">
            <div>
              <p class="text-gray-500 text-sm">素材数量</p>
              <p class="text-xl font-bold text-gray-900">{{ noteStats.fragment }} 条</p>
            </div>
            <div class="text-indigo-500">
              <i class="fas fa-puzzle-piece text-2xl"></i>
            </div>
          </div>
          <div class="flex items-center text-sm text-gray-500">
            <i class="fas fa-clock mr-1"></i>
            <span>最近更新: {{ getLatestUpdate('fragment') }}</span>
          </div>
        </div>

        <!-- 框架笔记仪表盘 -->
        <div class="bg-gradient-to-br from-purple-50 to-purple-100 rounded-lg p-5 hover:shadow-lg transition-shadow cursor-pointer border border-purple-200" @click="$router.push('/framework-notes')">
          <div class="flex items-center justify-between mb-3">
            <h4 class="text-lg font-semibold text-gray-900">框架笔记</h4>
            <span class="bg-purple-200 text-purple-700 px-2 py-1 rounded-full text-xs font-medium">框架</span>
          </div>
          <p class="text-gray-600 mb-3 text-sm">知识框架、体系结构、思维导图</p>
          <div class="flex justify-between items-center mb-3">
            <div>
              <p class="text-gray-500 text-sm">素材数量</p>
              <p class="text-xl font-bold text-gray-900">{{ noteStats.framework }} 条</p>
            </div>
            <div class="text-purple-500">
              <i class="fas fa-sitemap text-2xl"></i>
            </div>
          </div>
          <div class="flex items-center text-sm text-gray-500">
            <i class="fas fa-clock mr-1"></i>
            <span>最近更新: {{ getLatestUpdate('framework') }}</span>
          </div>
        </div>

        <!-- 求学笔记仪表盘 -->
        <div class="bg-gradient-to-br from-blue-50 to-blue-100 rounded-lg p-5 hover:shadow-lg transition-shadow cursor-pointer border border-blue-200" @click="$router.push('/study-notes')">
          <div class="flex items-center justify-between mb-3">
            <h4 class="text-lg font-semibold text-gray-900">求学笔记</h4>
            <span class="bg-blue-200 text-blue-700 px-2 py-1 rounded-full text-xs font-medium">学习</span>
          </div>
          <p class="text-gray-600 mb-3 text-sm">课程学习、书籍阅读、知识深化</p>
          <div class="flex justify-between items-center mb-3">
            <div>
              <p class="text-gray-500 text-sm">素材数量</p>
              <p class="text-xl font-bold text-gray-900">{{ noteStats.study }} 条</p>
            </div>
            <div class="text-blue-500">
              <i class="fas fa-graduation-cap text-2xl"></i>
            </div>
          </div>
          <div class="flex items-center text-sm text-gray-500">
            <i class="fas fa-clock mr-1"></i>
            <span>最近更新: {{ getLatestUpdate('study') }}</span>
          </div>
        </div>

        <!-- 背诵笔记仪表盘 -->
        <div class="bg-gradient-to-br from-green-50 to-green-100 rounded-lg p-5 hover:shadow-lg transition-shadow cursor-pointer border border-green-200" @click="$router.push('/memorization-notes')">
          <div class="flex items-center justify-between mb-3">
            <h4 class="text-lg font-semibold text-gray-900">背诵笔记</h4>
            <span class="bg-green-200 text-green-700 px-2 py-1 rounded-full text-xs font-medium">记忆</span>
          </div>
          <p class="text-gray-600 mb-3 text-sm">重点内容、公式记忆、知识点背诵</p>
          <div class="flex justify-between items-center mb-3">
            <div>
              <p class="text-gray-500 text-sm">素材数量</p>
              <p class="text-xl font-bold text-gray-900">{{ noteStats.memorization }} 条</p>
            </div>
            <div class="text-green-500">
              <i class="fas fa-lightbulb text-2xl"></i>
            </div>
          </div>
          <div class="flex items-center text-sm text-gray-500">
            <i class="fas fa-clock mr-1"></i>
            <span>最近更新: {{ getLatestUpdate('memorization') }}</span>
          </div>
        </div>

        <!-- 刷题笔记仪表盘 -->
        <div class="bg-gradient-to-br from-orange-50 to-orange-100 rounded-lg p-5 hover:shadow-lg transition-shadow cursor-pointer border border-orange-200" @click="$router.push('/exercise-notes')">
          <div class="flex items-center justify-between mb-3">
            <h4 class="text-lg font-semibold text-gray-900">刷题笔记</h4>
            <span class="bg-orange-200 text-orange-700 px-2 py-1 rounded-full text-xs font-medium">练习</span>
          </div>
          <p class="text-gray-600 mb-3 text-sm">题目练习、解题思路、错题总结</p>
          <div class="flex justify-between items-center mb-3">
            <div>
              <p class="text-gray-500 text-sm">素材数量</p>
              <p class="text-xl font-bold text-gray-900">{{ noteStats.exercise }} 条</p>
            </div>
            <div class="text-orange-500">
              <i class="fas fa-pen-to-square text-2xl"></i>
            </div>
          </div>
          <div class="flex items-center text-sm text-gray-500">
            <i class="fas fa-clock mr-1"></i>
            <span>最近更新: {{ getLatestUpdate('exercise') }}</span>
          </div>
        </div>

        <!-- 实战笔记仪表盘 -->
        <div class="bg-gradient-to-br from-red-50 to-red-100 rounded-lg p-5 hover:shadow-lg transition-shadow cursor-pointer border border-red-200" @click="$router.push('/practical-notes')">
          <div class="flex items-center justify-between mb-3">
            <h4 class="text-lg font-semibold text-gray-900">实战笔记</h4>
            <span class="bg-red-200 text-red-700 px-2 py-1 rounded-full text-xs font-medium">实践</span>
          </div>
          <p class="text-gray-600 mb-3 text-sm">项目实践、工作经验、实战总结</p>
          <div class="flex justify-between items-center mb-3">
            <div>
              <p class="text-gray-500 text-sm">素材数量</p>
              <p class="text-xl font-bold text-gray-900">{{ noteStats.practical }} 条</p>
            </div>
            <div class="text-red-500">
              <i class="fas fa-flask text-2xl"></i>
            </div>
          </div>
          <div class="flex items-center text-sm text-gray-500">
            <i class="fas fa-clock mr-1"></i>
            <span>最近更新: {{ getLatestUpdate('practical') }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 行动组 -->
    <div class="bg-gradient-to-br from-white to-gray-50 rounded-2xl shadow-lg p-8 mb-8 border border-gray-100">
      <div class="mb-8">
        <h3 class="text-2xl font-bold text-gray-900 flex items-center">
          <i class="fa-solid fa-bolt text-gray-900 text-2xl mr-3"></i>
          行动组
        </h3>
        <p class="text-gray-500 text-sm mt-2">项目执行与任务管理</p>
          </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <!-- 战争行动 -->
        <div class="bg-white rounded-2xl p-6 border-2 border-red-200 hover:border-red-300 hover:shadow-2xl transition-all duration-300 group">
          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center">
              <div class="w-12 h-12 bg-gradient-to-br from-red-500 to-red-600 rounded-xl flex items-center justify-center mr-3 shadow-lg group-hover:scale-110 transition-transform">
                <i class="fa-solid fa-crosshairs text-white text-xl"></i>
              </div>
              <div>
                <h4 class="text-xl font-bold text-gray-900">战争行动</h4>
                <p class="text-xs text-red-600 font-medium">实战任务管理</p>
              </div>
            </div>
            <router-link to="/actions/defense-projects" class="bg-gradient-to-r from-red-600 to-red-700 text-white px-4 py-2 rounded-xl text-sm font-semibold hover:from-red-700 hover:to-red-800 transition-all shadow-md hover:shadow-lg">
              实战任务
            </router-link>
          </div>
          <p class="text-sm text-gray-500 mb-5 leading-relaxed">日常工作执行、考试备战、副业发展等核心任务</p>

          <!-- 项目和任务统计 -->
          <div class="grid grid-cols-2 gap-4 mb-5">
            <div class="bg-gradient-to-br from-red-50 to-red-100 rounded-xl p-4 border border-red-200">
              <div class="flex items-center mb-3">
                <div class="w-8 h-8 bg-red-500 rounded-lg flex items-center justify-center mr-2 shadow-sm">
                  <i class="fa-solid fa-folder text-white text-sm"></i>
              </div>
                <span class="text-xs font-bold text-gray-700 uppercase tracking-wide">项目总数</span>
              </div>
              <div class="text-4xl font-black text-red-600 mb-3">{{ statsByCategory.defense.projects.total }}<span class="text-lg font-medium text-gray-400 ml-1">个</span></div>
              <div class="space-y-1">
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已完成</span>
                  <span class="font-bold text-green-600">{{ statsByCategory.defense.projects.completed }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">进行中</span>
                  <span class="font-bold text-blue-600">{{ statsByCategory.defense.projects.active }}</span>
            </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">规划中</span>
                  <span class="font-bold text-yellow-600">{{ statsByCategory.defense.projects.planning }}</span>
              </div>
              </div>
            </div>
            <div class="bg-gradient-to-br from-red-50 to-red-100 rounded-xl p-4 border border-red-200">
              <div class="flex items-center mb-3">
                <div class="w-8 h-8 bg-red-500 rounded-lg flex items-center justify-center mr-2 shadow-sm">
                  <i class="fa-solid fa-list-check text-white text-sm"></i>
              </div>
                <span class="text-xs font-bold text-gray-700 uppercase tracking-wide">任务总数</span>
              </div>
              <div class="text-4xl font-black text-red-600 mb-3">{{ statsByCategory.defense.tasks.total }}<span class="text-lg font-medium text-gray-400 ml-1">个</span></div>
              <div class="space-y-1">
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已完成</span>
                  <span class="font-bold text-green-600">{{ statsByCategory.defense.tasks.completed }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">进行中</span>
                  <span class="font-bold text-blue-600">{{ statsByCategory.defense.tasks.inProgress }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">待开始</span>
                  <span class="font-bold text-yellow-600">{{ statsByCategory.defense.tasks.pending }}</span>
            </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已停止</span>
                  <span class="font-bold text-gray-500">{{ statsByCategory.defense.tasks.stopped }}</span>
              </div>
              </div>
            </div>
          </div>
          
          <!-- 紧急任务提醒 -->
          <div class="mb-5">
            <div 
              @click="toggleCategory('defense')" 
              class="flex items-center justify-between p-4 bg-gradient-to-r from-orange-50 to-red-50 rounded-xl border-2 border-orange-200 cursor-pointer hover:border-orange-300 hover:shadow-md transition-all group"
            >
            <div class="flex items-center space-x-3">
                <div class="w-8 h-8 bg-gradient-to-br from-orange-500 to-red-500 rounded-lg flex items-center justify-center shadow-md group-hover:scale-110 transition-transform">
                  <i class="fas fa-exclamation-triangle text-white text-sm"></i>
              </div>
              <div>
                  <span class="text-sm font-bold text-gray-900">紧急任务</span>
                  <p class="text-xs text-gray-500">点击查看详情</p>
              </div>
            </div>
              <div class="flex items-center space-x-3">
                <span :class="urgentTasksByCategory.defense.count > 0 ? 'bg-red-600 text-white' : 'bg-gray-200 text-gray-600'" class="px-3 py-1.5 rounded-full text-xs font-bold shadow-sm">
                  {{ urgentTasksByCategory.defense.count > 0 ? `${urgentTasksByCategory.defense.count} 个` : '无' }}
                </span>
                <i :class="`fas fa-chevron-${expandedCategories.defense ? 'up' : 'down'} text-gray-500 text-sm group-hover:text-gray-700 transition-colors`"></i>
          </div>
              </div>
            <transition name="slide-fade">
              <div v-show="expandedCategories.defense" class="mt-3 space-y-2">
                <div v-if="urgentTasksByCategory.defense.count === 0" class="text-center py-4 bg-gradient-to-r from-green-50 to-emerald-50 rounded-xl border border-green-200">
                <div class="flex items-center justify-center space-x-2">
                  <i class="fas fa-check-circle text-green-500 text-lg"></i>
                  <p class="text-sm font-semibold text-green-700">当前无紧急任务</p>
              </div>
                </div>
              <div 
                v-else
                v-for="task in urgentTasksByCategory.defense.tasks" 
                :key="task.id"
                class="bg-white rounded-xl p-4 border-l-4 shadow-sm hover:shadow-md transition-shadow"
                :class="{
                  'border-red-600 bg-gradient-to-r from-red-50 to-red-100': task.isOverdue,
                  'border-red-400 bg-red-50': !task.isOverdue && task.daysLeft <= 2,
                  'border-orange-400 bg-orange-50': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
                }"
              >
                <div class="flex items-start justify-between">
                  <div class="flex-1">
                    <div class="flex items-center space-x-2 mb-1">
                      <h5 class="text-xs font-semibold text-gray-900">{{ task.name }}</h5>
                      <span 
                        class="px-2 py-0.5 rounded-full text-xs font-medium flex items-center space-x-1"
                        :class="{
                          'bg-red-600 text-white': task.isOverdue,
                          'bg-red-100 text-red-700': !task.isOverdue && task.daysLeft <= 2,
                          'bg-orange-100 text-orange-700': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
                        }"
                      >
                        <i v-if="task.isOverdue" class="fas fa-fire text-xs"></i>
                        <span class="text-xs">{{ task.isOverdue ? `逾期${Math.abs(task.daysLeft)}天` : task.daysLeft === 0 ? '今天' : task.daysLeft === 1 ? '明天' : `${task.daysLeft}天` }}</span>
                      </span>
                </div>
                    <p class="text-xs text-gray-600 mb-1 line-clamp-1">{{ task.description }}</p>
                    <div class="flex items-center space-x-2 text-xs text-gray-500">
                      <span><i class="far fa-calendar mr-1"></i>{{ task.deadline }}</span>
                </div>
              </div>
                  <button 
                    @click="goToProjectRecord(task)"
                    :class="{
                      'bg-red-600 hover:bg-red-700': task.isOverdue,
                      'bg-blue-600 hover:bg-blue-700': !task.isOverdue
                    }"
                    class="ml-2 px-2 py-1 text-white text-xs rounded transition-colors whitespace-nowrap"
                  >
                    处理
                  </button>
            </div>
              </div>
              </div>
            </transition>
          </div>
          
          <!-- 最近更新 -->
          <div class="flex items-center justify-between pt-4 mt-5 border-t-2 border-dashed border-red-200">
            <div class="flex items-center space-x-2">
              <div class="w-6 h-6 bg-red-100 rounded-lg flex items-center justify-center">
                <i class="far fa-clock text-red-600 text-xs"></i>
            </div>
              <div>
                <p class="text-xs text-gray-500">最近更新</p>
                <p class="text-xs font-bold text-gray-700">{{ getLastUpdateTime('defense') }}</p>
        </div>
            </div>
            <router-link to="/actions/defense-projects" class="text-xs font-semibold text-red-600 hover:text-red-700 flex items-center space-x-1 bg-red-50 px-3 py-1.5 rounded-lg hover:bg-red-100 transition-colors">
              <span>查看详情</span>
              <i class="fas fa-arrow-right text-xs"></i>
            </router-link>
          </div>
        </div>

        <!-- 工程建设 -->
        <div class="bg-white rounded-2xl p-6 border-2 border-blue-200 hover:border-blue-300 hover:shadow-2xl transition-all duration-300 group">
          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center">
              <div class="w-12 h-12 bg-gradient-to-br from-blue-500 to-blue-600 rounded-xl flex items-center justify-center mr-3 shadow-lg group-hover:scale-110 transition-transform">
                <i class="fa-solid fa-hammer text-white text-xl"></i>
              </div>
              <div>
                <h4 class="text-xl font-bold text-gray-900">工程建设</h4>
                <p class="text-xs text-blue-600 font-medium">模拟实战管理</p>
              </div>
            </div>
            <router-link to="/actions/construction-projects" class="bg-gradient-to-r from-blue-600 to-blue-700 text-white px-4 py-2 rounded-xl text-sm font-semibold hover:from-blue-700 hover:to-blue-800 transition-all shadow-md hover:shadow-lg">
              模拟训练
            </router-link>
          </div>
          <p class="text-sm text-gray-500 mb-5 leading-relaxed">技术项目实践、模拟业务场景、技能训练项目</p>

          <!-- 项目和任务统计 -->
          <div class="grid grid-cols-2 gap-4 mb-5">
            <div class="bg-gradient-to-br from-blue-50 to-blue-100 rounded-xl p-4 border border-blue-200">
              <div class="flex items-center mb-3">
                <div class="w-8 h-8 bg-blue-500 rounded-lg flex items-center justify-center mr-2 shadow-sm">
                  <i class="fa-solid fa-folder text-white text-sm"></i>
              </div>
                <span class="text-xs font-bold text-gray-700 uppercase tracking-wide">项目总数</span>
              </div>
              <div class="text-4xl font-black text-blue-600 mb-3">{{ statsByCategory.construction.projects.total }}<span class="text-lg font-medium text-gray-400 ml-1">个</span></div>
              <div class="space-y-1">
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已完成</span>
                  <span class="font-bold text-green-600">{{ statsByCategory.construction.projects.completed }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">进行中</span>
                  <span class="font-bold text-blue-600">{{ statsByCategory.construction.projects.active }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">规划中</span>
                  <span class="font-bold text-yellow-600">{{ statsByCategory.construction.projects.planning }}</span>
                </div>
              </div>
            </div>
            <div class="bg-gradient-to-br from-blue-50 to-blue-100 rounded-xl p-4 border border-blue-200">
              <div class="flex items-center mb-3">
                <div class="w-8 h-8 bg-blue-500 rounded-lg flex items-center justify-center mr-2 shadow-sm">
                  <i class="fa-solid fa-list-check text-white text-sm"></i>
              </div>
                <span class="text-xs font-bold text-gray-700 uppercase tracking-wide">任务总数</span>
              </div>
              <div class="text-4xl font-black text-blue-600 mb-3">{{ statsByCategory.construction.tasks.total }}<span class="text-lg font-medium text-gray-400 ml-1">个</span></div>
              <div class="space-y-1">
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已完成</span>
                  <span class="font-bold text-green-600">{{ statsByCategory.construction.tasks.completed }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">进行中</span>
                  <span class="font-bold text-blue-600">{{ statsByCategory.construction.tasks.inProgress }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">待开始</span>
                  <span class="font-bold text-yellow-600">{{ statsByCategory.construction.tasks.pending }}</span>
                </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已停止</span>
                  <span class="font-bold text-gray-500">{{ statsByCategory.construction.tasks.stopped }}</span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 紧急任务提醒 -->
          <div class="mb-5">
            <div 
              @click="toggleCategory('construction')" 
              class="flex items-center justify-between p-4 bg-gradient-to-r from-orange-50 to-red-50 rounded-xl border-2 border-orange-200 cursor-pointer hover:border-orange-300 hover:shadow-md transition-all group"
            >
              <div class="flex items-center space-x-3">
                <div class="w-8 h-8 bg-gradient-to-br from-orange-500 to-red-500 rounded-lg flex items-center justify-center shadow-md group-hover:scale-110 transition-transform">
                  <i class="fas fa-exclamation-triangle text-white text-sm"></i>
            </div>
                <div>
                  <span class="text-sm font-bold text-gray-900">紧急任务</span>
                  <p class="text-xs text-gray-500">点击查看详情</p>
            </div>
          </div>
              <div class="flex items-center space-x-3">
                <span :class="urgentTasksByCategory.construction.count > 0 ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-600'" class="px-3 py-1.5 rounded-full text-xs font-bold shadow-sm">
                  {{ urgentTasksByCategory.construction.count > 0 ? `${urgentTasksByCategory.construction.count} 个` : '无' }}
                </span>
                <i :class="`fas fa-chevron-${expandedCategories.construction ? 'up' : 'down'} text-gray-500 text-sm group-hover:text-gray-700 transition-colors`"></i>
        </div>
      </div>
            <transition name="slide-fade">
              <div v-show="expandedCategories.construction" class="mt-3 space-y-2">
                <div v-if="urgentTasksByCategory.construction.count === 0" class="text-center py-4 bg-gradient-to-r from-green-50 to-emerald-50 rounded-xl border border-green-200">
                <div class="flex items-center justify-center space-x-2">
                  <i class="fas fa-check-circle text-green-500 text-lg"></i>
                  <p class="text-sm font-semibold text-green-700">当前无紧急任务</p>
    </div>
        </div>
              <div 
                v-else
                v-for="task in urgentTasksByCategory.construction.tasks" 
                :key="task.id"
                class="bg-white rounded-xl p-4 border-l-4 shadow-sm hover:shadow-md transition-shadow"
                :class="{
                  'border-red-600 bg-gradient-to-r from-red-50 to-red-100': task.isOverdue,
                  'border-red-400 bg-red-50': !task.isOverdue && task.daysLeft <= 2,
                  'border-orange-400 bg-orange-50': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
                }"
              >
                <div class="flex items-start justify-between">
                  <div class="flex-1">
                    <div class="flex items-center space-x-2 mb-1">
                      <h5 class="text-xs font-semibold text-gray-900">{{ task.name }}</h5>
                      <span 
                        class="px-2 py-0.5 rounded-full text-xs font-medium flex items-center space-x-1"
                        :class="{
                          'bg-red-600 text-white': task.isOverdue,
                          'bg-red-100 text-red-700': !task.isOverdue && task.daysLeft <= 2,
                          'bg-orange-100 text-orange-700': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
                        }"
                      >
                        <i v-if="task.isOverdue" class="fas fa-fire text-xs"></i>
                        <span class="text-xs">{{ task.isOverdue ? `逾期${Math.abs(task.daysLeft)}天` : task.daysLeft === 0 ? '今天' : task.daysLeft === 1 ? '明天' : `${task.daysLeft}天` }}</span>
                      </span>
        </div>
                    <p class="text-xs text-gray-600 mb-1 line-clamp-1">{{ task.description }}</p>
                    <div class="flex items-center space-x-2 text-xs text-gray-500">
                      <span><i class="far fa-calendar mr-1"></i>{{ task.deadline }}</span>
      </div>
        </div>
                  <button 
                    @click="goToProjectRecord(task)"
                    :class="{
                      'bg-red-600 hover:bg-red-700': task.isOverdue,
                      'bg-blue-600 hover:bg-blue-700': !task.isOverdue
                    }"
                    class="ml-2 px-2 py-1 text-white text-xs rounded transition-colors whitespace-nowrap"
                  >
                    处理
                  </button>
        </div>
      </div>
              </div>
            </transition>
    </div>

          <!-- 最近更新 -->
          <div class="flex items-center justify-between pt-4 mt-5 border-t-2 border-dashed border-blue-200">
            <div class="flex items-center space-x-2">
              <div class="w-6 h-6 bg-blue-100 rounded-lg flex items-center justify-center">
                <i class="far fa-clock text-blue-600 text-xs"></i>
      </div>
              <div>
                <p class="text-xs text-gray-500">最近更新</p>
                <p class="text-xs font-bold text-gray-700">{{ getLastUpdateTime('construction') }}</p>
          </div>
            </div>
            <router-link to="/actions/construction-projects" class="text-xs font-semibold text-blue-600 hover:text-blue-700 flex items-center space-x-1 bg-blue-50 px-3 py-1.5 rounded-lg hover:bg-blue-100 transition-colors">
              <span>查看详情</span>
              <i class="fas fa-arrow-right text-xs"></i>
            </router-link>
          </div>
          </div>

        <!-- 外交行动 -->
        <div class="bg-white rounded-2xl p-6 border-2 border-green-200 hover:border-green-300 hover:shadow-2xl transition-all duration-300 group">
          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center">
              <div class="w-12 h-12 bg-gradient-to-br from-green-500 to-green-600 rounded-xl flex items-center justify-center mr-3 shadow-lg group-hover:scale-110 transition-transform">
                <i class="fa-solid fa-handshake text-white text-xl"></i>
        </div>
              <div>
                <h4 class="text-xl font-bold text-gray-900">外交行动</h4>
                <p class="text-xs text-green-600 font-medium">对外推广管理</p>
          </div>
            </div>
            <router-link to="/actions/diplomatic-projects" class="bg-gradient-to-r from-green-600 to-green-700 text-white px-4 py-2 rounded-xl text-sm font-semibold hover:from-green-700 hover:to-green-800 transition-all shadow-md hover:shadow-lg">
              对外推广
            </router-link>
          </div>
          <p class="text-sm text-gray-500 mb-5 leading-relaxed">已有成果对外分享、技术交流、利益互动</p>

          <!-- 项目和任务统计 -->
          <div class="grid grid-cols-2 gap-4 mb-5">
            <div class="bg-gradient-to-br from-green-50 to-green-100 rounded-xl p-4 border border-green-200">
              <div class="flex items-center mb-3">
                <div class="w-8 h-8 bg-green-500 rounded-lg flex items-center justify-center mr-2 shadow-sm">
                  <i class="fa-solid fa-folder text-white text-sm"></i>
          </div>
                <span class="text-xs font-bold text-gray-700 uppercase tracking-wide">项目总数</span>
        </div>
              <div class="text-4xl font-black text-green-600 mb-3">{{ statsByCategory.diplomatic.projects.total }}<span class="text-lg font-medium text-gray-400 ml-1">个</span></div>
              <div class="space-y-1">
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已完成</span>
                  <span class="font-bold text-green-600">{{ statsByCategory.diplomatic.projects.completed }}</span>
          </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">进行中</span>
                  <span class="font-bold text-blue-600">{{ statsByCategory.diplomatic.projects.active }}</span>
            </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">规划中</span>
                  <span class="font-bold text-yellow-600">{{ statsByCategory.diplomatic.projects.planning }}</span>
          </div>
          </div>
        </div>
            <div class="bg-gradient-to-br from-green-50 to-green-100 rounded-xl p-4 border border-green-200">
              <div class="flex items-center mb-3">
                <div class="w-8 h-8 bg-green-500 rounded-lg flex items-center justify-center mr-2 shadow-sm">
                  <i class="fa-solid fa-list-check text-white text-sm"></i>
          </div>
                <span class="text-xs font-bold text-gray-700 uppercase tracking-wide">任务总数</span>
            </div>
              <div class="text-4xl font-black text-green-600 mb-3">{{ statsByCategory.diplomatic.tasks.total }}<span class="text-lg font-medium text-gray-400 ml-1">个</span></div>
              <div class="space-y-1">
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已完成</span>
                  <span class="font-bold text-green-600">{{ statsByCategory.diplomatic.tasks.completed }}</span>
          </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">进行中</span>
                  <span class="font-bold text-blue-600">{{ statsByCategory.diplomatic.tasks.inProgress }}</span>
          </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">待开始</span>
                  <span class="font-bold text-yellow-600">{{ statsByCategory.diplomatic.tasks.pending }}</span>
        </div>
                <div class="flex items-center justify-between text-xs">
                  <span class="text-gray-600">已停止</span>
                  <span class="font-bold text-gray-500">{{ statsByCategory.diplomatic.tasks.stopped }}</span>
          </div>
            </div>
          </div>
          </div>

          <!-- 紧急任务提醒 -->
          <div class="mb-5">
            <div 
              @click="toggleCategory('diplomatic')" 
              class="flex items-center justify-between p-4 bg-gradient-to-r from-orange-50 to-red-50 rounded-xl border-2 border-orange-200 cursor-pointer hover:border-orange-300 hover:shadow-md transition-all group"
            >
              <div class="flex items-center space-x-3">
                <div class="w-8 h-8 bg-gradient-to-br from-orange-500 to-red-500 rounded-lg flex items-center justify-center shadow-md group-hover:scale-110 transition-transform">
                  <i class="fas fa-exclamation-triangle text-white text-sm"></i>
        </div>
                <div>
                  <span class="text-sm font-bold text-gray-900">紧急任务</span>
                  <p class="text-xs text-gray-500">点击查看详情</p>
          </div>
            </div>
              <div class="flex items-center space-x-3">
                <span :class="urgentTasksByCategory.diplomatic.count > 0 ? 'bg-green-600 text-white' : 'bg-gray-200 text-gray-600'" class="px-3 py-1.5 rounded-full text-xs font-bold shadow-sm">
                  {{ urgentTasksByCategory.diplomatic.count > 0 ? `${urgentTasksByCategory.diplomatic.count} 个` : '无' }}
                </span>
                <i :class="`fas fa-chevron-${expandedCategories.diplomatic ? 'up' : 'down'} text-gray-500 text-sm group-hover:text-gray-700 transition-colors`"></i>
          </div>
          </div>
            <transition name="slide-fade">
              <div v-show="expandedCategories.diplomatic" class="mt-3 space-y-2">
                <div v-if="urgentTasksByCategory.diplomatic.count === 0" class="text-center py-4 bg-gradient-to-r from-green-50 to-emerald-50 rounded-xl border border-green-200">
                <div class="flex items-center justify-center space-x-2">
                  <i class="fas fa-check-circle text-green-500 text-lg"></i>
                  <p class="text-sm font-semibold text-green-700">当前无紧急任务</p>
        </div>
          </div>
              <div 
                v-else
                v-for="task in urgentTasksByCategory.diplomatic.tasks" 
                :key="task.id"
                class="bg-white rounded-xl p-4 border-l-4 shadow-sm hover:shadow-md transition-shadow"
                :class="{
                  'border-red-600 bg-gradient-to-r from-red-50 to-red-100': task.isOverdue,
                  'border-red-400 bg-red-50': !task.isOverdue && task.daysLeft <= 2,
                  'border-orange-400 bg-orange-50': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
                }"
              >
                <div class="flex items-start justify-between">
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
                      <h5 class="text-xs font-semibold text-gray-900">{{ task.name }}</h5>
                      <span 
                        class="px-2 py-0.5 rounded-full text-xs font-medium flex items-center space-x-1"
                        :class="{
                          'bg-red-600 text-white': task.isOverdue,
                          'bg-red-100 text-red-700': !task.isOverdue && task.daysLeft <= 2,
                          'bg-orange-100 text-orange-700': !task.isOverdue && task.daysLeft > 2 && task.daysLeft <= 5
                        }"
                      >
                        <i v-if="task.isOverdue" class="fas fa-fire text-xs"></i>
                        <span class="text-xs">{{ task.isOverdue ? `逾期${Math.abs(task.daysLeft)}天` : task.daysLeft === 0 ? '今天' : task.daysLeft === 1 ? '明天' : `${task.daysLeft}天` }}</span>
                      </span>
            </div>
                    <p class="text-xs text-gray-600 mb-1 line-clamp-1">{{ task.description }}</p>
                    <div class="flex items-center space-x-2 text-xs text-gray-500">
                      <span><i class="far fa-calendar mr-1"></i>{{ task.deadline }}</span>
          </div>
          </div>
                  <button 
                    @click="goToProjectRecord(task)"
                    :class="{
                      'bg-red-600 hover:bg-red-700': task.isOverdue,
                      'bg-blue-600 hover:bg-blue-700': !task.isOverdue
                    }"
                    class="ml-2 px-2 py-1 text-white text-xs rounded transition-colors whitespace-nowrap"
                  >
                    处理
                  </button>
        </div>
          </div>
            </div>
            </transition>
          </div>

          <!-- 最近更新 -->
          <div class="flex items-center justify-between pt-4 mt-5 border-t-2 border-dashed border-green-200">
            <div class="flex items-center space-x-2">
              <div class="w-6 h-6 bg-green-100 rounded-lg flex items-center justify-center">
                <i class="far fa-clock text-green-600 text-xs"></i>
          </div>
              <div>
                <p class="text-xs text-gray-500">最近更新</p>
                <p class="text-xs font-bold text-gray-700">{{ getLastUpdateTime('diplomatic') }}</p>
        </div>
          </div>
            <router-link to="/actions/diplomatic-projects" class="text-xs font-semibold text-green-600 hover:text-green-700 flex items-center space-x-1 bg-green-50 px-3 py-1.5 rounded-lg hover:bg-green-100 transition-colors">
              <span>查看详情</span>
              <i class="fas fa-arrow-right text-xs"></i>
            </router-link>
            </div>
          </div>
          </div>
        </div>
    <!-- 统计图表 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
      <!-- 素材增长趋势 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-xl font-semibold text-gray-900 flex items-center">
            <i class="fa-solid fa-chart-line text-primary mr-3"></i>
            素材增长趋势
          </h3>
          </div>
        <div class="h-64">
          <canvas ref="materialsChartRef"></canvas>
            </div>
          </div>

      <!-- 行动组月度统计 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-xl font-semibold text-gray-900 flex items-center">
            <i class="fa-solid fa-briefcase text-secondary mr-3"></i>
            行动组月度统计
          </h3>
          </div>
        <div class="h-64">
          <canvas ref="actionsChartRef"></canvas>
        </div>
          </div>
            </div>

  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, onActivated, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import Chart from 'chart.js/auto'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/zh-cn'
// 使用API请求
import request from '@/api/request'
import { getCheckinItemList, getCheckinRecords, checkCheckinStatus, type CheckinItem, type CheckinRecord } from '@/api/checkin'

const router = useRouter()

dayjs.extend(relativeTime)
dayjs.locale('zh-cn')

const materialsChartRef = ref<HTMLCanvasElement>()
const actionsChartRef = ref<HTMLCanvasElement>()

// 响应式数据容器
const noteStats = ref<any>({ framework: 0, study: 0, memorization: 0, exercise: 0, practical: 0, fragment: 0 })
const allHonors = ref<any[]>([])
const allNotes = ref<any[]>([])
const allTasks = ref<any[]>([])
const allProjects = ref<any[]>([])
const loading = ref(true)

// 倒计时事件
const countdownEvents = ref<any[]>([])

// 当前时间戳（用于自动更新相对时间）
const currentTime = ref(Date.now())
let timeUpdateTimer: number | null = null

// 荣誉战绩折叠状态
const isDefenseHonorsCollapsed = ref(true)
const isConstructionHonorsCollapsed = ref(true)
const isDiplomaticHonorsCollapsed = ref(true)

// ========== 打卡记录相关 ==========
const checkinItems = ref<CheckinItem[]>([])
const checkinRecordsMap = ref<Record<number, CheckinRecord[]>>({})
const checkinStatusMap = ref<Record<number, boolean>>({})

// 只显示启用状态的打卡项目
const activeCheckinItems = computed(() => {
  return checkinItems.value.filter(item => item.status === 'active')
})

// 加载打卡项目
const loadCheckinItems = async () => {
  try {
    const response = await getCheckinItemList()
    if (response.code === 200) {
      checkinItems.value = response.data || []
      // 加载每个项目的打卡记录和状态
      for (const item of checkinItems.value) {
        if (item.id) {
          await loadCheckinRecords(item.id)
          await loadCheckinStatus(item.id, item.frequency)
        }
      }
    }
  } catch (error) {
    console.error('加载打卡项目失败:', error)
  }
}

// 加载打卡记录
const loadCheckinRecords = async (itemId: number) => {
  try {
    const response = await getCheckinRecords(itemId)
    if (response.code === 200) {
      checkinRecordsMap.value[itemId] = response.data || []
    }
  } catch (error) {
    console.error('加载打卡记录失败:', error)
  }
}

// 加载打卡状态
const loadCheckinStatus = async (itemId: number, frequency: string) => {
  try {
    const response = await checkCheckinStatus(itemId, frequency)
    if (response.code === 200 && response.data) {
      checkinStatusMap.value[itemId] = response.data.isCheckedIn || false
    }
  } catch (error) {
    console.error('加载打卡状态失败:', error)
    checkinStatusMap.value[itemId] = false
  }
}

// 获取累计打卡次数
const getTotalCheckins = (itemId: number): number => {
  return checkinRecordsMap.value[itemId]?.length || 0
}

// 获取最长连续打卡次数
const getMaxStreak = (itemId: number): number => {
  const records = checkinRecordsMap.value[itemId]
  if (!records || records.length === 0) return 0
  
  // 按时间排序
  const sortedRecords = [...records].sort((a, b) => {
    return new Date(a.checkinTime!).getTime() - new Date(b.checkinTime!).getTime()
  })
  
  let maxStreak = 1
  let currentStreak = 1
  
  for (let i = 1; i < sortedRecords.length; i++) {
    const prevCycleKey = sortedRecords[i - 1].cycleKey!
    const currCycleKey = sortedRecords[i].cycleKey!
    
    // 判断是否连续（根据周期标识判断）
    if (isConsecutiveCycle(prevCycleKey, currCycleKey)) {
      currentStreak++
      maxStreak = Math.max(maxStreak, currentStreak)
    } else {
      currentStreak = 1
    }
  }
  
  return maxStreak
}

// 判断两个周期是否连续
const isConsecutiveCycle = (prev: string, curr: string): boolean => {
  // 日：2025-10-11 -> 2025-10-12
  if (prev.match(/^\d{4}-\d{2}-\d{2}$/)) {
    const prevDate = new Date(prev)
    const currDate = new Date(curr)
    const diffDays = (currDate.getTime() - prevDate.getTime()) / (1000 * 60 * 60 * 24)
    return diffDays === 1
  }
  
  // 周：2025-W41 -> 2025-W42
  if (prev.match(/^\d{4}-W\d{2}$/)) {
    const prevWeek = parseInt(prev.split('-W')[1])
    const currWeek = parseInt(curr.split('-W')[1])
    const prevYear = parseInt(prev.split('-')[0])
    const currYear = parseInt(curr.split('-')[0])
    
    if (prevYear === currYear) {
      return currWeek === prevWeek + 1
    } else if (currYear === prevYear + 1) {
      return prevWeek >= 52 && currWeek === 1
    }
    return false
  }
  
  // 月：2025-10 -> 2025-11
  if (prev.match(/^\d{4}-\d{2}$/)) {
    const [prevYear, prevMonth] = prev.split('-').map(Number)
    const [currYear, currMonth] = curr.split('-').map(Number)
    
    if (prevYear === currYear) {
      return currMonth === prevMonth + 1
    } else if (currYear === prevYear + 1) {
      return prevMonth === 12 && currMonth === 1
    }
    return false
  }
  
  // 季：2025-Q4 -> 2026-Q1
  if (prev.match(/^\d{4}-Q\d$/)) {
    const prevQuarter = parseInt(prev.split('-Q')[1])
    const currQuarter = parseInt(curr.split('-Q')[1])
    const prevYear = parseInt(prev.split('-')[0])
    const currYear = parseInt(curr.split('-')[0])
    
    if (prevYear === currYear) {
      return currQuarter === prevQuarter + 1
    } else if (currYear === prevYear + 1) {
      return prevQuarter === 4 && currQuarter === 1
    }
    return false
  }
  
  // 年：2025 -> 2026
  if (prev.match(/^\d{4}$/)) {
    return parseInt(curr) === parseInt(prev) + 1
  }
  
  return false
}

// 获取频率标签
const getFrequencyLabel = (frequency: string): string => {
  const map: Record<string, string> = {
    'daily': '每日',
    'weekly': '每周',
    'monthly': '每月',
    'quarterly': '每季',
    'yearly': '每年'
  }
  return map[frequency] || frequency
}

// 获取频率徽章样式
const getFrequencyBadgeClass = (frequency: string): string => {
  const map: Record<string, string> = {
    'daily': 'bg-blue-200 text-blue-700',
    'weekly': 'bg-green-200 text-green-700',
    'monthly': 'bg-purple-200 text-purple-700',
    'quarterly': 'bg-orange-200 text-orange-700',
    'yearly': 'bg-red-200 text-red-700'
  }
  return map[frequency] || 'bg-gray-200 text-gray-700'
}

// 计算各类项目数量
const defenseProjects = computed(() => allProjects.value.filter(p => p.category === 'defense'))
const constructionProjects = computed(() => allProjects.value.filter(p => p.category === 'construction'))
const diplomaticProjects = computed(() => allProjects.value.filter(p => p.category === 'diplomatic'))

// 计算属性改为从ref中过滤
const defenseHonors = computed(() => 
  allHonors.value.filter(h => h.category === 'defense')
)
const constructionHonors = computed(() => 
  allHonors.value.filter(h => h.category === 'construction')
)
const diplomaticHonors = computed(() => 
  allHonors.value.filter(h => h.category === 'diplomatic')
)

// 显示的荣誉列表（折叠时只显示前3个）
const displayedDefenseHonors = computed(() => 
  isDefenseHonorsCollapsed.value && defenseHonors.value.length > 3 
    ? defenseHonors.value.slice(0, 3) 
    : defenseHonors.value
)
const displayedConstructionHonors = computed(() => 
  isConstructionHonorsCollapsed.value && constructionHonors.value.length > 3 
    ? constructionHonors.value.slice(0, 3) 
    : constructionHonors.value
)
const displayedDiplomaticHonors = computed(() => 
  isDiplomaticHonorsCollapsed.value && diplomaticHonors.value.length > 3 
    ? diplomaticHonors.value.slice(0, 3) 
    : diplomaticHonors.value
)

// 战争行动项目状态统计
const defenseProjectStats = computed(() => {
  const completed = defenseProjects.value.filter(p => p.status === 'completed').length
  const inProgress = defenseProjects.value.filter(p => p.status === 'inProgress').length
  const planning = defenseProjects.value.filter(p => p.status === 'planning').length
  return { completed, inProgress, planning }
})

// 战争行动任务统计
const defenseTaskStats = computed(() => {
  const defenseTasks = allTasks.value.filter(t => defenseProjects.value.some(p => p.id === t.projectId))
  const completed = defenseTasks.filter(t => t.status === 'completed').length
  const inProgress = defenseTasks.filter(t => t.status === 'inProgress').length
  const notStarted = defenseTasks.filter(t => t.status === 'notStarted').length
  const stopped = defenseTasks.filter(t => t.status === 'stopped').length
  return { total: defenseTasks.length, completed, inProgress, notStarted, stopped }
})

// 工程建设任务统计
const constructionTaskStats = computed(() => {
  const constructionTasks = allTasks.value.filter(t => constructionProjects.value.some(p => p.id === t.projectId))
  const completed = constructionTasks.filter(t => t.status === 'completed').length
  const inProgress = constructionTasks.filter(t => t.status === 'inProgress').length
  const notStarted = constructionTasks.filter(t => t.status === 'notStarted').length
  const stopped = constructionTasks.filter(t => t.status === 'stopped').length
  return { total: constructionTasks.length, completed, inProgress, notStarted, stopped }
})

// 外交行动任务统计
const diplomaticTaskStats = computed(() => {
  const diplomaticTasks = allTasks.value.filter(t => diplomaticProjects.value.some(p => p.id === t.projectId))
  const completed = diplomaticTasks.filter(t => t.status === 'completed').length
  const inProgress = diplomaticTasks.filter(t => t.status === 'inProgress').length
  const notStarted = diplomaticTasks.filter(t => t.status === 'notStarted').length
  const stopped = diplomaticTasks.filter(t => t.status === 'stopped').length
  return { total: diplomaticTasks.length, completed, inProgress, notStarted, stopped }
})

// 获取各项目类型最近更新时间
const getProjectLatestUpdate = (category: string) => {
  // 依赖currentTime.value以实现自动更新
  currentTime.value // 触发响应式更新
  
  const projects = allProjects.value.filter(p => p.category === category)
  if (!projects.length) return '暂无数据'
  
  const tasks = allTasks.value.filter(t => projects.some(p => p.id === t.projectId))
  const allTimes = [
    ...projects.map(p => p.recReviseTime || p.recCreateTime || ''),
    ...tasks.map(t => t.recReviseTime || t.recCreateTime || '')
  ].filter(t => t)
  
  if (!allTimes.length) return '暂无数据'
  
  const latest = allTimes.reduce((max, time) => time > max ? time : max, allTimes[0])
  return dayjs(latest).fromNow()
}

// 加载所有数据
const loadData = async () => {
  loading.value = true
  try {
    // 加载笔记统计
    const noteRes = await request.get('/note/list')
    if (noteRes.code === 200) {
      allNotes.value = noteRes.data || []
      // 计算笔记统计
      const stats: any = { framework: 0, study: 0, memorization: 0, exercise: 0, practical: 0, fragment: 0 }
      allNotes.value.forEach((note: any) => {
        if (stats.hasOwnProperty(note.type)) {
          stats[note.type]++
        }
      })
      noteStats.value = stats
    }

    // 加载任务
    const taskRes = await request.get('/task/list')
    if (taskRes.code === 200) {
      allTasks.value = taskRes.data || []
    }

    // 加载荣誉
    const honorRes = await request.get('/honor/list')
    if (honorRes.code === 200) {
      allHonors.value = honorRes.data || []
    }

    // 加载项目
    const projectRes = await request.get('/project/list')
    if (projectRes.code === 200) {
      allProjects.value = projectRes.data || []
    }

    // 加载打卡数据
    await loadCheckinItems()
  } catch (error) {
    console.error('加载数据失败:', error)
  } finally {
    loading.value = false
  }
}

// 刷新荣誉战绩数据（改为异步）
const refreshHonors = async () => {
  try {
    const honorRes = await request.get('/honor/list')
    if (honorRes.code === 200) {
      allHonors.value = honorRes.data || []
    }
  } catch (error) {
    console.error('刷新荣誉战绩失败:', error)
  }
}

// 折叠状态控制
const expandedCategories = ref<{ [key: string]: boolean }>({
  defense: false,
  construction: false,
  diplomatic: false
})

// 切换分类折叠状态
const toggleCategory = (category: string) => {
  expandedCategories.value[category] = !expandedCategories.value[category]
}

// 切换荣誉战绩折叠状态
const toggleDefenseHonors = () => {
  isDefenseHonorsCollapsed.value = !isDefenseHonorsCollapsed.value
}
const toggleConstructionHonors = () => {
  isConstructionHonorsCollapsed.value = !isConstructionHonorsCollapsed.value
}
const toggleDiplomaticHonors = () => {
  isDiplomaticHonorsCollapsed.value = !isDiplomaticHonorsCollapsed.value
}

// 计算紧急任务（已过期或5天内到期且未完成）
const urgentTasks = computed(() => {
  const now = new Date()
  const fiveDaysLater = new Date(now.getTime() + 5 * 24 * 60 * 60 * 1000)
  
  return allTasks.value
    .filter(task => {
      // 排除已完成和已停止的任务
      if (task.status === 'completed' || task.status === 'stopped') return false
      
      // 检查截止时间
      if (!task.deadline) return false
      
      const deadline = new Date(task.deadline)
      // 已过期或在5天内到期
      return deadline <= fiveDaysLater
    })
    .map(task => {
      const deadline = new Date(task.deadline)
      const timeDiff = deadline.getTime() - now.getTime()
      const daysLeft = Math.ceil(timeDiff / (1000 * 60 * 60 * 24))
      
      // 获取项目信息来确定分类
      const project = allProjects.value.find(p => p.id === task.projectId)
      
      return {
        ...task,
        daysLeft,
        isOverdue: deadline < now, // 标记是否已过期
        category: project?.category || 'unknown'
      }
    })
    .sort((a, b) => a.daysLeft - b.daysLeft) // 按剩余天数排序（过期的会排在最前面，因为是负数）
})

// 按分类分组紧急任务
const urgentTasksByCategory = computed(() => {
  const defense = urgentTasks.value.filter(t => t.category === 'defense')
  const construction = urgentTasks.value.filter(t => t.category === 'construction')
  const diplomatic = urgentTasks.value.filter(t => t.category === 'diplomatic')
  
  return {
    defense: {
      name: '战争行动',
      icon: 'fa-crosshairs',
      color: 'red',
      tasks: defense,
      count: defense.length
    },
    construction: {
      name: '工程建设',
      icon: 'fa-hammer',
      color: 'blue',
      tasks: construction,
      count: construction.length
    },
    diplomatic: {
      name: '外交行动',
      icon: 'fa-handshake',
      color: 'green',
      tasks: diplomatic,
      count: diplomatic.length
    }
  }
})

// 总紧急任务数
const totalUrgentTasks = computed(() => urgentTasks.value.length)

// 按分类统计项目和任务
const statsByCategory = computed(() => {
  // 战争行动统计
  const defenseProjects = allProjects.value.filter(p => p.category === 'defense')
  const defenseTasks = allTasks.value.filter(t => {
    const project = allProjects.value.find(p => p.id === t.projectId)
    return project?.category === 'defense'
  })
  
  // 工程建设统计
  const constructionProjects = allProjects.value.filter(p => p.category === 'construction')
  const constructionTasks = allTasks.value.filter(t => {
    const project = allProjects.value.find(p => p.id === t.projectId)
    return project?.category === 'construction'
  })
  
  // 外交行动统计
  const diplomaticProjects = allProjects.value.filter(p => p.category === 'diplomatic')
  const diplomaticTasks = allTasks.value.filter(t => {
    const project = allProjects.value.find(p => p.id === t.projectId)
    return project?.category === 'diplomatic'
  })
  
  return {
    defense: {
      projects: {
        total: defenseProjects.length,
        completed: defenseProjects.filter(p => p.status === 'completed').length,
        active: defenseProjects.filter(p => p.status === 'active').length,
        planning: defenseProjects.filter(p => p.status === 'planning').length
      },
      tasks: {
        total: defenseTasks.length,
        completed: defenseTasks.filter(t => t.status === 'completed').length,
        inProgress: defenseTasks.filter(t => t.status === 'in-progress').length,
        pending: defenseTasks.filter(t => t.status === 'pending').length,
        stopped: defenseTasks.filter(t => t.status === 'stopped').length
      }
    },
    construction: {
      projects: {
        total: constructionProjects.length,
        completed: constructionProjects.filter(p => p.status === 'completed').length,
        active: constructionProjects.filter(p => p.status === 'active').length,
        planning: constructionProjects.filter(p => p.status === 'planning').length
      },
      tasks: {
        total: constructionTasks.length,
        completed: constructionTasks.filter(t => t.status === 'completed').length,
        inProgress: constructionTasks.filter(t => t.status === 'in-progress').length,
        pending: constructionTasks.filter(t => t.status === 'pending').length,
        stopped: constructionTasks.filter(t => t.status === 'stopped').length
      }
    },
    diplomatic: {
      projects: {
        total: diplomaticProjects.length,
        completed: diplomaticProjects.filter(p => p.status === 'completed').length,
        active: diplomaticProjects.filter(p => p.status === 'active').length,
        planning: diplomaticProjects.filter(p => p.status === 'planning').length
      },
      tasks: {
        total: diplomaticTasks.length,
        completed: diplomaticTasks.filter(t => t.status === 'completed').length,
        inProgress: diplomaticTasks.filter(t => t.status === 'in-progress').length,
        pending: diplomaticTasks.filter(t => t.status === 'pending').length,
        stopped: diplomaticTasks.filter(t => t.status === 'stopped').length
      }
    }
  }
})

// 获取最近更新时间（通过项目和任务的最后更新时间）
const getLastUpdateTime = (category: string) => {
  // 依赖currentTime.value以实现自动更新
  currentTime.value // 触发响应式更新
  
  const projects = allProjects.value.filter(p => p.category === category)
  const tasks = allTasks.value.filter(t => {
    const project = allProjects.value.find(p => p.id === t.projectId)
    return project?.category === category
  })
  
  const allItems = [...projects, ...tasks]
  if (allItems.length === 0) return '暂无数据'
  
  const latest = allItems.reduce((max, item) => {
    const time = item.recReviseTime || item.recCreateTime || ''
    return time > max ? time : max
  }, allItems[0].recReviseTime || allItems[0].recCreateTime || '')
  
  return latest ? dayjs(latest).fromNow() : '暂无数据'
}

// 获取项目名称
const getProjectName = (projectId: number) => {
  const project = allProjects.value.find(p => p.id === projectId)
  return project ? project.name : '未知项目'
}

// 跳转到项目执行页面
const goToProjectRecord = (task: any) => {
  router.push('/daily/project-record')
}

// 格式化日期显示
const formatAchievedTime = (dateTimeStr: string) => {
  if (!dateTimeStr) return '-'
  // 如果已经是简化格式 (YYYY-MM)，直接返回
  if (dateTimeStr.length <= 7) return dateTimeStr
  // 否则提取年月部分
  const date = new Date(dateTimeStr.replace(' ', 'T'))
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  return `${year}.${month}`
}

// 删除荣誉战绩（改为异步）
const removeHonor = async (honorId: number) => {
  if (confirm('确定要删除这个荣誉战绩吗？')) {
    try {
      await request.delete(`/honor/${honorId}`)
      await refreshHonors()
      alert('荣誉战绩已删除！')
    } catch (error) {
      console.error('删除失败:', error)
      alert('删除失败！')
    }
  }
}

// 加载倒计时事件
const loadCountdownEvents = async () => {
  try {
    const response = await request.get('/calendar/list')
    if (response.code === 200) {
      const allEvents = response.data || []
      const today = new Date()
      today.setHours(0, 0, 0, 0)
      
      // 过滤：只显示启用了倒计时的事件，且过期不超过1天
      countdownEvents.value = allEvents
        .filter((event: any) => event.showCountdown) // 只显示启用倒计时的
        .map((event: any) => {
          const eventDate = new Date(event.eventDate)
          eventDate.setHours(0, 0, 0, 0)
          const daysUntil = Math.floor((eventDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24))
          return { ...event, daysUntil }
        })
        .filter((event: any) => event.daysUntil >= -1) // 过期超过1天的隐藏
        .sort((a: any, b: any) => a.daysUntil - b.daysUntil) // 按距离排序
    }
  } catch (error) {
    console.error('加载倒计时失败:', error)
  }
}

// 格式化事件日期
const formatEventDate = (dateStr: string) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日`
}

// 获取天数显示的边框颜色类
const getDaysUntilClass = (days: number) => {
  if (days < 0) return 'border-gray-400' // 已过期
  if (days === 0) return 'border-red-500' // 今天
  if (days <= 7) return 'border-orange-500' // 一周内
  if (days <= 30) return 'border-yellow-500' // 一月内
  return 'border-blue-500' // 更远的未来
}

// 获取天数显示的文字颜色类
const getDaysTextColor = (days: number) => {
  if (days < 0) return 'text-gray-600' // 已过期
  if (days === 0) return 'text-red-600' // 今天
  if (days <= 7) return 'text-orange-600' // 一周内
  if (days <= 30) return 'text-yellow-600' // 一月内
  return 'text-blue-600' // 更远的未来
}

// 获取事件表情
const getEventEmoji = (days: number) => {
  if (days < 0) return '⏰' // 已过期
  if (days === 0) return '🎯' // 今天
  if (days <= 3) return '🔥' // 紧急
  if (days <= 7) return '⚠️' // 一周内
  if (days <= 30) return '📅' // 一月内
  return '🗓️' // 更远的未来
}

// 动态获取各类笔记最近更新时间（改为异步）
const getLatestUpdate = (type: 'framework' | 'study' | 'memorization' | 'exercise' | 'practical' | 'fragment') => {
  // 依赖currentTime.value以实现自动更新
  currentTime.value // 触发响应式更新
  
  const notes = allNotes.value.filter(n => n.type === type)
  if (!notes.length) return '暂无数据'
  // 取最大 createTime 或 recCreateTime
  const latest = notes.reduce((max, n) => {
    const time = n.createTime || n.recCreateTime || ''
    return time > max ? time : max
  }, notes[0].createTime || notes[0].recCreateTime || '')
  return latest ? dayjs(latest).fromNow() : '暂无数据'
}

// 近6个月的月份标签
const getRecentMonths = (n = 6) => {
  const arr: string[] = []
  // 获取当前北京时间
  const now = new Date()
  const beijingTime = new Date(now.getTime() + 8 * 60 * 60 * 1000)
  // 往前推6个月作为基准
  const baseDate = new Date(beijingTime)
  baseDate.setMonth(baseDate.getMonth() - 5) // 往前推5个月，这样加上当前月就是6个月
  for (let i = n - 1; i >= 0; i--) {
    const d = new Date(baseDate.getFullYear(), baseDate.getMonth() + (n - 1 - i), 1)
    arr.push(`${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`)
  }
  return arr
}
const recentMonths = getRecentMonths(6)

// 所有笔记按月创建数量（使用异步加载的数据）
const notesByMonth = computed(() => {
  const map: Record<string, number> = {}
  recentMonths.forEach(m => (map[m] = 0))
  allNotes.value.forEach(note => {
    const d = new Date(note.createTime || note.recCreateTime || '')
    const key = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`
    if (map[key] !== undefined) map[key]++
  })
  return recentMonths.map(m => map[m])
})

// 所有任务按月完成数量（使用异步加载的数据）
const tasksByMonth = computed(() => {
  const map: Record<string, number> = {}
  recentMonths.forEach(m => (map[m] = 0))
  allTasks.value.forEach(task => {
    if (task.completedTime) {
      const d = new Date(task.completedTime)
      const key = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`
      if (map[key] !== undefined) map[key]++
        }
  })
  return recentMonths.map(m => map[m])
})

// loadData 函数已在上面定义

// 组件激活时刷新数据（当从其他页面切换回来时）
onActivated(() => {
  console.log('【总览面板】页面被激活，刷新数据...')
  // 刷新所有数据，确保任务状态等信息是最新的
  loadData()
  loadCountdownEvents()
})

onMounted(async () => {
  // 先加载数据
  await loadData()
  await loadCountdownEvents()
  
  // 启动定时器，每分钟更新一次当前时间，让相对时间自动刷新
  timeUpdateTimer = window.setInterval(() => {
    currentTime.value = Date.now()
  }, 60000) // 60秒更新一次
  
  // 然后初始化图表
  // 素材增长趋势图表（改为折线图）
  if (materialsChartRef.value) {
    const ctx = materialsChartRef.value.getContext('2d')
    if (ctx) {
      new Chart(ctx, {
        type: 'line',
        data: {
          labels: recentMonths,
          datasets: [{
            label: '笔记创建数',
            data: notesByMonth.value,
            borderColor: 'rgb(59, 130, 246)',
            backgroundColor: 'rgba(59, 130, 246, 0.1)',
                    tension: 0.4,
                    fill: true
          }]
        },
        options: {
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
    })
    }
}

  // 行动组月度统计图表（改为所有任务按月完成数量）
  if (actionsChartRef.value) {
    const ctx = actionsChartRef.value.getContext('2d')
    if (ctx) {
      new Chart(ctx, {
        type: 'line',
        data: {
          labels: recentMonths,
            datasets: [{
            label: '任务完成数',
            data: tasksByMonth.value,
            borderColor: 'rgb(34, 197, 94)',
            backgroundColor: 'rgba(34, 197, 94, 0.1)',
            tension: 0.4,
            fill: true
            }]
        },
        options: {
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
      })
    }
  }
})

// 组件卸载时清除定时器
onUnmounted(() => {
  if (timeUpdateTimer !== null) {
    clearInterval(timeUpdateTimer)
    timeUpdateTimer = null
  }
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

/* 折叠展开过渡动画 */
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.2s ease-in;
}

.slide-fade-enter-from {
  transform: translateY(-10px);
  opacity: 0;
}

.slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}
</style> 