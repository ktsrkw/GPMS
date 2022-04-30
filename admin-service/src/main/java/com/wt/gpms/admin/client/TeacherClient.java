package com.wt.gpms.admin.client;

import com.wt.gpms.admin.pojo.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("teacher-service")
public interface TeacherClient {

    @RequestMapping("/teacher/all")
    List<Teacher> getAllTeachers();

    @RequestMapping("/teacher/{tId}")
    Teacher getTeacherById(@PathVariable("tId") Integer tId);

    @PostMapping("/teacher/update")
    Integer updateTeacherInfo(@RequestBody Teacher teacher);

    @GetMapping("/teacher/delete/{tId}")
    Integer deleteTeacher(@PathVariable("tId") Integer tId);

    @PostMapping("/teacher/add")
    Integer addTeacher(@RequestBody Teacher teacher);

    @PostMapping("/teacher/search")
    List<Teacher> searchTeachers(@RequestBody String searchString);
}
