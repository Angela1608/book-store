package com.online.book.store.repository.order;

import com.online.book.store.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findAllByUser_Email(String email);

}
