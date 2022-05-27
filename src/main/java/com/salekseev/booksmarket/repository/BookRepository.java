package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.model.Genre;

import java.util.List;

public interface BookRepository {

    long save(Book book);
    void update(Book book);
    void delete(long id);

    List<Book> findAll();
    List<Book> findByGenreId(long genreId);

}
