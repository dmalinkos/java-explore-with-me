package ru.practicum.main.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.mapper.CategoryMapper;
import ru.practicum.main.model.dto.CategoryDto;
import ru.practicum.main.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("POST /admin/categories categoryDto: {}", categoryDto);
        return categoryMapper.mapToCategoryDto(categoryService.addCategory(categoryMapper.mapToCategory(categoryDto)));
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.info("DELETE /admin/categories/{catId} catId: {}", catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@Valid @RequestBody CategoryDto categoryDto,
                                     @PathVariable Long catId) {
        log.info("PATCH /admin/categories/{catId} categoryDto: {}, catId: {}", categoryDto, catId);
        return categoryMapper.mapToCategoryDto(categoryService.patchCategory(categoryDto, catId));
    }
}
