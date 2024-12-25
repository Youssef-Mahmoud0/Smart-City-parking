package com.databaseProject.backend.service;

import com.databaseProject.backend.entity.Driver;
import com.databaseProject.backend.enums.UserType;
import com.databaseProject.backend.exception.EmailAlreadyRegisteredException;
import com.databaseProject.backend.repository.DriverRepository;
import com.databaseProject.backend.repository.UserRepository;
import com.databaseProject.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.databaseProject.backend.dto.SignUpRequest;
@Service
@RequiredArgsConstructor
public class AuthService {
        private final PasswordService passwordService;
        private final JwtUtil jwtUtil;
        private final EmailService emailService;
        private final UserRepository userRepository;
        private final DriverRepository driverRepository;

        public void driverSignUp(SignUpRequest signUpRequest) {
            String password = passwordService.hashPassword(signUpRequest.getPassword());
            signUpRequest.setPassword(password);
//            if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent())
//                throw new EmailAlreadyRegisteredException("Email already exists.");
            System.out.println("Email not found");
            signUpRequest.setUserType(UserType.DRIVER);
            String signUpToken = jwtUtil.generateSignupToken(signUpRequest);
            emailService.sendConfirmationEmail(signUpRequest.getEmail(), signUpToken);
            System.out.println("Email sent");
        }

        public void createNewUser(SignUpRequest request){
//            if(userRepository.findByEmail(request.getEmail()).isPresent()){
//                throw new EmailAlreadyRegisteredException("User already exists");
//            }
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
}
