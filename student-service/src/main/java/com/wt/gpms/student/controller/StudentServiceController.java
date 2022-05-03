package com.wt.gpms.student.controller;

import com.wt.gpms.student.pojo.Student;
import com.wt.gpms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    //路由到学生登录页
    @GetMapping({"/","/login.html"})
    public String toStudentLoginPage(){
        return "student-login";
    }

    //学生登录功能
    @PostMapping("/login")
    public String studentLogin(String sNo, String password, HttpSession session, Model model){
        Student student = studentService.selectStudentByNo(sNo);
        if (student != null){
            //如果查到用户且密码一致
            if (student.getPassword().equals(password)){
                //登录成功的标识，在session中放入学号姓名等等
                session.setAttribute("sId",student.getsId());
                session.setAttribute("sNo",student.getsNo());
                session.setAttribute("name",student.getName());
                session.setAttribute("classNo",student.getClassNo());
                session.setAttribute("school",student.getSchool());
                return "redirect:/project";
            } else {
                //密码错误
                model.addAttribute("msg", "密码错误");
                return "student-login";
            }
        } else {
            //用户不存在
            model.addAttribute("msg", "学号不存在");
            return "student-login";
        }
    }

    //登出
    @GetMapping("/logout")
    public String studentLogout(HttpSession session){
        session.removeAttribute("sId");
        session.removeAttribute("sNo");
        session.removeAttribute("name");
        session.removeAttribute("classNo");
        session.removeAttribute("school");

        return "redirect:/login.html";
    }

}
