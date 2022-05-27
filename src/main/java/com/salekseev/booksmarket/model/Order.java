package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {

    private Long id;
    private LocalDateTime orderDate;
    private List<OrderItem> items;
    private Double totalCost;

}
