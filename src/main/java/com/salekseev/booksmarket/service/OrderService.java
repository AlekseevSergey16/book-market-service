package com.salekseev.booksmarket.service;

import com.salekseev.booksmarket.model.Order;

import java.util.List;

public interface OrderService {

    long addOrder(Order order);

    List<Order> getAllOrders();

}
