package ru.practicum.ewm.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.QRequest;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.RequestDto;
import ru.practicum.ewm.model.dto.RequestUpdateDto;
import ru.practicum.ewm.model.dto.UpdatedRequestsDto;
import ru.practicum.ewm.model.enums.RequestStatus;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;
import ru.practicum.ewm.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final RequestMapper requestMapper;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Override
    @SneakyThrows
    public RequestDto createRequest(Long userId, Long eventId) {
        User requester = userMapper.mapToUser(userService.getUser(userId));
        Event event = eventMapper.mapToEvent(eventService.getEventById(eventId));
        if (Objects.equals(event.getInitiator().getId(), requester.getId())) {
            throw new RequesterIsInitiatorException(String.format("User with id=%d is Initiator event with id=%d", userId, eventId));
        }
        if (event.getPublishedOn() == null) {
            throw new EventNotYetPublishedException(String.format("Event with id=%d not yet PUBLISHED", eventId));
        }
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new MaximumEventConfirmedRequests(String.format("Event with id=%d have max confirmed requests", eventId));
        }
        RequestStatus status;
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            if (event.getParticipantLimit() == 0 || event.getConfirmedRequests() < event.getParticipantLimit()) {
                status = RequestStatus.CONFIRMED;
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventRepository.save(event);
            } else {
                status = RequestStatus.REJECTED;
            }
        } else {
            status = RequestStatus.PENDING;
        }
        Request request = Request.builder()
                .event(event)
                .requester(requester)
                .status(status)
                .build();
        return requestMapper.mapToDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        Request cancelledRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Request with id=%d is not exist", requestId)));
        if (cancelledRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
            throw new RequestAlreadyConfirmedException(String.format("Request with id=%d already CONFIRMED", requestId));
        }
        cancelledRequest.setStatus(RequestStatus.CANCELED);
        return requestMapper.mapToDto(requestRepository.save(cancelledRequest));
    }

    @Override
    public UpdatedRequestsDto updateRequest(Long userId, Long eventId, RequestUpdateDto requestUpdateDto) {
        int confirmedRequest = 0;
        long availableConfirmations;
        Event event = eventMapper.mapToEvent(eventService.getEventById(eventId));
        UpdatedRequestsDto result = new UpdatedRequestsDto();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            return result;
        }
        availableConfirmations = event.getParticipantLimit() - event.getConfirmedRequests();
        if (availableConfirmations <= 0) {
            throw new MaximumEventConfirmedRequests(String.format("Event with id=%d have max confirmed requests", eventId));
        }
        RequestStatus status = RequestStatus.valueOf(requestUpdateDto.getStatus().toString());
        List<Request> requestsToUpdate = getInitiatorEventRequests(userId, eventId).stream()
                .filter(req -> requestUpdateDto.getRequestIds().contains(req.getId()))
                .map(requestMapper::mapToRequest)
                .collect(Collectors.toList());
        for (Request request : requestsToUpdate) {
            if (status.equals(RequestStatus.REJECTED)) {
                request.setStatus(status);
                RequestDto requestDto = requestMapper.mapToDto(request);
                List<RequestDto> rejectedRequests = result.getRejectedRequests();
                rejectedRequests.add(requestDto);
                result.setRejectedRequests(rejectedRequests);
            }
            if (status.equals(RequestStatus.CONFIRMED) && confirmedRequest < availableConfirmations) {
                request.setStatus(status);
                confirmedRequest++;
                RequestDto requestDto = requestMapper.mapToDto(request);
                List<RequestDto> confirmedRequests = result.getConfirmedRequests();
                confirmedRequests.add(requestDto);
                result.setConfirmedRequests(confirmedRequests);
            } else {
                request.setStatus(RequestStatus.REJECTED);
                RequestDto requestDto = requestMapper.mapToDto(request);
                List<RequestDto> rejectedRequests = result.getRejectedRequests();
                rejectedRequests.add(requestDto);
                result.setRejectedRequests(rejectedRequests);
            }
        }
        event.setConfirmedRequests((long) confirmedRequest);
        eventRepository.save(event);
        requestRepository.saveAll(requestsToUpdate);
        return result;
    }

    @Override
    public List<RequestDto> getUserRequests(Long userId) {
        return requestRepository.findByRequesterId(userId).stream()
                .map(requestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getInitiatorEventRequests(Long userId, Long eventId) {
        QRequest qRequest = QRequest.request;
        BooleanExpression expression = qRequest.event.initiator.id.eq(userId).and(qRequest.event.id.eq(eventId));
        Iterable<Request> requests = requestRepository.findAll(expression);
        return StreamSupport.stream(requests.spliterator(), false)
                .map(requestMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
