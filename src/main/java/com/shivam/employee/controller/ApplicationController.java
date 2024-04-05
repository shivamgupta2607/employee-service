package com.shivam.employee.controller;

import com.shivam.employee.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@RestController
@Slf4j
public class ApplicationController {

    @Value("${publishDate}")
    public String publishDate;

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
