package com.online.book.store.dto.response;

import java.math.BigDecimal;

public record BookWithoutCategoriesDto(Long id,
                                       String title,
                                       String author,
                                       String isbn,
                                       BigDecimal price,
                                       String description,
                                       String coverImage) {

}
