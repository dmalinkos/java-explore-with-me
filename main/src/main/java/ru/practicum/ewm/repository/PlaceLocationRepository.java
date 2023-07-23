package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.model.PlaceLocation;

public interface PlaceLocationRepository extends CrudRepository<PlaceLocation, Long> {

    Iterable<PlaceLocation> findAll(Pageable pageable);
}
