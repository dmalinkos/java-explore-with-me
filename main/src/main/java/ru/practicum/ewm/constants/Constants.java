package ru.practicum.ewm.constants;

import java.time.format.DateTimeFormatter;

public final class Constants {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private Constants() {
    }
}
