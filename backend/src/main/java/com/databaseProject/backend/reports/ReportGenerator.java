package com.databaseProject.backend.reports;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportGenerator {

    @Autowired
    private DataSource dataSource;

    public void generateReport() {
        try (Connection jdbcConnection = dataSource.getConnection()) {
            // Load and compile the .jrxml file
            String reportPath = "src/main/resources/reports/ParkingLotPerformance.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Parking Lot Performance Report");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jdbcConnection);

            // Export to PDF
            String outputFile = "ParkingLotPerformanceReport.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            System.out.println("Report generated at: " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
