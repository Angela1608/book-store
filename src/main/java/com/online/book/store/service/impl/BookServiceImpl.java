package com.online.book.store.service.impl;

import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.dto.response.BookWithoutCategoriesDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.BookMapper;
import com.online.book.store.model.Book;
import com.online.book.store.repository.book.BookRepository;
import com.online.book.store.repository.book.BookSpecificationBuilder;
import com.online.book.store.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final String BOOK_NOT_FOUND = "Can't find book by id '%s'";

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        List<BookDto> bookDtos = bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(bookDtos, pageable, bookDtos.size());
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findBookById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(BOOK_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public BookDto createBook(BookRequestDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        var savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookDto updateBookById(Long id, BookRequestDto bookRequestDto) {
        Book book = bookRepository.findBookById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(BOOK_NOT_FOUND, id)));
        Book updatedBook = bookMapper.updateBookFromDto(book, bookRequestDto);
        Book savedBook = bookRepository.save(updatedBook);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public Page<BookWithoutCategoriesDto> getBooksByCategoryId(
            Long categoryId, Pageable pageable) {
        List<BookWithoutCategoriesDto> bookDtos = bookRepository
                .findAllByCategoriesId(categoryId, pageable).stream()
                .map(bookMapper::toBookWithoutCategoriesDto)
                .toList();
        return new PageImpl<>(bookDtos, pageable, bookDtos.size());
    }

}
