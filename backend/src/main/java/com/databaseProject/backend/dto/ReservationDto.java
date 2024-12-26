package com.databaseProject.backend.dto;

import com.databaseProject.backend.enums.ReservationStatus;

import java.sql.Timestamp;

public class ReservationDto {
    private int reservationId;
    private int driverId;
    private int spotId;
    private Timestamp startTime;
    private Timestamp endTime;
    private ReservationStatus status;
    private Timestamp expectedEndTime;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int diverId) {
        this.driverId = diverId;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Timestamp getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(Timestamp expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }
}
