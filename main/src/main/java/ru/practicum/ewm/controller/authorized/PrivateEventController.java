package ru.practicum.ewm.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@Valid @RequestBody NewEventDto newEventDto,
                                  @PathVariable @PositiveOrZero Long userId) {
        return eventService.createEvent(userId, newEventDto);
    }

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Long from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive Long size) {
        return eventService.getCurrentUserEvents(userId, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable @PositiveOrZero Long userId,
                                   @PathVariable @PositiveOrZero Long eventId,
                                   @Valid @RequestBody UserUpdateRequestEventDto eventPatchDto) {
        return eventService.updateCurrentUserEvent(userId, eventId, eventPatchDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable @PositiveOrZero Long userId,
                                 @PathVariable @PositiveOrZero Long eventId) {
        return eventService.getCurrentUserEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable @PositiveOrZero Long userId,
                                             @PathVariable @PositiveOrZero Long eventId) {
        return new ArrayList<>(requestService.getInitiatorEventRequests(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public UpdatedRequestsDto updateEventRequests(@PathVariable @PositiveOrZero Long userId,
                                                  @PathVariable @PositiveOrZero Long eventId,
                                                  @RequestBody RequestUpdateDto requestUpdateDto) {
        return requestService.updateRequest(userId, eventId, requestUpdateDto);
    }

}
