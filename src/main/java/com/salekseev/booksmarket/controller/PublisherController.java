package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.model.Publisher;
import com.salekseev.booksmarket.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public long createPublisher(@RequestBody Publisher publisher) {
        return publisherService.addPublisher(publisher);
    }

    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable long id) {
        return publisherService.getPublisherById(id);
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return publishers;
    }

    @PutMapping
    public void updatePublisher(@RequestBody Publisher publisher) {
        publisherService.updatePublisher(publisher);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable long id) {
        publisherService.deletePublisher(id);
    }

}
