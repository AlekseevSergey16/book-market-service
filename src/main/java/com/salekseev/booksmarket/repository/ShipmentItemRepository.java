package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.ShipmentItem;

import java.util.List;

public interface ShipmentItemRepository {

    void saveAll(long shipmentId, List<ShipmentItem> items);
    List<ShipmentItem> findByShipmentId(long shipmentId);
    boolean checkExistByBookId(long bookId);

}
