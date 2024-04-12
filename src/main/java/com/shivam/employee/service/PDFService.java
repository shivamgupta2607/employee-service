package com.shivam.employee.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PDFService {


    public String readProtectedPDF(InputStream pdfInputStream, String password) {
        try (PDDocument document = PDDocument.load(pdfInputStream, password)) {
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            } else {
                System.out.println("**********PASSWORD IS **********" + password);
                return "**********PASSWORD IS ********** " + password;
            }
        } catch (IOException e) {
            return null;
        }
    }
}