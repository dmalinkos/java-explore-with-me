package ru.practicum.ewm.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NewCompilationDto {
    Boolean pinned = false;
    @NotBlank
    @Size(min = 1, max = 50)
    String title;
    Set<Long> events;
}
