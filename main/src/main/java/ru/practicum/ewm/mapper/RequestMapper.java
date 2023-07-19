package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.dto.RequestDto;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.UserService;

@Mapper(componentModel = "spring")
public abstract class RequestMapper {

    @Autowired
    protected EventService eventService;

    @Autowired
    protected UserService userService;

    @Mapping(target = "event", expression = "java(eventService.getEventById(requestDto.getEvent()))")
    @Mapping(target = "requester", expression = "java(userService.getUser(requestDto.getRequester()))")
    public abstract Request mapToRequest(RequestDto requestDto);

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    public abstract RequestDto mapToDto(Request request);
}
