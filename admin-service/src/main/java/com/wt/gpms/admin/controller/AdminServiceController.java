package com.wt.gpms.admin.controller;

import com.wt.gpms.admin.pojo.Admin;
import com.wt.gpms.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminServiceController {
    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping("/all")
    public List<Admin> getAllAdmins(){
        return adminService.allAdmins();
    }

}
