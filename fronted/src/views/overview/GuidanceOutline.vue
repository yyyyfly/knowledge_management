<template>
  <div class="p-6 bg-gray-50 h-screen overflow-hidden flex flex-col">
    <!-- 页面头部 -->
    <div class="mb-6 flex-shrink-0">
      <div class="flex items-center justify-between">
        <div class="flex items-center space-x-4">
          <div class="w-12 h-12 bg-gradient-to-br from-blue-500 to-indigo-600 rounded-xl flex items-center justify-center shadow-lg">
            <i class="fas fa-sitemap text-white text-2xl"></i>
          </div>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">指导大纲</h1>
            <p class="text-gray-600">思维导图和知识体系整理</p>
          </div>
        </div>
        <button 
          @click="openCategoryManage"
          class="px-4 py-2 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg transition-colors"
        >
          <i class="fas fa-cog mr-2"></i>
          管理学科分类
        </button>
      </div>
    </div>

    <!-- 筛选和搜索区域 -->
    <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-4 mb-4 flex-shrink-0">
      <div class="flex items-center space-x-4">
        <div class="flex-1">
          <div class="relative">
            <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            <input 
              v-model="searchKeyword"
              @input="handleSearch"
              type="text" 
              placeholder="搜索学科名称或标签..."
              class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
            >
          </div>
        </div>
        <select 
          v-model="selectedCategory"
          @change="handleCategoryChange"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
        >
          <option value="">全部分类</option>
          <option v-for="cat in categories" :key="cat.id" :value="cat.configName">
            {{ cat.configName }}
          </option>
        </select>
        <button 
          @click="showAddDialog = true"
          class="px-4 py-2 bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white rounded-lg font-medium transition-all duration-300 hover:shadow-lg whitespace-nowrap"
        >
          <i class="fas fa-plus mr-2"></i>
          新增学科
        </button>
      </div>
    </div>

    <!-- 主体内容区域 -->
    <div class="grid grid-cols-12 gap-6 flex-1 overflow-hidden">
      <!-- 左侧学科列表 -->
      <div class="col-span-4 overflow-y-auto pr-2" style="max-height: calc(100vh - 240px);">
        <div class="space-y-3">
          <div 
            v-for="outline in outlineList" 
            :key="outline.id"
            @click="selectOutline(outline)"
            class="bg-white rounded-xl p-4 border-2 cursor-pointer transition-all hover:shadow-lg"
            :class="selectedOutline?.id === outline.id ? 'border-blue-500 shadow-lg' : 'border-gray-200 hover:border-blue-300'"
          >
            <div class="flex items-start justify-between mb-2">
              <h3 class="text-base font-bold text-gray-900">{{ outline.subjectName }}</h3>
              <div class="flex space-x-2">
                <button 
                  @click.stop="editOutline(outline)"
                  class="text-blue-600 hover:text-blue-700"
                >
                  <i class="fas fa-edit"></i>
                </button>
                <button 
                  @click.stop="confirmDelete(outline.id)"
                  class="text-red-600 hover:text-red-700"
                >
                  <i class="fas fa-trash"></i>
                </button>
              </div>
            </div>
            <div v-if="outline.subjectCategory" class="mb-2">
              <span class="bg-blue-100 text-blue-700 px-2 py-1 rounded-full text-xs">
                {{ outline.subjectCategory }}
              </span>
            </div>
            <div v-if="outline.tags" class="flex flex-wrap gap-1">
              <span 
                v-for="tag in outline.tags.split(',')" 
                :key="tag"
                class="bg-gray-100 text-gray-600 px-2 py-0.5 rounded text-xs"
              >
                {{ tag }}
              </span>
            </div>
          </div>

          <!-- 空状态 -->
          <div v-if="outlineList.length === 0" class="bg-white rounded-xl p-12 text-center">
            <i class="fas fa-folder-open text-5xl text-gray-300 mb-4"></i>
            <p class="text-gray-600">暂无学科数据</p>
          </div>
        </div>
      </div>

      <!-- 右侧知识树编辑器 -->
      <div class="col-span-8 overflow-y-auto" style="max-height: calc(100vh - 240px);">
        <div v-if="selectedOutline" class="bg-white rounded-2xl shadow-lg border border-gray-100 p-4 h-full">
          <div class="flex items-center justify-between mb-4">
            <h2 class="text-xl font-bold text-gray-900">{{ selectedOutline.subjectName }}</h2>
            <button 
              @click="saveKnowledgeTree"
              class="px-4 py-2 bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white rounded-lg font-medium transition-all"
            >
              <i class="fas fa-save mr-2"></i>
              保存
            </button>
          </div>

          <!-- 树形编辑器 -->
          <div class="border border-gray-200 rounded-lg p-4 overflow-y-auto" style="max-height: calc(100vh - 360px);">
            <button 
              @click="addRootNode"
              class="mb-3 px-3 py-2 bg-blue-50 hover:bg-blue-100 text-blue-600 rounded-lg transition-colors text-sm"
            >
              <i class="fas fa-plus mr-2"></i>
              添加一级分支
            </button>

            <!-- 树形节点递归显示 -->
            <div class="space-y-2">
              <TreeNode 
                v-for="(node, index) in knowledgeTree.children" 
                :key="node.id"
                :node="node"
                :level="0"
                @add-child="addChildNode"
                @edit-node="editNode"
                @delete-node="deleteNode"
              />
            </div>
          </div>
        </div>

        <!-- 未选择状态 -->
        <div v-else class="bg-white rounded-2xl shadow-lg border border-gray-100 p-12 text-center h-full flex flex-col items-center justify-center">
          <i class="fas fa-hand-pointer text-5xl text-gray-300 mb-4"></i>
          <p class="text-gray-600">请从左侧选择一个学科来编辑知识体系</p>
        </div>
      </div>
    </div>

    <!-- 新增/编辑学科弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showAddDialog || showEditDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-md">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-xl font-semibold text-gray-900">
              {{ showAddDialog ? '新增学科' : '编辑学科' }}
            </h3>
            <button @click="closeDialog" class="text-gray-500 hover:text-gray-700">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">学科名称</label>
              <input 
                v-model="formData.subjectName"
                type="text" 
                placeholder="请输入学科名称"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              >
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">学科分类</label>
              <select 
                v-model="formData.subjectCategory"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              >
                <option value="">请选择分类</option>
                <option v-for="cat in categories" :key="cat.id" :value="cat.configName">
                  {{ cat.configName }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">标签（逗号分隔）</label>
              <input 
                v-model="formData.tags"
                type="text" 
                placeholder="例如：编程,算法,数据结构"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              >
            </div>
          </div>

          <div class="flex gap-4 mt-6">
            <button 
              @click="handleSubmit"
              class="flex-1 bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white py-3 rounded-lg font-medium transition-all"
            >
              {{ showAddDialog ? '创建' : '保存' }}
            </button>
            <button 
              @click="closeDialog"
              class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 py-3 rounded-lg font-medium transition-colors"
            >
              取消
            </button>
          </div>
        </div>
      </div>
    </Transition>

    <!-- 学科分类管理弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showCategoryManage" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-xl shadow-2xl w-full max-w-3xl flex flex-col" style="max-height: 80vh;">
          <!-- 标题 -->
          <div class="flex items-center justify-between p-6 border-b border-gray-200 flex-shrink-0">
            <div>
              <h3 class="text-xl font-semibold text-gray-900">管理学科分类</h3>
              <p class="text-sm text-gray-500 mt-1">配置类型：guidance > subjectCategory</p>
            </div>
            <button @click="showCategoryManage = false" class="text-gray-500 hover:text-gray-700">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <!-- 内容区域 -->
          <div class="flex-1 overflow-y-auto p-6">
            <!-- 添加新分类 -->
            <div class="mb-6 flex gap-2">
              <input 
                v-model="newCategoryName"
                type="text" 
                placeholder="输入新分类名称..."
                class="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                @keyup.enter="addCategory"
              >
              <button 
                @click="addCategory"
                class="px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg transition-colors whitespace-nowrap"
              >
                <i class="fas fa-plus mr-2"></i>
                添加分类
              </button>
            </div>

            <!-- 分类列表 - 折叠式 -->
            <div class="space-y-2">
              <div 
                v-for="(category, index) in categoryList" 
                :key="category.id"
                class="border border-gray-200 rounded-lg overflow-hidden"
              >
                <!-- 分类头部 -->
                <div 
                  @click="toggleCategory(category.id)"
                  class="flex items-center justify-between p-4 bg-gray-50 hover:bg-gray-100 cursor-pointer transition-colors"
                >
                  <div class="flex items-center space-x-3">
                    <i 
                      class="fas transition-transform duration-200"
                      :class="expandedCategories.includes(category.id) ? 'fa-chevron-down' : 'fa-chevron-right'"
                    ></i>
                    <span class="font-medium text-gray-900">{{ category.configName }}</span>
                    <span class="text-xs text-gray-500">ID: {{ category.id }}</span>
                  </div>
                  <div class="flex items-center space-x-2" @click.stop>
                    <button 
                      @click="editCategory(category)"
                      class="px-3 py-1 text-blue-600 hover:bg-blue-50 rounded transition-colors"
                    >
                      <i class="fas fa-edit mr-1"></i>
                      编辑
                    </button>
                    <button 
                      @click="deleteCategory(category.id)"
                      class="px-3 py-1 text-red-600 hover:bg-red-50 rounded transition-colors"
                    >
                      <i class="fas fa-trash mr-1"></i>
                      删除
                    </button>
                  </div>
                </div>

                <!-- 分类详细信息（展开时显示） -->
                <transition name="expand">
                  <div v-if="expandedCategories.includes(category.id)" class="p-4 bg-white border-t border-gray-200">
                    <div class="grid grid-cols-2 gap-3 text-sm">
                      <div>
                        <span class="text-gray-600">笔记类型：</span>
                        <span class="font-medium">{{ category.noteType }}</span>
                      </div>
                      <div>
                        <span class="text-gray-600">配置类型：</span>
                        <span class="font-medium">{{ category.configType }}</span>
                      </div>
                      <div>
                        <span class="text-gray-600">创建人：</span>
                        <span class="font-medium">{{ category.recCreator || '-' }}</span>
                      </div>
                      <div>
                        <span class="text-gray-600">创建时间：</span>
                        <span class="font-medium">{{ formatDate(category.recCreateTime) }}</span>
                      </div>
                    </div>
                  </div>
                </transition>
              </div>
            </div>

            <!-- 空状态 -->
            <div v-if="categoryList.length === 0" class="text-center py-12">
              <i class="fas fa-folder-open text-5xl text-gray-300 mb-4"></i>
              <p class="text-gray-600">暂无学科分类</p>
            </div>
          </div>

          <!-- 底部按钮 -->
          <div class="p-6 border-t border-gray-200 flex justify-end flex-shrink-0">
            <button 
              @click="showCategoryManage = false"
              class="px-6 py-2 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg transition-colors"
            >
              关闭
            </button>
          </div>
        </div>
      </div>
    </Transition>

    <!-- 编辑分类弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showEditCategory" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[60] p-4">
        <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-md">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-xl font-semibold text-gray-900">编辑分类</h3>
            <button @click="showEditCategory = false" class="text-gray-500 hover:text-gray-700">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">分类名称</label>
            <input 
              v-model="editingCategory.configName"
              type="text" 
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
          </div>

          <div class="flex gap-4">
            <button 
              @click="updateCategory"
              class="flex-1 bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg transition-colors"
            >
              保存
            </button>
            <button 
              @click="showEditCategory = false"
              class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 py-2 rounded-lg transition-colors"
            >
              取消
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { 
  getAllGuidanceOutlines, 
  getGuidanceOutlinesByCategory,
  searchGuidanceOutlines,
  createGuidanceOutline, 
  updateGuidanceOutline, 
  deleteGuidanceOutline,
  type GuidanceOutline 
} from '@/api/guidanceOutline'
import { getNoteConfigByType, getConfigsByNoteType, createConfig, updateConfig, deleteConfig } from '@/api/noteConfig'
import TreeNode from '@/components/TreeNode.vue'

// 数据定义
const outlineList = ref<GuidanceOutline[]>([])
const selectedOutline = ref<GuidanceOutline | null>(null)
const categories = ref<any[]>([])
const selectedCategory = ref('')
const searchKeyword = ref('')
const showAddDialog = ref(false)
const showEditDialog = ref(false)
const showCategoryManage = ref(false)
const showEditCategory = ref(false)

// 表单数据
const formData = ref({
  id: undefined as number | undefined,
  subjectName: '',
  subjectCategory: '',
  tags: '',
  knowledgeTree: ''
})

// 学科分类管理相关
const categoryList = ref<any[]>([])
const expandedCategories = ref<number[]>([])
const newCategoryName = ref('')
const editingCategory = ref<any>({
  id: undefined,
  configName: ''
})

// 知识树结构
interface TreeNodeType {
  id: string
  name: string
  children: TreeNodeType[]
}

const knowledgeTree = ref<{ children: TreeNodeType[] }>({
  children: []
})

// 加载数据
const loadOutlines = async () => {
  try {
    const response = await getAllGuidanceOutlines()
    if (response.code === 200) {
      outlineList.value = response.data || []
    }
  } catch (error) {
    console.error('加载指导大纲失败:', error)
  }
}

// 加载学科分类
const loadCategories = async () => {
  try {
    const response = await getNoteConfigByType('guidance', 'subjectCategory')
    if (response.code === 200) {
      categories.value = response.data || []
    }
  } catch (error) {
    console.error('加载学科分类失败:', error)
  }
}

// 选择大纲
const selectOutline = (outline: GuidanceOutline) => {
  selectedOutline.value = outline
  // 解析知识树
  try {
    if (outline.knowledgeTree) {
      knowledgeTree.value = JSON.parse(outline.knowledgeTree)
    } else {
      knowledgeTree.value = { children: [] }
    }
  } catch (error) {
    console.error('解析知识树失败:', error)
    knowledgeTree.value = { children: [] }
  }
}

// 分类筛选
const handleCategoryChange = async () => {
  if (selectedCategory.value) {
    const response = await getGuidanceOutlinesByCategory(selectedCategory.value)
    if (response.code === 200) {
      outlineList.value = response.data || []
    }
  } else {
    loadOutlines()
  }
}

// 搜索
const handleSearch = async () => {
  if (searchKeyword.value) {
    const response = await searchGuidanceOutlines(searchKeyword.value)
    if (response.code === 200) {
      outlineList.value = response.data || []
    }
  } else {
    loadOutlines()
  }
}

// 编辑大纲
const editOutline = (outline: GuidanceOutline) => {
  formData.value = {
    id: outline.id,
    subjectName: outline.subjectName,
    subjectCategory: outline.subjectCategory || '',
    tags: outline.tags || '',
    knowledgeTree: outline.knowledgeTree || ''
  }
  showEditDialog.value = true
}

// 提交表单
const handleSubmit = async () => {
  try {
    if (showAddDialog.value) {
      // 创建
      const response = await createGuidanceOutline({
        subjectName: formData.value.subjectName,
        subjectCategory: formData.value.subjectCategory,
        tags: formData.value.tags,
        knowledgeTree: JSON.stringify({ children: [] })
      })
      if (response.code === 200) {
        alert('创建成功')
        await loadOutlines()
        closeDialog()
      }
    } else if (showEditDialog.value) {
      // 更新
      const response = await updateGuidanceOutline({
        id: formData.value.id,
        subjectName: formData.value.subjectName,
        subjectCategory: formData.value.subjectCategory,
        tags: formData.value.tags,
        knowledgeTree: formData.value.knowledgeTree
      })
      if (response.code === 200) {
        alert('更新成功')
        await loadOutlines()
        closeDialog()
      }
    }
  } catch (error) {
    console.error('提交失败:', error)
    alert('操作失败，请重试')
  }
}

// 关闭弹窗
const closeDialog = () => {
  showAddDialog.value = false
  showEditDialog.value = false
  formData.value = {
    id: undefined,
    subjectName: '',
    subjectCategory: '',
    tags: '',
    knowledgeTree: ''
  }
}

// 删除确认
const confirmDelete = async (id: number | undefined) => {
  if (!id) return
  if (confirm('确定要删除这个学科吗？')) {
    try {
      const response = await deleteGuidanceOutline(id)
      if (response.code === 200) {
        alert('删除成功')
        if (selectedOutline.value?.id === id) {
          selectedOutline.value = null
        }
        await loadOutlines()
      }
    } catch (error) {
      console.error('删除失败:', error)
      alert('删除失败，请重试')
    }
  }
}

// 保存知识树
const saveKnowledgeTree = async () => {
  if (!selectedOutline.value) return
  try {
    const response = await updateGuidanceOutline({
      id: selectedOutline.value.id,
      subjectName: selectedOutline.value.subjectName,
      subjectCategory: selectedOutline.value.subjectCategory,
      tags: selectedOutline.value.tags,
      knowledgeTree: JSON.stringify(knowledgeTree.value)
    })
    if (response.code === 200) {
      alert('保存成功')
      await loadOutlines()
    }
  } catch (error) {
    console.error('保存失败:', error)
    alert('保存失败，请重试')
  }
}

// 树形节点操作
const addRootNode = () => {
  const newNode: TreeNodeType = {
    id: 'node-' + Date.now(),
    name: '新分支',
    children: []
  }
  knowledgeTree.value.children.push(newNode)
}

const addChildNode = (parentNode: TreeNodeType) => {
  const newNode: TreeNodeType = {
    id: 'node-' + Date.now(),
    name: '新子分支',
    children: []
  }
  parentNode.children.push(newNode)
}

const editNode = (node: TreeNodeType, newName: string) => {
  node.name = newName
}

const deleteNode = (nodeId: string) => {
  const deleteRecursive = (nodes: TreeNodeType[]): boolean => {
    for (let i = 0; i < nodes.length; i++) {
      if (nodes[i].id === nodeId) {
        nodes.splice(i, 1)
        return true
      }
      if (nodes[i].children && deleteRecursive(nodes[i].children)) {
        return true
      }
    }
    return false
  }
  
  if (confirm('确定要删除这个节点及其所有子节点吗？')) {
    deleteRecursive(knowledgeTree.value.children)
  }
}

// 学科分类管理相关函数
// 打开分类管理弹窗
const openCategoryManage = async () => {
  showCategoryManage.value = true
  await loadCategoryList()
}

// 加载分类列表
const loadCategoryList = async () => {
  try {
    const response = await getConfigsByNoteType('guidance')
    if (response.code === 200) {
      categoryList.value = (response.data || []).filter((item: any) => item.configType === 'subjectCategory')
    }
  } catch (error) {
    console.error('加载分类列表失败:', error)
  }
}

// 切换折叠状态
const toggleCategory = (categoryId: number) => {
  const index = expandedCategories.value.indexOf(categoryId)
  if (index > -1) {
    expandedCategories.value.splice(index, 1)
  } else {
    expandedCategories.value.push(categoryId)
  }
}

// 添加分类
const addCategory = async () => {
  if (!newCategoryName.value.trim()) {
    alert('请输入分类名称')
    return
  }

  try {
    const response = await createConfig({
      noteType: 'guidance',
      configType: 'subjectCategory',
      configName: newCategoryName.value.trim()
    })
    
    if (response.code === 200) {
      alert('添加成功')
      newCategoryName.value = ''
      await loadCategoryList()
      await loadCategories() // 刷新主页面的分类下拉列表
    } else {
      alert(response.msg || '添加失败')
    }
  } catch (error) {
    console.error('添加分类失败:', error)
    alert('添加失败')
  }
}

// 编辑分类
const editCategory = (category: any) => {
  editingCategory.value = {
    id: category.id,
    configName: category.configName
  }
  showEditCategory.value = true
}

// 更新分类
const updateCategory = async () => {
  if (!editingCategory.value.configName.trim()) {
    alert('请输入分类名称')
    return
  }

  try {
    const response = await updateConfig({
      id: editingCategory.value.id,
      configName: editingCategory.value.configName.trim()
    })
    
    if (response.code === 200) {
      alert('更新成功')
      showEditCategory.value = false
      await loadCategoryList()
      await loadCategories()
    } else {
      alert(response.msg || '更新失败')
    }
  } catch (error) {
    console.error('更新分类失败:', error)
    alert('更新失败')
  }
}

// 删除分类
const deleteCategory = async (categoryId: number) => {
  if (!confirm('确定要删除这个分类吗？')) {
    return
  }

  try {
    const response = await deleteConfig(categoryId)
    
    if (response.code === 200) {
      alert('删除成功')
      await loadCategoryList()
      await loadCategories()
    } else {
      alert(response.msg || '删除失败')
    }
  } catch (error) {
    console.error('删除分类失败:', error)
    alert('删除失败')
  }
}

// 格式化日期
const formatDate = (dateStr: string) => {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 页面加载
onMounted(() => {
  loadOutlines()
  loadCategories()
})
</script>

<style scoped>
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.expand-enter-active,
.expand-leave-active {
  transition: all 0.3s ease;
  max-height: 200px;
  overflow: hidden;
}

.expand-enter-from,
.expand-leave-to {
  max-height: 0;
  opacity: 0;
}
</style>

