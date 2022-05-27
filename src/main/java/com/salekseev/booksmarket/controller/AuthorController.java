package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.model.Author;
import com.salekseev.booksmarket.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public long createAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return authors;
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable long id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping
    public void updateAuthor(@RequestBody Author author) {
        authorService.updateAuthor(author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable long id) {
        authorService.deleteAuthor(id);
    }

}
