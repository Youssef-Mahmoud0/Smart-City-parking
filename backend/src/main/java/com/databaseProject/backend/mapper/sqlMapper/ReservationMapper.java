package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.entity.Reservation;
import com.databaseProject.backend.enums.ReservationStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<ReservationDto> {
    @Override
    public ReservationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setReservationId(rs.getInt("reservation_id"));
        reservationDto.setDriverId(rs.getInt("driver_id"));
        reservationDto.setSpotId(rs.getInt("spot_id"));

        reservationDto.setStartTime(rs.getTimestamp("start_time"));
        reservationDto.setExpectedEndTime(rs.getTimestamp("expected_end_time"));
        reservationDto.setStatus(ReservationStatus.valueOf(rs.getString("status")));

        return reservationDto;
    }
}
