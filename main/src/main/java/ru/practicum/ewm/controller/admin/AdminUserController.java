package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.dto.UserDto;
import ru.practicum.ewm.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        log.info("POST /admin/users userDto: {}", userDto);
        return userMapper.mapToUserDto(userService.addUser(userMapper.mapToUser(userDto)));
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                  @RequestParam(name = "from", defaultValue = "0") Long from,
                                  @RequestParam(name = "size", defaultValue = "10") Long size) {
        log.info("GET /admin/users ids: {}, from: {}, size = {}", ids, from, size);
        return userService.getUsers(ids, from, size).stream()
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        log.info("DELETE /admin/users/{}", userId);
        userService.deleteUser(userId);
    }
}
