package com.databaseProject.backend.service;

import com.databaseProject.backend.dto.AuthenticationResponse;
import com.databaseProject.backend.dto.LogInRequest;
import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.enums.UserType;
import com.databaseProject.backend.exception.EmailAlreadyRegisteredException;
import com.databaseProject.backend.exception.InvalidCredentialsException;
import com.databaseProject.backend.repository.AdminRepository;
import com.databaseProject.backend.repository.DriverRepository;
import com.databaseProject.backend.repository.ManagerRepository;
import com.databaseProject.backend.repository.UserRepository;
import com.databaseProject.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.databaseProject.backend.dto.SignUpRequest;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final PasswordService passwordService;
        private final JwtUtil jwtUtil;
        private final EmailService emailService;
        private final UserRepository userRepository;
        private final DriverRepository driverRepository;
        private final ManagerRepository managerRepository;
        private final AdminRepository adminRepository;

        public void driverSignUp(SignUpRequest signUpRequest) throws NoSuchAlgorithmException {
            String password = passwordService.hashPassword(signUpRequest.getPassword());
            signUpRequest.setPassword(password);
            System.out.println(signUpRequest.getLicensePlateNumber().length());
            if (driverRepository.findByEmail(signUpRequest.getEmail()).isPresent())
                throw new EmailAlreadyRegisteredException("Email already exists.");
            System.out.println("Email not found");
            signUpRequest.setUserType(UserType.DRIVER);
            String signUpToken = jwtUtil.generateSignupToken(signUpRequest);
            emailService.sendConfirmationEmail(signUpRequest.getEmail(), signUpToken);
            System.out.println("Email sent");
        }

        public void createNewUser(SignUpRequest request){
            if(driverRepository.findByEmail(request.getEmail()).isPresent()){
                throw new EmailAlreadyRegisteredException("User already exists");
            }
            System.out.println(request.getUserType());
            System.out.println(request.getPaymentMethod());
            if (request.getUserType() == UserType.DRIVER) {
                Driver driver = new Driver();
                driver.setName(request.getName());
                driver.setEmail(request.getEmail());
                driver.setPassword(request.getPassword());
                driver.setLicensePlateNumber(request.getLicensePlateNumber());
                driver.setPhoneNumber(request.getPhoneNumber());
                driver.setPaymentMethod(request.getPaymentMethod());
                driverRepository.save(driver);
            }
        }

        public AuthenticationResponse driverLogIn(LogInRequest request) throws NoSuchAlgorithmException {
            var driver = driverRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("email or password is incorrect"));
            System.out.println("email found");
            if (!(passwordService.verifyPassword((request.getPassword()), driver.getPassword()))) {
                throw new InvalidCredentialsException("email or password is incorrect");
            }
            System.out.println("password verified");
            int id = driver.getDriverId();
            String accessToken = jwtUtil.generateAccessToken(id);
            String refreshToken = jwtUtil.generateRefreshToken(id);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .role("DRIVER")
                    .id(String.valueOf(id))
                    .build();
        }

        public AuthenticationResponse managerLogIn(LogInRequest request) throws NoSuchAlgorithmException {
            var manager = managerRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("email or password is incorrect"));
            System.out.println("email found");
            if (!(passwordService.verifyPassword((request.getPassword()), manager.getPassword()))) {
                throw new InvalidCredentialsException("email or password is incorrect");
            }
            System.out.println("password verified");
            int id = manager.getManagerId();
            String accessToken = jwtUtil.generateAccessToken(id);
            String refreshToken = jwtUtil.generateRefreshToken(id);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .role("MANAGER")
                    .id(String.valueOf(id))
                    .build();
        }

        public AuthenticationResponse adminLogIn(LogInRequest request) throws NoSuchAlgorithmException {
            var admin = adminRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("email or password is incorrect"));
            System.out.println("email found");
            if (!(passwordService.verifyPassword((request.getPassword()), admin.getPassword()))) {
                throw new InvalidCredentialsException("email or password is incorrect");
            }
            System.out.println("password verified");
            int id = admin.getAdminId();
            String accessToken = jwtUtil.generateAccessToken(id);
            String refreshToken = jwtUtil.generateRefreshToken(id);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .role("ADMIN")
                    .id(String.valueOf(id))
                    .build();
        }

        public String hashRawPassword(String password) throws NoSuchAlgorithmException {
            return passwordService.hashPassword(password);
    }
}
