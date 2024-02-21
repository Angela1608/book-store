package com.online.book.store.mapper;

import com.online.book.store.config.MapperConfig;
import com.online.book.store.dto.response.CartItemDto;
import com.online.book.store.dto.response.ShoppingCartDto;
import com.online.book.store.model.CartItem;
import com.online.book.store.model.ShoppingCart;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(imports = MapperConfig.class, componentModel = "spring")
public interface ShoppingCartMapper {

    @Mapping(source = "cartItem.book.id", target = "bookId")
    @Mapping(source = "cartItem.book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItemsIds", source = ".", qualifiedByName = "setCartItemsIds")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Named("setCartItemsIds")
    default Set<Long> setCartItemsIds(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet());
    }

}
