package ru.practicum.main.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.dto.NewEventDto;
import ru.practicum.main.model.dto.EventFullDto;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventFullDto mapToFullDto(Event event);

//    @Mapping(source = "initiator.id", target = "initiator.id")
//    @Mapping(source = "initiator.name", target = "initiator.name")
//    EventFullDto mapToEvent(EventFullDto eventFullDto, User initiator, Location location);

    @Mapping(source = "category", target = "category.id")
    Event mapToCreateEvent(NewEventDto eventFullDto);

}
