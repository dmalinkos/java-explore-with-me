package ru.practicum.ewm.model.dto;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceLocationUpdateDto {

    @Size(min = 3, max = 255)
    private String name;

    private Float lat;

    private Float lon;

    @Positive
    private Float radius;
}
