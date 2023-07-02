package ru.practicum.service;

import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHit saveEndpointHit(EndpointHit endpointHit);

    List<ViewStats> getViewStats(LocalDateTime start,
                                 LocalDateTime end,
                                 List<String> uris,
                                 boolean unique);
}
