package com.wt.gpms.teacher.service;

import com.wt.gpms.teacher.pojo.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> allTeachers();

    /**
     * 查询【请填写功能名称】
     *
     * @param tId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Teacher selectTeacherById(Integer tId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param teacher 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);

    /**
     * 新增【请填写功能名称】
     *
     * @param teacher 【请填写功能名称】
     * @return 结果
     */
    public int insertTeacher(Teacher teacher);

    /**
     * 修改【请填写功能名称】
     *
     * @param teacher 【请填写功能名称】
     * @return 结果
     */
    public int updateTeacher(Teacher teacher);

    /**
     * 删除【请填写功能名称】
     *
     * @param tId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTeacherById(Integer tId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param tIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTeacherByIds(Integer[] tIds);

    List<Teacher> searchTeachers(String searchString);

    Teacher selectTeacherByNo(String tNo);
}
