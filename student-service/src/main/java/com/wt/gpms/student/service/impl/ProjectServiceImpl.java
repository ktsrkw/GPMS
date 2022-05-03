package com.wt.gpms.student.service.impl;

import com.wt.gpms.student.mapper.ProjectMapper;
import com.wt.gpms.student.pojo.Project;
import com.wt.gpms.student.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectMapper projectMapper;

    @Override
    public Project selectProjectById(Integer pId) {
        return projectMapper.selectProjectById(pId);
    }

    @Override
    public List<Project> selectProjectList(Project project) {
        return projectMapper.selectProjectList(project);
    }

    @Override
    public int insertProject(Project project) {
        return projectMapper.insertProject(project);
    }

    @Override
    public int updateProject(Project project) {
        return projectMapper.updateProject(project);
    }

    @Override
    public int deleteProjectById(Integer pId) {
        return projectMapper.deleteProjectById(pId);
    }

    @Override
    public int deleteProjectByIds(Integer[] pIds) {
        return projectMapper.deleteProjectByIds(pIds);
    }

    @Override
    public List<Project> searchProjects(String searchString) {
        return projectMapper.searchProjects(searchString);
    }
}
