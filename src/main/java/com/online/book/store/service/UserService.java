package com.online.book.store.service;

import com.online.book.store.dto.request.UserRequestDto;
import com.online.book.store.dto.response.UserDto;

public interface UserService {

    UserDto register(UserRequestDto requestDto);
}
