package ru.practicum.main.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@Builder
public class UserDto {
    Long id;
    @NotBlank
    String name;
    @Email
    String email;
}