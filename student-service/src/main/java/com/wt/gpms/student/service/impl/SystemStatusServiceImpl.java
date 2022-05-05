package com.wt.gpms.student.service.impl;

import com.wt.gpms.student.mapper.SystemStatusMapper;
import com.wt.gpms.student.service.SystemStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemStatusServiceImpl implements SystemStatusService {
    @Autowired
    SystemStatusMapper systemStatusMapper;

    @Override
    public Integer getCreateStatus() {
        return systemStatusMapper.getCreateStatus();
    }

    @Override
    public Integer getChooseStatus() {
        return systemStatusMapper.getChooseStatus();
    }

    @Override
    public int closeCreate() {
        return systemStatusMapper.closeCreate();
    }

    @Override
    public int openCreate() {
        return systemStatusMapper.openCreate();
    }

    @Override
    public int closeChoose() {
        return systemStatusMapper.closeChoose();
    }

    @Override
    public int openChoose() {
        return systemStatusMapper.openChoose();
    }
}
