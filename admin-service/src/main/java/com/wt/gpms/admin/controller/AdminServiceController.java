package com.wt.gpms.admin.controller;

import com.wt.gpms.admin.client.StudentClient;
import com.wt.gpms.admin.client.TeacherClient;
import com.wt.gpms.admin.pojo.Admin;
import com.wt.gpms.admin.pojo.Student;
import com.wt.gpms.admin.pojo.Teacher;
import com.wt.gpms.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminServiceController {
    @Autowired
    AdminService adminService;

    @Autowired
    StudentClient studentClient;

    @Autowired
    TeacherClient teacherClient;

    @ResponseBody
    @RequestMapping("/all")
    public List<Admin> getAllAdmins(){
        return adminService.allAdmins();
    }

    @ResponseBody
    @RequestMapping("/student/all")
    public List<Student> getAllStudents(){
        return studentClient.getAllStudents();
    }

    @ResponseBody
    @RequestMapping("/teacher/all")
    public List<Teacher> getAllTeachers(){
        return teacherClient.getAllTeachers();
    }

    @RequestMapping("/student/manage")
    public String toStudentManagePage(Model model){
        model.addAttribute("students", studentClient.getAllStudents());
        return "student-manage";
    }

}
