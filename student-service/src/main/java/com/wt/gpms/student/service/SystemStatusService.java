package com.wt.gpms.student.service;

public interface SystemStatusService {
    Integer getCreateStatus();

    Integer getChooseStatus();

    int closeCreate();

    int openCreate();

    int closeChoose();

    int openChoose();
}
