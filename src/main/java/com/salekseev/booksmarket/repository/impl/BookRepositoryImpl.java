package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.Author;
import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.model.Genre;
import com.salekseev.booksmarket.model.Publisher;
import com.salekseev.booksmarket.repository.BookRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
class BookRepositoryImpl implements BookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public long save(Book book) {
        var sql = """
                INSERT INTO book (title, description, publication_year, cost, pages, weight, genre_id, publisher_id)
                VALUES (:title, :description, :year, :cost, :pages, :weight, :genreId, :publisherId);
                """;
        var params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("description", book.getDescription())
                .addValue("year", book.getPublicationYear())
                .addValue("cost", book.getCost())
                .addValue("pages", book.getPages())
                .addValue("weight", book.getWeight())
                .addValue("genreId", book.getGenre().getId())
                .addValue("publisherId", book.getPublisher().getId());

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        long id = (long) keyHolder.getKeys().get("id");

        var sql2 = """
                INSERT INTO book_author (book_id, author_id) VALUES (?, ?);
                """;

        book.getAuthors().stream()
                .map(Author::getId)
                .forEach(authorId -> jdbcTemplate.getJdbcOperations().update(sql2, id, authorId));

        return id;
    }

    @Override
    public List<Book> findAll() {
        var sql = """
                SELECT book.id,
                       book.title,
                       book.description,
                       book.publication_year,
                       book.cost,
                       book.pages,
                       book.weight,
                       book.publisher_id,
                       genre.id              AS genre_id,
                       genre.name            AS genre_name,
                       publisher.id          AS publisher_id,
                       publisher.name        AS publisher_name,
                       publisher.phone       AS publisher_phone,
                       publisher.email       AS publisher_email,
                       publisher.information AS publisher_information
                FROM book
                       INNER JOIN genre ON genre.id = book.genre_id
                       INNER JOIN publisher ON publisher.id = book.publisher_id;
                """;

        return jdbcTemplate.query(sql, this::bookMapper);
    }

    @Override
    public List<Book> findByAuthorId(long authorId) {
        var sql = """
                SELECT book.id,
                       book.title,
                       book.description,
                       book.publication_year,
                       book.cost,
                       book.pages,
                       book.weight,
                       book.publisher_id,
                       genre.id              AS genre_id,
                       genre.name            AS genre_name,
                       publisher.id          AS publisher_id,
                       publisher.name        AS publisher_name,
                       publisher.phone       AS publisher_phone,
                       publisher.email       AS publisher_email,
                       publisher.information AS publisher_information
                FROM book
                       INNER JOIN genre ON genre.id = book.genre_id
                       INNER JOIN publisher ON publisher.id = book.publisher_id
                       INNER JOIN book_author ba on book.id = ba.book_id
                WHERE ba.author_id = ?;
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::bookMapper, authorId);
    }

    @Override
    public List<Book> findByGenreId(long genreId) {
        var sql = """
                SELECT book.id,
                       book.title,
                       book.description,
                       book.publication_year,
                       book.cost,
                       book.pages,
                       book.weight,
                       book.publisher_id,
                       genre.id              AS genre_id,
                       genre.name            AS genre_name,
                       publisher.id          AS publisher_id,
                       publisher.name        AS publisher_name,
                       publisher.phone       AS publisher_phone,
                       publisher.email       AS publisher_email,
                       publisher.information AS publisher_information
                FROM book
                       INNER JOIN genre ON genre.id = book.genre_id
                       INNER JOIN publisher ON publisher.id = book.publisher_id
                WHERE book.genre_id = ?;
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::bookMapper, genreId);
    }

    @Override
    public void update(Book book) {
        var sql = """
                UPDATE book
                SET title = :title,
                    description = :description,
                    publication_year = :publicationYear,
                    cost = :cost,
                    pages = :pages,
                    weight = :weight,
                    genre_id = :genreId
                WHERE book.id = :id;
                """;
        var params = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("description", book.getDescription())
                .addValue("publicationYear", book.getPublicationYear())
                .addValue("cost", book.getCost())
                .addValue("pages", book.getPages())
                .addValue("weight", book.getWeight())
                .addValue("genreId", book.getGenre().getId());

        jdbcTemplate.update(sql, params);
        //todo update list authors
    }

    @Transactional
    @Override
    public void delete(long id) {
        var sql = """
                DELETE FROM book_author
                WHERE book_id = ?;
                """;

        jdbcTemplate.getJdbcOperations().update(sql, id);

        var sql2 = """
                DELETE FROM book
                WHERE id = ?;
                """;

        jdbcTemplate.getJdbcOperations().update(sql2, id);
    }

    private Book bookMapper(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .publicationYear(rs.getInt("publication_year"))
                .cost(rs.getDouble("cost"))
                .pages(rs.getInt("pages"))
                .weight(rs.getInt("weight"))
                .genre(Genre.builder()
                        .id(rs.getLong("genre_id"))
                        .name(rs.getString("genre_name"))
                        .build())
                .publisher(Publisher.builder()
                        .id(rs.getLong("publisher_id"))
                        .name(rs.getString("publisher_name"))
                        .phone(rs.getString("publisher_phone"))
                        .email(rs.getString("publisher_email"))
                        .information(rs.getString("publisher_information"))
                        .build())
                .build();
    }

}
