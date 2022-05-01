package com.wt.gpms.teacher.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SystemStatusMapper {

    Integer getCreateStatus();

    Integer getChooseStatus();

    int closeCreate();

    int openCreate();

    int closeChoose();

    int openChoose();
}
