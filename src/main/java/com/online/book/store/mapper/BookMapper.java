package com.online.book.store.mapper;

import com.online.book.store.config.MapperConfig;
import com.online.book.store.dto.request.BookRequestDto;
import com.online.book.store.dto.response.BookDto;
import com.online.book.store.model.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(imports = MapperConfig.class, componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book updateBookFromDto(BookRequestDto requestDto, @MappingTarget Book book);

}
