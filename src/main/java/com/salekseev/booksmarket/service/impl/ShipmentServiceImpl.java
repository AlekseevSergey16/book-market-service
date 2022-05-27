package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Shipment;
import com.salekseev.booksmarket.model.ShipmentItem;
import com.salekseev.booksmarket.repository.ShipmentItemRepository;
import com.salekseev.booksmarket.repository.ShipmentRepository;
import com.salekseev.booksmarket.service.ShipmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentItemRepository shipmentItemRepository;

    ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentItemRepository shipmentItemRepository) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentItemRepository = shipmentItemRepository;
    }

    @Override
    public long addShipment(Shipment shipment) {
        long id = shipmentRepository.save(shipment);
        if (!shipment.getItems().isEmpty()) {
            shipmentItemRepository.saveAll(id, shipment.getItems());
        }

        return id;
    }

    @Override
    public void updateShipment(Shipment shipment) {

    }

    @Override
    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();

        for (Shipment shipment : shipments) {
            long shipmentId = shipment.getId();
            List<ShipmentItem> items = shipmentItemRepository.findByShipmentId(shipmentId);
            shipment.setItems(items);
        }

        return shipments;
    }
}
