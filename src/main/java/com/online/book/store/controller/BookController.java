package com.online.book.store.controller;

import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

@Tag(name = "Book management", description = "Endpoints for managing books")
@RestController
@Validated
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    @Operation(summary = "Get all books(paged)", description = "Returns a list of books")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public Page<BookDto> getAll(
            @PageableDefault(size = 20, sort = {"price"}, direction = Direction.DESC
            ) Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a book by id", description = "Returns a book by it's id")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    @Operation(summary = "Create a book", description = "Creates a new book")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public BookDto createBook(@Valid @RequestBody BookRequestDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a book by id", description = "Deletes a book by it's id")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a book by id", description = "Updates a book by it's id")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public BookDto updateBookById(@PathVariable Long id,
                                  @Valid @RequestBody BookRequestDto bookDto) {
        return bookService.updateBookById(id, bookDto);
    }

    @GetMapping("/search")
    @Operation(summary = "Get books by parameters",
            description = "Returns books by certain parameters")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

}
