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

// 技能笔记类型定义
export interface SkillNote {
  id: number
  type: 'skill'
  title: string
  summary: string
  content: string
  tags: string[]
  createTime: string
  subjectType: string[]      // 学科类型
  knowledgePoint: string[]   // 知识点类型
}

export const skillNotes: SkillNote[] = [
  {
    id: 1,
    type: 'skill',
    title: 'Vue.js 组件化开发框架',
    summary: 'Vue.js前端框架的组件化思想、状态管理和路由系统的完整梳理',
    content: '<h2>Vue.js 组件化开发框架</h2><p>Vue.js是一个渐进式的JavaScript框架，以组件化开发为核心理念。</p><h3>核心概念</h3><ul><li>响应式数据绑定原理</li><li>组件生命周期管理</li><li>虚拟DOM和diff算法</li><li>指令系统和模板语法</li></ul><h3>状态管理</h3><ul><li>Vuex状态管理模式</li><li>模块化状态管理</li><li>状态持久化方案</li><li>异步状态处理</li></ul><h3>路由系统</h3><ul><li>Vue Router配置</li><li>路由守卫机制</li><li>动态路由匹配</li><li>嵌套路由设计</li></ul>',
    tags: ['Vue.js', '前端框架', '组件化', '状态管理'],
    subjectType: ['计算机科学'],
    knowledgePoint: ['前端开发', 'JavaScript框架', '组件化设计'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 2,
    type: 'skill',
    title: '数据库设计规范化框架',
    summary: '关系型数据库设计的规范化理论、索引优化和性能调优体系',
    content: '<h2>数据库设计规范化框架</h2><p>数据库设计是系统架构的重要组成部分，需要遵循科学的设计原则。</p><h3>规范化理论</h3><ul><li>第一范式到第三范式</li><li>BCNF和第四范式</li><li>函数依赖分析</li><li>数据冗余控制</li></ul><h3>索引策略</h3><ul><li>B+树索引原理</li><li>联合索引设计</li><li>覆盖索引优化</li><li>索引失效场景</li></ul><h3>性能优化</h3><ul><li>SQL查询优化</li><li>执行计划分析</li><li>分库分表策略</li><li>读写分离方案</li></ul>',
    tags: ['数据库', '设计规范', '性能优化', 'SQL'],
    subjectType: ['计算机科学'],
    knowledgePoint: ['数据库设计', '性能优化', '索引策略'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 3,
    type: 'framework',
    title: '机器学习算法分类框架',
    summary: '监督学习、无监督学习和强化学习的算法体系和应用场景',
    content: '<h2>机器学习算法分类框架</h2><p>机器学习算法根据学习方式和数据特点可以分为多个类别。</p><h3>监督学习</h3><ul><li>分类算法（决策树、随机森林、SVM）</li><li>回归算法（线性回归、逻辑回归）</li><li>集成学习（Bagging、Boosting）</li><li>模型评估与选择</li></ul><h3>无监督学习</h3><ul><li>聚类算法（K-means、层次聚类）</li><li>降维技术（PCA、t-SNE）</li><li>关联规则挖掘</li><li>异常检测方法</li></ul><h3>强化学习</h3><ul><li>马尔可夫决策过程</li><li>Q-Learning算法</li><li>深度强化学习</li><li>策略梯度方法</li></ul>',
    tags: ['机器学习', '人工智能', '算法分类', '数据科学'],
    subjectType: ['计算机科学'],
    knowledgePoint: ['机器学习', '算法设计', '数据分析'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 4,
    type: 'framework',
    title: '微服务架构设计框架',
    summary: '微服务架构的设计原则、服务治理和分布式系统解决方案',
    content: '<h2>微服务架构设计框架</h2><p>微服务架构将单体应用拆分为多个独立的服务，提高系统的可扩展性和维护性。</p><h3>设计原则</h3><ul><li>单一职责原则</li><li>服务自治性</li><li>去中心化治理</li><li>故障隔离设计</li></ul><h3>服务治理</h3><ul><li>服务注册与发现</li><li>配置中心管理</li><li>API网关设计</li><li>服务监控体系</li></ul><h3>分布式解决方案</h3><ul><li>分布式事务处理</li><li>消息队列通信</li><li>缓存一致性</li><li>负载均衡策略</li></ul>',
    tags: ['微服务', '系统架构', '分布式系统', '服务治理'],
    subjectType: ['计算机科学'],
    knowledgePoint: ['系统架构', '微服务设计', '分布式系统'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 5,
    type: 'framework',
    title: '统计学基础理论框架',
    summary: '描述性统计、推断统计和概率论的理论体系与实际应用',
    content: '<h2>统计学基础理论框架</h2><p>统计学是数据科学的基础，为数据分析和决策提供科学方法。</p><h3>描述性统计</h3><ul><li>集中趋势测量（均值、中位数、众数）</li><li>离散程度测量（方差、标准差）</li><li>分布形状分析</li><li>数据可视化方法</li></ul><h3>概率论基础</h3><ul><li>概率空间和事件</li><li>条件概率和贝叶斯定理</li><li>随机变量和分布</li><li>大数定律和中心极限定理</li></ul><h3>推断统计</h3><ul><li>参数估计方法</li><li>假设检验理论</li><li>置信区间构造</li><li>方差分析和回归分析</li></ul>',
    tags: ['统计学', '概率论', '数据分析', '数学理论'],
    subjectType: ['数学'],
    knowledgePoint: ['统计理论', '概率分析', '数据建模'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 6,
    type: 'framework',
    title: '企业财务管理框架',
    summary: '企业财务决策、资本结构优化和风险管理的理论与实践',
    content: '<h2>企业财务管理框架</h2><p>财务管理是企业经营管理的核心，关系到企业的生存和发展。</p><h3>财务决策</h3><ul><li>投资决策分析（NPV、IRR）</li><li>融资决策原理</li><li>股利政策制定</li><li>营运资金管理</li></ul><h3>资本结构</h3><ul><li>最优资本结构理论</li><li>杠杆效应分析</li><li>资本成本计算</li><li>价值评估方法</li></ul><h3>风险管理</h3><ul><li>财务风险识别</li><li>风险度量方法</li><li>对冲策略设计</li><li>内部控制体系</li></ul>',
    tags: ['财务管理', '企业管理', '投资决策', '风险控制'],
    subjectType: ['管理学'],
    knowledgePoint: ['财务分析', '投资决策', '风险管理'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 7,
    type: 'framework',
    title: '宪法学基本原理框架',
    summary: '宪法的基本理论、国家机构设置和公民基本权利保障体系',
    content: '<h2>宪法学基本原理框架</h2><p>宪法是国家的根本大法，规定国家的基本制度和公民的基本权利。</p><h3>宪法基础理论</h3><ul><li>宪法的概念和特征</li><li>宪法的制定和修改</li><li>宪法解释方法</li><li>违宪审查制度</li></ul><h3>国家机构</h3><ul><li>人民代表大会制度</li><li>行政机关职权</li><li>司法机关独立</li><li>监察体制改革</li></ul><h3>基本权利</h3><ul><li>公民基本权利体系</li><li>平等权和自由权</li><li>社会经济权利</li><li>权利救济机制</li></ul>',
    tags: ['宪法学', '法学理论', '国家制度', '公民权利'],
    subjectType: ['法学'],
    knowledgePoint: ['宪法理论', '国家制度', '权利保障'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 8,
    type: 'framework',
    title: '现代文学流派发展框架',
    summary: '20世纪文学流派的兴起、发展和相互影响的历史脉络',
    content: '<h2>现代文学流派发展框架</h2><p>20世纪文学呈现出多元化发展态势，形成了众多重要的文学流派。</p><h3>现代主义文学</h3><ul><li>意识流小说技法</li><li>象征主义诗歌</li><li>表现主义戏剧</li><li>荒诞派文学</li></ul><h3>现实主义发展</h3><ul><li>批判现实主义</li><li>社会主义现实主义</li><li>魔幻现实主义</li><li>新现实主义</li></ul><h3>后现代文学</h3><ul><li>解构主义文学</li><li>元小说技巧</li><li>互文性理论</li><li>文化批评视角</li></ul>',
    tags: ['文学史', '文学流派', '现代文学', '文学理论'],
    subjectType: ['文学'],
    knowledgePoint: ['文学史', '文学流派', '文学批评'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 9,
    type: 'framework',
    title: '量子力学基础框架',
    summary: '量子力学的基本假设、数学形式和物理解释',
    content: '<h2>量子力学基础框架</h2><p>量子力学是描述微观世界的基础物理理论，揭示了物质的波粒二象性。</p><h3>基本假设</h3><ul><li>波函数和概率解释</li><li>观测量和算符</li><li>量子态的演化</li><li>测量假设</li></ul><h3>数学工具</h3><ul><li>希尔伯特空间</li><li>薛定谔方程</li><li>厄米算符</li><li>本征值问题</li></ul><h3>经典应用</h3><ul><li>无限深势阱</li><li>谐振子模型</li><li>氢原子结构</li><li>自旋和角动量</li></ul>',
    tags: ['量子力学', '物理学', '数学物理', '微观世界'],
    subjectType: ['物理学'],
    knowledgePoint: ['量子理论', '数学物理', '微观粒子'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 10,
    type: 'framework',
    title: '有机化学反应机理框架',
    summary: '有机化学反应的分类、机理分析和合成策略',
    content: '<h2>有机化学反应机理框架</h2><p>有机化学反应机理揭示了分子间电子转移和化学键变化的过程。</p><h3>反应分类</h3><ul><li>取代反应机理</li><li>加成反应类型</li><li>消除反应条件</li><li>重排反应特点</li></ul><h3>机理分析</h3><ul><li>中间体识别</li><li>过渡态理论</li><li>反应活性比较</li><li>立体化学影响</li></ul><h3>合成策略</h3><ul><li>逆合成分析</li><li>保护基策略</li><li>选择性反应</li><li>一锅法合成</li></ul>',
    tags: ['有机化学', '反应机理', '合成化学', '分子结构'],
    subjectType: ['化学'],
    knowledgePoint: ['有机反应', '反应机理', '合成策略'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 11,
    type: 'framework',
    title: '细胞生物学研究框架',
    summary: '细胞结构、功能和生命活动的分子机制研究体系',
    content: '<h2>细胞生物学研究框架</h2><p>细胞是生命的基本单位，细胞生物学研究生命活动的基本规律。</p><h3>细胞结构</h3><ul><li>细胞膜结构与功能</li><li>细胞器组成与作用</li><li>细胞骨架系统</li><li>核质结构分析</li></ul><h3>细胞功能</h3><ul><li>物质运输机制</li><li>能量代谢途径</li><li>信号转导过程</li><li>基因表达调控</li></ul><h3>细胞生命过程</h3><ul><li>细胞周期调控</li><li>细胞分裂机制</li><li>细胞凋亡程序</li><li>细胞分化过程</li></ul>',
    tags: ['细胞生物学', '分子生物学', '生命科学', '细胞研究'],
    subjectType: ['生物学'],
    knowledgePoint: ['细胞结构', '分子机制', '生命过程'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 12,
    type: 'framework',
    title: '中国古代历史发展框架',
    summary: '中国古代政治制度、经济发展和文化传承的历史演进',
    content: '<h2>中国古代历史发展框架</h2><p>中国古代历史源远流长，形成了独特的政治制度和文化传统。</p><h3>政治制度演进</h3><ul><li>分封制度特点</li><li>郡县制建立</li><li>科举制度发展</li><li>中央集权强化</li></ul><h3>经济发展模式</h3><ul><li>农业技术进步</li><li>手工业发展</li><li>商业贸易扩展</li><li>货币制度变化</li></ul><h3>文化传承体系</h3><ul><li>儒家思想发展</li><li>文学艺术成就</li><li>科技发明创新</li><li>教育制度完善</li></ul>',
    tags: ['中国历史', '古代史', '政治制度', '文化传承'],
    subjectType: ['历史学'],
    knowledgePoint: ['古代政治', '经济发展', '文化传统'],
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  }
]

// 获取所有技能笔记
export const getAllSkillNotes = () => {
  return skillNotes
}

// 根据ID获取单个技能笔记
export const getSkillNoteById = (id: number) => {
  return skillNotes.find(note => note.id === id)
}

// 根据技能类型获取技能笔记
export const getSkillNotesByType = (skillType: string) => {
  return skillNotes.filter(note => 
    note.subjectType.includes(skillType)
  )
}

// 根据技能点获取技能笔记
export const getSkillNotesByPoint = (skillPoint: string) => {
  return skillNotes.filter(note => 
    note.knowledgePoint.includes(skillPoint)
  )
} 