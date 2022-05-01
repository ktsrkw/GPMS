package com.wt.gpms.teacher.controller;

import com.wt.gpms.teacher.pojo.Teacher;
import com.wt.gpms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeacherServiceController {

    @Autowired
    TeacherService teacherService;

    @ResponseBody
    @RequestMapping("/all")
    public List<Teacher> getAllTeachers(){
        return teacherService.allTeachers();
    }

    @ResponseBody
    @RequestMapping("/{tId}")
    public Teacher getTeacherById(@PathVariable("tId") Integer tId){
        return teacherService.selectTeacherById(tId);
    }

    @ResponseBody
    @PostMapping("/update")
    public Integer updateTeacherInfo(@RequestBody Teacher teacher){
        return teacherService.updateTeacher(teacher);
    }

    @ResponseBody
    @GetMapping("/delete/{tId}")
    public Integer deleteTeacher(@PathVariable("tId") Integer tId){
        return teacherService.deleteTeacherById(tId);
    }

    @ResponseBody
    @PostMapping("/add")
    public Integer addTeacher(@RequestBody Teacher teacher){
        return teacherService.insertTeacher(teacher);
    }

    @ResponseBody
    @PostMapping("/search")
    public List<Teacher> searchTeachers(@RequestBody String searchString){
        return teacherService.searchTeachers(searchString);
    }

    //路由到登陆页面
    @GetMapping({"/login.html","/"})
    public String toTeacherLoginPage(){
        return "teacher-login";
    }

    //教师登录
    @PostMapping("/login")
    public String teacherLogin(String tNo, String password, HttpSession session, Model model){
        Teacher teacher = teacherService.selectTeacherByNo(tNo);
        if (teacher != null){
            //如果查到用户且密码一致
            if (teacher.getPassword().equals(password)){
                //登录成功的标识，在session中放入教师编号与教师名等等
                session.setAttribute("tId",teacher.gettId());
                session.setAttribute("tNo",teacher.gettNo());
                session.setAttribute("name",teacher.getName());
                session.setAttribute("title",teacher.getTitle());
                return "redirect:/project";
            } else {
                //密码错误
                model.addAttribute("msg", "密码错误");
                return "teacher-login";
            }
        } else {
            //用户不存在
            model.addAttribute("msg", "教师号不存在");
            return "teacher-login";
        }
    }

    //教师登出
    @GetMapping("/logout")
    public String teacherLogout(HttpSession session){
        session.removeAttribute("tId");
        session.removeAttribute("tNo");
        session.removeAttribute("name");
        session.removeAttribute("title");
        return "redirect:/login.html";
    }

}



