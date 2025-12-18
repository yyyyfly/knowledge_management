<template>
  <section id="project-analysis" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-2">项目分析</h1>
      <p class="text-gray-600">分析项目的业务底层逻辑，制定特定的执行方案</p>
    </div>

    <!-- 项目选择区 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-6">
      <div class="flex flex-col md:flex-row gap-4">
        <!-- 分类筛选 -->
        <div class="w-full md:w-48">
          <label class="block text-sm font-medium text-gray-700 mb-2">项目分类</label>
          <select 
            v-model="categoryFilter"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          >
            <option value="">全部分类</option>
            <option value="defense">战争行动</option>
            <option value="construction">工程建设</option>
            <option value="diplomatic">外交行动</option>
          </select>
        </div>
        
        <!-- 项目选择 -->
        <div class="flex-1">
          <label class="block text-sm font-medium text-gray-700 mb-2">选择项目</label>
          <select 
            v-model="selectedProjectId" 
            @change="loadProjectAnalysis"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          >
            <option value="">请选择项目</option>
            <option v-for="project in filteredProjects" :key="project.id" :value="project.id">
              {{ project.name }} 
              <template v-if="project.category === 'defense'">【战争行动】</template>
              <template v-else-if="project.category === 'construction'">【工程建设】</template>
              <template v-else-if="project.category === 'diplomatic'">【外交行动】</template>
            </option>
          </select>
        </div>
      </div>
      
      <!-- 筛选结果统计 -->
      <div class="mt-3 text-sm text-gray-500">
        共找到 {{ filteredProjects.length }} 个项目
        <span v-if="selectedProjectId" class="ml-4 text-blue-600">
          已选择: {{ selectedProjectName }}
        </span>
      </div>
    </div>

    <!-- 内容区域 -->
    <div v-if="selectedProjectId" class="space-y-6">
      <!-- 底层逻辑分析区 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <div>
            <h3 class="text-xl font-semibold text-gray-900">底层逻辑分析</h3>
            <p class="text-sm text-gray-500 mt-1">分析项目的业务本质、选拔逻辑或运作机制</p>
          </div>
          <div class="flex space-x-3">
            <button 
              v-if="!isEditing"
              @click="startEditing"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center space-x-2"
            >
              <i class="fas fa-edit"></i>
              <span>编辑</span>
            </button>
            <template v-else>
              <button 
                @click="cancelEditing"
                class="px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50"
              >
                取消
              </button>
              <button 
                @click="saveAnalysis"
                :disabled="isSaving"
                class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 flex items-center space-x-2"
              >
                <i v-if="isSaving" class="fas fa-spinner fa-spin"></i>
                <i v-else class="fas fa-save"></i>
                <span>{{ isSaving ? '保存中...' : '保存' }}</span>
              </button>
            </template>
          </div>
        </div>

        <!-- 编辑模式 - 使用完整的富文本编辑器组件 -->
        <div v-if="isEditing">
          <RichTextEditor 
            :editor="editor" 
            border-color="blue"
            content-class="min-h-[300px] max-h-[500px]"
            @insert-table="insertTable"
            @insert-image="insertImage"
            @upload-image="uploadImage"
            @set-link="setLink"
            @import-markdown="importMarkdown"
          />
        </div>

        <!-- 查看模式 -->
        <div v-else class="prose prose-sm max-w-none">
          <div v-if="analysisContent" v-html="analysisContent" class="p-4 bg-gray-50 rounded-lg min-h-[200px]"></div>
          <div v-else class="p-8 text-center text-gray-400 bg-gray-50 rounded-lg">
            <i class="fas fa-file-alt text-4xl mb-3"></i>
            <p>暂无底层逻辑分析</p>
            <p class="text-sm mt-1">点击"编辑"按钮开始分析</p>
          </div>
        </div>
      </div>

      <!-- 笔记调用区 -->
      <div class="bg-white rounded-xl shadow-soft p-6">
        <div class="flex items-center justify-between mb-4">
          <div>
            <h3 class="text-xl font-semibold text-gray-900">笔记调用</h3>
            <p class="text-sm text-gray-500 mt-1">关联项目相关笔记，作为分析参考资料</p>
          </div>
          <div class="flex space-x-3">
            <button 
              @click="showAddNoteDialog = true"
              class="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 flex items-center space-x-2"
            >
              <i class="fas fa-plus"></i>
              <span>添加笔记</span>
            </button>
            <button 
              @click="openArchiveImportDialog"
              class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 flex items-center space-x-2"
            >
              <i class="fas fa-folder-open"></i>
              <span>从归档批量添加</span>
            </button>
          </div>
        </div>

        <!-- 笔记卡片网格 -->
        <div v-if="projectNotes.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <div 
            v-for="note in projectNotes" 
            :key="note.id"
            class="p-4 border-2 rounded-lg hover:shadow-md transition-all duration-200 cursor-pointer"
            :class="getNoteTypeBorderClass(note.type)"
            @click="viewNoteDetail(note)"
          >
            <div class="flex items-start justify-between mb-3">
              <div class="flex items-center space-x-2">
                <div class="w-8 h-8 rounded-full flex items-center justify-center" :class="getNoteTypeIconClass(note.type)">
                  <i :class="getNoteTypeIcon(note.type)" class="text-sm"></i>
                </div>
                <span class="px-2 py-1 rounded text-xs font-medium" :class="getNoteTypeClass(note.type)">
                  {{ getNoteTypeText(note.type) }}
                </span>
              </div>
              <button 
                @click.stop="removeNoteFromProject(note.id)"
                class="text-red-500 hover:text-red-700"
                title="移除关联"
              >
                <i class="fas fa-times"></i>
              </button>
            </div>
            <h5 class="text-md font-semibold text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h5>
            <p class="text-sm text-gray-600 mb-3 line-clamp-2">{{ note.summary }}</p>
            <div class="flex items-center justify-between text-xs text-gray-500">
              <span>{{ note.createTime || note.recCreateTime }}</span>
              <div class="flex space-x-1" v-if="Array.isArray(note.tags) && note.tags.length > 0">
                <span v-for="tag in note.tags.slice(0, 2)" :key="tag" class="bg-gray-100 text-gray-600 px-2 py-0.5 rounded">
                  {{ tag }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-else class="text-center py-12 text-gray-500">
          <i class="fas fa-book-open text-6xl mb-4 text-indigo-200"></i>
          <p class="text-lg font-medium mb-2">暂无关联笔记</p>
          <p class="text-sm">点击"添加笔记"按钮，为项目关联相关笔记</p>
        </div>
      </div>
    </div>

    <!-- 未选择项目提示 -->
    <div v-else class="bg-white rounded-xl shadow-soft p-12 text-center text-gray-500">
      <i class="fas fa-folder-open text-6xl mb-4 text-gray-300"></i>
      <p class="text-lg font-medium">请先选择一个项目</p>
      <p class="text-sm mt-2">选择项目后可以进行底层逻辑分析和笔记调用</p>
    </div>

    <!-- 添加笔记弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showAddNoteDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl w-full max-w-6xl max-h-[90vh] flex flex-col">
            <!-- 弹窗头部 -->
            <div class="flex items-center justify-between p-6 border-b border-gray-200">
              <h3 class="text-2xl font-semibold text-gray-900">添加笔记到项目</h3>
              <button 
                @click="closeAddNoteDialog"
                class="text-gray-500 hover:text-gray-700 transition-colors"
              >
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
            
            <!-- 搜索和筛选 -->
            <div class="p-6 border-b border-gray-200">
              <div class="flex gap-4">
                <div class="flex-1 relative">
                  <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="noteSearchQuery"
                    type="text" 
                    placeholder="搜索笔记标题或内容..."
                    class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  >
                </div>
                <select 
                  v-model="noteFilterType"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                >
                  <option value="">全部类型</option>
                  <option value="fragment">碎片笔记</option>
                  <option value="skill">技能笔记</option>
                  <option value="study">求学笔记</option>
                  <option value="memorization">背诵笔记</option>
                  <option value="exercise">刷题笔记</option>
                  <option value="practical">实战笔记</option>
                </select>
              </div>
            </div>
            
            <!-- 笔记列表 -->
            <div class="flex-1 overflow-y-auto p-6">
              <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div 
                  v-for="note in filteredAvailableNotes" 
                  :key="note.id"
                  class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 hover:border-indigo-300 cursor-pointer transition-all duration-200"
                  @click="addNoteToProject(note)"
                >
                  <div class="flex items-start justify-between mb-3">
                    <span class="px-2 py-1 rounded text-xs font-medium" :class="getNoteTypeClass(note.type)">
                      {{ getNoteTypeText(note.type) }}
                    </span>
                    <i class="fas fa-plus-circle text-indigo-600 text-xl"></i>
                  </div>
                  <h5 class="text-sm font-medium text-gray-900 mb-2 line-clamp-2">{{ note.title }}</h5>
                  <p class="text-xs text-gray-600 mb-2 line-clamp-2">{{ note.summary }}</p>
                  <div class="flex items-center justify-between text-xs text-gray-500">
                    <span>{{ note.createTime || note.recCreateTime }}</span>
                  </div>
                </div>
                
                <!-- 空状态 -->
                <div v-if="filteredAvailableNotes.length === 0" class="col-span-full text-center py-16 text-gray-500">
                  <i class="fas fa-search text-5xl mb-4 text-gray-300"></i>
                  <p class="text-lg font-medium mb-2">未找到匹配的笔记</p>
                  <p class="text-sm">请尝试调整搜索条件</p>
                </div>
              </div>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 从归档批量添加弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showArchiveImportDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl w-full max-w-2xl max-h-[80vh] flex flex-col">
            <!-- 弹窗头部 -->
            <div class="flex items-center justify-between p-6 border-b border-gray-200">
              <h3 class="text-2xl font-semibold text-gray-900">从归档批量添加笔记</h3>
              <button 
                @click="closeArchiveImportDialog"
                class="text-gray-500 hover:text-gray-700 transition-colors"
              >
                <i class="fas fa-times text-2xl"></i>
              </button>
            </div>
            
            <!-- 说明 -->
            <div class="p-6 border-b border-gray-200 bg-blue-50">
              <div class="flex items-start">
                <i class="fas fa-info-circle text-blue-600 mt-1 mr-3"></i>
                <div class="text-sm text-gray-700">
                  <p class="font-semibold mb-1">快速关联归档中的所有笔记</p>
                  <p>选择一个笔记归档，系统会自动将该归档中的所有笔记添加到当前项目中。</p>
                </div>
              </div>
            </div>
            
            <!-- 归档列表 -->
            <div class="flex-1 overflow-y-auto p-6">
              <div v-if="isLoadingArchives" class="text-center py-12">
                <i class="fas fa-spinner fa-spin text-4xl text-gray-400 mb-3"></i>
                <p class="text-gray-500">加载归档中...</p>
              </div>
              
              <div v-else-if="archives.length === 0" class="text-center py-12 text-gray-500">
                <i class="fas fa-folder-open text-6xl mb-4 text-gray-300"></i>
                <p class="text-lg font-medium mb-2">暂无归档</p>
                <p class="text-sm">请先在笔记归档页面创建归档</p>
              </div>
              
              <div v-else class="space-y-3">
                <div 
                  v-for="archive in archives" 
                  :key="archive.id"
                  class="p-4 border-2 border-gray-200 rounded-lg hover:border-green-500 hover:bg-green-50 cursor-pointer transition-all duration-200"
                  @click="importArchiveNotes(archive)"
                >
                  <div class="flex items-start justify-between">
                    <div class="flex-1">
                      <div class="mb-2">
                        <h4 class="text-lg font-semibold text-gray-900">{{ archive.title }}</h4>
                      </div>
                      <p v-if="archive.description" class="text-sm text-gray-600 mb-2">{{ archive.description }}</p>
                      <div class="flex items-center text-xs text-gray-500">
                        <span v-if="archive.author" class="mr-3">
                          <i class="fas fa-user mr-1"></i>{{ archive.author }}
                        </span>
                        <span>
                          <i class="fas fa-sticky-note mr-1"></i>{{ archive.noteCount || 0 }} 条笔记
                        </span>
                      </div>
                    </div>
                    <i class="fas fa-arrow-circle-right text-green-600 text-2xl"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>

    <!-- 笔记详情弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showNoteDetail" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <Transition name="modal-slide">
          <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-xl font-semibold text-gray-900">笔记详情</h3>
              <button 
                @click="closeNoteDetail"
                class="text-gray-500 hover:text-gray-700"
              >
                <i class="fas fa-times text-xl"></i>
              </button>
            </div>
            
            <div v-if="selectedNoteDetail" class="space-y-4">
              <div class="flex items-center space-x-3 mb-4">
                <h4 class="text-lg font-medium text-gray-900">{{ selectedNoteDetail.title }}</h4>
                <span :class="getNoteTypeClass(selectedNoteDetail.type)" class="px-2 py-1 rounded-full text-xs font-medium">
                  {{ getNoteTypeText(selectedNoteDetail.type) }}
                </span>
              </div>
              
              <div class="bg-gray-50 p-4 rounded-lg" v-if="selectedNoteDetail.summary">
                <p class="text-sm font-medium text-gray-700 mb-2">摘要：</p>
                <p class="text-sm text-gray-600">{{ selectedNoteDetail.summary }}</p>
              </div>
              
              <div>
                <p class="text-sm font-medium text-gray-700 mb-2">详细内容：</p>
                <div class="text-sm text-gray-600 prose prose-sm max-w-none" v-html="selectedNoteDetail.content"></div>
              </div>
              
              <div class="flex flex-col space-y-2 text-sm text-gray-500">
                <span>创建时间：{{ selectedNoteDetail.createTime || selectedNoteDetail.recCreateTime }}</span>
                <div class="flex flex-wrap gap-2" v-if="Array.isArray(selectedNoteDetail.tags) && selectedNoteDetail.tags.length > 0">
                  <span class="text-gray-700 mr-2">标签：</span>
                  <span v-for="tag in selectedNoteDetail.tags" :key="tag" class="bg-gray-100 text-gray-600 px-2 py-1 rounded">
                    {{ tag }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>
  </section>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onActivated, onBeforeUnmount } from 'vue'
import { useEditor } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Underline from '@tiptap/extension-underline'
import Strike from '@tiptap/extension-strike'
import Highlight from '@tiptap/extension-highlight'
import CodeBlock from '@tiptap/extension-code-block'
import TextAlign from '@tiptap/extension-text-align'
import Table from '@tiptap/extension-table'
import TableRow from '@tiptap/extension-table-row'
import TableCell from '@tiptap/extension-table-cell'
import TableHeader from '@tiptap/extension-table-header'
import Image from '@tiptap/extension-image'
import Link from '@tiptap/extension-link'
import Placeholder from '@tiptap/extension-placeholder'
import RichTextEditor from '@/components/RichTextEditor.vue'
import request from '@/api/request'
import { getAllNotes, type Note } from '@/services/noteService'

// 响应式数据
const projects = ref<any[]>([])
const notes = ref<Note[]>([])
const selectedProjectId = ref<string>('')
const categoryFilter = ref<string>('')

// 底层逻辑分析相关
const analysisContent = ref<string>('')
const originalContent = ref<string>('')
const isEditing = ref(false)
const isSaving = ref(false)

// 笔记调用相关
const projectNotes = ref<Note[]>([])
const showAddNoteDialog = ref(false)
const noteSearchQuery = ref('')
const noteFilterType = ref('')
const showNoteDetail = ref(false)
const selectedNoteDetail = ref<Note | null>(null)

// 归档相关
const showArchiveImportDialog = ref(false)
const archives = ref<any[]>([])
const isLoadingArchives = ref(false)

// 富文本编辑器 - 完整功能
const editor = useEditor({
  content: '',
  extensions: [
    StarterKit.configure({
      heading: { levels: [1, 2, 3] },
      bulletList: { keepMarks: true, keepAttributes: false },
      orderedList: { keepMarks: true, keepAttributes: false },
    }),
    Underline,
    Strike,
    Highlight.configure({ multicolor: false }),
    CodeBlock.configure({
      HTMLAttributes: { class: 'bg-gray-800 text-gray-100 p-4 rounded-lg font-mono text-sm' },
    }),
    TextAlign.configure({
      types: ['heading', 'paragraph'],
    }),
    Table.configure({
      resizable: true,
      HTMLAttributes: { class: 'border-collapse border border-gray-300 w-full' },
    }),
    TableRow.configure({
      HTMLAttributes: { class: 'border border-gray-300' },
    }),
    TableHeader.configure({
      HTMLAttributes: { class: 'border border-gray-300 bg-gray-100 font-bold p-2' },
    }),
    TableCell.configure({
      HTMLAttributes: { class: 'border border-gray-300 p-2' },
    }),
    Image.configure({
      HTMLAttributes: { class: 'max-w-full h-auto rounded-lg' },
    }),
    Link.configure({
      openOnClick: false,
      HTMLAttributes: { class: 'text-blue-600 underline cursor-pointer' },
    }),
    Placeholder.configure({
      placeholder: '开始分析项目的底层逻辑...',
    }),
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-sm sm:prose lg:prose-lg mx-auto focus:outline-none min-h-[300px] p-4',
    },
  },
})

// 编辑器工具函数
const insertTable = () => {
  editor.value?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

const insertImage = () => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editor.value?.chain().focus().setImage({ src: url }).run()
  }
}

const uploadImage = () => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*'
  input.onchange = (event) => {
    const file = (event.target as HTMLInputElement).files?.[0]
    if (file) {
      const reader = new FileReader()
      reader.onload = (e) => {
        const url = e.target?.result as string
        editor.value?.chain().focus().setImage({ src: url }).run()
      }
      reader.readAsDataURL(file)
    }
  }
  input.click()
}

const setLink = () => {
  const url = window.prompt('请输入链接地址:')
  if (url) {
    editor.value?.chain().focus().setLink({ href: url }).run()
  }
}

const importMarkdown = () => {
  const markdown = window.prompt('请粘贴Markdown内容:')
  if (markdown) {
    editor.value?.commands.setContent(markdown)
  }
}

// 计算属性：筛选后的项目列表
const filteredProjects = computed(() => {
  let filtered = projects.value
  if (categoryFilter.value) {
    filtered = filtered.filter(p => p.category === categoryFilter.value)
  }
  return filtered
})

// 计算属性：已选择项目名称
const selectedProjectName = computed(() => {
  const project = projects.value.find(p => p.id === parseInt(selectedProjectId.value))
  return project ? project.name : ''
})

// 计算属性：筛选后的可用笔记（排除已关联的）
const filteredAvailableNotes = computed(() => {
  const projectNoteIds = new Set(projectNotes.value.map(n => n.id))
  let filtered = notes.value.filter(note => !projectNoteIds.has(note.id))
  
  if (noteFilterType.value) {
    filtered = filtered.filter(note => note.type === noteFilterType.value)
  }
  
  if (noteSearchQuery.value) {
    const query = noteSearchQuery.value.toLowerCase()
    filtered = filtered.filter(note => 
      note.title.toLowerCase().includes(query) ||
      note.summary.toLowerCase().includes(query) ||
      (Array.isArray(note.tags) && note.tags.some(tag => tag.toLowerCase().includes(query)))
    )
  }
  
  return filtered
})

// 加载数据
const loadData = async () => {
  try {
    // 加载项目列表
    const projectRes = await request.get('/project/list')
    if (projectRes.code === 200) {
      projects.value = projectRes.data || []
    }
    
    // 加载笔记列表
    const notesData = await getAllNotes()
    notes.value = notesData
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

// 加载项目分析
const loadProjectAnalysis = async () => {
  if (!selectedProjectId.value) {
    analysisContent.value = ''
    projectNotes.value = []
    return
  }
  
  try {
    // 加载底层逻辑分析
    const analysisRes = await request.get(`/project/analysis/project/${selectedProjectId.value}`)
    if (analysisRes.code === 200 && analysisRes.data) {
      analysisContent.value = analysisRes.data.logicDescription || ''
    } else {
      analysisContent.value = ''
    }
    
    // 加载项目关联笔记
    await loadProjectNotes()
  } catch (error) {
    console.error('加载项目分析失败:', error)
    analysisContent.value = ''
  }
}

// 加载项目关联的笔记
const loadProjectNotes = async () => {
  if (!selectedProjectId.value) {
    projectNotes.value = []
    return
  }
  
  try {
    const response = await request.get(`/project/${selectedProjectId.value}/notes`)
    if (response.code === 200) {
      const noteIds = response.data || []
      projectNotes.value = notes.value.filter(note => noteIds.includes(note.id))
    }
  } catch (error) {
    console.error('加载项目笔记失败:', error)
    projectNotes.value = []
  }
}

// 开始编辑
const startEditing = () => {
  originalContent.value = analysisContent.value
  editor.value?.commands.setContent(analysisContent.value || '')
  isEditing.value = true
}

// 取消编辑
const cancelEditing = () => {
  isEditing.value = false
  editor.value?.commands.setContent('')
}

// 保存分析
const saveAnalysis = async () => {
  if (!selectedProjectId.value) return
  
  isSaving.value = true
  try {
    const content = editor.value?.getHTML() || ''
    const response = await request.post('/project/analysis', {
      projectId: parseInt(selectedProjectId.value),
      logicDescription: content
    })
    
    if (response.code === 200) {
      analysisContent.value = content
      isEditing.value = false
      alert('保存成功！')
    } else {
      alert('保存失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('保存分析失败:', error)
    alert('保存失败，请稍后重试')
  } finally {
    isSaving.value = false
  }
}

// 添加笔记到项目
const addNoteToProject = async (note: Note) => {
  if (!selectedProjectId.value) return
  
  try {
    const response = await request.post(`/project/${selectedProjectId.value}/notes`, {
      noteId: note.id
    })
    
    if (response.code === 200) {
      projectNotes.value.push(note)
      alert('笔记已关联到项目')
    } else {
      alert('关联失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('关联笔记失败:', error)
    if (!projectNotes.value.find(n => n.id === note.id)) {
      projectNotes.value.push(note)
      alert('笔记已关联到项目（本地模式）')
    }
  }
}

// 从项目移除笔记
const removeNoteFromProject = async (noteId: number) => {
  if (!selectedProjectId.value) return
  if (!confirm('确定要移除这个笔记的关联吗？')) return
  
  try {
    const response = await request.delete(`/project/${selectedProjectId.value}/notes/${noteId}`)
    
    if (response.code === 200) {
      projectNotes.value = projectNotes.value.filter(n => n.id !== noteId)
      alert('已移除笔记关联')
    } else {
      alert('移除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('移除笔记关联失败:', error)
    projectNotes.value = projectNotes.value.filter(n => n.id !== noteId)
    alert('已移除笔记关联（本地模式）')
  }
}

// 关闭添加笔记弹窗
const closeAddNoteDialog = () => {
  showAddNoteDialog.value = false
  noteSearchQuery.value = ''
  noteFilterType.value = ''
}

// 打开归档导入弹窗
const openArchiveImportDialog = async () => {
  showArchiveImportDialog.value = true
  await loadArchives()
}

// 关闭归档导入弹窗
const closeArchiveImportDialog = () => {
  showArchiveImportDialog.value = false
  archives.value = []
}

// 加载归档列表
const loadArchives = async () => {
  isLoadingArchives.value = true
  try {
    const response = await request.get('/note-archive/list')
    if (response.code === 200) {
      archives.value = response.data || []
    } else {
      archives.value = []
    }
  } catch (error) {
    console.error('加载归档列表失败:', error)
    archives.value = []
  } finally {
    isLoadingArchives.value = false
  }
}

// 批量导入归档中的所有笔记
const importArchiveNotes = async (archive: any) => {
  if (!selectedProjectId.value) return
  
  try {
    const response = await request.get(`/note-archive/${archive.id}/notes`)
    if (response.code === 200) {
      const noteIds = response.data || []
      
      if (noteIds.length === 0) {
        alert('该归档中没有笔记')
        return
      }
      
      let successCount = 0
      let failCount = 0
      
      for (const noteId of noteIds) {
        try {
          await request.post(`/project/${selectedProjectId.value}/notes`, { noteId })
          successCount++
        } catch (error) {
          failCount++
        }
      }
      
      await loadProjectNotes()
      
      if (failCount === 0) {
        alert(`成功添加 ${successCount} 条笔记！`)
      } else {
        alert(`添加完成：成功 ${successCount} 条，失败 ${failCount} 条（可能已经关联过）`)
      }
      
      closeArchiveImportDialog()
    } else {
      alert('获取归档笔记失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('批量导入笔记失败:', error)
    alert('批量导入失败，请重试')
  }
}

// 查看笔记详情
const viewNoteDetail = (note: Note) => {
  const normalizedNote = { ...note }
  
  const ensureArray = (value: any) => {
    if (!value) return []
    if (Array.isArray(value)) return value
    if (typeof value === 'string') {
      if (value.trim().startsWith('[')) {
        try {
          const parsed = JSON.parse(value)
          if (Array.isArray(parsed)) return parsed
        } catch (e) {}
      }
      return value.split(',').map((item: string) => item.trim()).filter((item: string) => item !== '')
    }
    return [value]
  }
  
  if (normalizedNote.tags) normalizedNote.tags = ensureArray(normalizedNote.tags) as string[]
  
  selectedNoteDetail.value = normalizedNote
  showNoteDetail.value = true
}

// 关闭笔记详情
const closeNoteDetail = () => {
  showNoteDetail.value = false
  selectedNoteDetail.value = null
}

// 笔记类型相关工具函数
const getNoteTypeClass = (type: Note['type']) => {
  const classes: Record<string, string> = {
    skill: 'bg-purple-100 text-purple-600',
    study: 'bg-blue-100 text-blue-600',
    memorization: 'bg-green-100 text-green-600',
    exercise: 'bg-orange-100 text-orange-600',
    practical: 'bg-red-100 text-red-600',
    fragment: 'bg-pink-100 text-pink-600'
  }
  return classes[type] || 'bg-gray-100 text-gray-600'
}

const getNoteTypeBorderClass = (type: Note['type']) => {
  const classes: Record<string, string> = {
    skill: 'border-purple-200 hover:border-purple-400',
    study: 'border-blue-200 hover:border-blue-400',
    memorization: 'border-green-200 hover:border-green-400',
    exercise: 'border-orange-200 hover:border-orange-400',
    practical: 'border-red-200 hover:border-red-400',
    fragment: 'border-pink-200 hover:border-pink-400'
  }
  return classes[type] || 'border-gray-200 hover:border-gray-400'
}

const getNoteTypeText = (type: Note['type']) => {
  const texts: Record<string, string> = {
    skill: '技能笔记',
    study: '求学笔记',
    memorization: '背诵笔记',
    exercise: '刷题笔记',
    practical: '实战笔记',
    fragment: '碎片笔记'
  }
  return texts[type] || '未知类型'
}

const getNoteTypeIcon = (type: Note['type']) => {
  const icons: Record<string, string> = {
    skill: 'fas fa-sitemap',
    study: 'fas fa-graduation-cap',
    memorization: 'fas fa-lightbulb',
    exercise: 'fa-regular fa-pen-to-square',
    practical: 'fa-solid fa-flask',
    fragment: 'fas fa-puzzle-piece'
  }
  return icons[type] || 'fas fa-file'
}

const getNoteTypeIconClass = (type: Note['type']) => {
  const classes: Record<string, string> = {
    skill: 'bg-purple-100 text-purple-600',
    study: 'bg-blue-100 text-blue-600',
    memorization: 'bg-green-100 text-green-600',
    exercise: 'bg-orange-100 text-orange-600',
    practical: 'bg-red-100 text-red-600',
    fragment: 'bg-pink-100 text-pink-600'
  }
  return classes[type] || 'bg-gray-100 text-gray-600'
}

// 生命周期钩子
onMounted(() => {
  loadData()
})

onActivated(() => {
  loadData()
  if (selectedProjectId.value) {
    loadProjectAnalysis()
  }
})

onBeforeUnmount(() => {
  editor.value?.destroy()
})
</script>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-slide-enter-active,
.modal-slide-leave-active {
  transition: all 0.3s ease;
}

.modal-slide-enter-from {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

.modal-slide-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}

/* 富文本编辑器样式 */
:deep(.ProseMirror) {
  outline: none;
}

:deep(.ProseMirror p.is-editor-empty:first-child::before) {
  color: #adb5bd;
  content: attr(data-placeholder);
  float: left;
  height: 0;
  pointer-events: none;
}

:deep(.ProseMirror h1) {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

:deep(.ProseMirror h2) {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

:deep(.ProseMirror h3) {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

:deep(.ProseMirror ul),
:deep(.ProseMirror ol) {
  padding-left: 1.5rem;
  margin-bottom: 0.5rem;
}

:deep(.ProseMirror mark) {
  background-color: #fef08a;
  padding: 0 0.25rem;
}
</style>

