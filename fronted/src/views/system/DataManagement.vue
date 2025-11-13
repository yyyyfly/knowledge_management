<template>
  <div class="min-h-screen bg-gradient-to-br from-dark-800 via-dark-700 to-dark-800 p-6">
    <!-- 页面标题 -->
    <div class="mb-6">
      <div class="flex items-center gap-3 mb-4">
        <div class="p-3 bg-gradient-to-br from-blue-600 to-blue-500 rounded-xl shadow-lg">
          <i class="fas fa-database text-2xl text-white"></i>
        </div>
        <div>
          <h1 class="text-3xl font-bold text-gray-900">数据管理</h1>
          <p class="text-gray-700 text-sm mt-1">查看和管理系统数据库</p>
        </div>
      </div>
      <!-- 管理员提示框 -->
      <div class="bg-yellow-50 border-l-4 border-yellow-400 p-4 rounded-r-lg">
        <div class="flex items-center">
          <div class="flex-shrink-0">
            <i class="fas fa-shield-alt text-yellow-600 text-lg"></i>
          </div>
          <div class="ml-3">
            <p class="text-sm text-yellow-800 font-medium">
              管理员专属页面 - 只有admin用户可以查看此页面
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Tab切换 -->
    <div class="bg-white rounded-xl shadow-lg mb-6 border border-gray-200">
      <div class="flex border-b border-gray-200">
        <button 
          @click="activeTab = 'structure'"
          :class="[
            'px-8 py-4 font-medium transition-all relative group',
            activeTab === 'structure' 
              ? 'text-blue-600 bg-blue-50' 
              : 'text-gray-600 hover:text-blue-600 hover:bg-gray-50'
          ]"
        >
          <div class="flex items-center gap-2">
            <i class="fas fa-table"></i>
            <span>表结构管理</span>
          </div>
          <div v-if="activeTab === 'structure'" class="absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r from-blue-500 to-blue-400"></div>
        </button>
        <button 
          @click="activeTab = 'data'"
          :class="[
            'px-8 py-4 font-medium transition-all relative group',
            activeTab === 'data' 
              ? 'text-green-600 bg-green-50' 
              : 'text-gray-600 hover:text-green-600 hover:bg-gray-50'
          ]"
        >
          <div class="flex items-center gap-2">
            <i class="fas fa-database"></i>
            <span>数据管理</span>
          </div>
          <div v-if="activeTab === 'data'" class="absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r from-green-500 to-green-400"></div>
        </button>
      </div>
    </div>

    <!-- 表结构管理Tab -->
    <div v-show="activeTab === 'structure'" class="grid grid-cols-12 gap-6">
      <!-- 左侧表列表 -->
      <div class="col-span-2 bg-dark-700/80 backdrop-blur-sm rounded-xl shadow-2xl p-5 border border-dark-600/50">
        <div class="mb-4">
          <div class="relative">
            <input 
              v-model="tableSearchKeyword"
              type="text" 
              placeholder="搜索表名..."
              class="w-full pl-10 pr-4 py-2.5 bg-dark-600/80 text-white text-sm rounded-lg border border-dark-500/50 focus:outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-400/20 transition-all placeholder-dark-400"
            >
            <i class="fas fa-search absolute left-3 top-1/2 -translate-y-1/2 text-dark-300"></i>
          </div>
        </div>
        <button 
          @click="showCreateTableDialog = true"
          class="w-full mb-3 px-3 py-2 bg-gradient-to-r from-green-600 to-green-500 hover:from-green-700 hover:to-green-600 text-white text-sm rounded-lg transition-all shadow-lg hover:shadow-green-500/20 font-medium"
        >
          <i class="fas fa-plus mr-1.5 text-xs"></i>新建表
        </button>
        <div class="text-xs text-blue-300 mb-2 px-2 font-medium">
          <i class="fas fa-database text-xs mr-1"></i>共 {{ allTables.length }} 个表
        </div>
        <div class="space-y-1 max-h-[calc(100vh-360px)] overflow-y-auto custom-scrollbar">
          <div 
            v-for="table in filteredTables" 
            :key="table"
            @click="selectTable(table)"
            :class="[
              'px-3 py-2.5 rounded-lg cursor-pointer transition-all group text-sm',
              selectedTable === table 
                ? 'bg-gradient-to-r from-blue-600 to-blue-500 text-white shadow-lg' 
                : 'text-gray-700 hover:bg-gray-100 hover:text-gray-900'
            ]"
          >
            <div class="flex items-center gap-2">
              <i class="fas fa-table text-xs"></i>
              <span class="font-medium truncate">{{ table }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧表结构详情 -->
      <div class="col-span-10 bg-dark-700/80 backdrop-blur-sm rounded-xl shadow-2xl p-6 border border-dark-600/50">
        <div v-if="!selectedTable" class="text-center text-dark-300 py-32">
          <div class="inline-block p-8 bg-dark-600/30 rounded-full mb-6">
            <i class="fas fa-table text-6xl text-dark-400"></i>
          </div>
          <p class="text-xl font-medium text-gray-300">请从左侧选择一个表</p>
          <p class="text-base text-gray-400 mt-3">选择表后可查看结构和数据</p>
        </div>
        
        <div v-else>
           <!-- 表信息 -->
          <div class="mb-6 p-6 bg-gray-50 rounded-xl border border-gray-300 shadow-sm">
            <div class="flex justify-between items-start mb-4">
              <div class="flex-1">
                <div class="flex items-center gap-3 mb-3">
                  <div class="p-2.5 bg-blue-100 rounded-lg">
                    <i class="fas fa-table text-blue-600 text-lg"></i>
                  </div>
                  <h2 class="text-2xl font-bold text-gray-900">{{ selectedTable }}</h2>
                </div>
                <div v-if="tableInfo" class="grid grid-cols-2 gap-3 text-base">
                  <div class="flex items-center gap-2">
                    <i class="fas fa-cog text-blue-500 w-5"></i>
                    <span class="text-gray-800">引擎:</span>
                    <span class="text-gray-900 font-semibold">{{ tableInfo.engine }}</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <i class="fas fa-font text-purple-500 w-5"></i>
                    <span class="text-gray-800">字符集:</span>
                    <span class="text-gray-900 font-semibold">{{ tableInfo.collation }}</span>
                  </div>
                  <div v-if="tableInfo.comment" class="flex items-center gap-2 col-span-2">
                    <i class="fas fa-comment text-green-500 w-5"></i>
                    <span class="text-gray-800">注释:</span>
                    <span class="text-gray-900 font-semibold">{{ tableInfo.comment }}</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <i class="fas fa-list-ol text-orange-500 w-5"></i>
                    <span class="text-gray-800">记录数:</span>
                    <span class="text-gray-900 font-semibold">{{ tableInfo.tableRows }}</span>
                  </div>
                </div>
              </div>
              <div class="flex flex-wrap gap-2">
                <button 
                  @click="viewCreateSQL"
                  class="px-4 py-2.5 bg-gradient-to-r from-blue-600 to-blue-500 hover:from-blue-700 hover:to-blue-600 text-white rounded-lg text-sm transition-all shadow-lg hover:shadow-blue-500/20 font-medium"
                >
                  <i class="fas fa-code mr-1.5"></i>查看SQL
                </button>
                <button 
                  @click="showAddColumnDialog = true"
                  class="px-4 py-2.5 bg-gradient-to-r from-green-600 to-green-500 hover:from-green-700 hover:to-green-600 text-white rounded-lg text-sm transition-all shadow-lg hover:shadow-green-500/20 font-medium"
                >
                  <i class="fas fa-plus mr-1.5"></i>添加列
                </button>
                <button 
                  @click="showRenameTableDialog = true"
                  class="px-4 py-2.5 bg-gradient-to-r from-yellow-600 to-yellow-500 hover:from-yellow-700 hover:to-yellow-600 text-white rounded-lg text-sm transition-all shadow-lg hover:shadow-yellow-500/20 font-medium"
                >
                  <i class="fas fa-edit mr-1.5"></i>重命名
                </button>
                <button 
                  @click="confirmDropTable"
                  class="px-4 py-2.5 bg-gradient-to-r from-red-600 to-red-500 hover:from-red-700 hover:to-red-600 text-white rounded-lg text-sm transition-all shadow-lg hover:shadow-red-500/20 font-medium"
                >
                  <i class="fas fa-trash mr-1.5"></i>删除表
                </button>
              </div>
            </div>
          </div>

          <!-- 列列表 -->
          <div class="overflow-x-auto rounded-xl border border-dark-600/50">
            <table class="w-full">
              <thead>
                <tr class="bg-gradient-to-r from-dark-600 to-dark-600/50">
                  <th class="px-5 py-4 text-left text-sm font-semibold text-blue-300 uppercase tracking-wider">列名</th>
                  <th class="px-5 py-4 text-left text-sm font-semibold text-blue-300 uppercase tracking-wider">类型</th>
                  <th class="px-5 py-4 text-left text-sm font-semibold text-blue-300 uppercase tracking-wider">可空</th>
                  <th class="px-5 py-4 text-left text-sm font-semibold text-blue-300 uppercase tracking-wider">默认值</th>
                  <th class="px-5 py-4 text-left text-sm font-semibold text-blue-300 uppercase tracking-wider">键</th>
                  <th class="px-5 py-4 text-left text-sm font-semibold text-blue-300 uppercase tracking-wider">注释</th>
                  <th class="px-5 py-4 text-left text-sm font-semibold text-blue-300 uppercase tracking-wider">操作</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-dark-600/30">
                <tr v-for="column in tableStructure" :key="column.columnName" class="hover:bg-dark-600/30 transition-colors">
                   <td class="px-5 py-3.5">
                    <div class="flex items-center gap-2">
                      <i class="fas fa-columns text-purple-500 text-sm"></i>
                      <span class="text-gray-900 font-mono font-semibold text-base">{{ column.columnName }}</span>
                    </div>
                  </td>
                  <td class="px-5 py-3.5">
                    <span class="px-3 py-1.5 bg-blue-100 text-blue-800 rounded-full text-sm font-semibold border border-blue-300">
                      {{ column.columnType }}
                    </span>
                  </td>
                  <td class="px-5 py-3.5">
                    <span v-if="column.isNullable === 'YES'" class="px-3 py-1.5 bg-green-100 text-green-800 rounded-full text-sm font-semibold border border-green-300 flex items-center gap-1.5 w-fit">
                      <i class="fas fa-check text-xs"></i>
                      YES
                    </span>
                    <span v-else class="px-3 py-1.5 bg-red-100 text-red-800 rounded-full text-sm font-semibold border border-red-300 flex items-center gap-1.5 w-fit">
                      <i class="fas fa-times text-xs"></i>
                      NO
                    </span>
                  </td>
                  <td class="px-5 py-3.5 text-gray-800 font-mono text-base font-medium">{{ column.defaultValue || '-' }}</td>
                  <td class="px-5 py-3.5">
                    <span v-if="column.columnKey === 'PRI'" class="px-3 py-1.5 bg-yellow-100 text-yellow-800 rounded-full text-sm font-semibold border border-yellow-300 flex items-center gap-1.5 w-fit">
                      <i class="fas fa-key text-xs"></i>
                      主键
                    </span>
                    <span v-else-if="column.columnKey === 'UNI'" class="px-3 py-1.5 bg-blue-100 text-blue-800 rounded-full text-sm font-semibold border border-blue-300 flex items-center gap-1.5 w-fit">
                      <i class="fas fa-star text-xs"></i>
                      唯一
                    </span>
                    <span v-else-if="column.columnKey === 'MUL'" class="px-3 py-1.5 bg-purple-100 text-purple-800 rounded-full text-sm font-semibold border border-purple-300 flex items-center gap-1.5 w-fit">
                      <i class="fas fa-list text-xs"></i>
                      索引
                    </span>
                    <span v-else class="text-gray-600 text-base">-</span>
                  </td>
                  <td class="px-5 py-3.5 text-gray-800 text-base max-w-xs truncate font-medium" :title="column.comment">
                    {{ column.comment || '-' }}
                  </td>
                  <td class="px-5 py-3.5">
                    <div class="flex gap-3">
                      <button 
                        @click="modifyColumnDialog(column)"
                        class="text-blue-400 hover:text-blue-300 transition-colors text-base"
                        title="修改列"
                      >
                        <i class="fas fa-edit"></i>
                      </button>
                      <button 
                        @click="confirmDropColumn(column.columnName)"
                        class="text-red-400 hover:text-red-300 transition-colors text-base"
                        title="删除列"
                      >
                        <i class="fas fa-trash"></i>
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!-- 数据管理Tab -->
    <div v-show="activeTab === 'data'">
      <div class="bg-dark-700/80 backdrop-blur-sm rounded-xl shadow-2xl p-6 border border-dark-600/50">
        <!-- 顶部操作栏 -->
         <div class="flex gap-4 mb-6">
          <div class="flex-1 relative">
            <select 
              v-model="dataSelectedTable"
              @change="loadTableData"
              class="w-full pl-10 pr-4 py-3 bg-white text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-green-500 focus:ring-2 focus:ring-green-500/20 transition-all appearance-none cursor-pointer font-medium shadow-sm"
            >
              <option value="">选择表...</option>
              <option v-for="table in allTables" :key="table" :value="table">{{ table }}</option>
            </select>
            <i class="fas fa-table absolute left-3 top-1/2 -translate-y-1/2 text-gray-600"></i>
            <i class="fas fa-chevron-down absolute right-3 top-1/2 -translate-y-1/2 text-gray-600"></i>
          </div>
          <button 
            @click="loadTableData"
            :disabled="!dataSelectedTable"
            class="px-6 py-3 bg-gradient-to-r from-blue-600 to-blue-500 hover:from-blue-700 hover:to-blue-600 text-white rounded-lg transition-all shadow-lg hover:shadow-blue-500/20 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none font-medium"
          >
            <i class="fas fa-sync mr-2"></i>刷新
          </button>
          <button 
            @click="showAddRecordDialog = true"
            :disabled="!dataSelectedTable"
            class="px-6 py-3 bg-gradient-to-r from-green-600 to-green-500 hover:from-green-700 hover:to-green-600 text-white rounded-lg transition-all shadow-lg hover:shadow-green-500/20 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none font-medium"
          >
            <i class="fas fa-plus mr-2"></i>新增记录
          </button>
        </div>

         <!-- 表信息 -->
        <div v-if="dataSelectedTable" class="mb-6 p-5 bg-gray-50 rounded-xl border border-gray-300 shadow-sm">
          <div class="flex items-center gap-8 text-base">
            <div class="flex items-center gap-2">
              <i class="fas fa-table text-green-600 text-lg"></i>
              <span class="text-gray-800">当前表:</span>
              <span class="text-gray-900 font-semibold">{{ dataSelectedTable }}</span>
            </div>
            <div class="flex items-center gap-2">
              <i class="fas fa-list-ol text-blue-600 text-lg"></i>
              <span class="text-gray-800">总记录数:</span>
              <span class="text-gray-900 font-semibold">{{ dataTotal }}</span>
            </div>
            <div class="flex items-center gap-2">
              <i class="fas fa-columns text-purple-600 text-lg"></i>
              <span class="text-gray-800">列数:</span>
              <span class="text-gray-900 font-semibold">{{ dataTableStructure.length }}</span>
            </div>
          </div>
        </div>

        <!-- 数据表格 -->
        <div v-if="dataSelectedTable" class="overflow-x-auto mb-6 rounded-xl border border-dark-600/50">
          <table class="w-full">
              <thead>
                <tr class="bg-gradient-to-r from-dark-600 to-dark-600/50">
                  <th 
                    v-for="column in dataTableStructure" 
                    :key="column.columnName"
                    class="px-5 py-4 text-left text-sm font-semibold text-green-300 uppercase tracking-wider"
                  >
                    <div class="flex items-center gap-2">
                      <span>{{ column.columnName }}</span>
                      <i v-if="column.columnKey === 'PRI'" class="fas fa-key text-yellow-300 text-xs"></i>
                    </div>
                  </th>
                  <th class="px-5 py-4 text-left text-sm font-semibold text-green-300 uppercase tracking-wider">操作</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-dark-600/30">
                <tr v-for="(row, index) in dataList" :key="index" class="hover:bg-dark-600/30 transition-colors">
                   <td 
                    v-for="column in dataTableStructure" 
                    :key="column.columnName"
                    class="px-5 py-3.5 text-gray-900 max-w-xs truncate font-mono text-base font-medium"
                    :title="String(row[column.columnName])"
                  >
                    {{ formatCellValue(row[column.columnName]) }}
                  </td>
                  <td class="px-5 py-3.5">
                    <div class="flex gap-3">
                      <button 
                        @click="editRecord(row)"
                        class="text-blue-400 hover:text-blue-300 transition-colors text-base"
                        title="编辑记录"
                      >
                        <i class="fas fa-edit"></i>
                      </button>
                      <button 
                        @click="confirmDeleteRecord(row)"
                        class="text-red-400 hover:text-red-300 transition-colors text-base"
                        title="删除记录"
                      >
                        <i class="fas fa-trash"></i>
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
          </table>
        </div>

        <!-- 分页 -->
        <div v-if="dataSelectedTable && dataTotal > 0" class="flex justify-between items-center p-4 bg-gray-50 rounded-xl border border-gray-300">
          <div class="text-gray-800 text-sm flex items-center gap-2 font-medium">
            <i class="fas fa-info-circle text-blue-600"></i>
            显示 <span class="text-gray-900 font-semibold">{{ (dataPage - 1) * dataPageSize + 1 }}</span> - 
            <span class="text-gray-900 font-semibold">{{ Math.min(dataPage * dataPageSize, dataTotal) }}</span> 条，
            共 <span class="text-gray-900 font-semibold">{{ dataTotal }}</span> 条
          </div>
          <div class="flex gap-2 items-center">
            <button 
              @click="dataPage--; loadTableData()"
              :disabled="dataPage === 1"
              class="px-4 py-2 bg-white hover:bg-gray-100 text-gray-900 rounded-lg disabled:opacity-50 disabled:cursor-not-allowed transition-all border border-gray-300 font-medium"
            >
              <i class="fas fa-chevron-left mr-1"></i>上一页
            </button>
            <div class="px-4 py-2 bg-blue-100 text-blue-900 rounded-lg font-semibold border border-blue-300">
              {{ dataPage }} / {{ Math.ceil(dataTotal / dataPageSize) }}
            </div>
            <button 
              @click="dataPage++; loadTableData()"
              :disabled="dataPage >= Math.ceil(dataTotal / dataPageSize)"
              class="px-4 py-2 bg-white hover:bg-gray-100 text-gray-900 rounded-lg disabled:opacity-50 disabled:cursor-not-allowed transition-all border border-gray-300 font-medium"
            >
              下一页<i class="fas fa-chevron-right ml-1"></i>
            </button>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-if="!dataSelectedTable" class="text-center text-dark-300 py-32">
          <div class="inline-block p-8 bg-dark-600/30 rounded-full mb-6">
            <i class="fas fa-database text-6xl text-dark-400"></i>
          </div>
          <p class="text-xl font-medium text-gray-300">请选择一个表查看数据</p>
          <p class="text-base text-gray-400 mt-3">从上方下拉框选择表</p>
        </div>
      </div>
    </div>

    <!-- 创建表弹窗 -->
    <div v-if="showCreateTableDialog" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 backdrop-blur-sm">
      <div class="bg-white rounded-2xl shadow-2xl p-8 w-[900px] max-h-[85vh] flex flex-col">
        <!-- 标题 -->
        <div class="flex items-center justify-between mb-6 pb-4 border-b border-gray-200">
          <div class="flex items-center gap-3">
            <div class="p-2.5 bg-green-100 rounded-lg">
              <i class="fas fa-plus-circle text-green-600 text-xl"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900">创建新表</h3>
          </div>
          <button 
            @click="showCreateTableDialog = false; resetCreateTableForm()"
            class="text-gray-400 hover:text-gray-600 transition-colors"
          >
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>

        <!-- 表单内容 -->
        <div class="flex-1 overflow-y-auto pr-2 custom-scrollbar">
          <!-- 表基本信息 -->
          <div class="grid grid-cols-2 gap-4 mb-6">
            <div class="col-span-2">
              <label class="block text-sm font-semibold text-gray-900 mb-2">
                表名 <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="createTableForm.tableName"
                placeholder="例如: user_info"
                class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-green-500 focus:ring-2 focus:ring-green-500/20 font-mono"
              >
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-900 mb-2">存储引擎</label>
              <select 
                v-model="createTableForm.engine"
                class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-green-500 focus:ring-2 focus:ring-green-500/20"
              >
                <option value="InnoDB">InnoDB</option>
                <option value="MyISAM">MyISAM</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-900 mb-2">字符集</label>
              <select 
                v-model="createTableForm.charset"
                class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-green-500 focus:ring-2 focus:ring-green-500/20"
              >
                <option value="utf8mb4">utf8mb4</option>
                <option value="utf8">utf8</option>
                <option value="latin1">latin1</option>
              </select>
            </div>
            <div class="col-span-2">
              <label class="block text-sm font-semibold text-gray-900 mb-2">表注释</label>
              <input 
                v-model="createTableForm.comment"
                placeholder="表的用途说明"
                class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-green-500 focus:ring-2 focus:ring-green-500/20"
              >
            </div>
          </div>

          <!-- 列列表 -->
          <div class="mb-4">
            <div class="flex items-center justify-between mb-3">
              <label class="text-sm font-semibold text-gray-900">列定义</label>
              <button 
                @click="addColumnToCreateForm"
                class="px-3 py-1.5 bg-blue-100 hover:bg-blue-200 text-blue-700 rounded-lg text-sm font-medium transition-colors"
              >
                <i class="fas fa-plus mr-1"></i>添加列
              </button>
            </div>
            <div class="space-y-3 max-h-[300px] overflow-y-auto custom-scrollbar">
              <div 
                v-for="(column, index) in createTableForm.columns" 
                :key="index"
                class="p-4 bg-gray-50 rounded-lg border border-gray-300"
              >
                <div class="grid grid-cols-6 gap-3">
                  <div class="col-span-2">
                    <label class="block text-xs font-medium text-gray-700 mb-1">列名</label>
                    <input 
                      v-model="column.name"
                      placeholder="列名"
                      class="w-full px-3 py-2 bg-white text-gray-900 rounded border border-gray-300 focus:outline-none focus:border-blue-500 text-sm font-mono"
                    >
                  </div>
                  <div>
                    <label class="block text-xs font-medium text-gray-700 mb-1">类型</label>
                    <select 
                      v-model="column.type"
                      class="w-full px-3 py-2 bg-white text-gray-900 rounded border border-gray-300 focus:outline-none focus:border-blue-500 text-sm"
                    >
                      <option value="INT">INT</option>
                      <option value="BIGINT">BIGINT</option>
                      <option value="VARCHAR">VARCHAR</option>
                      <option value="TEXT">TEXT</option>
                      <option value="DATE">DATE</option>
                      <option value="DATETIME">DATETIME</option>
                      <option value="TIMESTAMP">TIMESTAMP</option>
                      <option value="DECIMAL">DECIMAL</option>
                    </select>
                  </div>
                  <div>
                    <label class="block text-xs font-medium text-gray-700 mb-1">长度</label>
                    <input 
                      v-model="column.length"
                      placeholder="长度"
                      :disabled="!['VARCHAR', 'CHAR', 'DECIMAL'].includes(column.type)"
                      class="w-full px-3 py-2 bg-white text-gray-900 rounded border border-gray-300 focus:outline-none focus:border-blue-500 text-sm font-mono disabled:bg-gray-100"
                    >
                  </div>
                  <div class="col-span-2">
                    <label class="block text-xs font-medium text-gray-700 mb-1">注释</label>
                    <input 
                      v-model="column.comment"
                      placeholder="说明"
                      class="w-full px-3 py-2 bg-white text-gray-900 rounded border border-gray-300 focus:outline-none focus:border-blue-500 text-sm"
                    >
                  </div>
                </div>
                <div class="flex items-center gap-4 mt-3">
                  <label class="flex items-center gap-1.5 cursor-pointer">
                    <input type="checkbox" v-model="column.nullable" class="rounded border-gray-300">
                    <span class="text-xs text-gray-700">可空</span>
                  </label>
                  <label class="flex items-center gap-1.5 cursor-pointer">
                    <input type="checkbox" v-model="column.isPrimaryKey" class="rounded border-gray-300">
                    <span class="text-xs text-gray-700">主键</span>
                  </label>
                  <label class="flex items-center gap-1.5 cursor-pointer">
                    <input 
                      type="checkbox" 
                      v-model="column.autoIncrement" 
                      :disabled="!column.isPrimaryKey"
                      class="rounded border-gray-300"
                    >
                    <span class="text-xs text-gray-700">自增</span>
                  </label>
                  <input 
                    v-model="column.defaultValue"
                    placeholder="默认值"
                    :disabled="column.autoIncrement"
                    class="flex-1 px-3 py-1.5 bg-white text-gray-900 rounded border border-gray-300 focus:outline-none focus:border-blue-500 text-xs font-mono disabled:bg-gray-100"
                  >
                  <button 
                    @click="removeColumnFromCreateForm(index)"
                    :disabled="createTableForm.columns.length === 1"
                    class="px-2 py-1.5 text-red-600 hover:bg-red-50 rounded transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                    title="删除列"
                  >
                    <i class="fas fa-trash text-sm"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 按钮 -->
        <div class="flex justify-end gap-3 mt-6 pt-6 border-t border-gray-200">
          <button 
            @click="showCreateTableDialog = false; resetCreateTableForm()"
            class="px-6 py-2.5 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg transition-all font-medium"
          >
            <i class="fas fa-times mr-2"></i>取消
          </button>
          <button 
            @click="handleCreateTable"
            class="px-6 py-2.5 bg-gradient-to-r from-green-600 to-green-500 hover:from-green-700 hover:to-green-600 text-white rounded-lg transition-all shadow-lg hover:shadow-green-500/30 font-medium"
          >
            <i class="fas fa-check mr-2"></i>创建
          </button>
        </div>
      </div>
    </div>

    <!-- 查看建表SQL弹窗 -->
    <div v-if="showCreateSQLDialog" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 backdrop-blur-sm">
      <div class="bg-white rounded-2xl shadow-2xl p-8 w-[850px] max-h-[85vh] flex flex-col">
        <!-- 标题 -->
        <div class="flex items-center justify-between mb-6 pb-4 border-b border-gray-200">
          <div class="flex items-center gap-3">
            <div class="p-2.5 bg-blue-100 rounded-lg">
              <i class="fas fa-code text-blue-600 text-xl"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900">建表语句</h3>
          </div>
          <button 
            @click="showCreateSQLDialog = false"
            class="text-gray-400 hover:text-gray-600 transition-colors"
          >
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>

        <!-- SQL显示 -->
        <div class="flex-1 overflow-hidden">
          <textarea 
            :value="viewCreateTableSQL"
            readonly
            rows="18"
            class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 font-mono text-sm resize-none"
          ></textarea>
        </div>

        <!-- 按钮 -->
        <div class="flex justify-end gap-3 mt-6 pt-6 border-t border-gray-200">
          <button 
            @click="showCreateSQLDialog = false"
            class="px-6 py-2.5 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg transition-all font-medium"
          >
            <i class="fas fa-times mr-2"></i>关闭
          </button>
        </div>
      </div>
    </div>

    <!-- 添加/修改列弹窗 -->
    <div v-if="showAddColumnDialog || showModifyColumnDialog" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 backdrop-blur-sm">
      <div class="bg-white rounded-2xl shadow-2xl p-8 w-[650px]">
        <!-- 标题 -->
        <div class="flex items-center justify-between mb-6 pb-4 border-b border-gray-200">
          <div class="flex items-center gap-3">
            <div class="p-2.5 bg-purple-100 rounded-lg">
              <i :class="showModifyColumnDialog ? 'fas fa-edit text-orange-600' : 'fas fa-plus text-purple-600'" class="text-xl"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900">{{ showModifyColumnDialog ? '修改列' : '添加列' }}</h3>
          </div>
          <button 
            @click="showAddColumnDialog = false; showModifyColumnDialog = false; resetAddColumnForm()"
            class="text-gray-400 hover:text-gray-600 transition-colors"
          >
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>

        <!-- 表单 -->
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-semibold text-gray-900 mb-2">
              列名 <span class="text-red-500">*</span>
            </label>
            <input 
              v-model="addColumnForm.name"
              placeholder="例如: user_name"
              class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 font-mono"
            >
          </div>
          
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-semibold text-gray-900 mb-2">数据类型</label>
              <select 
                v-model="addColumnForm.type"
                class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20"
              >
                <option value="INT">INT</option>
                <option value="BIGINT">BIGINT</option>
                <option value="VARCHAR">VARCHAR</option>
                <option value="CHAR">CHAR</option>
                <option value="TEXT">TEXT</option>
                <option value="LONGTEXT">LONGTEXT</option>
                <option value="DATE">DATE</option>
                <option value="DATETIME">DATETIME</option>
                <option value="TIMESTAMP">TIMESTAMP</option>
                <option value="DECIMAL">DECIMAL</option>
                <option value="FLOAT">FLOAT</option>
                <option value="DOUBLE">DOUBLE</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-900 mb-2">
                长度/精度
                <span v-if="!['VARCHAR', 'CHAR', 'DECIMAL', 'FLOAT', 'DOUBLE'].includes(addColumnForm.type)" class="text-xs text-gray-500">(此类型无需)</span>
              </label>
              <input 
                v-model="addColumnForm.length"
                placeholder="例如: 50"
                :disabled="!['VARCHAR', 'CHAR', 'DECIMAL', 'FLOAT', 'DOUBLE'].includes(addColumnForm.type)"
                class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 font-mono disabled:bg-gray-100"
              >
            </div>
          </div>

          <div>
            <label class="block text-sm font-semibold text-gray-900 mb-2">默认值</label>
            <input 
              v-model="addColumnForm.defaultValue"
              placeholder="留空表示无默认值，或输入 CURRENT_TIMESTAMP"
              class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 font-mono"
            >
          </div>

          <div>
            <label class="block text-sm font-semibold text-gray-900 mb-2">注释</label>
            <input 
              v-model="addColumnForm.comment"
              placeholder="列的用途说明"
              class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20"
            >
          </div>

          <div class="pt-2">
            <label class="flex items-center gap-2 cursor-pointer">
              <input 
                type="checkbox" 
                v-model="addColumnForm.nullable"
                class="w-4 h-4 rounded border-gray-300 text-purple-600 focus:ring-purple-500"
              >
              <span class="text-sm text-gray-900 font-medium">允许为空 (NULL)</span>
            </label>
          </div>
        </div>

        <!-- 按钮 -->
        <div class="flex justify-end gap-3 mt-6 pt-6 border-t border-gray-200">
          <button 
            @click="showAddColumnDialog = false; showModifyColumnDialog = false; resetAddColumnForm()"
            class="px-6 py-2.5 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg transition-all font-medium"
          >
            <i class="fas fa-times mr-2"></i>取消
          </button>
          <button 
            @click="showModifyColumnDialog ? handleModifyColumn() : handleAddColumn()"
            class="px-6 py-2.5 bg-gradient-to-r from-purple-600 to-purple-500 hover:from-purple-700 hover:to-purple-600 text-white rounded-lg transition-all shadow-lg hover:shadow-purple-500/30 font-medium"
          >
            <i class="fas fa-check mr-2"></i>确定
          </button>
        </div>
      </div>
    </div>

    <!-- 重命名表弹窗 -->
    <div v-if="showRenameTableDialog" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 backdrop-blur-sm">
      <div class="bg-white rounded-2xl shadow-2xl p-8 w-[550px]">
        <!-- 标题 -->
        <div class="flex items-center justify-between mb-6 pb-4 border-b border-gray-200">
          <div class="flex items-center gap-3">
            <div class="p-2.5 bg-yellow-100 rounded-lg">
              <i class="fas fa-edit text-yellow-600 text-xl"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900">重命名表</h3>
          </div>
          <button 
            @click="showRenameTableDialog = false"
            class="text-gray-400 hover:text-gray-600 transition-colors"
          >
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>

        <!-- 表单 -->
        <div class="space-y-5">
          <div>
            <label class="block text-sm font-semibold text-gray-900 mb-2">当前表名</label>
            <input 
              :value="selectedTable"
              readonly
              class="w-full px-4 py-3 bg-gray-100 text-gray-600 rounded-lg border border-gray-300 font-mono"
            >
          </div>
          <div>
            <label class="block text-sm font-semibold text-gray-900 mb-2">新表名</label>
            <input 
              v-model="newTableName"
              placeholder="输入新表名"
              class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-yellow-500 focus:ring-2 focus:ring-yellow-500/20 font-mono"
            >
          </div>
        </div>

        <!-- 按钮 -->
        <div class="flex justify-end gap-3 mt-6 pt-6 border-t border-gray-200">
          <button 
            @click="showRenameTableDialog = false"
            class="px-6 py-2.5 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg transition-all font-medium"
          >
            <i class="fas fa-times mr-2"></i>取消
          </button>
          <button 
            @click="handleRenameTable"
            class="px-6 py-2.5 bg-gradient-to-r from-yellow-600 to-yellow-500 hover:from-yellow-700 hover:to-yellow-600 text-white rounded-lg transition-all shadow-lg hover:shadow-yellow-500/30 font-medium"
          >
            <i class="fas fa-check mr-2"></i>重命名
          </button>
        </div>
      </div>
    </div>

    <!-- 新增/编辑记录弹窗 -->
    <div v-if="showAddRecordDialog || showEditRecordDialog" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 overflow-y-auto backdrop-blur-sm">
      <div class="bg-white rounded-2xl shadow-2xl p-8 w-[750px] my-8 max-h-[90vh] flex flex-col">
        <!-- 弹窗标题 -->
        <div class="flex items-center justify-between mb-6 pb-4 border-b border-gray-200">
          <div class="flex items-center gap-3">
            <div class="p-2.5 bg-green-100 rounded-lg">
              <i :class="showEditRecordDialog ? 'fas fa-edit text-orange-600' : 'fas fa-plus text-green-600'" class="text-xl"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900">{{ showEditRecordDialog ? '编辑记录' : '新增记录' }}</h3>
          </div>
          <button 
            @click="showAddRecordDialog = false; showEditRecordDialog = false"
            class="text-gray-400 hover:text-gray-600 transition-colors"
          >
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>

        <!-- 表单内容 -->
        <div class="space-y-5 overflow-y-auto flex-1 pr-2 custom-scrollbar">
          <div v-for="column in dataTableStructure" :key="column.columnName" class="group">
            <label class="block mb-2">
              <div class="flex items-center gap-2 mb-1">
                <span class="text-sm font-semibold text-gray-900">{{ column.columnName }}</span>
                <span v-if="column.columnKey === 'PRI'" class="px-2 py-0.5 bg-yellow-100 text-yellow-800 text-xs rounded-full font-medium border border-yellow-300">
                  <i class="fas fa-key text-xs mr-1"></i>主键
                </span>
              </div>
              <div class="flex items-center gap-2 text-xs text-gray-500">
                <span class="px-2 py-0.5 bg-blue-50 text-blue-700 rounded font-medium">{{ column.columnType }}</span>
                <span v-if="column.comment" class="text-gray-600">{{ column.comment }}</span>
              </div>
            </label>
            <textarea 
              v-if="isTextType(column.dataType)"
              v-model="recordFormData[column.columnName]"
              :placeholder="column.comment || column.columnName"
              rows="3"
              :disabled="showEditRecordDialog && column.columnKey === 'PRI'"
              class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-green-500 focus:ring-2 focus:ring-green-500/20 disabled:opacity-50 disabled:bg-gray-100 transition-all placeholder-gray-400 font-mono text-sm"
            ></textarea>
            <input 
              v-else
              v-model="recordFormData[column.columnName]"
              :type="getInputType(column.dataType)"
              :placeholder="column.comment || column.columnName"
              :disabled="showEditRecordDialog && column.columnKey === 'PRI'"
              class="w-full px-4 py-3 bg-gray-50 text-gray-900 rounded-lg border border-gray-300 focus:outline-none focus:border-green-500 focus:ring-2 focus:ring-green-500/20 disabled:opacity-50 disabled:bg-gray-100 transition-all placeholder-gray-400 font-mono"
            >
          </div>
        </div>

        <!-- 底部按钮 -->
        <div class="flex justify-end gap-3 mt-6 pt-6 border-t border-gray-200">
          <button 
            @click="showAddRecordDialog = false; showEditRecordDialog = false"
            class="px-6 py-2.5 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg transition-all font-medium"
          >
            <i class="fas fa-times mr-2"></i>取消
          </button>
          <button 
            @click="showEditRecordDialog ? handleUpdateRecord() : handleInsertRecord()"
            class="px-6 py-2.5 bg-gradient-to-r from-green-600 to-green-500 hover:from-green-700 hover:to-green-600 text-white rounded-lg transition-all shadow-lg hover:shadow-green-500/30 font-medium"
          >
            <i class="fas fa-save mr-2"></i>保存
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import * as dataManagementApi from '@/api/dataManagement'

// Tab状态
const activeTab = ref<'structure' | 'data'>('structure')

// 表结构管理相关
const allTables = ref<string[]>([])
const tableSearchKeyword = ref('')
const selectedTable = ref('')
const tableInfo = ref<any>(null)
const tableStructure = ref<any[]>([])

// 弹窗状态
const showCreateTableDialog = ref(false)
const showCreateSQLDialog = ref(false)
const showAddColumnDialog = ref(false)
const showModifyColumnDialog = ref(false)
const showRenameTableDialog = ref(false)

// 表单数据
const createTableSQL = ref('')
const viewCreateTableSQL = ref('')
const columnDefinition = ref('')
const newTableName = ref('')

// 创建表表单数据
const createTableForm = ref({
  tableName: '',
  engine: 'InnoDB',
  charset: 'utf8mb4',
  comment: '',
  columns: [
    {
      name: 'id',
      type: 'INT',
      length: '',
      nullable: false,
      defaultValue: '',
      isPrimaryKey: true,
      autoIncrement: true,
      comment: '主键ID'
    }
  ]
})

// 添加列表单数据
const addColumnForm = ref({
  name: '',
  type: 'VARCHAR',
  length: '50',
  nullable: true,
  defaultValue: '',
  comment: ''
})

// 数据管理相关
const dataSelectedTable = ref('')
const dataTableStructure = ref<any[]>([])
const dataList = ref<any[]>([])
const dataTotal = ref(0)
const dataPage = ref(1)
const dataPageSize = ref(20)
const dataPrimaryKey = ref('')

// 数据操作弹窗
const showAddRecordDialog = ref(false)
const showEditRecordDialog = ref(false)
const recordFormData = ref<Record<string, any>>({})

// 计算属性
const filteredTables = computed(() => {
  if (!tableSearchKeyword.value) return allTables.value
  return allTables.value.filter(table => 
    table.toLowerCase().includes(tableSearchKeyword.value.toLowerCase())
  )
})

// 初始化加载
onMounted(() => {
  loadAllTables()
})

// 加载所有表
async function loadAllTables() {
  try {
    const response = await dataManagementApi.getAllTables()
    if (response.code === 200) {
      allTables.value = response.data
    } else {
      alert('获取表列表失败: ' + response.message)
    }
  } catch (error: any) {
    alert('加载表列表失败: ' + (error.message || '请求失败'))
  }
}

// 选择表
async function selectTable(tableName: string) {
  selectedTable.value = tableName
  await loadTableStructure(tableName)
  await loadTableInfo(tableName)
}

// 加载表结构
async function loadTableStructure(tableName: string) {
  try {
    const response = await dataManagementApi.getTableStructure(tableName)
    if (response.code === 200) {
      tableStructure.value = response.data
    }
  } catch (error: any) {
    alert('加载表结构失败: ' + (error.message || '请求失败'))
  }
}

// 加载表信息
async function loadTableInfo(tableName: string) {
  try {
    const response = await dataManagementApi.getTableInfo(tableName)
    if (response.code === 200) {
      tableInfo.value = response.data
    }
  } catch (error: any) {
    console.error('加载表信息失败:', error)
  }
}

// 查看建表SQL
async function viewCreateSQL() {
  try {
    const response = await dataManagementApi.getCreateTableSQL(selectedTable.value)
    if (response.code === 200) {
      viewCreateTableSQL.value = response.data
      showCreateSQLDialog.value = true
    }
  } catch (error: any) {
    alert('获取建表语句失败: ' + (error.message || '请求失败'))
  }
}

// 创建表
async function handleCreateTable() {
  // 验证表名
  if (!createTableForm.value.tableName.trim()) {
    alert('请输入表名')
    return
  }
  
  // 验证至少有一列
  if (createTableForm.value.columns.length === 0) {
    alert('请至少添加一列')
    return
  }
  
  // 拼接SQL
  const sql = buildCreateTableSQL()
  
  try {
    const response = await dataManagementApi.createTable(sql)
    if (response.code === 200) {
      alert('表创建成功')
      showCreateTableDialog.value = false
      resetCreateTableForm()
      await loadAllTables()
    }
  } catch (error: any) {
    alert('创建表失败: ' + (error.message || '请求失败'))
  }
}

// 构建CREATE TABLE SQL
function buildCreateTableSQL(): string {
  const { tableName, engine, charset, comment, columns } = createTableForm.value
  
  let sql = `CREATE TABLE \`${tableName}\` (\n`
  
  // 添加列定义
  const columnDefs: string[] = []
  let primaryKeys: string[] = []
  
  columns.forEach(col => {
    let colDef = `  \`${col.name}\` ${col.type}`
    
    // 添加长度
    if (col.length && ['VARCHAR', 'CHAR', 'DECIMAL', 'FLOAT', 'DOUBLE'].includes(col.type)) {
      colDef += `(${col.length})`
    }
    
    // 可空性
    colDef += col.nullable ? ' NULL' : ' NOT NULL'
    
    // 自增
    if (col.autoIncrement && col.isPrimaryKey) {
      colDef += ' AUTO_INCREMENT'
    }
    
    // 默认值
    if (col.defaultValue && !col.autoIncrement) {
      if (['INT', 'BIGINT', 'DECIMAL', 'FLOAT', 'DOUBLE'].includes(col.type)) {
        colDef += ` DEFAULT ${col.defaultValue}`
      } else if (col.defaultValue.toUpperCase() === 'CURRENT_TIMESTAMP') {
        colDef += ` DEFAULT CURRENT_TIMESTAMP`
      } else {
        colDef += ` DEFAULT '${col.defaultValue}'`
      }
    }
    
    // 注释
    if (col.comment) {
      colDef += ` COMMENT '${col.comment}'`
    }
    
    columnDefs.push(colDef)
    
    if (col.isPrimaryKey) {
      primaryKeys.push(col.name)
    }
  })
  
  sql += columnDefs.join(',\n')
  
  // 添加主键约束
  if (primaryKeys.length > 0) {
    sql += `,\n  PRIMARY KEY (\`${primaryKeys.join('`, `')}\`)`
  }
  
  sql += `\n) ENGINE=${engine} DEFAULT CHARSET=${charset}`
  
  if (comment) {
    sql += ` COMMENT='${comment}'`
  }
  
  sql += ';'
  
  return sql
}

// 重置创建表表单
function resetCreateTableForm() {
  createTableForm.value = {
    tableName: '',
    engine: 'InnoDB',
    charset: 'utf8mb4',
    comment: '',
    columns: [
      {
        name: 'id',
        type: 'INT',
        length: '',
        nullable: false,
        defaultValue: '',
        isPrimaryKey: true,
        autoIncrement: true,
        comment: '主键ID'
      }
    ]
  }
}

// 添加新列到创建表单
function addColumnToCreateForm() {
  createTableForm.value.columns.push({
    name: '',
    type: 'VARCHAR',
    length: '50',
    nullable: true,
    defaultValue: '',
    isPrimaryKey: false,
    autoIncrement: false,
    comment: ''
  })
}

// 从创建表单中删除列
function removeColumnFromCreateForm(index: number) {
  createTableForm.value.columns.splice(index, 1)
}

// 删除表
function confirmDropTable() {
  const confirmText = prompt(`危险操作！删除表将无法恢复。\n请输入表名 "${selectedTable.value}" 以确认删除：`)
  if (confirmText === selectedTable.value) {
    handleDropTable()
  } else if (confirmText !== null) {
    alert('表名不匹配，操作已取消')
  }
}

async function handleDropTable() {
  try {
    const response = await dataManagementApi.dropTable(selectedTable.value)
    if (response.code === 200) {
      alert('表删除成功')
      selectedTable.value = ''
      tableStructure.value = []
      tableInfo.value = null
      await loadAllTables()
    }
  } catch (error: any) {
    alert('删除表失败: ' + (error.message || '请求失败'))
  }
}

// 添加列
async function handleAddColumn() {
  // 验证列名
  if (!addColumnForm.value.name.trim()) {
    alert('请输入列名')
    return
  }
  
  // 构建列定义SQL
  const colDef = buildColumnDefinition(addColumnForm.value)
  
  try {
    const response = await dataManagementApi.addColumn(selectedTable.value, colDef)
    if (response.code === 200) {
      alert('列添加成功')
      showAddColumnDialog.value = false
      resetAddColumnForm()
      await loadTableStructure(selectedTable.value)
    }
  } catch (error: any) {
    alert('添加列失败: ' + (error.message || '请求失败'))
  }
}

// 构建列定义SQL
function buildColumnDefinition(col: any): string {
  let colDef = `\`${col.name}\` ${col.type}`
  
  // 添加长度
  if (col.length && ['VARCHAR', 'CHAR', 'DECIMAL', 'FLOAT', 'DOUBLE'].includes(col.type)) {
    colDef += `(${col.length})`
  }
  
  // 可空性
  colDef += col.nullable ? ' NULL' : ' NOT NULL'
  
  // 默认值
  if (col.defaultValue) {
    if (['INT', 'BIGINT', 'DECIMAL', 'FLOAT', 'DOUBLE'].includes(col.type)) {
      colDef += ` DEFAULT ${col.defaultValue}`
    } else if (col.defaultValue.toUpperCase() === 'CURRENT_TIMESTAMP') {
      colDef += ` DEFAULT CURRENT_TIMESTAMP`
    } else {
      colDef += ` DEFAULT '${col.defaultValue}'`
    }
  }
  
  // 注释
  if (col.comment) {
    colDef += ` COMMENT '${col.comment}'`
  }
  
  return colDef
}

// 重置添加列表单
function resetAddColumnForm() {
  addColumnForm.value = {
    name: '',
    type: 'VARCHAR',
    length: '50',
    nullable: true,
    defaultValue: '',
    comment: ''
  }
}

// 修改列对话框
function modifyColumnDialog(column: any) {
  // 解析长度
  let length = ''
  const typeMatch = column.columnType.match(/^(\w+)(?:\((\d+)\))?/)
  if (typeMatch && typeMatch[2]) {
    length = typeMatch[2]
  }
  
  addColumnForm.value = {
    name: column.columnName,
    type: column.dataType.toUpperCase(),
    length: length,
    nullable: column.isNullable === 'YES',
    defaultValue: column.defaultValue || '',
    comment: column.comment || ''
  }
  showModifyColumnDialog.value = true
}

// 修改列
async function handleModifyColumn() {
  // 验证列名
  if (!addColumnForm.value.name.trim()) {
    alert('请输入列名')
    return
  }
  
  // 构建列定义SQL
  const colDef = buildColumnDefinition(addColumnForm.value)
  
  try {
    const response = await dataManagementApi.modifyColumn(selectedTable.value, colDef)
    if (response.code === 200) {
      alert('列修改成功')
      showModifyColumnDialog.value = false
      resetAddColumnForm()
      await loadTableStructure(selectedTable.value)
    }
  } catch (error: any) {
    alert('修改列失败: ' + (error.message || '请求失败'))
  }
}

// 删除列
function confirmDropColumn(columnName: string) {
  if (confirm(`确定要删除列 "${columnName}" 吗？此操作不可恢复！`)) {
    handleDropColumn(columnName)
  }
}

async function handleDropColumn(columnName: string) {
  try {
    const response = await dataManagementApi.dropColumn(selectedTable.value, columnName)
    if (response.code === 200) {
      alert('列删除成功')
      await loadTableStructure(selectedTable.value)
    }
  } catch (error: any) {
    alert('删除列失败: ' + (error.message || '请求失败'))
  }
}

// 重命名表
async function handleRenameTable() {
  if (!newTableName.value.trim()) {
    alert('请输入新表名')
    return
  }
  try {
    const response = await dataManagementApi.renameTable(selectedTable.value, newTableName.value)
    if (response.code === 200) {
      alert('表重命名成功')
      showRenameTableDialog.value = false
      selectedTable.value = newTableName.value
      newTableName.value = ''
      await loadAllTables()
    }
  } catch (error: any) {
    alert('重命名表失败: ' + (error.message || '请求失败'))
  }
}

// ========== 数据管理相关函数 ==========

// 加载表数据
async function loadTableData() {
  if (!dataSelectedTable.value) return
  
  try {
    // 加载表结构
    const structureResponse = await dataManagementApi.getTableStructure(dataSelectedTable.value)
    if (structureResponse.code === 200) {
      dataTableStructure.value = structureResponse.data
      // 找出主键
      const pkColumn = dataTableStructure.value.find(col => col.columnKey === 'PRI')
      dataPrimaryKey.value = pkColumn?.columnName || ''
    }
    
    // 加载数据
    const dataResponse = await dataManagementApi.queryTableData(dataSelectedTable.value, dataPage.value, dataPageSize.value)
    if (dataResponse.code === 200) {
      const result = dataResponse.data
      dataList.value = result.data
      dataTotal.value = result.total
    }
  } catch (error: any) {
    alert('加载表数据失败: ' + (error.message || '请求失败'))
  }
}

// 新增记录
function showAddRecordForm() {
  recordFormData.value = {}
  showAddRecordDialog.value = true
}

async function handleInsertRecord() {
  try {
    const response = await dataManagementApi.insertRecord(dataSelectedTable.value, recordFormData.value)
    if (response.code === 200) {
      alert('记录添加成功')
      showAddRecordDialog.value = false
      recordFormData.value = {}
      await loadTableData()
    }
  } catch (error: any) {
    alert('添加记录失败: ' + (error.message || '请求失败'))
  }
}

// 编辑记录
function editRecord(row: any) {
  recordFormData.value = { ...row }
  showEditRecordDialog.value = true
}

async function handleUpdateRecord() {
  try {
    const response = await dataManagementApi.updateRecord(dataSelectedTable.value, recordFormData.value)
    if (response.code === 200) {
      alert('记录更新成功')
      showEditRecordDialog.value = false
      recordFormData.value = {}
      await loadTableData()
    }
  } catch (error: any) {
    alert('更新记录失败: ' + (error.message || '请求失败'))
  }
}

// 删除记录
function confirmDeleteRecord(row: any) {
  if (!dataPrimaryKey.value) {
    alert('该表没有主键，无法删除')
    return
  }
  if (confirm('确定要删除这条记录吗？')) {
    handleDeleteRecord(row)
  }
}

async function handleDeleteRecord(row: any) {
  try {
    const pkValue = row[dataPrimaryKey.value]
    const response = await dataManagementApi.deleteRecord(dataSelectedTable.value, dataPrimaryKey.value, pkValue)
    if (response.code === 200) {
      alert('记录删除成功')
      await loadTableData()
    }
  } catch (error: any) {
    alert('删除记录失败: ' + (error.message || '请求失败'))
  }
}

// 工具函数
function formatCellValue(value: any): string {
  if (value === null || value === undefined) return '-'
  if (typeof value === 'object') return JSON.stringify(value)
  return String(value)
}

function isTextType(dataType: string): boolean {
  return ['text', 'longtext', 'mediumtext', 'json'].includes(dataType.toLowerCase())
}

function getInputType(dataType: string): string {
  dataType = dataType.toLowerCase()
  if (dataType.includes('int') || dataType.includes('decimal') || dataType.includes('float') || dataType.includes('double')) {
    return 'number'
  }
  if (dataType.includes('date') || dataType.includes('time')) {
    return 'datetime-local'
  }
  return 'text'
}
</script>

<style scoped>
/* 自定义滚动条样式 */
.custom-scrollbar::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 4px;
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background: rgba(96, 165, 250, 0.3);
  border-radius: 4px;
  transition: background 0.3s;
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background: rgba(96, 165, 250, 0.5);
}

/* 确保滚动条始终显示 */
.custom-scrollbar {
  overflow-y: auto;
  scrollbar-width: thin;
  scrollbar-color: rgba(96, 165, 250, 0.3) rgba(255, 255, 255, 0.05);
}
</style>

