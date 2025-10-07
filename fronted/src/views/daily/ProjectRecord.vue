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
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
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
            <p class="text-gray-600">查看和执行系统决策中创建的任务，记录执行进度</p>
          </div>
        </div>

        <!-- 项目心得 -->
        <div 
          @click="openOperation('projectRecord')"
          class="p-6 border-2 border-dashed border-blue-300 rounded-xl hover:border-blue-500 hover:bg-blue-50 cursor-pointer transition-all duration-200"
        >
          <div class="text-center">
            <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <i class="fas fa-lightbulb text-2xl text-blue-600"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2">项目心得</h4>
            <p class="text-gray-600">记录项目执行过程中的心得体会、技术收获和执行总结</p>
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
      <template v-else-if="currentOperation === 'projectRecord'">
        <div class="bg-white rounded-xl shadow-soft p-6 mb-8 max-h-[80vh] flex flex-col">
          <div class="flex items-center justify-between mb-6 flex-shrink-0">
            <h3 class="text-xl font-semibold text-gray-900">项目心得</h3>
            <div class="flex space-x-2">
              <button 
                @click="showCreateRecord = true"
                class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center space-x-2"
              >
                <i class="fas fa-plus"></i>
                <span>新建心得</span>
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
                  v-model="projectRecordSearchQuery"
                  type="text" 
                  placeholder="搜索心得标题、内容或技术关键词..."
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                >
              </div>
              <div>
                <select v-model="projectRecordFilterProject" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                  <option value="">全部项目</option>
                  <option v-for="project in projects" :key="project.id" :value="project.id">
                    {{ project.name }}
                  </option>
                </select>
              </div>
            </div>
          </div>
          
          <!-- 项目心得列表 -->
          <div class="space-y-4 flex-1 overflow-y-auto">
            <div v-for="record in filteredProjectRecords" :key="record.id" class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50">
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center space-x-3 mb-2">
                    <h4 class="text-lg font-medium text-gray-900">{{ record.title }}</h4>
                    <span class="text-sm text-gray-500">项目：{{ getProjectName(record.projectId) }}</span>
                  </div>
                  <div class="bg-gray-50 p-3 rounded-lg mb-3">
                    <p class="text-sm font-medium text-gray-700 mb-1">心得摘要：</p>
                    <p class="text-sm text-gray-600">{{ record.summary }}</p>
                  </div>
                  
                  <!-- 详细描述 -->
                  <div v-if="record.content" class="mb-3">
                    <p class="text-sm font-medium text-gray-700 mb-1">详细内容：</p>
                    <div class="text-sm text-gray-600 prose prose-sm max-w-none" v-html="record.content"></div>
                  </div>
                  
                  <div class="flex items-center space-x-4 text-sm text-gray-500">
                    <span>{{ record.updateTime }}</span>
                    <div class="flex space-x-1">
                      <span v-for="tag in record.tags" :key="tag" class="bg-gray-100 text-gray-600 px-2 py-1 rounded">
                        {{ tag }}
                      </span>
                    </div>
                  </div>
                </div>
                <div class="flex space-x-2">
                  <button @click="editProjectRecord(record)" class="text-blue-600 hover:text-blue-800">
                    <i class="fas fa-edit"></i>
                  </button>
                  <button @click="deleteProjectRecord(record.id)" class="text-red-600 hover:text-red-800">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
              </div>
            </div>
            
            <!-- 搜索空状态 -->
            <div v-if="filteredProjectRecords.length === 0" class="text-center py-12 text-gray-500">
              <i class="fas fa-search text-4xl mb-4"></i>
              <p class="text-lg font-medium mb-2">未找到匹配的项目心得</p>
              <p class="text-sm">请尝试调整搜索条件或创建新心得</p>
              <button 
                @click="showCreateRecord = true"
                class="mt-4 px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
              >
                创建新心得
              </button>
            </div>
          </div>
        </div>
      </template>
    </Transition>

    <!-- 素材调用模块 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-xl font-semibold text-gray-900">素材调用</h3>
        <div class="flex items-center space-x-2">
          <span class="text-sm text-gray-500">点击卡片进入对应类型的素材库</span>
        </div>
      </div>
      
      <!-- 素材类型卡片 -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- 碎片笔记卡片 -->
        <div 
          @click="openMaterialType('fragment')"
          class="p-6 border-2 border-pink-200 rounded-xl hover:border-pink-400 hover:bg-pink-50 cursor-pointer transition-all duration-200 group"
        >
          <div class="flex items-center space-x-4 mb-4">
            <div class="w-12 h-12 bg-pink-100 rounded-full flex items-center justify-center group-hover:bg-pink-200 transition-colors">
              <i class="fas fa-lightbulb text-2xl text-pink-600"></i>
            </div>
            <div class="flex-1">
              <h4 class="text-lg font-semibold text-gray-900">碎片笔记</h4>
              <p class="text-sm text-gray-600">灵感和想法记录</p>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-500">{{ getMaterialsByType('fragment').length }} 条笔记</span>
            <i class="fas fa-chevron-right text-pink-600 group-hover:translate-x-1 transition-transform"></i>
          </div>
        </div>

        <!-- 框架笔记卡片 -->
        <div 
          @click="openMaterialType('framework')"
          class="p-6 border-2 border-purple-200 rounded-xl hover:border-purple-400 hover:bg-purple-50 cursor-pointer transition-all duration-200 group"
        >
          <div class="flex items-center space-x-4 mb-4">
            <div class="w-12 h-12 bg-purple-100 rounded-full flex items-center justify-center group-hover:bg-purple-200 transition-colors">
              <i class="fas fa-sitemap text-2xl text-purple-600"></i>
            </div>
            <div class="flex-1">
              <h4 class="text-lg font-semibold text-gray-900">框架笔记</h4>
              <p class="text-sm text-gray-600">知识框架和体系结构</p>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-500">{{ getMaterialsByType('framework').length }} 条笔记</span>
            <i class="fas fa-chevron-right text-purple-600 group-hover:translate-x-1 transition-transform"></i>
          </div>
        </div>

        <!-- 求学笔记卡片 -->
        <div 
          @click="openMaterialType('study')"
          class="p-6 border-2 border-blue-200 rounded-xl hover:border-blue-400 hover:bg-blue-50 cursor-pointer transition-all duration-200 group"
        >
          <div class="flex items-center space-x-4 mb-4">
            <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center group-hover:bg-blue-200 transition-colors">
              <i class="fas fa-graduation-cap text-2xl text-blue-600"></i>
            </div>
            <div class="flex-1">
              <h4 class="text-lg font-semibold text-gray-900">求学笔记</h4>
              <p class="text-sm text-gray-600">课程学习和知识积累</p>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-500">{{ getMaterialsByType('study').length }} 条笔记</span>
            <i class="fas fa-chevron-right text-blue-600 group-hover:translate-x-1 transition-transform"></i>
          </div>
        </div>

        <!-- 背诵笔记卡片 -->
        <div 
          @click="openMaterialType('memorization')"
          class="p-6 border-2 border-green-200 rounded-xl hover:border-green-400 hover:bg-green-50 cursor-pointer transition-all duration-200 group"
        >
          <div class="flex items-center space-x-4 mb-4">
            <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center group-hover:bg-green-200 transition-colors">
              <i class="fas fa-lightbulb text-2xl text-green-600"></i>
            </div>
            <div class="flex-1">
              <h4 class="text-lg font-semibold text-gray-900">背诵笔记</h4>
              <p class="text-sm text-gray-600">重点内容和记忆技巧</p>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-500">{{ getMaterialsByType('memorization').length }} 条笔记</span>
            <i class="fas fa-chevron-right text-green-600 group-hover:translate-x-1 transition-transform"></i>
          </div>
        </div>



        <!-- 刷题笔记卡片 -->
        <div 
          @click="openMaterialType('exercise')"
          class="p-6 border-2 border-orange-200 rounded-xl hover:border-orange-400 hover:bg-orange-50 cursor-pointer transition-all duration-200 group"
        >
          <div class="flex items-center space-x-4 mb-4">
            <div class="w-12 h-12 bg-orange-100 rounded-full flex items-center justify-center group-hover:bg-orange-200 transition-colors">
              <i class="fa-regular fa-pen-to-square text-2xl text-orange-600"></i>
            </div>
            <div class="flex-1">
              <h4 class="text-lg font-semibold text-gray-900">刷题笔记</h4>
              <p class="text-sm text-gray-600">题目解析和解题技巧</p>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-500">{{ getMaterialsByType('exercise').length }} 条笔记</span>
            <i class="fas fa-chevron-right text-orange-600 group-hover:translate-x-1 transition-transform"></i>
          </div>
        </div>

        <!-- 实战笔记卡片 -->
        <div 
          @click="openMaterialType('practical')"
          class="p-6 border-2 border-red-200 rounded-xl hover:border-red-400 hover:bg-red-50 cursor-pointer transition-all duration-200 group"
        >
          <div class="flex items-center space-x-4 mb-4">
            <div class="w-12 h-12 bg-red-100 rounded-full flex items-center justify-center group-hover:bg-red-200 transition-colors">
              <i class="fa-solid fa-flask text-2xl text-red-600"></i>
            </div>
            <div class="flex-1">
              <h4 class="text-lg font-semibold text-gray-900">实战笔记</h4>
              <p class="text-sm text-gray-600">项目实践和技术应用</p>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-500">{{ getMaterialsByType('practical').length }} 条笔记</span>
            <i class="fas fa-chevron-right text-red-600 group-hover:translate-x-1 transition-transform"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- 素材类型详情弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showMaterialTypeDetail" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[90vh] flex flex-col">
            <!-- 弹窗头部 -->
            <div class="flex items-center justify-between p-6 border-b border-gray-200">
              <div class="flex items-center space-x-3">
                <div class="w-12 h-12 rounded-full flex items-center justify-center" :class="getMaterialTypeIconClass(currentMaterialType)">
                  <i :class="getMaterialTypeIcon(currentMaterialType)" class="text-2xl"></i>
                </div>
                <div>
                  <h3 class="text-2xl font-semibold text-gray-900">{{ getMaterialTypeText(currentMaterialType) }}</h3>
                  <p class="text-sm text-gray-500">{{ getMaterialsByType(currentMaterialType).length }} 条笔记</p>
                </div>
              </div>
              <button 
                @click="closeMaterialTypeDetail"
                class="text-gray-500 hover:text-gray-700 transition-colors"
              >
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
            
            <!-- 搜索框 -->
            <div class="p-6 border-b border-gray-200">
              <div class="relative">
                <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                <input 
                  v-model="materialTypeSearchQuery"
                  type="text" 
                  :placeholder="`搜索${getMaterialTypeText(currentMaterialType)}...`"
                  class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all"
                >
              </div>
            </div>
            
            <!-- 素材列表 - 可滚动区域 -->
            <div class="flex-1 overflow-y-auto p-6">
              <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div 
                  v-for="material in filteredMaterialTypeList" 
                  :key="material.id"
                  class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 hover:border-gray-300 cursor-pointer transition-all duration-200 group"
                  @click="viewMaterialDetail(material)"
                >
                  <div class="mb-3">
                    <h5 class="text-sm font-medium text-gray-900 line-clamp-2 group-hover:text-purple-600 transition-colors">{{ material.title }}</h5>
                  </div>
                  <p class="text-xs text-gray-600 mb-3 line-clamp-2">{{ material.summary }}</p>
                  
                  <!-- 背诵笔记显示知识点 -->
                  <div v-if="material.type === 'memorization' && Array.isArray(material.knowledgePoint)" class="flex flex-wrap gap-1 mb-2">
                    <span v-for="kp in material.knowledgePoint.slice(0, 3)" :key="kp" class="bg-green-100 text-green-600 px-1 py-0.5 rounded text-xs">
                      {{ kp }}
                    </span>
                  </div>
                  
                  <div class="flex items-center justify-between text-xs text-gray-500">
                    <span>{{ material.createTime || material.recCreateTime }}</span>
                    <div class="flex space-x-1" v-if="Array.isArray(material.tags) && material.tags.length > 0">
                      <span v-for="tag in material.tags.slice(0, 2)" :key="tag" class="bg-gray-100 text-gray-600 px-1 py-0.5 rounded text-xs">
                        {{ tag }}
                      </span>
                    </div>
                  </div>
                </div>
                
                <!-- 空状态 -->
                <div v-if="filteredMaterialTypeList.length === 0" class="col-span-full text-center py-16 text-gray-500">
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

    <!-- 素材详情弹窗 -->
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

    <!-- 项目经验界面 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="mb-6">
        <h3 class="text-xl font-semibold text-gray-900">项目经验</h3>
        <p class="text-gray-600 mt-1">已完成项目的任务和心得，为当前项目提供经验参考</p>
      </div>

      <!-- 选中的项目单独展示区域 -->
      <transition name="project-lift" mode="out-in">
        <div v-if="selectedExperienceProject" :key="selectedExperienceProject.id" class="mb-8">
          <div class="rounded-2xl border-2 shadow-xl" :class="getProjectColorClasses(selectedExperienceProject.id)">
            <div class="flex items-center justify-between px-6 py-5">
              <div class="flex items-center gap-4">
                <div class="w-12 h-12 rounded-xl flex items-center justify-center flex-shrink-0" :class="getProjectIconClasses(selectedExperienceProject.id)">
                  <i class="fa-solid fa-box-archive text-white text-xl"></i>
                </div>
                <div class="min-w-0 flex-1">
                  <h3 class="text-xl font-bold text-gray-900 truncate">{{ selectedExperienceProject.name }}</h3>
                  <p class="text-sm text-gray-500 mt-1 truncate">{{ selectedExperienceProject.description }}</p>
                </div>
              </div>
              <div class="flex items-center gap-3 flex-shrink-0">
                <span class="px-3 py-1 rounded-full text-sm font-medium" :class="getProjectStatusClasses(selectedExperienceProject.id)">已完成</span>
                <button @click="deselectExperienceProject" class="text-gray-400 hover:text-gray-600 transition-colors duration-200">
                  <i class="fa-solid fa-times text-xl"></i>
                </button>
              </div>
            </div>
            
            <!-- 任务列表 -->
            <div class="px-6 pb-6">
              <h4 class="text-md font-semibold text-gray-800 mb-3">项目任务</h4>
              <div v-if="getProjectTasks(selectedExperienceProject.id).length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3">
                <div v-for="task in getProjectTasks(selectedExperienceProject.id)" :key="task.id" class="bg-white/90 border rounded-lg p-3 shadow-sm hover:shadow-md transition-all duration-200" :class="getProjectBorderClasses(selectedExperienceProject.id)">
                  <div class="flex items-start justify-between mb-2">
                    <div class="font-medium text-gray-900 text-sm flex-1 min-w-0 mr-2">{{ task.name }}</div>
                    <span class="px-2 py-1 rounded-full text-xs font-medium flex-shrink-0" :class="task.status === 'completed' ? 'bg-emerald-100 text-emerald-700' : 'bg-amber-100 text-amber-700'">
                      {{ task.status === 'completed' ? '已完成' : '已停止' }}
                    </span>
                  </div>
                  <div class="text-xs text-gray-500 mb-2 line-clamp-2">{{ task.description }}</div>
                  <div class="text-xs text-gray-400">创建：{{ task.createTime }}</div>
                  <div class="text-xs text-gray-400">截止：{{ task.deadline }}</div>
                </div>
              </div>
              <div v-else class="text-gray-400 text-sm">暂无任务</div>
            </div>

            <!-- 心得列表 -->
            <div class="px-6 pb-6">
              <h4 class="text-md font-semibold text-gray-800 mb-3">项目心得</h4>
              <div v-if="getProjectRecords(selectedExperienceProject.id).length > 0" class="space-y-3">
                <div 
                  v-for="record in getProjectRecords(selectedExperienceProject.id)" 
                  :key="record.id" 
                  class="bg-white/90 border rounded-lg p-3 shadow-sm hover:shadow-md transition-all duration-200 cursor-pointer group" 
                  :class="getProjectRecordBorderClasses(selectedExperienceProject.id)"
                  @click="showRecordDetail(record)"
                >
                  <div class="flex items-start justify-between mb-2">
                    <div class="font-medium text-gray-900 flex-1">{{ record.title }}</div>
                    <i class="fa-solid fa-chevron-right text-gray-400 group-hover:text-gray-600 transition-colors duration-200 text-sm"></i>
                  </div>
                  <div class="text-xs text-gray-500 mb-2 line-clamp-2">{{ record.summary }}</div>
                  <div class="text-xs text-gray-400 mb-2">更新时间：{{ record.updateTime }}</div>
                  <div class="flex flex-wrap gap-1">
                    <span v-for="tag in record.tags" :key="tag" class="px-2 py-0.5 rounded-full text-xs" :class="getProjectTagClasses(selectedExperienceProject.id)">{{ tag }}</span>
                  </div>
                </div>
              </div>
              <div v-else class="text-gray-400 text-sm">暂无心得</div>
            </div>
          </div>
        </div>
      </transition>

      <!-- 项目网格 -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <transition-group name="grid-item" tag="div" class="contents">
          <div 
            v-for="project in visibleExperienceProjects" 
            :key="project.id" 
            class="rounded-xl border shadow-lg hover:shadow-xl transition-all duration-500 cursor-pointer group transform hover:scale-105"
            :class="getProjectColorClasses(project.id)"
            @click="selectExperienceProject(project)"
          >
            <div class="p-4 h-full flex flex-col">
              <div class="flex items-start gap-3 mb-3">
                <div class="w-10 h-10 rounded-lg flex items-center justify-center flex-shrink-0" :class="getProjectIconClasses(project.id)">
                  <i class="fa-solid fa-box-archive text-white text-sm"></i>
                </div>
                <div class="min-w-0 flex-1">
                  <h3 class="text-sm font-bold text-gray-900 group-hover:text-gray-800 transition-colors duration-200 truncate">{{ project.name }}</h3>
                  <p class="text-xs text-gray-500 mt-1 line-clamp-2">{{ project.description }}</p>
                </div>
              </div>
              
              <div class="flex items-center justify-between mt-auto">
                <span class="px-2 py-1 rounded-full text-xs font-medium" :class="getProjectStatusClasses(project.id)">已完成</span>
                <div class="text-xs text-gray-400">
                  {{ getProjectTasks(project.id).length }} 任务 · {{ getProjectRecords(project.id).length }} 心得
                </div>
              </div>
            </div>
          </div>
        </transition-group>
      </div>
    </div>

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
    materials.value = notesData
    console.log('【项目执行】加载笔记数据:', notes.value.length, '个')
  } catch (error) {
    console.error('【项目执行】加载数据失败:', error)
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

// 素材调用相关数据
const materials = ref<Note[]>([])
const materialSearchQuery = ref('')
const materialFilterType = ref('')
const showMaterialDetail = ref(false)
const selectedMaterial = ref<Note | null>(null)

// 素材类型详情相关数据
const showMaterialTypeDetail = ref(false)
const currentMaterialType = ref<Note['type']>('basic')
const materialTypeSearchQuery = ref('')

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

// 筛选后的素材
const filteredMaterials = computed(() => {
  let filtered = materials.value
  
  // 按类型筛选
  if (materialFilterType.value) {
    filtered = filtered.filter(material => material.type === materialFilterType.value)
  }
  
  // 按搜索关键词筛选
  if (materialSearchQuery.value) {
    const query = materialSearchQuery.value.toLowerCase()
    filtered = filtered.filter(material => 
      material.title.toLowerCase().includes(query) ||
      material.summary.toLowerCase().includes(query) ||
      material.tags.some(tag => tag.toLowerCase().includes(query))
    )
  }
  
  return filtered
})

// 按类型获取素材
const getMaterialsByType = (type: Note['type']) => {
  return filteredMaterials.value.filter(material => material.type === type)
}

// 筛选后的素材类型列表
const filteredMaterialTypeList = computed(() => {
  const typeMaterials = materials.value.filter(material => material.type === currentMaterialType.value)
  
  if (!materialTypeSearchQuery.value) {
    return typeMaterials
  }
  
  const query = materialTypeSearchQuery.value.toLowerCase()
  return typeMaterials.filter(material => 
    material.title.toLowerCase().includes(query) ||
    material.summary.toLowerCase().includes(query) ||
    material.tags.some(tag => tag.toLowerCase().includes(query))
  )
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
const submitProjectRecord = () => {
  if (!projectForm.projectId || !projectForm.title || !projectForm.summary) {
    alert('请填写完整信息')
    return
  }

  const newRecord = {
    id: Date.now(),
    projectId: parseInt(projectForm.projectId),
    title: projectForm.title,
    summary: projectForm.summary,
    content: projectForm.content,
    tags: projectForm.tags,
    updateTime: new Date().toLocaleString()
  }

  projectRecords.value.unshift(newRecord)

  closeCreateRecord()
  alert('项目心得保存成功！')
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
const deleteProjectRecord = (id: number) => {
  if (confirm('确定要删除这条项目心得吗？此操作不可撤销。')) {
    const index = projectRecords.value.findIndex(record => record.id === id)
    if (index !== -1) {
      projectRecords.value.splice(index, 1)
      alert('项目心得已删除！')
    }
  }
}

// 提交编辑记录
const submitEditRecord = () => {
  if (!editForm.projectId || !editForm.title || !editForm.summary) {
    alert('请填写完整信息')
    return
  }

  const updatedRecord = {
    id: editForm.id,
    projectId: parseInt(editForm.projectId),
    title: editForm.title,
    summary: editForm.summary,
    content: editForm.content,
    tags: editForm.tags,
    updateTime: new Date().toLocaleString()
  }

  const index = projectRecords.value.findIndex(record => record.id === updatedRecord.id)
  if (index !== -1) {
    projectRecords.value.splice(index, 1, updatedRecord)
    alert('项目心得已更新！')
  }

  closeEditRecord()
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

// 项目经验相关
const selectedExperienceProject = ref<any>(null)
const selectedRecord = ref<any>(null)

// 获取已完成的项目
const completedProjects = computed(() => {
  return projects.value.filter(project => project.status === 'completed')
})

const visibleExperienceProjects = computed(() => {
  if (selectedExperienceProject.value) {
    return completedProjects.value.filter(project => project.id !== selectedExperienceProject.value.id)
  }
  return completedProjects.value
})

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

// 选择项目经验
const selectExperienceProject = (project: any) => {
  selectedExperienceProject.value = project
}

// 取消选择项目经验
const deselectExperienceProject = () => {
  selectedExperienceProject.value = null
}

// 显示记录详情
const showRecordDetail = (record: any) => {
  selectedRecord.value = record
}

// 关闭记录详情
const closeRecordDetail = () => {
  selectedRecord.value = null
}

// 素材相关方法
// 获取素材类型样式类
const getMaterialTypeClass = (type: Note['type']) => {
  const classes = {
    basic: 'bg-purple-100 text-purple-600',
    study: 'bg-blue-100 text-blue-600',
    book: 'bg-green-100 text-green-600',
    exercise: 'bg-indigo-100 text-indigo-600',
    practical: 'bg-orange-100 text-orange-600',
    fragment: 'bg-pink-100 text-pink-600'
  }
  return classes[type] || 'bg-gray-100 text-gray-600'
}

// 获取素材类型文本
const getMaterialTypeText = (type: Note['type']) => {
  const texts = {
    framework: '框架笔记',
    study: '求学笔记',
    memorization: '背诵笔记',
    exercise: '刷题笔记',
    practical: '实战笔记',
    fragment: '碎片笔记'
  }
  return texts[type] || '未知类型'
}

// 查看素材详情
const viewMaterialDetail = (material: Note) => {
  // 确保数组字段被正确解析
  const normalizedMaterial = { ...material }
  
  // 处理可能是 JSON 字符串的字段
  const ensureArray = (value: any) => {
    if (!value) return []
    if (Array.isArray(value)) return value
    if (typeof value === 'string') {
      // 尝试解析 JSON 字符串
      if (value.trim().startsWith('[')) {
        try {
          const parsed = JSON.parse(value)
          if (Array.isArray(parsed)) return parsed
        } catch (e) {
          // 忽略解析错误
        }
      }
      // 如果是逗号分隔的字符串
      return value.split(',').map((item: string) => item.trim()).filter((item: string) => item !== '')
    }
    return [value]
  }
  
  // 确保所有可能的数组字段都是数组
  if (normalizedMaterial.tags) normalizedMaterial.tags = ensureArray(normalizedMaterial.tags) as string[]
  if (normalizedMaterial.knowledgePoint) normalizedMaterial.knowledgePoint = ensureArray(normalizedMaterial.knowledgePoint) as string[]
  
  selectedMaterial.value = normalizedMaterial
  showMaterialDetail.value = true
}

// 关闭素材详情
const closeMaterialDetail = () => {
  showMaterialDetail.value = false
  selectedMaterial.value = null
}



// 素材类型相关方法
// 打开素材类型详情
const openMaterialType = (type: Note['type']) => {
  currentMaterialType.value = type
  materialTypeSearchQuery.value = ''
  showMaterialTypeDetail.value = true
}

// 关闭素材类型详情
const closeMaterialTypeDetail = () => {
  showMaterialTypeDetail.value = false
  currentMaterialType.value = 'framework'
  materialTypeSearchQuery.value = ''
}

// 获取素材类型图标类
const getMaterialTypeIconClass = (type: Note['type']) => {
  const classes = {
    framework: 'bg-purple-100 text-purple-600',
    study: 'bg-blue-100 text-blue-600',
    memorization: 'bg-green-100 text-green-600',
    exercise: 'bg-orange-100 text-orange-600',
    practical: 'bg-red-100 text-red-600',
    fragment: 'bg-pink-100 text-pink-600'
  }
  return classes[type] || 'bg-gray-100 text-gray-600'
}

// 获取素材类型图标
const getMaterialTypeIcon = (type: Note['type']) => {
  const icons = {
    framework: 'fas fa-sitemap',
    study: 'fas fa-graduation-cap',
    memorization: 'fas fa-lightbulb',
    exercise: 'fa-regular fa-pen-to-square',
    practical: 'fa-solid fa-flask',
    fragment: 'fas fa-lightbulb'
  }
  return icons[type] || 'fas fa-file'
}

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

const openOperation = (operation: 'taskExecution' | 'projectRecord') => {
  // 关闭所有相关视图（为未来扩展预留）
  currentOperation.value = ''
  setTimeout(() => {
    currentOperation.value = operation
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