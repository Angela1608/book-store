package com.online.book.store.service;

import com.online.book.store.dto.request.CategoryRequestDto;
import com.online.book.store.dto.response.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDto> getAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto create(CategoryRequestDto requestDto);

    CategoryDto update(Long id, CategoryRequestDto requestDto);

    void deleteById(Long id);

}
