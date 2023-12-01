package com.online.book.store.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String author;

    @Column(unique = true)
    private String isbn;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    private String coverImage;

}
