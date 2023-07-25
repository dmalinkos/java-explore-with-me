package ru.practicum.ewm.controller.nonauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.PlaceLocationDto;
import ru.practicum.ewm.service.PlaceLocationService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/locations")
public class PublicLocationController {
    private final PlaceLocationService placeLocationService;

    @GetMapping("/{locId}")
    public PlaceLocationDto getLocation(@PathVariable @PositiveOrZero Long locId) {
        return placeLocationService.findById(locId);
    }

    @GetMapping
    public List<PlaceLocationDto> getLocations(@RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        return placeLocationService.findAll(from, size);
    }
}
