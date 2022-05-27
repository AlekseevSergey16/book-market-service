package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.*;
import com.salekseev.booksmarket.repository.ShipmentRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
class ShipmentRepositoryImpl implements ShipmentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    ShipmentRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Shipment shipment) {
        var sql = """
                INSERT INTO shipment (shipment_date, total_amount)
                VALUES (:shipmentDate, :totalAmount);
                """;
        var params = new MapSqlParameterSource()
                .addValue("shipmentDate", shipment.getShipmentDate())
                .addValue("totalAmount", shipment.getTotalAmount());

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public void update(Shipment shipment) {

    }

    @Override
    public List<Shipment> findAll() {
        var sql = """
                SELECT shipment.id,
                       shipment.shipment_date,
                       shipment.total_amount
                FROM shipment;
                """;

        return jdbcTemplate.query(sql, this::shipmentMapper);
    }

    private Shipment shipmentMapper(ResultSet rs, int rowNum) throws SQLException {
        return Shipment.builder()
                .id(rs.getLong("id"))
                .shipmentDate(rs.getDate("shipment_date").toLocalDate())
                .totalAmount(rs.getInt("total_amount"))
                .build();
    }

}
