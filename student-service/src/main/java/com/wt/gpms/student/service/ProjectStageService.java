package com.wt.gpms.student.service;

import com.wt.gpms.student.pojo.ProjectStage;

import java.util.List;

public interface ProjectStageService {

    /**
     * 查询【请填写功能名称】
     *
     * @param psId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    ProjectStage selectProjectStageById(Integer psId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param projectStage 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<ProjectStage> selectProjectStageList(ProjectStage projectStage);

    /**
     * 新增【请填写功能名称】
     *
     * @param projectStage 【请填写功能名称】
     * @return 结果
     */
    int insertProjectStage(ProjectStage projectStage);

    /**
     * 修改【请填写功能名称】
     *
     * @param projectStage 【请填写功能名称】
     * @return 结果
     */
    int updateProjectStage(ProjectStage projectStage);

    /**
     * 删除【请填写功能名称】
     *
     * @param psId 【请填写功能名称】ID
     * @return 结果
     */
    int deleteProjectStageById(Integer psId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param psIds 需要删除的数据ID
     * @return 结果
     */
    int deleteProjectStageByIds(Integer[] psIds);
}
