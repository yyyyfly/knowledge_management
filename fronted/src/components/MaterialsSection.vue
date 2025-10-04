<template>
  <section class="mb-8 animate-fade-in">
    <div class="bg-dark-800 rounded-xl shadow-card p-6 mb-6">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-2xl font-bold">素材组</h2>
        <div class="flex space-x-2">
          <button class="px-4 py-2 bg-primary hover:bg-primary/90 text-white rounded-lg flex items-center space-x-2 transition-colors">
            <i class="fas fa-plus"></i>
            <span>新建笔记</span>
          </button>
          <button class="px-4 py-2 bg-dark-600/50 hover:bg-dark-600/80 text-dark-100 rounded-lg flex items-center space-x-2 transition-colors">
            <i class="fas fa-filter"></i>
            <span>筛选</span>
          </button>
          <button class="px-4 py-2 bg-dark-600/50 hover:bg-dark-600/80 text-dark-100 rounded-lg flex items-center space-x-2 transition-colors">
            <i class="fas fa-sort"></i>
            <span>排序</span>
          </button>
        </div>
      </div>
      
      <!-- 素材组标签页 -->
      <div class="border-b border-dark-600 mb-6">
        <div class="flex space-x-1 overflow-x-auto scrollbar-hide">
          <button class="px-4 py-2 text-primary border-b-2 border-primary font-medium">全部</button>
          <button class="px-4 py-2 text-dark-400 hover:text-dark-100">碎片笔记</button>
          <button class="px-4 py-2 text-dark-400 hover:text-dark-100">框架笔记</button>
          <button class="px-4 py-2 text-dark-400 hover:text-dark-100">求学笔记</button>
          <button class="px-4 py-2 text-dark-400 hover:text-dark-100">背诵笔记</button>
          <button class="px-4 py-2 text-dark-400 hover:text-dark-100">刷题笔记</button>
          <button class="px-4 py-2 text-dark-400 hover:text-dark-100">实战笔记</button>
        </div>
      </div>
      
      <!-- 笔记列表 -->
      <div class="space-y-4">
        <div v-for="note in notes" :key="note.id" class="flex items-start p-4 bg-dark-600/20 rounded-lg border border-dark-600/40 hover:shadow-hover transition-shadow cursor-pointer">
          <div class="flex-shrink-0 w-10 h-10 rounded-full" :class="note.iconBgClass">
            <i :class="note.icon" class="w-full h-full flex items-center justify-center"></i>
          </div>
          <div class="ml-4 flex-1">
            <div class="flex items-center justify-between">
              <h3 class="font-medium">{{ note.title }}</h3>
              <span class="text-dark-400 text-sm">{{ note.time }}</span>
            </div>
            <p class="text-dark-400 text-sm mt-1">{{ note.description }}</p>
            <div class="flex flex-wrap gap-2 mt-2">
              <span v-for="tag in note.tags" :key="tag" class="px-2 py-1 bg-dark-600/50 text-dark-200 text-xs rounded-full">
                {{ tag }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Note {
  id: number
  title: string
  time: string
  description: string
  icon: string
  iconBgClass: string
  tags: string[]
}

const notes = ref<Note[]>([
  {
    id: 1,
    title: 'Python数据分析笔记',
    time: '2小时前',
    description: '更新了数据可视化部分，添加了Matplotlib和Seaborn的使用示例',
    icon: 'fas fa-file-lines',
    iconBgClass: 'bg-primary/20 text-primary',
    tags: ['基础笔记', 'Python']
  },
  {
    id: 2,
    title: '个人网站重构',
    time: '昨天',
    description: '完成了首页设计，优化了响应式布局，添加了新的交互元素',
    icon: 'fas fa-gear',
    iconBgClass: 'bg-secondary/20 text-secondary',
    tags: ['工程建设', '前端']
  },
  {
    id: 3,
    title: '《学习之道》读书笔记',
    time: '3天前',
    description: '完成了第三章"学习的记忆机制"的阅读笔记，整理了关键概念和实践方法',
    icon: 'fas fa-book',
    iconBgClass: 'bg-accent/20 text-accent',
    tags: ['书籍笔记', '学习方法']
  }
])
</script>

<style scoped>
.scrollbar-hide {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.scrollbar-hide::-webkit-scrollbar {
  display: none;
}
</style> 