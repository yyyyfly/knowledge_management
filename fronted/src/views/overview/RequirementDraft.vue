<template>
  <div class="p-6">
    <!-- 页面标题 -->
    <div class="mb-6">
      <div class="flex items-center space-x-4">
        <div class="w-12 h-12 bg-gradient-to-br from-indigo-500 to-purple-600 rounded-xl flex items-center justify-center shadow-lg">
          <i class="fas fa-file-contract text-white text-xl"></i>
        </div>
        <div>
          <h1 class="text-2xl font-semibold text-gray-900">需求草案管理</h1>
          <p class="text-gray-600">记录和管理功能想法，一次编辑一个版本，积累到一定程度后统一规划制作。</p>
        </div>
      </div>
    </div>

    <!-- 当前编辑版本 -->
    <div class="bg-gradient-to-r from-indigo-600 via-purple-600 to-pink-600 rounded-xl shadow-2xl overflow-hidden mb-6">
      <div class="p-6">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <div class="w-14 h-14 bg-white/20 rounded-lg flex items-center justify-center backdrop-blur-sm">
              <i class="fas fa-file-contract text-white text-3xl"></i>
            </div>
             <div>
               <div class="flex items-center space-x-3">
                 <select
                   v-if="editingVersion"
                   v-model="currentVersion"
                   @change="updateVersionName"
                   class="text-xl font-bold text-white bg-white/20 px-3 py-2 rounded cursor-pointer"
                 >
                   <option value="" disabled class="text-gray-900">选择版本号</option>
                   <option v-for="opt in versionOptions" :key="opt.value" :value="opt.value" class="text-gray-900">
                     {{ opt.label }}
                   </option>
                 </select>
                 <h2 v-else class="text-2xl font-bold text-white">{{ currentVersion || '点击编辑按钮选择版本号' }}</h2>
                 <button @click="toggleVersionEdit" class="text-white/80 hover:text-white">
                   <i class="fas fa-edit"></i>
                 </button>
               </div>
               <p class="text-blue-100 text-sm mt-1">
                 {{ currentVersion ? `${currentDrafts.length} 个功能想法` : '请先选择版本号' }}
               </p>
             </div>
          </div>
          
          <!-- 状态操作按钮 -->
          <div class="flex items-center space-x-3">
            <button
              @click="approveVersion"
              class="px-4 py-2 bg-green-500/80 hover:bg-green-500 text-white rounded-lg transition-colors flex items-center space-x-2"
            >
              <i class="fas fa-check-circle"></i>
              <span>通过版本</span>
            </button>
            <button
              @click="abandonVersion"
              class="px-4 py-2 bg-red-500/80 hover:bg-red-500 text-white rounded-lg transition-colors flex items-center space-x-2"
            >
              <i class="fas fa-times-circle"></i>
              <span>废弃版本</span>
            </button>
          </div>
        </div>
      </div>
      <div class="h-1 bg-gradient-to-r from-yellow-400 via-pink-400 to-purple-400"></div>
    </div>

    <!-- 需求列表 -->
    <div class="bg-white rounded-xl shadow-lg p-6 mb-6">
      <div class="flex items-center justify-between mb-4">
        <h3 class="text-lg font-semibold text-gray-900">需求列表</h3>
        <button
          @click="showAddDialog = true"
          class="px-4 py-2 bg-gradient-to-r from-blue-600 to-purple-600 text-white rounded-lg hover:from-blue-700 hover:to-purple-700 transition-all shadow-md flex items-center space-x-2"
        >
          <i class="fas fa-plus"></i>
          <span>添加需求</span>
        </button>
      </div>

      <!-- 需求卡片列表 -->
      <div class="space-y-3">
        <div
          v-for="(draft, index) in currentDrafts"
          :key="draft.id"
          class="border-l-4 border-blue-500 bg-gray-50 rounded-lg p-4 hover:shadow-md transition-all"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1 cursor-pointer" @click="toggleDraft(draft.id!)">
              <div class="flex items-center space-x-2 mb-2">
                <span class="text-gray-500 font-mono text-sm">#{{ index + 1 }}</span>
                <h4 class="text-base font-semibold text-gray-900">{{ draft.title }}</h4>
                <i
                  :class="expandedDrafts[draft.id!] ? 'fas fa-chevron-up' : 'fas fa-chevron-down'"
                  class="text-gray-400 text-sm"
                ></i>
              </div>
              <p class="text-xs text-gray-500">创建于 {{ formatDate(draft.recCreateTime) }}</p>
            </div>
            <!-- 操作按钮 -->
            <div class="flex items-center space-x-2 ml-4">
              <button
                @click.stop="handleEdit(draft)"
                class="px-3 py-1 text-blue-600 hover:bg-blue-50 rounded transition-colors"
              >
                <i class="fas fa-edit"></i>
              </button>
              <button
                @click.stop="handleDelete(draft.id!)"
                class="px-3 py-1 text-red-600 hover:bg-red-50 rounded transition-colors"
              >
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>

          <!-- 详细描述 -->
          <transition name="slide-fade">
            <div v-if="expandedDrafts[draft.id!] && draft.description" class="mt-3 pt-3 border-t border-gray-200">
              <p class="text-sm text-gray-700 whitespace-pre-wrap">{{ draft.description }}</p>
            </div>
          </transition>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-if="currentDrafts.length === 0" class="text-center py-12">
        <i class="fas fa-inbox text-gray-300 text-5xl mb-4"></i>
        <p class="text-gray-500">还没有添加任何需求，点击上方按钮开始添加吧！</p>
      </div>
    </div>

    <!-- 归档区 -->
    <div class="bg-gray-50 rounded-xl shadow-lg p-6">
      <h3 class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
        <i class="fas fa-archive text-gray-600 mr-2"></i>
        归档版本
      </h3>
      
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <!-- 已通过的版本 -->
        <div class="bg-white rounded-lg p-4 border border-green-200">
          <h4 class="font-semibold text-green-700 mb-3 flex items-center">
            <i class="fas fa-check-circle mr-2"></i>
            已通过（{{ approvedVersions.length }}）
          </h4>
          <div class="space-y-2">
            <div
              v-for="version in approvedVersions"
              :key="version"
              @click="loadArchivedVersion(version)"
              class="px-3 py-2 bg-green-50 hover:bg-green-100 rounded cursor-pointer transition-colors flex items-center justify-between"
            >
              <span class="text-sm font-medium text-gray-900">{{ version }}</span>
              <i class="fas fa-chevron-right text-gray-400 text-xs"></i>
            </div>
          </div>
          <div v-if="approvedVersions.length === 0" class="text-center py-4 text-gray-400 text-sm">
            暂无已通过的版本
          </div>
        </div>

        <!-- 已废弃的版本 -->
        <div class="bg-white rounded-lg p-4 border border-red-200">
          <h4 class="font-semibold text-red-700 mb-3 flex items-center">
            <i class="fas fa-times-circle mr-2"></i>
            已废弃（{{ abandonedVersions.length }}）
          </h4>
          <div class="space-y-2">
            <div
              v-for="version in abandonedVersions"
              :key="version"
              @click="loadArchivedVersion(version)"
              class="px-3 py-2 bg-red-50 hover:bg-red-100 rounded cursor-pointer transition-colors flex items-center justify-between"
            >
              <span class="text-sm font-medium text-gray-900">{{ version }}</span>
              <i class="fas fa-chevron-right text-gray-400 text-xs"></i>
            </div>
          </div>
          <div v-if="abandonedVersions.length === 0" class="text-center py-4 text-gray-400 text-sm">
            暂无已废弃的版本
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑需求对话框 -->
    <div v-if="showAddDialog || showEditDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl shadow-2xl w-full max-w-2xl mx-4 max-h-[90vh] overflow-y-auto">
        <div class="p-6">
          <h3 class="text-xl font-bold text-gray-900 mb-4">
            {{ showEditDialog ? '编辑需求' : '添加需求' }}
          </h3>

          <div class="space-y-4">
            <!-- 标题 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">标题 *</label>
              <input
                v-model="editingDraft.title"
                type="text"
                placeholder="输入功能标题"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>

            <!-- 详细描述 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">详细描述</label>
              <textarea
                v-model="editingDraft.description"
                rows="8"
                placeholder="输入详细的功能说明和实现思路..."
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none"
              ></textarea>
            </div>
          </div>

          <!-- 按钮组 -->
          <div class="flex items-center justify-end space-x-3 mt-6">
            <button
              @click="closeDialog"
              class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
            >
              取消
            </button>
            <button
              @click="handleSave"
              class="px-4 py-2 bg-gradient-to-r from-blue-600 to-purple-600 text-white rounded-lg hover:from-blue-700 hover:to-purple-700 transition-all"
            >
              保存
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 归档版本查看对话框 -->
    <div v-if="showArchivedDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl shadow-2xl w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
        <div class="p-6">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-xl font-bold text-gray-900">{{ archivedVersion }} - 归档查看</h3>
            <button @click="showArchivedDialog = false" class="text-gray-500 hover:text-gray-700">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="space-y-3">
            <div
              v-for="(draft, index) in archivedDrafts"
              :key="draft.id"
              class="border-l-4 bg-gray-50 rounded-lg p-4"
              :class="draft.status === 'approved' ? 'border-green-500' : 'border-red-500'"
            >
              <div class="mb-2">
                <span class="text-gray-500 font-mono text-sm mr-2">#{{ index + 1 }}</span>
                <span class="text-base font-semibold text-gray-900">{{ draft.title }}</span>
              </div>
              <p v-if="draft.description" class="text-sm text-gray-700 mt-2 whitespace-pre-wrap">
                {{ draft.description }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated } from 'vue'
import {
  getAllRequirementDrafts,
  createRequirementDraft,
  updateRequirementDraft,
  deleteRequirementDraft,
  type RequirementDraft
} from '@/api/requirementDraft'

// 数据
const drafts = ref<RequirementDraft[]>([])
const currentVersion = ref('') // 当前编辑的版本号
const editingVersion = ref(false)
const expandedDrafts = ref<Record<number, boolean>>({})

// 版本号选项
const versionOptions = ref<Array<{value: string, label: string}>>([])

// 最新版本号（手动更新）
const latestVersion = 'v2.8.0'

// 对话框
const showAddDialog = ref(false)
const showEditDialog = ref(false)
const showArchivedDialog = ref(false)
const archivedVersion = ref('')
const archivedDrafts = ref<RequirementDraft[]>([])

// 编辑中的需求
const editingDraft = ref<RequirementDraft>({
  version: '',
  title: '',
  description: ''
})

// 当前版本的需求列表（草拟中）
const currentDrafts = computed(() => {
  return drafts.value.filter(d => d.version === currentVersion.value && d.status === 'drafting')
})

// 已通过的版本列表
const approvedVersions = computed(() => {
  const versions = new Set<string>()
  drafts.value.forEach(d => {
    if (d.status === 'approved') {
      versions.add(d.version)
    }
  })
  return Array.from(versions).sort().reverse()
})

// 已废弃的版本列表
const abandonedVersions = computed(() => {
  const versions = new Set<string>()
  drafts.value.forEach(d => {
    if (d.status === 'abandoned') {
      versions.add(d.version)
    }
  })
  return Array.from(versions).sort().reverse()
})

// 生成版本号选项
const generateVersionOptions = () => {
  // 解析版本号 v2.8.0 -> [2, 8, 0]
  const match = latestVersion.match(/^v(\d+)\.(\d+)\.(\d+)$/)
  if (!match) {
    versionOptions.value = []
    return
  }
  
  const [, major, minor, patch] = match.map(Number)
  
  // 生成三个选项
  versionOptions.value = [
    {
      value: `v${major}.${minor}.${patch + 1}`,
      label: `v${major}.${minor}.${patch + 1} - 小版本（Bug修复）`
    },
    {
      value: `v${major}.${minor + 1}.0`,
      label: `v${major}.${minor + 1}.0 - 中版本（新功能）`
    },
    {
      value: `v${major + 1}.0.0`,
      label: `v${major + 1}.0.0 - 大版本（重大更新）`
    }
  ]
  
  // 默认选择中版本
  if (!currentVersion.value) {
    currentVersion.value = versionOptions.value[1].value
  }
}

// 加载数据
const loadData = async () => {
  try {
    const response = await getAllRequirementDrafts()
    if (response.code === 200) {
      drafts.value = response.data || []
    }
  } catch (error) {
    console.error('加载需求草案失败:', error)
    alert('加载数据失败')
  }
}

// 切换版本编辑状态
const toggleVersionEdit = () => {
  editingVersion.value = !editingVersion.value
}

// 更新版本号
const updateVersionName = () => {
  editingVersion.value = false
}

// 切换需求展开/折叠
const toggleDraft = (id: number) => {
  expandedDrafts.value[id] = !expandedDrafts.value[id]
}

// 添加需求
const handleEdit = (draft: RequirementDraft) => {
  editingDraft.value = { ...draft }
  showEditDialog.value = true
}

// 关闭对话框
const closeDialog = () => {
  showAddDialog.value = false
  showEditDialog.value = false
  editingDraft.value = {
    version: '',
    title: '',
    description: ''
  }
}

// 保存
const handleSave = async () => {
  if (!editingDraft.value.title.trim()) {
    alert('请输入标题')
    return
  }
  
  try {
    let response
    if (editingDraft.value.id) {
      response = await updateRequirementDraft(editingDraft.value)
    } else {
      // 新增时设置当前版本号
      editingDraft.value.version = currentVersion.value
      response = await createRequirementDraft(editingDraft.value)
    }
    
    if (response.code === 200) {
      alert(editingDraft.value.id ? '更新成功' : '添加成功')
      closeDialog()
      await loadData()
    } else {
      alert(response.message || '保存失败')
    }
  } catch (error) {
    console.error('保存需求草案失败:', error)
    alert('保存失败')
  }
}

// 删除
const handleDelete = async (id: number) => {
  if (!confirm('确定要删除这条需求吗？')) {
    return
  }
  
  try {
    const response = await deleteRequirementDraft(id)
    if (response.code === 200) {
      alert('删除成功')
      await loadData()
    } else {
      alert(response.message || '删除失败')
    }
  } catch (error) {
    console.error('删除需求草案失败:', error)
    alert('删除失败')
  }
}

// 通过版本
const approveVersion = async () => {
  if (currentDrafts.value.length === 0) {
    alert('当前版本没有任何需求，无法通过')
    return
  }
  
  if (!confirm(`确定通过版本 ${currentVersion.value} 吗？通过后将归档，无法再编辑。`)) {
    return
  }
  
  try {
    // 更新所有当前版本的需求状态为已通过
    for (const draft of currentDrafts.value) {
      draft.status = 'approved'
      await updateRequirementDraft(draft)
    }
    
    alert('版本已通过并归档')
    await loadData()
  } catch (error) {
    console.error('通过版本失败:', error)
    alert('操作失败')
  }
}

// 废弃版本
const abandonVersion = async () => {
  if (currentDrafts.value.length === 0) {
    alert('当前版本没有任何需求')
    return
  }
  
  if (!confirm(`确定废弃版本 ${currentVersion.value} 吗？废弃后将归档。`)) {
    return
  }
  
  try {
    // 更新所有当前版本的需求状态为已废弃
    for (const draft of currentDrafts.value) {
      draft.status = 'abandoned'
      await updateRequirementDraft(draft)
    }
    
    alert('版本已废弃并归档')
    await loadData()
  } catch (error) {
    console.error('废弃版本失败:', error)
    alert('操作失败')
  }
}

// 加载归档版本
const loadArchivedVersion = (version: string) => {
  archivedVersion.value = version
  archivedDrafts.value = drafts.value.filter(d => d.version === version)
  showArchivedDialog.value = true
}

// 格式化日期
const formatDate = (dateStr?: string) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))
  
  if (days === 0) return '今天'
  if (days === 1) return '昨天'
  if (days < 7) return `${days}天前`
  
  return dateStr.split(' ')[0]
}

// 页面挂载和激活时加载数据
onMounted(() => {
  generateVersionOptions() // 生成版本号选项
  loadData()
})

onActivated(() => {
  loadData()
})
</script>

<style scoped>
/* 折叠展开过渡动画 */
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.2s ease-in;
}

.slide-fade-enter-from {
  transform: translateY(-10px);
  opacity: 0;
}

.slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}
</style>
