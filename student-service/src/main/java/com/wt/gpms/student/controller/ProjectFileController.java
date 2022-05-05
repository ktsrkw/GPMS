package com.wt.gpms.student.controller;

import com.wt.gpms.student.pojo.Project;
import com.wt.gpms.student.pojo.ProjectFile;
import com.wt.gpms.student.pojo.ProjectStage;
import com.wt.gpms.student.service.ProjectFileService;
import com.wt.gpms.student.service.ProjectService;
import com.wt.gpms.student.service.ProjectStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class ProjectFileController {
    @Autowired
    ProjectFileService projectFileService;

    @Autowired
    ProjectStageService projectStageService;

    @Autowired
    ProjectService projectService;

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

    //文件上传
    @PostMapping("/project/stage/file/upload")
    public String uploadFiles(ProjectStage projectStage,
                              @RequestParam("uploadFiles") MultipartFile[] uploadFiles,
                              HttpServletRequest request) throws IOException{
        //完成文件的上传
        if (uploadFiles.length > 0){
            //遍历上传的文件
            for (MultipartFile uploadFile : uploadFiles) {
                if(!uploadFile.isEmpty()){
                    //为该文件创建一个对象
                    ProjectFile projectFile = new ProjectFile();
                    projectFile.setTitle(uploadFile.getOriginalFilename());
                    projectFile.setPsId(projectStage.getPsId());
                    projectFile.setpId(projectStage.getpId());
                    projectFile.setUserType("学生");

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
                    //这里的/files是为了访问的映射路径，别忘了本项目的访问前缀/teacher
                    String fileURL = request.getScheme() + "://" + request.getServerName() + ":"
                            + request.getServerPort() + "/student/files/" + newFileName;

                    //设置文件保存路径
                    projectFile.setPath(savePath + "\\" + newFileName);
                    //设置文件访问路径
                    projectFile.setUrl(fileURL);

                    //数据库中保存该文件的信息
                    projectFileService.insertProjectFile(projectFile);
                }
            }
        } else {
            return "redirect:/project/stage";
        }

        //修改project_stage表的状态，结束当前阶段，进入下一阶段，还要判断答辩完成时进行操作
        //标识该阶段已完成
        projectStage.setStatus(2);
        //标识该阶段完成时间
        projectStage.setEndTime(new Date());
        projectStageService.updateProjectStage(projectStage);
        if (projectStage.getName().equals("答辩阶段")){
            //TODO:答辩完成时的操作，等待打分等等
        } else {
            //本阶段非最后阶段（答辩），进入下一个阶段
            ProjectStage projectStage1 = new ProjectStage();
            projectStage1.setPsId(projectStage.getPsId() + 1);
            //下一阶段状态设置为进行中
            projectStage1.setStatus(1);
            //设置下一阶段的开始时间
            projectStage1.setStartTime(new Date());

            projectStageService.updateProjectStage(projectStage1);
        }

        return "redirect:/project/stage";
    }

    @GetMapping("/project/stage/file")
    public String projectStageFileManage(Model model,
                                         HttpSession session){

        //判断此学生是否选题
        Project project = new Project();
        project.setsId((Integer) session.getAttribute("sId"));
        List<Project> projectList = projectService.selectProjectList(project);
        if (projectList.size() > 0){
            //学生有选题
            //拿到此学生各个阶段上传的文件
            ProjectFile projectFile = new ProjectFile();
            projectFile.setpId(project.getpId());
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

            return "project-file-manage";
        } else {
            //学生尚未选题，重定向到project-choose页面
            return "redirect:/project/choose";
        }
    }

    //根据id删除文件
    @GetMapping("/project/stage/file/delete/{pfId}")
    public String fileDeleteById(@PathVariable("pfId") Integer pfId,
                                 HttpSession session){
        //检查学生是否为此文件的拥有者
        ProjectFile projectFile = projectFileService.selectProjectFileById(pfId);
        Project project = projectService.selectProjectById(projectFile.getpId());
        if (project.getsId().equals((Integer) session.getAttribute("sId"))){
            //当前学生是文件的拥有者，可以删除
            File file = new File(projectFile.getPath());
            if (file.isFile() && file.exists()){
                file.delete();
            }
            projectFileService.deleteProjectFileById(projectFile.getPfId());
        }

        return "redirect:/project/stage/file";
    }

}
