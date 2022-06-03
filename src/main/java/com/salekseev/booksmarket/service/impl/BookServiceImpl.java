package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Author;
import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.repository.AuthorRepository;
import com.salekseev.booksmarket.repository.BookRepository;
import com.salekseev.booksmarket.service.BookService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public long addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void updateBook(Book book) {
//        List<Author> authors = authorRepository.findByBookId(book.getId());
//        List<Long> dbAuthorIds = authors
//                .parallelStream()
//                .map(Author::getId)
//                .toList();
//
//        List<Long> bookAuthorIds = book.getAuthors()
//                .parallelStream()
//                .map(Author::getId)
//                .toList();
//
//        Set<Long> authorIdSet = new HashSet<>();
//        authorIdSet.addAll(dbAuthorIds);
//        authorIdSet.addAll(bookAuthorIds);

        bookRepository.update(book);
    }

    @Override
    public void removeBook(long id) {
        bookRepository.delete(id);
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getId().equals(id))
                .findAny().orElseThrow();
    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            book.setAuthors(authorRepository.findByBookId(book.getId()));
        }

        return books;
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);

        for (Book book : books) {
            book.setAuthors(authorRepository.findByBookId(book.getId()));
        }

        return books;
    }

    @Override
    public List<Book> getBooksByGenreId(long genreId) {
        return bookRepository.findByGenreId(genreId);
    }

    @Override
    public List<Book> getAvailabilityBooks() {
        List<Book> books = bookRepository.findAllByAmountNotNull();

        for (Book book : books) {
            book.setAuthors(authorRepository.findByBookId(book.getId()));
        }

        return books;
    }

}
