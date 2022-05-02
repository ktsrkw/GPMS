package com.wt.gpms.teacher.mapper;

import java.util.List;
import com.wt.gpms.teacher.pojo.ProjectFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProjectFileMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param pfId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    ProjectFile selectProjectFileById(Integer pfId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectFile 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<ProjectFile> selectProjectFileList(ProjectFile projectFile);

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectFile 【请填写功能名称】
     * @return 结果
     */
    int insertProjectFile(ProjectFile projectFile);

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectFile 【请填写功能名称】
     * @return 结果
     */
    int updateProjectFile(ProjectFile projectFile);

    /**
     * 删除【请填写功能名称】
     * 
     * @param pfId 【请填写功能名称】ID
     * @return 结果
     */
    int deleteProjectFileById(Integer pfId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param pfIds 需要删除的数据ID
     * @return 结果
     */
    int deleteProjectFileByIds(Integer[] pfIds);
}
