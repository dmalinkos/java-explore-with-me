package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.ewm.model.EndpointHit;

@Mapper(componentModel = "spring")
@Component
public interface EndpointHitMapper {

    EndpointHitDto toDto(EndpointHit endpointHit);

    EndpointHit toEntity(EndpointHitDto endpointHitDto);
}
