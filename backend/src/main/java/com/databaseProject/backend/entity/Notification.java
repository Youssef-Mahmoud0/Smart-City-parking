package com.databaseProject.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Notification {

    int notificationID;

    int driverID;

    String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp createdAt;

    boolean seen;

    String type;
}