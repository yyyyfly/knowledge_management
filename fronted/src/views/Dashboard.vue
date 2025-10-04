<template>
  <section id="dashboard" class="p-6 animate-fade-in">
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
            <div v-for="honor in defenseHonors" :key="honor.id" class="flex items-center justify-between p-3 bg-white/50 rounded-lg border border-red-100 group">
              <div class="flex items-center">
                <i :class="`fa ${honor.icon} text-warning mr-3 text-lg`"></i>
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
            <div v-for="honor in constructionHonors" :key="honor.id" class="flex items-center justify-between p-3 bg-white/50 rounded-lg border border-blue-100 group">
              <div class="flex items-center">
                <i :class="`fa ${honor.icon} text-warning mr-3 text-lg`"></i>
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
            <div v-for="honor in diplomaticHonors" :key="honor.id" class="flex items-center justify-between p-3 bg-white/50 rounded-lg border border-green-100 group">
              <div class="flex items-center">
                <i :class="`fa ${honor.icon} text-warning mr-3 text-lg`"></i>
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

    <!-- 行动组管理 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="mb-6">
        <h3 class="text-xl font-semibold text-gray-900 flex items-center">
          <i class="fa-solid fa-briefcase text-secondary mr-3"></i>
          行动组
        </h3>
        <p class="text-gray-600 text-sm mt-1">项目执行与任务管理</p>
          </div>
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- 战争行动工程 -->
        <div class="group relative bg-gradient-to-br from-red-50 via-red-100 to-red-50 rounded-xl p-6 hover:shadow-xl transition-all duration-300 cursor-pointer border border-red-200 hover:border-red-300 overflow-hidden" @click="$router.push('/actions/defense-projects')">
          <!-- 背景装饰 -->
          <div class="absolute top-0 right-0 w-24 h-24 bg-gradient-to-bl from-red-200/30 to-transparent rounded-full -translate-y-8 translate-x-8"></div>
          <div class="absolute bottom-0 left-0 w-16 h-16 bg-gradient-to-tr from-red-200/20 to-transparent rounded-full translate-y-8 -translate-x-8"></div>
          
          <!-- 头部区域 -->
          <div class="relative flex items-center justify-between mb-6">
            <div class="flex items-center space-x-3">
              <div class="w-12 h-12 bg-gradient-to-br from-red-500 to-red-600 rounded-xl flex items-center justify-center shadow-lg group-hover:scale-110 transition-transform duration-300">
                <i class="fa-solid fa-crosshairs text-white text-lg"></i>
              </div>
              <div>
                <h4 class="text-xl font-bold text-gray-900">战争行动</h4>
                <p class="text-sm text-gray-600">实战任务管理</p>
              </div>
            </div>
            <span class="bg-gradient-to-r from-red-500 to-red-600 text-white px-4 py-2 rounded-full text-sm font-semibold shadow-md">实战任务</span>
          </div>
          
          <!-- 描述 -->
          <p class="text-gray-700 mb-6 text-sm leading-relaxed">日常工作执行、考试备战、副业发展等核心任务的管理与追踪</p>
          
          <!-- 统计卡片 -->
          <div class="grid grid-cols-2 gap-4 mb-6">
            <!-- 项目统计卡片 -->
            <div class="bg-white/70 backdrop-blur-sm rounded-lg p-4 border border-red-200/50">
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-medium text-gray-600">项目总数</span>
                <i class="fa-solid fa-folder text-red-500 text-sm"></i>
              </div>
              <div class="flex items-baseline space-x-1">
                <span class="text-2xl font-bold text-gray-900">{{ defenseProjects.length }}</span>
                <span class="text-sm text-gray-500">个</span>
              </div>
              <div class="mt-2 space-y-1">
                <div class="flex justify-between text-xs">
                  <span class="text-blue-600">已完成</span>
                  <span class="font-medium">1</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-green-600">进行中</span>
                  <span class="font-medium">2</span>
            </div>
                <div class="flex justify-between text-xs">
                  <span class="text-yellow-600">规划中</span>
                  <span class="font-medium">0</span>
              </div>
              </div>
            </div>
            
            <!-- 任务统计卡片 -->
            <div class="bg-white/70 backdrop-blur-sm rounded-lg p-4 border border-red-200/50">
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-medium text-gray-600">任务总数</span>
                <i class="fa-solid fa-tasks text-red-500 text-sm"></i>
              </div>
              <div class="flex items-baseline space-x-1">
                <span class="text-2xl font-bold text-gray-900">{{ allTasks.filter(t => defenseProjects.some(p => p.id === t.projectId)).length }}</span>
                <span class="text-sm text-gray-500">个</span>
              </div>
              <div class="mt-2 space-y-1">
                <div class="flex justify-between text-xs">
                  <span class="text-green-600">已完成</span>
                  <span class="font-medium">9</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-blue-600">进行中</span>
                  <span class="font-medium">2</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-yellow-600">待开始</span>
                  <span class="font-medium">8</span>
            </div>
                <div class="flex justify-between text-xs">
                  <span class="text-red-600">已停止</span>
                  <span class="font-medium">0</span>
              </div>
              </div>
            </div>
          </div>
          
          <!-- 底部信息 -->
          <div class="flex items-center justify-between text-sm text-gray-500">
            <div class="flex items-center space-x-2">
              <i class="fas fa-clock text-gray-400"></i>
              <span>最近更新: 2小时前</span>
            </div>
            <div class="flex items-center space-x-1 text-red-500 group-hover:translate-x-1 transition-transform duration-300">
              <span class="text-sm font-medium">查看详情</span>
              <i class="fas fa-arrow-right text-xs"></i>
        </div>
          </div>
        </div>

        <!-- 工程建设管理 -->
        <div class="group relative bg-gradient-to-br from-blue-50 via-blue-100 to-blue-50 rounded-xl p-6 hover:shadow-xl transition-all duration-300 cursor-pointer border border-blue-200 hover:border-blue-300 overflow-hidden" @click="$router.push('/actions/construction-projects')">
          <!-- 背景装饰 -->
          <div class="absolute top-0 right-0 w-24 h-24 bg-gradient-to-bl from-blue-200/30 to-transparent rounded-full -translate-y-8 translate-x-8"></div>
          <div class="absolute bottom-0 left-0 w-16 h-16 bg-gradient-to-tr from-blue-200/20 to-transparent rounded-full translate-y-8 -translate-x-8"></div>
          
          <!-- 头部区域 -->
          <div class="relative flex items-center justify-between mb-6">
            <div class="flex items-center space-x-3">
              <div class="w-12 h-12 bg-gradient-to-br from-blue-500 to-blue-600 rounded-xl flex items-center justify-center shadow-lg group-hover:scale-110 transition-transform duration-300">
                <i class="fa-solid fa-building text-white text-lg"></i>
              </div>
              <div>
                <h4 class="text-xl font-bold text-gray-900">工程建设</h4>
                <p class="text-sm text-gray-600">模拟实战管理</p>
              </div>
            </div>
            <span class="bg-gradient-to-r from-blue-500 to-blue-600 text-white px-4 py-2 rounded-full text-sm font-semibold shadow-md">模拟实战</span>
          </div>
          
          <!-- 描述 -->
          <p class="text-gray-700 mb-6 text-sm leading-relaxed">技术项目实践、模拟业务场景、技能训练等开发项目的管理与执行</p>
          
          <!-- 统计卡片 -->
          <div class="grid grid-cols-2 gap-4 mb-6">
            <!-- 项目统计卡片 -->
            <div class="bg-white/70 backdrop-blur-sm rounded-lg p-4 border border-blue-200/50">
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-medium text-gray-600">项目总数</span>
                <i class="fa-solid fa-folder text-blue-500 text-sm"></i>
              </div>
              <div class="flex items-baseline space-x-1">
                <span class="text-2xl font-bold text-gray-900">{{ constructionProjects.length }}</span>
                <span class="text-sm text-gray-500">个</span>
              </div>
              <div class="mt-2 space-y-1">
                <div class="flex justify-between text-xs">
                  <span class="text-green-600">进行中</span>
                  <span class="font-medium">{{ constructionProjects.filter(p => p.status === 'inProgress').length }}</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-yellow-600">规划中</span>
                  <span class="font-medium">{{ constructionProjects.filter(p => p.status === 'planning').length }}</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-blue-600">已完成</span>
                  <span class="font-medium">{{ constructionProjects.filter(p => p.status === 'completed').length }}</span>
                </div>
              </div>
            </div>
            
            <!-- 任务统计卡片 -->
            <div class="bg-white/70 backdrop-blur-sm rounded-lg p-4 border border-blue-200/50">
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-medium text-gray-600">任务总数</span>
                <i class="fa-solid fa-tasks text-blue-500 text-sm"></i>
              </div>
              <div class="flex items-baseline space-x-1">
                <span class="text-2xl font-bold text-gray-900">{{ allTasks.filter(t => constructionProjects.some(p => p.id === t.projectId)).length }}</span>
                <span class="text-sm text-gray-500">个</span>
              </div>
              <div class="mt-2 space-y-1">
                <div class="flex justify-between text-xs">
                  <span class="text-green-600">已完成</span>
                  <span class="font-medium">5</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-blue-600">进行中</span>
                  <span class="font-medium">1</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-yellow-600">待开始</span>
                  <span class="font-medium">6</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-red-600">已停止</span>
                  <span class="font-medium">0</span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 底部信息 -->
          <div class="flex items-center justify-between text-sm text-gray-500">
            <div class="flex items-center space-x-2">
              <i class="fas fa-clock text-gray-400"></i>
              <span>最近更新: 1小时前</span>
            </div>
            <div class="flex items-center space-x-1 text-blue-500 group-hover:translate-x-1 transition-transform duration-300">
              <span class="text-sm font-medium">查看详情</span>
              <i class="fas fa-arrow-right text-xs"></i>
        </div>
          </div>
        </div>

        <!-- 外交行动协作 -->
        <div class="group relative bg-gradient-to-br from-green-50 via-green-100 to-green-50 rounded-xl p-6 hover:shadow-xl transition-all duration-300 cursor-pointer border border-green-200 hover:border-green-300 overflow-hidden" @click="$router.push('/actions/diplomatic-projects')">
          <!-- 背景装饰 -->
          <div class="absolute top-0 right-0 w-24 h-24 bg-gradient-to-bl from-green-200/30 to-transparent rounded-full -translate-y-8 translate-x-8"></div>
          <div class="absolute bottom-0 left-0 w-16 h-16 bg-gradient-to-tr from-green-200/20 to-transparent rounded-full translate-y-8 -translate-x-8"></div>
          
          <!-- 头部区域 -->
          <div class="relative flex items-center justify-between mb-6">
            <div class="flex items-center space-x-3">
              <div class="w-12 h-12 bg-gradient-to-br from-green-500 to-green-600 rounded-xl flex items-center justify-center shadow-lg group-hover:scale-110 transition-transform duration-300">
                <i class="fa-solid fa-handshake text-white text-lg"></i>
              </div>
              <div>
                <h4 class="text-xl font-bold text-gray-900">外交行动</h4>
                <p class="text-sm text-gray-600">对外推广管理</p>
              </div>
            </div>
            <span class="bg-gradient-to-r from-green-500 to-green-600 text-white px-4 py-2 rounded-full text-sm font-semibold shadow-md">对外推广</span>
          </div>
          
          <!-- 描述 -->
          <p class="text-gray-700 mb-6 text-sm leading-relaxed">已有成果对外分享、技术交流、利益互动等协作项目的管理与执行</p>
          
          <!-- 统计卡片 -->
          <div class="grid grid-cols-2 gap-4 mb-6">
            <!-- 项目统计卡片 -->
            <div class="bg-white/70 backdrop-blur-sm rounded-lg p-4 border border-green-200/50">
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-medium text-gray-600">项目总数</span>
                <i class="fa-solid fa-folder text-green-500 text-sm"></i>
              </div>
              <div class="flex items-baseline space-x-1">
                <span class="text-2xl font-bold text-gray-900">{{ diplomaticProjects.length }}</span>
                <span class="text-sm text-gray-500">个</span>
              </div>
              <div class="mt-2 space-y-1">
                <div class="flex justify-between text-xs">
                  <span class="text-green-600">进行中</span>
                  <span class="font-medium">{{ diplomaticProjects.filter(p => p.status === 'inProgress').length }}</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-yellow-600">规划中</span>
                  <span class="font-medium">{{ diplomaticProjects.filter(p => p.status === 'planning').length }}</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-blue-600">已完成</span>
                  <span class="font-medium">{{ diplomaticProjects.filter(p => p.status === 'completed').length }}</span>
                </div>
              </div>
            </div>
            
            <!-- 任务统计卡片 -->
            <div class="bg-white/70 backdrop-blur-sm rounded-lg p-4 border border-green-200/50">
              <div class="flex items-center justify-between mb-2">
                <span class="text-xs font-medium text-gray-600">任务总数</span>
                <i class="fa-solid fa-tasks text-green-500 text-sm"></i>
              </div>
              <div class="flex items-baseline space-x-1">
                <span class="text-2xl font-bold text-gray-900">{{ allTasks.filter(t => diplomaticProjects.some(p => p.id === t.projectId)).length }}</span>
                <span class="text-sm text-gray-500">个</span>
              </div>
              <div class="mt-2 space-y-1">
                <div class="flex justify-between text-xs">
                  <span class="text-green-600">已完成</span>
                  <span class="font-medium">0</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-blue-600">进行中</span>
                  <span class="font-medium">1</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-yellow-600">待开始</span>
                  <span class="font-medium">4</span>
                </div>
                <div class="flex justify-between text-xs">
                  <span class="text-red-600">已停止</span>
                  <span class="font-medium">0</span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 底部信息 -->
          <div class="flex items-center justify-between text-sm text-gray-500">
            <div class="flex items-center space-x-2">
              <i class="fas fa-clock text-gray-400"></i>
              <span>最近更新: 4小时前</span>
            </div>
            <div class="flex items-center space-x-1 text-green-500 group-hover:translate-x-1 transition-transform duration-300">
              <span class="text-sm font-medium">查看详情</span>
              <i class="fas fa-arrow-right text-xs"></i>
            </div>
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

    <!-- 最近活动 -->
    <div class="bg-white rounded-xl shadow-soft p-6">
      <div class="mb-6">
        <h3 class="text-xl font-semibold text-gray-900 flex items-center">
          <i class="fa-solid fa-history text-accent mr-3"></i>
          最近活动
        </h3>
        <p class="text-gray-600 text-sm mt-1">系统操作记录与动态追踪</p>
      </div>
      <div class="space-y-4">
        <!-- 碎片笔记活动 -->
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-indigo-50 to-indigo-100 rounded-lg border border-indigo-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-indigo-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-puzzle-piece text-indigo-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">前端性能优化灵感集</p>
              <span class="bg-indigo-200 text-indigo-700 px-2 py-1 rounded-full text-xs font-medium">碎片笔记</span>
            </div>
            <p class="text-xs text-gray-600">记录了代码分割、懒加载等优化技巧的灵感片段</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">3天前</span>
          </div>
        </div>
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-indigo-50 to-indigo-100 rounded-lg border border-indigo-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-indigo-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-puzzle-piece text-indigo-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">移动端交互细节随记</p>
              <span class="bg-indigo-200 text-indigo-700 px-2 py-1 rounded-full text-xs font-medium">碎片笔记</span>
            </div>
            <p class="text-xs text-gray-600">收集移动端常见交互设计灵感与问题</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">5天前</span>
          </div>
        </div>
        <!-- 框架笔记活动 -->
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-purple-50 to-purple-100 rounded-lg border border-purple-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-purple-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-sitemap text-purple-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">Vue 3 框架知识导图</p>
              <span class="bg-purple-200 text-purple-700 px-2 py-1 rounded-full text-xs font-medium">框架笔记</span>
            </div>
            <p class="text-xs text-gray-600">整理Vue 3核心知识点的结构化导图</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">2天前</span>
          </div>
        </div>
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-purple-50 to-purple-100 rounded-lg border border-purple-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-purple-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-sitemap text-purple-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">前后端分层架构梳理</p>
              <span class="bg-purple-200 text-purple-700 px-2 py-1 rounded-full text-xs font-medium">框架笔记</span>
            </div>
            <p class="text-xs text-gray-600">总结常见分层架构及其优缺点</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">4天前</span>
          </div>
        </div>
        <!-- 求学笔记活动 -->
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-blue-50 to-blue-100 rounded-lg border border-blue-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-blue-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-graduation-cap text-blue-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">算法课程学习笔记</p>
              <span class="bg-blue-200 text-blue-700 px-2 py-1 rounded-full text-xs font-medium">求学笔记</span>
            </div>
            <p class="text-xs text-gray-600">记录算法课程中的重点难点与解题思路</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">1天前</span>
          </div>
        </div>
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-blue-50 to-blue-100 rounded-lg border border-blue-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-blue-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-graduation-cap text-blue-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">《深入理解计算机系统》学习</p>
              <span class="bg-blue-200 text-blue-700 px-2 py-1 rounded-full text-xs font-medium">求学笔记</span>
            </div>
            <p class="text-xs text-gray-600">系统性梳理CSAPP书籍内容与思考</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">2天前</span>
          </div>
        </div>
        <!-- 背诵笔记活动 -->
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-green-50 to-green-100 rounded-lg border border-green-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-green-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-lightbulb text-green-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">高频考点公式背诵</p>
              <span class="bg-green-200 text-green-700 px-2 py-1 rounded-full text-xs font-medium">背诵笔记</span>
            </div>
            <p class="text-xs text-gray-600">整理常考公式与记忆技巧</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">1天前</span>
          </div>
        </div>
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-green-50 to-green-100 rounded-lg border border-green-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-green-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-lightbulb text-green-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">英语单词记忆卡片</p>
              <span class="bg-green-200 text-green-700 px-2 py-1 rounded-full text-xs font-medium">背诵笔记</span>
            </div>
            <p class="text-xs text-gray-600">制作高频词汇记忆卡片，便于随时复习</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">2天前</span>
          </div>
        </div>
        <!-- 刷题笔记活动 -->
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-orange-50 to-orange-100 rounded-lg border border-orange-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-orange-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-pen-to-square text-orange-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">动态规划刷题总结</p>
              <span class="bg-orange-200 text-orange-700 px-2 py-1 rounded-full text-xs font-medium">刷题笔记</span>
            </div>
            <p class="text-xs text-gray-600">总结DP题型的常用套路与易错点</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">3天前</span>
          </div>
        </div>
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-orange-50 to-orange-100 rounded-lg border border-orange-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-orange-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-pen-to-square text-orange-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">二分查找刷题记录</p>
              <span class="bg-orange-200 text-orange-700 px-2 py-1 rounded-full text-xs font-medium">刷题笔记</span>
            </div>
            <p class="text-xs text-gray-600">记录二分查找相关题目的解题思路</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">4天前</span>
          </div>
        </div>
        <!-- 实战笔记活动 -->
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-red-50 to-red-100 rounded-lg border border-red-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-red-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-flask text-red-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">微前端架构实践总结</p>
              <span class="bg-red-200 text-red-700 px-2 py-1 rounded-full text-xs font-medium">实战笔记</span>
            </div>
            <p class="text-xs text-gray-600">基于qiankun的微前端架构设计与实战经验</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">2天前</span>
          </div>
        </div>
        <div class="flex items-center space-x-4 p-4 bg-gradient-to-r from-red-50 to-red-100 rounded-lg border border-red-200 hover:shadow-md transition-shadow">
          <div class="w-12 h-12 bg-red-200 rounded-full flex items-center justify-center shadow-sm">
            <i class="fas fa-flask text-red-600 text-lg"></i>
          </div>
          <div class="flex-1">
            <div class="flex items-center space-x-2 mb-1">
              <p class="text-sm font-medium text-gray-900">CI/CD自动化部署实战</p>
              <span class="bg-red-200 text-red-700 px-2 py-1 rounded-full text-xs font-medium">实战笔记</span>
            </div>
            <p class="text-xs text-gray-600">记录CI/CD自动化部署流程与踩坑经验</p>
          </div>
          <div class="text-right">
            <span class="text-xs text-gray-500 font-medium">3天前</span>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, onActivated } from 'vue'
import Chart from 'chart.js/auto'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/zh-cn'
// 使用API请求
import request from '@/api/request'

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

// 动态获取各类笔记最近更新时间（改为异步）
const getLatestUpdate = (type: 'framework' | 'study' | 'memorization' | 'exercise' | 'practical' | 'fragment') => {
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

// 组件激活时刷新荣誉战绩数据
onActivated(() => {
  refreshHonors()
})

onMounted(async () => {
  // 先加载数据
  await loadData()
  
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
</script>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
</style> 