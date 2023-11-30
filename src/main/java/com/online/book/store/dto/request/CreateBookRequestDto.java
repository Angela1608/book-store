package com.online.book.store.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotNull
    private String title;

    @NotNull
    private String author;

    @Column(unique = true)
    private String isbn;

    @NotNull
    private BigDecimal price;

    private String description;

    private String coverImage;

}
