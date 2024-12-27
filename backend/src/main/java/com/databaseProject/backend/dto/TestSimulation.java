package com.databaseProject.backend.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TestSimulation {
    int lotId;
    int spotNumber;
    Timestamp startTime;
    Timestamp endTime;
}
