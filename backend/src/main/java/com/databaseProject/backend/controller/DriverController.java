package com.databaseProject.backend.controller;


import com.databaseProject.backend.dto.DriverDto;
import com.databaseProject.backend.service.DriverService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @CrossOrigin(origins = "*")
    @GetMapping("/driver")
    public ResponseEntity<?> fetchDriver( HttpServletRequest request ) {
        try {
            int driverId = (int) request.getAttribute("id");

            System.out.println("Driver id inside driver controller: " + driverId);

            DriverDto result = driverService.fetchDriver(driverId);
            System.out.println("Driver fetched: " + result);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to fetch driver.");
        }
    }


}
