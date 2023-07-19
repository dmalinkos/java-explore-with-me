package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long>, QuerydslPredicateExecutor<Event>  {
    List<Event> findByInitiatorId(Long initiatorId, Pageable page);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    boolean existsByCategoryId(Long catId);

}
