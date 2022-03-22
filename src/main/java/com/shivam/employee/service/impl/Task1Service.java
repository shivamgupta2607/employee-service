package com.shivam.employee.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class Task1Service {

    public void performTask1Service(String code) {
        log.error("Going to execute task 1 service");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.error("task1 service executed");
    }

}
