package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    long save(Author author);
    void update(Author author);
    void delete(long id);

    Optional<Author> findById(long id);
    List<Author> findAll();
    List<Author> findByBookId(long bookId);

}
