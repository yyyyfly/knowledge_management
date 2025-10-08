# MaterialRecord.vue 配置管理补丁

## 说明
此补丁在 MaterialRecord.vue 中添加笔记配置管理功能。用户在此页面可以管理所有笔记类型的分类、主题、学科等配置项。

## 1. 已添加：UI部分（template）

已在"操作选项"之前添加"笔记分类标签管理"区域。

## 2. 需要添加：配置管理函数（script）

在 `onMounted` 之前添加以下函数：

```typescript
// 加载所有配置
const loadAllConfigs = async () => {
  try {
    // 加载所有笔记类型的配置
    const types = ['fragment', 'study', 'memorization', 'exercise', 'practical', 'framework']
    const promises = types.map(type => getConfigsByNoteType(type))
    const results = await Promise.all(promises)
    
    // 合并所有配置
    allConfigs.value = []
    results.forEach(res => {
      if (res.code === 200 && res.data) {
        allConfigs.value.push(...res.data)
      }
    })
    
    // 更新预设选项
    updatePresetsFromConfigs()
  } catch (error) {
    console.error('加载配置失败:', error)
  }
}

// 从配置更新预设选项
const updatePresetsFromConfigs = () => {
  // 碎片笔记
  const fragmentCategories = allConfigs.value
    .filter(c => c.noteType === 'fragment' && c.configType === 'category')
    .map(c => c.configName)
  const fragmentThemes = allConfigs.value
    .filter(c => c.noteType === 'fragment' && c.configType === 'theme')
    .map(c => c.configName)
  
  if (fragmentCategories.length > 0) {
    availableFragmentCategories.value = fragmentCategories
  }
  if (fragmentThemes.length > 0) {
    availableFragmentThemes.value = fragmentThemes
  }
  
  // 求学笔记
  const studySubjects = allConfigs.value
    .filter(c => c.noteType === 'study' && c.configType === 'subject')
    .map(c => c.configName)
  const studyCourses = allConfigs.value
    .filter(c => c.noteType === 'study' && c.configType === 'course')
    .map(c => c.configName)
  
  if (studySubjects.length > 0) {
    availableStudySubjects.value = studySubjects
  }
  if (studyCourses.length > 0) {
    availableCourses.value = studyCourses
  }
  
  // 其他笔记类型同理...
}

// 获取配置类型
const getConfigTypes = (noteType: string) => {
  return configTypeMap[noteType] || { type1: null, type2: null }
}

// 获取配置项
const getConfigItems = (noteType: string, configType: string) => {
  return allConfigs.value.filter(c => c.noteType === noteType && c.configType === configType)
}

// 打开添加配置弹窗
const openAddConfigModal = (noteType: string, configType: string) => {
  configDialogForm.noteType = noteType
  configDialogForm.configType = configType
  configDialogForm.configName = ''
  showAddConfigDialog.value = true
}

// 添加配置
const addConfigItem = async () => {
  if (!configDialogForm.configName.trim()) {
    alert('请输入配置名称')
    return
  }
  
  try {
    const res = await createConfig({
      noteType: configDialogForm.noteType,
      configType: configDialogForm.configType,
      configName: configDialogForm.configName.trim()
    })
    
    if (res.code === 200) {
      await loadAllConfigs()
      showAddConfigDialog.value = false
      alert('添加成功')
    } else {
      alert(res.message || '添加失败')
    }
  } catch (error: any) {
    console.error('添加配置失败:', error)
    alert(error.message || '添加失败')
  }
}

// 删除配置
const deleteConfigItem = async (id: number) => {
  if (!confirm('确定要删除这个配置吗？')) {
    return
  }
  
  try {
    const res = await deleteConfigAPI(id)
    if (res.code === 200) {
      await loadAllConfigs()
      alert('删除成功')
    } else {
      alert(res.message || '删除失败')
    }
  } catch (error: any) {
    console.error('删除配置失败:', error)
    alert(error.message || '删除失败')
  }
}
```

## 3. 需要添加：弹窗（template末尾）

在 `</template>` 之前添加：

```vue
    <!-- 添加配置弹窗 -->
    <Transition name="modal-fade">
      <div v-if="showAddConfigDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-xl shadow-2xl p-6 max-w-md w-full mx-4">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-xl font-bold text-gray-900">添加配置项</h3>
            <button 
              @click="showAddConfigDialog = false"
              class="text-gray-400 hover:text-gray-600 transition-colors"
            >
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="mb-6">
            <label class="block text-sm font-medium text-gray-700 mb-2">
              {{ getConfigTypes(configDialogForm.noteType)[configDialogForm.configType === getConfigTypes(configDialogForm.noteType).type1?.key ? 'type1' : 'type2']?.label }}名称
            </label>
            <input 
              v-model="configDialogForm.configName"
              type="text" 
              placeholder="输入配置名称"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              @keyup.enter="addConfigItem"
            />
          </div>

          <div class="flex items-center justify-end space-x-3">
            <button 
              @click="showAddConfigDialog = false"
              class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
            >
              取消
            </button>
            <button 
              @click="addConfigItem"
              class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
            >
              添加
            </button>
          </div>
        </div>
      </div>
    </Transition>
```

## 4. 需要添加：CSS样式

在 `<style scoped>` 中添加：

```css
/* 配置管理折叠动画 */
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
```

## 5. 需要修改：onMounted 函数

在 `onMounted` 中添加加载配置的调用：

```typescript
onMounted(() => {
  loadNotes()
  loadAllConfigs()  // 新增这一行
  loadProjectTemplates()
})

onActivated(() => {
  loadNotes()
  loadAllConfigs()  // 新增这一行
})
```

## 6. 需要定义：预设选项响应式变量

在变量定义区域添加：

```typescript
// 预设选项（从配置动态加载）
const availableFragmentCategories = ref<string[]>(defaultFragmentCategories)
const availableFragmentThemes = ref<string[]>(defaultFragmentThemes)
const availableStudySubjects = ref<string[]>(defaultStudySubjects)
const availableCourses = ref<string[]>(['在线课程', '大学课程', '培训班', '自学教程'])
const availableMemorizationSubjects = ref<string[]>(['面试题', 'Java核心', 'Spring框架', '数据库', '算法'])
const availableExerciseSubjects = ref<string[]>(defaultExerciseSubjects)
const availableExerciseSources = ref<string[]>(defaultExerciseSources)
```

## 7. 工作原理

1. **页面加载时**：调用 `loadAllConfigs()` 从后端加载所有配置
2. **配置加载后**：调用 `updatePresetsFromConfigs()` 更新各个预设选项
3. **新建笔记时**：预设按钮显示的是从配置加载的选项
4. **管理配置时**：用户在配置管理区域添加/删除配置项
5. **配置更新后**：自动刷新，新建笔记时即可使用新配置

## 8. 与原有功能的兼容

- ✅ **保留临时添加功能**：用户仍可在新建笔记时临时添加分类/主题
- ✅ **预设选项优先**：配置管理的选项会显示在预设按钮中
- ✅ **向后兼容**：如果配置为空，回退到默认选项（defaultFragmentCategories 等）

## 9. 部署步骤

1. UI部分已添加 ✅
2. 在script中添加上述函数
3. 在template末尾添加弹窗
4. 在style中添加CSS动画
5. 修改onMounted和onActivated
6. 添加预设选项变量
7. 重启前端服务

## 10. 测试验证

- [ ] 打开 http://localhost:5173/daily/material-record
- [ ] 看到"笔记分类标签管理"区域
- [ ] 展开后可切换不同笔记类型
- [ ] 可以添加/删除配置项
- [ ] 新建笔记时，预设选项包含新添加的配置
- [ ] 刷新页面后配置保留


