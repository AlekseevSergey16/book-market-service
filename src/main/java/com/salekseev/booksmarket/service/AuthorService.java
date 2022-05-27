package com.salekseev.booksmarket.service;

import com.salekseev.booksmarket.model.Author;

import java.util.List;

public interface AuthorService {

    long addAuthor(Author author);
    void updateAuthor(Author author);
    void deleteAuthor(long id);

    Author getAuthorById(long id);

    List<Author> getAllAuthors();

}
