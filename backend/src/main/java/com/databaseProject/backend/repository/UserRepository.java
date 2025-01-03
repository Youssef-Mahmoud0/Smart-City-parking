package com.databaseProject.backend.repository;

import com.databaseProject.backend.entity.User;
import com.databaseProject.backend.mapper.sqlMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(User  user) {
        String sql = "INSERT INTO driver (name) VALUES (?)";

        int rowsAffected = jdbcTemplate.update(sql, user.getFirstName());
        return rowsAffected;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM driver WHERE email = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), email);
        return Optional.ofNullable(user);
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM driver WHERE username = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
        return Optional.ofNullable(user);
    }
}
