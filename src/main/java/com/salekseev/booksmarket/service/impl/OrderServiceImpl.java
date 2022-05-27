package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Order;
import com.salekseev.booksmarket.model.OrderItem;
import com.salekseev.booksmarket.repository.OrderItemRepository;
import com.salekseev.booksmarket.repository.OrderRepository;
import com.salekseev.booksmarket.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public long addOrder(Order order) {
        long orderId = orderRepository.save(order);
        orderItemRepository.saveAll(orderId, order.getItems());

        return orderId;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        orders.forEach(order -> {
            long orderId = order.getId();
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
            order.setItems(orderItems);
        });

        return orders;
    }
}
