package com.wt.gpms.teacher.service;

import com.wt.gpms.teacher.pojo.ProjectProcess;

import java.util.List;

public interface ProjectProcessService {

    ProjectProcess selectProjectProcessById(Integer ppId);

    List<ProjectProcess> selectProjectProcessList(ProjectProcess projectProcess);

    int insertProjectProcess(ProjectProcess projectProcess);

    int updateProjectProcess(ProjectProcess projectProcess);

    int deleteProjectProcessById(Integer ppId);

    int deleteProjectProcessByIds(Integer[] ppIds);

    int deleteProjectProcessBypId(Integer pId);
}
