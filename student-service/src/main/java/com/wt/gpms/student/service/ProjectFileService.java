package com.wt.gpms.student.service;

import com.wt.gpms.student.pojo.ProjectFile;

import java.util.List;

public interface ProjectFileService {

    ProjectFile selectProjectFileById(Integer pfId);

    List<ProjectFile> selectProjectFileList(ProjectFile projectFile);

    int insertProjectFile(ProjectFile projectFile);

    int updateProjectFile(ProjectFile projectFile);

    int deleteProjectFileById(Integer pfId);

    int deleteProjectFileByIds(Integer[] pfIds);
}
