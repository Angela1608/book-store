package com.online.book.store.service.impl;

import com.online.book.store.dto.request.CategoryRequestDto;
import com.online.book.store.dto.response.CategoryDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.CategoryMapper;
import com.online.book.store.model.Category;
import com.online.book.store.repository.category.CategoryRepository;
import com.online.book.store.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY_NOT_FOUND = "Can't find category by id '%s'";

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> getAll(Pageable pageable) {
        List<CategoryDto> categoryDtos = categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(categoryDtos, pageable, categoryDtos.size());
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CATEGORY_NOT_FOUND, id)));
    }

    @Override
    public CategoryDto create(CategoryRequestDto requestDto) {
        Category category = categoryMapper.toEntity(requestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CATEGORY_NOT_FOUND, id)));
        Category updatedCategory = categoryMapper.updateCategoryFromDto(requestDto, category);
        Category savedCategory = categoryRepository.save(updatedCategory);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CATEGORY_NOT_FOUND, id)));
        categoryRepository.deleteById(id);
    }

}
