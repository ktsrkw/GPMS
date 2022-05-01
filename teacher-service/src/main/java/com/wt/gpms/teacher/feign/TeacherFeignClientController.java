package com.wt.gpms.teacher.feign;

import com.wt.gpms.teacher.pojo.Teacher;
import com.wt.gpms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feign")
public class TeacherFeignClientController {
    @Autowired
    TeacherService teacherService;

    @RequestMapping("/all")
    public List<Teacher> getAllTeachers(){
        return teacherService.allTeachers();
    }

    @RequestMapping("/{tId}")
    public Teacher getTeacherById(@PathVariable("tId") Integer tId){
        return teacherService.selectTeacherById(tId);
    }

    @PostMapping("/update")
    public Integer updateTeacherInfo(@RequestBody Teacher teacher){
        return teacherService.updateTeacher(teacher);
    }

    @GetMapping("/delete/{tId}")
    public Integer deleteTeacher(@PathVariable("tId") Integer tId){
        return teacherService.deleteTeacherById(tId);
    }

    @PostMapping("/add")
    public Integer addTeacher(@RequestBody Teacher teacher){
        return teacherService.insertTeacher(teacher);
    }

    @PostMapping("/search")
    public List<Teacher> searchTeachers(@RequestBody String searchString){
        return teacherService.searchTeachers(searchString);
    }
}
