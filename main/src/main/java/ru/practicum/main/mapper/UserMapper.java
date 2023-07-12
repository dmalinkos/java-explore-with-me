package ru.practicum.main.mapper;

import org.mapstruct.Mapper;
import ru.practicum.main.model.User;
import ru.practicum.main.model.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);
}
