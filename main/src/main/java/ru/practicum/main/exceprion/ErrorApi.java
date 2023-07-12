package ru.practicum.main.exceprion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.main.constants.Constants;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorApi {
    private final String status;
    private final String reason;
    private final String message;
    private final String timestamp;

    public ErrorApi(HttpStatus status, String message, String reason) {
        this.status = status.getReasonPhrase().toUpperCase();
        this.reason = reason;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER);
    }
}
