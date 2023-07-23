package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto patchCategory(CategoryDto categoryDto, Long id);

    List<CategoryDto> getCategories(Long from, Long size);

    CategoryDto getCategory(Long id);
}
