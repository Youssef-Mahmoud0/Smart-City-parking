package com.databaseProject.backend.controller;


import com.databaseProject.backend.dto.*;
import com.databaseProject.backend.entity.User;
import com.databaseProject.backend.reports.ReportGenerator;
import com.databaseProject.backend.service.AuthService;
import com.databaseProject.backend.service.PasswordService;
import com.databaseProject.backend.service.TokenService;
import com.databaseProject.backend.service.UserService;
import com.databaseProject.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ReportGenerator reportGenerator;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/user/create")
    public int createUser(@RequestBody User user, HttpServletRequest request) {
        Object id = request.getAttribute("id");
        System.out.println("id: " + id.toString());
        System.out.println("Creating user");
        return userService.createUser(user) ;
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/generate-report-admin")
    public ResponseEntity<byte[]> getPdfReport() {
        try {
            byte[] pdfBytes = reportGenerator.generateReport(true, 0);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "inline; filename=\"ParkingLotPerformanceReport.pdf\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/generate-report-manager")
    public ResponseEntity<byte[]> getPdfReportManager(HttpServletRequest request) {
        try {
            Object id = request.getAttribute("id");
            byte[] pdfBytes = reportGenerator.generateReport(false, Integer.parseInt(id.toString()));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "inline; filename=\"ParkingLotPerformanceReport.pdf\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/hash-password")
    public void hashPassword(@RequestBody String password) throws NoSuchAlgorithmException {
        System.out.println("Hashing password" + password);
        System.out.println(authService.hashRawPassword(password));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/signup/driver")
    public ResponseEntity<?> signUpDriver(@RequestBody SignUpRequest signUpRequest ) throws NoSuchAlgorithmException {
        System.out.println("welcome from driver signup");
        authService.driverSignUp(signUpRequest);
        System.out.println(signUpRequest.getPassword());
        System.out.println(signUpRequest.getEmail());
        System.out.println(signUpRequest.getLicensePlateNumber().length());
        System.out.println(signUpRequest.getLicensePlateNumber());
        ResponseMessage responseMessage = new ResponseMessage("Email confirmation sent. Please verify your email.");
        return ResponseEntity.ok().body(responseMessage);
    }
    @GetMapping("/confirm-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        System.out.println("Verifying email");
        System.out.println("Token: " + token);
        SignUpRequest signUpRequest = jwtUtil.validateSignupToken(token);
        System.out.println(signUpRequest.getEmail());
        authService.createNewUser(signUpRequest);
        ResponseMessage responseMessage = new ResponseMessage("Email verified successfully. You can now log in.");
        return ResponseEntity.ok().body(responseMessage);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login/driver")
    public ResponseEntity<?> loginDriver(@RequestBody LogInRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        System.out.println("Login Endpoint ");
        System.out.println(request.getPassword());
        AuthenticationResponse authenticationResponse = authService.driverLogIn(request);
        tokenService.storeTokens(authenticationResponse, response);
        AuthenticationResponse objectToReturn = new AuthenticationResponse();
        objectToReturn.setId(authenticationResponse.getId());
        return ResponseEntity.ok().body(objectToReturn);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login/manager")
    public ResponseEntity<?> loginManager(@RequestBody LogInRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        System.out.println("Login Endpoint as Manager");
        AuthenticationResponse authenticationResponse = authService.managerLogIn(request);
        tokenService.storeTokens(authenticationResponse, response);
        AuthenticationResponse objectToReturn = new AuthenticationResponse();
        objectToReturn.setId(authenticationResponse.getId());
        return ResponseEntity.ok().body(objectToReturn);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody LogInRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        System.out.println("Login Endpoint as Admin");
        AuthenticationResponse authenticationResponse = authService.adminLogIn(request);
        tokenService.storeTokens(authenticationResponse, response);
        AuthenticationResponse objectToReturn = new AuthenticationResponse();
        objectToReturn.setId(authenticationResponse.getId());
        return ResponseEntity.ok().body(objectToReturn);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/testSimulation")
    public ResponseEntity<?> testSimulation(@RequestBody TestSimulation request) {
        System.out.println("Test Simulation");
        System.out.println(request.getLotId());
        System.out.println(request.getSpotNumber());
        System.out.println(request.getStartTime());
        System.out.println(request.getEndTime());
        if (request.getLotId() == 1 && request.getSpotNumber() == 1) {
            return ResponseEntity.ok().body(new ResponseMessage("Success"));
        }
        else {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error"));
        }

    }
}
