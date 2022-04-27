package com.wt.gpms.admin.client;

import com.wt.gpms.admin.pojo.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("teacher-service")
public interface TeacherClient {

    @RequestMapping("/teacher/all")
    List<Teacher> getAllTeachers();

}
