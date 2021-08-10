package com.myproject.restaurantvoting.service;

import com.myproject.restaurantvoting.error.exceptions.NotFoundException;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.repository.UserRepository;
import com.myproject.restaurantvoting.security.SecurityUser;
import com.myproject.restaurantvoting.util.ValidationUtil;
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
        ValidationUtil.checkForExist(user, id, User.class);
        return user;
    }

    public User getByEmail(String email) {
        System.out.println("getByEmail: " + email);
        User user = repository.getByEmail(email);
        ValidationUtil.checkUserByEmail(user, email);
        log.info("getByEmail: {}", user);
        return user;
    }

    public User create(User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);
        return repository.save(user);
    }

    public User update(User user, int id) {
        log.info("update user: {}, id: {}", user, id);
        ValidationUtil.assureIdConsistent(user, id);
        if (user.getPassword() == null) {
            User u = repository.get(id);
            user.setPassword(u.getPassword());
        }
        user.setId(id);
        return repository.save(user);
    }

    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.delete(id);
    }

    public User vote(Integer userId, int restaurantId, LocalDateTime votingDateTime) {
        log.info("vote {}", restaurantId);
        User user = new User();
        if (userId != null) {
            user = repository.get(userId);
        }
        return repository.save(VoteUtil.voteCreateUpdateHelper(user, restaurantId, votingDateTime));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found! email: " + email);
        }
        return new SecurityUser(user);
    }
}
