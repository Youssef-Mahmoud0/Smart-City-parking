package com.databaseProject.backend.repository;

import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.mapper.sqlMapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createReservation(int spotId, Timestamp startTime, Timestamp expectedEndTime, int driverId) {
        // status ????????
        String reservationSql = "INSERT INTO reservation (spot_id, start_time, expected_end_time, driver_id, status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(reservationSql, spotId, startTime, expectedEndTime, driverId, "WAITING_FOR_ARRIVAL");

        String updateSpotStatusSql = "UPDATE parking_spot SET status = 'RESERVED' WHERE spot_id = ?";
        jdbcTemplate.update(updateSpotStatusSql, spotId);
    }

    // allow reservation if the status is AVAILABLE
    public boolean isSpotAvailable(int spotId) {
        String sql = "SELECT status FROM parking_spot WHERE spot_id = ?";
        String status = jdbcTemplate.queryForObject(sql, String.class, spotId);
        return "AVAILABLE".equals(status);
    }

    public void cancelReservation(int spotId, int driverId) {
        String cancelReservationSql = "UPDATE reservation SET status = 'CANCELLED' WHERE spot_id = ? AND driver_id = ?";
        jdbcTemplate.update(cancelReservationSql, spotId, driverId);

        String updateSpotStatusSql = "UPDATE parking_spot SET status = 'AVAILABLE' WHERE spot_id = ?";
        jdbcTemplate.update(updateSpotStatusSql, spotId);
    }

    public void completeReservation(int spotId, int driverId) {
        String completeReservationSql = "UPDATE reservation SET status = 'COMPLETED' WHERE spot_id = ? AND driver_id = ?";
        jdbcTemplate.update(completeReservationSql, spotId, driverId);

        String updateSpotStatusSql = "UPDATE parking_spot SET status = 'AVAILABLE' WHERE spot_id = ?";
        jdbcTemplate.update(updateSpotStatusSql, spotId);
    }

    public List<ReservationDto> getReservationsById(int driverId) {
        String getReservationsSql = "SELECT * FROM reservation WHERE driver_id = ?";
        return jdbcTemplate.query(getReservationsSql, new Object[]{driverId}, new ReservationMapper());
    }

}
