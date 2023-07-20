package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.RequestDto;
import ru.practicum.ewm.model.dto.RequestUpdateDto;
import ru.practicum.ewm.model.dto.UpdatedRequestsDto;

import java.util.List;

public interface RequestService {
    RequestDto createRequest(Long userId, Long eventId);

    RequestDto cancelRequest(Long userId, Long requestId);

    UpdatedRequestsDto updateRequest(Long userId, Long eventId, RequestUpdateDto requestUpdateDto);

    List<RequestDto> getUserRequests(Long userId);

    List<RequestDto> getInitiatorEventRequests(Long userId, Long eventId);
}
