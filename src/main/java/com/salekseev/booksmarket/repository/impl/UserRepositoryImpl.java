package com.salekseev.booksmarket.repository.impl;

import com.salekseev.booksmarket.model.User;
import com.salekseev.booksmarket.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(User user) {
        var sql = """
                INSERT INTO users (username, password, role)
                VALUES (:username, :password, :role);
                """;
        var params = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("role", "user");

        var keyHolder = new GeneratedKeyHolder();

        return jdbcTemplate.update(sql, params, keyHolder);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        var sql = """
                SELECT id, username, password, role
                FROM users
                WHERE username = ? AND password = ?;
                """;

        return jdbcTemplate.getJdbcOperations().query(sql, this::userMapper, username, password)
                .stream().findAny();
    }

    private User userMapper(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .role(rs.getString("role"))
                .build();
    }

}
