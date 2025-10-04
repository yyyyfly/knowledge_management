-- 测试数据 SQL 脚本
-- 所有数据的创建者为 admin 用户
-- 使用前请确保数据库已创建相关表结构

-- 清理现有数据（可选，谨慎使用）
-- DELETE FROM km_time_node WHERE rec_creator = 'admin';
-- DELETE FROM km_task WHERE rec_creator = 'admin';
-- DELETE FROM km_project WHERE rec_creator = 'admin';
-- DELETE FROM km_note WHERE rec_creator = 'admin';
-- DELETE FROM km_honor WHERE rec_creator = 'admin';
-- DELETE FROM km_summary WHERE rec_creator = 'admin';

-- =============================================
-- 项目数据 (km_project)
-- =============================================

-- 工程建设项目
INSERT INTO km_project (id, name, description, category, status, start_date, end_date, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(1, '个人知识管理系统', '基于Vue3和SpringBoot的全栈知识管理平台，支持笔记、项目、任务的综合管理', 'construction', 'inProgress', '2024-01-15', '2024-06-30', 'Vue3,SpringBoot,MySQL', 'admin', NOW(), 'admin', NOW(), 0),
(2, '在线教育平台', '为中小学生提供在线学习和作业辅导的教育平台', 'construction', 'planning', '2024-03-01', '2024-12-31', 'React,Node.js,MongoDB', 'admin', NOW(), 'admin', NOW(), 0),
(3, '智能问答机器人', '基于大语言模型的智能问答系统，支持多轮对话和上下文理解', 'construction', 'completed', '2023-09-01', '2024-01-20', 'Python,FastAPI,LLM', 'admin', NOW(), 'admin', NOW(), 0);

-- 战争行动项目（实战任务）
INSERT INTO km_project (id, name, description, category, status, start_date, end_date, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(4, '软件设计师备考', '2024年上半年软件设计师考试备考计划', 'defense', 'inProgress', '2024-01-01', '2024-05-25', '考试,软考,中级', 'admin', NOW(), 'admin', NOW(), 0),
(5, '前端面试准备', '准备大厂前端面试，包括算法、框架原理、项目经验', 'defense', 'inProgress', '2024-02-01', '2024-04-30', '面试,前端,算法', 'admin', NOW(), 'admin', NOW(), 0),
(6, '副业项目-博客运营', '技术博客内容创作和运营，提升个人影响力', 'defense', 'inProgress', '2024-01-01', '2024-12-31', '博客,写作,副业', 'admin', NOW(), 'admin', NOW(), 0);

-- 外交行动项目（对外推广）
INSERT INTO km_project (id, name, description, category, status, start_date, end_date, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(7, 'GitHub开源项目推广', '将个人优质项目开源到GitHub，吸引star和contributor', 'diplomatic', 'inProgress', '2024-01-15', '2024-06-30', 'GitHub,开源,推广', 'admin', NOW(), 'admin', NOW(), 0),
(8, '技术社区分享', '在掘金、CSDN等平台分享技术文章，积累粉丝', 'diplomatic', 'inProgress', '2024-01-01', '2024-12-31', '技术分享,社区,文章', 'admin', NOW(), 'admin', NOW(), 0),
(9, '线下技术交流会', '参加本地技术meetup，拓展人脉资源', 'diplomatic', 'planning', '2024-04-01', '2024-12-31', '线下活动,交流,人脉', 'admin', NOW(), 'admin', NOW(), 0);

-- =============================================
-- 任务数据 (km_task)
-- =============================================

-- 个人知识管理系统的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(1, '前端框架搭建', '使用Vue3+Vite+TypeScript搭建前端项目框架', 1, 'completed', '2024-01-20 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(2, '后端API开发', '基于SpringBoot开发RESTful API接口', 1, 'in-progress', '2024-02-28 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(3, '数据库设计', '设计MySQL数据库表结构，包括用户、笔记、项目等模块', 1, 'completed', '2024-01-25 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(4, '用户认证系统', '实现JWT登录注册、权限控制功能', 1, 'completed', '2024-02-05 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(5, '笔记管理模块', '实现笔记的增删改查、分类、标签功能', 1, 'in-progress', '2024-03-15 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- 在线教育平台的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(6, '需求调研', '调研市场上现有在线教育平台的功能和用户痛点', 2, 'pending', '2024-03-10 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(7, '原型设计', '设计平台的UI原型和交互流程', 2, 'pending', '2024-03-20 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- 智能问答机器人的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(8, '模型选型', '对比不同大语言模型的性能和成本', 3, 'completed', '2023-09-15 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(9, 'API接口开发', '开发问答接口，支持流式输出', 3, 'completed', '2023-11-30 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(10, '前端界面开发', '开发对话界面，支持markdown渲染', 3, 'completed', '2023-12-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- 软件设计师备考的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(11, '教材通读', '通读软件设计师官方教材，做好笔记', 4, 'in-progress', '2024-03-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(12, '真题练习', '完成近5年的真题练习和分析', 4, 'pending', '2024-05-15 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(13, '模拟考试', '进行3次全真模拟考试', 4, 'pending', '2024-05-20 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- 前端面试准备的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(14, '算法刷题', '在LeetCode上刷200道中等难度题目', 5, 'in-progress', '2024-04-15 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(15, 'Vue源码阅读', '深入学习Vue3响应式原理和虚拟DOM', 5, 'in-progress', '2024-03-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(16, '项目经验整理', '整理3-5个有亮点的项目经验', 5, 'pending', '2024-04-20 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- 博客运营的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(17, '每周发布技术文章', '保持每周至少发布1篇高质量技术文章', 6, 'in-progress', '2024-12-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(18, 'SEO优化', '优化博客的SEO，提升搜索引擎排名', 6, 'pending', '2024-06-30 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- GitHub开源项目推广的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(19, '完善项目README', '编写详细的项目说明文档和使用指南', 7, 'in-progress', '2024-02-28 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(20, '录制演示视频', '录制项目功能演示视频，上传到B站', 7, 'pending', '2024-03-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(21, '社交媒体推广', '在Twitter、微博等平台宣传项目', 7, 'pending', '2024-04-30 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- 技术社区分享的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(22, '发布Vue3实战文章', '分享Vue3项目开发的最佳实践', 8, 'completed', '2024-01-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(23, '发布SpringBoot文章', '分享SpringBoot微服务架构经验', 8, 'in-progress', '2024-03-15 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(24, '互动维护粉丝', '回复评论，维护粉丝关系', 8, 'in-progress', '2024-12-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- 线下技术交流会的任务
INSERT INTO km_task (id, name, description, project_id, status, deadline, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(25, '寻找合适的活动', '关注本地技术社区，找到合适的meetup', 9, 'pending', '2024-04-15 23:59:59', 'admin', NOW(), 'admin', NOW(), 0),
(26, '准备分享主题', '准备一个技术分享的主题和PPT', 9, 'pending', '2024-05-31 23:59:59', 'admin', NOW(), 'admin', NOW(), 0);

-- =============================================
-- 笔记数据 (km_note)
-- =============================================

-- 碎片笔记
INSERT INTO km_note (id, title, content, type, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(1, 'Vue3 Composition API 要点', '# Vue3 Composition API\n\n1. setup函数是组件的入口\n2. ref和reactive用于创建响应式数据\n3. computed用于计算属性\n4. watch和watchEffect用于监听', 'fragment', 'Vue3,前端', 'admin', NOW(), 'admin', NOW(), 0),
(2, 'MySQL索引优化技巧', '# MySQL索引优化\n\n1. 为WHERE、ORDER BY字段建立索引\n2. 避免使用SELECT *\n3. 使用EXPLAIN分析查询\n4. 联合索引遵循最左匹配原则', 'fragment', 'MySQL,数据库,性能优化', 'admin', NOW(), 'admin', NOW(), 0),
(3, 'SpringBoot常用注解', '# SpringBoot注解\n\n- @RestController：标记REST控制器\n- @Autowired：自动注入\n- @Service：标记服务层\n- @Repository：标记数据访问层', 'fragment', 'SpringBoot,Java', 'admin', NOW(), 'admin', NOW(), 0);

-- 框架笔记
INSERT INTO km_note (id, title, content, type, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(4, 'Vue3完整学习路线', '# Vue3学习路线\n\n## 基础部分\n1. 模板语法\n2. 响应式基础\n3. 组件基础\n\n## 进阶部分\n1. Composition API\n2. 高级组件\n3. 路由和状态管理\n\n## 实战部分\n1. 项目搭建\n2. 性能优化\n3. 部署上线', 'framework', 'Vue3,前端,学习路线', 'admin', NOW(), 'admin', NOW(), 0),
(5, 'Java后端技术栈', '# Java后端技术栈\n\n## 基础\n- Java SE\n- 集合框架\n- 并发编程\n\n## 框架\n- Spring全家桶\n- MyBatis\n- Redis\n\n## 分布式\n- 微服务\n- 消息队列\n- 分布式事务', 'framework', 'Java,后端,技术栈', 'admin', NOW(), 'admin', NOW(), 0);

-- 求学笔记
INSERT INTO km_note (id, title, content, type, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(6, '数据结构-树', '# 树的基本概念\n\n## 二叉树\n- 前序遍历\n- 中序遍历\n- 后序遍历\n\n## 平衡树\n- AVL树\n- 红黑树\n\n## 应用\n- 二叉搜索树\n- 堆', 'study', '数据结构,算法', 'admin', NOW(), 'admin', NOW(), 0),
(7, '计算机网络-HTTP协议', '# HTTP协议\n\n## 请求方法\n- GET：获取资源\n- POST：提交数据\n- PUT：更新资源\n- DELETE：删除资源\n\n## 状态码\n- 2xx：成功\n- 3xx：重定向\n- 4xx：客户端错误\n- 5xx：服务器错误', 'study', '计算机网络,HTTP', 'admin', NOW(), 'admin', NOW(), 0);

-- 背诵笔记
INSERT INTO km_note (id, title, content, type, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(8, '设计模式-单例模式', '# 单例模式\n\n```java\npublic class Singleton {\n    private static Singleton instance;\n    \n    private Singleton() {}\n    \n    public static synchronized Singleton getInstance() {\n        if (instance == null) {\n            instance = new Singleton();\n        }\n        return instance;\n    }\n}\n```', 'memorization', '设计模式,Java', 'admin', NOW(), 'admin', NOW(), 0);

-- 刷题笔记
INSERT INTO km_note (id, title, content, type, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(9, 'LeetCode 206 反转链表', '# 反转链表\n\n## 思路\n使用迭代法，依次反转指针方向\n\n## 代码\n```java\npublic ListNode reverseList(ListNode head) {\n    ListNode prev = null;\n    ListNode curr = head;\n    while (curr != null) {\n        ListNode next = curr.next;\n        curr.next = prev;\n        prev = curr;\n        curr = next;\n    }\n    return prev;\n}\n```\n\n## 复杂度\n- 时间：O(n)\n- 空间：O(1)', 'exercise', 'LeetCode,算法,链表', 'admin', NOW(), 'admin', NOW(), 0),
(10, 'LeetCode 1 两数之和', '# 两数之和\n\n## 思路\n使用哈希表存储已遍历的数字\n\n## 代码\n```java\npublic int[] twoSum(int[] nums, int target) {\n    Map<Integer, Integer> map = new HashMap<>();\n    for (int i = 0; i < nums.length; i++) {\n        int complement = target - nums[i];\n        if (map.containsKey(complement)) {\n            return new int[] { map.get(complement), i };\n        }\n        map.put(nums[i], i);\n    }\n    throw new IllegalArgumentException("No solution");\n}\n```', 'exercise', 'LeetCode,算法,哈希表', 'admin', NOW(), 'admin', NOW(), 0);

-- 实战笔记
INSERT INTO km_note (id, title, content, type, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(11, '个人知识管理系统-用户认证实现', '# 用户认证实现\n\n## 技术选型\n- JWT：无状态认证\n- MD5：密码加密\n- 拦截器：权限控制\n\n## 实现步骤\n1. 创建User实体\n2. 实现登录接口\n3. 生成JWT token\n4. 配置拦截器\n5. 前端存储token\n\n## 踩坑记录\n- JWT密钥不能太短\n- 拦截器要排除登录接口', 'practical', 'JWT,认证,SpringBoot', 'admin', NOW(), 'admin', NOW(), 0),
(12, '个人知识管理系统-数据隔离方案', '# 数据隔离方案\n\n## 需求\n每个用户只能看到自己创建的数据\n\n## 实现\n1. 表中添加rec_creator字段\n2. 创建时自动设置创建者\n3. 查询时添加创建者过滤\n4. 使用ThreadLocal传递用户信息\n\n## 核心代码\n```java\n// 拦截器中设置用户\nUserContext.setCurrentUser(username);\n\n// Controller中获取用户\nString creator = UserContext.getCurrentUser();\nentity.setRecCreator(creator);\n```', 'practical', '数据隔离,权限,SpringBoot', 'admin', NOW(), 'admin', NOW(), 0);

-- =============================================
-- 荣誉数据 (km_honor)
-- =============================================

INSERT INTO km_honor (id, title, description, category, achieved_time, icon, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(1, '全国计算机等级考试二级', '通过全国计算机等级考试二级C语言程序设计', 'construction', '2022-03-15 10:00:00', 'certificate', 'admin', NOW(), 'admin', NOW(), 0),
(2, '英语四级证书', '大学英语四级考试通过，成绩550分', 'construction', '2021-12-20 14:30:00', 'certificate', 'admin', NOW(), 'admin', NOW(), 0),
(3, '优秀毕业生', '获得校级优秀毕业生称号', 'defense', '2023-06-30 09:00:00', 'trophy', 'admin', NOW(), 'admin', NOW(), 0),
(4, 'GitHub 100 Stars', '个人开源项目在GitHub获得100+ stars', 'diplomatic', '2024-01-10 20:00:00', 'star', 'admin', NOW(), 'admin', NOW(), 0),
(5, '技术博客1000粉丝', '掘金技术博客粉丝突破1000人', 'diplomatic', '2023-11-20 18:00:00', 'users', 'admin', NOW(), 'admin', NOW(), 0);

-- =============================================
-- 总结数据 (km_summary)
-- =============================================

INSERT INTO km_summary (id, title, type, period, content, tags, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(1, '2024年1月总结', 'monthly', '2024-01', '# 一月总结\n\n## 学习方面\n- 完成了Vue3基础学习\n- 开始SpringBoot项目实战\n- 刷了30道算法题\n\n## 项目方面\n- 启动个人知识管理系统项目\n- 完成前端框架搭建\n- 完成数据库设计\n\n## 不足\n- 学习时间不够集中\n- 算法练习量偏少\n\n## 下月计划\n- 完成知识管理系统后端开发\n- 继续算法刷题', '月总结,学习,项目', 'admin', NOW(), 'admin', NOW(), 0),
(2, '2024年2月总结', 'monthly', '2024-02', '# 二月总结\n\n## 学习方面\n- 深入学习MyBatis框架\n- 完成JWT认证学习\n- 刷了25道算法题\n\n## 项目方面\n- 完成后端API开发\n- 实现用户认证功能\n- 完成数据隔离\n\n## 收获\n- 对全栈开发有了更深理解\n- 提升了问题解决能力\n\n## 下月计划\n- 完成前后端联调\n- 优化系统性能', '月总结,学习,项目', 'admin', NOW(), 'admin', NOW(), 0),
(3, '第一季度总结', 'quarterly', '2024-Q1', '# Q1季度总结\n\n## 主要成就\n1. 完成个人知识管理系统开发\n2. GitHub项目获得100+ stars\n3. 技术博客发布12篇文章\n\n## 学习进展\n- 前端：Vue3全家桶熟练掌握\n- 后端：SpringBoot项目实战经验丰富\n- 算法：LeetCode刷题100+\n\n## 下季度目标\n1. 准备软考\n2. 学习微服务架构\n3. 开始准备面试', '季度总结,回顾,规划', 'admin', NOW(), 'admin', NOW(), 0);

-- =============================================
-- 时间节点数据 (km_time_node)
-- =============================================

-- 战争行动（考试相关）
INSERT INTO km_time_node (id, title, description, time, type, status, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(1, '软件设计师考试报名', '2024年上半年软件设计师考试网上报名', '2024-03-15 09:00:00', 'war', 'completed', 'admin', NOW(), 'admin', NOW(), 0),
(2, '软件设计师考试', '软件设计师正式考试，上午综合知识，下午案例分析', '2024-05-25 09:00:00', 'war', 'not-started', 'admin', NOW(), 'admin', NOW(), 0),
(3, '前端面试-阿里巴巴', '阿里巴巴前端岗位技术面试', '2024-04-10 14:00:00', 'war', 'not-started', 'admin', NOW(), 'admin', NOW(), 0),
(4, '项目Deadline-知识管理系统', '个人知识管理系统项目第一版上线截止日期', '2024-06-30 23:59:59', 'war', 'not-started', 'admin', NOW(), 'admin', NOW(), 0);

-- 外交行动（对外推广）
INSERT INTO km_time_node (id, title, description, time, type, status, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) VALUES
(5, '技术分享会', '参加本地前端技术分享会，分享Vue3实战经验', '2024-04-20 14:00:00', 'diplomatic', 'not-started', 'admin', NOW(), 'admin', NOW(), 0),
(6, '开源项目推广', 'GitHub项目推广活动，目标获得500+ stars', '2024-06-30 23:59:59', 'diplomatic', 'not-started', 'admin', NOW(), 'admin', NOW(), 0),
(7, 'B站视频发布', '发布个人知识管理系统的介绍和使用教程视频', '2024-05-15 20:00:00', 'diplomatic', 'not-started', 'admin', NOW(), 'admin', NOW(), 0);

-- 重置自增ID（可选）
-- ALTER TABLE km_project AUTO_INCREMENT = 10;
-- ALTER TABLE km_task AUTO_INCREMENT = 27;
-- ALTER TABLE km_note AUTO_INCREMENT = 13;
-- ALTER TABLE km_honor AUTO_INCREMENT = 6;
-- ALTER TABLE km_summary AUTO_INCREMENT = 4;
-- ALTER TABLE km_time_node AUTO_INCREMENT = 8;

-- 插入完成提示
SELECT '测试数据插入完成！所有数据的创建者为 admin' AS message;

