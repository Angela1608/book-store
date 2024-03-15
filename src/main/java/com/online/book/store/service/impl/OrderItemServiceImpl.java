package com.online.book.store.service.impl;

import com.online.book.store.dto.response.OrderItemDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.OrderMapper;
import com.online.book.store.model.Order;
import com.online.book.store.model.OrderItem;
import com.online.book.store.repository.order.OrderItemRepository;
import com.online.book.store.repository.order.OrderRepository;
import com.online.book.store.service.OrderItemService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private static final String ORDER_NOT_FOUND =
            "Can't find order by id '%s'";

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderItemDto> getOrderItems(Pageable pageable, Long orderId) {
        Page<OrderItem> orderItemPage = orderItemRepository.findAllByOrder_Id(pageable, orderId);
        List<OrderItemDto> orderItemDtos = orderItemPage.getContent().stream()
                .map(orderMapper::toOrderItemDto)
                .toList();
        return new PageImpl<>(orderItemDtos, pageable, orderItemPage.getTotalElements());
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(ORDER_NOT_FOUND, orderId)));

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> Objects.equals(item.getId(), itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "OrderItem with id %d not found in order %d", itemId, orderId)));

        return orderMapper.toOrderItemDto(orderItem);
    }

}
