package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    private static final String NOT_FOUND_REASON = "The required object was not found.";
    private static final String INTEGRITY_VIOLATION = "Integrity constraint has been violated.";
    private static final String BAD_REQUEST_REASON = "Incorrectly made request.";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorApi handleException(Exception exception) {
        log.error("ERROR: ", exception);
        return new ErrorApi(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getClass().toString());
    }

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            EventAccessException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorApi handeEntityNotFoundException(Exception e) {
        return new ErrorApi(HttpStatus.NOT_FOUND, e.getMessage(), NOT_FOUND_REASON);
    }

    @ExceptionHandler(value = {
            RequesterIsInitiatorException.class,
            DataIntegrityViolationException.class,
            CategoryIsUseException.class,
            EventAlreadyPublishedException.class,
            EventAlreadyCanceledException.class,
            EventNotYetPublishedException.class,
            MaximumEventConfirmedRequests.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorApi handleDataIntegrityViolationException(Exception e) {
        return new ErrorApi(HttpStatus.CONFLICT, e.getMessage(), INTEGRITY_VIOLATION);
    }

    @ExceptionHandler(value = {
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            BadTimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleMissingServletRequestParameterException(Exception e) {
        return new ErrorApi(HttpStatus.BAD_REQUEST, e.getMessage(), BAD_REQUEST_REASON);
    }
}
