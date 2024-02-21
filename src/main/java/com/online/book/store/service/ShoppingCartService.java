package com.online.book.store.service;

import com.online.book.store.dto.request.CartItemQuantityRequestDto;
import com.online.book.store.dto.request.CartItemRequestDto;
import com.online.book.store.dto.response.CartItemDto;
import com.online.book.store.dto.response.ShoppingCartDto;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    ShoppingCartDto get(Authentication auth);

    CartItemDto addCartItemToShoppingCart(Authentication auth, CartItemRequestDto requestDto);

    CartItemDto updateBookQuantity(Long cartItemId, CartItemQuantityRequestDto requestDto);

    void delete(Long cartItemId);

}
