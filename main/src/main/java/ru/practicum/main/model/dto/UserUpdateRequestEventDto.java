package ru.practicum.main.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import ru.practicum.main.constants.Constants;
import ru.practicum.main.model.enums.AdminStateAction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
@Builder
public class UserUpdateRequestEventDto {
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @Size(min = 20, max = 7000)
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime eventDate;
    EventFullDto.LocationShortDto location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    AdminStateAction stateAction;
    @Size(min = 3, max = 120)
    String title;

    @Value
    @Getter
    public static class LocationShortDto {
        Float lat;
        Float lon;
    }
}
