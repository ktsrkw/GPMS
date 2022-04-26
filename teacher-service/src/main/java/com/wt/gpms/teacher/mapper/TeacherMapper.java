package com.wt.gpms.teacher.mapper;

import com.wt.gpms.teacher.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {
    List<Teacher> allTeachers();
}
