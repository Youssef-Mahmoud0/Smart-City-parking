package com.databaseProject.backend.repository;

import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.dto.SpotReservationDto;
import com.databaseProject.backend.mapper.sqlMapper.ReservationMapper;
import com.databaseProject.backend.mapper.sqlMapper.ReservationWithDriverMapper;
import com.databaseProject.backend.mapper.sqlMapper.SpotReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // allow reservation if the status is AVAILABLE applying concurrency control (SELECT ... FOR UPDATE)
//    public boolean isSpotAvailableWithLock(int spotId) {
//        String sql = "SELECT status FROM parking_spot WHERE spot_id = ? FOR UPDATE";
//        String status = jdbcTemplate.queryForObject(sql, String.class, spotId);
//        return "AVAILABLE".equals(status);
//    }

    public boolean checkOverlapping(int spotId, Timestamp startTime, Timestamp endTime) {
        String sql = "SELECT COUNT(*) FROM reservation " +
                     "WHERE spot_id = ? " +
                     "AND status IN ('WAITING_FOR_ARRIVAL', 'DRIVER_ARRIVED') " +
                     "AND (start_time < ? AND end_time > ?)";
        int overlappingReservations =  jdbcTemplate.queryForObject(sql, Integer.class, spotId, endTime, startTime);

        return (overlappingReservations > 0);
    }

    // price is a placeholder for now
    public void createReservation(int lotId, int spotId, Timestamp startTime, Timestamp endTime, int driverId) {
        String reservationSql = "INSERT INTO reservation (lot_id, spot_id, start_time, end_time, driver_id, status, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(reservationSql, lotId ,spotId, startTime, endTime, driverId, "WAITING_FOR_ARRIVAL", 0.0);

        String updateSpotStatusSql = "UPDATE parking_spot SET status = 'RESERVED' WHERE spot_id = ?";
        jdbcTemplate.update(updateSpotStatusSql, spotId);
    }

    public void cancelReservation(int spotId, int driverId) {
        String lockSql = "SELECT status FROM parking_spot WHERE spot_id = ? FOR UPDATE";
        jdbcTemplate.queryForObject(lockSql, String.class, spotId);

        String cancelReservationSql = "UPDATE reservation SET status = 'CANCELLED' WHERE spot_id = ? AND driver_id = ?";
        jdbcTemplate.update(cancelReservationSql, spotId, driverId);

        String updateSpotStatusSql = "UPDATE parking_spot SET status = 'AVAILABLE' WHERE spot_id = ?";
        jdbcTemplate.update(updateSpotStatusSql, spotId);
    }

    public void completeReservation(int spotId, int driverId) {
        String lockSql = "SELECT status FROM parking_spot WHERE spot_id = ? FOR UPDATE";
        jdbcTemplate.queryForObject(lockSql, String.class, spotId);

        String completeReservationSql = "UPDATE reservation SET status = 'COMPLETED' WHERE spot_id = ? AND driver_id = ?";
        jdbcTemplate.update(completeReservationSql, spotId, driverId);

        String updateSpotStatusSql = "UPDATE parking_spot SET status = 'AVAILABLE' WHERE spot_id = ?";
        jdbcTemplate.update(updateSpotStatusSql, spotId);
    }

    public List<ReservationDto> getReservationsById(int driverId) {
        String getReservationsSql = "SELECT * FROM reservation WHERE driver_id = ?";
        return jdbcTemplate.query(getReservationsSql, new Object[]{driverId}, new ReservationMapper());
    }

    public List<ReservationDto> getReservationsBySpotId(int spotId) {
        String getReservationsSql = "SELECT * FROM reservation WHERE spot_id = ?";
        return jdbcTemplate.query(getReservationsSql, new Object[]{spotId}, new ReservationMapper());
    }

    // change spot status from reserved to occupied and reservation status from waiting for arrival to driver arrived
    public void checkIn(int reservationId, int spotId, int driverId) {
        String lockSql = "SELECT status FROM parking_spot WHERE spot_id = ? FOR UPDATE";
        jdbcTemplate.queryForObject(lockSql, String.class, spotId);

        String checkInSql = "UPDATE reservation SET status = 'DRIVER_ARRIVED' WHERE reservation_id = ? AND spot_id = ? AND driver_id = ?";
        jdbcTemplate.update(checkInSql, reservationId, spotId, driverId);

        String updateSpotStatusSql = "UPDATE parking_spot SET status = 'OCCUPIED' WHERE spot_id = ?";
        jdbcTemplate.update(updateSpotStatusSql, spotId);
    }
    public List<ReservationDto> getReservationsWithDriversBySpotId(int spotId) {
        String sql = """
    SELECT
        r.spot_id, r.lot_id, r.driver_id, r.start_time, r.end_time, r.status, r.price, r.penalty,
        d.driver_id, d.license_plate_number, d.name, d.email, d.phone_number, d.payment_method, d.password
    FROM
        reservation r
    JOIN
        driver d
    ON
        r.driver_id = d.driver_id
    WHERE
        r.spot_id = ?;
""";

        return jdbcTemplate.query(sql, new Object[]{spotId}, new ReservationWithDriverMapper());
    }
    public double getReservationPrice(int spotId, Timestamp startTime, Timestamp endTime) {
        String sql = "{call get_dynamic_spot_price(?, ?, ?)}";
        try {
            double price = jdbcTemplate.queryForObject(sql, new Object[]{spotId, startTime, endTime}, Double.class);
            System.out.println(price);
            return price;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get reservation price.");
        }
    }
}
