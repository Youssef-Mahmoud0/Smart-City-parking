package com.databaseProject.backend.mapper.sqlMapper;

import com.databaseProject.backend.entity.Admin;
import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.entity.Manager;
import com.databaseProject.backend.enums.PaymentType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<Admin> {
    @Override
    public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        admin.setPhoneNumber(rs.getString("phone_number"));
        admin.setPassword(rs.getString("password"));
        return admin;
    }
}
