package com.example.kurs;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.dto.SigninDto;
import com.example.kurs.dto.SignupDto;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.entity.User;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.SystemException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TestsUser {
    SignupDto signupDto;

    @Autowired
    UserService userService;

    @Before
    @SneakyThrows
    public void init() {
        this.signupDto=new SignupDto();
        signupDto.setEmail("testmail@mail.ru");
        signupDto.setUsername("username");
        signupDto.setPassword("testPass");

    }


    @Test
    void addRecipeValid() {
        assertDoesNotThrow(() -> {
            userService.register(signupDto);
        });

    }

    @Test
    void addRecipeNoValid() {
        signupDto.setPassword(null);
        assertThrows(Exception.class, () -> {
            userService.register(signupDto);
        });
    }

    @Test
    @SneakyThrows
    void getByUsername() {
        userService.register(signupDto);
        User testUser=userService.getByUsername(signupDto.getUsername());
        assertEquals(testUser.getUsername(),signupDto.getUsername());

    }

    @Test
    @SneakyThrows
    void getById() {
        userService.register(signupDto);
        User testUser=userService.getUser(1l);
        assertEquals(testUser.getUsername(),signupDto.getUsername());

    }


}