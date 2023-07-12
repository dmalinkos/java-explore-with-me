package ru.practicum.main.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.dto.AdminUpdateRequestEventDto;
import ru.practicum.main.model.dto.EventFullDto;
import ru.practicum.main.model.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/events")
public class AdminEventController {
    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "users", required = false) List<Long> usersIds,
                                         @RequestParam(name = "states", required = false) List<Long> states,
                                         @RequestParam(name = "categories", required = false) List<Long> categoriesIds,
                                         @RequestParam(name = "rangeStart", required = false) LocalDateTime rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) LocalDateTime rangeEnd,
                                         @RequestParam(name = "from", required = false) Long from,
                                         @RequestParam(name = "size", required = false) Long size) {
        return null;
    }

    @PatchMapping("/{id}")
    public EventFullDto updateEvent(@PathVariable(name = "id") Long eventId,
                                   @RequestBody AdminUpdateRequestEventDto eventPatchDto) {
        return null;
    }
}
