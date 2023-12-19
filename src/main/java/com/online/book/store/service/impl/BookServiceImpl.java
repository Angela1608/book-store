package com.online.book.store.service.impl;

import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.BookMapper;
import com.online.book.store.model.Book;
import com.online.book.store.repository.book.BookRepository;
import com.online.book.store.repository.book.BookSpecificationBuilder;
import com.online.book.store.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        String msg = String.format("Can't find book by id '%s'", id);
        return bookRepository.findBookById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(msg));
    }

    @Override
    public BookDto createBook(BookRequestDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto deleteBookById(Long id) {
        String msg = String.format("Can't find book by id '%s'", id);
        return bookRepository.findBookById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(msg));
    }

    @Override
    @Transactional
    public BookDto updateBookById(Long id, BookRequestDto bookRequestDto) {
        String msg = String.format("Can't find book by id '%s'", id);
        Book book = bookRepository.findBookById(id)
                .orElseThrow(() -> new EntityNotFoundException(msg));
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPrice(bookRequestDto.getPrice());
        book.setDescription(bookRequestDto.getDescription());
        book.setCoverImage(bookRequestDto.getCoverImage());
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

}
