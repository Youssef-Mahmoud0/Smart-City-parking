package com.databaseProject.backend.entity;

import com.databaseProject.backend.enums.SpotStatus;
import com.databaseProject.backend.enums.SpotType;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ParkingSpot {
    private int spotId;
    private int lotId;
    private SpotType type;
    private SpotStatus status;
}
