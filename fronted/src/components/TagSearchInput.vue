<template>
  <div class="relative">
    <div class="flex items-center space-x-2">
      <div class="relative flex-1">
        <input 
          v-model="searchText"
          type="text" 
          :placeholder="placeholder"
          @focus="showDropdown = true"
          @input="handleInput"
          @keyup.enter="handleAdd"
          :class="['w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:border-transparent form-input pr-8', focusRingClass]"
        >
        <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
      </div>
      <button 
        type="button"
        @click="handleAdd"
        :disabled="!searchText.trim()"
        :class="['px-4 py-2 text-white rounded-lg disabled:bg-gray-300 disabled:cursor-not-allowed flex items-center space-x-1 btn-hover whitespace-nowrap', buttonClass]"
      >
        <i class="fas fa-plus"></i>
        <span>添加</span>
      </button>
    </div>
    
    <!-- 下拉搜索结果 -->
    <div 
      v-if="showDropdown && filteredOptions.length > 0" 
      class="absolute z-10 w-full mt-1 bg-white border border-gray-300 rounded-lg shadow-lg max-h-48 overflow-y-auto"
    >
      <button
        v-for="option in filteredOptions"
        :key="option"
        type="button"
        @click="selectOption(option)"
        :class="['w-full text-left px-4 py-2 transition-colors flex items-center justify-between group', hoverClass]"
      >
        <span>{{ option }}</span>
        <i v-if="!(props.selected || []).includes(option)" :class="['fas fa-plus opacity-0 group-hover:opacity-100', iconClass]"></i>
        <i v-else class="fas fa-check text-green-600"></i>
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

interface Props {
  selected?: string[]  // 改为可选，支持 v-model:selected
  allOptions?: string[]
  placeholder?: string
  color?: string
}

const props = withDefaults(defineProps<Props>(), {
  selected: () => [],  // 添加默认空数组
  allOptions: () => [],  // 添加默认空数组
  placeholder: '搜索或新建标签...',
  color: 'blue'
})

const emit = defineEmits<{
  'add': [tag: string]
  'update:selected': [tags: string[]]  // 支持 v-model
}>()

const searchText = ref('')
const showDropdown = ref(false)

// 过滤选项
const filteredOptions = computed(() => {
  const options = props.allOptions || []
  if (!searchText.value.trim()) {
    return options
  }
  return options.filter(option => 
    option.toLowerCase().includes(searchText.value.toLowerCase())
  )
})

// 根据颜色生成样式类
const colorMap: Record<string, { button: string; hover: string; icon: string; ring: string }> = {
  blue: {
    button: 'bg-blue-600 hover:bg-blue-700',
    hover: 'hover:bg-blue-50',
    icon: 'text-blue-600',
    ring: 'focus:ring-blue-500'
  },
  green: {
    button: 'bg-green-600 hover:bg-green-700',
    hover: 'hover:bg-green-50',
    icon: 'text-green-600',
    ring: 'focus:ring-green-500'
  },
  purple: {
    button: 'bg-purple-600 hover:bg-purple-700',
    hover: 'hover:bg-purple-50',
    icon: 'text-purple-600',
    ring: 'focus:ring-purple-500'
  },
  amber: {
    button: 'bg-amber-600 hover:bg-amber-700',
    hover: 'hover:bg-amber-50',
    icon: 'text-amber-600',
    ring: 'focus:ring-amber-500'
  },
  orange: {
    button: 'bg-orange-600 hover:bg-orange-700',
    hover: 'hover:bg-orange-50',
    icon: 'text-orange-600',
    ring: 'focus:ring-orange-500'
  },
  emerald: {
    button: 'bg-emerald-600 hover:bg-emerald-700',
    hover: 'hover:bg-emerald-50',
    icon: 'text-emerald-600',
    ring: 'focus:ring-emerald-500'
  },
  teal: {
    button: 'bg-teal-600 hover:bg-teal-700',
    hover: 'hover:bg-teal-50',
    icon: 'text-teal-600',
    ring: 'focus:ring-teal-500'
  }
}

const buttonClass = computed(() => colorMap[props.color]?.button || colorMap.blue.button)
const hoverClass = computed(() => colorMap[props.color]?.hover || colorMap.blue.hover)
const iconClass = computed(() => colorMap[props.color]?.icon || colorMap.blue.icon)
const focusRingClass = computed(() => colorMap[props.color]?.ring || colorMap.blue.ring)

const handleInput = () => {
  showDropdown.value = true
}

const handleAdd = () => {
  const tag = searchText.value.trim()
  if (tag) {
    const currentSelected = props.selected || []
    if (!currentSelected.includes(tag)) {
      emit('update:selected', [...currentSelected, tag])
    }
    emit('add', tag)  // 通知父组件可能需要保存新标签到配置
    searchText.value = ''
    showDropdown.value = false
  }
}

const selectOption = (option: string) => {
  const currentSelected = props.selected || []
  if (!currentSelected.includes(option)) {
    emit('update:selected', [...currentSelected, option])
  }
  searchText.value = ''
  showDropdown.value = false
}

// 点击外部关闭下拉框（可选）
// 注意：如果需要v-click-outside指令，需要在父组件中处理
</script>

