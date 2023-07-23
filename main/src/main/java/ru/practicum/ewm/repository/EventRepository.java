package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.EventFullDto;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    List<Event> findByInitiatorId(Long initiatorId, Pageable page);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    boolean existsByCategoryId(Long catId);

    @Query(nativeQuery = true, value =
            "SELECT e " +
            "FROM events e " +
            "JOIN locations l on l.id = e.location_id " +
            "WHERE distance(l.lat, l.lon, :lat, :lon) <= :radius ")
    List<EventFullDto> getEventsInRadius(Float lat, Float lon, Float radius);

}
