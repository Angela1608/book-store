package com.online.book.store.service.impl;

import com.online.book.store.dto.request.UserRegistrationRequestDto;
import com.online.book.store.dto.response.UserDto;
import com.online.book.store.exception.RegistrationException;
import com.online.book.store.mapper.UserMapper;
import com.online.book.store.model.User;
import com.online.book.store.repository.user.UserRepository;
import com.online.book.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User is already registered");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

}
