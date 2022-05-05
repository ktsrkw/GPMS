package com.wt.gpms.student.controller;

import com.wt.gpms.student.pojo.Project;
import com.wt.gpms.student.pojo.ProjectProcess;
import com.wt.gpms.student.service.ProjectProcessService;
import com.wt.gpms.student.service.ProjectService;
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
public class ProjectProcessController {
    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectProcessService projectProcessService;

    //跳转到过程记录表管理页面
    @GetMapping("/project/process")
    public String toProjectProcessManagePage(HttpSession session, Model model){
        //判断此学生是否选题
        Project project = new Project();
        project.setsId((Integer) session.getAttribute("sId"));
        List<Project> projectList = projectService.selectProjectList(project);
        if (projectList.size() > 0){
            //学生有选题
            //拿到此学生选题的过程记录表的数据
            ProjectProcess projectProcess = new ProjectProcess();
            projectProcess.setpId(projectList.get(0).getpId());
            List<ProjectProcess> projectProcessList = projectProcessService.selectProjectProcessList(projectProcess);
            model.addAttribute("projectProcessList", projectProcessList);

            return "project-process";
        } else {
            //学生尚未选题，重定向到project-choose页面
            return "redirect:/project/choose";
        }
    }

    //跳转到新增过程记录页面
    @GetMapping("/project/process/add")
    public String toProjectProcessAddPage(HttpSession session, Model model){
        //判断此学生是否选题
        Project project = new Project();
        project.setsId((Integer) session.getAttribute("sId"));
        List<Project> projectList = projectService.selectProjectList(project);
        if (projectList.size() > 0){
            //学生有选题
            ProjectProcess projectProcess = new ProjectProcess();
            projectProcess.setpId(projectList.get(0).getpId());
            List<ProjectProcess> projectProcessList = projectProcessService.selectProjectProcessList(projectProcess);
            if (projectProcessList.size() > 0){
                //不是第一次上传过程记录表，计数值为最近的一次加1
                ProjectProcess lastedProjectProcess = projectProcessList.get(projectProcessList.size() - 1);
                projectProcess.setTimes(lastedProjectProcess.getTimes() + 1);
            } else {
                //是第一次上传过程记录表，计数值1
                projectProcess.setTimes(1L);
            }

            model.addAttribute("projectProcess",projectProcess);
            return "project-process-add";
        } else {
            //学生尚未选题，重定向到project-choose页面
            return "redirect:/project/choose";
        }
    }

    //新增过程记录
    @PostMapping("/project/process/add")
    public String projectProcessAdd(ProjectProcess projectProcess){
        projectProcess.setRecordTime(new Date());
        projectProcessService.insertProjectProcess(projectProcess);
        return "redirect:/project/process";
    }

    //跳转到查看过程记录评语页面
    @GetMapping("/project/process/{ppId}")
    public String projectProcess(@PathVariable("ppId") Integer ppId,
                                 Model model,
                                 HttpSession session){
        //判断当前学生是否为该过程记录的拥有者
        ProjectProcess projectProcess = projectProcessService.selectProjectProcessById(ppId);
        Integer sId = projectService.selectProjectById(projectProcess.getpId()).getsId();
        if (sId.equals((Integer) session.getAttribute("sId"))){
            //当前学生是此记录表的拥有者，跳转
            ProjectProcess projectProcess1 = projectProcessService.selectProjectProcessById(ppId);
            model.addAttribute("projectProcess",projectProcess1);

            return "project-process-comment";
        } else {
            //当前学生不是，重定向
            return "redirect:/project/process";
        }
    }

    //跳转到修改过程记录内容页面
    @GetMapping("/project/process/update/{ppId}")
    public String toUpdateProjectProcessPage(@PathVariable("ppId") Integer ppId,
                                             Model model,
                                             HttpSession session){
        //判断当前学生是否为该过程记录的拥有者
        ProjectProcess projectProcess = projectProcessService.selectProjectProcessById(ppId);
        Integer sId = projectService.selectProjectById(projectProcess.getpId()).getsId();
        if (sId.equals((Integer) session.getAttribute("sId"))){
            //当前学生是此记录表的拥有者，跳转
            ProjectProcess projectProcess1 = projectProcessService.selectProjectProcessById(ppId);
            model.addAttribute("projectProcess",projectProcess1);

            return "project-process-update";
        } else {
            //当前学生不是，重定向
            return "redirect:/project/process";
        }
    }

    //修改过程记录内容
    @PostMapping("/project/process/update")
    public String updateProjectProcess(ProjectProcess projectProcess,
                                       HttpSession session){
        //判断当前学生是否为该过程记录的拥有者
        Integer sId = projectService.selectProjectById(projectProcess.getpId()).getsId();
        if (sId.equals((Integer) session.getAttribute("sId"))){
            //允许修改
            projectProcessService.updateProjectProcess(projectProcess);
        }

        return "redirect:/project/process";
    }

}
