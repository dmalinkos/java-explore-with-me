package ru.practicum.ewm.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.model.dto.PlaceLocationDto;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.PlaceLocationService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/locations")
@RequiredArgsConstructor
public class PrivateLocationController {
    private final PlaceLocationService placeLocationService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @GetMapping("/events/{locId}")
    public List<EventFullDto> getEventsInPlaceLocation(@PathVariable @PositiveOrZero Long locId) {
        PlaceLocationDto placeLocationDto = placeLocationService.findById(locId);
        Float lat = placeLocationDto.getLat();
        Float lon = placeLocationDto.getLon();
        Float radius = placeLocationDto.getRadius();
        return eventRepository.getEventsInRadius(lat, lon, radius).stream()
                .map(eventMapper::mapToFullDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/events")
    public List<EventFullDto> getEventsInRadius(@RequestParam Float lat,
                                                @RequestParam Float lon,
                                                @RequestParam Float radius) {
        return eventRepository.getEventsInRadius(lat, lon, radius).stream()
                .map(eventMapper::mapToFullDto)
                .collect(Collectors.toList());
    }
}
