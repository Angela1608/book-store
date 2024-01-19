package com.online.book.store.mapper;

import com.online.book.store.dto.response.UserDto;
import com.online.book.store.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(imports = MapperConfig.class, componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

}
