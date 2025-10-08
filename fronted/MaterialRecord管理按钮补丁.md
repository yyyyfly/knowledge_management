# MaterialRecord.vue 管理按钮补丁

## 需要添加管理按钮的位置

### ✅ 已添加
1. 碎片笔记 - 分类（line ~491）
2. 碎片笔记 - 主题（line ~558）
3. 求学笔记 - 学科（line ~797）

### 📝 待添加

#### 4. 求学笔记 - 知识点
查找：`<!-- 知识点 -->` 在 study 笔记部分
添加位置：`<label class="block text-sm font-medium text-gray-700 mb-2">知识点</label>` 改为：
```html
<div class="flex items-center justify-between mb-2">
  <label class="block text-sm font-medium text-gray-700">知识点</label>
  <button 
    type="button"
    @click="jumpToConfigManagement('study', 'knowledgePoint')"
    class="text-sm text-emerald-600 hover:text-emerald-800 flex items-center space-x-1"
  >
    <i class="fas fa-cog"></i>
    <span>管理</span>
  </button>
</div>
```

#### 5. 背诵笔记 - 学科项目（科目）
查找：`recordForm.type === 'memorization'`
添加管理按钮到"学科项目"字段

#### 6. 背诵笔记 - 知识点
添加管理按钮到"知识点"字段

#### 7. 刷题笔记 - 题目来源
查找：`recordForm.type === 'exercise'`
添加管理按钮到"题目来源"字段

#### 8. 刷题笔记 - 学科类型
添加管理按钮到"学科类型"字段

#### 9. 实战笔记 - 技术栈
查找：`recordForm.type === 'practical'`
添加管理按钮到"技术栈"字段

#### 10. 实战笔记 - 项目模板
如果有，添加管理按钮

#### 11. 框架笔记 - 学科类型（已有，需修改）
修改现有的管理按钮，从打开本地弹窗改为调用 `jumpToConfigManagement('framework', 'subjectType')`

#### 12. 框架笔记 - 知识点类型（已有，需修改）
修改现有的管理按钮，从打开本地弹窗改为调用 `jumpToConfigManagement('framework', 'knowledgePoint')`

## jumpToConfigManagement 函数已添加 ✅

位置：在 `deleteConfigItem` 函数后面

功能：
- 关闭新建笔记表单
- 展开配置管理区域
- 自动选择对应的笔记类型
- 滚动到配置管理区域


