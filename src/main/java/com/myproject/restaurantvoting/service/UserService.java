package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.repository.UserRepository;
import com.myproject.restaurantvoting.repository.UserRepositoryImpl;
import com.myproject.restaurantvoting.security.SecurityUser;
import com.myproject.restaurantvoting.util.VoteUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    public final UserRepository repository;

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

    public User update(User user, int id) {
        log.info("update user: {}, id: {}", user, id);
        user.setId(id);
        return repository.save(user);
    }

    public boolean delete(int id) {
        log.info("delete {}", id);
       return repository.delete(id);
    }

    public User vote(User user, int restaurantId, LocalDateTime votingDateTime) {
        log.info("vote {}", restaurantId);
        return repository.save(VoteUtil.voteCreateUpdateHelper(user, restaurantId, votingDateTime));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not fount! email: " + email);
        }
        return new SecurityUser(user);
    }
}
