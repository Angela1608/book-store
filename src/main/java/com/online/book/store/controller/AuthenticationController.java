package com.online.book.store.controller;

import com.online.book.store.dto.request.UserLoginRequestDto;
import com.online.book.store.dto.request.UserRegistrationRequestDto;
import com.online.book.store.dto.response.UserLoginResponseDto;
import com.online.book.store.dto.response.UserRegistrationResponseDto;
import com.online.book.store.exception.RegistrationException;
import com.online.book.store.security.AuthenticationService;
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
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Registers a new user")
    public UserRegistrationResponseDto register(
            @Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates an existing user")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

}
