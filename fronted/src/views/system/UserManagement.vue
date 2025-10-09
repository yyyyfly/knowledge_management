<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50 p-6">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-3xl font-bold text-gray-900 flex items-center space-x-3">
            <div class="w-12 h-12 bg-gradient-to-br from-blue-500 to-purple-600 rounded-xl flex items-center justify-center">
              <i class="fas fa-users text-white text-xl"></i>
            </div>
            <span>用户管理</span>
          </h1>
          <p class="mt-2 text-gray-600">查看和管理系统用户信息</p>
        </div>
        
        <!-- 统计信息 -->
        <div class="bg-white rounded-xl shadow-md p-4 flex items-center space-x-4">
          <div class="text-center">
            <div class="text-2xl font-bold text-blue-600">{{ users.length }}</div>
            <div class="text-sm text-gray-600">总用户数</div>
          </div>
          <div class="w-px h-12 bg-gray-200"></div>
          <div class="text-center">
            <div class="text-2xl font-bold text-green-600">{{ activeUsersCount }}</div>
            <div class="text-sm text-gray-600">活跃用户</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 权限提示卡片 -->
    <div class="mb-6">
      <div class="bg-gradient-to-r from-yellow-50 to-orange-50 rounded-xl p-4 border border-yellow-200">
        <div class="flex items-center space-x-3">
          <i class="fas fa-shield-alt text-yellow-600 text-xl"></i>
          <span class="text-sm text-gray-700">
            <span class="font-semibold">管理员专属页面</span> - 只有admin用户可以查看此页面
          </span>
        </div>
      </div>
    </div>

    <!-- 用户列表 -->
    <div class="bg-white rounded-xl shadow-lg overflow-hidden">
      <div class="p-6 border-b border-gray-200">
        <h2 class="text-xl font-bold text-gray-900 flex items-center">
          <i class="fas fa-list text-blue-600 mr-2"></i>
          用户列表
        </h2>
      </div>

      <!-- 表格 -->
      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">用户名</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">昵称</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">邮箱</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">手机号</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">最后登录</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">注册时间</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="user in users" :key="user.id" class="hover:bg-gray-50 transition-colors">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ user.id }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center">
                  <div class="w-8 h-8 rounded-full bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center text-white font-semibold text-sm mr-3">
                    {{ user.username.charAt(0).toUpperCase() }}
                  </div>
                  <div class="font-medium text-gray-900">
                    {{ user.username }}
                    <span v-if="user.username === 'admin'" class="ml-2 inline-flex items-center px-2 py-0.5 rounded text-xs font-medium bg-red-100 text-red-800">
                      <i class="fas fa-crown mr-1"></i>管理员
                    </span>
                  </div>
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                {{ user.nickname || '-' }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                <div class="flex items-center">
                  <i class="far fa-envelope text-gray-400 mr-2"></i>
                  {{ user.email || '-' }}
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                <div class="flex items-center">
                  <i class="fas fa-mobile-alt text-gray-400 mr-2"></i>
                  {{ user.phone || '-' }}
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span 
                  :class="[
                    'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium',
                    user.status === 1 
                      ? 'bg-green-100 text-green-800' 
                      : 'bg-red-100 text-red-800'
                  ]"
                >
                  <span :class="[
                    'w-1.5 h-1.5 rounded-full mr-1.5',
                    user.status === 1 ? 'bg-green-500' : 'bg-red-500'
                  ]"></span>
                  {{ user.status === 1 ? '正常' : '禁用' }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                <div class="flex items-center">
                  <i class="far fa-clock text-gray-400 mr-2"></i>
                  {{ formatDateTime(user.lastLoginTime) }}
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                {{ formatDateTime(user.recCreateTime) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- 空状态 -->
      <div v-if="users.length === 0" class="text-center py-12">
        <i class="fas fa-users text-gray-300 text-6xl mb-4"></i>
        <p class="text-gray-500">暂无用户数据</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '@/api/request'

const router = useRouter()

// 用户列表
const users = ref<any[]>([])

// 计算活跃用户数
const activeUsersCount = computed(() => {
  return users.value.filter(u => u.status === 1).length
})

// 加载用户列表
const loadUsers = async () => {
  try {
    const response = await request.get('/auth/users')
    if (response.code === 200) {
      users.value = response.data || []
    } else {
      alert(response.message || '加载失败')
      // 如果权限不足，跳转回首页
      if (response.message.includes('权限不足')) {
        router.push('/')
      }
    }
  } catch (error: any) {
    console.error('加载用户列表失败:', error)
    alert('加载用户列表失败，请稍后重试')
    // 如果是权限错误，跳转回首页
    if (error.response?.status === 403 || error.response?.status === 401) {
      router.push('/')
    }
  }
}

// 格式化日期时间
const formatDateTime = (dateTime: string | null) => {
  if (!dateTime) return '-'
  try {
    const date = new Date(dateTime)
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch {
    return '-'
  }
}

// 页面加载时获取数据
onMounted(() => {
  loadUsers()
})
</script>

<style scoped>
/* 表格样式优化 */
table {
  border-collapse: separate;
  border-spacing: 0;
}

tbody tr:last-child td {
  border-bottom: none;
}

/* 悬停效果 */
tbody tr:hover {
  transform: translateY(-1px);
  transition: all 0.2s;
}
</style>

