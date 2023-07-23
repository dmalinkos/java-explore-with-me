package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.PlaceLocationDto;
import ru.practicum.ewm.model.dto.PlaceLocationUpdateDto;
import ru.practicum.ewm.service.PlaceLocationService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/locations")
public class AdminLocationController {
    private final PlaceLocationService placeLocationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceLocationDto addLocation(@RequestBody @Valid PlaceLocationDto placeLocationDto) {
        return placeLocationService.savePlaceLocation(placeLocationDto);
    }

    @PatchMapping("/{locId}")
    public PlaceLocationDto updateLocation(@PathVariable @PositiveOrZero Long locId,
                                           @RequestBody @Valid PlaceLocationUpdateDto placeLocationUpdateDto) {
        return placeLocationService.updatePlaceLocation(locId, placeLocationUpdateDto);
    }

    @DeleteMapping("/{locId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable @PositiveOrZero Long locId) {
        placeLocationService.deletePlaceLocation(locId);
    }
}
