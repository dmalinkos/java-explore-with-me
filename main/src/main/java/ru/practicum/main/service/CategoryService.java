package ru.practicum.main.service;

import ru.practicum.main.model.Category;
import ru.practicum.main.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);

    void deleteCategory(Long id);

    Category patchCategory(CategoryDto categoryDto, Long id);

    List<Category> getCategories(Long from, Long size);

    Category getCategory(Long id);
}