package com.example.kurs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@Builder
public class StatusDTO {
    @NotNull(message = "status must not be empty")
    @Size(min = 0, max = 256, message = " status do not match the size")
    private String status;
}
