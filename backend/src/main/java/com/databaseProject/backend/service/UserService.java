package com.databaseProject.backend.service;

import com.databaseProject.backend.entity.User;
import com.databaseProject.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public int createUser(User user) {
        return userRepository.save(user);
    }
}
