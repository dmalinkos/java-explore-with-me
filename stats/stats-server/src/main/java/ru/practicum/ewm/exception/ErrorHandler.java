package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    private static final String BAD_REQUEST_REASON = "Incorrectly made request.";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleBadTimeException(BadTimeException e) {
        return new ErrorApi(HttpStatus.BAD_REQUEST, e.getMessage(), BAD_REQUEST_REASON);
    }
}
