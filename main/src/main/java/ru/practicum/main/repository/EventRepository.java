package ru.practicum.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.main.model.Event;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByInitiatorId(Long initiatorId, Pageable page);

    Event findByIdAndInitiatorId(Long eventId, Long initiatorId);

}
