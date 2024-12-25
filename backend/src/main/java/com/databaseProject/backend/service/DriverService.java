package com.databaseProject.backend.service;

import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.entity.Notification;
import com.databaseProject.backend.repository.DriverRepository;
import com.databaseProject.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;



    public boolean addDriver(Driver driver) {
        return driverRepository.addDriver(driver);
    }

    public Driver getDriverProfile(int driverID) {
        return driverRepository.getDriverProfile(driverID);
    }

}
