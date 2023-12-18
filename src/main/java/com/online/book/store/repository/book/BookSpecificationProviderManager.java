package com.online.book.store.repository.book;

import com.online.book.store.model.Book;
import com.online.book.store.repository.SpecificationProvider;
import com.online.book.store.repository.SpecificationProviderManager;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final Set<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findAny()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find correct specification for key " + key));
    }

}
