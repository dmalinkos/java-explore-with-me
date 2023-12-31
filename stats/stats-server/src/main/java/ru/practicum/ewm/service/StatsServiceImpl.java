package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.constants.Constants;
import ru.practicum.ewm.exception.BadTimeException;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.repository.StatsRepository;
import ru.practicum.ewm.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public EndpointHit saveEndpointHit(EndpointHit endpointHit) {
        return statsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<ViewStats> viewStats;
        if (start.isAfter(end)) {
            throw new BadTimeException(String.format("rangeEnd %s is after rangeStart %s",
                    start.format(Constants.DATE_TIME_FORMATTER),
                    end.format(Constants.DATE_TIME_FORMATTER)));
        }
        if (unique) {
            if (uris != null) {
                viewStats = statsRepository.getUniqueViewStatsBetweenStartAndEndByUris(start, end, uris);
            } else {
                viewStats = statsRepository.getUniqueViewStatsBetweenStartAndEnd(start, end);
            }
        } else {
            if (uris != null) {
                viewStats = statsRepository.getViewStatsBetweenStartAndEndByUris(start, end, uris);
            } else {
                viewStats = statsRepository.getViewStatsBetweenStartAndEnd(start, end);
            }
        }
        return viewStats;
    }

}
