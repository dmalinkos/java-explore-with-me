package ru.practicum.ewm.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class CategoryDto {

    Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    String name;
}
