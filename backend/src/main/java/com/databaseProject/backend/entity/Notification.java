package com.databaseProject.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notification {

    int notificationId;

    int driverId;

    String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp createdAt;

    boolean seen;

    String type;
}