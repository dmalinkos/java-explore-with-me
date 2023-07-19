package ru.practicum.ewm.service;

import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.dto.RequestUpdateDto;
import ru.practicum.ewm.model.dto.UpdatedRequestsDto;

import java.util.List;

public interface RequestService {
    Request createRequest(Long userId, Long eventId);

    Request cancelRequest(Long userId, Long requestId);

    UpdatedRequestsDto updateRequest(Long userId, Long eventId, RequestUpdateDto requestUpdateDto);

    List<Request> getUserRequests(Long userId);

    List<Request> getInitiatorEventRequests(Long userId, Long eventId);
}
