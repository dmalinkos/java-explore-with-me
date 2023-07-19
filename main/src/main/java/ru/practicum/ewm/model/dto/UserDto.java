package ru.practicum.ewm.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class UserDto {
    Long id;
    @Size(min = 2, max = 250)
    @NotBlank
    String name;
    @Email
    @NotNull
    @Size(min = 6, max = 254)
    String email;
}