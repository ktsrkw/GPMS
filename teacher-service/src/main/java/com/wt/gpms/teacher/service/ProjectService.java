package com.wt.gpms.teacher.service;

import com.wt.gpms.teacher.pojo.Project;

import java.util.List;

public interface ProjectService {

    Project selectProjectById(Integer pId);

    List<Project> selectProjectList(Project project);

    int insertProject(Project project);

    int updateProject(Project project);

    int deleteProjectById(Integer pId);

    int deleteProjectByIds(Integer[] pIds);

}
