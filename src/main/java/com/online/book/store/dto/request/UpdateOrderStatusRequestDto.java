package com.online.book.store.dto.request;

import com.online.book.store.model.Order.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequestDto(@NotNull Status status) {

}
