package com.wt.gpms.student.pojo;

public class Student {
    private Integer sId;
    private Integer sNo;
    private String name;
    private String gender;
    private String school;
    private String major;
    private String classNo;
    private String tele;
    private String email;

    public Student() {
    }

    public Student(Integer sId, Integer sNo, String name, String gender, String school, String major, String classNo, String tele, String email) {
        this.sId = sId;
        this.sNo = sNo;
        this.name = name;
        this.gender = gender;
        this.school = school;
        this.major = major;
        this.classNo = classNo;
        this.tele = tele;
        this.email = email;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
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

    @Override
    public String toString() {
        return "Student{" +
                "sId=" + sId +
                ", sNo=" + sNo +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", classNo='" + classNo + '\'' +
                ", tele='" + tele + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
