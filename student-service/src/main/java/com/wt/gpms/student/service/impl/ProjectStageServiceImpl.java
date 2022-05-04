package com.wt.gpms.student.service.impl;

import com.wt.gpms.student.mapper.ProjectStageMapper;
import com.wt.gpms.student.pojo.ProjectStage;
import com.wt.gpms.student.service.ProjectStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectStageServiceImpl implements ProjectStageService {
    @Autowired
    ProjectStageMapper projectStageMapper;

    @Override
    public ProjectStage selectProjectStageById(Integer psId) {
        return projectStageMapper.selectProjectStageById(psId);
    }

    @Override
    public List<ProjectStage> selectProjectStageList(ProjectStage projectStage) {
        return projectStageMapper.selectProjectStageList(projectStage);
    }

    @Override
    public int insertProjectStage(ProjectStage projectStage) {
        return projectStageMapper.insertProjectStage(projectStage);
    }

    @Override
    public int updateProjectStage(ProjectStage projectStage) {
        return projectStageMapper.updateProjectStage(projectStage);
    }

    @Override
    public int deleteProjectStageById(Integer psId) {
        return projectStageMapper.deleteProjectStageById(psId);
    }

    @Override
    public int deleteProjectStageByIds(Integer[] psIds) {
        return projectStageMapper.deleteProjectStageByIds(psIds);
    }

    @Override
    public int projectChooseCancel(Integer pId) {
        return projectStageMapper.projectChooseCancel(pId);
    }
}
