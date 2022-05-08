package com.wt.gpms.teacher.pojo;

import java.util.Date;

public class ProjectProcess
{

    private Integer ppId;

    /** 课题id */
    private Integer pId;

    /** 记录次数 */
    private Long times;

    /** 记录时间 */
    private Date recordTime;

    /** 记录内容 */
    private String content;

    /** 教师评语 */
    private String comments;

    public ProjectProcess() {
    }

    public ProjectProcess(Integer ppId, Integer pId, Long times, Date recordTime, String content, String comments) {
        this.ppId = ppId;
        this.pId = pId;
        this.times = times;
        this.recordTime = recordTime;
        this.content = content;
        this.comments = comments;
    }

    public Integer getPpId() {
        return ppId;
    }

    public void setPpId(Integer ppId) {
        this.ppId = ppId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ProjectProcess{" +
                "ppId='" + ppId + '\'' +
                ", pId='" + pId + '\'' +
                ", times=" + times +
                ", recordTime=" + recordTime +
                ", content='" + content + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
