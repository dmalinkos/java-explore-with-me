package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.constants.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Value
public class EventShortDto {

    Long id;
    @Size(min = 20, max = 2000)
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime eventDate;
    UserShortDto initiator;
    Boolean paid;
    @Size(min = 3, max = 120)
    String title;
    Long views;
}
