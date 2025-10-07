<template>
  <header class="sticky top-0 z-40 bg-dark-800/80 backdrop-blur-md border-b border-dark-600 shadow-sm">
    <div class="flex items-center justify-between px-4 py-3">
      <div class="flex items-center">
        <button class="lg:hidden text-dark-200 hover:text-dark-100 mr-4" @click="toggleSidebar">
          <i class="fa-solid fa-bars"></i>
        </button>
      </div>
      
      <div class="flex items-center space-x-4">
        <!-- 个性化日期时间显示 -->
        <div class="hidden md:flex items-center space-x-4 text-dark-200">
          <!-- 问候语 -->
          <div class="flex items-center space-x-2">
            <span class="text-base font-medium">{{ greeting }}</span>
          </div>
          
          <!-- 日期 -->
          <div class="flex items-center space-x-2 bg-dark-600/30 px-3 py-1 rounded-full">
            <i class="fa-regular fa-calendar text-base text-blue-400"></i>
            <span class="text-base font-medium">{{ currentDate }}</span>
          </div>
          
          <!-- 星期 -->
          <div class="flex items-center space-x-2 bg-dark-600/30 px-3 py-1 rounded-full">
            <i class="fa-solid fa-calendar-week text-base text-purple-400"></i>
            <span class="text-base font-medium">{{ currentWeekday }}</span>
          </div>
          
          <!-- 工作状态 -->
          <div v-if="workStatus !== '休息时间'" class="flex items-center space-x-2">
            <div class="w-2 h-2 rounded-full animate-pulse" :class="workStatusColor"></div>
            <span class="text-sm font-medium">{{ workStatus }}</span>
          </div>
          
          <!-- 时间 -->
          <div class="flex items-center space-x-2 bg-dark-600/30 px-3 py-1 rounded-full">
            <i class="fa-regular fa-clock text-base text-green-400"></i>
            <span class="text-base font-medium font-mono">{{ currentTime }}</span>
          </div>
        </div>

        <!-- 用户信息和退出登录 -->
        <div class="relative" ref="userMenuRef">
          <button 
            @click="showUserMenu = !showUserMenu"
            class="flex items-center space-x-2 px-3 py-2 rounded-lg hover:bg-dark-600/30 transition-colors"
          >
            <i class="fa-solid fa-user-circle text-xl text-dark-200"></i>
            <span class="text-dark-200 text-sm hidden md:block">{{ currentUser }}</span>
            <i class="fa-solid fa-chevron-down text-xs text-dark-400"></i>
          </button>

          <!-- 下拉菜单 -->
          <transition name="dropdown">
            <div 
              v-if="showUserMenu"
              class="absolute right-0 mt-2 w-48 bg-white dark:bg-dark-700 rounded-lg shadow-lg border border-gray-200 dark:border-dark-600 overflow-hidden"
            >
              <div class="px-4 py-3 border-b border-gray-200 dark:border-dark-600">
                <p class="text-sm text-gray-600 dark:text-dark-300">当前用户</p>
                <p class="text-sm font-medium text-gray-900 dark:text-dark-100">{{ currentUser }}</p>
              </div>
              <button 
                @click="handleLogout"
                class="w-full px-4 py-2 text-left text-sm text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20 flex items-center space-x-2 transition-colors"
              >
                <i class="fa-solid fa-sign-out-alt"></i>
                <span>退出登录</span>
              </button>
            </div>
          </transition>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '@/api/auth'

const router = useRouter()

const isDarkMode = ref(false)
const showUserMenu = ref(false)
const userMenuRef = ref<HTMLElement>()

// 获取当前用户
const currentUser = computed(() => {
  const user = localStorage.getItem('user')
  if (user) {
    try {
      const userObj = JSON.parse(user)
      return userObj.nickname || userObj.username || 'User'
    } catch {
      return 'User'
    }
  }
  return 'User'
})

// 退出登录
const handleLogout = async () => {
  if (confirm('确定要退出登录吗？')) {
    try {
      await logout()
    } catch (error) {
      console.error('退出登录失败:', error)
    } finally {
      // 清除本地存储
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      // 跳转到登录页
      router.push('/login')
    }
  }
}

// 点击外部关闭下拉菜单
const handleClickOutside = (event: MouseEvent) => {
  if (userMenuRef.value && !userMenuRef.value.contains(event.target as Node)) {
    showUserMenu.value = false
  }
}

// 日期时间相关
const currentDate = ref('')
const currentTime = ref('')
const currentWeekday = ref('')
const currentHour = ref(0)
let timeInterval: number | null = null

// 计算问候语
const greeting = computed(() => {
  if (currentHour.value >= 5 && currentHour.value < 12) {
    return '早上好！☀️'
  } else if (currentHour.value >= 12 && currentHour.value < 18) {
    return '下午好！🌤️'
  } else if (currentHour.value >= 18 && currentHour.value < 22) {
    return '晚上好！🌙'
  } else {
    return '夜深了！🌃'
  }
})

// 计算工作状态
const workStatus = computed(() => {
  const hour = currentHour.value
  const day = new Date().getDay()
  
  // 周末
  if (day === 0 || day === 6) {
    if (hour >= 9 && hour < 18) {
      return '周末时光'
    } else {
      return '休息时间'
    }
  }
  
  // 工作日
  if (hour >= 9 && hour < 12) {
    return '上午工作'
  } else if (hour >= 12 && hour < 14) {
    return '午休时间'
  } else if (hour >= 14 && hour < 18) {
    return '下午工作'
  } else if (hour >= 18 && hour < 22) {
    return '加班时间'
  } else {
    return '休息时间'
  }
})

// 计算状态颜色
const workStatusColor = computed(() => {
  const hour = currentHour.value
  const day = new Date().getDay()
  
  if (day === 0 || day === 6) {
    return 'bg-green-400' // 周末绿色
  }
  
  if (hour >= 9 && hour < 18) {
    return 'bg-blue-400' // 工作时间蓝色
  } else if (hour >= 12 && hour < 14) {
    return 'bg-yellow-400' // 午休黄色
  } else if (hour >= 18 && hour < 22) {
    return 'bg-orange-400' // 加班橙色
  } else {
    return 'bg-gray-400' // 休息灰色
  }
})

// 更新日期时间
const updateDateTime = () => {
  const now = new Date()
  
  // 更新小时
  currentHour.value = now.getHours()
  
  // 格式化日期
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  currentDate.value = `${year}-${month}-${day}`
  
  // 格式化时间
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  const seconds = String(now.getSeconds()).padStart(2, '0')
  currentTime.value = `${hours}:${minutes}:${seconds}`
  
  // 格式化星期
  const weekdays = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
  currentWeekday.value = weekdays[now.getDay()]
}

const toggleSidebar = () => {
  // 触发侧边栏切换事件
  const event = new CustomEvent('toggle-sidebar')
  window.dispatchEvent(event)
}

const toggleDarkMode = () => {
  isDarkMode.value = !isDarkMode.value
  document.documentElement.classList.toggle('dark')
  localStorage.setItem('darkMode', isDarkMode.value.toString())
}

onMounted(() => {
  // 初始化日期时间
  updateDateTime()
  
  // 每秒更新时间
  timeInterval = setInterval(updateDateTime, 1000)
  
  const savedDarkMode = localStorage.getItem('darkMode')
  if (savedDarkMode === 'true') {
    isDarkMode.value = true
    document.documentElement.classList.add('dark')
  }
})

onUnmounted(() => {
  // 清理定时器
  if (timeInterval) {
    clearInterval(timeInterval)
  }
  // 移除事件监听
  document.removeEventListener('click', handleClickOutside)
})
</script> 