package com.example.kurs;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.dto.SigninDto;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.SystemException;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TestsRecipe {
    RecipeDto recipeDto;
    RecipeDto recipeDto2;
    RecipeDto recipeDto3;
    SigninDto signinDto;

    @Autowired
    RecipeService recipeService;

    @Autowired
    ObjectMapper objectMapper;
    @Before
    @SneakyThrows
    public void init() {
        this.recipeDto=new RecipeDto()
                .builder()
                .kitchen("Russian")
                .title("Макароны с хлебом")
                .description("Тестовое описание")
                .build();

        this.recipeDto2=new RecipeDto()
                .builder()
                .kitchen("Russian")
                .title("Макароны с хлебом2")
                .description("Тестовое описание2")
                .build();

        this.recipeDto3=new RecipeDto()
                .builder()
                .kitchen("Russian")
                .title("Макароны с хлебом3")
                .description("Тестовое описание4")
                .build();


    }


    @Test
    void addRecipeValid() {
        assertDoesNotThrow(() -> {
            recipeService.addRecipe(recipeDto,1l);
        });

    }

    @Test
    void addRecipeNoValid() {
        recipeDto.setTitle(null);
        assertThrows(SystemException.class, () -> {
            recipeService.addRecipe(recipeDto,1l);
        });
    }

    @Test
    @SneakyThrows
    void getRecipesListOnModeration() {
        recipeService.addRecipe(recipeDto,1l);
        recipeService.addRecipe(recipeDto2,2l);
        recipeService.addRecipe(recipeDto3,3l);

        List<RecipeDto> actualList= List.of(recipeDto,recipeDto2,recipeDto3);
        actualList.stream().map((x)->objectMapper.convertValue(x,Recipe.class)).collect(Collectors.toList());

        List<Recipe> expectedList =recipeService.getRecipesListOnModeration(0,10,"","");

        assertEquals(expectedList,actualList);

    }

    @Test
    @SneakyThrows
    void getApprovedRecipesList() {
        recipeService.addRecipe(recipeDto,1l);
        recipeService.addRecipe(recipeDto2,2l);
        recipeService.addRecipe(recipeDto3,3l);

        recipeService.changeStatus(1l, Status.APPROVED);
        recipeService.changeStatus(2l, Status.APPROVED);
        recipeService.changeStatus(3l, Status.APPROVED);

        List<RecipeDto> actualList= List.of(recipeDto,recipeDto2,recipeDto3);
        actualList.stream().map((x)->objectMapper.convertValue(x,Recipe.class)).collect(Collectors.toList());

        List<Recipe> expectedList =recipeService.getRecipesListOnModeration(0,10,"","");

        assertEquals(expectedList,actualList);

    }

    @Test
    @SneakyThrows
    void getApprovedRecipesList() {
        recipeService.addRecipe(recipeDto,1l);
        recipeService.addRecipe(recipeDto2,2l);
        recipeService.addRecipe(recipeDto3,3l);

        recipeService.changeStatus(1l, Status.APPROVED);
        recipeService.changeStatus(2l, Status.APPROVED);
        recipeService.changeStatus(3l, Status.APPROVED);

        List<RecipeDto> actualList= List.of(recipeDto,recipeDto2,recipeDto3);
        actualList.stream().map((x)->objectMapper.convertValue(x,Recipe.class)).collect(Collectors.toList());

        List<Recipe> expectedList =recipeService.getRecipesListOnModeration(0,10,"","");

        assertEquals(expectedList,actualList);

    }

    @Test
    @SneakyThrows
    void getRecipe() {
        recipeService.addRecipe(recipeDto,1l);
        Recipe recipe=recipeService.getRecipe(1l);
        assertEquals(recipe,objectMapper.convertValue(recipeDto, Recipe.class));
    }

    @Test
    @SneakyThrows
    void changeStatusRecipe() {
        recipeService.addRecipe(recipeDto,1l);
        recipeService.changeStatus(1l, Status.APPROVED);
        Recipe recipe=recipeService.getRecipe(1l);
        assertEquals(recipe.getStatus(),Status.APPROVED);
    }


}