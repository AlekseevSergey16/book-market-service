package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    long save(Order order);

    List<Order> findAll();
    List<Order> findByUserId(long userId);
    Optional<Order> findById(long orderId);

}
