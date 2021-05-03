package com.myproject.restaurantvoting.controller.user;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserService userService;

    public List<User> getAll() {
        List<User> users = userService.getAll();
        log.info("getAllUsers: {}", users);
        return users;
    }

    public User get(int id) {
        User user = userService.get(id);
        log.info("get: {}", user);
        return user;
    }

    public User getByEmail(String email) {
        User user = userService.getByEmail(email);
        log.info("getByEmail: {}", user);
        return user;
    }

    public User create(User user) {
        log.info("create {}", user);
        return userService.create(user);
    }

    public void update(User user, int id) {
        log.info("update user: {} id: {}, ", user, id);
        userService.update(user, id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    public void vote(User user, int restaurantId) {
        log.info("vote {}", restaurantId);
        userService.vote(user, restaurantId, LocalDateTime.now());
    }
}
