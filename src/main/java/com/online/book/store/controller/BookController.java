package com.online.book.store.controller;

import com.online.book.store.dto.request.CreateBookRequestDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.service.BookService;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
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

}
