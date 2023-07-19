package ru.practicum.ewm.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.model.dto.RequestDto;
import ru.practicum.ewm.service.RequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable Long userId,
                                    @RequestParam(name = "eventId") Long eventId) {
        return requestMapper.mapToDto(requestService.createRequest(userId, eventId));
    }

    @GetMapping
    public List<RequestDto> getUserRequests(@PathVariable Long userId) {
        return requestService.getUserRequests(userId).stream()
                .map(requestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                    @PathVariable Long requestId) {
        return requestMapper.mapToDto(requestService.cancelRequest(userId, requestId));
    }
}
