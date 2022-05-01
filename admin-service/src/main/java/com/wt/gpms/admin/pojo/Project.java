package com.wt.gpms.admin.pojo;

import java.util.Date;

public class Project
{
    /** $column.columnComment */
    private Integer pId;

    /** 课题编号 */
    private String pNo;

    /** 题目名称 */
    private String title;

    /** 课题简介 */
    private String content;

    /** 课题类型 */
    private String type;

    /** 课题性质 */
    private String nature;

    /** 课题状态 */
    private String status;

    /** 课题成绩 */
    private String point;

    /** 学生选题时间 */
    private Date selectionTime;

    /** 选题学生id */
    private Integer sId;

    /** 创建时间 */
    private Date createTime;

    /** 立题教师id */
    private Integer tId;

    /** 审批通过时间 */
    private Date approveTime;

    /** 审批管理员id */
    private Integer adminId;

    public Project() {
    }

    public Project(Integer pId, String pNo, String title, String content, String type, String nature, String status, String point, Date selectionTime, Integer sId, Date createTime, Integer tId, Date approveTime, Integer adminId) {
        this.pId = pId;
        this.pNo = pNo;
        this.title = title;
        this.content = content;
        this.type = type;
        this.nature = nature;
        this.status = status;
        this.point = point;
        this.selectionTime = selectionTime;
        this.sId = sId;
        this.createTime = createTime;
        this.tId = tId;
        this.approveTime = approveTime;
        this.adminId = adminId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Date getSelectionTime() {
        return selectionTime;
    }

    public void setSelectionTime(Date selectionTime) {
        this.selectionTime = selectionTime;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "pId=" + pId +
                ", pNo='" + pNo + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", nature='" + nature + '\'' +
                ", status='" + status + '\'' +
                ", point='" + point + '\'' +
                ", selectionTime=" + selectionTime +
                ", sId=" + sId +
                ", createTime=" + createTime +
                ", tId=" + tId +
                ", approveTime=" + approveTime +
                ", adminId=" + adminId +
                '}';
    }
}
