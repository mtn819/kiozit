package com.example.employeemanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanager.dtos.LoginAppUserDto;
import com.example.employeemanager.dtos.RegisterAppUserDto;
import com.example.employeemanager.model.AppUser;
import com.example.employeemanager.service.AuthenticationService;
import com.example.employeemanager.service.JWTService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(
        JWTService jwtService,
        AuthenticationService authenticationService
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AppUser> register(@RequestBody RegisterAppUserDto registerAppUserDto) {
        AppUser appUser = authenticationService.signup(registerAppUserDto);
        
        return ResponseEntity.ok(appUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginAppUserDto loginAppUserDto) {
        AppUser appUser = authenticationService.authenticate(loginAppUserDto);
        String jwtToken = jwtService.generateToken(appUser);
        LoginResponse loginResponse = LoginResponse.builder()
                                        .token(jwtToken)
                                        .expiresIn(jwtService.getExpirationTime())
                                        .build();
        
        return ResponseEntity.ok(loginResponse);
    }
}
