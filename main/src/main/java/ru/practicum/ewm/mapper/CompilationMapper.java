package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.QCompilation;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.UpdateCompilationDto;
import ru.practicum.ewm.repository.EventRepository;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Event.class, QCompilation.class})
public abstract class CompilationMapper {
    @Autowired
    protected EventRepository eventRepository;
    @Autowired
    protected EventMapper eventMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    public abstract Compilation mapToCompilation(NewCompilationDto compilationDto, Set<Event> events);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    public abstract Compilation mapToCompilation(UpdateCompilationDto updateCompilationDto, Set<Event> events);

    @Mapping(target = "events", expression = "java(compilation.getEvents().stream().map(eventMapper::mapToShortDto).collect(java.util.stream.Collectors.toSet()))")
    public abstract CompilationDto mapToDto(Compilation compilation);

    public abstract Compilation mapToCompilation(CompilationDto compilationDto);
}
