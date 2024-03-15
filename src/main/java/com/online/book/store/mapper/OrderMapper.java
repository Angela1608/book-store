package com.online.book.store.mapper;

import com.online.book.store.config.MapperConfig;
import com.online.book.store.dto.response.OrderDto;
import com.online.book.store.dto.response.OrderItemDto;
import com.online.book.store.model.CartItem;
import com.online.book.store.model.Order;
import com.online.book.store.model.OrderItem;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(imports = MapperConfig.class, componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItemIds", source = ".", qualifiedByName = "setOrderItemIds")
    OrderDto toDto(Order order);

    @Named("setOrderItemIds")
    default Set<Long> setOrderItemIds(Order order) {
        return order.getOrderItems().stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "price", expression = "java(calculatePrice(cartItem))")
    OrderItem cartItemToOrderItem(CartItem cartItem);

    Set<OrderItem> cartItemsToOrderItems(Set<CartItem> cartItem);

    default BigDecimal calculatePrice(CartItem cartItem) {
        return cartItem.getBook().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
    }

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

}
