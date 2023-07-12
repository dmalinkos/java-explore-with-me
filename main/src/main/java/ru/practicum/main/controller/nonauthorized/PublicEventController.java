package ru.practicum.main.controller.nonauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.dto.EventFullDto;
import ru.practicum.main.model.dto.EventShortDto;
import ru.practicum.main.model.enums.SortType;
import ru.practicum.main.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text") String text,
                                         @RequestParam(name = "categories") List<Long> categoriesIds,
                                         @RequestParam(name = "paid") Boolean paid,
                                         @RequestParam(name = "rangeStart") LocalDateTime rangeStart,
                                         @RequestParam(name = "rangeEnd") LocalDateTime rangeEnd,
                                         @RequestParam(name = "onlyAvailable") Boolean onlyAvailable,
                                         @RequestParam(name = "sort") SortType sort,
                                         @RequestParam(name = "from") Long from,
                                         @RequestParam(name = "size") Long size) {
        return null;
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Long id) {
        return eventMapper.mapToFullDto(eventService.getEventById(id));
    }
}
