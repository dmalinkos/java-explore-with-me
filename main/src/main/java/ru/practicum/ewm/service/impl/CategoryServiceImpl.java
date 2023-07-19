package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.CategoryIsUseException;
import ru.practicum.ewm.exception.EntityNotFoundException;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        if (eventRepository.existsByCategoryId(catId)) {
            throw new CategoryIsUseException(String.format("Category with id=%d in use", catId));
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public Category patchCategory(CategoryDto categoryDto, Long catId) {
        Category patchedCategory = getCategory(catId);
        patchedCategory.setName(categoryDto.getName());
        return categoryRepository.save(patchedCategory);
    }

    @Override
    public List<Category> getCategories(Long from, Long size) {
        return categoryRepository.findAll(PageRequest.of(Math.toIntExact(from / size), Math.toIntExact(size)));
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id=%d is not exist", id)));
    }
}
