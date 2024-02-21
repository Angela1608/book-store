package com.online.book.store.service;

import com.online.book.store.dto.request.UserRegistrationRequestDto;
import com.online.book.store.dto.response.UserRegistrationResponseDto;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);

}
