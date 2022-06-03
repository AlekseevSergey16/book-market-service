package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.model.Shipment;
import com.salekseev.booksmarket.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public long createShipment(@RequestBody Shipment shipment) {
        return shipmentService.addShipment(shipment);
    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.getAllShipments();
    }

}
