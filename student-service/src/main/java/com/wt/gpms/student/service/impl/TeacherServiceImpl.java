package com.wt.gpms.student.service.impl;

import com.wt.gpms.student.mapper.TeacherMapper;
import com.wt.gpms.student.pojo.Teacher;
import com.wt.gpms.student.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public List<Teacher> allTeachers() {
        return teacherMapper.allTeachers();
    }

    @Override
    public Teacher selectTeacherById(Integer tId) {
        return teacherMapper.selectTeacherById(tId);
    }

    @Override
    public List<Teacher> selectTeacherList(Teacher teacher) {
        return teacherMapper.selectTeacherList(teacher);
    }

    @Override
    public int insertTeacher(Teacher teacher) {
        return teacherMapper.insertTeacher(teacher);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public int deleteTeacherById(Integer tId) {
        return teacherMapper.deleteTeacherById(tId);
    }

    @Override
    public int deleteTeacherByIds(Integer[] tIds) {
        return teacherMapper.deleteTeacherByIds(tIds);
    }

    @Override
    public List<Teacher> searchTeachers(String searchString) {
        return teacherMapper.searchTeachers(searchString);
    }

    @Override
    public Teacher selectTeacherByNo(String tNo) {
        return teacherMapper.selectTeacherByNo(tNo);
    }
}
