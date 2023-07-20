package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long comId);

    CompilationDto updateCompilation(Long comId, UpdateCompilationDto updateCompilationDto);

    List<CompilationDto> getCompilations(boolean pinned, Long from, Long size);

    CompilationDto getCompilation(Long comId);
}
