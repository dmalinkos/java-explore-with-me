package ru.practicum.ewm.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlaceLocationDto {

    Long id;
    @NotBlank
    @Size(min = 3, max = 255)
    String name;
    @NotNull
    Float lat;
    @NotNull
    Float lon;
    @NotNull
    @Positive
    Float radius;

}
