package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.NewEventDto;
import ru.practicum.ewm.model.dto.EventFullDto;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventFullDto mapToFullDto(Event event);

    EventShortDto mapToShortDto(Event event);

    @Mapping(target = "views", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "category", target = "category.id")
    Event mapToCreateEvent(NewEventDto newEventDto);

}
