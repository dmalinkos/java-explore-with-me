package ru.practicum.ewm.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Set;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompilationDto {
    Long id;
    Boolean pinned;
    String title;
    Set<EventShortDto> events;
}
