package com.online.book.store.service.impl;

import com.online.book.store.dto.request.CreateBookRequestDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.BookMapper;
import com.online.book.store.model.Book;
import com.online.book.store.repository.BookRepository;
import com.online.book.store.service.BookService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        String msg = String.format("Can't find book by id '%s'", id);
        Optional<Book> optionalBook = bookRepository.findBookById(id);
        Book book = optionalBook.orElseThrow(() -> new EntityNotFoundException(msg));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto createBook(CreateBookRequestDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }
}
