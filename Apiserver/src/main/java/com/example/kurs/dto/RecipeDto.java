package com.example.kurs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@Builder
public class RecipeDto {
    @NotNull(message = "title must not be empty")
    @Size(min = 5, max = 256, message = " description do not match the size")
    private String title;
    @NotNull(message = "description must not be empty")
    @Size(min = 5, max = 4096, message = " description do not match the size")
    private String description;

    private String kitchen;
}
