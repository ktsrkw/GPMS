package com.wt.gpms.student.mapper;

import com.wt.gpms.student.pojo.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-29
 */
@Mapper
@Repository
public interface ProjectMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param pId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    Project selectProjectById(Integer pId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param project 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<Project> selectProjectList(Project project);

    /**
     * 新增【请填写功能名称】
     * 
     * @param project 【请填写功能名称】
     * @return 结果
     */
    int insertProject(Project project);

    /**
     * 修改【请填写功能名称】
     * 
     * @param project 【请填写功能名称】
     * @return 结果
     */
    int updateProject(Project project);

    /**
     * 删除【请填写功能名称】
     * 
     * @param pId 【请填写功能名称】ID
     * @return 结果
     */
    int deleteProjectById(Integer pId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param pIds 需要删除的数据ID
     * @return 结果
     */
    int deleteProjectByIds(Integer[] pIds);

    List<Project> searchProjects(String searchString);
}
