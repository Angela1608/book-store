package com.online.book.store.dto.request;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartRequestDto {

    private Set<CartItemRequestDto> cartItems;

}
