package com.online.book.store.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PlaceOrderRequestDto(@NotBlank(
        message = "Shipping address is required") String shippingAddress) {

}
