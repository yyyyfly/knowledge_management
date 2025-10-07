<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 flex items-center justify-center p-4">
    <div class="max-w-md w-full">
      <!-- 注册卡片 -->
      <div class="bg-white rounded-2xl shadow-2xl p-8 space-y-6">
        <!-- Logo和标题 -->
        <div class="text-center">
          <div class="inline-block p-4 bg-gradient-to-br from-blue-500 to-purple-600 rounded-2xl shadow-lg mb-4">
            <i class="fas fa-user-plus text-white text-4xl"></i>
          </div>
          <h2 class="text-3xl font-bold text-gray-900 mb-2">创建账户</h2>
          <p class="text-gray-600">注册您的个人知识管理账号</p>
        </div>

        <!-- 注册表单 -->
        <form @submit.prevent="handleRegister" class="space-y-5">
          <!-- 用户名输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-user text-gray-400 mr-2"></i>
              用户名
            </label>
            <input
              v-model="registerForm.username"
              type="text"
              required
              minlength="3"
              maxlength="20"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="3-20个字符"
            />
          </div>

          <!-- 昵称输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-id-card text-gray-400 mr-2"></i>
              昵称
            </label>
            <input
              v-model="registerForm.nickname"
              type="text"
              required
              maxlength="50"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="显示名称"
            />
          </div>

          <!-- 邮箱输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-envelope text-gray-400 mr-2"></i>
              邮箱 <span class="text-red-500">*</span>
            </label>
            <input
              v-model="registerForm.email"
              type="email"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="用于密码找回"
            />
            <p class="mt-1 text-xs text-gray-500">
              <i class="fas fa-info-circle mr-1"></i>
              邮箱用于密码找回，请确保填写正确
            </p>
          </div>

          <!-- 手机号输入（可选） -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-phone text-gray-400 mr-2"></i>
              手机号（可选）
            </label>
            <input
              v-model="registerForm.phone"
              type="tel"
              maxlength="11"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="11位手机号码"
            />
          </div>

          <!-- 密码输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-lock text-gray-400 mr-2"></i>
              密码
            </label>
            <input
              v-model="registerForm.password"
              type="password"
              required
              minlength="5"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="至少5个字符"
            />
          </div>

          <!-- 确认密码输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-lock text-gray-400 mr-2"></i>
              确认密码
            </label>
            <input
              v-model="registerForm.confirmPassword"
              type="password"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="再次输入密码"
            />
          </div>

          <!-- 成功提示 -->
          <div v-if="successMessage" class="bg-green-50 border border-green-200 rounded-lg p-3">
            <p class="text-green-600 text-sm flex items-center">
              <i class="fas fa-check-circle mr-2"></i>
              {{ successMessage }}
            </p>
          </div>

          <!-- 错误提示 -->
          <div v-if="errorMessage" class="bg-red-50 border border-red-200 rounded-lg p-3">
            <p class="text-red-600 text-sm flex items-center">
              <i class="fas fa-exclamation-circle mr-2"></i>
              {{ errorMessage }}
            </p>
          </div>

          <!-- 注册按钮 -->
          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-4 bg-gradient-to-r from-blue-500 to-purple-600 text-white font-medium rounded-lg hover:from-blue-600 hover:to-purple-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="!loading">
              <i class="fas fa-user-plus mr-2"></i>
              注册
            </span>
            <span v-else>
              <i class="fas fa-spinner fa-spin mr-2"></i>
              注册中...
            </span>
          </button>
        </form>

        <!-- 登录链接 -->
        <div class="pt-4 border-t border-gray-200">
          <p class="text-center text-sm text-gray-600">
            已有账号？
            <router-link to="/login" class="text-blue-600 hover:text-blue-700 font-medium">
              立即登录
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
import { register } from '@/api/auth'

const router = useRouter()

const registerForm = ref({
  username: '',
  nickname: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: ''
})

const loading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const handleRegister = async () => {
  errorMessage.value = ''
  successMessage.value = ''

  // 验证密码一致性
  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    errorMessage.value = '两次输入的密码不一致'
    return
  }

  // 验证密码长度
  if (registerForm.value.password.length < 5) {
    errorMessage.value = '密码至少需要5个字符'
    return
  }

  loading.value = true

  // 验证邮箱格式
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(registerForm.value.email)) {
    errorMessage.value = '请输入有效的邮箱地址'
    return
  }

  try {
    const response = await register(
      registerForm.value.username,
      registerForm.value.password,
      registerForm.value.nickname,
      registerForm.value.email,
      registerForm.value.phone
    )

    if (response.code === 200) {
      successMessage.value = '注册成功！正在跳转到登录页...'
      
      // 2秒后跳转到登录页
      setTimeout(() => {
        router.push('/login')
      }, 2000)
    } else {
      errorMessage.value = response.message || '注册失败，请重试'
    }
  } catch (error: any) {
    console.error('注册失败:', error)
    errorMessage.value = error.response?.data?.message || '注册失败，请检查网络连接'
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





