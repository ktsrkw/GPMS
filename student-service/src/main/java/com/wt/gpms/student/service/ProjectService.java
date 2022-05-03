package com.wt.gpms.student.service;

import com.wt.gpms.student.pojo.Project;

import java.util.List;

public interface ProjectService {

    Project selectProjectById(Integer pId);

    List<Project> selectProjectList(Project project);

    int insertProject(Project project);

    int updateProject(Project project);

    int deleteProjectById(Integer pId);

    int deleteProjectByIds(Integer[] pIds);

    List<Project> searchProjects(String searchString);

}
