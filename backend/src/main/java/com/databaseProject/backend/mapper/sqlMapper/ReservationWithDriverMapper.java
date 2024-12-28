
package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.dto.DriverDto;
import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.enums.PaymentType;
import com.databaseProject.backend.enums.ReservationStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationWithDriverMapper implements RowMapper<ReservationDto> {
    @Override
    public ReservationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReservationDto reservation = new ReservationDto();
        reservation.setSpotId(rs.getInt("spot_id"));
        reservation.setLotId(rs.getInt("lot_id"));
        reservation.setDriverId(rs.getInt("driver_id"));
        reservation.setStartTime(rs.getTimestamp("start_time"));
        reservation.setEndTime(rs.getTimestamp("end_time"));
        reservation.setStatus(ReservationStatus.valueOf(rs.getString("status")));
        reservation.setPrice(rs.getDouble("price"));
        reservation.setPenalty(rs.getDouble("penalty"));

        DriverDto driver = new DriverDto();
        driver.setDriverId(rs.getInt("driver_id"));
        driver.setLicensePlateNumber(rs.getString("license_plate_number"));
        driver.setName(rs.getString("name"));
        driver.setEmail(rs.getString("email"));
        driver.setPhoneNumber(rs.getString("phone_number"));
        driver.setPaymentMethod(PaymentType.valueOf(rs.getString("payment_method")));
        driver.setPassword(rs.getString("password"));

        reservation.setDriver(driver);
        return reservation;
    }
}