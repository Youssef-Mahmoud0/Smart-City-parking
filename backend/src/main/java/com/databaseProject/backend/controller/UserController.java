package com.databaseProject.backend.controller;


import com.databaseProject.backend.entity.User;
import com.databaseProject.backend.reports.ReportGenerator;
import com.databaseProject.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReportGenerator reportGenerator;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/user/create")
    public int createUser(@RequestBody User user) {
        return userService.createUser(user) ;
    }

    @GetMapping("/report")
    public void generateReport() {
        reportGenerator.generateReport();
    }
}
