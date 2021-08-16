package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.repository.UserRepository;
import com.myproject.restaurantvoting.security.SecurityUser;
import com.myproject.restaurantvoting.util.ValidationUtil;
import com.myproject.restaurantvoting.util.VoteUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable("users")
    public List<User> getAll() {
        List<User> users = repository.findAll();
        log.info("getAllUsers: {}", users);
        return users;
    }

    @Cacheable(value = "users")
    public User get(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id = " + id + ", is not exist!"));
        log.info("get: {}", user);
        return user;
    }

    @Cacheable(value = "users")
    public User getByEmail(String email) {
        System.out.println("getByEmail: " + email);
        User user = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User with email = " + email + ", is not exist!"));
        log.info("getByEmail: {}", user);
        return user;
    }

    @CachePut(value = "users", key = "#user.email")
    public User create(User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);
        return repository.save(user);
    }

    @CachePut(value = "users", key = "#user.email")
    public User update(User user, int id) {
        log.info("update user: {}, id: {}", user, id);
        ValidationUtil.assureIdConsistent(user, id);
        if (user.getPassword() == null) {
            User u = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("User with id = " + id + ", is not exist!"));
            user.setPassword(u.getPassword());
        }
        user.setId(id);
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(Integer id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }

    @CachePut("users")
    public User vote(Integer userId, int restaurantId, LocalDateTime votingDateTime) {
        log.info("vote {}", restaurantId);
        User user = new User();
        if (userId != null) {
            User u = repository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User with id = " + userId + ", is not exist!"));
            user = u;
        }
        return repository.save(VoteUtil.voteCreateUpdateHelper(user, restaurantId, votingDateTime));
    }

    @Override
    @Cacheable("users")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername email: {}", email);
        User user = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found! email: " + email));
        return new SecurityUser(user);
    }
}
