<template>
  <div class="tree-node relative">
    <!-- 层级连接线 -->
    <div 
      v-if="level > 0" 
      class="absolute left-0 top-0 bottom-0 border-l-2"
      :class="getLevelBorderColor(level)"
      :style="{ left: `${(level - 1) * 32 + 24}px` }"
    ></div>
    
    <!-- 水平连接线 -->
    <div 
      v-if="level > 0"
      class="absolute top-6 h-0.5"
      :class="getLevelBgColor(level)"
      :style="{ left: `${(level - 1) * 32 + 24}px`, width: '16px' }"
    ></div>

    <div 
      class="flex items-center py-2 px-3 rounded-lg transition-all duration-200 group relative"
      :class="getNodeClass(level)"
      :style="{ marginLeft: `${level * 32}px` }"
    >
      <!-- 层级装饰图标 -->
      <div 
        class="flex items-center justify-center mr-2 rounded-md transition-all duration-200"
        :class="getLevelIconBg(level)"
        :style="{ width: '28px', height: '28px' }"
      >
        <!-- 展开/折叠图标 -->
        <button 
          v-if="node.children && node.children.length > 0"
          @click="expanded = !expanded"
          class="w-full h-full flex items-center justify-center transition-transform duration-200"
          :class="expanded ? 'rotate-0' : '-rotate-90'"
        >
          <i class="fas fa-chevron-down text-xs" :class="getLevelIconColor(level)"></i>
        </button>
        <!-- 叶子节点图标 -->
        <i v-else class="text-xs" :class="[getLevelIcon(level), getLevelIconColor(level)]"></i>
      </div>

      <!-- 节点名称区域 -->
      <div class="flex-1 flex items-center min-w-0">
        <input 
          v-if="editing"
          v-model="editName"
          @blur="saveEdit"
          @keyup.enter="saveEdit"
          @keyup.esc="cancelEdit"
          class="flex-1 px-3 py-1.5 border-2 border-blue-500 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-white shadow-sm"
          ref="editInput"
        >
        <div v-else class="flex-1 flex items-center space-x-2 min-w-0">
          <!-- 节点指示器 -->
          <div 
            class="w-1.5 h-1.5 rounded-full flex-shrink-0"
            :class="getLevelDotColor(level)"
          ></div>
          <!-- 节点文本 -->
          <span 
            class="font-medium truncate"
            :class="getLevelTextColor(level)"
            :style="{ fontSize: getLevelFontSize(level) }"
          >
            {{ node.name }}
          </span>
          <!-- 子节点数量徽章 -->
          <span 
            v-if="node.children && node.children.length > 0"
            class="px-2 py-0.5 rounded-full text-xs font-semibold flex-shrink-0"
            :class="getLevelBadgeClass(level)"
          >
            {{ node.children.length }}
          </span>
        </div>
      </div>

      <!-- 操作按钮组 -->
      <div class="flex items-center space-x-1 ml-2 opacity-0 group-hover:opacity-100 transition-all duration-200">
        <button 
          @click="startEdit"
          class="p-1.5 rounded-lg transition-all hover:scale-110"
          :class="getButtonClass('edit')"
          title="编辑节点"
        >
          <i class="fas fa-edit text-xs"></i>
        </button>
        <button 
          @click="addChild"
          class="p-1.5 rounded-lg transition-all hover:scale-110"
          :class="getButtonClass('add')"
          title="添加子节点"
        >
          <i class="fas fa-plus text-xs"></i>
        </button>
        <button 
          @click="deleteNode"
          class="p-1.5 rounded-lg transition-all hover:scale-110"
          :class="getButtonClass('delete')"
          title="删除节点"
        >
          <i class="fas fa-trash text-xs"></i>
        </button>
      </div>

      <!-- 悬停效果装饰 -->
      <div 
        class="absolute inset-0 rounded-lg opacity-0 group-hover:opacity-100 transition-opacity duration-200 pointer-events-none"
        :class="getLevelHoverGradient(level)"
      ></div>
    </div>

    <!-- 子节点容器 -->
    <transition name="expand">
      <div v-if="expanded && node.children && node.children.length > 0" class="relative">
        <TreeNode 
          v-for="(child, index) in node.children"
          :key="child.id"
          :node="child"
          :level="level + 1"
          @add-child="$emit('add-child', $event)"
          @edit-node="$emit('edit-node', $event.node, $event.name)"
          @delete-node="$emit('delete-node', $event)"
        />
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref, nextTick } from 'vue'

interface TreeNodeType {
  id: string
  name: string
  children: TreeNodeType[]
}

interface Props {
  node: TreeNodeType
  level: number
}

const props = defineProps<Props>()

const emit = defineEmits<{
  'add-child': [node: TreeNodeType]
  'edit-node': [payload: { node: TreeNodeType; name: string }]
  'delete-node': [nodeId: string]
}>()

const expanded = ref(true)
const editing = ref(false)
const editName = ref('')
const editInput = ref<HTMLInputElement | null>(null)

const startEdit = () => {
  editing.value = true
  editName.value = props.node.name
  nextTick(() => {
    editInput.value?.focus()
    editInput.value?.select()
  })
}

const saveEdit = () => {
  if (editName.value.trim()) {
    emit('edit-node', { node: props.node, name: editName.value.trim() })
  }
  editing.value = false
}

const cancelEdit = () => {
  editing.value = false
  editName.value = props.node.name
}

const addChild = () => {
  emit('add-child', props.node)
  expanded.value = true
}

const deleteNode = () => {
  emit('delete-node', props.node.id)
}

// 根据层级获取不同的样式
const getNodeClass = (level: number) => {
  const baseClass = 'hover:shadow-md'
  const levelClasses = [
    'bg-gradient-to-r from-blue-50 to-indigo-50 border border-blue-200',
    'bg-gradient-to-r from-emerald-50 to-teal-50 border border-emerald-200',
    'bg-gradient-to-r from-purple-50 to-pink-50 border border-purple-200',
    'bg-gradient-to-r from-orange-50 to-amber-50 border border-orange-200',
    'bg-gradient-to-r from-cyan-50 to-sky-50 border border-cyan-200',
    'bg-gradient-to-r from-rose-50 to-red-50 border border-rose-200',
    'bg-gradient-to-r from-lime-50 to-green-50 border border-lime-200',
    'bg-gradient-to-r from-fuchsia-50 to-purple-50 border border-fuchsia-200',
    'bg-gradient-to-r from-amber-50 to-yellow-50 border border-amber-200',
    'bg-gradient-to-r from-teal-50 to-emerald-50 border border-teal-200',
    'bg-gradient-to-r from-violet-50 to-indigo-50 border border-violet-200',
    'bg-gradient-to-r from-pink-50 to-rose-50 border border-pink-200',
  ]
  return `${baseClass} ${levelClasses[level % levelClasses.length]}`
}

const getLevelIconBg = (level: number) => {
  const colors = [
    'bg-blue-500 shadow-lg shadow-blue-200',
    'bg-emerald-500 shadow-lg shadow-emerald-200',
    'bg-purple-500 shadow-lg shadow-purple-200',
    'bg-orange-500 shadow-lg shadow-orange-200',
    'bg-cyan-500 shadow-lg shadow-cyan-200',
    'bg-rose-500 shadow-lg shadow-rose-200',
    'bg-lime-500 shadow-lg shadow-lime-200',
    'bg-fuchsia-500 shadow-lg shadow-fuchsia-200',
    'bg-amber-500 shadow-lg shadow-amber-200',
    'bg-teal-500 shadow-lg shadow-teal-200',
    'bg-violet-500 shadow-lg shadow-violet-200',
    'bg-pink-500 shadow-lg shadow-pink-200',
  ]
  return colors[level % colors.length]
}

const getLevelIconColor = (level: number) => {
  return 'text-white'
}

const getLevelIcon = (level: number) => {
  const icons = [
    'fas fa-folder',
    'fas fa-book',
    'fas fa-file-alt',
    'fas fa-tag',
    'fas fa-bookmark',
    'fas fa-paperclip',
    'fas fa-cube',
    'fas fa-star',
    'fas fa-lightbulb',
    'fas fa-flask',
    'fas fa-gem',
    'fas fa-certificate',
  ]
  return icons[level % icons.length]
}

const getLevelTextColor = (level: number) => {
  const colors = [
    'text-blue-900',
    'text-emerald-900',
    'text-purple-900',
    'text-orange-900',
    'text-cyan-900',
    'text-rose-900',
    'text-lime-900',
    'text-fuchsia-900',
    'text-amber-900',
    'text-teal-900',
    'text-violet-900',
    'text-pink-900',
  ]
  return colors[level % colors.length]
}

const getLevelFontSize = (level: number) => {
  const sizes = ['16px', '15px', '14px', '13px', '13px', '13px', '13px', '13px', '13px', '13px', '13px', '13px']
  return sizes[Math.min(level, sizes.length - 1)]
}

const getLevelDotColor = (level: number) => {
  const colors = [
    'bg-blue-600',
    'bg-emerald-600',
    'bg-purple-600',
    'bg-orange-600',
    'bg-cyan-600',
    'bg-rose-600',
    'bg-lime-600',
    'bg-fuchsia-600',
    'bg-amber-600',
    'bg-teal-600',
    'bg-violet-600',
    'bg-pink-600',
  ]
  return colors[level % colors.length]
}

const getLevelBadgeClass = (level: number) => {
  const classes = [
    'bg-blue-600 text-white',
    'bg-emerald-600 text-white',
    'bg-purple-600 text-white',
    'bg-orange-600 text-white',
    'bg-cyan-600 text-white',
    'bg-rose-600 text-white',
    'bg-lime-600 text-white',
    'bg-fuchsia-600 text-white',
    'bg-amber-600 text-white',
    'bg-teal-600 text-white',
    'bg-violet-600 text-white',
    'bg-pink-600 text-white',
  ]
  return classes[level % classes.length]
}

const getLevelBorderColor = (level: number) => {
  const colors = [
    'border-blue-300',
    'border-emerald-300',
    'border-purple-300',
    'border-orange-300',
    'border-cyan-300',
    'border-rose-300',
    'border-lime-300',
    'border-fuchsia-300',
    'border-amber-300',
    'border-teal-300',
    'border-violet-300',
    'border-pink-300',
  ]
  return colors[level % colors.length]
}

const getLevelBgColor = (level: number) => {
  const colors = [
    'bg-blue-300',
    'bg-emerald-300',
    'bg-purple-300',
    'bg-orange-300',
    'bg-cyan-300',
    'bg-rose-300',
    'bg-lime-300',
    'bg-fuchsia-300',
    'bg-amber-300',
    'bg-teal-300',
    'bg-violet-300',
    'bg-pink-300',
  ]
  return colors[level % colors.length]
}

const getLevelHoverGradient = (level: number) => {
  const gradients = [
    'bg-gradient-to-r from-blue-100/50 to-indigo-100/50',
    'bg-gradient-to-r from-emerald-100/50 to-teal-100/50',
    'bg-gradient-to-r from-purple-100/50 to-pink-100/50',
    'bg-gradient-to-r from-orange-100/50 to-amber-100/50',
    'bg-gradient-to-r from-cyan-100/50 to-sky-100/50',
    'bg-gradient-to-r from-rose-100/50 to-red-100/50',
    'bg-gradient-to-r from-lime-100/50 to-green-100/50',
    'bg-gradient-to-r from-fuchsia-100/50 to-purple-100/50',
    'bg-gradient-to-r from-amber-100/50 to-yellow-100/50',
    'bg-gradient-to-r from-teal-100/50 to-emerald-100/50',
    'bg-gradient-to-r from-violet-100/50 to-indigo-100/50',
    'bg-gradient-to-r from-pink-100/50 to-rose-100/50',
  ]
  return gradients[level % gradients.length]
}

const getButtonClass = (type: 'edit' | 'add' | 'delete') => {
  const classes = {
    edit: 'bg-blue-100 hover:bg-blue-200 text-blue-700',
    add: 'bg-green-100 hover:bg-green-200 text-green-700',
    delete: 'bg-red-100 hover:bg-red-200 text-red-700',
  }
  return classes[type]
}
</script>

<style scoped>
.tree-node {
  user-select: none;
}

/* 展开/收起动画 */
.expand-enter-active,
.expand-leave-active {
  transition: all 0.3s ease;
  overflow: hidden;
}

.expand-enter-from,
.expand-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

.expand-enter-to,
.expand-leave-from {
  opacity: 1;
  transform: translateY(0);
}
</style>

