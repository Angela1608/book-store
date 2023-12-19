package com.online.book.store.service;

import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.response.BookDto;
import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getBookById(Long id);

    BookDto createBook(BookRequestDto bookDto);

    BookDto deleteBookById(Long id);

    BookDto updateBookById(Long id, BookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParametersDto bookSearchParametersDto);

}
