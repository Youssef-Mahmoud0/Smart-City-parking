package com.databaseProject.backend.dto;

import com.databaseProject.backend.enums.PenaltyReason;

public class PenaltyDto {
    private int penaltyId;
    private int reservationId;
    private double amount;
    private PenaltyReason reason;

    public int getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(int penaltyId) {
        this.penaltyId = penaltyId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PenaltyReason getReason() {
        return reason;
    }

    public void setReason(PenaltyReason reason) {
        this.reason = reason;
    }


}
