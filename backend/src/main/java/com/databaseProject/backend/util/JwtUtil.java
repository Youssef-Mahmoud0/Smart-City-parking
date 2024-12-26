package com.databaseProject.backend.util;

import com.databaseProject.backend.dto.SignUpRequest;
import com.databaseProject.backend.enums.PaymentType;
import com.databaseProject.backend.enums.UserType;
import com.databaseProject.backend.exception.InvalidCredentialsException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;

    private Key secretKey;

    @PostConstruct
    protected void initSecretKey() {
        this.secretKey = new SecretKeySpec(
                Base64.getDecoder().decode(env.getProperty("JWT_SECRET_KEY")),
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

    public String generateAccessToken(int id) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("ACCESS_TOKEN_EXPIRATION"))))
                .signWith(secretKey)
                .compact();
    }

    public  String generateRefreshToken(int id) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("REFRESH_TOKEN_EXPIRATION")))
                )
                .signWith(secretKey)
                .compact();
    }

    public String generateSignupToken(SignUpRequest request) {
        System.out.println("userRole: " + request.getUserType());
        return Jwts.builder()
                .claim("userType", request.getUserType().name()) // Store as String
                .claim("email", request.getEmail())
                .claim("name", request.getName())
                .claim("password", request.getPassword())
                .claim("phone", request.getPhoneNumber())
                .claim("plateNumber", request.getLicensePlateNumber())
                .claim("paymentMethod", request.getPaymentMethod())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // 5 min expiration
                .signWith(secretKey)
                .compact();
    }

    public SignUpRequest validateSignupToken(String token) {
        System.out.println("validating signup token");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            SignUpRequest request = new SignUpRequest();
            System.out.println("claims: " + claims);

            // Convert userType back to Enum
            String userTypeString = claims.get("userType", String.class);
            request.setUserType(UserType.valueOf(userTypeString)); // Convert String to Enum

            request.setEmail(claims.get("email", String.class));
            request.setName(claims.get("name", String.class));
            request.setPassword(claims.get("password", String.class));
            request.setPhoneNumber(claims.get("phone", String.class));
            request.setLicensePlateNumber(claims.get("plateNumber", String.class));
            String paymentMethod = claims.get("paymentMethod", String.class);
            request.setPaymentMethod(PaymentType.valueOf(paymentMethod));
            System.out.println("token validated");
            return request;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidCredentialsException("Invalid or expired token");
        }
    }

    public  Boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // Replace 'key' with your signing key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date()) ) {
                throw new InvalidCredentialsException("Token is expired");
            }
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public int getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public Key getSecretKey() {
        return secretKey;
    }
}