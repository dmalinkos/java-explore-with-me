package ru.practicum.ewm.exception;

public class MaximumEventConfirmedRequests extends RuntimeException {
    public MaximumEventConfirmedRequests(String message) {
        super(message);
    }
}
