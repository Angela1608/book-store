package com.online.book.store.service.impl;

import com.online.book.store.config.SecurityConfig;
import com.online.book.store.dto.request.UserRequestDto;
import com.online.book.store.dto.response.UserDto;
import com.online.book.store.exception.RegistrationException;
import com.online.book.store.mapper.UserMapper;
import com.online.book.store.model.Role;
import com.online.book.store.model.User;
import com.online.book.store.repository.user.UserRepository;
import com.online.book.store.service.RoleService;
import com.online.book.store.service.UserService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final SecurityConfig config;

    private final RoleService roleService;

    @Override
    public UserDto register(UserRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User is already registered");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(config.getPasswordEncoder().encode(requestDto.getPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        Set<Role> roles = roleService
                .findAllByRoleNames(requestDto.getRoles()
                                .stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toSet()));
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

}
