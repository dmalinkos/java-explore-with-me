package ru.practicum.ewm.service;

import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);

    void deleteCategory(Long id);

    Category patchCategory(CategoryDto categoryDto, Long id);

    List<Category> getCategories(Long from, Long size);

    Category getCategory(Long id);
}
