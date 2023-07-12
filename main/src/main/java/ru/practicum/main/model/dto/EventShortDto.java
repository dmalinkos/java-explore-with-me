package ru.practicum.main.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Value;
import ru.practicum.main.constants.Constants;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventShortDto {

    Long id;
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime eventDate;
    EventFullDto.UserShortDto initiator;
    Boolean paid;
    String title;
    Long views;

    @Value
    @Getter
    public static class UserShortDto {
        Long id;
        String name;
    }
}
