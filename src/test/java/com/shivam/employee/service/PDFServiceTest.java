package com.shivam.employee.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(MockitoJUnitRunner.class)
public class PDFServiceTest {

    @InjectMocks
    private PDFService pdfService;

    @Test
    public void test() {
        String dob = "";
        for (int i = 0; i <= 99999; i++) {
            try (InputStream inputStream = new FileInputStream("E:\\projects\\my projects\\employee-service\\src\\test\\java\\com\\shivam\\employee\\service\\file.pdf")) {
                String password = String.format("%05d", i) + dob;
                String s = pdfService.readProtectedPDF(inputStream, password);
                if (StringUtils.isNotBlank(s)) {
                    System.out.println(s);
                    break;
                }
            } catch (Exception ignored) {
            }
        }
    }
}