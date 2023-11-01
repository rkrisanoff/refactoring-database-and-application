package com.example.kurs.controller;


import com.example.kurs.dto.StatusDTO;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.exceptions.InvalidPageNumberException;
import com.example.kurs.exceptions.InvalidSizeException;
import com.example.kurs.exceptions.InvalidSortDirectionException;
import com.example.kurs.exceptions.RecipeNotFoundException;
import com.example.kurs.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
@AllArgsConstructor
public class AdminController {

    private final RecipeService recipeService;


    @GetMapping("/recipes/all")
    @ResponseBody
    public List<Recipe> getRecipesOnModeration(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir,
            @RequestParam(value = "sort", defaultValue = "id") String sort) throws InvalidSizeException, InvalidSortDirectionException, InvalidPageNumberException {
        log.info("Получение запроса за вывод всех рецептов на модерации, с следующими параметрами page - {} , size - {}, sortDir - {}, sort - {}",page,size,sortDir,sort);
        List<Recipe> recipes = recipeService.getRecipesListOnModeration(page, size, sortDir, sort);

        return new ArrayList<>(recipes);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> recipeId(@PathVariable("id") Long id) throws RecipeNotFoundException {
        log.info("Получение запроса на вывод рецепта с id - {}", id);
        Optional<Recipe> recipe = recipeService.getRecipeOnModerationId(id);
        if (!recipe.isPresent()) {
            throw new RecipeNotFoundException("recipe " + id + " doesn't exist");
        }
        return ResponseEntity.ok(recipe.get());
    }

    @PatchMapping("/recipes/{id}")
    public ResponseEntity<String> getRecipeId(@RequestBody StatusDTO statusDTO, @PathVariable("id") Long id) throws SystemException, RecipeNotFoundException {
        log.info("Получение запроса на обновление рецепта с id - {} c следующими данными - {}",id,statusDTO.toString());
        recipeService.changeStatus(id, Status.valueOf(statusDTO.getStatus()));
        return ResponseEntity.ok("");
    }

}
