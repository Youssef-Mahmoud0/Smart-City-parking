package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.enums.PaymentType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverMapper implements RowMapper<Driver> {
    @Override
    public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
        Driver driver = new Driver();
        driver.setDriverId(rs.getInt("driver_id"));
        driver.setName(rs.getString("name"));
        driver.setEmail(rs.getString("email"));
        driver.setPhoneNumber(rs.getString("phone_number"));
        driver.setLicensePlateNumber(rs.getString("license_plate_number"));

        String paymentMethod = rs.getString("payment_method");
        if (paymentMethod != null) {
            try {
                driver.setPaymentMethod(PaymentType.valueOf(paymentMethod));
            } catch (IllegalArgumentException e) {
                throw new SQLException("Invalid payment method value: " + paymentMethod, e);
            }
        } else {
            driver.setPaymentMethod(null); // Or set a default value if required
        }

        driver.setPassword(rs.getString("password"));
        driver.setCreatedAt(rs.getTimestamp("created_at"));
        driver.setUpdatedAt(rs.getTimestamp("updated_at"));
        return driver;
    }
}
