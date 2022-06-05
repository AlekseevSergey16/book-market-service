package com.salekseev.booksmarket.service;

import com.salekseev.booksmarket.model.User;

import java.util.Optional;

public interface UserService {

    long addUser(User user);
    User getUser(String username, String password);

}
