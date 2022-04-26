package com.wt.gpms.student.mapper;

import com.wt.gpms.student.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    List<Student> allStudents();
}
