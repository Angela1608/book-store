package com.online.book.store.dto.response;

public record CartItemDto(Long id,
                          Long bookId,
                          String bookTitle,
                          int quantity) {

}
