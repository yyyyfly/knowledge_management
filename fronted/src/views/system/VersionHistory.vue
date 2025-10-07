<template>
  <div class="p-6">
    <!-- 页面标题 -->
    <div class="mb-6 flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-semibold text-gray-900">版本更新记录</h1>
        <p class="mt-1 text-sm text-gray-600">
          个人知识管理系统的版本迭代历史，记录每个版本的新增功能、优化改进和问题修复。
        </p>
      </div>
      
      <!-- 排序切换按钮 -->
      <div class="flex items-center space-x-2">
        <span class="text-sm text-gray-600">时间顺序：</span>
        <button 
          @click="toggleSortOrder"
          class="px-4 py-2 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors flex items-center space-x-2 shadow-sm"
        >
          <i :class="sortAscending ? 'fas fa-arrow-up' : 'fas fa-arrow-down'" class="text-blue-600"></i>
          <span class="text-sm font-medium">{{ sortAscending ? '从早到晚' : '从晚到早' }}</span>
        </button>
      </div>
    </div>

    <!-- 版本时间线 -->
    <div class="space-y-10">
      <!-- 按大版本分组 -->
      <div v-for="majorGroup in groupedVersions" :key="majorGroup.major" class="relative">
        <!-- 大版本标题卡片 -->
        <div class="mb-6 bg-gradient-to-r from-blue-600 via-purple-600 to-indigo-700 rounded-xl shadow-2xl overflow-hidden">
          <div class="p-6">
            <!-- 标题行 -->
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center space-x-3">
                <div class="w-12 h-12 bg-white/20 rounded-lg flex items-center justify-center backdrop-blur-sm">
                  <i class="fas fa-rocket text-white text-2xl"></i>
                </div>
                <div>
                  <h2 class="text-2xl font-bold text-white">{{ majorGroup.major }} 大版本</h2>
                  <p class="text-blue-100 text-sm">{{ majorGroup.versions.length }} 个迭代版本</p>
                </div>
              </div>
              <div class="px-4 py-2 bg-white/20 rounded-full backdrop-blur-sm">
                <span class="text-white text-sm font-medium">{{ getMajorVersionDateRange(majorGroup.versions) }}</span>
              </div>
            </div>
            
            <!-- 大版本描述 -->
            <div class="bg-white/10 rounded-lg p-4 backdrop-blur-sm">
              <div class="flex items-start space-x-3">
                <i class="fas fa-quote-left text-white/60 text-lg mt-1"></i>
                <p class="text-white text-sm leading-relaxed flex-1">
                  {{ getMajorVersionDescription(majorGroup.major) }}
                </p>
                <i class="fas fa-quote-right text-white/60 text-lg mt-1"></i>
              </div>
            </div>
          </div>
          
          <!-- 底部装饰线 -->
          <div class="h-1 bg-gradient-to-r from-yellow-400 via-pink-400 to-purple-400"></div>
        </div>

        <!-- 时间线容器 -->
        <div class="relative ml-8">
          <!-- 时间线竖线 -->
          <div class="absolute left-0 top-0 bottom-0 w-1 bg-gradient-to-b from-blue-400 via-purple-400 to-pink-400 rounded-full"></div>
          
          <div class="space-y-6 pl-12">
            <!-- 小版本卡片 -->
            <div 
              v-for="(version, index) in majorGroup.versions" 
              :key="version.version"
              class="relative"
            >
              <!-- 时间线节点 -->
              <div class="absolute -left-12 top-4">
                <div :class="[
                  'w-8 h-8 rounded-full flex items-center justify-center shadow-lg border-4 border-white',
                  version.isMajor ? 'bg-gradient-to-br from-blue-500 to-purple-600' : 'bg-gradient-to-br from-purple-400 to-pink-400',
                  version.isLatest && 'ring-4 ring-green-300 ring-opacity-50'
                ]">
                  <i v-if="version.isMajor" class="fas fa-star text-white text-xs"></i>
                  <div v-else class="w-2 h-2 bg-white rounded-full"></div>
                </div>
              </div>

              <!-- 版本内容卡片 -->
              <div :class="[
                'rounded-lg shadow-lg p-6 border-l-4 transform transition-all duration-300 hover:scale-[1.01] hover:shadow-xl',
                version.isMajor ? 'border-blue-500 bg-gradient-to-br from-white via-blue-50 to-purple-50' : 'border-purple-300 bg-white',
                version.isLatest && 'ring-2 ring-green-400 ring-opacity-30'
              ]">
                <div class="flex items-center justify-between mb-4">
                  <div class="flex items-center space-x-3">
                    <span :class="[
                      'px-4 py-1.5 rounded-full text-sm font-bold',
                      version.isMajor ? 'bg-gradient-to-r from-blue-500 to-purple-600 text-white shadow-md' : 'bg-gray-100 text-gray-700'
                    ]">{{ version.version }}</span>
                    <span class="text-sm text-gray-500">{{ version.date }}</span>
                    <span v-if="version.isMajor" class="px-2 py-1 bg-yellow-100 text-yellow-700 rounded text-xs font-medium">
                      <i class="fas fa-crown mr-1"></i>大版本
                    </span>
                  </div>
                  <div class="flex items-center space-x-2">
                    <span v-if="version.isLatest" class="px-2 py-1 bg-green-100 text-green-700 rounded text-xs font-medium">
                      <i class="fas fa-check-circle mr-1"></i>最新版本
                    </span>
                    <span v-if="version.isInitial" class="px-2 py-1 bg-purple-100 text-purple-700 rounded text-xs font-medium">
                      <i class="fas fa-flag mr-1"></i>初始版本
                    </span>
                  </div>
                </div>
            
            <div class="space-y-4">
              <!-- 新增功能 -->
              <div v-if="version.features && version.features.length">
                <h3 class="text-sm font-semibold text-gray-900 mb-2 flex items-center">
                  <i class="fas fa-sparkles text-blue-500 mr-2"></i>
                  {{ version.featuresTitle || '新增功能' }}
                </h3>
                <ul class="space-y-1 text-sm text-gray-600">
                  <li v-for="(feature, idx) in version.features" :key="idx" class="flex items-start">
                    <i class="fas fa-check-circle text-green-500 mr-2 mt-1 flex-shrink-0"></i>
                    <span>{{ feature }}</span>
                  </li>
                </ul>
              </div>

              <!-- 优化改进 -->
              <div v-if="version.improvements && version.improvements.length">
                <h3 class="text-sm font-semibold text-gray-900 mb-2 flex items-center">
                  <i class="fas fa-wrench text-orange-500 mr-2"></i>
                  优化改进
                </h3>
                <ul class="space-y-1 text-sm text-gray-600">
                  <li v-for="(item, idx) in version.improvements" :key="idx" class="flex items-start">
                    <i class="fas fa-arrow-up text-orange-500 mr-2 mt-1 flex-shrink-0"></i>
                    <span>{{ item }}</span>
                  </li>
                </ul>
              </div>

              <!-- 问题修复 -->
              <div v-if="version.bugfixes && version.bugfixes.length">
                <h3 class="text-sm font-semibold text-gray-900 mb-2 flex items-center">
                  <i class="fas fa-bug text-red-500 mr-2"></i>
                  问题修复
                </h3>
                <ul class="space-y-1 text-sm text-gray-600">
                  <li v-for="(bug, idx) in version.bugfixes" :key="idx" class="flex items-start">
                    <i class="fas fa-times-circle text-red-500 mr-2 mt-1 flex-shrink-0"></i>
                    <span>{{ bug }}</span>
                  </li>
                </ul>
              </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 版本说明 -->
    <div class="mt-8 bg-blue-50 border border-blue-200 rounded-lg p-4">
      <div class="flex items-start">
        <i class="fas fa-info-circle text-blue-500 mr-3 mt-1"></i>
        <div class="text-sm text-blue-800">
          <p class="font-medium mb-1">版本号说明</p>
          <p>版本号采用语义化版本规范（Semantic Versioning）：主版本号.次版本号.修订号</p>
          <ul class="mt-2 space-y-1 ml-4">
            <li>• 主版本号：重大功能变更或架构调整</li>
            <li>• 次版本号：新增功能或重要优化</li>
            <li>• 修订号：问题修复和小幅改进</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

// 排序状态：true = 升序（从早到晚），false = 降序（从晚到早）
const sortAscending = ref(true)

// 大版本描述配置
const majorVersionDescriptions: Record<string, string> = {
  'v1': '🚀 基础架构的奠基之作。从零到一构建完整的个人知识管理体系，建立素材组、行动组、首脑组三大核心模块，实现知识积累与项目管理的闭环。这是系统从概念到实现的关键突破，为后续所有功能奠定了坚实基础。',
  'v2': '🔥 智能化与协作的全面革新。引入AI助手、团队协作、知识图谱等颠覆性功能，将个人知识管理提升到智能化、网络化的新高度。这是从工具到平台的质的飞跃。',
  'v3': '🌟 生态化与开放的战略转型。构建插件系统、开放API、跨平台同步等能力，打造知识管理生态圈。这是从封闭到开放、从产品到生态的历史性跨越。'
}

// 版本数据
const versions = ref([
  {
    version: 'v1.0.0',
    date: '2025-09-15',
    timestamp: '2025-09-15',
    isLatest: false,
    isInitial: true,
    isMajor: true, // 大版本
    featuresTitle: '核心功能',
    features: [
      '素材组：6种笔记类型（框架、求学、背诵、刷题、实战、碎片）',
      '行动组：3大行动类别（战争、工程、外交）',
      '首脑组：系统决策、项目管理、时间节点',
      '总览面板：数据统计和可视化展示',
      '日常记录：项目跟踪和素材积累'
    ],
    improvements: [],
    bugfixes: []
  },
  {
    version: 'v1.1.0',
    date: '2025-10-04',
    timestamp: '2025-10-04',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '用户认证系统：JWT 登录鉴权，支持用户注册',
      '荣誉战绩：记录项目成就和里程碑',
      '系统总结：支持阶段性总结和复盘'
    ],
    improvements: [
      'Dashboard 数据可视化增强',
      '笔记编辑器富文本功能完善'
    ],
    bugfixes: []
  },
  {
    version: 'v1.2.0',
    date: '2025-10-06',
    timestamp: '2025-10-06',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '项目模板管理：支持自定义项目模板，快速创建标准化项目',
      '笔记类型扩展：新增实战笔记、背诵笔记等多种笔记类型',
      '版本更新记录：系统版本迭代历史一目了然'
    ],
    improvements: [
      '数据隔离机制：所有用户数据完全隔离，确保隐私安全',
      '前后端完全对接：移除所有 Mock 数据，全部使用真实 API',
      '导航栏优化：调整菜单结构，提升使用体验'
    ],
    bugfixes: [
      '修复任务状态更新不同步的问题',
      '修复总览面板数据统计不准确的问题',
      '修复路由跳转和权限验证的若干问题'
    ]
  },
  {
    version: 'v1.3.0',
    date: '2025-10-07',
    timestamp: '2025-10-07',
    isLatest: true,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '账号设置：全新的账号管理页面，支持修改个人信息和密码',
      '忘记密码：通过邮箱验证重置密码，保障账号安全',
      '注册优化：新增邮箱必填项，手机号可选，为密码找回提供保障',
      '用户信息完善：支持修改昵称、邮箱、手机号等个人资料'
    ],
    improvements: [
      '页面布局优化：登录、注册、忘记密码页面采用独立全屏设计',
      '密码安全增强：修改密码需验证原密码，支持独立的密码重置流程',
      '用户体验提升：实时表单验证，友好的错误提示和成功反馈',
      '导航菜单完善：在系统配置下新增账号设置入口'
    ],
    bugfixes: [
      '修复注册时缺少邮箱字段导致无法找回密码的问题',
      '修复忘记密码页面显示导航栏的问题',
      '修复用户信息更新后本地存储不同步的问题'
    ]
  }
])

// 按大版本分组并排序
const groupedVersions = computed(() => {
  // 先按时间排序所有版本
  const sorted = [...versions.value].sort((a, b) => {
    const dateA = new Date(a.timestamp).getTime()
    const dateB = new Date(b.timestamp).getTime()
    return sortAscending.value ? dateA - dateB : dateB - dateA
  })

  // 按主版本号分组
  const groups: Record<string, any[]> = {}
  sorted.forEach(version => {
    const major = version.version.split('.')[0] // 提取主版本号，如 'v1'
    if (!groups[major]) {
      groups[major] = []
    }
    groups[major].push(version)
  })

  // 转换为数组格式，并计算每个大版本的最早时间
  const result = Object.keys(groups).map(major => {
    const versionsInGroup = groups[major]
    // 找到该大版本中最早的时间戳
    const timestamps = versionsInGroup.map(v => new Date(v.timestamp).getTime())
    const earliestTime = Math.min(...timestamps)
    
    return {
      major,
      versions: versionsInGroup,
      earliestTime
    }
  })

  // 按大版本的最早时间排序（这样能真正体现时间顺序）
  result.sort((a, b) => {
    return sortAscending.value ? a.earliestTime - b.earliestTime : b.earliestTime - a.earliestTime
  })

  return result
})

// 切换排序顺序
const toggleSortOrder = () => {
  sortAscending.value = !sortAscending.value
}

// 获取大版本描述
const getMajorVersionDescription = (major: string): string => {
  return majorVersionDescriptions[major] || '持续迭代优化中...'
}

// 获取大版本时间范围
const getMajorVersionDateRange = (versions: any[]): string => {
  if (versions.length === 0) return ''
  if (versions.length === 1) return versions[0].date
  
  const dates = versions.map(v => v.date).sort()
  const startDate = dates[0]
  const endDate = dates[dates.length - 1]
  
  // 提取月份和日期
  const formatShort = (dateStr: string) => {
    const [year, month, day] = dateStr.split('-')
    return `${month}.${day}`
  }
  
  return `${formatShort(startDate)} - ${formatShort(endDate)}`
}
</script>
