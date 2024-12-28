package com.databaseProject.backend.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
public class Admin {
    private int adminId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
