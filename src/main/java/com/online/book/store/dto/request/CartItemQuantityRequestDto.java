package com.online.book.store.dto.request;

import jakarta.validation.constraints.NotNull;

public record CartItemQuantityRequestDto(@NotNull int quantity) {

}
