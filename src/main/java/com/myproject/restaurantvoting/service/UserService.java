package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.repository.UserRepository;
import com.myproject.restaurantvoting.util.VoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        List<User> users = repository.getAll();
        LOG.info("getAllUsers: {}", users);
        return users;
    }

    public User get(int id) {
        User user = repository.get(id);
        LOG.info("get: {}", user);
        return user;
    }

    public User getByEmail(String email) {
        User user = repository.getByEmail(email);
        LOG.info("getByEmail: {}", user);
        return user;
    }

    public User create(User user) {
        LOG.info("create {}", user);
        return repository.save(user);
    }

    public User update(User user) {
        LOG.info("update {}", user);
        return repository.save(user);
    }

    public boolean delete(int id) {
        LOG.info("delete {}", id);
       return repository.delete(id);
    }

    public void vote(User user, int restaurantId, LocalDateTime votingDateTime) {
        LOG.info("vote {}", restaurantId);
        repository.save(VoteUtil.voteCreateUpdateHelper(user, restaurantId, votingDateTime));
    }
}
