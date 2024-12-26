package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.entity.Manager;
import com.databaseProject.backend.enums.PaymentType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerMapper implements RowMapper<Manager> {
    @Override
    public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {
        Manager manager = new Manager();
        manager.setManagerId(rs.getInt("manager_id"));
        manager.setName(rs.getString("name"));
        manager.setEmail(rs.getString("email"));
        manager.setPhoneNumber(rs.getString("phone_number"));
        manager.setPassword(rs.getString("password"));
        manager.setCreatedAt(rs.getTimestamp("created_at"));
        manager.setUpdatedAt(rs.getTimestamp("updated_at"));
        return manager;
    }
}
