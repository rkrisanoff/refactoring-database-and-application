package com.example.kurs.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @NotNull(message = "username must not be empty")
    @Size(min = 5, max = 256, message = " username do not match the size")
    private String username;
    @NotNull(message = "password must not be empty")
    @Size(min = 5, max = 256, message = " password do not match the size")
    private String password;
    @NotNull(message = "email must not be empty")
    @Size(min = 5, max = 256, message = " email do not match the size")
    @Email(message = "email does not match the format")
    private String email;
}
