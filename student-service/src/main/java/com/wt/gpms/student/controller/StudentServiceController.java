package com.wt.gpms.student.controller;

import com.wt.gpms.student.pojo.Student;
import com.wt.gpms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentServiceController {

    @Autowired
    StudentService studentService;

    @ResponseBody
    @RequestMapping("/all")
    public List<Student> getAllStudents(){
        return studentService.allStudents();
    }
}
