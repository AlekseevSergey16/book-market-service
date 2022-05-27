package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private Long id;
    private Order order;
    private Book book;
    private Integer quantity;

}
