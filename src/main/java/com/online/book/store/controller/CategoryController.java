package com.online.book.store.controller;

import com.online.book.store.dto.request.CategoryRequestDto;
import com.online.book.store.dto.response.BookWithoutCategoriesDto;
import com.online.book.store.dto.response.CategoryDto;
import com.online.book.store.service.BookService;
import com.online.book.store.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Endpoints for managing book categories")
@RestController
@Validated
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all categories(paged)",
            description = "Returns a list of categories ")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public Page<CategoryDto> getAll(Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id", description = "Returns category by it's id")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a category", description = "Creates a new category")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public CategoryDto createCategory(@Valid @RequestBody CategoryRequestDto requestDto) {
        return categoryService.create(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category by id", description = "Updates a category by it's id")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @Valid @RequestBody CategoryRequestDto requestDto) {
        return categoryService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by id", description = "Deletes a category by it's id")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);

    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Get books by category(paged)",
            description = "Returns a list of books by specific category")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public Page<BookWithoutCategoriesDto> getBooksByCategoryId(
            @PathVariable Long id, Pageable pageable) {
        return bookService.getBooksByCategoryId(id, pageable);
    }

}
