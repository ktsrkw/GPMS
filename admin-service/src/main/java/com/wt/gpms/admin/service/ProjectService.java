package com.wt.gpms.admin.service;

import com.wt.gpms.admin.pojo.Project;

import java.util.List;

public interface ProjectService {

    Project selectProjectById(Integer pId);

    List<Project> selectProjectList(Project project);

    int insertProject(Project project);

    int updateProject(Project project);

    int deleteProjectById(Integer pId);

    int deleteProjectByIds(Integer[] pIds);

}
