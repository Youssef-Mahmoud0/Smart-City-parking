package com.databaseProject.backend.repository;

import com.databaseProject.backend.entity.Manager;
import com.databaseProject.backend.mapper.sqlMapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ManagerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Manager> findByEmail(String email) {
        String sql = "SELECT * FROM manager WHERE email = ?";
        Manager manager = jdbcTemplate.queryForObject(sql, new ManagerMapper(), email);
        return Optional.ofNullable(manager);
    }
}
