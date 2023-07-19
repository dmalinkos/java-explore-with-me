package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.constants.Constants;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.dto.AdminUpdateRequestEventDto;
import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Long> usersIds,
                                        @RequestParam(name = "states", required = false) List<String> states,
                                        @RequestParam(name = "categories", required = false) List<Long> categoriesIds,
                                        @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0", required = false) Long from,
                                        @RequestParam(name = "size", defaultValue = "10", required = false) Long size) {
        log.info("GET /admin/events users: {}\n" +
                        "states: {}\n" +
                        "categories: {}\n" +
                        "rangeStart: {}\n" +
                        "rangeEnd: {}\n" +
                        "from: {}; size:{}",
                usersIds, states, categoriesIds, rangeStart, rangeEnd, from, size);
        return eventService.getEventsByAdmin(usersIds, states, categoriesIds, rangeStart, rangeEnd, from, size).stream()
                .map(eventMapper::mapToFullDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    public EventFullDto updateEvent(@PathVariable(name = "id") Long eventId,
                                    @Valid @RequestBody AdminUpdateRequestEventDto eventPatchDto) {
        log.info("POST /admin/events/{} eventPatchDto: {}", eventId, eventPatchDto);
        return eventMapper.mapToFullDto(eventService.updateEventByAdmin(eventId, eventPatchDto));
    }
}
