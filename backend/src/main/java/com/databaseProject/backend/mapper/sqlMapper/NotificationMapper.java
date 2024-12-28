package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.entity.Notification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notification_id"));
        notification.setDriverId(rs.getInt("driver_id"));
        notification.setMessage(rs.getString("message"));
        notification.setCreatedAt(rs.getTimestamp("created_at"));
        notification.setSeen(rs.getBoolean("seen"));
        notification.setType(rs.getString("type"));
        return notification;
    }
}
