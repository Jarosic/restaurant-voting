package com.myproject.restaurantvoting.controller;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "admin/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        List<User> users = userService.getAll();
        log.info("GET all USERS: {}", users);
        return users;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getById(@PathVariable int id) {
        User user = userService.get(id);
        log.info("GET USER BY ID : {}", user);
        return user;
    }
}
