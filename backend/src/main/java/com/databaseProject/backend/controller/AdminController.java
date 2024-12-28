package com.databaseProject.backend.controller;

import com.databaseProject.backend.reports.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private ReportGenerator reportGenerator;

    @CrossOrigin(origins = "*")
    @GetMapping("/generate-report-admin")
    public ResponseEntity<byte[]> getPdfReport() {
        try {
            byte[] pdfBytes = reportGenerator.generateReport(true, 0);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "inline; filename=\"ParkingLotPerformanceReport.pdf\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
