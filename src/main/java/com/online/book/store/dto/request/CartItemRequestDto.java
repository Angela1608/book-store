package com.online.book.store.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemRequestDto(@NotNull Long bookId, @NotNull @Min(1) int quantity) {

}
