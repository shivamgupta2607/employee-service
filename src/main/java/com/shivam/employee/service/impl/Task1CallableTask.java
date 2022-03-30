package com.shivam.employee.service.impl;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;

@Log4j2
public class Task1CallableTask implements Callable<String> {

    private final String code;
    private final Task1Service task1Service;

    Task1CallableTask(final String code, final Task1Service task1Service) {
        this.code = code;
        this.task1Service = task1Service;
    }

    @Override
    public String call() throws Exception {
        this.task1Service.performTask1Service(code);
        return "SUCCESS";
    }
}
