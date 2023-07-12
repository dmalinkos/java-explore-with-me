package ru.practicum.main.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @Size(min = 20, max = 7000)
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    LocationShortDto location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    @Size(min = 3, max = 120)
    String title;

    @Value
    @Getter
    public static class UserShortDto {
        Long id;
        String name;
    }

    @Value
    @Getter
    public static class LocationShortDto {
        Float lat;
        Float lon;
    }
}
