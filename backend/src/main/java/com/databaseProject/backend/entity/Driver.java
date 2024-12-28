package com.databaseProject.backend.entity;
import com.databaseProject.backend.enums.PaymentType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
public class Driver {
    private int driverId;
    private String licensePlateNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private PaymentType paymentMethod;
    private String password;
}
