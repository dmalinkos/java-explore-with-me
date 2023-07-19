package ru.practicum.ewm.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.model.Request;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long>, QuerydslPredicateExecutor<Request> {
    List<Request> findByRequesterId(Long userId);
}
