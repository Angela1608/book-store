package com.online.book.store.mapper;

import com.online.book.store.config.MapperConfig;
import com.online.book.store.dto.request.CreateBookRequestDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.model.Book;
import org.mapstruct.Mapper;

@Mapper(imports = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toEntity(CreateBookRequestDto requestDto);

}