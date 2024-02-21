package com.online.book.store.service.impl;

import com.online.book.store.dto.request.CartItemQuantityRequestDto;
import com.online.book.store.dto.request.CartItemRequestDto;
import com.online.book.store.dto.response.CartItemDto;
import com.online.book.store.dto.response.ShoppingCartDto;
import com.online.book.store.exception.EntityNotFoundException;
import com.online.book.store.mapper.ShoppingCartMapper;
import com.online.book.store.model.Book;
import com.online.book.store.model.CartItem;
import com.online.book.store.model.ShoppingCart;
import com.online.book.store.model.User;
import com.online.book.store.repository.book.BookRepository;
import com.online.book.store.repository.cart.CartItemRepository;
import com.online.book.store.repository.cart.ShoppingCartRepository;
import com.online.book.store.repository.user.UserRepository;
import com.online.book.store.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final String CART_ITEM_NOT_FOUND = "Can't find cart item by id '%s'";
    private static final String BOOK_NOT_FOUND = "Can't find book by id '%s'";
    private static final String SHOPPING_CART_NOT_FOUND =
            "Can't find shopping cart by user name '%s'";

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public void registerShoppingCart(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User is not found"));
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);

    }

    @Override
    public ShoppingCartDto get(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserName(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        SHOPPING_CART_NOT_FOUND, email)));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto addCartItemToShoppingCart(Authentication auth,
                                                 CartItemRequestDto requestDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserName(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        SHOPPING_CART_NOT_FOUND, email)));

        Book book = bookRepository.findBookById(requestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        BOOK_NOT_FOUND, requestDto.bookId())));

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(requestDto.quantity());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(savedCartItem);
    }

    @Override
    @Transactional
    public CartItemDto updateBookQuantity(Long cartItemId, CartItemQuantityRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CART_ITEM_NOT_FOUND, cartItemId)));
        cartItem.setQuantity(requestDto.quantity());
        return shoppingCartMapper.toDto(cartItem);
    }

    @Override
    @Transactional
    public void delete(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        CART_ITEM_NOT_FOUND, cartItemId)));

        cartItemRepository.delete(cartItem);
    }

}
