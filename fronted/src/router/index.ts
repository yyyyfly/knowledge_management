import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '@/views/Dashboard.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import ForgotPassword from '@/views/ForgotPassword.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: { requiresAuth: false }
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      meta: { requiresAuth: false }
    },
    {
      path: '/forgot-password',
      name: 'forgot-password',
      component: ForgotPassword,
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      name: 'dashboard',
      component: Dashboard,
      meta: { requiresAuth: true }
    },
    // 日常行动
    {
      path: '/daily/material-record',
      name: 'material-record',
      component: () => import('@/views/daily/MaterialRecord.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/daily/project-record',
      name: 'project-record',
      component: () => import('@/views/daily/ProjectRecord.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/daily/daily-summary',
      name: 'daily-summary',
      component: () => import('@/views/daily/DailySummary.vue'),
      meta: { requiresAuth: true }
    },
    // 素材组 - 笔记页面
    {
      path: '/framework-notes',
      name: 'framework-notes',
      component: () => import('@/views/materials/FrameworkNotes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/study-notes',
      name: 'study-notes',
      component: () => import('@/views/materials/StudyNotes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/memorization-notes',
      name: 'memorization-notes',
      component: () => import('@/views/materials/MemorizationNotes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/exercise-notes',
      name: 'exercise-notes',
      component: () => import('@/views/materials/ExerciseNotes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/practical-notes',
      name: 'practical-notes',
      component: () => import('@/views/materials/PracticalNotes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/fragment-notes',
      name: 'fragment-notes',
      component: () => import('@/views/materials/FragmentNotes.vue'),
      meta: { requiresAuth: true }
    },
    // 行动组仪表盘
    {
      path: '/actions',
      name: 'actions-dashboard',
      component: () => import('@/views/ActionsDashboard.vue'),
      meta: { requiresAuth: true }
    },
    // 行动组子仪表盘
    {
      path: '/actions/defense-projects',
      name: 'defense-projects-dashboard',
      component: () => import('@/views/actions/DefenseProjectsDashboard.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/actions/construction-projects',
      name: 'construction-projects-dashboard',
      component: () => import('@/views/actions/ConstructionProjectsDashboard.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/actions/diplomatic-projects',
      name: 'diplomatic-projects-dashboard',
      component: () => import('@/views/actions/DiplomaticProjectsDashboard.vue'),
      meta: { requiresAuth: true }
    },
    // 首脑决策
    {
      path: '/system-decisions',
      name: 'system-decisions',
      component: () => import('@/views/headquarters/SystemDecisions.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/system-summary',
      name: 'system-summary',
      component: () => import('@/views/headquarters/SystemSummary.vue'),
      meta: { requiresAuth: true }
    },
    // 系统管理
    {
      path: '/system-overview',
      name: 'system-overview',
      component: () => import('@/views/system/SystemOverview.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/version-history',
      name: 'version-history',
      component: () => import('@/views/system/VersionHistory.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/account-settings',
      name: 'account-settings',
      component: () => import('@/views/AccountSettings.vue'),
      meta: { requiresAuth: true }
    }
  ]
})

// 路由守卫：检查登录状态
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  // 如果访问登录页、注册页或忘记密码页
  if (to.path === '/login' || to.path === '/register' || to.path === '/forgot-password') {
    if (token) {
      // 已登录，跳转到首页
      next('/')
    } else {
      // 未登录，允许访问
      next()
    }
    return
  }
  
  // 访问其他页面，检查是否需要认证
  if (to.meta.requiresAuth !== false) {
    if (!token) {
      // 未登录，跳转到登录页
      next({ path: '/login', query: { redirect: to.fullPath } })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
