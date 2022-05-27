package com.salekseev.booksmarket.service;

import com.salekseev.booksmarket.model.Shipment;

import java.util.List;

public interface ShipmentService {

    long addShipment(Shipment shipment);
    void updateShipment(Shipment shipment);

    List<Shipment> getAllShipments();

}
