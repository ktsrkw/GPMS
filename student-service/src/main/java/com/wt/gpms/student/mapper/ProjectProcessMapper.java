package com.wt.gpms.student.mapper;

import java.util.List;
import com.wt.gpms.student.pojo.ProjectProcess;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProjectProcessMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param ppId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ProjectProcess selectProjectProcessById(Integer ppId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectProcess 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ProjectProcess> selectProjectProcessList(ProjectProcess projectProcess);

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectProcess 【请填写功能名称】
     * @return 结果
     */
    public int insertProjectProcess(ProjectProcess projectProcess);

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectProcess 【请填写功能名称】
     * @return 结果
     */
    public int updateProjectProcess(ProjectProcess projectProcess);

    /**
     * 删除【请填写功能名称】
     * 
     * @param ppId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteProjectProcessById(Integer ppId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ppIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProjectProcessByIds(Integer[] ppIds);

    int deleteProjectProcessBypId(Integer pId);
}
