package com.wt.gpms.teacher.service;

import com.wt.gpms.teacher.pojo.Student;

import java.util.List;

public interface StudentService {
    List<Student> allStudents();

    Student getStudentById(Integer sId);

    int updateStudentInfo(Student student);

    int deleteStudentById(Integer sId);

    List<Student> searchStudent(String searchString);

    int addStudent(Student student);

    Student selectStudentByNo(String sNo);
}
