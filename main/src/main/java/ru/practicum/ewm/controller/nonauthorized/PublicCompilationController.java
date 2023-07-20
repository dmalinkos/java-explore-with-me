package ru.practicum.ewm.controller.nonauthorized;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", defaultValue = "false") Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0") Long from,
                                                @RequestParam(name = "size", defaultValue = "10") Long size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{comId}")
    public CompilationDto getCompilation(@PathVariable(name = "comId") Long comId) {
        return compilationService.getCompilation(comId);
    }
}