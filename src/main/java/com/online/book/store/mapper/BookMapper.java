package com.online.book.store.mapper;

import com.online.book.store.config.MapperConfig;
import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.dto.response.BookWithoutCategoriesDto;
import com.online.book.store.model.Book;
import com.online.book.store.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(imports = MapperConfig.class, componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "categoriesIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoriesIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoriesIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoriesIds(categoriesIds);
    }

    BookWithoutCategoriesDto toBookWithoutCategoriesDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toEntity(BookRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categories", ignore = true)
    Book updateBookFromDto(@MappingTarget Book book, BookRequestDto requestDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, BookRequestDto requestDto) {
        Set<Category> categories = requestDto.getCategories().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

}
