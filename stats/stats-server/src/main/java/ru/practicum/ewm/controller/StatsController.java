package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.StatsService;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.ewm.mapper.EndpointHitMapper;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StatsController {
    private final StatsService statsService;
    private final EndpointHitMapper mapper;

    @PostMapping("/hit")
    public EndpointHitDto saveEndpointHit(@Valid EndpointHitDto endpointHitDto) {
        log.info(String.format("[StatsController]: POST /hit \nendpointHitDto: {%s}", endpointHitDto));
        return mapper.toDto(statsService.saveEndpointHit(mapper.toEntity(endpointHitDto)));
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                        @RequestParam("uris") List<String> uris,
                                        @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        log.info(String.format("[StatsController]: GET /stats" +
                "\nstart:\t%s\nend:\t%s\nuris:\t%s\nunique:\t%s", start, end, uris, unique));
        return statsService.getViewStats(start, end, uris, unique);
    }
}
