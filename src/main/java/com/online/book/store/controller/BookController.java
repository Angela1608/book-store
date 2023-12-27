package com.online.book.store.controller;

import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.service.BookService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RestController
@Validated
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    @Operation(summary = "Get all books", description = "Get a list of all available books")
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a book by id", description = "Get a book by it's id")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    @Operation(summary = "Create a book", description = "Create a new book")
    public BookDto createBook(@Valid @RequestBody BookRequestDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a book by id", description = "Delete a book by it's id")
    public BookDto deleteBookById(@PathVariable Long id) {
        return bookService.deleteBookById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a book by id", description = "Update a book by it's id")
    public BookDto updateBookById(@PathVariable Long id,
                                  @Valid @RequestBody BookRequestDto bookDto) {
        return bookService.updateBookById(id, bookDto);
    }

    @GetMapping("/search")
    @Operation(summary = "Get books by parameters", description = "Get books by certain parameters")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

}
