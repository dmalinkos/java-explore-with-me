package ru.practicum.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.exceprion.EntityNotFoundException;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.Category;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.User;
import ru.practicum.main.model.dto.AdminUpdateRequestEventDto;
import ru.practicum.main.model.dto.NewEventDto;
import ru.practicum.main.model.dto.UserUpdateRequestEventDto;
import ru.practicum.main.model.enums.EventState;
import ru.practicum.main.repository.EventRepository;
import ru.practicum.main.service.CategoryService;
import ru.practicum.main.service.EventService;
import ru.practicum.main.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final CategoryService categoryService;
    private final UserService userService;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    @Override
    public Event createEvent(Long initiatorId, NewEventDto newEventDto) {
        Category category = categoryService.getCategory(newEventDto.getCategory());
        User initiator = userService.getUser(initiatorId);
        Event event = eventMapper.mapToCreateEvent(newEventDto);
        event.setCategory(category);
        event.setInitiator(initiator);
        event.setConfirmedRequests(0L);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        event.setViews(0L);
        return eventRepository.save(event);
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(String.format("Event with id=%d is not exist", eventId)));
    }

    @Override
    public List<Event> getCurrentUserEvents(Long userId, Long from, Long size) {
        Pageable page = PageRequest.of(Math.toIntExact(size / from), Math.toIntExact(size));
        return eventRepository.findByInitiatorId(userId, page);
    }

    @Override
    public Event getCurrentUserEvent(Long userId, Long eventId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId);
    }

    @Override
    public Event updateCurrentUserEvent(Long userId, Long eventId, UserUpdateRequestEventDto eventDto) {
        Event updatedEvent = eventRepository.findByIdAndInitiatorId(eventId, userId);
        //TODO обновление евента
        return updatedEvent;
    }

    @Override
    public List<Event> getEventsByAdmin() {
        // TODO фильтрации с помощью спецификации
        return null;
    }

    @Override
    public Event updateEventByAdmin(Long eventId, AdminUpdateRequestEventDto eventDto) {
        return null;
    }
}
