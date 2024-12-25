package com.databaseProject.backend.repository;

import com.databaseProject.backend.entity.User;
import com.databaseProject.backend.mapper.sqlMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(User  user) {
        String sql = "INSERT INTO user (first_name, last_name) VALUES (?, ?)";

        int rowsAffected = jdbcTemplate.update(sql, user.getFirstName(), user.getLastName());
        return rowsAffected;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserMapper());
    }
}
