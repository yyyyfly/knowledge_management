package com.knowledge.entity;

import java.util.Date;

/**
 * 项目笔记关联实体类
 */
public class ProjectNoteRelation {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 笔记ID
     */
    private Integer noteId;

    /**
     * 笔记类型
     */
    private String noteType;

    /**
     * 创建人
     */
    private String recCreator;

    /**
     * 创建时间
     */
    private Date recCreateTime;

    /**
     * 修改人
     */
    private String recRevisor;

    /**
     * 修改时间
     */
    private Date recReviseTime;

    /**
     * 归档标志
     */
    private String archFlag;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getRecCreator() {
        return recCreator;
    }

    public void setRecCreator(String recCreator) {
        this.recCreator = recCreator;
    }

    public Date getRecCreateTime() {
        return recCreateTime;
    }

    public void setRecCreateTime(Date recCreateTime) {
        this.recCreateTime = recCreateTime;
    }

    public String getRecRevisor() {
        return recRevisor;
    }

    public void setRecRevisor(String recRevisor) {
        this.recRevisor = recRevisor;
    }

    public Date getRecReviseTime() {
        return recReviseTime;
    }

    public void setRecReviseTime(Date recReviseTime) {
        this.recReviseTime = recReviseTime;
    }

    public String getArchFlag() {
        return archFlag;
    }

    public void setArchFlag(String archFlag) {
        this.archFlag = archFlag;
    }

    @Override
    public String toString() {
        return "ProjectNoteRelation{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", noteId=" + noteId +
                ", noteType='" + noteType + '\'' +
                ", recCreator='" + recCreator + '\'' +
                ", recCreateTime=" + recCreateTime +
                ", recRevisor='" + recRevisor + '\'' +
                ", recReviseTime=" + recReviseTime +
                ", archFlag='" + archFlag + '\'' +
                '}';
    }
}





























