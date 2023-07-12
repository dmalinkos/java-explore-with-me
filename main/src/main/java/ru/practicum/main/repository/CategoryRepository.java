package ru.practicum.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.main.model.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAll(Pageable pageable);


}
