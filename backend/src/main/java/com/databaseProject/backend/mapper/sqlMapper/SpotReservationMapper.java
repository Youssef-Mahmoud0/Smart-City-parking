package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.dto.SpotReservationDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpotReservationMapper implements RowMapper<SpotReservationDto> {

    @Override
    public SpotReservationDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        if (rs.getObject("reservation_id") == null) return null;

        SpotReservationDto dto = new SpotReservationDto();
        dto.setSpotId(rs.getInt("spot_id"));
        dto.setReservationId(rs.getInt("reservation_id"));
        dto.setDriverId(rs.getInt("driver_id"));
        dto.setStartTime(rs.getTimestamp("start_time"));
        dto.setEndTime(rs.getTimestamp("end_time"));
        dto.setStatus(rs.getString("status"));
        dto.setExpectedEndTime(rs.getTimestamp("expected_end_time"));
        return dto;
    }
}
