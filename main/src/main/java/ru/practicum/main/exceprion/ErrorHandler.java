package ru.practicum.main.exceprion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    private static final String NOT_FOUND_REASON = "The required object was not found.";
    private static final String INTEGRITY_VIOLATION = "Integrity constraint has been violated.";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorApi handleException(Exception exception) {
        log.error("ERROR: ", exception);
        StringWriter out = new StringWriter();
        exception.printStackTrace(new PrintWriter(out));
        String stackTrace = out.toString();
        return new ErrorApi(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), stackTrace);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorApi handeEntityNotFoundException(EntityNotFoundException e) {
        log.warn("Entity is not exist", e);
        return new ErrorApi(HttpStatus.NOT_FOUND, e.getMessage(), NOT_FOUND_REASON);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorApi handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn("Unique index or primary key violation", e);
        return new ErrorApi(HttpStatus.CONFLICT, e.getMessage(), INTEGRITY_VIOLATION);
    }
}
