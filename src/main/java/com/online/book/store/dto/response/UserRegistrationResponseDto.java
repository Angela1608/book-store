package com.online.book.store.dto.response;

public record UserRegistrationResponseDto(Long id,
                                          String email,
                                          String firstName,
                                          String lastName,
                                          String shippingAddress) {
}
