export interface ProjectImplementation {
  id: number;
  templateId: number;
  techStack: string;
  summary: string;
  notes: string[];
}

export const projectImplementations: ProjectImplementation[] = [
  {
    id: 101,
    templateId: 1,
    techStack: 'JavaWeb (SpringBoot + MyBatis + Thymeleaf)',
    summary: '实现了通用后台管理系统，涵盖用户、公告、数据管理等模块，权限细粒度控制采用RBAC模型。',
    notes: [
      '用户管理采用JWT鉴权，支持多角色。',
      '公告模块支持富文本编辑和定时发布。',
      '数据管理支持分页、导出Excel。',
      '日志模块集成了操作日志和异常日志。'
    ]
  },
  {
    id: 102,
    templateId: 1,
    techStack: 'PythonWeb (Django + DRF + ElementUI)',
    summary: '用Django实现后台管理系统，文件上传用阿里云OSS，权限用Django自带权限系统。',
    notes: [
      '采用Django Admin快速生成管理界面。',
      '文件上传集成阿里云OSS。',
      '数据表支持自定义字段和动态表单。'
    ]
  },
  {
    id: 103,
    templateId: 1,
    techStack: 'React + Node (Express + MongoDB)',
    summary: '前后端分离实现，前端用Antd，后端用Express，数据存储MongoDB。',
    notes: [
      '权限管理用JWT+RBAC。',
      '评论和消息模块用WebSocket实现实时推送。',
      '仪表盘用echarts实现数据可视化。'
    ]
  },
  {
    id: 201,
    templateId: 2,
    techStack: 'PowerPoint',
    summary: '用PPT制作企业宣传模板，统一配色和动画，导出PDF。',
    notes: [
      '首页封面突出品牌形象。',
      '数据展示页用SmartArt和图表。',
      '动画效果适度，突出重点。'
    ]
  },
  {
    id: 202,
    templateId: 2,
    techStack: 'Figma',
    summary: '用Figma设计企业宣传PPT，便于多人协作和快速修改。',
    notes: [
      '所有页面组件化，便于复用。',
      '配色和字体风格统一。',
      '导出为PPT和PDF格式。'
    ]
  }
]; 