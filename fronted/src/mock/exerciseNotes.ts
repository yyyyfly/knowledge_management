// 生成当前北京时间往前推6个月的时间范围
const generateTimeRange = () => {
  const now = new Date();
  // 获取北京时间（UTC+8）
  const beijingTime = new Date(now.getTime() + 8 * 60 * 60 * 1000);
  
  // 往前推6个月
  const sixMonthsAgo = new Date(beijingTime);
  sixMonthsAgo.setMonth(sixMonthsAgo.getMonth() - 6);
  
  return {
    start: sixMonthsAgo,
    end: beijingTime
  };
};

// 生成指定时间范围内的随机时间
const generateRandomTime = (start: Date, end: Date): string => {
  const randomTime = new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
  const year = randomTime.getFullYear();
  const month = String(randomTime.getMonth() + 1).padStart(2, '0');
  const day = String(randomTime.getDate()).padStart(2, '0');
  const hours = String(randomTime.getHours()).padStart(2, '0');
  const minutes = String(randomTime.getMinutes()).padStart(2, '0');
  
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

// 获取时间范围
const timeRange = generateTimeRange();

// 刷题笔记类型定义
export interface ExerciseNote {
  id: number
  type: 'exercise'
  title: string
  summary: string
  content: string
  tags: string[]
  createTime: string
  problemName: string
  exerciseSource: string       // 题目来源 - 单标签
  exerciseSubject: string      // 学科类型 - 单标签
  exerciseKnowledge: string[]  // 知识点类型 - 多标签
  accuracy: number
}

export const exerciseNotes: ExerciseNote[] = [
  {
    id: 25,
    type: 'exercise',
    title: 'LeetCode 1: 两数之和',
    summary: '使用哈希表解决两数之和问题，时间复杂度O(n)',
    content: '<h2>两数之和解题思路</h2><p>给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数。</p><h3>解题思路</h3><ul><li>使用哈希表存储已遍历的数字</li><li>对于当前数字，检查target-num是否在哈希表中</li><li>如果在，返回两个数字的下标</li><li>如果不在，将当前数字加入哈希表</li></ul><h3>代码实现</h3><pre><code>function twoSum(nums: number[], target: number): number[] {\n  const map = new Map();\n  for (let i = 0; i < nums.length; i++) {\n    const complement = target - nums[i];\n    if (map.has(complement)) {\n      return [map.get(complement), i];\n    }\n    map.set(nums[i], i);\n  }\n  return [];\n}</code></pre>',
    tags: ['LeetCode', '哈希表', '数组', '简单'],
    problemName: '两数之和',
    exerciseSource: 'LeetCode',
    exerciseSubject: '算法',
    exerciseKnowledge: ['哈希表', '数组遍历'],
    accuracy: 95,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 26,
    type: 'exercise',
    title: 'LeetCode 2: 两数相加',
    summary: '模拟链表加法，处理进位问题',
    content: '<h2>两数相加解题思路</h2><p>给你两个非空的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。</p><h3>解题思路</h3><ul><li>同时遍历两个链表</li><li>逐位相加，处理进位</li><li>创建新节点存储结果</li><li>注意处理链表长度不同的情况</li></ul><h3>关键点</h3><ul><li>进位处理</li><li>链表遍历</li><li>边界条件</li></ul>',
    tags: ['LeetCode', '链表', '数学', '中等'],
    problemName: '两数相加',
    exerciseSource: 'LeetCode',
    exerciseSubject: '数据结构',
    exerciseKnowledge: ['链表操作', '数学运算'],
    accuracy: 88,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 27,
    type: 'exercise',
    title: 'LeetCode 3: 无重复字符的最长子串',
    summary: '使用滑动窗口解决最长子串问题',
    content: '<h2>无重复字符的最长子串解题思路</h2><p>给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。</p><h3>解题思路</h3><ul><li>使用滑动窗口</li><li>维护一个字符集合</li><li>遇到重复字符时收缩窗口</li><li>记录最大长度</li></ul><h3>时间复杂度</h3><ul><li>时间复杂度：O(n)</li><li>空间复杂度：O(min(m,n))</li></ul>',
    tags: ['LeetCode', '滑动窗口', '字符串', '中等'],
    problemName: '无重复字符的最长子串',
    exerciseSource: 'LeetCode',
    exerciseSubject: '字符串',
    exerciseKnowledge: ['滑动窗口', '字符串处理'],
    accuracy: 92,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 28,
    type: 'exercise',
    title: 'LeetCode 4: 寻找两个正序数组的中位数',
    summary: '使用二分查找解决中位数问题',
    content: '<h2>寻找两个正序数组的中位数解题思路</h2><p>给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。</p><h3>解题思路</h3><ul><li>使用二分查找</li><li>分割两个数组</li><li>确保左边元素数量等于右边</li><li>处理边界情况</li></ul><h3>复杂度分析</h3><ul><li>时间复杂度：O(log(min(m,n)))</li><li>空间复杂度：O(1)</li></ul>',
    tags: ['LeetCode', '二分查找', '数组', '困难'],
    problemName: '寻找两个正序数组的中位数',
    exerciseSource: 'LeetCode',
    exerciseSubject: '算法',
    exerciseKnowledge: ['二分查找', '数组分割'],
    accuracy: 75,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 29,
    type: 'exercise',
    title: 'LeetCode 15: 三数之和',
    summary: '使用双指针解决三数之和问题',
    content: '<h2>三数之和解题思路</h2><p>给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。</p><h3>解题思路</h3><ul><li>先对数组排序</li><li>固定一个数，用双指针找另外两个数</li><li>注意去重处理</li><li>时间复杂度 O(n²)</li></ul>',
    tags: ['LeetCode', '双指针', '数组', '中等'],
    problemName: '三数之和',
    exerciseSource: 'LeetCode',
    exerciseSubject: '算法',
    exerciseKnowledge: ['双指针', '排序'],
    accuracy: 85,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 30,
    type: 'exercise',
    title: 'LeetCode 20: 有效的括号',
    summary: '使用栈结构验证括号匹配',
    content: '<h2>有效的括号解题思路</h2><p>给定一个只包括 \'(\'，\')\'，\'[\'，\']\'，\'{\'，\'}\' 的字符串 s ，判断字符串是否有效。</p><h3>解题思路</h3><ul><li>使用栈存储左括号</li><li>遇到右括号时检查栈顶</li><li>匹配则出栈，不匹配返回false</li><li>最后检查栈是否为空</li></ul>',
    tags: ['LeetCode', '栈', '字符串', '简单'],
    problemName: '有效的括号',
    exerciseSource: 'LeetCode',
    exerciseSubject: '数据结构',
    exerciseKnowledge: ['栈', '字符串匹配'],
    accuracy: 98,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 31,
    type: 'exercise',
    title: 'LeetCode 21: 合并两个有序链表',
    summary: '递归或迭代合并有序链表',
    content: '<h2>合并两个有序链表解题思路</h2><p>将两个升序链表合并为一个新的升序链表并返回。</p><h3>解题思路</h3><ul><li>方法一：递归</li><li>方法二：迭代</li><li>比较两个链表头节点</li><li>选择较小的节点</li></ul>',
    tags: ['LeetCode', '链表', '递归', '简单'],
    problemName: '合并两个有序链表',
    exerciseSource: 'LeetCode',
    exerciseSubject: '数据结构',
    exerciseKnowledge: ['链表', '递归'],
    accuracy: 94,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 32,
    type: 'exercise',
    title: 'LeetCode 53: 最大子数组和',
    summary: '动态规划解决最大子数组和问题',
    content: '<h2>最大子数组和解题思路</h2><p>给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。</p><h3>解题思路</h3><ul><li>动态规划思想</li><li>当前位置最大和 = max(当前元素, 前一位置最大和 + 当前元素)</li><li>维护全局最大值</li></ul>',
    tags: ['LeetCode', '动态规划', '数组', '中等'],
    problemName: '最大子数组和',
    exerciseSource: 'LeetCode',
    exerciseSubject: '动态规划',
    exerciseKnowledge: ['动态规划', '数组'],
    accuracy: 89,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 33,
    type: 'exercise',
    title: 'LeetCode 70: 爬楼梯',
    summary: '斐波那契数列的经典应用',
    content: '<h2>爬楼梯解题思路</h2><p>假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？</p><h3>解题思路</h3><ul><li>动态规划</li><li>状态转移方程：f(n) = f(n-1) + f(n-2)</li><li>实际上就是斐波那契数列</li></ul>',
    tags: ['LeetCode', '动态规划', '数学', '简单'],
    problemName: '爬楼梯',
    exerciseSource: 'LeetCode',
    exerciseSubject: '动态规划',
    exerciseKnowledge: ['动态规划', '斐波那契'],
    accuracy: 96,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 34,
    type: 'exercise',
    title: 'LeetCode 121: 买卖股票的最佳时机',
    summary: '一次遍历找到最大利润',
    content: '<h2>买卖股票的最佳时机解题思路</h2><p>给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。你只能选择某一天买入这只股票，并选择在未来的某一天卖出该股票。</p><h3>解题思路</h3><ul><li>维护历史最低价格</li><li>计算当前价格与最低价格的差值</li><li>更新最大利润</li></ul>',
    tags: ['LeetCode', '贪心', '数组', '简单'],
    problemName: '买卖股票的最佳时机',
    exerciseSource: 'LeetCode',
    exerciseSubject: '贪心算法',
    exerciseKnowledge: ['贪心算法', '数组遍历'],
    accuracy: 91,
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  }
];

// 获取所有刷题笔记
export const getAllExerciseNotes = (): ExerciseNote[] => {
  return exerciseNotes;
};

// 根据ID获取刷题笔记
export const getExerciseNoteById = (id: number): ExerciseNote | undefined => {
  return exerciseNotes.find(note => note.id === id);
};

// 搜索刷题笔记
export const searchExerciseNotes = (query: string): ExerciseNote[] => {
  const lowerQuery = query.toLowerCase();
  return exerciseNotes.filter(note => 
    note.title.toLowerCase().includes(lowerQuery) ||
    note.summary.toLowerCase().includes(lowerQuery) ||
    note.tags.some(tag => tag.toLowerCase().includes(lowerQuery))
  );
};

// 根据标签获取刷题笔记
export const getExerciseNotesByTag = (tag: string): ExerciseNote[] => {
  return exerciseNotes.filter(note => note.tags.includes(tag));
};

// 获取刷题笔记统计信息
export const getExerciseNotesStats = () => {
  return {
    total: exerciseNotes.length,
    byAccuracy: {
      high: exerciseNotes.filter(n => n.accuracy >= 90).length,
      medium: exerciseNotes.filter(n => n.accuracy >= 80 && n.accuracy < 90).length,
      low: exerciseNotes.filter(n => n.accuracy < 80).length
    }
  };
}; 