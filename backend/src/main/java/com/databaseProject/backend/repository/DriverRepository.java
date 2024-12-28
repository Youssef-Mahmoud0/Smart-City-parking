package com.databaseProject.backend.repository;

import com.databaseProject.backend.dto.DriverDto;
import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.entity.User;
import com.databaseProject.backend.mapper.sqlMapper.DriverMapper;
import com.databaseProject.backend.mapper.sqlMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DriverRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Driver driver) {
        System.out.println(driver.getPaymentMethod());
        String sql = "INSERT INTO driver (email, password, license_plate_number, phone_number, name, payment_method) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, driver.getEmail(), driver.getPassword(), driver.getLicensePlateNumber(), driver.getPhoneNumber(), driver.getName(), driver.getPaymentMethod().toString());
    }
    public Optional<Driver> findByEmail(String email) {
        String sql = "SELECT * FROM driver WHERE email = ?";

        try {
            Driver driver = jdbcTemplate.queryForObject(sql, new DriverMapper(), email);
            return Optional.ofNullable(driver);
        } catch (EmptyResultDataAccessException e) {
            // Return empty Optional if no result found
            return Optional.empty();
        }
    }


    public Driver findById(int driverId) {
        System.out.println(driverId + "inside driver repository");
        String sql = "SELECT * FROM driver WHERE driver_id = ?";
        return jdbcTemplate.queryForObject(sql, new DriverMapper(), driverId);
    }
}
