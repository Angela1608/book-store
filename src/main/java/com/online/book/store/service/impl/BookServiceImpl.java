package com.online.book.store.service.impl;

import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.BookMapper;
import com.online.book.store.mapper.CategoryMapper;
import com.online.book.store.model.Book;
import com.online.book.store.model.Category;
import com.online.book.store.repository.book.BookRepository;
import com.online.book.store.repository.book.BookSpecificationBuilder;
import com.online.book.store.repository.category.CategoryRepository;
import com.online.book.store.service.BookService;
import java.util.List;
import java.util.Set;
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

    private final CategoryRepository categoryRepository;

    private final BookMapper bookMapper;

    private final CategoryMapper categoryMapper;

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
        var book = bookMapper.toEntity(bookDto);
        Set<Category> categories = bookDto.getCategories().stream()
                .map(categoryMapper::toEntity)
                .map(categoryRepository::save)
                .collect(Collectors.toSet());
        book.setCategories(categories);
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
        var book = bookRepository.findBookById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(BOOK_NOT_FOUND, id)));
        var updatedBook = bookMapper.updateBookFromDto(bookRequestDto, book);
        var savedBook = bookRepository.save(updatedBook);
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
    public Page<BookDto> getBooksByCategoryId(
            Long categoryId, Pageable pageable) {
        List<BookDto> bookDtos = bookRepository
                .findAllByCategoriesId(categoryId, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
        return new PageImpl<>(bookDtos, pageable, bookDtos.size());
    }

}
