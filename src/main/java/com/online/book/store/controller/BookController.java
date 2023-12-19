package com.online.book.store.controller;

import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.request.CreateBookRequestDto;
import com.online.book.store.dto.request.UpdateBookRequestDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    public BookDto createBook(@Valid @RequestBody CreateBookRequestDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @DeleteMapping("{id}")
    public BookDto deleteBookById(@PathVariable Long id) {
        return bookService.deleteBookById(id);
    }

    @PutMapping("{id}")
    public BookDto updateBookById(@PathVariable Long id,
                                  @Valid @RequestBody UpdateBookRequestDto bookDto) {
        return bookService.updateBookById(id, bookDto);
    }

    @GetMapping("/search")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

}
