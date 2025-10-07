<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 flex items-center justify-center p-4">
    <div class="max-w-md w-full">
      <!-- 忘记密码卡片 -->
      <div class="bg-white rounded-2xl shadow-2xl p-8 space-y-6">
        <!-- Logo和标题 -->
        <div class="text-center">
          <div class="inline-block p-4 bg-gradient-to-br from-orange-500 to-red-600 rounded-2xl shadow-lg mb-4">
            <i class="fas fa-key text-white text-4xl"></i>
          </div>
          <h2 class="text-3xl font-bold text-gray-900 mb-2">重置密码</h2>
          <p class="text-gray-600">请输入您的账户信息来重置密码</p>
        </div>

        <!-- 成功提示 -->
        <div v-if="resetSuccess" class="bg-green-50 border border-green-200 rounded-lg p-4">
          <div class="flex items-start">
            <i class="fas fa-check-circle text-green-500 text-xl mr-3 mt-0.5"></i>
            <div>
              <p class="text-green-800 font-medium mb-1">密码重置成功！</p>
              <p class="text-green-700 text-sm">您的新密码已设置成功，请使用新密码登录。</p>
              <button 
                @click="goToLogin"
                class="mt-3 text-sm text-green-700 hover:text-green-800 font-medium underline"
              >
                <i class="fas fa-arrow-left mr-1"></i>
                返回登录
              </button>
            </div>
          </div>
        </div>

        <!-- 重置表单 -->
        <form v-if="!resetSuccess" @submit.prevent="handleResetPassword" class="space-y-5">
          <!-- 用户名输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-user text-gray-400 mr-2"></i>
              用户名
            </label>
            <input
              v-model="resetForm.username"
              type="text"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
              placeholder="请输入您的用户名"
            />
          </div>

          <!-- 邮箱输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-envelope text-gray-400 mr-2"></i>
              邮箱
            </label>
            <input
              v-model="resetForm.email"
              type="email"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
              placeholder="请输入注册时使用的邮箱"
            />
            <p class="mt-1 text-xs text-gray-500">
              <i class="fas fa-info-circle mr-1"></i>
              请输入您注册时使用的邮箱地址
            </p>
          </div>

          <!-- 新密码输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-lock text-gray-400 mr-2"></i>
              新密码
            </label>
            <input
              v-model="resetForm.newPassword"
              type="password"
              required
              minlength="6"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
              placeholder="请输入新密码（至少6位）"
            />
          </div>

          <!-- 确认密码输入 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              <i class="fas fa-lock text-gray-400 mr-2"></i>
              确认新密码
            </label>
            <input
              v-model="resetForm.confirmPassword"
              type="password"
              required
              minlength="6"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
              placeholder="请再次输入新密码"
            />
          </div>

          <!-- 错误提示 -->
          <div v-if="errorMessage" class="bg-red-50 border border-red-200 rounded-lg p-3">
            <p class="text-red-600 text-sm flex items-center">
              <i class="fas fa-exclamation-circle mr-2"></i>
              {{ errorMessage }}
            </p>
          </div>

          <!-- 重置按钮 -->
          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-4 bg-gradient-to-r from-orange-500 to-red-600 text-white font-medium rounded-lg hover:from-orange-600 hover:to-red-700 focus:outline-none focus:ring-2 focus:ring-orange-500 focus:ring-offset-2 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="!loading">
              <i class="fas fa-key mr-2"></i>
              重置密码
            </span>
            <span v-else>
              <i class="fas fa-spinner fa-spin mr-2"></i>
              处理中...
            </span>
          </button>
        </form>

        <!-- 返回登录链接 -->
        <div v-if="!resetSuccess" class="pt-4 border-t border-gray-200">
          <p class="text-center text-sm text-gray-600">
            想起密码了？
            <router-link to="/login" class="text-blue-600 hover:text-blue-700 font-medium">
              返回登录
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
import { resetPassword } from '@/api/auth'

const router = useRouter()

const resetForm = ref({
  username: '',
  email: '',
  newPassword: '',
  confirmPassword: ''
})

const loading = ref(false)
const errorMessage = ref('')
const resetSuccess = ref(false)

const handleResetPassword = async () => {
  errorMessage.value = ''

  // 验证密码是否一致
  if (resetForm.value.newPassword !== resetForm.value.confirmPassword) {
    errorMessage.value = '两次输入的密码不一致，请重新输入'
    return
  }

  // 验证密码长度
  if (resetForm.value.newPassword.length < 6) {
    errorMessage.value = '密码长度至少为6位'
    return
  }

  loading.value = true

  try {
    const response = await resetPassword(
      resetForm.value.username,
      resetForm.value.email,
      resetForm.value.newPassword
    )
    
    if (response.code === 200) {
      resetSuccess.value = true
      // 3秒后自动跳转到登录页
      setTimeout(() => {
        router.push('/login')
      }, 3000)
    } else {
      errorMessage.value = response.message || '密码重置失败，请重试'
    }
  } catch (error: any) {
    console.error('密码重置失败:', error)
    errorMessage.value = error.response?.data?.message || '密码重置失败，请检查您的信息是否正确'
  } finally {
    loading.value = false
  }
}

const goToLogin = () => {
  router.push('/login')
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
