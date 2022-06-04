package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.*;
import com.salekseev.booksmarket.repository.ShipmentItemRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
class ShipmentItemRepositoryImpl implements ShipmentItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    ShipmentItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(long shipmentId, List<ShipmentItem> items) {
        var itemSql = """
                INSERT INTO shipment_item (shipment_id, book_id, amount)
                VALUES (:shipmentId, :bookId, :amount);
                """;
        var params = new MapSqlParameterSource[items.size()];

        for (int i = 0; i < items.size(); i++) {
            params[i] = new MapSqlParameterSource()
                    .addValue("shipmentId", shipmentId)
                    .addValue("bookId", items.get(i).getBook().getId())
                    .addValue("amount", items.get(i).getAmount());
        }

        jdbcTemplate.batchUpdate(itemSql, params);
    }

    @Override
    public List<ShipmentItem> findByShipmentId(long shipmentId) {
        var sql = """
                SELECT item.id,
                       item.amount,
                
                       shipment.id           AS shipmentId,
                       shipment.shipment_date,
                       shipment.total_amount,
                
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
                FROM shipment_item item
                         INNER JOIN shipment ON shipment.id = item.shipment_id
                         INNER JOIN book ON book.id = item.book_id
                         INNER JOIN genre ON genre.id = book.genre_id
                         INNER JOIN publisher ON publisher.id = book.publisher_id
                WHERE item.shipment_id = ?;
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::shipmentItemMapper, shipmentId);
    }

    @Override
    public boolean checkExistByBookId(long bookId) {
        var sql = """
                SELECT item.id
                FROM shipment_item item
                WHERE item.book_id = ?;
                """;

        return !jdbcTemplate.getJdbcOperations()
                .query(sql, (rs, rowNum) -> rs.getLong("id"), bookId)
                .isEmpty();
    }

    private ShipmentItem shipmentItemMapper(ResultSet rs, int rowNum) throws SQLException {
        return ShipmentItem.builder()
                .id(rs.getLong("id"))
                .amount(rs.getInt("amount"))
                .shipment(Shipment.builder()
                        .id(rs.getLong("shipmentId"))
                        .shipmentDate(rs.getDate("shipment_date").toLocalDate())
                        .totalAmount(rs.getInt("total_amount"))
                        .build())
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
