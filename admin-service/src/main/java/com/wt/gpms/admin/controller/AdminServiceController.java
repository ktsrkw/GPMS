package com.wt.gpms.admin.controller;

import com.wt.gpms.admin.client.StudentClient;
import com.wt.gpms.admin.client.TeacherClient;
import com.wt.gpms.admin.pojo.Admin;
import com.wt.gpms.admin.pojo.Student;
import com.wt.gpms.admin.pojo.Teacher;
import com.wt.gpms.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminServiceController {
    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping("/all")
    public List<Admin> getAllAdmins(){
        return adminService.allAdmins();
    }

    //路由到登陆页面
    @GetMapping("/login.html")
    public String toAdminLoginPage(){
        return "admin-login";
    }

    //登录功能
    @PostMapping("/login")
    public String adminLogin(String username, String password, HttpSession session, Model model){
        Admin admin = adminService.selectAdminByName(username);
        if (admin != null){
            //如果查到用户且密码一致
            if (admin.getPassword().equals(password)){
                //登录成功的标识，在session中放入用户名
                session.setAttribute("username", admin.getName());
                return "redirect:/student/manage";
            } else {
                //密码错误
                model.addAttribute("msg", "密码错误");
                return "admin-login";
            }
        } else {
            //用户不存在
            model.addAttribute("msg", "用户不存在");
            return "admin-login";
        }
    }

    //登出功能
    @GetMapping("/logout")
    public String adminLogout(HttpSession session){
        //销毁session
        //session.invalidate();
        session.removeAttribute("username");
        return "redirect:/login.html";
    }

}
