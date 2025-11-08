<template>
  <section id="material-record" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">笔记记录</h1>
        <span class="bg-blue-100 text-blue-600 px-3 py-1 rounded-full text-sm font-medium">日常记录</span>
      </div>
      <p class="text-gray-600">记录日常学习、阅读、思考的素材和笔记，为项目制作积累素材</p>
    </div>

    <!-- 笔记配置管理 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <div class="flex items-center space-x-3">
          <div class="w-10 h-10 bg-gradient-to-br from-blue-500 to-indigo-600 rounded-lg flex items-center justify-center">
            <i class="fas fa-cog text-white"></i>
          </div>
          <div>
            <h3 class="text-xl font-semibold text-gray-900">笔记分类标签管理</h3>
            <p class="text-sm text-gray-600">配置专属于此笔记类型的特殊字段</p>
          </div>
        </div>
        <button 
          @click="showConfigManagement = !showConfigManagement"
          class="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 transition-colors flex items-center space-x-2"
        >
          <i :class="showConfigManagement ? 'fas fa-chevron-up' : 'fas fa-chevron-down'"></i>
          <span>{{ showConfigManagement ? '收起' : '展开' }}</span>
        </button>
      </div>

      <Transition name="slide-fade">
        <div v-if="showConfigManagement" class="space-y-6">
          <!-- 配置类型选择 -->
          <div class="flex items-center space-x-2 border-b pb-4">
            <button 
              v-for="noteType in noteTypes" 
              :key="noteType.value"
              @click="selectedConfigNoteType = noteType.value"
              :class="selectedConfigNoteType === noteType.value ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'"
              class="px-4 py-2 rounded-lg transition-colors flex items-center space-x-2"
            >
              <i :class="noteType.icon"></i>
              <span>{{ noteType.label }}</span>
            </button>
          </div>

          <!-- 配置管理内容区 -->
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <!-- 配置类型1 -->
            <div v-if="getConfigTypes(selectedConfigNoteType).type1" class="border border-gray-200 rounded-lg p-4">
              <div class="mb-4">
                <h4 class="font-semibold text-gray-800 flex items-center space-x-2 mb-3">
                  <i class="fas fa-tag text-blue-500"></i>
                  <span>{{ getConfigTypes(selectedConfigNoteType).type1.label }}</span>
                </h4>
                <!-- 搜索/添加输入框 -->
                <div class="flex items-center space-x-2">
                  <div class="relative flex-1">
                    <input 
                      v-model="configType1Search"
                      type="text" 
                      :placeholder="'搜索或新建' + getConfigTypes(selectedConfigNoteType).type1.label + '...'"
                      class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 form-input pr-8"
                    >
                    <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  </div>
                  <button 
                    type="button"
                    @click="addConfigFromSearch(selectedConfigNoteType, getConfigTypes(selectedConfigNoteType).type1.key)"
                    :disabled="!configType1Search.trim()"
                    class="px-3 py-2 bg-blue-500 text-white text-sm rounded-lg hover:bg-blue-600 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors flex items-center space-x-1 whitespace-nowrap"
                  >
                    <i class="fas fa-plus"></i>
                    <span>添加</span>
                  </button>
                </div>
              </div>
              <div class="space-y-2 max-h-60 overflow-y-auto">
                <div 
                  v-for="item in getConfigItems(selectedConfigNoteType, getConfigTypes(selectedConfigNoteType).type1.key)" 
                  :key="item.id"
                  class="flex items-center justify-between p-2 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors group"
                >
                  <span class="text-sm text-gray-700">{{ item.configName }}</span>
                  <button 
                    @click="deleteConfigItem(item.id)"
                    class="text-red-500 hover:text-red-700 opacity-0 group-hover:opacity-100 transition-opacity text-sm"
                  >
                    <i class="fas fa-trash-alt"></i>
                  </button>
                </div>
                <div v-if="getConfigItems(selectedConfigNoteType, getConfigTypes(selectedConfigNoteType).type1.key).length === 0" class="text-center py-8 text-gray-400">
                  <i class="fas fa-inbox text-3xl mb-2"></i>
                  <p class="text-sm">暂无配置项</p>
                </div>
              </div>
            </div>

            <!-- 配置类型2 -->
            <div v-if="getConfigTypes(selectedConfigNoteType).type2" class="border border-gray-200 rounded-lg p-4">
              <div class="mb-4">
                <h4 class="font-semibold text-gray-800 flex items-center space-x-2 mb-3">
                  <i class="fas fa-tags text-green-500"></i>
                  <span>{{ getConfigTypes(selectedConfigNoteType).type2.label }}</span>
                </h4>
                <!-- 搜索/添加输入框 -->
                <div class="flex items-center space-x-2">
                  <div class="relative flex-1">
                    <input 
                      v-model="configType2Search"
                      type="text" 
                      :placeholder="'搜索或新建' + getConfigTypes(selectedConfigNoteType).type2.label + '...'"
                      class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500 form-input pr-8"
                    >
                    <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  </div>
                  <button 
                    type="button"
                    @click="addConfigFromSearch(selectedConfigNoteType, getConfigTypes(selectedConfigNoteType).type2.key)"
                    :disabled="!configType2Search.trim()"
                    class="px-3 py-2 bg-green-500 text-white text-sm rounded-lg hover:bg-green-600 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors flex items-center space-x-1 whitespace-nowrap"
                  >
                    <i class="fas fa-plus"></i>
                    <span>添加</span>
                  </button>
                </div>
              </div>
              <div class="space-y-2 max-h-60 overflow-y-auto">
                <div 
                  v-for="item in getConfigItems(selectedConfigNoteType, getConfigTypes(selectedConfigNoteType).type2.key)" 
                  :key="item.id"
                  class="flex items-center justify-between p-2 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors group"
                >
                  <span class="text-sm text-gray-700">{{ item.configName }}</span>
                  <button 
                    @click="deleteConfigItem(item.id)"
                    class="text-red-500 hover:text-red-700 opacity-0 group-hover:opacity-100 transition-opacity text-sm"
                  >
                    <i class="fas fa-trash-alt"></i>
                  </button>
                </div>
                <div v-if="getConfigItems(selectedConfigNoteType, getConfigTypes(selectedConfigNoteType).type2.key).length === 0" class="text-center py-8 text-gray-400">
                  <i class="fas fa-inbox text-3xl mb-2"></i>
                  <p class="text-sm">暂无配置项</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </div>

    <!-- 操作选项 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <h3 class="text-xl font-semibold text-gray-900 mb-6">选择操作</h3>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <!-- 新建笔记 -->
        <div 
          type="button"
          @click="createNewNote"
          class="p-6 border-2 border-dashed border-blue-300 rounded-xl hover:border-blue-500 hover:bg-blue-50 cursor-pointer transition-all duration-200 btn-hover"
        >
          <div class="text-center">
            <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fas fa-plus text-2xl text-blue-600"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2">新建笔记</h4>
            <p class="text-gray-600">创建新的学习笔记、阅读笔记或思考记录</p>
          </div>
        </div>

        <!-- 管理已有笔记 -->
        <div 
          type="button"
          @click="showManageNotes = true"
          class="p-6 border-2 border-dashed border-green-300 rounded-xl hover:border-green-500 hover:bg-green-50 cursor-pointer transition-all duration-200 btn-hover"
        >
          <div class="text-center">
            <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fas fa-edit text-2xl text-green-600"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2">管理笔记</h4>
            <p class="text-gray-600">查看、编辑、删除已有的笔记记录</p>
          </div>
        </div>

        <!-- 笔记归档整理 -->
        <div 
          type="button"
          @click="showArchiveManager = true"
          class="p-6 border-2 border-dashed border-purple-300 rounded-xl hover:border-purple-500 hover:bg-purple-50 cursor-pointer transition-all duration-200 btn-hover"
        >
          <div class="text-center">
            <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fas fa-archive text-2xl text-purple-600"></i>
            </div>
            <h4 class="text-lg font-semibold text-gray-900 mb-2">笔记归档</h4>
            <p class="text-gray-600">查看归档列表，整理笔记来源</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 归档管理主界面 -->
    <Transition name="manage-notes-fade" mode="out-in">
      <div v-if="showArchiveManager" class="bg-white rounded-xl shadow-soft p-6 mb-8">
        <!-- 头部 -->
        <div class="flex items-center justify-between mb-6">
          <div class="flex items-center space-x-3">
            <i class="fas fa-archive text-purple-600 text-2xl"></i>
            <div>
              <h3 class="text-xl font-semibold text-gray-900">归档管理</h3>
              <p class="text-sm text-gray-600">双视角管理：归档→笔记 / 笔记→归档</p>
            </div>
          </div>
          <div class="flex items-center space-x-3">
            <div class="flex bg-gray-100 rounded-lg p-1">
              <button 
                @click="archiveViewMode = 'archive'"
                :class="archiveViewMode === 'archive' ? 'bg-white shadow-sm' : ''"
                class="px-4 py-2 rounded-md text-sm font-medium transition-all"
              >
                <i class="fas fa-folder mr-2"></i>归档视角
              </button>
              <button 
                @click="archiveViewMode = 'note'"
                :class="archiveViewMode === 'note' ? 'bg-white shadow-sm' : ''"
                class="px-4 py-2 rounded-md text-sm font-medium transition-all"
              >
                <i class="fas fa-file-alt mr-2"></i>笔记视角
              </button>
            </div>
            <button 
              type="button"
              @click="closeArchiveManager"
              class="text-gray-500 hover:text-gray-700 transition-colors"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
        </div>

        <!-- 归档视角 -->
        <div v-if="archiveViewMode === 'archive'" class="space-y-4">
          <!-- 工具栏 -->
          <div class="flex items-center space-x-3 pb-4 border-b">
            <div class="relative flex-1">
              <input 
                v-model="archiveViewSearch"
                type="text" 
                placeholder="搜索归档名称..."
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-input pr-10"
              >
              <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            </div>
            <select 
              v-model="archiveViewTypeFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-select"
            >
              <option value="">全部类型</option>
              <option value="book">书籍</option>
              <option value="course">课程</option>
              <option value="material">材料</option>
              <option value="video">视频</option>
              <option value="other">其他</option>
            </select>
            <button 
              @click="openCreateArchiveForBatch"
              class="px-4 py-2 bg-gradient-to-r from-purple-500 to-pink-600 text-white rounded-lg hover:shadow-lg transition-all flex items-center space-x-2 whitespace-nowrap"
            >
              <i class="fas fa-plus"></i>
              <span>新建归档</span>
            </button>
          </div>

          <!-- 归档列表 -->
          <div class="space-y-3 max-h-[600px] overflow-y-auto pr-2">
            <div 
              v-for="archive in filteredArchivesInView" 
              :key="archive.id"
              class="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-all"
            >
              <div class="flex items-start justify-between mb-3">
                <div class="flex-1">
                  <div class="flex items-center space-x-3 mb-2">
                    <h4 class="text-lg font-semibold text-gray-900">{{ archive.archiveName }}</h4>
                    <span class="px-2 py-1 text-xs rounded bg-purple-100 text-purple-600">
                      {{ getArchiveTypeLabel(archive.archiveType) }}
                    </span>
                    <span class="text-sm text-gray-500">
                      <i class="fas fa-file-alt mr-1"></i>{{ archive.noteCount || 0 }} 条笔记
                    </span>
                  </div>
                  <p v-if="archive.description" class="text-sm text-gray-600 mb-2">{{ archive.description }}</p>
                  <p v-if="archive.sourceInfo" class="text-sm text-gray-500">
                    <i class="fas fa-info-circle mr-1"></i>{{ archive.sourceInfo }}
                  </p>
                </div>
                <div class="flex items-center space-x-2">
                  <button 
                    @click="deleteArchiveConfirm(archive.id, archive.archiveName)"
                    class="text-red-500 hover:text-red-700 transition-colors"
                    title="删除归档"
                  >
                    <i class="fas fa-trash-alt"></i>
                  </button>
                  <button 
                    @click="toggleArchiveExpand(archive.id)"
                    class="text-purple-600 hover:text-purple-700 transition-colors"
                  >
                    <i :class="expandedArchives.includes(archive.id) ? 'fas fa-chevron-up' : 'fas fa-chevron-down'"></i>
                  </button>
                </div>
              </div>

              <!-- 展开显示笔记列表 -->
              <Transition name="slide-fade">
                <div v-if="expandedArchives.includes(archive.id)" class="mt-3 pt-3 border-t border-gray-200">
                  <div class="flex items-center justify-between mb-3">
                    <h5 class="text-sm font-semibold text-gray-700">包含的笔记：</h5>
                    <button 
                      @click="addNotesToArchive(archive.id)"
                      class="px-3 py-1 text-sm bg-purple-500 text-white rounded hover:bg-purple-600 transition-colors"
                    >
                      <i class="fas fa-plus mr-1"></i>添加笔记
                    </button>
                  </div>
                  <div class="space-y-2 max-h-[300px] overflow-y-auto">
                    <div 
                      v-for="note in getArchiveNotes(archive.id)" 
                      :key="note.noteId"
                      class="flex items-center justify-between p-2 bg-gray-50 rounded hover:bg-gray-100 transition-colors"
                    >
                      <div class="flex-1">
                        <div class="flex items-center space-x-2">
                          <span :class="getTypeClass(note.type)" class="px-2 py-0.5 rounded text-xs">
                            {{ getTypeText(note.type) }}
                          </span>
                          <span class="text-sm font-medium text-gray-900">{{ note.title }}</span>
                        </div>
                        <p v-if="note.relationNote" class="text-xs text-gray-500 mt-1">
                          <i class="fas fa-bookmark mr-1"></i>{{ note.relationNote }}
                        </p>
                      </div>
                      <button 
                        @click="removeNoteFromArchiveAction(note.relationId, archive.id)"
                        class="text-red-500 hover:text-red-700 text-sm"
                      >
                        <i class="fas fa-times"></i>
                      </button>
                    </div>
                    <div v-if="getArchiveNotes(archive.id).length === 0" class="text-center py-4 text-gray-400 text-sm">
                      暂无笔记，点击上方按钮添加
                    </div>
                  </div>
                </div>
              </Transition>
            </div>

            <div v-if="filteredArchivesInView.length === 0" class="text-center py-12 text-gray-400">
              <i class="fas fa-inbox text-5xl mb-3"></i>
              <p>暂无归档记录</p>
              <button 
                @click="openCreateArchiveForBatch"
                class="mt-4 px-4 py-2 bg-purple-500 text-white rounded-lg hover:bg-purple-600 transition-colors"
              >
                创建第一个归档
              </button>
            </div>
          </div>
        </div>

        <!-- 笔记视角 -->
        <div v-if="archiveViewMode === 'note'" class="space-y-4">
          <!-- 工具栏 -->
          <div class="flex items-center space-x-3 pb-4 border-b">
            <div class="relative flex-1">
              <input 
                v-model="noteViewSearch"
                type="text" 
                placeholder="搜索笔记标题或内容..."
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-input pr-10"
              >
              <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            </div>
            <select 
              v-model="noteViewTypeFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-select"
            >
              <option value="">全部类型</option>
              <option value="fragment">碎片笔记</option>
              <option value="framework">框架笔记</option>
              <option value="study">求学笔记</option>
              <option value="memorization">背诵笔记</option>
              <option value="exercise">刷题笔记</option>
              <option value="practical">实战笔记</option>
            </select>
          </div>

          <!-- 笔记列表 -->
          <div class="space-y-3 max-h-[600px] overflow-y-auto pr-2">
            <div 
              v-for="note in filteredNotesInView" 
              :key="note.id"
              class="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-all"
            >
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center space-x-2 mb-2">
                    <span :class="getTypeClass(note.type)" class="px-2 py-1 rounded text-xs font-medium">
                      {{ getTypeText(note.type) }}
                    </span>
                    <h4 class="text-lg font-semibold text-gray-900">{{ note.title }}</h4>
                  </div>
                  <p v-if="note.summary" class="text-sm text-gray-600 mb-2">{{ note.summary }}</p>
                  
                  <!-- 显示归档信息 -->
                  <div v-if="noteArchiveMap[note.id] && noteArchiveMap[note.id].length > 0" class="flex items-start space-x-2 mt-2">
                    <i class="fas fa-archive text-purple-500 mt-1"></i>
                    <div class="flex-1">
                      <span class="text-xs text-gray-500 mr-2">已归档至：</span>
                      <div class="flex flex-wrap gap-1">
                        <span 
                          v-for="archiveInfo in noteArchiveMap[note.id]" 
                          :key="archiveInfo.archiveId"
                          class="inline-flex items-center px-2 py-1 bg-purple-50 text-purple-700 rounded text-xs"
                        >
                          <i class="fas fa-folder mr-1"></i>
                          {{ archiveInfo.archiveName }}
                          <button 
                            @click="removeNoteFromArchiveAction(archiveInfo.relationId, archiveInfo.archiveId)"
                            class="ml-1 text-purple-500 hover:text-purple-700"
                          >
                            <i class="fas fa-times text-xs"></i>
                          </button>
                        </span>
                      </div>
                    </div>
                  </div>
                  <div v-else class="text-xs text-gray-400 mt-2">
                    <i class="fas fa-inbox mr-1"></i>未归档
                  </div>
                </div>
                <button 
                  @click="quickAddNoteToArchive(note.id)"
                  class="px-3 py-1 text-sm bg-purple-500 text-white rounded hover:bg-purple-600 transition-colors whitespace-nowrap ml-3"
                >
                  <i class="fas fa-plus mr-1"></i>添加到归档
                </button>
              </div>
            </div>

            <div v-if="filteredNotesInView.length === 0" class="text-center py-12 text-gray-400">
              <i class="fas fa-inbox text-5xl mb-3"></i>
              <p>没有找到符合条件的笔记</p>
            </div>
          </div>
        </div>
      </div>
    </Transition>

    <!-- 管理已有笔记 -->
    <Transition name="manage-notes-fade" mode="out-in">
      <div v-if="showManageNotes" class="bg-white rounded-xl shadow-soft p-6 mb-8 max-h-[80vh] flex flex-col">
        <div class="flex items-center justify-between mb-6 flex-shrink-0">
          <h3 class="text-xl font-semibold text-gray-900">管理笔记</h3>
          <button 
            type="button"
            @click="showManageNotes = false"
            class="text-gray-500 hover:text-gray-700 transition-colors"
          >
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>
      
      <!-- 搜索和筛选 -->
      <div class="mb-6 flex-shrink-0">
        <div class="flex flex-col md:flex-row gap-4">
          <div class="flex-1">
            <input 
              v-model="searchQuery"
              type="text" 
              placeholder="搜索笔记标题或内容..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 form-input"
            >
          </div>
          <div>
            <select v-model="filterType" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 form-input">
              <option value="">全部类型</option>
              <option value="study">求学笔记</option>
              <option value="exercise">刷题笔记</option>
              <option value="practical">实战笔记</option>
              <option value="fragment">碎片笔记</option>
              <option value="framework">框架笔记</option>
              <option value="memorization">背诵笔记</option>
            </select>
          </div>
        </div>
      </div>
      
      <!-- 笔记列表 -->
      <div class="space-y-4 flex-1 overflow-y-auto">
        <div v-for="record in paginatedRecords" :key="record.id" class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50">
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center space-x-3 mb-2">
                <h4 class="text-lg font-medium text-gray-900">{{ record.title }}</h4>
                <span :class="getTypeClass(record.type)" class="px-2 py-1 rounded-full text-xs font-medium">
                  {{ getTypeText(record.type) }}
                </span>
              </div>
              
              <!-- 类型特定信息显示 -->
              <div class="mb-2">
                <!-- 求学笔记 - 显示课程信息 -->
                <div v-if="record.type === 'study'" class="space-y-1">
                  <div v-if="record.course && record.course.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-graduation-cap text-blue-500"></i>
                    <span>课程：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="course in record.course" :key="course" class="bg-blue-100 text-blue-600 px-2 py-1 rounded text-xs">
                        {{ course }}
                      </span>
                    </div>
                  </div>
                  <div v-if="record.studySubject && record.studySubject.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-book text-green-500"></i>
                    <span>学科类型：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="subject in record.studySubject" :key="subject" class="bg-green-100 text-green-600 px-2 py-1 rounded text-xs">
                        {{ subject }}
                      </span>
                    </div>
                  </div>
                </div>
                
                <!-- 拓展笔记 - 显示学科项目和知识点 -->
                <div v-if="record.type === 'expansion'" class="space-y-1">
                  <div v-if="record.project" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-book-open text-blue-500"></i>
                    <span>学科项目：{{ record.project }}</span>
                  </div>
                  <div v-if="record.knowledgePoint && record.knowledgePoint.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-lightbulb text-blue-500"></i>
                    <span>知识点：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="point in record.knowledgePoint" :key="point" class="bg-blue-100 text-blue-600 px-2 py-1 rounded text-xs">
                        {{ point }}
                      </span>
                    </div>
                  </div>
                </div>
                
                <!-- 书籍笔记 - 显示书籍信息 -->
                <div v-if="record.type === 'book'" class="space-y-1">
                  <div v-if="record.bookName" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-book-open text-green-500"></i>
                    <span>书名：{{ record.bookName }}</span>
                  </div>
                  <div v-if="record.bookSubject && record.bookSubject.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-book text-green-500"></i>
                    <span>学科类型：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="subject in record.bookSubject" :key="subject" class="bg-green-100 text-green-600 px-2 py-1 rounded text-xs">
                        {{ subject }}
                      </span>
                    </div>
                  </div>
                </div>
                
                <!-- 刷题笔记 - 显示题目信息 -->
                <div v-if="record.type === 'exercise'" class="space-y-1">
                  <div v-if="record.exerciseSource" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-code text-indigo-500"></i>
                    <span>{{ record.exerciseSource }}</span>
                  </div>
                  <div v-if="record.subjectType && record.subjectType.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-book text-blue-500"></i>
                    <span>学科类型：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="type in record.subjectType" :key="type" class="bg-blue-100 text-blue-600 px-2 py-1 rounded text-xs">
                        {{ type }}
                      </span>
                    </div>
                  </div>
                  <div v-if="record.knowledgePoint && record.knowledgePoint.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-lightbulb text-green-500"></i>
                    <span>知识点：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="point in record.knowledgePoint" :key="point" class="bg-green-100 text-green-600 px-2 py-1 rounded text-xs">
                        {{ point }}
                      </span>
                    </div>
                  </div>
                  <div v-if="record.exerciseDifficulty" class="flex items-center space-x-2 text-sm">
                    <i class="fas fa-signal text-yellow-500"></i>
                    <span :class="getDifficultyClass(record.exerciseDifficulty)">
                      难度：{{ getDifficultyText(record.exerciseDifficulty) }}
                    </span>
                  </div>
                </div>
                
                <!-- 实战笔记 - 显示项目信息 -->
                <div v-if="record.type === 'practical'" class="space-y-1">
                  <div v-if="record.techTags && record.techTags.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-tools text-blue-500"></i>
                    <span>技术栈：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="tag in record.techTags" :key="tag" class="bg-blue-100 text-blue-600 px-2 py-1 rounded text-xs">
                        {{ tag }}
                      </span>
                    </div>
                  </div>
                  <div v-if="record.projectType && record.projectType.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-project-diagram text-green-500"></i>
                    <span>项目类型：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="type in record.projectType" :key="type" class="bg-green-100 text-green-600 px-2 py-1 rounded text-xs">
                        {{ type }}
                      </span>
                    </div>
                  </div>
                </div>
                
                <!-- 碎片笔记 - 显示分类信息 -->
                <div v-if="record.type === 'fragment'" class="space-y-1">
                  <div v-if="record.fragmentCategory && record.fragmentCategory.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-tags text-blue-500"></i>
                    <span>分类：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="category in record.fragmentCategory" :key="category" class="bg-blue-100 text-blue-600 px-2 py-1 rounded text-xs">
                        {{ category }}
                      </span>
                    </div>
                  </div>
                  <div v-if="record.fragmentTheme && record.fragmentTheme.length > 0" class="flex items-center space-x-2 text-sm text-gray-600">
                    <i class="fas fa-lightbulb text-green-500"></i>
                    <span>主题：</span>
                    <div class="flex flex-wrap gap-1">
                      <span v-for="theme in record.fragmentTheme" :key="theme" class="bg-green-100 text-green-600 px-2 py-1 rounded text-xs">
                        {{ theme }}
                      </span>
                    </div>
                  </div>
                  <div v-if="record.importance" class="flex items-center space-x-2 text-sm">
                    <i class="fas fa-star text-yellow-500"></i>
                    <span :class="getImportanceClass(record.importance)">
                      重要程度：{{ getImportanceText(record.importance) }}
                    </span>
                  </div>
                </div>
              </div>
              
              <p v-if="record.summary" class="text-gray-600 mb-2 font-medium">{{ record.summary }}</p>
              <div class="text-gray-600 mb-3" v-html="record.content"></div>
              <div class="flex items-center space-x-4 text-sm text-gray-500">
                <span>{{ formatDate(record.recCreateTime) }}</span>
              </div>
            </div>
            <div class="flex space-x-2">
              <button type="button" @click="editRecord(record)" class="text-blue-600 hover:text-blue-800">
                <i class="fas fa-edit"></i>
              </button>
              <button type="button" @click="deleteRecord(record.id)" class="text-red-600 hover:text-red-800">
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 笔记分页控制 -->
      <div class="flex items-center justify-center space-x-4 mt-6 flex-shrink-0">
        <button 
          type="button"
          @click="prevRecordPage" 
          :disabled="recordCurrentPage === 1"
          class="px-4 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <i class="fas fa-chevron-left mr-1"></i>上一页
        </button>
        <span class="text-sm text-gray-600">
          {{ recordCurrentPage }} / {{ recordTotalPages }}
          <span class="text-gray-400 ml-2">(共 {{ filteredRecords.length }} 条记录)</span>
        </span>
        <button 
          type="button"
          @click="nextRecordPage" 
          :disabled="recordCurrentPage === recordTotalPages"
          class="px-4 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          下一页<i class="fas fa-chevron-right ml-1"></i>
        </button>
      </div>
    </div>
    </Transition>


    <!-- 新建/编辑笔记弹窗 -->
    <Transition name="modal">
      <div v-if="showCreateForm" class="fixed inset-0 z-50 flex items-center justify-center">
        <!-- 背景遮罩 -->
        <Transition name="modal-backdrop">
          <div 
            class="absolute inset-0 bg-black bg-opacity-50 transition-opacity duration-300 ease-in-out"
            @click="closeModal"
          ></div>
        </Transition>
        
        <!-- 弹窗内容 -->
        <div class="relative bg-white rounded-xl shadow-2xl w-full max-w-5xl max-h-[95vh] overflow-hidden transform transition-all duration-300 ease-out scale-100 opacity-100 flex flex-col">
          <!-- 弹窗头部 -->
          <div class="flex items-center justify-between p-6 border-b border-gray-200">
            <h3 class="text-xl font-semibold text-gray-900">
              {{ isEditing ? '编辑笔记' : '新建笔记' }}
            </h3>
            <button 
              type="button"
              @click="closeModal"
              class="text-gray-500 hover:text-gray-700 transition-colors duration-200"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          
          <!-- 弹窗内容区域 -->
          <div class="flex-1 overflow-y-auto p-6 max-h-[calc(95vh-120px)]">
            <form @submit.prevent="submitRecord" class="space-y-6">
              <!-- 记录类型选择 -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">记录类型</label>
                  <select v-model="recordForm.type" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 form-input">
                    <option value="">请选择类型</option>
                    <option value="fragment">碎片笔记</option>
                    <option value="framework">框架笔记</option>
                    <option value="study">求学笔记</option>
                    <option value="expansion">拓展笔记</option>
                    <option value="exercise">刷题笔记</option>
                    <option value="practical">实战笔记</option>
                  </select>
                </div>

              </div>

              <!-- 未选择类型时的提示 -->
              <div v-if="!recordForm.type" class="bg-gradient-to-br from-blue-50 to-indigo-50 rounded-xl p-8 border border-blue-200 shadow-sm">
                <div class="text-center">
                  <div class="w-16 h-16 bg-gradient-to-r from-blue-500 to-indigo-500 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-pencil-alt text-white text-2xl"></i>
                  </div>
                  <h3 class="text-lg font-semibold text-gray-800 mb-2">选择笔记类型开始记录</h3>
                  <p class="text-gray-600 mb-4">请先在上方选择您要创建的笔记类型，然后填写相应的内容字段</p>
                  <div class="grid grid-cols-2 md:grid-cols-3 gap-3 text-sm">
                    <div class="bg-white bg-opacity-60 rounded-lg p-3 border border-blue-100">
                      <i class="fas fa-puzzle-piece text-purple-500 mb-1"></i>
                      <div class="font-medium text-purple-700">碎片笔记</div>
                      <div class="text-gray-600 text-xs">记录灵感思考</div>
                    </div>
                    <div class="bg-white bg-opacity-60 rounded-lg p-3 border border-blue-100">
                      <i class="fas fa-sitemap text-blue-500 mb-1"></i>
                      <div class="font-medium text-blue-700">框架笔记</div>
                      <div class="text-gray-600 text-xs">知识体系构建</div>
                    </div>
                    <div class="bg-white bg-opacity-60 rounded-lg p-3 border border-blue-100">
                      <i class="fas fa-graduation-cap text-green-500 mb-1"></i>
                      <div class="font-medium text-green-700">求学笔记</div>
                      <div class="text-gray-600 text-xs">学习知识记录</div>
                    </div>
                    <div class="bg-white bg-opacity-60 rounded-lg p-3 border border-blue-100">
                      <i class="fas fa-book-open text-blue-500 mb-1"></i>
                      <div class="font-medium text-blue-700">拓展笔记</div>
                      <div class="text-gray-600 text-xs">原文理解拓展</div>
                    </div>
                    <div class="bg-white bg-opacity-60 rounded-lg p-3 border border-blue-100">
                      <i class="fas fa-code text-red-500 mb-1"></i>
                      <div class="font-medium text-red-700">刷题笔记</div>
                      <div class="text-gray-600 text-xs">算法练习记录</div>
                    </div>
                    <div class="bg-white bg-opacity-60 rounded-lg p-3 border border-blue-100">
                      <i class="fas fa-tools text-cyan-500 mb-1"></i>
                      <div class="font-medium text-cyan-700">实战笔记</div>
                      <div class="text-gray-600 text-xs">项目实践总结</div>
                    </div>
                  </div>
                </div>
              </div>

                            <!-- 动态特色字段 -->
              <div v-if="recordForm.type" class="bg-white rounded-xl border border-gray-200 shadow-sm p-2">
                <div class="bg-gradient-to-r from-gray-50 to-gray-100 rounded-lg p-4 mb-4">
                  <h4 class="text-lg font-semibold text-gray-800 flex items-center">
                    <i class="fas fa-cogs text-gray-600 mr-2"></i>
                    {{ getTypeSpecificTitle() }}
                  </h4>
                  <p class="text-sm text-gray-600 mt-1">配置专属于此笔记类型的特殊字段</p>
                </div>
                
                <!-- 碎片笔记 -->
                <div v-if="recordForm.type === 'fragment'" class="space-y-3 bg-gradient-to-br from-purple-50 to-indigo-50 rounded-xl p-6 border border-purple-200 shadow-sm">
                  <div class="flex items-center mb-4">
                    <div class="w-8 h-8 bg-gradient-to-r from-purple-500 to-indigo-500 rounded-lg flex items-center justify-center mr-3">
                      <i class="fas fa-puzzle-piece text-white text-sm"></i>
                    </div>
                    <h4 class="text-lg font-semibold text-purple-900">碎片笔记配置</h4>
                </div>
                <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">分类</label>
                      <button 
                        type="button"
                        @click="openConfigManager('fragment', 'category')"
                        class="text-sm text-purple-600 hover:text-purple-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/添加分类 -->
                    <TagSearchInput
                      v-model:selected="recordForm.fragmentCategory"
                      :all-options="fragmentCategories"
                      placeholder="搜索或新建分类..."
                      color="blue"
                      @add="handleFragmentCategoryAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.fragmentCategory.length > 0"
                      :tags="recordForm.fragmentCategory"
                      color="blue"
                      @remove="removeFragmentCategory"
                    />
                  </div>
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">主题</label>
                      <button 
                        type="button"
                        @click="openConfigManager('fragment', 'theme')"
                        class="text-sm text-purple-600 hover:text-purple-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/添加主题 -->
                    <TagSearchInput
                      v-model:selected="recordForm.fragmentTheme"
                      :all-options="fragmentThemes"
                      placeholder="搜索或新建主题..."
                      color="green"
                      @add="handleFragmentThemeAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.fragmentTheme.length > 0"
                      :tags="recordForm.fragmentTheme"
                      color="green"
                      @remove="removeFragmentTheme"
                    />
                  </div>
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">重要程度</label>
                    <div class="flex space-x-3">
                      <label class="flex items-center">
                        <input type="radio" v-model="recordForm.importance" value="low" class="mr-2">
                        <span class="text-sm text-green-600">低</span>
                      </label>
                      <label class="flex items-center">
                        <input type="radio" v-model="recordForm.importance" value="medium" class="mr-2">
                        <span class="text-sm text-yellow-600">中</span>
                      </label>
                      <label class="flex items-center">
                        <input type="radio" v-model="recordForm.importance" value="high" class="mr-2">
                        <span class="text-sm text-red-600">高</span>
                      </label>
                    </div>
                  </div>
                  
                  <!-- 碎片笔记内容编辑 -->
                  <div class="pt-4 border-t border-purple-200 mt-4">
                    <div class="flex items-center mb-4">
                      <div class="w-6 h-6 bg-gradient-to-r from-purple-500 to-indigo-500 rounded-lg flex items-center justify-center mr-2">
                        <i class="fas fa-pen text-white text-xs"></i>
                      </div>
                      <h5 class="text-md font-semibold text-purple-800">碎片内容编辑</h5>
                    </div>

                    <!-- 标题 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-purple-800 mb-2">
                        <i class="fas fa-heading text-purple-600 mr-1"></i>
                        标题
                      </label>
                      <input 
                        v-model="recordForm.title" 
                        type="text" 
                        placeholder="请输入标题"
                        class="w-full px-4 py-3 border border-purple-200 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 bg-white shadow-sm form-input"
                      >
                    </div>

                    <!-- 摘要 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-purple-800 mb-2">
                        <i class="fas fa-align-left text-purple-600 mr-1"></i>
                        摘要
                      </label>
                      <textarea 
                        v-model="recordForm.summary" 
                        rows="3" 
                        placeholder="请输入摘要..."
                        class="w-full px-4 py-3 border border-purple-200 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 bg-white shadow-sm form-input"
                      ></textarea>
                    </div>
                    
                    <!-- 内容编辑器 -->
                    <div>
                      <label class="block text-sm font-medium text-purple-800 mb-2">
                        <i class="fas fa-file-alt text-purple-600 mr-1"></i>
                        内容
                      </label>
                      <RichTextEditor 
                        :editor="editor"
                        border-color="purple"
                        @insert-table="insertTable"
                        @insert-image="insertImage"
                        @upload-image="uploadImage"
                        @set-link="setLink"
                        @import-markdown="importMarkdown(editor)"
                      />
                    </div>
                  </div>
                </div>

                <!-- 框架笔记 -->
                <div v-if="recordForm.type === 'framework'" class="space-y-3 bg-gradient-to-br from-blue-50 to-sky-50 rounded-xl p-6 border border-blue-200 shadow-sm">
                  <div class="flex items-center mb-4">
                    <div class="w-8 h-8 bg-gradient-to-r from-blue-500 to-sky-500 rounded-lg flex items-center justify-center mr-3">
                      <i class="fas fa-sitemap text-white text-sm"></i>
                    </div>
                    <h4 class="text-lg font-semibold text-blue-900">框架笔记配置</h4>
                  </div>
                  <!-- 学科类型 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">学科类型</label>
                      <button 
                        type="button"
                        @click="openConfigManager('framework', 'subjectType')"
                        class="text-sm text-blue-600 hover:text-blue-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/添加学科类型 -->
                    <TagSearchInput
                      v-model:selected="recordForm.subjectType"
                      :all-options="frameworkSubjects"
                      placeholder="搜索或新建学科类型..."
                      color="blue"
                      @add="handleFrameworkSubjectAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.subjectType.length > 0"
                      :tags="recordForm.subjectType"
                      color="blue"
                      @remove="removeFrameworkSubject"
                    />
                  </div>

                  <!-- 知识点类型 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">知识点类型</label>
                      <button 
                        type="button"
                        @click="openConfigManager('framework', 'knowledgePoint')"
                        class="text-sm text-green-600 hover:text-green-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/添加知识点 -->
                    <TagSearchInput
                      v-model:selected="recordForm.knowledgePoint"
                      :all-options="frameworkKnowledge"
                      placeholder="搜索或新建知识点..."
                      color="green"
                      @add="handleFrameworkKnowledgeAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.knowledgePoint.length > 0"
                      :tags="recordForm.knowledgePoint"
                      color="green"
                      @remove="removeFrameworkKnowledge"
                    />
                  </div>
                  
                  <!-- 框架笔记内容编辑 -->
                  <div class="pt-4 border-t border-blue-200 mt-4">
                    <div class="flex items-center mb-4">
                      <div class="w-6 h-6 bg-gradient-to-r from-blue-500 to-sky-500 rounded-lg flex items-center justify-center mr-2">
                        <i class="fas fa-edit text-white text-xs"></i>
                      </div>
                      <h5 class="text-md font-semibold text-blue-800">框架内容编辑</h5>
                    </div>

                    <!-- 标题 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-blue-800 mb-2">
                        <i class="fas fa-heading text-blue-600 mr-1"></i>
                        标题
                      </label>
                      <input 
                        v-model="recordForm.title" 
                        type="text" 
                        placeholder="请输入标题"
                        class="w-full px-4 py-3 border border-blue-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white shadow-sm form-input"
                      >
                    </div>

                    <!-- 摘要 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-blue-800 mb-2">
                        <i class="fas fa-align-left text-blue-600 mr-1"></i>
                        摘要
                      </label>
                      <textarea 
                        v-model="recordForm.summary" 
                        rows="3" 
                        placeholder="请输入摘要..."
                        class="w-full px-4 py-3 border border-blue-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white shadow-sm form-input"
                      ></textarea>
                    </div>
                    
                    <!-- 内容编辑器 -->
                    <div>
                      <label class="block text-sm font-medium text-blue-800 mb-2">
                        <i class="fas fa-file-alt text-blue-600 mr-1"></i>
                        内容
                      </label>
                      <RichTextEditor 
                        :editor="editor"
                        border-color="blue"
                        @insert-table="insertTable"
                        @insert-image="insertImage"
                        @upload-image="uploadImage"
                        @set-link="setLink"
                        @import-markdown="importMarkdown(editor)"
                      />
                    </div>
                  </div>
                </div>

                <!-- 求学笔记 -->
                <div v-if="recordForm.type === 'study'" class="space-y-3 bg-gradient-to-br from-emerald-50 to-green-50 rounded-xl p-6 border border-emerald-200 shadow-sm">
                  <div class="flex items-center mb-4">
                    <div class="w-8 h-8 bg-gradient-to-r from-emerald-500 to-green-500 rounded-lg flex items-center justify-center mr-3">
                      <i class="fas fa-graduation-cap text-white text-sm"></i>
                    </div>
                    <h4 class="text-lg font-semibold text-emerald-900">求学笔记配置</h4>
                  </div>
                  
                  <!-- 学科 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">学科</label>
                      <button 
                        type="button"
                        @click="openConfigManager('study', 'subject')"
                        class="text-sm text-emerald-600 hover:text-emerald-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/添加学科 -->
                    <TagSearchInput
                      v-model:selected="recordForm.studySubject"
                      :all-options="studySubjects"
                      placeholder="搜索或新建学科..."
                      color="blue"
                      @add="handleStudySubjectAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.studySubject.length > 0"
                      :tags="recordForm.studySubject"
                      color="blue"
                      @remove="removeStudySubject"
                    />
                  </div>

                  <!-- 知识点 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">知识点</label>
                      <button 
                        type="button"
                        @click="openConfigManager('study', 'knowledgePoint')"
                        class="text-sm text-emerald-600 hover:text-emerald-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/添加知识点 -->
                    <TagSearchInput
                      v-model:selected="recordForm.knowledgePoint"
                      :all-options="studyKnowledge"
                      placeholder="搜索或新建知识点..."
                      color="green"
                      @add="handleStudyKnowledgeAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.knowledgePoint.length > 0"
                      :tags="recordForm.knowledgePoint"
                      color="green"
                      @remove="removeKnowledgePoint"
                    />
                  </div>
                  
                  <!-- 求学笔记内容编辑 -->
                  <div class="pt-4 border-t border-emerald-200 mt-4">
                    <div class="flex items-center mb-4">
                      <div class="w-6 h-6 bg-gradient-to-r from-emerald-500 to-green-500 rounded-lg flex items-center justify-center mr-2">
                        <i class="fas fa-book-open text-white text-xs"></i>
                      </div>
                      <h5 class="text-md font-semibold text-emerald-800">求学内容编辑</h5>
                    </div>

                    <!-- 标题 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-heading text-emerald-600 mr-1"></i>
                        标题
                      </label>
                      <input 
                        v-model="recordForm.title" 
                        type="text" 
                        placeholder="请输入标题"
                        class="w-full px-4 py-3 border border-emerald-200 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 bg-white shadow-sm form-input"
                      >
                    </div>

                    <!-- 摘要 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-align-left text-emerald-600 mr-1"></i>
                        摘要
                      </label>
                      <textarea 
                        v-model="recordForm.summary" 
                        rows="3" 
                        placeholder="请输入摘要..."
                        class="w-full px-4 py-3 border border-emerald-200 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 bg-white shadow-sm form-input"
                      ></textarea>
                    </div>
                    
                    <!-- 核心概念 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-lightbulb text-emerald-600 mr-1"></i>
                        核心概念
                      </label>
                      <RichTextEditor 
                        :editor="coreConceptEditor"
                        border-color="emerald"
                        @insert-table="insertStudyTable(coreConceptEditor)"
                        @insert-image="insertStudyImage(coreConceptEditor)"
                        @upload-image="uploadStudyImage(coreConceptEditor)"
                        @set-link="setStudyLink(coreConceptEditor)"
                        @import-markdown="importMarkdown(coreConceptEditor)"
                      />
                    </div>

                    <!-- 机制原理 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-cogs text-emerald-600 mr-1"></i>
                        机制原理
                      </label>
                      <RichTextEditor 
                        :editor="mechanismEditor"
                        border-color="emerald"
                        @insert-table="insertStudyTable(mechanismEditor)"
                        @insert-image="insertStudyImage(mechanismEditor)"
                        @upload-image="uploadStudyImage(mechanismEditor)"
                        @set-link="setStudyLink(mechanismEditor)"
                        @import-markdown="importMarkdown(mechanismEditor)"
                      />
                    </div>

                    <!-- 应用案例 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-tasks text-emerald-600 mr-1"></i>
                        应用案例
                      </label>
                      <RichTextEditor 
                        :editor="applicationCaseEditor"
                        border-color="emerald"
                        @insert-table="insertStudyTable(applicationCaseEditor)"
                        @insert-image="insertStudyImage(applicationCaseEditor)"
                        @upload-image="uploadStudyImage(applicationCaseEditor)"
                        @set-link="setStudyLink(applicationCaseEditor)"
                        @import-markdown="importMarkdown(applicationCaseEditor)"
                      />
                    </div>

                    <!-- 延伸/对比 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-project-diagram text-emerald-600 mr-1"></i>
                        延伸/对比
                      </label>
                      <RichTextEditor 
                        :editor="extensionEditor"
                        border-color="emerald"
                        @insert-table="insertStudyTable(extensionEditor)"
                        @insert-image="insertStudyImage(extensionEditor)"
                        @upload-image="uploadStudyImage(extensionEditor)"
                        @set-link="setStudyLink(extensionEditor)"
                        @import-markdown="importMarkdown(extensionEditor)"
                      />
                    </div>

                    <!-- 常见误区 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-exclamation-triangle text-emerald-600 mr-1"></i>
                        常见误区
                      </label>
                      <RichTextEditor 
                        :editor="commonMistakeEditor"
                        border-color="emerald"
                        @insert-table="insertStudyTable(commonMistakeEditor)"
                        @insert-image="insertStudyImage(commonMistakeEditor)"
                        @upload-image="uploadStudyImage(commonMistakeEditor)"
                        @set-link="setStudyLink(commonMistakeEditor)"
                        @import-markdown="importMarkdown(commonMistakeEditor)"
                      />
                    </div>

                    <!-- 思考理解 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-emerald-800 mb-2">
                        <i class="fas fa-brain text-emerald-600 mr-1"></i>
                        思考理解
                      </label>
                      <RichTextEditor 
                        :editor="reflectionEditor"
                        border-color="emerald"
                        @insert-table="insertStudyTable(reflectionEditor)"
                        @insert-image="insertStudyImage(reflectionEditor)"
                        @upload-image="uploadStudyImage(reflectionEditor)"
                        @set-link="setStudyLink(reflectionEditor)"
                        @import-markdown="importMarkdown(reflectionEditor)"
                      />
                    </div>
                  </div>
                </div>

                <!-- 拓展笔记 -->
                <div v-if="recordForm.type === 'expansion'" class="space-y-3 bg-gradient-to-br from-blue-50 to-indigo-50 rounded-xl p-6 border border-blue-200 shadow-sm">
                  <div class="flex items-center mb-4">
                    <div class="w-8 h-8 bg-gradient-to-r from-blue-500 to-indigo-600 rounded-lg flex items-center justify-center mr-3">
                      <i class="fas fa-book-open text-white text-sm"></i>
                    </div>
                    <h4 class="text-lg font-semibold text-blue-900">拓展笔记配置</h4>
                  </div>
                  
                  <!-- 学科项目 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">学科项目</label>
                      <button 
                        type="button"
                        @click="openConfigManager('expansion', 'project')"
                        class="text-sm text-blue-600 hover:text-blue-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <input 
                      v-model="recordForm.project" 
                      type="text" 
                      placeholder="请输入学科项目"
                      class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 form-input"
                    >
                  </div>
                  
                  <!-- 知识点 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">知识点</label>
                      <button 
                        type="button"
                        @click="openConfigManager('expansion', 'knowledgePoint')"
                        class="text-sm text-blue-600 hover:text-blue-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <TagSearchInput
                      v-model:selected="recordForm.knowledgePoint"
                      :all-options="expansionKnowledge"
                      placeholder="搜索或新建知识点..."
                      color="blue"
                      @add="handleExpansionKnowledgeAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.knowledgePoint.length > 0"
                      :tags="recordForm.knowledgePoint"
                      color="blue"
                      @remove="removeKnowledgePoint"
                    />
                  </div>
                  
                  <!-- 拓展笔记专用内容字段 -->
                  <div class="pt-4 border-t border-blue-200 mt-4">
                    <div class="flex items-center mb-4">
                      <div class="w-6 h-6 bg-gradient-to-r from-blue-500 to-indigo-600 rounded-lg flex items-center justify-center mr-2">
                        <i class="fas fa-brain text-white text-xs"></i>
                      </div>
                      <h5 class="text-md font-semibold text-blue-800">拓展内容编辑</h5>
                    </div>

                    <!-- 标题 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-blue-800 mb-2">
                        <i class="fas fa-heading text-blue-600 mr-1"></i>
                        标题
                      </label>
                      <input 
                        v-model="recordForm.title" 
                        type="text" 
                        placeholder="请输入标题"
                        class="w-full px-4 py-3 border border-blue-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white shadow-sm form-input"
                      >
                    </div>

                    <!-- 摘要 -->
                    <div class="mb-6">
                      <label class="block text-sm font-medium text-blue-800 mb-2">
                        <i class="fas fa-align-left text-blue-600 mr-1"></i>
                        摘要
                      </label>
                      <textarea 
                        v-model="recordForm.summary" 
                        rows="3" 
                        placeholder="请输入摘要..."
                        class="w-full px-4 py-3 border border-blue-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white shadow-sm form-input"
                      ></textarea>
                    </div>
                    
                    <!-- 原文记录编辑器 -->
                    <div class="mb-6">
                      <label class="block text-sm font-medium text-gray-700 mb-2">
                        <i class="fas fa-file-text text-blue-600 mr-1"></i>
                        原文记录
                      </label>
                      <RichTextEditor 
                        :editor="originalTextEditor"
                        border-color="gray"
                        @insert-table="insertExpansionTable(originalTextEditor)"
                        @insert-image="insertExpansionImage(originalTextEditor)"
                        @upload-image="uploadExpansionImage(originalTextEditor)"
                        @set-link="setExpansionLink(originalTextEditor)"
                        @import-markdown="importMarkdown(originalTextEditor)"
                      />
                      <p class="text-xs text-gray-500 mt-1">输入需要拓展的原始文本内容</p>
                    </div>
                    
                    <!-- 理解记录编辑器 -->
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">
                        <i class="fas fa-brain text-blue-600 mr-1"></i>
                        理解记录
                      </label>
                      <RichTextEditor 
                        :editor="understandingEditor"
                        border-color="gray"
                        @insert-table="insertExpansionTable(understandingEditor)"
                        @insert-image="insertExpansionImage(understandingEditor)"
                        @upload-image="uploadExpansionImage(understandingEditor)"
                        @set-link="setExpansionLink(understandingEditor)"
                        @import-markdown="importMarkdown(understandingEditor)"
                      />
                      <p class="text-xs text-gray-500 mt-1">对原文内容的深度理解和分析</p>
                    </div>
                  </div>
                </div>

                <!-- 刷题笔记 -->
                <div v-if="recordForm.type === 'exercise'" class="space-y-3 bg-gradient-to-br from-rose-50 to-pink-50 rounded-xl p-6 border border-rose-200 shadow-sm">
                  <div class="flex items-center mb-4">
                    <div class="w-8 h-8 bg-gradient-to-r from-rose-500 to-pink-500 rounded-lg flex items-center justify-center mr-3">
                      <i class="fas fa-code text-white text-sm"></i>
                    </div>
                    <h4 class="text-lg font-semibold text-rose-900">刷题笔记配置</h4>
                  </div>
                  <!-- 题目来源 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">题目来源</label>
                      <button 
                        type="button"
                        @click="openConfigManager('exercise', 'source')"
                        class="text-sm text-rose-600 hover:text-rose-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    <div class="space-y-2">
                      <select 
                      v-model="recordForm.exerciseSource"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-red-500 focus:border-red-500 form-input"
                      >
                        <option value="">请选择题目来源</option>
                        <option v-for="source in availableExerciseSources" :key="source" :value="source">
                          {{ source }}
                        </option>
                      </select>
                    </div>
                  </div>

                  <!-- 学科类型 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">学科类型</label>
                      <button 
                        type="button"
                        @click="openConfigManager('exercise', 'subject')"
                        class="text-sm text-rose-600 hover:text-rose-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    <div class="space-y-2">
                      <select 
                        v-model="recordForm.exerciseSubject"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 form-input"
                      >
                        <option value="">请选择学科类型</option>
                        <option v-for="subject in availableExerciseSubjects" :key="subject" :value="subject">
                          {{ subject }}
                        </option>
                      </select>
                    </div>
                  </div>
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">难度等级</label>
                    <div class="flex space-x-3">
                      <label class="flex items-center">
                        <input type="radio" v-model="recordForm.exerciseDifficulty" value="easy" class="mr-2">
                        <span class="text-sm text-green-600">简单</span>
                      </label>
                      <label class="flex items-center">
                        <input type="radio" v-model="recordForm.exerciseDifficulty" value="medium" class="mr-2">
                        <span class="text-sm text-yellow-600">中等</span>
                      </label>
                      <label class="flex items-center">
                        <input type="radio" v-model="recordForm.exerciseDifficulty" value="hard" class="mr-2">
                        <span class="text-sm text-red-600">困难</span>
                      </label>
                    </div>
                  </div>
                  
                  <!-- 刷题笔记内容编辑 -->
                  <div class="pt-4 border-t border-rose-200 mt-4">
                    <div class="flex items-center mb-4">
                      <div class="w-6 h-6 bg-gradient-to-r from-rose-500 to-pink-500 rounded-lg flex items-center justify-center mr-2">
                        <i class="fas fa-keyboard text-white text-xs"></i>
                      </div>
                      <h5 class="text-md font-semibold text-rose-800">刷题内容编辑</h5>
                    </div>

                    <!-- 标题 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-rose-800 mb-2">
                        <i class="fas fa-heading text-rose-600 mr-1"></i>
                        标题
                      </label>
                      <input 
                        v-model="recordForm.title" 
                        type="text" 
                        placeholder="请输入标题"
                        class="w-full px-4 py-3 border border-rose-200 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 bg-white shadow-sm form-input"
                      >
                    </div>

                    <!-- 摘要 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-rose-800 mb-2">
                        <i class="fas fa-align-left text-rose-600 mr-1"></i>
                        摘要
                      </label>
                      <textarea 
                        v-model="recordForm.summary" 
                        rows="3" 
                        placeholder="请输入摘要..."
                        class="w-full px-4 py-3 border border-rose-200 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 bg-white shadow-sm form-input"
                      ></textarea>
                    </div>

                    <!-- 题目描述 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-rose-800 mb-2">
                        <i class="fas fa-file-lines text-rose-600 mr-1"></i>
                        题目描述
                      </label>
                      <RichTextEditor 
                        :editor="questionDescriptionEditor"
                        border-color="rose"
                        @insert-table="insertExerciseTable(questionDescriptionEditor)"
                        @insert-image="insertExerciseImage(questionDescriptionEditor)"
                        @upload-image="uploadExerciseImage(questionDescriptionEditor)"
                        @set-link="setExerciseLink(questionDescriptionEditor)"
                        @import-markdown="importMarkdown(questionDescriptionEditor)"
                      />
                    </div>

                    <!-- 分析理解 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-rose-800 mb-2">
                        <i class="fas fa-lightbulb text-rose-600 mr-1"></i>
                        分析理解
                      </label>
                      <RichTextEditor 
                        :editor="analysisEditor"
                        border-color="rose"
                        @insert-table="insertExerciseTable(analysisEditor)"
                        @insert-image="insertExerciseImage(analysisEditor)"
                        @upload-image="uploadExerciseImage(analysisEditor)"
                        @set-link="setExerciseLink(analysisEditor)"
                        @import-markdown="importMarkdown(analysisEditor)"
                      />
                    </div>

                    <!-- 参考答案 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-rose-800 mb-2">
                        <i class="fas fa-check-circle text-rose-600 mr-1"></i>
                        参考答案
                      </label>
                      <RichTextEditor 
                        :editor="referenceAnswerEditor"
                        border-color="rose"
                        @insert-table="insertExerciseTable(referenceAnswerEditor)"
                        @insert-image="insertExerciseImage(referenceAnswerEditor)"
                        @upload-image="uploadExerciseImage(referenceAnswerEditor)"
                        @set-link="setExerciseLink(referenceAnswerEditor)"
                        @import-markdown="importMarkdown(referenceAnswerEditor)"
                      />
                    </div>
                  </div>
                </div>

                <!-- 实战笔记 -->
                <div v-if="recordForm.type === 'practical'" class="space-y-3 bg-gradient-to-br from-cyan-50 to-teal-50 rounded-xl p-6 border border-cyan-200 shadow-sm">
                  <div class="flex items-center mb-4">
                    <div class="w-8 h-8 bg-gradient-to-r from-cyan-500 to-teal-500 rounded-lg flex items-center justify-center mr-3">
                      <i class="fas fa-tools text-white text-sm"></i>
                    </div>
                    <h4 class="text-lg font-semibold text-cyan-900">实战笔记配置</h4>
                  </div>
                  
                  <!-- 领域 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">项目领域</label>
                      <button 
                        type="button"
                        @click="openConfigManager('practical', 'domain')"
                        class="text-sm text-cyan-600 hover:text-cyan-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/选择领域（单选） -->
                    <div class="relative">
                      <div class="flex items-center space-x-2">
                        <div class="relative flex-1">
                          <input 
                            v-model="practicalDomainSearch"
                            type="text" 
                            placeholder="搜索或新建领域..."
                            @focus="showPracticalDomainDropdown = true"
                            @input="filterPracticalDomains"
                            @keyup.enter="addPracticalDomainFromSearch"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-cyan-500 form-input pr-8"
                          >
                          <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                        </div>
                        <button 
                          type="button"
                          @click="addPracticalDomainFromSearch"
                          :disabled="!practicalDomainSearch.trim()"
                          class="px-4 py-2 bg-cyan-600 text-white rounded-lg hover:bg-cyan-700 disabled:bg-gray-300 disabled:cursor-not-allowed flex items-center space-x-1 btn-hover whitespace-nowrap"
                        >
                          <i class="fas fa-plus"></i>
                          <span>添加</span>
                        </button>
                      </div>
                      
                      <!-- 下拉搜索结果（单选） -->
                      <div v-if="showPracticalDomainDropdown && filteredPracticalDomains.length > 0" 
                           class="absolute z-10 w-full mt-1 bg-white border border-gray-300 rounded-lg shadow-lg max-h-48 overflow-y-auto">
                        <button
                          v-for="domain in filteredPracticalDomains"
                          :key="domain"
                          type="button"
                          @click="selectPracticalDomainFromDropdown(domain)"
                          class="w-full text-left px-4 py-2 hover:bg-cyan-50 transition-colors flex items-center justify-between group"
                        >
                          <span>{{ domain }}</span>
                          <i v-if="recordForm.projectDomain !== domain" class="fas fa-check text-cyan-600 opacity-0 group-hover:opacity-100"></i>
                          <i v-else class="fas fa-check text-green-600"></i>
                        </button>
                      </div>
                    </div>
                    
                    <div v-if="recordForm.projectDomain" class="mt-2">
                      <span class="text-sm text-gray-500">当前领域: </span>
                      <span class="bg-cyan-100 text-cyan-700 px-3 py-1 rounded-full text-sm inline-flex items-center space-x-1">
                        <span>{{ recordForm.projectDomain }}</span>
                        <button 
                          type="button"
                          @click="recordForm.projectDomain = ''"
                          class="text-cyan-500 hover:text-cyan-700"
                        >
                          <i class="fas fa-times"></i>
                        </button>
                      </span>
                    </div>
                  </div>

                  <!-- 技术栈 -->
                  <div>
                    <div class="flex items-center justify-between mb-2">
                      <label class="block text-sm font-medium text-gray-700">技术栈</label>
                      <button 
                        type="button"
                        @click="openConfigManager('practical', 'techStack')"
                        class="text-sm text-cyan-600 hover:text-cyan-800 flex items-center space-x-1"
                      >
                        <i class="fas fa-cog"></i>
                        <span>管理</span>
                      </button>
                    </div>
                    
                    <!-- 搜索/添加技术栈 -->
                    <TagSearchInput
                      v-model:selected="recordForm.techTags"
                      :all-options="practicalTechStacks"
                      placeholder="搜索或新建技术栈..."
                      color="teal"
                      @add="handlePracticalTechStackAdd"
                    />
                    <TagDisplay
                      v-if="recordForm.techTags.length > 0"
                      :tags="recordForm.techTags"
                      color="teal"
                      @remove="removeTechTag"
                    />
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">项目模板</label>
                    <div class="space-y-2">
                      <select v-model="selectedProjectType" @change="handleProjectTypeSelect" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 form-input">
                        <option value="">请选择项目模板</option>
                        <option v-for="option in allProjectTypeOptions" :key="option" :value="option">{{ option }}</option>
                      </select>
                    <div v-if="recordForm.projectType.length > 0" class="mt-2">
                      <span class="text-sm text-gray-500">已选择模板: </span>
                      <div class="flex flex-wrap gap-2 mt-1">
                        <span 
                          v-for="type in recordForm.projectType" 
                          :key="type"
                          class="bg-green-100 text-green-700 px-3 py-1 rounded-full text-sm flex items-center space-x-1"
                        >
                          <span>{{ type }}</span>
                          <button 
                            type="button"
                            @click="removeProjectType(type)"
                            class="text-green-500 hover:text-green-700"
                          >
                            <i class="fas fa-times"></i>
                          </button>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                  
                  <!-- 实战笔记内容编辑 -->
                  <div class="pt-4 border-t border-cyan-200 mt-4">
                    <div class="flex items-center mb-4">
                      <div class="w-6 h-6 bg-gradient-to-r from-cyan-500 to-teal-500 rounded-lg flex items-center justify-center mr-2">
                        <i class="fas fa-file-alt text-white text-xs"></i>
                      </div>
                      <h5 class="text-md font-semibold text-cyan-800">实战内容编辑</h5>
                    </div>

                    <!-- 标题 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-cyan-800 mb-2">
                        <i class="fas fa-heading text-cyan-600 mr-1"></i>
                        标题
                      </label>
                      <input 
                        v-model="recordForm.title" 
                        type="text" 
                        placeholder="请输入标题"
                        class="w-full px-4 py-3 border border-cyan-200 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-cyan-500 bg-white shadow-sm form-input"
                      >
                    </div>

                    <!-- 摘要 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-cyan-800 mb-2">
                        <i class="fas fa-align-left text-cyan-600 mr-1"></i>
                        摘要
                      </label>
                      <textarea 
                        v-model="recordForm.summary" 
                        rows="3" 
                        placeholder="请输入摘要..."
                        class="w-full px-4 py-3 border border-cyan-200 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-cyan-500 bg-white shadow-sm form-input"
                      ></textarea>
                    </div>

                    <!-- 需求描述 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-cyan-800 mb-2">
                        <i class="fas fa-clipboard-list text-cyan-600 mr-1"></i>
                        需求描述
                      </label>
                      <RichTextEditor 
                        :editor="requirementDescriptionEditor"
                        border-color="cyan"
                        @insert-table="insertPracticalTable(requirementDescriptionEditor)"
                        @insert-image="insertPracticalImage(requirementDescriptionEditor)"
                        @upload-image="uploadPracticalImage(requirementDescriptionEditor)"
                        @set-link="setPracticalLink(requirementDescriptionEditor)"
                        @import-markdown="importMarkdown(requirementDescriptionEditor)"
                      />
                    </div>

                    <!-- 设计思路 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-cyan-800 mb-2">
                        <i class="fas fa-lightbulb text-cyan-600 mr-1"></i>
                        设计思路
                      </label>
                      <RichTextEditor 
                        :editor="designThinkingEditor"
                        border-color="cyan"
                        @insert-table="insertPracticalTable(designThinkingEditor)"
                        @insert-image="insertPracticalImage(designThinkingEditor)"
                        @upload-image="uploadPracticalImage(designThinkingEditor)"
                        @set-link="setPracticalLink(designThinkingEditor)"
                        @import-markdown="importMarkdown(designThinkingEditor)"
                      />
                    </div>

                    <!-- 参考设计 -->
                    <div class="mb-4">
                      <label class="block text-sm font-medium text-cyan-800 mb-2">
                        <i class="fas fa-book-open text-cyan-600 mr-1"></i>
                        参考设计
                      </label>
                      <RichTextEditor 
                        :editor="referenceDesignEditor"
                        border-color="cyan"
                        @insert-table="insertPracticalTable(referenceDesignEditor)"
                        @insert-image="insertPracticalImage(referenceDesignEditor)"
                        @upload-image="uploadPracticalImage(referenceDesignEditor)"
                        @set-link="setPracticalLink(referenceDesignEditor)"
                        @import-markdown="importMarkdown(referenceDesignEditor)"
                      />
                    </div>
                  </div>
                </div>
              </div>
                    
              <!-- 所有笔记类型的内容编辑器已整合到各自的颜色容器内 -->
              
              <!-- 固定底部按钮栏 -->
              <div class="flex justify-end items-center gap-4 pt-6 mt-6 border-t border-gray-200">
                <button type="button" @click="closeModal" class="px-6 py-2 rounded-lg bg-gray-200 text-gray-700 hover:bg-gray-300 font-medium">取消</button>
                <button 
                  type="submit" 
                  :disabled="!recordForm.type"
                  :class="[
                    'px-6 py-2 rounded-lg font-medium transition-all duration-200',
                    recordForm.type 
                      ? 'bg-blue-600 text-white hover:bg-blue-700 hover:shadow-md transform hover:scale-105' 
                      : 'bg-gray-300 text-gray-500 cursor-not-allowed'
                  ]"
                >
                  {{ isEditing ? '保存修改' : '保存笔记' }}
                </button>
              </div>

            </form>
          </div>
        </div>
      </div>
    </Transition>
  </section>

  <!-- 题目来源管理弹窗 -->
  <div v-if="showExerciseSourceManager" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-xl p-6 w-full max-w-md mx-4 max-h-[80vh] flex flex-col">
      <div class="flex items-center justify-between mb-4">
        <h3 class="text-lg font-semibold text-gray-900">管理题目来源</h3>
        <button @click="showExerciseSourceManager = false" class="text-gray-500 hover:text-gray-700">
          <i class="fas fa-times"></i>
        </button>
      </div>
      
      <div class="flex-1 overflow-hidden flex flex-col">
        <div class="text-sm text-gray-600 mb-3">所有题目来源：</div>
        
        <div v-if="allExerciseSources.length === 0" class="text-center text-gray-500 py-4">
          暂无题目来源
        </div>
        
        <div v-else class="flex-1 overflow-y-auto space-y-2 pr-2">
          <div v-for="source in allExerciseSources" :key="source" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
            <span class="text-gray-900">{{ source }}</span>
            <button 
              @click="deleteExerciseSource(source)"
              class="text-red-600 hover:text-red-800 text-sm"
            >
              <i class="fas fa-trash"></i>
            </button>
          </div>
        </div>
        
        <div class="border-t pt-3 mt-4 flex-shrink-0">
                  <div class="flex items-center space-x-2">
                    <input 
              v-model="newExerciseSource"
                      type="text" 
              placeholder="添加新的题目来源"
              @keyup.enter="addCustomExerciseSource"
              class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-red-500 focus:border-red-500"
                    >
                    <button 
              @click="addCustomExerciseSource"
              :disabled="!newExerciseSource.trim()"
              class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
            >
              添加
                    </button>
          </div>
        </div>
      </div>
    </div>
                  </div>
                  
  <!-- 学科类型管理弹窗 -->
  <div v-if="showExerciseSubjectManager" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-xl p-6 w-full max-w-md mx-4 max-h-[80vh] flex flex-col">
      <div class="flex items-center justify-between mb-4">
        <h3 class="text-lg font-semibold text-gray-900">管理学科类型</h3>
        <button @click="showExerciseSubjectManager = false" class="text-gray-500 hover:text-gray-700">
          <i class="fas fa-times"></i>
        </button>
                    </div>
      
      <div class="flex-1 overflow-hidden flex flex-col">
        <div class="text-sm text-gray-600 mb-3">所有学科类型：</div>
        
        <div v-if="allExerciseSubjects.length === 0" class="text-center text-gray-500 py-4">
          暂无学科类型
        </div>
        
        <div v-else class="flex-1 overflow-y-auto space-y-2 pr-2">
          <div v-for="subject in allExerciseSubjects" :key="subject" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
            <span class="text-gray-900">{{ subject }}</span>
            <button 
              @click="deleteExerciseSubject(subject)"
              class="text-red-600 hover:text-red-800 text-sm"
            >
              <i class="fas fa-trash"></i>
            </button>
          </div>
        </div>
        
        <div class="border-t pt-3 mt-4 flex-shrink-0">
          <div class="flex items-center space-x-2">
            <input 
              v-model="newExerciseSubject"
              type="text" 
              placeholder="添加新的学科类型"
              @keyup.enter="addCustomExerciseSubject"
              class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
                      <button 
              @click="addCustomExerciseSubject"
              :disabled="!newExerciseSubject.trim()"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
            >
              添加
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 知识点管理弹窗 -->
  <div v-if="showExerciseKnowledgeManager" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-xl p-6 w-full max-w-md mx-4 max-h-[80vh] flex flex-col">
      <div class="flex items-center justify-between mb-4">
        <h3 class="text-lg font-semibold text-gray-900">管理知识点</h3>
        <button @click="showExerciseKnowledgeManager = false" class="text-gray-500 hover:text-gray-700">
                        <i class="fas fa-times"></i>
                      </button>
                      </div>
      
      <div class="flex-1 overflow-hidden flex flex-col">
        <div class="text-sm text-gray-600 mb-3">所有知识点：</div>
        
        <div v-if="allExerciseKnowledge.length === 0" class="text-center text-gray-500 py-4">
          暂无知识点
        </div>
        
        <div v-else class="flex-1 overflow-y-auto space-y-2 pr-2">
          <div v-for="knowledge in allExerciseKnowledge" :key="knowledge" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
            <span class="text-gray-900">{{ knowledge }}</span>
            <button 
              @click="deleteExerciseKnowledge(knowledge)"
              class="text-red-600 hover:text-red-800 text-sm"
            >
              <i class="fas fa-trash"></i>
            </button>
                    </div>
                  </div>
                  
        <div class="border-t pt-3 mt-4 flex-shrink-0">
          <div class="flex items-center space-x-2">
            <input 
              v-model="newExerciseKnowledge"
              type="text" 
              placeholder="添加新的知识点"
              @keyup.enter="addCustomExerciseKnowledge"
              class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500"
            >
            <button 
              @click="addCustomExerciseKnowledge"
              :disabled="!newExerciseKnowledge.trim()"
              class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
            >
              添加
            </button>
                    </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 框架笔记学科类型管理弹窗 -->
  <div v-if="showFrameworkSubjectManager" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-lg p-6 w-full max-w-md max-h-[80vh] flex flex-col">
      <h3 class="text-lg font-semibold mb-4">管理学科类型</h3>
      <div class="flex-1 overflow-hidden flex flex-col">
        <div class="flex-1 overflow-y-auto space-y-2 pr-2">
          <div v-for="subject in availableFrameworkSubjects" :key="subject" class="flex items-center justify-between p-2 border rounded">
            <span>{{ subject }}</span>
                    <button 
              @click="deleteFrameworkSubject(subject)"
              class="text-red-600 hover:text-red-800 px-2 py-1 rounded"
            >
              删除
                    </button>
                    </div>
                  </div>
        <div class="flex-shrink-0 mt-4 pt-4 border-t">
          <div class="flex space-x-2">
            <input 
              v-model="newFrameworkSubject"
              type="text" 
              placeholder="添加新学科类型"
              @keyup.enter="addFrameworkSubject"
              class="flex-1 px-3 py-2 border border-gray-300 rounded-lg"
            >
            <button 
              @click="addFrameworkSubject"
              :disabled="!newFrameworkSubject.trim()"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
            >
              添加
            </button>
                </div>
              </div>
      </div>
      <div class="flex justify-end space-x-2 mt-4">
        <button 
          @click="showFrameworkSubjectManager = false"
          class="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600"
        >
          关闭
        </button>
      </div>
    </div>
          </div>
          
  <!-- 框架笔记知识点管理弹窗 -->
  <div v-if="showFrameworkKnowledgeManager" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-lg p-6 w-full max-w-md max-h-[80vh] flex flex-col">
      <h3 class="text-lg font-semibold mb-4">管理知识点类型</h3>
      <div class="flex-1 overflow-hidden flex flex-col">
        <div class="flex-1 overflow-y-auto space-y-2 pr-2">
          <div v-for="knowledge in availableFrameworkKnowledge" :key="knowledge" class="flex items-center justify-between p-2 border rounded">
            <span>{{ knowledge }}</span>
            <button 
              @click="deleteFrameworkKnowledge(knowledge)"
              class="text-red-600 hover:text-red-800 px-2 py-1 rounded"
            >
              删除
            </button>
          </div>
        </div>
        <div class="flex-shrink-0 mt-4 pt-4 border-t">
          <div class="flex space-x-2">
            <input 
              v-model="newFrameworkKnowledge"
              type="text" 
              placeholder="添加新知识点"
              @keyup.enter="addFrameworkKnowledge"
              class="flex-1 px-3 py-2 border border-gray-300 rounded-lg"
            >
            <button 
              @click="addFrameworkKnowledge"
              :disabled="!newFrameworkKnowledge.trim()"
              class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
            >
              添加
            </button>
      </div>
        </div>
      </div>
      <div class="flex justify-end space-x-2 mt-4">
        <button 
          @click="showFrameworkKnowledgeManager = false"
          class="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600"
        >
          关闭
        </button>
      </div>
    </div>

    <!-- 添加配置弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showAddConfigDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-xl shadow-2xl p-6 max-w-md w-full mx-4">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-xl font-bold text-gray-900">添加配置项</h3>
            <button 
              @click="showAddConfigDialog = false"
              class="text-gray-400 hover:text-gray-600 transition-colors"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="mb-6">
            <label class="block text-sm font-medium text-gray-700 mb-2">配置名称</label>
            <input 
              v-model="configDialogForm.configName"
              type="text" 
              placeholder="输入配置名称"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              @keyup.enter="addConfigItem"
            />
          </div>

          <div class="flex items-center justify-end space-x-3">
            <button 
              @click="showAddConfigDialog = false"
              class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
            >
              取消
            </button>
            <button 
              @click="addConfigItem"
              class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
            >
              添加
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </div>

  <!-- 通用配置管理弹窗 -->
  <div v-if="showConfigManagerDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50" @click.self="closeConfigManager">
    <div class="bg-white rounded-lg p-6 w-full max-w-md max-h-[80vh] flex flex-col shadow-2xl">
      <!-- 标题 -->
      <div class="flex items-center justify-between mb-4">
        <h3 class="text-lg font-semibold text-gray-900">{{ configManagerDialog.title }}</h3>
        <button 
          @click="closeConfigManager"
          class="text-gray-400 hover:text-gray-600 transition-colors"
        >
          <i class="fas fa-times"></i>
        </button>
      </div>

      <!-- 所有配置项来源列表 -->
      <div class="flex-1 overflow-hidden flex flex-col">
        <p class="text-sm text-gray-600 mb-3">所有{{ configManagerDialog.title.replace('管理', '') }}：</p>
        <div class="flex-1 overflow-y-auto space-y-2 pr-2">
          <div 
            v-for="item in configManagerDialog.items" 
            :key="item.id" 
            class="flex items-center justify-between p-3 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
          >
            <span class="text-gray-800">{{ item.configName }}</span>
            <button 
              @click="deleteConfigManagerItem(item.id)"
              class="text-red-600 hover:text-red-800 px-3 py-1 rounded transition-colors flex items-center space-x-1"
            >
              <i class="fas fa-trash-alt"></i>
            </button>
          </div>
          <div v-if="configManagerDialog.items.length === 0" class="text-center py-8 text-gray-400">
            <i class="fas fa-inbox text-3xl mb-2"></i>
            <p class="text-sm">暂无配置项</p>
          </div>
        </div>

        <!-- 添加新项 -->
        <div class="flex-shrink-0 mt-4 pt-4 border-t border-gray-200">
          <div class="flex space-x-2">
            <input 
              v-model="configManagerDialog.newItemName"
              type="text" 
              :placeholder="'添加新的' + configManagerDialog.title.replace('管理', '')"
              @keyup.enter="addConfigManagerItem"
              class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
            <button 
              @click="addConfigManagerItem"
              :disabled="!configManagerDialog.newItemName.trim()"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors"
            >
              添加
            </button>
          </div>
        </div>
      </div>

      <!-- 关闭按钮 -->
      <div class="flex justify-end mt-4 pt-4 border-t border-gray-200">
        <button 
          @click="closeConfigManager"
          class="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors"
        >
          关闭
        </button>
      </div>
    </div>
  </div>

  <!-- 选择归档弹窗（批量添加笔记） -->
  <div v-if="showArchiveListForBatch" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="showArchiveListForBatch = false">
    <div class="bg-white rounded-2xl shadow-2xl w-full max-w-3xl max-h-[90vh] overflow-hidden flex flex-col animate-fade-in">
      <div class="bg-gradient-to-r from-purple-500 to-pink-600 text-white p-6">
        <h3 class="text-2xl font-bold">选择归档</h3>
        <p class="text-purple-100 text-sm mt-1">将 {{ selectedNoteIdsForBatch.length }} 条笔记添加到归档中</p>
      </div>

      <!-- 搜索归档 -->
      <div class="p-6 border-b border-gray-200">
        <div class="flex items-center space-x-3">
          <div class="relative flex-1">
            <input 
              v-model="batchArchiveSearch"
              type="text" 
              placeholder="搜索归档名称..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-input pr-10"
            >
            <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
          </div>
          <select 
            v-model="batchArchiveTypeFilter"
            class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-select"
          >
            <option value="">全部类型</option>
            <option value="book">书籍</option>
            <option value="course">课程</option>
            <option value="material">材料</option>
            <option value="video">视频</option>
            <option value="other">其他</option>
          </select>
        </div>
      </div>

      <!-- 归档列表 -->
      <div class="flex-1 overflow-y-auto p-6">
        <div class="space-y-3">
          <div 
            v-for="archive in filteredArchivesForBatch" 
            :key="archive.id"
            class="border border-gray-200 rounded-lg p-4 hover:bg-purple-50 cursor-pointer transition-all"
            @click="addSelectedNotesToArchive(archive.id)"
          >
            <div class="flex items-center justify-between">
              <div class="flex-1">
                <div class="flex items-center space-x-2 mb-1">
                  <span class="px-2 py-1 text-xs rounded bg-purple-100 text-purple-600">
                    {{ getArchiveTypeLabel(archive.archiveType) }}
                  </span>
                  <h4 class="font-semibold text-gray-900">{{ archive.archiveName }}</h4>
                </div>
                <p v-if="archive.description" class="text-sm text-gray-600 mt-1">{{ archive.description }}</p>
                <div class="flex items-center space-x-3 text-xs text-gray-500 mt-2">
                  <span><i class="fas fa-file-alt mr-1"></i>{{ archive.noteCount || 0 }} 条笔记</span>
                  <span v-if="archive.sourceInfo"><i class="fas fa-info-circle mr-1"></i>{{ archive.sourceInfo }}</span>
                </div>
              </div>
              <i class="fas fa-plus-circle text-purple-500 text-2xl"></i>
            </div>
          </div>

          <div v-if="filteredArchivesForBatch.length === 0" class="text-center py-12 text-gray-400">
            <i class="fas fa-inbox text-5xl mb-3"></i>
            <p>没有找到符合条件的归档</p>
            <p class="text-sm mt-2">试试创建一个新归档</p>
          </div>
        </div>
      </div>

      <!-- 底部按钮 -->
      <div class="p-6 border-t border-gray-200 flex justify-between">
        <button 
          @click="openCreateArchiveForBatch"
          class="px-6 py-2 bg-gradient-to-r from-purple-500 to-pink-600 text-white rounded-lg hover:shadow-lg transition-all flex items-center space-x-2"
        >
          <i class="fas fa-plus"></i>
          <span>新建归档</span>
        </button>
        <button 
          @click="showArchiveListForBatch = false"
          class="px-6 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors"
        >
          取消
        </button>
      </div>
    </div>
  </div>

  <!-- 笔记选择弹窗（添加笔记到归档） -->
  <div v-if="showNoteSelectDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="showNoteSelectDialog = false">
    <div class="bg-white rounded-2xl shadow-2xl w-full max-w-6xl max-h-[90vh] overflow-hidden flex flex-col animate-fade-in">
      <div class="bg-gradient-to-r from-indigo-500 to-purple-600 text-white p-6">
        <div class="flex items-center justify-between">
          <div>
            <h3 class="text-2xl font-bold">选择笔记</h3>
            <p class="text-indigo-100 text-sm mt-1">选择要添加到归档的笔记</p>
          </div>
          <button @click="showNoteSelectDialog = false" class="text-white hover:text-gray-200">
            <i class="fas fa-times text-2xl"></i>
          </button>
        </div>
      </div>

      <!-- 搜索和筛选 -->
      <div class="p-6 border-b border-gray-200 bg-gray-50">
        <div class="flex gap-4">
          <div class="flex-1 relative">
            <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            <input 
              v-model="noteViewSearch"
              type="text" 
              placeholder="搜索笔记标题..."
              class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            >
          </div>
          <select 
            v-model="noteViewTypeFilter"
            class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
          >
            <option value="">全部类型</option>
            <option value="fragment">碎片笔记</option>
            <option value="framework">框架笔记</option>
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
            v-for="note in filteredNotesInView" 
            :key="note.id"
            class="p-4 border-2 rounded-lg transition-all duration-200 cursor-pointer"
            :class="selectedNoteIdsForBatch.includes(note.id) ? 'border-indigo-500 bg-indigo-50' : 'border-gray-200 hover:border-indigo-300'"
            @click="toggleNoteSelection(note.id)"
          >
            <div class="flex items-start justify-between mb-3">
              <span class="px-2 py-1 rounded text-xs font-medium" :class="getTypeClass(note.type)">
                {{ getTypeText(note.type) }}
              </span>
              <i class="text-xl" :class="selectedNoteIdsForBatch.includes(note.id) ? 'fas fa-check-circle text-indigo-600' : 'far fa-circle text-gray-400'"></i>
            </div>
            <h5 class="text-sm font-medium text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h5>
            <p class="text-xs text-gray-600 line-clamp-2">{{ note.summary }}</p>
          </div>
        </div>
        
        <div v-if="filteredNotesInView.length === 0" class="text-center py-16 text-gray-500">
          <i class="fas fa-search text-6xl mb-4 text-gray-300"></i>
          <p class="text-lg font-medium mb-2">未找到笔记</p>
          <p class="text-sm">请尝试调整搜索条件</p>
        </div>
      </div>

      <!-- 底部操作栏 -->
      <div class="border-t border-gray-200 p-6 bg-gray-50">
        <div class="flex items-center justify-between">
          <div class="text-sm text-gray-600">
            已选择 <span class="font-semibold text-indigo-600">{{ selectedNoteIdsForBatch.length }}</span> 条笔记
          </div>
          <div class="flex space-x-3">
            <button 
              @click="showNoteSelectDialog = false"
              class="px-6 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-100 transition-colors"
            >
              取消
            </button>
            <button 
              @click="confirmAddNotesToArchive"
              :disabled="selectedNoteIdsForBatch.length === 0"
              class="px-6 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors disabled:bg-gray-300 disabled:cursor-not-allowed"
            >
              添加到归档
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 新建归档并添加笔记弹窗 -->
  <div v-if="showCreateArchiveForBatchDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="closeCreateArchiveForBatch">
    <div class="bg-white rounded-2xl shadow-2xl w-full max-w-2xl max-h-[90vh] overflow-y-auto animate-fade-in">
      <div class="bg-gradient-to-r from-purple-500 to-pink-600 text-white p-6">
        <h3 class="text-2xl font-bold">新建归档并添加笔记</h3>
        <p class="text-purple-100 text-sm mt-1">将 {{ selectedNoteIdsForBatch.length }} 条笔记添加到新归档</p>
      </div>

      <div class="p-6 space-y-4">
        <!-- 归档名称 -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">
            归档名称<span class="text-red-500">*</span>
          </label>
          <input 
            v-model="batchArchiveForm.archiveName"
            type="text" 
            placeholder="例如：《深入理解计算机系统》、前端进阶课程"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-input"
          >
        </div>

        <!-- 归档类型 -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">归档类型</label>
          <select 
            v-model="batchArchiveForm.archiveType"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-select"
          >
            <option value="">请选择</option>
            <option value="book">书籍</option>
            <option value="course">课程</option>
            <option value="material">材料</option>
            <option value="video">视频</option>
            <option value="other">其他</option>
          </select>
        </div>

        <!-- 来源信息 -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">来源信息</label>
          <input 
            v-model="batchArchiveForm.sourceInfo"
            type="text" 
            placeholder="例如：作者、出版社、链接等"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-input"
          >
        </div>

        <!-- 描述 -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">描述</label>
          <textarea 
            v-model="batchArchiveForm.description"
            rows="3"
            placeholder="简要描述这个归档的内容和用途"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 form-textarea"
          ></textarea>
        </div>

        <!-- 操作按钮 -->
        <div class="flex justify-end space-x-3 pt-4">
          <button 
            @click="closeCreateArchiveForBatch"
            class="px-6 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors"
          >
            取消
          </button>
          <button 
            @click="createArchiveAndAddNotes"
            :disabled="!batchArchiveForm.archiveName.trim()"
            class="px-6 py-2 bg-gradient-to-r from-purple-500 to-pink-600 text-white rounded-lg hover:shadow-lg disabled:opacity-50 disabled:cursor-not-allowed transition-all"
          >
            创建并添加
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onActivated, onBeforeUnmount, computed, watch, nextTick } from 'vue'
import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Table from '@tiptap/extension-table'
import TableRow from '@tiptap/extension-table-row'
import TableCell from '@tiptap/extension-table-cell'
import TableHeader from '@tiptap/extension-table-header'
import TextAlign from '@tiptap/extension-text-align'
import Underline from '@tiptap/extension-underline'
import Strike from '@tiptap/extension-strike'
import Link from '@tiptap/extension-link'
import Image from '@tiptap/extension-image'
import { mergeAttributes } from '@tiptap/core'
import CodeBlock from '@tiptap/extension-code-block'
import Highlight from '@tiptap/extension-highlight'
import Placeholder from '@tiptap/extension-placeholder'
import Color from '@tiptap/extension-color'
import TextStyle from '@tiptap/extension-text-style'
import FontFamily from '@tiptap/extension-font-family'
import request from '@/api/request'
import { getConfigsByNoteType, createConfig, deleteConfig as deleteConfigAPI, type NoteConfig } from '@/api/noteConfig'
// 导入归档管理API
import { 
  getArchiveList, 
  getArchiveDetail, 
  createArchive, 
  updateArchive, 
  deleteArchive,
  addNoteToArchive,
  removeNoteFromArchive,
  updateRelationNote,
  type NoteArchive,
  type NoteArchiveDetail
} from '@/api/noteArchive'
// 导入通用组件
import RichTextEditor from '@/components/RichTextEditor.vue'
import TagSearchInput from '@/components/TagSearchInput.vue'
import TagDisplay from '@/components/TagDisplay.vue'
// 导入日期工具
import { formatDate } from '@/utils/dateUtils'

type Note = any

// 响应式数据
const allNotes = ref<Note[]>([])

// 归档管理相关状态
const showArchiveManager = ref(false)  // 显示归档管理主界面
const archiveViewMode = ref<'archive' | 'note'>('archive')  // 当前视角：归档 or 笔记
const archives = ref<NoteArchive[]>([])  // 归档列表
const archiveNotesMap = ref<Record<number, any[]>>({})  // 归档ID → 笔记列表的映射
const noteArchiveMap = ref<Record<number, any[]>>({})  // 笔记ID → 归档列表的映射
const expandedArchives = ref<number[]>([])  // 展开的归档ID列表

// 归档视角的搜索和筛选
const archiveViewSearch = ref('')  // 归档名称搜索
const archiveViewTypeFilter = ref('')  // 归档类型筛选

// 笔记视角的搜索和筛选
const noteViewSearch = ref('')  // 笔记搜索
const noteViewTypeFilter = ref('')  // 笔记类型筛选

// 批量添加到归档相关
const showArchiveListForBatch = ref(false)  // 显示选择归档弹窗
const showNoteSelectDialog = ref(false)  // 显示笔记选择弹窗（添加笔记到归档）
const targetArchiveId = ref<number | null>(null)  // 目标归档ID
const batchArchiveSearch = ref('')  // 归档搜索
const batchArchiveTypeFilter = ref('')  // 归档类型筛选
const selectedNoteIdsForBatch = ref<number[]>([])  // 待添加到归档的笔记ID列表

// 新建归档并批量添加
const showCreateArchiveForBatchDialog = ref(false)
const batchArchiveForm = reactive({
  archiveName: '',
  archiveType: '',
  description: '',
  sourceInfo: ''
})


// 配置管理相关
const showConfigManagement = ref(false)
const selectedConfigNoteType = ref('fragment')
const allConfigs = ref<NoteConfig[]>([])
const showAddConfigDialog = ref(false)
const configDialogForm = reactive({
  noteType: '',
  configType: '',
  configName: ''
})

// 笔记类型配置
const noteTypes = [
  { value: 'fragment', label: '碎片笔记', icon: 'fas fa-puzzle-piece' },
  { value: 'study', label: '求学笔记', icon: 'fas fa-graduation-cap' },
  { value: 'expansion', label: '拓展笔记', icon: 'fas fa-book-open' },
  { value: 'exercise', label: '刷题笔记', icon: 'fas fa-code' },
  { value: 'practical', label: '实战笔记', icon: 'fas fa-tools' },
  { value: 'framework', label: '框架笔记', icon: 'fas fa-sitemap' }
]

// 配置类型映射
const configTypeMap: Record<string, { type1: { key: string, label: string }, type2: { key: string, label: string } }> = {
  fragment: { type1: { key: 'category', label: '分类' }, type2: { key: 'theme', label: '主题' } },
  study: { type1: { key: 'subject', label: '学科' }, type2: { key: 'knowledgePoint', label: '知识点' } },
  expansion: { type1: { key: 'project', label: '学科项目' }, type2: { key: 'knowledgePoint', label: '知识点' } },
  exercise: { type1: { key: 'source', label: '题目来源' }, type2: { key: 'subject', label: '学科类型' } },
  practical: { type1: { key: 'domain', label: '领域' }, type2: { key: 'techStack', label: '技术栈' } },
  framework: { type1: { key: 'subjectType', label: '学科类型' }, type2: { key: 'knowledgePoint', label: '知识点类型' } }
}

// 加载笔记数据
const loadNotes = async () => {
  try {
    const res = await request.get('/note/list')
    if (res.code === 200) {
      allNotes.value = res.data || []
      todayRecords.value = res.data || []
      // 数据加载后立即规范化数组字段
      normalizeRecordArrays()
    }
  } catch (error) {
    console.error('加载笔记失败:', error)
  }
}

// 加载所有配置
const loadAllConfigs = async () => {
  try {
    const types = ['fragment', 'study', 'expansion', 'exercise', 'practical', 'framework']
    const promises = types.map(type => getConfigsByNoteType(type))
    const results = await Promise.all(promises)
    
    allConfigs.value = []
    results.forEach(res => {
      if (res.code === 200 && res.data) {
        allConfigs.value.push(...res.data)
      }
    })
    
    updatePresetsFromConfigs()
  } catch (error) {
    console.error('加载配置失败:', error)
  }
}

// 从配置更新预设选项（注：availableFragmentCategories是computed，会自动从allConfigs获取）
const updatePresetsFromConfigs = () => {
  // 这个函数现在不需要做任何事，因为computed属性会自动响应allConfigs的变化
  // 保留函数只是为了兼容性
}

// 获取配置类型
const getConfigTypes = (noteType: string) => {
  return configTypeMap[noteType] || { type1: null, type2: null }
}

// 获取配置项
const getConfigItems = (noteType: string, configType: string) => {
  return allConfigs.value.filter(c => c.noteType === noteType && c.configType === configType)
}

// 打开添加配置弹窗
const openAddConfigModal = (noteType: string, configType: string) => {
  configDialogForm.noteType = noteType
  configDialogForm.configType = configType
  configDialogForm.configName = ''
  showAddConfigDialog.value = true
}

// 添加配置
const addConfigItem = async () => {
  if (!configDialogForm.configName.trim()) {
    alert('请输入配置名称')
    return
  }
  
  try {
    const res = await createConfig({
      noteType: configDialogForm.noteType,
      configType: configDialogForm.configType,
      configName: configDialogForm.configName.trim()
    })
    
    if (res.code === 200) {
      await loadAllConfigs()
      showAddConfigDialog.value = false
      alert('添加成功')
    } else {
      alert(res.message || '添加失败')
    }
  } catch (error: any) {
    console.error('添加配置失败:', error)
    alert(error.message || '添加失败')
  }
}

// 删除配置
const deleteConfigItem = async (id: number) => {
  if (!confirm('确定要删除这个配置吗？')) {
    return
  }
  
  try {
    const res = await deleteConfigAPI(id)
    if (res.code === 200) {
      await loadAllConfigs()
      alert('删除成功')
    } else {
      alert(res.message || '删除失败')
    }
  } catch (error: any) {
    console.error('删除配置失败:', error)
    alert(error.message || '删除失败')
  }
}

// 从搜索框添加配置
const addConfigFromSearch = async (noteType: string, configType: string) => {
  // 确定使用哪个搜索框的值
  const searchValue = configType === getConfigTypes(noteType).type1.key 
    ? configType1Search.value 
    : configType2Search.value
  
  const configName = searchValue.trim()
  
  if (!configName) {
    alert('请输入配置名称')
    return
  }
  
  // 检查是否已存在
  const existingItems = getConfigItems(noteType, configType)
  if (existingItems.some(item => item.configName === configName)) {
    alert('该配置已存在')
    return
  }
  
  try {
    const res = await createConfig({
      noteType,
      configType,
      configName
    })
    
    if (res.code === 200) {
      await loadAllConfigs()
      // 清空搜索框
      if (configType === getConfigTypes(noteType).type1.key) {
        configType1Search.value = ''
      } else {
        configType2Search.value = ''
      }
      alert('添加成功')
    } else {
      alert(res.message || '添加失败')
    }
  } catch (error: any) {
    console.error('添加配置失败:', error)
    alert(error.message || '添加失败')
  }
}

// 通用的配置管理弹窗状态
const showConfigManagerDialog = ref(false)
const configManagerDialog = reactive({
  show: false,
  noteType: '',
  configType: '',
  title: '',
  items: [] as any[],
  newItemName: ''
})

// 打开配置管理弹窗
const openConfigManager = async (noteType: string, configType: string) => {
  console.log('打开配置管理弹窗:', noteType, configType)
  
  configManagerDialog.noteType = noteType
  configManagerDialog.configType = configType
  configManagerDialog.title = getConfigManagerTitle(noteType, configType)
  configManagerDialog.newItemName = ''
  
  console.log('弹窗标题:', configManagerDialog.title)
  
  // 从API加载配置项
  await loadConfigManagerItems(noteType, configType)
  
  console.log('加载的配置项:', configManagerDialog.items)
  
  showConfigManagerDialog.value = true
  configManagerDialog.show = true
  console.log('弹窗显示状态 ref:', showConfigManagerDialog.value)
  console.log('弹窗显示状态 reactive:', configManagerDialog.show)
  console.log('configManagerDialog对象:', configManagerDialog)
  
  // 强制触发视图更新
  nextTick(() => {
    const element = document.querySelector('.z-\\[9999\\]')
    console.log('nextTick - 弹窗元素:', element)
    if (element) {
      console.log('✅ 弹窗已渲染到DOM！')
    } else {
      console.error('❌ 弹窗未渲染到DOM')
    }
  })
}

// 获取弹窗标题
const getConfigManagerTitle = (noteType: string, configType: string): string => {
  const titles: Record<string, Record<string, string>> = {
    fragment: { category: '管理分类', theme: '管理主题' },
    study: { subject: '管理学科', knowledgePoint: '管理知识点' },
    memorization: { subject: '管理科目', knowledgePoint: '管理知识点' },
    exercise: { source: '管理题目来源', subject: '管理学科类型' },
    practical: { domain: '管理领域', techStack: '管理技术栈' },
    framework: { subjectType: '管理学科类型', knowledgePoint: '管理知识点类型' }
  }
  return titles[noteType]?.[configType] || '管理配置'
}

// 加载配置管理弹窗的项目
const loadConfigManagerItems = async (noteType: string, configType: string) => {
  console.log('开始加载配置项:', noteType, configType)
  try {
    const res = await getConfigsByNoteType(noteType)
    console.log('API返回结果:', res)
    if (res.code === 200 && res.data) {
      const filteredItems = res.data.filter((item: any) => item.configType === configType)
      console.log('过滤后的配置项:', filteredItems)
      configManagerDialog.items = filteredItems
    } else {
      console.warn('API返回非200状态或无数据:', res)
    }
  } catch (error) {
    console.error('加载配置项失败:', error)
  }
}

// 在配置管理弹窗中添加新项
const addConfigManagerItem = async () => {
  if (!configManagerDialog.newItemName.trim()) {
    alert('请输入配置名称')
    return
  }
  
  try {
    const res = await createConfig({
      noteType: configManagerDialog.noteType,
      configType: configManagerDialog.configType,
      configName: configManagerDialog.newItemName.trim(),
      sortOrder: 999,
      recCreator: 'admin'
    })
    
    if (res.code === 200) {
      // 刷新配置项列表
      await loadConfigManagerItems(configManagerDialog.noteType, configManagerDialog.configType)
      // 刷新所有配置（更新下拉选项）
      await loadAllConfigs()
      configManagerDialog.newItemName = ''
    } else {
      alert(res.message || '添加失败')
    }
  } catch (error: any) {
    console.error('添加配置失败:', error)
    alert(error.message || '添加失败')
  }
}

// 在配置管理弹窗中删除项
const deleteConfigManagerItem = async (id: number) => {
  if (!confirm('确定要删除这个配置吗？')) {
    return
  }
  
  try {
    const res = await deleteConfigAPI(id)
    if (res.code === 200) {
      // 刷新配置项列表
      await loadConfigManagerItems(configManagerDialog.noteType, configManagerDialog.configType)
      // 刷新所有配置（更新下拉选项）
      await loadAllConfigs()
    } else {
      alert(res.message || '删除失败')
    }
  } catch (error: any) {
    console.error('删除配置失败:', error)
    alert(error.message || '删除失败')
  }
}

// 关闭配置管理弹窗
const closeConfigManager = () => {
  showConfigManagerDialog.value = false
  configManagerDialog.show = false
}

// 默认配置（保留原有配置）
import {
  defaultProjectOptions,
  defaultFragmentCategories,
  defaultFragmentThemes,
  defaultStudySubjects,
  defaultFrameworkSubjects,
  defaultFrameworkKnowledge,
  defaultSubjectTypeOptions,
  defaultProjectTypeOptions,
  defaultExerciseSources,
  defaultExerciseSubjects,
  defaultExerciseKnowledge
} from '@/mock/defaultOptions'
import { getAllProjectTemplates } from '@/api/projectTemplate'
import type { ProjectTemplate as ApiProjectTemplate } from '@/api/projectTemplate'

// 表单数据
const recordForm = reactive({
  type: '',
  title: '',
  summary: '',
  content: '',
  // 框架笔记字段
  subjectType: [] as string[],
  knowledgePoint: [] as string[],
  subject: [] as string[],
  frameworkType: [] as string[],
  // 求学笔记字段
  course: [] as string[],
  studySubject: [] as string[],
  bookSubject: [] as string[],
  coreConcept: '',          // 核心概念（富文本）
  mechanism: '',            // 机制原理（富文本）
  applicationCase: '',      // 应用案例（富文本）
  extension: '',            // 延伸/对比（富文本）
  commonMistake: '',        // 常见误区（富文本）
  reflection: '',           // 思考理解（富文本）

  // 背诵笔记字段
  project: '',
  originalText: '',
  explanation: '',
  cue: '',
  // 拓展笔记字段
  understanding: '',        // 理解记录（富文本）
  // 刷题笔记字段
  exerciseSource: '',                  // 题目来源 - 单标签
  exerciseSubject: '',                 // 学科类型 - 单标签
  exerciseKnowledge: [] as string[],   // 知识点类型 - 多标签
  exerciseDifficulty: '',
  questionDescription: '',             // 题目描述（富文本）
  analysis: '',                        // 分析理解（富文本）
  referenceAnswer: '',                 // 参考答案（富文本）
  // 实战笔记字段
  projectDomain: '',                   // 项目领域
  techTags: [] as string[],
  projectType: [] as string[],
  requirementDescription: '',          // 需求描述（富文本）
  designThinking: '',                  // 设计思路（富文本）
  referenceDesign: '',                 // 参考设计（富文本）
  // 碎片笔记字段
  fragmentCategory: [] as string[],
  fragmentTheme: [] as string[],
  importance: ''
})





// 新增字段相关
const newFrameworkSubject = ref('')
const newFrameworkKnowledge = ref('')
const newSubject = ref('')
const newCourse = ref('')
const newFragmentCategory = ref('')
const newBookSubject = ref('')
const newStudySubject = ref('')
// 刷题笔记新增字段
const newExerciseSource = ref('')
const newExerciseSubject = ref('')
const newExerciseKnowledge = ref('')

// 背诵笔记搜索变量（科目为单选，保留）
const memorizationSubjectSearch = ref('')
const showMemorizationSubjectDropdown = ref(false)
const filteredMemorizationSubjects = ref<string[]>([])

// 实战笔记搜索变量（领域为单选，保留）
const practicalDomainSearch = ref('')
const showPracticalDomainDropdown = ref(false)
const filteredPracticalDomains = ref<string[]>([])

// 配置管理搜索变量
const configType1Search = ref('')
const configType2Search = ref('')

// 刷题笔记管理弹窗
const showExerciseSourceManager = ref(false)
const showExerciseSubjectManager = ref(false)
const showExerciseKnowledgeManager = ref(false)

// 框架笔记管理弹窗
const showFrameworkSubjectManager = ref(false)
const showFrameworkKnowledgeManager = ref(false)

// 框架笔记存储的学科类型和知识点（可以被删除）
const storedFrameworkSubjects = ref<string[]>([])
const storedFrameworkKnowledge = ref<string[]>([])

// 所有题目来源、学科类型和知识点存储（包括预设和自定义）
const allExerciseSources = ref<string[]>([])
const allExerciseSubjects = ref<string[]>([])
const allExerciseKnowledge = ref<string[]>([])
const newTechTag = ref('')
const newFragmentSubject = ref('')
const newSubjectType = ref('')
const newKnowledgePoint = ref('')
const newProjectType = ref('')
const newFragmentTheme = ref('')

// 项目模板数据
const projectTemplates = ref<{id: number; domain: string; name: string; description: string; requirements: any[]; commonFeatures: any[]}[]>([])

// 自定义输入变量
const customProject = ref('')
const selectedSubjectType = ref('')
const selectedProjectType = ref('')

// 监听笔记类型变化，实现内容字段映射
watch(() => recordForm.type, (newType, oldType) => {
  console.log('笔记类型变化:', { 
    oldType, 
    newType, 
    content: recordForm.content, 
    originalText: recordForm.originalText,
    contentLength: recordForm.content ? recordForm.content.length : 0,
    hasEditorContent: editor.value ? editor.value.getHTML() : 'editor not ready'
  })
  
  if (oldType && newType && oldType !== newType) {
    // 从其他类型切换到背诵笔记
    if (newType === 'memorization' && oldType !== 'memorization') {
      console.log('切换到背诵笔记，转移content到originalText')
      
      // 获取当前编辑器内容或form中的content
      const currentContent = editor.value ? editor.value.getHTML() : recordForm.content
      console.log('当前内容:', { currentContent, formContent: recordForm.content })
      
      // 将content内容转移到originalText
      if (currentContent && currentContent.trim() !== '' && currentContent !== '<p></p>') {
        if (!recordForm.originalText) {
          recordForm.originalText = currentContent
          recordForm.content = ''
          console.log('内容已转移到originalText:', recordForm.originalText)
        }
        
        // 更新背诵笔记编辑器内容
        setTimeout(() => {
          originalTextEditor.value?.commands.setContent(recordForm.originalText)
          // 清空主编辑器
          editor.value?.commands.setContent('')
        }, 100)
      } else {
        console.log('没有内容需要转移')
      }
    }
    // 从背诵笔记切换到其他类型
    else if (oldType === 'memorization' && newType !== 'memorization') {
      console.log('从背诵笔记切换，转移originalText到content')
      // 将originalText内容转移到content
      if (recordForm.originalText) {
        if (!recordForm.content) {
          recordForm.content = recordForm.originalText
          console.log('内容已转移:', recordForm.content)
        }
        recordForm.originalText = ''
        recordForm.explanation = ''
        recordForm.cue = ''
        
        // 更新主编辑器内容
        setTimeout(() => {
          editor.value?.commands.setContent(recordForm.content)
          // 清空背诵笔记编辑器
          originalTextEditor.value?.commands.setContent('')
          explanationEditor.value?.commands.setContent('')
          cueEditor.value?.commands.setContent('')
        }, 100)
      }
    }
  }
})

// 动态选项列表
const customProjectOptions = ref<string[]>([])
const customSubjectTypeOptions = ref<string[]>([])
const customProjectTypeOptions = ref<string[]>([])

// 预设选项（已从 defaultOptions.ts 导入）

// 碎片笔记可用分类（动态获取，与素材组页面保持完全一致）
const availableFragmentCategories = computed(() => {
  const categories = new Set<string>()
  // 优先从配置加载
  allConfigs.value
    .filter(c => c.noteType === 'fragment' && c.configType === 'category')
    .forEach(c => categories.add(c.configName))
  // 如果配置为空，使用默认值
  if (categories.size === 0) {
    defaultFragmentCategories.forEach(cat => categories.add(cat))
  }
  // 从现有笔记中也获取（保留动态特性）
  todayRecords.value.forEach(record => {
    if (record.type === 'fragment' && record.fragmentCategory && Array.isArray(record.fragmentCategory)) {
      record.fragmentCategory.forEach(cat => categories.add(cat))
    }
  })
  return Array.from(categories).sort()
})

// 碎片笔记可用主题（动态获取，与素材组页面保持完全一致）
const availableFragmentThemes = computed(() => {
  const themes = new Set<string>()
  // 优先从配置加载
  allConfigs.value
    .filter(c => c.noteType === 'fragment' && c.configType === 'theme')
    .forEach(c => themes.add(c.configName))
  // 如果配置为空，使用默认值
  if (themes.size === 0) {
    defaultFragmentThemes.forEach(theme => themes.add(theme))
  }
  // 从现有笔记中也获取（保留动态特性）
  todayRecords.value.forEach(record => {
    if (record.type === 'fragment' && record.fragmentTheme && Array.isArray(record.fragmentTheme)) {
      record.fragmentTheme.forEach(theme => themes.add(theme))
    }
  })
  return Array.from(themes).sort()
})

// 求学笔记可用学科（动态获取）
const availableStudySubjects = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'study' && c.configType === 'subject')
  return configs.map(c => c.configName).sort()
})

// 求学笔记可用知识点
const availableStudyKnowledge = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'study' && c.configType === 'knowledgePoint')
  return configs.map(c => c.configName).sort()
})

// 框架笔记可用学科类型
const availableFrameworkSubjects = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'framework' && c.configType === 'subjectType')
  return configs.map(c => c.configName).sort()
})

// 框架笔记可用知识点
const availableFrameworkKnowledge = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'framework' && c.configType === 'knowledgePoint')
  return configs.map(c => c.configName).sort()
})


// 刷题笔记可用题目来源
const availableExerciseSources = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'exercise' && c.configType === 'source')
  return configs.map(c => c.configName).sort()
})

// 刷题笔记可用学科类型
const availableExerciseSubjects = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'exercise' && c.configType === 'subject')
  return configs.map(c => c.configName).sort()
})

// 背诵笔记可用科目
const availableMemorizationSubjects = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'memorization' && c.configType === 'subject')
  return configs.map(c => c.configName).sort()
})

// 背诵笔记可用知识点
const availableMemorizationKnowledge = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'memorization' && c.configType === 'knowledgePoint')
  return configs.map(c => c.configName).sort()
})

// 拓展笔记可用学科项目
const availableExpansionProjects = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'expansion' && c.configType === 'project')
  return configs.map(c => c.configName).sort()
})

// 拓展笔记可用知识点
const expansionKnowledge = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'expansion' && c.configType === 'knowledgePoint')
  return configs.map(c => c.configName).sort()
})

// 实战笔记可用领域
const availablePracticalDomains = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'practical' && c.configType === 'domain')
  return configs.map(c => c.configName).sort()
})

// 实战笔记可用技术栈
const availablePracticalTechStacks = computed(() => {
  // 从API配置中获取
  const configs = allConfigs.value.filter(c => c.noteType === 'practical' && c.configType === 'techStack')
  return configs.map(c => c.configName).sort()
})

// ===== TagSearchInput组件使用的别名 =====
const fragmentCategories = availableFragmentCategories
const fragmentThemes = availableFragmentThemes
const frameworkSubjects = availableFrameworkSubjects
const frameworkKnowledge = availableFrameworkKnowledge
const studySubjects = availableStudySubjects
const studyKnowledge = availableStudyKnowledge
const memorizationKnowledge = availableMemorizationKnowledge
const practicalTechStacks = availablePracticalTechStacks

// ===== TagSearchInput的add事件处理函数 =====
const handleFragmentCategoryAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'fragment', configType: 'category', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加分类失败:', error)
  }
}

const handleFragmentThemeAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'fragment', configType: 'theme', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加主题失败:', error)
  }
}

const handleFrameworkSubjectAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'framework', configType: 'subjectType', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加学科类型失败:', error)
  }
}

const handleFrameworkKnowledgeAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'framework', configType: 'knowledgePoint', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加知识点失败:', error)
  }
}

const handleStudySubjectAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'study', configType: 'subject', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加学科失败:', error)
  }
}

const handleStudyKnowledgeAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'study', configType: 'knowledgePoint', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加知识点失败:', error)
  }
}

const handleMemorizationKnowledgeAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'memorization', configType: 'knowledgePoint', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加知识点失败:', error)
  }
}

const handleExpansionKnowledgeAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'expansion', configType: 'knowledgePoint', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加知识点失败:', error)
  }
}

const handlePracticalTechStackAdd = async (tagName: string) => {
  try {
    await createConfig({ noteType: 'practical', configType: 'techStack', configName: tagName })
    await loadAllConfigs()
  } catch (error) {
    console.error('添加技术栈失败:', error)
  }
}

// 学科类型和项目类型选项（已从 defaultOptions.ts 导入）

// 持久化存储的类型列表
const storedSubjects = ref<string[]>([])
const storedCourses = ref<string[]>([])
const storedStudySubjects = ref<string[]>([])
const storedBookSubjects = ref<string[]>([])
const storedTechTags = ref<string[]>([])
const storedFragmentCategories = ref<string[]>([])
const storedExerciseSubjects = ref<string[]>([])
const storedSubjectTypes = ref<string[]>([])
const storedKnowledgePoints = ref<string[]>([])
const storedProjectTypes = ref<string[]>([])
const storedFragmentThemes = ref<string[]>([])

// 界面控制
const showCreateForm = ref(false)
const showManageNotes = ref(false)
  const isEditing = ref(false)
  const editingRecordId = ref<number | null>(null)
  const showTableMenu = ref(false)
  
  // 图片映射：保存图片占位符和原始图片的对应关系
  const imageMap = ref<Map<string, string>>(new Map())
const tableRows = ref(3)
const tableCols = ref(3)

// 搜索和筛选
const searchQuery = ref('')
const filterType = ref('')

// 今日记录数据
const todayRecords = ref<Note[]>([])

// 兜底：所有类型字段都转为数组（从后端返回的逗号分隔字符串需要拆分）
function normalizeRecordArrays() {
  todayRecords.value.forEach((record: any) => {
    // 将逗号分隔的字符串或单个值转换为数组
    const toArray = (value: any) => {
      if (!value) return []
      if (Array.isArray(value)) return value
      if (typeof value === 'string') {
        // 如果是逗号分隔的字符串，拆分成数组
        return value.split(',').map(item => item.trim()).filter(item => item !== '')
      }
      return [value]
    }
    
    // 确保所有数组字段都是数组类型
    if (record.subject) record.subject = toArray(record.subject)
    if (record.course) record.course = toArray(record.course)
    if (record.studySubject) record.studySubject = toArray(record.studySubject)
    if (record.bookSubject) record.bookSubject = toArray(record.bookSubject)
    if (record.subjectType) record.subjectType = toArray(record.subjectType)
    if (record.knowledgePoint) record.knowledgePoint = toArray(record.knowledgePoint)
    if (record.techTags) record.techTags = toArray(record.techTags)
    if (record.fragmentCategory) record.fragmentCategory = toArray(record.fragmentCategory)
    if (record.fragmentTheme) record.fragmentTheme = toArray(record.fragmentTheme)
    if (record.projectType) record.projectType = toArray(record.projectType)
    if (record.exerciseKnowledge) record.exerciseKnowledge = toArray(record.exerciseKnowledge)
  })
}

onMounted(async () => {
  loadNotes()
  loadAllConfigs()  // 加载配置
  loadStoredTypes()
  
  // 加载项目模板
  try {
    const response = await getAllProjectTemplates()
    if (response.code === 200 && response.data) {
      projectTemplates.value = response.data.map((item: ApiProjectTemplate) => ({
        id: item.id || 0,
        domain: item.domain,
        name: item.name,
        description: item.description,
        requirements: JSON.parse(item.requirements || '[]'),
        commonFeatures: JSON.parse(item.commonFeatures || '[]')
      }))
    }
  } catch (error) {
    console.error('加载项目模板失败:', error)
  }
  
  // 初始化练习笔记选项
  const initExerciseOptions = () => {
    // 初始化题目来源
    const savedSources = localStorage.getItem('allExerciseSources')
    allExerciseSources.value = savedSources ? JSON.parse(savedSources) : [...defaultExerciseSources]
    
    // 初始化学科类型
    const savedSubjects = localStorage.getItem('allExerciseSubjects')
    allExerciseSubjects.value = savedSubjects ? JSON.parse(savedSubjects) : [...defaultExerciseSubjects]
    
    // 初始化知识点
    const savedKnowledge = localStorage.getItem('allExerciseKnowledge')
    allExerciseKnowledge.value = savedKnowledge ? JSON.parse(savedKnowledge) : [...defaultExerciseKnowledge]
  }
  
  initExerciseOptions()
  
  // 点击外部关闭表格菜单
  document.addEventListener('click', (event) => {
    const target = event.target as HTMLElement
    if (!target.closest('.relative.inline-block')) {
      showTableMenu.value = false
    }
  })
})

// 组件激活时刷新数据（从其他页面返回时）
onActivated(() => {
  loadNotes()
})

// 计算已有学科列表
const existingSubjects = computed(() => {
  const subjects = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.subject) && record.subject.forEach(subject => subjects.add(subject))
  })
  storedSubjects.value.forEach(subject => {
    subjects.add(subject)
  })
  return Array.from(subjects).sort()
})

// 计算已有课程列表
const existingCourses = computed(() => {
  const courses = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.course) && record.course.forEach(course => courses.add(course))
  })
  storedCourses.value.forEach(course => {
    courses.add(course)
  })
  return Array.from(courses).sort()
})

// 计算已有求学学科类型列表
const existingStudySubjects = computed(() => {
  const subjects = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.studySubject) && record.studySubject.forEach(subject => subjects.add(subject))
  })
  storedStudySubjects.value.forEach(subject => {
    subjects.add(subject)
  })
  return Array.from(subjects).sort()
})

// 计算已有书籍学科类型列表
const existingBookSubjects = computed(() => {
  const subjects = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.bookSubject) && record.bookSubject.forEach(subject => subjects.add(subject))
  })
  storedBookSubjects.value.forEach(subject => {
    subjects.add(subject)
  })
  return Array.from(subjects).sort()
})

// 计算已有碎片分类列表
const existingFragmentCategories = computed(() => {
  const categories = new Set<string>()
  todayRecords.value.forEach((record: any) => {
    if (record.fragmentCategory && Array.isArray(record.fragmentCategory)) {
      record.fragmentCategory.forEach((category: string) => categories.add(category))
    }
  })
  storedFragmentCategories.value.forEach(category => {
    categories.add(category)
  })
  return Array.from(categories).sort()
})

// 计算已有学科类型列表
const existingSubjectTypes = computed(() => {
  const types = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.subjectType) && record.subjectType.forEach(type => types.add(type))
  })
  storedSubjectTypes.value.forEach(type => {
    types.add(type)
  })
  return Array.from(types).sort()
})

// 计算已有知识点列表
const existingKnowledgePoints = computed(() => {
  const points = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.knowledgePoint) && record.knowledgePoint.forEach(point => points.add(point))
  })
  storedKnowledgePoints.value.forEach(point => {
    points.add(point)
  })
  return Array.from(points).sort()
})

// 计算已有项目类型列表
const existingProjectTypes = computed(() => {
  const types = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.projectType) && record.projectType.forEach(type => types.add(type))
  })
  storedProjectTypes.value.forEach(type => {
    types.add(type)
  })
  return Array.from(types).sort()
})

// 计算已有碎片主题列表
const existingFragmentThemes = computed(() => {
  const themes = new Set<string>()
  todayRecords.value.forEach(record => {
    Array.isArray(record.fragmentTheme) && record.fragmentTheme.forEach(theme => themes.add(theme))
  })
  storedFragmentThemes.value.forEach(theme => {
    themes.add(theme)
  })
  return Array.from(themes).sort()
})

// 获取类型特定标题
const getTypeSpecificTitle = () => {
  const titles = {
    fragment: '碎片笔记 - 分类标签管理',
    framework: '框架笔记 - 结构框架管理',
    study: '求学笔记 - 学习课程管理',
    expansion: '拓展笔记 - 学科项目管理',
    memorization: '背诵笔记 - 记忆要点管理',
    exercise: '刷题笔记 - 练习题目管理',
    practical: '实战笔记 - 实战项目管理'
  }
  return titles[recordForm.type as keyof typeof titles] || ''
}

// 添加学科（框架笔记用）
const addSubject = () => {
  const subject = newSubject.value.trim()
  if (subject) {
    recordForm.subjectType.push(subject)
    saveTypeToStorage('subjectType', subject)
    newSubject.value = ''
  }
}

// 选择学科（框架笔记用）
const selectSubject = (subject: string) => {
  if (!recordForm.subjectType.includes(subject)) {
    recordForm.subjectType.push(subject)
  }
}

// 添加课程
const addCourse = () => {
  const course = newCourse.value.trim()
  if (course) {
    recordForm.course.push(course)
    saveTypeToStorage('course', course)
    newCourse.value = ''
  }
}

// 选择课程
const selectCourse = (course: string) => {
  if (!recordForm.course.includes(course)) {
    recordForm.course.push(course)
  }
}

// 添加求学学科类型
const addStudySubject = () => {
  const subject = newStudySubject.value.trim()
  if (subject) {
    recordForm.studySubject.push(subject)
    saveTypeToStorage('studySubject', subject)
    newStudySubject.value = ''
  }
}

// 选择求学学科类型
const selectStudySubject = (subject: string) => {
  if (!recordForm.studySubject.includes(subject)) {
    recordForm.studySubject.push(subject)
  }
}

// 添加书籍学科类型
const addBookSubject = () => {
  const subject = newBookSubject.value.trim()
  if (subject) {
    recordForm.bookSubject.push(subject)
    saveTypeToStorage('bookSubject', subject)
    newBookSubject.value = ''
  }
}

// 选择书籍学科类型
const selectBookSubject = (subject: string) => {
  if (!recordForm.bookSubject.includes(subject)) {
    recordForm.bookSubject.push(subject)
  }
}

// 添加碎片分类
const addFragmentCategory = () => {
  const category = newFragmentCategory.value.trim()
  if (category) {
    recordForm.fragmentCategory.push(category)
    saveTypeToStorage('fragmentCategory', category)
    newFragmentCategory.value = ''
  }
}

// 选择碎片分类
const selectFragmentCategory = (category: string) => {
  if (!recordForm.fragmentCategory.includes(category)) {
    recordForm.fragmentCategory.push(category)
  }
}

// ========== 背诵笔记搜索处理（科目为单选，保留）==========
const filterMemorizationSubjects = () => {
  const search = memorizationSubjectSearch.value.toLowerCase().trim()
  if (search) {
    filteredMemorizationSubjects.value = availableMemorizationSubjects.value.filter(subject => 
      subject.toLowerCase().includes(search)
    )
  } else {
    filteredMemorizationSubjects.value = availableMemorizationSubjects.value
  }
}

const addMemorizationSubjectFromSearch = () => {
  const subject = memorizationSubjectSearch.value.trim()
  if (subject) {
    recordForm.project = subject
    if (!availableMemorizationSubjects.value.includes(subject)) {
      saveTypeToStorage('memorizationSubject', subject)
    }
    memorizationSubjectSearch.value = ''
    showMemorizationSubjectDropdown.value = false
  }
}

const selectMemorizationSubjectFromDropdown = (subject: string) => {
  recordForm.project = subject
  memorizationSubjectSearch.value = ''
  showMemorizationSubjectDropdown.value = false
}

// ========== 实战笔记搜索处理（领域为单选，保留）==========
const filterPracticalDomains = () => {
  const search = practicalDomainSearch.value.toLowerCase().trim()
  if (search) {
    filteredPracticalDomains.value = availablePracticalDomains.value.filter(domain => 
      domain.toLowerCase().includes(search)
    )
  } else {
    filteredPracticalDomains.value = availablePracticalDomains.value
  }
}

const addPracticalDomainFromSearch = () => {
  const domain = practicalDomainSearch.value.trim()
  if (domain) {
    recordForm.projectDomain = domain
    if (!availablePracticalDomains.value.includes(domain)) {
      saveTypeToStorage('practicalDomain', domain)
    }
    practicalDomainSearch.value = ''
    showPracticalDomainDropdown.value = false
  }
}

const selectPracticalDomainFromDropdown = (domain: string) => {
  recordForm.projectDomain = domain
  practicalDomainSearch.value = ''
  showPracticalDomainDropdown.value = false
}

// ========== 标签移除函数 ==========

// 添加技术栈标签
const addTechTag = () => {
  const tag = newTechTag.value.trim()
  if (tag && !recordForm.techTags.includes(tag)) {
    recordForm.techTags.push(tag)
    newTechTag.value = ''
  }
}

// 从已有标签添加技术栈
const addTechTagFromExisting = (tag: string) => {
  if (!recordForm.techTags.includes(tag)) {
    recordForm.techTags.push(tag)
  }
}

// 移除技术栈标签
const removeTechTag = (tag: string) => {
  recordForm.techTags = recordForm.techTags.filter(t => t !== tag)
}

// 切换技术栈标签
const toggleTechTag = (tag: string) => {
  if (recordForm.techTags.includes(tag)) {
    recordForm.techTags = recordForm.techTags.filter(t => t !== tag)
  } else {
    recordForm.techTags.push(tag)
  }
}



// 添加学科类型
const addSubjectType = () => {
  const type = newSubjectType.value.trim()
  if (type) {
    recordForm.subjectType.push(type)
    saveTypeToStorage('subjectType', type)
    newSubjectType.value = ''
  }
}

// 选择学科类型
const selectSubjectType = (type: string) => {
  if (!recordForm.subjectType.includes(type)) {
    recordForm.subjectType.push(type)
  }
}

// 添加知识点
const addKnowledgePoint = () => {
  const point = newKnowledgePoint.value.trim()
  if (point) {
    recordForm.knowledgePoint.push(point)
    saveTypeToStorage('knowledgePoint', point)
    newKnowledgePoint.value = ''
  }
}

// 选择知识点
const selectKnowledgePoint = (point: string) => {
  if (!recordForm.knowledgePoint.includes(point)) {
    recordForm.knowledgePoint.push(point)
  }
}

// 添加项目类型
const addProjectType = () => {
  const type = newProjectType.value.trim()
  if (type) {
    recordForm.projectType.push(type)
    saveTypeToStorage('projectType', type)
    newProjectType.value = ''
  }
}

// 选择项目类型
const selectProjectType = (type: string) => {
  if (!recordForm.projectType.includes(type)) {
    recordForm.projectType.push(type)
  }
}

// 添加主题
const addFragmentTheme = () => {
  const theme = newFragmentTheme.value.trim()
  if (theme) {
    recordForm.fragmentTheme.push(theme)
    saveTypeToStorage('fragmentTheme', theme)
    newFragmentTheme.value = ''
  }
}

// 选择主题
const selectFragmentTheme = (theme: string) => {
  if (!recordForm.fragmentTheme.includes(theme)) {
    recordForm.fragmentTheme.push(theme)
  }
}



// 筛选后的记录
const filteredRecords = computed(() => {
  let records = todayRecords.value
  
  // 按类型筛选
  if (filterType.value) {
    records = records.filter(record => record.type === filterType.value)
  }
  
  // 按搜索关键词筛选
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    records = records.filter(record => 
      record.title.toLowerCase().includes(query) ||
      record.summary.toLowerCase().includes(query) ||
      record.content.toLowerCase().includes(query) ||
      record.tags.some(tag => tag.toLowerCase().includes(query))
    )
  }
  
  return records
})

// 分页相关状态
const recordCurrentPage = ref(1)
const recordPageSize = ref(10)

// 计算总页数
const recordTotalPages = computed(() => {
  return Math.ceil(filteredRecords.value.length / recordPageSize.value)
})

// 分页后的记录
const paginatedRecords = computed(() => {
  const start = (recordCurrentPage.value - 1) * recordPageSize.value
  const end = start + recordPageSize.value
  return filteredRecords.value.slice(start, end)
})

// 分页控制函数
const prevRecordPage = () => {
  if (recordCurrentPage.value > 1) {
    recordCurrentPage.value--
  }
}

const nextRecordPage = () => {
  if (recordCurrentPage.value < recordTotalPages.value) {
    recordCurrentPage.value++
  }
}

// 监听筛选条件变化，重置分页
watch([searchQuery, filterType], () => {
  recordCurrentPage.value = 1
})

// 关闭弹窗
const closeModal = () => {
  showCreateForm.value = false
  isEditing.value = false
  editingRecordId.value = null
  resetForm()
}

// 编辑记录
const editRecord = (record: Note) => {
  // 填充表单数据
  recordForm.type = record.type
  recordForm.title = record.title
  recordForm.summary = record.summary
  
  // 填充类型特定字段
  const recordData = record as any
  if (record.type === 'framework') {
    recordForm.subjectType = recordData.subjectType ? [...recordData.subjectType] : []
    recordForm.knowledgePoint = recordData.knowledgePoint ? [...recordData.knowledgePoint] : []
    recordForm.content = record.content || ''
  } else if (record.type === 'study') {
    recordForm.studySubject = recordData.studySubject ? [...recordData.studySubject] : []
    recordForm.knowledgePoint = recordData.knowledgePoint ? [...recordData.knowledgePoint] : []
    recordForm.content = record.content || ''
    recordForm.coreConcept = recordData.coreConcept || ''
    recordForm.mechanism = recordData.mechanism || ''
    recordForm.applicationCase = recordData.applicationCase || ''
    recordForm.extension = recordData.extension || ''
    recordForm.commonMistake = recordData.commonMistake || ''
    recordForm.reflection = recordData.reflection || ''
  } else if (record.type === 'expansion') {
    recordForm.project = recordData.project || ''
    recordForm.knowledgePoint = recordData.knowledgePoint ? [...recordData.knowledgePoint] : []
    recordForm.originalText = recordData.originalText || ''
    recordForm.understanding = recordData.understanding || ''
    recordForm.content = record.content || ''
  } else if (record.type === 'memorization') {
    recordForm.project = recordData.project || ''
    recordForm.knowledgePoint = recordData.knowledgePoint ? [...recordData.knowledgePoint] : []
    recordForm.originalText = recordData.originalText || record.content || ''
    recordForm.explanation = recordData.explanation || ''
    recordForm.cue = recordData.cue || ''
    recordForm.content = record.content || ''
  } else if (record.type === 'exercise') {
    recordForm.exerciseSource = recordData.exerciseSource || ''
    recordForm.exerciseSubject = recordData.exerciseSubject || ''
    recordForm.exerciseKnowledge = recordData.exerciseKnowledge ? [...recordData.exerciseKnowledge] : []
    recordForm.exerciseDifficulty = recordData.exerciseDifficulty || ''
    recordForm.content = record.content || ''
    recordForm.questionDescription = recordData.questionDescription || ''
    recordForm.analysis = recordData.analysis || ''
    recordForm.referenceAnswer = recordData.referenceAnswer || ''
  } else if (record.type === 'practical') {
    recordForm.techTags = recordData.techTags ? [...recordData.techTags] : []
    recordForm.projectType = recordData.projectType ? [...recordData.projectType] : []
    recordForm.content = record.content || ''
    recordForm.requirementDescription = recordData.requirementDescription || ''
    recordForm.designThinking = recordData.designThinking || ''
    recordForm.referenceDesign = recordData.referenceDesign || ''
  } else if (record.type === 'fragment') {
    recordForm.fragmentCategory = recordData.fragmentCategory ? [...recordData.fragmentCategory] : []
    recordForm.fragmentTheme = recordData.fragmentTheme ? [...recordData.fragmentTheme] : []
    recordForm.importance = recordData.importance || ''
    recordForm.content = record.content || ''
  }
  
  // 先设置编辑状态
  isEditing.value = true
  editingRecordId.value = record.id
  showCreateForm.value = true
  
  // 等待表单显示和编辑器初始化后再设置内容
  nextTick(() => {
    setTimeout(() => {
      console.log('=== 开始加载编辑器内容 ===')
      console.log('笔记类型:', record.type)
      console.log('笔记标题:', record.title)
      
      if (record.type === 'expansion') {
        // 拓展笔记：使用2个专用编辑器
        console.log('加载拓展笔记内容...')
        if (originalTextEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.originalText || '')
          originalTextEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 原文编辑器已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (understandingEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.understanding || '')
          understandingEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 理解编辑器已加载，内容长度:', contentWithPlaceholders.length)
        }
        // 清空主编辑器
        if (editor.value) {
          editor.value.commands.setContent('')
        }
      } else if (record.type === 'memorization') {
        // 背诵笔记：使用3个专用编辑器
        console.log('加载背诵笔记内容...')
        if (originalTextEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.originalText || '')
          originalTextEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 原文编辑器已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (explanationEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.explanation || '')
          explanationEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 解释编辑器已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (cueEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.cue || '')
          cueEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 提示词编辑器已加载，内容长度:', contentWithPlaceholders.length)
        }
        // 清空主编辑器
        if (editor.value) {
          editor.value.commands.setContent('')
        }
      } else if (record.type === 'study') {
        // 求学笔记：使用6个专用编辑器
        console.log('加载求学笔记内容...')
        if (coreConceptEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.coreConcept || '')
          coreConceptEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 核心概念已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (mechanismEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.mechanism || '')
          mechanismEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 机制原理已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (applicationCaseEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.applicationCase || '')
          applicationCaseEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 应用案例已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (extensionEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.extension || '')
          extensionEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 延伸对比已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (commonMistakeEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.commonMistake || '')
          commonMistakeEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 常见误区已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (reflectionEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.reflection || '')
          reflectionEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 思考理解已加载，内容长度:', contentWithPlaceholders.length)
        }
      } else if (record.type === 'exercise') {
        // 刷题笔记：使用3个专用编辑器
        console.log('加载刷题笔记内容...')
        if (questionDescriptionEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.questionDescription || '')
          questionDescriptionEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 题目描述已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (analysisEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.analysis || '')
          analysisEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 分析理解已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (referenceAnswerEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.referenceAnswer || '')
          referenceAnswerEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 参考答案已加载，内容长度:', contentWithPlaceholders.length)
        }
      } else if (record.type === 'practical') {
        // 实战笔记：使用3个专用编辑器
        console.log('加载实战笔记内容...')
        if (requirementDescriptionEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.requirementDescription || '')
          requirementDescriptionEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 需求描述已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (designThinkingEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.designThinking || '')
          designThinkingEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 设计思路已加载，内容长度:', contentWithPlaceholders.length)
        }
        if (referenceDesignEditor.value) {
          const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.referenceDesign || '')
          referenceDesignEditor.value.commands.setContent(contentWithPlaceholders)
          console.log('✓ 参考设计已加载，内容长度:', contentWithPlaceholders.length)
        }
      } else {
        // 碎片笔记、框架笔记：使用主编辑器
        console.log('加载主编辑器内容，长度:', recordForm.content?.length || 0)
        console.log('内容是否包含图片:', recordForm.content?.includes('<img') || false)
        console.log('内容预览（前200字符）:', recordForm.content?.substring(0, 200))
        
        if (editor.value) {
          if (recordForm.content) {
            try {
              // 清空后再设置，确保内容完全替换
              editor.value.commands.clearContent()
              console.log('已清空编辑器')
              
              // 将图片替换为占位符
              const contentWithPlaceholders = replaceImagesWithPlaceholders(recordForm.content)
              console.log('替换后内容长度:', contentWithPlaceholders.length)
              console.log('图片占位符数量:', imageMap.value.size)
              
              // 等待一下再设置新内容
              setTimeout(() => {
                try {
                  // 尝试使用 parseOptions 来保留所有属性
                  const parseOptions = {
                    preserveWhitespace: 'full' as const,
                  }
                  
                  editor.value?.commands.setContent(contentWithPlaceholders, false, parseOptions)
                  console.log('✓ 主编辑器内容已加载成功')
                  
                  // 立即验证
                  setTimeout(() => {
                    const currentContent = editor.value?.getHTML()
                    const hasPlaceholder = currentContent?.includes('image-placeholder') || false
                    
                    console.log('验证：编辑器当前内容长度:', currentContent?.length || 0)
                    console.log('验证：是否包含占位符:', hasPlaceholder)
                    
                    if (hasPlaceholder) {
                      console.log('✅ 图片占位符已成功加载到编辑器中')
                    }
                  }, 150)
                  
                } catch (error) {
                  console.error('❌ 加载内容失败:', error)
                }
              }, 50)
            } catch (error) {
              console.error('❌ 设置编辑器内容失败:', error)
            }
          } else {
            console.warn('⚠️ 内容为空')
          }
        } else {
          console.error('❌ 主编辑器未初始化')
        }
      }
      console.log('=== 编辑器内容加载完成 ===')
    }, 300)
  })
}

// 删除记录
const deleteRecord = async (id: number) => {
  if (confirm('确定要删除这条记录吗？')) {
    try {
      const response = await request.delete(`/note/${id}`)
      
      if (response.code === 200) {
      alert('记录已删除！')
        // 重新加载数据
        await loadNotes()
      } else {
        alert('删除失败：' + (response.message || '未知错误'))
      }
    } catch (error) {
      console.error('删除笔记失败:', error)
      alert('删除失败，请检查网络连接')
    }
  }
}

// 将图片替换为占位符
const replaceImagesWithPlaceholders = (html: string): string => {
  imageMap.value.clear()
  let imageIndex = 0
  
  return html.replace(/<img[^>]*src="(data:image\/[^"]+)"[^>]*>/g, (match) => {
    const placeholderId = `IMAGE_PLACEHOLDER_${imageIndex++}`
    imageMap.value.set(placeholderId, match)
    
    // 返回一个带样式的占位符
    return `<span class="image-placeholder" style="display: inline-block; padding: 8px 16px; margin: 4px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border-radius: 8px; font-weight: bold; cursor: pointer; user-select: none;" data-placeholder-id="${placeholderId}">🖼️ 图片 ${imageIndex}</span>`
  })
}

// 将占位符还原为图片
const restorePlaceholdersToImages = (html: string): string => {
  let restored = html
  
  imageMap.value.forEach((originalImage, placeholderId) => {
    const placeholderRegex = new RegExp(`<span[^>]*data-placeholder-id="${placeholderId}"[^>]*>.*?</span>`, 'g')
    restored = restored.replace(placeholderRegex, originalImage)
  })
  
  return restored
}

// 自定义 Image 扩展，允许 Base64 图片
const CustomImage = Image.extend({
  addAttributes() {
    return {
      ...this.parent?.(),
      src: {
        default: null,
        parseHTML: element => element.getAttribute('src'),
        renderHTML: attributes => {
          if (!attributes.src) {
            return {}
          }
          return {
            src: attributes.src, // 不验证，直接使用
          }
        },
      },
    }
  },
})

// TipTap 编辑器
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
    TextAlign.configure({
      types: ['heading', 'paragraph'],
    }),
    Underline,
    Strike,
    Link.configure({
      openOnClick: false,
      HTMLAttributes: {
        class: 'text-blue-600 underline',
      },
    }),
    CustomImage.configure({
      HTMLAttributes: {
        class: 'max-w-full h-auto cursor-pointer',
      },
    }),
    CodeBlock.configure({
      HTMLAttributes: {
        class: 'bg-gray-100 p-4 rounded-lg font-mono text-sm',
      },
    }),
    Highlight.configure({
      HTMLAttributes: {
        class: 'bg-yellow-200',
      },
    }),
    Placeholder.configure({
      placeholder: '开始编写你的笔记内容...',
    }),
    Color,
    TextStyle,
    FontFamily,
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm sm:prose lg:prose-lg xl:prose-2xl mx-auto focus:outline-none min-h-[300px] p-4',
    },
    handlePaste: (view, event) => {
      const items = event.clipboardData?.items
      if (!items) return false
      
      // 检查是否包含图片
      for (let i = 0; i < items.length; i++) {
        if (items[i].type.indexOf('image') !== -1) {
          event.preventDefault()
          const file = items[i].getAsFile()
          if (file) {
            console.log('检测到粘贴图片:', file.name || '剪贴板图片')
            // 使用压缩函数处理图片
            compressImage(file).then(base64 => {
              editor.value?.chain().focus().setImage({ src: base64 }).run()
              console.log('粘贴图片已插入')
            }).catch(error => {
              console.error('粘贴图片处理失败:', error)
            })
          }
          return true
        }
      }
      return false
    },
  },
  onUpdate: ({ editor }) => {
    // 更新表单内容
    recordForm.content = editor.getHTML()
  },
})

// 插入表格
const insertTable = () => {
  editor.value?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

// 插入自定义行数的表格
const insertCustomTable = (rows: number, cols: number) => {
  editor.value?.chain().focus().insertTable({ rows, cols, withHeaderRow: true }).run()
}

// 设置链接
const setLink = () => {
  const url = window.prompt('请输入链接地址:')
  if (url) {
    editor.value?.chain().focus().setLink({ href: url }).run()
  }
}

// 背诵笔记专用编辑器的完整配置
const memorizationEditorExtensions = [
    StarterKit.configure({
    heading: { levels: [1, 2, 3, 4, 5, 6] },
      bulletList: { keepMarks: true, keepAttributes: false },
      orderedList: { keepMarks: true, keepAttributes: false },
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
    TextAlign.configure({ types: ['heading', 'paragraph'] }),
    Underline,
    Strike,
    Link.configure({
      openOnClick: false,
      HTMLAttributes: { class: 'text-blue-600 underline' },
    }),
  Image.configure({
    HTMLAttributes: {
      class: 'max-w-full h-auto cursor-pointer',
    },
  }),
    CodeBlock.configure({
      HTMLAttributes: { class: 'bg-gray-100 p-4 rounded-lg font-mono text-sm' },
    }),
    Highlight.configure({ HTMLAttributes: { class: 'bg-yellow-200' } }),
    Color,
    TextStyle,
    FontFamily,
]

// 背诵笔记专用编辑器
const originalTextEditor = useEditor({
  content: '',
  extensions: [
    ...memorizationEditorExtensions,
    Placeholder.configure({ placeholder: '输入需要记忆的原文内容...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[150px] p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.originalText = editor.getHTML()
  },
})

const explanationEditor = useEditor({
  content: '',
  extensions: [
    ...memorizationEditorExtensions,
    Placeholder.configure({ placeholder: '输入对原文的解释说明...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[120px] p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.explanation = editor.getHTML()
  },
})

const cueEditor = useEditor({
  content: '',
  extensions: [
    ...memorizationEditorExtensions,
    Placeholder.configure({ placeholder: '输入帮助记忆的关键词或提示...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[80px] p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.cue = editor.getHTML()
  },
})

// 拓展笔记专用编辑器
const understandingEditor = useEditor({
  content: '',
  extensions: [
    ...memorizationEditorExtensions,
    Placeholder.configure({ placeholder: '输入对原文的深度理解和分析...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[150px] p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.understanding = editor.getHTML()
  },
})

// 刷题笔记编辑器
const exerciseEditorExtensions = [
  StarterKit.configure({
    heading: { levels: [1, 2, 3] },
    bulletList: { keepMarks: true, keepAttributes: false },
    orderedList: { keepMarks: true, keepAttributes: false },
  }),
  Table.configure({
    resizable: true,
    HTMLAttributes: { class: 'border-collapse border border-gray-300 w-full' },
  }),
  TableRow,
  TableCell,
  TableHeader,
  Image,
  Underline,
  Strike,
  TextAlign.configure({ types: ['heading', 'paragraph'] }),
  CodeBlock,
  Highlight.configure({ multicolor: false }),
  Link.configure({
    openOnClick: false,
    HTMLAttributes: { class: 'text-blue-600 underline cursor-pointer' },
  }),
]

const questionDescriptionEditor = useEditor({
  content: '',
  extensions: [
    ...exerciseEditorExtensions,
    Placeholder.configure({ placeholder: '输入题目描述...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.questionDescription = editor.getHTML()
  },
})

const analysisEditor = useEditor({
  content: '',
  extensions: [
    ...exerciseEditorExtensions,
    Placeholder.configure({ placeholder: '输入对题目的分析理解...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.analysis = editor.getHTML()
  },
})

const referenceAnswerEditor = useEditor({
  content: '',
  extensions: [
    ...exerciseEditorExtensions,
    Placeholder.configure({ placeholder: '输入参考答案...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.referenceAnswer = editor.getHTML()
  },
})

// 求学笔记编辑器
const studyEditorExtensions = [
  StarterKit.configure({
    heading: { levels: [1, 2, 3] },
    bulletList: { keepMarks: true, keepAttributes: false },
    orderedList: { keepMarks: true, keepAttributes: false },
  }),
  Table.configure({
    resizable: true,
    HTMLAttributes: { class: 'border-collapse border border-gray-300 w-full' },
  }),
  TableRow,
  TableCell,
  TableHeader,
  Image,
  Underline,
  Strike,
  TextAlign.configure({ types: ['heading', 'paragraph'] }),
  CodeBlock,
  Highlight.configure({ multicolor: false }),
  Link.configure({
    openOnClick: false,
    HTMLAttributes: { class: 'text-blue-600 underline cursor-pointer' },
  }),
]

const coreConceptEditor = useEditor({
  content: '',
  extensions: [
    ...studyEditorExtensions,
    Placeholder.configure({ placeholder: '输入核心概念...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.coreConcept = editor.getHTML()
  },
})

const mechanismEditor = useEditor({
  content: '',
  extensions: [
    ...studyEditorExtensions,
    Placeholder.configure({ placeholder: '输入机制原理...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.mechanism = editor.getHTML()
  },
})

const applicationCaseEditor = useEditor({
  content: '',
  extensions: [
    ...studyEditorExtensions,
    Placeholder.configure({ placeholder: '输入应用案例...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.applicationCase = editor.getHTML()
  },
})

const extensionEditor = useEditor({
  content: '',
  extensions: [
    ...studyEditorExtensions,
    Placeholder.configure({ placeholder: '输入延伸/对比内容...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.extension = editor.getHTML()
  },
})

const commonMistakeEditor = useEditor({
  content: '',
  extensions: [
    ...studyEditorExtensions,
    Placeholder.configure({ placeholder: '输入常见误区...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.commonMistake = editor.getHTML()
  },
})

const reflectionEditor = useEditor({
  content: '',
  extensions: [
    ...studyEditorExtensions,
    Placeholder.configure({ placeholder: '输入思考理解...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.reflection = editor.getHTML()
  },
})

// 插入图片
const insertImage = () => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editor.value?.chain().focus().setImage({ src: url }).run()
  }
}

// 压缩图片
const compressImage = (file: File, maxWidth: number = 1200, quality: number = 0.8): Promise<string> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = (e) => {
      // 使用 window.Image 避免与 Tiptap 的 Image 扩展冲突
      const img = new window.Image()
      img.onload = () => {
        const canvas = document.createElement('canvas')
        let width = img.width
        let height = img.height
        
        // 如果图片宽度超过最大宽度，按比例缩放
        if (width > maxWidth) {
          height = (height * maxWidth) / width
          width = maxWidth
        }
        
        canvas.width = width
        canvas.height = height
        
        const ctx = canvas.getContext('2d')
        ctx?.drawImage(img, 0, 0, width, height)
        
        // 转换为 Base64，使用指定的质量
        const compressedBase64 = canvas.toDataURL('image/jpeg', quality)
        
        // 计算压缩比例
        const originalSize = (e.target?.result as string).length
        const compressedSize = compressedBase64.length
        const compressionRatio = ((1 - compressedSize / originalSize) * 100).toFixed(1)
        
        console.log(`图片压缩完成：原始大小 ${(originalSize / 1024).toFixed(1)}KB，压缩后 ${(compressedSize / 1024).toFixed(1)}KB，压缩率 ${compressionRatio}%`)
        
        resolve(compressedBase64)
      }
      img.onerror = reject
      img.src = e.target?.result as string
    }
    reader.onerror = reject
    reader.readAsDataURL(file)
  })
}

// 上传图片
const uploadImage = () => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = async (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      try {
        console.log(`正在处理图片：${file.name}，大小：${(file.size / 1024).toFixed(1)}KB`)
        
        // 压缩图片
        const compressedBase64 = await compressImage(file)
        
        // 插入到编辑器
        editor.value?.chain().focus().setImage({ src: compressedBase64 }).run()
        console.log('图片已插入编辑器')
      } catch (error) {
        console.error('图片处理失败:', error)
        alert('图片处理失败，请重试')
      }
    }
  }
  input.click()
}

// Markdown 转 HTML 的改进实现
const markdownToHtml = (markdown: string): string => {
  // 预处理：标准化换行符
  let html = markdown.replace(/\r\n/g, '\n').replace(/\r/g, '\n')
  
  // 1. 代码块（```）- 必须最先处理，避免内部内容被转换
  const codeBlocks: string[] = []
  html = html.replace(/```(\w*)\n([\s\S]*?)```/g, (match, lang, code) => {
    const placeholder = `__CODE_BLOCK_${codeBlocks.length}__`
    codeBlocks.push(`<pre><code class="language-${lang || 'text'}">${code.trim()}</code></pre>`)
    return placeholder
  })
  
  // 2. 行内代码（`code`）- 保护起来避免被其他规则处理
  const inlineCodes: string[] = []
  html = html.replace(/`([^`]+)`/g, (match, code) => {
    const placeholder = `__INLINE_CODE_${inlineCodes.length}__`
    inlineCodes.push(`<code>${code}</code>`)
    return placeholder
  })
  
  // 3. 表格处理
  html = html.replace(/^\|(.+)\|\n\|[-:\s|]+\|\n((?:\|.+\|\n?)*)/gm, (match, header, rows) => {
    const headers = header.split('|').filter(h => h.trim()).map(h => `<th>${h.trim()}</th>`).join('')
    const rowsHtml = rows.trim().split('\n').map(row => {
      const cells = row.split('|').filter(c => c.trim()).map(c => `<td>${c.trim()}</td>`).join('')
      return `<tr>${cells}</tr>`
    }).join('')
    return `<table><thead><tr>${headers}</tr></thead><tbody>${rowsHtml}</tbody></table>`
  })
  
  // 4. 标题（从大到小，避免误匹配）
  html = html.replace(/^#### (.*$)/gim, '<h4>$1</h4>')
  html = html.replace(/^### (.*$)/gim, '<h3>$1</h3>')
  html = html.replace(/^## (.*$)/gim, '<h2>$1</h2>')
  html = html.replace(/^# (.*$)/gim, '<h1>$1</h1>')
  
  // 5. 分割线
  html = html.replace(/^[-*_]{3,}$/gim, '<hr>')
  
  // 6. 引用块
  html = html.replace(/^> (.+)$/gim, '<blockquote>$1</blockquote>')
  
  // 7. 处理列表（改进版）
  // 有序列表
  html = html.replace(/^(\d+)\. (.+)$/gim, '<li>$2</li>')
  // 无序列表
  html = html.replace(/^[*+-] (.+)$/gim, '<li>$1</li>')
  
  // 包裹连续的列表项
  html = html.replace(/(<li>.*?<\/li>\n?)+/g, (match) => {
    return `<ul>${match}</ul>`
  })
  
  // 8. 图片（必须在链接之前处理）
  html = html.replace(/!\[([^\]]*)\]\(([^)]+)\)/g, '<img src="$2" alt="$1" />')
  
  // 9. 链接
  html = html.replace(/\[([^\]]+)\]\(([^)]+)\)/g, '<a href="$2" target="_blank">$1</a>')
  
  // 10. 文本样式
  // 粗体（**text** 或 __text__）
  html = html.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
  html = html.replace(/__(.+?)__/g, '<strong>$1</strong>')
  
  // 斜体（*text* 或 _text_）
  html = html.replace(/\*(.+?)\*/g, '<em>$1</em>')
  html = html.replace(/_(.+?)_/g, '<em>$1</em>')
  
  // 删除线（~~text~~）
  html = html.replace(/~~(.+?)~~/g, '<s>$1</s>')
  
  // 11. 恢复代码块和行内代码
  codeBlocks.forEach((code, index) => {
    html = html.replace(`__CODE_BLOCK_${index}__`, code)
  })
  inlineCodes.forEach((code, index) => {
    html = html.replace(`__INLINE_CODE_${index}__`, code)
  })
  
  // 12. 段落处理
  // 分割成段落（双换行分隔）
  const paragraphs = html.split(/\n\n+/)
  html = paragraphs.map(para => {
    para = para.trim()
    // 跳过已经是块级元素的内容
    if (!para || 
        para.startsWith('<h') || 
        para.startsWith('<ul') || 
        para.startsWith('<ol') || 
        para.startsWith('<pre') || 
        para.startsWith('<blockquote') || 
        para.startsWith('<table') || 
        para.startsWith('<hr')) {
      return para
    }
    // 单换行转为 <br>
    para = para.replace(/\n/g, '<br>')
    return `<p>${para}</p>`
  }).join('\n')
  
  return html
}

// 导入 Markdown 文件
const importMarkdown = (targetEditor: any) => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = '.md,.markdown'
  input.onchange = (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      const reader = new FileReader()
      reader.onload = (e) => {
        const markdownContent = e.target?.result as string
        const htmlContent = markdownToHtml(markdownContent)
        
        // 如果传入了编辑器实例，使用该编辑器；否则使用主编辑器
        if (targetEditor && targetEditor.chain) {
          targetEditor.chain().focus().setContent(htmlContent).run()
        } else if (editor.value) {
          editor.value.chain().focus().setContent(htmlContent).run()
        }
      }
      reader.readAsText(file, 'UTF-8')
    }
  }
  input.click()
}

// 背诵笔记编辑器工具栏功能
// 插入表格功能
const insertMemorizationTable = (editorInstance: any) => {
  editorInstance?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

const insertMemorizationCustomTable = (editorInstance: any, rows: number, cols: number) => {
  editorInstance?.chain().focus().insertTable({ rows, cols, withHeaderRow: true }).run()
}

// 插入图片功能
const insertMemorizationImage = (editorInstance: any) => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editorInstance?.chain().focus().setImage({ src: url }).run()
  }
}

// 上传图片功能
const uploadMemorizationImage = async (editorInstance: any) => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = async (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      try {
        console.log(`正在处理图片：${file.name}，大小：${(file.size / 1024).toFixed(1)}KB`)
        const compressedBase64 = await compressImage(file)
        editorInstance?.chain().focus().setImage({ src: compressedBase64 }).run()
        console.log('图片已插入编辑器')
      } catch (error) {
        console.error('图片处理失败:', error)
        alert('图片处理失败，请重试')
      }
    }
  }
  input.click()
}

// 文本格式化功能
const toggleMemorizationBold = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleBold().run()
}

const toggleMemorizationItalic = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleItalic().run()
}

const toggleMemorizationUnderline = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleUnderline().run()
}

const toggleMemorizationStrike = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleStrike().run()
}

const toggleMemorizationHighlight = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleHighlight().run()
}

// 标题功能
const setMemorizationHeading = (editorInstance: any, level: number) => {
  editorInstance?.chain().focus().toggleHeading({ level }).run()
}

// 列表功能
const toggleMemorizationBulletList = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleBulletList().run()
}

const toggleMemorizationOrderedList = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleOrderedList().run()
}

// 对齐功能
const setMemorizationTextAlign = (editorInstance: any, alignment: string) => {
  editorInstance?.chain().focus().setTextAlign(alignment).run()
}

// 链接功能
const setMemorizationLink = (editorInstance: any) => {
  const url = prompt('请输入链接URL:')
  if (url) {
    editorInstance?.chain().focus().setLink({ href: url }).run()
  }
}

const unsetMemorizationLink = (editorInstance: any) => {
  editorInstance?.chain().focus().unsetLink().run()
}

// 代码块功能
const toggleMemorizationCodeBlock = (editorInstance: any) => {
  editorInstance?.chain().focus().toggleCodeBlock().run()
}

// 撤销重做功能
const undoMemorization = (editorInstance: any) => {
  editorInstance?.chain().focus().undo().run()
}

const redoMemorization = (editorInstance: any) => {
  editorInstance?.chain().focus().redo().run()
}

// ===== 刷题笔记编辑器辅助函数 =====
// 链接功能
const setExerciseLink = (editorInstance: any) => {
  const url = prompt('请输入链接URL:')
  if (url) {
    editorInstance?.chain().focus().setLink({ href: url }).run()
  }
}

// 插入表格功能
const insertExerciseTable = (editorInstance: any) => {
  editorInstance?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

// 插入图片功能
const insertExerciseImage = (editorInstance: any) => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editorInstance?.chain().focus().setImage({ src: url }).run()
  }
}

// 上传图片功能
const uploadExerciseImage = async (editorInstance: any) => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = async (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      try {
        console.log(`正在处理图片：${file.name}，大小：${(file.size / 1024).toFixed(1)}KB`)
        const compressedBase64 = await compressImage(file)
        editorInstance?.chain().focus().setImage({ src: compressedBase64 }).run()
        console.log('图片已插入编辑器')
      } catch (error) {
        console.error('图片处理失败:', error)
        alert('图片处理失败，请重试')
      }
    }
  }
  input.click()
}

// 撤销重做功能
const undoExercise = (editorInstance: any) => {
  editorInstance?.chain().focus().undo().run()
}

const redoExercise = (editorInstance: any) => {
  editorInstance?.chain().focus().redo().run()
}

// ===== 求学笔记编辑器辅助函数 =====
// 链接功能
const setStudyLink = (editorInstance: any) => {
  const url = prompt('请输入链接URL:')
  if (url) {
    editorInstance?.chain().focus().setLink({ href: url }).run()
  }
}

// 插入表格功能
const insertStudyTable = (editorInstance: any) => {
  editorInstance?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

// 插入图片功能
const insertStudyImage = (editorInstance: any) => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editorInstance?.chain().focus().setImage({ src: url }).run()
  }
}

// 上传图片功能
const uploadStudyImage = async (editorInstance: any) => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = async (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      try {
        console.log(`正在处理图片：${file.name}，大小：${(file.size / 1024).toFixed(1)}KB`)
        const compressedBase64 = await compressImage(file)
        editorInstance?.chain().focus().setImage({ src: compressedBase64 }).run()
        console.log('图片已插入编辑器')
      } catch (error) {
        console.error('图片处理失败:', error)
        alert('图片处理失败，请重试')
      }
    }
  }
  input.click()
}

// 撤销重做功能
const undoStudy = (editorInstance: any) => {
  editorInstance?.chain().focus().undo().run()
}

const redoStudy = (editorInstance: any) => {
  editorInstance?.chain().focus().redo().run()
}

// ===== 拓展笔记编辑器工具函数 =====
const insertExpansionTable = (editorInstance: any) => {
  editorInstance?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

const insertExpansionImage = (editorInstance: any) => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editorInstance?.chain().focus().setImage({ src: url }).run()
  }
}

const uploadExpansionImage = async (editorInstance: any) => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = async (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      try {
        console.log(`正在处理图片：${file.name}，大小：${(file.size / 1024).toFixed(1)}KB`)
        const compressedBase64 = await compressImage(file)
        editorInstance?.chain().focus().setImage({ src: compressedBase64 }).run()
        console.log('图片已插入编辑器')
      } catch (error) {
        console.error('图片处理失败:', error)
        alert('图片处理失败，请重试')
      }
    }
  }
  input.click()
}

const setExpansionLink = (editorInstance: any) => {
  const url = prompt('请输入链接URL:')
  if (url) {
    editorInstance?.chain().focus().setLink({ href: url }).run()
  }
}

// 实战笔记编辑器
const practicalEditorExtensions = [
  StarterKit.configure({
    heading: { levels: [1, 2, 3] },
    bulletList: { keepMarks: true, keepAttributes: false },
    orderedList: { keepMarks: true, keepAttributes: false },
  }),
  Table.configure({
    resizable: true,
    HTMLAttributes: { class: 'border-collapse border border-gray-300 w-full' },
  }),
  TableRow,
  TableCell,
  TableHeader,
  Image,
  Underline,
  Strike,
  TextAlign.configure({ types: ['heading', 'paragraph'] }),
  CodeBlock,
  Highlight.configure({ multicolor: false }),
  Link.configure({
    openOnClick: false,
    HTMLAttributes: { class: 'text-blue-600 underline cursor-pointer' },
  }),
]

const requirementDescriptionEditor = useEditor({
  content: '',
  extensions: [
    ...practicalEditorExtensions,
    Placeholder.configure({ placeholder: '输入需求描述...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.requirementDescription = editor.getHTML()
  },
})

const designThinkingEditor = useEditor({
  content: '',
  extensions: [
    ...practicalEditorExtensions,
    Placeholder.configure({ placeholder: '输入设计思路...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.designThinking = editor.getHTML()
  },
})

const referenceDesignEditor = useEditor({
  content: '',
  extensions: [
    ...practicalEditorExtensions,
    Placeholder.configure({ placeholder: '输入参考设计...' }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm focus:outline-none min-h-[200px] max-h-[400px] overflow-y-auto p-3',
    },
  },
  onUpdate: ({ editor }) => {
    recordForm.referenceDesign = editor.getHTML()
  },
})

// ===== 实战笔记编辑器辅助函数 =====
// 链接功能
const setPracticalLink = (editorInstance: any) => {
  const url = prompt('请输入链接URL:')
  if (url) {
    editorInstance?.chain().focus().setLink({ href: url }).run()
  }
}

// 插入表格功能
const insertPracticalTable = (editorInstance: any) => {
  editorInstance?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

// 插入图片功能
const insertPracticalImage = (editorInstance: any) => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editorInstance?.chain().focus().setImage({ src: url }).run()
  }
}

// 上传图片功能
const uploadPracticalImage = async (editorInstance: any) => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = async (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      try {
        console.log(`正在处理图片：${file.name}，大小：${(file.size / 1024).toFixed(1)}KB`)
        const compressedBase64 = await compressImage(file)
        editorInstance?.chain().focus().setImage({ src: compressedBase64 }).run()
        console.log('图片已插入编辑器')
      } catch (error) {
        console.error('图片处理失败:', error)
        alert('图片处理失败，请重试')
      }
    }
  }
  input.click()
}

// 撤销重做功能
const undoPractical = (editorInstance: any) => {
  editorInstance?.chain().focus().undo().run()
}

const redoPractical = (editorInstance: any) => {
  editorInstance?.chain().focus().redo().run()
}

// 提交记录
const submitRecord = async () => {
  console.log('submitRecord 函数被调用')
  console.log('表单数据:', recordForm)
  
  // 获取编辑器内容
  const editorContent = editor.value?.getHTML() || ''
  console.log('编辑器内容:', editorContent)
  
  // 检查编辑器是否有实际内容（不是空的HTML标签）
  let hasContent = false
  let contentFieldName = '内容'
  
  if (recordForm.type === 'expansion') {
    // 拓展笔记：检查两个专用编辑器的内容
    const originalTextContent = originalTextEditor.value?.getHTML() || ''
    const understandingContent = understandingEditor.value?.getHTML() || ''
    
    const hasOriginalText = originalTextContent && originalTextContent.trim() !== '' && originalTextContent !== '<p></p>' && originalTextContent !== '<p><br></p>'
    const hasUnderstanding = understandingContent && understandingContent.trim() !== '' && understandingContent !== '<p></p>' && understandingContent !== '<p><br></p>'
    
    hasContent = hasOriginalText || hasUnderstanding
    contentFieldName = '拓展内容（原文记录/理解记录至少填一项）'
    console.log('拓展笔记内容检查:', { hasOriginalText, hasUnderstanding, hasContent })
  } else if (recordForm.type === 'memorization') {
    // 背诵笔记：检查三个专用编辑器的内容
    const originalTextContent = originalTextEditor.value?.getHTML() || ''
    const explanationContent = explanationEditor.value?.getHTML() || ''
    const cueContent = cueEditor.value?.getHTML() || ''
    
    const hasOriginalText = originalTextContent && originalTextContent.trim() !== '' && originalTextContent !== '<p></p>' && originalTextContent !== '<p><br></p>'
    const hasExplanation = explanationContent && explanationContent.trim() !== '' && explanationContent !== '<p></p>' && explanationContent !== '<p><br></p>'
    const hasCue = cueContent && cueContent.trim() !== '' && cueContent !== '<p></p>' && cueContent !== '<p><br></p>'
    
    hasContent = hasOriginalText || hasExplanation || hasCue
    contentFieldName = '背诵内容（原文/解释/提示词至少填一项）'
    console.log('背诵笔记内容检查:', { hasOriginalText, hasExplanation, hasCue, hasContent })
  } else if (recordForm.type === 'exercise') {
    // 刷题笔记：检查三个专用编辑器的内容
    const questionContent = questionDescriptionEditor.value?.getHTML() || ''
    const analysisContent = analysisEditor.value?.getHTML() || ''
    const answerContent = referenceAnswerEditor.value?.getHTML() || ''
    
    const hasQuestion = questionContent && questionContent.trim() !== '' && questionContent !== '<p></p>' && questionContent !== '<p><br></p>'
    const hasAnalysis = analysisContent && analysisContent.trim() !== '' && analysisContent !== '<p></p>' && analysisContent !== '<p><br></p>'
    const hasAnswer = answerContent && answerContent.trim() !== '' && answerContent !== '<p></p>' && answerContent !== '<p><br></p>'
    
    hasContent = hasQuestion || hasAnalysis || hasAnswer
    contentFieldName = '题目内容（题目描述/分析理解/参考答案至少填一项）'
    console.log('刷题笔记内容检查:', { hasQuestion, hasAnalysis, hasAnswer, hasContent })
  } else if (recordForm.type === 'practical') {
    // 实战笔记：检查三个专用编辑器的内容
    const requirementContent = requirementDescriptionEditor.value?.getHTML() || ''
    const designContent = designThinkingEditor.value?.getHTML() || ''
    const referenceContent = referenceDesignEditor.value?.getHTML() || ''
    
    const hasRequirement = requirementContent && requirementContent.trim() !== '' && requirementContent !== '<p></p>' && requirementContent !== '<p><br></p>'
    const hasDesign = designContent && designContent.trim() !== '' && designContent !== '<p></p>' && designContent !== '<p><br></p>'
    const hasReference = referenceContent && referenceContent.trim() !== '' && referenceContent !== '<p></p>' && referenceContent !== '<p><br></p>'
    
    hasContent = hasRequirement || hasDesign || hasReference
    contentFieldName = '项目内容（需求描述/设计思路/参考设计至少填一项）'
    console.log('实战笔记内容检查:', { hasRequirement, hasDesign, hasReference, hasContent })
  } else if (recordForm.type === 'study') {
    // 求学笔记：检查六个专用编辑器的内容
    const coreConceptContent = coreConceptEditor.value?.getHTML() || ''
    const mechanismContent = mechanismEditor.value?.getHTML() || ''
    const applicationContent = applicationCaseEditor.value?.getHTML() || ''
    const extensionContent = extensionEditor.value?.getHTML() || ''
    const mistakeContent = commonMistakeEditor.value?.getHTML() || ''
    const reflectionContent = reflectionEditor.value?.getHTML() || ''
    
    const hasCoreConcept = coreConceptContent && coreConceptContent.trim() !== '' && coreConceptContent !== '<p></p>' && coreConceptContent !== '<p><br></p>'
    const hasMechanism = mechanismContent && mechanismContent.trim() !== '' && mechanismContent !== '<p></p>' && mechanismContent !== '<p><br></p>'
    const hasApplication = applicationContent && applicationContent.trim() !== '' && applicationContent !== '<p></p>' && applicationContent !== '<p><br></p>'
    const hasExtension = extensionContent && extensionContent.trim() !== '' && extensionContent !== '<p></p>' && extensionContent !== '<p><br></p>'
    const hasMistake = mistakeContent && mistakeContent.trim() !== '' && mistakeContent !== '<p></p>' && mistakeContent !== '<p><br></p>'
    const hasReflection = reflectionContent && reflectionContent.trim() !== '' && reflectionContent !== '<p></p>' && reflectionContent !== '<p><br></p>'
    
    hasContent = hasCoreConcept || hasMechanism || hasApplication || hasExtension || hasMistake || hasReflection
    contentFieldName = '学习内容（核心概念/机制原理/应用案例/延伸对比/常见误区/思考理解至少填一项）'
    console.log('求学笔记内容检查:', { hasCoreConcept, hasMechanism, hasApplication, hasExtension, hasMistake, hasReflection, hasContent })
  } else {
    // 框架笔记、碎片笔记：检查通用编辑器
    hasContent = editorContent && editorContent.trim() !== '' && editorContent !== '<p></p>' && editorContent !== '<p><br></p>'
    contentFieldName = '内容'
  }
  
  console.log('是否有内容:', hasContent)
  
  console.log('验证结果:')
  console.log('- 标题:', recordForm.title, '是否为空:', !recordForm.title)
  console.log('- 内容:', hasContent, '是否为空:', !hasContent)
  console.log('- 类型:', recordForm.type, '是否为空:', !recordForm.type)
  
  if (!recordForm.title || !hasContent || !recordForm.type) {
    let missingFields = []
    if (!recordForm.title) missingFields.push('标题')
    if (!hasContent) missingFields.push(contentFieldName)
    if (!recordForm.type) missingFields.push('类型')
    
    alert(`请填写完整信息，缺少：${missingFields.join('、')}`)
    return
  }

  // 准备提交的数据
  // 对于有专用富文本编辑器的笔记类型，content字段设置为空字符串
  let finalContent = ''
  if (recordForm.type === 'expansion' || recordForm.type === 'memorization' || 
      recordForm.type === 'exercise' || recordForm.type === 'practical' || recordForm.type === 'study') {
    // 这些类型有专用编辑器，content字段可以为空
    finalContent = ''
  } else {
    // 框架笔记、碎片笔记使用通用编辑器
    finalContent = editorContent
    
    // 将占位符还原为原始图片
    if (imageMap.value.size > 0) {
      console.log('检测到', imageMap.value.size, '个图片占位符，正在还原...')
      finalContent = restorePlaceholdersToImages(finalContent)
      console.log('✓ 图片已还原，最终内容长度:', finalContent.length)
    }
  }
      
  const noteData: any = {
        type: recordForm.type,
        title: recordForm.title,
        summary: recordForm.summary,
    content: finalContent
      }
      
  // 添加类型特定字段
      if (recordForm.type === 'framework') {
    noteData.subjectType = recordForm.subjectType.join(',')
    noteData.knowledgePoint = recordForm.knowledgePoint.join(',')
      } else if (recordForm.type === 'study') {
    // 求学笔记：从编辑器实时获取HTML内容（包含图片）
    noteData.studySubject = recordForm.studySubject.join(',')
    noteData.knowledgePoint = recordForm.knowledgePoint.join(',')
    
    // 获取内容并还原图片占位符
    noteData.coreConcept = restorePlaceholdersToImages(coreConceptEditor.value?.getHTML() || '')
    noteData.mechanism = restorePlaceholdersToImages(mechanismEditor.value?.getHTML() || '')
    noteData.applicationCase = restorePlaceholdersToImages(applicationCaseEditor.value?.getHTML() || '')
    noteData.extension = restorePlaceholdersToImages(extensionEditor.value?.getHTML() || '')
    noteData.commonMistake = restorePlaceholdersToImages(commonMistakeEditor.value?.getHTML() || '')
    noteData.reflection = restorePlaceholdersToImages(reflectionEditor.value?.getHTML() || '')
    
    console.log('求学笔记保存数据（图片已还原）:', {
      coreConcept: noteData.coreConcept.substring(0, 100),
      mechanism: noteData.mechanism.substring(0, 100),
      applicationCase: noteData.applicationCase.substring(0, 100)
    })
  } else if (recordForm.type === 'expansion') {
    // 拓展笔记：从编辑器实时获取HTML内容（包含图片）
    noteData.project = recordForm.project
    noteData.knowledgePoint = recordForm.knowledgePoint.join(',')
    
    // 获取内容并还原图片占位符
    noteData.originalText = restorePlaceholdersToImages(originalTextEditor.value?.getHTML() || '')
    noteData.understanding = restorePlaceholdersToImages(understandingEditor.value?.getHTML() || '')
    
    console.log('拓展笔记保存数据（图片已还原）:', {
      originalText: noteData.originalText.substring(0, 100),
      understanding: noteData.understanding.substring(0, 100)
    })
  } else if (recordForm.type === 'memorization') {
    // 背诵笔记：从编辑器实时获取HTML内容（包含图片）
    noteData.project = recordForm.project
    noteData.knowledgePoint = recordForm.knowledgePoint.join(',')
    
    // 获取内容并还原图片占位符
    noteData.originalText = restorePlaceholdersToImages(originalTextEditor.value?.getHTML() || '')
    noteData.explanation = restorePlaceholdersToImages(explanationEditor.value?.getHTML() || '')
    noteData.cue = restorePlaceholdersToImages(cueEditor.value?.getHTML() || '')
    
    console.log('背诵笔记保存数据（图片已还原）:', {
      originalText: noteData.originalText.substring(0, 100),
      explanation: noteData.explanation.substring(0, 100)
    })
      } else if (recordForm.type === 'exercise') {
    // 刷题笔记：从编辑器实时获取HTML内容（包含图片）
    noteData.exerciseSource = recordForm.exerciseSource
    noteData.exerciseSubject = recordForm.exerciseSubject
    noteData.exerciseKnowledge = recordForm.exerciseKnowledge.join(',')
    noteData.exerciseDifficulty = recordForm.exerciseDifficulty
    
    // 获取内容并还原图片占位符
    noteData.questionDescription = restorePlaceholdersToImages(questionDescriptionEditor.value?.getHTML() || '')
    noteData.analysis = restorePlaceholdersToImages(analysisEditor.value?.getHTML() || '')
    noteData.referenceAnswer = restorePlaceholdersToImages(referenceAnswerEditor.value?.getHTML() || '')
    
    // 检查是否包含图片
    const hasImageInQuestion = noteData.questionDescription.includes('<img')
    const hasImageInAnalysis = noteData.analysis.includes('<img')
    const hasImageInAnswer = noteData.referenceAnswer.includes('<img')
    
    console.log('刷题笔记保存数据检查（图片已还原）:', {
      questionDescription长度: noteData.questionDescription.length,
      包含图片: hasImageInQuestion,
      analysis长度: noteData.analysis.length,
      包含图片_分析: hasImageInAnalysis,
      referenceAnswer长度: noteData.referenceAnswer.length,
      包含图片_答案: hasImageInAnswer
    })
    
    if (hasImageInQuestion || hasImageInAnalysis || hasImageInAnswer) {
      console.log('✅ 检测到图片数据，准备提交')
    } else {
      console.log('⚠️ 未检测到图片数据')
    }
      } else if (recordForm.type === 'practical') {
    // 实战笔记：从编辑器实时获取HTML内容（包含图片）
    noteData.techTags = recordForm.techTags.join(',')
    noteData.projectType = recordForm.projectType.join(',')
    
    // 获取内容并还原图片占位符
    noteData.requirementDescription = restorePlaceholdersToImages(requirementDescriptionEditor.value?.getHTML() || '')
    noteData.designThinking = restorePlaceholdersToImages(designThinkingEditor.value?.getHTML() || '')
    noteData.referenceDesign = restorePlaceholdersToImages(referenceDesignEditor.value?.getHTML() || '')
    
    console.log('实战笔记保存数据（图片已还原）:', {
      requirementDescription: noteData.requirementDescription.substring(0, 100),
      designThinking: noteData.designThinking.substring(0, 100)
    })
      } else if (recordForm.type === 'fragment') {
    noteData.fragmentCategory = recordForm.fragmentCategory.join(',')
    noteData.fragmentTheme = recordForm.fragmentTheme.join(',')
    noteData.importance = recordForm.importance
  }
  
  console.log('准备提交的完整数据:', noteData)
  console.log('数据大小 (字符数):', JSON.stringify(noteData).length)

  console.log('=== 开始提交数据到服务器 ===')
  
  try {
    if (isEditing.value && editingRecordId.value) {
      // 编辑现有记录
      noteData.id = editingRecordId.value
      console.log('执行更新操作，笔记ID:', editingRecordId.value)
      const response = await request.put('/note', noteData)
      
      console.log('服务器响应:', response)
      
      if (response.code === 200) {
        console.log('✅ 保存成功！笔记ID:', response.data?.id || editingRecordId.value)
        alert('记录修改成功！')
        // 重新加载数据
        await loadNotes()
      } else {
        console.error('❌ 保存失败，服务器返回:', response)
        alert('修改失败：' + (response.message || '未知错误'))
      }
    } else {
      // 创建新记录
      console.log('执行创建操作')
      const response = await request.post('/note', noteData)
      
      console.log('服务器响应:', response)
      
      if (response.code === 200) {
        console.log('✅ 保存成功！新笔记ID:', response.data?.id)
        alert('记录保存成功！')
        // 重新加载数据
        await loadNotes()
      } else {
        console.error('❌ 保存失败，服务器返回:', response)
        alert('保存失败：' + (response.message || '未知错误'))
      }
    }

    closeModal()
  } catch (error) {
    console.error('❌ 保存笔记失败，捕获异常:', error)
    console.error('异常详情:', {
      message: error.message,
      stack: error.stack,
      response: error.response
    })
    alert('保存失败，请检查网络连接或查看控制台错误信息')
  }
  
  console.log('=== 提交流程结束 ===')
}

// 重置表单
const resetForm = () => {
  recordForm.type = ''
  recordForm.title = ''
  recordForm.summary = ''

  
  // 重置类型特定字段
  recordForm.subject = []
  recordForm.frameworkType = ''
  recordForm.course = []
  recordForm.subjectType = []
  recordForm.project = ''
  recordForm.knowledgePoint = []
  
  // 背诵笔记字段
  recordForm.originalText = ''
  recordForm.explanation = ''
  recordForm.cue = ''
  
  // 求学笔记字段
  recordForm.studySubject = []
  recordForm.coreConcept = ''
  recordForm.mechanism = ''
  recordForm.applicationCase = ''
  recordForm.extension = ''
  recordForm.commonMistake = ''
  recordForm.reflection = ''
  
  // 刷题笔记字段
  recordForm.exerciseSource = ''
  recordForm.exerciseSubject = ''
  recordForm.exerciseKnowledge = []
  recordForm.exerciseDifficulty = ''
  recordForm.questionDescription = ''
  recordForm.analysis = ''
  recordForm.referenceAnswer = ''
  
  // 实战笔记字段
  recordForm.techTags = []
  recordForm.projectType = []
  recordForm.requirementDescription = ''
  recordForm.designThinking = ''
  recordForm.referenceDesign = ''
  
  // 碎片笔记字段
  recordForm.fragmentCategory = []
  recordForm.fragmentTheme = []
  recordForm.importance = ''
  
  // 重置输入框
  newSubject.value = ''
  newCourse.value = ''
  newFragmentCategory.value = ''
  newBookSubject.value = ''
  newStudySubject.value = ''
  newTechTag.value = ''
  newFragmentSubject.value = ''
  newSubjectType.value = ''
  newKnowledgePoint.value = ''
  newProjectType.value = ''
  newFragmentTheme.value = ''
  
  // 重置编辑器内容
  console.log('开始重置所有编辑器...')
  
  // 主编辑器（碎片笔记、框架笔记）
  if (editor.value) {
    editor.value.commands.setContent('')
    console.log('✓ 主编辑器已重置')
  }
  
  // 背诵笔记的3个编辑器
  if (originalTextEditor.value) {
    originalTextEditor.value.commands.setContent('')
    console.log('✓ 背诵笔记-原文编辑器已重置')
  }
  if (explanationEditor.value) {
    explanationEditor.value.commands.setContent('')
    console.log('✓ 背诵笔记-解释编辑器已重置')
  }
  if (cueEditor.value) {
    cueEditor.value.commands.setContent('')
    console.log('✓ 背诵笔记-提示词编辑器已重置')
  }
  
  // 拓展笔记的编辑器
  if (understandingEditor.value) {
    understandingEditor.value.commands.setContent('')
    console.log('✓ 拓展笔记-理解编辑器已重置')
  }
  
  // 求学笔记的6个编辑器
  if (coreConceptEditor.value) {
    coreConceptEditor.value.commands.setContent('')
    console.log('✓ 求学笔记-核心概念编辑器已重置')
  }
  if (mechanismEditor.value) {
    mechanismEditor.value.commands.setContent('')
    console.log('✓ 求学笔记-机制原理编辑器已重置')
  }
  if (applicationCaseEditor.value) {
    applicationCaseEditor.value.commands.setContent('')
    console.log('✓ 求学笔记-应用案例编辑器已重置')
  }
  if (extensionEditor.value) {
    extensionEditor.value.commands.setContent('')
    console.log('✓ 求学笔记-延伸对比编辑器已重置')
  }
  if (commonMistakeEditor.value) {
    commonMistakeEditor.value.commands.setContent('')
    console.log('✓ 求学笔记-常见误区编辑器已重置')
  }
  if (reflectionEditor.value) {
    reflectionEditor.value.commands.setContent('')
    console.log('✓ 求学笔记-思考理解编辑器已重置')
  }
  
  // 刷题笔记的3个编辑器
  if (questionDescriptionEditor.value) {
    questionDescriptionEditor.value.commands.setContent('')
    console.log('✓ 刷题笔记-题目描述编辑器已重置')
  }
  if (analysisEditor.value) {
    analysisEditor.value.commands.setContent('')
    console.log('✓ 刷题笔记-分析理解编辑器已重置')
  }
  if (referenceAnswerEditor.value) {
    referenceAnswerEditor.value.commands.setContent('')
    console.log('✓ 刷题笔记-参考答案编辑器已重置')
  }
  
  // 实战笔记的3个编辑器
  if (requirementDescriptionEditor.value) {
    requirementDescriptionEditor.value.commands.setContent('')
    console.log('✓ 实战笔记-需求描述编辑器已重置')
  }
  if (designThinkingEditor.value) {
    designThinkingEditor.value.commands.setContent('')
    console.log('✓ 实战笔记-设计思路编辑器已重置')
  }
  if (referenceDesignEditor.value) {
    referenceDesignEditor.value.commands.setContent('')
    console.log('✓ 实战笔记-参考设计编辑器已重置')
  }
  
  console.log('所有编辑器重置完成！')
}

// 获取类型样式
const getTypeClass = (type: string) => {
  const classes = {
    study: 'bg-blue-100 text-blue-600',
    expansion: 'bg-blue-100 text-blue-600',
    exercise: 'bg-orange-100 text-orange-600',
    practical: 'bg-red-100 text-red-600',
    fragment: 'bg-indigo-100 text-indigo-600',
    framework: 'bg-teal-100 text-teal-600',
    memorization: 'bg-pink-100 text-pink-600'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

// 获取类型文本
const getTypeText = (type: string) => {
  const texts = {
    study: '求学笔记',
    expansion: '拓展笔记',
    exercise: '刷题笔记',
    practical: '实战笔记',
    fragment: '碎片笔记',
    framework: '框架笔记',
    memorization: '背诵笔记'
  }
  return texts[type as keyof typeof texts] || '未知'
}

// 获取难度文本
const getDifficultyText = (difficulty: string) => {
  const texts = {
    easy: '简单',
    medium: '中等',
    hard: '困难'
  }
  return texts[difficulty as keyof typeof texts] || difficulty
}

// 获取难度样式
const getDifficultyClass = (difficulty: string) => {
  const classes = {
    easy: 'text-green-600',
    medium: 'text-yellow-600',
    hard: 'text-red-600'
  }
  return classes[difficulty as keyof typeof classes] || 'text-gray-600'
}

// 获取重要程度文本
const getImportanceText = (importance: string) => {
  const texts = {
    low: '一般',
    medium: '重要',
    high: '非常重要'
  }
  return texts[importance as keyof typeof texts] || importance
}

// 获取重要程度样式
const getImportanceClass = (importance: string) => {
  const classes = {
    low: 'text-gray-600',
    medium: 'text-yellow-600',
    high: 'text-red-600'
  }
  return classes[importance as keyof typeof classes] || 'text-gray-600'
}



// 组件卸载时销毁编辑器
onBeforeUnmount(() => {
  editor.value?.destroy()
  originalTextEditor.value?.destroy()
  explanationEditor.value?.destroy()
  cueEditor.value?.destroy()
})

// 加载存储的类型
const loadStoredTypes = () => {
  try {
    storedSubjects.value = JSON.parse(localStorage.getItem('noteSubjects') || '[]')
    storedCourses.value = JSON.parse(localStorage.getItem('noteCourses') || '[]')
    storedStudySubjects.value = JSON.parse(localStorage.getItem('noteStudySubjects') || '[]')
    storedBookSubjects.value = JSON.parse(localStorage.getItem('noteBookSubjects') || '[]')
    storedTechTags.value = JSON.parse(localStorage.getItem('noteTechTags') || '[]')
    storedFragmentCategories.value = JSON.parse(localStorage.getItem('noteFragmentCategories') || '[]')
    storedExerciseSubjects.value = JSON.parse(localStorage.getItem('noteExerciseSubjects') || '[]')
    storedSubjectTypes.value = JSON.parse(localStorage.getItem('noteSubjectTypes') || '[]')
    storedKnowledgePoints.value = JSON.parse(localStorage.getItem('noteKnowledgePoints') || '[]')
    storedProjectTypes.value = JSON.parse(localStorage.getItem('noteProjectTypes') || '[]')
    storedFragmentThemes.value = JSON.parse(localStorage.getItem('noteFragmentThemes') || '[]')
    
    // 加载框架笔记数据
    storedFrameworkSubjects.value = JSON.parse(localStorage.getItem('storedFrameworkSubjects') || JSON.stringify(defaultFrameworkSubjects))
    storedFrameworkKnowledge.value = JSON.parse(localStorage.getItem('storedFrameworkKnowledge') || JSON.stringify(defaultFrameworkKnowledge))
    
    // 加载自定义选项
    customProjectOptions.value = JSON.parse(localStorage.getItem('customProjectOptions') || '[]')
    customSubjectTypeOptions.value = JSON.parse(localStorage.getItem('customSubjectTypeOptions') || '[]')
    customProjectTypeOptions.value = JSON.parse(localStorage.getItem('customProjectTypeOptions') || '[]')
    
    console.log('加载的存储数据:')
    console.log('- 学科:', storedSubjects.value)
    console.log('- 课程:', storedCourses.value)
    console.log('- 求学学科:', storedStudySubjects.value)
    console.log('- 书籍学科:', storedBookSubjects.value)
    console.log('- 技术标签:', storedTechTags.value)
    console.log('- 碎片分类:', storedFragmentCategories.value)
    console.log('- 练习学科:', storedExerciseSubjects.value)
    console.log('- 学科类型:', storedSubjectTypes.value)
    console.log('- 知识点:', storedKnowledgePoints.value)
    console.log('- 项目类型:', storedProjectTypes.value)
    console.log('- 主题:', storedFragmentThemes.value)
    console.log('- 自定义学科项目:', customProjectOptions.value)
    console.log('- 自定义学科类型:', customSubjectTypeOptions.value)
    console.log('- 自定义项目类型:', customProjectTypeOptions.value)
  } catch (error) {
    console.error('加载存储的类型失败:', error)
  }
}

// 保存类型到本地存储
const saveTypeToStorage = (type: string, value: string) => {
  try {
    let storedArray: string[] = []
    let storageKey = ''
    
    switch (type) {
      case 'subject':
        storedArray = storedSubjects.value
        storageKey = 'noteSubjects'
        break
      case 'course':
        storedArray = storedCourses.value
        storageKey = 'noteCourses'
        break
      case 'studySubject':
        storedArray = storedStudySubjects.value
        storageKey = 'noteStudySubjects'
        break
      case 'bookSubject':
        storedArray = storedBookSubjects.value
        storageKey = 'noteBookSubjects'
        break
      case 'techTag':
        storedArray = storedTechTags.value
        storageKey = 'noteTechTags'
        break
      case 'fragmentCategory':
        storedArray = storedFragmentCategories.value
        storageKey = 'noteFragmentCategories'
        break
      case 'exerciseSubject':
        storedArray = storedExerciseSubjects.value
        storageKey = 'noteExerciseSubjects'
        break
      case 'subjectType':
        storedArray = storedSubjectTypes.value
        storageKey = 'noteSubjectTypes'
        break
      case 'knowledgePoint':
        storedArray = storedKnowledgePoints.value
        storageKey = 'noteKnowledgePoints'
        break
      case 'projectType':
        storedArray = storedProjectTypes.value
        storageKey = 'noteProjectTypes'
        break
      case 'fragmentTheme':
        storedArray = storedFragmentThemes.value
        storageKey = 'noteFragmentThemes'
        break
    }
    
    if (!storedArray.includes(value)) {
      storedArray.push(value)
      localStorage.setItem(storageKey, JSON.stringify(storedArray))
      
      // 更新对应的响应式数组
      switch (type) {
        case 'subject':
          storedSubjects.value = storedArray
          break
        case 'course':
          storedCourses.value = storedArray
          break
        case 'studySubject':
          storedStudySubjects.value = storedArray
          break
        case 'bookSubject':
          storedBookSubjects.value = storedArray
          break
        case 'techTag':
          storedTechTags.value = storedArray
          break
        case 'fragmentCategory':
          storedFragmentCategories.value = storedArray
          break
        case 'exerciseSubject':
          storedExerciseSubjects.value = storedArray
          break
        case 'subjectType':
          storedSubjectTypes.value = storedArray
          break
        case 'knowledgePoint':
          storedKnowledgePoints.value = storedArray
          break
        case 'projectType':
          storedProjectTypes.value = storedArray
          break
        case 'fragmentTheme':
          storedFragmentThemes.value = storedArray
          break
      }
    }
  } catch (error) {
    console.error('保存类型到存储失败:', error)
  }
}

// 删除存储的类型
const deleteStoredType = (type: string, value: string) => {
  try {
    let storageKey = ''
    
    // 根据类型确定正确的存储key
    switch (type) {
      case 'subject':
        storageKey = 'noteSubjects'
        break
      case 'course':
        storageKey = 'noteCourses'
        break
      case 'studySubject':
        storageKey = 'noteStudySubjects'
        break
      case 'bookSubject':
        storageKey = 'noteBookSubjects'
        break
      case 'techTag':
        storageKey = 'noteTechTags'
        break
      case 'fragmentCategory':
        storageKey = 'noteFragmentCategories'
        break
      case 'exerciseSubject':
        storageKey = 'noteExerciseSubjects'
        break
      case 'subjectType':
        storageKey = 'noteSubjectTypes'
        break
      case 'knowledgePoint':
        storageKey = 'noteKnowledgePoints'
        break
      case 'projectType':
        storageKey = 'noteProjectTypes'
        break
      case 'fragmentTheme':
        storageKey = 'noteFragmentThemes'
        break
    }
    
    if (!storageKey) {
      console.error('未知的类型:', type)
      return
    }
    
    let storedArray: string[] = JSON.parse(localStorage.getItem(storageKey) || '[]')
    const index = storedArray.indexOf(value)
    if (index > -1) {
      storedArray.splice(index, 1)
      localStorage.setItem(storageKey, JSON.stringify(storedArray))
      
      // 更新对应的响应式数组
      switch (type) {
        case 'subject':
          storedSubjects.value = storedArray
          break
        case 'course':
          storedCourses.value = storedArray
          break
        case 'studySubject':
          storedStudySubjects.value = storedArray
          break
        case 'bookSubject':
          storedBookSubjects.value = storedArray
          break
        case 'techTag':
          storedTechTags.value = storedArray
          break
        case 'fragmentCategory':
          storedFragmentCategories.value = storedArray
          break
        case 'exerciseSubject':
          storedExerciseSubjects.value = storedArray
          break
        case 'subjectType':
          storedSubjectTypes.value = storedArray
          break
        case 'knowledgePoint':
          storedKnowledgePoints.value = storedArray
          break
        case 'projectType':
          storedProjectTypes.value = storedArray
          break
        case 'fragmentTheme':
          storedFragmentThemes.value = storedArray
          break
      }
      
      console.log(`成功删除 ${type}: ${value}`)
    } else {
      console.log(`未找到要删除的 ${type}: ${value}`)
    }
  } catch (error) {
    console.error('删除存储的类型失败:', error)
  }
}

// 切换学科
const toggleSubject = (subject: string) => {
  if (recordForm.subject.includes(subject)) {
    recordForm.subject = recordForm.subject.filter(s => s !== subject)
  } else {
    recordForm.subject.push(subject)
  }
}

// 移除学科
const removeSubject = (subject: string) => {
  recordForm.subject = recordForm.subject.filter(s => s !== subject)
}

// 切换课程
const toggleCourse = (course: string) => {
  if (recordForm.course.includes(course)) {
    recordForm.course = recordForm.course.filter(c => c !== course)
  } else {
    recordForm.course.push(course)
  }
}

// 移除课程
const removeCourse = (course: string) => {
  recordForm.course = recordForm.course.filter(c => c !== course)
}

// 切换求学学科类型
const toggleStudySubject = (subject: string) => {
  if (recordForm.studySubject.includes(subject)) {
    recordForm.studySubject = recordForm.studySubject.filter(s => s !== subject)
  } else {
    recordForm.studySubject.push(subject)
  }
}

// 移除求学学科类型
const removeStudySubject = (subject: string) => {
  recordForm.studySubject = recordForm.studySubject.filter(s => s !== subject)
}

// === 框架笔记处理函数 ===
// 移除框架笔记学科类型
const removeFrameworkSubject = (subject: string) => {
  recordForm.subjectType = recordForm.subjectType.filter(s => s !== subject)
}

// 移除框架笔记知识点
const removeFrameworkKnowledge = (knowledge: string) => {
  recordForm.knowledgePoint = recordForm.knowledgePoint.filter(k => k !== knowledge)
}

// 添加框架笔记学科类型
const addFrameworkSubject = () => {
  const subject = newFrameworkSubject.value.trim()
  if (subject) {
    // 添加到表单
    if (!recordForm.subjectType.includes(subject)) {
      recordForm.subjectType.push(subject)
    }
    // 添加到存储数组
    if (!storedFrameworkSubjects.value.includes(subject)) {
      storedFrameworkSubjects.value.push(subject)
      localStorage.setItem('storedFrameworkSubjects', JSON.stringify(storedFrameworkSubjects.value))
    }
    newFrameworkSubject.value = ''
  }
}

// 添加框架笔记知识点
const addFrameworkKnowledge = () => {
  const knowledge = newFrameworkKnowledge.value.trim()
  if (knowledge) {
    // 添加到表单
    if (!recordForm.knowledgePoint.includes(knowledge)) {
      recordForm.knowledgePoint.push(knowledge)
    }
    // 添加到存储数组
    if (!storedFrameworkKnowledge.value.includes(knowledge)) {
      storedFrameworkKnowledge.value.push(knowledge)
      localStorage.setItem('storedFrameworkKnowledge', JSON.stringify(storedFrameworkKnowledge.value))
    }
    newFrameworkKnowledge.value = ''
  }
}

// 选择框架笔记学科类型
const selectFrameworkSubject = (subject: string) => {
  if (!recordForm.subjectType.includes(subject)) {
    recordForm.subjectType.push(subject)
  }
}

// 选择框架笔记知识点
const selectFrameworkKnowledge = (knowledge: string) => {
  if (!recordForm.knowledgePoint.includes(knowledge)) {
    recordForm.knowledgePoint.push(knowledge)
  }
}

// 删除框架笔记学科类型（检查是否有笔记在使用）
const deleteFrameworkSubject = (subject: string) => {
  // 检查是否有框架笔记在使用这个学科类型
  const isInUse = todayRecords.value.some(record => 
    record.type === 'framework' && 
    record.subjectType && 
    Array.isArray(record.subjectType) && 
    record.subjectType.includes(subject)
  )
  
  if (isInUse) {
    alert(`无法删除"${subject}"，因为有笔记正在使用此学科类型`)
    return
  }
  
  // 从存储数组中删除
  const index = storedFrameworkSubjects.value.indexOf(subject)
  if (index > -1) {
    storedFrameworkSubjects.value.splice(index, 1)
    // 保存到localStorage
    localStorage.setItem('storedFrameworkSubjects', JSON.stringify(storedFrameworkSubjects.value))
    console.log(`学科类型"${subject}"已从列表中删除`)
  }
}

// 删除框架笔记知识点（检查是否有笔记在使用）
const deleteFrameworkKnowledge = (knowledge: string) => {
  // 检查是否有框架笔记在使用这个知识点
  const isInUse = todayRecords.value.some(record => 
    record.type === 'framework' && 
    record.knowledgePoint && 
    Array.isArray(record.knowledgePoint) && 
    record.knowledgePoint.includes(knowledge)
  )
  
  if (isInUse) {
    alert(`无法删除"${knowledge}"，因为有笔记正在使用此知识点`)
    return
  }
  
  // 从存储数组中删除
  const index = storedFrameworkKnowledge.value.indexOf(knowledge)
  if (index > -1) {
    storedFrameworkKnowledge.value.splice(index, 1)
    // 保存到localStorage
    localStorage.setItem('storedFrameworkKnowledge', JSON.stringify(storedFrameworkKnowledge.value))
    console.log(`知识点"${knowledge}"已从列表中删除`)
  }
}

// === 刷题笔记处理函数 ===
// 快速添加题目来源
const addCustomExerciseSource = () => {
  if (newExerciseSource.value.trim() && !allExerciseSources.value.includes(newExerciseSource.value.trim())) {
    allExerciseSources.value.push(newExerciseSource.value.trim())
    recordForm.exerciseSource = newExerciseSource.value.trim() // 自动选中新添加的来源
    newExerciseSource.value = ''
    // 保存到本地存储
    localStorage.setItem('allExerciseSources', JSON.stringify(allExerciseSources.value))
  }
}

// 快速添加学科类型
const addCustomExerciseSubject = () => {
  if (newExerciseSubject.value.trim() && !allExerciseSubjects.value.includes(newExerciseSubject.value.trim())) {
    allExerciseSubjects.value.push(newExerciseSubject.value.trim())
    recordForm.exerciseSubject = newExerciseSubject.value.trim() // 自动选中新添加的学科
    newExerciseSubject.value = ''
    // 保存到本地存储
    localStorage.setItem('allExerciseSubjects', JSON.stringify(allExerciseSubjects.value))
  }
}

// 删除题目来源（检查是否有笔记在使用）
const deleteExerciseSource = (source: string) => {
  // 检查是否有笔记正在使用这个来源
  const isInUse = todayRecords.value.some(record => 
    record.type === 'exercise' && record.exerciseSource === source
  )
  
  if (isInUse) {
    alert('该题目来源正在被笔记使用，无法删除')
    return
  }
  
  const index = allExerciseSources.value.indexOf(source)
  if (index > -1) {
    allExerciseSources.value.splice(index, 1)
    // 保存到本地存储
    localStorage.setItem('allExerciseSources', JSON.stringify(allExerciseSources.value))
  }
}

// 删除学科类型（检查是否有笔记在使用）
const deleteExerciseSubject = (subject: string) => {
  // 检查是否有笔记正在使用这个学科
  const isInUse = todayRecords.value.some(record => 
    record.type === 'exercise' && record.exerciseSubject === subject
  )
  
  if (isInUse) {
    alert('该学科类型正在被笔记使用，无法删除')
    return
  }
  
  const index = allExerciseSubjects.value.indexOf(subject)
  if (index > -1) {
    allExerciseSubjects.value.splice(index, 1)
    // 保存到本地存储
    localStorage.setItem('allExerciseSubjects', JSON.stringify(allExerciseSubjects.value))
  }
}

// 快速添加知识点
const addCustomExerciseKnowledge = () => {
  if (newExerciseKnowledge.value.trim() && !allExerciseKnowledge.value.includes(newExerciseKnowledge.value.trim())) {
    allExerciseKnowledge.value.push(newExerciseKnowledge.value.trim())
    newExerciseKnowledge.value = ''
    // 保存到本地存储
    localStorage.setItem('allExerciseKnowledge', JSON.stringify(allExerciseKnowledge.value))
  }
}

// 删除知识点（检查是否有笔记在使用）
const deleteExerciseKnowledge = (knowledge: string) => {
  // 检查是否有笔记正在使用这个知识点
  const isInUse = todayRecords.value.some(record => 
    record.type === 'exercise' && record.exerciseKnowledge && Array.isArray(record.exerciseKnowledge) && record.exerciseKnowledge.includes(knowledge)
  )
  
  if (isInUse) {
    alert('该知识点正在被笔记使用，无法删除')
    return
  }
  
  const index = allExerciseKnowledge.value.indexOf(knowledge)
  if (index > -1) {
    allExerciseKnowledge.value.splice(index, 1)
    // 保存到本地存储
    localStorage.setItem('allExerciseKnowledge', JSON.stringify(allExerciseKnowledge.value))
  }
}

// 添加知识点类型
const addExerciseKnowledge = () => {
  if (newExerciseKnowledge.value.trim() && !recordForm.exerciseKnowledge.includes(newExerciseKnowledge.value.trim())) {
    recordForm.exerciseKnowledge.push(newExerciseKnowledge.value.trim())
    newExerciseKnowledge.value = ''
  }
}

// 选择知识点类型
const selectExerciseKnowledge = (knowledge: string) => {
  if (recordForm.exerciseKnowledge.includes(knowledge)) {
    recordForm.exerciseKnowledge = recordForm.exerciseKnowledge.filter(k => k !== knowledge)
  } else {
    recordForm.exerciseKnowledge.push(knowledge)
  }
}

// 移除知识点类型
const removeExerciseKnowledge = (knowledge: string) => {
  recordForm.exerciseKnowledge = recordForm.exerciseKnowledge.filter(k => k !== knowledge)
}

// 切换书籍学科类型
const toggleBookSubject = (subject: string) => {
  if (recordForm.bookSubject.includes(subject)) {
    recordForm.bookSubject = recordForm.bookSubject.filter(s => s !== subject)
  } else {
    recordForm.bookSubject.push(subject)
  }
}

// 移除书籍学科类型
const removeBookSubject = (subject: string) => {
  recordForm.bookSubject = recordForm.bookSubject.filter(s => s !== subject)
}


// 切换碎片分类
const toggleFragmentCategory = (category: string) => {
  if (recordForm.fragmentCategory.includes(category)) {
    recordForm.fragmentCategory = recordForm.fragmentCategory.filter(c => c !== category)
  } else {
    recordForm.fragmentCategory.push(category)
  }
}

// 移除碎片分类
const removeFragmentCategory = (category: string) => {
  recordForm.fragmentCategory = recordForm.fragmentCategory.filter(c => c !== category)
}

// 切换学科类型
const toggleSubjectType = (type: string) => {
  if (recordForm.subjectType.includes(type)) {
    recordForm.subjectType = recordForm.subjectType.filter(t => t !== type)
  } else {
    recordForm.subjectType.push(type)
  }
}

// 移除学科类型
const removeSubjectType = (type: string) => {
  recordForm.subjectType = recordForm.subjectType.filter(t => t !== type)
}

// 切换知识点
const toggleKnowledgePoint = (point: string) => {
  if (recordForm.knowledgePoint.includes(point)) {
    recordForm.knowledgePoint = recordForm.knowledgePoint.filter(p => p !== point)
  } else {
    recordForm.knowledgePoint.push(point)
  }
}

// 移除知识点
const removeKnowledgePoint = (point: string) => {
  recordForm.knowledgePoint = recordForm.knowledgePoint.filter(p => p !== point)
}

// 选择背诵笔记知识点
const selectMemorizationKnowledge = (point: string) => {
  if (!recordForm.knowledgePoint.includes(point)) {
    recordForm.knowledgePoint.push(point)
  }
}

// 选择实战笔记领域
const selectPracticalDomain = (domain: string) => {
  recordForm.projectDomain = domain
}

// 选择实战笔记技术栈
const selectPracticalTech = (tech: string) => {
  if (!recordForm.techTags.includes(tech)) {
    recordForm.techTags.push(tech)
  }
}

// 切换项目类型
const toggleProjectType = (type: string) => {
  if (recordForm.projectType.includes(type)) {
    recordForm.projectType = recordForm.projectType.filter(t => t !== type)
  } else {
    recordForm.projectType.push(type)
  }
}

// 移除项目类型
const removeProjectType = (type: string) => {
  recordForm.projectType = recordForm.projectType.filter(t => t !== type)
}

// 计算属性：合并默认选项和自定义选项
const allProjectOptions = computed(() => {
  return [...defaultProjectOptions, ...customProjectOptions.value].sort()
})

const allSubjectTypeOptions = computed(() => {
  return [...defaultSubjectTypeOptions, ...customSubjectTypeOptions.value].sort()
})

const allProjectTypeOptions = computed(() => {
  // 获取项目模板的名称作为实战笔记的项目类型选项
  const templateNames = projectTemplates.value.map(template => template.name)
  return templateNames.sort()
})

// 处理自定义学科项目
const handleCustomProject = () => {
  if (customProject.value.trim()) {
    const newProject = customProject.value.trim()
    recordForm.project = newProject
    
    // 添加到自定义选项中
    if (!defaultProjectOptions.includes(newProject) && !customProjectOptions.value.includes(newProject)) {
      customProjectOptions.value.push(newProject)
      // 保存到本地存储
      localStorage.setItem('customProjectOptions', JSON.stringify(customProjectOptions.value))
    }
    
    customProject.value = ''
  }
}

// 处理学科类型选择
const handleSubjectTypeSelect = () => {
  if (selectedSubjectType.value && selectedSubjectType.value !== 'other') {
    if (!recordForm.subjectType.includes(selectedSubjectType.value)) {
      recordForm.subjectType.push(selectedSubjectType.value)
    }
    selectedSubjectType.value = ''
  }
}

// 处理项目类型选择
const handleProjectTypeSelect = () => {
  if (selectedProjectType.value) {
    if (!recordForm.projectType.includes(selectedProjectType.value)) {
      recordForm.projectType.push(selectedProjectType.value)
    }
    selectedProjectType.value = ''
  }
}



// 添加自定义学科类型
const addCustomSubjectType = () => {
  if (newSubjectType.value.trim()) {
    const newType = newSubjectType.value.trim()
    
    // 添加到表单
    if (!recordForm.subjectType.includes(newType)) {
      recordForm.subjectType.push(newType)
    }
    
    // 添加到自定义选项中
    if (!defaultSubjectTypeOptions.includes(newType) && !customSubjectTypeOptions.value.includes(newType)) {
      customSubjectTypeOptions.value.push(newType)
      localStorage.setItem('customSubjectTypeOptions', JSON.stringify(customSubjectTypeOptions.value))
    }
    
    newSubjectType.value = ''
    selectedSubjectType.value = ''
  }
}

// 添加自定义项目类型
const addCustomProjectType = () => {
  if (newProjectType.value.trim()) {
    const newType = newProjectType.value.trim()
    
    // 添加到表单
    if (!recordForm.projectType.includes(newType)) {
      recordForm.projectType.push(newType)
    }
    
    // 添加到自定义选项中
    if (!defaultProjectTypeOptions.includes(newType) && !customProjectTypeOptions.value.includes(newType)) {
      customProjectTypeOptions.value.push(newType)
      localStorage.setItem('customProjectTypeOptions', JSON.stringify(customProjectTypeOptions.value))
    }
    
    newProjectType.value = ''
    selectedProjectType.value = ''
  }
}

// 切换碎片主题
const toggleFragmentTheme = (theme: string) => {
  if (recordForm.fragmentTheme.includes(theme)) {
    recordForm.fragmentTheme = recordForm.fragmentTheme.filter(t => t !== theme)
  } else {
    recordForm.fragmentTheme.push(theme)
  }
}

// 移除碎片主题
const removeFragmentTheme = (theme: string) => {
  recordForm.fragmentTheme = recordForm.fragmentTheme.filter(t => t !== theme)
}

// 创建新笔记
const createNewNote = () => {
  resetForm()
  isEditing.value = false
  editingRecordId.value = null
  showCreateForm.value = true
}

// 图片调整大小相关
const selectedImage = ref<HTMLElement | null>(null)
const showImageResizer = ref(false)

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

// ==================== 归档管理功能 ====================

// 加载归档列表（用于批量添加时选择归档）
const loadArchiveList = async () => {
  try {
    const response = await getArchiveList({})
    console.log('归档列表响应:', response)
    if (response.data) {
      archives.value = response.data || []
      console.log('已加载归档列表:', archives.value)
      // 输出第一个归档的详细信息
      if (archives.value.length > 0) {
        console.log('第一个归档对象:', archives.value[0])
        console.log('第一个归档的ID:', archives.value[0].id)
      }
    }
  } catch (error) {
    console.error('加载归档列表失败:', error)
  }
}

// 获取归档类型标签
const getArchiveTypeLabel = (type: string | undefined) => {
  const typeMap: Record<string, string> = {
    book: '书籍',
    course: '课程',
    material: '材料',
    video: '视频',
    other: '其他'
  }
  return typeMap[type || ''] || '未分类'
}

// 获取笔记类型标签
const getNoteTypeLabel = (type: string) => {
  const typeMap: Record<string, string> = {
    fragment: '碎片笔记',
    study: '求学笔记',
    expansion: '拓展笔记',
    memorization: '背诵笔记',
    exercise: '刷题笔记',
    practical: '实战笔记',
    framework: '框架笔记'
  }
  return typeMap[type] || type
}

// ==================== 归档管理功能 ====================

// 关闭归档管理界面
const closeArchiveManager = () => {
  showArchiveManager.value = false
  archiveViewMode.value = 'archive'
  archiveViewSearch.value = ''
  archiveViewTypeFilter.value = ''
  noteViewSearch.value = ''
  noteViewTypeFilter.value = ''
  expandedArchives.value = []
}

// 归档视角：过滤归档列表
const filteredArchivesInView = computed(() => {
  return archives.value.filter(archive => {
    const matchName = !archiveViewSearch.value || 
      archive.archiveName.toLowerCase().includes(archiveViewSearch.value.toLowerCase())
    const matchType = !archiveViewTypeFilter.value || archive.archiveType === archiveViewTypeFilter.value
    return matchName && matchType
  })
})

// 笔记视角：过滤笔记列表
const filteredNotesInView = computed(() => {
  return allNotes.value.filter(note => {
    const matchQuery = !noteViewSearch.value || 
      note.title?.toLowerCase().includes(noteViewSearch.value.toLowerCase()) ||
      note.summary?.toLowerCase().includes(noteViewSearch.value.toLowerCase())
    const matchType = !noteViewTypeFilter.value || note.type === noteViewTypeFilter.value
    return matchQuery && matchType
  })
})

// 切换归档展开/收起
const toggleArchiveExpand = async (archiveId: number | undefined) => {
  if (!archiveId) {
    console.error('归档ID不存在')
    return
  }
  
  const index = expandedArchives.value.indexOf(archiveId)
  if (index > -1) {
    expandedArchives.value.splice(index, 1)
  } else {
    expandedArchives.value.push(archiveId)
    // 加载这个归档的笔记列表
    await loadArchiveNotes(archiveId)
  }
}

// 删除归档确认
const deleteArchiveConfirm = (archiveId: number | undefined, archiveName: string) => {
  if (!archiveId) {
    console.error('归档ID不存在')
    return
  }
  
  if (confirm(`确定要删除归档"${archiveName}"吗？\n\n删除归档后，归档中的笔记本身不会被删除，只是移除归档关系。`)) {
    deleteArchiveAction(archiveId)
  }
}

// 删除归档
const deleteArchiveAction = async (archiveId: number) => {
  try {
    console.log('开始删除归档，ID:', archiveId)
    console.log('删除前归档数量:', archives.value.length)
    
    const response = await deleteArchive(archiveId)
    console.log('删除响应:', response)
    
    if (response.code === 0 || response.code === 200) {
      // 立即从归档列表中移除（确保界面立即更新）
      const oldLength = archives.value.length
      archives.value = archives.value.filter(a => a.id !== archiveId)
      console.log('删除后归档数量:', archives.value.length, '预期删除:', oldLength - archives.value.length)
      
      // 从展开列表中移除
      const index = expandedArchives.value.indexOf(archiveId)
      if (index > -1) {
        expandedArchives.value.splice(index, 1)
      }
      
      // 从笔记映射中移除
      delete archiveNotesMap.value[archiveId]
      
      // 更新笔记-归档映射
      Object.keys(noteArchiveMap.value).forEach(noteId => {
        noteArchiveMap.value[Number(noteId)] = noteArchiveMap.value[Number(noteId)].filter(
          (a: any) => a.archiveId !== archiveId
        )
      })
      
      // 使用 setTimeout 延迟显示 alert，避免阻塞 UI 更新
      setTimeout(() => {
        alert('归档删除成功')
      }, 100)
      
      // 重新加载归档列表（确保数据同步）
      await loadArchiveList()
    } else {
      alert(response.message || '删除归档失败')
    }
  } catch (error) {
    console.error('删除归档失败:', error)
    alert('删除归档失败，请重试')
  }
}

// 加载归档的笔记列表
const loadArchiveNotes = async (archiveId: number) => {
  try {
    console.log('正在加载归档笔记，归档ID:', archiveId)
    const response = await getArchiveDetail(archiveId)
    console.log('归档详情响应:', response)
    
    if (response && response.data) {
      const notes = response.data.notes || []
      console.log('归档包含的笔记:', notes)
      archiveNotesMap.value[archiveId] = notes
      
      // 更新笔记-归档映射
      notes.forEach((note: any) => {
        if (!noteArchiveMap.value[note.noteId]) {
          noteArchiveMap.value[note.noteId] = []
        }
        // 避免重复添加
        const exists = noteArchiveMap.value[note.noteId].some((a: any) => a.archiveId === archiveId)
        if (!exists) {
          noteArchiveMap.value[note.noteId].push({
            archiveId: archiveId,
            archiveName: archives.value.find(a => a.id === archiveId)?.archiveName,
            relationId: note.relationId
          })
        }
      })
      console.log('archiveNotesMap 已更新:', archiveNotesMap.value)
    } else {
      console.warn('响应数据为空')
      archiveNotesMap.value[archiveId] = []
    }
  } catch (error) {
    console.error('加载归档笔记失败:', error)
    archiveNotesMap.value[archiveId] = []
  }
}

// 获取归档的笔记列表
const getArchiveNotes = (archiveId: number | undefined) => {
  if (!archiveId) {
    console.warn('getArchiveNotes: 归档ID为空')
    return []
  }
  const notes = archiveNotesMap.value[archiveId] || []
  console.log(`getArchiveNotes(${archiveId}):`, notes)
  return notes
}

// 获取标签数量
const getTagsCount = (tags: string | undefined) => {
  if (!tags) return 0
  try {
    const tagArray = JSON.parse(tags)
    return Array.isArray(tagArray) ? tagArray.length : 0
  } catch {
    return 0
  }
}

// 归档视角：点击"添加笔记"按钮
const addNotesToArchive = (archiveId: number) => {
  targetArchiveId.value = archiveId
  selectedNoteIdsForBatch.value = []
  showNoteSelectDialog.value = true
}

// 切换笔记选择状态
const toggleNoteSelection = (noteId: number) => {
  const index = selectedNoteIdsForBatch.value.indexOf(noteId)
  if (index > -1) {
    selectedNoteIdsForBatch.value.splice(index, 1)
  } else {
    selectedNoteIdsForBatch.value.push(noteId)
  }
}

// 确认添加笔记到归档
const confirmAddNotesToArchive = async () => {
  if (!targetArchiveId.value || selectedNoteIdsForBatch.value.length === 0) {
    return
  }
  
  try {
    let successCount = 0
    let errorCount = 0
    
    for (const noteId of selectedNoteIdsForBatch.value) {
      try {
        const response = await addNoteToArchive({
          archiveId: targetArchiveId.value,
          noteId: noteId
        })
        
        if (response.data && response.data.id) {
          successCount++
          
          // 更新 noteArchiveMap（笔记视角需要）
          if (!noteArchiveMap.value[noteId]) {
            noteArchiveMap.value[noteId] = []
          }
          const archive = archives.value.find(a => a.id === targetArchiveId.value)
          if (archive) {
            noteArchiveMap.value[noteId].push({
              archiveId: targetArchiveId.value,
              archiveName: archive.archiveName,
              relationId: response.data.id
            })
          }
        }
      } catch (error: any) {
        // 如果是已存在的错误，也算成功
        if (error.response?.data?.message?.includes('已在此归档中')) {
          successCount++
        } else {
          errorCount++
          console.error(`添加笔记${noteId}失败:`, error)
        }
      }
    }
    
    if (errorCount > 0) {
      alert(`添加完成：成功 ${successCount} 条，失败 ${errorCount} 条`)
    } else {
      alert(`成功添加 ${successCount} 条笔记到归档！`)
    }
    
    // 关闭弹窗
    showNoteSelectDialog.value = false
    selectedNoteIdsForBatch.value = []
    
    // 重新加载归档列表（更新笔记数量）
    await loadArchiveList()
    
    // 如果当前归档已展开，重新加载其笔记列表
    if (expandedArchives.value.includes(targetArchiveId.value)) {
      await loadArchiveNotes(targetArchiveId.value)
    }
  } catch (error) {
    console.error('添加笔记到归档失败:', error)
    alert('添加失败')
  }
}

// 笔记视角：快速添加笔记到归档
const quickAddNoteToArchive = (noteId: number) => {
  selectedNoteIdsForBatch.value = [noteId]
  showArchiveListForBatch.value = true
}

// 从归档中移除笔记
const removeNoteFromArchiveAction = async (relationId: number, archiveId: number) => {
  if (!confirm('确定要从归档中移除这条笔记吗？')) {
    return
  }
  
  try {
    await removeNoteFromArchive(relationId, archiveId)
    alert('移除成功')
    
    // 从 noteArchiveMap 中移除（用于笔记视角）
    Object.keys(noteArchiveMap.value).forEach(noteId => {
      const archives = noteArchiveMap.value[Number(noteId)]
      if (archives) {
        const index = archives.findIndex(a => a.relationId === relationId)
        if (index > -1) {
          archives.splice(index, 1)
          // 如果该笔记没有任何归档了，也可以保留空数组
        }
      }
    })
    
    // 重新加载归档笔记（用于归档视角）
    if (expandedArchives.value.includes(archiveId)) {
      await loadArchiveNotes(archiveId)
    }
    
    // 重新加载归档列表（更新笔记数量）
    await loadArchiveList()
  } catch (error) {
    console.error('移除笔记失败:', error)
    alert('移除失败')
  }
}

// 过滤归档列表（用于批量添加）
const filteredArchivesForBatch = computed(() => {
  return archives.value.filter(archive => {
    const matchName = !batchArchiveSearch.value || 
      archive.archiveName.toLowerCase().includes(batchArchiveSearch.value.toLowerCase())
    const matchType = !batchArchiveTypeFilter.value || archive.archiveType === batchArchiveTypeFilter.value
    return matchName && matchType
  })
})

// 打开创建归档弹窗（批量添加模式）
const openCreateArchiveForBatch = () => {
  showArchiveListForBatch.value = false
  batchArchiveForm.archiveName = ''
  batchArchiveForm.archiveType = ''
  batchArchiveForm.description = ''
  batchArchiveForm.sourceInfo = ''
  showCreateArchiveForBatchDialog.value = true
}

// 关闭创建归档弹窗
const closeCreateArchiveForBatch = () => {
  showCreateArchiveForBatchDialog.value = false
}

// 创建归档并添加笔记
const createArchiveAndAddNotes = async () => {
  try {
    if (!batchArchiveForm.archiveName.trim()) {
      alert('请输入归档名称')
      return
    }

    // 创建归档
    const response = await createArchive(batchArchiveForm)
    if (response.data && response.data.id) {
      const archiveId = response.data.id
      const archiveName = batchArchiveForm.archiveName
      
      // 批量添加笔记到归档
      let successCount = 0
      for (const noteId of selectedNoteIdsForBatch.value) {
        try {
          const addResponse = await addNoteToArchive({
            archiveId,
            noteId
          })
          successCount++
          
          // 立即更新 noteArchiveMap（用于笔记视角）
          if (!noteArchiveMap.value[noteId]) {
            noteArchiveMap.value[noteId] = []
          }
          // 从响应中获取 relationId（后端返回的data就是relationId）
          const relationId = addResponse?.data || 0
          noteArchiveMap.value[noteId].push({
            archiveId: archiveId,
            archiveName: archiveName,
            relationId: relationId
          })
        } catch (error) {
          console.error(`添加笔记${noteId}失败:`, error)
        }
      }
      
      alert(`成功创建归档并添加 ${successCount}/${selectedNoteIdsForBatch.value.length} 条笔记`)
      
      // 关闭弹窗并重置
      closeCreateArchiveForBatch()
      await loadArchiveList()
    }
  } catch (error) {
    console.error('创建归档失败:', error)
    alert('创建归档失败')
  }
}

// 将选中的笔记添加到指定归档
const addSelectedNotesToArchive = async (archiveId: number) => {
  try {
    let successCount = 0
    let errorCount = 0
    const archive = archives.value.find(a => a.id === archiveId)
    
    for (const noteId of selectedNoteIdsForBatch.value) {
      try {
        const response = await addNoteToArchive({
          archiveId,
          noteId
        })
        successCount++
        
        // 立即更新 noteArchiveMap（用于笔记视角）
        if (archive) {
          if (!noteArchiveMap.value[noteId]) {
            noteArchiveMap.value[noteId] = []
          }
          // 检查是否已存在
          const exists = noteArchiveMap.value[noteId].some(a => a.archiveId === archiveId)
          if (!exists) {
            // 从响应中获取 relationId（后端返回的data就是relationId）
            const relationId = response?.data || 0
            noteArchiveMap.value[noteId].push({
              archiveId: archiveId,
              archiveName: archive.archiveName,
              relationId: relationId
            })
          }
        }
      } catch (error: any) {
        // 如果是已存在的错误，也算成功
        if (error.response?.data?.message?.includes('已在此归档中')) {
          successCount++
        } else {
          errorCount++
          console.error(`添加笔记${noteId}失败:`, error)
        }
      }
    }
    
    if (errorCount > 0) {
      alert(`添加完成：成功 ${successCount} 条，失败 ${errorCount} 条`)
    } else {
      alert(`成功添加 ${successCount} 条笔记到归档`)
    }
    
    // 关闭弹窗并重置
    showArchiveListForBatch.value = false
    await loadArchiveList()
    // 如果当前归档已展开，重新加载其笔记列表
    if (expandedArchives.value.includes(archiveId)) {
      await loadArchiveNotes(archiveId)
    }
  } catch (error) {
    console.error('批量添加笔记失败:', error)
    alert('批量添加失败')
  }
}

// 加载所有笔记的归档映射（用于笔记视角）
const loadAllNoteArchiveMap = async () => {
  try {
    noteArchiveMap.value = {}
    for (const archive of archives.value) {
      await loadArchiveNotes(archive.id!)
    }
  } catch (error) {
    console.error('加载笔记归档映射失败:', error)
  }
}

// 监听归档管理界面打开
watch(showArchiveManager, async (newVal) => {
  if (newVal) {
    await loadArchiveList()
    await loadAllNoteArchiveMap()
  }
})

// 监听选择归档弹窗打开，加载归档列表
watch(showArchiveListForBatch, (newVal) => {
  if (newVal) {
    loadArchiveList()
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

/* 滑动展开动画 */
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.2s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}

/* 背景遮罩动画 */
.modal-backdrop-enter-active,
.modal-backdrop-leave-active {
  transition: opacity 0.3s ease-in-out;
}

.modal-backdrop-enter-from,
.modal-backdrop-leave-to {
  opacity: 0;
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

/* 按钮悬停效果 */
.btn-hover {
  transition: all 0.2s ease-in-out;
}

.btn-hover:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* 表单输入框焦点效果 */
.form-input:focus {
  transform: scale(1.01);
  transition: transform 0.2s ease-in-out;
}

/* 配置管理折叠动画 */
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

/* 管理笔记渐入渐出动画 */
.manage-notes-fade-enter-active,
.manage-notes-fade-leave-active {
  transition: all 0.3s ease-in-out;
}

.manage-notes-fade-enter-from {
  opacity: 0;
  transform: translateY(-20px);
}

.manage-notes-fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

/* 自定义滚动条样式 */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style> 