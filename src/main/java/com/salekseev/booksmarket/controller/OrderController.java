package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.model.Order;
import com.salekseev.booksmarket.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public long createOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders(@RequestParam Optional<Long> userId) {
        if (userId.isPresent()) {
            return orderService.getOrdersByUserId(userId.get());
        }

        return orderService.getAllOrders();
    }

}
