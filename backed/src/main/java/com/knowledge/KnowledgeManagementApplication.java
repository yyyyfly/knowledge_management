package com.knowledge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 个人知识管理系统主启动类
 *
 * @author system
 * @date 2025-10-04
 */
@SpringBootApplication
@MapperScan("com.knowledge.mapper")
public class KnowledgeManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeManagementApplication.class, args);
        System.out.println("========================================");
        System.out.println("个人知识管理系统启动成功！");
        System.out.println("API访问地址: http://localhost:8080/api");
        System.out.println("========================================");
    }
}





