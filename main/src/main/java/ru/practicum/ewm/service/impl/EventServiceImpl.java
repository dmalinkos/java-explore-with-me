package ru.practicum.ewm.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.StatisticClient;
import ru.practicum.ewm.constants.Constants;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.QEvent;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.enums.AdminStateAction;
import ru.practicum.ewm.model.enums.EventState;
import ru.practicum.ewm.model.enums.SortType;
import ru.practicum.ewm.model.enums.UserStateAction;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final CategoryService categoryService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final EventRepository eventRepository;
    private final StatisticClient statisticClient;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public EventFullDto createEvent(Long initiatorId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new BadTimeException("Еhe date and time for which the event is scheduled cannot be earlier than two hours from the current moment");
        }
        Category category = categoryMapper.mapToCategory(categoryService.getCategory(newEventDto.getCategory()));
        User initiator = userMapper.mapToUser(userService.getUser(initiatorId));
        Event event = eventMapper.mapToCreateEvent(newEventDto);
        event.setCategory(category);
        event.setInitiator(initiator);
        event.setConfirmedRequests(0L);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        event.setViews(0L);
        return eventMapper.mapToFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::mapToFullDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event with id=%d is not exist", eventId)));
    }

    @SneakyThrows
    @Override
    public EventFullDto getPublicEventById(Long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(String.format("Event with id=%d is not exist", eventId)));
        if (event.getPublishedOn() == null) {
            throw new EventAccessException(String.format("Event with id=%d not yet PUBLISHED", eventId));
        }
        statisticClient.addEndpointHit("ewm-main-service", request);
        List<ViewStats> stats = statisticClient.getStatsView(
                event.getPublishedOn(),
                LocalDateTime.now(),
                List.of(request.getRequestURI()),
                true);
        event.setViews(stats.get(0).getHits());
        return eventMapper.mapToFullDto(event);
    }

    @Override
    public List<EventShortDto> getCurrentUserEvents(Long userId, Long from, Long size) {
        Pageable page = PageRequest.of(Math.toIntExact(from / size), Math.toIntExact(size));
        return eventRepository.findByInitiatorId(userId, page).stream()
                .map(eventMapper::mapToShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getCurrentUserEvent(Long userId, Long eventId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .map(eventMapper::mapToFullDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Event with id=%d and initiator with id=%d is not exist", eventId, userId))
                );
    }

    @Override
    public List<EventFullDto> getEventsByAdmin(List<Long> usersIds, List<String> states, List<Long> categoriesIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Long from, Long size) {
        QEvent qEvent = QEvent.event;
        BooleanExpression expression = Expressions.asBoolean(true).eq(true);
        if (usersIds != null) {
            expression = expression.and(qEvent.initiator.id.in(usersIds));
        }
        if (states != null) {
            expression = expression.and(qEvent.state.stringValue().in(states));
        }
        if (categoriesIds != null) {
            expression = expression.and(qEvent.category.id.in(categoriesIds));
        }
        if (rangeStart != null) {
            expression = expression.and(qEvent.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            expression = expression.and(qEvent.eventDate.before(rangeEnd));
        }
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(from / size), Math.toIntExact(size));
        List<Event> events = eventRepository.findAll(expression, pageRequest).getContent();
        setViews(events);
        return events.stream()
                .map(eventMapper::mapToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> getEventsByPublic(String text, List<Long> categoriesIds, Boolean paid, LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sortType,
                                                 Long from, Long size, HttpServletRequest request) {
        statisticClient.addEndpointHit("ewm-main-service", request);
        QEvent qEvent = QEvent.event;
        BooleanExpression expression = Expressions.asBoolean(true).eq(true);
        if (text != null) {
            expression = expression.and(qEvent.annotation.toLowerCase().contains(text.toLowerCase())
                    .or(qEvent.description.toLowerCase().contains(text.toLowerCase())));
        }
        if (categoriesIds != null) {
            expression = expression.and(qEvent.category.id.in(categoriesIds));
        }
        if (paid != null) {
            expression = expression.and(qEvent.paid.eq(paid));
        }
        if (rangeStart != null) {
            expression = expression.and(qEvent.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            expression = expression.and(qEvent.eventDate.before(rangeEnd));
        }
        if (rangeStart == null && rangeEnd == null) {
            expression = expression.and(qEvent.eventDate.after(LocalDateTime.now()));
        } else if (rangeStart.isAfter(rangeEnd)) {
            throw new BadTimeException(String.format("rangeEnd %s is after rangeStart %s",
                    rangeEnd.format(Constants.DATE_TIME_FORMATTER),
                    rangeStart.format(Constants.DATE_TIME_FORMATTER)));
        }
        if (onlyAvailable) {
            expression = expression.and(qEvent.confirmedRequests.lt(qEvent.participantLimit));
        }
        PageRequest pageRequest;
        if (sortType != null) {
            Sort sort;
            switch (sortType) {
                case EVENT_DATE:
                    sort = Sort.by(Sort.Direction.ASC, "eventDate");
                    break;
                case VIEWS:
                    sort = Sort.by(Sort.Direction.ASC, "views");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sortType);
            }
            pageRequest = PageRequest.of(Math.toIntExact(from / size), Math.toIntExact(size), sort);
        } else {
            pageRequest = PageRequest.of(Math.toIntExact(from / size), Math.toIntExact(size));
        }
        List<Event> events = eventRepository.findAll(expression, pageRequest).getContent();
        setViews(events);
        return events.stream()
                .map(eventMapper::mapToShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateCurrentUserEvent(Long userId, Long eventId, UserUpdateRequestEventDto eventDto) {
        Event updatedEvent = eventMapper.mapToEvent(getCurrentUserEvent(userId, eventId));
        Optional.ofNullable(updatedEvent.getPublishedOn()).ifPresent(e -> {
            throw new EventAlreadyPublishedException(String.format("Cannot update event with id=%d because it's not in the right state: PUBLISHED", eventId));
        });
        Optional.ofNullable(eventDto.getAnnotation()).ifPresent(updatedEvent::setAnnotation);
        Optional.ofNullable(eventDto.getCategory()).ifPresent(catId -> categoryMapper.mapToCategory(categoryService.getCategory(catId)));
        Optional.ofNullable(eventDto.getDescription()).ifPresent(updatedEvent::setDescription);
        Optional.ofNullable(eventDto.getEventDate()).ifPresent(eventDate -> {
            if (eventDate.minusHours(2).isBefore(LocalDateTime.now())) {
                throw new BadTimeException("Еhe date and time for which the event is scheduled cannot be earlier than two hours from the current moment");
            }
            updatedEvent.setEventDate(eventDate);
        });
        Optional.ofNullable(eventDto.getLocation()).ifPresent(updatedEvent::setLocation);
        Optional.ofNullable(eventDto.getPaid()).ifPresent(updatedEvent::setPaid);
        Optional.ofNullable(eventDto.getParticipantLimit()).ifPresent(updatedEvent::setParticipantLimit);
        Optional.ofNullable(eventDto.getRequestModeration()).ifPresent(updatedEvent::setRequestModeration);
        Optional.ofNullable(eventDto.getStateAction()).ifPresent(state -> {
            if (state.equals(UserStateAction.SEND_TO_REVIEW)) {
                updatedEvent.setState(EventState.PENDING);
            } else {
                updatedEvent.setState(EventState.CANCELED);
            }
        });
        Optional.ofNullable(eventDto.getTitle()).ifPresent(updatedEvent::setTitle);
        return eventMapper.mapToFullDto(updatedEvent);
    }

    @SneakyThrows
    @Override
    public EventFullDto updateEventByAdmin(Long eventId, AdminUpdateRequestEventDto eventDto) {
        Event updatedEvent = eventMapper.mapToEvent(getEventById(eventId));
        Optional.ofNullable(eventDto.getAnnotation()).ifPresent(updatedEvent::setAnnotation);
        Optional.ofNullable(eventDto.getCategory()).ifPresent(catId -> categoryMapper.mapToCategory(categoryService.getCategory(catId)));
        Optional.ofNullable(eventDto.getDescription()).ifPresent(updatedEvent::setDescription);
        Optional.ofNullable(eventDto.getEventDate()).ifPresent(eventDate -> {
            if (eventDate.minusHours(2).isBefore(LocalDateTime.now())) {
                throw new BadTimeException("Еhe date and time for which the event is scheduled cannot be earlier than two hours from the current moment");
            }
            updatedEvent.setEventDate(eventDate);
        });
        Optional.ofNullable(eventDto.getLocation()).ifPresent(updatedEvent::setLocation);
        Optional.ofNullable(eventDto.getPaid()).ifPresent(updatedEvent::setPaid);
        Optional.ofNullable(eventDto.getParticipantLimit()).ifPresent(updatedEvent::setParticipantLimit);
        Optional.ofNullable(eventDto.getRequestModeration()).ifPresent(updatedEvent::setRequestModeration);
        Optional.ofNullable(eventDto.getStateAction()).ifPresent(state -> {
            if (state.equals(AdminStateAction.PUBLISH_EVENT)) {
                if (updatedEvent.getPublishedOn() != null) {
                    throw new EventAlreadyPublishedException("Cannot publish the event because it's not in the right state: PUBLISHED");
                }
                if (updatedEvent.getState().equals(EventState.CANCELED)) {
                    throw new EventAlreadyCanceledException("Cannot publish the event because it's not in the right state: CANCELED");
                }
                updatedEvent.setState(EventState.PUBLISHED);
                updatedEvent.setPublishedOn(LocalDateTime.now());
            } else if (state.equals(AdminStateAction.REJECT_EVENT)) {
                if (updatedEvent.getPublishedOn() != null) {
                    throw new EventAlreadyPublishedException("Cannot publish the event because it's not in the right state: PUBLISHED");
                }
                updatedEvent.setState(EventState.CANCELED);
            }
        });
        Optional.ofNullable(eventDto.getTitle()).ifPresent(updatedEvent::setTitle);
        return eventMapper.mapToFullDto(eventRepository.save(updatedEvent));
    }

    private void setViews(List<Event> events) {
        LocalDateTime start = events.get(0).getCreatedOn();
        List<String> uris = new ArrayList<>();
        Map<String, Event> eventUri = new HashMap<>();
        for (Event event : events) {
            if (start.isBefore(event.getCreatedOn())) {
                start = event.getCreatedOn();
            }
            String uri = "/events/" + event.getId();
            uris.add(uri);
            eventUri.put(uri, event);
        }
        List<ViewStats> statsView = statisticClient.getStatsView(start, LocalDateTime.now(), uris, false);
        statsView.forEach((stat) -> eventUri.get(stat.getUri()).setViews(stat.getHits()));
    }
}
