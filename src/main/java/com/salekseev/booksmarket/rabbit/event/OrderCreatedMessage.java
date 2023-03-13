package com.salekseev.booksmarket.rabbit.event;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class OrderCreatedMessage {

    private Long orderId;
    private LocalDateTime orderDate;
    private Long userId;
    private String userEmail;
    private Double totalCost;
    private List<OrderItem> items;

    public OrderCreatedMessage(Long orderId, LocalDateTime orderDate, Long userId, String userEmail,
                               Double totalCost, List<OrderItem> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.userId = userId;
        this.userEmail = userEmail;
        this.totalCost = totalCost;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    @Builder
    public static class OrderItem {
        private Long id;
        private BookPayload book;
        private Integer quantity;

        public OrderItem(Long id, BookPayload book, Integer quantity) {
            this.id = id;
            this.book = book;
            this.quantity = quantity;
        }

        public Long getId() {
            return id;
        }

        public BookPayload getBook() {
            return book;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }

    @Builder
    public static class BookPayload {
        private Long bookId;
        private String title;
        private String description;
        private List<String> authors;
        private String publisher;
        private String genre;
        private Double cost;

        public BookPayload(Long bookId, String title, String description, List<String> authors, String publisher, String genre, Double cost) {
            this.bookId = bookId;
            this.title = title;
            this.description = description;
            this.authors = authors;
            this.publisher = publisher;
            this.genre = genre;
            this.cost = cost;
        }

        public Long getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getGenre() {
            return genre;
        }

        public Double getCost() {
            return cost;
        }
    }

}
