package com.online.book.store.service;

import com.online.book.store.model.Book;
import java.util.List;

public interface BookService {

    Book add(Book book);

    List<Book> findAll();

}
