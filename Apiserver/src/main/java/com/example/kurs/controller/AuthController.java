package com.example.kurs.controller;

import com.example.kurs.dto.SignupDto;
import com.example.kurs.exceptions.EmailAlreadyExistsException;
import com.example.kurs.exceptions.UserAlreadyExistsException;
import com.example.kurs.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.SystemException;
import javax.validation.Valid;


@CrossOrigin
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) throws UserAlreadyExistsException, EmailAlreadyExistsException, SystemException {
        log.info("Получение запроса на регистрацию пользователя {}",signupDto.getEmail());
        userService.register(signupDto);
        return ResponseEntity.ok("");
    }
}
