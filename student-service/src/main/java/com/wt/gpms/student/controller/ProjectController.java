package com.wt.gpms.student.controller;

import com.wt.gpms.student.pojo.*;
import com.wt.gpms.student.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    ProjectStageService projectStageService;

    @Autowired
    ProjectFileService projectFileService;

    @Autowired
    ProjectProcessService projectProcessService;

    @Autowired
    SystemStatusService systemStatusService;

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
            //开启选题逻辑前，检查选题功能是否被管理员关闭
            if (systemStatusService.getChooseStatus().equals(1)){
                //选题功能开启，展示未选课题
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
            } else {
                //选题功能关闭
                model.addAttribute("msg","选题功能没有开启，无法选题，可联系管理员开启");
            }
            model.addAttribute("chooseStatus",systemStatusService.getChooseStatus());
            return "project-choose";
        }
    }

    //学生选题
    @GetMapping("/project/choose/{pId}")
    public String projectChoose(@PathVariable("pId") Integer pId,
                                HttpSession session,
                                Model model){
        //判断此学生是否选题
        Project project1 = new Project();
        project1.setsId((Integer) session.getAttribute("sId"));
        List<Project> projects = projectService.selectProjectList(project1);
        if (projects.size() > 0){
            //学生有选题，进到退选页面
            model.addAttribute("project", projects.get(0));
            return "project-choose-cancel";
        } else {
            //学生尚未选题
            //开启选题逻辑前，检查选题功能是否被管理员关闭
            if (systemStatusService.getChooseStatus().equals(1)){
                //选题功能开启
                //进入选题逻辑前再次判断此课题是否已被选
                Project project = projectService.selectProjectById(pId);
                if (!project.getStatus().equals("待选题")){
                    //如果此时课题已被选，选题失败
                    model.addAttribute("msg","手慢了一步，该课题已被选");
                    return "project-choose-fail";
                }
                //进入选题逻辑，既更改project的sId等字段使之与学生关联起来
                project.setsId((Integer) session.getAttribute("sId"));
                project.setSelectionTime(new Date());
                project.setStatus("进行中");
                projectService.updateProject(project);

                //同时选题的那一刻意味着”立题表阶段的开始“，为该课题的”立题表阶段的开始“设置startTime
                ProjectStage projectStage = new ProjectStage();
                projectStage.setpId(pId);
                projectStage.setpId(pId);
                projectStage.setName("立题表提交阶段");
                List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStage);
                projectStage = projectStageList.get(0);
                projectStage.setStartTime(new Date());
                projectStage.setStatus(1);
                projectStageService.updateProjectStage(projectStage);
            } else {
                //选题功能关闭
                model.addAttribute("chooseStatus",systemStatusService.getChooseStatus());
                return "project-choose";
            }
            return "redirect:/project";
        }
    }

    //学生退选课题
    @GetMapping("/project/choose/cancel/{pId}")
    public String projectChooseCancel(@PathVariable("pId") Integer pId,
                                      HttpSession session,
                                      Model model){
        //学生已开始课题(学生完成“立题表提交阶段”)则无法退选该课题
        ProjectStage projectStageSearch = new ProjectStage();
        projectStageSearch.setpId(pId);
        projectStageSearch.setName("立题表提交阶段");
        List<ProjectStage> projectStageListForConfirm = projectStageService.selectProjectStageList(projectStageSearch);
        if (projectStageListForConfirm.size() > 0){
            if (projectStageListForConfirm.get(0).getStatus().equals(2)){
                //学生已完成“立题表提交阶段”无法退选
                model.addAttribute("msg","你已开始进行课题，无法退选");
                return "fail";
            }
            //学生没有进行课题，开始退选逻辑
            //检查此学生是否为该课题的拥有者
            Project project = projectService.selectProjectById(pId);
            if (project.getsId().equals((Integer) session.getAttribute("sId"))){
                //开始退选逻辑
                //删除project表的selection_time与s_id的值，设置课题状态为“待选题”
                project.setsId(0);
                project.setStatus("待选题");
                projectService.updateProject(project);

                //删除project_stage表的start_time&end_time字段的值，重制status为0
                projectStageService.projectChooseCancel(pId);

                //删除project_file表的相关文件
                //根据pId拿到文件列表
                ProjectFile projectFile = new ProjectFile();
                projectFile.setpId(pId);
                projectFile.setUserType("学生");
                List<ProjectFile> projectFileList = projectFileService.selectProjectFileList(projectFile);
                //遍历这个List，删除文件后删除文件在数据库中的记录
                for (ProjectFile file : projectFileList) {
                    //在磁盘上删除文件
                    File fileIO = new File(file.getPath());
                    if (fileIO.isFile() && fileIO.exists()){
                        fileIO.delete();
                    }
                    //在数据库中删除文件记录
                    projectFileService.deleteProjectFileById(file.getPfId());
                }

                //删除此课题的过程记录表
                projectProcessService.deleteProjectProcessBypId(pId);

            }
            return "redirect:/project/choose";
        } else {
            model.addAttribute("msg","此课题不存在");
            return "fail";
        }
    }

    //跳转到阶段管理页面
    @GetMapping("/project/stage")
    public String toProjectStageManagePage(Model model,
                                           HttpSession session){
        //判断此学生是否选题
        Project project = new Project();
        project.setsId((Integer) session.getAttribute("sId"));
        List<Project> projectList = projectService.selectProjectList(project);
        if (projectList.size() > 0){
            //学生有选题
            //拿到学生选题的课题的各个阶段
            Integer pId = projectList.get(0).getpId();
            ProjectStage projectStage = new ProjectStage();
            projectStage.setpId(pId);
            List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStage);
            model.addAttribute("projectStageList",projectStageList);

            model.addAttribute("title",projectService.selectProjectById(pId).getTitle());

            return "project-stage";
        } else {
            //学生尚未选题，重定向到project-choose页面
            return "redirect:/project/choose";
        }
    }

    //跳转到查看阶段详情与下载上传文件页面
    @GetMapping("/project/stage/manage/{psId}")
    public String toProjectStageFilePage(@PathVariable("psId") Integer psId,
                                         Model model){
        ProjectStage projectStage = projectStageService.selectProjectStageById(psId);
        model.addAttribute("projectStage",projectStage);

        ProjectFile projectFile = new ProjectFile();
        projectFile.setPsId(psId);
        projectFile.setUserType("教师");
        List<ProjectFile> projectFileList = projectFileService.selectProjectFileList(projectFile);
        model.addAttribute("projectFileList",projectFileList);

        return "project-stage-file";
    }

    //学生选题搜索功能
    @PostMapping("/project/search")
    public String searchProjects(String searchString,
                                 Model model,
                                 HttpSession session){
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
            //开启选题逻辑前，检查选题功能是否被管理员关闭
            if (systemStatusService.getChooseStatus().equals(1)){
                //选题功能开启，展示未选课题
                //拿到符合搜索条件的选题
                List<Project> originalProjectList = projectService.searchProjects(searchString);

                //从中筛选出学生所在学院的选题&状态为“待选题”的课题
                List<Project> projectList = new ArrayList<>();
                Map<Project, Teacher> projectTeacherBind = new HashMap<>();
                for (Project project2 : originalProjectList) {
                    //如果此课题尚未被选
                    if (project2.getStatus().equals("待选题")){
                        //如果课题所属学院与该同学学院相符，则留下此课题
                        if (teacherService.selectTeacherById(project2.gettId()).getSchool().equals((String) session.getAttribute("school"))){
                            projectList.add(project2);
                            projectTeacherBind.put(project2,teacherService.selectTeacherById(project2.gettId()));
                        }
                    }
                }

                //将符合的课题送到前台
                model.addAttribute("projects",projectList);
                model.addAttribute("projectTeacherBind",projectTeacherBind);
            } else {
                //选题功能关闭
                model.addAttribute("msg","选题功能没有开启，无法选题，可联系管理员开启");
            }
            model.addAttribute("chooseStatus",systemStatusService.getChooseStatus());
            return "project-choose";
        }
    }

}
