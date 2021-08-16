package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    @Transactional
    @Override
    User save(User user);

    @Transactional
    @Override
    void deleteById(Integer id);

}
