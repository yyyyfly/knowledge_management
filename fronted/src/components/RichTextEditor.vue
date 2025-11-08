<template>
  <div :class="['border rounded-lg overflow-hidden bg-white', borderColorClass]">
    <!-- 工具栏 -->
    <div class="bg-gray-50 border-b border-gray-300 p-2 flex flex-wrap items-center gap-1">
      <!-- 基础格式 -->
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleBold().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('bold') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="粗体"
      >
        <i class="fas fa-bold"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleItalic().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('italic') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="斜体"
      >
        <i class="fas fa-italic"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleUnderline().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('underline') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="下划线"
      >
        <i class="fas fa-underline"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleStrike().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('strike') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="删除线"
      >
        <i class="fas fa-strikethrough"></i>
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- 标题 -->
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleHeading({ level: 1 }).run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 1 }) }" 
        class="px-2 py-1 rounded hover:bg-gray-200 font-bold text-sm" 
        title="标题1"
      >
        H1
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleHeading({ level: 2 }).run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 2 }) }" 
        class="px-2 py-1 rounded hover:bg-gray-200 font-bold text-sm" 
        title="标题2"
      >
        H2
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleHeading({ level: 3 }).run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 3 }) }" 
        class="px-2 py-1 rounded hover:bg-gray-200 font-bold text-sm" 
        title="标题3"
      >
        H3
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- 列表 -->
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleBulletList().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('bulletList') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="无序列表"
      >
        <i class="fas fa-list-ul"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleOrderedList().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('orderedList') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="有序列表"
      >
        <i class="fas fa-list-ol"></i>
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- 对齐 -->
      <button 
        type="button" 
        @click="editor?.chain().focus().setTextAlign('left').run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'left' }) }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="左对齐"
      >
        <i class="fas fa-align-left"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().setTextAlign('center').run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'center' }) }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="居中"
      >
        <i class="fas fa-align-center"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().setTextAlign('right').run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive({ textAlign: 'right' }) }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="右对齐"
      >
        <i class="fas fa-align-right"></i>
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- 高亮和代码 -->
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleHighlight().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('highlight') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="高亮"
      >
        <i class="fas fa-highlighter"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().toggleCodeBlock().run()" 
        :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('codeBlock') }" 
        class="p-2 rounded hover:bg-gray-200" 
        title="代码块"
      >
        <i class="fas fa-code"></i>
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- 表格和媒体 -->
      <button 
        type="button" 
        @click="$emit('insert-table')" 
        class="p-2 rounded hover:bg-gray-200" 
        title="插入表格"
      >
        <i class="fas fa-table"></i>
      </button>
      <button 
        type="button" 
        @click="$emit('insert-image')" 
        class="p-2 rounded hover:bg-gray-200" 
        title="插入图片"
      >
        <i class="fas fa-image"></i>
      </button>
      <button 
        type="button" 
        @click="$emit('upload-image')" 
        class="p-2 rounded hover:bg-gray-200" 
        title="上传图片"
      >
        <i class="fas fa-upload"></i>
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- 链接 -->
      <button 
        type="button" 
        @click="$emit('set-link')" 
        class="p-2 rounded hover:bg-gray-200" 
        title="插入链接"
      >
        <i class="fas fa-link"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().unsetLink().run()" 
        :disabled="!editor?.isActive('link')" 
        class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed" 
        title="取消链接"
      >
        <i class="fas fa-unlink"></i>
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- 撤销重做 -->
      <button 
        type="button" 
        @click="editor?.chain().focus().undo().run()" 
        :disabled="!editor?.can().undo()" 
        class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed" 
        title="撤销"
      >
        <i class="fas fa-undo"></i>
      </button>
      <button 
        type="button" 
        @click="editor?.chain().focus().redo().run()" 
        :disabled="!editor?.can().redo()" 
        class="p-2 rounded hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed" 
        title="重做"
      >
        <i class="fas fa-redo"></i>
      </button>
      <div class="w-px h-6 bg-gray-300 mx-1"></div>
      
      <!-- Markdown -->
      <button 
        type="button" 
        @click="$emit('import-markdown')" 
        class="px-2 py-1 rounded hover:bg-gray-200 text-sm font-semibold" 
        title="导入Markdown"
      >
        <i class="fab fa-markdown"></i>
      </button>
    </div>
    
    <!-- 编辑器内容区 -->
    <div :class="['p-4 overflow-y-auto', contentClass]">
      <EditorContent :editor="editor" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { EditorContent } from '@tiptap/vue-3'
import type { Editor } from '@tiptap/vue-3'

interface Props {
  editor?: Editor | null
  borderColor?: string
  contentClass?: string
}

const props = withDefaults(defineProps<Props>(), {
  editor: null,
  borderColor: 'gray',
  contentClass: 'min-h-[200px] max-h-[400px]'
})

defineEmits<{
  'insert-table': []
  'insert-image': []
  'upload-image': []
  'set-link': []
  'import-markdown': []
}>()

// 根据颜色生成边框类
const borderColorClass = computed(() => {
  const colorMap: Record<string, string> = {
    purple: 'border-purple-200',
    blue: 'border-blue-200',
    green: 'border-emerald-200',
    emerald: 'border-emerald-200',
    amber: 'border-amber-200',
    yellow: 'border-amber-200',
    rose: 'border-rose-200',
    red: 'border-rose-200',
    cyan: 'border-cyan-200',
    gray: 'border-gray-300'
  }
  return colorMap[props.borderColor] || 'border-gray-300'
})
</script>

<style scoped>
/* Tiptap编辑器样式 */
:deep(.ProseMirror) {
  outline: none;
  min-height: 100px;
}

:deep(.ProseMirror p) {
  margin: 0.5em 0;
}

:deep(.ProseMirror h1),
:deep(.ProseMirror h2),
:deep(.ProseMirror h3) {
  font-weight: bold;
  margin: 1em 0 0.5em;
}

:deep(.ProseMirror h1) {
  font-size: 2em;
}

:deep(.ProseMirror h2) {
  font-size: 1.5em;
}

:deep(.ProseMirror h3) {
  font-size: 1.25em;
}

:deep(.ProseMirror ul),
:deep(.ProseMirror ol) {
  padding-left: 2em;
  margin: 0.5em 0;
}

:deep(.ProseMirror ul) {
  list-style-type: disc;
}

:deep(.ProseMirror ol) {
  list-style-type: decimal;
  list-style-position: outside;
}

:deep(.ProseMirror li) {
  margin: 0.25em 0;
  padding-left: 0.25em;
}

/* 嵌套列表 */
:deep(.ProseMirror li > ul),
:deep(.ProseMirror li > ol) {
  margin: 0.25em 0;
}

:deep(.ProseMirror ul ul) {
  list-style-type: circle;
}

:deep(.ProseMirror ul ul ul) {
  list-style-type: square;
}

:deep(.ProseMirror code) {
  background-color: #f3f4f6;
  padding: 0.125em 0.25em;
  border-radius: 0.25em;
  font-family: monospace;
}

:deep(.ProseMirror pre) {
  background: #1e293b;
  color: #e2e8f0;
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
  overflow-x: auto;
  margin: 0.5em 0;
}

:deep(.ProseMirror pre code) {
  background: none;
  padding: 0;
  color: inherit;
}

:deep(.ProseMirror mark) {
  background-color: #fef08a;
  padding: 0.125em 0;
}

:deep(.ProseMirror table) {
  border-collapse: collapse;
  margin: 0.5em 0;
  width: 100%;
}

:deep(.ProseMirror th),
:deep(.ProseMirror td) {
  border: 1px solid #d1d5db;
  padding: 0.5em;
}

:deep(.ProseMirror th) {
  background-color: #f3f4f6;
  font-weight: bold;
}

:deep(.ProseMirror img) {
  max-width: 100%;
  height: auto;
  border-radius: 0.5rem;
}

:deep(.ProseMirror a) {
  color: #3b82f6;
  text-decoration: underline;
}

:deep(.ProseMirror a:hover) {
  color: #2563eb;
}
</style>

