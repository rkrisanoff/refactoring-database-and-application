package com.example.kurs.controller;


import com.example.kurs.dto.StatusDTO;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.exceptions.InvalidPageNumberException;
import com.example.kurs.exceptions.InvalidSizeException;
import com.example.kurs.exceptions.InvalidSortDirectionException;
import com.example.kurs.exceptions.RecipeNotFoundException;
import com.example.kurs.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")


public class AdminController {
    @Autowired
    RecipeService recipeService;


    @GetMapping("/recipes/all")
    @ResponseBody
    public List<Recipe> getRecipesOnModeration(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir,
            @RequestParam(value = "sort", defaultValue = "id") String sort) throws InvalidSizeException, InvalidSortDirectionException, InvalidPageNumberException {
        List<Recipe> recipes = recipeService.getRecipesListOnModeration(page, size, sortDir, sort);

        return new ArrayList<>(recipes);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> recipeId(@PathVariable("id") Long id) throws RecipeNotFoundException {
        Optional<Recipe> recipe = recipeService.getRecipeOnModerationId(id);
        if (!recipe.isPresent()) {
            throw new RecipeNotFoundException("recipe " + id + " doesn't exist");
        }
        return ResponseEntity.ok(recipe.get());
    }

    @PatchMapping("/recipes/{id}")
    public ResponseEntity<String> getRecipeId(@RequestBody StatusDTO statusDTO, @PathVariable("id") Long id) throws SystemException, RecipeNotFoundException {
        recipeService.changeStatus(id, Status.valueOf(statusDTO.getStatus()));
        return ResponseEntity.ok("");
    }

}
