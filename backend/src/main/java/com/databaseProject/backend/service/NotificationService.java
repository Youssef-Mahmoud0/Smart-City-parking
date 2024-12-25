package com.databaseProject.backend.service;

import com.databaseProject.backend.entity.Notification;
import com.databaseProject.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getDriverNotifications(int driverID) {
        return notificationRepository.getNotifications(driverID);
    }

    public void markNotificationsAsSeen(int driverID) {
        notificationRepository.markNotificationsAsSeen(driverID);
    }
}
