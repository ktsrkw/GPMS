package com.wt.gpms.teacher.service.impl;

import com.wt.gpms.teacher.mapper.ProjectFileMapper;
import com.wt.gpms.teacher.pojo.ProjectFile;
import com.wt.gpms.teacher.service.ProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectFileServiceImpl implements ProjectFileService {
    @Autowired
    ProjectFileMapper projectFileMapper;

    @Override
    public ProjectFile selectProjectFileById(Integer pfId) {
        return projectFileMapper.selectProjectFileById(pfId);
    }

    @Override
    public List<ProjectFile> selectProjectFileList(ProjectFile projectFile) {
        return projectFileMapper.selectProjectFileList(projectFile);
    }

    @Override
    public int insertProjectFile(ProjectFile projectFile) {
        return projectFileMapper.insertProjectFile(projectFile);
    }

    @Override
    public int updateProjectFile(ProjectFile projectFile) {
        return projectFileMapper.updateProjectFile(projectFile);
    }

    @Override
    public int deleteProjectFileById(Integer pfId) {
        return projectFileMapper.deleteProjectFileById(pfId);
    }

    @Override
    public int deleteProjectFileByIds(Integer[] pfIds) {
        return projectFileMapper.deleteProjectFileByIds(pfIds);
    }
}
