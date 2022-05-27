package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.Genre;
import com.salekseev.booksmarket.repository.GenreRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
class GenreRepositoryImpl implements GenreRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    GenreRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> findAllGenres() {
        var sql = """
                SELECT genre.id AS genre_id, genre.name AS genre_name
                FROM genre;
                """;

        return jdbcTemplate.query(sql, this::genreMapper);
    }

    private Genre genreMapper(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("genre_id"))
                .name(rs.getString("genre_name"))
                .build();
    }

}
