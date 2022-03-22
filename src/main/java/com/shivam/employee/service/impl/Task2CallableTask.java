package com.shivam.employee.service.impl;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;

@Log4j2
public class Task2CallableTask implements Callable<String> {

    String code;
    private Task2Service task2Service;

    Task2CallableTask(String code, Task2Service task2Service) {
        this.code = code;
        this.task2Service = task2Service;
    }


    @Override
    public String call() throws Exception {
        this.task2Service.performTask2Service(code);
        return "SUCCESS";
    }
}
