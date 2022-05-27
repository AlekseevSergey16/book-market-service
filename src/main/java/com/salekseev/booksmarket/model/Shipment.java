package com.salekseev.booksmarket.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Shipment {

    private Long id;
    private LocalDate shipmentDate;
    private List<ShipmentItem> items;
    private Integer totalAmount;

}
