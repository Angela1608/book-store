package com.online.book.store.service;

import com.online.book.store.dto.response.OrderItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    Page<OrderItemDto> getOrderItems(Pageable pageable, Long orderId);

    OrderItemDto getOrderItem(Long orderId, Long itemId);

}
