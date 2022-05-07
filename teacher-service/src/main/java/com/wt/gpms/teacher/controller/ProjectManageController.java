package com.wt.gpms.teacher.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.wt.gpms.teacher.pojo.Project;
import com.wt.gpms.teacher.pojo.ProjectFile;
import com.wt.gpms.teacher.pojo.ProjectStage;
import com.wt.gpms.teacher.service.ProjectFileService;
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
import java.io.File;
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

    @Autowired
    ProjectFileService projectFileService;

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

    //教师撤销（删除）立题
    @GetMapping("/project/cancel/{pId}")
    public String cancelProject(@PathVariable("pId") Integer pId,
                                HttpSession session,
                                Model model){
        Project project = projectService.selectProjectById(pId);
        //已审批的立题无法删除，判断此立题是否已审批
        if (project.getStatus().equals("待审批")){
            //还未审批，可以撤销
            //判断此教师是否为此立题的拥有者
            if (project.gettId().equals((Integer) session.getAttribute("tId"))){
                //符合，允许删除
                //删除此课题的所有阶段记录
                ProjectStage projectStageBypId = new ProjectStage();
                projectStageBypId.setpId(pId);
                List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStageBypId);
                for (ProjectStage projectStage : projectStageList) {
                    projectStageService.deleteProjectStageById(projectStage.getPsId());
                }

                //删除此课题的所有文件
                ProjectFile projectFileBypId = new ProjectFile();
                projectFileBypId.setpId(pId);
                List<ProjectFile> projectFileList = projectFileService.selectProjectFileList(projectFileBypId);
                for (ProjectFile projectFile : projectFileList) {
                    //删除磁盘上的文件
                    File IOFile = new File(projectFile.getPath());
                    if (IOFile.isFile() && IOFile.exists()){
                        IOFile.delete();
                    }

                    //删除文件在数据库中的记录
                    projectFileService.deleteProjectFileById(projectFile.getPfId());
                }

                //删除此课题的记录
                projectService.deleteProjectById(pId);

                return "redirect:/project";
            } else {
                //不符合，重定向到当前教师的课题页面
                return "redirect:/project";
            }

        } else {
            //不可撤销
            model.addAttribute("msg","此课题已审批通过，不可撤销");
            return "fail";
        }

    }

    //老师的打分功能
    //跳转到课题打分课题列表
    @GetMapping("/project/score")
    public String toProjectScoreListPage(HttpSession session,
                                         Model model){
        //拿到所有状态为“待评分”的课题和“已完成”的课题
        Project project = new Project();
        project.settId((Integer) session.getAttribute("tId"));
        project.setStatus("待评分");
        List<Project> projectList1 = projectService.selectProjectList(project);
        project.setStatus("已完成");
        List<Project> projectList2 = projectService.selectProjectList(project);
        projectList1.addAll(projectList2);

        model.addAttribute("projects",projectList1);

        return "project-score";

    }

    //跳转到打分页面
    @GetMapping("/project/score/{pId}")
    public String toScoreProjectPage(@PathVariable("pId") Integer pId,
                                     HttpSession session,
                                     Model model){
        Project project = projectService.selectProjectById(pId);
        //判断课题是否可以打分
        if (project.getStatus().equals("待评分") || project.getStatus().equals("已完成")){
            //可以评分
            //判断教师是否为此课题的教师
            if (project.gettId().equals((Integer) session.getAttribute("tId"))){
                //教师满足，可以进入评分
                model.addAttribute("project",project);

                return "project-score-point";
            } else {
                model.addAttribute("你不是此课题的发布者，无法评分");
                return "fail";
            }
        } else {
            //不可以评分，展示提示
            model.addAttribute("此课题还未到评分阶段不可以评分");
            return "fail";
        }

    }

    //处理打分请求，保存分数
    @PostMapping("/project/score/point")
    public String scoreProject(Project project,
                               HttpSession session,
                               Model model){
        //判断此教师是否为此课题的拥有者
        if (project.gettId().equals((Integer) session.getAttribute("tId"))){
            //允许打分
            //判断课题状态是否为可评分
            if (project.getStatus().equals("待评分") || project.getStatus().equals("已完成")){
                //开始评分逻辑
                //评分后状态应该为“已完成”
                project.setStatus("已完成");

                //提交评分
                projectService.updateProject(project);

                return "redirect:/project/score";
            } else {
                model.addAttribute("此课题还未到评分阶段不可以评分");
                return "fail";
            }

        } else {
            //跳转到失败页面
            model.addAttribute("你不是此课题的发布者，无法评分");
            return "fail";
        }

    }

}
