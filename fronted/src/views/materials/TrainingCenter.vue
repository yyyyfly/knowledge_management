<template>
  <div class="p-6 bg-gradient-to-br from-gray-50 to-indigo-50 min-h-screen">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <div class="w-12 h-12 bg-gradient-to-br from-indigo-500 to-purple-600 rounded-xl flex items-center justify-center shadow-lg">
          <i class="fas fa-laptop-code text-white text-2xl"></i>
        </div>
        <div>
          <h1 class="text-3xl font-bold text-gray-900">模拟实战</h1>
          <p class="text-gray-600">设计实战练习方案 · 基础赛题与专项实战</p>
        </div>
      </div>
    </div>

    <!-- 基础赛题 -->
    <div class="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden mb-8">
      <div class="bg-gradient-to-r from-blue-600 to-cyan-600 p-6">
        <div class="flex items-center justify-between">
          <div>
            <h2 class="text-2xl font-bold text-white mb-2">
              <i class="fas fa-list-check mr-2"></i>
              基础赛题
            </h2>
            <p class="text-blue-100">通用题目适用所有技术栈 · 鼠标悬停卡片可编辑</p>
          </div>
          <button @click="openAddTemplateModal" 
                  class="bg-white/20 hover:bg-white/30 text-white px-6 py-3 rounded-xl flex items-center space-x-2 transition-all duration-300 hover:scale-105 shadow-lg">
            <i class="fas fa-plus"></i>
            <span class="font-medium">新建赛题</span>
          </button>
        </div>
      </div>

      <!-- 模板列表 -->
      <div class="p-6">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="template in projectTemplates" :key="template.id" 
               class="group relative bg-white border-2 border-gray-100 rounded-xl p-6 hover:border-blue-300 hover:shadow-lg transition-all duration-300">
            
            <!-- 操作按钮 -->
            <div class="absolute top-3 right-3 opacity-0 group-hover:opacity-100 transition-opacity z-10">
              <div class="flex space-x-2 bg-white rounded-lg shadow-lg p-1">
                <button @click.stop="openEditTemplateModal(template)" 
                        class="p-2 bg-blue-50 hover:bg-blue-100 text-blue-600 rounded-lg transition-colors">
                  <i class="fas fa-edit text-sm"></i>
                </button>
                <button @click.stop="deleteTemplate(template.id)" 
                        class="p-2 bg-red-50 hover:bg-red-100 text-red-600 rounded-lg transition-colors">
                  <i class="fas fa-trash text-sm"></i>
                </button>
              </div>
            </div>

            <div class="mb-4">
              <div class="mb-3">
                <div class="flex items-center justify-between mb-2">
                  <h3 class="text-lg font-bold text-gray-900 group-hover:text-blue-600 transition-colors pr-2 flex-1">{{ template.name }}</h3>
                </div>
                <div class="flex items-start gap-2">
                  <span class="px-3 py-1 rounded-full text-xs font-medium bg-gradient-to-r from-blue-500 to-cyan-600 text-white whitespace-nowrap">
                    {{ template.domain }}
                  </span>
                  <p class="text-gray-600 text-sm line-clamp-2 flex-1">{{ template.description }}</p>
                </div>
              </div>
            </div>

            <!-- 模板统计 -->
            <div class="grid grid-cols-2 gap-4 mb-4">
              <div class="text-center p-3 bg-blue-50 rounded-lg">
                <div class="text-lg font-bold text-blue-600">{{ template.requirements.length }}</div>
                <div class="text-xs text-gray-600">功能需求</div>
              </div>
              <div class="text-center p-3 bg-green-50 rounded-lg">
                <div class="text-lg font-bold text-green-600">{{ template.commonFeatures.length }}</div>
                <div class="text-xs text-gray-600">非功能性需求</div>
              </div>
            </div>

            <!-- 特性标签 -->
            <div class="flex flex-wrap gap-2 mb-4">
              <span v-for="feature in template.commonFeatures.slice(0, 3)" :key="feature.name" 
                    class="bg-gray-100 text-gray-700 px-2 py-1 rounded-md text-xs font-medium">
                {{ feature.name }}
              </span>
              <span v-if="template.commonFeatures.length > 3" 
                    class="bg-gray-100 text-gray-500 px-2 py-1 rounded-md text-xs">
                +{{ template.commonFeatures.length - 3 }}
              </span>
            </div>
            
            <!-- 查看详情按钮 -->
            <button @click="openTemplateDetail(template)" 
                    class="w-full bg-gradient-to-r from-blue-600 to-cyan-600 hover:from-blue-700 hover:to-cyan-700 text-white py-3 rounded-lg font-medium transition-all duration-300 hover:shadow-lg">
              查看详情
            </button>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-if="projectTemplates.length === 0" class="text-center py-16">
          <div class="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <i class="fas fa-list-check text-3xl text-gray-400"></i>
          </div>
          <h3 class="text-lg font-medium text-gray-900 mb-2">暂无基础赛题</h3>
          <p class="text-gray-600 mb-6">创建通用赛题，适用于所有技术栈</p>
          <button @click="openAddTemplateModal" 
                  class="bg-gradient-to-r from-blue-600 to-cyan-600 hover:from-blue-700 hover:to-cyan-700 text-white px-6 py-3 rounded-lg font-medium transition-all duration-300 hover:shadow-lg">
            新建赛题
          </button>
        </div>
      </div>
    </div>

    <!-- 专项实战 -->
    <div class="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">
      <div class="bg-gradient-to-r from-indigo-600 to-purple-600 p-6">
        <div class="flex items-center justify-between">
          <div>
            <h2 class="text-2xl font-bold text-white mb-2">
              <i class="fas fa-crosshairs mr-2"></i>
              专项实战
            </h2>
            <p class="text-indigo-100">针对特定场景的专项训练 · 适用于项目、考试、竞赛 • 鼠标悬停卡片可编辑</p>
          </div>
          <button @click="openAddPracticeModal" 
                  class="bg-white/20 hover:bg-white/30 text-white px-6 py-3 rounded-xl flex items-center space-x-2 transition-all duration-300 hover:scale-105 shadow-lg">
            <i class="fas fa-plus"></i>
            <span class="font-medium">新建实战</span>
          </button>
        </div>
      </div>

      <!-- 实战方案列表 -->
      <div class="p-6">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="practice in customPractices" :key="practice.id" 
               class="group relative bg-white border-2 border-gray-100 rounded-xl p-6 hover:border-indigo-300 hover:shadow-lg transition-all duration-300">
            
            <!-- 操作按钮 -->
            <div class="absolute top-3 right-3 opacity-0 group-hover:opacity-100 transition-opacity z-10">
              <div class="flex space-x-2 bg-white rounded-lg shadow-lg p-1">
                <button @click.stop="openEditPracticeModal(practice)" 
                        class="p-2 bg-blue-50 hover:bg-blue-100 text-blue-600 rounded-lg transition-colors">
                  <i class="fas fa-edit text-sm"></i>
                </button>
                <button @click.stop="deletePractice(practice.id)" 
                        class="p-2 bg-red-50 hover:bg-red-100 text-red-600 rounded-lg transition-colors">
                  <i class="fas fa-trash text-sm"></i>
                </button>
              </div>
            </div>

            <div class="mb-4">
              <div class="mb-3">
                <div class="flex items-center justify-between mb-2">
                  <h3 class="text-lg font-bold text-gray-900 group-hover:text-indigo-600 transition-colors pr-2 flex-1">{{ practice.practiceName }}</h3>
                </div>
                <div class="flex items-start gap-2 mb-2">
                  <span class="px-3 py-1 rounded-full text-xs font-medium bg-gradient-to-r from-indigo-500 to-purple-600 text-white whitespace-nowrap">
                    {{ practice.scenario }}
                  </span>
                  <span v-if="practice.difficulty" class="px-3 py-1 rounded-full text-xs font-medium bg-orange-100 text-orange-600 whitespace-nowrap">
                    {{ practice.difficulty }}
                  </span>
                </div>
                <p class="text-gray-600 text-sm line-clamp-2">{{ practice.description }}</p>
              </div>
            </div>

            <!-- 实战信息 -->
            <div class="mb-4 space-y-2">
              <div class="flex items-center text-xs text-gray-600">
                <i class="fas fa-clock mr-2 text-indigo-500"></i>
                <span>建议时长：{{ practice.timeLimit || '--' }} 分钟</span>
              </div>
              <div v-if="practice.tags" class="flex flex-wrap gap-1">
                <span v-for="tag in practice.tags.split(',')" :key="tag" 
                      class="bg-gray-100 text-gray-600 px-2 py-1 rounded text-xs">
                  {{ tag }}
                </span>
              </div>
            </div>
            
            <!-- 查看详情按钮 -->
            <button @click="openPracticeDetail(practice)" 
                    class="w-full bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white py-3 rounded-lg font-medium transition-all duration-300 hover:shadow-lg">
              查看详情
            </button>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-if="customPractices.length === 0" class="text-center py-16">
          <div class="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <i class="fas fa-crosshairs text-3xl text-gray-400"></i>
          </div>
          <h3 class="text-lg font-medium text-gray-900 mb-2">暂无专项实战</h3>
          <p class="text-gray-600 mb-6">创建专项实战，针对特定场景</p>
          <button @click="openAddPracticeModal" 
                  class="bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white px-6 py-3 rounded-lg font-medium transition-all duration-300 hover:shadow-lg">
            新建实战
          </button>
        </div>
      </div>
    </div>

    <!-- 基础赛题 - 新增/编辑弹窗 -->
    <transition name="modal-fade">
      <div v-if="showTemplateFormModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-4xl max-h-[90vh] overflow-y-auto">
          <!-- 弹窗头部 -->
          <div class="bg-gradient-to-r from-blue-600 to-cyan-600 p-6 rounded-t-2xl">
            <div class="flex items-center justify-between">
              <div>
                <h2 class="text-2xl font-bold text-white">{{ isEditingTemplate ? '编辑基础赛题' : '创建基础赛题' }}</h2>
                <p class="text-blue-100 mt-1">适用于所有技术栈的通用题目</p>
              </div>
              <button @click="closeTemplateFormModal" class="text-white/80 hover:text-white transition-colors">
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
          </div>
          
          <form @submit.prevent="saveTemplate" class="p-6 space-y-6">
            <!-- 基本信息 -->
            <div class="bg-gradient-to-br from-blue-50 to-purple-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-info-circle text-blue-600 mr-2"></i>
                基本信息
              </h3>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">赛题分类</label>
                  <input v-model="templateFormData.domain" type="text" required 
                         class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" 
                         placeholder="如：Web开发、移动应用、数据分析">
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">赛题名称</label>
                  <input v-model="templateFormData.name" type="text" required 
                         class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" 
                         placeholder="如：智慧校园管理系统">
                </div>
              </div>
              
              <div class="mt-4">
                <label class="block text-sm font-medium text-gray-700 mb-2">赛题描述</label>
                <textarea v-model="templateFormData.description" rows="3" required 
                          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" 
                          placeholder="详细描述该赛题的适用场景和核心价值"></textarea>
              </div>
            </div>
            
            <!-- 功能性需求 -->
            <div class="bg-gradient-to-br from-green-50 to-blue-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-list-check text-green-600 mr-2"></i>
                功能性需求
              </h3>
              <p class="text-sm text-gray-600 mb-4">定义系统必须实现的具体功能模块和业务逻辑</p>
              <div class="space-y-3">
                <div v-for="(req, index) in templateFormData.requirements" :key="index" 
                     class="flex gap-3 p-4 bg-white rounded-lg border border-gray-200">
                  <div class="flex-1">
                    <input v-model="req.name" type="text" placeholder="功能模块名称" 
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent mb-2">
                    <input v-model="req.desc" type="text" placeholder="功能描述和业务价值" 
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent">
                  </div>
                  <button type="button" @click="removeRequirement(index)" 
                          class="px-3 py-2 bg-red-50 text-red-600 rounded-lg hover:bg-red-100 transition-colors">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
                <button type="button" @click="addRequirement" 
                        class="w-full py-3 border-2 border-dashed border-green-300 rounded-lg text-green-600 hover:border-green-400 hover:text-green-700 hover:bg-green-50 transition-all">
                  <i class="fas fa-plus mr-2"></i>添加功能性需求
                </button>
              </div>
            </div>
            
            <!-- 非功能性需求 -->
            <div class="bg-gradient-to-br from-purple-50 to-pink-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-shield-check text-purple-600 mr-2"></i>
                非功能性需求
              </h3>
              <p class="text-sm text-gray-600 mb-4">定义系统的性能、安全、可用性等质量属性要求</p>
              <div class="space-y-3">
                <div v-for="(feature, index) in templateFormData.commonFeatures" :key="index" 
                     class="space-y-3 p-4 bg-white rounded-lg border border-gray-200">
                  <div class="flex gap-3">
                    <input v-model="templateFormData.commonFeatures[index].name" type="text" placeholder="非功能性需求名称" 
                           class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent">
                    <button type="button" @click="removeFeature(index)" 
                            class="px-3 py-2 bg-red-50 text-red-600 rounded-lg hover:bg-red-100 transition-colors">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                  <textarea v-model="templateFormData.commonFeatures[index].desc" placeholder="详细描述该非功能性需求的具体要求..." 
                            rows="2" 
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent resize-none"></textarea>
                </div>
                <button type="button" @click="addFeature" 
                        class="w-full py-3 border-2 border-dashed border-purple-300 rounded-lg text-purple-600 hover:border-purple-400 hover:text-purple-700 hover:bg-purple-50 transition-all">
                  <i class="fas fa-plus mr-2"></i>添加非功能性需求
                </button>
              </div>
            </div>

            <!-- 操作按钮 -->
            <div class="flex gap-4 pt-4">
              <button type="submit" 
                      class="flex-1 bg-gradient-to-r from-blue-600 to-cyan-600 hover:from-blue-700 hover:to-cyan-700 text-white py-4 rounded-xl font-medium transition-all duration-300 hover:shadow-lg">
                <i class="fas fa-save mr-2"></i>
                {{ isEditingTemplate ? '保存修改' : '创建赛题' }}
              </button>
              <button type="button" @click="closeTemplateFormModal" 
                      class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 py-4 rounded-xl font-medium transition-colors">
                <i class="fas fa-times mr-2"></i>
                取消
              </button>
            </div>
          </form>
        </div>
      </div>
    </transition>

    <!-- 专项实战 - 新增/编辑弹窗 -->
    <transition name="modal-fade">
      <div v-if="showPracticeFormModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-4xl max-h-[90vh] overflow-y-auto">
          <!-- 弹窗头部 -->
          <div class="bg-gradient-to-r from-indigo-600 to-purple-600 p-6 rounded-t-2xl">
            <div class="flex items-center justify-between">
              <div>
                <h2 class="text-2xl font-bold text-white">{{ isEditingPractice ? '编辑专项实战' : '创建专项实战' }}</h2>
                <p class="text-indigo-100 mt-1">针对特定场景的专项训练</p>
              </div>
              <button @click="closePracticeFormModal" class="text-white/80 hover:text-white transition-colors">
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
          </div>
          
          <form @submit.prevent="savePractice" class="p-6 space-y-6">
            <!-- 基本信息 -->
            <div class="bg-gradient-to-br from-indigo-50 to-purple-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-info-circle text-indigo-600 mr-2"></i>
                基本信息
              </h3>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">应用场景</label>
                  <input v-model="practiceFormData.scenario" type="text" required 
                         class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" 
                         placeholder="如：项目实战、考试模拟、竞赛训练">
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">难度等级</label>
                  <select v-model="practiceFormData.difficulty" 
                          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all">
                    <option value="">不限</option>
                    <option value="简单">简单</option>
                    <option value="中等">中等</option>
                    <option value="困难">困难</option>
                  </select>
                </div>
              </div>
              
              <div class="mt-4">
                <label class="block text-sm font-medium text-gray-700 mb-2">实战名称 *</label>
                <input v-model="practiceFormData.practiceName" type="text" required 
                       class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" 
                       placeholder="如：Spring Boot微服务项目实战">
              </div>
              
              <div class="mt-4">
                <label class="block text-sm font-medium text-gray-700 mb-2">详细描述 *</label>
                <textarea v-model="practiceFormData.description" rows="3" required 
                          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" 
                          placeholder="详细描述该实战方案的背景、目标和适用范围"></textarea>
              </div>
              
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">建议时长（分钟）</label>
                  <input v-model.number="practiceFormData.timeLimit" type="number" 
                         class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" 
                         placeholder="如：120">
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">标签（逗号分隔）</label>
                  <input v-model="practiceFormData.tags" type="text" 
                         class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" 
                         placeholder="如：SpringBoot,微服务,Docker">
                </div>
              </div>
            </div>
            
            <!-- 目标技能点 -->
            <div class="bg-gradient-to-br from-green-50 to-blue-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-bullseye text-green-600 mr-2"></i>
                目标技能点
              </h3>
              <p class="text-sm text-gray-600 mb-4">定义本次实战需要掌握或训练的技能点</p>
              <div class="space-y-3">
                <div v-for="(skill, index) in practiceFormData.targetSkillsList" :key="index" 
                     class="flex gap-3 p-4 bg-white rounded-lg border border-gray-200">
                  <input v-model="practiceFormData.targetSkillsList[index]" type="text" placeholder="技能点名称" 
                         class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent">
                  <button type="button" @click="removeSkill(index)" 
                          class="px-3 py-2 bg-red-50 text-red-600 rounded-lg hover:bg-red-100 transition-colors">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
                <button type="button" @click="addSkill" 
                        class="w-full py-3 border-2 border-dashed border-green-300 rounded-lg text-green-600 hover:border-green-400 hover:text-green-700 hover:bg-green-50 transition-all">
                  <i class="fas fa-plus mr-2"></i>添加技能点
                </button>
              </div>
            </div>
            
            <!-- 实战内容 -->
            <div class="bg-gradient-to-br from-orange-50 to-red-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-tasks text-orange-600 mr-2"></i>
                实战内容
              </h3>
              <p class="text-sm text-gray-600 mb-4">定义具体的实战任务和内容步骤</p>
              <div class="space-y-3">
                <div v-for="(content, index) in practiceFormData.practiceContentList" :key="index" 
                     class="space-y-2 p-4 bg-white rounded-lg border border-gray-200">
                  <input v-model="practiceFormData.practiceContentList[index].title" type="text" placeholder="任务标题" 
                         class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-transparent">
                  <textarea v-model="practiceFormData.practiceContentList[index].description" placeholder="任务详细描述..." 
                            rows="2" 
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-transparent resize-none"></textarea>
                  <button type="button" @click="removeContent(index)" 
                          class="px-3 py-1 bg-red-50 text-red-600 rounded-lg hover:bg-red-100 transition-colors text-sm">
                    <i class="fas fa-trash mr-1"></i>删除任务
                  </button>
                </div>
                <button type="button" @click="addContent" 
                        class="w-full py-3 border-2 border-dashed border-orange-300 rounded-lg text-orange-600 hover:border-orange-400 hover:text-orange-700 hover:bg-orange-50 transition-all">
                  <i class="fas fa-plus mr-2"></i>添加实战任务
                </button>
              </div>
            </div>
            
            <!-- 评估标准 -->
            <div class="bg-gradient-to-br from-purple-50 to-pink-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-check-double text-purple-600 mr-2"></i>
                评估标准
              </h3>
              <p class="text-sm text-gray-600 mb-4">定义实战成果的评估标准和验收要求</p>
              <div class="space-y-3">
                <div v-for="(criteria, index) in practiceFormData.evaluationCriteriaList" :key="index" 
                     class="space-y-2 p-4 bg-white rounded-lg border border-gray-200">
                  <div class="flex gap-3">
                    <input v-model="practiceFormData.evaluationCriteriaList[index].name" type="text" placeholder="评估项名称" 
                           class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent">
                    <input v-model.number="practiceFormData.evaluationCriteriaList[index].weight" type="number" placeholder="权重%" 
                           class="w-24 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent">
                    <button type="button" @click="removeCriteria(index)" 
                            class="px-3 py-2 bg-red-50 text-red-600 rounded-lg hover:bg-red-100 transition-colors">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                  <textarea v-model="practiceFormData.evaluationCriteriaList[index].standard" placeholder="评估标准详细说明..." 
                            rows="2" 
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent resize-none"></textarea>
                </div>
                <button type="button" @click="addCriteria" 
                        class="w-full py-3 border-2 border-dashed border-purple-300 rounded-lg text-purple-600 hover:border-purple-400 hover:text-purple-700 hover:bg-purple-50 transition-all">
                  <i class="fas fa-plus mr-2"></i>添加评估标准
                </button>
              </div>
            </div>

            <!-- 操作按钮 -->
            <div class="flex gap-4 pt-4">
              <button type="submit" 
                      class="flex-1 bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white py-4 rounded-xl font-medium transition-all duration-300 hover:shadow-lg">
                <i class="fas fa-save mr-2"></i>
                {{ isEditingPractice ? '保存修改' : '创建实战' }}
              </button>
              <button type="button" @click="closePracticeFormModal" 
                      class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 py-4 rounded-xl font-medium transition-colors">
                <i class="fas fa-times mr-2"></i>
                取消
              </button>
            </div>
          </form>
        </div>
      </div>
    </transition>

    <!-- 基础赛题详情弹窗 -->
    <transition name="modal-fade">
      <div v-if="showTemplateDetailModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-5xl max-h-[90vh] overflow-y-auto">
          <!-- 弹窗头部 -->
          <div class="bg-gradient-to-r from-blue-600 to-cyan-600 p-6 rounded-t-2xl sticky top-0 z-10">
            <div class="flex items-center justify-between">
              <div class="flex-1">
                <h2 class="text-2xl font-bold text-white mb-2">{{ selectedTemplateDetail?.name }}</h2>
                <div class="flex items-center space-x-3">
                  <span class="px-3 py-1 bg-white/20 rounded-full text-sm text-white">
                    {{ selectedTemplateDetail?.domain }}
                  </span>
                </div>
              </div>
              <button @click="closeTemplateDetail" class="text-white/80 hover:text-white transition-colors">
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
          </div>

          <div class="p-6 space-y-6">
            <!-- 赛题描述 -->
            <div class="bg-gradient-to-br from-blue-50 to-purple-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-3 flex items-center">
                <i class="fas fa-align-left text-blue-600 mr-2"></i>
                赛题描述
              </h3>
              <p class="text-gray-700 leading-relaxed">{{ selectedTemplateDetail?.description }}</p>
            </div>

            <!-- 功能性需求 -->
            <div class="bg-gradient-to-br from-green-50 to-blue-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-list-check text-green-600 mr-2"></i>
                功能性需求
                <span class="ml-2 text-sm font-normal text-gray-600">({{ selectedTemplateDetail?.requirements.length }} 项)</span>
              </h3>
              <div class="space-y-3">
                <div v-for="(req, index) in selectedTemplateDetail?.requirements" :key="index"
                     class="bg-white rounded-lg p-4 border-l-4 border-green-500">
                  <h4 class="font-semibold text-gray-900 mb-1">{{ index + 1 }}. {{ req.name }}</h4>
                  <p class="text-sm text-gray-600">{{ req.desc }}</p>
                </div>
              </div>
            </div>

            <!-- 非功能性需求 -->
            <div class="bg-gradient-to-br from-purple-50 to-pink-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-shield-check text-purple-600 mr-2"></i>
                非功能性需求
                <span class="ml-2 text-sm font-normal text-gray-600">({{ selectedTemplateDetail?.commonFeatures.length }} 项)</span>
              </h3>
              <div class="space-y-3">
                <div v-for="(feature, index) in selectedTemplateDetail?.commonFeatures" :key="index"
                     class="bg-white rounded-lg p-4 border-l-4 border-purple-500">
                  <h4 class="font-semibold text-gray-900 mb-1">{{ index + 1 }}. {{ feature.name }}</h4>
                  <p class="text-sm text-gray-600">{{ feature.desc }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 底部按钮 -->
          <div class="p-6 bg-gray-50 rounded-b-2xl sticky bottom-0">
            <button @click="closeTemplateDetail"
                    class="w-full bg-gradient-to-r from-blue-600 to-cyan-600 hover:from-blue-700 hover:to-cyan-700 text-white py-3 rounded-xl font-medium transition-all duration-300 hover:shadow-lg">
              <i class="fas fa-check mr-2"></i>
              知道了
            </button>
          </div>
        </div>
      </div>
    </transition>

    <!-- 专项实战详情弹窗 -->
    <transition name="modal-fade">
      <div v-if="showPracticeDetailModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-5xl max-h-[90vh] overflow-y-auto">
          <!-- 弹窗头部 -->
          <div class="bg-gradient-to-r from-indigo-600 to-purple-600 p-6 rounded-t-2xl sticky top-0 z-10">
            <div class="flex items-center justify-between">
              <div class="flex-1">
                <h2 class="text-2xl font-bold text-white mb-2">{{ selectedPracticeDetail?.practiceName }}</h2>
                <div class="flex items-center space-x-3">
                  <span class="px-3 py-1 bg-white/20 rounded-full text-sm text-white">
                    {{ selectedPracticeDetail?.scenario }}
                  </span>
                  <span v-if="selectedPracticeDetail?.difficulty" class="px-3 py-1 bg-white/20 rounded-full text-sm text-white">
                    {{ selectedPracticeDetail?.difficulty }}
                  </span>
                  <span v-if="selectedPracticeDetail?.timeLimit" class="px-3 py-1 bg-white/20 rounded-full text-sm text-white">
                    <i class="fas fa-clock mr-1"></i>{{ selectedPracticeDetail?.timeLimit }} 分钟
                  </span>
                </div>
              </div>
              <button @click="closePracticeDetail" class="text-white/80 hover:text-white transition-colors">
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
          </div>

          <div class="p-6 space-y-6">
            <!-- 实战描述 -->
            <div class="bg-gradient-to-br from-indigo-50 to-purple-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-3 flex items-center">
                <i class="fas fa-align-left text-indigo-600 mr-2"></i>
                实战描述
              </h3>
              <p class="text-gray-700 leading-relaxed">{{ selectedPracticeDetail?.description }}</p>
            </div>

            <!-- 标签 -->
            <div v-if="selectedPracticeDetail?.tags" class="flex flex-wrap gap-2">
              <span v-for="tag in selectedPracticeDetail?.tags.split(',')" :key="tag"
                    class="px-3 py-1 bg-indigo-100 text-indigo-700 rounded-full text-sm font-medium">
                {{ tag }}
              </span>
            </div>

            <!-- 目标技能点 -->
            <div v-if="selectedPracticeDetail?.targetSkillsList && selectedPracticeDetail?.targetSkillsList.length > 0" 
                 class="bg-gradient-to-br from-green-50 to-blue-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-bullseye text-green-600 mr-2"></i>
                目标技能点
                <span class="ml-2 text-sm font-normal text-gray-600">({{ selectedPracticeDetail?.targetSkillsList.length }} 项)</span>
              </h3>
              <div class="flex flex-wrap gap-2">
                <span v-for="(skill, index) in selectedPracticeDetail?.targetSkillsList" :key="index"
                      class="px-3 py-2 bg-white rounded-lg border border-green-300 text-gray-700 text-sm">
                  {{ skill }}
                </span>
              </div>
            </div>

            <!-- 实战内容 -->
            <div v-if="selectedPracticeDetail?.practiceContentList && selectedPracticeDetail?.practiceContentList.length > 0"
                 class="bg-gradient-to-br from-orange-50 to-red-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-tasks text-orange-600 mr-2"></i>
                实战内容
                <span class="ml-2 text-sm font-normal text-gray-600">({{ selectedPracticeDetail?.practiceContentList.length }} 项任务)</span>
              </h3>
              <div class="space-y-3">
                <div v-for="(content, index) in selectedPracticeDetail?.practiceContentList" :key="index"
                     class="bg-white rounded-lg p-4 border-l-4 border-orange-500">
                  <h4 class="font-semibold text-gray-900 mb-1">{{ index + 1 }}. {{ content.title }}</h4>
                  <p class="text-sm text-gray-600">{{ content.description }}</p>
                </div>
              </div>
            </div>

            <!-- 评估标准 -->
            <div v-if="selectedPracticeDetail?.evaluationCriteriaList && selectedPracticeDetail?.evaluationCriteriaList.length > 0"
                 class="bg-gradient-to-br from-purple-50 to-pink-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-check-double text-purple-600 mr-2"></i>
                评估标准
                <span class="ml-2 text-sm font-normal text-gray-600">({{ selectedPracticeDetail?.evaluationCriteriaList.length }} 项)</span>
              </h3>
              <div class="space-y-3">
                <div v-for="(criteria, index) in selectedPracticeDetail?.evaluationCriteriaList" :key="index"
                     class="bg-white rounded-lg p-4 border-l-4 border-purple-500">
                  <div class="flex items-center justify-between mb-1">
                    <h4 class="font-semibold text-gray-900">{{ index + 1 }}. {{ criteria.name }}</h4>
                    <span class="px-2 py-1 bg-purple-100 text-purple-700 rounded text-xs font-medium">
                      权重 {{ criteria.weight }}%
                    </span>
                  </div>
                  <p class="text-sm text-gray-600">{{ criteria.standard }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 底部按钮 -->
          <div class="p-6 bg-gray-50 rounded-b-2xl sticky bottom-0">
            <button @click="closePracticeDetail"
                    class="w-full bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white py-3 rounded-xl font-medium transition-all duration-300 hover:shadow-lg">
              <i class="fas fa-check mr-2"></i>
              知道了
            </button>
          </div>
        </div>
      </div>
    </transition>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onActivated } from 'vue'
import { getAllProjectTemplates, createProjectTemplate, updateProjectTemplate, deleteProjectTemplate } from '@/api/projectTemplate'
import type { ProjectTemplate as ApiProjectTemplate } from '@/api/projectTemplate'
import { getAllCustomPractices, createCustomPractice, updateCustomPractice, deleteCustomPractice } from '@/api/customPractice'
import type { CustomPractice as ApiCustomPractice } from '@/api/customPractice'

// ========== 通用训练赛模板相关 ==========
export interface ProjectRequirement {
  name: string
  desc: string
}

export interface ProjectTemplate {
  id: number
  domain: string
  name: string
  description: string
  requirements: ProjectRequirement[]
  commonFeatures: ProjectRequirement[]
}

const projectTemplates = ref<ProjectTemplate[]>([])
const showTemplateFormModal = ref(false)
const showTemplateDetailModal = ref(false)
const isEditingTemplate = ref(false)
const selectedTemplateDetail = ref<ProjectTemplate | null>(null)
const templateFormData = ref({
  id: 0,
  domain: '',
  name: '',
  description: '',
  requirements: [] as ProjectRequirement[],
  commonFeatures: [] as ProjectRequirement[]
})

// ========== 定制模拟实战相关 ==========
export interface CustomPractice {
  id?: number
  practiceName: string
  scenario: string
  difficulty?: string
  description: string
  targetSkills: string
  practiceContent: string
  evaluationCriteria: string
  timeLimit?: number
  tags?: string
}

const customPractices = ref<any[]>([])
const showPracticeFormModal = ref(false)
const showPracticeDetailModal = ref(false)
const isEditingPractice = ref(false)
const selectedPracticeDetail = ref<any>(null)
const practiceFormData = ref({
  id: 0,
  practiceName: '',
  scenario: '',
  difficulty: '',
  description: '',
  timeLimit: undefined as number | undefined,
  tags: '',
  targetSkillsList: [] as string[],
  practiceContentList: [] as { title: string; description: string }[],
  evaluationCriteriaList: [] as { name: string; weight: number; standard: string }[]
})

// 加载数据
const loadData = async () => {
  try {
    // 加载通用训练赛模板
    const templateResponse = await getAllProjectTemplates()
    if (templateResponse.code === 200 && templateResponse.data) {
      projectTemplates.value = templateResponse.data.map((item: ApiProjectTemplate) => ({
        id: item.id || 0,
        domain: item.domain,
        name: item.name,
        description: item.description,
        requirements: JSON.parse(item.requirements || '[]'),
        commonFeatures: JSON.parse(item.commonFeatures || '[]')
      }))
    }
    
    // 加载定制模拟实战
    const practiceResponse = await getAllCustomPractices()
    if (practiceResponse.code === 200 && practiceResponse.data) {
      customPractices.value = practiceResponse.data.map((item: ApiCustomPractice) => ({
        id: item.id,
        practiceName: item.practiceName,
        scenario: item.scenarioType || '', // 后端字段是 scenarioType，前端显示用 scenario
        scenarioType: item.scenarioType,
        scenarioDetail: item.scenarioDetail,
        difficulty: item.difficulty,
        background: item.background,
        description: item.description,
        timeLimit: item.timeLimit,
        tags: item.tags,
        targetSkillsList: item.targetSkills ? JSON.parse(item.targetSkills) : [],
        techStack: item.techStack,
        constraints: item.constraints,
        practiceContentList: item.practiceContent ? JSON.parse(item.practiceContent) : [],
        evaluationCriteriaList: item.evaluationCriteria ? JSON.parse(item.evaluationCriteria) : [],
        referenceMaterials: item.referenceMaterials,
        compatibilityNotes: item.compatibilityNotes
      }))
    }
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

onMounted(() => {
  loadData()
})

onActivated(() => {
  loadData()
})

// ========== 通用训练赛模板操作 ==========
const openAddTemplateModal = () => {
  isEditingTemplate.value = false
  templateFormData.value = {
    id: 0,
    domain: '',
    name: '',
    description: '',
    requirements: [],
    commonFeatures: []
  }
  showTemplateFormModal.value = true
}

const openEditTemplateModal = (template: ProjectTemplate) => {
  isEditingTemplate.value = true
  templateFormData.value = {
    id: template.id,
    domain: template.domain,
    name: template.name,
    description: template.description,
    requirements: [...template.requirements],
    commonFeatures: [...template.commonFeatures]
  }
  showTemplateFormModal.value = true
}

const closeTemplateFormModal = () => {
  showTemplateFormModal.value = false
}

const saveTemplate = async () => {
  try {
    const templateData: ApiProjectTemplate = {
      id: isEditingTemplate.value ? templateFormData.value.id : undefined,
      domain: templateFormData.value.domain,
      name: templateFormData.value.name,
      description: templateFormData.value.description,
      requirements: JSON.stringify(templateFormData.value.requirements),
      commonFeatures: JSON.stringify(templateFormData.value.commonFeatures)
    }
    
    let response
    if (isEditingTemplate.value) {
      response = await updateProjectTemplate(templateData)
    } else {
      response = await createProjectTemplate(templateData)
    }
    
    if (response.code === 200) {
      await loadData()
      closeTemplateFormModal()
    } else {
      alert(response.message || '操作失败')
    }
  } catch (error) {
    console.error('保存模板失败:', error)
    alert('保存失败，请重试')
  }
}

const deleteTemplate = async (id: number) => {
  if (confirm('确定要删除这个模板吗？')) {
    try {
      const response = await deleteProjectTemplate(id)
      if (response.code === 200) {
        await loadData()
      } else {
        alert(response.message || '删除失败')
      }
    } catch (error) {
      console.error('删除模板失败:', error)
      alert('删除失败，请重试')
    }
  }
}

const openTemplateDetail = (template: ProjectTemplate) => {
  selectedTemplateDetail.value = template
  showTemplateDetailModal.value = true
}

const closeTemplateDetail = () => {
  showTemplateDetailModal.value = false
  selectedTemplateDetail.value = null
}

const addRequirement = () => {
  templateFormData.value.requirements.push({ name: '', desc: '' })
}

const removeRequirement = (index: number) => {
  templateFormData.value.requirements.splice(index, 1)
}

const addFeature = () => {
  templateFormData.value.commonFeatures.push({ name: '', desc: '' })
}

const removeFeature = (index: number) => {
  templateFormData.value.commonFeatures.splice(index, 1)
}

// ========== 定制模拟实战操作 ==========
const openAddPracticeModal = () => {
  isEditingPractice.value = false
  practiceFormData.value = {
    id: 0,
    practiceName: '',
    scenario: '',
    difficulty: '',
    description: '',
    timeLimit: undefined,
    tags: '',
    targetSkillsList: [],
    practiceContentList: [],
    evaluationCriteriaList: []
  }
  showPracticeFormModal.value = true
}

const openEditPracticeModal = (practice: any) => {
  isEditingPractice.value = true
  practiceFormData.value = {
    id: practice.id,
    practiceName: practice.practiceName,
    scenario: practice.scenario || practice.scenarioType || '', // 兼容两种字段名
    difficulty: practice.difficulty || '',
    description: practice.description,
    timeLimit: practice.timeLimit,
    tags: practice.tags || '',
    targetSkillsList: practice.targetSkillsList || [],
    practiceContentList: practice.practiceContentList || [],
    evaluationCriteriaList: practice.evaluationCriteriaList || []
  }
  showPracticeFormModal.value = true
}

const closePracticeFormModal = () => {
  showPracticeFormModal.value = false
}

const savePractice = async () => {
  try {
    const practiceData: ApiCustomPractice = {
      id: isEditingPractice.value ? practiceFormData.value.id : undefined,
      practiceName: practiceFormData.value.practiceName,
      scenarioType: practiceFormData.value.scenario, // 前端用 scenario，后端是 scenarioType
      scenarioDetail: '',
      difficulty: practiceFormData.value.difficulty,
      background: '',
      description: practiceFormData.value.description,
      targetSkills: JSON.stringify(practiceFormData.value.targetSkillsList),
      techStack: '',
      constraints: '',
      practiceContent: JSON.stringify(practiceFormData.value.practiceContentList),
      evaluationCriteria: JSON.stringify(practiceFormData.value.evaluationCriteriaList),
      referenceMaterials: '',
      timeLimit: practiceFormData.value.timeLimit,
      compatibilityNotes: '',
      tags: practiceFormData.value.tags
    }
    
    let response
    if (isEditingPractice.value) {
      response = await updateCustomPractice(practiceData)
    } else {
      response = await createCustomPractice(practiceData)
    }
    
    if (response.code === 200) {
      await loadData()
      closePracticeFormModal()
      alert(isEditingPractice.value ? '更新成功' : '创建成功')
    } else {
      alert(response.message || '操作失败')
    }
  } catch (error) {
    console.error('保存实战方案失败:', error)
    alert('保存失败，请重试')
  }
}

const deletePractice = async (id: number) => {
  if (confirm('确定要删除这个专项实战吗？')) {
    try {
      const response = await deleteCustomPractice(id)
      if (response.code === 200) {
        await loadData()
        alert('删除成功')
      } else {
        alert(response.message || '删除失败')
      }
    } catch (error) {
      console.error('删除实战方案失败:', error)
      alert('删除失败，请重试')
    }
  }
}

const openPracticeDetail = (practice: any) => {
  selectedPracticeDetail.value = practice
  showPracticeDetailModal.value = true
}

const closePracticeDetail = () => {
  showPracticeDetailModal.value = false
  selectedPracticeDetail.value = null
}

const addSkill = () => {
  practiceFormData.value.targetSkillsList.push('')
}

const removeSkill = (index: number) => {
  practiceFormData.value.targetSkillsList.splice(index, 1)
}

const addContent = () => {
  practiceFormData.value.practiceContentList.push({ title: '', description: '' })
}

const removeContent = (index: number) => {
  practiceFormData.value.practiceContentList.splice(index, 1)
}

const addCriteria = () => {
  practiceFormData.value.evaluationCriteriaList.push({ name: '', weight: 0, standard: '' })
}

const removeCriteria = (index: number) => {
  practiceFormData.value.evaluationCriteriaList.splice(index, 1)
}
</script>

<style scoped>
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
