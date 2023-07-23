package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.PlaceLocationDto;
import ru.practicum.ewm.model.dto.PlaceLocationUpdateDto;

import java.util.List;

public interface PlaceLocationService {

    PlaceLocationDto savePlaceLocation(PlaceLocationDto placeLocationDto);

    PlaceLocationDto updatePlaceLocation(Long locId, PlaceLocationUpdateDto placeLocationUpdateDto);

    void deletePlaceLocation(Long locId);

    PlaceLocationDto findById(Long locId);

    List<PlaceLocationDto> findAll(int from, int size);

}
