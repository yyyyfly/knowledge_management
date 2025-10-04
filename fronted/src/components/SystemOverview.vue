<template>
  <section class="mb-8 animate-fade-in">
    <div class="bg-dark-800 rounded-xl shadow-card p-6 mb-6">
      <h2 class="text-2xl font-bold mb-6">系统概述</h2>
      
      <!-- 系统定位与核心目标 -->
      <div class="mb-8">
        <h3 class="text-xl font-semibold mb-4">系统定位与核心目标</h3>
        <div class="space-y-4">
          <div class="flex items-start">
            <div class="flex-shrink-0 w-6 h-6 rounded-full bg-primary/20 flex items-center justify-center text-primary mt-1 mr-3">
              <i class="fas fa-book"></i>
            </div>
            <div>
              <h4 class="font-medium">素材组</h4>
              <p class="text-dark-400">为日常学习、灵感、知识碎片的总汇，知识的资源池。</p>
            </div>
          </div>
          
          <div class="flex items-start">
            <div class="flex-shrink-0 w-6 h-6 rounded-full bg-secondary/20 flex items-center justify-center text-secondary mt-1 mr-3">
              <i class="fas fa-cogs"></i>
            </div>
            <div>
              <h4 class="font-medium">行动组</h4>
              <p class="text-dark-400">为所有实际项目、工作、工程、考试、对外交付的开展场域，是知识的实践与转化区。</p>
            </div>
          </div>
          
          <div class="flex items-start">
            <div class="flex-shrink-0 w-6 h-6 rounded-full bg-accent/20 flex items-center justify-center text-accent mt-1 mr-3">
              <i class="fas fa-star"></i>
            </div>
            <div>
              <h4 class="font-medium">首脑组</h4>
              <p class="text-dark-400">为方向决策、大局规划、理论总结与战略复盘的"指挥中枢"，优化素材与行动间流转，驱动持续成长。</p>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 分区结构及功能定义 -->
      <div class="mb-8">
        <h3 class="text-xl font-semibold mb-4">分区结构及功能定义</h3>
        
        <!-- 素材组 -->
        <div class="bg-dark-600/30 rounded-xl p-5 border border-dark-600/50 mb-6">
          <div class="flex items-center mb-4">
            <div class="w-10 h-10 rounded-full bg-primary/20 flex items-center justify-center text-primary mr-3">
              <i class="fas fa-book"></i>
            </div>
            <h4 class="text-lg font-semibold">素材组</h4>
          </div>
          
          <div class="space-y-4">
            <div>
              <h5 class="font-medium">用途</h5>
              <ul class="list-disc pl-5 text-dark-400 space-y-1">
                <li>承载日常所有学习、积累、输出、灵感、素材的"第一存放点"</li>
                <li>按素材性质设六大分组，便于归类与后续调取</li>
              </ul>
            </div>
            
            <div>
              <h5 class="font-medium">六大分组说明</h5>
              <ul class="list-disc pl-5 text-dark-400 space-y-1">
                <li>基础笔记：自主探索、原理归纳，立足于通用技能和系统知识框架</li>
                <li>求学笔记：参与课程、外部班级学习的记录</li>
                <li>书籍笔记：各类书籍阅读摘要与洞见</li>
                <li>刷题笔记：做题过程中的技巧总结、典型题型与答疑札记</li>
                <li>实战笔记：实操尝试或临时项目实践中的心得、踩坑、改进点，不直接挂钩大项目</li>
                <li>碎片笔记：随时记录的灵感、短内容、网络摘录等</li>
              </ul>
            </div>
          </div>
        </div>
        
        <!-- 行动组 -->
        <div class="bg-dark-600/30 rounded-xl p-5 border border-dark-600/50 mb-6">
          <div class="flex items-center mb-4">
            <div class="w-10 h-10 rounded-full bg-secondary/20 flex items-center justify-center text-secondary mr-3">
              <i class="fas fa-briefcase"></i>
            </div>
            <h4 class="text-lg font-semibold">行动组</h4>
          </div>
          
          <div class="space-y-4">
            <div>
              <h5 class="font-medium">用途</h5>
              <p class="text-dark-400">将素材转化为成果，承担项目落地、工作任务、对外交付等"行动主场"</p>
            </div>
            
            <div>
              <h5 class="font-medium">下设三类</h5>
              <ul class="list-disc pl-5 text-dark-400 space-y-1">
                <li>防守战争：代表工作、考试、对外需求等外部驱动的项目</li>
                <li>工程建设：自研/主动发起的工程项目</li>
                <li>外交行动：对外合作、联动、自主对外输出</li>
              </ul>
            </div>
          </div>
        </div>
        
        <!-- 首脑组 -->
        <div class="bg-dark-600/30 rounded-xl p-5 border border-dark-600/50">
          <div class="flex items-center mb-4">
            <div class="w-10 h-10 rounded-full bg-accent/20 flex items-center justify-center text-accent mr-3">
              <i class="fas fa-star"></i>
            </div>
            <h4 class="text-lg font-semibold">首脑组</h4>
          </div>
          
          <div class="space-y-4">
            <div>
              <h5 class="font-medium">用途</h5>
              <p class="text-dark-400">统揽全局，从战略高度规划方向，制定决策，总结规律，优化知识管理系统运转</p>
            </div>
            
            <div>
              <h5 class="font-medium">下设七项</h5>
              <ul class="list-disc pl-5 text-dark-400 space-y-1">
                <li>信息统计：对素材组、行动组数据进行多维度统计分析，辅助决策</li>
                <li>系统决策：基于数据与趋势分析，制定阶段性目标、资源分配与优先级决策</li>
                <li>系统总结：定期对整体系统运行情况进行全面总结，发现问题与改进点</li>
                <li>理论体系：将零散知识系统化，构建个人知识体系框架</li>
                <li>实战体系：提炼项目实践中的方法论、流程、模式，形成可复用的实战体系</li>
                <li>备考工程：针对重要考试、认证的专门备考计划与执行</li>
                <li>项目制作：将积累的素材和能力整合，制作有价值的输出成果</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
// 组件逻辑可以在这里添加
</script>

<style scoped>
/* 组件样式可以在这里添加 */
</style> 