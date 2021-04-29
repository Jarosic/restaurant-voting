package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.repository.UserRepository;
import com.myproject.restaurantvoting.util.VoteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserService {

    public final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        List<User> users = repository.getAll();
        log.info("getAllUsers: {}", users);
        return users;
    }

    public User get(int id) {
        User user = repository.get(id);
        log.info("get: {}", user);
        return user;
    }

    public User getByEmail(String email) {
        User user = repository.getByEmail(email);
        log.info("getByEmail: {}", user);
        return user;
    }

    public User create(User user) {
        log.info("create {}", user);
        return repository.save(user);
    }

    public User update(User user) {
        log.info("update {}", user);
        return repository.save(user);
    }

    public boolean delete(int id) {
        log.info("delete {}", id);
       return repository.delete(id);
    }

    public void vote(User user, int restaurantId, LocalDateTime votingDateTime) {
        log.info("vote {}", restaurantId);
        repository.save(VoteUtil.voteCreateUpdateHelper(user, restaurantId, votingDateTime));
    }
}
