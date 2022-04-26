package com.wt.gpms.student.service.impl;

import com.wt.gpms.student.mapper.StudentMapper;
import com.wt.gpms.student.pojo.Student;
import com.wt.gpms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<Student> allStudents() {
        return studentMapper.allStudents();
    }
}
