package com.wt.gpms.admin.controller;

import com.wt.gpms.admin.client.StudentClient;
import com.wt.gpms.admin.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentManageController {

    @Autowired
    StudentClient studentClient;

    //路由到student-manage.html页面
    @RequestMapping("/student/manage")
    public String toStudentManagePage(Model model){
        model.addAttribute("students", studentClient.getAllStudents());
        return "student-manage";
    }

    //路由到student-manage-update.html页面
    @GetMapping("/student/manage/update/{sId}")
    public String toStudentManageUpdatePage(Model model, @PathVariable("sId") Integer sId){
        //根据id拿到学生信息
        model.addAttribute("student",studentClient.getStudentById(sId));
        return "student-manage-update";
    }

    //学生信息修改，修改完成后重定向到student-manage.html页面
    @PostMapping("/student/manage/update")
    public String updateStudentInfo(Student student){
        //调用接口修改信息
        studentClient.updateStudentInfo(student);

        return "redirect:/student/manage";
    }

    //根据id删除学生信息，修改完成后重定向到student-manage.html页面
    @GetMapping("/student/manage/delete/{sId}")
    public String deleteStudentById(@PathVariable("sId") Integer sId){
        //调用接口删除
        studentClient.deleteStudentById(sId);

        return "redirect:/student/manage";
    }

    //搜索功能，根据输入模糊搜索
    @PostMapping("/student/manage/search")
    public String searchStudent(Model model, String searchString){
        model.addAttribute("students",studentClient.searchStudent(searchString));
        return "student-manage";
    }

    //路由到student-manage-add.html页面
    @GetMapping("/student/manage/add")
    public String toStudentManageAddPage(){
        return "student-manage-add";
    }

    //添加学生功能
    @PostMapping("/student/manage/add")
    public String addStudent(Student student){
        //账户默认密码
        student.setPassword("123456");
        studentClient.addStudent(student);

        return "redirect:/student/manage";
    }

    @ResponseBody
    @RequestMapping("/student/all")
    public List<Student> getAllStudents(){
        return studentClient.getAllStudents();
    }

}
