package com.databaseProject.backend.reports;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportGenerator {
    String managerReportPath = "E:/3rd-year-1st term/Database/project/Smart-City-parking/backend/src/main/resources/reports/ManagerParkingLotPerformance.jrxml";
    String adminReportPath = "E:/3rd-year-1st term/Database/project/Smart-City-parking/backend/src/main/resources/reports/AdminParkingLotPerformance.jrxml";
    @Autowired
    private DataSource dataSource;

    public byte[] generateReport(boolean isAdmin, int mgrId) throws JRException {
        try (Connection jdbcConnection = dataSource.getConnection()) {
            // Load and compile the .jrxml file
            JasperReport jasperReport = JasperCompileManager.compileReport(isAdmin ? adminReportPath: managerReportPath);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Parking Lot Performance Report");
            if (!isAdmin) {
                parameters.put("MANAGER_ID", mgrId);
            }
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jdbcConnection);

            // Export to PDF and return as byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            System.out.println("Report generated successfully");
            // Return the generated PDF as a byte array
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new JRException("Error generating report", e);
        }
    }
}
