package com.online.book.store.controller;

import com.online.book.store.dto.request.PlaceOrderRequestDto;
import com.online.book.store.dto.request.UpdateOrderStatusRequestDto;
import com.online.book.store.dto.response.OrderDto;
import com.online.book.store.dto.response.OrderItemDto;
import com.online.book.store.service.OrderItemService;
import com.online.book.store.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@Validated
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping()
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @Operation(summary = "Place an order", description = "Place a new order")
    public OrderDto placeOrder(@Valid @RequestBody PlaceOrderRequestDto requestDto,
                               Authentication auth) {
        return orderService.placeOrder(requestDto, auth);
    }

    @GetMapping
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @Operation(summary = "Get order history",
            description = "Retrieves order history to track past purchases")
    public Page<OrderDto> getOrderHistory(Pageable pageable, Authentication auth) {
        return orderService.getOrderHistory(pageable, auth);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @Operation(summary = "Get order items for a specific order.",
            description = "Retrieves all OrderItems for a specific order")
    public Page<OrderItemDto> getOrderItems(Pageable pageable,
                                             @PathVariable Long orderId) {
        return orderItemService.getOrderItems(pageable, orderId);
    }

    @GetMapping("{orderId}/items/{id}")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @Operation(summary = "Get a specific OrderItem within an order",
            description = "Retrieves a specific OrderItem within an order")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long id) {
        return orderItemService.getOrderItem(orderId, id);
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public OrderDto updateOrderStatus(@Valid @RequestBody UpdateOrderStatusRequestDto requestDto,
                                      @PathVariable Long id) {
        return orderService.updateOrderStatus(requestDto, id);
    }

}
