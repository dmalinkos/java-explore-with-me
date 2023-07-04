package ru.practicum.ewm.service;

import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHit saveEndpointHit(EndpointHit endpointHit);

    List<ViewStats> getViewStats(LocalDateTime start,
                                 LocalDateTime end,
                                 List<String> uris,
                                 boolean unique);
}
