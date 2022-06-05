package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.model.User;
import com.salekseev.booksmarket.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Long createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping
    public User getUser(@RequestParam String username, @RequestParam String password) {
        return userService.getUser(username, password);
    }

}
