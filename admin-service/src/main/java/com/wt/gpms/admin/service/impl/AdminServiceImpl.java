package com.wt.gpms.admin.service.impl;

import com.wt.gpms.admin.mapper.AdminMapper;
import com.wt.gpms.admin.pojo.Admin;
import com.wt.gpms.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> allAdmins() {
        return adminMapper.allAdmins();
    }

    @Override
    public Admin selectAdminByName(String username) {
        return adminMapper.selectAdminByName(username);
    }
}
