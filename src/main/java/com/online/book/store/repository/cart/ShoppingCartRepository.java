package com.online.book.store.repository.cart;

import com.online.book.store.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser_Email(String userName);

}
