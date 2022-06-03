package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Order;
import com.salekseev.booksmarket.model.OrderItem;
import com.salekseev.booksmarket.repository.AuthorRepository;
import com.salekseev.booksmarket.repository.BookRepository;
import com.salekseev.booksmarket.repository.OrderItemRepository;
import com.salekseev.booksmarket.repository.OrderRepository;
import com.salekseev.booksmarket.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                     BookRepository bookRepository, AuthorRepository authorRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public long addOrder(Order order) {
        if (order.getItems().isEmpty()) {
            throw new RuntimeException("Не указаны книги");
        }
        long orderId = orderRepository.save(order);
        orderItemRepository.saveAll(orderId, order.getItems());

        order.getItems().forEach(item -> {
            if (item.getQuantity() > item.getBook().getAmount()) {
                throw new RuntimeException("Превышено количество книг на складе");
            }
            bookRepository.reduceAmount(item.getBook().getId(), item.getQuantity());
        });

        return orderId;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        orders.forEach(order -> {
            long orderId = order.getId();
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
            order.setItems(orderItems);
            for (OrderItem orderItem : order.getItems()) {
                long bookId = orderItem.getBook().getId();
                orderItem.getBook().setAuthors(authorRepository.findByBookId(bookId));
            }
        });

        return orders;
    }
}
