<template>
  <div class="fixed inset-y-0 left-0 z-50 w-64 bg-dark-800 shadow-lg transform transition-transform duration-300 lg:translate-x-0 -translate-x-full h-screen" :class="{ 'translate-x-0': isOpen }">
    <div class="flex flex-col h-full">
      <div class="flex items-center justify-between p-4 border-b border-dark-600">
        <h1 class="text-xl font-bold text-primary flex items-center space-x-2">
          <i class="fa-solid fa-brain"></i>
          <span>知识管理系统</span>
        </h1>
        <button class="lg:hidden text-dark-400 hover:text-dark-100" @click="toggleSidebar">
          <i class="fa-solid fa-xmark"></i>
        </button>
      </div>

      <nav class="flex-1 p-4 space-y-1 overflow-y-auto">
        <!-- 主仪表盘分组 -->
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
          
          <div v-show="expandedGroups.main" class="space-y-1">
            <!-- 主仪表盘链接 -->
            <div class="pl-8 space-y-1">
              <router-link to="/" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                  <i class="fa-solid fa-chart-line"></i>
              </div>
                <span class="ml-3">总览面板</span>
            </router-link>
        </div>

        <!-- 素材组 -->
            <div class="pl-8 space-y-1">
              <button 
                @click="toggleGroup('materials')" 
                class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
              >
                <div class="flex items-center">
            <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-chart-pie"></i>
            </div>
            <span class="ml-3">素材组</span>
                </div>
                <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.materials }"></i>
          </button>
              
              <!-- 素材组子菜单 - 使用配置对象确保顺序 -->
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

            <!-- 行动组 -->
            <div class="pl-8 space-y-1">
              <button 
                @click="toggleGroup('actions')" 
                class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
              >
                <div class="flex items-center">
                  <div class="w-6 flex justify-center">
                    <i class="fa-solid fa-briefcase"></i>
                  </div>
                  <span class="ml-3">行动组</span>
                </div>
                <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.actions }"></i>
              </button>
              
              <!-- 行动组子菜单 -->
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
              <span class="ml-3">系统决策</span>
            </router-link>
            <router-link to="/system-summary" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-chart-line"></i>
              </div>
              <span class="ml-3">系统总结</span>
            </router-link>
          </div>
        </div>

        <!-- 日常行动 -->
        <div class="space-y-1">
          <button 
            @click="toggleGroup('daily')" 
            class="w-full flex items-center justify-between p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors"
          >
            <div class="flex items-center">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-calendar-day"></i>
              </div>
              <span class="ml-3">日常行动</span>
            </div>
            <i class="fa-solid fa-chevron-down transition-transform duration-200" :class="{ 'rotate-180': expandedGroups.daily }"></i>
          </button>
          
          <!-- 日常行动子菜单 -->
          <div v-show="expandedGroups.daily" class="pl-8 space-y-1">
            <router-link to="/daily/material-record" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-pen-fancy"></i>
              </div>
              <span class="ml-3">笔记记录</span>
            </router-link>
            <router-link to="/daily/project-record" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-tasks"></i>
              </div>
              <span class="ml-3">项目执行</span>
            </router-link>
            <router-link to="/daily/daily-summary" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-clipboard-list"></i>
              </div>
              <span class="ml-3">日常总结</span>
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
            <router-link to="/system-overview" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-circle-info"></i>
              </div>
              <span class="ml-3">系统概述</span>
            </router-link>
            <router-link to="/version-history" class="flex items-center p-3 rounded-lg text-dark-200 hover:bg-dark-600/30 transition-colors">
              <div class="w-6 flex justify-center">
                <i class="fa-solid fa-code-branch"></i>
              </div>
              <span class="ml-3">版本更新</span>
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
import { ref } from 'vue'

// ======= 素材组唯一权威顺序配置，严禁其他地方定义或引用 =======
const materialsConfig = [
  { name: '碎片笔记', path: '/fragment-notes', icon: 'fa-solid fa-puzzle-piece' },
  { name: '框架笔记', path: '/framework-notes', icon: 'fa-solid fa-sitemap' },
  { name: '求学笔记', path: '/study-notes', icon: 'fa-solid fa-graduation-cap' },
  { name: '背诵笔记', path: '/memorization-notes', icon: 'fa-regular fa-lightbulb' },
  { name: '刷题笔记', path: '/exercise-notes', icon: 'fa-regular fa-pen-to-square' },
  { name: '实战笔记', path: '/practical-notes', icon: 'fa-solid fa-flask' }
]
// ======= 只允许在本文件Sidebar.vue中用 materialsConfig 渲染素材组 =======

const isOpen = ref(false)
const expandedGroups = ref({
  main: true, // 默认展开主仪表盘
  materials: false,
  actions: false,
  headquarters: false,
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