package com.shivam.employee.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@Profile("local-1")
public class BeanProfileTest1 {
    @PostConstruct
    public void init(){
      log.info("Initialized BeanProfileTest1");
    }
}
