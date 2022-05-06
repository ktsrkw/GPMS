package com.wt.gpms.teacher.controller;

import com.wt.gpms.teacher.pojo.Project;
import com.wt.gpms.teacher.pojo.ProjectFile;
import com.wt.gpms.teacher.pojo.ProjectStage;
import com.wt.gpms.teacher.service.ProjectFileService;
import com.wt.gpms.teacher.service.ProjectService;
import com.wt.gpms.teacher.service.ProjectStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Controller
public class ProjectFileController {
    @Autowired
    ProjectFileService projectFileService;

    @Autowired
    ProjectStageService projectStageService;

    @Autowired
    ProjectService projectService;

    //跳转到文档管理页面（课题）
    @GetMapping("/project/file/teacher")
    public String toProjectFileManagePage(HttpSession session,
                                          Model model){
        //拿到此教师的所有课题
        Project project = new Project();
        project.settId((Integer) session.getAttribute("tId"));
        List<Project> projects = projectService.selectProjectList(project);

        model.addAttribute("projects",projects);

        return "project-file-project";
    }

    //跳转到文档管理页面（阶段文档）
    @GetMapping("/project/file/teacher/{pId}")
    public String toProjectStageFilePage(@PathVariable("pId") Integer pId,
                                         Model model){
        //拿到此教师在此课题各个阶段上传的文件
        ProjectFile projectFile = new ProjectFile();
        projectFile.setpId(pId);
        projectFile.setUserType("教师");
        List<ProjectFile> projectFileList = projectFileService.selectProjectFileList(projectFile);
        //遍历文件列表，为文件分类
        List<ProjectFile> projectFileList1 = new ArrayList<>();//立题表提交阶段的文件列表
        List<ProjectFile> projectFileList2 = new ArrayList<>();//任务书提交阶段的文件列表
        List<ProjectFile> projectFileList3 = new ArrayList<>();//论文翻译提交阶段的文件列表
        List<ProjectFile> projectFileList4 = new ArrayList<>();//开题报告提交阶段的文件列表
        List<ProjectFile> projectFileList5 = new ArrayList<>();//中期检查阶段的文件列表
        List<ProjectFile> projectFileList6 = new ArrayList<>();//论文提交阶段的文件列表
        List<ProjectFile> projectFileList7 = new ArrayList<>();//答辩阶段的文件列表
        for (ProjectFile file : projectFileList) {
            ProjectStage projectStage = projectStageService.selectProjectStageById(file.getPsId());
            switch (projectStage.getName()){
                case "立题表提交阶段":
                    projectFileList1.add(file);
                    break;
                case "任务书提交阶段":
                    projectFileList2.add(file);
                    break;
                case "论文翻译提交阶段":
                    projectFileList3.add(file);
                    break;
                case "开题报告提交阶段":
                    projectFileList4.add(file);
                    break;
                case "中期检查阶段":
                    projectFileList5.add(file);
                    break;
                case "论文提交阶段":
                    projectFileList6.add(file);
                    break;
                case "答辩阶段":
                    projectFileList7.add(file);
                    break;
            }
        }
        model.addAttribute("projectFileList1",projectFileList1);
        model.addAttribute("projectFileList2",projectFileList2);
        model.addAttribute("projectFileList3",projectFileList3);
        model.addAttribute("projectFileList4",projectFileList4);
        model.addAttribute("projectFileList5",projectFileList5);
        model.addAttribute("projectFileList6",projectFileList6);
        model.addAttribute("projectFileList7",projectFileList7);

        return "project-file-manage";
    }

    //文件下载
    @ResponseBody
    @GetMapping("/project/stage/file/download/{pfId}")
    public void downloadFile(@PathVariable("pfId") Integer pfId,
                             HttpServletResponse response) throws IOException {
        ProjectFile projectFile = projectFileService.selectProjectFileById(pfId);
        String path = projectFile.getPath();

        // 读到流中
        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        response.reset();
        response.setContentType("application/octet-stream");
        String filename = projectFile.getTitle();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }

    //根据id删除文件
    @GetMapping("/project/stage/file/delete/{pfId}")
    public String fileDeleteById(@PathVariable("pfId") Integer pfId,
                                 HttpSession session){
        //检查此教师是否为此文件的拥有者
        ProjectFile projectFile = projectFileService.selectProjectFileById(pfId);
        Project project = projectService.selectProjectById(projectFile.getpId());
        if (project.gettId().equals((Integer) session.getAttribute("tId"))){
            //当前教师是文件的拥有者，可以删除
            File file = new File(projectFile.getPath());
            if (file.isFile() && file.exists()){
                file.delete();
            }
            projectFileService.deleteProjectFileById(projectFile.getPfId());
        }

        return "redirect:/project/file/teacher";
    }

    @GetMapping("/project/file/student")
    public String toProjectFileStudentManagePage(HttpSession session,
                                           Model model){
        //拿到此教师的所有课题
        Project project = new Project();
        project.settId((Integer) session.getAttribute("tId"));
        List<Project> projects = projectService.selectProjectList(project);

        model.addAttribute("projects",projects);

        return "project-file-project-student";
    }

    @GetMapping("/project/file/student/{pId}")
    public String toProjectFileStudentPage(@PathVariable("pId") Integer pId,
                                           Model model){
        //拿到学生在此课题各个阶段上传的文件
        ProjectFile projectFile = new ProjectFile();
        projectFile.setpId(pId);
        projectFile.setUserType("学生");
        List<ProjectFile> projectFileList = projectFileService.selectProjectFileList(projectFile);
        //遍历文件列表，为文件分类
        List<ProjectFile> projectFileList1 = new ArrayList<>();//立题表提交阶段的文件列表
        List<ProjectFile> projectFileList2 = new ArrayList<>();//任务书提交阶段的文件列表
        List<ProjectFile> projectFileList3 = new ArrayList<>();//论文翻译提交阶段的文件列表
        List<ProjectFile> projectFileList4 = new ArrayList<>();//开题报告提交阶段的文件列表
        List<ProjectFile> projectFileList5 = new ArrayList<>();//中期检查阶段的文件列表
        List<ProjectFile> projectFileList6 = new ArrayList<>();//论文提交阶段的文件列表
        List<ProjectFile> projectFileList7 = new ArrayList<>();//答辩阶段的文件列表
        for (ProjectFile file : projectFileList) {
            ProjectStage projectStage = projectStageService.selectProjectStageById(file.getPsId());
            switch (projectStage.getName()){
                case "立题表提交阶段":
                    projectFileList1.add(file);
                    break;
                case "任务书提交阶段":
                    projectFileList2.add(file);
                    break;
                case "论文翻译提交阶段":
                    projectFileList3.add(file);
                    break;
                case "开题报告提交阶段":
                    projectFileList4.add(file);
                    break;
                case "中期检查阶段":
                    projectFileList5.add(file);
                    break;
                case "论文提交阶段":
                    projectFileList6.add(file);
                    break;
                case "答辩阶段":
                    projectFileList7.add(file);
                    break;
            }
        }
        model.addAttribute("projectFileList1",projectFileList1);
        model.addAttribute("projectFileList2",projectFileList2);
        model.addAttribute("projectFileList3",projectFileList3);
        model.addAttribute("projectFileList4",projectFileList4);
        model.addAttribute("projectFileList5",projectFileList5);
        model.addAttribute("projectFileList6",projectFileList6);
        model.addAttribute("projectFileList7",projectFileList7);

        return "project-file-manage-student";
    }

}
