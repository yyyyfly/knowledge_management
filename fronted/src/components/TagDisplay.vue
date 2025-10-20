<template>
  <div v-if="tags.length > 0" class="mt-2">
    <span class="text-sm text-gray-500">{{ label }}: </span>
    <div class="flex flex-wrap gap-2 mt-1">
      <span 
        v-for="tag in tags" 
        :key="tag"
        :class="['px-3 py-1 rounded-full text-sm flex items-center space-x-1', tagClass]"
      >
        <span>{{ tag }}</span>
        <button 
          type="button"
          @click="$emit('remove', tag)"
          :class="['hover:opacity-70 transition-opacity', removeButtonClass]"
        >
          <i class="fas fa-times"></i>
        </button>
      </span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  tags: string[]
  label?: string
  color?: string
}

const props = withDefaults(defineProps<Props>(), {
  label: '当前标签',
  color: 'blue'
})

defineEmits<{
  'remove': [tag: string]
}>()

// 根据颜色生成样式类
const colorMap: Record<string, { tag: string; button: string }> = {
  blue: {
    tag: 'bg-blue-100 text-blue-700',
    button: 'text-blue-500 hover:text-blue-700'
  },
  green: {
    tag: 'bg-green-100 text-green-700',
    button: 'text-green-500 hover:text-green-700'
  },
  purple: {
    tag: 'bg-purple-100 text-purple-700',
    button: 'text-purple-500 hover:text-purple-700'
  },
  amber: {
    tag: 'bg-amber-100 text-amber-700',
    button: 'text-amber-500 hover:text-amber-700'
  },
  orange: {
    tag: 'bg-orange-100 text-orange-700',
    button: 'text-orange-500 hover:text-orange-700'
  },
  emerald: {
    tag: 'bg-emerald-100 text-emerald-700',
    button: 'text-emerald-500 hover:text-emerald-700'
  },
  rose: {
    tag: 'bg-rose-100 text-rose-700',
    button: 'text-rose-500 hover:text-rose-700'
  },
  cyan: {
    tag: 'bg-cyan-100 text-cyan-700',
    button: 'text-cyan-500 hover:text-cyan-700'
  }
}

const tagClass = computed(() => colorMap[props.color]?.tag || colorMap.blue.tag)
const removeButtonClass = computed(() => colorMap[props.color]?.button || colorMap.blue.button)
</script>

