package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.*;
import com.salekseev.booksmarket.repository.OrderItemRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
class OrderItemRepositoryImpl implements OrderItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    OrderItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(long orderId, List<OrderItem> items) {

    }

    @Override
    public List<OrderItem> findByOrderId(long orderId) {
        var sql = """
                SELECT item.id,
                       item.order_id,
                       item.quantity,
                                
                       book.id               AS book_id,
                       book.title            AS book_title,
                       book.description      AS book_description,
                       book.publication_year AS book_publication_year,
                       book.cost             AS book_cost,
                       book.pages            AS book_pages,
                       book.weight           AS book_weight,
                                
                       genre.id              AS genre_id,
                       genre.name            AS genre_name,
                                
                       publisher.id          AS publisher_id,
                       publisher.name        AS publisher_name,
                       publisher.phone       AS publisher_phone,
                       publisher.email       AS publisher_email,
                       publisher.information AS publisher_information
                                
                FROM order_item item
                    INNER JOIN book ON book.id = item.book_id
                    INNER JOIN genre ON genre.id = book.genre_id
                    INNER JOIN publisher ON publisher.id = book.publisher_id
                WHERE order_id = ?;
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::orderMapper, orderId);
    }

    private OrderItem orderMapper(ResultSet rs, int rowNum) throws SQLException {
        return OrderItem.builder()
                .id(rs.getLong("id"))
                .order(Order.builder().id(rs.getLong("order_id")).build())
                .quantity(rs.getInt("quantity"))
                .book(Book.builder()
                        .id(rs.getLong("book_id"))
                        .title(rs.getString("book_title"))
                        .description(rs.getString("book_description"))
                        .publicationYear(rs.getInt("book_publication_year"))
                        .cost(rs.getDouble("book_cost"))
                        .pages(rs.getInt("book_pages"))
                        .weight(rs.getInt("book_weight"))
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
                        .build())
                .build();
    }

}
