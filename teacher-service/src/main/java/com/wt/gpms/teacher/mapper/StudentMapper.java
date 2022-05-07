package com.wt.gpms.teacher.mapper;

import com.wt.gpms.teacher.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    List<Student> allStudents();

    Student getStudentById(Integer sId);

    int updateStudentInfo(Student student);

    int deleteStudentById(Integer sId);

    List<Student> searchStudent(String searchString);

    int addStudent(Student student);

    Student selectStudentByNo(String sNo);
}
