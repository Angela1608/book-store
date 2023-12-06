package com.online.book.store.service;

import com.online.book.store.dto.request.CreateBookRequestDto;
import com.online.book.store.dto.response.BookDto;
import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto bookDto);

    BookDto deleteBookById(Long id);

    BookDto updateBookById(Long id);

}
