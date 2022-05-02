package com.wt.gpms.teacher.pojo;

import java.util.Date;

public class ProjectStage
{
    private Integer psId;

    /** 课题id */
    private Integer pId;

    /** 阶段名称 */
    private String name;

    /** 阶段状态 */
    private Integer status;

    /** 阶段描述与要求 */
    private String content;

    /** 阶段开始时间 */
    private Date startTime;

    /** 阶段完成时间 */
    private Date endTime;

    public ProjectStage() {
    }

    public ProjectStage(Integer psId, Integer pId, String name, Integer status, String content, Date startTime, Date endTime) {
        this.psId = psId;
        this.pId = pId;
        this.name = name;
        this.status = status;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ProjectStage(Integer pId, String name, Integer status) {
        this.pId = pId;
        this.name = name;
        this.status = status;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ProjectStage{" +
                "psId=" + psId +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
