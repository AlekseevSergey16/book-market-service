package com.salekseev.booksmarket.service;

import com.salekseev.booksmarket.model.Book;

import java.util.List;

public interface BookService {

    long addBook(Book book);
    void updateBook(Book book);
    void removeBook(long id);

    Book getBookById(long id);
    List<Book> getBooks();
    List<Book> getBooksByGenreId(long genreId);

}
