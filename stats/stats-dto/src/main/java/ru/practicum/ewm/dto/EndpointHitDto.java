package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EndpointHitDto {
    private Long id;
    @Pattern(regexp = "^\\w+(.\\w+)*", message = "'app' incorrect input")
    @NotBlank(message = "'app' can't be null or blank")
    private String app;
    @Pattern(regexp = "^/\\w+(/\\d+)*", message = "'uri' incorrect input")
    @NotBlank(message = "'uri' can't be null or blank")
    private String uri;
    @NotNull(message = "'ip' can't be null")
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "'timestamp' can't be null")
    private LocalDateTime timestamp;
}
