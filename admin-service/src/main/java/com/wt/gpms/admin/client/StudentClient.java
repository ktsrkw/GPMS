package com.wt.gpms.admin.client;

import com.wt.gpms.admin.pojo.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("student-service")
public interface StudentClient {

    @RequestMapping("/student/all")
    List<Student> getAllStudents();

    @RequestMapping("/student/{sId}")
    Student getStudentById(@PathVariable("sId") Integer sId);

    @PostMapping("/student/update")
    Integer updateStudentInfo(@RequestBody Student student);

    @GetMapping("/student/delete/{sId}")
    Integer deleteStudentById(@PathVariable("sId") Integer sId);

    @PostMapping("/student/search")
    List<Student> searchStudent(@RequestBody String searchString);

    @PostMapping("/student/add")
    Integer addStudent(@RequestBody Student student);
}
