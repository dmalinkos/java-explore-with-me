package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapToCategoryDto(Category category);

    Category mapToCategory(CategoryDto categoryDto);
}
