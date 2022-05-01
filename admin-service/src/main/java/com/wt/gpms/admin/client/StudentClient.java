package com.wt.gpms.admin.client;

import com.wt.gpms.admin.pojo.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("student-service")
public interface StudentClient {

    @RequestMapping("/student/feign/all")
    List<Student> getAllStudents();

    @RequestMapping("/student/feign/{sId}")
    Student getStudentById(@PathVariable("sId") Integer sId);

    @PostMapping("/student/feign/update")
    Integer updateStudentInfo(@RequestBody Student student);

    @GetMapping("/student/feign/delete/{sId}")
    Integer deleteStudentById(@PathVariable("sId") Integer sId);

    @PostMapping("/student/feign/search")
    List<Student> searchStudent(@RequestBody String searchString);

    @PostMapping("/student/feign/add")
    Integer addStudent(@RequestBody Student student);
}
