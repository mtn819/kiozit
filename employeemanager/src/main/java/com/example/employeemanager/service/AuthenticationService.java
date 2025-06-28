package com.example.employeemanager.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employeemanager.dtos.LoginAppUserDto;
import com.example.employeemanager.dtos.RegisterAppUserDto;
import com.example.employeemanager.exception.UserNotFoundException;
import com.example.employeemanager.model.AppUser;
import com.example.employeemanager.repo.AppUserRepo;

@Service
public class AuthenticationService {
    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        AppUserRepo appUserRepo,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser signup(RegisterAppUserDto input) {
        AppUser appUser = AppUser.builder()
                            .fullName(input.getFullName())
                            .email(input.getEmail())
                            .password(passwordEncoder.encode(input.getPassword()))
                            .build();

        return appUserRepo.save(appUser);
    }

    public AppUser authenticate(LoginAppUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        return appUserRepo.findByEmail(input.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}
