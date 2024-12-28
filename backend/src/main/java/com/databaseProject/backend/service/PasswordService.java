package com.databaseProject.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Service
public class PasswordService {
        public static String hashPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(encodedHash);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error initializing SHA-256 algorithm", e);
            }
        }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        System.out.println("raw pass hashed "+ hashPassword(rawPassword));
        System.out.println(hashPassword(rawPassword).equals(hashedPassword));
        return hashPassword(rawPassword).equals(hashedPassword);
    }
}
