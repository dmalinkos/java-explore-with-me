package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.practicum.ewm.constants.Constants;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.enums.AdminStateAction;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AdminUpdateRequestEventDto {
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @Size(min = 20, max = 7000)
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    AdminStateAction stateAction;
    @Size(min = 3, max = 120)
    String title;
}
