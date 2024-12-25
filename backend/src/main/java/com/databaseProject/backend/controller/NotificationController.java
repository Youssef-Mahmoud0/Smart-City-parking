package com.databaseProject.backend.controller;

import com.databaseProject.backend.entity.Notification;
import com.databaseProject.backend.handler.NotificationHandler;
import com.databaseProject.backend.repository.NotificationRepository;
import com.databaseProject.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationHandler notificationHandler;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;
    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody Notification notification) throws Exception {
        System.out.println(notification);
        notificationRepository.addNotification(notification);
        notificationHandler.sendNotification(notification);
    }

    @GetMapping("/driver/notifications")
    public List<Notification> getDriverNotifications(@RequestParam(name="driverID") int driverID) {
        return notificationService.getDriverNotifications(driverID);
    }

    @PatchMapping("/driver/notifications/seen")
    public void markNotificationAsSeen(@RequestParam(name="driverID") int driverID) {
        notificationService.markNotificationsAsSeen(driverID);
    }
}
