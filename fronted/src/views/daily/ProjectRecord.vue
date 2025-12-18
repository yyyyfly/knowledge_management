<template>
  <section id="project-record" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
            <h1 class="text-3xl font-bold text-gray-900">项目执行</h1>
    <span class="bg-green-100 text-green-600 px-3 py-1 rounded-full text-sm font-medium">任务执行</span>
  </div>
  <p class="text-gray-600">记录项目执行过程中的任务管理、进度跟踪和执行记录</p>
    </div>

    <!-- 操作选项 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <h3 class="text-xl font-semibold text-gray-900 mb-6">选择操作</h3>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <!-- 任务执行 -->
        <div 
          @click="openOperation('taskExecution')"
          class="p-6 border-2 border-dashed border-green-300 rounded-xl hover:border-green-500 hover:bg-green-50 cursor-pointer transition-all duration-200"
        >
          <div class="text-center">
            <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fas fa-play-circle text-2xl text-green-600"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2">任务执行</h4>
            <p class="text-gray-600">查看和执行日常决策中创建的任务，记录执行进度</p>
          </div>
        </div>

        <!-- 问题管理 -->
        <div 
          @click="openOperation('issueManagement')"
          class="p-6 border-2 border-dashed border-orange-300 rounded-xl hover:border-orange-500 hover:bg-orange-50 cursor-pointer transition-all duration-200"
        >
          <div class="text-center">
            <div class="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fas fa-exclamation-triangle text-2xl text-orange-600"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2">问题管理</h4>
            <p class="text-gray-600">记录项目执行过程中遇到的问题，跟踪问题处理进度</p>
          </div>
        </div>

        <!-- 打卡操作 -->
        <div 
          @click="openOperation('checkinOperation')"
          class="p-6 border-2 border-dashed border-purple-300 rounded-xl hover:border-purple-500 hover:bg-purple-50 cursor-pointer transition-all duration-200"
        >
          <div class="text-center">
            <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fas fa-check-circle text-2xl text-purple-600"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2">打卡操作</h4>
            <p class="text-gray-600">每日打卡、习惯养成和目标追踪</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 操作视图过渡区 -->
    <Transition name="operation-fade" mode="out-in">
      <template v-if="currentOperation === 'taskExecution'">
        <div class="bg-white rounded-xl shadow-soft p-6 mb-8 max-h-[80vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <h3 class="text-xl font-semibold text-gray-900">任务执行</h3>
            <div class="flex space-x-2">
              <button 
                @click="closeOperation()"
                class="text-gray-500 hover:text-gray-700 transition-colors"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
          </div>
          <!-- 任务筛选 -->
          <div class="mb-6 flex-shrink-0">
            <div class="flex flex-col md:flex-row gap-4 mb-4">
              <div class="flex-1">
                <input 
                  v-model="taskSearchQuery"
                  type="text" 
                  placeholder="搜索任务名称或描述..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500"
                >
              </div>
              <div>
                <select v-model="taskFilterProject" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500">
                  <option value="">全部项目</option>
                  <option v-for="project in projects" :key="project.id" :value="project.id">
                    {{ project.name }}
                  </option>
                </select>
              </div>
              <div>
                <select v-model="taskFilterStatus" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500">
                  <option value="">全部状态</option>
                  <option value="pending">待开始</option>
                  <option value="in-progress">进行中</option>
                  <option value="completed">已完成</option>
                  <option value="stopped">已停止</option>
                </select>
              </div>
            </div>
            
            <!-- 分类选项 -->
            <div class="flex flex-wrap gap-2 mb-4">
              <button 
                @click="taskViewMode = 'list'"
                :class="taskViewMode === 'list' ? 'bg-green-600 text-white' : 'bg-gray-200 text-gray-700 hover:bg-gray-300'"
                class="px-4 py-2 rounded-lg text-sm font-medium transition-colors"
              >
                <i class="fas fa-list mr-2"></i>列表视图
              </button>
              <button 
                @click="taskViewMode = 'project'"
                :class="taskViewMode === 'project' ? 'bg-green-600 text-white' : 'bg-gray-200 text-gray-700 hover:bg-gray-300'"
                class="px-4 py-2 rounded-lg text-sm font-medium transition-colors"
              >
                <i class="fas fa-folder mr-2"></i>按项目分类
              </button>
              <button 
                @click="taskViewMode = 'time'"
                :class="taskViewMode === 'time' ? 'bg-green-600 text-white' : 'bg-gray-200 text-gray-700 hover:bg-gray-300'"
                class="px-4 py-2 rounded-lg text-sm font-medium transition-colors"
              >
                <i class="fas fa-calendar mr-2"></i>按时间分类
              </button>
            </div>
          </div>
          
          <!-- 任务列表 - 列表视图 -->
          <div v-if="taskViewMode === 'list'" class="space-y-4 flex-1 overflow-y-auto">
            <div v-for="task in filteredTasks" :key="task.id" class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50">
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center space-x-3 mb-2">
                    <h4 class="text-lg font-medium text-gray-900">{{ task.name }}</h4>
                    <span :class="getTaskStatusClass(task.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                      {{ getTaskStatusText(task.status) }}
                    </span>
                    <span class="text-sm text-gray-500">项目：{{ getProjectName(task.projectId) }}</span>
                  </div>
                  <p class="text-gray-600 mb-2">{{ task.description }}</p>
                  <div class="flex items-center space-x-4 text-sm text-gray-500 mb-3">
                    <span>创建时间：{{ task.createTime }}</span>
                    <span>截止时间：{{ task.deadline }}</span>
                  </div>
                  
                  <!-- 执行进度记录 -->
                  <div v-if="task.executionRecords && task.executionRecords.length > 0" class="bg-gray-50 p-3 rounded-lg mb-3">
                    <p class="text-sm font-medium text-gray-700 mb-2">执行记录：</p>
                    <div class="space-y-2">
                      <div v-for="record in task.executionRecords" :key="record.id" class="text-sm text-gray-600">
                        <span class="font-medium">{{ record.date }}：</span>{{ record.progress }}
                      </div>
                    </div>
                  </div>
                  
                  <!-- 添加执行记录 -->
                  <div class="flex space-x-2">
                    <input 
                      :value="task.newProgress"
                      @input="updateTaskProgress(task, ($event.target as HTMLInputElement).value)"
                      type="text" 
                      placeholder="记录执行进度..."
                      :disabled="task.status === 'stopped'"
                      :class="task.status === 'stopped' ? 'bg-gray-100 cursor-not-allowed' : ''"
                      class="flex-1 px-3 py-1 text-sm border border-gray-300 rounded focus:ring-2 focus:ring-green-500 focus:border-green-500"
                    >
                    <button 
                      @click="addExecutionRecord(task)"
                      :disabled="task.status === 'stopped'"
                      :class="task.status === 'stopped' ? 'bg-gray-400 cursor-not-allowed' : 'bg-green-600 hover:bg-green-700'"
                      class="px-3 py-1 text-white text-sm rounded"
                    >
                      记录
                    </button>
                    <button 
                      @click="updateTaskStatus(task, 'completed')"
                      v-if="task.status !== 'completed' && task.status !== 'stopped'"
                      class="px-3 py-1 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
                    >
                      完成
                    </button>
                    <button 
                      @click="updateTaskStatus(task, 'in-progress')"
                      v-if="task.status === 'completed'"
                      class="px-3 py-1 bg-yellow-600 text-white text-sm rounded hover:bg-yellow-700"
                    >
                      取消完成
                    </button>
                    <button 
                      @click="updateTaskStatus(task, 'stopped')"
                      v-if="task.status !== 'stopped'"
                      class="px-3 py-1 bg-red-600 text-white text-sm rounded hover:bg-red-700"
                    >
                      停止
                    </button>
                    <button 
                      @click="updateTaskStatus(task, 'in-progress')"
                      v-if="task.status === 'stopped'"
                      class="px-3 py-1 bg-orange-600 text-white text-sm rounded hover:bg-orange-700"
                    >
                      重启
                    </button>
                    <button 
                      @click="updateTaskStatus(task, 'pending')"
                      v-if="task.status !== 'pending' && task.status !== 'stopped'"
                      class="px-3 py-1 bg-gray-600 text-white text-sm rounded hover:bg-gray-700"
                    >
                      设为未开始
                    </button>
                    <button 
                      @click="updateTaskStatus(task, 'in-progress')"
                      v-if="task.status === 'pending'"
                      class="px-3 py-1 bg-indigo-600 text-white text-sm rounded hover:bg-indigo-700"
                    >
                      开始执行
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 按项目分类视图 -->
          <div v-if="taskViewMode === 'project'" class="space-y-6 flex-1 overflow-y-auto">
            <div v-for="project in groupedProjects" :key="project.id" class="border border-gray-200 rounded-lg overflow-hidden">
              <!-- 项目头部 -->
              <div class="bg-gray-50 px-4 py-3 border-b border-gray-200">
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <button 
                      @click="toggleProjectCollapse(project.id)"
                      class="flex items-center space-x-2 text-gray-700 hover:text-gray-900 transition-colors"
                    >
                      <i 
                        :class="collapsedProjects.includes(project.id) ? 'fas fa-chevron-right' : 'fas fa-chevron-down'"
                        class="text-sm transition-transform duration-200"
                      ></i>
                      <h4 class="text-lg font-semibold text-gray-900">{{ project.name }}</h4>
                    </button>
                    <span :class="getProjectStatusClass(project.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                      {{ getProjectStatusText(project.status) }}
                    </span>
                    <span class="text-sm text-gray-500">{{ project.categoryText }}</span>
                  </div>
                  <div class="flex items-center space-x-3">
                    <div class="text-sm text-gray-500">
                      任务数：{{ project.tasks.length }}
                    </div>
                    <button 
                      @click="toggleProjectCollapse(project.id)"
                      class="text-gray-500 hover:text-gray-700 transition-colors"
                      :title="collapsedProjects.includes(project.id) ? '展开' : '折叠'"
                    >
                      <i 
                        :class="collapsedProjects.includes(project.id) ? 'fas fa-chevron-down' : 'fas fa-chevron-up'"
                        class="text-sm"
                      ></i>
                    </button>
                  </div>
                </div>
                <p class="text-sm text-gray-600 mt-1">{{ project.description }}</p>
              </div>
              
              <!-- 项目下的任务列表 -->
              <div v-show="!collapsedProjects.includes(project.id)" class="divide-y divide-gray-200">
                <div v-for="task in project.tasks" :key="task.id" class="p-4 hover:bg-gray-50">
                  <div class="flex items-start justify-between">
                    <div class="flex-1">
                      <div class="flex items-center space-x-3 mb-2">
                        <h5 class="text-md font-medium text-gray-900">{{ task.name }}</h5>
                        <span :class="getTaskStatusClass(task.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                          {{ getTaskStatusText(task.status) }}
                        </span>
                      </div>
                      <p class="text-gray-600 mb-2 text-sm">{{ task.description }}</p>
                      <div class="flex items-center space-x-4 text-xs text-gray-500 mb-3">
                        <span>创建：{{ task.createTime }}</span>
                        <span>截止：{{ task.deadline }}</span>
                      </div>
                      
                      <!-- 执行进度记录 -->
                      <div v-if="task.executionRecords && task.executionRecords.length > 0" class="bg-gray-50 p-2 rounded mb-3">
                        <p class="text-xs font-medium text-gray-700 mb-1">执行记录：</p>
                        <div class="space-y-1">
                          <div v-for="record in task.executionRecords.slice(-2)" :key="record.id" class="text-xs text-gray-600">
                            <span class="font-medium">{{ record.date }}：</span>{{ record.progress }}
                          </div>
                        </div>
                      </div>
                      
                      <!-- 操作按钮 -->
                      <div class="flex flex-wrap gap-2">
                        <input 
                          :value="task.newProgress"
                          @input="updateTaskProgress(task, ($event.target as HTMLInputElement).value)"
                          type="text" 
                          placeholder="记录进度..."
                          :disabled="task.status === 'stopped'"
                          :class="task.status === 'stopped' ? 'bg-gray-100 cursor-not-allowed' : ''"
                          class="flex-1 px-2 py-1 text-xs border border-gray-300 rounded focus:ring-1 focus:ring-green-500 focus:border-green-500"
                        >
                        <button 
                          @click="addExecutionRecord(task)"
                          :disabled="task.status === 'stopped'"
                          :class="task.status === 'stopped' ? 'bg-gray-400 cursor-not-allowed' : 'bg-green-600 hover:bg-green-700'"
                          class="px-2 py-1 text-white text-xs rounded"
                        >
                          记录
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'completed')"
                          v-if="task.status !== 'completed' && task.status !== 'stopped'"
                          class="px-2 py-1 bg-blue-600 text-white text-xs rounded hover:bg-blue-700"
                        >
                          完成
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'in-progress')"
                          v-if="task.status === 'completed'"
                          class="px-2 py-1 bg-yellow-600 text-white text-xs rounded hover:bg-yellow-700"
                        >
                          取消完成
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'stopped')"
                          v-if="task.status !== 'stopped'"
                          class="px-2 py-1 bg-red-600 text-white text-xs rounded hover:bg-red-700"
                        >
                          停止
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'in-progress')"
                          v-if="task.status === 'stopped'"
                          class="px-2 py-1 bg-orange-600 text-white text-xs rounded hover:bg-orange-700"
                        >
                          重启
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'pending')"
                          v-if="task.status !== 'pending' && task.status !== 'stopped'"
                          class="px-2 py-1 bg-gray-600 text-white text-xs rounded hover:bg-gray-700"
                        >
                          设为未开始
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'in-progress')"
                          v-if="task.status === 'pending'"
                          class="px-2 py-1 bg-indigo-600 text-white text-xs rounded hover:bg-indigo-700"
                        >
                          开始执行
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 按时间分类视图 -->
          <div v-if="taskViewMode === 'time'" class="space-y-6 flex-1 overflow-y-auto">
            <div v-for="timeGroup in groupedByTime" :key="timeGroup.key" class="border border-gray-200 rounded-lg overflow-hidden">
              <!-- 时间分组头部 -->
              <div class="bg-blue-50 px-4 py-3 border-b border-gray-200">
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <button 
                      @click="toggleTimeGroupCollapse(timeGroup.key)"
                      class="flex items-center space-x-2 text-gray-700 hover:text-gray-900 transition-colors"
                    >
                      <i 
                        :class="collapsedTimeGroups.includes(timeGroup.key) ? 'fas fa-chevron-right' : 'fas fa-chevron-down'"
                        class="text-sm transition-transform duration-200"
                      ></i>
                      <i class="fas fa-calendar text-blue-600"></i>
                      <h4 class="text-lg font-semibold text-gray-900">{{ timeGroup.title }}</h4>
                    </button>
                    <span class="bg-blue-100 text-blue-600 px-2 py-1 rounded-full text-xs font-medium">
                      {{ timeGroup.tasks.length }} 个任务
                    </span>
                  </div>
                  <div class="flex items-center space-x-3">
                    <div class="text-sm text-gray-500">
                      {{ timeGroup.description }}
                    </div>
                    <button 
                      @click="toggleTimeGroupCollapse(timeGroup.key)"
                      class="text-gray-500 hover:text-gray-700 transition-colors"
                      :title="collapsedTimeGroups.includes(timeGroup.key) ? '展开' : '折叠'"
                    >
                      <i 
                        :class="collapsedTimeGroups.includes(timeGroup.key) ? 'fas fa-chevron-down' : 'fas fa-chevron-up'"
                        class="text-sm"
                      ></i>
                    </button>
                  </div>
                </div>
              </div>
              
              <!-- 时间分组下的任务列表 -->
              <div v-show="!collapsedTimeGroups.includes(timeGroup.key)" class="divide-y divide-gray-200">
                <div v-for="task in timeGroup.tasks" :key="task.id" class="p-4 hover:bg-gray-50">
                  <div class="flex items-start justify-between">
                    <div class="flex-1">
                      <div class="flex items-center space-x-3 mb-2">
                        <h5 class="text-md font-medium text-gray-900">{{ task.name }}</h5>
                        <span :class="getTaskStatusClass(task.status)" class="px-2 py-1 rounded-full text-xs font-medium">
                          {{ getTaskStatusText(task.status) }}
                        </span>
                        <span class="text-sm text-gray-500">项目：{{ getProjectName(task.projectId) }}</span>
                      </div>
                      <p class="text-gray-600 mb-2 text-sm">{{ task.description }}</p>
                      <div class="flex items-center space-x-4 text-xs text-gray-500 mb-3">
                        <span>创建：{{ task.createTime }}</span>
                        <span>截止：{{ task.deadline }}</span>
                      </div>
                      
                      <!-- 执行进度记录 -->
                      <div v-if="task.executionRecords && task.executionRecords.length > 0" class="bg-gray-50 p-2 rounded mb-3">
                        <p class="text-xs font-medium text-gray-700 mb-1">执行记录：</p>
                        <div class="space-y-1">
                          <div v-for="record in task.executionRecords.slice(-2)" :key="record.id" class="text-xs text-gray-600">
                            <span class="font-medium">{{ record.date }}：</span>{{ record.progress }}
                          </div>
                        </div>
                      </div>
                      
                      <!-- 操作按钮 -->
                      <div class="flex flex-wrap gap-2">
                        <input 
                          :value="task.newProgress"
                          @input="updateTaskProgress(task, ($event.target as HTMLInputElement).value)"
                          type="text" 
                          placeholder="记录进度..."
                          :disabled="task.status === 'stopped'"
                          :class="task.status === 'stopped' ? 'bg-gray-100 cursor-not-allowed' : ''"
                          class="flex-1 px-2 py-1 text-xs border border-gray-300 rounded focus:ring-1 focus:ring-green-500 focus:border-green-500"
                        >
                        <button 
                          @click="addExecutionRecord(task)"
                          :disabled="task.status === 'stopped'"
                          :class="task.status === 'stopped' ? 'bg-gray-400 cursor-not-allowed' : 'bg-green-600 hover:bg-green-700'"
                          class="px-2 py-1 text-white text-xs rounded"
                        >
                          记录
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'completed')"
                          v-if="task.status !== 'completed' && task.status !== 'stopped'"
                          class="px-2 py-1 bg-blue-600 text-white text-xs rounded hover:bg-blue-700"
                        >
                          完成
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'in-progress')"
                          v-if="task.status === 'completed'"
                          class="px-2 py-1 bg-yellow-600 text-white text-xs rounded hover:bg-yellow-700"
                        >
                          取消完成
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'stopped')"
                          v-if="task.status !== 'stopped'"
                          class="px-2 py-1 bg-red-600 text-white text-xs rounded hover:bg-red-700"
                        >
                          停止
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'in-progress')"
                          v-if="task.status === 'stopped'"
                          class="px-2 py-1 bg-orange-600 text-white text-xs rounded hover:bg-orange-700"
                        >
                          重启
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'pending')"
                          v-if="task.status !== 'pending' && task.status !== 'stopped'"
                          class="px-2 py-1 bg-gray-600 text-white text-xs rounded hover:bg-gray-700"
                        >
                          设为未开始
                        </button>
                        <button 
                          @click="updateTaskStatus(task, 'in-progress')"
                          v-if="task.status === 'pending'"
                          class="px-2 py-1 bg-indigo-600 text-white text-xs rounded hover:bg-indigo-700"
                        >
                          开始执行
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>

      <!-- 问题管理 -->
      <template v-else-if="currentOperation === 'issueManagement'">
        <div class="bg-white rounded-xl shadow-soft p-6 mb-8 max-h-[80vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <h3 class="text-xl font-semibold text-gray-900">问题管理</h3>
            <div class="flex space-x-2">
              <button 
                @click="showCreateIssue = true"
                class="px-4 py-2 bg-orange-600 text-white rounded-lg hover:bg-orange-700 flex items-center space-x-2"
              >
                <i class="fas fa-plus"></i>
                <span>记录问题</span>
              </button>
              <button 
                @click="closeOperation()"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
          </div>
          
          <!-- 搜索和筛选 -->
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
                  <option value="closed">已关闭</option>
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
            <div v-for="issue in filteredIssues" :key="issue.id" class="p-4 border-2 rounded-lg hover:shadow-md transition-shadow"
              :class="{
                'border-red-200 bg-red-50': issue.priority === 'urgent',
                'border-orange-200 bg-orange-50': issue.priority === 'high',
                'border-yellow-200 bg-yellow-50': issue.priority === 'medium',
                'border-gray-200 bg-gray-50': issue.priority === 'low'
              }">
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center space-x-3 mb-2">
                    <h4 class="text-lg font-medium text-gray-900">{{ issue.issueTitle }}</h4>
                    <span class="px-2 py-1 rounded text-xs font-medium"
                      :class="{
                        'bg-red-100 text-red-700': issue.priority === 'urgent',
                        'bg-orange-100 text-orange-700': issue.priority === 'high',
                        'bg-yellow-100 text-yellow-700': issue.priority === 'medium',
                        'bg-gray-100 text-gray-700': issue.priority === 'low'
                      }">
                      {{ getPriorityText(issue.priority) }}
                    </span>
                    <span class="px-2 py-1 rounded text-xs font-medium"
                      :class="{
                        'bg-gray-100 text-gray-700': issue.status === 'open',
                        'bg-blue-100 text-blue-700': issue.status === 'in_progress',
                        'bg-green-100 text-green-700': issue.status === 'resolved',
                        'bg-slate-100 text-slate-700': issue.status === 'closed'
                      }">
                      {{ getIssueStatusText(issue.status) }}
                    </span>
                  </div>
                  
                  <div class="bg-white p-3 rounded-lg mb-3">
                    <p class="text-sm font-medium text-gray-700 mb-1">问题描述：</p>
                    <p class="text-sm text-gray-600">{{ issue.issueDescription }}</p>
                  </div>
                  
                  <!-- 解决方案 -->
                  <div v-if="issue.solution" class="bg-green-50 p-3 rounded-lg mb-3 border border-green-200">
                    <p class="text-sm font-medium text-green-700 mb-1">✅ 解决方案：</p>
                    <p class="text-sm text-gray-700">{{ issue.solution }}</p>
                    <p v-if="issue.resolveTime" class="text-xs text-gray-500 mt-2">
                      解决时间：{{ formatDate(issue.resolveTime) }}
                    </p>
                  </div>
                  
                  <div class="flex items-center space-x-4 text-sm text-gray-500">
                    <span>项目：{{ getProjectName(issue.projectId) }}</span>
                    <span>类型：{{ getIssueTypeText(issue.issueType) }}</span>
                    <span>{{ formatDate(issue.recCreateTime) }}</span>
                  </div>
                </div>
                <div class="flex space-x-2">
                  <!-- 未解决状态的操作 -->
                  <button 
                    v-if="issue.status !== 'resolved' && issue.status !== 'closed'"
                    @click="resolveIssue(issue)" 
                    class="text-green-600 hover:text-green-800"
                    title="处理问题"
                  >
                    <i class="fas fa-check-circle"></i>
                  </button>
                  
                  <!-- 已解决状态的退回按钮 -->
                  <button 
                    v-if="issue.status === 'resolved'"
                    @click="reopenIssueFromExecution(issue.id)" 
                    class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded hover:bg-yellow-200 transition-colors text-sm"
                    title="退回重新处理"
                  >
                    <i class="fas fa-undo mr-1"></i>退回
                  </button>
                  
                  <button @click="editIssue(issue)" class="text-blue-600 hover:text-blue-800" title="编辑">
                    <i class="fas fa-edit"></i>
                  </button>
                  <button @click="deleteIssue(issue.id)" class="text-red-600 hover:text-red-800" title="删除">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
              </div>
            </div>
            
            <!-- 空状态 -->
            <div v-if="filteredIssues.length === 0" class="text-center py-12 text-gray-500">
              <i class="fas fa-exclamation-triangle text-4xl mb-4 text-orange-400"></i>
              <p class="text-lg font-medium mb-2">暂无问题记录</p>
              <p class="text-sm">项目很顺利！也可以点击上方按钮记录遇到的问题</p>
            </div>
          </div>
        </div>
      </template>

      <!-- 打卡操作 -->
      <template v-else-if="currentOperation === 'checkinOperation'">
        <div class="bg-white rounded-xl shadow-soft p-6 mb-8 max-h-[80vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <h3 class="text-xl font-semibold text-gray-900">打卡操作</h3>
            <button 
              @click="closeOperation()"
              class="text-gray-500 hover:text-gray-700 transition-colors"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <!-- 打卡项目列表 -->
          <div v-if="activeCheckinItems.length === 0" class="text-center py-12 text-gray-500 flex-1">
            <i class="fas fa-check-circle text-6xl mb-4"></i>
            <p class="text-lg font-medium mb-2">暂无打卡项目</p>
            <p class="text-sm">请先在"日常决策"中创建打卡项目</p>
          </div>

          <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 flex-1 overflow-y-auto">
            <div v-for="item in activeCheckinItems" :key="item.id" 
              class="p-4 border-2 rounded-lg transition-all"
              :class="checkinStatus[item.id!] ? 'border-green-500 bg-green-50' : 'border-gray-300 hover:border-purple-400'">
              <div class="flex flex-col h-full">
                <div class="flex items-center justify-between mb-3">
                  <h4 class="text-lg font-medium text-gray-900">{{ item.title }}</h4>
                  <span :class="getFrequencyClassForCheckin(item.frequency)" class="px-2 py-1 rounded-full text-xs font-medium">
                    {{ getFrequencyTextForCheckin(item.frequency) }}
                  </span>
                </div>
                <p v-if="item.description" class="text-gray-600 text-sm mb-4 flex-1">{{ item.description }}</p>
                
                <div class="mt-auto">
                  <button 
                    v-if="!checkinStatus[item.id!]"
                    @click="handleCheckin(item.id!)"
                    class="w-full py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors flex items-center justify-center space-x-2"
                  >
                    <i class="fas fa-check"></i>
                    <span>打卡</span>
                  </button>
                  <button 
                    v-else
                    @click="handleCancelCheckin(item.id!)"
                    class="w-full py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors flex items-center justify-center space-x-2"
                  >
                    <i class="fas fa-check-double"></i>
                    <span>已打卡 - 点击取消</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>

      <!-- 笔记调用 -->
      <template v-else-if="currentOperation === 'noteReference'">
        <div class="bg-white rounded-xl shadow-soft p-6 mb-8 max-h-[80vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <h3 class="text-xl font-semibold text-gray-900">笔记调用</h3>
            <button 
              @click="closeOperation()"
              class="text-gray-500 hover:text-gray-700 transition-colors"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
      </div>
      
          <!-- 项目筛选和搜索 -->
          <div class="mb-6 flex-shrink-0">
            <div class="flex flex-col md:flex-row gap-4 mb-4">
              <!-- 搜索框 -->
              <div class="flex-1">
                <label class="block text-sm font-medium text-gray-700 mb-2">搜索项目</label>
                <div class="relative">
                  <input 
                    v-model="noteProjectSearchQuery" 
                    type="text" 
                    placeholder="输入项目名称搜索..."
                    class="w-full px-4 py-2 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  >
                  <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            </div>
            </div>
              
              <!-- 分类筛选 -->
              <div class="w-full md:w-64">
                <label class="block text-sm font-medium text-gray-700 mb-2">项目分类</label>
                <select 
                  v-model="noteProjectCategoryFilter"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                >
                  <option value="">全部分类</option>
                  <option value="defense">战争行动</option>
                  <option value="construction">工程建设</option>
                  <option value="diplomatic">外交行动</option>
                </select>
          </div>
        </div>

            <!-- 项目选择下拉框 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">选择项目</label>
              <select 
                v-model="selectedProjectForNotes" 
                @change="loadProjectNotes"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              >
                <option value="">请选择项目</option>
                <option v-for="project in filteredProjectsForNotes" :key="project.id" :value="project.id">
                  {{ project.name }} 
                  <template v-if="project.category === 'defense'">【战争行动】</template>
                  <template v-else-if="project.category === 'construction'">【工程建设】</template>
                  <template v-else-if="project.category === 'diplomatic'">【外交行动】</template>
                </option>
              </select>
              
              <!-- 筛选结果统计 -->
              <div class="mt-2 text-sm text-gray-500">
                共找到 {{ filteredProjectsForNotes.length }} 个项目
            </div>
          </div>
        </div>

          <!-- 已关联笔记列表 -->
          <div v-if="selectedProjectForNotes" class="flex-1 overflow-y-auto">
            <div class="flex items-center justify-between mb-4">
              <h4 class="text-lg font-semibold text-gray-900">已关联笔记</h4>
              <div class="flex space-x-3">
                <button 
                  @click="showAddNoteDialog = true"
                  class="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 flex items-center space-x-2"
                >
                  <i class="fas fa-plus"></i>
                  <span>添加笔记</span>
                </button>
                <button 
                  @click="openArchiveImportDialog"
                  class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 flex items-center space-x-2"
                >
                  <i class="fas fa-folder-open"></i>
                  <span>从归档批量添加</span>
                </button>
          </div>
        </div>

            <!-- 笔记卡片网格 -->
            <div v-if="projectNotes.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              <div 
                v-for="note in projectNotes" 
                :key="note.id"
                class="p-4 border-2 rounded-lg hover:shadow-md transition-all duration-200 cursor-pointer"
                :class="getNoteTypeBorderClass(note.type)"
                @click="viewNoteDetail(note)"
              >
                <div class="flex items-start justify-between mb-3">
                  <div class="flex items-center space-x-2">
                    <div class="w-8 h-8 rounded-full flex items-center justify-center" :class="getNoteTypeIconClass(note.type)">
                      <i :class="getNoteTypeIcon(note.type)" class="text-sm"></i>
            </div>
                    <span class="px-2 py-1 rounded text-xs font-medium" :class="getNoteTypeClass(note.type)">
                      {{ getNoteTypeText(note.type) }}
                    </span>
            </div>
                  <button 
                    @click.stop="removeNoteFromProject(note.id)"
                    class="text-red-500 hover:text-red-700"
                    title="移除关联"
                  >
                    <i class="fas fa-times"></i>
                  </button>
            </div>
                <h5 class="text-md font-semibold text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h5>
                <p class="text-sm text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
                <div class="flex items-center justify-between text-xs text-gray-500">
                  <span>{{ note.createTime || note.recCreateTime }}</span>
                  <div class="flex space-x-1" v-if="Array.isArray(note.tags) && note.tags.length > 0">
                    <span v-for="tag in note.tags.slice(0, 2)" :key="tag" class="bg-gray-100 text-gray-600 px-2 py-0.5 rounded">
                      {{ tag }}
                    </span>
            </div>
          </div>
          </div>
        </div>

            <!-- 空状态 -->
            <div v-else class="text-center py-12 text-gray-500">
              <i class="fas fa-book-open text-6xl mb-4 text-indigo-200"></i>
              <p class="text-lg font-medium mb-2">暂无关联笔记</p>
              <p class="text-sm mb-4">点击"添加笔记"按钮，为项目关联相关笔记</p>
              <button 
                @click="showAddNoteDialog = true"
                class="px-6 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700"
              >
                添加笔记
              </button>
            </div>
            </div>

          <!-- 未选择项目提示 -->
          <div v-else class="flex-1 flex items-center justify-center text-gray-500">
            <div class="text-center">
              <i class="fas fa-folder-open text-6xl mb-4 text-gray-300"></i>
              <p class="text-lg font-medium">请先选择一个项目</p>
          </div>
          </div>
        </div>
      </template>
    </Transition>

    <!-- 添加笔记弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showAddNoteDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[90vh] flex flex-col">
            <!-- 弹窗头部 -->
            <div class="flex items-center justify-between p-6 border-b border-gray-200">
              <h3 class="text-2xl font-semibold text-gray-900">添加笔记到项目</h3>
              <button 
                @click="closeAddNoteDialog"
                class="text-gray-500 hover:text-gray-700 transition-colors"
              >
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
            
            <!-- 搜索和筛选 -->
            <div class="p-6 border-b border-gray-200">
              <div class="flex gap-4">
                <div class="flex-1 relative">
                <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                <input 
                    v-model="noteSearchQuery"
                  type="text" 
                    placeholder="搜索笔记标题或内容..."
                    class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  >
                </div>
                <select 
                  v-model="noteFilterType"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                >
                  <option value="">全部类型</option>
                  <option value="fragment">碎片笔记</option>
                  <option value="skill">技能笔记</option>
                  <option value="study">求学笔记</option>
                  <option value="memorization">背诵笔记</option>
                  <option value="exercise">刷题笔记</option>
                  <option value="practical">实战笔记</option>
                </select>
              </div>
            </div>
            
            <!-- 笔记列表 -->
            <div class="flex-1 overflow-y-auto p-6">
              <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div 
                  v-for="note in filteredAvailableNotes" 
                  :key="note.id"
                  class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 hover:border-indigo-300 cursor-pointer transition-all duration-200"
                  @click="addNoteToProject(note)"
                >
                  <div class="flex items-start justify-between mb-3">
                    <span class="px-2 py-1 rounded text-xs font-medium" :class="getNoteTypeClass(note.type)">
                      {{ getNoteTypeText(note.type) }}
                    </span>
                    <i class="fas fa-plus-circle text-indigo-600 text-xl"></i>
                  </div>
                  <h5 class="text-sm font-medium text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h5>
                  <p class="text-xs text-gray-600 mb-2 line-clamp-2">{{ note.summary }}</p>
                  <div class="flex items-center justify-between text-xs text-gray-500">
                    <span>{{ note.createTime || note.recCreateTime }}</span>
                  </div>
                </div>
                
                <!-- 空状态 -->
                <div v-if="filteredAvailableNotes.length === 0" class="col-span-full text-center py-16 text-gray-500">
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

    <!-- 从归档批量添加弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showArchiveImportDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl w-full max-w-2xl max-h-[80vh] flex flex-col">
            <!-- 弹窗头部 -->
            <div class="flex items-center justify-between p-6 border-b border-gray-200">
              <h3 class="text-2xl font-semibold text-gray-900">从归档批量添加笔记</h3>
              <button 
                @click="closeArchiveImportDialog"
                class="text-gray-500 hover:text-gray-700 transition-colors"
              >
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
            
            <!-- 说明 -->
            <div class="p-6 border-b border-gray-200 bg-blue-50">
              <div class="flex items-start">
                <i class="fas fa-info-circle text-blue-600 mt-1 mr-3"></i>
                <div class="text-sm text-gray-700">
                  <p class="font-semibold mb-1">快速关联归档中的所有笔记</p>
                  <p>选择一个笔记归档，系统会自动将该归档中的所有笔记添加到当前项目中。</p>
                </div>
              </div>
            </div>
            
            <!-- 归档列表 -->
            <div class="flex-1 overflow-y-auto p-6">
              <div v-if="isLoadingArchives" class="text-center py-12">
                <i class="fas fa-spinner fa-spin text-4xl text-gray-400 mb-3"></i>
                <p class="text-gray-500">加载归档中...</p>
              </div>
              
              <div v-else-if="archives.length === 0" class="text-center py-12 text-gray-500">
                <i class="fas fa-folder-open text-6xl mb-4 text-gray-300"></i>
                <p class="text-lg font-medium mb-2">暂无归档</p>
                <p class="text-sm">请先在笔记归档页面创建归档</p>
              </div>
              
              <div v-else class="space-y-3">
                <div 
                  v-for="archive in archives" 
                  :key="archive.id"
                  class="p-4 border-2 border-gray-200 rounded-lg hover:border-green-500 hover:bg-green-50 cursor-pointer transition-all duration-200"
                  @click="importArchiveNotes(archive)"
                >
                  <div class="flex items-start justify-between">
                    <div class="flex-1">
                      <div class="mb-2">
                        <h4 class="text-lg font-semibold text-gray-900">{{ archive.title }}</h4>
                      </div>
                      <p v-if="archive.description" class="text-sm text-gray-600 mb-2">{{ archive.description }}</p>
                      <div class="flex items-center text-xs text-gray-500">
                        <span v-if="archive.author" class="mr-3">
                          <i class="fas fa-user mr-1"></i>{{ archive.author }}
                        </span>
                        <span>
                          <i class="fas fa-sticky-note mr-1"></i>{{ archive.noteCount || 0 }} 条笔记
                        </span>
                      </div>
                    </div>
                    <i class="fas fa-arrow-circle-right text-green-600 text-2xl"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 笔记详情弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showMaterialDetail" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">素材详情</h3>
              <button 
                @click="closeMaterialDetail"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <div v-if="selectedMaterial" class="space-y-4">
              <div class="flex items-center space-x-3 mb-4">
                <h4 class="text-lg font-medium text-gray-900">{{ selectedMaterial.title }}</h4>
                <span :class="getMaterialTypeClass(selectedMaterial.type)" class="px-2 py-1 rounded-full text-xs font-medium">
                  {{ getMaterialTypeText(selectedMaterial.type) }}
                </span>
              </div>
              
              <!-- 背诵笔记特殊显示 -->
              <template v-if="selectedMaterial.type === 'memorization'">
                <div class="bg-gray-50 p-4 rounded-lg" v-if="selectedMaterial.summary">
                  <p class="text-sm font-medium text-gray-700 mb-2">摘要：</p>
                  <p class="text-sm text-gray-600">{{ selectedMaterial.summary }}</p>
                </div>
                
                <div v-if="selectedMaterial.knowledgePoint && Array.isArray(selectedMaterial.knowledgePoint) && selectedMaterial.knowledgePoint.length > 0">
                  <p class="text-sm font-medium text-gray-700 mb-2">知识点：</p>
                  <div class="flex flex-wrap gap-2">
                    <span v-for="kp in selectedMaterial.knowledgePoint" :key="kp" class="bg-green-100 text-green-700 px-2 py-1 rounded text-sm">
                      {{ kp }}
                    </span>
                  </div>
                </div>
                
                <div v-if="selectedMaterial.project">
                  <p class="text-sm font-medium text-gray-700 mb-2">所属项目：</p>
                  <span class="bg-blue-100 text-blue-700 px-3 py-1 rounded text-sm">{{ selectedMaterial.project }}</span>
                </div>
              </template>
              
              <!-- 其他类型笔记显示 -->
              <template v-else>
                <div class="bg-gray-50 p-4 rounded-lg" v-if="selectedMaterial.summary">
                  <p class="text-sm font-medium text-gray-700 mb-2">摘要：</p>
                  <p class="text-sm text-gray-600">{{ selectedMaterial.summary }}</p>
                </div>
              </template>
              
              <div>
                <p class="text-sm font-medium text-gray-700 mb-2">详细内容：</p>
                <div class="text-sm text-gray-600 prose prose-sm max-w-none" v-html="selectedMaterial.content"></div>
              </div>
              
              <div class="flex flex-col space-y-2 text-sm text-gray-500">
                <span>创建时间：{{ selectedMaterial.createTime || selectedMaterial.recCreateTime }}</span>
                <div class="flex flex-wrap gap-2" v-if="Array.isArray(selectedMaterial.tags) && selectedMaterial.tags.length > 0">
                  <span class="text-gray-700 mr-2">标签：</span>
                  <span v-for="tag in selectedMaterial.tags" :key="tag" class="bg-gray-100 text-gray-600 px-2 py-1 rounded">
                    {{ tag }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 新建记录弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCreateRecord" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">新建项目心得</h3>
              <button 
                @click="closeCreateRecord"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitProjectRecord" class="space-y-6">
              <!-- 项目选择 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">选择项目</label>
                <select v-model="projectForm.projectId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                  <option value="">请选择项目</option>
                  <option v-for="project in projects" :key="project.id" :value="project.id">
                    {{ project.name }}
                  </option>
                </select>
              </div>

              <!-- 心得标题 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">心得标题</label>
                <input 
                  v-model="projectForm.title" 
                  type="text" 
                  placeholder="请输入心得标题"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                >
              </div>

              <!-- 心得摘要 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">心得摘要</label>
                <textarea 
                  v-model="projectForm.summary" 
                  rows="3" 
                  placeholder="请输入心得摘要..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                ></textarea>
              </div>

              <!-- 富文本编辑器 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">详细内容</label>
                <div class="border border-gray-300 rounded-lg overflow-hidden">
                  <!-- 工具栏 -->
                  <div class="bg-gray-50 border-b border-gray-300 p-3 flex flex-wrap items-center gap-2">
                    <!-- 文本格式 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleBold().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('bold') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="粗体"
                      >
                        <i class="fas fa-bold"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleItalic().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('italic') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="斜体"
                      >
                        <i class="fas fa-italic"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleUnderline().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('underline') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="下划线"
                      >
                        <i class="fas fa-underline"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleStrike().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('strike') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="删除线"
                      >
                        <i class="fas fa-strikethrough"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 标题 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleHeading({ level: 1 }).run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 1 }) }"
                        class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                        title="标题1"
                      >
                        H1
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleHeading({ level: 2 }).run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 2 }) }"
                        class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                        title="标题2"
                      >
                        H2
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleHeading({ level: 3 }).run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 3 }) }"
                        class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                        title="标题3"
                      >
                        H3
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 列表 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleBulletList().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('bulletList') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="无序列表"
                      >
                        <i class="fas fa-list-ul"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleOrderedList().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('orderedList') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="有序列表"
                      >
                        <i class="fas fa-list-ol"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 引用和代码 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleBlockquote().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('blockquote') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="引用"
                      >
                        <i class="fas fa-quote-left"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleCodeBlock().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('codeBlock') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="代码块"
                      >
                        <i class="fas fa-code"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 表格 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        type="button"
                        @click="insertTable"
                        class="p-2 rounded hover:bg-gray-200"
                        title="插入表格"
                      >
                        <i class="fas fa-table"></i>
                      </button>
                      
                      <!-- 表格行数控制 -->
                      <div class="relative inline-block">
                        <button 
                          type="button"
                          @click="showTableMenu = !showTableMenu"
                          class="p-2 rounded hover:bg-gray-200 flex items-center space-x-1"
                          title="自定义表格"
                        >
                          <i class="fas fa-table"></i>
                          <i class="fas fa-chevron-down text-xs"></i>
                        </button>
                        <div v-if="showTableMenu" class="absolute top-full left-0 mt-1 bg-white border border-gray-300 rounded-lg shadow-lg z-10 min-w-[200px]">
                          <div class="p-3">
                            <div class="grid grid-cols-2 gap-2 mb-3">
                              <div>
                                <label class="block text-xs text-gray-600 mb-1">行数</label>
                                <input 
                                  v-model="tableRows" 
                                  type="number" 
                                  min="1" 
                                  max="10"
                                  class="w-full px-2 py-1 text-sm border border-gray-300 rounded"
                                >
                              </div>
                              <div>
                                <label class="block text-xs text-gray-600 mb-1">列数</label>
                                <input 
                                  v-model="tableCols" 
                                  type="number" 
                                  min="1" 
                                  max="10"
                                  class="w-full px-2 py-1 text-sm border border-gray-300 rounded"
                                >
                              </div>
                            </div>
                            <button 
                              type="button"
                              @click="insertCustomTable(parseInt(tableRows), parseInt(tableCols)); showTableMenu = false"
                              class="w-full px-3 py-1 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
                            >
                              插入表格
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 图片 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        type="button"
                        @click="insertImage"
                        class="p-2 rounded hover:bg-gray-200"
                        title="插入图片URL"
                      >
                        <i class="fas fa-image"></i>
                      </button>
                      <button 
                        type="button"
                        @click="uploadImage"
                        class="p-2 rounded hover:bg-gray-200"
                        title="上传图片"
                      >
                        <i class="fas fa-upload"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 对齐 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        type="button"
                        @click="editor?.chain().focus().setTextAlign('left').run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'left' }) }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="左对齐"
                      >
                        <i class="fas fa-align-left"></i>
                      </button>
                      <button 
                        type="button"
                        @click="editor?.chain().focus().setTextAlign('center').run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'center' }) }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="居中"
                      >
                        <i class="fas fa-align-center"></i>
                      </button>
                      <button 
                        type="button"
                        @click="editor?.chain().focus().setTextAlign('right').run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'right' }) }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="右对齐"
                      >
                        <i class="fas fa-align-right"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 高亮 -->
                    <button 
                      type="button"
                      @click="editor?.chain().focus().toggleHighlight().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('highlight') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="高亮"
                    >
                      <i class="fas fa-highlighter"></i>
                    </button>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 链接 -->
                    <button 
                      type="button"
                      @click="setLink"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入链接"
                    >
                      <i class="fas fa-link"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editor?.chain().focus().unsetLink().run()"
                      :disabled="!editor?.isActive('link')"
                      class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed"
                      title="取消链接"
                    >
                      <i class="fas fa-unlink"></i>
                    </button>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 撤销重做 -->
                    <button 
                      type="button"
                      @click="editor?.chain().focus().undo().run()"
                      :disabled="!editor?.can().undo()"
                      class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed"
                      title="撤销"
                    >
                      <i class="fas fa-undo"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editor?.chain().focus().redo().run()"
                      :disabled="!editor?.can().redo()"
                      class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed"
                      title="重做"
                    >
                      <i class="fas fa-redo"></i>
                    </button>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- Markdown -->
                    <button 
                      type="button"
                      @click="importMarkdown"
                      class="px-2 py-1 rounded hover:bg-gray-200 text-sm font-semibold"
                      title="导入Markdown"
                    >
                      <i class="fab fa-markdown"></i>
                    </button>
                  </div>
                  
                  <!-- 编辑器内容区域 -->
                  <div class="p-4 min-h-[300px] max-h-[500px] overflow-y-auto" @click="selectImage">
                    <editor-content :editor="editor" class="prose-editor" />
                  </div>
                </div>
              </div>

              <!-- 标签 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">标签</label>
                <div class="space-y-3">
                  <!-- 标签输入区域 -->
                  <div class="flex items-center space-x-2">
                    <input 
                      v-model="newTag" 
                      type="text" 
                      placeholder="输入标签名称"
                      @keyup.enter="addTag"
                      class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    >
                    <button 
                      type="button"
                      @click="addTag"
                      :disabled="!newTag.trim()"
                      class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed flex items-center space-x-1"
                    >
                      <i class="fas fa-plus"></i>
                      <span>添加</span>
                    </button>
                  </div>
                  
                  <!-- 已添加的标签列表 -->
                  <div v-if="projectForm.tags.length > 0" class="flex flex-wrap gap-2">
                    <div 
                      v-for="(tag, index) in projectForm.tags" 
                      :key="index"
                      class="flex items-center space-x-2 bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-sm"
                    >
                      <span>{{ tag }}</span>
                      <button 
                        type="button"
                        @click="removeTag(index)"
                        class="text-blue-500 hover:text-blue-700"
                      >
                        <i class="fas fa-times"></i>
                      </button>
                    </div>
                  </div>
                  
                  <!-- 常用标签建议 -->
                  <div class="flex flex-wrap gap-2">
                    <span class="text-sm text-gray-500 mr-2">常用标签:</span>
                    <button 
                      v-for="suggestedTag in suggestedTags" 
                      :key="suggestedTag"
                      type="button"
                      @click="addSuggestedTag(suggestedTag)"
                      class="px-2 py-1 text-xs bg-gray-100 text-gray-600 rounded hover:bg-gray-200"
                    >
                      {{ suggestedTag }}
                    </button>
                  </div>
                </div>
              </div>

              <!-- 提交按钮 -->
              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeCreateRecord"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                  保存心得
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 编辑记录弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showEditRecord" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">编辑项目心得</h3>
              <button 
                @click="closeEditRecord"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitEditRecord" class="space-y-6">
              <!-- 项目选择 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">选择项目</label>
                <select v-model="editForm.projectId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                  <option value="">请选择项目</option>
                  <option v-for="project in projects" :key="project.id" :value="project.id">
                    {{ project.name }}
                  </option>
                </select>
              </div>

              <!-- 心得标题 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">心得标题</label>
                <input 
                  v-model="editForm.title" 
                  type="text" 
                  placeholder="请输入心得标题"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                >
              </div>

              <!-- 心得摘要 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">心得摘要</label>
                <textarea 
                  v-model="editForm.summary" 
                  rows="3" 
                  placeholder="请输入心得摘要..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                ></textarea>
              </div>

              <!-- 富文本编辑器 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">详细内容</label>
                <div class="border border-gray-300 rounded-lg overflow-hidden">
                  <!-- 工具栏 -->
                  <div class="bg-gray-50 border-b border-gray-300 p-3 flex flex-wrap items-center gap-2">
                    <!-- 文本格式 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleBold().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('bold') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="粗体"
                      >
                        <i class="fas fa-bold"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleItalic().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('italic') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="斜体"
                      >
                        <i class="fas fa-italic"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleUnderline().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('underline') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="下划线"
                      >
                        <i class="fas fa-underline"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleStrike().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('strike') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="删除线"
                      >
                        <i class="fas fa-strikethrough"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 标题 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleHeading({ level: 1 }).run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 1 }) }"
                        class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                        title="标题1"
                      >
                        H1
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleHeading({ level: 2 }).run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 2 }) }"
                        class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                        title="标题2"
                      >
                        H2
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleHeading({ level: 3 }).run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 3 }) }"
                        class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                        title="标题3"
                      >
                        H3
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 列表 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleBulletList().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('bulletList') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="无序列表"
                      >
                        <i class="fas fa-list-ul"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleOrderedList().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('orderedList') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="有序列表"
                      >
                        <i class="fas fa-list-ol"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 引用和代码 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        @click="editor?.chain().focus().toggleBlockquote().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('blockquote') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="引用"
                      >
                        <i class="fas fa-quote-left"></i>
                      </button>
                      <button 
                        @click="editor?.chain().focus().toggleCodeBlock().run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('codeBlock') }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="代码块"
                      >
                        <i class="fas fa-code"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 表格 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        type="button"
                        @click="insertTable"
                        class="p-2 rounded hover:bg-gray-200"
                        title="插入表格"
                      >
                        <i class="fas fa-table"></i>
                      </button>
                      
                      <!-- 表格行数控制 -->
                      <div class="relative inline-block">
                        <button 
                          type="button"
                          @click="showTableMenu = !showTableMenu"
                          class="p-2 rounded hover:bg-gray-200 flex items-center space-x-1"
                          title="自定义表格"
                        >
                          <i class="fas fa-table"></i>
                          <i class="fas fa-chevron-down text-xs"></i>
                        </button>
                        <div v-if="showTableMenu" class="absolute top-full left-0 mt-1 bg-white border border-gray-300 rounded-lg shadow-lg z-10 min-w-[200px]">
                          <div class="p-3">
                            <div class="grid grid-cols-2 gap-2 mb-3">
                              <div>
                                <label class="block text-xs text-gray-600 mb-1">行数</label>
                                <input 
                                  v-model="tableRows" 
                                  type="number" 
                                  min="1" 
                                  max="10"
                                  class="w-full px-2 py-1 text-sm border border-gray-300 rounded"
                                >
                              </div>
                              <div>
                                <label class="block text-xs text-gray-600 mb-1">列数</label>
                                <input 
                                  v-model="tableCols" 
                                  type="number" 
                                  min="1" 
                                  max="10"
                                  class="w-full px-2 py-1 text-sm border border-gray-300 rounded"
                                >
                              </div>
                            </div>
                            <button 
                              type="button"
                              @click="insertCustomTable(parseInt(tableRows), parseInt(tableCols)); showTableMenu = false"
                              class="w-full px-3 py-1 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
                            >
                              插入表格
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 图片 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        type="button"
                        @click="insertImage"
                        class="p-2 rounded hover:bg-gray-200"
                        title="插入图片URL"
                      >
                        <i class="fas fa-image"></i>
                      </button>
                      <button 
                        type="button"
                        @click="uploadImage"
                        class="p-2 rounded hover:bg-gray-200"
                        title="上传图片"
                      >
                        <i class="fas fa-upload"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 对齐 -->
                    <div class="flex items-center space-x-1">
                      <button 
                        type="button"
                        @click="editor?.chain().focus().setTextAlign('left').run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'left' }) }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="左对齐"
                      >
                        <i class="fas fa-align-left"></i>
                      </button>
                      <button 
                        type="button"
                        @click="editor?.chain().focus().setTextAlign('center').run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'center' }) }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="居中"
                      >
                        <i class="fas fa-align-center"></i>
                      </button>
                      <button 
                        type="button"
                        @click="editor?.chain().focus().setTextAlign('right').run()"
                        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'right' }) }"
                        class="p-2 rounded hover:bg-gray-200"
                        title="右对齐"
                      >
                        <i class="fas fa-align-right"></i>
                      </button>
                    </div>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 高亮 -->
                    <button 
                      type="button"
                      @click="editor?.chain().focus().toggleHighlight().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('highlight') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="高亮"
                    >
                      <i class="fas fa-highlighter"></i>
                    </button>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 链接 -->
                    <button 
                      type="button"
                      @click="setLink"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入链接"
                    >
                      <i class="fas fa-link"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editor?.chain().focus().unsetLink().run()"
                      :disabled="!editor?.isActive('link')"
                      class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed"
                      title="取消链接"
                    >
                      <i class="fas fa-unlink"></i>
                    </button>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- 撤销重做 -->
                    <button 
                      type="button"
                      @click="editor?.chain().focus().undo().run()"
                      :disabled="!editor?.can().undo()"
                      class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed"
                      title="撤销"
                    >
                      <i class="fas fa-undo"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editor?.chain().focus().redo().run()"
                      :disabled="!editor?.can().redo()"
                      class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed"
                      title="重做"
                    >
                      <i class="fas fa-redo"></i>
                    </button>
                    
                    <div class="w-px h-6 bg-gray-300"></div>
                    
                    <!-- Markdown -->
                    <button 
                      type="button"
                      @click="importMarkdown"
                      class="px-2 py-1 rounded hover:bg-gray-200 text-sm font-semibold"
                      title="导入Markdown"
                    >
                      <i class="fab fa-markdown"></i>
                    </button>
                  </div>
                  
                  <!-- 编辑器内容区域 -->
                  <div class="p-4 min-h-[300px] max-h-[500px] overflow-y-auto" @click="selectImage">
                    <editor-content :editor="editor" class="prose-editor" />
                  </div>
                </div>
              </div>

              <!-- 标签 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">标签</label>
                <div class="space-y-3">
                  <!-- 标签输入区域 -->
                  <div class="flex items-center space-x-2">
                    <input 
                      v-model="newTag" 
                      type="text" 
                      placeholder="输入标签名称"
                      @keyup.enter="addEditTag"
                      class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    >
                    <button 
                      type="button"
                      @click="addEditTag"
                      :disabled="!newTag.trim()"
                      class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed flex items-center space-x-1"
                    >
                      <i class="fas fa-plus"></i>
                      <span>添加</span>
                    </button>
                  </div>
                  
                  <!-- 已添加的标签列表 -->
                  <div v-if="editForm.tags.length > 0" class="flex flex-wrap gap-2">
                    <div 
                      v-for="(tag, index) in editForm.tags" 
                      :key="index"
                      class="flex items-center space-x-2 bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-sm"
                    >
                      <span>{{ tag }}</span>
                      <button 
                        type="button"
                        @click="removeEditTag(index)"
                        class="text-blue-500 hover:text-blue-700"
                      >
                        <i class="fas fa-times"></i>
                      </button>
                    </div>
                  </div>
                  
                  <!-- 常用标签建议 -->
                  <div class="flex flex-wrap gap-2">
                    <span class="text-sm text-gray-500 mr-2">常用标签:</span>
                    <button 
                      v-for="suggestedTag in suggestedTags" 
                      :key="suggestedTag"
                      type="button"
                      @click="addEditSuggestedTag(suggestedTag)"
                      class="px-2 py-1 text-xs bg-gray-100 text-gray-600 rounded hover:bg-gray-200"
                    >
                      {{ suggestedTag }}
                    </button>
                  </div>
                </div>
              </div>

              <!-- 提交按钮 -->
              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeEditRecord"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                  保存修改
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 新建问题弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCreateIssue" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-2xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">{{ currentIssue ? '编辑问题' : '记录问题' }}</h3>
              <button 
                @click="closeCreateIssue"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <form @submit.prevent="submitIssue" class="space-y-6">
              <!-- 项目选择 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">关联项目 *</label>
                <select v-model="issueForm.projectId" required class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500">
                  <option value="">请选择项目</option>
                  <option v-for="project in projects" :key="project.id" :value="project.id">
                    {{ project.name }}
                  </option>
                </select>
              </div>

              <!-- 问题标题 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">问题标题 *</label>
                <input 
                  v-model="issueForm.issueTitle" 
                  type="text" 
                  required
                  placeholder="请输入问题标题"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500"
                >
              </div>

              <!-- 问题描述 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">问题描述 *</label>
                <textarea 
                  v-model="issueForm.issueDescription" 
                  rows="4"
                  required
                  placeholder="请详细描述遇到的问题..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500"
                ></textarea>
              </div>

              <!-- 问题类型 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">问题类型 *</label>
                <select v-model="issueForm.issueType" required class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500">
                  <option value="technical">技术问题</option>
                  <option value="requirement">需求问题</option>
                  <option value="design">设计问题</option>
                  <option value="other">其他问题</option>
                </select>
              </div>

              <!-- 优先级 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">优先级 *</label>
                <div class="grid grid-cols-4 gap-3">
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="issueForm.priority === 'urgent' ? 'border-red-500 bg-red-50' : 'border-gray-300 hover:border-red-300'">
                    <input type="radio" v-model="issueForm.priority" value="urgent" class="hidden">
                    <span class="text-sm font-medium" :class="issueForm.priority === 'urgent' ? 'text-red-700' : 'text-gray-700'">🔴 紧急</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="issueForm.priority === 'high' ? 'border-orange-500 bg-orange-50' : 'border-gray-300 hover:border-orange-300'">
                    <input type="radio" v-model="issueForm.priority" value="high" class="hidden">
                    <span class="text-sm font-medium" :class="issueForm.priority === 'high' ? 'text-orange-700' : 'text-gray-700'">🟠 高</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="issueForm.priority === 'medium' ? 'border-yellow-500 bg-yellow-50' : 'border-gray-300 hover:border-yellow-300'">
                    <input type="radio" v-model="issueForm.priority" value="medium" class="hidden">
                    <span class="text-sm font-medium" :class="issueForm.priority === 'medium' ? 'text-yellow-700' : 'text-gray-700'">🟡 中</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="issueForm.priority === 'low' ? 'border-gray-500 bg-gray-50' : 'border-gray-300 hover:border-gray-400'">
                    <input type="radio" v-model="issueForm.priority" value="low" class="hidden">
                    <span class="text-sm font-medium" :class="issueForm.priority === 'low' ? 'text-gray-700' : 'text-gray-600'">⚪ 低</span>
                  </label>
                </div>
              </div>

              <!-- 问题状态（编辑时显示） -->
              <div v-if="currentIssue">
                <label class="block text-sm font-medium text-gray-700 mb-2">问题状态 *</label>
                <div class="grid grid-cols-3 gap-3">
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="issueForm.status === 'open' ? 'border-gray-500 bg-gray-50' : 'border-gray-300 hover:border-gray-400'">
                    <input type="radio" v-model="issueForm.status" value="open" class="hidden">
                    <span class="text-sm font-medium" :class="issueForm.status === 'open' ? 'text-gray-700' : 'text-gray-600'">⏸️ 待处理</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="issueForm.status === 'in_progress' ? 'border-blue-500 bg-blue-50' : 'border-gray-300 hover:border-blue-300'">
                    <input type="radio" v-model="issueForm.status" value="in_progress" class="hidden">
                    <span class="text-sm font-medium" :class="issueForm.status === 'in_progress' ? 'text-blue-700' : 'text-gray-700'">🔄 处理中</span>
                  </label>
                  <label class="flex items-center justify-center p-3 border-2 rounded-lg cursor-pointer transition-all"
                    :class="issueForm.status === 'resolved' ? 'border-green-500 bg-green-50' : 'border-gray-300 hover:border-green-300'">
                    <input type="radio" v-model="issueForm.status" value="resolved" class="hidden">
                    <span class="text-sm font-medium" :class="issueForm.status === 'resolved' ? 'text-green-700' : 'text-gray-700'">✅ 已解决</span>
                  </label>
                </div>
              </div>

              <!-- 操作按钮 -->
              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeCreateIssue"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-orange-600 text-white rounded-lg hover:bg-orange-700"
                >
                  {{ currentIssue ? '保存修改' : '记录问题' }}
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 处理问题弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showResolveIssue" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-2xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">处理问题</h3>
              <button 
                @click="closeResolveIssue"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <!-- 问题信息 -->
            <div class="mb-6 p-4 bg-gray-50 rounded-lg">
              <h4 class="font-medium text-gray-900 mb-2">{{ currentIssue?.issueTitle }}</h4>
              <p class="text-sm text-gray-600 mb-3">{{ currentIssue?.issueDescription }}</p>
              <div class="flex items-center space-x-3 text-sm text-gray-500">
                <span>项目：{{ getProjectName(currentIssue?.projectId) }}</span>
                <span>优先级：{{ getPriorityText(currentIssue?.priority) }}</span>
                <span>类型：{{ getIssueTypeText(currentIssue?.issueType) }}</span>
              </div>
            </div>

            <form @submit.prevent="submitResolveIssue" class="space-y-6">
              <!-- 解决方案 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">解决方案 *</label>
                <textarea 
                  v-model="resolveForm.solution" 
                  rows="6"
                  required
                  placeholder="请详细描述解决方案..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500"
                ></textarea>
              </div>

              <!-- 操作按钮 -->
              <div class="flex justify-end space-x-4">
                <button 
                  type="button" 
                  @click="closeResolveIssue"
                  class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                >
                  取消
                </button>
                <button 
                  type="submit" 
                  class="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
                >
                  标记为已解决
                </button>
              </div>
            </form>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 心得详情模态框 -->
    <transition name="modal" appear>
      <div v-if="selectedRecord" class="fixed inset-0 z-50 overflow-y-auto">
        <div class="flex items-center justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0">
          <!-- 背景遮罩 -->
          <div class="fixed inset-0 transition-opacity bg-gray-500 bg-opacity-75" @click="closeRecordDetail"></div>

          <!-- 模态框内容 -->
          <div class="inline-block w-full max-w-4xl px-4 pt-5 pb-4 overflow-hidden text-left align-bottom transition-all transform bg-white rounded-lg shadow-xl sm:my-8 sm:align-middle sm:p-6">
            <div class="absolute top-0 right-0 pt-4 pr-4">
              <button @click="closeRecordDetail" class="text-gray-400 hover:text-gray-600 transition-colors duration-200">
                <i class="fa-solid fa-times text-xl"></i>
              </button>
            </div>
            
            <div class="sm:flex sm:items-start">
              <div class="w-full">
                <!-- 标题和基本信息 -->
                <div class="mb-6">
                  <h3 class="text-2xl font-bold text-gray-900 mb-2">{{ selectedRecord.title }}</h3>
                  <div class="flex items-center gap-4 text-sm text-gray-500 mb-4">
                    <span>更新时间：{{ selectedRecord.createTime }}</span>
                    <span>项目：{{ selectedRecord.projectName || '未知项目' }}</span>
                  </div>
                  <div class="flex flex-wrap gap-2">
                    <span v-for="tag in selectedRecord.tags" :key="tag" class="px-3 py-1 rounded-full text-sm font-medium" :class="getProjectTagClasses(selectedRecord.projectId || 0)">{{ tag }}</span>
                  </div>
                </div>

                <!-- 摘要 -->
                <div class="mb-6">
                  <h4 class="text-lg font-semibold text-gray-800 mb-2">摘要</h4>
                  <p class="text-gray-600 leading-relaxed">{{ selectedRecord.summary }}</p>
                </div>

                <!-- 详细内容 -->
                <div class="mb-6">
                  <h4 class="text-lg font-semibold text-gray-800 mb-2">详细内容</h4>
                  <div class="bg-gray-50 rounded-lg p-4">
                    <div class="prose max-w-none">
                      <div v-html="selectedRecord.content" class="text-gray-700 leading-relaxed"></div>
                    </div>
                  </div>
                </div>

                <!-- 技术栈信息 -->
                <div v-if="selectedRecord.techStack && selectedRecord.techStack.length > 0" class="mb-6">
                  <h4 class="text-lg font-semibold text-gray-800 mb-2">技术栈</h4>
                  <div class="flex flex-wrap gap-2">
                    <span v-for="tech in selectedRecord.techStack" :key="tech" class="bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-sm">{{ tech }}</span>
                  </div>
                </div>

                <!-- 项目类型信息 -->
                <div v-if="selectedRecord.projectType && selectedRecord.projectType.length > 0" class="mb-6">
                  <h4 class="text-lg font-semibold text-gray-800 mb-2">项目类型</h4>
                  <div class="flex flex-wrap gap-2">
                    <span v-for="type in selectedRecord.projectType" :key="type" class="bg-green-100 text-green-700 px-3 py-1 rounded-full text-sm">{{ type }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, onActivated, onBeforeUnmount } from 'vue'
import { 
  getCheckinItemList, 
  checkin as checkinAPI, 
  cancelCheckin as cancelCheckinAPI, 
  checkCheckinStatus,
  type CheckinItem 
} from '@/api/checkin'
import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Table from '@tiptap/extension-table'
import TableRow from '@tiptap/extension-table-row'
import TableCell from '@tiptap/extension-table-cell'
import TableHeader from '@tiptap/extension-table-header'
import Image from '@tiptap/extension-image'
import Underline from '@tiptap/extension-underline'
import Strike from '@tiptap/extension-strike'
import TextAlign from '@tiptap/extension-text-align'
import CodeBlock from '@tiptap/extension-code-block'
import Placeholder from '@tiptap/extension-placeholder'
import Highlight from '@tiptap/extension-highlight'
import Link from '@tiptap/extension-link'
import request from '@/api/request'
import { getAllNotes, type Note } from '@/services/noteService'

// 响应式数据
const projects = ref<any[]>([])
const tasks = ref<any[]>([])
const notes = ref<any[]>([])

// 默认标签（如果需要）
const defaultProjectTags = ['技术', '学习', '工作']

// 加载数据
const loadData = async () => {
  try {
    const [projectRes, taskRes] = await Promise.all([
      request.get('/project/list'),
      request.get('/task/list')
    ])
    
    if (projectRes.code === 200) {
      projects.value = projectRes.data || []
      console.log('【项目执行】加载项目数据:', projects.value.length, '个')
    }
    if (taskRes.code === 200) {
      tasks.value = taskRes.data || []
      console.log('【项目执行】加载任务数据:', tasks.value.length, '个')
    }
    
    // 使用 noteService 获取笔记数据，自动处理数组字段
    const notesData = await getAllNotes()
    notes.value = notesData
    console.log('【项目执行】加载笔记数据:', notes.value.length, '个')
  } catch (error) {
    console.error('【项目执行】加载数据失败:', error)
  }
}

// 加载项目心得数据
const loadProjectInsights = async () => {
  try {
    const response = await request.get('/project/insight/list')
    if (response.code === 200) {
      // 处理tags字段：从逗号分隔的字符串转换为数组
      projectRecords.value = (response.data || []).map((item: any) => ({
        ...item,
        tags: item.tags ? item.tags.split(',').filter((t: string) => t.trim()) : []
      }))
      console.log('【项目执行】加载项目心得数据:', projectRecords.value.length, '个')
    }
  } catch (error) {
    console.error('【项目执行】加载项目心得失败:', error)
  }
}

// 界面控制
const showTaskExecution = ref(false)
const showProjectRecord = ref(false)
const showCreateRecord = ref(false)
const showEditRecord = ref(false)
const taskViewMode = ref('list') // 任务视图模式：list, project, time
const showTableMenu = ref(false)
const tableRows = ref(3)
const tableCols = ref(3)

// 折叠状态控制
const collapsedProjects = ref<number[]>([])
const collapsedTimeGroups = ref<string[]>([])

// 搜索和筛选
const taskSearchQuery = ref('')
const taskFilterProject = ref('')
const taskFilterStatus = ref('')

// 富文本编辑器
const editor = useEditor({
  content: '',
  extensions: [
    StarterKit.configure({
      heading: {
        levels: [1, 2, 3]
      },
      bulletList: {
        keepMarks: true,
        keepAttributes: false,
      },
      orderedList: {
        keepMarks: true,
        keepAttributes: false,
      },
    }),
    Table.configure({
      resizable: true,
      HTMLAttributes: {
        class: 'border-collapse border border-gray-300 w-full',
      },
    }),
    TableRow.configure({
      HTMLAttributes: {
        class: 'border border-gray-300',
      },
    }),
    TableHeader.configure({
      HTMLAttributes: {
        class: 'border border-gray-300 bg-gray-100 font-bold p-2',
      },
    }),
    TableCell.configure({
      HTMLAttributes: {
        class: 'border border-gray-300 p-2',
      },
    }),
    Image.configure({
      HTMLAttributes: {
        class: 'max-w-full h-auto cursor-pointer',
      },
    }),
    Underline,
    Strike,
    TextAlign.configure({
      types: ['heading', 'paragraph'],
    }),
    CodeBlock.configure({
      HTMLAttributes: {
        class: 'bg-gray-100 p-4 rounded-lg font-mono text-sm',
      },
    }),
    Highlight.configure({
      multicolor: false,
    }),
    Link.configure({
      openOnClick: false,
      HTMLAttributes: {
        class: 'text-blue-600 underline cursor-pointer',
      },
    }),
    Placeholder.configure({
      placeholder: '开始编写你的项目心得...',
    }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm sm:prose lg:prose-lg xl:prose-2xl mx-auto focus:outline-none min-h-[300px] p-4',
    },
  },
  onUpdate: ({ editor }) => {
    // 根据当前模式更新对应的表单
    if (showEditRecord.value) {
      editForm.content = editor.getHTML()
    } else {
      projectForm.content = editor.getHTML()
    }
  },
})

// 文件输入引用
const imageInput = ref<HTMLInputElement>()
const fileInput = ref<HTMLInputElement>()

// 项目表单数据
const projectForm = reactive({
  projectId: '',
  title: '',
  summary: '',
  content: '',
  tags: [] as string[]
})

// 编辑表单数据
const editForm = reactive({
  id: 0,
  projectId: '',
  title: '',
  summary: '',
  content: '',
  tags: [] as string[]
})

// 标签相关
const newTag = ref('')
const suggestedTags = defaultProjectTags

// 项目心得记录（从后端加载）
const projectRecords = ref<any[]>([])

// 笔记调用相关数据
const selectedProjectForNotes = ref('')
const projectNotes = ref<Note[]>([])
const showAddNoteDialog = ref(false)
const noteSearchQuery = ref('')
const noteFilterType = ref('')
const showNoteDetail = ref(false)
const selectedNoteDetail = ref<Note | null>(null)
const noteProjectSearchQuery = ref('') // 项目搜索关键词
const noteProjectCategoryFilter = ref('') // 项目分类筛选

// 归档批量导入相关
const showArchiveImportDialog = ref(false)
const archives = ref<any[]>([])
const isLoadingArchives = ref(false)

// 保留素材详情相关（用于通用笔记详情显示）
const showMaterialDetail = ref(false)
const selectedMaterial = ref<Note | null>(null)

// 筛选后的任务
const filteredTasks = computed(() => {
  let filtered = tasks.value
  
  // 按项目筛选
  if (taskFilterProject.value) {
    filtered = filtered.filter(task => task.projectId === parseInt(taskFilterProject.value))
  }
  
  // 按状态筛选
  if (taskFilterStatus.value) {
    filtered = filtered.filter(task => task.status === taskFilterStatus.value)
  }
  
  // 按搜索关键词筛选
  if (taskSearchQuery.value) {
    const query = taskSearchQuery.value.toLowerCase()
    filtered = filtered.filter(task => 
      task.name.toLowerCase().includes(query) ||
      task.description.toLowerCase().includes(query)
    )
  }
  
  return filtered
})

// 筛选后的项目列表（用于笔记调用）
const filteredProjectsForNotes = computed(() => {
  let filtered = projects.value
  
  // 按分类筛选
  if (noteProjectCategoryFilter.value) {
    filtered = filtered.filter(project => project.category === noteProjectCategoryFilter.value)
  }
  
  // 按搜索关键词筛选
  if (noteProjectSearchQuery.value) {
    const query = noteProjectSearchQuery.value.toLowerCase()
    filtered = filtered.filter(project => 
      project.name.toLowerCase().includes(query) ||
      (project.description && project.description.toLowerCase().includes(query))
    )
  }
  
  return filtered
})

// 筛选后的可用笔记（排除已关联的）
const filteredAvailableNotes = computed(() => {
  const projectNoteIds = new Set(projectNotes.value.map(n => n.id))
  let filtered = notes.value.filter(note => !projectNoteIds.has(note.id))
  
  // 按类型筛选
  if (noteFilterType.value) {
    filtered = filtered.filter(note => note.type === noteFilterType.value)
  }
  
  // 按搜索关键词筛选
  if (noteSearchQuery.value) {
    const query = noteSearchQuery.value.toLowerCase()
    filtered = filtered.filter(note => 
      note.title.toLowerCase().includes(query) ||
      note.summary.toLowerCase().includes(query) ||
      (Array.isArray(note.tags) && note.tags.some(tag => tag.toLowerCase().includes(query)))
    )
  }
  
  return filtered
})

// 按项目分类的任务
const groupedProjects = computed(() => {
  const filtered = filteredTasks.value
  const grouped = projects.value.map(project => {
    const projectTasks = filtered.filter(task => task.projectId === project.id)
    return {
      ...project,
      tasks: projectTasks,
      categoryText: getCategoryText(project.category)
    }
  }).filter(project => project.tasks.length > 0)
  
  return grouped
})

// 按时间分类的任务
const groupedByTime = computed(() => {
  const filtered = filteredTasks.value
  const now = new Date()
  const today = new Date(now.getFullYear(), now.getMonth(), now.getDate())
  const tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000)
  const nextWeek = new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000)
  
  const groups = [
    {
      key: 'overdue',
      title: '已逾期',
      description: '截止日期已过的任务',
      tasks: filtered.filter(task => {
        const deadline = new Date(task.deadline)
        return deadline < today && task.status !== 'completed'
      })
    },
    {
      key: 'today',
      title: '今日截止',
      description: '今天需要完成的任务',
      tasks: filtered.filter(task => {
        const deadline = new Date(task.deadline)
        return deadline >= today && deadline < tomorrow
      })
    },
    {
      key: 'tomorrow',
      title: '明日截止',
      description: '明天需要完成的任务',
      tasks: filtered.filter(task => {
        const deadline = new Date(task.deadline)
        return deadline >= tomorrow && deadline < new Date(tomorrow.getTime() + 24 * 60 * 60 * 1000)
      })
    },
    {
      key: 'this-week',
      title: '本周截止',
      description: '本周内需要完成的任务',
      tasks: filtered.filter(task => {
        const deadline = new Date(task.deadline)
        return deadline >= new Date(tomorrow.getTime() + 24 * 60 * 60 * 1000) && deadline < nextWeek
      })
    },
    {
      key: 'future',
      title: '未来任务',
      description: '下周及以后的任务',
      tasks: filtered.filter(task => {
        const deadline = new Date(task.deadline)
        return deadline >= nextWeek
      })
    }
  ]
  
  return groups.filter(group => group.tasks.length > 0)
})

// 获取分类文本
const getCategoryText = (category: string) => {
  const texts = {
    defense: '战争行动',
    construction: '工程建设',
    diplomatic: '外交行动'
  }
  return texts[category as keyof typeof texts] || '未知分类'
}

// 获取项目状态样式
const getProjectStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-600',
    planning: 'bg-yellow-100 text-yellow-600',
    completed: 'bg-blue-100 text-blue-600'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

// 获取项目状态文本
const getProjectStatusText = (status: string) => {
  const texts = {
    active: '进行中',
    planning: '规划中',
    completed: '已完成'
  }
  return texts[status as keyof typeof texts] || '未知'
}

// 获取项目名称
const getProjectName = (projectId: number) => {
  const project = projects.value.find(p => p.id === projectId)
  return project ? project.name : '未知项目'
}

// 获取任务状态样式
const getTaskStatusClass = (status: string) => {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-600',
    'in-progress': 'bg-blue-100 text-blue-600',
    completed: 'bg-green-100 text-green-600',
    stopped: 'bg-red-100 text-red-600'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

// 获取任务状态文本
const getTaskStatusText = (status: string) => {
  const texts = {
    pending: '待开始',
    'in-progress': '进行中',
    completed: '已完成',
    stopped: '已停止'
  }
  return texts[status as keyof typeof texts] || '未知'
}

// 更新任务状态
const updateTaskStatus = async (task: any, status: string) => {
  try {
    // 调用后端API更新任务状态
    const response = await request.put(`/task`, {
      ...task,
      status: status
    })
    
    if (response.code === 200) {
      // 更新成功后，更新前端数据
      const taskIndex = tasks.value.findIndex(t => t.id === task.id)
      if (taskIndex !== -1) {
        const updatedTask = { ...tasks.value[taskIndex], status }
        tasks.value.splice(taskIndex, 1, updatedTask)
      }
      alert(`任务状态已更新为：${getTaskStatusText(status)}`)
    } else {
      alert('更新失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('更新任务状态失败:', error)
    alert('更新失败，请稍后重试')
  }
}

// 更新任务的 newProgress 字段
const updateTaskProgress = (task: any, progress: string) => {
  const taskIndex = tasks.value.findIndex(t => t.id === task.id)
  if (taskIndex !== -1) {
    const updatedTask = { ...tasks.value[taskIndex], newProgress: progress }
    tasks.value.splice(taskIndex, 1, updatedTask)
  }
}

// 添加执行记录
const addExecutionRecord = async (task: any) => {
  if (!task.newProgress.trim()) {
    alert('请输入执行进度')
    return
  }
  
  // 停止状态的任务不能添加执行记录
  if (task.status === 'stopped') {
    alert('已停止的任务不能添加执行记录，请先重启任务')
    return
  }
  
  try {
    const newRecord = {
      id: Date.now(),
      date: new Date().toLocaleDateString(),
      progress: task.newProgress
    }
    
    const taskIndex = tasks.value.findIndex(t => t.id === task.id)
    if (taskIndex !== -1) {
      const currentTask = tasks.value[taskIndex]
      const updatedExecutionRecords = [...(currentTask.executionRecords || []), newRecord]
      const newStatus = currentTask.status === 'pending' ? 'in-progress' : currentTask.status
      
      // 调用后端API更新任务
      const response = await request.put(`/task`, {
        ...currentTask,
        executionRecords: JSON.stringify(updatedExecutionRecords),
        status: newStatus
      })
      
      if (response.code === 200) {
        // 更新前端数据
        const updatedTask = { 
          ...currentTask, 
          executionRecords: updatedExecutionRecords,
          newProgress: '',
          status: newStatus
        }
        tasks.value.splice(taskIndex, 1, updatedTask)
        alert('执行记录已添加！')
      } else {
        alert('保存失败：' + (response.message || '未知错误'))
      }
    }
  } catch (error) {
    console.error('添加执行记录失败:', error)
    alert('保存失败，请稍后重试')
  }
}

// 提交项目记录
const submitProjectRecord = async () => {
  if (!projectForm.projectId || !projectForm.title || !projectForm.summary) {
    alert('请填写完整信息')
    return
  }

  try {
    const response = await request.post('/project/insight', {
      projectId: parseInt(projectForm.projectId),
      title: projectForm.title,
      summary: projectForm.summary,
      content: projectForm.content,
      tags: projectForm.tags.join(',') // 转换为逗号分隔的字符串
    })

    if (response.code === 200) {
      await loadProjectInsights() // 重新加载列表
      closeCreateRecord()
      alert('项目心得保存成功！')
    } else {
      alert('保存失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('保存项目心得失败:', error)
    alert('保存失败，请稍后重试')
  }
}

// 关闭创建记录表单
const closeCreateRecord = () => {
  showCreateRecord.value = false
  projectForm.projectId = ''
  projectForm.title = ''
  projectForm.summary = ''
  projectForm.content = ''
  projectForm.tags = []
  newTag.value = ''
  // 清空编辑器内容
  editor.value?.commands.setContent('')
}

// 添加标签
const addTag = () => {
  if (newTag.value.trim() && !projectForm.tags.includes(newTag.value.trim())) {
    projectForm.tags.push(newTag.value.trim())
    newTag.value = ''
  }
}

// 移除标签
const removeTag = (index: number) => {
  projectForm.tags.splice(index, 1)
}

// 添加建议标签
const addSuggestedTag = (tag: string) => {
  if (!projectForm.tags.includes(tag)) {
    projectForm.tags.push(tag)
  }
}

// 搜索和筛选
const projectRecordSearchQuery = ref('')
const projectRecordFilterProject = ref('')

// 筛选后的项目记录
const filteredProjectRecords = computed(() => {
  let filtered = projectRecords.value
  
  // 按项目筛选
  if (projectRecordFilterProject.value) {
    filtered = filtered.filter(record => record.projectId === parseInt(projectRecordFilterProject.value))
  }
  
  // 按搜索关键词筛选
  if (projectRecordSearchQuery.value) {
    const query = projectRecordSearchQuery.value.toLowerCase()
    filtered = filtered.filter(record => 
      record.title.toLowerCase().includes(query) ||
      record.summary.toLowerCase().includes(query) ||
      (record.content && record.content.toLowerCase().includes(query)) ||
      record.tags.some(tag => tag.toLowerCase().includes(query))
    )
  }
  
  return filtered
})

// 插入表格
const insertTable = () => {
  editor.value?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

// 插入自定义行数的表格
const insertCustomTable = (rows: number, cols: number) => {
  editor.value?.chain().focus().insertTable({ rows, cols, withHeaderRow: true }).run()
}

// 插入图片
const insertImage = () => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editor.value?.chain().focus().setImage({ src: url }).run()
  }
}

// 上传图片
const uploadImage = () => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      const reader = new FileReader()
      reader.onload = (e) => {
        const url = e.target?.result as string
        editor.value?.chain().focus().setImage({ src: url }).run()
      }
      reader.readAsDataURL(file)
    }
  }
  input.click()
}

// 设置链接
const setLink = () => {
  const url = window.prompt('请输入链接地址:')
  if (url) {
    editor.value?.chain().focus().setLink({ href: url }).run()
  }
}

// 导入Markdown
const importMarkdown = () => {
  const markdown = window.prompt('请粘贴Markdown内容:')
  if (markdown) {
    // 简单的Markdown转换（实际项目中可以使用markdown-it等库）
    editor.value?.commands.setContent(markdown)
  }
}

// 选择图片
const selectImage = (event: Event) => {
  const target = event.target as HTMLElement
  if (target.tagName === 'IMG') {
    selectedImage.value = target
    showImageResizer.value = true
  }
}

// 调整图片大小
const resizeImage = (width: number, height: number) => {
  if (selectedImage.value) {
    selectedImage.value.style.width = `${width}px`
    selectedImage.value.style.height = `${height}px`
  }
}

// 重置图片大小
const resetImageSize = () => {
  if (selectedImage.value) {
    selectedImage.value.style.width = ''
    selectedImage.value.style.height = ''
  }
  showImageResizer.value = false
  selectedImage.value = null
}

// 处理图片上传
const handleImageUpload = (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      const result = e.target?.result as string
      editor.value?.chain().focus().setImage({ src: result }).run()
    }
    reader.readAsDataURL(file)
  }
  // 清空input
  if (target) target.value = ''
}

// 处理文件上传
const handleFileUpload = (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  if (file) {
    // 这里可以处理文件上传逻辑
    // 目前只是显示文件名
    const fileName = file.name
    editor.value?.chain().focus().insertContent(`<p><strong>附件：</strong>${fileName}</p>`).run()
  }
  // 清空input
  if (target) target.value = ''
}

// 编辑项目记录
const editProjectRecord = (record: any) => {
  // 填充编辑表单
  editForm.id = record.id
  editForm.projectId = record.projectId.toString()
  editForm.title = record.title
  editForm.summary = record.summary
  editForm.content = record.content
  editForm.tags = [...record.tags]
  
  // 设置编辑器内容
  editor.value?.commands.setContent(record.content || '')
  
  // 显示编辑弹窗
  showEditRecord.value = true
}

// 删除项目记录
const deleteProjectRecord = async (id: number) => {
  if (!confirm('确定要删除这条项目心得吗？此操作不可撤销。')) return

  try {
    const response = await request.delete(`/project/insight/${id}`)
    if (response.code === 200) {
      await loadProjectInsights() // 重新加载列表
      alert('项目心得已删除！')
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除项目心得失败:', error)
    alert('删除失败，请稍后重试')
  }
}

// 提交编辑记录
const submitEditRecord = async () => {
  if (!editForm.projectId || !editForm.title || !editForm.summary) {
    alert('请填写完整信息')
    return
  }

  try {
    const response = await request.put(`/project/insight/${editForm.id}`, {
      projectId: parseInt(editForm.projectId),
      title: editForm.title,
      summary: editForm.summary,
      content: editForm.content,
      tags: editForm.tags.join(',') // 转换为逗号分隔的字符串
    })

    if (response.code === 200) {
      await loadProjectInsights() // 重新加载列表
      closeEditRecord()
      alert('项目心得已更新！')
    } else {
      alert('更新失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('更新项目心得失败:', error)
    alert('更新失败，请稍后重试')
  }
}

// 关闭编辑记录弹窗
const closeEditRecord = () => {
  showEditRecord.value = false
  editForm.projectId = ''
  editForm.title = ''
  editForm.summary = ''
  editForm.content = ''
  editForm.tags = []
  newTag.value = ''
  // 清空编辑器内容
  editor.value?.commands.setContent('')
}

// 添加编辑标签
const addEditTag = () => {
  if (newTag.value.trim() && !editForm.tags.includes(newTag.value.trim())) {
    editForm.tags.push(newTag.value.trim())
    newTag.value = ''
  }
}

// 移除编辑标签
const removeEditTag = (index: number) => {
  editForm.tags.splice(index, 1)
}

// 添加编辑建议标签
const addEditSuggestedTag = (tag: string) => {
  if (!editForm.tags.includes(tag)) {
    editForm.tags.push(tag)
  }
}

// 项目心得详情相关
const selectedRecord = ref<any>(null)

// 获取项目任务
const getProjectTasks = (projectId: number) => {
  return tasks.value.filter(task => task.projectId === projectId)
}

// 获取项目记录
const getProjectRecords = (projectId: number) => {
  return projectRecords.value.filter(record => record.projectId === projectId)
}

// 颜色主题配置
const colorThemes = [
  {
    bg: 'bg-gradient-to-br from-blue-50 to-indigo-50',
    border: 'border-blue-300',
    icon: 'bg-gradient-to-br from-blue-500 to-indigo-600',
    status: 'bg-blue-100 text-blue-700',
    taskBorder: 'border-blue-100',
    recordBorder: 'border-purple-100',
    tag: 'bg-purple-100 text-purple-700'
  },
  {
    bg: 'bg-gradient-to-br from-emerald-50 to-teal-50',
    border: 'border-emerald-300',
    icon: 'bg-gradient-to-br from-emerald-500 to-teal-600',
    status: 'bg-emerald-100 text-emerald-700',
    taskBorder: 'border-emerald-100',
    recordBorder: 'border-teal-100',
    tag: 'bg-teal-100 text-teal-700'
  },
  {
    bg: 'bg-gradient-to-br from-purple-50 to-violet-50',
    border: 'border-purple-300',
    icon: 'bg-gradient-to-br from-purple-500 to-violet-600',
    status: 'bg-purple-100 text-purple-700',
    taskBorder: 'border-purple-100',
    recordBorder: 'border-violet-100',
    tag: 'bg-violet-100 text-violet-700'
  },
  {
    bg: 'bg-gradient-to-br from-amber-50 to-orange-50',
    border: 'border-amber-300',
    icon: 'bg-gradient-to-br from-amber-500 to-orange-600',
    status: 'bg-amber-100 text-amber-700',
    taskBorder: 'border-amber-100',
    recordBorder: 'border-orange-100',
    tag: 'bg-orange-100 text-orange-700'
  },
  {
    bg: 'bg-gradient-to-br from-rose-50 to-pink-50',
    border: 'border-rose-300',
    icon: 'bg-gradient-to-br from-rose-500 to-pink-600',
    status: 'bg-rose-100 text-rose-700',
    taskBorder: 'border-rose-100',
    recordBorder: 'border-pink-100',
    tag: 'bg-pink-100 text-pink-700'
  },
  {
    bg: 'bg-gradient-to-br from-cyan-50 to-sky-50',
    border: 'border-cyan-300',
    icon: 'bg-gradient-to-br from-cyan-500 to-sky-600',
    status: 'bg-cyan-100 text-cyan-700',
    taskBorder: 'border-cyan-100',
    recordBorder: 'border-sky-100',
    tag: 'bg-sky-100 text-sky-700'
  }
]

// 根据项目ID获取颜色主题
const getProjectTheme = (projectId: number) => {
  const index = projectId % colorThemes.length
  return colorThemes[index]
}

// 获取项目颜色类
const getProjectColorClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return `${theme.bg} ${theme.border}`
}

// 获取项目图标类
const getProjectIconClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.icon
}

// 获取项目状态类
const getProjectStatusClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.status
}

// 获取项目边框类
const getProjectBorderClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.taskBorder
}

// 获取项目记录边框类
const getProjectRecordBorderClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.recordBorder
}

// 获取项目标签类
const getProjectTagClasses = (projectId: number) => {
  const theme = getProjectTheme(projectId)
  return theme.tag
}

// 显示记录详情
const showRecordDetail = (record: any) => {
  selectedRecord.value = record
}

// 关闭记录详情
const closeRecordDetail = () => {
  selectedRecord.value = null
}

// ========== 笔记调用相关方法 ==========

// 加载项目关联的笔记
const loadProjectNotes = async () => {
  if (!selectedProjectForNotes.value) {
    projectNotes.value = []
    return
  }
  
  try {
    // 从后端加载项目关联的笔记ID
    const response = await request.get(`/project/${selectedProjectForNotes.value}/notes`)
    if (response.code === 200) {
      const noteIds = response.data || []
      // 根据ID从笔记列表中筛选出关联的笔记
      projectNotes.value = notes.value.filter(note => noteIds.includes(note.id))
    }
  } catch (error) {
    console.error('加载项目笔记失败:', error)
    // 如果后端接口还没实现，暂时从本地筛选（可选）
    projectNotes.value = []
  }
}

// 添加笔记到项目
const addNoteToProject = async (note: Note) => {
  if (!selectedProjectForNotes.value) return
  
  try {
    const response = await request.post(`/project/${selectedProjectForNotes.value}/notes`, {
      noteId: note.id
    })
    
    if (response.code === 200) {
      // 添加成功，更新本地列表
      projectNotes.value.push(note)
      alert('✅ 笔记已关联到项目')
    } else {
      alert('关联失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('关联笔记失败:', error)
    // 如果后端接口还没实现，暂时添加到本地列表
    if (!projectNotes.value.find(n => n.id === note.id)) {
      projectNotes.value.push(note)
      alert('✅ 笔记已关联到项目（本地模式）')
    }
  }
}

// 从项目移除笔记
const removeNoteFromProject = async (noteId: number) => {
  if (!selectedProjectForNotes.value) return
  if (!confirm('确定要移除这个笔记的关联吗？')) return
  
  try {
    const response = await request.delete(`/project/${selectedProjectForNotes.value}/notes/${noteId}`)
    
    if (response.code === 200) {
      // 移除成功
      projectNotes.value = projectNotes.value.filter(n => n.id !== noteId)
      alert('✅ 已移除笔记关联')
    } else {
      alert('移除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('移除笔记关联失败:', error)
    // 如果后端接口还没实现，暂时从本地列表移除
    projectNotes.value = projectNotes.value.filter(n => n.id !== noteId)
    alert('✅ 已移除笔记关联（本地模式）')
  }
}

// 关闭添加笔记弹窗
const closeAddNoteDialog = () => {
  showAddNoteDialog.value = false
  noteSearchQuery.value = ''
  noteFilterType.value = ''
}

// 打开归档导入弹窗并加载归档列表
const openArchiveImportDialog = async () => {
  showArchiveImportDialog.value = true
  await loadArchives()
}

// 关闭归档导入弹窗
const closeArchiveImportDialog = () => {
  showArchiveImportDialog.value = false
  archives.value = []
}

// 加载归档列表
const loadArchives = async () => {
  isLoadingArchives.value = true
  try {
    const response = await request.get('/note-archive/list')
    if (response.code === 200) {
      archives.value = response.data || []
    } else {
      archives.value = []
    }
  } catch (error) {
    console.error('加载归档列表失败:', error)
    archives.value = []
  } finally {
    isLoadingArchives.value = false
  }
}

// 批量导入归档中的所有笔记
const importArchiveNotes = async (archive: any) => {
  if (!selectedProjectForNotes.value) return
  
  try {
    // 获取归档中的所有笔记ID
    const response = await request.get(`/note-archive/${archive.id}/notes`)
    if (response.code === 200) {
      const noteIds = response.data || []
      
      if (noteIds.length === 0) {
        alert('该归档中没有笔记')
        return
      }
      
      // 批量添加笔记到项目
      let successCount = 0
      let failCount = 0
      
      for (const noteId of noteIds) {
        try {
          await request.post(`/project/${selectedProjectForNotes.value}/notes`, {
            noteId: noteId
          })
          successCount++
        } catch (error) {
          console.error(`添加笔记 ${noteId} 失败:`, error)
          failCount++
        }
      }
      
      // 重新加载项目笔记
      await loadProjectNotes()
      
      // 显示结果
      if (failCount === 0) {
        alert(`成功添加 ${successCount} 条笔记！`)
      } else {
        alert(`添加完成：成功 ${successCount} 条，失败 ${failCount} 条（可能已经关联过）`)
      }
      
      // 关闭弹窗
      closeArchiveImportDialog()
    } else {
      alert('获取归档笔记失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('批量导入笔记失败:', error)
    alert('批量导入失败，请重试')
  }
}

// 获取归档类型文本
const getArchiveTypeText = (type: string) => {
  const typeMap: Record<string, string> = {
    'book': '书籍',
    'course': '课程',
    'material': '材料',
    'video': '视频',
    'other': '其他'
  }
  return typeMap[type] || '未知'
}

// 查看笔记详情
const viewNoteDetail = (note: Note) => {
  // 确保数组字段被正确解析
  const normalizedNote = { ...note }
  
  // 处理可能是 JSON 字符串的字段
  const ensureArray = (value: any) => {
    if (!value) return []
    if (Array.isArray(value)) return value
    if (typeof value === 'string') {
      if (value.trim().startsWith('[')) {
        try {
          const parsed = JSON.parse(value)
          if (Array.isArray(parsed)) return parsed
        } catch (e) {
          // 忽略解析错误
        }
      }
      return value.split(',').map((item: string) => item.trim()).filter((item: string) => item !== '')
    }
    return [value]
  }
  
  if (normalizedNote.tags) normalizedNote.tags = ensureArray(normalizedNote.tags) as string[]
  if (normalizedNote.knowledgePoint) normalizedNote.knowledgePoint = ensureArray(normalizedNote.knowledgePoint) as string[]
  
  selectedMaterial.value = normalizedNote
  showMaterialDetail.value = true
}

// 关闭笔记详情
const closeMaterialDetail = () => {
  showMaterialDetail.value = false
  selectedMaterial.value = null
}

// 获取笔记类型样式类
const getNoteTypeClass = (type: Note['type']) => {
  const classes = {
    skill: 'bg-purple-100 text-purple-600',
    study: 'bg-blue-100 text-blue-600',
    memorization: 'bg-green-100 text-green-600',
    exercise: 'bg-orange-100 text-orange-600',
    practical: 'bg-red-100 text-red-600',
    fragment: 'bg-pink-100 text-pink-600'
  }
  return classes[type] || 'bg-gray-100 text-gray-600'
}

// 获取笔记类型边框样式类
const getNoteTypeBorderClass = (type: Note['type']) => {
  const classes = {
    skill: 'border-purple-200 hover:border-purple-400',
    study: 'border-blue-200 hover:border-blue-400',
    memorization: 'border-green-200 hover:border-green-400',
    exercise: 'border-orange-200 hover:border-orange-400',
    practical: 'border-red-200 hover:border-red-400',
    fragment: 'border-pink-200 hover:border-pink-400'
  }
  return classes[type] || 'border-gray-200 hover:border-gray-400'
}

// 获取笔记类型文本
const getNoteTypeText = (type: Note['type']) => {
  const texts = {
    skill: '技能笔记',
    study: '求学笔记',
    memorization: '背诵笔记',
    exercise: '刷题笔记',
    practical: '实战笔记',
    fragment: '碎片笔记'
  }
  return texts[type] || '未知类型'
}

// 获取笔记类型图标
const getNoteTypeIcon = (type: Note['type']) => {
  const icons = {
    skill: 'fas fa-sitemap',
    study: 'fas fa-graduation-cap',
    memorization: 'fas fa-lightbulb',
    exercise: 'fa-regular fa-pen-to-square',
    practical: 'fa-solid fa-flask',
    fragment: 'fas fa-puzzle-piece'
  }
  return icons[type] || 'fas fa-file'
}

// 获取笔记类型图标样式类
const getNoteTypeIconClass = (type: Note['type']) => {
  const classes = {
    skill: 'bg-purple-100 text-purple-600',
    study: 'bg-blue-100 text-blue-600',
    memorization: 'bg-green-100 text-green-600',
    exercise: 'bg-orange-100 text-orange-600',
    practical: 'bg-red-100 text-red-600',
    fragment: 'bg-pink-100 text-pink-600'
  }
  return classes[type] || 'bg-gray-100 text-gray-600'
}

// 保留旧的方法名以兼容素材详情弹窗
const getMaterialTypeClass = getNoteTypeClass
const getMaterialTypeText = getNoteTypeText

// 折叠功能方法
// 切换项目折叠状态
const toggleProjectCollapse = (projectId: number) => {
  const index = collapsedProjects.value.indexOf(projectId)
  if (index > -1) {
    collapsedProjects.value.splice(index, 1)
  } else {
    collapsedProjects.value.push(projectId)
  }
}

// 切换时间分组折叠状态
const toggleTimeGroupCollapse = (timeGroupKey: string) => {
  const index = collapsedTimeGroups.value.indexOf(timeGroupKey)
  if (index > -1) {
    collapsedTimeGroups.value.splice(index, 1)
  } else {
    collapsedTimeGroups.value.push(timeGroupKey)
  }
}

// 操作视图控制
const currentOperation = ref('')

// ========== 打卡操作相关 ==========

// 打卡项目列表
const checkinItems = ref<CheckinItem[]>([])
const checkinStatus = ref<Record<number, boolean>>({})

// 激活的打卡项目（状态为active）
const activeCheckinItems = computed(() => {
  return checkinItems.value.filter(item => item.status === 'active')
})

// 加载打卡项目
const loadCheckinItems = async () => {
  try {
    const response = await getCheckinItemList()
    if (response.code === 200) {
      checkinItems.value = response.data || []
      // 加载每个项目的打卡状态
      await loadCheckinStatuses()
    }
  } catch (error) {
    console.error('加载打卡项目失败:', error)
  }
}

// 加载所有打卡状态
const loadCheckinStatuses = async () => {
  for (const item of checkinItems.value) {
    if (item.id) {
      try {
        const response = await checkCheckinStatus(item.id, item.frequency)
        if (response.code === 200 && response.data) {
          checkinStatus.value[item.id] = response.data.isCheckedIn || false
        }
      } catch (error) {
        console.error('加载打卡状态失败:', error)
      }
    }
  }
}

// 打卡
const handleCheckin = async (itemId: number) => {
  try {
    const response = await checkinAPI(itemId)
    if (response.code === 200) {
      checkinStatus.value[itemId] = true
      alert('打卡成功！')
    } else {
      alert('打卡失败：' + (response.message || '未知错误'))
    }
  } catch (error: any) {
    console.error('打卡失败:', error)
    alert('打卡失败：' + (error.message || '请稍后重试'))
  }
}

// 取消打卡
const handleCancelCheckin = async (itemId: number) => {
  if (!confirm('确定要取消本周期的打卡吗？')) return
  
  try {
    const response = await cancelCheckinAPI(itemId)
    if (response.code === 200) {
      checkinStatus.value[itemId] = false
      alert('已取消打卡！')
    } else {
      alert('取消打卡失败：' + (response.message || '未知错误'))
    }
  } catch (error: any) {
    console.error('取消打卡失败:', error)
    alert('取消打卡失败：' + (error.message || '请稍后重试'))
  }
}

// 获取频率文本（用于打卡）
const getFrequencyTextForCheckin = (frequency: string) => {
  const map: Record<string, string> = {
    'daily': '每日',
    'weekly': '每周',
    'monthly': '每月',
    'quarterly': '每季',
    'yearly': '每年'
  }
  return map[frequency] || frequency
}

// 获取频率样式（用于打卡）
const getFrequencyClassForCheckin = (frequency: string) => {
  const map: Record<string, string> = {
    'daily': 'bg-blue-100 text-blue-700',
    'weekly': 'bg-green-100 text-green-700',
    'monthly': 'bg-purple-100 text-purple-700',
    'quarterly': 'bg-orange-100 text-orange-700',
    'yearly': 'bg-red-100 text-red-700'
  }
  return map[frequency] || 'bg-gray-100 text-gray-700'
}

const openOperation = (operation: 'taskExecution' | 'projectRecord' | 'issueManagement' | 'checkinOperation' | 'noteReference') => {
  // 关闭所有相关视图（为未来扩展预留）
  currentOperation.value = ''
  setTimeout(() => {
    currentOperation.value = operation
    // 如果打开问题管理，加载问题列表
    if (operation === 'issueManagement') {
      loadIssues()
    }
    // 如果打开打卡操作，加载打卡项目
    if (operation === 'checkinOperation') {
      loadCheckinItems()
    }
    // 如果打开笔记调用，重置状态
    if (operation === 'noteReference') {
      selectedProjectForNotes.value = ''
      projectNotes.value = []
      noteProjectSearchQuery.value = ''
      noteProjectCategoryFilter.value = ''
    }
  }, 0)
}

const closeOperation = () => {
  currentOperation.value = ''
}

// 图片调整大小相关
const selectedImage = ref<HTMLElement | null>(null)
const showImageResizer = ref(false)

onMounted(() => {
  // 加载数据
  loadData()
  loadProjectInsights()
  
  // 点击外部关闭表格菜单
  document.addEventListener('click', (event) => {
    const target = event.target as HTMLElement
    if (!target.closest('.relative.inline-block')) {
      showTableMenu.value = false
    }
  })
})

onActivated(() => {
  // 页面激活时重新加载数据
  loadData()
  loadProjectInsights()
})

// ========== 问题管理相关 ==========

// 问题管理状态
const showCreateIssue = ref(false)
const showResolveIssue = ref(false)
const currentIssue = ref<any>(null)
const issues = ref<any[]>([])

// 问题表单
const issueForm = reactive({
  projectId: '',
  issueTitle: '',
  issueDescription: '',
  issueType: 'technical',
  priority: 'medium',
  status: 'open'
})

// 处理问题表单
const resolveForm = reactive({
  solution: ''
})

// 问题筛选
const issueSearchQuery = ref('')
const issueFilterProject = ref('')
const issueFilterStatus = ref('')
const issueFilterPriority = ref('')

// 问题过滤列表
const filteredIssues = computed(() => {
  return issues.value.filter(issue => {
    const matchSearch = !issueSearchQuery.value || 
      issue.issueTitle.toLowerCase().includes(issueSearchQuery.value.toLowerCase()) ||
      (issue.issueDescription && issue.issueDescription.toLowerCase().includes(issueSearchQuery.value.toLowerCase()))
    
    const matchProject = !issueFilterProject.value || issue.projectId === parseInt(issueFilterProject.value)
    const matchStatus = !issueFilterStatus.value || issue.status === issueFilterStatus.value
    const matchPriority = !issueFilterPriority.value || issue.priority === issueFilterPriority.value
    
    return matchSearch && matchProject && matchStatus && matchPriority
  })
})

// 加载问题列表
const loadIssues = async () => {
  try {
    const response = await request.get('/project/issue/list')
    if (response.code === 200) {
      issues.value = response.data || []
    }
  } catch (error) {
    console.error('加载问题列表失败:', error)
  }
}

// 提交问题
const submitIssue = async () => {
  try {
    // 判断是新建还是编辑
    const isEdit = currentIssue.value !== null
    const url = isEdit ? `/project/issue/${currentIssue.value.id}` : '/project/issue'
    const method = isEdit ? 'put' : 'post'
    
    const response = await request[method](url, {
      ...issueForm,
      projectId: parseInt(issueForm.projectId)
    })
    
    if (response.code === 200) {
      alert(isEdit ? '✅ 问题更新成功' : '✅ 问题记录成功')
      closeCreateIssue()
      await loadIssues()
    } else {
      alert((isEdit ? '更新' : '记录') + '失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error((currentIssue.value ? '更新' : '记录') + '问题失败:', error)
    alert((currentIssue.value ? '更新' : '记录') + '失败，请稍后重试')
  }
}

// 关闭创建问题弹窗
const closeCreateIssue = () => {
  showCreateIssue.value = false
  currentIssue.value = null
  issueForm.projectId = ''
  issueForm.issueTitle = ''
  issueForm.issueDescription = ''
  issueForm.issueType = 'technical'
  issueForm.priority = 'medium'
  issueForm.status = 'open'
}

// 编辑问题
const editIssue = (issue: any) => {
  issueForm.projectId = issue.projectId.toString()
  issueForm.issueTitle = issue.issueTitle
  issueForm.issueDescription = issue.issueDescription
  issueForm.issueType = issue.issueType
  issueForm.priority = issue.priority
  issueForm.status = issue.status || 'open'
  currentIssue.value = issue
  showCreateIssue.value = true
}

// 处理问题
const resolveIssue = (issue: any) => {
  currentIssue.value = issue
  resolveForm.solution = ''
  showResolveIssue.value = true
}

// 提交解决方案
const submitResolveIssue = async () => {
  if (!currentIssue.value) return
  
  try {
    const response = await request.put(`/project/issue/${currentIssue.value.id}/resolve`, resolveForm)
    if (response.code === 200) {
      alert('✅ 问题已标记为已解决')
      closeResolveIssue()
      await loadIssues()
    } else {
      alert('处理失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('处理问题失败:', error)
    alert('处理失败，请稍后重试')
  }
}

// 关闭处理问题弹窗
const closeResolveIssue = () => {
  showResolveIssue.value = false
  currentIssue.value = null
  resolveForm.solution = ''
}

// 删除问题
const deleteIssue = async (id: number) => {
  if (!confirm('确定要删除这个问题吗？')) return
  
  try {
    const response = await request.delete(`/project/issue/${id}`)
    if (response.code === 200) {
      alert('✅ 问题已删除')
      await loadIssues()
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除问题失败:', error)
    alert('删除失败，请稍后重试')
  }
}

// 退回问题（从执行端退回给决策端）
const reopenIssueFromExecution = async (id: number) => {
  if (!confirm('发现处理方案不合适？确定要退回重新处理吗？')) return
  
  try {
    const response = await request.put(`/project/issue/${id}/reopen`)
    if (response.code === 200) {
      alert('✅ 问题已退回，可以重新制定解决方案了')
      await loadIssues()
    } else {
      alert('退回失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('退回问题失败:', error)
    alert('退回失败，请稍后重试')
  }
}

// 获取优先级文本
const getPriorityText = (priority: string) => {
  const texts: Record<string, string> = {
    urgent: '紧急',
    high: '高',
    medium: '中',
    low: '低'
  }
  return texts[priority] || priority
}

// 获取问题状态文本
const getIssueStatusText = (status: string) => {
  const texts: Record<string, string> = {
    open: '待处理',
    in_progress: '处理中',
    resolved: '已解决',
    closed: '已关闭'
  }
  return texts[status] || status
}

// 获取问题类型文本
const getIssueTypeText = (type: string) => {
  const texts: Record<string, string> = {
    technical: '技术问题',
    requirement: '需求问题',
    design: '设计问题',
    other: '其他问题'
  }
  return texts[type] || type
}

// 格式化日期
const formatDate = (date: any) => {
  if (!date) return ''
  const d = new Date(date)
  return d.toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
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

/* 项目经验项目网格效果 */
.grid-item-enter-active,
.grid-item-leave-active {
  transition: all 0.3s ease;
}

.grid-item-enter-from,
.grid-item-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

.project-lift-enter-active,
.project-lift-leave-active {
  transition: all 0.3s ease;
}

.project-lift-enter-from,
.project-lift-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

/* 素材调用模块样式 */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* 素材卡片悬停效果 */
.material-card {
  transition: all 0.2s ease-in-out;
}

.material-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 素材类型图标动画 */
.material-type-icon {
  transition: all 0.2s ease-in-out;
}

.material-type-icon:hover {
  transform: scale(1.1);
}

/* 素材类型卡片样式 */
.material-type-card {
  transition: all 0.3s ease-in-out;
}

.material-type-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

/* 素材类型卡片图标悬停效果 */
.material-type-card:hover .material-type-icon {
  transform: scale(1.1);
}

/* 素材类型卡片箭头动画 */
.material-type-card:hover .arrow-icon {
  transform: translateX(4px);
}

/* 素材类型详情页面动画 */
.material-type-detail-enter-active,
.material-type-detail-leave-active {
  transition: all 0.3s ease;
}

.material-type-detail-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.material-type-detail-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}

/* 操作视图渐入渐出动画 */
.operation-fade-enter-active, .operation-fade-leave-active {
  transition: opacity 0.4s cubic-bezier(0.4,0,0.2,1);
}
.operation-fade-enter-from, .operation-fade-leave-to {
  opacity: 0;
}
.operation-fade-enter-to, .operation-fade-leave-from {
  opacity: 1;
}

/* TipTap 编辑器样式 */
:deep(.ProseMirror) {
  outline: none;
  min-height: 200px;
}

:deep(.ProseMirror p.is-editor-empty:first-child::before) {
  color: #adb5bd;
  content: attr(data-placeholder);
  float: left;
  height: 0;
  pointer-events: none;
}

:deep(.ProseMirror table) {
  border-collapse: collapse;
  margin: 0;
  overflow: hidden;
  table-layout: fixed;
  width: 100%;
}

:deep(.ProseMirror table td),
:deep(.ProseMirror table th) {
  border: 2px solid #ced4da;
  box-sizing: border-box;
  min-width: 1em;
  padding: 3px 5px;
  position: relative;
  vertical-align: top;
}

:deep(.ProseMirror table th) {
  background-color: #f1f3f4;
  font-weight: bold;
  text-align: left;
}

:deep(.ProseMirror table .selectedCell:after) {
  background: rgba(200, 200, 255, 0.4);
  content: "";
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  pointer-events: none;
  position: absolute;
  z-index: 2;
}

:deep(.ProseMirror table .column-resize-handle) {
  background-color: #adf;
  bottom: -2px;
  position: absolute;
  right: -2px;
  pointer-events: none;
  top: 0;
  width: 4px;
}

:deep(.ProseMirror table p) {
  margin: 0;
}

:deep(.ProseMirror blockquote) {
  border-left: 3px solid #ddd;
  margin-left: 0;
  padding-left: 1rem;
}

:deep(.ProseMirror code) {
  background-color: #f1f3f4;
  border-radius: 3px;
  padding: 0.2em 0.4em;
}

:deep(.ProseMirror s) {
  text-decoration: line-through;
}

:deep(.ProseMirror pre) {
  background: #0d0d0d;
  border-radius: 0.5rem;
  color: #fff;
  font-family: 'JetBrainsMono', monospace;
  padding: 0.75rem 1rem;
}

:deep(.ProseMirror pre code) {
  background: none;
  color: inherit;
  font-size: 0.8rem;
  padding: 0;
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