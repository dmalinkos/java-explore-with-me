package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.EntityNotFoundException;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getUsers(List<Long> ids, Long from, Long size) {
        Iterable<User> users;
        if (ids != null) {
            users = userRepository.findAllById(ids);
        } else {
            PageRequest page = PageRequest.of(Math.toIntExact(from / size),
                    Math.toIntExact(size));
            users = userRepository.findAll(page);
        }
        return StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d is not exist", userId)));
    }
}
