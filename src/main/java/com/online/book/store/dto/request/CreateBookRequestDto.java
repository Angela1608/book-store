package com.online.book.store.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotBlank
    private String coverImage;

}

