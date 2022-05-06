package com.wt.gpms.admin.controller;

import com.wt.gpms.admin.client.TeacherClient;
import com.wt.gpms.admin.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeacherManageController {

    @Autowired
    TeacherClient teacherClient;

    @RequestMapping("/teacher/manage")
    public String toTeacherManagePage(Model model){
        model.addAttribute("teachers",teacherClient.getAllTeachers());
        return "teacher-manage";
    }

    @GetMapping("/teacher/manage/update/{tId}")
    public String toTeacherUpdatePage(Model model, @PathVariable("tId") Integer tId){
        model.addAttribute("teacher",teacherClient.getTeacherById(tId));
        return "teacher-manage-update";
    }

    @GetMapping("/teacher/manage/add")
    public String toTeacherManageAddPage(){
        return "teacher-manage-add";
    }

    @PostMapping("/teacher/manage/update")
    public String updateTeacherInfo(Teacher teacher){
        teacherClient.updateTeacherInfo(teacher);
        return "redirect:/teacher/manage";
    }

    @GetMapping("/teacher/manage/delete/{tId}")
    public String deleteTeacher(@PathVariable("tId") Integer tId){
        teacherClient.deleteTeacher(tId);

        //TODO:删除教师时要删除此教师的立题信息

        return "redirect:/teacher/manage";
    }

    @PostMapping("/teacher/manage/add")
    public String addTeacher(Teacher teacher){
        //账户默认密码
        teacher.setPassword("123456");
        teacherClient.addTeacher(teacher);
        return "redirect:/teacher/manage";
    }

    @PostMapping("/teacher/manage/search")
    public String searchTeachers(Model model, String searchString){
        model.addAttribute("teachers", teacherClient.searchTeachers(searchString));
        return "teacher-manage";
    }

    @ResponseBody
    @RequestMapping("/teacher/all")
    public List<Teacher> getAllTeachers(){
        return teacherClient.getAllTeachers();
    }
}
