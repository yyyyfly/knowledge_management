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
        <div 
          class="mb-6 bg-gradient-to-r from-blue-600 via-purple-600 to-indigo-700 rounded-xl shadow-2xl overflow-hidden cursor-pointer hover:shadow-3xl transition-all duration-300"
          @click="toggleMajorVersion(majorGroup.major)"
        >
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
              <div class="flex items-center space-x-3">
                <div class="px-4 py-2 bg-white/20 rounded-full backdrop-blur-sm">
                  <span class="text-white text-sm font-medium">{{ getMajorVersionDateRange(majorGroup.versions) }}</span>
                </div>
                <!-- 折叠/展开图标 -->
                <div class="w-10 h-10 bg-white/20 rounded-full flex items-center justify-center backdrop-blur-sm">
                  <i 
                    :class="expandedMajorVersions[majorGroup.major] ? 'fas fa-chevron-up' : 'fas fa-chevron-down'" 
                    class="text-white text-sm transition-transform duration-300"
                  ></i>
                </div>
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
        <transition name="slide-fade">
          <div v-if="expandedMajorVersions[majorGroup.major]" class="relative ml-8">
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
        </transition>
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
const expandedMajorVersions = ref<Record<string, boolean>>({}) // 大版本折叠状态
const allExpanded = ref(true) // 默认全部展开

// 大版本描述配置
const majorVersionDescriptions: Record<string, string> = {
  'v1': '🚀 基础架构的奠基之作。从零到一构建完整的个人知识管理体系，建立素材组、行动组、首脑组三大核心模块，实现知识积累与项目管理的闭环。这是系统从概念到实现的关键突破，为后续所有功能奠定了坚实基础。',
  'v2': '🔥 笔记展示的全面革新。对导航栏六大笔记模块的展示方式进行彻底重构，带来更舒适的浏览体验和更高效的知识管理方式。这是从基础功能到精致体验的重要升级。',
  'v3': '🌟 生态化与开放的战略转型。构建插件系统、开放API、跨平台同步等能力，打造知识管理生态圈。这是从封闭到开放、从产品到生态的历史性跨越。'
}

// 版本数据
const versions = ref([
  {
    version: 'v1.0.0',
    date: '2025-10-01',
    timestamp: '2025-10-01',
    isLatest: false,
    isInitial: true,
    isMajor: true, // 大版本
    featuresTitle: '核心功能',
    features: [
      '素材组：6种笔记类型（框架、求学、背诵、刷题、实战、碎片）',
      '行动组：3大行动类别（战争、工程、外交）',
      '首脑组：日常决策、项目管理、时间节点',
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
    isLatest: false,
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
      '功能精简：移除了日常决策中用处不大的"时间节点管理"功能，让系统更简洁专注',
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
  },
  {
    version: 'v2.8.0',
    date: '2025-11-15',
    timestamp: '2025-11-15',
    isLatest: true,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '模拟实战功能：全新的实战训练页面，可以创建和管理各种训练题目，就像教练给你准备训练计划一样',
      '基础赛题库：创建通用的训练题目，任何技术都能拿来练手，就像健身房的基础器材，谁都可以用',
      '专项实战方案：针对特定场景设计专门的训练计划，比如为某个考试、某个项目、某个竞赛量身定制',
      '场景定制功能：可以详细说明这个训练是为哪个考试准备的、用什么技术、有什么限制条件',
      '背景资料记录：可以记下考试大纲、项目背景、竞赛规则等重要信息，训练时随时查看',
      '技术栈指定：给每个专项实战指定要用的技术，比如前端用Vue、后端用Java，训练目标更明确',
      '限制条件设置：可以设置时间限制、资源限制等约束条件，让训练更贴近真实场景',
      '参考资料整理：把相关的学习资料、文档链接都记录下来，需要的时候方便查阅'
    ],
    improvements: [
      '功能独立分离：把原来实战笔记页面的项目模板管理功能单独拎出来，专门开了一个"模拟实战"页面',
      '双层管理结构：页面分成两个区域，上面管理通用题目，下面管理专项训练，分工明确不混乱',
      '卡片式展示：所有训练题目都用卡片展示，鼠标放上去就能看到编辑和删除按钮，操作很直观',
      '难度等级标识：每个训练都能设置难度（简单、中等、困难），用不同颜色标记一眼就能看出来',
      '标签分类管理：可以给训练添加标签，比如"算法"、"数据库"、"前端"等，方便以后查找',
      '详细内容记录：可以写训练的详细要求、评估标准、目标技能，像写训练计划书一样完整',
      '时间建议设置：可以给每个训练设置建议完成时间，帮你合理安排训练进度',
      '兼容性说明：可以备注这个训练适合什么水平的人、需要什么基础，避免选错训练',
      '导航位置调整：新页面放在主仪表盘下面，和数据总览、时光轨迹并列，方便快速找到'
    ],
    bugfixes: [
      '修复了实战笔记页面功能过于杂乱的问题：项目模板管理现在有了专属页面，不会和笔记记录混在一起',
      '修复了训练题目难以分类的问题：现在通用题目和专项训练分开管理，清晰明了'
    ]
  },
  {
    version: 'v2.7.0',
    date: '2025-11-12',
    timestamp: '2025-11-12',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '数据管理工具：管理员专属的数据管理页面，可以直接在网页上管理系统数据，再也不用去服务器操作了',
      '表格管理：能看到系统里所有的数据表，可以新建表、删除表、给表改名字，还能查看每个表的详细结构',
      '填表式建表：创建新表不用写复杂的命令了，就像填表格一样选择字段类型、长度、是否必填，系统自动帮你生成',
      '字段管理：给表添加新字段、修改已有字段、删除不需要的字段，都是可视化操作，很方便',
      '数据增删改查：可以查看表里的数据，支持新增记录、编辑记录、删除记录，还有分页翻页功能',
      '表单式操作：所有的添加、编辑操作都改成了表单填空，不需要懂专业知识也能轻松操作'
    ],
    improvements: [
      '界面配色大优化：整个数据管理页面的配色全面调整，深色背景配白色文字，所有内容都清晰可见',
      '浅色弹窗设计：所有的弹窗（创建表、添加字段、编辑数据等）都用了白色背景+深色文字，看起来更专业',
      '标签颜色优化：字段类型、状态标记等标签都改用浅色背景配深色文字，对比度更高更容易辨识',
      'Tab标签美化：表结构管理和数据管理的切换标签采用浅色设计，鼠标悬停有颜色提示',
      '下拉框样式调整：选择表的下拉框改成白色背景黑色文字，再也不会看不清了',
      '分页信息优化：页码、记录数等分页信息都用了高对比度配色，一眼就能看清楚',
      '左侧列表优化：表列表的悬停效果改成浅灰色背景+深黑色文字，不会再变成白色看不见',
      '弹窗内容丰富：创建表的弹窗可以设置存储引擎、字符集、表注释，还能添加多个字段',
      '字段配置详细：每个字段都能设置类型、长度、默认值、注释、是否可空、是否主键、是否自增',
      '智能禁用提示：比如选择了TEXT类型，长度输入框会自动禁用提示"此类型无需长度"',
      '记录编辑优化：编辑数据时，主键字段自动禁用不能修改，防止误操作'
    ],
    bugfixes: [
      '修复了所有白色文字看不见的问题：页面标题、表信息、数据内容等所有文字都改成了深色',
      '修复了鼠标悬停文字消失的问题：列表项悬停时文字颜色从白色改成深黑色',
      '修复了表格单元格内容看不清的问题：数据表格的内容全部改用深色字体',
      '修复了分页信息显示不清的问题：显示条数、页码等信息都用了高对比度配色'
    ]
  },
  {
    version: 'v2.6.0',
    date: '2025-11-11',
    timestamp: '2025-11-11',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '指导大纲模块：全新的思维导图和知识体系管理功能，帮你建立完整的学科知识结构',
      '学科知识树：可以为每个学科创建树状知识体系，支持无限层级的知识节点，想怎么组织就怎么组织',
      '树形节点管理：每个知识点都能添加子分支、编辑内容、删除节点，构建你自己的知识体系架构',
      '学科分类系统：可以给学科添加分类标签，比如"编程语言"、"数学"、"文学"等，便于知识归类管理',
      '折叠式分类管理：新增专门的学科分类管理界面，采用折叠卡片设计，点击展开就能看到分类的详细信息',
      '分类增删改查：在分类管理界面可以添加新分类、编辑分类名称、删除不需要的分类，配置灵活自由',
      '技能笔记全面升级：原来的"框架笔记"正式更名为"技能笔记"，更符合实际使用场景',
      '三段式技能记录：技能笔记改用"预期描述"、"思考总结"、"最终效果"三个富文本字段，记录技能学习全过程',
      '数据完整迁移：系统自动将所有"框架笔记"数据迁移为"技能笔记"，历史数据完全保留不丢失'
    ],
    improvements: [
      '页面布局优化：指导大纲页面采用左右分栏设计，左侧学科列表、右侧知识树编辑器，操作更直观',
      '滚动容器限高：所有容器都设置了合理的高度限制，内容再多也在容器内滚动，页面永远不会超出屏幕',
      '知识树实时保存：编辑知识树后点击保存按钮，立即更新到数据库，随时可以查看编辑历史',
      '分类详情展开：折叠式分类卡片支持展开查看详细信息，包括创建人、创建时间、配置类型等',
      '平滑折叠动画：分类卡片展开收起有流畅的过渡动画，操作体验更舒适',
      '技能笔记字段调整：技能类型和技能点字段从原来的"学科类型"、"知识点"改名，语义更清晰',
      'Content字段解除：技能笔记的content字段不再必填，使用三个专用富文本字段即可完成记录',
      '全局类型修复：修复了整个项目中所有API返回类型的定义，TypeScript类型检查完全通过',
      '类型智能提示：所有API调用现在都有正确的类型提示，IDE能准确提示code、data、message字段',
      '代码质量提升：清理了大量类型错误，项目编译零警告，代码更健壮可靠'
    ],
    bugfixes: [
      '修复了API响应类型错误的问题：全局修复"Property code does not exist on type AxiosResponse"错误',
      '修复了技能笔记保存验证过于严格的问题：现在只需填写三个富文本字段之一即可保存',
      '修复了拓展笔记字段混用的问题：拓展笔记使用knowledgePoint，技能笔记使用skillPoint，不再冲突',
      '修复了TreeNode组件事件参数错误：editNode事件现在使用正确的payload格式传递参数',
      '修复了所有前端组件的类型定义问题：response.code、response.data现在都能正确访问',
      '修复了NoteConfig类型不完整的问题：新增guidance、skill、expansion等笔记类型支持'
    ]
  },
  {
    version: 'v2.5.0',
    date: '2025-11-08',
    timestamp: '2025-11-08',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '冲刺记忆笔记：整装待发页面新增了专门为项目准备的冲刺记忆功能，可以快速记录项目相关的知识要点',
      '三合一管理按钮：提供"新增记忆"、"管理记忆"、"回顾记忆"三个功能按钮，让你能快速添加、编辑和查看项目的冲刺记忆',
      '项目专属记忆：每个冲刺记忆都会自动关联到对应的项目，在整装待发页面就能直接管理，不用到处找笔记',
      '拓展笔记功能：全新的笔记类型"拓展笔记"，专门用于记录原文和自己的理解，帮你深度学习知识',
      '原文与理解分离：拓展笔记分为"原文记录"和"理解记录"两个部分，可以先记录原文，再写下自己的想法和分析',
      '拓展笔记全场景支持：在导航栏、总仪表盘、笔记记录等所有地方都能创建和查看拓展笔记，使用起来很方便'
    ],
    improvements: [
      '整装待发页面简化：移除了用处不大的"关键备忘"功能，改用更实用的冲刺记忆笔记来记录项目要点',
      '笔记类型优化调整：用"拓展笔记"替代了原有的"背诵笔记"，新的拓展笔记更适合深度学习和知识理解',
      '记忆内容独立管理：冲刺记忆作为独立的笔记类型，可以像其他笔记一样编辑、查看和删除，使用更灵活',
      '拓展笔记编辑体验：原文记录和理解记录都使用富文本编辑器，支持插入表格、图片、链接等多种格式',
      '字段验证更合理：拓展笔记的原文记录和理解记录至少填写一项即可保存，不强制两个都填，降低使用门槛'
    ],
    bugfixes: [
      '修复了拓展笔记内容保存失败的问题：原文记录和理解记录现在都能正确保存到数据库了',
      '修复了编辑拓展笔记时内容不显示的问题：打开编辑对话框时，原文和理解内容都能正确加载到编辑器中',
      '修复了拓展笔记标签显示异常的问题：笔记列表中的学科项目和知识点标签现在能正常显示了',
      '修复了笔记记录页面类型选择的问题：下拉框中正确显示"拓展笔记"选项，移除了旧的"背诵笔记"选项'
    ]
  },
  {
    version: 'v2.4.0',
    date: '2025-10-20',
    timestamp: '2025-10-20',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '笔记归档整理：全新的笔记来源整理功能，可以记录每条笔记是从哪本书、哪门课、哪个材料收集来的',
      '双视角管理：就像项目和任务的关系一样，可以从归档看笔记，也可以从笔记看归档，两个角度都很清晰',
      '归档视角：点开一个归档（比如《深入理解计算机系统》），就能看到你从这本书记录的所有笔记',
      '笔记视角：在笔记列表中，每条笔记下方会显示它属于哪些归档，一眼就知道这笔记的来源',
      '快速整理笔记：可以选中多条笔记，一次性添加到某个归档里，也可以边选笔记边创建新归档',
      '归档类型丰富：支持书籍、课程、材料、视频、其他5种归档类型，还能添加作者、出版社等来源信息',
      '项目关键备忘：给每个项目记录关键信息，考试前、项目上线前、演讲前都能快速回顾重点内容',
      '三种备忘分类：核心要点记录最重要的知识，易错陷阱记录容易出错的地方，待办事项记录还没完成的事',
      '战备状态页面：专门的战前检查页面，列出所有正在进行的项目，点一下就能看到这个项目记录了哪些关键备忘',
      '随时添加删除：项目进行中想到什么就记什么，随时可以添加新的备忘，不需要的也能随时删掉'
    ],
    improvements: [
      '即时更新效果：添加或删除归档关联后，页面立即刷新显示，不用手动刷新，操作反馈很及时',
      '视角切换流畅：归档视角和笔记视角之间一键切换，Tab按钮会高亮显示当前在哪个视角',
      '归档展开收起：归档卡片可以展开查看里面的笔记，也可以收起来保持页面整洁',
      '笔记标签显示：每条笔记会显示类型标签（碎片笔记、求学笔记等），还有创建时间，方便识别',
      '归档信息完整：每个归档都显示包含多少条笔记，还能写描述说明和来源信息',
      '操作简单直接：删除关联就是点一下[X]按钮，添加关联就是点"添加到归档"，非常好理解',
      '备忘数据持久化：关键备忘不再依赖浏览器缓存，所有内容都保存在数据库里，换电脑也不会丢失',
      '备忘数量统计：每个项目卡片上会显示已记录多少条备忘，一眼就知道哪个项目准备得最充分',
      '分类清晰展示：三种备忘类型用不同颜色和图标区分，核心要点是蓝色星星，易错陷阱是黄色感叹号，待办事项是绿色勾选框'
    ],
    bugfixes: [
      '修复表名错误导致查询归档笔记失败的问题：之前因为数据库表名写错，展开归档看不到笔记，现在正常了',
      '修复删除归档关联后笔记视角不更新的问题：之前删除关联后，笔记下方的归档标签还在，现在会立即消失',
      '修复添加归档关联后不显示的问题：之前添加完需要刷新页面才能看到，现在添加后立刻就显示出来',
      '修复关键备忘数据容易丢失的问题：之前存在浏览器缓存里，清理浏览器或换设备就没了，现在改成存数据库永久保存',
      '修复后端编译报错的问题：之前代码里引用了不存在的类名导致编译失败，现在统一使用正确的类名'
    ]
  },
  {
    version: 'v2.3.0',
    date: '2025-10-17',
    timestamp: '2025-10-17',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '标签智能搜索：所有笔记类型添加标签时，现在可以先搜索已有的标签，找到就直接选，找不到再新建，避免标签重复',
      '标签快速添加：碎片笔记、框架笔记、求学笔记、背诵笔记、实战笔记的分类、主题、学科、知识点、技术栈等，都能边输入边搜索边添加',
      '标签下拉提示：输入标签名时会显示匹配的现有标签列表，点一下就能快速选择，不用每次都输全名',
      '已选标签标记：搜索下拉列表里，已经选过的标签会显示绿色勾号，避免重复添加',
      '标签展示统一：所有笔记的标签显示样式完全一致，每种标签都有自己的颜色（分类-蓝色、主题-绿色、知识点-橙色等），一眼就能区分'
    ],
    improvements: [
      '新建笔记更顺手：编辑器加载速度明显加快，打开创建笔记弹窗几乎是秒开',
      '标签颜色更丰富：新增了青色(teal)主题，实战笔记的技术栈标签变得更醒目',
      '操作反馈更及时：添加标签后立即显示在下方，删除标签也是立即消失，不会有延迟感',
      '界面响应更快：优化了大量重复代码，整个系统运行速度提升了近一倍',
      '内存占用更少：精简了不必要的功能代码，系统运行更轻盈流畅',
      '代码质量提升：清理了60多个用不到的功能，让系统更稳定可靠',
      '维护更容易：统一了标签相关的所有操作逻辑，未来添加新功能更简单'
    ],
    bugfixes: [
      '修复标签可能为空导致的报错问题：现在即使数据异常，标签功能也能正常工作',
      '修复标签搜索时的卡顿：输入文字搜索标签时不会再出现明显延迟',
      '修复重复标签可能被添加多次的问题：系统会自动过滤重复的标签',
      '修复标签添加后需要手动刷新才显示的问题：现在添加标签立即就能看到'
    ]
  },
  {
    version: 'v2.2.0',
    date: '2025-10-14',
    timestamp: '2025-10-14',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '笔记巩固系统：六大笔记页面从单纯的数据统计变成了帮你复习巩固的好帮手，会智能推荐你该复习哪些笔记了',
      '智能推荐复习：每个笔记页面会自动推荐5篇最该复习的笔记，越久没看的越优先推荐，让你不会遗忘重要知识',
      '一键完成巩固：看完笔记点一下"完成巩固"按钮，系统会记录你的复习次数和时间，下次推荐就更精准',
      '手动搜索复习：如果想自己找笔记复习，点击"搜索所有笔记"按钮就能浏览全部内容，想看啥看啥',
      '复习历史跟踪：每条笔记都能看到你复习了多少次、上次什么时候看的，让你了解自己的学习轨迹',
      '背诵笔记分栏展示：原文、解释、提示词三个内容分成三个标签页，切换着看更清楚',
      '刷题笔记分栏展示：题目描述、分析理解、参考答案分开显示，做题思路一目了然',
      '求学笔记六栏展示：核心概念、机制原理、应用案例、延伸对比、常见误区、思考理解都有独立的标签页，知识体系更完整',
      '实战笔记三栏展示：需求描述、设计思路、参考设计分开查看，项目经验更系统',
      '巩固记录自动刷新：完成巩固后推荐列表自动更新，下一批该复习的笔记马上就出现'
    ],
    improvements: [
      '笔记页面聚焦优化：删除了所有复杂的统计图表，页面变得简洁清爽，专心复习不分心',
      '数据显示人性化：复习时间显示"今天"、"昨天"、"3天前"这样的自然语言，不再是冷冰冰的日期',
      '巩固推荐不设上限：想复习多少遍都行，没有次数限制，多看多记忆深刻',
      '空状态友好提示：暂时没有要复习的笔记会显示"太棒了！暂时没有需要巩固的笔记"，给你积极反馈',
      '内容格式正确显示：笔记内容的粗体、颜色、表格等格式都能正确显示了，不会再出现一堆HTML标签',
      '推荐逻辑智能化：从未复习过的笔记优先推荐，复习过的按照距离上次复习的时间长短排序',
      '标签页主题色统一：每种笔记的标签页颜色都和笔记主题色一致（背诵-绿色、刷题-粉色、求学-蓝色、实战-橙色）',
      '数据加载更高效：只更新需要的字段，不会影响笔记的其他内容，系统运行更流畅',
      '字段映射全面修正：框架笔记、求学笔记、碎片笔记的分类标签都能正确显示了'
    ],
    bugfixes: [
      '修复笔记编辑后内容丢失的问题：之前编辑笔记保存后，题目描述、设计思路等富文本内容会变空，现在都能正确保存了',
      '修复完成巩固后字段被清空的问题：点击"完成巩固"不会再把笔记的其他信息清空了',
      '修复笔记详情显示HTML标签的问题：原本应该显示格式化内容，却显示成了<p>333</p>这样的代码，现在正常了',
      '修复框架笔记字段不对的问题：学科字段从错误的subject改成了正确的subjectType',
      '修复求学笔记字段不对的问题：课程字段从错误的course改成了正确的studySubject',
      '修复碎片笔记字段不对的问题：分类字段从错误的category改成了正确的fragmentCategory',
      '修复刷题笔记字段映射错误：知识点、难度、分析理解等字段都用上了正确的名称',
      '修复编辑笔记缺少富文本内容的问题：刷题笔记的3个编辑器、实战笔记的3个编辑器、求学笔记的6个编辑器内容现在都能正确保存'
    ]
  },
  {
    version: 'v2.1.0',
    date: '2025-10-13',
    timestamp: '2025-10-13',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '富文本编辑器全面升级：所有笔记类型（碎片、框架、求学、刷题、实战、背诵）的编辑器都升级了，功能更强大、操作更顺手',
      '文本高亮功能：可以给重要内容加上黄色高亮标记，让关键信息一眼就能看到',
      '链接插入功能：写笔记时可以插入网页链接了，点一下就能跳转，方便整理学习资料',
      '撤销重做功能：写错了可以一键撤销，改过头了还能重做回来，再也不怕误操作',
      '工具栏统一优化：8个编辑器（5种笔记类型+背诵笔记的3个编辑器）的工具栏样式完全统一，使用更流畅',
      '项目复盘页面全新升级：战争行动、工程建设、外交行动三个页面改成了项目复盘展示，现在能看到每个项目的完整信息了',
      '项目卡片式展示：每个项目都是一张漂亮的卡片，点一下卡片就能展开查看详细内容，包括任务完成情况、心得总结、遇到的问题',
      '项目筛选功能：可以按状态筛选项目了，想看进行中的、已完成的、还是计划中的都能快速找到',
      '项目完成度统计：每个项目都能看到任务完成了多少、问题解决了多少，用百分比显示一目了然'
    ],
    improvements: [
      '编辑器布局优化：工具栏改成紧凑的单行设计，按功能分组排列，找按钮更方便',
      '按钮状态提示：正在使用的格式（比如粗体、高亮）会高亮显示，一眼就知道当前格式',
      '智能按钮禁用：撤销、重做、取消链接等按钮会根据实际情况变灰，避免误点击',
      '分隔线统一风格：工具栏的分组分隔线统一样式，视觉效果更整洁',
      '编辑器高度优化：内容区域最小200px、最大400px，既能看到足够内容，又不会太占地方',
      '项目卡片颜色主题：6种渐变色卡片自动配色，每个项目都有自己独特的颜色，看起来更美观',
      '心得内容快速查看：点击项目心得可以弹窗查看完整内容，包括心得摘要、详细内容和标签分类',
      '问题记录清晰展示：项目遇到的问题会显示优先级和解决状态，还能看到解决方案，方便总结经验',
      '页面交互动画优化：卡片展开收起、鼠标悬停都有流畅的动画效果，操作起来更舒服',
      '项目执行页面精简：移除了项目经验展示区域，让页面更加简洁清爽，专注于任务执行和心得记录'
    ],
    bugfixes: [
      '修复了背诵笔记三个编辑器（原文内容、解释说明、记忆提示词）工具栏不统一的问题',
      '修复了部分编辑器缺少高亮、链接等实用功能的问题',
      '优化了所有编辑器的代码块按钮，统一使用 fa-code 图标'
    ]
  },
  {
    version: 'v2.0.0',
    date: '2025-10-09',
    timestamp: '2025-10-09',
    isLatest: false,
    isInitial: false,
    isMajor: true, // 大版本
    features: [
      '打卡养成系统：帮你养成好习惯、追踪长期目标的全新功能，支持每日、每周、每月、每季、每年5种打卡频率',
      '打卡项目管理：在日常决策页面创建你的打卡项目，比如"每日阅读30分钟"、"每周运动3次"，给它起个名字、写点说明就行',
      '一键打卡：在项目执行页面点一下就能完成打卡，打错了也可以取消，每个周期只能打一次卡，防止重复记录',
      '打卡统计：数据总览页面会显示你的打卡成果，包括最长连续打卡天数（帮你见证坚持的力量）和累计打卡次数',
      '打卡状态提醒：每个打卡项目会自动显示本周期是否已完成，还没打卡会有黄色提醒，已完成显示绿色，一目了然'
    ],
    improvements: [
      '打卡周期智能计算：系统会根据你选择的频率自动判断周期，比如每日打卡按自然日计算，每周打卡从周一开始算',
      '启用停用灵活切换：不想打卡了可以暂时停用项目，打卡操作界面只显示启用的项目，保持页面清爽',
      '连续打卡判断：系统会智能判断你的打卡是否连续，比如今天打卡、明天打卡算连续，断了就重新开始计算',
      '打卡记录永久保存：所有打卡记录都会保存下来，让你随时回顾自己的成长轨迹',
      '荣誉战绩折叠功能：三种荣誉类型（战争行动、工程建设、外交行动）现在支持折叠了，超过3条时默认只显示前3条，点击"展开全部"可以看完整列表',
      '图标风格统一：荣誉战绩图标统一改为黑白配色，与整体页面风格保持一致',
    ],
    bugfixes: [
      '修复了数据总览页面荣誉战绩太多时页面过长的问题，现在可以折叠起来了',
      '修复了荣誉图标颜色太花哨的问题，统一使用简洁的黑白图标'
    ]
  },
  {
    version: 'v1.10.0',
    date: '2025-10-09',
    timestamp: '2025-10-09',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '日历节假日完整显示：国庆节、春节等长假期现在会完整显示所有假期天数，不再只显示节日当天',
      '节假日智能扩展：系统会根据官方放假安排，自动把连休的假期都标记出来，一眼就能看到哪些天是假期',
      '倒计时提醒功能：给重要日期加上倒计时开关，在数据概览页面顶部会显示"距离XX还有X天"',
      '倒计时智能隐藏：过期的日期会自动隐藏，但当天和昨天的事件还会显示，避免错过重要事情',
      '倒计时颜色分级：根据紧急程度显示不同颜色，今天是红色，一周内是橙色，一个月内是黄色',
      '即将到来的节假日：日历下方会显示最近6个即将到来的节假日，提前知道什么时候放假',
      '节日假期区分：节日当天显示节日名称（如"国庆节"），假期其余天显示"XX假期"（如"国庆节假期"）'
    ],
    improvements: [
      '倒计时提醒优化：数据概览页面顶部会显示所有开启了倒计时的重要日期，按远近排序',
      '倒计时空状态：没有倒计时时会显示友好的提示，告诉你去哪里添加',
      '日期输入优化：点击"添加日期"按钮时，日期输入框会自动填充当前日期，不用手动输入了',
      '日历事件删除：鼠标悬停在事件上时会显示删除按钮，可以快速删除不需要的事件',
      '节假日标记样式：法定假日显示为红色🎉标记，补班工作日显示为橙色💼标记，一目了然',
      '智能过滤优化："即将到来的节假日"只显示真正的假期，不显示补班日，避免混淆',
      '页面自动刷新：切换回数据概览页面时，倒计时会自动更新，始终显示最新的数据'
    ],
    bugfixes: [
      '修复了节假日不显示的问题：之前只显示节日当天，现在会自动展开成完整假期了',
      '修复了日期显示错误的问题：日期格式统一了，不会再出现2001年这种奇怪的年份',
      '修复了编辑日期报错的问题：编辑或添加日期时不会再提示"字段不能为空"的错误了',
      '修复了节假日异常数据的问题：API返回的错误连休天数会被自动过滤，不会显示70多天的假期了'
    ]
  },
  {
    version: 'v1.9.0',
    date: '2025-10-09',
    timestamp: '2025-10-09',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '问题管理中心：项目执行过程中遇到问题？现在可以在项目记录页面直接记录问题了',
      '问题跟踪看板：记录的所有问题一目了然，可以按项目、状态、优先级筛选，再也不怕问题被遗忘',
      '决策处理系统：在日常决策页面专门设置了"问题处理"区域，记录的问题可以在这里制定解决方案',
      '问题优先级标识：紧急、高、中、低四个优先级，用不同颜色标注，重要的问题一眼就能看到',
      '问题状态追踪：待处理、处理中、已解决三种状态，问题的进展情况随时掌握',
      '问题编辑功能：记录错了？没关系，可以随时编辑问题的内容、类型和优先级'
    ],
    improvements: [
      '日常决策页面重新设计：原来的标签页改成了两个独立的折叠区域，项目管理和问题处理可以同时看到',
      '问题记录简化：既然是自己用的系统，就不需要什么"报告人"、"负责人"这些繁琐的字段了',
      '视觉效果优化：问题管理用橙红色主题，让问题更醒目；日常决策的配色也更加和谐美观',
      '待处理提醒：问题处理区域会显示待处理问题数量的小红点，提醒你还有多少问题需要解决',
      '界面交互提升：所有问题卡片都支持折叠展开，长长的问题列表也能轻松浏览'
    ],
    bugfixes: [
      '修复了点击编辑问题时却新建了一个问题的bug，现在编辑和新建终于分开了',
      '修复了问题表单提交后的成功提示，现在新建显示"记录成功"，编辑显示"更新成功"'
    ]
  },
  {
    version: 'v1.8.0',
    date: '2025-10-09',
    timestamp: '2025-10-09',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '用户管理功能：管理员专属的用户管理页面，可以查看系统中所有用户的详细信息',
      '权限管控体系：建立完整的三层权限控制（路由层、API层、UI层），确保只有管理员能访问敏感功能',
      '用户名重复检测：注册时自动检测用户名是否已存在，避免重复注册',
      '用户信息展示：查看所有用户的用户名、昵称、邮箱、手机号、状态、注册时间等详细信息',
      '用户统计面板：实时统计总用户数和活跃用户数，掌握系统使用情况'
    ],
    improvements: [
      '路由守卫增强：在路由层面拦截非管理员访问用户管理页面的请求',
      '安全性提升：查询用户列表时自动过滤密码字段，绝不向前端返回敏感信息',
      '用户体验优化：添加美观的用户头像（自动生成首字母头像），状态标签带有指示灯效果',
      '表格布局优化：用户列表采用响应式表格设计，悬停行高亮显示'
    ],
    bugfixes: []
  },
  {
    version: 'v1.7.0',
    date: '2025-10-08',
    timestamp: '2025-10-08',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '时光轨迹功能：全新的历史回顾模块，记录个人成长和项目演进的每一个重要时刻',
      '全局历史事件：按年月分层展示历史事件，支持折叠查看，一目了然回顾过往',
      '专项时间轴：为重要主题（如技术学习路线、项目制作过程）创建独立时间轴，记录里程碑式进展',
      '历史事件管理：在每日总结页面直接记录历史事件，支持全局事件和主题里程碑两种类型',
      '三级导航结构：新增"主仪表盘"顶层分组，统一管理驾驶舱、素材组、行动组三大模块',
      '数据总览页面：专注于数据展示的总览面板，精简冗余信息，聚焦核心指标'
    ],
    improvements: [
      '导航层级优化：采用三级菜单结构（主仪表盘 > 驾驶舱 > 总仪表盘/时光轨迹），层次更清晰',
      '页面功能聚焦：数据总览专注统计信息，时光轨迹专注历史回顾，各司其职',
      '时间轴视觉设计：渐变背景、圆角卡片、悬浮效果，打造优雅的时间轴展示',
      '里程碑节点美化：大尺寸节点配合对勾图标，充分体现里程碑的重要意义',
      '主题寄语设计：在时光轨迹页面添加富有哲理的寄语，营造回顾氛围',
      '卡片选择交互：日报和历史事件记录采用卡片选择模式，支持折叠展开，界面更整洁',
      '表单布局优化：采用垂直布局和统一H1标题，便于未来扩展更多模板',
      '代码结构优化：移除冗余组件和代码，提升系统运行效率'
    ],
    bugfixes: [
      '修复页面切换时数据不刷新的问题，现在每次回到页面都会自动更新最新内容',
      '修复日期选择器打开异常的问题，点击日期输入框更流畅',
      '修复历史事件保存后不立即显示的问题，保存后立刻就能在列表中看到',
      '修复时间轴页面显示示例数据的问题，现在显示的都是真实保存的历史记录',
      '修复数据总览页面"最近活动"显示不准确的问题（已移除该模块）'
    ]
  },
  {
    version: 'v1.6.0',
    date: '2025-10-08',
    timestamp: '2025-10-08',
    isLatest: false,
    isInitial: false,
    isMajor: false, // 小版本
    features: [
      '笔记分类标签管理：全新的配置管理系统，可以自定义所有笔记类型的分类和标签',
      '统一管理入口：在素材记录页面新增"笔记分类标签管理"功能，六大笔记的标签都可以在这里统一管理',
      '自由定制标签：想要添加新的分类或标签？直接在管理界面操作即可，无需重启系统',
      '分类维度灵活：每种笔记都有独立的分类方式，比如碎片笔记有"分类"和"主题"两个维度',
      'Markdown导入功能：所有富文本编辑器现在都支持直接导入Markdown文件，一键转换格式'
    ],
    improvements: [
      '便捷的操作方式：添加和删除标签就像编辑待办事项一样简单，点几下就能完成',
      '折叠式设计：配置管理区域可以收起来，不需要时不占用页面空间',
      '即时生效：刚添加的标签，新建笔记时立刻就能在下拉框中看到',
      '页面更流畅：优化了图表显示逻辑，切换页面时更加顺滑',
      '界面响应更快：提升了页面切换和数据加载的速度',
      'Markdown解析升级：支持表格、代码块、多级标题等复杂格式，智能识别段落和列表',
      '编辑器增强：所有编辑器（包括背诵笔记的三个专用编辑器）都新增Markdown导入按钮',
      '格式保护机制：代码块和行内代码在转换时完全保护，不会被错误处理'
    ],
    bugfixes: [
      '修复框架笔记页面切换时偶尔卡顿的问题',
      '修复图表显示异常的问题',
      '修复笔记配置保存失败的问题',
      '修复标签管理功能无法正常使用的问题',
      '修复Markdown导入时代码块内容被错误转换的问题',
      '修复Markdown导入时段落格式混乱的问题',
      '修复Markdown导入时列表显示不正确的问题'
    ]
  }
])

// 按大版本分组并排序
const groupedVersions = computed(() => {
  // 辅助函数：解析版本号为数字数组
  const parseVersion = (version: string) => {
    // 移除 'v' 前缀，然后按 '.' 分割
    return version.substring(1).split('.').map(Number)
  }

  // 辅助函数：比较两个版本号
  const compareVersions = (versionA: string, versionB: string) => {
    const partsA = parseVersion(versionA)
    const partsB = parseVersion(versionB)
    
    // 逐段比较版本号
    for (let i = 0; i < Math.max(partsA.length, partsB.length); i++) {
      const partA = partsA[i] || 0
      const partB = partsB[i] || 0
      
      if (partA !== partB) {
        return partA - partB
      }
    }
    return 0
  }

  // 按版本号排序所有版本
  const sorted = [...versions.value].sort((a, b) => {
    const result = compareVersions(a.version, b.version)
    return sortAscending.value ? result : -result
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

  // 转换为数组格式
  const result = Object.keys(groups).map(major => {
    return {
      major,
      versions: groups[major]
    }
  })

  // 按大版本号排序（v1, v2, v3...）
  result.sort((a, b) => {
    const majorA = parseInt(a.major.substring(1))
    const majorB = parseInt(b.major.substring(1))
    const majorResult = majorA - majorB
    return sortAscending.value ? majorResult : -majorResult
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

// 切换大版本的展开/折叠状态
const toggleMajorVersion = (major: string) => {
  expandedMajorVersions.value[major] = !expandedMajorVersions.value[major]
}

// 一键全部展开/折叠
const toggleAllVersions = () => {
  allExpanded.value = !allExpanded.value
  const newState = allExpanded.value
  
  // 展开/折叠所有小版本
  versions.value.forEach(v => {
    expandedVersions.value[v.version] = newState
  })
  
  // 展开/折叠所有大版本
  Object.keys(majorVersionDescriptions).forEach(major => {
    expandedMajorVersions.value[major] = newState
  })
}

// 初始化所有版本为展开状态
const initExpandedStates = () => {
  versions.value.forEach(v => {
    if (expandedVersions.value[v.version] === undefined) {
      expandedVersions.value[v.version] = true
    }
  })
  
  // 初始化所有大版本为展开状态
  Object.keys(majorVersionDescriptions).forEach(major => {
    if (expandedMajorVersions.value[major] === undefined) {
      expandedMajorVersions.value[major] = true
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
  
  // 提取月份和日期（去掉年份）
  const formatShort = (dateStr: string) => {
    const [year, month, day] = dateStr.split('-')
    return `${month}-${day}`
  }
  
  if (versions.length === 1) return formatShort(versions[0].date)
  
  const dates = versions.map(v => v.date).sort()
  const startDate = dates[0]
  const endDate = dates[dates.length - 1]
  
  return `${formatShort(startDate)} ~ ${formatShort(endDate)}`
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
