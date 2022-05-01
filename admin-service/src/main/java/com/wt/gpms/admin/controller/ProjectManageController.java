package com.wt.gpms.admin.controller;

import com.wt.gpms.admin.client.TeacherClient;
import com.wt.gpms.admin.mapper.SystemStatusMapper;
import com.wt.gpms.admin.pojo.Project;
import com.wt.gpms.admin.service.ProjectService;
import com.wt.gpms.admin.service.SystemStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ProjectManageController {
    @Autowired
    ProjectService projectService;

    @Autowired
    TeacherClient teacherClient;

    @Autowired
    SystemStatusService systemStatusService;

    //路由到待审批课题页面
    @GetMapping("/project/manage/approving")
    public String toProjectApprovingPage(Model model){
        //拿到所有待审批课题
        Project project = new Project();
        project.setStatus("待审批");
        model.addAttribute("projects",projectService.selectProjectList(project));

        return "project-manage-unapproved";
    }

    //路由到已审批课题页面
    @GetMapping("/project/manage/approved")
    public String toProjectApprovedPage(Model model){
        //拿到所有已审批课题
        Project project = new Project();
        project.setStatus("待选题");
        model.addAttribute("projects",projectService.selectProjectList(project));

        return "project-manage-approved";
    }

    //路由到审批课题页面
    @GetMapping("/project/manage/approve/{pId}")
    public String toProjectApprovePage(@PathVariable("pId") Integer pId, Model model){
        //拿到此课题信息
        Project project = projectService.selectProjectById(pId);
        model.addAttribute("project",project);
        //拿到此立题教师的信息
        model.addAttribute("teacher",
                teacherClient.getTeacherById(project.gettId()));

        return "project-manage-approve";
    }

    //路由到详细信息页面
    @GetMapping("/project/manage/approved/{pId}")
    public String toProjectInfoPage(@PathVariable("pId") Integer pId, Model model){
        //拿到此课题信息
        Project project = projectService.selectProjectById(pId);
        model.addAttribute("project",project);
        //拿到此立题教师的信息
        model.addAttribute("teacher",
                teacherClient.getTeacherById(project.gettId()));

        return "project-manage-info";
    }

    //立题审批通过
    @PostMapping("/project/approve")
    public String projectApprove(Integer pId, String pNo, HttpSession session){
        Project project = new Project();
        project.setpId(pId);
        project.setpNo(pNo);
        project.setStatus("待选题");
        project.setApproveTime(new Date());
        project.setAdminId((Integer) session.getAttribute("adminId"));
        projectService.updateProject(project);

        return "redirect:/project/manage/approved";
    }

    //路由到立题选题开放关闭管理页面
    @GetMapping("/project/manage/open-close")
    public String openClose(Model model){
        model.addAttribute("createStatus",systemStatusService.getCreateStatus());
        model.addAttribute("chooseStatus",systemStatusService.getChooseStatus());
        return "system-open-close";
    }

    //开启立题功能
    @GetMapping("/project/manage/open/create")
    public String openCreate(){
        systemStatusService.openCreate();
        return "redirect:/project/manage/open-close";
    }

    //关闭立题功能
    @GetMapping("/project/manage/close/create")
    public String closeCreate(){
        systemStatusService.closeCreate();
        return "redirect:/project/manage/open-close";
    }

    //开启选题功能
    @GetMapping("/project/manage/open/choose")
    public String openChoose(){
        systemStatusService.openChoose();
        return "redirect:/project/manage/open-close";
    }

    //关闭选题功能
    @GetMapping("/project/manage/close/choose")
    public String closeChoose(){
        systemStatusService.closeChoose();
        return "redirect:/project/manage/open-close";
    }

    //搜索，已审批项目
    @PostMapping("/project/approved/search")
    public String searchApprovedProject(String searchString,Model model){
        Project project = new Project();
        project.setTitle(searchString);
        project.setStatus("待选题");
        model.addAttribute("projects",projectService.selectProjectList(project));

        return "project-manage-approved";
    }

    //搜索，未审批项目
    @PostMapping("/project/unapproved/search")
    public String searchUnapprovedProject(String searchString,Model model){
        Project project = new Project();
        project.setTitle(searchString);
        project.setStatus("待审批");
        model.addAttribute("projects",projectService.selectProjectList(project));

        return "project-manage-unapproved";
    }

}
