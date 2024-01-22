package com.online.book.store.controller;

import com.online.book.store.dto.request.UserRequestDto;
import com.online.book.store.dto.response.UserDto;
import com.online.book.store.exception.RegistrationException;
import com.online.book.store.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management", description = "Endpoint for managing authentication")
@RestController
@Validated
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Register a new user")
    public UserDto register(@Valid @RequestBody UserRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

}
