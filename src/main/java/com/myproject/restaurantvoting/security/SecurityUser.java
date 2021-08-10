package com.myproject.restaurantvoting.security;

import com.myproject.restaurantvoting.model.User;
import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class SecurityUser extends org.springframework.security.core.userdetails.User {

    @NotNull
    private final User user;

    public SecurityUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
