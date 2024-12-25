package com.databaseProject.backend.service;

import com.databaseProject.backend.dto.AuthenticationResponse;
import com.databaseProject.backend.exception.InvalidCredentialsException;
import com.databaseProject.backend.util.CookieUtil;
import com.databaseProject.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class TokenService {

    private final Environment env;

    private final JwtUtil jwtUtil;

    public void createNewTokens(String refreshToken,HttpServletResponse response) {
        // Fetch the username from the refresh token
        String username = jwtUtil.getUsernameFromRefreshToken(refreshToken);
        // Generate new access and refresh tokens
        String newAccessToken = jwtUtil.generateAccessToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);
        // Add the new tokens to the response cookies
        CookieUtil.addCookie(response, "accessToken", newAccessToken);
        CookieUtil.addCookie(response, "refreshToken", newRefreshToken);
    }


    public void storeTokens(
            AuthenticationResponse authenticationResponse,
            HttpServletResponse response
    ) {
        authenticationResponse.getAccessToken();
        authenticationResponse.getRefreshToken();
        CookieUtil cookieUtil = new CookieUtil();
        cookieUtil.addCookie(response, "accessToken", authenticationResponse.getAccessToken());
        cookieUtil.addCookie(response, "refreshToken", authenticationResponse.getRefreshToken());
        System.out.println("tokens stored");
    }

}
