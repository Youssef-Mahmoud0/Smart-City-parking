package com.databaseProject.backend.dto;

import com.databaseProject.backend.enums.PaymentType;
import lombok.Data;

@Data
public class DriverDto {
    private int driverId;
    private String licensePlateNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private PaymentType paymentMethod;
    private double penalty;

}
