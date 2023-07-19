package ru.practicum.ewm.service;

import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {
    Compilation createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long comId);

    Compilation updateCompilation(Long comId, UpdateCompilationDto updateCompilationDto);

    List<Compilation> getCompilations(boolean pinned, Long from, Long size);

    Compilation getCompilation(Long comId);
}
