package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Book> getBooksByGenre(@RequestParam long genreId) {
        return bookService.getBooksByGenreId(genreId);
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
