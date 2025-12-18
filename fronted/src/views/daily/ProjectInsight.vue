<template>
  <section id="project-insight" class="p-6 animate-fade-in">
    <!-- 页面头部 -->
    <div class="mb-8">
      <div class="flex items-center space-x-4 mb-4">
        <h1 class="text-3xl font-bold text-gray-900">项目心得</h1>
        <span class="bg-blue-100 text-blue-600 px-3 py-1 rounded-full text-sm font-medium">心得记录</span>
      </div>
      <p class="text-gray-600">记录项目执行过程中的心得体会、技术收获和执行总结</p>
    </div>

    <!-- 操作区域 -->
    <div class="bg-white rounded-xl shadow-soft p-6 mb-8">
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-xl font-semibold text-gray-900">项目心得列表</h3>
        <button 
          @click="showCreateRecord = true"
          class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center space-x-2"
        >
          <i class="fas fa-plus"></i>
          <span>新建心得</span>
        </button>
      </div>
      
      <!-- 搜索和筛选 -->
      <div class="mb-6">
        <div class="flex flex-col md:flex-row gap-4">
          <div class="flex-1">
            <input 
              v-model="searchQuery"
              type="text" 
              placeholder="搜索心得标题、内容或技术关键词..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
          </div>
          <div>
            <select v-model="filterProject" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
              <option value="">全部项目</option>
              <option v-for="project in projects" :key="project.id" :value="project.id">
                {{ project.name }}
              </option>
            </select>
          </div>
        </div>
      </div>
      
      <!-- 项目心得列表 -->
      <div class="space-y-4">
        <div v-for="record in filteredRecords" :key="record.id" class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center space-x-3 mb-2">
                <h4 class="text-lg font-medium text-gray-900">{{ record.title }}</h4>
                <span class="text-sm text-gray-500">项目：{{ getProjectName(record.projectId) }}</span>
              </div>
              <div class="bg-gray-50 p-3 rounded-lg mb-3">
                <p class="text-sm font-medium text-gray-700 mb-1">心得摘要：</p>
                <p class="text-sm text-gray-600">{{ record.summary }}</p>
              </div>
              
              <!-- 详细描述 -->
              <div v-if="record.content" class="mb-3">
                <p class="text-sm font-medium text-gray-700 mb-1">详细内容：</p>
                <div class="text-sm text-gray-600 prose prose-sm max-w-none" v-html="record.content"></div>
              </div>
              
              <div class="flex items-center space-x-4 text-sm text-gray-500">
                <span>{{ formatDate(record.recReviseTime || record.recCreateTime) }}</span>
                <div class="flex space-x-1">
                  <span v-for="tag in record.tags" :key="tag" class="bg-gray-100 text-gray-600 px-2 py-1 rounded">
                    {{ tag }}
                  </span>
                </div>
              </div>
            </div>
            <div class="flex space-x-2">
              <button @click="editRecord(record)" class="text-blue-600 hover:text-blue-800" title="编辑">
                <i class="fas fa-edit"></i>
              </button>
              <button @click="deleteRecord(record.id)" class="text-red-600 hover:text-red-800" title="删除">
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>
        </div>
        
        <!-- 空状态 -->
        <div v-if="filteredRecords.length === 0" class="text-center py-12 text-gray-500">
          <i class="fas fa-lightbulb text-6xl mb-4 text-blue-200"></i>
          <p class="text-lg font-medium mb-2">暂无项目心得</p>
          <p class="text-sm mb-4">记录你的项目经验和技术收获</p>
          <button 
            @click="showCreateRecord = true"
            class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
          >
            创建第一条心得
          </button>
        </div>
      </div>
    </div>

    <!-- 新建心得弹窗 -->
    <div v-if="showCreateRecord" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <Transition name="modal-slide">
        <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-xl font-semibold text-gray-900">新建项目心得</h3>
            <button 
              @click="closeCreateRecord"
              class="text-gray-500 hover:text-gray-700"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          
          <form @submit.prevent="submitRecord" class="space-y-6">
            <!-- 项目选择 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">选择项目</label>
              <select v-model="recordForm.projectId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                <option value="">请选择项目</option>
                <option v-for="project in projects" :key="project.id" :value="project.id">
                  {{ project.name }}
                </option>
              </select>
            </div>

            <!-- 心得标题 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">心得标题</label>
              <input 
                v-model="recordForm.title" 
                type="text" 
                placeholder="请输入心得标题"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              >
            </div>

            <!-- 心得摘要 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">心得摘要</label>
              <textarea 
                v-model="recordForm.summary" 
                rows="3" 
                placeholder="请输入心得摘要..."
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              ></textarea>
            </div>

            <!-- 富文本编辑器 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">详细内容</label>
              <div class="border border-gray-300 rounded-lg overflow-hidden">
                <!-- 工具栏 -->
                <div class="bg-gray-50 border-b border-gray-300 p-3 flex flex-wrap items-center gap-2">
                  <!-- 文本格式 -->
                  <div class="flex items-center space-x-1">
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
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 标题 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="editor?.chain().focus().toggleHeading({ level: 1 }).run()"
                      :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 1 }) }"
                      class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                      title="标题1"
                    >
                      H1
                    </button>
                    <button 
                      type="button"
                      @click="editor?.chain().focus().toggleHeading({ level: 2 }).run()"
                      :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 2 }) }"
                      class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                      title="标题2"
                    >
                      H2
                    </button>
                    <button 
                      type="button"
                      @click="editor?.chain().focus().toggleHeading({ level: 3 }).run()"
                      :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('heading', { level: 3 }) }"
                      class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                      title="标题3"
                    >
                      H3
                    </button>
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 列表 -->
                  <div class="flex items-center space-x-1">
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
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 引用和代码 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="editor?.chain().focus().toggleBlockquote().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editor?.isActive('blockquote') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="引用"
                    >
                      <i class="fas fa-quote-left"></i>
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
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 表格 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="insertTable"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入表格"
                    >
                      <i class="fas fa-table"></i>
                    </button>
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 图片和链接 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="insertImage"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入图片"
                    >
                      <i class="fas fa-image"></i>
                    </button>
                    <button 
                      type="button"
                      @click="setLink"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入链接"
                    >
                      <i class="fas fa-link"></i>
                    </button>
                  </div>
                </div>
                
                <!-- 编辑器内容区 -->
                <EditorContent :editor="editor" class="min-h-[200px] p-4 prose prose-sm max-w-none" />
              </div>
            </div>

            <!-- 标签管理 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">标签</label>
              <div class="flex flex-wrap gap-2 mb-3">
                <span v-for="(tag, index) in recordForm.tags" :key="index" class="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm flex items-center space-x-1">
                  <span>{{ tag }}</span>
                  <button type="button" @click="removeTag(index)" class="text-blue-600 hover:text-blue-800">
                    <i class="fas fa-times text-xs"></i>
                  </button>
                </span>
              </div>
              <div class="flex space-x-2">
                <input 
                  v-model="newTag" 
                  type="text" 
                  placeholder="输入标签名称"
                  @keyup.enter.prevent="addTag"
                  class="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                >
                <button type="button" @click="addTag" class="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
                  添加
                </button>
              </div>
              <div class="mt-3">
                <span class="text-sm text-gray-500 mr-2">建议标签：</span>
                <button 
                  v-for="tag in suggestedTags" 
                  :key="tag"
                  type="button"
                  @click="addSuggestedTag(tag)"
                  class="mr-2 mb-1 px-2 py-1 text-xs bg-gray-100 text-gray-600 rounded hover:bg-gray-200"
                >
                  {{ tag }}
                </button>
              </div>
            </div>

            <!-- 提交按钮 -->
            <div class="flex justify-end space-x-3">
              <button 
                type="button"
                @click="closeCreateRecord" 
                class="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
              >
                取消
              </button>
              <button 
                type="submit" 
                class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
              >
                保存心得
              </button>
            </div>
          </form>
        </div>
      </Transition>
    </div>

    <!-- 编辑心得弹窗 -->
    <div v-if="showEditRecord" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <Transition name="modal-slide">
        <div class="bg-white rounded-xl shadow-2xl p-6 w-full max-w-4xl mx-4 max-h-[90vh] overflow-y-auto">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-xl font-semibold text-gray-900">编辑项目心得</h3>
            <button 
              @click="closeEditRecord"
              class="text-gray-500 hover:text-gray-700"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          
          <form @submit.prevent="submitEditRecord" class="space-y-6">
            <!-- 项目选择 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">选择项目</label>
              <select v-model="editForm.projectId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                <option value="">请选择项目</option>
                <option v-for="project in projects" :key="project.id" :value="project.id">
                  {{ project.name }}
                </option>
              </select>
            </div>

            <!-- 心得标题 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">心得标题</label>
              <input 
                v-model="editForm.title" 
                type="text" 
                placeholder="请输入心得标题"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              >
            </div>

            <!-- 心得摘要 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">心得摘要</label>
              <textarea 
                v-model="editForm.summary" 
                rows="3" 
                placeholder="请输入心得摘要..."
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              ></textarea>
            </div>

            <!-- 富文本编辑器 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">详细内容</label>
              <div class="border border-gray-300 rounded-lg overflow-hidden">
                <!-- 工具栏 -->
                <div class="bg-gray-50 border-b border-gray-300 p-3 flex flex-wrap items-center gap-2">
                  <!-- 文本格式 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleBold().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('bold') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="粗体"
                    >
                      <i class="fas fa-bold"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleItalic().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('italic') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="斜体"
                    >
                      <i class="fas fa-italic"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleUnderline().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('underline') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="下划线"
                    >
                      <i class="fas fa-underline"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleStrike().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('strike') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="删除线"
                    >
                      <i class="fas fa-strikethrough"></i>
                    </button>
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 标题 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleHeading({ level: 1 }).run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('heading', { level: 1 }) }"
                      class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                      title="标题1"
                    >
                      H1
                    </button>
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleHeading({ level: 2 }).run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('heading', { level: 2 }) }"
                      class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                      title="标题2"
                    >
                      H2
                    </button>
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleHeading({ level: 3 }).run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('heading', { level: 3 }) }"
                      class="px-3 py-2 rounded hover:bg-gray-200 text-sm font-bold"
                      title="标题3"
                    >
                      H3
                    </button>
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 列表 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleBulletList().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('bulletList') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="无序列表"
                    >
                      <i class="fas fa-list-ul"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleOrderedList().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('orderedList') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="有序列表"
                    >
                      <i class="fas fa-list-ol"></i>
                    </button>
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 引用和代码 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleBlockquote().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('blockquote') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="引用"
                    >
                      <i class="fas fa-quote-left"></i>
                    </button>
                    <button 
                      type="button"
                      @click="editEditor?.chain().focus().toggleCodeBlock().run()"
                      :class="{ 'bg-blue-100 text-blue-600': editEditor?.isActive('codeBlock') }"
                      class="p-2 rounded hover:bg-gray-200"
                      title="代码块"
                    >
                      <i class="fas fa-code"></i>
                    </button>
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 表格 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="insertTableEdit"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入表格"
                    >
                      <i class="fas fa-table"></i>
                    </button>
                  </div>
                  
                  <div class="w-px h-6 bg-gray-300"></div>
                  
                  <!-- 图片和链接 -->
                  <div class="flex items-center space-x-1">
                    <button 
                      type="button"
                      @click="insertImageEdit"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入图片"
                    >
                      <i class="fas fa-image"></i>
                    </button>
                    <button 
                      type="button"
                      @click="setLinkEdit"
                      class="p-2 rounded hover:bg-gray-200"
                      title="插入链接"
                    >
                      <i class="fas fa-link"></i>
                    </button>
                  </div>
                </div>
                
                <!-- 编辑器内容区 -->
                <EditorContent :editor="editEditor" class="min-h-[200px] p-4 prose prose-sm max-w-none" />
              </div>
            </div>

            <!-- 标签管理 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">标签</label>
              <div class="flex flex-wrap gap-2 mb-3">
                <span v-for="(tag, index) in editForm.tags" :key="index" class="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm flex items-center space-x-1">
                  <span>{{ tag }}</span>
                  <button type="button" @click="removeEditTag(index)" class="text-blue-600 hover:text-blue-800">
                    <i class="fas fa-times text-xs"></i>
                  </button>
                </span>
              </div>
              <div class="flex space-x-2">
                <input 
                  v-model="newEditTag" 
                  type="text" 
                  placeholder="输入标签名称"
                  @keyup.enter.prevent="addEditTag"
                  class="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                >
                <button type="button" @click="addEditTag" class="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
                  添加
                </button>
              </div>
            </div>

            <!-- 提交按钮 -->
            <div class="flex justify-end space-x-3">
              <button 
                type="button"
                @click="closeEditRecord" 
                class="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
              >
                取消
              </button>
              <button 
                type="submit" 
                class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
              >
                保存修改
              </button>
            </div>
          </form>
        </div>
      </Transition>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, onBeforeUnmount } from 'vue'
import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Table from '@tiptap/extension-table'
import TableRow from '@tiptap/extension-table-row'
import TableHeader from '@tiptap/extension-table-header'
import TableCell from '@tiptap/extension-table-cell'
import Image from '@tiptap/extension-image'
import Underline from '@tiptap/extension-underline'
import Strike from '@tiptap/extension-strike'
import TextAlign from '@tiptap/extension-text-align'
import CodeBlock from '@tiptap/extension-code-block'
import Highlight from '@tiptap/extension-highlight'
import Link from '@tiptap/extension-link'
import Placeholder from '@tiptap/extension-placeholder'
import request from '@/utils/request'

// 项目列表
const projects = ref<any[]>([])

// 心得记录列表
const records = ref<any[]>([])

// 界面控制
const showCreateRecord = ref(false)
const showEditRecord = ref(false)

// 搜索和筛选
const searchQuery = ref('')
const filterProject = ref('')

// 新建表单
const recordForm = reactive({
  projectId: '',
  title: '',
  summary: '',
  content: '',
  tags: [] as string[]
})

// 编辑表单
const editForm = reactive({
  id: 0,
  projectId: '',
  title: '',
  summary: '',
  content: '',
  tags: [] as string[]
})

// 标签相关
const newTag = ref('')
const newEditTag = ref('')
const suggestedTags = ['技术总结', '经验教训', '方法论', '最佳实践', '问题解决', '流程优化', '工具使用', '团队协作']

// 富文本编辑器 - 新建
const editor = useEditor({
  content: '',
  extensions: [
    StarterKit.configure({
      heading: { levels: [1, 2, 3] },
      bulletList: { keepMarks: true, keepAttributes: false },
      orderedList: { keepMarks: true, keepAttributes: false },
    }),
    Table.configure({
      resizable: true,
      HTMLAttributes: { class: 'border-collapse border border-gray-300 w-full' },
    }),
    TableRow.configure({ HTMLAttributes: { class: 'border border-gray-300' } }),
    TableHeader.configure({ HTMLAttributes: { class: 'border border-gray-300 bg-gray-100 font-bold p-2' } }),
    TableCell.configure({ HTMLAttributes: { class: 'border border-gray-300 p-2' } }),
    Image.configure({ HTMLAttributes: { class: 'max-w-full h-auto cursor-pointer' } }),
    Underline,
    Strike,
    TextAlign.configure({ types: ['heading', 'paragraph'] }),
    CodeBlock.configure({ HTMLAttributes: { class: 'bg-gray-100 p-4 rounded-lg font-mono text-sm' } }),
    Highlight.configure({ multicolor: false }),
    Link.configure({ openOnClick: false, HTMLAttributes: { class: 'text-blue-600 underline cursor-pointer' } }),
    Placeholder.configure({ placeholder: '开始编写你的项目心得...' }),
  ],
  onUpdate: ({ editor }) => {
    recordForm.content = editor.getHTML()
  },
})

// 富文本编辑器 - 编辑
const editEditor = useEditor({
  content: '',
  extensions: [
    StarterKit.configure({
      heading: { levels: [1, 2, 3] },
      bulletList: { keepMarks: true, keepAttributes: false },
      orderedList: { keepMarks: true, keepAttributes: false },
    }),
    Table.configure({
      resizable: true,
      HTMLAttributes: { class: 'border-collapse border border-gray-300 w-full' },
    }),
    TableRow.configure({ HTMLAttributes: { class: 'border border-gray-300' } }),
    TableHeader.configure({ HTMLAttributes: { class: 'border border-gray-300 bg-gray-100 font-bold p-2' } }),
    TableCell.configure({ HTMLAttributes: { class: 'border border-gray-300 p-2' } }),
    Image.configure({ HTMLAttributes: { class: 'max-w-full h-auto cursor-pointer' } }),
    Underline,
    Strike,
    TextAlign.configure({ types: ['heading', 'paragraph'] }),
    CodeBlock.configure({ HTMLAttributes: { class: 'bg-gray-100 p-4 rounded-lg font-mono text-sm' } }),
    Highlight.configure({ multicolor: false }),
    Link.configure({ openOnClick: false, HTMLAttributes: { class: 'text-blue-600 underline cursor-pointer' } }),
    Placeholder.configure({ placeholder: '编辑项目心得...' }),
  ],
  onUpdate: ({ editor }) => {
    editForm.content = editor.getHTML()
  },
})

// 筛选后的记录
const filteredRecords = computed(() => {
  let filtered = records.value
  
  if (filterProject.value) {
    filtered = filtered.filter(record => record.projectId === parseInt(filterProject.value))
  }
  
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(record => 
      record.title.toLowerCase().includes(query) ||
      record.summary.toLowerCase().includes(query) ||
      (record.content && record.content.toLowerCase().includes(query)) ||
      record.tags.some((tag: string) => tag.toLowerCase().includes(query))
    )
  }
  
  return filtered
})

// 加载项目列表
const loadProjects = async () => {
  try {
    const response = await request.get('/project/list')
    if (response.code === 200) {
      projects.value = response.data || []
    }
  } catch (error) {
    console.error('加载项目列表失败:', error)
  }
}

// 加载心得数据
const loadRecords = async () => {
  try {
    const response = await request.get('/project/insight/list')
    if (response.code === 200) {
      records.value = (response.data || []).map((item: any) => ({
        ...item,
        tags: item.tags ? item.tags.split(',').filter((t: string) => t.trim()) : []
      }))
    }
  } catch (error) {
    console.error('加载项目心得失败:', error)
  }
}

// 获取项目名称
const getProjectName = (projectId: number) => {
  const project = projects.value.find(p => p.id === projectId)
  return project?.name || '未知项目'
}

// 格式化日期
const formatDate = (dateStr: string) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN', { 
    year: 'numeric', 
    month: '2-digit', 
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 标签操作
const addTag = () => {
  if (newTag.value.trim() && !recordForm.tags.includes(newTag.value.trim())) {
    recordForm.tags.push(newTag.value.trim())
    newTag.value = ''
  }
}

const removeTag = (index: number) => {
  recordForm.tags.splice(index, 1)
}

const addSuggestedTag = (tag: string) => {
  if (!recordForm.tags.includes(tag)) {
    recordForm.tags.push(tag)
  }
}

const addEditTag = () => {
  if (newEditTag.value.trim() && !editForm.tags.includes(newEditTag.value.trim())) {
    editForm.tags.push(newEditTag.value.trim())
    newEditTag.value = ''
  }
}

const removeEditTag = (index: number) => {
  editForm.tags.splice(index, 1)
}

// 编辑器操作
const insertTable = () => {
  editor.value?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

const insertImage = () => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editor.value?.chain().focus().setImage({ src: url }).run()
  }
}

const setLink = () => {
  const url = window.prompt('请输入链接地址:')
  if (url) {
    editor.value?.chain().focus().setLink({ href: url }).run()
  }
}

const insertTableEdit = () => {
  editEditor.value?.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()
}

const insertImageEdit = () => {
  const url = prompt('请输入图片URL:')
  if (url) {
    editEditor.value?.chain().focus().setImage({ src: url }).run()
  }
}

const setLinkEdit = () => {
  const url = window.prompt('请输入链接地址:')
  if (url) {
    editEditor.value?.chain().focus().setLink({ href: url }).run()
  }
}

// 提交新建心得
const submitRecord = async () => {
  if (!recordForm.projectId || !recordForm.title || !recordForm.summary) {
    alert('请填写完整信息')
    return
  }

  try {
    const response = await request.post('/project/insight', {
      projectId: parseInt(recordForm.projectId),
      title: recordForm.title,
      summary: recordForm.summary,
      content: recordForm.content,
      tags: recordForm.tags.join(',')
    })

    if (response.code === 200) {
      await loadRecords()
      closeCreateRecord()
      alert('项目心得已保存！')
    } else {
      alert('保存失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('保存项目心得失败:', error)
    alert('保存失败，请稍后重试')
  }
}

// 关闭新建弹窗
const closeCreateRecord = () => {
  showCreateRecord.value = false
  recordForm.projectId = ''
  recordForm.title = ''
  recordForm.summary = ''
  recordForm.content = ''
  recordForm.tags = []
  newTag.value = ''
  editor.value?.commands.setContent('')
}

// 编辑记录
const editRecord = (record: any) => {
  editForm.id = record.id
  editForm.projectId = record.projectId.toString()
  editForm.title = record.title
  editForm.summary = record.summary
  editForm.content = record.content
  editForm.tags = [...record.tags]
  
  editEditor.value?.commands.setContent(record.content || '')
  showEditRecord.value = true
}

// 提交编辑
const submitEditRecord = async () => {
  if (!editForm.projectId || !editForm.title || !editForm.summary) {
    alert('请填写完整信息')
    return
  }

  try {
    const response = await request.put(`/project/insight/${editForm.id}`, {
      projectId: parseInt(editForm.projectId),
      title: editForm.title,
      summary: editForm.summary,
      content: editForm.content,
      tags: editForm.tags.join(',')
    })

    if (response.code === 200) {
      await loadRecords()
      closeEditRecord()
      alert('项目心得已更新！')
    } else {
      alert('更新失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('更新项目心得失败:', error)
    alert('更新失败，请稍后重试')
  }
}

// 关闭编辑弹窗
const closeEditRecord = () => {
  showEditRecord.value = false
  editForm.projectId = ''
  editForm.title = ''
  editForm.summary = ''
  editForm.content = ''
  editForm.tags = []
  newEditTag.value = ''
  editEditor.value?.commands.setContent('')
}

// 删除记录
const deleteRecord = async (id: number) => {
  if (!confirm('确定要删除这条项目心得吗？此操作不可撤销。')) return

  try {
    const response = await request.delete(`/project/insight/${id}`)
    if (response.code === 200) {
      await loadRecords()
      alert('项目心得已删除！')
    } else {
      alert('删除失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('删除项目心得失败:', error)
    alert('删除失败，请稍后重试')
  }
}

// 初始化
onMounted(() => {
  loadProjects()
  loadRecords()
})

// 销毁编辑器
onBeforeUnmount(() => {
  editor.value?.destroy()
  editEditor.value?.destroy()
})
</script>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.3s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.modal-slide-enter-active,
.modal-slide-leave-active {
  transition: all 0.3s ease;
}

.modal-slide-enter-from,
.modal-slide-leave-to {
  opacity: 0;
  transform: scale(0.95);
}

/* Tiptap 编辑器样式 */
:deep(.ProseMirror) {
  min-height: 200px;
  outline: none;
}

:deep(.ProseMirror p.is-editor-empty:first-child::before) {
  content: attr(data-placeholder);
  float: left;
  color: #adb5bd;
  pointer-events: none;
  height: 0;
}

:deep(.ProseMirror table) {
  border-collapse: collapse;
  width: 100%;
  margin: 1rem 0;
}

:deep(.ProseMirror th),
:deep(.ProseMirror td) {
  border: 1px solid #d1d5db;
  padding: 0.5rem;
}

:deep(.ProseMirror th) {
  background-color: #f3f4f6;
  font-weight: bold;
}
</style>

