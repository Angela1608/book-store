package com.online.book.store.repository.book;

import com.online.book.store.dto.request.BookSearchParametersDto;
import com.online.book.store.model.Book;
import com.online.book.store.repository.SpecificationBuilder;
import com.online.book.store.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> spec = Specification.where(null);

        if (bookSearchParametersDto.authors() != null
                && bookSearchParametersDto.authors().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearchParametersDto.authors()));
        }

        if (bookSearchParametersDto.titles() != null
                && bookSearchParametersDto.titles().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearchParametersDto.titles()));
        }

        return spec;
    }

}
