package com.wt.gpms.teacher.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.wt.gpms.teacher.pojo.Project;
import com.wt.gpms.teacher.pojo.ProjectProcess;
import com.wt.gpms.teacher.service.ProjectProcessService;
import com.wt.gpms.teacher.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectProcessController {
    @Autowired
    ProjectProcessService projectProcessService;

    @Autowired
    ProjectService projectService;

    //跳转到项目列表
    @GetMapping("/project/process")
    public String toProjectListPage(HttpSession session,
                                    Model model){
        //拿到所有状态为“进行中”及以后的项目列表送往前台
        Project projectSearch = new Project();
        projectSearch.settId((Integer) session.getAttribute("tId"));
        List<Project> projectList = projectService.selectProjectList(projectSearch);

        List<Project> projects = new ArrayList<>();

        for (Project project : projectList) {
            //过滤出状态为“进行中”及以后的项目
            if (!project.getStatus().equals("待审批") && !project.getStatus().equals("待选题")){
                projects.add(project);
            }
        }

        model.addAttribute("projects",projects);

        return "project-process";

    }

    //跳转到某一课题的过程记录列表页面
    @GetMapping("/project/process/list/{pId}")
    public String toProcessListPage(@PathVariable("pId") Integer pId,
                                    HttpSession session,
                                    Model model){
        //检查当前教师是否为此课题的教师
        Project projectConfirm = projectService.selectProjectById(pId);
        if (projectConfirm.gettId().equals((Integer) session.getAttribute("tId"))){
            //符合，拿到此项目的过程记录列表
            ProjectProcess projectProcessSearch = new ProjectProcess();
            projectProcessSearch.setpId(pId);
            List<ProjectProcess> projectProcessList = projectProcessService.selectProjectProcessList(projectProcessSearch);

            model.addAttribute("projectProcessList",projectProcessList);
            model.addAttribute("project",projectConfirm);

            return "project-process-list";

        } else {
            //教师不符
            model.addAttribute("msg","你不是此课题的教师，无法查看");
            return "fail";
        }

    }

    @GetMapping("/project/process/update/{ppId}")
    public String toProcessUpdatePage(@PathVariable("ppId") Integer ppId,
                                      HttpSession session,
                                      Model model){
        ProjectProcess projectProcess = projectProcessService.selectProjectProcessById(ppId);
        Project projectConfirm = projectService.selectProjectById(projectProcess.getpId());
        //检查当前教师是否为此课题的教师
        if (projectConfirm.gettId().equals((Integer) session.getAttribute("tId"))){
            //符合，将过程记录信息送到页面
            model.addAttribute("projectProcess",projectProcess);
            model.addAttribute("project",projectConfirm);

            return "project-process-update";
        } else {
            //教师不符
            model.addAttribute("msg","你不是此课题的教师，无法查看");
            return "fail";
        }
    }

    //更新批语
    @PostMapping("/project/process/update")
    public String updateProcess(ProjectProcess projectProcess,
                                HttpSession session,
                                Model model){
        Project projectConfirm = projectService.selectProjectById(projectProcess.getpId());
        //检查当前教师是否为此课题的教师
        if (projectConfirm.gettId().equals((Integer) session.getAttribute("tId"))){
            //符合，更新
            projectProcessService.updateProjectProcess(projectProcess);

            return "redirect:/project/process/list/" + projectProcess.getpId();
        } else {
            //教师不符
            model.addAttribute("msg","你不是此课题的教师，无法查看");
            return "fail";
        }
    }

}
