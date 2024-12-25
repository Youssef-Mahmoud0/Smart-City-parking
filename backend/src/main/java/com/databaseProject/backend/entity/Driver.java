package com.databaseProject.backend.entity;

import lombok.Data;

@Data
public class Driver {
    int driverID;
    String name;
    String phoneNumber;
    String licensePlateNumber;
    int paymentMethod;
    String password;
}