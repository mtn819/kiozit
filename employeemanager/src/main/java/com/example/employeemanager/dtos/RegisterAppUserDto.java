package com.example.employeemanager.dtos;

import lombok.Data;

@Data
public class RegisterAppUserDto {
    private String email;

    private String password;

    private String fullName;
}
