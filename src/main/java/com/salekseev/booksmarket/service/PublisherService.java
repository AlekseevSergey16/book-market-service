package com.salekseev.booksmarket.service;

import com.salekseev.booksmarket.model.Publisher;

import java.util.List;

public interface PublisherService {

    long addPublisher(Publisher publisher);
    void updatePublisher(Publisher publisher);

    Publisher getPublisherById(long id);
    List<Publisher> getAllPublishers();

    void deletePublisher(long id);

}
