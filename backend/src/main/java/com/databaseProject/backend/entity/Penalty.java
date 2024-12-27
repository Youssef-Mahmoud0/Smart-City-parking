package com.databaseProject.backend.entity;

import com.databaseProject.backend.enums.PenaltyReason;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Penalty {
    private int penaltyId;
    private int reservationId;
    private double amount;
    private PenaltyReason reason;
}
