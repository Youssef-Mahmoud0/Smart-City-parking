package com.databaseProject.backend.entity;

import com.databaseProject.backend.enums.ReservationStatus;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
public class Reservation {
    private int reservationId;
    private int driverId;
    private int spotId;
    private Timestamp startTime;
    private Timestamp endTime;
    private ReservationStatus status;
    private Timestamp expectedEndTime;
}
