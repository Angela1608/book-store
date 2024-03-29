package com.online.book.store.service;

import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.dto.response.BookWithoutCategoriesDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> getAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto createBook(BookRequestDto bookDto);

    void deleteBookById(Long id);

    BookDto updateBookById(Long id, BookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParametersDto bookSearchParametersDto);

    Page<BookWithoutCategoriesDto> getBooksByCategoryId(Long categoryId, Pageable pageable);

}
