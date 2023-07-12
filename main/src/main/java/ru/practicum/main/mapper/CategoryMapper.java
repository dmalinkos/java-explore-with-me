package ru.practicum.main.mapper;

import org.mapstruct.Mapper;
import ru.practicum.main.model.Category;
import ru.practicum.main.model.dto.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapToCategoryDto(Category category);

    Category mapToCategory(CategoryDto categoryDto);
}
