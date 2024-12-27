package com.databaseProject.backend.service;

import com.databaseProject.backend.dto.DriverDto;
import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.mapper.dtoMapper.DriverMapper;
import com.databaseProject.backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public DriverDto fetchDriver(int driverId) {
        Driver driver =  driverRepository.findById(driverId);
        return DriverMapper.toDto(driver);
    }

}
