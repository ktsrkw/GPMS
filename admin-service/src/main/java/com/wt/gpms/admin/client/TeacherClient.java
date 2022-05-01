package com.wt.gpms.admin.client;

import com.wt.gpms.admin.pojo.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("teacher-service")
public interface TeacherClient {

    @RequestMapping("/teacher/feign/all")
    List<Teacher> getAllTeachers();

    @RequestMapping("/teacher/feign/{tId}")
    Teacher getTeacherById(@PathVariable("tId") Integer tId);

    @PostMapping("/teacher/feign/update")
    Integer updateTeacherInfo(@RequestBody Teacher teacher);

    @GetMapping("/teacher/feign/delete/{tId}")
    Integer deleteTeacher(@PathVariable("tId") Integer tId);

    @PostMapping("/teacher/feign/add")
    Integer addTeacher(@RequestBody Teacher teacher);

    @PostMapping("/teacher/feign/search")
    List<Teacher> searchTeachers(@RequestBody String searchString);
}
