package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);
}
