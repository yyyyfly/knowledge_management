<template>
  <div class="tree-node">
    <div 
      class="flex items-center py-2 px-3 rounded-lg hover:bg-gray-50 transition-colors group"
      :style="{ paddingLeft: `${level * 24 + 12}px` }"
    >
      <!-- 展开/折叠图标 -->
      <button 
        v-if="node.children && node.children.length > 0"
        @click="expanded = !expanded"
        class="mr-2 text-gray-500 hover:text-gray-700"
      >
        <i :class="expanded ? 'fas fa-chevron-down' : 'fas fa-chevron-right'"></i>
      </button>
      <span v-else class="w-4 mr-2"></span>

      <!-- 节点名称 -->
      <div class="flex-1 flex items-center">
        <input 
          v-if="editing"
          v-model="editName"
          @blur="saveEdit"
          @keyup.enter="saveEdit"
          @keyup.esc="cancelEdit"
          class="flex-1 px-2 py-1 border border-blue-500 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
          ref="editInput"
        >
        <span v-else class="flex-1">{{ node.name }}</span>
      </div>

      <!-- 操作按钮 -->
      <div class="flex items-center space-x-2 opacity-0 group-hover:opacity-100 transition-opacity">
        <button 
          @click="startEdit"
          class="text-blue-600 hover:text-blue-700 p-1"
          title="编辑"
        >
          <i class="fas fa-edit text-sm"></i>
        </button>
        <button 
          @click="addChild"
          class="text-green-600 hover:text-green-700 p-1"
          title="添加子分支"
        >
          <i class="fas fa-plus text-sm"></i>
        </button>
        <button 
          @click="deleteNode"
          class="text-red-600 hover:text-red-700 p-1"
          title="删除"
        >
          <i class="fas fa-trash text-sm"></i>
        </button>
      </div>
    </div>

    <!-- 子节点 -->
    <div v-if="expanded && node.children && node.children.length > 0">
      <TreeNode 
        v-for="child in node.children"
        :key="child.id"
        :node="child"
        :level="level + 1"
        @add-child="$emit('add-child', $event)"
        @edit-node="$emit('edit-node', $event.node, $event.name)"
        @delete-node="$emit('delete-node', $event)"
      />
    </div>
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
</script>

<style scoped>
.tree-node {
  user-select: none;
}
</style>

