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
      
      <!-- 操作按钮组 -->
      <div class="flex items-center space-x-4">
        <!-- 折叠/展开按钮 -->
        <div class="flex items-center space-x-2">
          <span class="text-sm text-gray-600">版本详情：</span>
          <button 
            @click="toggleAllVersions"
            class="px-4 py-2 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors flex items-center space-x-2 shadow-sm"
          >
            <i :class="allExpanded ? 'fas fa-compress-alt' : 'fas fa-expand-alt'" class="text-purple-600"></i>
            <span class="text-sm font-medium">{{ allExpanded ? '全部折叠' : '全部展开' }}</span>
          </button>
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
                <!-- 可点击的版本头部 -->
                <div 
                  @click="toggleVersion(version.version)" 
                  class="flex items-center justify-between mb-4 cursor-pointer group"
                >
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
                    <!-- 折叠/展开图标 -->
                    <i :class="`fas fa-chevron-${expandedVersions[version.version] ? 'up' : 'down'} text-gray-400 group-hover:text-gray-600 transition-colors`"></i>
                  </div>
                </div>
            
            <transition name="slide-fade">
              <div v-show="expandedVersions[version.version]" class="space-y-4">
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
              </transition>
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
          <p>版本号采用语义化版本规范（Semantic Versioning）：主版本号.次版本号.修订版号</p>
          <ul class="mt-2 space-y-1 ml-4">
            <li>• 主版本号：重大功能变更或架构调整</li>
            <li>• 次版本号：新增功能或重要优化</li>
            <li>• 修订版号：问题修复和小幅改进</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

// 排序状态：true = 升序（从早到晚），false = 降序（从晚到早）
const sortAscending = ref(true)

// 折叠/展开状态
const expandedVersions = ref<Record<string, boolean>>({})
const allExpanded = ref(true) // 默认全部展开

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
    isLatest: false,
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
      '导航菜单完善：在系统配置下新增账号设置入口',
      '数据库兼容性：提供MySQL 5.7完全兼容的初始化脚本，避免编码问题',
      '错误提示优化：登录失败时显示具体错误原因，不再统一提示网络错误'
    ],
    bugfixes: [
      '修复注册时缺少邮箱字段导致无法找回密码的问题',
      '修复忘记密码页面显示导航栏的问题',
      '修复用户信息更新后本地存储不同步的问题',
      '修复登录错误提示不明确的问题，现在能正确显示后端返回的错误信息',
      '修复SQL初始化脚本中文编码问题，提供MySQL 5.7兼容版本'
    ]
  },
  {
    version: 'v1.4.0',
    date: '2025-10-08',
    timestamp: '2025-10-08',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '任务提醒系统：全新的紧急任务提醒功能，支持已过期和即将到期任务智能提醒',
      '分类折叠视图：三大行动组（战争行动、工程建设、外交行动）任务分类展示，支持折叠/展开',
      '过期任务高亮：已逾期任务采用深红色背景和火焰图标，醒目提醒用户及时处理',
      '总览面板美化：行动组卡片全新设计，统一视觉风格，提升整体美观度',
      '版本记录折叠：版本更新记录页面支持一键展开/折叠所有版本，便于快速浏览',
      '折叠动画效果：所有折叠/展开操作（总览面板、版本记录）添加平滑过渡动画，提升交互体验'
    ],
    improvements: [
      'UI全面升级：行动组卡片采用现代化设计，包含渐变色、圆角、阴影等精美效果',
      '统计卡片优化：项目和任务统计采用独立渐变背景卡片，数字更大更醒目',
      '紧急程度分级：根据剩余时间自动分级显示（已过期/2天内/5天内），颜色编码清晰',
      '交互体验提升：悬停动画、图标缩放、渐变按钮等细节打磨，操作反馈更流畅',
      '按钮文案优化：行动组按钮调整为"实战任务"、"模拟训练"、"对外推广"，语义更明确',
      '图标风格统一：行动组标题图标改为黑白风格，与整体页面保持一致',
      '空状态优化：无紧急任务时显示绿色勾选提示，传递积极反馈',
      '底部信息美化：最近更新时间采用卡片化设计，查看详情按钮更突出',
      '时间显示优化：所有相对时间（"X小时前"）现在会每分钟自动更新，始终保持准确',
      '项目模板实时同步：新建素材记录-实战笔记时，项目模板下拉框改为从API动态加载，与实战笔记页面保持实时同步',
      '数据统计真实化：实战笔记页面的统计数据全面改为真实数据（项目领域分布、技术栈统计、实战笔记数量）',
      '模板详情优化：模板详情弹窗完全使用真实数据展示，包括实战笔记数量和技术栈统计',
      '卡片布局优化：项目模板卡片操作按钮添加白色背景和阴影，标题和描述重新布局，避免与领域标签重叠',
      '术语规范化：统一使用"功能性需求"和"非功能性需求"，移除不规范的"质量需求"表述'
    ],
    bugfixes: [
      '修复任务提醒只显示5天内任务的问题，现在包含已过期任务',
      '修复任务数量徽章显示不醒目的问题，采用主题色高亮显示',
      '修复行动组卡片视觉不统一的问题，统一应用美化样式',
      '修复总览面板所有相对时间不会自动更新的问题，包括行动组最近更新、素材卡片最近更新、笔记列表创建时间',
      '修复项目模板卡片中操作按钮与领域标签重叠遮挡的问题',
      '修复实战笔记页面项目定制进度、热门技术栈使用硬编码mock数据的问题',
      '修复模板详情弹窗显示mock数据而非真实笔记数据的问题',
      '修复新建实战笔记时项目模板选项与实战笔记页面不同步的问题'
    ]
  },
  {
    version: 'v1.5.0',
    date: '2025-10-08',
    timestamp: '2025-10-08',
    isLatest: true,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '总结模板管理：支持自定义总结模板，未来可以灵活添加新的总结类型（如周报、专题总结等）',
      '删除总结记录：系统总结页面现在可以删除不需要的总结，点击总结类型即可查看并删除单条记录',
      '重复日期提醒：保存每日总结时，如果当天已有总结会弹出提示，可选择覆盖或取消',
      '日历选择日期：每日总结的日期输入改为日历选择器，选日期更方便直观',
      '笔记数据管理：统一了所有笔记类型的数据管理方式，标签、分类等信息显示更准确'
    ],
    improvements: [
      '功能精简：移除了系统决策中用处不大的"时间节点管理"功能，让系统更简洁专注',
      '实时数据更新：所有笔记页面（框架笔记、学习笔记、背诵笔记、刷题笔记、实战笔记、碎片笔记）切换回来后会自动刷新最新内容',
      '标签显示优化：统一处理所有笔记的标签、知识点、技术栈等分类信息，确保正确显示',
      '日期选择美化：日期输入框添加日历图标，默认显示今天日期，不允许选择未来日期',
      '界面简化：移除了总结页面的满意度评分功能，减少不必要的填写项',
      '界面简化：移除了系统总结的标签显示，让页面更清爽易读',
      '文案优化：将"有效动作 / 有感收获"改为更简洁的"今日收获"',
      '文案统一：总结类型显示统一简化，如"月度总结"改为"月度"，保持一致风格',
      '灵活展示：总结详情页面现在根据总结类型自动调整显示内容，支持未来扩展',
      '系统稳定性：优化了底层代码结构，运行更稳定流畅'
    ],
    bugfixes: [
      '修复管理笔记修改后内容不更新的问题，现在修改后立即可见',
      '修复碎片笔记切换页面后数据不刷新的问题',
      '修复背诵笔记标签显示异常的问题（之前只显示单个字而不是完整词语）',
      '修复背诵笔记页面偶尔报错无法打开的问题',
      '修复项目记录查看素材详情时标签显示为乱码的问题',
      '修复系统总结页面标签显示异常的问题',
      '修复每日总结保存失败的问题',
      '修复日期选择框显示"yyyy/mm/日"占位符不美观的问题',
      '修复系统总结页面偶尔报错的问题',
      '修复所有笔记类型的数据刷新、标签显示等一系列体验问题'
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

// 切换单个版本的展开/折叠状态
const toggleVersion = (versionKey: string) => {
  expandedVersions.value[versionKey] = !expandedVersions.value[versionKey]
}

// 一键全部展开/折叠
const toggleAllVersions = () => {
  allExpanded.value = !allExpanded.value
  const newState = allExpanded.value
  versions.value.forEach(v => {
    expandedVersions.value[v.version] = newState
  })
}

// 初始化所有版本为展开状态
const initExpandedStates = () => {
  versions.value.forEach(v => {
    if (expandedVersions.value[v.version] === undefined) {
      expandedVersions.value[v.version] = true
    }
  })
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

// 初始化展开状态
onMounted(() => {
  initExpandedStates()
})
</script>

<style scoped>
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
