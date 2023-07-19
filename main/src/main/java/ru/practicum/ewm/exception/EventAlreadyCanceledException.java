package ru.practicum.ewm.exception;

public class EventAlreadyCanceledException extends RuntimeException {
    public EventAlreadyCanceledException(String message) {
        super(message);
    }
}
