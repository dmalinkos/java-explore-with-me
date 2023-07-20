package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.CategoryIsUseException;
import ru.practicum.ewm.exception.EntityNotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.mapToCategory(categoryDto);
        return categoryMapper.mapToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        if (eventRepository.existsByCategoryId(catId)) {
            throw new CategoryIsUseException(String.format("Category with id=%d in use", catId));
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto patchCategory(CategoryDto categoryDto, Long catId) {
        Category patchedCategory = categoryMapper.mapToCategory(getCategory(catId));
        patchedCategory.setName(categoryDto.getName());
        return categoryMapper.mapToCategoryDto(categoryRepository.save(patchedCategory));
    }

    @Override
    public List<CategoryDto> getCategories(Long from, Long size) {
        return categoryRepository.findAll(PageRequest.of(Math.toIntExact(from / size), Math.toIntExact(size))).stream()
                .map(categoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapToCategoryDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id=%d is not exist", id)));
    }
}
