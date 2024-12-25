package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.entity.Notification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationID(rs.getInt("notification_id"));
        notification.setDriverID(rs.getInt("driver_id"));
        notification.setMessage(rs.getString("message"));
        return notification;
    }
}
//CREATE TABLE IF NOT EXISTS `smart_city_db`.`notification` (
//        `notification_id` INT NOT NULL AUTO_INCREMENT,
//  `driver_id` INT NOT NULL,
//        `message` TEXT NOT NULL,
//PRIMARY KEY (`notification_id`))