package com.online.book.store.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 5)
    private String isbn;

    @DecimalMin(value = "0.0", message = "Price must be a positive number")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotBlank(message = "Description is required")
    @Size(min = 50, max = 100)
    private String description;

    private String coverImage;

}
