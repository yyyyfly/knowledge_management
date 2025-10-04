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

// 实战笔记类型定义
export interface PracticalNote {
  id: number
  type: 'practical'
  title: string
  summary: string
  content: string
  tags: string[]
  createTime: string
  techStack: string[]
  projectType: string[]
  projectName: string
}

export const practicalNotes: PracticalNote[] = [
  {
    id: 37,
    type: 'practical',
    title: 'Vue3 + TypeScript 项目搭建实战',
    summary: '基于Vue3和TypeScript的现代化前端项目搭建经验',
    content: '<h2>Vue3 + TypeScript 项目搭建实战</h2><p>Vue3和TypeScript的结合是现代前端开发的主流选择。</p><h3>项目初始化</h3><ul><li>使用Vite创建项目</li><li>配置TypeScript</li><li>设置ESLint和Prettier</li><li>配置路径别名</li></ul><h3>核心配置</h3><ul><li>Vue Router配置</li><li>Pinia状态管理</li><li>Axios请求封装</li><li>环境变量管理</li></ul><h3>开发实践</h3><ul><li>组件类型定义</li><li>Composition API使用</li><li>响应式数据处理</li><li>生命周期管理</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['Vue3', 'TypeScript', 'Vite', 'Pinia'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 38,
    type: 'practical',
    title: 'React + Node.js 全栈项目开发',
    summary: '基于React前端和Node.js后端的全栈项目开发经验',
    content: '<h2>React + Node.js 全栈项目开发</h2><p>全栈开发需要前后端技术的结合。</p><h3>前端技术栈</h3><ul><li>React 18新特性</li><li>React Router路由管理</li><li>Redux Toolkit状态管理</li><li>Ant Design UI组件</li></ul><h3>后端技术栈</h3><ul><li>Express.js框架</li><li>MongoDB数据库</li><li>JWT身份认证</li><li>文件上传处理</li></ul><h3>项目部署</h3><ul><li>Docker容器化</li><li>Nginx反向代理</li><li>PM2进程管理</li><li>CI/CD自动化部署</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['React', 'Node.js', 'MongoDB', 'Express'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 39,
    type: 'practical',
    title: 'Spring Boot 微服务架构设计',
    summary: '基于Spring Boot的微服务架构设计和实现经验',
    content: '<h2>Spring Boot 微服务架构设计</h2><p>微服务架构是现代企业级应用的主流架构。</p><h3>服务拆分</h3><ul><li>业务边界划分</li><li>服务粒度设计</li><li>数据一致性处理</li><li>服务间通信</li></ul><h3>技术组件</h3><ul><li>Spring Cloud Gateway</li><li>Eureka服务注册</li><li>Ribbon负载均衡</li><li>Hystrix熔断器</li></ul><h3>部署运维</h3><ul><li>Docker容器化</li><li>Kubernetes编排</li><li>监控告警</li><li>日志收集</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['Spring Boot', 'Spring Cloud', 'Docker', 'Kubernetes'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 40,
    type: 'practical',
    title: 'Python 数据分析项目实战',
    summary: '基于Python的数据分析项目开发经验',
    content: '<h2>Python 数据分析项目实战</h2><p>数据分析是Python的重要应用领域。</p><h3>数据处理</h3><ul><li>Pandas数据清洗</li><li>NumPy数值计算</li><li>数据可视化</li><li>特征工程</li></ul><h3>机器学习</h3><ul><li>Scikit-learn模型训练</li><li>模型评估和选择</li><li>超参数调优</li><li>模型部署</li></ul><h3>项目流程</h3><ul><li>需求分析</li><li>数据收集</li><li>数据预处理</li><li>模型构建</li><li>结果分析</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['Python', 'Pandas', 'NumPy', 'Scikit-learn'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 41,
    type: 'practical',
    title: '企业宣传PPT数据可视化设计',
    summary: '基于企业宣传演示文稿模板的数据可视化设计经验',
    content: '<h2>企业宣传PPT数据可视化设计</h2><p>数据可视化是PPT设计的重要部分。</p><h3>设计原则</h3><ul><li>数据准确性</li><li>视觉清晰</li><li>重点突出</li><li>易于理解</li></ul><h3>技术实现</h3><ul><li>图表类型选择</li><li>颜色搭配</li><li>数据标注</li><li>动画效果</li></ul>',
    tags: ['项目模板：企业宣传演示文稿'],
    techStack: ['PowerPoint', '数据可视化', '图表设计'],
    projectType: ['演示文稿'],
    projectName: '企业宣传演示文稿',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 42,
    type: 'practical',
    title: '后台管理系统权限控制实现',
    summary: '基于通用后台管理系统模板的权限控制功能实现经验',
    content: '<h2>后台管理系统权限控制实现</h2><p>权限控制是后台管理系统的核心功能。</p><h3>权限模型</h3><ul><li>RBAC权限模型</li><li>角色管理</li><li>权限分配</li><li>菜单权限</li></ul><h3>技术实现</h3><ul><li>JWT Token验证</li><li>路由守卫</li><li>按钮权限</li><li>数据权限</li></ul><h3>安全考虑</h3><ul><li>权限验证</li><li>越权防护</li><li>日志记录</li><li>安全审计</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['Vue3', 'Node.js', 'JWT', 'RBAC'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 43,
    type: 'practical',
    title: '移动端H5页面适配方案',
    summary: '基于移动端H5项目模板的页面适配和兼容性处理经验',
    content: '<h2>移动端H5页面适配方案</h2><p>移动端适配是H5开发的重要环节。</p><h3>适配方案</h3><ul><li>viewport设置</li><li>rem布局</li><li>flexbox布局</li><li>媒体查询</li></ul><h3>兼容性处理</h3><ul><li>浏览器兼容</li><li>设备兼容</li><li>性能优化</li><li>触摸事件</li></ul><h3>开发工具</h3><ul><li>Chrome DevTools</li><li>真机调试</li><li>性能监控</li><li>错误追踪</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['HTML5', 'CSS3', 'JavaScript', '移动端适配'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 44,
    type: 'practical',
    title: '微信小程序开发实战',
    summary: '基于微信小程序项目模板的开发经验',
    content: '<h2>微信小程序开发实战</h2><p>微信小程序是移动端开发的重要平台。</p><h3>开发框架</h3><ul><li>原生开发</li><li>uni-app跨平台</li><li>Taro框架</li><li>mpvue框架</li></ul><h3>核心功能</h3><ul><li>页面路由</li><li>数据绑定</li><li>事件处理</li><li>API调用</li></ul><h3>发布流程</h3><ul><li>代码审核</li><li>版本管理</li><li>灰度发布</li><li>数据统计</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['微信小程序', 'JavaScript', 'WXML', 'WXSS'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 45,
    type: 'practical',
    title: 'Flutter 跨平台应用开发',
    summary: '基于Flutter跨平台项目模板的应用开发经验',
    content: '<h2>Flutter 跨平台应用开发</h2><p>Flutter是Google推出的跨平台开发框架。</p><h3>开发环境</h3><ul><li>Flutter SDK安装</li><li>Android Studio配置</li><li>iOS开发环境</li><li>模拟器调试</li></ul><h3>核心概念</h3><ul><li>Widget树</li><li>状态管理</li><li>路由导航</li><li>网络请求</li></ul><h3>性能优化</h3><ul><li>内存管理</li><li>渲染优化</li><li>包体积优化</li><li>启动优化</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['Flutter', 'Dart', '跨平台开发'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 46,
    type: 'practical',
    title: 'Docker 容器化部署实践',
    summary: '基于Docker容器化项目模板的部署经验',
    content: '<h2>Docker 容器化部署实践</h2><p>Docker是现代应用部署的标准方式。</p><h3>容器化流程</h3><ul><li>Dockerfile编写</li><li>镜像构建</li><li>容器运行</li><li>网络配置</li></ul><h3>编排工具</h3><ul><li>Docker Compose</li><li>Kubernetes</li><li>服务发现</li><li>负载均衡</li></ul><h3>运维管理</h3><ul><li>镜像仓库</li><li>监控告警</li><li>日志管理</li><li>备份恢复</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['Docker', 'Kubernetes', '容器化', 'DevOps'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 47,
    type: 'practical',
    title: '后台管理系统日志审计功能开发',
    summary: '基于通用后台管理系统模板的日志审计功能开发经验',
    content: '<h2>后台管理系统日志审计功能开发</h2><p>日志审计是后台管理系统的重要功能。</p><h3>功能特性</h3><ul><li>操作日志记录</li><li>异常日志监控</li><li>审计追踪</li><li>日志查询</li></ul><h3>技术实现</h3><ul><li>日志中间件</li><li>数据库存储</li><li>日志分析</li><li>告警机制</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['Vue3', 'Node.js', '数据库', '日志系统'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 48,
    type: 'practical',
    title: '企业宣传PPT导出PDF功能设计',
    summary: '基于企业宣传演示文稿模板的PDF导出功能设计经验',
    content: '<h2>企业宣传PPT导出PDF功能设计</h2><p>PDF导出是企业宣传PPT的重要功能。</p><h3>功能特性</h3><ul><li>高质量导出</li><li>批量导出</li><li>自定义设置</li><li>文件压缩</li></ul><h3>技术实现</h3><ul><li>导出参数配置</li><li>页面设置</li><li>字体嵌入</li><li>图片优化</li></ul>',
    tags: ['项目模板：企业宣传演示文稿'],
    techStack: ['PowerPoint', 'PDF导出', '文件处理'],
    projectType: ['演示文稿'],
    projectName: '企业宣传演示文稿',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 49,
    type: 'practical',
    title: '后台管理系统API接口规范设计',
    summary: '基于通用后台管理系统模板的API接口规范设计经验',
    content: '<h2>后台管理系统API接口规范设计</h2><p>API接口规范是后台管理系统的重要基础。</p><h3>设计原则</h3><ul><li>RESTful设计</li><li>统一响应格式</li><li>错误处理机制</li><li>版本控制</li></ul><h3>技术实现</h3><ul><li>接口文档</li><li>参数验证</li><li>错误码设计</li><li>接口测试</li></ul>',
    tags: ['项目模板：通用后台管理系统'],
    techStack: ['API设计', '接口规范', '后端开发'],
    projectType: ['后台管理系统'],
    projectName: '通用后台管理系统',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 50,
    type: 'practical',
    title: '企业宣传PPT动画效果设计',
    summary: '基于企业宣传演示文稿模板的动画效果设计经验',
    content: '<h2>企业宣传PPT动画效果设计</h2><p>动画效果是PPT设计的重要元素。</p><h3>动画类型</h3><ul><li>进入动画</li><li>强调动画</li><li>退出动画</li><li>路径动画</li></ul><h3>设计原则</h3><ul><li>简洁明了</li><li>突出重点</li><li>节奏控制</li><li>视觉协调</li></ul>',
    tags: ['项目模板：企业宣传演示文稿'],
    techStack: ['PowerPoint', '动画设计', '视觉设计'],
    projectType: ['演示文稿'],
    projectName: '企业宣传演示文稿',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 51,
    type: 'practical',
    title: '企业宣传PPT配色方案设计',
    summary: '基于企业宣传演示文稿模板的配色方案设计经验',
    content: '<h2>企业宣传PPT配色方案设计</h2><p>配色方案是PPT设计的基础。</p><h3>配色原则</h3><ul><li>品牌一致性</li><li>视觉层次</li><li>对比度控制</li><li>色彩心理学</li></ul><h3>技术实现</h3><ul><li>主色调选择</li><li>辅助色搭配</li><li>渐变效果</li><li>色彩平衡</li></ul>',
    tags: ['项目模板：企业宣传演示文稿'],
    techStack: ['PowerPoint', '色彩设计', '视觉设计'],
    projectType: ['演示文稿'],
    projectName: '企业宣传演示文稿',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 52,
    type: 'practical',
    title: '企业宣传PPT图标素材应用',
    summary: '基于企业宣传演示文稿模板的图标素材应用经验',
    content: '<h2>企业宣传PPT图标素材应用</h2><p>图标素材是PPT设计的重要元素。</p><h3>图标类型</h3><ul><li>线性图标</li><li>填充图标</li><li>3D图标</li><li>插画图标</li></ul><h3>应用技巧</h3><ul><li>风格统一</li><li>大小协调</li><li>颜色搭配</li><li>位置布局</li></ul>',
    tags: ['项目模板：企业宣传演示文稿'],
    techStack: ['PowerPoint', '图标设计', '素材应用'],
    projectType: ['演示文稿'],
    projectName: '企业宣传演示文稿',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  },
  {
    id: 53,
    type: 'practical',
    title: '企业宣传PPT排版规范设计',
    summary: '基于企业宣传演示文稿模板的排版规范设计经验',
    content: '<h2>企业宣传PPT排版规范设计</h2><p>排版规范是PPT设计的基础。</p><h3>排版原则</h3><ul><li>对齐原则</li><li>对比原则</li><li>重复原则</li><li>亲密原则</li></ul><h3>技术实现</h3><ul><li>网格系统</li><li>字体层次</li><li>间距控制</li><li>留白设计</li></ul>',
    tags: ['项目模板：企业宣传演示文稿'],
    techStack: ['PowerPoint', '排版设计', '视觉设计'],
    projectType: ['演示文稿'],
    projectName: '企业宣传演示文稿',
    createTime: generateRandomTime(timeRange.start, timeRange.end)
  }
];

// 获取所有实战笔记
export const getAllPracticalNotes = (): PracticalNote[] => {
  return practicalNotes;
};

// 根据ID获取实战笔记
export const getPracticalNoteById = (id: number): PracticalNote | undefined => {
  return practicalNotes.find(note => note.id === id);
};

// 搜索实战笔记
export const searchPracticalNotes = (query: string): PracticalNote[] => {
  const lowerQuery = query.toLowerCase();
  return practicalNotes.filter(note => 
    note.title.toLowerCase().includes(lowerQuery) ||
    note.summary.toLowerCase().includes(lowerQuery) ||
    note.tags.some(tag => tag.toLowerCase().includes(lowerQuery))
  );
};

// 根据标签获取实战笔记
export const getPracticalNotesByTag = (tag: string): PracticalNote[] => {
  return practicalNotes.filter(note => note.tags.includes(tag));
};

// 获取实战笔记统计信息
export const getPracticalNotesStats = () => {
  return {
    total: practicalNotes.length,
    byProjectType: practicalNotes.reduce((acc, note) => {
      note.projectType.forEach(type => {
        acc[type] = (acc[type] || 0) + 1;
      });
      return acc;
    }, {} as Record<string, number>)
  };
}; 