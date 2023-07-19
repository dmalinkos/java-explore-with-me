package ru.practicum.ewm.model.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.Set;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateCompilationDto {
    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;
    Set<Long> events;
}
