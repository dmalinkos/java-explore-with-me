package ru.practicum.ewm.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@Valid @RequestBody NewEventDto newEventDto,
                                  @PathVariable Long userId) {
        return eventMapper.mapToFullDto(eventService.createEvent(userId, newEventDto));
    }

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                         @RequestParam(name = "from", defaultValue = "0") Long from,
                                         @RequestParam(name = "size", defaultValue = "10") Long size) {
        return eventService.getCurrentUserEvents(userId, from, size).stream()
                .map(eventMapper::mapToShortDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long userId,
                                   @PathVariable Long eventId,
                                   @Valid @RequestBody UserUpdateRequestEventDto eventPatchDto) {
        return eventMapper.mapToFullDto(eventService.updateCurrentUserEvent(userId, eventId, eventPatchDto));
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId,
                                 @PathVariable Long eventId) {
        return eventMapper.mapToFullDto(eventService.getCurrentUserEvent(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable Long userId,
                                             @PathVariable Long eventId) {
        return requestService.getInitiatorEventRequests(userId, eventId).stream()
                .map(requestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}/requests")
    public UpdatedRequestsDto updateEventRequests(@PathVariable Long userId,
                                                  @PathVariable Long eventId,
                                                  @RequestBody RequestUpdateDto requestUpdateDto) {
        return requestService.updateRequest(userId, eventId, requestUpdateDto);
    }

}
