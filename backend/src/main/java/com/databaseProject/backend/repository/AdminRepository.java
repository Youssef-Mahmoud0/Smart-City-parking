package com.databaseProject.backend.repository;

import com.databaseProject.backend.entity.Admin;
import com.databaseProject.backend.entity.Manager;
import com.databaseProject.backend.mapper.sqlMapper.AdminMapper;
import com.databaseProject.backend.mapper.sqlMapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Admin> findByEmail(String email) {
        String sql = "SELECT * FROM admin WHERE email = ?";
        Admin admin = jdbcTemplate.queryForObject(sql, new AdminMapper(), email);
        return Optional.ofNullable(admin);
    }
}
