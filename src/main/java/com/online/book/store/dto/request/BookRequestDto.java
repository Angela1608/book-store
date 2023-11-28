package com.online.book.store.dto.request;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public record BookRequestDto(Long id,
                             @NotNull String title,
                             @NotNull String author,
                             String isbn,
                             @NotNull BigDecimal price,
                             String description,
                             String coverImage
) implements Serializable {

}
