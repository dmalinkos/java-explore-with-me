package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.EntityNotFoundException;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.UserDto;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        return userMapper.mapToUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Long from, Long size) {
        Iterable<User> users;
        if (ids != null) {
            users = userRepository.findAllById(ids);
        } else {
            PageRequest page = PageRequest.of(Math.toIntExact(from / size),
                    Math.toIntExact(size));
            users = userRepository.findAll(page);
        }
        return StreamSupport.stream(users.spliterator(), false)
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapToUserDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d is not exist", userId)));
    }
}
