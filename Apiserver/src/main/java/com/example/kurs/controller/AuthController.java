package com.example.kurs.controller;

import com.example.kurs.dto.SignupDto;
import com.example.kurs.exceptions.EmailAlreadyExistsException;
import com.example.kurs.exceptions.UserAlreadyExistsException;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.SystemException;
import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) throws UserAlreadyExistsException, EmailAlreadyExistsException, SystemException {
        userService.register(signupDto);
        return ResponseEntity.ok("");
    }
}
