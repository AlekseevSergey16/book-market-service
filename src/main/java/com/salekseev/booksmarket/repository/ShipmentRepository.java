package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Shipment;

import java.util.List;

public interface ShipmentRepository {

    long save(Shipment shipment);
    void update(Shipment shipment);

    List<Shipment> findAll();

}
