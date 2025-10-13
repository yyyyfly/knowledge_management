package com.knowledge.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 打卡记录实体类
 */
@Data
public class CheckinRecord {
    private Long id;
    private Long checkinItemId;
    private String username;
    private LocalDateTime checkinTime;
    private String cycleKey; // 周期标识，如：2025-10-11, 2025-W41, 2025-10, 2025-Q4, 2025
    private String recCreator;
    private LocalDateTime recCreateTime;
    private String recRevisor;
    private LocalDateTime recReviseTime;
    private Integer archFlag;
}

