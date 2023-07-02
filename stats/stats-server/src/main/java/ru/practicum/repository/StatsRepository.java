package ru.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.model.EndpointHit;

public interface StatsRepository extends CrudRepository<EndpointHit, Long> {
}
