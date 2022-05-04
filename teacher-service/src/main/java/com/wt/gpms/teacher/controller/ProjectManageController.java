package com.wt.gpms.teacher.controller;

import com.wt.gpms.teacher.pojo.Project;
import com.wt.gpms.teacher.pojo.ProjectStage;
import com.wt.gpms.teacher.service.ProjectService;
import com.wt.gpms.teacher.service.ProjectStageService;
import com.wt.gpms.teacher.service.SystemStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProjectManageController {
    @Autowired
    ProjectService projectService;

    @Autowired
    SystemStatusService systemStatusService;

    @Autowired
    ProjectStageService projectStageService;

    //路由到教师的课题页面
    @GetMapping("/project")
    public String toTeacherProjectPage(HttpSession session, Model model){
        //拿到该教师教师所有的立题信息
        Project project = new Project();
        project.settId((Integer) session.getAttribute("tId"));
        model.addAttribute("projects",projectService.selectProjectList(project));

        return "project";
    }

    //路由到新增立题页面
    @GetMapping("/project/add")
    public String toProjectAddPage(Model model){
        //判断立题功能是否开启
        model.addAttribute("createStatus",systemStatusService.getCreateStatus());

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

        //将这个立题的各阶段也新增进数据库
        //首先要得到刚才新增数据的pId，然后将阶段与pId绑定在一起
        List<Project> projectList = projectService.selectProjectList(project);
        Integer pId = projectList.get(0).getpId();
        projectStageService.insertProjectStage(new ProjectStage(pId,"立题表提交阶段",0));
        projectStageService.insertProjectStage(new ProjectStage(pId,"任务书提交阶段",0));
        projectStageService.insertProjectStage(new ProjectStage(pId,"论文翻译提交阶段",0));
        projectStageService.insertProjectStage(new ProjectStage(pId,"开题报告提交阶段",0));
        projectStageService.insertProjectStage(new ProjectStage(pId,"中期检查阶段",0));
        projectStageService.insertProjectStage(new ProjectStage(pId,"论文提交阶段",0));
        projectStageService.insertProjectStage(new ProjectStage(pId,"答辩阶段",0));

        return "redirect:/project";
    }

    //搜索当前教师的立题
    @PostMapping("/project/search")
    public String searchProjects(String searchString, Model model, HttpSession session){
        List<Project> projectList = projectService.searchProjects(searchString);
        List<Project> projects = new ArrayList<>();
        for (Project project : projectList) {
            if (project.gettId().equals((Integer)session.getAttribute("tId"))){
                projects.add(project);
            }
        }
        model.addAttribute("projects",projects);

        return "project";
    }

    //到课题详细信息页面
    @GetMapping("/project/{pId}")
    public String projectInfo(@PathVariable("pId") Integer pId, Model model){
        model.addAttribute("project", projectService.selectProjectById(pId));

        return "project-info";
    }

    //TODO:教师撤销（删除）立题

}
