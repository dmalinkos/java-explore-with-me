package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {

    EndpointHitDto toDto(EndpointHit endpointHit);

    EndpointHit toEntity(EndpointHitDto endpointHitDto);
}
