package com.databaseProject.backend.entity;

import lombok.Data;

@Data
public class Notification {
    int notificationID;
    int driverID;
    String message;
}