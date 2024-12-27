package com.databaseProject.backend.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ParkingLot {
    private int lotId;
    private int mgrId;
    private String name;
    private double latitude;
    private double longitude;
    private int capacity;
    private double basePrice;
}
