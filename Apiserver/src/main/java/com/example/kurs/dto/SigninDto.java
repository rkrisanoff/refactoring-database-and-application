package com.example.kurs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SigninDto {
    @NotNull(message = "username must not be empty")
    @Size(min = 5, max = 256, message = " username do not match the size")
    private String username;
    @NotNull(message = "password must not be empty")
    @Size(min = 5, max = 256, message = " password do not match the size")
    private String password;
}
