package com.wt.gpms.teacher.controller;

import com.wt.gpms.teacher.pojo.Project;
import com.wt.gpms.teacher.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ProjectManageController {
    @Autowired
    ProjectService projectService;

    //路由到教师的课题页面
    @GetMapping("/project")
    public String toTeacherProjectPage(HttpSession session, Model model){
        //拿到教师所有的立题信息
        Project project = new Project();
        project.settId((Integer) session.getAttribute("tId"));
        model.addAttribute("projects",projectService.selectProjectList(project));

        return "project";
    }

    //路由到新增立题页面
    @GetMapping("/project/add")
    public String toProjectAddPage(){
        return "project-add";
    }

    //教师新增立题
    @PostMapping("/project/add")
    public String projectAdd(Project project, HttpSession session){
        //设置一些基本信息后提交新增
        project.setStatus("待审批");
        project.setCreateTime(new Date());
        project.settId((Integer) session.getAttribute("tId"));
        projectService.insertProject(project);

        return "redirect:/project";
    }

}
