package com.databaseProject.backend.repository;

import com.databaseProject.backend.entity.Notification;
import com.databaseProject.backend.mapper.sqlMapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addNotification(Notification notification) {

        String sql = "INSERT INTO notification(driver_id, message, type, seen) VALUES(?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql, notification.getDriverID(), notification.getMessage(),
                notification.getType(), false);
        return rowsAffected == 1;
    }

    public List<Notification> getNotifications(int driverID) {
        String sql = "SELECT * FROM notification WHERE driver_id = " + driverID + " ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new NotificationMapper());
    }

    public void markNotificationsAsSeen(int driverID) {
        String sql = "UPDATE notification SET seen = true WHERE driver_id = ?";
        jdbcTemplate.update(sql, driverID);
    }
}
