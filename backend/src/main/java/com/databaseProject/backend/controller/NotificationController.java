package com.databaseProject.backend.controller;

import com.databaseProject.backend.entity.Notification;
import com.databaseProject.backend.handler.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationHandler notificationHandler;

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody Notification notification) throws Exception {
        System.out.println(notification);
        notificationHandler.sendNotification(notification);
    }
}
