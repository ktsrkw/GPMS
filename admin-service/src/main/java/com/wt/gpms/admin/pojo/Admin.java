package com.wt.gpms.admin.pojo;

public class Admin {
    private Integer adminId;
    private String adminNo;
    private String name;

    public Admin() {
    }

    public Admin(Integer adminId, String adminNo, String name) {
        this.adminId = adminId;
        this.adminNo = adminNo;
        this.name = name;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
