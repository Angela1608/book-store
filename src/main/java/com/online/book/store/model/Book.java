package com.online.book.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
public class Book {

    @Id
    private Long id;

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
