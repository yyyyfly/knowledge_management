<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 flex items-center justify-center p-4">
    <div class="max-w-md w-full">
      <!-- 登录卡片 -->
      <div class="bg-white rounded-2xl shadow-2xl p-8 space-y-6">
        <!-- Logo和标题 -->
        <div class="text-center">
          <div class="inline-block p-4 bg-gradient-to-br from-blue-500 to-purple-600 rounded-2xl shadow-lg mb-4">
            <i class="fas fa-brain text-white text-4xl"></i>
          </div>
          <h2 class="text-3xl font-bold text-gray-900 mb-2">个人知识管理系统</h2>
          <p class="text-gray-600">请登录您的账户</p>
        </div>

        <!-- 登录表单 -->
        <form @submit.prevent="handleLogin" class="space-y-5">
          <!-- 用户名输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-user text-gray-400 mr-2"></i>
              用户名
            </label>
            <input
              v-model="loginForm.username"
              type="text"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="请输入用户名"
            />
          </div>

          <!-- 密码输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-lock text-gray-400 mr-2"></i>
              密码
            </label>
            <input
              v-model="loginForm.password"
              type="password"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="请输入密码"
            />
          </div>

          <!-- 错误提示 -->
          <div v-if="errorMessage" class="bg-red-50 border border-red-200 rounded-lg p-3">
            <p class="text-red-600 text-sm flex items-center">
              <i class="fas fa-exclamation-circle mr-2"></i>
              {{ errorMessage }}
            </p>
          </div>

          <!-- 登录按钮 -->
          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-4 bg-gradient-to-r from-blue-500 to-purple-600 text-white font-medium rounded-lg hover:from-blue-600 hover:to-purple-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="!loading">
              <i class="fas fa-sign-in-alt mr-2"></i>
              登录
            </span>
            <span v-else>
              <i class="fas fa-spinner fa-spin mr-2"></i>
              登录中...
            </span>
          </button>
        </form>

        <!-- 忘记密码和注册链接 -->
        <div class="pt-4 border-t border-gray-200 space-y-3">
          <p class="text-center text-sm text-gray-600">
            <router-link to="/forgot-password" class="text-orange-600 hover:text-orange-700 font-medium">
              <i class="fas fa-key mr-1"></i>
              忘记密码？
            </router-link>
          </p>
          <p class="text-center text-sm text-gray-600">
            还没有账号？
            <router-link to="/register" class="text-blue-600 hover:text-blue-700 font-medium">
              立即注册
            </router-link>
          </p>
        </div>
      </div>

      <!-- 底部信息 -->
      <div class="text-center mt-6">
        <p class="text-sm text-gray-600">
          © 2024 个人知识管理系统
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth'

const router = useRouter()

const loginForm = ref({
  username: '',
  password: ''
})

const loading = ref(false)
const errorMessage = ref('')

const handleLogin = async () => {
  errorMessage.value = ''
  loading.value = true

  try {
    const response = await login(loginForm.value.username, loginForm.value.password)
    
    if (response.code === 200) {
      // 保存Token和用户信息
      localStorage.setItem('token', response.data.token)
      localStorage.setItem('user', JSON.stringify(response.data.user))
      
      // 获取重定向路径（如果有的话）
      const redirect = (router.currentRoute.value.query.redirect as string) || '/'
      
      // 跳转到指定页面或首页
      router.push(redirect)
    } else {
      // 显示后端返回的具体错误信息
      errorMessage.value = response.message || '登录失败，请重试'
    }
  } catch (error: any) {
    console.error('登录失败:', error)
    // 优先显示后端返回的错误信息
    if (error.response?.data?.message) {
      errorMessage.value = error.response.data.message
    } else if (error.message) {
      errorMessage.value = error.message
    } else {
      errorMessage.value = '登录失败，请检查网络连接或后端服务是否正常'
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
/* 添加一些动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in {
  animation: fadeIn 0.5s ease-out;
}
</style>

