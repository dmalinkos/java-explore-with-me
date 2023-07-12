package ru.practicum.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.main.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAllByIdIn(Collection<Long> ids);

    List<User> findAll(Pageable pageable);
}
