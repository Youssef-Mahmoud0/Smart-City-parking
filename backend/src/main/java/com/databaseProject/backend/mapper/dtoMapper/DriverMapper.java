package com.databaseProject.backend.mapper.dtoMapper;


import com.databaseProject.backend.dto.DriverDto;
import com.databaseProject.backend.entity.Driver;

public class DriverMapper {


    public static DriverDto toDto(Driver driver) {
        DriverDto driverDto = new DriverDto();

        driverDto.setDriverId(driver.getDriverId());
        driverDto.setEmail(driver.getEmail());
        driverDto.setLicensePlateNumber(driver.getLicensePlateNumber());
        driverDto.setName(driver.getName());
        driverDto.setPaymentMethod(driver.getPaymentMethod());
        driverDto.setPhoneNumber(driver.getPhoneNumber());
        driverDto.setPenalty(driver.getPenalty());
        return driverDto;
    }



}
