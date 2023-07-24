package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
    @NotEmpty
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    Long category;
    @NotNull
    @Size(min = 20, max = 7000)
    String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull
    LocationDto location;
    Boolean paid = false;
    Long participantLimit;
    Boolean requestModeration = true;
    @NotNull
    @Size(min = 3, max = 120)
    String title;
}
