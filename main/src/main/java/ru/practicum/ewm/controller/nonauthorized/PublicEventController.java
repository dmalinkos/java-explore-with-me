package ru.practicum.ewm.controller.nonauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.constants.Constants;
import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.enums.SortType;
import ru.practicum.ewm.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) List<Long> categoriesIds,
                                         @RequestParam(name = "paid", required = false) Boolean paid,
                                         @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "false", required = false) Boolean onlyAvailable,
                                         @RequestParam(name = "sort", required = false) SortType sort,
                                         @RequestParam(name = "from", defaultValue = "0", required = false) Long from,
                                         @RequestParam(name = "size", defaultValue = "10", required = false) Long size,
                                         HttpServletRequest request) {
        log.info("GET /events text: {}\n" +
                        "categories: {}\n" +
                        "paid: {}\n" +
                        "rangeStart: {}\n" +
                        "rangeEnd: {}\n" +
                        "onlyAvailable: {}\n" +
                        "sort: {}\n" +
                        "from: {}; size:{}",
                text, categoriesIds, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventService.getEventsByPublic(text, categoriesIds, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable(name = "id") Long eventId,
                                 HttpServletRequest request) {
        log.info("GET /events/{} ", eventId);
        return eventService.getPublicEventById(eventId, request);
    }
}
