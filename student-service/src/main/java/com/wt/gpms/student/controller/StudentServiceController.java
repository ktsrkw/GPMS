package com.wt.gpms.student.controller;

import com.wt.gpms.student.pojo.Student;
import com.wt.gpms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @RequestMapping("/{sId}")
    public Student getStudentById(@PathVariable("sId") Integer sId){
        return studentService.getStudentById(sId);
    }

    @ResponseBody
    @PostMapping("/update")
    public Integer updateStudentInfo(@RequestBody Student student){
        return studentService.updateStudentInfo(student);
    }

    @ResponseBody
    @GetMapping("/delete/{sId}")
    public Integer deleteStudentById(@PathVariable("sId") Integer sId){
        return studentService.deleteStudentById(sId);
    }

    @ResponseBody
    @PostMapping("/search")
    public List<Student> searchStudent(@RequestBody String searchString){
        return studentService.searchStudent(searchString);
    }

    @ResponseBody
    @PostMapping("/add")
    public Integer addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

}
