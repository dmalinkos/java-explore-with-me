package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.EntityNotFoundException;
import ru.practicum.ewm.mapper.PlaceLocationMapper;
import ru.practicum.ewm.model.PlaceLocation;
import ru.practicum.ewm.model.dto.PlaceLocationDto;
import ru.practicum.ewm.model.dto.PlaceLocationUpdateDto;
import ru.practicum.ewm.repository.PlaceLocationRepository;
import ru.practicum.ewm.service.PlaceLocationService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PlaceLocationServiceImpl implements PlaceLocationService {
    public final PlaceLocationMapper placeLocationMapper;
    private final PlaceLocationRepository placeLocationRepository;

    @Override
    public PlaceLocationDto savePlaceLocation(PlaceLocationDto placeLocationDto) {
        PlaceLocation placeLocation = placeLocationMapper.mapToEntity(placeLocationDto);
        return placeLocationMapper.mapToDto(placeLocationRepository.save(placeLocation));
    }

    @Override
    public PlaceLocationDto updatePlaceLocation(Long locId, PlaceLocationUpdateDto placeLocationUpdateDto) {
        PlaceLocation placeLocation = placeLocationRepository.findById(locId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Location with id=%d is not exist", locId)
                )
        );
        if (placeLocationUpdateDto.getName() != null) {
            placeLocation.setName(placeLocationUpdateDto.getName());
        }
        if (placeLocationUpdateDto.getLon() != null) {
            placeLocation.setLon(placeLocationUpdateDto.getLon());
        }
        if (placeLocationUpdateDto.getLat() != null) {
            placeLocation.setLat(placeLocationUpdateDto.getLat());
        }
        if (placeLocationUpdateDto.getRadius() != null) {
            placeLocation.setRadius(placeLocationUpdateDto.getRadius());
        }
        return placeLocationMapper.mapToDto(placeLocationRepository.save(placeLocation));
    }

    @Override
    public void deletePlaceLocation(Long locId) {
        placeLocationRepository.deleteById(locId);
    }

    @Override
    public PlaceLocationDto findById(Long locId) {
        return placeLocationMapper.mapToDto(placeLocationRepository.findById(locId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Location with id=%d is not exist", locId))));
    }

    @Override
    public List<PlaceLocationDto> findAll(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return StreamSupport.stream(placeLocationRepository.findAll(pageRequest).spliterator(), false)
                .map(placeLocationMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
