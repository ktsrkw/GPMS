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

    @Override
    public Student getStudentById(Integer sId) {
        return studentMapper.getStudentById(sId);
    }

    @Override
    public int updateStudentInfo(Student student) {
        return studentMapper.updateStudentInfo(student);
    }

    @Override
    public int deleteStudentById(Integer sId) {
        return studentMapper.deleteStudentById(sId);
    }

    @Override
    public List<Student> searchStudent(String searchString) {
        return studentMapper.searchStudent(searchString);
    }

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public Student selectStudentByNo(String sNo) {
        return studentMapper.selectStudentByNo(sNo);
    }
}
