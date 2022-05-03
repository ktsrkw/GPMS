package com.wt.gpms.student.controller;

import com.wt.gpms.student.pojo.Project;
import com.wt.gpms.student.pojo.ProjectStage;
import com.wt.gpms.student.pojo.Teacher;
import com.wt.gpms.student.service.ProjectService;
import com.wt.gpms.student.service.ProjectStageService;
import com.wt.gpms.student.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    ProjectStageService projectStageService;

    //路由到project页面
    @GetMapping("/project")
    public String toProjectPage(HttpSession session, Model model){
        //判断此学生是否选题
        Project project = new Project();
        project.setsId((Integer) session.getAttribute("sId"));
        List<Project> projectList = projectService.selectProjectList(project);
        if (projectList.size() > 0){
            //学生有选题
            //拿到课题信息
            project = projectList.get(0);
            model.addAttribute("project", project);
            //拿到该课题的教师信息
            model.addAttribute("teacher", teacherService.selectTeacherById(project.gettId()));
            //拿到该课题的进度信息
            ProjectStage projectStage = new ProjectStage();
            projectStage.setpId(project.getpId());
            List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStage);
            model.addAttribute("projectStageList",projectStageList);

            return "project";
        } else {
            //学生尚未选题，重定向到project-choose页面
            return "redirect:/project/choose";
        }
    }

    //路由到projectChoose页面
    @GetMapping("/project/choose")
    public String toProjectChoosePage(Model model, HttpSession session){
        //判断此学生是否选题
        Project project = new Project();
        project.setsId((Integer) session.getAttribute("sId"));
        List<Project> projects = projectService.selectProjectList(project);
        if (projects.size() > 0){
            //学生有选题，进到退选页面
            model.addAttribute("project", projects.get(0));
            return "project-choose-cancel";
        } else {
            //学生尚未选题
            //拿到还未选题的课题
            Project project1 = new Project();
            project1.setStatus("待选题");
            List<Project> originalProjectList = projectService.selectProjectList(project1);

            //从中筛选出学生所在学院的选题
            List<Project> projectList = new ArrayList<>();
            Map<Project, Teacher> projectTeacherBind = new HashMap<>();
            for (Project project2 : originalProjectList) {
                //如果课题所属学院与该同学学院相符，则留下此课题
                if (teacherService.selectTeacherById(project2.gettId()).getSchool().equals((String) session.getAttribute("school"))){
                    projectList.add(project2);
                    projectTeacherBind.put(project2,teacherService.selectTeacherById(project2.gettId()));
                }
            }

            //将符合的课题送到前台
            model.addAttribute("projects",projectList);
            model.addAttribute("projectTeacherBind",projectTeacherBind);
            return "project-choose";
        }
    }

    //学生选题
    @GetMapping("/project/choose/{pId}")
    public String projectChoose(@PathVariable("pId") Integer pId,
                                HttpSession session){
        //选题，既更改project的sId等字段使之与学生关联起来
        Project project = projectService.selectProjectById(pId);
        project.setsId((Integer) session.getAttribute("sId"));
        project.setSelectionTime(new Date());
        project.setStatus("进行中");
        projectService.updateProject(project);

        //同时选题的那一刻意味着”立题表阶段的开始“，为该课题的”立题表阶段的开始“设置startTime
        ProjectStage projectStage = new ProjectStage();
        projectStage.setpId(pId);
        projectStage.setName("立题表提交阶段");
        List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStage);
        projectStage = projectStageList.get(0);
        projectStage.setStartTime(new Date());
        projectStage.setStatus(1);
        projectStageService.updateProjectStage(projectStage);

        return "redirect:/project";
    }

}
