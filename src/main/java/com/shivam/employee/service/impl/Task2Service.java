package com.shivam.employee.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class Task2Service {

    public void performTask2Service(String code) {
        log.error("Going to execute task 2 service");

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.error("task2 service executed");
    }

}
