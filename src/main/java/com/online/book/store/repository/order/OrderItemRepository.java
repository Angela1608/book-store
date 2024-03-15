package com.online.book.store.repository.order;

import com.online.book.store.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findAllByOrder_Id(Pageable pageable, Long orderId);

}
