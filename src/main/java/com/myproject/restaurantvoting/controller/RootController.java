package com.myproject.restaurantvoting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RootController {

    @GetMapping("/index")
    public String root() {
        log.info("Index page");
        return "index";
    }

    @GetMapping("/users")
    public String users() {
        log.info("users page");
        return "users";
    }

    @GetMapping("/restaurants")
    public String restaurants() {
        return "restaurants";
    }
}
