<template>
  <div class="p-6">
    <!-- 系统概述（定位与结构） -->
    <div class="mb-6">
      <h1 class="text-2xl font-semibold text-gray-900">系统概述</h1>
      <p class="mt-1 text-sm text-gray-600">
        本系统是一套面向个人工作、学习与项目成长的多场景管理平台。通过素材组、行动组、首脑组三大核心模块，实现知识积累、项目实践与系统决策的闭环管理，助力高效成长与目标达成。
      </p>
    </div>

    <!-- 系统定位三栏卡片，保留打钩子选项 -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
      <!-- 素材组 -->
      <div class="bg-white rounded-lg shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold text-gray-900">素材组</h2>
          <i class="fa-solid fa-book text-primary text-xl"></i>
        </div>
        <p class="text-sm text-gray-600 mb-4">知识与信息的归集地，聚焦于内容的收集、整理与归档。</p>
        <div class="space-y-2">
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-primary mr-2"></i>
            <span>基础笔记</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-primary mr-2"></i>
            <span>求学笔记</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-primary mr-2"></i>
            <span>书籍笔记</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-primary mr-2"></i>
            <span>刷题笔记</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-primary mr-2"></i>
            <span>实战笔记</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-primary mr-2"></i>
            <span>碎片笔记</span>
          </div>
        </div>
      </div>
      <!-- 行动组 -->
      <div class="bg-white rounded-lg shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold text-gray-900">行动组</h2>
          <i class="fa-solid fa-briefcase text-secondary text-xl"></i>
        </div>
        <p class="text-sm text-gray-600 mb-4">项目与任务的执行场，负责将知识转化为实际成果。</p>
        <div class="space-y-2">
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-secondary mr-2"></i>
            <span>战争行动</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-secondary mr-2"></i>
            <span>工程建设</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-secondary mr-2"></i>
            <span>外交行动</span>
          </div>
        </div>
      </div>
      <!-- 首脑组 -->
      <div class="bg-white rounded-lg shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold text-gray-900">首脑组</h2>
          <i class="fa-solid fa-users text-accent text-xl"></i>
        </div>
        <p class="text-sm text-gray-600 mb-4">系统的决策与总结中枢，统筹资源、方向与成长复盘。</p>
        <div class="space-y-2">
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-accent mr-2"></i>
            <span>系统决策</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-accent mr-2"></i>
            <span>系统总结</span>
          </div>
          <div class="flex items-center text-sm text-gray-600">
            <i class="fa-solid fa-check text-accent mr-2"></i>
            <span>项目储备</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 功能定义（详细描述，无重复） -->
    <div class="bg-white rounded-lg shadow-soft p-6 mb-8">
      <h2 class="text-lg font-semibold text-gray-900 mb-4">功能定义</h2>
      <!-- 素材组功能 -->
      <div class="mb-6">
        <h3 class="font-medium text-gray-900 mb-3">素材组</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">基础笔记</h4>
            <p class="text-sm text-gray-600">自主探索、原理归纳，立足于通用技能和系统知识框架。通过系统化的记录和整理，帮助构建坚实的知识基础，为后续的学习和实践提供支持。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">求学笔记</h4>
            <p class="text-sm text-gray-600">参与课程、外部班级学习的记录。详细记录学习过程中的重点、难点和心得，便于复习和巩固，提升学习效果。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">书籍笔记</h4>
            <p class="text-sm text-gray-600">各类书籍阅读摘要与洞见。通过深入阅读和思考，提取书籍中的精华内容，形成个人的知识体系，促进思维的深度和广度。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">刷题笔记</h4>
            <p class="text-sm text-gray-600">做题过程中的技巧总结、典型题型与答疑札记。通过记录解题思路和技巧，帮助掌握各类题型的解题方法，提升解题能力。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">实战笔记</h4>
            <p class="text-sm text-gray-600">实操尝试或临时项目实践中的心得、踩坑、改进点，不直接挂钩大项目。通过记录实践中的经验和教训，帮助积累实战经验，提升实践能力。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">碎片笔记</h4>
            <p class="text-sm text-gray-600">随时记录的灵感、短内容、网络摘录等。通过快速记录和整理，帮助捕捉灵感，积累碎片化知识，为后续的创作和实践提供素材。</p>
          </div>
        </div>
      </div>
      <!-- 行动组功能 -->
      <div class="mb-6">
        <h3 class="font-medium text-gray-900 mb-3">行动组</h3>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">战争行动</h4>
            <p class="text-sm text-gray-600">针对工作、考试等外部驱动的项目，聚焦于目标拆解、进度跟踪与成果归档，强调在关键节点的高效应对与阶段性突破。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">工程建设</h4>
            <p class="text-sm text-gray-600">自主设定项目或任务，注重能力训练和技能提升，通过持续实践积累经验、查漏补缺，为后续实际行动打下坚实基础。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">外交行动</h4>
            <p class="text-sm text-gray-600">参与技术分享会、发布会、拍卖会等外部社交活动，旨在拓展人脉、获取行业资源，为个人或团队发展争取有利条件和支持。</p>
          </div>
        </div>
      </div>
      <!-- 首脑组功能 -->
      <div class="mb-6">
        <h3 class="font-medium text-gray-900 mb-3">首脑组</h3>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">决策</h4>
            <p class="text-sm text-gray-600">记录决定做什么，明确目标和方向。通过系统化的决策过程，确保每个项目的目标和方向清晰，为后续的行动提供指导。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">总结</h4>
            <p class="text-sm text-gray-600">项目完成后进行系统性总结，归纳经验与教训。通过总结，帮助识别成功和失败的原因，为未来的项目提供宝贵的经验和教训。</p>
          </div>
          <div class="bg-gray-50 rounded-lg p-4">
            <h4 class="font-medium mb-2">项目储备</h4>
            <p class="text-sm text-gray-600">将已完成的项目封存起来，作为未来项目的经验和参考。通过项目储备，可以快速调用过去的成功经验，提升新项目的效率和成功率。</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 使用流程（重新总结） -->
    <div class="bg-white rounded-lg shadow-soft p-6 mb-8">
      <h2 class="text-lg font-semibold text-gray-900 mb-4">使用流程</h2>
      <div class="relative pl-4 space-y-4">
        <!-- 步骤1 -->
        <div class="flex items-center relative">
          <div class="z-10 flex-shrink-0 w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center text-white font-medium shadow-md ring-2 ring-white"></div>
          <div class="ml-4">
            <h3 class="font-medium text-gray-900">素材积累</h3>
            <p class="text-sm text-gray-600">在日常空闲时段，自主在素材组积累各类知识和资源，内容由个人兴趣和成长需求驱动，不受外部任务或项目影响。首脑组仅在整体方向上提供建议，不做具体干预，确保知识库持续、自由地丰富，为未来可能的项目或任务提供坚实的基础。</p>
          </div>
        </div>
        <!-- 步骤2 -->
        <div class="flex items-center relative">
          <div class="z-10 flex-shrink-0 w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center text-white font-medium shadow-md ring-2 ring-white"></div>
          <div class="ml-4">
            <h3 class="font-medium text-gray-900">首脑决策</h3>
            <p class="text-sm text-gray-600">当系统面临新需求、机会或挑战时，首脑组负责"做什么"的决策。其核心任务是识别、分析和判断哪些项目、任务或目标值得投入与推进。例如，评估某场考试是否值得准备、某个项目是否应当启动、哪些方向优先发展。首脑组不参与具体执行方案的制定，而是聚焦于战略层面的选择和优先级排序，为系统指明前进方向和资源分配重点。</p>
          </div>
        </div>
        <!-- 步骤3 -->
        <div class="flex items-center relative">
          <div class="z-10 flex-shrink-0 w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center text-white font-medium shadow-md ring-2 ring-white"></div>
          <div class="ml-4">
            <h3 class="font-medium text-gray-900">行动执行</h3>
            <p class="text-sm text-gray-600">行动组在首脑组明确"做什么"之后，承担"怎么做"的具体实施。行动组负责制定详细的执行方案、分解任务、调配资源，并优先调用素材组中已积累的知识和工具，确保高效推进项目。如果发现素材组中存在知识空缺，行动组可临时补充所需内容，但以调用为主、补充为辅，避免临时抱佛脚。行动组需灵活调整执行策略，确保项目顺利完成，并与素材组保持信息流通，促进知识的及时更新。</p>
          </div>
        </div>
        <!-- 步骤4 -->
        <div class="flex items-center relative">
          <div class="z-10 flex-shrink-0 w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center text-white font-medium shadow-md ring-2 ring-white"></div>
          <div class="ml-4">
            <h3 class="font-medium text-gray-900">反馈优化</h3>
            <p class="text-sm text-gray-600">项目或任务完成后，首脑组对行动过程和结果进行系统性总结，归纳经验与教训。反馈的重点在于为未来的决策和行动提供参考，而非单纯补充素材组。通过持续的反馈与优化，推动系统整体能力提升，实现知识、决策与行动的良性循环。</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 荣誉战绩展示 -->
    <div class="bg-white rounded-lg shadow-soft p-6">
      <h2 class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
        <i class="fa-solid fa-trophy text-yellow-500 mr-3"></i>
        荣誉战绩
      </h2>
      <p class="text-sm text-gray-600 mb-6">系统完成的重要项目和里程碑成就</p>
      
      <!-- 荣誉战绩统计 -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
        <div class="bg-gray-50 rounded-lg p-4 text-center">
          <div class="text-2xl font-bold text-gray-900">{{ honorsStats.total }}</div>
          <div class="text-sm text-gray-600">总计荣誉</div>
        </div>
        <div class="bg-red-50 rounded-lg p-4 text-center">
          <div class="text-2xl font-bold text-red-600">{{ honorsStats.defense }}</div>
          <div class="text-sm text-gray-600">战争行动</div>
        </div>
        <div class="bg-blue-50 rounded-lg p-4 text-center">
          <div class="text-2xl font-bold text-blue-600">{{ honorsStats.construction }}</div>
          <div class="text-sm text-gray-600">工程建设</div>
        </div>
        <div class="bg-green-50 rounded-lg p-4 text-center">
          <div class="text-2xl font-bold text-green-600">{{ honorsStats.diplomatic }}</div>
          <div class="text-sm text-gray-600">外交行动</div>
        </div>
      </div>

      <!-- 最近荣誉战绩 -->
      <div class="space-y-3">
        <h3 class="font-medium text-gray-900">最近荣誉战绩</h3>
        <div v-for="honor in recentHonors" :key="honor.id" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg border border-gray-200">
          <div class="flex items-center space-x-3">
            <div class="w-8 h-8 rounded-full flex items-center justify-center" :class="getHonorCategoryClass(honor.category)">
              <i :class="`fa ${honor.icon} text-white text-sm`"></i>
            </div>
            <div>
              <h4 class="text-sm font-medium text-gray-900">{{ honor.title }}</h4>
              <p class="text-xs text-gray-500">{{ honor.description }}</p>
            </div>
          </div>
          <span class="text-xs text-gray-500 font-medium">{{ formatAchievedTime(honor.achievedTime) }}</span>
        </div>
        <div v-if="recentHonors.length === 0" class="text-center py-4 text-gray-500">
          <i class="fas fa-trophy text-2xl mb-2"></i>
          <p class="text-sm">暂无荣誉战绩</p>
          <p class="text-xs">完成项目后可在系统决策页面加入荣誉战绩</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue'
import request from '@/api/request'

// 响应式数据
const allHonors = ref<any[]>([])

// 加载数据
const loadData = async () => {
  try {
    const res = await request.get('/honor/list')
    if (res.code === 200) {
      allHonors.value = res.data || []
    }
  } catch (error) {
    console.error('加载荣誉数据失败:', error)
  }
}

// 统计数据
const honorsStats = computed(() => {
  return {
    total: allHonors.value.length,
    defense: allHonors.value.filter(h => h.category === 'defense').length,
    construction: allHonors.value.filter(h => h.category === 'construction').length,
    diplomatic: allHonors.value.filter(h => h.category === 'diplomatic').length
  }
})

// 获取最近的5个荣誉战绩
const recentHonors = computed(() => {
  return allHonors.value
    .sort((a, b) => new Date(b.recCreateTime || b.createTime).getTime() - new Date(a.recCreateTime || a.createTime).getTime())
    .slice(0, 5)
})

// 组件挂载时加载数据
onMounted(() => {
  loadData()
})

// 格式化日期显示
const formatAchievedTime = (dateTimeStr: string) => {
  if (!dateTimeStr) return '-'
  // 如果已经是简化格式 (YYYY-MM)，直接返回
  if (dateTimeStr.length <= 7) return dateTimeStr
  // 否则提取年月部分
  const date = new Date(dateTimeStr.replace(' ', 'T'))
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  return `${year}.${month}`
}

// 获取荣誉分类样式
const getHonorCategoryClass = (category: string) => {
  const classes = {
    defense: 'bg-red-500',
    construction: 'bg-blue-500',
    diplomatic: 'bg-green-500'
  }
  return classes[category as keyof typeof classes] || 'bg-gray-500'
}
</script> 