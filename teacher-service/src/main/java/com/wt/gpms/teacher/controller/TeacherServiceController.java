package com.wt.gpms.teacher.controller;

import com.wt.gpms.teacher.pojo.Teacher;
import com.wt.gpms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherServiceController {

    @Autowired
    TeacherService teacherService;

    @ResponseBody
    @RequestMapping("/all")
    public List<Teacher> getAllTeachers(){
        return teacherService.allTeachers();
    }
}
