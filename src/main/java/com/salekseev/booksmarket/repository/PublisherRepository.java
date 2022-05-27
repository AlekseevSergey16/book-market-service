package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    long save(Publisher publisher);
    void update(Publisher publisher);

    Optional<Publisher> findById(long id);
    List<Publisher> findAll();

    void delete(long id);

}
