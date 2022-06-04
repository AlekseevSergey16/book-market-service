package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.exception.BadRequestException;
import com.salekseev.booksmarket.model.Publisher;
import com.salekseev.booksmarket.repository.BookRepository;
import com.salekseev.booksmarket.repository.PublisherRepository;
import com.salekseev.booksmarket.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    PublisherServiceImpl(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
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
        if (bookRepository.checkExistByPublisherId(id)) {
            throw new BadRequestException("Удаление издательства, у которых имеются книги - невозможно.");
        }
        publisherRepository.delete(id);
    }

}
