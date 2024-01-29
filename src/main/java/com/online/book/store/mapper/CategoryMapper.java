package com.online.book.store.mapper;

import com.online.book.store.config.MapperConfig;
import com.online.book.store.dto.request.CategoryRequestDto;
import com.online.book.store.dto.response.CategoryDto;
import com.online.book.store.model.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(imports = MapperConfig.class, componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category updateCategoryFromDto(CategoryRequestDto requestDto, @MappingTarget Category category);

}
