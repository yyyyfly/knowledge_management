<template>
  <div class="fixed inset-y-0 left-0 z-50 w-64 bg-dark-800 shadow-lg transform transition-transform duration-300 lg:translate-x-0 -translate-x-full h-screen" :class="{ 'translate-x-0': isOpen }">
    <div class="flex flex-col h-full">
      <div class="flex items-center justify-between p-4 border-b border-dark-600">
        <h1 class="text-xl font-bold text-primary flex items-center space-x-2">
          <i class="fa-solid fa-project-diagram"></i>
          <span>知识管理系统</span>
        </h1>
        <button class="lg:hidden text-dark-400 hover:text-dark-100" @click="toggleSidebar">
          <i class="fa-solid fa-xmark"></i>
        </button>
      </div>

      <nav class="flex-1 p-4 space-y-1 overflow-y-auto">
        <!-- 主仪表盘 -->
        <div class="space-y-1">
          <button 
            @click="toggleGroup('main')" 
            class="w-full flex items-center justify-between p-3 rounded-lg bg-primary/10 text-primary hover:bg-primary/20 transition-colors"
          >
            <div class="flex items-center">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-gauge-high"></i>
              </div>
              <span class="ml-3">主仪表盘</span>
            </div>
            <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.main }"></i>
          </button>
          
          <div v-show="expandedGroups.main" class="pl-8 space-y-1">
            <!-- 总仪表盘 -->
            <router-link to="/overview/data" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-chart-bar"></i>
              </div>
              <span class="ml-3">总仪表盘</span>
            </router-link>
            
            <!-- 时光轨迹 -->
            <router-link to="/overview/timeline" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-history"></i>
              </div>
              <span class="ml-3">时光轨迹</span>
            </router-link>
            
            <!-- 指导大纲 -->
            <router-link to="/overview/guidance-outline" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-sitemap"></i>
              </div>
              <span class="ml-3">指导大纲</span>
            </router-link>
            
            <!-- 模拟实战 -->
            <router-link to="/training-center" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-laptop-code"></i>
              </div>
              <span class="ml-3">模拟实战</span>
            </router-link>
            
            <!-- 需求草案 -->
            <router-link to="/overview/requirement-draft" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-file-contract"></i>
              </div>
              <span class="ml-3">需求草案</span>
            </router-link>
          </div>
        </div>

        <!-- 知识积累 -->
        <div class="space-y-1">
          <button 
            @click="toggleGroup('knowledge')" 
            class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
          >
            <div class="flex items-center">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-warehouse"></i>
              </div>
              <span class="ml-3">储备积累</span>
            </div>
            <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.knowledge }"></i>
          </button>
          
          <div v-show="expandedGroups.knowledge" class="space-y-1">
            <!-- 素材积累 -->
            <div class="pl-4 space-y-1">
              <button 
                @click="toggleGroup('materials')" 
                class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
              >
                <div class="flex items-center">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-layer-group"></i>
                  </div>
                  <span class="ml-3">素材回顾</span>
                </div>
                <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.materials }"></i>
              </button>
              
              <!-- 素材回顾子菜单 -->
              <div v-show="expandedGroups.materials" class="pl-8 space-y-1">
                <router-link 
                  v-for="item in materialsConfig" 
                  :key="item.path"
                  :to="item.path" 
                  class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
                >
                  <div class="w-6 flex justify-center">
                    <i :class="item.icon"></i>
                  </div>
                  <span class="ml-3">{{ item.name }}</span>
                </router-link>
              </div>
            </div>

            <!-- 经验积累 -->
            <div class="pl-4 space-y-1">
              <button 
                @click="toggleGroup('actions')" 
                class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
              >
                <div class="flex items-center">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-compass"></i>
                  </div>
                  <span class="ml-3">经验回顾</span>
                </div>
                <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.actions }"></i>
              </button>
              
              <!-- 经验回顾子菜单 -->
              <div v-show="expandedGroups.actions" class="pl-8 space-y-1">
                <router-link to="/actions/defense-projects" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-crosshairs"></i>
                  </div>
                  <span class="ml-3">战争行动</span>
                </router-link>
                <router-link to="/actions/construction-projects" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-building"></i>
                  </div>
                  <span class="ml-3">工程建设</span>
                </router-link>
                <router-link to="/actions/diplomatic-projects" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-handshake"></i>
                  </div>
                  <span class="ml-3">外交行动</span>
                </router-link>
              </div>
            </div>

            <!-- 笔记记录（与素材回顾、经验回顾同级） -->
            <div class="pl-4 space-y-1">
              <button 
                @click="toggleGroup('noteRecord')" 
                class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
              >
                <div class="flex items-center">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-pen-fancy"></i>
                  </div>
                  <span class="ml-3">笔记记录</span>
                </div>
                <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.noteRecord }"></i>
              </button>
              
              <!-- 笔记记录子菜单 -->
              <div v-show="expandedGroups.noteRecord" class="pl-8 space-y-1">
                <router-link to="/daily/material-record" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-file-pen"></i>
                  </div>
                  <span class="ml-3">素材笔记</span>
                </router-link>
                <router-link to="/daily/project-insight" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-lightbulb"></i>
                  </div>
                  <span class="ml-3">项目心得</span>
                </router-link>
              </div>
            </div>
          </div>
        </div>

        <!-- 首脑决策 -->
        <div class="space-y-1">
          <button 
            @click="toggleGroup('headquarters')" 
            class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
          >
            <div class="flex items-center">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-crown"></i>
              </div>
              <span class="ml-3">首脑决策</span>
            </div>
            <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.headquarters }"></i>
          </button>
          
          <!-- 首脑决策子菜单 -->
          <div v-show="expandedGroups.headquarters" class="pl-8 space-y-1">
            <router-link to="/system-decisions" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-chess-king"></i>
              </div>
              <span class="ml-3">日常决策</span>
            </router-link>
          </div>
        </div>

        <!-- 执行行动 -->
        <div class="space-y-1">
          <button 
            @click="toggleGroup('daily')" 
            class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
          >
            <div class="flex items-center">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-bolt"></i>
              </div>
              <span class="ml-3">执行行动</span>
            </div>
            <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.daily }"></i>
          </button>
          
          <!-- 执行行动子菜单 -->
          <div v-show="expandedGroups.daily" class="pl-8 space-y-1">
            <router-link to="/daily/project-record" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-tasks"></i>
              </div>
              <span class="ml-3">项目执行</span>
            </router-link>
            <router-link to="/daily/project-analysis" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-microscope"></i>
              </div>
              <span class="ml-3">项目分析</span>
            </router-link>
            <router-link to="/ready-to-deploy" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-flag-checkered"></i>
              </div>
              <span class="ml-3">整装待发</span>
            </router-link>
          </div>
        </div>

        <!-- 系统总结 -->
        <div class="space-y-1">
          <button 
            @click="toggleGroup('summary')" 
            class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
          >
            <div class="flex items-center">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-chart-line"></i>
              </div>
              <span class="ml-3">系统总结</span>
            </div>
            <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.summary }"></i>
          </button>
          
          <!-- 系统总结子菜单 -->
          <div v-show="expandedGroups.summary" class="pl-8 space-y-1">
            <router-link to="/daily/daily-summary" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-calendar-day"></i>
              </div>
              <span class="ml-3">日常总结</span>
            </router-link>
            <router-link to="/system-summary" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-clipboard-check"></i>
              </div>
              <span class="ml-3">系统总结</span>
            </router-link>
          </div>
        </div>
        
        <!-- 系统配置 -->
        <div class="space-y-1">
          <button 
            @click="toggleGroup('system')" 
            class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
          >
            <div class="flex items-center">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-cog"></i>
              </div>
              <span class="ml-3">系统配置</span>
            </div>
            <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.system }"></i>
          </button>
          
          <!-- 系统配置子菜单 -->
          <div v-show="expandedGroups.system" class="pl-8 space-y-1">
            <router-link to="/version-history" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-code-branch"></i>
              </div>
              <span class="ml-3">版本更新</span>
            </router-link>
            <!-- 用户管理（仅管理员可见） -->
            <router-link v-if="isAdmin" to="/user-management" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-users-cog"></i>
              </div>
              <span class="ml-3">用户管理</span>
            </router-link>
            <!-- 数据管理（仅管理员可见） -->
            <router-link v-if="isAdmin" to="/data-management" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-database"></i>
              </div>
              <span class="ml-3">数据管理</span>
            </router-link>
            <router-link to="/account-settings" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-user-cog"></i>
              </div>
              <span class="ml-3">账号设置</span>
            </router-link>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

// ======= 素材组唯一权威顺序配置，严禁其他地方定义或引用 =======
const materialsConfig = [
  { name: '碎片笔记', path: '/fragment-notes', icon: 'fa-solid fa-puzzle-piece' },
  { name: '技能笔记', path: '/skill-notes', icon: 'fa-solid fa-tools' },
  { name: '求学笔记', path: '/study-notes', icon: 'fa-solid fa-graduation-cap' },
  { name: '拓展笔记', path: '/expansion-notes', icon: 'fa-solid fa-book-open' },
  { name: '刷题笔记', path: '/exercise-notes', icon: 'fa-regular fa-pen-to-square' },
  { name: '实战笔记', path: '/practical-notes', icon: 'fa-solid fa-flask' }
]
// ======= 只允许在本文件Sidebar.vue中用 materialsConfig 渲染素材组 =======

// 检查是否是管理员
const isAdmin = computed(() => {
  const userStr = localStorage.getItem('user')
  if (userStr) {
    try {
      const user = JSON.parse(userStr)
      return user.username === 'admin'
    } catch {
      return false
    }
  }
  return false
})

const isOpen = ref(false)
const expandedGroups = ref({
  main: true, // 默认展开主仪表盘
  knowledge: false, // 知识积累
  materials: false, // 素材回顾
  actions: false, // 经验回顾
  noteRecord: false, // 笔记记录
  headquarters: false, // 首脑决策
  summary: false, // 系统总结
  daily: false,
  system: false
})

const toggleSidebar = () => {
  isOpen.value = !isOpen.value
}

const toggleGroup = (groupName: keyof typeof expandedGroups.value) => {
  expandedGroups.value[groupName] = !expandedGroups.value[groupName]
}
</script>

<style scoped>
.overflow-y-auto {
  scrollbar-width: thin;
  scrollbar-color: rgba(156, 163, 175, 0.5) transparent;
}

.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: transparent;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background-color: rgba(156, 163, 175, 0.5);
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background-color: rgba(156, 163, 175, 0.7);
}
</style> 