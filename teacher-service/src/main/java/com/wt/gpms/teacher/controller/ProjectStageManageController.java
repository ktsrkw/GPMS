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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ProjectStageManageController {
    @Autowired
    ProjectStageService projectStageService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectFileService projectFileService;

    //跳转到阶段管理页面
    @GetMapping("/project/stage")
    public String toProjectStagePage(Model model, HttpSession session){
        //拿到该教师教师所有的立题信息
        Project project = new Project();
        project.settId((Integer) session.getAttribute("tId"));
        model.addAttribute("projects",projectService.selectProjectList(project));

        return "project-stage";
    }

    //跳转到阶段管理页面
    @GetMapping("/project/stage/{pId}")
    public String toProjectStageManagePage(@PathVariable("pId") Integer pId, Model model){
        //根据pId拿到此课题所有的阶段
        ProjectStage projectStage = new ProjectStage();
        projectStage.setpId(pId);
        List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStage);
        model.addAttribute("projectStageList", projectStageList);

        //根据pId拿到title
        model.addAttribute("title", projectService.selectProjectById(pId).getTitle());

        return "project-stage-manage";
    }

    //调转到更改阶段信息页面
    @GetMapping("/project/stage/manage/{psId}")
    public String toUpdateStageInfoPage(@PathVariable("psId") Integer psId, Model model){
        //根据psId拿到此阶段
        model.addAttribute("projectStage", projectStageService.selectProjectStageById(psId));
        return "project-stage-manage-update";
    }

    //处理某个阶段的阶段要求与文件上传
    @PostMapping("/project/stage/update")
    public String projectStageUpdate(ProjectStage projectStage,
                                     @RequestParam("uploadFiles") MultipartFile[] uploadFiles,
                                     HttpServletRequest request) throws IOException {
        //更改阶段描述内容
        projectStageService.updateProjectStage(projectStage);

        //处理上传的阶段文件
        if (uploadFiles.length > 0){
            //遍历上传的文件
            for (MultipartFile uploadFile : uploadFiles) {
                if(!uploadFile.isEmpty()){
                    //为该文件创建一个对象
                    ProjectFile projectFile = new ProjectFile();
                    projectFile.setTitle(uploadFile.getOriginalFilename());
                    projectFile.setPsId(projectStage.getPsId());
                    projectFile.setpId(projectStage.getpId());

                    //保存到服务器
                    //1、创建保存到服务器的路径
                    String savePath = "D:\\codes\\resources\\gpms";
                    File fileDir = new File(savePath);

                    //2、生成文件在服务器端存放的名字（避免重名）
                    //得到上传文件的后缀名（.jpg，.txt，.mp4 ...）
                    String fileSuffix= Objects.requireNonNull(uploadFile.getOriginalFilename())
                            .substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1);
                    //给文件准备好一个新的名字newFileName：随机生成的UUID再加上前面取得的文件后缀名
                    String newFileName= UUID.randomUUID().toString() + "." + fileSuffix;
                    File uploadedFile = new File(fileDir+"/"+newFileName);

                    //3、上传
                    uploadFile.transferTo(uploadedFile);

                    //4、获取上传文件的访问路径
                    //这里的/files是为了访问的映射路径
                    String fileURL = request.getScheme() + "://" + request.getServerName() + ":"
                            + request.getServerPort() + "/files/" + newFileName;

                    //设置文件保存路径
                    projectFile.setPath(savePath + "\\" + newFileName);
                    //设置文件访问路径
                    projectFile.setUrl(fileURL);

                    //数据库中保存该文件的信息
                    projectFileService.insertProjectFile(projectFile);
                }
            }
        }

        return "redirect:/project/stage";
    }

}
