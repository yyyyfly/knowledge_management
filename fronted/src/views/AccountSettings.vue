<template>
  <div class="p-6">
    <!-- 页面标题 -->
    <div class="mb-6">
      <h1 class="text-2xl font-semibold text-gray-900">账号设置</h1>
      <p class="mt-1 text-sm text-gray-600">
        管理您的个人信息和账号安全
      </p>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <!-- 基本信息卡片 -->
      <div class="bg-white rounded-xl shadow-lg p-6">
        <div class="flex items-center mb-6">
          <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
            <i class="fas fa-user text-blue-600 text-lg"></i>
          </div>
          <h2 class="ml-3 text-lg font-semibold text-gray-900">基本信息</h2>
        </div>

        <form @submit.prevent="handleUpdateInfo" class="space-y-4">
          <!-- 用户名（只读） -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              用户名
            </label>
            <input
              :value="userInfo.username"
              type="text"
              disabled
              class="w-full px-4 py-3 border border-gray-300 rounded-lg bg-gray-100 text-gray-500 cursor-not-allowed"
            />
            <p class="mt-1 text-xs text-gray-500">
              <i class="fas fa-lock mr-1"></i>
              用户名不可修改
            </p>
          </div>

          <!-- 昵称 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              昵称
            </label>
            <input
              v-model="userInfo.nickname"
              type="text"
              required
              maxlength="50"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="显示名称"
            />
          </div>

          <!-- 邮箱 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              邮箱
            </label>
            <input
              v-model="userInfo.email"
              type="email"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="用于密码找回"
            />
          </div>

          <!-- 手机号 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              手机号
            </label>
            <input
              v-model="userInfo.phone"
              type="tel"
              maxlength="11"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
              placeholder="11位手机号码"
            />
          </div>

          <!-- 成功/错误提示 -->
          <div v-if="infoSuccessMessage" class="bg-green-50 border border-green-200 rounded-lg p-3">
            <p class="text-green-600 text-sm flex items-center">
              <i class="fas fa-check-circle mr-2"></i>
              {{ infoSuccessMessage }}
            </p>
          </div>
          <div v-if="infoErrorMessage" class="bg-red-50 border border-red-200 rounded-lg p-3">
            <p class="text-red-600 text-sm flex items-center">
              <i class="fas fa-exclamation-circle mr-2"></i>
              {{ infoErrorMessage }}
            </p>
          </div>

          <!-- 保存按钮 -->
          <button
            type="submit"
            :disabled="infoLoading"
            class="w-full py-3 px-4 bg-gradient-to-r from-blue-500 to-purple-600 text-white font-medium rounded-lg hover:from-blue-600 hover:to-purple-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="!infoLoading">
              <i class="fas fa-save mr-2"></i>
              保存修改
            </span>
            <span v-else>
              <i class="fas fa-spinner fa-spin mr-2"></i>
              保存中...
            </span>
          </button>
        </form>
      </div>

      <!-- 修改密码卡片 -->
      <div class="bg-white rounded-xl shadow-lg p-6">
        <div class="flex items-center mb-6">
          <div class="w-10 h-10 bg-orange-100 rounded-lg flex items-center justify-center">
            <i class="fas fa-key text-orange-600 text-lg"></i>
          </div>
          <h2 class="ml-3 text-lg font-semibold text-gray-900">修改密码</h2>
        </div>

        <form @submit.prevent="handleChangePassword" class="space-y-4">
          <!-- 原密码 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              原密码
            </label>
            <input
              v-model="passwordForm.oldPassword"
              type="password"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
              placeholder="请输入原密码"
            />
          </div>

          <!-- 新密码 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              新密码
            </label>
            <input
              v-model="passwordForm.newPassword"
              type="password"
              required
              minlength="6"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
              placeholder="至少6位字符"
            />
          </div>

          <!-- 确认新密码 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              确认新密码
            </label>
            <input
              v-model="passwordForm.confirmPassword"
              type="password"
              required
              minlength="6"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all"
              placeholder="再次输入新密码"
            />
          </div>

          <!-- 成功/错误提示 -->
          <div v-if="passwordSuccessMessage" class="bg-green-50 border border-green-200 rounded-lg p-3">
            <p class="text-green-600 text-sm flex items-center">
              <i class="fas fa-check-circle mr-2"></i>
              {{ passwordSuccessMessage }}
            </p>
          </div>
          <div v-if="passwordErrorMessage" class="bg-red-50 border border-red-200 rounded-lg p-3">
            <p class="text-red-600 text-sm flex items-center">
              <i class="fas fa-exclamation-circle mr-2"></i>
              {{ passwordErrorMessage }}
            </p>
          </div>

          <!-- 修改按钮 -->
          <button
            type="submit"
            :disabled="passwordLoading"
            class="w-full py-3 px-4 bg-gradient-to-r from-orange-500 to-red-600 text-white font-medium rounded-lg hover:from-orange-600 hover:to-red-700 focus:outline-none focus:ring-2 focus:ring-orange-500 focus:ring-offset-2 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="!passwordLoading">
              <i class="fas fa-key mr-2"></i>
              修改密码
            </span>
            <span v-else>
              <i class="fas fa-spinner fa-spin mr-2"></i>
              修改中...
            </span>
          </button>
        </form>

        <!-- 安全提示 -->
        <div class="mt-6 bg-yellow-50 border border-yellow-200 rounded-lg p-4">
          <div class="flex items-start">
            <i class="fas fa-shield-alt text-yellow-600 mr-3 mt-1"></i>
            <div class="text-sm text-yellow-800">
              <p class="font-medium mb-1">安全提示</p>
              <ul class="space-y-1 ml-4">
                <li>• 密码长度至少6位</li>
                <li>• 建议包含字母、数字和特殊字符</li>
                <li>• 定期修改密码以保护账号安全</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 账号信息展示 -->
    <div class="mt-6 bg-white rounded-xl shadow-lg p-6">
      <div class="flex items-center mb-4">
        <div class="w-10 h-10 bg-gray-100 rounded-lg flex items-center justify-center">
          <i class="fas fa-info-circle text-gray-600 text-lg"></i>
        </div>
        <h2 class="ml-3 text-lg font-semibold text-gray-900">账号信息</h2>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div class="bg-gray-50 rounded-lg p-4">
          <p class="text-xs text-gray-500 mb-1">账号状态</p>
          <p class="text-sm font-medium text-green-600">
            <i class="fas fa-check-circle mr-1"></i>
            正常
          </p>
        </div>
        <div class="bg-gray-50 rounded-lg p-4">
          <p class="text-xs text-gray-500 mb-1">注册时间</p>
          <p class="text-sm font-medium text-gray-900">
            {{ formatDate(userInfo.recCreateTime) }}
          </p>
        </div>
        <div class="bg-gray-50 rounded-lg p-4">
          <p class="text-xs text-gray-500 mb-1">最后登录</p>
          <p class="text-sm font-medium text-gray-900">
            {{ formatDate(userInfo.lastLoginTime) }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getCurrentUser, updateUserInfo, changePassword } from '@/api/auth'

// 用户信息
const userInfo = ref({
  username: '',
  nickname: '',
  email: '',
  phone: '',
  recCreateTime: '',
  lastLoginTime: ''
})

// 基本信息表单状态
const infoLoading = ref(false)
const infoSuccessMessage = ref('')
const infoErrorMessage = ref('')

// 密码表单
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 密码表单状态
const passwordLoading = ref(false)
const passwordSuccessMessage = ref('')
const passwordErrorMessage = ref('')

// 加载用户信息
const loadUserInfo = async () => {
  try {
    const response = await getCurrentUser()
    if (response.code === 200 && response.data) {
      userInfo.value = {
        username: response.data.username || '',
        nickname: response.data.nickname || '',
        email: response.data.email || '',
        phone: response.data.phone || '',
        recCreateTime: response.data.recCreateTime || '',
        lastLoginTime: response.data.lastLoginTime || ''
      }
    }
  } catch (error) {
    console.error('加载用户信息失败:', error)
  }
}

// 更新基本信息
const handleUpdateInfo = async () => {
  infoSuccessMessage.value = ''
  infoErrorMessage.value = ''
  infoLoading.value = true

  try {
    const response = await updateUserInfo({
      nickname: userInfo.value.nickname,
      email: userInfo.value.email,
      phone: userInfo.value.phone
    })

    if (response.code === 200) {
      infoSuccessMessage.value = '个人信息更新成功！'
      // 更新本地存储的用户信息
      const storedUser = JSON.parse(localStorage.getItem('user') || '{}')
      storedUser.nickname = userInfo.value.nickname
      storedUser.email = userInfo.value.email
      storedUser.phone = userInfo.value.phone
      localStorage.setItem('user', JSON.stringify(storedUser))
      
      // 3秒后清除成功提示
      setTimeout(() => {
        infoSuccessMessage.value = ''
      }, 3000)
    } else {
      infoErrorMessage.value = response.message || '更新失败，请重试'
    }
  } catch (error: any) {
    console.error('更新信息失败:', error)
    infoErrorMessage.value = error.response?.data?.message || '更新失败，请检查网络连接'
  } finally {
    infoLoading.value = false
  }
}

// 修改密码
const handleChangePassword = async () => {
  passwordSuccessMessage.value = ''
  passwordErrorMessage.value = ''

  // 验证密码
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    passwordErrorMessage.value = '两次输入的新密码不一致'
    return
  }

  if (passwordForm.value.newPassword.length < 6) {
    passwordErrorMessage.value = '新密码长度至少为6位'
    return
  }

  passwordLoading.value = true

  try {
    const response = await changePassword(
      passwordForm.value.oldPassword,
      passwordForm.value.newPassword
    )

    if (response.code === 200) {
      passwordSuccessMessage.value = '密码修改成功！'
      // 清空表单
      passwordForm.value = {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
      
      // 3秒后清除成功提示
      setTimeout(() => {
        passwordSuccessMessage.value = ''
      }, 3000)
    } else {
      passwordErrorMessage.value = response.message || '修改失败，请重试'
    }
  } catch (error: any) {
    console.error('修改密码失败:', error)
    passwordErrorMessage.value = error.response?.data?.message || '修改失败，请检查网络连接'
  } finally {
    passwordLoading.value = false
  }
}

// 格式化日期
const formatDate = (dateString: string) => {
  if (!dateString) return '暂无记录'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 页面加载时获取用户信息
onMounted(() => {
  loadUserInfo()
})
</script>
