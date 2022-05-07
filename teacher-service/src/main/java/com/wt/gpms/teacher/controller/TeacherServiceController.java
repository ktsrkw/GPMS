package com.wt.gpms.teacher.controller;

import com.wt.gpms.teacher.pojo.Project;
import com.wt.gpms.teacher.pojo.ProjectStage;
import com.wt.gpms.teacher.pojo.Student;
import com.wt.gpms.teacher.pojo.Teacher;
import com.wt.gpms.teacher.service.ProjectService;
import com.wt.gpms.teacher.service.ProjectStageService;
import com.wt.gpms.teacher.service.StudentService;
import com.wt.gpms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherServiceController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    ProjectStageService projectStageService;

    @Autowired
    ProjectService projectService;

    @Autowired
    StudentService studentService;

    @ResponseBody
    @RequestMapping("/all")
    public List<Teacher> getAllTeachers(){
        return teacherService.allTeachers();
    }

    @ResponseBody
    @RequestMapping("/{tId}")
    public Teacher getTeacherById(@PathVariable("tId") Integer tId){
        return teacherService.selectTeacherById(tId);
    }

    @ResponseBody
    @GetMapping("/delete/{tId}")
    public Integer deleteTeacher(@PathVariable("tId") Integer tId){
        return teacherService.deleteTeacherById(tId);
    }

    @ResponseBody
    @PostMapping("/add")
    public Integer addTeacher(@RequestBody Teacher teacher){
        return teacherService.insertTeacher(teacher);
    }

    @ResponseBody
    @PostMapping("/search")
    public List<Teacher> searchTeachers(@RequestBody String searchString){
        return teacherService.searchTeachers(searchString);
    }

    //路由到登陆页面
    @GetMapping({"/login.html","/"})
    public String toTeacherLoginPage(){
        return "teacher-login";
    }

    //教师登录
    @PostMapping("/login")
    public String teacherLogin(String tNo, String password, HttpSession session, Model model){
        Teacher teacher = teacherService.selectTeacherByNo(tNo);
        if (teacher != null){
            //如果查到用户且密码一致
            if (teacher.getPassword().equals(password)){
                //登录成功的标识，在session中放入教师编号与教师名等等
                session.setAttribute("tId",teacher.gettId());
                session.setAttribute("tNo",teacher.gettNo());
                session.setAttribute("name",teacher.getName());
                session.setAttribute("title",teacher.getTitle());
                return "redirect:/project";
            } else {
                //密码错误
                model.addAttribute("msg", "密码错误");
                return "teacher-login";
            }
        } else {
            //用户不存在
            model.addAttribute("msg", "教师号不存在");
            return "teacher-login";
        }
    }

    //教师登出
    @GetMapping("/logout")
    public String teacherLogout(HttpSession session){
        session.removeAttribute("tId");
        session.removeAttribute("tNo");
        session.removeAttribute("name");
        session.removeAttribute("title");
        return "redirect:/login.html";
    }

    //路由到更新信息页面
    @GetMapping("/update.html")
    public String toUpdatePage(Model model, HttpSession session){
        model.addAttribute("teacher",
                teacherService.selectTeacherById((Integer) session.getAttribute("tId")));

        return "teacher-update";
    }

    //更新信息
    @PostMapping("/update")
    public String updateInfo(Model model,
                             Teacher teacher,
                             HttpSession session,
                             String passwordConfirm){
        //检查当前教师是否为传过来的更新信息的教师
        if (teacher.gettId().equals((Integer) session.getAttribute("tId"))){
            //允许更新
            //检查两次密码输入是否一致
            if (teacher.getPassword().equals(passwordConfirm)){
                //两次密码输入一致，更新
                teacherService.updateTeacher(teacher);
                model.addAttribute("msg","更新成功！");
            } else {
                //不一致，存入提示信息
                model.addAttribute("msg","两次密码输入不一致");
            }
        model.addAttribute("teacher",
                teacherService.selectTeacherById((Integer) session.getAttribute("tId")));
            return "teacher-update";
        } else {
            //不允许更新，重定向
            return "redirect:/update.html";
        }
    }

    //跳转到“我的学生页面”
    @GetMapping("/student")
    public String toStudentPage(HttpSession session,
                                Model model){
        //课题、阶段、学生信息
        Project projectSelect = new Project();
        projectSelect.settId((Integer) session.getAttribute("tId"));
        List<Project> projectList = projectService.selectProjectList(projectSelect);

        Map<Project, Student> projectStudentMap = new HashMap<>();
        Map<Project, String> projectStageMap = new HashMap<>();
        List<Project> projects = new ArrayList<>();

        for (Project project : projectList) {
            if (!project.getStatus().equals("待选题") && !project.getStatus().equals("待审批")){
                //该课题已有学生选择
                //绑定课题与学生
                projects.add(project);
                projectStudentMap.put(project, studentService.getStudentById(project.getsId()));
                ProjectStage projectStage = new ProjectStage();
                //找到当前课题所处阶段，绑定
                projectStage.setpId(project.getpId());
                projectStage.setStatus(1);
                List<ProjectStage> projectStageList = projectStageService.selectProjectStageList(projectStage);
                if (projectStageList.size() == 0){
                    //说明已走完所有阶段
                    projectStageMap.put(project, "课题已完成");
                } else {
                    projectStageMap.put(project, projectStageList.get(0).getName());
                }
            }
        }

        if (projects.size() == 0){
            //没有课题被学生选择
            model.addAttribute("msg","当前我的课题没有被学生选择");
            return "fail";
        }

        model.addAttribute("projects",projects);
        model.addAttribute("projectStudentMap",projectStudentMap);
        model.addAttribute("projectStageMap",projectStageMap);
        return "student";
    }

    //进入”我的学生信息“详细页面
    @GetMapping("/student/{pId}")
    public String studentInfoPage(@PathVariable("pId") Integer pId,
                                  HttpSession session,
                                  Model model){
        Project project = projectService.selectProjectById(pId);
        //判断此教师是否为此课题的拥有者
        if (project.gettId().equals((Integer) session.getAttribute("tId"))){
            //判断此课题是否有学生选择
            if (!project.getStatus().equals("待选题") && !project.getStatus().equals("待审批")){
                //有学生选择
                //拿到课题与学生的信息
                Student student = studentService.getStudentById(project.getsId());
                model.addAttribute("student",student);
                model.addAttribute("project",project);

                return "student-info";
            } else {
                model.addAttribute("此课题尚未被学生选择，无法查看");
                return "fail";
            }
        } else {
            //跳转到失败页面
            model.addAttribute("你不是此课题的发布者，无法查看");
            return "fail";
        }
    }

}



