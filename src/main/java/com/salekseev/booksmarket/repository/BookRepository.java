package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.model.Genre;

import java.util.List;

public interface BookRepository {

    long save(Book book);
    void update(Book book);
    void delete(long id);
    void increaseAmount(long id, long amount);
    void reduceAmount(long id, long amount);

    List<Book> findAll();
    List<Book> findByAuthorId(long authorId);
    List<Book> findByGenreId(long genreId);
    List<Book> findAllByAmountNotNull();

    boolean checkExistByPublisherId(long publisherId);

}
