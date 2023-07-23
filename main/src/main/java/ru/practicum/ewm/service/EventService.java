package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.enums.SortType;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto createEvent(Long initiatorId, NewEventDto newEventDto);

    EventFullDto getEventById(Long eventId);

    EventFullDto getPublicEventById(Long eventId, HttpServletRequest request);

    List<EventShortDto> getCurrentUserEvents(Long userId, Long from, Long size);

    EventFullDto getCurrentUserEvent(Long userId, Long eventId);

    EventFullDto updateCurrentUserEvent(Long userId, Long eventId, UserUpdateRequestEventDto eventDto);

    List<EventFullDto> getEventsByAdmin(List<Long> usersIds, List<String> states, List<Long> categoriesIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Long from, Long size);

    List<EventShortDto> getEventsByPublic(String text, List<Long> categoriesIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Long from, Long size, HttpServletRequest request);

    EventFullDto updateEventByAdmin(Long eventId, AdminUpdateRequestEventDto eventDto);

}
