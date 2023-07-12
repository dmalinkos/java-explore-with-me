package ru.practicum.main.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.main.constants.Constants;
import ru.practicum.main.model.enums.EventState;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Value
public class EventFullDto {

    Long id;
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime createdOn;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime eventDate;
    UserShortDto initiator;
    LocationShortDto location;
    Boolean paid;
    Long participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime publishedOn;
    Boolean requestModeration;
    EventState state;
    String title;
    Long views;

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
