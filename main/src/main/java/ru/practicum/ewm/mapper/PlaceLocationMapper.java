package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.model.PlaceLocation;
import ru.practicum.ewm.model.dto.PlaceLocationDto;

@Mapper(componentModel = "spring")
public interface PlaceLocationMapper {

    PlaceLocation mapToEntity(PlaceLocationDto placeLocationDto);

    PlaceLocationDto mapToDto(PlaceLocation placeLocation);
}
