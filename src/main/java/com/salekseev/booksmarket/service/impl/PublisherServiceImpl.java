package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Publisher;
import com.salekseev.booksmarket.repository.PublisherRepository;
import com.salekseev.booksmarket.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public long addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public void updatePublisher(Publisher publisher) {
        publisherRepository.update(publisher);
    }

    @Override
    public Publisher getPublisherById(long id) {
        return publisherRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public void deletePublisher(long id) {
        publisherRepository.delete(id);
    }

}
