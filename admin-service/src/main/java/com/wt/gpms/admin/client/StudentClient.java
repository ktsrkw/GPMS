package com.wt.gpms.admin.client;

import com.wt.gpms.admin.pojo.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("student-service")
public interface StudentClient {

    @RequestMapping("/student/all")
    List<Student> getAllStudents();

}
