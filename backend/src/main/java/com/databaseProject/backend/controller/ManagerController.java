package com.databaseProject.backend.controller;

import com.databaseProject.backend.reports.ReportGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ManagerController {
    @Autowired
    private ReportGenerator reportGenerator;

    @CrossOrigin(origins = "*")
    @GetMapping("/generate-report-manager")
    public ResponseEntity<byte[]> getPdfReportManager(HttpServletRequest request) {
        try {
            Object id = request.getAttribute("id");
            byte[] pdfBytes = reportGenerator.generateReport(false, Integer.parseInt(id.toString()));
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
