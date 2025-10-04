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

// 碎片笔记类型定义
export interface FragmentNote {
  id: number
  type: 'fragment'
  title: string
  summary: string
  content: string
  tags: string[]
  createTime: string
  fragmentCategory: string[]
  fragmentTheme: string[]
  importance: 'low' | 'medium' | 'high'
}

export const fragmentNotes: FragmentNote[] = [
  {
    id: 50,
    type: 'fragment',
    title: '关于代码可读性的思考',
    summary: '代码可读性对团队协作的重要性，以及如何提升代码的可读性',
    content: '<h2>代码可读性的重要性</h2><p>代码可读性直接影响团队协作效率：</p><ul><li>清晰的命名规则</li><li>合理的函数长度</li><li>适当的注释说明</li><li>一致的代码风格</li></ul><p>可读性好的代码更容易维护和扩展。</p>',
    tags: ['代码质量', '团队协作', '思考'],
    fragmentCategory: ['技术', '思考'],
    fragmentTheme: ['代码质量', '团队协作'],
    importance: 'high',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 51,
    type: 'fragment',
    title: '学习效率的提升方法',
    summary: '如何更高效地学习新技术，建立知识体系',
    content: '<h2>学习效率提升方法</h2><p>高效学习的关键要素：</p><ul><li>建立知识框架</li><li>实践与理论结合</li><li>定期复习巩固</li><li>与他人交流分享</li></ul><p>持续学习是技术发展的基础。</p>',
    tags: ['学习方法', '效率提升', '思考'],
    fragmentCategory: ['学习', '思考'],
    fragmentTheme: ['学习方法', '效率提升'],
    importance: 'medium',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 52,
    type: 'fragment',
    title: '技术选型的考虑因素',
    summary: '在项目中选择合适技术栈时需要考虑的各种因素',
    content: '<h2>技术选型考虑因素</h2><p>技术选型需要综合考虑。</p><ul><li>团队技术能力</li><li>项目需求特点</li><li>社区活跃度</li><li>长期维护成本</li><li>性能要求</li></ul><p>没有最好的技术，只有最适合的技术。</p>',
    tags: ['技术选型', '架构设计', '思考'],
    fragmentCategory: ['技术', '思考'],
    fragmentTheme: ['技术选型', '架构设计'],
    importance: 'high',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 53,
    type: 'fragment',
    title: '关于时间管理的感悟',
    summary: '如何更好地管理时间，提高工作效率和生活质量',
    content: '<h2>时间管理的重要性</h2><p>有效的时间管理包括：</p><ul><li>优先级排序</li><li>时间块划分</li><li>避免干扰</li><li>定期回顾</li></ul><p>时间是最宝贵的资源，需要合理分配。</p>',
    tags: ['时间管理', '效率', '生活'],
    fragmentCategory: ['生活', '思考'],
    fragmentTheme: ['时间管理', '效率提升'],
    importance: 'medium',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 54,
    type: 'fragment',
    title: '团队协作中的沟通技巧',
    summary: '在技术团队中如何更好地进行沟通和协作',
    content: '<h2>团队沟通技巧</h2><p>有效的团队沟通需要：</p><ul><li>清晰表达想法</li><li>积极倾听反馈</li><li>及时同步信息</li><li>建立沟通机制</li></ul><p>良好的沟通是团队成功的基础。</p>',
    tags: ['团队协作', '沟通', '职场'],
    fragmentCategory: ['职场', '思考'],
    fragmentTheme: ['团队协作', '沟通技巧'],
    importance: 'high',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 55,
    type: 'fragment',
    title: '关于代码重构的思考',
    summary: '何时进行代码重构，以及重构的最佳实践',
    content: '<h2>代码重构的重要性</h2><p>代码重构是保持代码质量的重要手段：</p><ul><li>识别重构时机</li><li>制定重构计划</li><li>保证测试覆盖</li><li>逐步推进重构</li></ul><p>重构不是一次性工作，而是持续的过程。</p>',
    tags: ['代码重构', '代码质量', '最佳实践'],
    fragmentCategory: ['技术', '思考'],
    fragmentTheme: ['代码重构', '代码质量'],
    importance: 'medium',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 56,
    type: 'fragment',
    title: '技术债务的管理',
    summary: '如何识别和管理技术债务，避免系统腐化',
    content: '<h2>技术债务管理</h2><p>技术债务是软件开发中不可避免的问题：</p><ul><li>识别技术债务</li><li>评估债务影响</li><li>制定偿还计划</li><li>预防新债务产生</li></ul><p>合理管理技术债务是项目成功的关键。</p>',
    tags: ['技术债务', '项目管理', '代码质量'],
    fragmentCategory: ['技术', '项目管理'],
    fragmentTheme: ['技术债务', '项目管理'],
    importance: 'high',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 57,
    type: 'fragment',
    title: '关于持续集成的思考',
    summary: '持续集成对项目质量和开发效率的影响',
    content: '<h2>持续集成的价值</h2><p>持续集成是现代软件开发的重要实践：</p><ul><li>自动化构建</li><li>自动化测试</li><li>快速反馈</li><li>降低集成风险</li></ul><p>CI/CD流程能显著提升开发效率。</p>',
    tags: ['持续集成', 'DevOps', '自动化'],
    fragmentCategory: ['技术', 'DevOps'],
    fragmentTheme: ['持续集成', 'DevOps'],
    importance: 'medium',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 58,
    type: 'fragment',
    title: '关于技术分享的感悟',
    summary: '技术分享对个人成长和团队建设的重要性',
    content: '<h2>技术分享的价值</h2><p>技术分享是知识传播的重要方式：</p><ul><li>促进知识交流</li><li>提升表达能力</li><li>加深技术理解</li><li>建立技术氛围</li></ul><p>分享是最好的学习方式。</p>',
    tags: ['技术分享', '知识传播', '团队建设'],
    fragmentCategory: ['职场', '学习'],
    fragmentTheme: ['技术分享', '知识传播'],
    importance: 'medium',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  }
];

// 获取所有碎片笔记
export const getAllFragmentNotes = (): FragmentNote[] => {
  return fragmentNotes;
};

// 根据ID获取碎片笔记
export const getFragmentNoteById = (id: number): FragmentNote | undefined => {
  return fragmentNotes.find(note => note.id === id);
};

// 搜索碎片笔记
export const searchFragmentNotes = (query: string): FragmentNote[] => {
  const lowerQuery = query.toLowerCase();
  return fragmentNotes.filter(note => 
    note.title.toLowerCase().includes(lowerQuery) ||
    note.summary.toLowerCase().includes(lowerQuery) ||
    note.tags.some(tag => tag.toLowerCase().includes(lowerQuery))
  );
};

// 根据标签获取碎片笔记
export const getFragmentNotesByTag = (tag: string): FragmentNote[] => {
  return fragmentNotes.filter(note => note.tags.includes(tag));
};

// 获取碎片笔记统计信息
export const getFragmentNotesStats = () => {
  return {
    total: fragmentNotes.length,
    byImportance: {
      high: fragmentNotes.filter(n => n.importance === 'high').length,
      medium: fragmentNotes.filter(n => n.importance === 'medium').length,
      low: fragmentNotes.filter(n => n.importance === 'low').length
    }
  };
}; 