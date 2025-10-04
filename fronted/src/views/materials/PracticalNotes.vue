<template>
  <div class="p-6 bg-gradient-to-br from-gray-50 to-blue-50 min-h-screen">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <div class="w-12 h-12 bg-gradient-to-br from-orange-500 to-red-600 rounded-xl flex items-center justify-center shadow-lg">
          <i class="fas fa-rocket text-white text-2xl"></i>
        </div>
        <div>
          <h1 class="text-3xl font-bold text-gray-900">实战笔记</h1>
          <p class="text-gray-600">根据实战定制需求做项目 · 实践出真知</p>
        </div>
      </div>
    </div>

    <!-- 核心数据展示 -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <!-- 项目模板数 -->
      <div class="bg-white rounded-2xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-all duration-300 group">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">项目模板</p>
            <p class="text-3xl font-bold text-gray-900">{{ projectTemplates.length }}</p>
            <p class="text-xs text-green-600 font-medium mt-1">
              <i class="fas fa-arrow-up text-xs mr-1"></i>+{{ Math.floor(projectTemplates.length * 0.2) }} 本月
            </p>
          </div>
          <div class="p-4 bg-gradient-to-br from-blue-500 to-blue-600 rounded-xl shadow-md group-hover:shadow-lg transition-shadow">
            <i class="fas fa-layer-group text-white text-2xl"></i>
          </div>
        </div>
      </div>

      <!-- 实战笔记数 -->
      <div class="bg-white rounded-2xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-all duration-300 group">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">实战笔记</p>
            <p class="text-3xl font-bold text-gray-900">{{ practicalNotes.length }}</p>
            <p class="text-xs text-green-600 font-medium mt-1">
              <i class="fas fa-arrow-up text-xs mr-1"></i>+{{ thisMonthNotes.length }} 本月
            </p>
          </div>
          <div class="p-4 bg-gradient-to-br from-green-500 to-green-600 rounded-xl shadow-md group-hover:shadow-lg transition-shadow">
            <i class="fas fa-code text-white text-2xl"></i>
          </div>
        </div>
      </div>

      <!-- 技术实现数 -->
      <div class="bg-white rounded-2xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-all duration-300 group">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600 mb-1">技术实现</p>
            <p class="text-3xl font-bold text-gray-900">{{ projectImplementations.length }}</p>
            <p class="text-xs text-blue-600 font-medium mt-1">
              <i class="fas fa-tools text-xs mr-1"></i>{{ uniqueTechStacks.length }} 种技术栈
            </p>
          </div>
          <div class="p-4 bg-gradient-to-br from-purple-500 to-purple-600 rounded-xl shadow-md group-hover:shadow-lg transition-shadow">
            <i class="fas fa-cogs text-white text-2xl"></i>
          </div>
        </div>
      </div>

      <!-- 阅读笔记入口 -->
      <div 
        @click="showNotesList = true"
        class="group relative cursor-pointer bg-gradient-to-br from-orange-50 to-red-50 border-2 border-orange-200 rounded-2xl p-6 hover:shadow-xl hover:scale-[1.02] transition-all duration-300"
      >
        <!-- 背景装饰 -->
        <div class="absolute top-0 right-0 w-20 h-20 bg-gradient-to-br from-orange-200 to-transparent rounded-full -translate-y-10 translate-x-10 opacity-60"></div>
        
        <div class="relative z-10">
          <div class="flex items-center justify-between mb-4">
            <div>
              <p class="text-lg font-bold text-gray-900">阅读笔记</p>
              <p class="text-sm text-gray-600">浏览所有实战记录</p>
            </div>
            <div class="p-3 bg-gradient-to-br from-orange-500 to-red-600 rounded-xl shadow-md group-hover:shadow-lg transition-shadow">
              <i class="fas fa-book-open text-white text-xl"></i>
            </div>
          </div>

          <!-- 快捷信息 -->
          <div class="flex items-center justify-between text-sm">
            <span class="text-gray-600">{{ practicalNotes.length }} 条记录</span>
            <span class="inline-flex items-center bg-gradient-to-r from-orange-500 to-red-600 text-white px-3 py-1.5 rounded-full text-xs font-medium shadow-sm group-hover:shadow-md transition-shadow">
              立即查看 <i class="fas fa-arrow-right ml-1"></i>
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- 实战概览仪表盘 -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
      <!-- 项目定制进度 -->
      <div class="bg-white rounded-2xl shadow-lg p-6 border border-gray-100">
        <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
          <i class="fas fa-chart-line text-blue-600 mr-2"></i>
          项目定制进度
        </h3>
        <div class="space-y-4">
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-600">模板设计</span>
            <div class="flex items-center space-x-2">
              <div class="w-20 bg-gray-200 rounded-full h-2">
                <div class="bg-blue-600 h-2 rounded-full" style="width: 85%"></div>
              </div>
              <span class="text-xs font-medium text-blue-600">85%</span>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-600">需求分析</span>
            <div class="flex items-center space-x-2">
              <div class="w-20 bg-gray-200 rounded-full h-2">
                <div class="bg-green-600 h-2 rounded-full" style="width: 92%"></div>
              </div>
              <span class="text-xs font-medium text-green-600">92%</span>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-600">技术实现</span>
            <div class="flex items-center space-x-2">
              <div class="w-20 bg-gray-200 rounded-full h-2">
                <div class="bg-purple-600 h-2 rounded-full" style="width: 78%"></div>
              </div>
              <span class="text-xs font-medium text-purple-600">78%</span>
            </div>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-gray-600">项目交付</span>
            <div class="flex items-center space-x-2">
              <div class="w-20 bg-gray-200 rounded-full h-2">
                <div class="bg-orange-600 h-2 rounded-full" style="width: 65%"></div>
              </div>
              <span class="text-xs font-medium text-orange-600">65%</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 热门技术栈 -->
      <div class="bg-white rounded-2xl shadow-lg p-6 border border-gray-100">
        <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
          <i class="fas fa-fire text-red-600 mr-2"></i>
          热门技术栈
        </h3>
        <div class="space-y-3">
          <div v-for="(tech, index) in uniqueTechStacks.slice(0, 5)" :key="tech" 
               class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
            <div class="flex items-center space-x-3">
              <div class="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-lg flex items-center justify-center text-white text-sm font-bold">
                {{ index + 1 }}
              </div>
              <span class="text-sm font-medium text-gray-900">{{ tech }}</span>
            </div>
            <div class="flex items-center space-x-2">
              <div class="w-12 bg-gray-200 rounded-full h-2">
                <div class="bg-gradient-to-r from-blue-500 to-purple-600 h-2 rounded-full" 
                     :style="{ width: Math.max(30, 100 - index * 15) + '%' }"></div>
              </div>
              <span class="text-xs text-gray-500">{{ Math.max(2, 10 - index * 2) }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 最近活动 -->
      <div class="bg-white rounded-2xl shadow-lg p-6 border border-gray-100">
        <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
          <i class="fas fa-clock text-green-600 mr-2"></i>
          最近活动
        </h3>
        <div class="space-y-3">
          <div v-for="note in getRecentNotes().slice(0, 4)" :key="note.id" 
               class="p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors cursor-pointer group"
               @click="viewRecentNoteDetail(note)">
            <div class="flex items-start justify-between">
              <div class="flex-1">
                <h4 class="text-sm font-medium text-gray-900 line-clamp-1 group-hover:text-blue-600 transition-colors">{{ note.title }}</h4>
                <p class="text-xs text-gray-600 mt-1 line-clamp-1">{{ note.content }}</p>
                <div class="flex items-center mt-2 space-x-2">
                  <span v-if="note.template" class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-medium">
                    {{ note.template }}
                  </span>
                  <span class="text-xs text-gray-500">{{ note.time }}</span>
                </div>
              </div>
              <div class="w-2 h-2 bg-green-500 rounded-full ml-3 mt-2 flex-shrink-0"></div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 项目模板管理区域 -->
    <div class="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden mb-8">
      <div class="bg-gradient-to-r from-blue-600 to-purple-600 p-6">
        <div class="flex items-center justify-between">
          <div>
            <h2 class="text-2xl font-bold text-white mb-2">项目模板管理</h2>
            <p class="text-blue-100">创建和管理您的项目模板，定制化开发需求 • 鼠标悬停模板卡片可编辑</p>
          </div>
          <button @click="openAddModal" 
                  class="bg-white/20 hover:bg-white/30 text-white px-6 py-3 rounded-xl flex items-center space-x-2 transition-all duration-300 hover:scale-105 shadow-lg">
            <i class="fas fa-plus"></i>
            <span class="font-medium">创建模板</span>
          </button>
        </div>
      </div>

      <!-- 模板统计 -->
      <div class="p-6 bg-gray-50 border-b border-gray-100">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div class="text-center">
            <div class="text-2xl font-bold text-blue-600">{{ projectTemplates.length }}</div>
            <div class="text-sm text-gray-600">总模板数</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-green-600">{{ totalFunctionalRequirements }}</div>
            <div class="text-sm text-gray-600">功能需求</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-purple-600">{{ totalNonFunctionalRequirements }}</div>
            <div class="text-sm text-gray-600">质量需求</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-orange-600">{{ projectImplementations.length }}</div>
            <div class="text-sm text-gray-600">技术实现</div>
          </div>
        </div>
      </div>

      <!-- 模板列表 -->
      <div class="p-6">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="template in projectTemplates" :key="template.id" 
               class="group relative bg-white border-2 border-gray-100 rounded-xl p-6 hover:border-blue-300 hover:shadow-lg transition-all duration-300">
            
            <!-- 操作按钮 -->
            <div class="absolute top-4 right-4 opacity-0 group-hover:opacity-100 transition-opacity">
              <div class="flex space-x-2">
                <button @click.stop="openEditModal(template)" 
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
              <div class="flex items-center justify-between mb-2">
                <h3 class="text-lg font-bold text-gray-900 group-hover:text-blue-600 transition-colors">{{ template.name }}</h3>
                <span class="px-3 py-1 rounded-full text-xs font-medium bg-gradient-to-r from-blue-500 to-purple-600 text-white">
                  {{ template.domain }}
                </span>
              </div>
              <p class="text-gray-600 text-sm line-clamp-2">{{ template.description }}</p>
            </div>

            <!-- 模板统计 -->
            <div class="grid grid-cols-2 gap-4 mb-4">
              <div class="text-center p-3 bg-blue-50 rounded-lg">
                <div class="text-lg font-bold text-blue-600">{{ template.requirements.length }}</div>
                <div class="text-xs text-gray-600">功能需求</div>
              </div>
              <div class="text-center p-3 bg-green-50 rounded-lg">
                <div class="text-lg font-bold text-green-600">{{ getImplementationCount(template.id) }}</div>
                <div class="text-xs text-gray-600">技术实现</div>
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
            <button @click="openTemplate(template)" 
                    class="w-full bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white py-3 rounded-lg font-medium transition-all duration-300 hover:shadow-lg">
              查看详情
            </button>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-if="projectTemplates.length === 0" class="text-center py-16">
          <div class="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <i class="fas fa-layer-group text-3xl text-gray-400"></i>
          </div>
          <h3 class="text-lg font-medium text-gray-900 mb-2">暂无项目模板</h3>
          <p class="text-gray-600 mb-6">创建您的第一个项目模板，开始定制化开发</p>
          <button @click="openAddModal" 
                  class="bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white px-6 py-3 rounded-lg font-medium transition-all duration-300 hover:shadow-lg">
            创建模板
          </button>
        </div>
      </div>
    </div>

    <!-- 新增/编辑模板弹窗 -->
    <transition name="modal-fade">
      <div v-if="showFormModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-4xl max-h-[90vh] overflow-y-auto">
          <!-- 弹窗头部 -->
          <div class="bg-gradient-to-r from-blue-600 to-purple-600 p-6 rounded-t-2xl">
            <div class="flex items-center justify-between">
              <div>
                <h2 class="text-2xl font-bold text-white">{{ isEditing ? '编辑项目模板' : '创建项目模板' }}</h2>
                <p class="text-blue-100 mt-1">定制化配置您的项目需求和技术规格</p>
              </div>
              <button @click="closeFormModal" class="text-white/80 hover:text-white transition-colors">
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
                  <label class="block text-sm font-medium text-gray-700 mb-2">项目分类</label>
                  <input v-model="formData.domain" type="text" required 
                         class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" 
                         placeholder="如：Web应用、移动端、数据分析">
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">模板名称</label>
                  <input v-model="formData.name" type="text" required 
                         class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" 
                         placeholder="如：通用后台管理系统">
                </div>
              </div>
              
              <div class="mt-4">
                <label class="block text-sm font-medium text-gray-700 mb-2">项目描述</label>
                <textarea v-model="formData.description" rows="3" required 
                          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" 
                          placeholder="详细描述该模板的适用场景、目标用户和核心价值"></textarea>
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
                <div v-for="(req, index) in formData.requirements" :key="index" 
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
                <div v-for="(feature, index) in formData.commonFeatures" :key="index" 
                     class="space-y-3 p-4 bg-white rounded-lg border border-gray-200">
                  <div class="flex gap-3">
                    <input v-model="formData.commonFeatures[index].name" type="text" placeholder="非功能性需求名称" 
                           class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent">
                    <button type="button" @click="removeFeature(index)" 
                            class="px-3 py-2 bg-red-50 text-red-600 rounded-lg hover:bg-red-100 transition-colors">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                  <textarea v-model="formData.commonFeatures[index].desc" placeholder="详细描述该非功能性需求的具体要求..." 
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
                      class="flex-1 bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white py-4 rounded-xl font-medium transition-all duration-300 hover:shadow-lg">
                <i class="fas fa-save mr-2"></i>
                {{ isEditing ? '保存修改' : '创建模板' }}
              </button>
              <button type="button" @click="closeFormModal" 
                      class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 py-4 rounded-xl font-medium transition-colors">
                <i class="fas fa-times mr-2"></i>
                取消
              </button>
            </div>
          </form>
        </div>
      </div>
    </transition>

    <!-- 模板详情弹窗 -->
    <transition name="modal-fade">
      <div v-if="showDetail" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-5xl max-h-[90vh] overflow-y-auto">
          <!-- 详情头部 -->
          <div class="bg-gradient-to-r from-blue-600 to-purple-600 p-6 rounded-t-2xl">
            <div class="flex items-center justify-between">
              <div>
                <h2 class="text-2xl font-bold text-white">{{ selectedTemplateDetail?.name }}</h2>
                <div class="flex items-center mt-2 space-x-3">
                  <span class="px-3 py-1 rounded-full text-xs font-medium bg-white/20 text-white">
                    {{ selectedTemplateDetail?.domain }}
                  </span>
                  <span class="text-blue-100 text-sm">{{ selectedTemplateDetail?.description }}</span>
                </div>
              </div>
              <button @click="closeDetail" class="text-white/80 hover:text-white transition-colors">
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
          </div>
          
          <div class="p-6 space-y-6">
            <!-- 需求概览 -->
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
              <div class="text-center p-4 bg-blue-50 rounded-xl">
                <div class="text-2xl font-bold text-blue-600">{{ selectedTemplateDetail?.requirements?.length || 0 }}</div>
                <div class="text-sm text-gray-600">功能需求</div>
              </div>
              <div class="text-center p-4 bg-green-50 rounded-xl">
                <div class="text-2xl font-bold text-green-600">{{ selectedTemplateDetail?.commonFeatures?.length || 0 }}</div>
                <div class="text-sm text-gray-600">质量需求</div>
              </div>
              <div class="text-center p-4 bg-purple-50 rounded-xl">
                <div class="text-2xl font-bold text-purple-600">{{ implementations.length }}</div>
                <div class="text-sm text-gray-600">技术实现</div>
              </div>
              <div class="text-center p-4 bg-orange-50 rounded-xl">
                <div class="text-2xl font-bold text-orange-600">{{ uniqueTechStacks.length }}</div>
                <div class="text-sm text-gray-600">技术栈</div>
              </div>
            </div>

            <!-- 功能性需求详情 -->
            <div class="bg-green-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-list-check text-green-600 mr-2"></i>
                功能性需求
              </h3>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div v-for="req in selectedTemplateDetail?.requirements || []" :key="req.name" 
                     class="bg-white p-4 rounded-lg border border-green-200">
                  <h4 class="font-medium text-gray-900 mb-2">{{ req.name }}</h4>
                  <p class="text-sm text-gray-600">{{ req.desc }}</p>
                </div>
              </div>
            </div>

            <!-- 非功能性需求详情 -->
            <div class="bg-purple-50 rounded-xl p-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4 flex items-center">
                <i class="fas fa-shield-check text-purple-600 mr-2"></i>
                非功能性需求
              </h3>
              <div class="space-y-3">
                <div v-for="feature in selectedTemplateDetail?.commonFeatures || []" :key="feature.name" 
                     class="bg-white border border-purple-200 rounded-lg p-4">
                  <h4 class="font-semibold text-purple-700 mb-2">{{ feature.name }}</h4>
                  <p class="text-gray-600 text-sm">{{ feature.desc }}</p>
                </div>
              </div>
            </div>


          </div>
        </div>
      </div>
    </transition>

    <!-- 笔记列表弹窗 -->
    <transition name="modal-fade">
      <div v-if="showNotesList" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-7xl max-h-[90vh] flex flex-col">
          <!-- 弹窗头部 -->
          <div class="bg-gradient-to-r from-orange-500 to-red-600 p-6 rounded-t-2xl">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-4">
                <div class="w-12 h-12 bg-white/20 rounded-xl flex items-center justify-center">
                  <i class="fas fa-rocket text-white text-2xl"></i>
                </div>
                <div>
                  <h3 class="text-2xl font-bold text-white">实战笔记列表</h3>
                  <p class="text-orange-100">{{ practicalNotes.length }} 条实战记录 · 持续学习成长</p>
                </div>
              </div>
              <button @click="showNotesList = false" class="text-white/80 hover:text-white transition-colors">
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
          </div>
          
          <!-- 搜索和筛选 -->
          <div class="p-6 border-b border-gray-200 bg-gray-50">
            <div class="flex flex-col md:flex-row gap-4">
              <div class="flex-1">
                <div class="relative">
                  <i class="fas fa-search absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input v-model="searchQuery" type="text" placeholder="搜索笔记标题、内容或技术栈..."
                         class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all">
                </div>
              </div>
              <div class="flex space-x-3">
                <select v-model="selectedTemplate" 
                        class="px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-orange-500 focus:border-orange-500 bg-white">
                  <option :value="null">全部模板</option>
                  <option v-for="template in projectTemplates" :key="template.id" :value="template">
                    {{ template.name }}
                  </option>
                </select>
              </div>
            </div>
          </div>
          
          <!-- 笔记列表 -->
          <div class="flex-1 overflow-y-auto p-6">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              <div v-for="note in filteredNotes" :key="note.id"
                   class="group bg-white border border-gray-200 rounded-xl p-6 hover:border-orange-300 hover:shadow-lg cursor-pointer transition-all duration-300"
                   @click="viewNoteDetail(note)">
                
                <!-- 笔记头部 -->
                <div class="mb-4">
                  <h5 class="text-lg font-bold text-gray-900 line-clamp-2 group-hover:text-orange-600 transition-colors mb-2">
                    {{ note.title }}
                  </h5>
                  <p class="text-sm text-gray-600 line-clamp-2">{{ note.summary }}</p>
                </div>

                <!-- 技术栈标签 -->
                <div class="flex flex-wrap gap-2 mb-4">
                  <span v-for="tech in note.techStack?.slice(0, 3)" :key="tech" 
                        class="bg-gradient-to-r from-blue-500 to-purple-600 text-white px-3 py-1 rounded-full text-xs font-medium">
                    {{ tech }}
                  </span>
                  <span v-if="note.techStack && note.techStack.length > 3" 
                        class="bg-gray-100 text-gray-600 px-3 py-1 rounded-full text-xs font-medium">
                    +{{ note.techStack.length - 3 }}
                  </span>
                </div>

                <!-- 底部信息 -->
                <div class="flex items-center justify-between pt-4 border-t border-gray-100">
                  <span class="text-xs text-gray-500">{{ formatDate(note.createTime) }}</span>
                  <div class="flex items-center space-x-2">
                    <span v-if="note.projectName" class="bg-orange-100 text-orange-700 px-2 py-1 rounded text-xs font-medium">
                      {{ note.projectName }}
                    </span>
                    <i class="fas fa-arrow-right text-orange-500 group-hover:translate-x-1 transition-transform"></i>
                  </div>
                </div>
              </div>
              
              <!-- 空状态 -->
              <div v-if="filteredNotes.length === 0" class="col-span-full text-center py-16">
                <div class="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                  <i class="fas fa-search text-3xl text-gray-400"></i>
                </div>
                <h3 class="text-lg font-medium text-gray-900 mb-2">未找到匹配的笔记</h3>
                <p class="text-gray-600">请尝试调整搜索条件或筛选器</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>

    <!-- 笔记详情弹窗 -->
    <transition name="modal-fade">
      <div v-if="showNoteDetail" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-4xl max-h-[90vh] overflow-y-auto">
          <!-- 详情头部 -->
          <div class="bg-gradient-to-r from-orange-500 to-red-600 p-6 rounded-t-2xl">
            <div class="flex items-center justify-between">
              <div>
                <h3 class="text-2xl font-bold text-white">{{ selectedNote?.title }}</h3>
                <div class="flex items-center mt-2 space-x-3">
                  <span v-if="selectedNote?.projectName" class="px-3 py-1 rounded-full text-xs font-medium bg-white/20 text-white">
                    {{ selectedNote.projectName }}
                  </span>
                  <span class="text-orange-100 text-sm">{{ formatDate(selectedNote?.createTime || '') }}</span>
                </div>
              </div>
              <button @click="showNoteDetail = false" class="text-white/80 hover:text-white transition-colors">
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
          </div>
          
          <div v-if="selectedNote" class="p-6 space-y-6">
            <!-- 摘要 -->
            <div class="bg-orange-50 rounded-xl p-6">
              <h4 class="text-lg font-bold text-gray-900 mb-3 flex items-center">
                <i class="fas fa-file-alt text-orange-600 mr-2"></i>
                项目摘要
              </h4>
              <p class="text-gray-700">{{ selectedNote.summary }}</p>
            </div>
            
            <!-- 详细内容 -->
            <div class="bg-white border border-gray-200 rounded-xl p-6">
              <h4 class="text-lg font-bold text-gray-900 mb-3 flex items-center">
                <i class="fas fa-clipboard-list text-blue-600 mr-2"></i>
                详细内容
              </h4>
              <div class="text-gray-700 prose prose-sm max-w-none" v-html="selectedNote.content"></div>
            </div>
            
            <!-- 技术栈信息 -->
            <div v-if="selectedNote.techStack && selectedNote.techStack.length" class="bg-blue-50 rounded-xl p-6">
              <h4 class="text-lg font-bold text-gray-900 mb-3 flex items-center">
                <i class="fas fa-code text-blue-600 mr-2"></i>
                技术栈
              </h4>
              <div class="flex flex-wrap gap-3">
                <span v-for="tech in selectedNote.techStack" :key="tech" 
                      class="bg-gradient-to-r from-blue-500 to-purple-600 text-white px-4 py-2 rounded-lg text-sm font-medium">
                  {{ tech }}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { getAllNotes } from '@/services/noteService'

type Note = {
  id: number
  title: string
  content?: string
  type: string
  tags?: string[]
  materialIds?: string
  recCreator?: string
  recCreateTime?: string
  createTime?: string
}

// 响应式笔记数据
const allNotesData = ref<Note[]>([])

// 加载笔记数据
onMounted(async () => {
  try {
    allNotesData.value = await getAllNotes()
  } catch (error) {
    console.error('加载实战笔记失败:', error)
  }
})

// 保留mock数据作为公共配置（项目模板和实现方式属于公共配置数据，不需要用户隔离）
import { projectTemplates as initialProjectTemplates } from '@/mock/projectTemplates'
import { projectImplementations } from '@/mock/projectImplementations'
import type { ProjectTemplate, ProjectRequirement } from '@/mock/projectTemplates'

const showDetail = ref(false)
const showFormModal = ref(false)
const isEditing = ref(false)
const selectedTemplateDetail = ref<ProjectTemplate | null>(null)
const implementations = ref<typeof projectImplementations>([])
const showNotesList = ref(false)
const showNoteDetail = ref(false)
const selectedNote = ref<Note | null>(null)
const searchQuery = ref('')
const selectedTemplate = ref<ProjectTemplate | null>(null)

// 响应式的项目模板数据
const projectTemplates = ref<ProjectTemplate[]>([...initialProjectTemplates])

// 表单数据
const formData = ref({
  id: 0,
  domain: '',
  name: '',
  description: '',
  requirements: [] as ProjectRequirement[],
  commonFeatures: [] as ProjectRequirement[]
})

// 获取唯一技术栈列表
const uniqueTechStacks = computed(() => {
  return [...new Set(projectImplementations.map(i => i.techStack))]
})

// 获取最近笔记
const getRecentNotes = () => {
  const practicalNotes = allNotesData.value.filter(n => n.type === 'practical')
  return practicalNotes
    .sort((a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime())
    .slice(0, 6)
    .map(note => ({
      id: note.id,
      title: note.title,
      content: note.summary,
      time: formatDate(note.createTime),
      techStack: note.techStack || [],
      template: note.projectName || ''
    }))
}

// 获取模板的实现数量
const getImplementationCount = (templateId: number) => {
  return projectImplementations.filter(i => i.templateId === templateId).length
}

// 打开新增模态框
const openAddModal = () => {
  isEditing.value = false
  formData.value = {
    id: 0,
    domain: '',
    name: '',
    description: '',
    requirements: [],
    commonFeatures: []
  }
  showFormModal.value = true
}

// 打开编辑模态框
const openEditModal = (template: ProjectTemplate) => {
  isEditing.value = true
  formData.value = {
    id: template.id,
    domain: template.domain,
    name: template.name,
    description: template.description,
    requirements: [...template.requirements],
    commonFeatures: [...template.commonFeatures]
  }
  showFormModal.value = true
}

// 关闭表单模态框
const closeFormModal = () => {
  showFormModal.value = false
  isEditing.value = false
  formData.value = {
    id: 0,
    domain: '',
    name: '',
    description: '',
    requirements: [],
    commonFeatures: []
  }
}

// 添加需求点
const addRequirement = () => {
  formData.value.requirements.push({ name: '', desc: '' })
}

// 移除需求点
const removeRequirement = (index: number) => {
  formData.value.requirements.splice(index, 1)
}

// 添加功能
const addFeature = () => {
  formData.value.commonFeatures.push({ name: '', desc: '' })
}

// 移除功能
const removeFeature = (index: number) => {
  formData.value.commonFeatures.splice(index, 1)
}

// 保存模板
const saveTemplate = () => {
  // 这里应该调用API保存数据
  if (isEditing.value) {
    const index = projectTemplates.value.findIndex(t => t.id === formData.value.id)
    if (index !== -1) {
      projectTemplates.value[index] = { ...formData.value }
    }
  } else {
    const newId = Math.max(...projectTemplates.value.map(t => t.id)) + 1
    projectTemplates.value.push({
      ...formData.value,
      id: newId
    })
  }
  
  closeFormModal()
}

// 删除模板
const deleteTemplate = (id: number) => {
  if (confirm('确定要删除这个模板吗？')) {
    const index = projectTemplates.value.findIndex(t => t.id === id)
    if (index !== -1) {
      projectTemplates.value.splice(index, 1)
    }
  }
}

function openTemplate(template: ProjectTemplate) {
  selectedTemplateDetail.value = template
  implementations.value = projectImplementations.filter(i => i.templateId === template.id)
  showDetail.value = true
}

function closeDetail() {
  showDetail.value = false
  selectedTemplateDetail.value = null
  implementations.value = []
}

const totalFunctionalRequirements = computed(() => {
  return projectTemplates.value.reduce((sum, tpl) => sum + (tpl.requirements?.length || 0), 0)
})

const totalNonFunctionalRequirements = computed(() => {
  return projectTemplates.value.reduce((sum, tpl) => sum + (tpl.commonFeatures?.length || 0), 0)
})

// 获取本月新增笔记
const thisMonthNotes = computed(() => {
  const now = new Date()
  const practicalNotes = allNotesData.value.filter(n => n.type === 'practical')
  return practicalNotes.filter(n => {
    const noteDate = new Date(n.recCreateTime || n.createTime || '')
    return noteDate.getMonth() === now.getMonth() && noteDate.getFullYear() === now.getFullYear()
  })
})

// 获取过滤后的笔记列表
const filteredNotes = computed(() => {
  const practicalNotes = allNotesData.value.filter(n => n.type === 'practical')
  let notes = practicalNotes.slice()

  if (selectedTemplate.value) {
    const selectedTemplateName = selectedTemplate.value.name
    notes = notes.filter(n => n.title.includes(selectedTemplateName) || n.summary.includes(selectedTemplateName))
  }

  if (searchQuery.value) {
    notes = notes.filter(n => 
      n.title.includes(searchQuery.value) || 
      n.summary.includes(searchQuery.value) ||
      n.content.includes(searchQuery.value) ||
      (n.techStack && n.techStack.some(tech => tech.includes(searchQuery.value)))
    )
  }

  return notes
})

// 查看笔记详情
const viewNoteDetail = (note: any) => {
  selectedNote.value = note
  showNoteDetail.value = true
}

// 查看最近笔记详情
const viewRecentNoteDetail = (recentNote: any) => {
  const originalNote = allNotesData.value.find(n => n.id === recentNote.id)
  if (originalNote) {
    selectedNote.value = originalNote
    showNoteDetail.value = true
  }
}

// 格式化日期
const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleDateString()
}

// 添加practicalNotes计算属性
const practicalNotes = computed(() => allNotesData.value.filter(n => n.type === 'practical'))
</script>

<style scoped>
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

.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* 自定义渐变背景 */
.bg-gradient-to-br {
  background-image: linear-gradient(to bottom right, var(--tw-gradient-stops));
}

/* 卡片阴影效果 */
.shadow-card {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

/* 悬停动画 */
.hover\:scale-\[1\.02\]:hover {
  transform: scale(1.02);
}

.group:hover .group-hover\:translate-x-1 {
  transform: translateX(0.25rem);
}
</style> 