package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.dto.ParkingSpotDto;
import com.databaseProject.backend.enums.SpotStatus;
import com.databaseProject.backend.enums.SpotType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingSpotMapper implements RowMapper<ParkingSpotDto> {
    @Override
    public ParkingSpotDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();

        parkingSpotDto.setSpotId(rs.getInt("spot_id"));
        parkingSpotDto.setLotId(rs.getInt("lot_id"));
        parkingSpotDto.setStatus(SpotStatus.valueOf(rs.getString("status")));
        parkingSpotDto.setType(SpotType.valueOf(rs.getString("type")));
        parkingSpotDto.setOrder(rs.getShort("order"));

        return parkingSpotDto;
    }
}
