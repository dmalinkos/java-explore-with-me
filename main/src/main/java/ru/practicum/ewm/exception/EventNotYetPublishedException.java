package ru.practicum.ewm.exception;

public class EventNotYetPublishedException extends Exception {
    public EventNotYetPublishedException(String message) {
        super(message);
    }
}
