package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.dto.SpotReservationDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpotReservationMapper implements RowMapper<SpotReservationDto> {

    public SpotReservationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        SpotReservationDto dto = new SpotReservationDto();
        dto.setSpotId(rs.getInt("spot_id"));
        if (rs.getObject("start_time") != null) {
            dto.setDriverId(rs.getInt("driver_id"));
            dto.setStartTime(rs.getTimestamp("start_time"));
            dto.setEndTime(rs.getTimestamp("end_time"));
            dto.setStatus(rs.getString("status"));
            dto.setPrice(rs.getDouble("price"));
            dto.setPenalty(rs.getDouble("penalty"));
        }
        return dto;
    }
}
