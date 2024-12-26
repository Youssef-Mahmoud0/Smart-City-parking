package com.databaseProject.backend.controller;


import com.databaseProject.backend.dto.AuthenticationResponse;
import com.databaseProject.backend.dto.LogInRequest;
import com.databaseProject.backend.dto.ResponseMessage;
import com.databaseProject.backend.entity.User;
import com.databaseProject.backend.service.AuthService;
import com.databaseProject.backend.service.TokenService;
import com.databaseProject.backend.service.UserService;
import com.databaseProject.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.databaseProject.backend.dto.SignUpRequest;
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
    @PostMapping("/signup/driver")
    public ResponseEntity<?> signUpDriver(@RequestBody SignUpRequest signUpRequest ) {
        System.out.println("welcome from driver signup");
        authService.driverSignUp(signUpRequest);
        System.out.println(signUpRequest.getPassword());
        System.out.println(signUpRequest.getEmail());
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
    public ResponseEntity<?> loginDriver(@RequestBody LogInRequest request, HttpServletResponse response)
    {
        System.out.println("Login Endpoint ");
        AuthenticationResponse authenticationResponse = authService.driverLogIn(request);
        tokenService.storeTokens(authenticationResponse, response);
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login/manager")
    public ResponseEntity<?> loginManager(@RequestBody LogInRequest request, HttpServletResponse response)
    {
        System.out.println("Login Endpoint as Manager");
        AuthenticationResponse authenticationResponse = authService.managerLogIn(request);
        tokenService.storeTokens(authenticationResponse, response);
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody LogInRequest request, HttpServletResponse response)
    {
        System.out.println("Login Endpoint as Admin");
        AuthenticationResponse authenticationResponse = authService.adminLogIn(request);
        tokenService.storeTokens(authenticationResponse, response);
        return ResponseEntity.ok().body(authenticationResponse);
    }
}
