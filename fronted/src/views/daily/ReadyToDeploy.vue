<template>
  <section id="ready-to-deploy" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-6">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-3xl font-bold text-gray-900 flex items-center">
            <i class="fas fa-flag-checkered text-red-600 mr-3"></i>
            整装待发
          </h1>
          <p class="text-gray-600 mt-2">所有进行中的战争行动与外交行动项目</p>
        </div>
      </div>
    </div>

    <!-- 分类标签和搜索 -->
    <div class="mb-6 space-y-4">
      <!-- 分类标签 -->
      <div class="flex space-x-4">
        <button 
          @click="currentTab = 'all'"
          :class="currentTab === 'all' ? 'bg-gray-900 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'"
          class="px-6 py-2 rounded-lg font-medium transition-colors"
        >
          全部 ({{ allProjects.length }})
        </button>
        <button 
          @click="currentTab = 'war'"
          :class="currentTab === 'war' ? 'bg-red-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'"
          class="px-6 py-2 rounded-lg font-medium transition-colors flex items-center"
        >
          <i class="fas fa-swords mr-2"></i>
          战争行动 ({{ warProjects.length }})
        </button>
        <button 
          @click="currentTab = 'diplomatic'"
          :class="currentTab === 'diplomatic' ? 'bg-blue-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'"
          class="px-6 py-2 rounded-lg font-medium transition-colors flex items-center"
        >
          <i class="fas fa-handshake mr-2"></i>
          外交行动 ({{ diplomaticProjects.length }})
        </button>
      </div>

      <!-- 搜索框 -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="relative">
          <input 
            v-model="projectSearchQuery" 
            type="text" 
            placeholder="搜索项目名称或描述..."
            class="w-full px-4 py-3 pl-12 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
          >
          <i class="fas fa-search absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 text-lg"></i>
          <!-- 清空按钮 -->
          <button 
            v-if="projectSearchQuery"
            @click="projectSearchQuery = ''"
            class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600"
          >
            <i class="fas fa-times-circle"></i>
          </button>
        </div>
        <!-- 搜索结果统计 -->
        <div v-if="projectSearchQuery" class="mt-2 text-sm text-gray-500">
          找到 {{ displayProjects.length }} 个项目
        </div>
      </div>
    </div>

    <!-- 项目列表 -->
    <div class="bg-white rounded-xl shadow-soft p-6">
      <!-- 加载中 -->
      <div v-if="isLoading" class="text-center py-16">
        <i class="fas fa-spinner fa-spin text-6xl text-blue-400 mb-4"></i>
        <p class="text-gray-500 text-lg">加载中...</p>
      </div>

      <!-- 空状态 -->
      <div v-else-if="displayProjects.length === 0" class="text-center py-16">
        <i class="fas fa-check-circle text-6xl text-green-300 mb-4"></i>
        <p class="text-gray-500 text-lg">暂无进行中的项目</p>
        <p class="text-gray-400 text-sm mt-2">所有项目均已完成，或可在战争行动、外交行动中创建新项目</p>
    </div>

      <!-- 项目卡片列表 -->
      <div v-else class="space-y-4 max-h-[600px] overflow-y-auto pr-2">
        <div 
          v-for="project in displayProjects" 
          :key="project.id"
          class="border rounded-lg p-5 hover:shadow-md transition-shadow"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <!-- 项目头部 -->
              <div class="flex items-center space-x-3 mb-3">
                <span 
                  :class="project.type === 'war' ? 'bg-red-100 text-red-700' : 'bg-blue-100 text-blue-700'"
                  class="px-3 py-1 rounded-full text-sm font-medium"
                >
                  <i :class="project.type === 'war' ? 'fas fa-shield-alt' : 'fas fa-handshake'" class="mr-1"></i>
                  {{ project.type === 'war' ? '战争行动' : '外交行动' }}
                </span>
                <h3 class="text-xl font-bold text-gray-900">{{ project.name }}</h3>
                <span 
                  :class="getStatusClass(project.status)"
                  class="px-2 py-1 rounded text-xs font-medium"
                >
                  {{ getStatusText(project.status) }}
                </span>
              </div>

              <!-- 项目信息 -->
              <div class="grid grid-cols-2 md:grid-cols-3 gap-4 text-sm mb-4">
                <div>
                  <span class="text-gray-500">关联笔记：</span>
                  <span class="font-semibold text-gray-900">{{ project.noteCount || 0 }} 条</span>
                </div>
                <div>
                  <span class="text-gray-500">创建时间：</span>
                  <span class="font-semibold text-gray-900">{{ project.createDate || '-' }}</span>
                </div>
                <div>
                  <span class="text-gray-500">更新时间：</span>
                  <span class="font-semibold text-gray-900">{{ project.updateDate || '-' }}</span>
                </div>
              </div>

              <!-- 项目描述 -->
              <p v-if="project.description" class="text-gray-600 text-sm mb-3">
                {{ project.description }}
              </p>
            </div>

            <!-- 操作按钮 -->
            <div class="ml-6 flex flex-col space-y-3">
              <button 
                @click="viewProjectNotes(project)"
                class="px-6 py-3 bg-gradient-to-r from-blue-600 to-indigo-600 text-white rounded-lg hover:from-blue-700 hover:to-indigo-700 transition-all shadow-md hover:shadow-lg font-medium whitespace-nowrap"
              >
                <i class="fas fa-book-open mr-2"></i>
                回顾笔记
              </button>
              <button 
                @click="addSprintNote(project)"
                class="px-6 py-3 bg-gradient-to-r from-green-600 to-emerald-600 text-white rounded-lg hover:from-green-700 hover:to-emerald-700 transition-all shadow-md hover:shadow-lg font-medium whitespace-nowrap"
              >
                <i class="fas fa-plus mr-2"></i>
                新增记忆
              </button>
              <button 
                @click="manageSprintNotes(project)"
                class="px-6 py-3 bg-gradient-to-r from-yellow-600 to-orange-600 text-white rounded-lg hover:from-yellow-700 hover:to-orange-700 transition-all shadow-md hover:shadow-lg font-medium whitespace-nowrap"
              >
                <i class="fas fa-edit mr-2"></i>
                管理记忆
              </button>
              <button 
                @click="reviewSprintNotes(project)"
                class="px-6 py-3 bg-gradient-to-r from-purple-600 to-indigo-600 text-white rounded-lg hover:from-purple-700 hover:to-indigo-700 transition-all shadow-md hover:shadow-lg font-medium whitespace-nowrap"
              >
                <i class="fas fa-book-reader mr-2"></i>
                回顾记忆
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 底部说明 -->
    <div class="mt-6 bg-blue-50 rounded-lg p-4 border border-blue-200">
      <div class="flex items-start">
        <i class="fas fa-info-circle text-blue-600 mt-1 mr-3"></i>
        <div class="text-sm text-gray-700">
          <p class="font-semibold mb-1">功能说明</p>
          <p>此页面展示所有进行中的战争行动与外交行动项目。出征前在此页面反复回顾项目的<strong>关联笔记</strong>和<strong>冲刺记忆</strong>，确保准备充分，做好最后的冲刺准备。</p>
            </div>
          </div>
        </div>

    <!-- 笔记回顾弹窗 -->
    <div 
      v-if="showNotesDialog" 
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeNotesDialog"
    >
      <div class="bg-white rounded-xl shadow-2xl max-w-4xl w-full mx-4 max-h-[80vh] overflow-hidden">
        <!-- 弹窗头部 -->
        <div class="bg-gradient-to-r from-blue-600 to-indigo-600 text-white p-6 flex items-center justify-between">
          <div>
            <h3 class="text-2xl font-bold">{{ selectedProject?.name }}</h3>
            <p class="text-blue-100 mt-1">关联笔记回顾</p>
          </div>
          <button @click="closeNotesDialog" class="text-white hover:bg-white/20 rounded-lg p-2 transition-colors">
            <i class="fas fa-times text-2xl"></i>
          </button>
        </div>

        <!-- 笔记列表 -->
        <div class="p-6 overflow-y-auto" style="max-height: calc(80vh - 180px)">
          <div v-if="projectNotes.length === 0" class="text-center py-12">
            <i class="fas fa-inbox text-6xl text-gray-300 mb-4"></i>
            <p class="text-gray-500">暂无关联笔记</p>
            <p class="text-gray-400 text-sm mt-2">请在项目执行中关联笔记</p>
          </div>

          <div v-else class="space-y-4">
            <div 
              v-for="note in projectNotes" 
              :key="note.id"
              class="border-2 rounded-lg p-4 hover:shadow-lg transition-all cursor-pointer group"
              :class="getNoteCardBorderClass(note.type)"
              @click="openNoteDetail(note)"
            >
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center space-x-3 mb-2 flex-wrap">
                    <span 
                      class="px-3 py-1 rounded-full text-xs font-semibold flex items-center"
                      :class="getNoteTypeTagClass(note.type)"
                    >
                      <i :class="getNoteTypeIconClass(note.type)" class="mr-1.5"></i>
                      {{ getNoteTypeText(note.type) }}
                    </span>
                    <h4 class="text-lg font-bold text-gray-900">{{ note.title }}</h4>
                  </div>
                  <!-- 标签显示 -->
                  <div v-if="note.tags" class="flex items-center space-x-1 mb-2 flex-wrap">
                    <i class="fas fa-tags text-gray-400 text-xs mr-1"></i>
                    <span 
                      v-for="(tag, index) in parseTagsArray(note.tags)" 
                      :key="index"
                      class="px-2 py-0.5 bg-gray-100 text-gray-600 rounded text-xs"
                    >
                      {{ tag }}
                    </span>
                  </div>
                  <p v-if="note.summary" class="text-gray-600 text-sm line-clamp-2 leading-relaxed">{{ note.summary }}</p>
            </div>
                <i class="fas fa-chevron-right text-gray-400 ml-4 group-hover:text-gray-600 transition-colors"></i>
          </div>
        </div>
      </div>
    </div>

        <!-- 底部操作 -->
        <div class="border-t border-gray-200 p-4 bg-gray-50 flex justify-end">
          <button 
            @click="closeNotesDialog"
            class="px-6 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors"
          >
            关闭
          </button>
        </div>
          </div>
        </div>

    <!-- 笔记详情弹窗 -->
    <div 
      v-if="showNoteDetailDialog" 
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-[60]"
      @click.self="closeNoteDetailDialog"
    >
      <div class="bg-white rounded-xl shadow-2xl max-w-4xl w-full mx-4 max-h-[85vh] overflow-hidden flex flex-col">
        <!-- 笔记头部 -->
        <div class="bg-gradient-to-r from-blue-600 to-indigo-600 text-white p-6 flex-shrink-0">
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center space-x-2 mb-2 flex-wrap">
                <span class="px-3 py-1 bg-white/20 rounded-full text-sm font-medium">
                  {{ getNoteTypeText(selectedNoteDetail?.type) }}
                </span>
                <template v-if="selectedNoteDetail?.tags">
                  <i class="fas fa-tags text-blue-200 ml-2"></i>
                  <span 
                    v-for="(tag, index) in parseTagsArray(selectedNoteDetail.tags)" 
                    :key="index"
                    class="px-2 py-1 bg-white/30 text-white rounded text-xs font-medium"
                  >
                    {{ tag }}
                  </span>
                </template>
              </div>
              <h3 class="text-2xl font-bold">{{ selectedNoteDetail?.title }}</h3>
              <p v-if="selectedNoteDetail?.summary" class="text-blue-100 mt-2">{{ selectedNoteDetail.summary }}</p>
            </div>
            <button @click="closeNoteDetailDialog" class="text-white hover:bg-white/20 rounded-lg p-2 transition-colors">
              <i class="fas fa-times text-2xl"></i>
            </button>
          </div>
        </div>

        <!-- 笔记内容 -->
        <div class="p-6 overflow-y-auto flex-1">
          <!-- Tab 切换（根据笔记类型显示） -->
          <div v-if="noteDetailTabs.length > 0" class="flex space-x-2 mb-4 flex-wrap">
            <button 
              v-for="tab in noteDetailTabs" 
              :key="tab.value" 
              @click="currentNoteTab = tab.value"
              :class="[
                'px-4 py-2 rounded-lg font-medium transition-colors',
                currentNoteTab === tab.value 
                  ? 'bg-blue-600 text-white' 
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              ]"
            >
              {{ tab.label }}
            </button>
          </div>

          <!-- 笔记详细内容（根据类型和Tab显示） -->
          <div class="note-content bg-gray-50 p-4 rounded-lg min-h-[200px]">
            <!-- 碎片笔记/框架笔记：只有一个 content 字段 -->
            <div v-if="selectedNoteDetail?.type === 'fragment' || selectedNoteDetail?.type === 'framework'" 
                 v-html="selectedNoteDetail?.content || '暂无内容'">
            </div>

            <!-- 求学笔记：6个Tab -->
            <template v-else-if="selectedNoteDetail?.type === 'study'">
              <div v-if="currentNoteTab === 'coreConcept'" v-html="selectedNoteDetail?.coreConcept || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'mechanism'" v-html="selectedNoteDetail?.mechanism || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'applicationCase'" v-html="selectedNoteDetail?.applicationCase || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'extension'" v-html="selectedNoteDetail?.extension || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'commonMistake'" v-html="selectedNoteDetail?.commonMistake || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'reflection'" v-html="selectedNoteDetail?.reflection || '暂无内容'"></div>
            </template>

            <!-- 拓展笔记：2个Tab -->
            <template v-else-if="selectedNoteDetail?.type === 'expansion'">
              <div v-if="currentNoteTab === 'original'" v-html="selectedNoteDetail?.originalText || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'understanding'" v-html="selectedNoteDetail?.understanding || '暂无内容'"></div>
            </template>

            <!-- 背诵笔记：3个Tab -->
            <template v-else-if="selectedNoteDetail?.type === 'memorization'">
              <div v-if="currentNoteTab === 'original'" v-html="selectedNoteDetail?.originalText || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'explanation'" v-html="selectedNoteDetail?.explanation || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'cue'" v-html="selectedNoteDetail?.cue || '暂无内容'"></div>
            </template>

            <!-- 刷题笔记：3个Tab -->
            <template v-else-if="selectedNoteDetail?.type === 'exercise'">
              <div v-if="currentNoteTab === 'question'" v-html="selectedNoteDetail?.questionDescription || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'analysis'" v-html="selectedNoteDetail?.analysis || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'answer'" v-html="selectedNoteDetail?.referenceAnswer || '暂无内容'"></div>
            </template>

            <!-- 实战笔记：3个Tab -->
            <template v-else-if="selectedNoteDetail?.type === 'practical'">
              <div v-if="currentNoteTab === 'requirement'" v-html="selectedNoteDetail?.requirementDescription || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'design'" v-html="selectedNoteDetail?.designThinking || '暂无内容'"></div>
              <div v-else-if="currentNoteTab === 'reference'" v-html="selectedNoteDetail?.referenceDesign || '暂无内容'"></div>
            </template>

            <!-- 默认：显示 content -->
            <div v-else v-html="selectedNoteDetail?.content || '暂无内容'"></div>
          </div>

          <!-- 笔记特有字段显示 -->
          <div class="mt-4 space-y-2">
            <!-- 求学笔记 - 显示课程 -->
            <div v-if="selectedNoteDetail?.type === 'study' && selectedNoteDetail?.studySubject" 
                 class="flex items-center space-x-2">
              <span class="text-sm font-medium text-gray-700">课程：</span>
              <span class="bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.studySubject }}
              </span>
            </div>

            <!-- 背诵笔记 - 显示项目 -->
            <div v-if="selectedNoteDetail?.type === 'memorization' && selectedNoteDetail?.project" 
                 class="flex items-center space-x-2">
              <span class="text-sm font-medium text-gray-700">项目：</span>
              <span class="bg-amber-100 text-amber-700 px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.project }}
              </span>
            </div>

            <!-- 刷题笔记 - 显示知识点和难度 -->
            <div v-if="selectedNoteDetail?.type === 'exercise'" class="flex items-center space-x-2 flex-wrap">
              <span v-if="selectedNoteDetail?.exerciseKnowledge" 
                    class="bg-red-100 text-red-700 px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.exerciseKnowledge }}
              </span>
              <span v-if="selectedNoteDetail?.exerciseDifficulty" 
                    :class="{
                      'bg-green-100 text-green-700': selectedNoteDetail.exerciseDifficulty === '简单',
                      'bg-yellow-100 text-yellow-700': selectedNoteDetail.exerciseDifficulty === '中等',
                      'bg-red-100 text-red-700': selectedNoteDetail.exerciseDifficulty === '困难'
                    }"
                    class="px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.exerciseDifficulty }}
              </span>
            </div>

            <!-- 实战笔记 - 显示技术栈 -->
            <div v-if="selectedNoteDetail?.type === 'practical' && selectedNoteDetail?.techStack" 
                 class="flex items-center space-x-2 flex-wrap">
              <span class="text-sm font-medium text-gray-700">技术栈：</span>
              <span class="bg-indigo-100 text-indigo-700 px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.techStack }}
              </span>
            </div>

            <!-- 碎片笔记 - 显示分类和重要性 -->
            <div v-if="selectedNoteDetail?.type === 'fragment'" class="flex items-center space-x-2 flex-wrap">
              <span v-if="selectedNoteDetail?.fragmentCategory" 
                    class="bg-purple-100 text-purple-700 px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.fragmentCategory }}
              </span>
              <span v-if="selectedNoteDetail?.importance" 
                    :class="{
                      'bg-red-100 text-red-700': selectedNoteDetail.importance === '非常重要' || selectedNoteDetail.importance === '重要',
                      'bg-yellow-100 text-yellow-700': selectedNoteDetail.importance === '一般',
                      'bg-gray-100 text-gray-700': selectedNoteDetail.importance === '低'
                    }"
                    class="px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.importance }}
              </span>
            </div>

            <!-- 框架笔记 - 显示学科 -->
            <div v-if="selectedNoteDetail?.type === 'framework' && selectedNoteDetail?.subjectType" 
                 class="flex items-center space-x-2">
              <span class="text-sm font-medium text-gray-700">学科：</span>
              <span class="bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-sm">
                {{ selectedNoteDetail.subjectType }}
              </span>
          </div>
        </div>

          <!-- 笔记元信息 -->
          <div class="mt-6 pt-6 border-t border-gray-200">
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm">
              <div>
                <span class="text-gray-500">创建时间：</span>
                <span class="font-medium text-gray-900">{{ formatDate(selectedNoteDetail?.recCreateTime) }}</span>
              </div>
              <div>
                <span class="text-gray-500">更新时间：</span>
                <span class="font-medium text-gray-900">{{ formatDate(selectedNoteDetail?.recReviseTime) }}</span>
              </div>
              <div>
                <span class="text-gray-500">创建人：</span>
                <span class="font-medium text-gray-900">{{ selectedNoteDetail?.recCreator || '-' }}</span>
              </div>
              <div>
                <span class="text-gray-500">版本：</span>
                <span class="font-medium text-gray-900">v{{ selectedNoteDetail?.recVersion || 1 }}</span>
          </div>
        </div>
      </div>
    </div>

        <!-- 底部按钮 -->
        <div class="border-t border-gray-200 p-4 bg-gray-50 flex justify-end">
          <button 
            @click="closeNoteDetailDialog"
            class="px-6 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors"
          >
            关闭
          </button>
        </div>
          </div>
        </div>

    <!-- 添加/编辑冲刺记忆表单弹窗 -->
    <div 
      v-if="showAddSprintNoteDialog" 
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-[60]"
      @click.self="closeAddSprintNoteDialog"
    >
      <div class="bg-white rounded-xl shadow-2xl max-w-3xl w-full mx-4 max-h-[90vh] overflow-y-auto">
        <!-- 表单头部 -->
        <div class="bg-gradient-to-r from-green-600 to-emerald-600 text-white p-6 sticky top-0">
          <h3 class="text-2xl font-bold">{{ isEditingSprintNote ? '编辑冲刺记忆' : '新增冲刺记忆' }}</h3>
          <p class="text-green-100 mt-1">{{ selectedProject?.name }}</p>
        </div>

        <!-- 表单内容 -->
        <div class="p-6">
          <form @submit.prevent="saveSprintNote">
            <!-- 标题 -->
            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">标题 <span class="text-red-500">*</span></label>
              <input 
                v-model="sprintNoteForm.title" 
                type="text" 
                placeholder="请输入标题"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                required
              >
            </div>

            <!-- 摘要 -->
            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">摘要</label>
              <textarea 
                v-model="sprintNoteForm.summary" 
                rows="2" 
                placeholder="请输入摘要"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              ></textarea>
            </div>

            <!-- 学科项目 -->
            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">学科项目</label>
              <input 
                v-model="sprintNoteForm.project" 
                type="text" 
                placeholder="请输入学科项目"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              >
            </div>

            <!-- 原文内容 -->
            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">原文内容</label>
              <textarea 
                v-model="sprintNoteForm.originalText" 
                rows="5" 
                placeholder="请输入需要背诵的原文内容"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              ></textarea>
            </div>

            <!-- 解释说明 -->
            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">解释说明</label>
              <textarea 
                v-model="sprintNoteForm.explanation" 
                rows="4" 
                placeholder="请输入对原文的解释说明"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              ></textarea>
            </div>

            <!-- 记忆提示词 -->
            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">记忆提示词</label>
              <textarea 
                v-model="sprintNoteForm.cue" 
                rows="3" 
                placeholder="请输入记忆提示词或口诀"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              ></textarea>
            </div>

            <!-- 按钮 -->
            <div class="flex justify-end space-x-3">
              <button 
                type="button"
                @click="closeAddSprintNoteDialog"
                class="px-6 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200"
              >
                取消
              </button>
              <button 
                type="submit"
                class="px-6 py-2 bg-gradient-to-r from-green-600 to-emerald-600 text-white rounded-lg hover:from-green-700 hover:to-emerald-700"
              >
                <i class="fas fa-check mr-2"></i>
                保存
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- 管理冲刺记忆弹窗 -->
    <div 
      v-if="showManageSprintNotesDialog" 
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeManageSprintNotesDialog"
    >
      <div class="bg-white rounded-xl shadow-2xl max-w-4xl w-full mx-4 max-h-[80vh] overflow-hidden">
        <!-- 弹窗头部 -->
        <div class="bg-gradient-to-r from-yellow-600 to-orange-600 text-white p-6">
          <h3 class="text-2xl font-bold">管理冲刺记忆</h3>
          <p class="text-yellow-100 mt-1">{{ selectedProject?.name }}</p>
        </div>

        <!-- 记忆列表 -->
        <div class="p-6 overflow-y-auto" style="max-height: calc(80vh - 200px)">
          <div v-if="projectSprintNotes.length === 0" class="text-center py-12">
            <i class="fas fa-inbox text-6xl text-gray-300 mb-4"></i>
            <p class="text-gray-500">暂无冲刺记忆</p>
          </div>

          <div v-else class="space-y-3">
            <div 
              v-for="note in projectSprintNotes" 
              :key="note.id"
              class="border rounded-lg p-4 hover:shadow-md transition"
            >
              <div class="flex justify-between items-start">
                <div class="flex-1">
                  <h4 class="font-bold text-gray-900 mb-1">{{ note.title }}</h4>
                  <p v-if="note.summary" class="text-sm text-gray-600 mb-2">{{ note.summary }}</p>
                  <div class="flex items-center space-x-2 text-xs text-gray-500">
                    <span v-if="note.project">项目：{{ note.project }}</span>
                    <span>{{ formatDate(note.recCreateTime) }}</span>
                  </div>
                </div>
                <div class="flex space-x-2">
                  <button 
                    @click="editSprintNote(note)"
                    class="px-3 py-1 bg-blue-100 text-blue-700 rounded hover:bg-blue-200"
                  >
                    <i class="fas fa-edit"></i> 编辑
                  </button>
                  <button 
                    @click="deleteSprintNote(note)"
                    class="px-3 py-1 bg-red-100 text-red-700 rounded hover:bg-red-200"
                  >
                    <i class="fas fa-trash"></i> 删除
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 底部按钮 -->
        <div class="border-t border-gray-200 p-4 bg-gray-50 flex justify-end">
          <button 
            @click="closeManageSprintNotesDialog"
            class="px-6 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700"
          >
            关闭
          </button>
        </div>
      </div>
    </div>

    <!-- 回顾冲刺记忆弹窗 -->
    <div 
      v-if="showReviewSprintNotesDialog" 
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeReviewSprintNotesDialog"
    >
      <div class="bg-white rounded-xl shadow-2xl max-w-6xl w-full mx-4 max-h-[90vh] overflow-hidden">
        <!-- 弹窗头部 -->
        <div class="bg-gradient-to-r from-purple-600 to-indigo-600 text-white p-6">
          <h3 class="text-2xl font-bold">回顾冲刺记忆</h3>
          <p class="text-purple-100 mt-1">{{ selectedProject?.name }} - {{ filteredSprintNotes.length }} 条记忆</p>
        </div>

        <!-- 搜索框 -->
        <div class="p-6 border-b border-gray-200">
          <div class="relative">
            <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            <input 
              v-model="sprintNoteSearchQuery"
              type="text" 
              placeholder="搜索笔记标题或内容..."
              class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500"
            >
          </div>
        </div>

        <!-- 记忆列表 -->
        <div class="p-6 overflow-y-auto" style="max-height: calc(90vh - 300px)">
          <div v-if="filteredSprintNotes.length === 0" class="text-center py-12">
            <i class="fas fa-search text-6xl text-gray-300 mb-4"></i>
            <p class="text-gray-500">未找到匹配的记忆</p>
          </div>

          <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div 
              v-for="note in filteredSprintNotes" 
              :key="note.id"
              class="border-2 border-gray-200 rounded-lg p-4 hover:border-purple-400 hover:shadow-lg cursor-pointer transition"
              @click="viewSprintNoteDetail(note)"
            >
              <h4 class="font-bold text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h4>
              <p v-if="note.summary" class="text-sm text-gray-600 mb-2 line-clamp-2">{{ note.summary }}</p>
              <div class="flex items-center space-x-2 text-xs text-gray-500">
                <span v-if="note.project" class="bg-purple-100 text-purple-700 px-2 py-1 rounded">
                  {{ note.project }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- 底部按钮 -->
        <div class="border-t border-gray-200 p-4 bg-gray-50 flex justify-end">
          <button 
            @click="closeReviewSprintNotesDialog"
            class="px-6 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700"
          >
            关闭
          </button>
        </div>
      </div>
    </div>

    <!-- 冲刺记忆详情弹窗 -->
    <div 
      v-if="showSprintNoteDetailDialog" 
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-[60]"
      @click.self="closeSprintNoteDetailDialog"
    >
      <div class="bg-white rounded-xl shadow-2xl max-w-4xl w-full mx-4 max-h-[85vh] overflow-hidden flex flex-col">
        <!-- 头部 -->
        <div class="bg-gradient-to-r from-purple-600 to-indigo-600 text-white p-6">
          <div class="flex justify-between items-start">
            <div>
              <h3 class="text-2xl font-bold mb-2">{{ selectedSprintNote?.title }}</h3>
              <p v-if="selectedSprintNote?.summary" class="text-purple-100">{{ selectedSprintNote.summary }}</p>
              <div v-if="selectedSprintNote?.project" class="mt-2">
                <span class="bg-white/20 px-3 py-1 rounded-full text-sm">
                  {{ selectedSprintNote.project }}
                </span>
              </div>
            </div>
            <button @click="closeSprintNoteDetailDialog" class="text-white hover:bg-white/20 rounded-lg p-2">
              <i class="fas fa-times text-2xl"></i>
            </button>
          </div>
        </div>

        <!-- 内容 -->
        <div class="p-6 overflow-y-auto flex-1">
          <!-- Tab切换 -->
          <div class="flex space-x-2 mb-4">
            <button 
              v-for="tab in [{label: '原文', value: 'original'}, {label: '解释', value: 'explanation'}, {label: '提示词', value: 'cue'}]"
              :key="tab.value"
              @click="currentNoteTab = tab.value"
              :class="['px-4 py-2 rounded-lg font-medium', currentNoteTab === tab.value ? 'bg-purple-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']"
            >
              {{ tab.label }}
            </button>
          </div>

          <!-- 内容显示 -->
          <div class="bg-gray-50 p-4 rounded-lg min-h-[200px]">
            <pre v-if="currentNoteTab === 'original'" class="whitespace-pre-wrap text-gray-700">{{ selectedSprintNote?.originalText || '暂无内容' }}</pre>
            <pre v-else-if="currentNoteTab === 'explanation'" class="whitespace-pre-wrap text-gray-700">{{ selectedSprintNote?.explanation || '暂无内容' }}</pre>
            <pre v-else-if="currentNoteTab === 'cue'" class="whitespace-pre-wrap text-gray-700">{{ selectedSprintNote?.cue || '暂无内容' }}</pre>
          </div>
        </div>

        <!-- 底部 -->
        <div class="border-t border-gray-200 p-4 bg-gray-50 flex justify-end">
          <button 
            @click="closeSprintNoteDetailDialog"
            class="px-6 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700"
          >
            关闭
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated } from 'vue'
import request from '@/api/request'
import { formatDate } from '@/utils/dateUtils'

// 当前选中的标签
const currentTab = ref<'all' | 'war' | 'diplomatic'>('all')

// 搜索关键词
const projectSearchQuery = ref('')

// 加载状态
const isLoading = ref(false)

// 项目列表数据
const allProjects = ref<any[]>([])

// 弹窗状态
const showNotesDialog = ref(false)
const showNoteDetailDialog = ref(false)
const showAddSprintNoteDialog = ref(false)
const showManageSprintNotesDialog = ref(false)
const showReviewSprintNotesDialog = ref(false)
const showSprintNoteDetailDialog = ref(false)
const selectedProject = ref<any>(null)
const projectNotes = ref<any[]>([])
const projectSprintNotes = ref<any[]>([])
const selectedNoteDetail = ref<any>(null)
const selectedSprintNote = ref<any>(null)
const isEditingSprintNote = ref(false)
const editingSprintNoteId = ref<number | null>(null)

// 冲刺记忆笔记表单数据
const sprintNoteForm = ref({
  title: '',
  summary: '',
  project: '',
  originalText: '',
  explanation: '',
  cue: ''
})

// 冲刺记忆搜索关键词
const sprintNoteSearchQuery = ref('')

// 笔记详情Tab相关
const currentNoteTab = ref('coreConcept') // 默认第一个tab

// 根据笔记类型动态生成 Tab 列表
const noteDetailTabs = computed(() => {
  if (!selectedNoteDetail.value?.type) return []
  
  const type = selectedNoteDetail.value.type
  const tabConfigs: Record<string, Array<{label: string, value: string}>> = {
    'study': [
      { label: '核心概念', value: 'coreConcept' },
      { label: '机制原理', value: 'mechanism' },
      { label: '应用案例', value: 'applicationCase' },
      { label: '知识拓展', value: 'extension' },
      { label: '常见错误', value: 'commonMistake' },
      { label: '学习反思', value: 'reflection' }
    ],
    'expansion': [
      { label: '原文记录', value: 'original' },
      { label: '理解记录', value: 'understanding' }
    ],
    'memorization': [
      { label: '原文', value: 'original' },
      { label: '解释', value: 'explanation' },
      { label: '记忆线索', value: 'cue' }
    ],
    'exercise': [
      { label: '题目描述', value: 'question' },
      { label: '题目解析', value: 'analysis' },
      { label: '参考答案', value: 'answer' }
    ],
    'practical': [
      { label: '需求描述', value: 'requirement' },
      { label: '设计思路', value: 'design' },
      { label: '参考设计', value: 'reference' }
    ],
    // 碎片笔记和框架笔记没有 Tab，直接显示 content
    'fragment': [],
    'framework': []
  }
  
  return tabConfigs[type] || []
})

// 战争行动项目
const warProjects = computed(() => 
  allProjects.value.filter(p => p.type === 'war')
)

// 外交行动项目
const diplomaticProjects = computed(() => 
  allProjects.value.filter(p => p.type === 'diplomatic')
)

// 当前显示的项目列表
const displayProjects = computed(() => {
  let projects = []
  
  // 根据分类标签筛选
  if (currentTab.value === 'war') {
    projects = warProjects.value
  } else if (currentTab.value === 'diplomatic') {
    projects = diplomaticProjects.value
  } else {
    projects = allProjects.value
  }
  
  // 根据搜索关键词筛选
  if (projectSearchQuery.value) {
    const query = projectSearchQuery.value.toLowerCase()
    projects = projects.filter(project => 
      project.name.toLowerCase().includes(query) ||
      (project.description && project.description.toLowerCase().includes(query))
    )
  }
  
  return projects
})

// 获取状态样式类
const getStatusClass = (status: string) => {
  const statusMap: Record<string, string> = {
    'planning': 'bg-yellow-100 text-yellow-700',
    'active': 'bg-blue-100 text-blue-700',
    'completed': 'bg-green-100 text-green-700',
    'suspended': 'bg-gray-100 text-gray-700'
  }
  return statusMap[status] || 'bg-gray-100 text-gray-700'
}

// 获取状态文本
const getStatusText = (status: string) => {
  const statusMap: Record<string, string> = {
    'planning': '规划中',
    'active': '进行中',
    'completed': '已完成',
    'suspended': '已暂停'
  }
  return statusMap[status] || '未知'
}

// 加载待出征项目
const loadDeployProjects = async () => {
  try {
    isLoading.value = true
    
    // 获取战争行动项目（category=defense）
    const warResponse = await request.get('/project/category/defense')
    
    // 获取外交行动项目（category=diplomatic）
    const diplomaticResponse = await request.get('/project/category/diplomatic')
    
    // 处理战争行动项目（排除已完成状态）
    const warProjectsFiltered = (warResponse.data || [])
      .filter((p: any) => p.status !== 'completed')
    
    // 处理外交行动项目（排除已完成状态）
    const diplomaticProjectsFiltered = (diplomaticResponse.data || [])
      .filter((p: any) => p.status !== 'completed')
    
    // 合并所有项目
    const allProjectsData = [...warProjectsFiltered, ...diplomaticProjectsFiltered]
    
    // 并行获取每个项目的笔记数量
    const projectsWithCounts = await Promise.all(
      allProjectsData.map(async (project: any, index: number) => {
        const isWar = index < warProjectsFiltered.length
        let noteCount = 0
        
        try {
          const countResponse = await request.get(`/project/${project.id}/notes/count`)
          noteCount = countResponse.data || 0
        } catch (error) {
          console.error(`获取项目 ${project.id} 笔记数量失败:`, error)
          noteCount = 0
        }
        
        return {
          ...project,
          type: isWar ? 'war' : 'diplomatic',
          noteCount: noteCount,
          createDate: formatDate(project.recCreateTime),
          updateDate: formatDate(project.recReviseTime)
        }
      })
    )
    
    allProjects.value = projectsWithCounts
    
  } catch (error) {
    console.error('加载项目失败:', error)
    allProjects.value = []
  } finally {
    isLoading.value = false
  }
}

// 查看项目笔记
const viewProjectNotes = async (project: any) => {
  selectedProject.value = project
  showNotesDialog.value = true
  
  try {
    // 获取项目关联的笔记ID列表
    const noteIdsResponse = await request.get(`/project/${project.id}/notes`)
    const noteIds = noteIdsResponse.data || []
    
    if (noteIds.length === 0) {
      projectNotes.value = []
      return
    }
    
    // 获取笔记详情
    const notesPromises = noteIds.map((noteId: number) => 
      request.get(`/note/${noteId}`).catch(() => null)
    )
    const notesResponses = await Promise.all(notesPromises)
    
    projectNotes.value = notesResponses
      .filter(res => res !== null)
      .map(res => res.data)
      .filter(note => note !== null)
    
  } catch (error) {
    console.error('获取项目笔记失败:', error)
    projectNotes.value = []
  }
}

// ==================== 冲刺记忆笔记相关函数 ====================

// 打开添加冲刺记忆表单
const addSprintNote = (project: any) => {
  selectedProject.value = project
  isEditingSprintNote.value = false
  editingSprintNoteId.value = null
  sprintNoteForm.value = {
    title: '',
    summary: '',
    project: '',
    originalText: '',
    explanation: '',
    cue: ''
  }
  showAddSprintNoteDialog.value = true
}

// 打开管理冲刺记忆弹窗
const manageSprintNotes = async (project: any) => {
  selectedProject.value = project
  showManageSprintNotesDialog.value = true
  await loadProjectSprintNotes(project.id)
}

// 打开回顾冲刺记忆弹窗
const reviewSprintNotes = async (project: any) => {
  selectedProject.value = project
  showReviewSprintNotesDialog.value = true
  await loadProjectSprintNotes(project.id)
}

// 加载项目的冲刺记忆列表
const loadProjectSprintNotes = async (projectId: number) => {
  try {
    const response = await request.get(`/project/${projectId}/sprint-notes`)
    projectSprintNotes.value = response.data || []
  } catch (error) {
    console.error('获取冲刺记忆失败:', error)
    projectSprintNotes.value = []
  }
}

// 保存冲刺记忆（添加或编辑）
const saveSprintNote = async () => {
  if (!sprintNoteForm.value.title.trim()) {
    alert('请输入标题')
    return
  }
  
  try {
    const noteData = {
      title: sprintNoteForm.value.title.trim(),
      summary: sprintNoteForm.value.summary.trim(),
      project: sprintNoteForm.value.project.trim(),
      originalText: sprintNoteForm.value.originalText,
      explanation: sprintNoteForm.value.explanation,
      cue: sprintNoteForm.value.cue
    }
    
    if (isEditingSprintNote.value && editingSprintNoteId.value) {
      // 编辑模式
      const response = await request.put(`/project/sprint-notes/${editingSprintNoteId.value}`, noteData)
      if (response.code === 200) {
        alert('更新成功！')
        await loadProjectSprintNotes(selectedProject.value.id)
        closeAddSprintNoteDialog()
      } else {
        alert('更新失败：' + (response.message || '未知错误'))
      }
    } else {
      // 添加模式
      const response = await request.post(`/project/${selectedProject.value.id}/sprint-notes`, noteData)
      if (response.code === 200) {
        alert('添加成功！')
        await loadProjectSprintNotes(selectedProject.value.id)
        closeAddSprintNoteDialog()
      } else {
        alert('添加失败：' + (response.message || '未知错误'))
      }
    }
  } catch (error) {
    console.error('保存冲刺记忆失败:', error)
    alert('操作失败，请重试')
  }
}

// 编辑冲刺记忆
const editSprintNote = (note: any) => {
  isEditingSprintNote.value = true
  editingSprintNoteId.value = note.id
  sprintNoteForm.value = {
    title: note.title || '',
    summary: note.summary || '',
    project: note.project || '',
    originalText: note.originalText || '',
    explanation: note.explanation || '',
    cue: note.cue || ''
  }
  showManageSprintNotesDialog.value = false
  showAddSprintNoteDialog.value = true
}

// 删除冲刺记忆
const deleteSprintNote = async (note: any) => {
  if (!confirm('确定删除这条冲刺记忆吗？')) return
  
  try {
    const response = await request.delete(`/project/sprint-notes/${note.id}`)
    if (response.code === 200) {
      alert('删除成功！')
      await loadProjectSprintNotes(selectedProject.value.id)
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除冲刺记忆失败:', error)
    alert('删除失败，请重试')
  }
}

// 查看冲刺记忆详情
const viewSprintNoteDetail = (note: any) => {
  selectedSprintNote.value = note
  showSprintNoteDetailDialog.value = true
}

// 关闭各种弹窗
const closeAddSprintNoteDialog = () => {
  showAddSprintNoteDialog.value = false
  isEditingSprintNote.value = false
  editingSprintNoteId.value = null
  sprintNoteForm.value = {
    title: '',
    summary: '',
    project: '',
    originalText: '',
    explanation: '',
    cue: ''
  }
}

const closeManageSprintNotesDialog = () => {
  showManageSprintNotesDialog.value = false
  projectSprintNotes.value = []
}

const closeReviewSprintNotesDialog = () => {
  showReviewSprintNotesDialog.value = false
  projectSprintNotes.value = []
  sprintNoteSearchQuery.value = ''
}

const closeSprintNoteDetailDialog = () => {
  showSprintNoteDetailDialog.value = false
  selectedSprintNote.value = null
}

// 筛选后的冲刺记忆列表（用于回顾弹窗）
const filteredSprintNotes = computed(() => {
  if (!sprintNoteSearchQuery.value) return projectSprintNotes.value
  
  const query = sprintNoteSearchQuery.value.toLowerCase()
  return projectSprintNotes.value.filter(note => 
    note.title?.toLowerCase().includes(query) ||
    note.summary?.toLowerCase().includes(query)
  )
})

// 关闭笔记弹窗
const closeNotesDialog = () => {
  showNotesDialog.value = false
  selectedProject.value = null
  projectNotes.value = []
}

// 打开笔记详情
const openNoteDetail = (note: any) => {
  selectedNoteDetail.value = note
  
  // 根据笔记类型初始化 Tab
  const type = note?.type
  const defaultTabs: Record<string, string> = {
    'study': 'coreConcept',
    'expansion': 'original',
    'memorization': 'original',
    'exercise': 'question',
    'practical': 'requirement'
  }
  currentNoteTab.value = defaultTabs[type] || 'coreConcept'
  
  showNoteDetailDialog.value = true
}

// 关闭笔记详情
const closeNoteDetailDialog = () => {
  showNoteDetailDialog.value = false
  selectedNoteDetail.value = null
}

// 获取笔记类型文本
const getNoteTypeText = (type: string) => {
  const typeMap: Record<string, string> = {
    'fragment': '碎片笔记',
    'framework': '框架笔记',
    'study': '求学笔记',
    'expansion': '拓展笔记',
    'memorization': '背诵笔记',
    'exercise': '刷题笔记',
    'practical': '实战笔记'
  }
  return typeMap[type] || type
}

// 解析标签数组（处理数组或JSON字符串）
const parseTagsArray = (tags: any): string[] => {
  if (!tags) return []
  
  // 如果已经是数组，直接返回
  if (Array.isArray(tags)) {
    return tags.filter(tag => tag && tag.trim())
  }
  
  // 如果是字符串，尝试解析为JSON
  if (typeof tags === 'string') {
    try {
      const parsed = JSON.parse(tags)
      if (Array.isArray(parsed)) {
        return parsed.filter(tag => tag && tag.trim())
      }
      // 如果解析出来不是数组，返回单个标签
      return [tags]
    } catch {
      // 如果不是JSON，可能是逗号分隔的字符串
      if (tags.includes(',')) {
        return tags.split(',').map(tag => tag.trim()).filter(tag => tag)
      }
      // 否则返回整个字符串作为单个标签
      return [tags]
    }
  }
  
  return []
}

// 获取笔记类型标签样式
const getNoteTypeTagClass = (type: string) => {
  const classMap: Record<string, string> = {
    'fragment': 'bg-purple-100 text-purple-700',
    'framework': 'bg-blue-100 text-blue-700',
    'study': 'bg-green-100 text-green-700',
    'expansion': 'bg-blue-100 text-blue-700',
    'memorization': 'bg-amber-100 text-amber-700',
    'exercise': 'bg-red-100 text-red-700',
    'practical': 'bg-indigo-100 text-indigo-700'
  }
  return classMap[type] || 'bg-gray-100 text-gray-700'
}

// 获取笔记类型图标
const getNoteTypeIconClass = (type: string) => {
  const iconMap: Record<string, string> = {
    'fragment': 'fas fa-puzzle-piece',
    'framework': 'fas fa-sitemap',
    'study': 'fas fa-graduation-cap',
    'expansion': 'fas fa-book-open',
    'memorization': 'fas fa-bookmark',
    'exercise': 'fas fa-code',
    'practical': 'fas fa-tools'
  }
  return iconMap[type] || 'fas fa-file-alt'
}

// 获取笔记卡片边框样式
const getNoteCardBorderClass = (type: string) => {
  const classMap: Record<string, string> = {
    'fragment': 'border-purple-200 hover:border-purple-400',
    'framework': 'border-blue-200 hover:border-blue-400',
    'study': 'border-green-200 hover:border-green-400',
    'expansion': 'border-blue-200 hover:border-blue-400',
    'memorization': 'border-amber-200 hover:border-amber-400',
    'exercise': 'border-red-200 hover:border-red-400',
    'practical': 'border-indigo-200 hover:border-indigo-400'
  }
  return classMap[type] || 'border-gray-200 hover:border-gray-400'
}

// 组件挂载时加载数据
onMounted(() => {
  loadDeployProjects()
})

// 当从其他页面切换回来时，重新加载数据
onActivated(() => {
  loadDeployProjects()
})
</script>

<style scoped>
.animate-fade-in {
  animation: fade-in 0.5s ease-out;
}

@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.sr-only {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border-width: 0;
}

/* note-content 样式已移至全局 src/styles/note-content.css */
</style>

