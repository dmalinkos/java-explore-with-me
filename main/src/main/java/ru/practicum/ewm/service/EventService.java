package ru.practicum.ewm.service;

import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.AdminUpdateRequestEventDto;
import ru.practicum.ewm.model.dto.NewEventDto;
import ru.practicum.ewm.model.dto.UserUpdateRequestEventDto;
import ru.practicum.ewm.model.enums.SortType;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event createEvent(Long initiatorId, NewEventDto newEventDto);

    Event getEventById(Long eventId);

    Event getPublicEventById(Long eventId, HttpServletRequest request);

    List<Event> getCurrentUserEvents(Long userId, Long from, Long size);

    Event getCurrentUserEvent(Long userId, Long eventId);

    Event updateCurrentUserEvent(Long userId, Long eventId, UserUpdateRequestEventDto eventDto);

    List<Event> getEventsByAdmin(List<Long> usersIds, List<String> states, List<Long> categoriesIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Long from, Long size);

    List<Event> getEventsByPublic(String text, List<Long> categoriesIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Long from, Long size, HttpServletRequest request);

    Event updateEventByAdmin(Long eventId, AdminUpdateRequestEventDto eventDto);

}
