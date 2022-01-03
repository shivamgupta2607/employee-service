package com.shivam.employeeservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @RequestMapping("/")
    public ResponseEntity<String> app() {
        return new ResponseEntity<>("Welcome to Employee service. You can access swagger at--", HttpStatus.OK);
    }


}
