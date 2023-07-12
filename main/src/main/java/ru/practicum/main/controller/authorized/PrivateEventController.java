package ru.practicum.main.controller.authorized;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.dto.NewEventDto;
import ru.practicum.main.model.dto.EventFullDto;
import ru.practicum.main.model.dto.UserUpdateRequestEventDto;
import ru.practicum.main.service.EventService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@Valid @RequestBody NewEventDto newEventDto,
                                  @PathVariable Long userId) {
        return eventMapper.mapToFullDto(eventService.createEvent(userId, newEventDto));
    }

    @GetMapping
    public EventFullDto getEvents(@PathVariable Long userId,
                                  @RequestParam(name = "from", defaultValue = "0") Long from,
                                  @RequestParam(name = "size", defaultValue = "10") Long size) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long userId,
                                   @PathVariable Long eventId,
                                   @RequestBody UserUpdateRequestEventDto eventPatchDto) {
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId,
                                 @PathVariable Long eventId) {
        return null;
    }

}
