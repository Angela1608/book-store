package com.online.book.store.dto.response;

public record OrderItemDto(Long id,
                           Long bookId,
                           int quantity) {

}
