package com.wt.gpms.student.pojo;

public class ProjectFile
{
    private Integer pfId;

    /** 课题id */
    private Integer pId;

    /** 课题阶段id */
    private Integer psId;

    /** 文件访问url */
    private String url;

    /** 文件名称 */
    private String title;

    /** 文件保存路径 */
    private String path;

    private String userType;

    public ProjectFile() {
    }

    public ProjectFile(Integer pfId, Integer pId, Integer psId, String url, String title, String path, String userType) {
        this.pfId = pfId;
        this.pId = pId;
        this.psId = psId;
        this.url = url;
        this.title = title;
        this.path = path;
        this.userType = userType;
    }

    public Integer getPfId() {
        return pfId;
    }

    public void setPfId(Integer pfId) {
        this.pfId = pfId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "ProjectFile{" +
                "pfId=" + pfId +
                ", pId=" + pId +
                ", psId=" + psId +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
