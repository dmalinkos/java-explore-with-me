package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);

    List<UserDto> getUsers(List<Long> userIds, Long from, Long size);

    UserDto getUser(Long userId);
}
