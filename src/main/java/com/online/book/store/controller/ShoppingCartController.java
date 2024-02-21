package com.online.book.store.controller;

import com.online.book.store.dto.request.CartItemQuantityRequestDto;
import com.online.book.store.dto.request.CartItemRequestDto;
import com.online.book.store.dto.response.CartItemDto;
import com.online.book.store.dto.response.ShoppingCartDto;
import com.online.book.store.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RestController
@Validated
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/register")
    @Operation(summary = "Create a shopping cart", description = "Creates a new shopping cart")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void registerShoppingCart(Authentication auth) {
        shoppingCartService.registerShoppingCart(auth);
    }

    @GetMapping
    @Operation(summary = "Get shopping cart",
            description = "Retrieves user's shopping cart")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public ShoppingCartDto getShoppingCart(Authentication auth) {
        return shoppingCartService.get(auth);
    }

    @PostMapping()
    @Operation(summary = "Add book to the shopping cart",
            description = "Adds book to the existing shopping cart")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public CartItemDto addCartItem(
            Authentication auth, @Valid @RequestBody CartItemRequestDto requestDto) {
        return shoppingCartService.addCartItemToShoppingCart(auth, requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "update book quantity in the shopping cart",
            description = "Updates quantity of books in the shopping cart")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public CartItemDto updateBookQuantity(
            @PathVariable("cartItemId") Long cartItemId,
            @Valid @RequestBody CartItemQuantityRequestDto requestDto) {
        return shoppingCartService.updateBookQuantity(cartItemId, requestDto);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "delete book from shopping cart",
            description = "Deletes a book from the shopping cart")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("cartItemId") Long cartItemId) {
        shoppingCartService.delete(cartItemId);
    }

}
