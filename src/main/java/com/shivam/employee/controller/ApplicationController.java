package com.shivam.employee.controller;

import com.shivam.employee.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Autowired
    private ThreadService threadService;

    @RequestMapping("/")
    public ResponseEntity<String> app() {
        return new ResponseEntity<>("Welcome to Employee service. You can access swagger at--", HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        this.threadService.testCompletionService();
        return new ResponseEntity<>("Welcome to Employee service. You can access swagger at--", HttpStatus.OK);
    }


}
