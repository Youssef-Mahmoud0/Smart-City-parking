package com.databaseProject.backend.controller;

import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.entity.Notification;
import com.databaseProject.backend.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class DriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping("/driver/add")
    public boolean addDriver(@RequestBody Driver driver) {
        return driverService.addDriver(driver);
    }

    @GetMapping("/driver/{driverID}")
    public Driver getDriverProfile(@PathVariable int driverID) {
        return driverService.getDriverProfile(driverID);
    }


}
