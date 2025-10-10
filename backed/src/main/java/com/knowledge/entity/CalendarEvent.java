package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 日历事件实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CalendarEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 事件标题
     */
    private String eventTitle;

    /**
     * 事件日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date eventDate;

    /**
     * 事件类型：custom-自定义, holiday-节假日
     */
    private String eventType;

    /**
     * 重复类型：once-单次, daily-每天, monthly-每月, yearly-每年
     */
    private String repeatType;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 事件颜色
     */
    private String color;

    /**
     * 是否显示倒计时
     */
    private Boolean showCountdown;
}

