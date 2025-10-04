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

// 背诵笔记类型定义
export interface MemorizationNote {
  id: number
  type: 'memorization'
  title: string
  summary: string
  content: string
  tags: string[]
  project: string
  knowledgePoint: string[]
  reviewCount: number
  lastReviewTime?: string // 上次背诵时间
  createTime: string
  // 新增字段
  originalText: string // 原文
  explanation: string // 解释
  cue: string // 背诵提示词
}

export const memorizationNotes: MemorizationNote[] = [
  // 物理——牛顿第一定律
  {
    id: 1,
    type: 'memorization',
    title: '牛顿第一定律',
    summary: '',
    content: '',
    tags: ['物理', '力学', '基础定律'],
    project: '高中物理',
    knowledgePoint: ['力与运动'],
    reviewCount: 5,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '物体如果不受外力作用，将保持静止状态或匀速直线运动状态。描述物体在无外力作用下的运动状态。',
    explanation: '比如你在滑冰场上滑行，如果没人推你，你会一直滑下去，直到摩擦力让你停下来。这就是牛顿第一定律，也叫惯性定律。',
    cue: '不受外力，保持原状态，惯性',
  },
  // 法律——正当防卫
  {
    id: 2,
    type: 'memorization',
    title: '正当防卫',
    summary: '',
    content: '',
    tags: ['法律', '刑法', '权利保护'],
    project: '法学基础',
    knowledgePoint: ['刑法总则'],
    reviewCount: 3,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '为了使国家、公共利益、本人或者他人的人身、财产和其他权利免受正在进行的不法侵害，而采取的制止不法侵害的行为，对不法侵害人造成损害的，不负刑事责任。正当防卫的法律定义和免责条件。',
    explanation: '比如有人抢劫你，你反抗并制服了对方，这种情况下你的行为属于正当防卫，不会被追究刑事责任。',
    cue: '制止侵害，无责，保护权利',
  },
  // 英语——虚拟语气
  {
    id: 3,
    type: 'memorization',
    title: '虚拟语气',
    summary: '',
    content: '',
    tags: ['英语', '语法', '句型'],
    project: '英语语法',
    knowledgePoint: ['虚拟语气'],
    reviewCount: 7,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: 'If I were you, I would study harder.虚拟语气用于表达与事实相反的假设。',
    explanation: '比如你劝朋友努力学习时说：“如果我是你，我会更努力。”其实你不是他，这就是虚拟语气。',
    cue: 'if I were you, would',
  },

  // 软考项目
  {
    id: 4,
    type: 'memorization',
    title: '软考系统架构师重点知识点',
    summary: '',
    content: '<h2>软考系统架构师重点知识点</h2><p>系统架构师考试的核心知识点：</p><h3>软件架构设计</h3><ul><li>架构风格（分层架构、微服务架构、事件驱动架构）</li><li>架构模式（MVC、MVP、MVVM）</li><li>设计原则（SOLID原则、DRY原则）</li><li>架构评估方法（ATAM、SAAM）</li></ul><h3>系统设计</h3><ul><li>性能设计（负载均衡、缓存策略、数据库优化）</li><li>安全设计（身份认证、授权、数据加密）</li><li>可用性设计（容错、备份、灾难恢复）</li><li>可扩展性设计（水平扩展、垂直扩展）</li></ul><h3>技术选型</h3><ul><li>中间件技术（消息队列、缓存、搜索引擎）</li><li>数据库技术（关系型、NoSQL、分布式数据库）</li><li>云计算技术（IaaS、PaaS、SaaS）</li><li>容器技术（Docker、Kubernetes）</li></ul>',
    tags: ['概念记忆', '理论理解', '架构设计', '技术选型'],
    project: '软考项目',
    knowledgePoint: ['软件架构设计', '系统设计', '技术选型'],
    reviewCount: 8,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: 'MVC MVP MVVM SOLID DRY ATAM SAAM',
    explanation: '这些概念和方法用于软考系统架构师考试，是软件开发和架构设计的基础。例如：MVC模式可以提高代码的可维护性和可测试性。',
    cue: '软考系统架构师、重点知识点、软件架构设计、系统设计、技术选型',
  },
  {
    id: 5,
    type: 'memorization',
    title: '软考项目管理知识点',
    summary: '',
    content: '<h2>软考项目管理知识点</h2><p>项目管理考试的重要知识点：</p><h3>项目启动</h3><ul><li>项目章程的制定</li><li>干系人识别和管理</li><li>项目目标的确定</li><li>项目可行性分析</li></ul><h3>项目规划</h3><ul><li>工作分解结构（WBS）</li><li>项目进度计划</li><li>项目成本估算</li><li>项目风险管理</li></ul><h3>项目执行</h3><ul><li>团队建设和管理</li><li>项目沟通管理</li><li>项目质量管理</li><li>项目采购管理</li></ul>',
    tags: ['概念记忆', '管理理论', '项目管理', '软考知识'],
    project: '软考项目',
    knowledgePoint: ['项目启动', '项目规划', '项目执行'],
    reviewCount: 10,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: 'WBS 项目进度计划 项目成本估算 项目风险管理',
    explanation: '这些知识点是软考项目管理的基础，对于项目经理来说至关重要。例如：WBS可以帮助项目经理更好地理解项目的整体结构和范围。',
    cue: '软考项目管理、项目启动、项目规划、项目执行',
  },

  // 法考项目
  {
    id: 6,
    type: 'memorization',
    title: '法考刑法重点法条背诵',
    summary: '',
    content: '<h2>法考刑法重点法条</h2><p>刑法科目的重要法条和司法解释：</p><h3>总则部分</h3><ul><li>第13条：犯罪概念（情节显著轻微危害不大的，不认为是犯罪）</li><li>第14条：故意犯罪（明知自己的行为会发生危害社会的结果...）</li><li>第15条：过失犯罪（应当预见自己的行为可能发生危害社会的结果...）</li><li>第17条：刑事责任年龄（已满十六周岁的人犯罪，应当负刑事责任）</li></ul><h3>分则部分</h3><ul><li>第232条：故意杀人罪（故意杀人的，处死刑、无期徒刑或者十年以上有期徒刑）</li><li>第263条：抢劫罪（以暴力、胁迫或者其他方法抢劫公私财物的...）</li><li>第264条：盗窃罪（盗窃公私财物，数额较大的...）</li><li>第266条：诈骗罪（诈骗公私财物，数额较大的...）</li></ul><h3>司法解释</h3><ul><li>关于审理抢劫案件具体应用法律若干问题的解释</li><li>关于审理盗窃案件具体应用法律若干问题的解释</li><li>关于审理诈骗案件具体应用法律若干问题的解释</li></ul>',
    tags: ['法条记忆', '法律条文', '司法解释', '刑法知识'],
    project: '法考项目',
    knowledgePoint: ['总则法条', '分则法条', '司法解释'],
    reviewCount: 15,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '第13条 第14条 第15条 第17条 第232条 第263条 第264条 第266条',
    explanation: '这些法条和司法解释是法考刑法的核心，对于理解刑法的基本概念和原则非常重要。例如：第13条明确了犯罪的概念，第232条详细规定了故意杀人罪的量刑。',
    cue: '法考刑法、重点法条、司法解释、刑法知识',
  },
  {
    id: 7,
    type: 'memorization',
    title: '法考民法重点法条',
    summary: '',
    content: '<h2>法考民法重点法条</h2><p>民法科目的重要法条和司法解释：</p><h3>总则部分</h3><ul><li>第2条：民法调整对象（民法调整平等主体的自然人、法人和非法人组织之间的人身关系和财产关系）</li><li>第3条：民事主体地位平等</li><li>第4条：民事活动自愿原则</li><li>第5条：民事活动公平原则</li></ul><h3>物权部分</h3><ul><li>第114条：物权概念（物权是权利人依法对特定的物享有直接支配和排他的权利）</li><li>第208条：不动产物权登记</li><li>第224条：动产物权交付</li><li>第240条：所有权概念</li></ul><h3>合同部分</h3><ul><li>第464条：合同概念（合同是民事主体之间设立、变更、终止民事法律关系的协议）</li><li>第502条：合同成立</li><li>第563条：合同解除</li><li>第577条：违约责任</li></ul>',
    tags: ['法条记忆', '法律条文', '民法知识', '法律理解'],
    project: '法考项目',
    knowledgePoint: ['总则法条', '物权法条', '合同法条'],
    reviewCount: 12,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '第2条 第3条 第4条 第5条 第114条 第208条 第224条 第240条 第464条 第502条 第563条 第577条',
    explanation: '这些法条和司法解释是法考民法的基础，对于理解民法的基本原则和具体规定非常重要。例如：第2条明确了民法的调整对象，第114条详细规定了物权的概念。',
    cue: '法考民法、重点法条、司法解释、民法知识',
  },

  // Python八股文项目
  {
    id: 8,
    type: 'memorization',
    title: 'Python八股文面试题',
    summary: '',
    content: '<h2>Python八股文面试题</h2><p>Python面试中常见的技术问题：</p><h3>基础语法</h3><ul><li>Python的GIL是什么？对多线程有什么影响？</li><li>Python中的深拷贝和浅拷贝有什么区别？</li><li>Python的装饰器是什么？如何使用？</li><li>Python的生成器和迭代器有什么区别？</li></ul><h3>面向对象</h3><ul><li>Python中的继承、封装、多态如何实现？</li><li>Python的MRO（方法解析顺序）是什么？</li><li>Python的元类是什么？如何使用？</li><li>Python的抽象类和接口有什么区别？</li></ul><h3>高级特性</h3><ul><li>Python的上下文管理器是什么？如何使用？</li><li>Python的协程是什么？与线程有什么区别？</li><li>Python的内存管理机制是什么？</li><li>Python的垃圾回收机制是什么？</li></ul>',
    tags: ['面试题记忆', '技术概念', 'Python知识', '编程理解'],
    project: 'Python八股文项目',
    knowledgePoint: ['基础语法', '面向对象', '高级特性'],
    reviewCount: 20,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: 'GIL 深拷贝 浅拷贝 装饰器 生成器 迭代器 继承 封装 多态 MRO 元类 抽象类 接口 上下文管理器 协程 内存管理 垃圾回收',
    explanation: '这些概念和方法用于Python面试，是理解Python编程的重要基础。例如：GIL是Python的全局解释器锁，对多线程有重要影响。',
    cue: 'Python面试、八股文、技术概念、Python知识',
  },
  {
    id: 9,
    type: 'memorization',
    title: 'Python数据结构面试题',
    summary: '',
    content: '<h2>Python数据结构面试题</h2><p>Python数据结构相关的面试题：</p><h3>列表和元组</h3><ul><li>列表和元组的区别是什么？</li><li>列表的常用方法有哪些？</li><li>如何实现列表的深拷贝？</li><li>列表推导式的语法是什么？</li></ul><h3>字典和集合</h3><ul><li>字典的实现原理是什么？</li><li>字典的键有什么要求？</li><li>集合的特点是什么？</li><li>如何实现字典的合并？</li></ul><h3>字符串处理</h3><ul><li>字符串的常用方法有哪些？</li><li>字符串的格式化方式有哪些？</li><li>正则表达式的使用</li><li>字符串的编码和解码</li></ul>',
    tags: ['面试题记忆', '数据结构', 'Python知识', '编程理解'],
    project: 'Python八股文项目',
    knowledgePoint: ['列表和元组', '字典和集合', '字符串处理'],
    reviewCount: 16,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '列表 元组 深拷贝 列表推导式 字典 集合 字符串 格式化 正则表达式 编码 解码',
    explanation: '这些概念和方法用于Python数据结构相关的面试，是理解Python编程的重要基础。例如：列表和元组的区别在于列表是可变的，而元组是不可变的。',
    cue: 'Python数据结构、面试题、列表 元组 字典 集合 字符串',
  },

  // Java八股文项目
  {
    id: 10,
    type: 'memorization',
    title: 'Java八股文面试题',
    summary: '',
    content: '<h2>Java八股文面试题</h2><p>Java面试中常见的技术问题：</p><h3>基础语法</h3><ul><li>Java的面向对象特性有哪些？</li><li>Java的访问修饰符有哪些？</li><li>Java的final、finally、finalize有什么区别？</li><li>Java的static关键字有什么作用？</li></ul><h3>集合框架</h3><ul><li>ArrayList和LinkedList有什么区别？</li><li>HashMap和HashTable有什么区别？</li><li>ConcurrentHashMap的实现原理是什么？</li><li>Java集合的fail-fast机制是什么？</li></ul><h3>多线程</h3><ul><li>Java中如何创建线程？</li><li>synchronized和volatile有什么区别？</li><li>Java的线程池有哪些？如何选择？</li><li>Java的锁机制有哪些？</li></ul>',
    tags: ['面试题记忆', '技术概念', 'Java知识', '编程理解'],
    project: 'Java八股文项目',
    knowledgePoint: ['基础语法', '集合框架', '多线程'],
    reviewCount: 18,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '面向对象 访问修饰符 final finally finalize static 集合框架 ArrayList LinkedList HashMap HashTable ConcurrentHashMap fail-fast 多线程 线程 锁',
    explanation: '这些概念和方法用于Java面试，是理解Java编程的重要基础。例如：synchronized和volatile的区别在于synchronized是独占锁，而volatile是轻量级的同步机制。',
    cue: 'Java面试、八股文、技术概念、Java知识',
  },
  {
    id: 11,
    type: 'memorization',
    title: 'Java虚拟机面试题',
    summary: '',
    content: '<h2>Java虚拟机面试题</h2><p>JVM相关的面试题：</p><h3>内存模型</h3><ul><li>JVM的内存结构有哪些？</li><li>堆内存和栈内存的区别？</li><li>方法区的特点是什么？</li><li>直接内存是什么？</li></ul><h3>垃圾回收</h3><ul><li>垃圾回收算法有哪些？</li><li>垃圾回收器有哪些？</li><li>如何判断对象是否可回收？</li><li>垃圾回收的触发条件是什么？</li></ul><h3>类加载</h3><ul><li>类加载的过程是什么？</li><li>类加载器有哪些？</li><li>双亲委派模型是什么？</li><li>如何打破双亲委派模型？</li></ul>',
    tags: ['面试题记忆', 'JVM知识', 'Java技术', '系统理解'],
    project: 'Java八股文项目',
    knowledgePoint: ['内存模型', '垃圾回收', '类加载'],
    reviewCount: 14,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '内存模型 堆内存 栈内存 方法区 直接内存 垃圾回收 垃圾回收算法 垃圾回收器 对象回收 垃圾回收触发条件 类加载 类加载过程 类加载器 双亲委派模型',
    explanation: '这些概念和方法用于JVM相关的面试，是理解Java虚拟机的重要基础。例如：垃圾回收的触发条件包括对象不可达、内存不足等。',
    cue: 'JVM面试、八股文、JVM知识、Java技术',
  },

  // 英语四六级项目
  {
    id: 12,
    type: 'memorization',
    title: '英语四六级核心词汇',
    summary: '',
    content: '<h2>英语四六级核心词汇</h2><p>四六级考试中的高频词汇：</p><h3>高频动词</h3><ul><li>accomplish（完成、实现）</li><li>demonstrate（证明、展示）</li><li>establish（建立、确立）</li><li>maintain（维持、保持）</li><li>obtain（获得、得到）</li><li>participate（参与、参加）</li><li>recognize（认识、承认）</li><li>represent（代表、表示）</li></ul><h3>高频形容词</h3><ul><li>appropriate（适当的、合适的）</li><li>comprehensive（全面的、综合的）</li><li>essential（必要的、重要的）</li><li>significant（重要的、显著的）</li><li>sufficient（充足的、足够的）</li><li>various（各种各样的、不同的）</li></ul><h3>高频短语</h3><ul><li>in terms of（就...而言）</li><li>as a result of（由于...的结果）</li><li>in addition to（除了...之外）</li><li>on the basis of（基于...）</li><li>with regard to（关于...）</li></ul>',
    tags: ['词汇记忆', '单词背诵', '短语学习', '英语词汇'],
    project: '英语四六级项目',
    knowledgePoint: ['高频动词', '高频形容词', '高频短语'],
    reviewCount: 25,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: 'accomplish demonstrate establish maintain obtain participate recognize represent',
    explanation: '这些词汇和短语是英语四六级考试中的高频内容，对于提高词汇量和短语应用能力非常重要。例如：accomplish a task。',
    cue: '英语四六级、核心词汇、高频动词、高频形容词、高频短语',
  },
  {
    id: 13,
    type: 'memorization',
    title: '英语四六级语法重点',
    summary: '',
    content: '<h2>英语四六级语法重点</h2><p>四六级考试中的语法重点：</p><h3>时态</h3><ul><li>现在完成时（have/has + 过去分词）</li><li>过去完成时（had + 过去分词）</li><li>将来完成时（will have + 过去分词）</li><li>现在进行时（be + 现在分词）</li></ul><h3>从句</h3><ul><li>定语从句（关系代词、关系副词）</li><li>名词性从句（主语从句、宾语从句、表语从句）</li><li>状语从句（时间、条件、原因、结果）</li><li>虚拟语气（if条件句、wish从句）</li></ul><h3>非谓语动词</h3><ul><li>不定式（to do）</li><li>动名词（doing）</li><li>现在分词（doing）</li><li>过去分词（done）</li></ul>',
    tags: ['语法记忆', '语法规则', '英语语法', '语言学习'],
    project: '英语四六级项目',
    knowledgePoint: ['时态', '从句', '非谓语动词'],
    reviewCount: 20,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '现在完成时 过去完成时 将来完成时 现在进行时 定语从句 名词性从句 状语从句 虚拟语气 非谓语动词',
    explanation: '这些语法点是英语四六级考试中的重点，对于掌握英语语法结构和应用非常重要。例如：现在完成时表示过去发生的动作对现在造成的影响。',
    cue: '英语四六级、语法重点、时态、从句、非谓语动词',
  },

  // 考研数学项目
  {
    id: 14,
    type: 'memorization',
    title: '考研数学公式大全',
    summary: '',
    content: '<h2>考研数学公式大全</h2><p>考研数学中常用的公式和定理：</p><h3>高等数学</h3><ul><li>导数公式：（sin x）\' = cos x，（cos x）\' = -sin x</li><li>积分公式：∫sin x dx = -cos x + C，∫cos x dx = sin x + C</li><li>泰勒公式：f(x) = f(a) + f\'(a)(x-a) + f\'\'(a)(x-a)²/2! + ...</li><li>拉格朗日中值定理：f(b) - f(a) = f\'(ξ)(b-a)</li></ul><h3>线性代数</h3><ul><li>行列式性质：|AB| = |A|·|B|</li><li>矩阵运算：A(B+C) = AB + AC</li><li>特征值性质：tr(A) = λ₁ + λ₂ + ... + λₙ</li><li>相似矩阵：A ~ B ⇔ P⁻¹AP = B</li></ul><h3>概率论</h3><ul><li>概率公式：P(A∪B) = P(A) + P(B) - P(A∩B)</li><li>条件概率：P(A|B) = P(A∩B)/P(B)</li><li>贝叶斯公式：P(A|B) = P(B|A)P(A)/P(B)</li><li>期望性质：E(aX+b) = aE(X) + b</li></ul>',
    tags: ['公式记忆', '数学定理', '公式推导', '数学知识'],
    project: '考研数学项目',
    knowledgePoint: ['高等数学', '线性代数', '概率论'],
    reviewCount: 30,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '导数公式 积分公式 泰勒公式 拉格朗日中值定理 行列式性质 矩阵运算 特征值性质 相似矩阵 概率公式 条件概率 贝叶斯公式 期望性质',
    explanation: '这些公式和定理是考研数学中的重点，对于理解和应用数学知识非常重要。例如：导数公式是微分学的基础。',
    cue: '考研数学、公式大全、公式推导、数学定理',
  },
  {
    id: 15,
    type: 'memorization',
    title: '考研数学解题技巧',
    summary: '',
    content: '<h2>考研数学解题技巧</h2><p>考研数学解题的常用技巧：</p><h3>极限计算</h3><ul><li>等价无穷小替换</li><li>洛必达法则</li><li>泰勒展开</li><li>夹逼准则</li></ul><h3>积分计算</h3><ul><li>换元积分法</li><li>分部积分法</li><li>三角代换</li><li>有理函数积分</li></ul><h3>级数判断</h3><ul><li>比较判别法</li><li>比值判别法</li><li>根值判别法</li><li>积分判别法</li></ul>',
    tags: ['技巧记忆', '解题方法', '数学技巧', '考研知识'],
    project: '考研数学项目',
    knowledgePoint: ['极限计算', '积分计算', '级数判断'],
    reviewCount: 22,
    lastReviewTime: generateRandomTime(timeRange.start, timeRange.end),
    createTime: generateRandomTime(timeRange.start, timeRange.end),
    originalText: '等价无穷小替换 洛必达法则 泰勒展开 夹逼准则 换元积分法 分部积分法 三角代换 有理函数积分 比较判别法 比值判别法 根值判别法 积分判别法',
    explanation: '这些技巧和方法是考研数学解题的常用方法，对于提高解题效率和准确性非常重要。例如：等价无穷小替换可以简化极限计算。',
    cue: '考研数学、解题技巧、极限计算、积分计算、级数判断',
  }
];

// 获取所有背诵笔记
export const getAllMemorizationNotes = (): MemorizationNote[] => {
  return memorizationNotes;
};

// 根据项目筛选笔记
export const getNotesByProject = (project: string): MemorizationNote[] => {
  return memorizationNotes.filter(note => note.project === project);
};

// 根据知识点筛选笔记
export const getNotesByKnowledgePoint = (knowledgePoint: string): MemorizationNote[] => {
  return memorizationNotes.filter(note => note.knowledgePoint.includes(knowledgePoint));
};

// 获取所有项目
export const getAllProjects = (): string[] => {
  const projects = new Set<string>();
  memorizationNotes.forEach(note => projects.add(note.project));
  return Array.from(projects);
};

// 获取指定项目的所有知识点
export const getKnowledgePointsByProject = (project: string): string[] => {
  const knowledgePoints = new Set<string>();
  memorizationNotes
    .filter(note => note.project === project)
    .forEach(note => note.knowledgePoint.forEach(kp => knowledgePoints.add(kp)));
  return Array.from(knowledgePoints);
};

// 获取所有知识点
export const getAllKnowledgePoints = (): string[] => {
  const knowledgePoints = new Set<string>();
  memorizationNotes.forEach(note => note.knowledgePoint.forEach(kp => knowledgePoints.add(kp)));
  return Array.from(knowledgePoints);
};

// 获取最近笔记（按创建时间排序，取前6个）
export const getRecentMemorizationNotes = (): MemorizationNote[] => {
  return memorizationNotes
    .sort((a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime())
    .slice(0, 6)
}

// 完成背诵（增加背诵次数并更新上次背诵时间）
export const completeMemorization = (noteId: number): MemorizationNote | null => {
  const noteIndex = memorizationNotes.findIndex(note => note.id === noteId)
  if (noteIndex === -1) return null
  
  const now = new Date()
  const beijingTime = new Date(now.getTime() + 8 * 60 * 60 * 1000)
  const currentTime = beijingTime.toISOString().slice(0, 16).replace('T', ' ')
  
  // 创建新的对象来触发响应式更新
  const updatedNote = {
    ...memorizationNotes[noteIndex],
    reviewCount: memorizationNotes[noteIndex].reviewCount + 1,
    lastReviewTime: currentTime
  }
  
  memorizationNotes[noteIndex] = updatedNote
  
  return updatedNote
} 