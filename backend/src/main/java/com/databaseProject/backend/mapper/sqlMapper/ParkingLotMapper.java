package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.dto.ParkingLotDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingLotMapper implements RowMapper<ParkingLotDto> {

    @Override
    public ParkingLotDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        ParkingLotDto parkingLotDto = new ParkingLotDto();
        parkingLotDto.setLotId(rs.getInt("lot_id"));
        parkingLotDto.setMgrId(rs.getInt("mgr_id"));
        parkingLotDto.setLatitude(rs.getDouble("latitude"));
        parkingLotDto.setLongitude(rs.getDouble("longitude"));
        parkingLotDto.setCapacity(rs.getInt("capacity"));
        parkingLotDto.setBasePrice(rs.getDouble("base_price"));
        return parkingLotDto;
    }
}
