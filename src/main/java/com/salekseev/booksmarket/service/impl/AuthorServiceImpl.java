package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.exception.BadRequestException;
import com.salekseev.booksmarket.model.Author;
import com.salekseev.booksmarket.repository.AuthorRepository;
import com.salekseev.booksmarket.repository.BookRepository;
import com.salekseev.booksmarket.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public long addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.update(author);
    }

    @Override
    public void deleteAuthor(long id) {
        //todo create method check
        if (!bookRepository.findByAuthorId(id).isEmpty()) {
            throw new BadRequestException("Удаление автора, у которого имеются книги - невозможно.");
        }
        authorRepository.delete(id);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
