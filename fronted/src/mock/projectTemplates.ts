export interface ProjectRequirement {
  name: string;
  desc: string;
  children?: ProjectRequirement[];
}

export interface ProjectTemplate {
  id: number;
  domain: string;
  name: string;
  description: string;
  requirements: ProjectRequirement[];
  commonFeatures: ProjectRequirement[];
}

export const projectTemplates: ProjectTemplate[] = [
  {
    id: 1,
    domain: '网页开发',
    name: '通用后台管理系统',
    description: '适用于各类企业/组织的后台管理系统，支持多角色、多权限、数据管理等。',
    requirements: [
      { name: '用户管理', desc: '注册、登录、权限分配、用户信息维护' },
      { name: '公告管理', desc: '发布、编辑、删除公告，支持富文本' },
      { name: '数据增删改查', desc: '通用表格、分页、搜索、导出' },
      { name: '文件上传', desc: '支持图片、文档等多类型文件上传' },
      { name: '评论与消息', desc: '评论系统、站内信、通知推送' },
      { name: '统计与报表', desc: '数据可视化、图表、导出' },
      { name: '日志与审计', desc: '操作日志、异常日志、审计追踪' },
      { name: '仪表盘', desc: '首页数据总览、快捷入口' }
    ],
    commonFeatures: [
      { name: '权限管理', desc: '基于角色的权限控制，支持细粒度权限配置' },
      { name: '多角色支持', desc: '支持管理员、普通用户等多种角色，权限隔离' },
      { name: '响应式布局', desc: '适配桌面端、平板、手机等多种设备屏幕' },
      { name: '国际化', desc: '支持中英文等多语言切换，便于国际化部署' },
      { name: '主题切换', desc: '支持亮色/暗色主题，可自定义主题配色' },
      { name: 'API接口规范', desc: 'RESTful API设计，统一的接口规范和文档' }
    ]
  },
  {
    id: 2,
    domain: '视觉设计',
    name: '企业宣传演示文稿',
    description: '适用于企业宣传、产品介绍、路演等场景的专业演示文稿模板。',
    requirements: [
      { name: '首页封面', desc: '企业logo、主题、主KV' },
      { name: '公司介绍', desc: '企业文化、发展历程、荣誉资质' },
      { name: '产品/服务介绍', desc: '产品功能、优势、案例' },
      { name: '数据展示', desc: '图表、数据可视化' },
      { name: '团队介绍', desc: '团队成员、分工、照片' },
      { name: '联系方式', desc: '电话、邮箱、二维码' }
    ],
    commonFeatures: [
      { name: '统一配色', desc: '企业VI色彩规范，保持品牌视觉一致性' },
      { name: '动画效果', desc: '流畅的页面切换和元素动效，提升演示体验' },
      { name: '图标素材', desc: '提供丰富的矢量图标库，支持自定义替换' },
      { name: '排版规范', desc: '统一的字体、间距、布局标准，确保专业性' },
      { name: '导出PDF', desc: '支持高质量PDF导出，便于分享和打印' }
    ]
  }
]; 