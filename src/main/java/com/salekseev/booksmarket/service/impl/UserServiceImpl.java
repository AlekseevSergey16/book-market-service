package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.exception.BadRequestException;
import com.salekseev.booksmarket.model.User;
import com.salekseev.booksmarket.repository.UserRepository;
import com.salekseev.booksmarket.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new BadRequestException("Пользователь не найден"));
    }
}
