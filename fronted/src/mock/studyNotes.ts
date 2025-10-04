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

// 求学笔记类型定义
export interface StudyNote {
  id: number
  type: 'study'
  title: string
  summary: string
  content: string
  tags: string[]
  createTime: string
  course: string[]
  studySubject: string[]
  bookName?: string
  bookSubject?: string[]
}

export const studyNotes: StudyNote[] = [
  {
    id: 13,
    type: 'study',
    title: '《算法导论》动态规划学习笔记',
    summary: '动态规划是一种通过把原问题分解为相对简单的子问题的方式求解复杂问题的方法。',
    content: '<h2>动态规划基本概念</h2><p>动态规划(Dynamic Programming, DP)是一种在数学、管理科学、计算机科学、经济学和生物信息学中使用的，通过把原问题分解为相对简单的子问题的方式求解复杂问题的方法。</p><h3>基本特征</h3><ul><li><strong>最优子结构</strong>：问题的最优解包含子问题的最优解</li><li><strong>重叠子问题</strong>：递归算法反复求解相同的子问题</li><li><strong>无后效性</strong>：某阶段状态一旦确定，就不受这个状态以后决策的影响</li></ul><h3>经典问题</h3><p>1. <strong>斐波那契数列</strong></p><pre><code>function fibonacci(n) {\n  const dp = [0, 1];\n  for (let i = 2; i <= n; i++) {\n    dp[i] = dp[i-1] + dp[i-2];\n  }\n  return dp[n];\n}</code></pre><p>2. <strong>最长公共子序列</strong></p><p>3. <strong>0-1背包问题</strong></p><h3>学习心得</h3><p>动态规划的关键在于找到状态转移方程，明确边界条件。通过表格法可以更好地理解DP的执行过程。</p>',
    tags: ['算法', '动态规划', '数据结构', '计算机科学'],
    course: ['算法设计'],
    studySubject: ['计算机科学'],
    bookName: '算法导论',
    bookSubject: ['算法', '计算机科学'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 14,
    type: 'study',
    title: '《深入理解计算机系统》内存管理学习',
    summary: '学习计算机系统中内存管理的基本原理，包括虚拟内存、分页机制等核心概念。',
    content: '<h2>内存管理基础</h2><p>内存管理是操作系统的核心功能之一，负责管理计算机的主存储器资源。</p><h3>虚拟内存</h3><p>虚拟内存是计算机系统内存管理的一种技术，它使得应用程序认为它拥有连续可用的内存空间。</p><h4>主要特点：</h4><ul><li>程序可以使用比物理内存更大的地址空间</li><li>多个程序可以同时运行而不互相干扰</li><li>提供内存保护机制</li></ul><h3>分页机制</h3><p>分页是虚拟内存管理中的一种技术，将虚拟内存和物理内存分割成固定大小的块。</p><pre><code>// 虚拟地址到物理地址的转换过程\nvirtual_address = page_number + offset\nphysical_address = frame_number + offset</code></pre><h3>内存分配算法</h3><ol><li><strong>首次适应</strong> - 分配第一个足够大的空闲块</li><li><strong>最佳适应</strong> - 分配最小的足够大的空闲块</li><li><strong>最坏适应</strong> - 分配最大的空闲块</li></ol>',
    tags: ['计算机系统', '内存管理', '操作系统', '虚拟内存'],
    course: ['计算机系统基础'],
    studySubject: ['计算机科学'],
    bookName: '深入理解计算机系统',
    bookSubject: ['计算机系统', '操作系统'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 15,
    type: 'study',
    title: '《设计模式》观察者模式实践',
    summary: '深入学习观察者模式的实现原理和实际应用场景，理解发布-订阅模式的核心思想。',
    content: '<h2>观察者模式概述</h2><p>观察者模式定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。</p><h3>模式结构</h3><ul><li><strong>Subject（主题）</strong>：被观察的对象</li><li><strong>Observer（观察者）</strong>：观察主题的对象</li><li><strong>ConcreteSubject（具体主题）</strong>：具体的被观察对象</li><li><strong>ConcreteObserver（具体观察者）</strong>：具体的观察者对象</li></ul><h3>代码实现</h3><pre><code>// TypeScript实现\ninterface Observer {\n  update(data: any): void;\n}\n\nclass Subject {\n  private observers: Observer[] = [];\n  \n  addObserver(observer: Observer): void {\n    this.observers.push(observer);\n  }\n  \n  removeObserver(observer: Observer): void {\n    const index = this.observers.indexOf(observer);\n    if (index > -1) {\n      this.observers.splice(index, 1);\n    }\n  }\n  \n  notifyObservers(data: any): void {\n    this.observers.forEach(observer => {\n      observer.update(data);\n    });\n  }\n}</code></pre><h3>应用场景</h3><p>1. 事件处理系统<br>2. 模型-视图架构<br>3. 数据绑定机制</p>',
    tags: ['设计模式', '软件架构', '面向对象', 'TypeScript'],
    course: ['软件工程'],
    studySubject: ['软件工程'],
    bookName: '设计模式',
    bookSubject: ['软件设计', '面向对象'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 16,
    type: 'study',
    title: 'JavaScript闭包机制深度解析',
    summary: '理解JavaScript中闭包的工作原理，掌握闭包在实际开发中的应用技巧和注意事项。',
    content: '<h2>闭包基本概念</h2><p>闭包是指有权访问另一个函数作用域中变量的函数。创建闭包的常见方式是在一个函数内部创建另一个函数。</p><h3>闭包的形成条件</h3><ol><li>函数嵌套</li><li>内部函数引用外部函数的变量</li><li>外部函数被调用</li></ol><h3>代码示例</h3><pre><code>function outerFunction(x) {\n  // 外部函数的变量\n  let outerVariable = x;\n  \n  // 内部函数（闭包）\n  function innerFunction(y) {\n    console.log(outerVariable + y);\n  }\n  \n  return innerFunction;\n}\n\nconst myClosure = outerFunction(10);\nmyClosure(5); // 输出: 15</code></pre><h3>闭包的作用</h3><ul><li><strong>数据封装</strong>：创建私有变量</li><li><strong>回调函数</strong>：保持状态</li><li><strong>模块模式</strong>：创建独立的作用域</li></ul><h3>实际应用</h3><p>1. <strong>计数器</strong></p><pre><code>function createCounter() {\n  let count = 0;\n  return function() {\n    return ++count;\n  };\n}\n\nconst counter = createCounter();\nconsole.log(counter()); // 1\nconsole.log(counter()); // 2</code></pre><p>2. <strong>事件处理</strong></p><p>3. <strong>模块化开发</strong></p><h3>注意事项</h3><p>• 内存泄漏风险<br>• 性能影响<br>• 循环中的闭包陷阱</p>',
    tags: ['JavaScript', '闭包', '函数式编程', 'Web开发'],
    course: ['前端开发'],
    studySubject: ['Web开发'],
    bookName: 'JavaScript高级程序设计',
    bookSubject: ['JavaScript', 'Web开发'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 17,
    type: 'study',
    title: '数据库索引优化策略研究',
    summary: '深入研究数据库索引的工作原理，学习不同类型索引的适用场景和优化技巧。',
    content: '<h2>数据库索引概述</h2><p>数据库索引是一种数据结构，它提高了数据库表数据检索操作的速度，但会降低数据更新操作的速度。</p><h3>索引类型</h3><h4>1. B树索引</h4><p>最常见的索引类型，适用于等值查询和范围查询。</p><h4>2. 哈希索引</h4><p>适用于等值查询，但不支持范围查询。</p><h4>3. 位图索引</h4><p>适用于低基数列，在数据仓库环境中较为常见。</p><h3>索引设计原则</h3><ul><li><strong>选择性原则</strong>：选择具有高选择性的列</li><li><strong>最左前缀原则</strong>：复合索引的使用规则</li><li><strong>覆盖索引</strong>：减少回表操作</li></ul><h3>查询优化实例</h3><pre><code>-- 创建复合索引\nCREATE INDEX idx_user_status_create_time \nON users(status, create_time);\n\n-- 优化前\nSELECT * FROM users \nWHERE status = \'active\' \nAND create_time > \'2023-01-01\';\n\n-- 使用覆盖索引\nSELECT id, username, status \nFROM users \nWHERE status = \'active\' \nAND create_time > \'2023-01-01\';</code></pre><h3>性能监控</h3><p>• 使用EXPLAIN分析执行计划<br>• 监控索引使用率<br>• 定期维护索引统计信息</p>',
    tags: ['数据库', '索引优化', 'SQL', '性能调优'],
    course: ['数据库系统'],
    studySubject: ['数据库'],
    bookName: '高性能MySQL',
    bookSubject: ['数据库', 'MySQL'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 18,
    type: 'study',
    title: 'React Hooks深入理解与实践',
    summary: '全面学习React Hooks的使用方法，理解函数式组件的状态管理和生命周期处理。',
    content: '<h2>React Hooks 概述</h2><p>React Hooks 是 React 16.8 引入的新特性，它可以让你在不编写 class 的情况下使用 state 以及其他的 React 特性。</p><h3>基础 Hooks</h3><h4>1. useState</h4><pre><code>import React, { useState } from \'react\';\n\nfunction Counter() {\n  const [count, setCount] = useState(0);\n  \n  return (\n    <div>\n      <p>You clicked {count} times</p>\n      <button onClick={() => setCount(count + 1)}>\n        Click me\n      </button>\n    </div>\n  );\n}</code></pre><h4>2. useEffect</h4><pre><code>import React, { useState, useEffect } from \'react\';\n\nfunction Timer() {\n  const [seconds, setSeconds] = useState(0);\n  \n  useEffect(() => {\n    const interval = setInterval(() => {\n      setSeconds(seconds => seconds + 1);\n    }, 1000);\n    \n    return () => clearInterval(interval);\n  }, []);\n  \n  return <div>Seconds: {seconds}</div>;\n}</code></pre><h3>自定义 Hooks</h3><pre><code>// 自定义 Hook：useLocalStorage\nfunction useLocalStorage(key, initialValue) {\n  const [storedValue, setStoredValue] = useState(() => {\n    try {\n      const item = window.localStorage.getItem(key);\n      return item ? JSON.parse(item) : initialValue;\n    } catch (error) {\n      return initialValue;\n    }\n  });\n  \n  const setValue = (value) => {\n    try {\n      setStoredValue(value);\n      window.localStorage.setItem(key, JSON.stringify(value));\n    } catch (error) {\n      console.error(error);\n    }\n  };\n  \n  return [storedValue, setValue];\n}</code></pre><h3>性能优化</h3><p>• 使用 useMemo 缓存计算结果<br>• 使用 useCallback 缓存函数<br>• 合理使用依赖数组</p>',
    tags: ['React', 'Hooks', '前端开发', 'JavaScript'],
    course: ['前端框架'],
    studySubject: ['Web开发'],
    bookName: 'React官方文档',
    bookSubject: ['React', '前端开发'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 19,
    type: 'study',
    title: '机器学习线性回归算法实现',
    summary: '从数学原理到代码实现，全面掌握线性回归算法的核心概念和应用技巧。',
    content: '<h2>线性回归基础理论</h2><p>线性回归是机器学习中最基础的算法之一，用于建立因变量与自变量之间的线性关系模型。</p><h3>数学模型</h3><p>对于单变量线性回归：</p><p><strong>h(x) = θ₀ + θ₁x</strong></p><p>对于多变量线性回归：</p><p><strong>h(x) = θ₀ + θ₁x₁ + θ₂x₂ + ... + θₙxₙ</strong></p><h3>损失函数</h3><p>使用均方误差（MSE）作为损失函数：</p><p><strong>J(θ) = 1/2m ∑(h(x⁽ⁱ⁾) - y⁽ⁱ⁾)²</strong></p><h3>梯度下降算法</h3><pre><code># Python实现\nimport numpy as np\n\nclass LinearRegression:\n    def __init__(self, learning_rate=0.01, max_iterations=1000):\n        self.learning_rate = learning_rate\n        self.max_iterations = max_iterations\n        self.weights = None\n        self.bias = None\n        \n    def fit(self, X, y):\n        n_samples, n_features = X.shape\n        self.weights = np.zeros(n_features)\n        self.bias = 0\n        \n        for i in range(self.max_iterations):\n            y_predicted = self.predict(X)\n            \n            # 计算梯度\n            dw = (1/n_samples) * np.dot(X.T, (y_predicted - y))\n            db = (1/n_samples) * np.sum(y_predicted - y)\n            \n            # 更新参数\n            self.weights -= self.learning_rate * dw\n            self.bias -= self.learning_rate * db\n            \n    def predict(self, X):\n        return np.dot(X, self.weights) + self.bias</code></pre><h3>应用示例</h3><p>房价预测模型的实现和评估。</p>',
    tags: ['机器学习', '线性回归', 'Python', '数据科学'],
    course: ['机器学习基础'],
    studySubject: ['人工智能'],
    bookName: '机器学习实战',
    bookSubject: ['机器学习', '人工智能'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 20,
    type: 'study',
    title: 'TypeScript高级类型系统学习',
    summary: '深入学习TypeScript的高级类型特性，包括泛型、联合类型、映射类型等核心概念。',
    content: '<h2>TypeScript 高级类型</h2><p>TypeScript 的类型系统非常强大，提供了许多高级特性来帮助开发者编写更安全、更可维护的代码。</p><h3>泛型 (Generics)</h3><pre><code>// 基础泛型函数\nfunction identity<T>(arg: T): T {\n  return arg;\n}\n\n// 泛型接口\ninterface GenericIdentityFn<T> {\n  (arg: T): T;\n}\n\n// 泛型类\nclass GenericNumber<T> {\n  zeroValue: T;\n  add: (x: T, y: T) => T;\n}</code></pre><h3>联合类型与交叉类型</h3><pre><code>// 联合类型\ntype Status = \'loading\' | \'success\' | \'error\';\n\n// 交叉类型\ntype Person = {\n  name: string;\n  age: number;\n};\n\ntype Employee = {\n  employeeId: number;\n  department: string;\n};\n\ntype PersonEmployee = Person & Employee;</code></pre><h3>映射类型</h3><pre><code>// 内置映射类型\ntype Partial<T> = {\n  [P in keyof T]?: T[P];\n};\n\ntype Required<T> = {\n  [P in keyof T]-?: T[P];\n};\n\n// 自定义映射类型\ntype Nullable<T> = {\n  [P in keyof T]: T[P] | null;\n};</code></pre><h3>条件类型</h3><pre><code>type NonNullable<T> = T extends null | undefined ? never : T;\n\ntype ReturnType<T extends (...args: any) => any> = \n  T extends (...args: any) => infer R ? R : any;</code></pre><h3>实用工具类型</h3><p>• Pick<T, K><br>• Omit<T, K><br>• Record<K, T><br>• Exclude<T, U><br>• Extract<T, U></p>',
    tags: ['TypeScript', '类型系统', '前端开发', 'JavaScript'],
    course: ['前端进阶'],
    studySubject: ['Web开发'],
    bookName: 'TypeScript官方手册',
    bookSubject: ['TypeScript', '编程语言'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 21,
    type: 'study',
    title: 'Docker容器化技术实践',
    summary: '学习Docker容器化技术的核心概念，掌握容器的创建、管理和部署技巧。',
    content: '<h2>Docker 容器化技术</h2><p>Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的容器中。</p><h3>核心概念</h3><ul><li><strong>镜像 (Image)</strong>：只读的模板，用来创建容器</li><li><strong>容器 (Container)</strong>：镜像的运行实例</li><li><strong>仓库 (Repository)</strong>：存放镜像的地方</li></ul><h3>Dockerfile 编写</h3><pre><code># 基于 Node.js 的应用示例\nFROM node:16-alpine\n\n# 设置工作目录\nWORKDIR /app\n\n# 复制 package.json 和 package-lock.json\nCOPY package*.json ./\n\n# 安装依赖\nRUN npm ci --only=production\n\n# 复制应用代码\nCOPY . .\n\n# 暴露端口\nEXPOSE 3000\n\n# 启动应用\nCMD [\"npm\", \"start\"]</code></pre><h3>常用命令</h3><pre><code># 构建镜像\ndocker build -t my-app .\n\n# 运行容器\ndocker run -d -p 3000:3000 --name my-app-container my-app\n\n# 查看容器\ndocker ps\n\n# 停止容器\ndocker stop my-app-container\n\n# 删除容器\ndocker rm my-app-container</code></pre><h3>Docker Compose</h3><pre><code>version: \'3.8\'\nservices:\n  web:\n    build: .\n    ports:\n      - \"3000:3000\"\n    depends_on:\n      - db\n    environment:\n      - DATABASE_URL=postgres://user:password@db:5432/mydb\n      \n  db:\n    image: postgres:13\n    environment:\n      - POSTGRES_DB=mydb\n      - POSTGRES_USER=user\n      - POSTGRES_PASSWORD=password\n    volumes:\n      - postgres_data:/var/lib/postgresql/data\n      \nvolumes:\n  postgres_data:</code></pre><h3>最佳实践</h3><p>• 使用多阶段构建<br>• 优化镜像大小<br>• 安全配置<br>• 健康检查</p>',
    tags: ['Docker', '容器化', 'DevOps', '部署'],
    course: ['云计算技术'],
    studySubject: ['云计算'],
    bookName: 'Docker实战',
    bookSubject: ['Docker', '容器技术'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 22,
    type: 'study',
    title: 'Redis缓存架构设计与优化',
    summary: '深入学习Redis的数据结构和使用场景，掌握缓存架构设计和性能优化技巧。',
    content: '<h2>Redis 概述</h2><p>Redis 是一个开源的、内存中的数据结构存储系统，可以用作数据库、缓存和消息中间件。</p><h3>数据结构</h3><h4>1. 字符串 (String)</h4><pre><code># 基本操作\nSET key value\nGET key\nINCR counter\nEXPIRE key 60</code></pre><h4>2. 哈希 (Hash)</h4><pre><code># 存储对象\nHSET user:1 name \"John\" age 30\nHGET user:1 name\nHGETALL user:1</code></pre><h4>3. 列表 (List)</h4><pre><code># 队列操作\nLPUSH queue task1\nLPUSH queue task2\nRPOP queue</code></pre><h4>4. 集合 (Set)</h4><pre><code># 标签系统\nSADD tags:article:1 \"tech\" \"programming\"\nSMEMBERS tags:article:1\nSINTER tags:article:1 tags:article:2</code></pre><h4>5. 有序集合 (Sorted Set)</h4><pre><code># 排行榜\nZADD leaderboard 100 \"player1\"\nZADD leaderboard 200 \"player2\"\nZRANGE leaderboard 0 -1 WITHSCORES</code></pre><h3>缓存策略</h3><ul><li><strong>缓存穿透</strong>：布隆过滤器</li><li><strong>缓存击穿</strong>：互斥锁</li><li><strong>缓存雪崩</strong>：过期时间随机化</li></ul><h3>实际应用</h3><pre><code># Python 示例：缓存用户信息\nimport redis\nimport json\n\nclass UserCache:\n    def __init__(self):\n        self.redis_client = redis.Redis(host=\'localhost\', port=6379, db=0)\n        \n    def get_user(self, user_id):\n        # 先从缓存获取\n        cached_user = self.redis_client.get(f\"user:{user_id}\")\n        if cached_user:\n            return json.loads(cached_user)\n            \n        # 缓存未命中，从数据库获取\n        user = self.get_user_from_db(user_id)\n        if user:\n            # 设置缓存，过期时间1小时\n            self.redis_client.setex(\n                f\"user:{user_id}\", \n                3600, \n                json.dumps(user)\n            )\n        return user</code></pre>',
    tags: ['Redis', '缓存', '数据库', '性能优化'],
    course: ['数据库技术'],
    studySubject: ['数据库'],
    bookName: 'Redis实战',
    bookSubject: ['Redis', '缓存技术'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 23,
    type: 'study',
    title: 'Vue.js 3 Composition API 深度学习',
    summary: '全面掌握Vue 3的Composition API，理解响应式系统的工作原理和最佳实践。',
    content: '<h2>Vue 3 Composition API</h2><p>Composition API 是 Vue 3 中引入的一套新的 API，为组件提供了更灵活的逻辑复用能力。</p><h3>响应式基础</h3><pre><code>import { ref, reactive, computed } from \'vue\'\n\nexport default {\n  setup() {\n    // ref 创建响应式引用\n    const count = ref(0)\n    \n    // reactive 创建响应式对象\n    const state = reactive({\n      name: \'John\',\n      age: 30\n    })\n    \n    // computed 计算属性\n    const doubleCount = computed(() => count.value * 2)\n    \n    // 方法\n    const increment = () => {\n      count.value++\n    }\n    \n    return {\n      count,\n      state,\n      doubleCount,\n      increment\n    }\n  }\n}</code></pre><h3>生命周期钩子</h3><pre><code>import { onMounted, onUnmounted } from \'vue\'\n\nexport default {\n  setup() {\n    onMounted(() => {\n      console.log(\'组件已挂载\')\n    })\n    \n    onUnmounted(() => {\n      console.log(\'组件即将卸载\')\n    })\n  }\n}</code></pre><h3>自定义 Hooks</h3><pre><code>// useCounter.js\nimport { ref } from \'vue\'\n\nexport function useCounter(initialValue = 0) {\n  const count = ref(initialValue)\n  \n  const increment = () => count.value++\n  const decrement = () => count.value--\n  const reset = () => count.value = initialValue\n  \n  return {\n    count,\n    increment,\n    decrement,\n    reset\n  }\n}\n\n// 在组件中使用\nimport { useCounter } from \'./useCounter\'\n\nexport default {\n  setup() {\n    const { count, increment, decrement } = useCounter(10)\n    \n    return {\n      count,\n      increment,\n      decrement\n    }\n  }\n}</code></pre><h3>响应式原理</h3><p>Vue 3 使用 Proxy 来实现响应式系统，相比 Vue 2 的 Object.defineProperty 有以下优势：</p><ul><li>可以监听对象属性的添加和删除</li><li>可以监听数组的变化</li><li>性能更好</li></ul>',
    tags: ['Vue.js', 'Composition API', '前端框架', 'JavaScript'],
    course: ['前端框架'],
    studySubject: ['Web开发'],
    bookName: 'Vue.js 3 官方文档',
    bookSubject: ['Vue.js', '前端开发'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 24,
    type: 'study',
    title: 'GraphQL API 设计与实践',
    summary: '学习GraphQL的核心概念和设计原理，掌握现代API开发的最佳实践。',
    content: '<h2>GraphQL 概述</h2><p>GraphQL 是一种用于 API 的查询语言，同时也是一个使用基于类型系统来执行查询的服务端运行时。</p><h3>核心概念</h3><h4>1. Schema 定义</h4><pre><code>type User {\n  id: ID!\n  name: String!\n  email: String!\n  posts: [Post!]!\n}\n\ntype Post {\n  id: ID!\n  title: String!\n  content: String!\n  author: User!\n  createdAt: String!\n}\n\ntype Query {\n  users: [User!]!\n  user(id: ID!): User\n  posts: [Post!]!\n  post(id: ID!): Post\n}\n\ntype Mutation {\n  createUser(input: CreateUserInput!): User!\n  updateUser(id: ID!, input: UpdateUserInput!): User!\n  deleteUser(id: ID!): Boolean!\n}\n\ninput CreateUserInput {\n  name: String!\n  email: String!\n}</code></pre><h4>2. Resolver 实现</h4><pre><code>// Node.js + Apollo Server 示例\nconst resolvers = {\n  Query: {\n    users: () => users,\n    user: (parent, { id }) => users.find(user => user.id === id),\n    posts: () => posts,\n    post: (parent, { id }) => posts.find(post => post.id === id)\n  },\n  \n  Mutation: {\n    createUser: (parent, { input }) => {\n      const newUser = {\n        id: generateId(),\n        ...input\n      }\n      users.push(newUser)\n      return newUser\n    }\n  },\n  \n  User: {\n    posts: (parent) => posts.filter(post => post.authorId === parent.id)\n  },\n  \n  Post: {\n    author: (parent) => users.find(user => user.id === parent.authorId)\n  }\n}</code></pre><h3>查询示例</h3><pre><code># 查询用户及其文章\nquery GetUserWithPosts($userId: ID!) {\n  user(id: $userId) {\n    id\n    name\n    email\n    posts {\n      id\n      title\n      createdAt\n    }\n  }\n}\n\n# 创建新用户\nmutation CreateUser($input: CreateUserInput!) {\n  createUser(input: $input) {\n    id\n    name\n    email\n  }\n}</code></pre><h3>优势与特点</h3><ul><li><strong>精确获取</strong>：只请求需要的数据</li><li><strong>单一端点</strong>：一个 URL 处理所有请求</li><li><strong>强类型</strong>：编译时类型检查</li><li><strong>实时更新</strong>：支持 Subscription</li></ul>',
    tags: ['GraphQL', 'API设计', '后端开发', 'Node.js'],
    course: ['后端开发'],
    studySubject: ['Web开发'],
    bookName: 'GraphQL实战',
    bookSubject: ['GraphQL', 'API开发'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  }
];

// 获取所有求学笔记
export const getAllStudyNotes = () => {
  return studyNotes;
};

// 获取求学笔记统计信息
export const getStudyNotesStats = () => {
  return {
    total: studyNotes.length
  };
}; 
