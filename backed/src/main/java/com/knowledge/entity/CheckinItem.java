package com.knowledge.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 打卡项目实体类
 */
@Data
public class CheckinItem {
    private Long id;
    private String title;
    private String description;
    private String frequency; // daily, weekly, monthly, quarterly, yearly
    private String username;
    private String status; // active, inactive
    private String recCreator;
    private LocalDateTime recCreateTime;
    private String recRevisor;
    private LocalDateTime recReviseTime;
    private Integer archFlag;
}

