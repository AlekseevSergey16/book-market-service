package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public long createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/search")
    public List<Book> getBooksBy(@RequestParam Optional<Long> genreId,
                                 @RequestParam Optional<Long> authorId,
                                 @RequestParam Optional<Boolean> availability) {
        if ((genreId.isEmpty() && authorId.isEmpty() && availability.isEmpty())
                || (genreId.isPresent() && authorId.isPresent() && availability.isPresent())) {
            throw new RuntimeException();
        }

        if (genreId.isPresent()) {
            return bookService.getBooksByGenreId(genreId.get());
        }

        if (availability.isPresent()) {
            return bookService.getAvailabilityBooks();
        }

        return bookService.getBooksByAuthorId(authorId.get());
    }

    @PutMapping
    public void updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.removeBook(id);
    }

}
