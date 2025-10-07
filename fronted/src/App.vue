<template>
  <div class="font-inter bg-dark text-dark-100 min-h-screen flex flex-col">
    <!-- 登录页面：不显示菜单 -->
    <template v-if="isLoginPage">
      <router-view></router-view>
    </template>
    
    <!-- 其他页面：显示菜单 -->
    <template v-else>
      <Sidebar />
      <div class="flex-1 lg:ml-72 transition-all duration-300">
        <Header />
        <main class="p-4 lg:p-6">
          <router-view v-slot="{ Component }">
            <keep-alive>
              <component :is="Component" />
            </keep-alive>
          </router-view>
        </main>
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import Sidebar from '@/components/Sidebar.vue'
import Header from '@/components/Header.vue'

const route = useRoute()

// 判断是否是登录、注册或忘记密码页面（不显示导航栏）
const isLoginPage = computed(() => route.path === '/login' || route.path === '/register' || route.path === '/forgot-password')
</script> 