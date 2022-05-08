package com.wt.gpms.teacher.feign;

import com.wt.gpms.teacher.pojo.*;
import com.wt.gpms.teacher.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/feign")
public class TeacherFeignClientController {
    @Autowired
    TeacherService teacherService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectStageService projectStageService;

    @Autowired
    ProjectFileService projectFileService;

    @Autowired
    ProjectProcessService projectProcessService;

    @RequestMapping("/all")
    public List<Teacher> getAllTeachers(){
        return teacherService.allTeachers();
    }

    @RequestMapping("/{tId}")
    public Teacher getTeacherById(@PathVariable("tId") Integer tId){
        return teacherService.selectTeacherById(tId);
    }

    @PostMapping("/update")
    public Integer updateTeacherInfo(@RequestBody Teacher teacher){
        return teacherService.updateTeacher(teacher);
    }

    @GetMapping("/delete/{tId}")
    public Integer deleteTeacher(@PathVariable("tId") Integer tId){
        //删除教师时要删除此教师的立题信息
        //拿到此教师的所有立题
        Project projectSearch = new Project();
        projectSearch.settId(tId);
        List<Project> projectList = projectService.selectProjectList(projectSearch);

        //遍历删除
        for (Project project : projectList) {
            //删除与该课题相关的所有阶段
            ProjectStage projectStageSearch = new ProjectStage();
            projectStageSearch.setpId(project.getpId());
            List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStageSearch);
            for (ProjectStage projectStage : projectStageList) {
                projectStageService.deleteProjectStageById(projectStage.getPsId());
            }

            //删除与该课题相关的所有文件
            ProjectFile projectFileSearch = new ProjectFile();
            projectFileSearch.setpId(project.getpId());
            List<ProjectFile> projectFileList = projectFileService.selectProjectFileList(projectFileSearch);
            for (ProjectFile projectFile : projectFileList) {
                File IOFile = new File(projectFile.getPath());
                if (IOFile.exists() && IOFile.isFile()){
                    IOFile.delete();
                }
                projectFileService.deleteProjectFileById(projectFile.getPfId());
            }

            //删除与该课题相关的所有过程记录
            ProjectProcess projectProcessSearch = new ProjectProcess();
            projectProcessSearch.setpId(project.getpId());
            List<ProjectProcess> projectProcessList = projectProcessService.selectProjectProcessList(projectProcessSearch);
            for (ProjectProcess projectProcess : projectProcessList) {
                projectProcessService.deleteProjectProcessBypId(projectProcess.getPpId());
            }

            //删除课题
            projectService.deleteProjectById(project.getpId());

        }

        return teacherService.deleteTeacherById(tId);
    }

    @PostMapping("/add")
    public Integer addTeacher(@RequestBody Teacher teacher){
        return teacherService.insertTeacher(teacher);
    }

    @PostMapping("/search")
    public List<Teacher> searchTeachers(@RequestBody String searchString){
        return teacherService.searchTeachers(searchString);
    }
}
