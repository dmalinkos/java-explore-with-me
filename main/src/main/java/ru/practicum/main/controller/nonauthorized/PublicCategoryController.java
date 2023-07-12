package ru.practicum.main.controller.nonauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.mapper.CategoryMapper;
import ru.practicum.main.model.dto.CategoryDto;
import ru.practicum.main.service.CategoryService;

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
        log.info("GET /categories&from={}&size={}", from, size);
        return categoryService.getCategories(from, size).stream()
                .map(categoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        log.info("GET /categories/{}", catId);
        return categoryMapper.mapToCategoryDto(categoryService.getCategory(catId));
    }
}
