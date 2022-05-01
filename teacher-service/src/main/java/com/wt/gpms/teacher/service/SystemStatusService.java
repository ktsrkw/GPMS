package com.wt.gpms.teacher.service;

public interface SystemStatusService {
    Integer getCreateStatus();

    Integer getChooseStatus();

    int closeCreate();

    int openCreate();

    int closeChoose();

    int openChoose();
}
