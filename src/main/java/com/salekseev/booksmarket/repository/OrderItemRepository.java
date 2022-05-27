package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.OrderItem;

import java.util.List;

public interface OrderItemRepository {

    void saveAll(long orderId, List<OrderItem> items);

    List<OrderItem> findByOrderId(long orderId);

}
