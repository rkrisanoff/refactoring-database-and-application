package com.example.kurs.controller;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Recipe;
import com.example.kurs.exceptions.*;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.SystemException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

   private final RecipeService recipeService;

    private final UserService userService;

    @PostMapping("/add-recipe")
    public ResponseEntity<String> addRecipe(
            @Valid @RequestBody RecipeDto requestRecipe,
            Authentication authentication
    ) throws IllegalKitchenException, UserAlreadyExistsException, SystemException {
        log.info("Получение запроса за добавление нового рецепта {}",requestRecipe);
        if (!userService.existsByUsername(authentication.getName())) {
            throw new UserAlreadyExistsException("User with username = " +  authentication.getName() + " doesn't exits");
        }
        recipeService.addRecipe(requestRecipe, userService.getByUsername(authentication.getName()).getId());
        return ResponseEntity.ok("");
    }

    @GetMapping("/recipes/all")
    public List<Recipe> getRecipes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir,
            @RequestParam(value = "sort", defaultValue = "id") String sort) throws InvalidSizeException, InvalidSortDirectionException, InvalidPageNumberException {

        log.info("Получение запроса за вывод всех рецептов, с следующими параметрами page {} , size {}, sortDir {},sort {}",page,size,sortDir,sort);
        List<Recipe> recipes = recipeService.getApprovedRecipesList(page, size, sortDir, sort);

        return new ArrayList<>(recipes);
    }
}
