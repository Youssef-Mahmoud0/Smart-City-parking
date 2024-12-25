package com.databaseProject.backend.mapper.sqlMapper;


import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverMapper implements RowMapper<Driver> {
    @Override
    public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
        Driver driver = new Driver();
        driver.setDriverID(rs.getInt("driverID"));
        driver.setName(rs.getString("name"));
        driver.setPhoneNumber(rs.getString("phoneNumber"));
        driver.setLicensePlateNumber(rs.getString("licensePlateNumber"));
        driver.setPaymentMethod(rs.getInt("paymentMethod"));
        driver.setPassword(rs.getString("password"));
        return driver;
    }
}
