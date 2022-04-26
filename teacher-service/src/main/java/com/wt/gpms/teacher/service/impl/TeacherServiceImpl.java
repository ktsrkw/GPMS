package com.wt.gpms.teacher.service.impl;

import com.wt.gpms.teacher.mapper.TeacherMapper;
import com.wt.gpms.teacher.pojo.Teacher;
import com.wt.gpms.teacher.service.TeacherService;
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
}
