package com.wt.gpms.student.pojo;

public class SystemStatus {
    private String id;
    private Integer status;

    public SystemStatus() {
    }

    public SystemStatus(String id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SystemStatus{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }
}
