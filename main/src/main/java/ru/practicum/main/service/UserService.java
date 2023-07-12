package ru.practicum.main.service;

import ru.practicum.main.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    void deleteUser(Long userId);

    List<User> getUsers(List<Long> userIds, Long from, Long size);

    User getUser(Long userId);
}
