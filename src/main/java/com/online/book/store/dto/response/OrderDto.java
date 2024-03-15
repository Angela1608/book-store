package com.online.book.store.dto.response;

import java.util.Set;

public record OrderDto(Long id,
                       Long userId,
                       Set<Long> orderItemIds) {

}
