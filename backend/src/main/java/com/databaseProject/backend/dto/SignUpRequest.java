package com.databaseProject.backend.dto;

import com.databaseProject.backend.enums.PaymentType;
import com.databaseProject.backend.enums.UserType;
import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String licensePlateNumber;
    private PaymentType paymentMethod;
    private UserType userType;
}
