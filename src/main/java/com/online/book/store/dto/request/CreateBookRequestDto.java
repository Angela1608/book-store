package com.online.book.store.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @DecimalMin(value = "0.0", message = "Price must be a positive number")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotBlank(message = "Description is required")
    private String description;

    private String coverImage;

}
