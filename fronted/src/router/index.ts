import { createRouter, createWebHistory } from 'vue-router'
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
    // 主仪表盘 > 驾驶舱 - 重定向到总仪表盘
    {
      path: '/',
      redirect: '/overview/data'
    },
    // 主仪表盘 > 驾驶舱 > 总仪表盘
    {
      path: '/overview/data',
      name: 'data-overview',
      component: () => import('@/views/overview/DataOverview.vue'),
      meta: { requiresAuth: true }
    },
    // 主仪表盘 > 驾驶舱 > 时光轨迹
    {
      path: '/overview/timeline',
      name: 'timeline-review',
      component: () => import('@/views/overview/TimelineReview.vue'),
      meta: { requiresAuth: true }
    },
    // 主仪表盘 > 驾驶舱 > 指导大纲
    {
      path: '/overview/guidance-outline',
      name: 'guidance-outline',
      component: () => import('@/views/overview/GuidanceOutline.vue'),
      meta: { requiresAuth: true }
    },
    // 主仪表盘 > 需求草案
    {
      path: '/overview/requirement-draft',
      name: 'requirement-draft',
      component: () => import('@/views/overview/RequirementDraft.vue'),
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
    {
      path: '/ready-to-deploy',
      name: 'ready-to-deploy',
      component: () => import('@/views/daily/ReadyToDeploy.vue'),
      meta: { requiresAuth: true }
    },
    // 项目分析
    {
      path: '/daily/project-analysis',
      name: 'project-analysis',
      component: () => import('@/views/daily/ProjectAnalysis.vue'),
      meta: { requiresAuth: true }
    },
    // 项目心得
    {
      path: '/daily/project-insight',
      name: 'project-insight',
      component: () => import('@/views/daily/ProjectInsight.vue'),
      meta: { requiresAuth: true }
    },
    // 素材组 - 笔记页面
    {
      path: '/skill-notes',
      name: 'skill-notes',
      component: () => import('@/views/materials/SkillNotes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/study-notes',
      name: 'study-notes',
      component: () => import('@/views/materials/StudyNotes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/expansion-notes',
      name: 'expansion-notes',
      component: () => import('@/views/materials/ExpansionNotes.vue'),
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
    {
      path: '/training-center',
      name: 'training-center',
      component: () => import('@/views/materials/TrainingCenter.vue'),
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
      path: '/version-history',
      name: 'version-history',
      component: () => import('@/views/system/VersionHistory.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/user-management',
      name: 'user-management',
      component: () => import('@/views/system/UserManagement.vue'),
      meta: { requiresAuth: true, requiresAdmin: true }
    },
    {
      path: '/data-management',
      name: 'data-management',
      component: () => import('@/views/system/DataManagement.vue'),
      meta: { requiresAuth: true, requiresAdmin: true }
    },
    {
      path: '/account-settings',
      name: 'account-settings',
      component: () => import('@/views/AccountSettings.vue'),
      meta: { requiresAuth: true }
    }
  ]
})

// 路由守卫：检查登录状态和权限
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
      // 检查是否需要管理员权限
      if (to.meta.requiresAdmin) {
        const userStr = localStorage.getItem('user')
        if (userStr) {
          try {
            const user = JSON.parse(userStr)
            if (user.username === 'admin') {
              next()
            } else {
              // 非管理员，跳转到首页并提示
              alert('权限不足，该页面仅管理员可访问')
              next('/')
            }
          } catch (e) {
            // 解析失败，跳转到首页
            next('/')
          }
        } else {
          // 没有用户信息，跳转到首页
          next('/')
        }
      } else {
        next()
      }
    }
  } else {
    next()
  }
})

export default router
