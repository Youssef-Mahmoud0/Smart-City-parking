package com.databaseProject.backend.service;

import com.databaseProject.backend.entity.Notification;
import com.databaseProject.backend.handler.NotificationHandler;
import com.databaseProject.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationHandler notificationHandler;

    public List<Notification> getDriverNotifications(int driverID) {
        return notificationRepository.getNotifications(driverID);
    }

    public void markNotificationsAsSeen(int driverID) {
        notificationRepository.markNotificationsAsSeen(driverID);
    }

    @Scheduled(fixedRate = 60000)
    public void notifyReservationsDrivers() throws Exception {
        System.out.println("Checking for notifications");
        List<Integer> startedReservations = notificationRepository.getStartedReservations();
        List<Integer> endedReservations = notificationRepository.getEndedReservations();
        List<Integer> exceeded30MinReservations = notificationRepository.getExceeded30MinReservations();
        List<Integer> reservationsEndsIn5Min = notificationRepository.getReservationssEndsIn5Min();

        Notification notification = new Notification();
        for (Integer driverID : startedReservations) {
            notification.setDriverID(driverID);
            notification.setMessage("Your reservation has started");
            notification.setType("MESSAGE");
            System.out.println("Sending notification to driver " + driverID);
            notificationHandler.sendNotification(notification);
        }

        for (Integer driverID : endedReservations) {
            notification.setDriverID(driverID);
            notification.setMessage("Your reservation has ended");
            notification.setType("MESSAGE");
            System.out.println("Sending notification to driver " + driverID);

            notificationHandler.sendNotification(notification);
        }

        for (Integer driverID : exceeded30MinReservations) {
            notification.setDriverID(driverID);
            notification.setMessage("Your reservation has exceeded 30 minutes");
            notification.setType("MESSAGE");

            System.out.println("Sending notification to driver " + driverID);
            notificationHandler.sendNotification(notification);
        }

        for (Integer driverID : reservationsEndsIn5Min) {
            notification.setDriverID(driverID);
            notification.setMessage("Your reservation will end in 5 minutes");
            notification.setType("MESSAGE");

            System.out.println("Sending notification to driver " + driverID);
            notificationHandler.sendNotification(notification);
        }
    }
}
