package com.databaseProject.backend.dto;

import com.databaseProject.backend.enums.SpotStatus;
import com.databaseProject.backend.enums.SpotType;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpotDto {
    private int spotId;
    private int lotId;
    private SpotType type;
    private SpotStatus status;
    private short order;
    private List<SpotReservationDto> reservations = new ArrayList<>();


    public List<SpotReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<SpotReservationDto> reservations) {
        this.reservations = reservations;
    }


    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public SpotType getType() {
        return type;
    }

    public void setType(SpotType type) {
        this.type = type;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public void setStatus(SpotStatus status) {
        this.status = status;
    }

    public short getOrder() {
        return order;
    }

    public void setOrder(short order) {
        this.order = order;
    }
}
