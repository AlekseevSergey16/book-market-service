package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.exception.BadRequestException;
import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.repository.AuthorRepository;
import com.salekseev.booksmarket.repository.BookRepository;
import com.salekseev.booksmarket.repository.ShipmentItemRepository;
import com.salekseev.booksmarket.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ShipmentItemRepository shipmentItemRepository;

    BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, ShipmentItemRepository shipmentItemRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.shipmentItemRepository = shipmentItemRepository;
    }

    @Override
    public long addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.update(book);
    }

    @Override
    public void removeBook(long id) {
        if (shipmentItemRepository.checkExistByBookId(id)) {
            throw new BadRequestException("Невозможно удалить книгу");
        }
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
