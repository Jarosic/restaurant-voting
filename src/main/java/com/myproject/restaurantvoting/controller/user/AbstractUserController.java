package com.myproject.restaurantvoting.controller.user;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserService userService;

    public List<User> getAll() {
        List<User> users = userService.getAll();
        log.info("GET get all users: {}", users);
        return users;
    }

    public User get(int id) throws Throwable {
        User user = userService.get(id);
        log.info("GET get user by id: {}, user: {}",id, user);
        return user;
    }

    public User getByEmail(String email) {
        User user = userService.getByEmail(email);
        log.info("GET get by email: {}, user: {}",email, user);
        return user;
    }

    public User create(User user) {
        log.info("POST create user: {}", user);
        return userService.create(user);
    }

    public User update(User user, int id) {
        log.info("UPDATE update user: {} id: {}, ", user, id);
        return userService.update(user, id);
    }

    public void delete(int id) {
        log.info("DELETE delete user id: {}", id);
        userService.delete(id);
    }
}
