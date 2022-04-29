package com.wt.gpms.admin.pojo;

public class Admin {
    private Integer adminId;
    private String adminNo;
    private String name;
    private String password;

    public Admin() {
    }

    public Admin(Integer adminId, String adminNo, String name, String password) {
        this.adminId = adminId;
        this.adminNo = adminNo;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminNo='" + adminNo + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
