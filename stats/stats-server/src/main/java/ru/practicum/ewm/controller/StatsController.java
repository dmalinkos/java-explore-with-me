package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.ewm.mapper.EndpointHitMapper;
import ru.practicum.ewm.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;
    private final EndpointHitMapper mapper;

    @PostMapping("/hit")
    public EndpointHitDto saveEndpointHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info(String.format("[StatsController]: POST /hit endpointHitDto: {%s}", endpointHitDto));
        return mapper.toDto(statsService.saveEndpointHit(mapper.toEntity(endpointHitDto)));
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                        @RequestParam(name = "uris", required = false) List<String> uris,
                                        @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        log.info(String.format("[StatsController]: GET /stats" +
                "\nstart:\t%s\nend:\t%s\nuris:\t%s\nunique:\t%s", start, end, uris, unique));
        return statsService.getViewStats(start, end, uris, unique);
    }
}
