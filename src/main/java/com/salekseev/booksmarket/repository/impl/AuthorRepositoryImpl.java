package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.Author;
import com.salekseev.booksmarket.repository.AuthorRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
class AuthorRepositoryImpl implements AuthorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    AuthorRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Author author) {
        var sql = """
                INSERT INTO author (first_name, last_name, middle_name, information)
                VALUES (:firstName, :lastName, :middleName, :information);
                """;
        var params = new MapSqlParameterSource()
                .addValue("firstName", author.getFirstName())
                .addValue("lastName", author.getLastName())
                .addValue("middleName", author.getMiddleName())
                .addValue("information", author.getInformation());

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public void update(Author author) {
        var sql = """
                UPDATE author
                SET first_name =  :firstName,
                    last_name = :lastName,
                    middle_name = :middleName,
                    information = :information
                WHERE id = :id;
                """;
        var params = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("firstName", author.getFirstName())
                .addValue("lastName", author.getLastName())
                .addValue("middleName", author.getMiddleName())
                .addValue("information", author.getInformation());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Author> findById(long id) {
        var sql = """
                SELECT author.id,
                       author.first_name,
                       author.last_name,
                       author.middle_name,
                       author.information
                FROM author
                WHERE author.id = ?;
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::bookMapper, id)
                .stream().findAny();
    }

    @Override
    public List<Author> findAll() {
        var sql = """
                SELECT author.id,
                       author.first_name,
                       author.last_name,
                       author.middle_name,
                       author.information
                FROM author;
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::bookMapper);
    }

    @Override
    public List<Author> findByBookId(long bookId) {
        var sql = """
                SELECT author.id,
                       author.first_name,
                       author.last_name,
                       author.middle_name,
                       author.information
                FROM author
                    INNER JOIN book_author ba on author.id = ba.author_id
                WHERE ba.book_id = ?
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::bookMapper, bookId);
    }

    @Override
    public void delete(long id) {
        var sql = """
                DELETE
                FROM author
                WHERE id = ?;
                """;
        jdbcTemplate.getJdbcOperations().update(sql, id);
    }

    private Author bookMapper(ResultSet rs, int rowNum) throws SQLException {
        return Author.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .middleName(rs.getString("middle_name"))
                .information(rs.getString("information"))
                .build();
    }

}
