package com.online.book.store.service.impl;

import com.online.book.store.dto.request.UserRegistrationRequestDto;
import com.online.book.store.dto.response.UserRegistrationResponseDto;
import com.online.book.store.exception.RegistrationException;
import com.online.book.store.mapper.UserMapper;
import com.online.book.store.model.Role;
import com.online.book.store.model.ShoppingCart;
import com.online.book.store.model.User;
import com.online.book.store.repository.cart.ShoppingCartRepository;
import com.online.book.store.repository.user.UserRepository;
import com.online.book.store.service.RoleService;
import com.online.book.store.service.UserService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User is already registered");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        Set<Role> roles = roleService
                .findAllByRoleNames(requestDto.getRoles()
                                .stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toSet()));
        user.setRoles(roles);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

}
