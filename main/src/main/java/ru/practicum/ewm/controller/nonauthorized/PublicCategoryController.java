package ru.practicum.ewm.controller.nonauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") Long from,
                                           @RequestParam(name = "size", defaultValue = "10") Long size) {
        return categoryService.getCategories(from, size).stream()
                .map(categoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable(name = "catId") Long catId) {
        return categoryMapper.mapToCategoryDto(categoryService.getCategory(catId));
    }
}
