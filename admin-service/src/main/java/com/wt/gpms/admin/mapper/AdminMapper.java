package com.wt.gpms.admin.mapper;

import com.wt.gpms.admin.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    List<Admin> allAdmins();

    Admin selectAdminByName(String username);
}
