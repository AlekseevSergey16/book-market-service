package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.Order;
import com.salekseev.booksmarket.repository.OrderRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
class OrderRepositoryImpl implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    OrderRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Order order) {
        var sql = """
                INSERT INTO orders (order_date, total_cost)
                VALUES (:orderDate, :totalCost);
                """;

        var params = new MapSqlParameterSource()
                .addValue("orderDate", order.getOrderDate())
                .addValue("totalCost", order.getTotalCost());

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public List<Order> findAll() {
        var sql = """
                SELECT orders.id,
                       orders.order_date,
                       orders.total_cost
                FROM orders;
                """;
        return jdbcTemplate.query(sql, this::orderMapper);
    }

    private Order orderMapper(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getLong("id"))
                .orderDate(rs.getTimestamp("order_date").toLocalDateTime())
                .totalCost(rs.getDouble("total_cost"))
                .build();
    }

}
