package ru.practicum.ewm.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.EntityNotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.QCompilation;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.UpdateCompilationDto;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CompilationService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    public Compilation createCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = new ArrayList<>(0);
        if (newCompilationDto.getEvents() != null) {
            events = StreamSupport.stream(eventRepository.findAllById(newCompilationDto.getEvents()).spliterator(), false)
                    .collect(Collectors.toList());
        }
        Set<Event> eventsSet = new HashSet<>(events);
        return compilationRepository.save(compilationMapper.mapToCompilation(newCompilationDto, eventsSet));
    }

    @Override
    public void deleteCompilation(Long comId) {
        compilationRepository.deleteById(comId);
    }

    @Override
    public Compilation updateCompilation(Long comId, UpdateCompilationDto updateCompilationDto) {
        Compilation updatedCompilation = getCompilation(comId);
        List<Event> events = new ArrayList<>(0);
        if (updateCompilationDto.getEvents() != null) {
            events = StreamSupport.stream(eventRepository.findAllById(updateCompilationDto.getEvents()).spliterator(), false)
                    .collect(Collectors.toList());
        }
        Set<Event> eventsSet = new HashSet<>(events);
        Compilation patchCompilation = compilationMapper.mapToCompilation(updateCompilationDto, eventsSet);
        if (patchCompilation.getPinned() != null) {
            updatedCompilation.setPinned(patchCompilation.getPinned());
        }
        if (patchCompilation.getTitle() != null) {
            updatedCompilation.setTitle(patchCompilation.getTitle());
        }
        if (updateCompilationDto.getEvents() != null) {
            updatedCompilation.setEvents(patchCompilation.getEvents());
        }
        return compilationRepository.save(updatedCompilation);
    }

    @Override
    public List<Compilation> getCompilations(boolean pinned, Long from, Long size) {
        QCompilation qComp = QCompilation.compilation;
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(from / size), Math.toIntExact(size));
        BooleanExpression expression = Expressions.asBoolean(true).eq(true);
        if (pinned) {
            expression = expression.and(qComp.pinned.eq(true));
        }
        return compilationRepository.findAll(expression, pageRequest).getContent();
    }

    @Override
    public Compilation getCompilation(Long comId) {
        return compilationRepository.findById(comId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Compilation with id=%d was not found", comId)));
    }
}
