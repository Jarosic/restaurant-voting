package com.myproject.restaurantvoting.controller;

import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.security.SecurityUser;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal SecurityUser authUser) {
        return authUser.getUser();
    }
}
