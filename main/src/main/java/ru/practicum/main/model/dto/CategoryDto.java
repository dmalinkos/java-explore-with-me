package ru.practicum.main.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class CategoryDto {

    Long id;

    @NotBlank
    String name;
}
