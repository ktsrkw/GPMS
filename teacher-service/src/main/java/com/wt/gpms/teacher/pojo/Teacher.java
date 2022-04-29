package com.wt.gpms.teacher.pojo;

public class Teacher {
    private Integer tId;
    private String tNo;
    private String name;
    private String gender;
    private String school;
    private String title;
    private String tele;
    private String email;
    private String password;

    public Teacher() {
    }

    public Teacher(Integer tId, String tNo, String name, String gender, String school, String title, String tele, String email, String password) {
        this.tId = tId;
        this.tNo = tNo;
        this.name = name;
        this.gender = gender;
        this.school = school;
        this.title = title;
        this.tele = tele;
        this.email = email;
        this.password = password;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String gettNo() {
        return tNo;
    }

    public void settNo(String tNo) {
        this.tNo = tNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "tId=" + tId +
                ", tNo=" + tNo +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", school='" + school + '\'' +
                ", title='" + title + '\'' +
                ", tele='" + tele + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
