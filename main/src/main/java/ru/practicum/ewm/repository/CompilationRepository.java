package ru.practicum.ewm.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.model.Compilation;

public interface CompilationRepository extends CrudRepository<Compilation, Long>, QuerydslPredicateExecutor<Compilation> {
}
