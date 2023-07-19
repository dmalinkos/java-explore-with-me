package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.practicum.ewm.constants.Constants;
import ru.practicum.ewm.model.enums.RequestStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RequestDto {
    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @NotNull
    LocalDateTime created;
    @NotNull
    Long event;
    @NotNull
    Long requester;
    RequestStatus status;
}
