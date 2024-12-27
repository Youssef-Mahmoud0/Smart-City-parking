package com.databaseProject.backend.repository;

import com.databaseProject.backend.dto.ParkingLotDto;
import com.databaseProject.backend.dto.ParkingSpotDto;
import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.dto.SpotReservationDto;
import com.databaseProject.backend.mapper.sqlMapper.ParkingLotMapper;
import com.databaseProject.backend.mapper.sqlMapper.ParkingSpotMapper;
import com.databaseProject.backend.mapper.sqlMapper.ReservationMapper;
import com.databaseProject.backend.mapper.sqlMapper.SpotReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ParkingLotRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ParkingLotDto> getAllLots() {
        String sql = "SELECT * FROM parking_lot";
        return jdbcTemplate.query(sql, new ParkingLotMapper());
    }

    public List<ParkingSpotDto> getAllSpots(int lotId) {
        String sql = "SELECT * FROM parking_spot WHERE lot_id = ? ORDER BY `order`";
        return jdbcTemplate.query(sql, new Object[]{lotId}, new ParkingSpotMapper());
    }

    public List<SpotReservationDto> getSpotReservations(int lotId) {
        String sql = """
                    SELECT r.reservation_id, ps.spot_id, r.start_time, r.end_time, r.status, r.driver_id, r.end_time
                    FROM parking_spot ps
                    LEFT JOIN reservation r ON ps.spot_id = r.spot_id
                    WHERE ps.lot_id = ?
                    ORDER BY ps.spot_id, r.start_time
        """;
        return jdbcTemplate.query(sql, new Object[]{lotId}, new SpotReservationMapper());
    }
}
