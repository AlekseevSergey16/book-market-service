package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.Publisher;
import com.salekseev.booksmarket.repository.PublisherRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
class PublisherRepositoryImpl implements PublisherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    PublisherRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Publisher publisher) {
        var sql = """
            INSERT INTO publisher (name, information, email, phone)
            VALUES (:name, :information, :phone, :email);
            """;

        var params = new MapSqlParameterSource()
                .addValue("name", publisher.getName())
                .addValue("phone", publisher.getPhone())
                .addValue("email", publisher.getEmail())
                .addValue("information", publisher.getInformation());

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public void update(Publisher publisher) {
        var sql = """
            UPDATE publisher
            SET name        = :name,
                phone       = :phone,
                email       = :email,
                information = :information
            WHERE id = :id;
            """;

        var params = new MapSqlParameterSource()
                .addValue("id", publisher.getId())
                .addValue("name", publisher.getName())
                .addValue("phone", publisher.getPhone())
                .addValue("email", publisher.getEmail())
                .addValue("information", publisher.getInformation());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Publisher> findById(long id) {
        var sql = """
                    SELECT publisher.id,
                           publisher.name,
                           publisher.phone,
                           publisher.email,
                           publisher.information
                    FROM publisher
                    WHERE id = ?;
                    """;
        return jdbcTemplate.getJdbcTemplate().query(sql, this::publisherMapper, id)
                .stream().findAny();
    }

    @Override
    public List<Publisher> findAll() {
        var sql = """
                SELECT publisher.id,
                       publisher.name,
                       publisher.phone,
                       publisher.email,
                       publisher.information
                FROM publisher;
                """;
        return jdbcTemplate.getJdbcTemplate().query(sql, this::publisherMapper);
    }

    @Override
    public void delete(long id) {
        var sql = """
                DELETE
                FROM publisher
                WHERE id = ?;
                """;
        jdbcTemplate.getJdbcOperations().update(sql, id);
    }

    private Publisher publisherMapper(ResultSet rs, int rowNum) throws SQLException {
        return Publisher.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .phone(rs.getString("phone"))
                .email(rs.getString("email"))
                .information(rs.getString("information"))
                .build();
    }

}
