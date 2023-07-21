package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.UpdateCompilationDto;
import ru.practicum.ewm.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("POST /admin/compilations newCompilationDto: {}", newCompilationDto);
        return compilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable(name = "comId") @PositiveOrZero Long comId) {
        log.info("DELETE /admin/compilations/{} ", comId);
        compilationService.deleteCompilation(comId);
    }

    @PatchMapping("/{comId}")
    public CompilationDto updateCompilation(@PathVariable(name = "comId") @PositiveOrZero Long comId,
                                            @Valid @RequestBody UpdateCompilationDto compilationDto) {
        log.info("POST /admin/compilations/{} compilationDto: {}", comId, compilationDto);
        return compilationService.updateCompilation(comId, compilationDto);
    }
}
