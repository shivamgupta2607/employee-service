package com.shivam.employee.controller;


import com.shivam.employee.service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @PostMapping("/read")
    public ResponseEntity<String> readProtectedPDF(
            @RequestPart ("file") MultipartFile file,
            @RequestParam("password") String password) {
        try {
            String content = pdfService.readProtectedPDF(file.getInputStream(), password);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to read PDF: " + e.getMessage());
        }
    }
}