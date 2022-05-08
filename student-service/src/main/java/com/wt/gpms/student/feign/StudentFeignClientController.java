package com.wt.gpms.student.feign;

import com.wt.gpms.student.pojo.Project;
import com.wt.gpms.student.pojo.ProjectFile;
import com.wt.gpms.student.pojo.ProjectStage;
import com.wt.gpms.student.pojo.Student;
import com.wt.gpms.student.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/feign")
public class StudentFeignClientController {
    @Autowired
    StudentService studentService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectStageService projectStageService;

    @Autowired
    ProjectFileService projectFileService;

    @Autowired
    ProjectProcessService projectProcessService;

    @RequestMapping("/all")
    public List<Student> getAllStudents(){
        return studentService.allStudents();
    }

    @RequestMapping("/{sId}")
    public Student getStudentById(@PathVariable("sId") Integer sId){
        return studentService.getStudentById(sId);
    }

    @PostMapping("/update")
    public Integer updateStudentInfo(@RequestBody Student student){
        return studentService.updateStudentInfo(student);
    }

    @GetMapping("/delete/{sId}")
    public Integer deleteStudentById(@PathVariable("sId") Integer sId){
        //删除学生时让此学生“退选”
        //判断此学生是否选题
        Project project1 = new Project();
        project1.setsId(sId);
        List<Project> projects = projectService.selectProjectList(project1);

        if (projects.size() > 0){
            //学生有选题，开启删除相关退选逻辑
            Project project = projects.get(0);

            //删除project表的selection_time与s_id的值，设置课题状态为“待选题”
            project.setsId(0);
            project.setStatus("待选题");
            projectService.updateProject(project);

            //删除project_stage表的start_time&end_time字段的值，重制status为0
            projectStageService.projectChooseCancel(project.getpId());

            //删除project_file表的相关文件
            //根据pId拿到文件列表
            ProjectFile projectFile = new ProjectFile();
            projectFile.setpId(project.getpId());
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
            projectProcessService.deleteProjectProcessBypId(project.getpId());

        }


        return studentService.deleteStudentById(sId);
    }

    @PostMapping("/search")
    public List<Student> searchStudent(@RequestBody String searchString){
        return studentService.searchStudent(searchString);
    }

    @PostMapping("/add")
    public Integer addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }
}
