package com.salekseev.booksmarket.repository;

import com.salekseev.booksmarket.model.User;

import java.util.Optional;

public interface UserRepository {

    long save(User user);
    Optional<User> findById(long userId);
    Optional<User> findByUsernameAndPassword(String username, String password);

}
