package com.online.book.store.service;

import com.online.book.store.dto.request.PlaceOrderRequestDto;
import com.online.book.store.dto.request.UpdateOrderStatusRequestDto;
import com.online.book.store.dto.response.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto requestDto, Authentication auth);

    Page<OrderDto> getOrderHistory(Pageable pageable, Authentication auth);

    OrderDto updateOrderStatus(UpdateOrderStatusRequestDto requestDto, Long orderId);

}
