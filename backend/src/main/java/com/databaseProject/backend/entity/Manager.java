package com.databaseProject.backend.entity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
public class Manager {
        private int managerId;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;
}
