package com.wt.gpms.student.service.impl;

import com.wt.gpms.student.mapper.ProjectProcessMapper;
import com.wt.gpms.student.pojo.ProjectProcess;
import com.wt.gpms.student.service.ProjectProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectProcessServiceImpl implements ProjectProcessService {
    @Autowired
    ProjectProcessMapper projectProcessMapper;

    @Override
    public ProjectProcess selectProjectProcessById(Integer ppId) {
        return projectProcessMapper.selectProjectProcessById(ppId);
    }

    @Override
    public List<ProjectProcess> selectProjectProcessList(ProjectProcess projectProcess) {
        return projectProcessMapper.selectProjectProcessList(projectProcess);
    }

    @Override
    public int insertProjectProcess(ProjectProcess projectProcess) {
        return projectProcessMapper.insertProjectProcess(projectProcess);
    }

    @Override
    public int updateProjectProcess(ProjectProcess projectProcess) {
        return projectProcessMapper.updateProjectProcess(projectProcess);
    }

    @Override
    public int deleteProjectProcessById(Integer ppId) {
        return projectProcessMapper.deleteProjectProcessById(ppId);
    }

    @Override
    public int deleteProjectProcessByIds(Integer[] ppIds) {
        return projectProcessMapper.deleteProjectProcessByIds(ppIds);
    }

    @Override
    public int deleteProjectProcessBypId(Integer pId) {
        return projectProcessMapper.deleteProjectProcessBypId(pId);
    }
}
