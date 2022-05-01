package com.wt.gpms.student.feign;

import com.wt.gpms.student.pojo.Student;
import com.wt.gpms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feign")
public class StudentFeignClientController {
    @Autowired
    StudentService studentService;

    @RequestMapping("/all")
    public List<Student> getAllStudents(){
        return studentService.allStudents();
    }

    @RequestMapping("/{sId}")
    public Student getStudentById(@PathVariable("sId") Integer sId){
        return studentService.getStudentById(sId);
    }

    @PostMapping("/update")
    public Integer updateStudentInfo(@RequestBody Student student){
        return studentService.updateStudentInfo(student);
    }

    @GetMapping("/delete/{sId}")
    public Integer deleteStudentById(@PathVariable("sId") Integer sId){
        return studentService.deleteStudentById(sId);
    }

    @PostMapping("/search")
    public List<Student> searchStudent(@RequestBody String searchString){
        return studentService.searchStudent(searchString);
    }

    @PostMapping("/add")
    public Integer addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }
}
