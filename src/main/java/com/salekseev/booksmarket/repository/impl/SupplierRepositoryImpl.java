package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.Supplier;
import com.salekseev.booksmarket.repository.SupplierRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class SupplierRepositoryImpl implements SupplierRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    SupplierRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Supplier supplier) {
        var sql = """
                INSERT INTO supplier (name) VALUES (:name);
                """;
        var params = new MapSqlParameterSource()
                .addValue("name", supplier.getName());

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        var sql = """
                SELECT id, name FROM supplier;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> Supplier.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build());
    }



}
