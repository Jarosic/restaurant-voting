package com.myproject.restaurantvoting.repository;


import com.myproject.restaurantvoting.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    User get(int id);

    User getByEmail(String email);

    User save(User User);

    boolean delete(int id);
}
