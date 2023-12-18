package com.online.book.store.repository;

import com.online.book.store.dto.request.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {

    Specification<T> build(BookSearchParametersDto bookSearchParametersDto);

}
