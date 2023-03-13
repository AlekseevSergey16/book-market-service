package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Author;
import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.model.Order;
import com.salekseev.booksmarket.model.OrderItem;
import com.salekseev.booksmarket.rabbit.RabbitSender;
import com.salekseev.booksmarket.rabbit.event.OrderCreatedMessage;
import com.salekseev.booksmarket.repository.*;
import com.salekseev.booksmarket.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RabbitSender rabbitSender;
    private final UserRepository userRepository;

    OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                     BookRepository bookRepository, AuthorRepository authorRepository, RabbitSender rabbitSender,
                     UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.rabbitSender = rabbitSender;
        this.userRepository = userRepository;
    }

    @Override
    public long addOrder(Order order) {
        if (order.getItems().isEmpty()) {
            throw new RuntimeException("Не указаны книги");
        }

        long orderId = storeOrder(order);
        Long userId = order.getUserId();

        if (userId != null) {
            sendOrderCreatedMessageForUser(orderId, userId);
        }

        return orderId;
    }

    @Transactional
    protected long storeOrder(Order order) {
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

    private void sendOrderCreatedMessageForUser(long orderId, long userId) {
        var executorService = Executors.newFixedThreadPool(2);
        var userFuture = CompletableFuture
                .supplyAsync(() -> userRepository.findById(userId).orElseThrow(), executorService);
        CompletableFuture
                .supplyAsync(() -> getOrderById(orderId), executorService)
                .thenCombine(userFuture, (order, user) -> new OrderCreatedMessage(orderId, order.getOrderDate(),
                        user.getId(), user.getUsername(), order.getTotalCost(), order.getItems().stream()
                        .map(this::mapper).toList()))
                .thenAccept(rabbitSender::send);
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

    @Override
    public List<Order> getOrdersByUserId(long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

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

    private Order getOrderById(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        order.setItems(orderItems);

        for (OrderItem orderItem : order.getItems()) {
            long bookId = orderItem.getBook().getId();
            orderItem.getBook().setAuthors(authorRepository.findByBookId(bookId));
        }

        return order;
    }

    private OrderCreatedMessage.OrderItem mapper(OrderItem item) {
        return OrderCreatedMessage.OrderItem.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .book(mapper(item.getBook()))
                .build();
    }

    private OrderCreatedMessage.BookPayload mapper(Book book) {
        return OrderCreatedMessage.BookPayload.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .genre(book.getGenre().getName())
                .publisher(book.getPublisher().getName())
                .cost(book.getCost())
                .authors(book.getAuthors().stream().map(Author::getFullName).toList())
                .build();
    }

}
