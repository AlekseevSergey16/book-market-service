package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Order;

import java.util.List;

public interface OrderRepository {

    long save(Order order);

    List<Order> findAll();

}
