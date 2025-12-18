<template>
  <section id="system-decisions" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">日常决策</h1>
        <span class="bg-indigo-100 text-indigo-700 px-3 py-1 rounded-full text-sm font-medium">战略管理</span>
      </div>
      <p class="text-gray-600">管理项目结构、任务分配和问题处理，为项目执行提供决策支持</p>
    </div>

    <!-- 天气信息界面 -->
    <div class="bg-white rounded-xl shadow-soft mb-6 overflow-hidden">
      <button 
        @click="toggleWeatherSection"
        class="w-full px-6 py-4 bg-gradient-to-r from-sky-600 to-blue-600 hover:from-sky-700 hover:to-blue-700 transition-all flex items-center justify-between text-white"
      >
        <div class="flex items-center space-x-3">
          <i class="fas fa-cloud-sun text-xl"></i>
          <h3 class="text-xl font-semibold">天气信息</h3>
          <span v-if="currentWeather.city" class="text-sm bg-white/20 px-2 py-1 rounded-full">{{ currentWeather.city }}</span>
        </div>
        <i :class="isWeatherSectionExpanded ? 'fas fa-chevron-up' : 'fas fa-chevron-down'" class="text-xl transition-transform"></i>
      </button>
      
      <Transition name="collapse">
        <div v-if="isWeatherSectionExpanded" class="p-6">
          <!-- 城市选择 -->
          <div class="flex items-center justify-between mb-6">
            <div class="flex items-center space-x-4">
              <label class="text-sm font-medium text-gray-700">选择城市：</label>
              <select 
                v-model="selectedCity" 
                @change="loadWeather"
                class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-sky-500 focus:border-sky-500 bg-white"
              >
                <option value="Shanghai">上海</option>
                <option value="Beijing">北京</option>
                <option value="Guangzhou">广州</option>
                <option value="Shenzhen">深圳</option>
                <option value="Hangzhou">杭州</option>
                <option value="Chengdu">成都</option>
                <option value="Wuhan">武汉</option>
                <option value="Nanjing">南京</option>
              </select>
            </div>
            <button 
              @click="loadWeather"
              class="px-4 py-2 bg-sky-600 text-white rounded-lg hover:bg-sky-700 flex items-center space-x-2"
            >
              <i class="fas fa-sync-alt"></i>
              <span>刷新</span>
            </button>
          </div>

          <!-- 天气信息加载中 -->
          <div v-if="weatherLoading" class="text-center py-8">
            <i class="fas fa-spinner fa-spin text-3xl text-sky-600 mb-2"></i>
            <p class="text-gray-600">正在获取天气信息...</p>
          </div>

          <!-- 天气信息错误 -->
          <div v-else-if="weatherError" class="text-center py-8">
            <i class="fas fa-exclamation-circle text-3xl text-red-500 mb-2"></i>
            <p class="text-red-600">{{ weatherError }}</p>
            <button 
              @click="loadWeather"
              class="mt-4 px-4 py-2 bg-sky-600 text-white rounded-lg hover:bg-sky-700"
            >
              重新加载
            </button>
          </div>

          <!-- 天气信息展示 -->
          <div v-else-if="currentWeather.temp" class="space-y-4">
            <!-- 当前天气概览 -->
            <div class="bg-gradient-to-br from-sky-50 to-blue-50 rounded-xl p-6 border border-sky-200">
              <div class="flex items-start justify-between">
                <!-- 左侧：温度和天气状况 -->
                <div class="flex items-center space-x-6">
                  <div class="text-center">
                    <div class="text-5xl font-bold text-gray-900">{{ currentWeather.temp }}°</div>
                    <div class="text-sm text-gray-600 mt-1">{{ currentWeather.feelsLike }}° 体感</div>
                  </div>
                  <div class="border-l border-sky-300 pl-6">
                    <div class="text-2xl font-semibold text-gray-800 mb-2">{{ currentWeather.condition }}</div>
                    <div class="space-y-1 text-sm text-gray-600">
                      <div><i class="fas fa-temperature-high text-red-500 mr-2"></i>最高: {{ currentWeather.maxTemp }}°</div>
                      <div><i class="fas fa-temperature-low text-blue-500 mr-2"></i>最低: {{ currentWeather.minTemp }}°</div>
                    </div>
                  </div>
                </div>
                
                <!-- 右侧：天气图标 -->
                <div class="text-6xl">{{ currentWeather.emoji }}</div>
              </div>
            </div>

            <!-- 详细信息 -->
            <div class="grid grid-cols-4 gap-4">
              <div class="bg-white rounded-lg p-4 border border-gray-200">
                <div class="flex items-center space-x-2 text-gray-600 mb-2">
                  <i class="fas fa-wind text-sky-600"></i>
                  <span class="text-sm">风速</span>
                </div>
                <div class="text-lg font-semibold text-gray-900">{{ currentWeather.windSpeed }}</div>
              </div>
              
              <div class="bg-white rounded-lg p-4 border border-gray-200">
                <div class="flex items-center space-x-2 text-gray-600 mb-2">
                  <i class="fas fa-tint text-blue-600"></i>
                  <span class="text-sm">湿度</span>
                </div>
                <div class="text-lg font-semibold text-gray-900">{{ currentWeather.humidity }}%</div>
              </div>
              
              <div class="bg-white rounded-lg p-4 border border-gray-200">
                <div class="flex items-center space-x-2 text-gray-600 mb-2">
                  <i class="fas fa-eye text-purple-600"></i>
                  <span class="text-sm">能见度</span>
                </div>
                <div class="text-lg font-semibold text-gray-900">{{ currentWeather.visibility }}</div>
              </div>
              
              <div class="bg-white rounded-lg p-4 border border-gray-200">
                <div class="flex items-center space-x-2 text-gray-600 mb-2">
                  <i class="fas fa-compress-arrows-alt text-orange-600"></i>
                  <span class="text-sm">气压</span>
                </div>
                <div class="text-lg font-semibold text-gray-900">{{ currentWeather.pressure }}</div>
              </div>
            </div>

            <!-- 更新时间 -->
            <div class="text-center text-xs text-gray-500 pt-2">
              <i class="fas fa-clock mr-1"></i>
              更新时间：{{ currentWeather.updateTime }}
            </div>

            <!-- 未来天气预报 -->
            <div v-if="forecastWeather.length > 0" class="mt-6">
              <h4 class="text-lg font-semibold text-gray-800 mb-4 flex items-center">
                <i class="fas fa-calendar-week text-sky-600 mr-2"></i>
                未来预报
                <span class="ml-2 text-sm text-gray-500">({{ forecastWeather.length }}天)</span>
              </h4>
              <div class="grid gap-4" :class="{
                'grid-cols-2': forecastWeather.length === 2,
                'grid-cols-3': forecastWeather.length === 3
              }">
                <div 
                  v-for="(forecast, index) in forecastWeather" 
                  :key="index"
                  class="bg-white rounded-xl p-4 border border-gray-200 hover:border-sky-300 hover:shadow-md transition-all"
                >
                  <!-- 日期和星期 -->
                  <div class="text-center mb-3">
                    <div class="text-sm text-gray-500">{{ forecast.date }}</div>
                    <div class="text-base font-semibold text-gray-800">{{ forecast.dayOfWeek }}</div>
                  </div>
                  
                  <!-- 天气图标和状况 -->
                  <div class="text-center mb-3">
                    <div class="text-4xl mb-2">{{ forecast.emoji }}</div>
                    <div class="text-sm text-gray-700">{{ forecast.condition }}</div>
                  </div>
                  
                  <!-- 温度范围 -->
                  <div class="flex items-center justify-center space-x-2 text-sm">
                    <div class="flex items-center text-red-600">
                      <i class="fas fa-temperature-high mr-1"></i>
                      <span class="font-semibold">{{ forecast.maxTemp }}°</span>
                    </div>
                    <span class="text-gray-400">/</span>
                    <div class="flex items-center text-blue-600">
                      <i class="fas fa-temperature-low mr-1"></i>
                      <span class="font-semibold">{{ forecast.minTemp }}°</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </div>

    <!-- 日历管理界面 -->
    <div class="bg-white rounded-xl shadow-soft mb-6 overflow-hidden">
      <button 
        @click="toggleCalendarSection"
        class="w-full px-6 py-4 bg-gradient-to-r from-emerald-600 to-teal-600 hover:from-emerald-700 hover:to-teal-700 transition-all flex items-center justify-between text-white"
      >
        <div class="flex items-center space-x-3">
          <i class="fas fa-calendar-alt text-xl"></i>
          <h3 class="text-xl font-semibold">重要日期</h3>
        </div>
        <i :class="isCalendarSectionExpanded ? 'fas fa-chevron-up' : 'fas fa-chevron-down'" class="text-xl transition-transform"></i>
      </button>
      
      <Transition name="collapse">
        <div v-if="isCalendarSectionExpanded" class="p-6">
          <!-- 月份导航栏 -->
          <div class="flex items-center justify-between mb-6">
            <div class="flex items-center space-x-4">
              <button 
                @click="previousMonth"
                class="w-10 h-10 flex items-center justify-center rounded-lg bg-emerald-100 hover:bg-emerald-200 text-emerald-700 transition-colors"
              >
                <i class="fas fa-chevron-left"></i>
              </button>
              <h3 class="text-2xl font-bold text-gray-900">
                {{ currentYear }}年{{ currentMonth }}月
              </h3>
              <button 
                @click="nextMonth"
                class="w-10 h-10 flex items-center justify-center rounded-lg bg-emerald-100 hover:bg-emerald-200 text-emerald-700 transition-colors"
              >
                <i class="fas fa-chevron-right"></i>
              </button>
              <button 
                @click="goToToday"
                class="px-4 py-2 bg-emerald-600 text-white rounded-lg hover:bg-emerald-700 transition-colors text-sm"
              >
                今天
              </button>
            </div>
            <button 
              @click="openCreateEventModal"
              class="px-4 py-2 bg-emerald-600 text-white rounded-lg hover:bg-emerald-700 flex items-center space-x-2"
            >
              <i class="fas fa-plus"></i>
              <span>添加日期</span>
            </button>
          </div>

          <!-- 日历网格 -->
          <div class="bg-white border border-gray-200 rounded-lg overflow-hidden mb-6">
            <!-- 星期表头 -->
            <div class="grid grid-cols-7 bg-gray-50 border-b border-gray-200">
              <div v-for="day in weekDays" :key="day" 
                class="text-center py-3 text-sm font-semibold text-gray-700 border-r border-gray-200 last:border-r-0">
                {{ day }}
              </div>
            </div>
            
            <!-- 日期网格 -->
            <div class="grid grid-cols-7">
              <div 
                v-for="(day, index) in calendarDays" 
                :key="index"
                class="min-h-[120px] border-b border-r border-gray-200 last:border-r-0 p-2 transition-all hover:bg-gray-50"
                :class="{
                  'bg-gray-50': !day.isCurrentMonth,
                  'bg-blue-50': day.isToday,
                  'cursor-pointer': day.isCurrentMonth
                }"
                @click="day.isCurrentMonth && selectDate(day.date)"
              >
                <!-- 日期数字 -->
                <div class="flex items-center justify-between mb-1">
                  <span 
                    class="text-sm font-medium"
                    :class="{
                      'text-gray-400': !day.isCurrentMonth,
                      'text-blue-600 font-bold': day.isToday,
                      'text-gray-900': day.isCurrentMonth && !day.isToday
                    }"
                  >
                    {{ day.day }}
                  </span>
                  <span v-if="day.isToday" class="text-xs bg-blue-600 text-white px-2 py-0.5 rounded-full">今</span>
                </div>
                
                <!-- 节假日/补班标记 -->
                <div v-if="day.holiday" class="mb-1">
                  <!-- 法定假日 -->
                  <div v-if="day.holiday.isHoliday" 
                    class="text-xs bg-red-100 text-red-700 px-2 py-1 rounded truncate font-semibold" 
                    :title="`${day.holiday.name} - ${day.holiday.type}`">
                    🎉 {{ day.holiday.name }}
                  </div>
                  <!-- 补班工作日 -->
                  <div v-else 
                    class="text-xs bg-orange-100 text-orange-700 px-2 py-1 rounded truncate font-semibold border border-orange-300" 
                    :title="`${day.holiday.name} - 工作日`">
                    💼 {{ day.holiday.name }}
                  </div>
                </div>
                
                <!-- 事件列表 -->
                <div class="space-y-1">
                  <div 
                    v-for="event in day.events.slice(0, 2)" 
                    :key="event.id"
                    class="text-xs px-2 py-1 rounded truncate group flex items-center justify-between"
                    :style="{ backgroundColor: event.color, color: '#fff' }"
                    :title="event.eventTitle + (event.description ? '\n' + event.description : '')"
                  >
                    <span class="flex-1 cursor-pointer" @click.stop="editEvent(event)">{{ event.eventTitle }}</span>
                    <button 
                      @click.stop="deleteEventQuick(event.id)"
                      class="ml-1 opacity-0 group-hover:opacity-100 transition-opacity hover:bg-white/20 rounded px-1"
                      title="删除"
                    >
                      <i class="fas fa-times text-xs"></i>
                    </button>
                  </div>
                  <div 
                    v-if="day.events.length > 2"
                    class="text-xs text-gray-600 px-2 py-1 bg-gray-100 rounded cursor-pointer hover:bg-gray-200"
                    @click.stop="showDayEvents(day)"
                  >
                    +{{ day.events.length - 2 }} 更多
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 即将到来的节假日 -->
          <div v-if="upcomingHolidays.length > 0" class="bg-gradient-to-r from-red-50 to-orange-50 rounded-lg p-4 border border-red-200">
            <h4 class="text-sm font-semibold text-red-700 mb-3 flex items-center">
              <i class="fas fa-gift mr-2"></i>
              即将到来的节假日
            </h4>
            <div class="grid grid-cols-3 gap-3">
              <div 
                v-for="holiday in upcomingHolidays.slice(0, 6)" 
                :key="holiday.date"
                class="bg-white rounded-lg p-3 border border-red-200 hover:border-red-400 transition-all"
              >
                <div class="text-xs text-gray-500 mb-1">{{ holiday.date }}</div>
                <div class="text-sm font-medium text-gray-900">{{ holiday.name }}</div>
                <div class="text-xs text-red-600 mt-1">{{ holiday.type }}</div>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </div>

    <!-- 项目管理界面 -->
    <div class="bg-white rounded-xl shadow-soft mb-6 overflow-hidden">
      <button 
        @click="toggleProjectSection"
        class="w-full px-6 py-4 bg-gradient-to-r from-indigo-600 to-blue-600 hover:from-indigo-700 hover:to-blue-700 transition-all flex items-center justify-between text-white"
      >
        <div class="flex items-center space-x-3">
          <i class="fas fa-project-diagram text-xl"></i>
          <h3 class="text-xl font-semibold">项目管理</h3>
        </div>
        <i :class="isProjectSectionExpanded ? 'fas fa-chevron-up' : 'fas fa-chevron-down'" class="text-xl transition-transform"></i>
      </button>
      
      <Transition name="collapse">
        <div v-if="isProjectSectionExpanded" class="p-6 max-h-[70vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <div class="text-gray-600 text-sm">管理项目结构和任务分配</div>
            <div class="flex space-x-2">
              <button 
                @click="showCreateProject = true"
                class="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 flex items-center space-x-2"
              >
                <i class="fas fa-plus"></i>
                <span>新建项目</span>
              </button>
            </div>
          </div>
      
      <!-- 项目搜索和分类筛选 -->
      <div class="mb-6 flex-shrink-0">
        <div class="flex flex-col md:flex-row gap-4">
          <div class="flex-1">
            <input 
              v-model="projectSearchQuery"
              type="text" 
              placeholder="搜索项目名称或描述..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
            >
          </div>
          <div>
            <select v-model="projectFilterCategory" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
              <option value="">全部分类</option>
              <option value="defense">战争行动</option>
              <option value="construction">工程建设</option>
              <option value="diplomatic">外交行动</option>
            </select>
          </div>
          <div>
            <select v-model="projectFilterStatus" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
              <option value="">全部状态</option>
              <option value="planning">规划中</option>
              <option value="active">进行中</option>
              <option value="completed">已完成</option>
              <option value="paused">暂停</option>
            </select>
          </div>
        </div>
      </div>
      
      <!-- 项目列表 -->
      <div class="space-y-6 flex-1 overflow-y-auto">
        <div v-for="project in paginatedProjects" :key="project.id" class="border border-gray-200 rounded-lg overflow-hidden">
          <!-- 项目头部 -->
          <div class="bg-gray-50 px-6 py-4 border-b border-gray-200">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-3">
                <div class="w-8 h-8 rounded-lg flex items-center justify-center" :class="getCategoryClass(project.category)">
                  <i :class="getCategoryIcon(project.category)" class="text-white text-sm"></i>
                </div>
                <div>
                  <h4 class="text-lg font-semibold text-gray-900">{{ project.name }}</h4>
                  <div class="flex items-center space-x-2 mt-1">
                    <span :class="getProjectStatusClass(project.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                      {{ getProjectStatusText(project.status) }}
                    </span>
                    <span class="text-xs text-gray-500 bg-gray-200 px-2 py-1 rounded-full">
                      {{ getCategoryText(project.category) }}
                    </span>
                    <span class="text-sm text-gray-500">({{ getProjectTasks(project.id).length }} 个任务)</span>
                  </div>
                </div>
              </div>
              <div class="flex items-center space-x-2">
                <!-- 加入荣誉战绩按钮 - 只对已完成项目显示 -->
                <button 
                  v-if="project.status === 'completed' && !isProjectInHonors(project.id)"
                  @click="addProjectToHonors(project)" 
                  class="text-yellow-600 hover:text-yellow-800"
                  :title="'将项目加入荣誉战绩'"
                >
                  <i class="fas fa-trophy"></i>
                </button>
                <button @click="editProject(project)" class="text-blue-600 hover:text-blue-800">
                  <i class="fas fa-edit"></i>
                </button>
                <button @click="deleteProject(project.id)" class="text-red-600 hover:text-red-800">
                  <i class="fas fa-trash"></i>
                </button>
                <button 
                  @click="toggleProjectExpanded(project.id)"
                  class="text-gray-500 hover:text-gray-700"
                >
                  <i :class="projectExpanded.includes(project.id) ? 'fas fa-chevron-up' : 'fas fa-chevron-down'"></i>
                </button>
              </div>
            </div>
            <p class="text-sm text-gray-600 mt-2">{{ project.description }}</p>
            <div class="flex items-center space-x-4 text-sm text-gray-500 mt-2">
              <span>创建时间：{{ project.createTime }}</span>
            </div>
          </div>
          
          <!-- 项目任务列表 -->
          <Transition name="expand">
            <div v-if="projectExpanded.includes(project.id)" class="p-6">
              <!-- 任务管理头部 -->
              <div class="flex items-center justify-between mb-4">
                <h5 class="text-lg font-medium text-gray-900">任务管理</h5>
                <div class="flex space-x-2">
                  <button 
                    @click="addTaskToProject(project.id)"
                    class="px-3 py-1 bg-blue-600 text-white text-sm rounded hover:bg-blue-700 flex items-center space-x-1"
                  >
                    <i class="fas fa-plus"></i>
                    <span>添加任务</span>
                  </button>
                </div>
              </div>
              
              <!-- 任务筛选 -->
              <div class="mb-4">
                <div class="flex flex-col md:flex-row gap-4">
                  <div class="flex-1">
                    <input 
                      v-model="taskSearchQuery"
                      type="text" 
                      placeholder="搜索任务名称或描述..."
                      class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-sm"
                    >
                  </div>
                  <div>
                    <select v-model="taskFilterStatus" class="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-sm">
                      <option value="">全部状态</option>
                      <option value="pending">待开始</option>
                      <option value="in-progress">进行中</option>
                      <option value="completed">已完成</option>
                    </select>
                  </div>
                </div>
              </div>
              
              <!-- 任务列表 -->
              <div class="space-y-3">
                <div v-for="task in getFilteredProjectTasks(project.id)" :key="task.id" class="p-3 border border-gray-200 rounded-lg hover:bg-gray-50">
                  <div class="flex items-start justify-between">
                    <div class="flex-1">
                      <div class="flex items-center space-x-3 mb-2">
                        <h6 class="text-md font-medium text-gray-900">{{ task.name }}</h6>
                        <span :class="getTaskStatusClass(task.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                          {{ getTaskStatusText(task.status) }}
                        </span>
                      </div>
                      <p class="text-sm text-gray-600 mb-2">{{ task.description }}</p>
                      <div class="flex items-center space-x-4 text-xs text-gray-500">
                        <span>创建时间：{{ task.createTime }}</span>
                        <span>截止时间：{{ task.deadline }}</span>
                      </div>
                    </div>
                    <div class="flex space-x-2">
                      <button @click="editTask(task)" class="text-blue-600 hover:text-blue-800">
                        <i class="fas fa-edit"></i>
                      </button>
                      <button @click="deleteTask(task.id)" class="text-red-600 hover:text-red-800">
                        <i class="fas fa-trash"></i>
                      </button>
                    </div>
                  </div>
                </div>
                
                <!-- 空状态 -->
                <div v-if="getFilteredProjectTasks(project.id).length === 0" class="text-center py-6 text-gray-500">
                  <i class="fas fa-tasks text-2xl mb-2"></i>
                  <p class="text-sm">该项目暂无任务</p>
                  <button 
                    @click="addTaskToProject(project.id)"
                    class="mt-2 px-3 py-1 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
                  >
                    添加第一个任务
                  </button>
                </div>
              </div>
            </div>
          </Transition>
        </div>

        <!-- 项目搜索空状态 -->
        <div v-if="paginatedProjects.length === 0" class="text-center py-12 text-gray-500">
          <i class="fas fa-search text-4xl mb-4"></i>
          <p class="text-lg font-medium mb-2">未找到匹配的项目</p>
          <p class="text-sm">请尝试调整搜索条件或创建新项目</p>
          <button 
            @click="showCreateProject = true"
            class="mt-4 px-6 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700"
          >
            创建新项目
          </button>
        </div>
      </div>
      
      <!-- 项目分页控制 -->
      <div v-if="projectTotalPages > 1" class="flex items-center justify-center space-x-4 mt-6 flex-shrink-0">
        <button 
          @click="prevProjectPage" 
          :disabled="projectCurrentPage === 1"
          class="px-4 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <i class="fas fa-chevron-left mr-1"></i>上一页
        </button>
        <span class="text-sm text-gray-600">{{ projectCurrentPage }} / {{ projectTotalPages }}</span>
        <button 
          @click="nextProjectPage" 
          :disabled="projectCurrentPage === projectTotalPages"
          class="px-4 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          下一页<i class="fas fa-chevron-right ml-1"></i>
        </button>
        </div>
        </div>
      </Transition>
    </div>

    <!-- 问题处理界面 -->
    <div class="bg-white rounded-xl shadow-soft mb-6 overflow-hidden">
      <button 
        @click="toggleIssueSection"
        class="w-full px-6 py-4 bg-gradient-to-r from-rose-600 to-pink-600 hover:from-rose-700 hover:to-pink-700 transition-all flex items-center justify-between text-white"
      >
        <div class="flex items-center space-x-3">
          <i class="fas fa-exclamation-circle text-xl"></i>
          <h3 class="text-xl font-semibold">问题处理</h3>
          <span v-if="pendingIssuesCount > 0" class="bg-white/30 backdrop-blur-sm text-white px-2.5 py-0.5 rounded-full text-xs font-semibold border border-white/20">
            {{ pendingIssuesCount }}
          </span>
        </div>
        <i :class="isIssueSectionExpanded ? 'fas fa-chevron-up' : 'fas fa-chevron-down'" class="text-xl transition-transform"></i>
      </button>
      
      <Transition name="collapse">
        <div v-if="isIssueSectionExpanded" class="p-6 max-h-[70vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <div class="text-gray-600 text-sm">
              待处理：<span class="font-semibold text-orange-600">{{ pendingIssuesCount }}</span> | 
              已解决：<span class="font-semibold text-green-600">{{ resolvedIssuesCount }}</span>
            </div>
          </div>

      <!-- 问题筛选 -->
      <div class="mb-6 flex-shrink-0">
        <div class="flex flex-col md:flex-row gap-4">
          <div class="flex-1">
            <input 
              v-model="issueSearchQuery"
              type="text" 
              placeholder="搜索问题标题或描述..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500"
            >
          </div>
          <div>
            <select v-model="issueFilterProject" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500">
              <option value="">全部项目</option>
              <option v-for="project in projects" :key="project.id" :value="project.id">
                {{ project.name }}
              </option>
            </select>
          </div>
          <div>
            <select v-model="issueFilterStatus" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500">
              <option value="">全部状态</option>
              <option value="open">待处理</option>
              <option value="in_progress">处理中</option>
              <option value="resolved">已解决</option>
            </select>
          </div>
          <div>
            <select v-model="issueFilterPriority" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500">
              <option value="">全部优先级</option>
              <option value="urgent">紧急</option>
              <option value="high">高</option>
              <option value="medium">中</option>
              <option value="low">低</option>
            </select>
          </div>
        </div>
      </div>

      <!-- 问题列表 -->
      <div class="space-y-4 flex-1 overflow-y-auto">
        <div v-for="issue in filteredDecisionIssues" :key="issue.id" 
          class="border-2 rounded-lg p-5 transition-all hover:shadow-lg"
          :class="{
            'border-red-300 bg-red-50': issue.priority === 'urgent' && issue.status !== 'resolved',
            'border-orange-300 bg-orange-50': issue.priority === 'high' && issue.status !== 'resolved',
            'border-yellow-300 bg-yellow-50': issue.priority === 'medium' && issue.status !== 'resolved',
            'border-gray-300 bg-gray-50': issue.priority === 'low' && issue.status !== 'resolved',
            'border-green-300 bg-green-50': issue.status === 'resolved'
          }">
          
          <!-- 问题头部 -->
          <div class="flex items-start justify-between mb-3">
            <div class="flex-1">
              <div class="flex items-center space-x-3 mb-2">
                <h4 class="text-lg font-semibold text-gray-900">{{ issue.issueTitle }}</h4>
                <span class="px-2 py-1 rounded text-xs font-medium"
                  :class="{
                    'bg-red-100 text-red-700': issue.priority === 'urgent',
                    'bg-orange-100 text-orange-700': issue.priority === 'high',
                    'bg-yellow-100 text-yellow-700': issue.priority === 'medium',
                    'bg-gray-100 text-gray-700': issue.priority === 'low'
                  }">
                  {{ getIssuePriorityText(issue.priority) }}
                </span>
                <span class="px-2 py-1 rounded text-xs font-medium"
                  :class="{
                    'bg-gray-100 text-gray-700': issue.status === 'open',
                    'bg-blue-100 text-blue-700': issue.status === 'in_progress',
                    'bg-green-100 text-green-700': issue.status === 'resolved'
                  }">
                  {{ getIssueStatusTextDecision(issue.status) }}
                </span>
              </div>
              
              <p class="text-sm text-gray-700 mb-3 bg-white p-3 rounded-lg">{{ issue.issueDescription }}</p>
              
              <div class="flex items-center space-x-4 text-sm text-gray-500">
                <span class="flex items-center"><i class="fas fa-project-diagram mr-1"></i>{{ getProjectName(issue.projectId) }}</span>
                <span class="flex items-center"><i class="fas fa-tag mr-1"></i>{{ getIssueTypeTextDecision(issue.issueType) }}</span>
                <span class="flex items-center"><i class="fas fa-clock mr-1"></i>{{ formatIssueDate(issue.recCreateTime) }}</span>
              </div>
            </div>
          </div>

          <!-- 解决方案显示 -->
          <div v-if="issue.solution" class="mt-4 bg-green-100 border border-green-300 rounded-lg p-4">
            <div class="flex items-start">
              <i class="fas fa-check-circle text-green-600 text-xl mr-3 mt-1"></i>
              <div class="flex-1">
                <p class="text-sm font-medium text-green-800 mb-1">决策方案：</p>
                <p class="text-sm text-gray-700">{{ issue.solution }}</p>
                <p class="text-xs text-gray-500 mt-2">解决时间：{{ formatIssueDate(issue.resolveTime) }}</p>
              </div>
            </div>
          </div>

          <!-- 操作按钮 -->
          <div class="mt-4 flex space-x-3">
            <!-- 未解决状态的按钮 -->
            <template v-if="issue.status !== 'resolved'">
              <button 
                @click="startDeciding(issue)"
                class="flex-1 px-4 py-2 bg-orange-600 text-white rounded-lg hover:bg-orange-700 transition-colors flex items-center justify-center space-x-2"
              >
                <i class="fas fa-lightbulb"></i>
                <span>制定决策</span>
              </button>
              <button 
                v-if="issue.status === 'open'"
                @click="markIssueInProgress(issue.id)"
                class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
              >
                <i class="fas fa-play"></i>
              </button>
            </template>
            
            <!-- 已解决状态的按钮 -->
            <template v-else>
              <button 
                @click="reopenIssue(issue.id)"
                class="flex-1 px-4 py-2 bg-yellow-600 text-white rounded-lg hover:bg-yellow-700 transition-colors flex items-center justify-center space-x-2"
              >
                <i class="fas fa-undo"></i>
                <span>取消解决，重新处理</span>
              </button>
            </template>
          </div>
        </div>

          <!-- 空状态 -->
          <div v-if="filteredDecisionIssues.length === 0" class="text-center py-12 text-gray-500">
            <i class="fas fa-check-circle text-6xl mb-4 text-green-400"></i>
            <p class="text-lg font-medium mb-2">暂无待处理问题</p>
            <p class="text-sm">所有问题都已妥善处理！</p>
          </div>
        </div>
        </div>
      </Transition>
    </div>

    <!-- 打卡管理界面 -->
    <div class="bg-white rounded-xl shadow-soft mb-6 overflow-hidden">
      <button 
        @click="toggleCheckinSection"
        class="w-full px-6 py-4 bg-gradient-to-r from-purple-600 to-violet-600 hover:from-purple-700 hover:to-violet-700 transition-all flex items-center justify-between text-white"
      >
        <div class="flex items-center space-x-3">
          <i class="fas fa-check-circle text-xl"></i>
          <h3 class="text-xl font-semibold">打卡管理</h3>
        </div>
        <i :class="isCheckinSectionExpanded ? 'fas fa-chevron-up' : 'fas fa-chevron-down'" class="text-xl transition-transform"></i>
      </button>
      
      <Transition name="collapse">
        <div v-if="isCheckinSectionExpanded" class="p-6 max-h-[70vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <button 
              @click="openCreateCheckinModal"
              class="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 flex items-center space-x-2"
            >
              <i class="fas fa-plus"></i>
              <span>添加打卡项目</span>
            </button>
          </div>

          <!-- 打卡项目列表 -->
          <div v-if="checkinItems.length === 0" class="text-center py-12 text-gray-500 flex-1 overflow-y-auto">
            <i class="fas fa-check-circle text-5xl mb-4"></i>
            <p class="text-lg">暂无打卡项目</p>
            <p class="text-sm">点击上方按钮创建打卡项目</p>
          </div>

          <div v-else class="space-y-4 flex-1 overflow-y-auto">
            <div v-for="item in checkinItems" :key="item.id" 
              class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center space-x-3 mb-2">
                    <h4 class="text-lg font-medium text-gray-900">{{ item.title }}</h4>
                    <span :class="getFrequencyClass(item.frequency)" class="px-2 py-1 rounded-full text-xs font-medium">
                      {{ getFrequencyText(item.frequency) }}
                    </span>
                    <span :class="item.status === 'active' ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-700'" class="px-2 py-1 rounded-full text-xs font-medium">
                      {{ item.status === 'active' ? '启用' : '停用' }}
                    </span>
                  </div>
                  <p v-if="item.description" class="text-gray-600 mb-2">{{ item.description }}</p>
                </div>
                <div class="flex items-center space-x-2 ml-4">
                  <button 
                    @click="editCheckinItem(item)"
                    class="text-indigo-600 hover:text-indigo-800 transition-colors"
                    title="编辑"
                  >
                    <i class="fas fa-edit"></i>
                  </button>
                  <button 
                    @click="deleteCheckinItem(item.id)"
                    class="text-red-600 hover:text-red-800 transition-colors"
                    title="删除"
                  >
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </div>

    <!-- 添加/编辑日期弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCreateEvent" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-2xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">{{ currentEvent ? '编辑日期' : '添加重要日期' }}</h3>
              <button 
                @click="closeEventModal"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitEvent" class="space-y-6">
              <!-- 事件标题 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">事件标题 *</label>
                <input 
                  v-model="eventForm.eventTitle" 
                  type="text" 
                  required
                  placeholder="例如：项目发布、生日提醒等"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500"
                >
              </div>

              <!-- 事件日期 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">事件日期 *</label>
                <input 
                  v-model="eventForm.eventDate" 
                  type="date" 
                  required
                  placeholder="yyyy-MM-dd"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500"
                >
              </div>

              <!-- 重复类型 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">重复类型 *</label>
                <div class="grid grid-cols-4 gap-3">
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="eventForm.repeatType === 'once' ? 'border-emerald-500 bg-emerald-50' : 'border-gray-300 hover:border-emerald-300'">
                    <input type="radio" v-model="eventForm.repeatType" value="once" class="hidden">
                    <span class="text-sm font-medium" :class="eventForm.repeatType === 'once' ? 'text-emerald-700' : 'text-gray-700'">📅 单次</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="eventForm.repeatType === 'daily' ? 'border-blue-500 bg-blue-50' : 'border-gray-300 hover:border-blue-300'">
                    <input type="radio" v-model="eventForm.repeatType" value="daily" class="hidden">
                    <span class="text-sm font-medium" :class="eventForm.repeatType === 'daily' ? 'text-blue-700' : 'text-gray-700'">🔄 每天</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="eventForm.repeatType === 'monthly' ? 'border-purple-500 bg-purple-50' : 'border-gray-300 hover:border-purple-300'">
                    <input type="radio" v-model="eventForm.repeatType" value="monthly" class="hidden">
                    <span class="text-sm font-medium" :class="eventForm.repeatType === 'monthly' ? 'text-purple-700' : 'text-gray-700'">📆 每月</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="eventForm.repeatType === 'yearly' ? 'border-orange-500 bg-orange-50' : 'border-gray-300 hover:border-orange-300'">
                    <input type="radio" v-model="eventForm.repeatType" value="yearly" class="hidden">
                    <span class="text-sm font-medium" :class="eventForm.repeatType === 'yearly' ? 'text-orange-700' : 'text-gray-700'">🎂 每年</span>
                  </label>
                </div>
              </div>

              <!-- 倒计时功能 -->
              <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-200">
                <div class="flex items-center space-x-2">
                  <i class="fas fa-hourglass-half text-emerald-600"></i>
                  <div>
                    <label class="text-sm font-medium text-gray-700">启用倒计时</label>
                    <p class="text-xs text-gray-500">在其他地方显示距离此日期的天数</p>
                  </div>
                </div>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input 
                    type="checkbox" 
                    v-model="eventForm.showCountdown" 
                    class="sr-only peer"
                  >
                  <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-emerald-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-emerald-600"></div>
                </label>
              </div>

              <!-- 事件描述 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">事件描述</label>
                <textarea 
                  v-model="eventForm.description" 
                  rows="3"
                  placeholder="可选，添加更多说明..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500"
                ></textarea>
              </div>

              <!-- 事件颜色 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">标记颜色</label>
                <div class="flex space-x-3">
                  <button 
                    v-for="color in colorOptions" 
                    :key="color"
                    type="button"
                    @click="eventForm.color = color"
                    class="w-10 h-10 rounded-full border-2 transition-all"
                    :class="eventForm.color === color ? 'border-gray-800 ring-2 ring-offset-2 ring-gray-400' : 'border-gray-300'"
                    :style="{ backgroundColor: color }"
                  ></button>
                </div>
              </div>

              <!-- 操作按钮 -->
              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeEventModal"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-emerald-600 text-white rounded-lg hover:bg-emerald-700"
                >
                  {{ currentEvent ? '保存修改' : '添加日期' }}
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 制定决策弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showDecisionModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-3xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">制定决策方案</h3>
              <button 
                @click="closeDecisionModal"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>

            <!-- 问题信息 -->
            <div class="mb-6 p-4 bg-orange-50 border border-orange-200 rounded-lg">
              <h4 class="font-semibold text-gray-900 mb-2">{{ currentDecisionIssue?.issueTitle }}</h4>
              <p class="text-sm text-gray-700 mb-3">{{ currentDecisionIssue?.issueDescription }}</p>
              <div class="flex items-center space-x-4 text-sm text-gray-600">
                <span>项目：{{ getProjectName(currentDecisionIssue?.projectId) }}</span>
                <span>优先级：{{ getIssuePriorityText(currentDecisionIssue?.priority) }}</span>
                <span>类型：{{ getIssueTypeTextDecision(currentDecisionIssue?.issueType) }}</span>
              </div>
            </div>

            <form @submit.prevent="submitDecision" class="space-y-6">
              <!-- 决策方案 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  <i class="fas fa-lightbulb text-orange-500 mr-2"></i>决策方案 *
                </label>
                <textarea 
                  v-model="decisionForm.solution" 
                  rows="8"
                  required
                  placeholder="请详细描述解决方案和决策思路..."
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500"
                ></textarea>
              </div>

              <!-- 操作按钮 -->
              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeDecisionModal"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-orange-600 text-white rounded-lg hover:bg-orange-700 flex items-center space-x-2"
                >
                  <i class="fas fa-check"></i>
                  <span>确认决策</span>
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 项目弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCreateProject" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-md mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">{{ editingProject ? '编辑项目' : '新建项目' }}</h3>
              <button 
                @click="closeProjectForm"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitProject" class="space-y-6">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目名称</label>
                <input 
                  v-model="projectForm.name" 
                  type="text" 
                  placeholder="请输入项目名称"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  required
                >
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目描述</label>
                <textarea 
                  v-model="projectForm.description" 
                  rows="4" 
                  placeholder="请输入项目描述..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  required
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目分类</label>
                <select v-model="projectForm.category" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500" required>
                  <option value="">请选择项目分类</option>
                  <option value="defense">战争行动</option>
                  <option value="construction">工程建设</option>
                  <option value="diplomatic">外交行动</option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目状态</label>
                <select v-model="projectForm.status" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500">
                  <option value="planning">规划中</option>
                  <option value="active">进行中</option>
                  <option value="completed">已完成</option>
                  <option value="paused">暂停</option>
                </select>
              </div>

              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeProjectForm"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700"
                >
                  {{ editingProject ? '更新项目' : '创建项目' }}
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 任务弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCreateTask" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-md mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">{{ editingTask ? '编辑任务' : '新建任务' }}</h3>
              <button 
                @click="closeTaskForm"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitTask" class="space-y-6">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">所属项目</label>
                <select v-model="taskForm.projectId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" required>
                  <option value="">请选择项目</option>
                  <option v-for="project in projects" :key="project.id" :value="project.id">
                    {{ project.name }}
                  </option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">任务名称</label>
                <input 
                  v-model="taskForm.name" 
                  type="text" 
                  placeholder="请输入任务名称"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  required
                >
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">任务描述</label>
                <textarea 
                  v-model="taskForm.description" 
                  rows="4" 
                  placeholder="请输入任务描述..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  required
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">截止时间</label>
                <input 
                  v-model="taskForm.deadline" 
                  type="datetime-local"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  required
                >
              </div>

              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeTaskForm"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                  {{ editingTask ? '更新任务' : '创建任务' }}
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 添加/编辑打卡项目弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCheckinModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-lg mx-4">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">{{ currentCheckinItem ? '编辑打卡项目' : '添加打卡项目' }}</h3>
              <button 
                @click="closeCheckinModal"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitCheckinItem" class="space-y-6">
              <!-- 项目名称 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目名称 *</label>
                <input 
                  v-model="checkinForm.title" 
                  type="text" 
                  required
                  placeholder="例如：早起打卡、运动打卡等"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                >
              </div>

              <!-- 项目描述 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">项目描述</label>
                <textarea 
                  v-model="checkinForm.description" 
                  rows="3"
                  placeholder="简单描述一下这个打卡项目..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                ></textarea>
              </div>

              <!-- 打卡频率 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">打卡频率 *</label>
                <div class="grid grid-cols-5 gap-2">
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="checkinForm.frequency === 'daily' ? 'border-blue-500 bg-blue-50' : 'border-gray-300 hover:border-blue-300'">
                    <input type="radio" v-model="checkinForm.frequency" value="daily" class="hidden">
                    <span class="text-sm font-medium" :class="checkinForm.frequency === 'daily' ? 'text-blue-700' : 'text-gray-700'">日</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="checkinForm.frequency === 'weekly' ? 'border-green-500 bg-green-50' : 'border-gray-300 hover:border-green-300'">
                    <input type="radio" v-model="checkinForm.frequency" value="weekly" class="hidden">
                    <span class="text-sm font-medium" :class="checkinForm.frequency === 'weekly' ? 'text-green-700' : 'text-gray-700'">周</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="checkinForm.frequency === 'monthly' ? 'border-purple-500 bg-purple-50' : 'border-gray-300 hover:border-purple-300'">
                    <input type="radio" v-model="checkinForm.frequency" value="monthly" class="hidden">
                    <span class="text-sm font-medium" :class="checkinForm.frequency === 'monthly' ? 'text-purple-700' : 'text-gray-700'">月</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="checkinForm.frequency === 'quarterly' ? 'border-orange-500 bg-orange-50' : 'border-gray-300 hover:border-orange-300'">
                    <input type="radio" v-model="checkinForm.frequency" value="quarterly" class="hidden">
                    <span class="text-sm font-medium" :class="checkinForm.frequency === 'quarterly' ? 'text-orange-700' : 'text-gray-700'">季</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="checkinForm.frequency === 'yearly' ? 'border-red-500 bg-red-50' : 'border-gray-300 hover:border-red-300'">
                    <input type="radio" v-model="checkinForm.frequency" value="yearly" class="hidden">
                    <span class="text-sm font-medium" :class="checkinForm.frequency === 'yearly' ? 'text-red-700' : 'text-gray-700'">年</span>
                  </label>
                </div>
              </div>

              <!-- 状态 -->
              <div v-if="currentCheckinItem">
                <label class="block text-sm font-medium text-gray-700 mb-2">状态</label>
                <select 
                  v-model="checkinForm.status"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                >
                  <option value="active">启用</option>
                  <option value="inactive">停用</option>
                </select>
              </div>

              <!-- 操作按钮 -->
              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeCheckinModal"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700"
                >
                  {{ currentCheckinItem ? '保存修改' : '添加项目' }}
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch, onMounted, onUnmounted } from 'vue'
import request from '@/api/request'
import { getAllHonors, createHonor, type Honor } from '@/api/honor'
import { 
  getCheckinItemList, 
  createCheckinItem, 
  updateCheckinItem, 
  deleteCheckinItem as deleteCheckinItemAPI,
  type CheckinItem 
} from '@/api/checkin'

// 日常决策仪表板 - 专注于项目管理和决策制定

// 响应式数据
const projects = ref<any[]>([])
const tasks = ref<any[]>([])
const honors = ref<Honor[]>([]) // 荣誉战绩列表
const checkinItems = ref<CheckinItem[]>([]) // 打卡项目列表

// ========== 天气信息相关 ==========
const isWeatherSectionExpanded = ref(true) // 天气卡片折叠状态
const selectedCity = ref('Shanghai') // 默认选择上海
const weatherLoading = ref(false) // 天气加载状态
const weatherError = ref('') // 天气错误信息
const currentWeather = ref({
  city: '',
  temp: '',
  feelsLike: '',
  maxTemp: '',
  minTemp: '',
  condition: '',
  emoji: '',
  windSpeed: '',
  humidity: '',
  visibility: '',
  pressure: '',
  updateTime: ''
})

// 未来天气预报（3天）
const forecastWeather = ref<Array<{
  date: string
  dayOfWeek: string
  maxTemp: string
  minTemp: string
  condition: string
  emoji: string
}>>([])

// 加载数据
const loadData = async () => {
  try {
    // 加载项目
    const projectRes = await request.get('/project/list')
    if (projectRes.code === 200) {
      projects.value = projectRes.data || []
    }
    
    // 加载任务
    const taskRes = await request.get('/task/list')
    if (taskRes.code === 200) {
      tasks.value = taskRes.data || []
    }

    // 加载荣誉战绩
    const honorsRes = await getAllHonors()
    if (honorsRes.code === 200) {
      honors.value = honorsRes.data || []
    }

    // 加载打卡项目
    const checkinRes = await getCheckinItemList()
    if (checkinRes.code === 200) {
      checkinItems.value = checkinRes.data || []
    }
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

// 定时器引用
let statusUpdateTimer: number | null = null

// 翻页和排序相关
const currentPage = ref(1)
const pageSize = 4

// 响应式数据
const showCreateProject = ref(false)
const showCreateTask = ref(false)

// 编辑状态
const editingProject = ref<any>(null)
const editingTask = ref<any>(null)

// 搜索和筛选
const taskSearchQuery = ref('')
const taskFilterStatus = ref('')
const projectSearchQuery = ref('')
const projectFilterStatus = ref('')
const projectFilterCategory = ref('')

// ========== 天气信息功能 ==========

// 切换天气卡片折叠状态
const toggleWeatherSection = () => {
  isWeatherSectionExpanded.value = !isWeatherSectionExpanded.value
  if (isWeatherSectionExpanded.value && !currentWeather.value.temp) {
    loadWeather()
  }
}

// 获取天气图标emoji
const getWeatherEmoji = (condition: string): string => {
  const lowerCondition = condition.toLowerCase()
  if (lowerCondition.includes('sunny') || lowerCondition.includes('clear')) return '☀️'
  if (lowerCondition.includes('cloud')) return '☁️'
  if (lowerCondition.includes('rain') || lowerCondition.includes('shower')) return '🌧️'
  if (lowerCondition.includes('snow')) return '❄️'
  if (lowerCondition.includes('thunder') || lowerCondition.includes('storm')) return '⛈️'
  if (lowerCondition.includes('mist') || lowerCondition.includes('fog')) return '🌫️'
  if (lowerCondition.includes('overcast')) return '☁️'
  if (lowerCondition.includes('partly')) return '⛅'
  return '🌤️'
}

// 将英文天气状况翻译为中文
const translateWeatherCondition = (englishCondition: string): string => {
  if (!englishCondition) return '未知'
  const lowerCondition = englishCondition.toLowerCase().trim()
  
  // 晴天
  if (lowerCondition.includes('sunny') || lowerCondition === 'clear') return '晴'
  if (lowerCondition.includes('clear sky')) return '晴空'
  
  // 多云（注意顺序：先匹配长的，再匹配短的）
  if (lowerCondition.includes('partly cloudy')) return '多云'
  if (lowerCondition.includes('partly cloud')) return '多云'
  if (lowerCondition === 'cloudy' || lowerCondition.includes('cloud')) return '阴'
  if (lowerCondition === 'overcast') return '阴天'
  
  // 雨
  if (lowerCondition === 'light rain') return '小雨'
  if (lowerCondition === 'moderate rain') return '中雨'
  if (lowerCondition === 'heavy rain') return '大雨'
  if (lowerCondition.includes('rain shower') || lowerCondition.includes('shower')) return '阵雨'
  if (lowerCondition.includes('drizzle')) return '毛毛雨'
  if (lowerCondition.includes('rain')) return '雨'
  
  // 雪
  if (lowerCondition === 'light snow') return '小雪'
  if (lowerCondition === 'moderate snow') return '中雪'
  if (lowerCondition === 'heavy snow') return '大雪'
  if (lowerCondition.includes('snow shower')) return '阵雪'
  if (lowerCondition.includes('snow')) return '雪'
  
  // 雷暴
  if (lowerCondition.includes('thunder')) return '雷暴'
  if (lowerCondition.includes('storm')) return '暴风雨'
  
  // 雾霾
  if (lowerCondition === 'mist' || lowerCondition.includes('mist')) return '薄雾'
  if (lowerCondition === 'fog' || lowerCondition.includes('fog')) return '雾'
  if (lowerCondition.includes('haze')) return '霾'
  
  // 其他
  if (lowerCondition.includes('windy')) return '大风'
  if (lowerCondition.includes('dust')) return '浮尘'
  if (lowerCondition.includes('sand')) return '沙尘'
  
  // 如果没有匹配到，返回未知
  console.warn(`未知的天气状况: ${englishCondition}`)
  return '未知'
}

// 加载天气信息
const loadWeather = async () => {
  weatherLoading.value = true
  weatherError.value = ''
  
  try {
    // 使用 wttr.in API（免费，无需密钥）
    // 格式参数说明：%C-天气状况 %t-温度 %f-体感温度 %w-风速 %h-湿度 %V-能见度 %P-气压
    const response = await fetch(`https://wttr.in/${selectedCity.value}?format=j1`)
    
    if (!response.ok) {
      throw new Error('获取天气信息失败')
    }
    
    const data = await response.json()
    
    // 解析当前天气数据
    const current = data.current_condition?.[0]
    const today = data.weather?.[0]
    
    if (!current) {
      throw new Error('天气数据格式错误')
    }
    
    // 获取城市名称（中文）
    const cityNameMap: Record<string, string> = {
      'Shanghai': '上海',
      'Beijing': '北京',
      'Guangzhou': '广州',
      'Shenzhen': '深圳',
      'Hangzhou': '杭州',
      'Chengdu': '成都',
      'Wuhan': '武汉',
      'Nanjing': '南京'
    }
    
    // 获取英文天气描述并翻译为中文
    const englishCondition = current.weatherDesc?.[0]?.value || ''
    const chineseCondition = translateWeatherCondition(englishCondition)
    
    currentWeather.value = {
      city: cityNameMap[selectedCity.value] || selectedCity.value,
      temp: current.temp_C || '',
      feelsLike: current.FeelsLikeC || '',
      maxTemp: today?.maxtempC || '',
      minTemp: today?.mintempC || '',
      condition: chineseCondition || '未知',
      emoji: getWeatherEmoji(englishCondition),
      windSpeed: `${current.windspeedKmph} km/h`,
      humidity: current.humidity || '0',
      visibility: `${current.visibility} km`,
      pressure: `${current.pressure} hPa`,
      updateTime: new Date().toLocaleString('zh-CN', { 
        year: 'numeric',
        month: '2-digit', 
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      })
    }
    
    // 解析未来天气预报（跳过今天，显示接下来的几天）
    const weatherForecast = data.weather || []
    
    // 从索引1开始（跳过今天），获取所有可用的未来天数预报
    const forecastDays = weatherForecast.slice(1)
    
    forecastWeather.value = forecastDays.map((day: any) => {
      const date = new Date(day.date)
      const dayOfWeekList = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
      const dayOfWeek = dayOfWeekList[date.getDay()]
      
      // 使用中午时段的天气作为当天代表
      const forecastCondition = day.hourly?.[4]?.weatherDesc?.[0]?.value || day.hourly?.[0]?.weatherDesc?.[0]?.value || ''
      
      return {
        date: `${date.getMonth() + 1}/${date.getDate()}`,
        dayOfWeek: dayOfWeek,
        maxTemp: day.maxtempC || '',
        minTemp: day.mintempC || '',
        condition: translateWeatherCondition(forecastCondition),
        emoji: getWeatherEmoji(forecastCondition)
      }
    })
    
  } catch (error: any) {
    console.error('加载天气信息失败:', error)
    weatherError.value = error.message || '无法获取天气信息，请稍后重试'
    
    // 如果 wttr.in 访问失败，可以在这里添加备用 API
    // 例如使用高德天气 API（需要申请 key）：
    // const apiKey = 'YOUR_AMAP_KEY'
    // const response = await fetch(`https://restapi.amap.com/v3/weather/weatherInfo?city=310000&key=${apiKey}`)
    
  } finally {
    weatherLoading.value = false
  }
}

// ========== 打卡管理相关 ==========

// 打卡管理折叠状态
const isCheckinSectionExpanded = ref(false)

// 切换打卡管理折叠状态
const toggleCheckinSection = () => {
  isCheckinSectionExpanded.value = !isCheckinSectionExpanded.value
}

// 打卡项目表单
const showCheckinModal = ref(false)
const currentCheckinItem = ref<CheckinItem | null>(null)
const checkinForm = reactive({
  title: '',
  description: '',
  frequency: 'daily' as 'daily' | 'weekly' | 'monthly' | 'quarterly' | 'yearly',
  status: 'active'
})

// 打开创建打卡项目弹窗
const openCreateCheckinModal = () => {
  currentCheckinItem.value = null
  checkinForm.title = ''
  checkinForm.description = ''
  checkinForm.frequency = 'daily'
  checkinForm.status = 'active'
  showCheckinModal.value = true
}

// 编辑打卡项目
const editCheckinItem = (item: CheckinItem) => {
  currentCheckinItem.value = item
  checkinForm.title = item.title
  checkinForm.description = item.description || ''
  checkinForm.frequency = item.frequency
  checkinForm.status = item.status || 'active'
  showCheckinModal.value = true
}

// 关闭打卡项目弹窗
const closeCheckinModal = () => {
  showCheckinModal.value = false
  currentCheckinItem.value = null
}

// 提交打卡项目
const submitCheckinItem = async () => {
  try {
    if (currentCheckinItem.value) {
      // 更新
      const response = await updateCheckinItem(currentCheckinItem.value.id!, checkinForm as CheckinItem)
      if (response.code === 200) {
        await loadData()
        closeCheckinModal()
        alert('打卡项目已更新！')
      } else {
        alert('更新失败：' + (response.message || '未知错误'))
      }
    } else {
      // 创建
      const response = await createCheckinItem(checkinForm as CheckinItem)
      if (response.code === 200) {
        await loadData()
        closeCheckinModal()
        alert('打卡项目已创建！')
      } else {
        alert('创建失败：' + (response.message || '未知错误'))
      }
    }
  } catch (error) {
    console.error('保存打卡项目失败:', error)
    alert('保存失败，请稍后重试')
  }
}

// 删除打卡项目
const deleteCheckinItem = async (id: number) => {
  if (!confirm('确定要删除这个打卡项目吗？此操作不可撤销。')) return
  
  try {
    const response = await deleteCheckinItemAPI(id)
    if (response.code === 200) {
      await loadData()
      alert('打卡项目已删除！')
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除打卡项目失败:', error)
    alert('删除失败，请稍后重试')
  }
}

// 获取频率文本
const getFrequencyText = (frequency: string) => {
  const map: Record<string, string> = {
    'daily': '每日',
    'weekly': '每周',
    'monthly': '每月',
    'quarterly': '每季',
    'yearly': '每年'
  }
  return map[frequency] || frequency
}

// 获取频率样式
const getFrequencyClass = (frequency: string) => {
  const map: Record<string, string> = {
    'daily': 'bg-blue-100 text-blue-700',
    'weekly': 'bg-green-100 text-green-700',
    'monthly': 'bg-purple-100 text-purple-700',
    'quarterly': 'bg-orange-100 text-orange-700',
    'yearly': 'bg-red-100 text-red-700'
  }
  return map[frequency] || 'bg-gray-100 text-gray-700'
}

// ========== 日历事件相关 ==========

// 日历折叠状态
const isCalendarSectionExpanded = ref(true)

// 切换日历折叠状态
const toggleCalendarSection = () => {
  isCalendarSectionExpanded.value = !isCalendarSectionExpanded.value
  if (isCalendarSectionExpanded.value) {
    loadCalendarEvents()
    loadHolidays()
  }
}

// 日历事件
const calendarEvents = ref<any[]>([])
const showCreateEvent = ref(false)
const currentEvent = ref<any>(null)
const holidays = ref<any[]>([])

// 即将到来的节假日（未来的，只显示法定假日，不显示补班）
const upcomingHolidays = computed(() => {
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  return holidays.value.filter(h => {
    const hDate = new Date(h.date)
    return h.isHoliday && hDate >= today // 只显示法定假日
  }).slice(0, 6)
})

// 当前显示的年月
const currentDate = ref(new Date())
const currentYear = computed(() => currentDate.value.getFullYear())
const currentMonth = computed(() => currentDate.value.getMonth() + 1)

// 星期表头
const weekDays = ['日', '一', '二', '三', '四', '五', '六']

// 事件表单
const eventForm = reactive({
  eventTitle: '',
  eventDate: '',
  eventType: 'custom', // 事件类型：custom-自定义, holiday-节假日
  repeatType: 'once',
  description: '',
  color: '#3b82f6',
  showCountdown: false // 是否显示倒计时
})

// 颜色选项
const colorOptions = ['#3b82f6', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6', '#ec4899', '#14b8a6', '#f97316']

// 生成日历网格数据
const calendarDays = computed(() => {
  const year = currentDate.value.getFullYear()
  const month = currentDate.value.getMonth()
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  
  // 显式依赖 holidays 和 calendarEvents，确保它们变化时重新计算
  const holidaysCount = holidays.value.length
  const eventsCount = calendarEvents.value.length
  
  // 当月第一天
  const firstDay = new Date(year, month, 1)
  const firstDayOfWeek = firstDay.getDay() // 0-6, 0是周日
  
  // 当月最后一天
  const lastDay = new Date(year, month + 1, 0)
  const daysInMonth = lastDay.getDate()
  
  // 上个月最后几天
  const prevMonthDays = firstDayOfWeek
  const prevMonth = month === 0 ? 11 : month - 1
  const prevMonthYear = month === 0 ? year - 1 : year
  const prevMonthLastDay = new Date(prevMonthYear, prevMonth + 1, 0).getDate()
  
  const days: any[] = []
  
  // 上个月的日期（灰色显示）
  for (let i = prevMonthDays - 1; i >= 0; i--) {
    const day = prevMonthLastDay - i
    const date = new Date(prevMonthYear, prevMonth, day)
    days.push({
      day,
      date,
      isCurrentMonth: false,
      isToday: false,
      holiday: getHolidayForDate(date),
      events: getEventsForDate(date)
    })
  }
  
  // 当月的日期
  for (let day = 1; day <= daysInMonth; day++) {
    const date = new Date(year, month, day)
    const isToday = date.getTime() === today.getTime()
    days.push({
      day,
      date,
      isCurrentMonth: true,
      isToday,
      holiday: getHolidayForDate(date),
      events: getEventsForDate(date)
    })
  }
  
  // 下个月的日期（灰色显示）
  const remainingDays = 42 - days.length // 6行 x 7列 = 42格
  const nextMonth = month === 11 ? 0 : month + 1
  const nextMonthYear = month === 11 ? year + 1 : year
  for (let day = 1; day <= remainingDays; day++) {
    const date = new Date(nextMonthYear, nextMonth, day)
    days.push({
      day,
      date,
      isCurrentMonth: false,
      isToday: false,
      holiday: getHolidayForDate(date),
      events: getEventsForDate(date)
    })
  }
  
  return days
})

// 获取指定日期的节假日
const getHolidayForDate = (date: Date) => {
  const dateStr = formatDateToString(date)
  return holidays.value.find(h => h.date === dateStr)
}

// 获取指定日期的事件
const getEventsForDate = (date: Date) => {
  const dateStr = formatDateToString(date)
  
  return calendarEvents.value.filter(event => {
    const eventDate = new Date(event.eventDate)
    
    // 单次事件：日期完全匹配
    if (event.repeatType === 'once') {
      return formatDateToString(eventDate) === dateStr
    }
    
    // 每天重复：所有日期都显示
    if (event.repeatType === 'daily') {
      return new Date(eventDate) <= date
    }
    
    // 每月重复：日期相同
    if (event.repeatType === 'monthly') {
      return eventDate.getDate() === date.getDate() && new Date(eventDate) <= date
    }
    
    // 每年重复：月日相同
    if (event.repeatType === 'yearly') {
      return eventDate.getMonth() === date.getMonth() && 
             eventDate.getDate() === date.getDate() &&
             eventDate.getFullYear() <= date.getFullYear()
    }
    
    return false
  })
}

// 格式化日期为字符串
const formatDateToString = (date: Date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

// 上一月
const previousMonth = () => {
  const newDate = new Date(currentDate.value)
  newDate.setMonth(newDate.getMonth() - 1)
  currentDate.value = newDate
}

// 下一月
const nextMonth = () => {
  const newDate = new Date(currentDate.value)
  newDate.setMonth(newDate.getMonth() + 1)
  currentDate.value = newDate
}

// 回到今天
const goToToday = () => {
  currentDate.value = new Date()
}

// 选择日期（快速添加事件）
const selectDate = (date: Date) => {
  eventForm.eventDate = formatDateToString(date)
  showCreateEvent.value = true
}

// 显示某天的所有事件
const showDayEvents = (day: any) => {
  if (day.events.length > 0) {
    const eventList = day.events.map((e: any) => `• ${e.eventTitle}`).join('\n')
    alert(`${day.date.toLocaleDateString('zh-CN')}\n\n${eventList}`)
  }
}

// 加载日历事件
const loadCalendarEvents = async () => {
  try {
    const response = await request.get('/calendar/list')
    if (response.code === 200) {
      calendarEvents.value = response.data || []
    }
  } catch (error) {
    console.error('加载日历事件失败:', error)
  }
}

// 加载节假日
const loadHolidays = async () => {
  try {
    // 直接加载2025年的节假日
    const response = await fetch('https://timor.tech/api/holiday/year/2025')
    const data = await response.json()
    
    if (data.code === 0) {
      
      // 扩展节假日：不仅包括节日当天，还包括整个假期
      const expandedHolidays: any[] = []
      
      Object.entries(data.holiday || {}).forEach(([date, info]: [string, any]) => {
        // 修复日期格式：API返回的是 MM-DD，需要加上年份
        const fullDate = `2025-${date}`
        const baseDate = new Date(fullDate)
        
        // 限制连休天数（防止错误数据，最多10天）
        let restDays = info.rest || 1
        if (restDays > 10) {
          console.warn(`⚠️ ${info.name} rest=${restDays}异常，限制为1天`)
          restDays = 1
        }
        
        // 添加节日/补班当天
        expandedHolidays.push({
          date: fullDate,
          name: info.name,
          type: info.holiday ? '法定假日' : '工作日',
          isHoliday: info.holiday,
          wage: info.wage
        })
        
        // 如果是法定假日且有连休，添加后续假期天数
        if (info.holiday && restDays > 1) {
          for (let i = 1; i < restDays; i++) {
            const nextDay = new Date(baseDate)
            nextDay.setDate(baseDate.getDate() + i)
            const nextDateStr = nextDay.toISOString().split('T')[0]
            
            expandedHolidays.push({
              date: nextDateStr,
              name: `${info.name}假期`,
              type: '法定假日',
              isHoliday: true,
              wage: info.wage
            })
          }
        }
      })
      
      holidays.value = expandedHolidays.sort((a, b) => 
        new Date(a.date).getTime() - new Date(b.date).getTime()
      )
      
      const oct = holidays.value.filter(h => h.date && h.date.includes('2025-10') && h.isHoliday)
      console.log(`✅ 2025年节假日加载成功: 共${holidays.value.length}个 | 10月法定假日${oct.length}天`)
    } else {
      console.warn('⚠️ 节假日API返回错误:', data)
      holidays.value = []
    }
  } catch (error) {
    console.error('❌ 加载节假日失败:', error)
    holidays.value = []
  }
}

// 提交事件
const submitEvent = async () => {
  try {
    const isEdit = currentEvent.value !== null
    const url = isEdit ? `/calendar/${currentEvent.value.id}` : '/calendar'
    const method = isEdit ? 'put' : 'post'
    
    const response = await request[method](url, eventForm)
    
    if (response.code === 200) {
      alert(isEdit ? '✅ 日期更新成功' : '✅ 日期添加成功')
      closeEventModal()
      await loadCalendarEvents()
    } else {
      alert((isEdit ? '更新' : '添加') + '失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error((currentEvent.value ? '更新' : '添加') + '事件失败:', error)
    alert((currentEvent.value ? '更新' : '添加') + '失败，请稍后重试')
  }
}

// 关闭事件弹窗
const closeEventModal = () => {
  showCreateEvent.value = false
  currentEvent.value = null
  eventForm.eventTitle = ''
  eventForm.eventDate = ''
  eventForm.eventType = 'custom'
  eventForm.repeatType = 'once'
  eventForm.description = ''
  eventForm.color = '#3b82f6'
  eventForm.showCountdown = false
}

// 打开创建事件模态框（设置默认日期为当前日期）
const openCreateEventModal = () => {
  const today = new Date()
  eventForm.eventDate = formatDateToString(today)
  showCreateEvent.value = true
}

// 编辑事件
const editEvent = (event: any) => {
  eventForm.eventTitle = event.eventTitle
  eventForm.eventDate = event.eventDate.split('T')[0] // 转换为日期格式
  eventForm.eventType = event.eventType || 'custom'
  eventForm.repeatType = event.repeatType
  eventForm.description = event.description || ''
  eventForm.color = event.color || '#3b82f6'
  eventForm.showCountdown = event.showCountdown || false
  currentEvent.value = event
  showCreateEvent.value = true
}

// 删除事件
const deleteEvent = async (id: number) => {
  if (!confirm('确定要删除这个日期吗？')) return
  
  try {
    const response = await request.delete(`/calendar/${id}`)
    if (response.code === 200) {
      alert('✅ 日期已删除')
      await loadCalendarEvents()
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除事件失败:', error)
    alert('删除失败，请稍后重试')
  }
}

// 快速删除事件（日历格子中）
const deleteEventQuick = async (id: number) => {
  if (!confirm('确定要删除这个事件吗？')) return
  
  try {
    const response = await request.delete(`/calendar/${id}`)
    if (response.code === 200) {
      await loadCalendarEvents()
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除事件失败:', error)
    alert('删除失败，请稍后重试')
  }
}

// 格式化事件日期
const formatEventDate = (date: any) => {
  if (!date) return ''
  const d = new Date(date)
  return d.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}

// 格式化节假日日期
const formatHolidayDate = (date: string) => {
  const d = new Date(date)
  return d.toLocaleDateString('zh-CN', { month: 'long', day: 'numeric' })
}

// 获取重复类型文本
const getRepeatTypeText = (type: string) => {
  const texts: Record<string, string> = {
    once: '单次',
    daily: '每天',
    monthly: '每月',
    yearly: '每年'
  }
  return texts[type] || type
}

// 项目展开状态
const projectExpanded = ref<number[]>([])

// 初始化项目展开状态
onMounted(async () => {
  await loadData()
  projectExpanded.value = projects.value.map(project => project.id)
  await loadDecisionIssues()
  await loadCalendarEvents()
  await loadHolidays()
  
  // 加载天气信息（默认上海）
  loadWeather()
  
  // 加载完成后验证
  setTimeout(() => {
    const oct = holidays.value.filter(h => h.date && h.date.includes('2025-10') && h.isHoliday)
    console.log(`✅ 初始化完成 - 节假日:${holidays.value.length}个 | 10月法定假日:${oct.length}天`)
  }, 200)
})

// 项目表单
const projectForm = reactive({
  name: '',
  description: '',
  status: 'planning',
  category: ''
})

// 任务表单
const taskForm = reactive({
  projectId: '',
  name: '',
  description: '',
  deadline: '',
  status: 'pending'
})

// 项目分页相关
const projectPageSize = 5
const projectCurrentPage = ref(1)

// 项目分页计算属性
const projectTotalPages = computed(() => Math.ceil(filteredProjects.value.length / projectPageSize))

const paginatedProjects = computed(() => {
  const start = (projectCurrentPage.value - 1) * projectPageSize
  const end = start + projectPageSize
  return filteredProjects.value.slice(start, end)
})

// 项目翻页方法
const prevProjectPage = () => {
  if (projectCurrentPage.value > 1) {
    projectCurrentPage.value--
  }
}

const nextProjectPage = () => {
  if (projectCurrentPage.value < projectTotalPages.value) {
    projectCurrentPage.value++
  }
}

// 监听项目筛选变化，重置到第一页
watch([projectSearchQuery, projectFilterCategory, projectFilterStatus], () => {
  projectCurrentPage.value = 1
})

// 获取项目任务
const getProjectTasks = (projectId: number) => {
  return tasks.value.filter(task => task.projectId === projectId)
}

// 获取过滤后的项目任务
const getFilteredProjectTasks = (projectId: number) => {
  let projectTasks = getProjectTasks(projectId)
  
  // 按状态筛选
  if (taskFilterStatus.value) {
    projectTasks = projectTasks.filter(task => task.status === taskFilterStatus.value)
  }
  
  // 按搜索关键词筛选
  if (taskSearchQuery.value) {
    const query = taskSearchQuery.value.toLowerCase()
    projectTasks = projectTasks.filter(task => 
      task.name.toLowerCase().includes(query) ||
      task.description.toLowerCase().includes(query)
    )
  }
  
  return projectTasks
}

// 获取项目名称
const getProjectName = (projectId: number) => {
  const project = projects.value.find(p => p.id === projectId)
  return project ? project.name : '未知项目'
}

// 获取分类样式
const getCategoryClass = (category: string) => {
  const classes = {
    defense: 'bg-red-500',
    construction: 'bg-blue-500',
    diplomatic: 'bg-green-500'
  }
  return classes[category as keyof typeof classes] || 'bg-gray-500'
}

// 获取分类图标
const getCategoryIcon = (category: string) => {
  const icons = {
    defense: 'fa-solid fa-crosshairs',
    construction: 'fa-solid fa-building',
    diplomatic: 'fa-solid fa-handshake'
  }
  return icons[category as keyof typeof icons] || 'fa-solid fa-folder'
}

// 获取分类文本
const getCategoryText = (category: string) => {
  const texts = {
    defense: '战争行动',
    construction: '工程建设',
    diplomatic: '外交行动'
  }
  return texts[category as keyof typeof texts] || '未分类'
}

// 获取项目状态样式
const getProjectStatusClass = (status: string) => {
  const classes = {
    planning: 'bg-yellow-100 text-yellow-600',
    active: 'bg-green-100 text-green-600',
    completed: 'bg-blue-100 text-blue-600',
    paused: 'bg-gray-100 text-gray-600'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

// 获取项目状态文本
const getProjectStatusText = (status: string) => {
  const texts = {
    planning: '规划中',
    active: '进行中',
    completed: '已完成',
    paused: '暂停'
  }
  return texts[status as keyof typeof texts] || '未知'
}

// 获取任务状态样式
const getTaskStatusClass = (status: string) => {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-600',
    'in-progress': 'bg-blue-100 text-blue-600',
    completed: 'bg-green-100 text-green-600'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

// 获取任务状态文本
const getTaskStatusText = (status: string) => {
  const texts = {
    pending: '待开始',
    'in-progress': '进行中',
    completed: '已完成'
  }
  return texts[status as keyof typeof texts] || '未知'
}

// 编辑项目
const editProject = (project: any) => {
  editingProject.value = project
  projectForm.name = project.name
  projectForm.description = project.description
  projectForm.status = project.status
  projectForm.category = project.category
  showCreateProject.value = true
}

// 删除项目
const deleteProject = async (id: number) => {
  if (confirm('确定要删除这个项目吗？相关的任务也会被删除。')) {
    try {
      const res = await request.delete(`/project/${id}`)
      if (res.code === 200) {
        alert('项目已删除！')
        loadData()
      }
    } catch (error) {
      console.error('删除项目失败:', error)
      alert('删除失败，请重试')
    }
  }
}

// 提交项目
const submitProject = async () => {
  try {
    const projectData = {
      name: projectForm.name,
      description: projectForm.description,
      status: projectForm.status,
      category: projectForm.category
    }
    
    if (editingProject.value) {
      // 更新项目
      const res = await request.put('/project', { ...projectData, id: editingProject.value.id })
      if (res.code === 200) {
        alert('项目已更新！')
        loadData()
      }
    } else {
      // 创建新项目
      const res = await request.post('/project', projectData)
      if (res.code === 200) {
        alert('项目已创建！')
        loadData()
      }
    }
    closeProjectForm()
  } catch (error) {
    console.error('保存项目失败:', error)
    alert('操作失败，请重试')
  }
}

// 关闭项目表单
const closeProjectForm = () => {
  showCreateProject.value = false
  editingProject.value = null
  projectForm.name = ''
  projectForm.description = ''
  projectForm.status = 'planning'
  projectForm.category = ''
}

// 编辑任务
const editTask = (task: any) => {
  editingTask.value = task
  taskForm.projectId = task.projectId.toString()
  taskForm.name = task.name
  taskForm.description = task.description
  // 转换日期格式：从后端格式 (yyyy-MM-dd HH:mm:ss) 到 datetime-local (yyyy-MM-ddTHH:mm)
  if (task.deadline) {
    // 将 "2025-10-07 14:30:00" 转换为 "2025-10-07T14:30"
    taskForm.deadline = task.deadline.substring(0, 16).replace(' ', 'T')
  } else {
    taskForm.deadline = ''
  }
  // 不设置状态，状态由执行页面管理
  showCreateTask.value = true
}

// 删除任务
const deleteTask = async (id: number) => {
  if (confirm('确定要删除这个任务吗？')) {
    try {
      const res = await request.delete(`/task/${id}`)
      if (res.code === 200) {
        alert('任务已删除！')
        loadData()
      }
    } catch (error) {
      console.error('删除任务失败:', error)
      alert('删除失败，请重试')
    }
  }
}

// 提交任务
const submitTask = async () => {
  try {
    // 转换日期格式：从 datetime-local (yyyy-MM-ddTHH:mm) 到后端格式 (yyyy-MM-dd HH:mm:ss)
    let formattedDeadline = taskForm.deadline
    if (formattedDeadline) {
      // 将 "2025-10-07T14:30" 转换为 "2025-10-07 14:30:00"
      formattedDeadline = formattedDeadline.replace('T', ' ') + ':00'
    }
    
    const taskData = {
      projectId: parseInt(taskForm.projectId),
      name: taskForm.name,
      description: taskForm.description,
      deadline: formattedDeadline,
      status: taskForm.status || 'pending'
    }
    
    if (editingTask.value) {
      // 更新任务
      const res = await request.put('/task', { ...taskData, id: editingTask.value.id })
      if (res.code === 200) {
        alert('任务已更新！')
        loadData()
      }
    } else {
      // 创建新任务
      const res = await request.post('/task', taskData)
      if (res.code === 200) {
        alert('任务已创建！')
        loadData()
      }
    }
    closeTaskForm()
  } catch (error) {
    console.error('保存任务失败:', error)
    alert('操作失败，请重试')
  }
}

// 关闭任务表单
const closeTaskForm = () => {
  showCreateTask.value = false
  editingTask.value = null
  taskForm.projectId = ''
  taskForm.name = ''
  taskForm.description = ''
  taskForm.deadline = ''
  taskForm.status = 'pending'
}

// 添加任务到项目
const addTaskToProject = (projectId: number) => {
  taskForm.projectId = projectId.toString()
  showCreateTask.value = true
}

// 切换项目展开状态
const toggleProjectExpanded = (projectId: number) => {
  if (projectExpanded.value.includes(projectId)) {
    projectExpanded.value = projectExpanded.value.filter(id => id !== projectId)
  } else {
    projectExpanded.value.push(projectId)
  }
}

// 计算过滤后的项目列表
const filteredProjects = computed(() => {
  let filtered = projects.value
  
  // 按名称搜索
  if (projectSearchQuery.value) {
    const query = projectSearchQuery.value.toLowerCase()
    filtered = filtered.filter(project => 
      project.name.toLowerCase().includes(query) ||
      project.description.toLowerCase().includes(query)
    )
  }
  
  // 按状态筛选
  if (projectFilterStatus.value) {
    filtered = filtered.filter(project => project.status === projectFilterStatus.value)
  }
  
  // 按分类筛选
  if (projectFilterCategory.value) {
    filtered = filtered.filter(project => project.category === projectFilterCategory.value)
  }
  
  return filtered
})

// 检查项目是否已加入荣誉战绩
const isProjectInHonors = (projectId: number): boolean => {
  return honors.value.some(honor => honor.projectId === projectId)
}

// 格式化日期为 yyyy-MM-dd HH:mm:ss 格式
const formatDateTimeForBackend = (date: Date): string => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

// 将项目加入荣誉战绩
const addProjectToHonors = async (project: any) => {
  if (confirm(`确定要将项目「${project.name}」加入荣誉战绩吗？`)) {
    try {
      const newHonor: Honor = {
        title: project.name,
        description: project.description || '',
        category: project.category,
        achievedTime: formatDateTimeForBackend(new Date()), // 格式化为后端期望的格式
        projectId: project.id,
        icon: 'fa-solid fa-trophy'
      }
      
      const response = await createHonor(newHonor)
      if (response.code === 200) {
        // 重新加载荣誉战绩列表
        await loadData()
        alert('项目已成功加入荣誉战绩！')
      } else {
        alert('加入荣誉战绩失败：' + (response.message || '未知错误'))
      }
    } catch (error) {
      alert('加入荣誉战绩失败，请重试')
      console.error('加入荣誉战绩失败：', error)
    }
  }
}

// 组件卸载时清理定时器
onUnmounted(() => {
  if (statusUpdateTimer) {
    clearInterval(statusUpdateTimer)
  }
})

// ========== 问题处理相关 ==========

// 折叠状态
const isProjectSectionExpanded = ref(true)
const isIssueSectionExpanded = ref(true)

// 切换折叠状态
const toggleProjectSection = () => {
  isProjectSectionExpanded.value = !isProjectSectionExpanded.value
}

const toggleIssueSection = () => {
  isIssueSectionExpanded.value = !isIssueSectionExpanded.value
  if (isIssueSectionExpanded.value) {
    loadDecisionIssues()
  }
}

// 问题管理
const decisionIssues = ref<any[]>([])
const showDecisionModal = ref(false)
const currentDecisionIssue = ref<any>(null)

// 问题筛选
const issueSearchQuery = ref('')
const issueFilterProject = ref('')
const issueFilterStatus = ref('')
const issueFilterPriority = ref('')

// 决策表单
const decisionForm = reactive({
  solution: ''
})

// 加载问题列表
const loadDecisionIssues = async () => {
  try {
    const response = await request.get('/project/issue/list')
    if (response.code === 200) {
      decisionIssues.value = response.data || []
    }
  } catch (error) {
    console.error('加载问题列表失败:', error)
  }
}

// 待处理问题数量
const pendingIssuesCount = computed(() => {
  return decisionIssues.value.filter(issue => issue.status === 'open' || issue.status === 'in_progress').length
})

// 已解决问题数量
const resolvedIssuesCount = computed(() => {
  return decisionIssues.value.filter(issue => issue.status === 'resolved').length
})

// 过滤后的问题列表
const filteredDecisionIssues = computed(() => {
  return decisionIssues.value.filter(issue => {
    const matchSearch = !issueSearchQuery.value || 
      issue.issueTitle.toLowerCase().includes(issueSearchQuery.value.toLowerCase()) ||
      (issue.issueDescription && issue.issueDescription.toLowerCase().includes(issueSearchQuery.value.toLowerCase()))
    
    const matchProject = !issueFilterProject.value || issue.projectId === parseInt(issueFilterProject.value)
    const matchStatus = !issueFilterStatus.value || issue.status === issueFilterStatus.value
    const matchPriority = !issueFilterPriority.value || issue.priority === issueFilterPriority.value
    
    return matchSearch && matchProject && matchStatus && matchPriority
  })
})

// 开始制定决策
const startDeciding = (issue: any) => {
  currentDecisionIssue.value = issue
  decisionForm.solution = ''
  showDecisionModal.value = true
}

// 关闭决策弹窗
const closeDecisionModal = () => {
  showDecisionModal.value = false
  currentDecisionIssue.value = null
  decisionForm.solution = ''
}

// 提交决策
const submitDecision = async () => {
  if (!currentDecisionIssue.value) return
  
  try {
    const response = await request.put(`/project/issue/${currentDecisionIssue.value.id}/resolve`, {
      solution: decisionForm.solution
    })
    if (response.code === 200) {
      alert('✅ 决策方案已确认，问题已解决')
      closeDecisionModal()
      await loadDecisionIssues()
    } else {
      alert('提交失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('提交决策失败:', error)
    alert('提交失败，请稍后重试')
  }
}

// 标记问题为处理中
const markIssueInProgress = async (id: number) => {
  try {
    const response = await request.put(`/project/issue/${id}/status`, {
      status: 'in_progress'
    })
    if (response.code === 200) {
      await loadDecisionIssues()
    }
  } catch (error) {
    console.error('更新状态失败:', error)
  }
}

// 重新开启问题（取消解决）
const reopenIssue = async (id: number) => {
  if (!confirm('确定要取消解决此问题，重新处理吗？')) return
  
  try {
    const response = await request.put(`/project/issue/${id}/reopen`)
    if (response.code === 200) {
      alert('✅ 问题已重新开启，可以重新处理了')
      await loadDecisionIssues()
    } else {
      alert('操作失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('重新开启问题失败:', error)
    alert('操作失败，请稍后重试')
  }
}

// 获取问题优先级文本
const getIssuePriorityText = (priority: string) => {
  const texts: Record<string, string> = {
    urgent: '紧急',
    high: '高',
    medium: '中',
    low: '低'
  }
  return texts[priority] || priority
}

// 获取问题状态文本
const getIssueStatusTextDecision = (status: string) => {
  const texts: Record<string, string> = {
    open: '待处理',
    in_progress: '处理中',
    resolved: '已解决'
  }
  return texts[status] || status
}

// 获取问题类型文本
const getIssueTypeTextDecision = (type: string) => {
  const texts: Record<string, string> = {
    technical: '技术问题',
    requirement: '需求问题',
    design: '设计问题',
    other: '其他问题'
  }
  return texts[type] || type
}

// 格式化问题日期
const formatIssueDate = (date: any) => {
  if (!date) return ''
  const d = new Date(date)
  return d.toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' })
}

</script> 

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.3s ease-in-out;
}

/* 折叠动画 */
.collapse-enter-active,
.collapse-leave-active {
  transition: all 0.3s ease;
  max-height: 1000px;
  overflow: hidden;
}

.collapse-enter-from,
.collapse-leave-to {
  max-height: 0;
  opacity: 0;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

/* 弹窗渐入渐出效果 */
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

/* 弹窗内容滑入滑出效果 */
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

/* 项目展开收起动画 */
.expand-enter-active,
.expand-leave-active {
  transition: all 0.3s ease;
  overflow: hidden;
}

.expand-enter-from,
.expand-leave-to {
  opacity: 0;
  max-height: 0;
  padding-top: 0;
  padding-bottom: 0;
}

.expand-enter-to,
.expand-leave-from {
  opacity: 1;
  max-height: 1000px;
}

/* 自定义滚动条样式 */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f5f9;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}
</style> 