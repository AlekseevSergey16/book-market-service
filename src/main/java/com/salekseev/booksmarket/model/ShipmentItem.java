package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipmentItem {

    private Long id;
    private Shipment shipment;
    private Book book;
    private Integer amount;

}
