package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.ViewStats;
import ru.practicum.ewm.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends CrudRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT (eh.ip) DESC")
    List<ViewStats> getViewStatsBetweenStartAndEnd(@Param("start") LocalDateTime start,
                                                   @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT (DISTINCT eh.ip) DESC")
    List<ViewStats> getUniqueViewStatsBetweenStartAndEnd(@Param("start") LocalDateTime start,
                                                         @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "AND eh.uri IN :uris " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT (DISTINCT eh.ip) DESC")
    List<ViewStats> getUniqueViewStatsBetweenStartAndEndByUris(@Param("start") LocalDateTime start,
                                                               @Param("end") LocalDateTime end,
                                                               @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "AND eh.uri IN :uris " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT (eh.ip) DESC")
    List<ViewStats> getViewStatsBetweenStartAndEndByUris(@Param("start") LocalDateTime start,
                                                         @Param("end") LocalDateTime end,
                                                         @Param("uris") List<String> uris);

}
