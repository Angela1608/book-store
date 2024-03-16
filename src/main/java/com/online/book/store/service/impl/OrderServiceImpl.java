package com.online.book.store.service.impl;

import com.online.book.store.dto.request.PlaceOrderRequestDto;
import com.online.book.store.dto.request.UpdateOrderStatusRequestDto;
import com.online.book.store.dto.response.OrderDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.OrderMapper;
import com.online.book.store.model.CartItem;
import com.online.book.store.model.Order;
import com.online.book.store.model.Order.Status;
import com.online.book.store.model.OrderItem;
import com.online.book.store.model.ShoppingCart;
import com.online.book.store.model.User;
import com.online.book.store.repository.cart.ShoppingCartRepository;
import com.online.book.store.repository.order.OrderItemRepository;
import com.online.book.store.repository.order.OrderRepository;
import com.online.book.store.repository.user.UserRepository;
import com.online.book.store.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String SHOPPING_CART_NOT_FOUND =
            "Can't find shopping cart by user name '%s'";
    private static final String USER_NOT_FOUND =
            "Can't find user by user name '%s'";
    private static final String ORDER_NOT_FOUND =
            "Can't find order by id '%s'";
    private static final String ORDERS_NOT_FOUND =
            "Can't find orders by email '%s'";

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto placeOrder(PlaceOrderRequestDto requestDto, Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();

        User user = getUser(email);
        ShoppingCart shoppingCart = getShoppingCart(email);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.PENDING);
        order.setShippingAddress(requestDto.shippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(calculateTotal(shoppingCart));

        Set<OrderItem> orderItems = getOrderItems(shoppingCart, order);

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public Page<OrderDto> getOrderHistory(Pageable pageable, Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        List<Order> orders = orderRepository.findAllByUser_Email(email)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(ORDERS_NOT_FOUND, email)));

        List<OrderDto> orderDtos = orders.stream().map(orderMapper::toDto).toList();
        return new PageImpl<>(orderDtos, pageable, orderDtos.size());
    }

    @Override
    public OrderDto updateOrderStatus(UpdateOrderStatusRequestDto requestDto, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(ORDER_NOT_FOUND, orderId)));
        order.setStatus(requestDto.status());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    private Set<OrderItem> getOrderItems(ShoppingCart shoppingCart, Order order) {
        Set<OrderItem> orderItems = orderMapper.cartItemsToOrderItems(shoppingCart.getCartItems());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        return orderItems;
    }

    private BigDecimal calculateTotal(ShoppingCart shoppingCart) {
        BigDecimal total = BigDecimal.valueOf(shoppingCart.getCartItems().stream()
                .map(CartItem::getQuantity)
                .reduce(0, (a, b) -> a + b));
        return total;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        USER_NOT_FOUND, email)));
    }

    private ShoppingCart getShoppingCart(String email) {
        return shoppingCartRepository.findByUser_Email(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        SHOPPING_CART_NOT_FOUND, email)));
    }

}
