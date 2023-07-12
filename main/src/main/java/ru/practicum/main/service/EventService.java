package ru.practicum.main.service;

import ru.practicum.main.model.Event;
import ru.practicum.main.model.dto.AdminUpdateRequestEventDto;
import ru.practicum.main.model.dto.NewEventDto;
import ru.practicum.main.model.dto.UserUpdateRequestEventDto;

import java.util.List;

public interface EventService {
    Event createEvent(Long initiatorId, NewEventDto newEventDto);

    Event getEventById(Long eventId);

    List<Event> getCurrentUserEvents(Long userId, Long from, Long size);

    Event getCurrentUserEvent(Long userId, Long eventId);

    Event updateCurrentUserEvent(Long userId, Long eventId, UserUpdateRequestEventDto eventDto);

    List<Event> getEventsByAdmin(); // TODO

    Event updateEventByAdmin(Long eventId, AdminUpdateRequestEventDto eventDto);

}
