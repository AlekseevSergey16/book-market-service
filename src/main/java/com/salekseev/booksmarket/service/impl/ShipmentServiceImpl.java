package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Shipment;
import com.salekseev.booksmarket.model.ShipmentItem;
import com.salekseev.booksmarket.repository.AuthorRepository;
import com.salekseev.booksmarket.repository.BookRepository;
import com.salekseev.booksmarket.repository.ShipmentItemRepository;
import com.salekseev.booksmarket.repository.ShipmentRepository;
import com.salekseev.booksmarket.service.ShipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentItemRepository shipmentItemRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentItemRepository shipmentItemRepository,
                        AuthorRepository authorRepository, BookRepository bookRepository) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentItemRepository = shipmentItemRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public long addShipment(Shipment shipment) {
        if (shipment.getItems().isEmpty()) {
            throw new RuntimeException("Не указаны поставленные книги");
        }

        long id = shipmentRepository.save(shipment);

        //todo сделать триггером
        shipmentItemRepository.saveAll(id, shipment.getItems());
        shipment.getItems()
                .forEach(item -> bookRepository.increaseAmount(item.getBook().getId(), item.getAmount()));

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
            shipment.setItems(shipmentItemRepository.findByShipmentId(shipmentId));

            for (ShipmentItem shipmentItem : shipment.getItems()) {
                long bookId = shipmentItem.getBook().getId();
                shipmentItem.getBook().setAuthors(authorRepository.findByBookId(bookId));
            }

        }

        return shipments;
    }
}
