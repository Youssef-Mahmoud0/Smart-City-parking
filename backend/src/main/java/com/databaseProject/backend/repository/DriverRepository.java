package com.databaseProject.backend.repository;

import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.mapper.sqlMapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addDriver(Driver driver) {
        String sql = "INSERT INTO driver(name, phoneNumber, licensePlateNumber, paymentMethod, password) VALUES(?,?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql, driver.getName(), driver.getPhoneNumber(), driver.getLicensePlateNumber(), driver.getPaymentMethod(), driver.getPassword());
        return rowsAffected == 1;
    }

    public Driver getDriverProfile(int driverID) {
        String sql = "SELECT * FROM driver WHERE driverID = " + driverID;
        List<Driver> result = jdbcTemplate.query(sql, new DriverMapper());
        return result.getFirst();
    }
}
